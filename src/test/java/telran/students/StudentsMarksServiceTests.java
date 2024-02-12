package telran.students;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import telran.students.dto.Mark;
import telran.students.dto.Student;
import telran.students.exceptions.StudentIllegalStateException;
import telran.students.exceptions.StudentNotFoundException;
import telran.students.repo.StudentRepo;
import telran.students.service.StudentsService;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class StudentsMarksServiceTests {
	@Autowired
	 StudentsService studentService;
	@Autowired
	 StudentRepo studentRepo;
	
	final private static long ID_1 = 123L;
	final private static long ID_2 = 321L;
	final private static String PHONE_1 = "050-407-00-64";
	final private static String PHONE_2 = "050-937-27-73";
	final private static String SUBJECT_1 = "java";
	final private static int SCORE_1 = 85;
	final private static String DATE_STR = "2022-10-10";
	final private static LocalDate DATE_1 = LocalDate.parse(DATE_STR);
	final private static Mark MARK_1 = new Mark(SUBJECT_1, SCORE_1, DATE_1);
	final private static int EXCPECTED_INDEX = 0;
	
	List<Mark> mark = new ArrayList<>();
	
	Student student1 = new Student(ID_1, PHONE_1);
	
	
	
	@BeforeEach
	void setUp(@Autowired StudentRepo studentRepo) {
		studentRepo.deleteAll();
	}
	
	
	@Test
	void testAddStudent() {
		
		Student excpectedStudent = studentService.addStudent(student1);
		Student actualStudent = studentRepo.findById(ID_1).get().build();
		assertEquals(excpectedStudent, actualStudent);
		assertThrowsExactly(StudentIllegalStateException.class, () -> studentService.addStudent(student1));

	}
	
	@Test
	void tetsUpdatePhoneNumber() {
		studentService.addStudent(student1);
		studentService.updatePhoneNumber(ID_1, PHONE_2);
		String excpectedPhone  = PHONE_2;
		String actualPhone = studentRepo.findById(ID_1).get().build().phone();
		assertEquals(excpectedPhone, actualPhone);
		assertThrowsExactly(StudentNotFoundException.class, () -> studentService.updatePhoneNumber(ID_2, PHONE_2));
	}
	
	@Test
	void testAddMark() {
		studentService.addStudent(student1);
		Mark actualMark = studentService.addMark(ID_1, MARK_1);
		Mark excpectedMark = studentRepo.findById(ID_1).get().getMarks().get(EXCPECTED_INDEX);
		assertEquals(actualMark, excpectedMark);
		assertThrowsExactly(StudentNotFoundException.class, () -> studentService.addMark(ID_2, MARK_1));
	}

}
