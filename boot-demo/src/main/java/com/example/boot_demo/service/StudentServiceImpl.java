package com.example.boot_demo.service;

import com.example.boot_demo.Response;
import com.example.boot_demo.converter.StudentConverter;
import com.example.boot_demo.dao.Student;
import com.example.boot_demo.dao.StudentRepository;
import com.example.boot_demo.dto.StudentDTO;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO getStudentById(long id) {
        Student student = studentRepository.findById(id).orElseThrow(RuntimeException::new);
        return new StudentDTO(student.getId(), student.getName(), student.getEmail(), student.getAge());
    }

    @Override
    public Long adddNewStudent(StudentDTO studentDTO) {
        List<Student> studentList =  studentRepository.findByEmail(studentDTO.getEmail());
        if(!CollectionUtils.isEmpty(studentList)){
            throw new IllegalStateException("email:"+studentDTO.getEmail()+"have been taken");
        }

        Student student =studentRepository.save(StudentConverter.convertStudent(studentDTO));
        return studentDTO.getId();
    }

    @Override
    public void deleteStudentById(long id) {
        studentRepository.findById(id).orElseThrow(()-> new IllegalStateException("id:"+id+"doesn't exist"));
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public StudentDTO updateStudentById(long id, String name, String email) {
        Student studentInDB=studentRepository.findById(id).orElseThrow(()-> new IllegalStateException("id:"+id+"doesn't exist"));
        if(org.springframework.util.StringUtils.hasLength(name)&& !studentInDB.getName().equals(name)){

            studentInDB.setName(name);
        }
        if(org.springframework.util.StringUtils.hasLength(name)&& !studentInDB.getEmail().equals(email)){

            studentInDB.setEmail(email);
        }


        Student student=studentRepository.save(studentInDB);
        return StudentConverter.convertStudent(student);
    }

}
