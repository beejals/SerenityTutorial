package com.studentapp.junit.Feature1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Manual;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
public class FirstSerenityTest {
	
	@BeforeClass
	public static void init() {
		RestAssured.baseURI="http://10.53.213.40:8085/student";
	}
	
	@Test
	public void getAllStudents() {
		SerenityRest.given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200);
	}
	
	@Test
	public void thisIsAFailingTest() {
		SerenityRest.given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(500);
	}
	
	@Pending
	@Test
	public void thisIsAPendingTest() {
		
	}
	
	@Ignore
	@Test
	public void thisIsASkippedTest() {
		
	}
	
	@Test
	public void thisIsAErrorTest() {
		System.out.println("This is an error"+(5/0));
	}
	
	@Test
	public void fileDoesNotExist() throws FileNotFoundException {
		File file = new File("E://file.txt");
		FileReader fr = new FileReader(file);
	}
	
	@Manual
	@Test
	public void thisIsAManualTest() {
		
	}
	
	@Title("Get Student Information from Student App")
	@Test
	public void test01() {
		SerenityRest.given()
		.when()
		.get("/list")
		.then()
		.log()
		.all()
		.statusCode(200);
	}

}
