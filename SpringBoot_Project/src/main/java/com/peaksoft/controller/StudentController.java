package com.peaksoft.controller;

import com.peaksoft.entity.Group;
import com.peaksoft.entity.Student;
import com.peaksoft.service.CompanyService;
import com.peaksoft.service.GroupService;
import com.peaksoft.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;
    private final GroupService groupService;
    private final CompanyService companyService;
    @Autowired
    public StudentController(StudentService studentService, GroupService groupService, CompanyService companyService) {
        this.studentService = studentService;
        this.groupService = groupService;
        this.companyService = companyService;
    }

    @GetMapping
    public String students(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "student/students";
    }
    @GetMapping("/addStudent")
    public String add(Model model){
        model.addAttribute("student",new Student());
        model.addAttribute("groups",groupService.getAllGroups());
        return "student/addStudent";
    }
    @PostMapping("/saveStudent")
    public String save(@ModelAttribute("student") Student student){
        studentService.addStudent(student,student.getGroupId());
        return "redirect:/students";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        List<Group>groups = groupService.getAllGroups();
        model.addAttribute("groups",groups);
        Student student = studentService.getById(id);
        model.addAttribute("student",student);
        return "student/updateStudent";
    }
    @PatchMapping("/{id}")
    public String updateStudent(@PathVariable("id") Long id,@ModelAttribute("student") Student student){
        studentService.updateStudent(id,student,student.getGroupId());
        return "redirect:/students";
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        Student student = studentService.getById(id);
        studentService.deleteStudent(student);
        return "redirect:/students";
    }
}
