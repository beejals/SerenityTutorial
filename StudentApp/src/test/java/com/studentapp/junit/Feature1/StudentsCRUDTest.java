package com.studentapp.junit.Feature1;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.studentapp.model.StudentClass;
import com.studentapp.testbase.TestBase;
import com.studentapp.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase {
	
	static String firstName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String lastName = "SMOKEUSER"+TestUtils.getRandomValue();
	static String programme = "ComputerScience";
	static String email = TestUtils.getRandomValue()+"zyz@gmail.com";
	static int studentId;

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
		
		SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.log()
		.all()
		.when()
		.body(student)
		.post()
		.then()
		.log()
		.all()
		.statusCode(201);
	}
	
	@Title("Verify New Student is Created")
	@Test
	public void test002() {
		String p1 = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
		
		HashMap<String,Object> value = SerenityRest.rest().given()
				.log()
				.all()
				.when()
				.get("/list")
				.then()
				.log()
				.all()
				.statusCode(200)
				.extract()
				.path(p1+firstName+p2);
		
		System.out.println("The retrieved value is: "+value);
		
		assertThat(value,hasValue(firstName));
		studentId = (int) value.get("id");
	}
	
	@Title("Update the Student Name and Verify")
	@Test
	public void test003() {
		String p1 = "findAll{it.firstName=='";
		String p2 = "'}.get(0)";
		ArrayList<String> courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("C++");
		
		firstName = firstName+"_Updated";
		
		StudentClass student = new StudentClass();
		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setProgramme(programme);
		student.setCourses(courses);
		
		// put operation to update the student
		SerenityRest.rest().given()
		.contentType(ContentType.JSON)
		.log()
		.all()
		.when()
		.body(student)
		.put("/"+studentId)
		.then()
		.log()
		.all()
		.statusCode(200);
		
		// query and verify the update
		HashMap<String,Object> value = SerenityRest.rest().given()
				.log()
				.all()
				.when()
				.get("/list")
				.then()
				.log()
				.all()
				.statusCode(200)
				.extract()
				.path(p1+firstName+p2);
		
		System.out.println("The retrieved value is: "+value);
		
		assertThat(value,hasValue(firstName));
	}
	
	@Title("Delete the Student and Verify")
	@Test
	public void test004() {
		// delete the student
		SerenityRest.rest().given()
		.log()
		.all()
		.when()
		.delete("/"+studentId)
		.then()
		.log()
		.all()
		.statusCode(204);
		
		// verify that student is deleted
		SerenityRest.rest().given()
		.log()
		.all()
		.when()
		.get("/"+studentId)
		.then()
		.log()
		.all()
		.statusCode(404);
	}
}
