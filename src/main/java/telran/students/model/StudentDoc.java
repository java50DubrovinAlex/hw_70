package telran.students.model;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.students.dto.*;

@Document(collection="students")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDoc {
	@Id
	long id;
	@Setter
	String phone;
	@Setter
	List<Mark> marks = new ArrayList<>();
	
	public StudentDoc(Student student) {
		id = student.id();
		phone = student.phone();
	}
	
	public Student build() {
		return new Student(id, phone);
	}
}
