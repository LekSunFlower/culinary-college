package com.culinarycollege.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.culinarycollege.model.entities.Course;
import com.culinarycollege.model.entities.Grade;
import com.culinarycollege.model.entities.Recipe;
import com.culinarycollege.model.entities.Student;
import com.culinarycollege.model.entities.User;
import com.culinarycollege.service.CourseService;
import com.culinarycollege.service.GradeService;
import com.culinarycollege.service.LessonService;
import com.culinarycollege.service.RecipeService;
import com.culinarycollege.service.StudentService;
import com.culinarycollege.service.UserService;

@Controller
@RequestMapping("/site")
public class PublicController {

    private final CourseService courseService;
    private final LessonService lessonService;
    private final RecipeService recipeService;
    private final UserService userService;
    private final StudentService studentService;
    private final GradeService gradeService;

    public PublicController(CourseService courseService,
                            LessonService lessonService,
                            RecipeService recipeService,
                            UserService userService,
                            StudentService studentService,
                            GradeService gradeService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.recipeService = recipeService;
        this.userService = userService;
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Course> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "index"; 
    }

    @GetMapping("/courses")
    public String courses(Model model) {
        model.addAttribute("courses", courseService.getAll());
        return "courses"; 
    }

    @GetMapping("/courses/{id}")
    public String courseDetails(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getById(id);
        model.addAttribute("course", course);
        model.addAttribute("lessons", course.lessons);
        return "course-details";
    }

    @GetMapping("/lessons/{id}")
    public String lessonDetails(@PathVariable("id") Long id, Model model) {
        var lesson = lessonService.getById(id);

        model.addAttribute("lesson", lesson);
        model.addAttribute("recipes", recipeService.findByLesson(lesson));

        return "lesson-details";
    }

    @GetMapping("/recipes/{id}")
    public String recipeDetails(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.getById(id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeIngredients", recipe.recipeIngredients);
        return "recipe-details";
    }

    @GetMapping("/student/grades")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentGrades(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Student student = studentService.findByUser(user);
        List<Grade> grades = gradeService.findByStudent(student);
        model.addAttribute("student", student);
        model.addAttribute("grades", grades);
        return "student-grades"; 
    }
}