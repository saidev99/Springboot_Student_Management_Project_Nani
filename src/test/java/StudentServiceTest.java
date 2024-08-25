import com.example.Sainath.Sai;
import com.example.Sainath.Service.StudentService;
import com.example.Sainath.controller.Repository.StudentRepository;
import com.example.Sainath.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Equals;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Sai.class)
public class StudentServiceTest {

    //Create the Mock Instance for the StudentRepository
    @Mock
    private StudentRepository studentRepository;

    //Inject the Mockito StudentRepository to StudentService by this it allows you to test the service class
    @InjectMocks
    private StudentService studentService;

    //BeforeEach: This setup() method should run before each test case.
    //MockitoAnnotations.openMocks(this): This initializes the mock for StudentRepository and injects them into StudentService instance.
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    //Test Case Method: For to get All Students
@Test
    void testGetAllStudents() {
    Student student1 = new Student(1L,"Dinesh K","dineshk@gmail.com");
    Student student2 = new Student(2L,"Vinay R","vinayr@gmail.com");
    when(studentRepository.findAll()).thenReturn(Arrays.asList(student1,student2));

    //getAllStudents() method of StudentService is called
    List<Student> students = studentService.getAllStudents();

    //Assert
    assertEquals(2,students.size());
    verify(studentRepository, times(1)).findAll();
}
//Test Case Method: For to Get the Student By id
@Test
    void testGetStudentById(){
        Long id= 1L;
        Student student = new Student(id,"Sainath","sainath@gmail.com");
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        //getStudentById() method of StudentService is called
        //Optional<Student> foundStudent = studentService.getStudentById(1L);
        Optional<Student> foundStudent = studentService.getStudentById(id);
        //Assert
          assertTrue(foundStudent.isPresent(),"Student should be present");
//        assertTrue(foundStudent.isPresent());
        assertEquals("Sainath",foundStudent.get().getName());
        verify(studentRepository,times(1)).findById(1L);

}
//Test Case: For Create the Student
    void testCreateStudent(){
        Student student = new Student(null,"Sainath","sainath@gmail.com");
        when(studentRepository.save(student)).thenReturn(new Student(1L, "Sainath","sainath@gmail.com"));

        //createStudent() method of StudentService is called
        Student createStudent = studentService.createStudent(student);

        //Assert
        assertNotNull(createStudent.getId());
        assertEquals("Sainath",createStudent.getName());
        verify(studentRepository,times(1)).save(student);
    }

    //Test Case Method: For Update Student
    @Test
    void testUpdateStudent(){
        Student existingStudent = new Student(1L,"Sainath","sainath@gmail.com");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        //updateStudent() method of StudentService is called
        Student updateStudent = studentService.updateStudent(1L,existingStudent);

        //Assert
        assertEquals("Sainath",updateStudent.getName());
        verify(studentRepository,times(1)).findById(1L);
        verify(studentRepository,times(1)).save(existingStudent);
    }
//Test Case Method: For Delete Student
    @Test
    void testDeleteStudent(){
        Long id= 1L;
        Student student = new Student(id,"Sainath","sainath@gmail.com");
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        //doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(id);

        //verify(studentRepository,times(1)).deleteById(1L);
        verify(studentRepository).findById(id);
        verify(studentRepository).deleteById(id);
    }

}
