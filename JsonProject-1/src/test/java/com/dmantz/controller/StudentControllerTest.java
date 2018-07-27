package com.dmantz.controller;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dmantz.model.Course;
import com.dmantz.service.StudentService;

//ClassRunner providing the functionality to launch a Spring TestContext Framework
@RunWith(SpringRunner.class)

// This can be used when a test focuses only Spring MVC components

@WebMvcTest(value=StudentController.class,secure=false)

public class StudentControllerTest {
	
	//MockMvc is the main entry point for server-side Spring MVC test support
	
	@Autowired
	private MockMvc mockMvc;
	
//MockBean is used to add mocks to a Spring ApplicationContext. A mock of studentService is created and auto-wired into the StudentController
	
	@MockBean
	private StudentService studentService;
	
	
Course mockCourse=new Course("Course1", "Spring", "10 Steps",
				Arrays.asList("Learn springBoot", "Import Project", "First Example",
						"Second Example"));
String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn springBoot\",\"Import Project\",\"First Example\",\"Second Example\"]}";
	

@Test
	public void RetrieveDetailsForCourse() throws Exception {

	
	//Mocking the method retrieveCourse to return the specific mockCourse when invoked
	
		Mockito.when(
				studentService.retrieveCourse(Mockito.anyString(),
						Mockito.anyString())).thenReturn(mockCourse);
		
//Creating a Request builder to be able to execute a get request to uri “/students/Student1/courses/Course1” with accept header as “application/json”
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/students/Student1/courses/Course1").accept(
				MediaType.APPLICATION_JSON);
		
//request and return the response back
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());
		String expected = "{id:Course1,name:Spring,description:10 Steps}";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}

@Test
public void createStudentCourse() throws Exception {
	Course mockCourse = new Course("1", "Smallest Number", "1",
			Arrays.asList("1", "2", "3", "4"));

	// studentService.addCourse to respond back with mockCourse
	
	Mockito.when(
			studentService.addCourse(Mockito.anyString(),
					Mockito.any(Course.class))).thenReturn(mockCourse);

	// Send course as body to /students/Student1/courses
	
	RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/students/Student1/courses")
			.accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
			.contentType(MediaType.APPLICATION_JSON);

	MvcResult result = mockMvc.perform(requestBuilder).andReturn();

	MockHttpServletResponse response = result.getResponse();

	assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	assertEquals("http://localhost/students/Student1/courses/1",
			response.getHeader(HttpHeaders.LOCATION));

}

	}
	
	


