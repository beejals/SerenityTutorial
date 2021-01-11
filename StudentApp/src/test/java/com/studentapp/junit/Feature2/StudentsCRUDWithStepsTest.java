package com.studentapp.junit.Feature2;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.studentapp.model.StudentClass;
import com.studentapp.testbase.TestBase;
import com.studentapp.utils.ReuseableSpecifications;
import com.studentapp.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDWithStepsTest extends TestBase {
	
	static String firstName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String lastName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String programme = "ComputerScience";
	static String email = TestUtils.getRandomValue()+"zyz@gmail.com";
	static int studentId;

	@Steps
	StudentSerenitySteps steps;
	
	@Title("Create a New Student")
	@Test
	public void test001() {
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
		
		steps.createStudent(firstName, lastName, email, programme, courses)
		.statusCode(201)
		.spec(ReuseableSpecifications.getGenericResponseSpec());
	}
	
	@Title("Verify New Student is Created")
	@Test
	public void test002() {
		HashMap<String,Object> value = steps.getStudentInfoByFirstName(firstName);
		
		System.out.println("The retrieved value is: "+value);
		
		assertThat(value,hasValue(firstName));
		studentId = (int) value.get("id");
	}
	
	@Title("Update the Student Name and Verify")
	@Test
	public void test003() {
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		
		firstName = firstName+"_Updated";
		steps.updateStudent(studentId, firstName, lastName, email, programme, courses);
		
		// query and verify the update
		HashMap<String,Object> value = steps.getStudentInfoByFirstName(firstName);
		assertThat(value,hasValue(firstName));
	}
	
	@Title("Delete the Student and Verify")
	@Test
	public void test004() {
		// delete the student
		steps.deleteStudent(studentId);
		
		// verify that student is deleted
		steps.getStudentById(studentId).statusCode(404);
	}
}
