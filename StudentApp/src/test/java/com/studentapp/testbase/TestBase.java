package com.studentapp.testbase;

import org.junit.BeforeClass;

import io.restassured.RestAssured;

public class TestBase {

	@BeforeClass
	public static void init(){
		RestAssured.baseURI = "http://10.53.213.40:8085/student";
	}
}
