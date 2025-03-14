package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .flatMap(student -> studentCourseRepository.findByStudentId(student.getId()).stream())
                .collect(Collectors.toList());
    }

    public Optional<Student> findStudentWithHighestGpa() {
        List<Student> students = studentRepository.findAll();
        Student highestGpaStudent = students.get(0);
        for (int i = 1; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getGpa() > highestGpaStudent.getGpa()) {
                highestGpaStudent = student;
            }
        }
        return Optional.ofNullable(highestGpaStudent);
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            result.append(student.getName()).append(", ");
        }
        if (result.length() > 2) {
            result.setLength(result.length() - 2);
        }
        return result.toString();
    }
}

