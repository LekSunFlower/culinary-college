package com.culinarycollege.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.culinarycollege.model.entities.Course;
import com.culinarycollege.model.entities.Lesson;
import com.culinarycollege.service.CourseService;
import com.culinarycollege.service.GradeService;
import com.culinarycollege.service.LessonService;
import com.culinarycollege.service.RecipeService;
import com.culinarycollege.service.StudentService;
import com.culinarycollege.service.TeacherService;

@Controller
@RequestMapping("/site/teacher")
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {

    private final CourseService courseService;
    private final GradeService gradeService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final LessonService lessonService;
    private final RecipeService recipeService;

    public TeacherController(
            CourseService courseService,
            GradeService gradeService,
            StudentService studentService,
            TeacherService teacherService,
            LessonService lessonService,
            RecipeService recipeService 
    ) {
        this.courseService = courseService;
        this.gradeService = gradeService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.lessonService = lessonService;
        this.recipeService = recipeService;
    }

    @GetMapping("/manage")
    public String managePage(
            @RequestParam(name = "courseId", required = false) Long courseId,
            @RequestParam(name = "lessonId", required = false) Long lessonId,
            Model model
    ) {
        model.addAttribute("courses", courseService.getAll());

        if (courseId != null) {
            var course = courseService.getById(courseId);
            model.addAttribute("selectedCourse", course);
            model.addAttribute("lessons", lessonService.findByCourse(course));
        }

        if (lessonId != null) {
            var lesson = lessonService.getById(lessonId);
            model.addAttribute("selectedLesson", lesson);
            model.addAttribute("recipes", recipeService.findByLesson(lesson));
        }

        return "teacher-manage";
    }

    @PostMapping("/courses/create")
    public String createCourse(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description
    ) {
        courseService.create(title, description);
        return "redirect:/site/teacher/manage";
    }

    @PostMapping("/lessons/create")
    public String createLesson(
            @RequestParam(name = "courseId") Long courseId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content
    ) {
        lessonService.create(courseId, title, content);
        return "redirect:/site/teacher/manage?courseId=" + courseId;
    }

    @PostMapping("/recipes/create")
    public String createRecipe(
            @RequestParam(name = "lessonId") Long lessonId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description
    ) {
        recipeService.create(lessonId, title, description);
        return "redirect:/site/teacher/manage?lessonId=" + lessonId;
    }

    @GetMapping("/grades")
    public String gradesPage(
            @RequestParam(name = "courseId", required = false) Long courseId,
            @RequestParam(name = "lessonId", required = false) Long lessonId,
            Model model
    ) {
        model.addAttribute("courses", courseService.getAll());

        if (courseId != null) {
            Course selectedCourse = courseService.getById(courseId);
            model.addAttribute("selectedCourse", selectedCourse);
        }

        if (lessonId != null) {
            Lesson selectedLesson = lessonService.getById(lessonId);
            model.addAttribute("selectedLesson", selectedLesson);

            model.addAttribute("students", studentService.getAll());

            model.addAttribute("grades", gradeService.findByLesson(selectedLesson));
        }

        return "teacher-grades";
    }

    @PostMapping("/grades/{lessonId}/set")
    public String setGrade(
            @PathVariable("lessonId") Long lessonId,
            @RequestParam("studentId") Long studentId,
            @RequestParam("value") Integer value
    ) {
        gradeService.setGrade(studentId, lessonId, value);
        return "redirect:/site/teacher/grades?lessonId=" + lessonId;
    }

    @PostMapping("/save-grade")
    @ResponseBody
    public String gradeStudent(
            @RequestParam("studentId") Long studentId,
            @RequestParam("lessonId") Long lessonId,
            @RequestParam("value") Integer value
    ) {
        gradeService.setGrade(studentId, lessonId, value);
        return "OK";
    }
}
