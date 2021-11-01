package com.example.demo.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> GetStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (studentByEmail.isPresent())
            throw new IllegalStateException("Email is already taken.");

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists)
            throw new IllegalStateException("Not found student record with ID " + studentId);

        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student currentStudent = studentRepository.getById(studentId);
        if (currentStudent == null) {
            throw new IllegalStateException("Not found student record with ID " + studentId);
        } else {
            if (name != null && name.length() > 0 && !Objects.equals(currentStudent.getName(), name))
                currentStudent.setName(name);

            if (email != null && email.length() > 0 && !Objects.equals(currentStudent.getEmail(), email)) {
                Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);
                if (studentByEmail.isPresent())
                    throw new IllegalStateException("Email is already taken.");

                currentStudent.setEmail(email);
            }
        }
    }

}
