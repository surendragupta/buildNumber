package com.sinet.gage.provision.controller;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.service.CourseService;
import com.sinet.gage.provision.service.WelcomeLetterService;

/**
 * Test class for {@link com.sinet.gage.provision.controller.WelcomeController}
 * 
 * @author Team Gage
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class WelcomeControllerTest {
	
	private static final String SERVICE_URI = "/welcome/letter/12345/administrator";
	
	private static final String TOKEN_HEADER = "token";
	
	private MockMvc mockMvc;
	
	@InjectMocks
	private WelcomeController mockWelcomeController;
	
	@Mock
	private CourseService mockCourseService;
	
	@Mock
	private WelcomeLetterService mockWelcomeLetterService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				  .standaloneSetup(mockWelcomeController)
				  .setMessageConverters(new ResourceHttpMessageConverter())
				  .build();
	}

	/**
	 * Test method for {@link com.sinet.gage.provision.controller.WelcomeController#generateWelcomeLetter(java.lang.String, java.lang.Long, java.lang.String)}.
	 * @throws Exception 
	 */
	@Test
	public void testGenerateWelcomeLetter() throws Exception {
		List<String> courseNames = new ArrayList<>();
		courseNames.add("Language Arts 1 A - Sinet 14-15 2015-2016");
		when ( mockCourseService.getAllCourseNamesForDomain( anyString(), anyString() ) ).thenReturn( courseNames );
		when( mockWelcomeLetterService.createWelcomeLetter(anyLong(), anyString(), anyListOf(String.class))).thenReturn( createInputStream() );
		
		mockMvc.perform(get(SERVICE_URI)
				.header(TOKEN_HEADER, "")
				.contentType(MediaType.TEXT_PLAIN)
				.accept(MediaType.ALL))
				.andExpect( status().isOk() )
				.andExpect( header().string("Content-Disposition", "attachment; filename=welcome_letter.doc"))
				.andExpect( content().contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
		
		verify( mockWelcomeLetterService ).createWelcomeLetter(anyLong(), anyString(), anyListOf(String.class));
		verify( mockCourseService ).getAllCourseNamesForDomain( anyString(), anyString() );
	}
	
	private InputStream createInputStream() {
		ByteArrayInputStream stream = new ByteArrayInputStream("content".getBytes());
		return stream;
	}

}
