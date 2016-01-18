/**
 * 
 */
package com.sinet.gage.provision.model;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sinet.gage.dlap.entities.DomainResponse;
import com.sinet.gage.dlap.entities.DomainRightsResponse;
import com.sinet.gage.dlap.entities.EdivateLearn;
import com.sinet.gage.dlap.entities.LoginResponse;
import com.sinet.gage.dlap.entities.SubscriptionResponse;
import com.sinet.gage.provision.Application;
import com.sinet.gage.provision.service.AuthService;
import com.sinet.gage.provision.util.MessageConstants;

/**
 * @author Team.Gage
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
public class MessageUtilTest {

	private static final String EMPTY = StringUtils.EMPTY;

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	AuthService authService;

	private static final String USER_ID = "2347";
	private static final String DOMAIN_ID = "2348";
	private static final String TOKEN = "~FbT1BAAAAAAwGEPXkzlEtA.TbQ7i9AUs3JhpkGo5rK_pB";
	private static final boolean SUCCESS_STATUS=true;
	private static final boolean ERROR_STATUS=false;
	

	@Before
	public void setUp() throws Exception {
		// Object o = new Object();
		// messageUtil = new MessageUtil();
	}

	/**
	 * Test method for
	 * {@link com.sinet.gage.provision.model.MessageUtil#createMessageWithPayload(java.lang.String, java.util.List, java.util.Map, java.lang.Class)}
	 * .
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMessageWithPayloadStringListOfTMapOfStringStringClassOfT()
			throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		/*
		 * List<Object> listOfData = new ArrayList<>(); Object[] dataArray = new
		 * Object[listOfData.size()];//(Object[])Array.newInstance((Class<?>)
		 * new Object(), listOfData.size()); DataArray<Object> data= new
		 * DataArray<Object>(listOfData.toArray(dataArray));
		 * 
		 * Message message = new Message( MessageType.SUCCESS, "", data);
		 * Map<String,String> nameReplaceMap = new HashMap<>();
		 * nameReplaceMap.put("Msg", "Testing"); ObjectMapper mapper = new
		 * ObjectMapper(); mapper.setPropertyNamingStrategy(new
		 * ReplaceNamingStrategy(nameReplaceMap)); Class<Object> clazz = null;
		 * JavaType type =
		 * mapper.getTypeFactory().constructParametricType(Message.class,
		 * clazz); message =
		 * mapper.readValue(mapper.writeValueAsString(message), type);
		 */
		Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("data1", "domain");
		renameMap.put("data2", "domain2");

		DomainResponse domainResponse = new DomainResponse("1234", "Academy School District", "ed-academy", "4321",
				EMPTY, EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null);
		List<DomainResponse> domainResponseList = new ArrayList<>();
		domainResponseList.add(domainResponse);

		Message message = messageUtil.createMessageWithPayload(MessageConstants.DOMAIN_FOUND, domainResponseList,
				renameMap, DomainResponse.class);

		Assert.assertNotNull(message);
		Assert.assertEquals("Domain found", message.getMessage());
	}

	/**
	 * Test method for
	 * {@link com.sinet.gage.provision.model.MessageUtil#createMessageWithPayload(java.lang.String, java.lang.Object, java.util.Map, java.lang.Class)}
	 * .
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMessageWithPayloadStringTMapOfStringStringClassOfT() {

		DomainResponse domainResponse = new DomainResponse("1234", "Academy School District", "ed-academy", "4321",
				EMPTY, EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null);

		Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("data1", "domain");
		renameMap.put("data2", "domain2");

		Message message = messageUtil.createMessageWithPayload(MessageConstants.DOMAIN_FOUND, domainResponse, renameMap,
				DomainResponse.class);

		Assert.assertNotNull(message);
		Assert.assertEquals("Domain found", message.getMessage());

	}

	/**
	 * Test method for
	 * {@link com.sinet.gage.provision.model.MessageUtil#createMessageWithPayload(java.lang.String, java.lang.Object, java.lang.Object, java.lang.String, java.util.Map, java.lang.Class)}
	 * .
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMessageWithPayloadStringTUStringMapOfStringStringClassOfT() {
		LoginResponse response = new LoginResponse(USER_ID, EMPTY, EMPTY, EMPTY, EMPTY, DOMAIN_ID, EMPTY, EMPTY, EMPTY,
				EMPTY);
		DomainRightsResponse domainResponse = new DomainRightsResponse(DOMAIN_ID, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, 0);

		Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("data1", "user");
		renameMap.put("data2", "domain");
		renameMap.put("data4", "token");

		Message message = messageUtil.createMessageWithPayload(MessageConstants.LOGIN_AUTHENTICATED, response,
				domainResponse, TOKEN, renameMap, LoginResponse.class);
		Assert.assertNotNull(message);
		Assert.assertEquals("User is authenticated", message.getMessage());
		Assert.assertEquals("SUCCESS", message.getMessageType().toString());
	}

	/**
	 * Test method for
	 * {@link com.sinet.gage.provision.model.MessageUtil#createMessageWithPayload(java.lang.String, java.lang.Object, java.lang.Object, java.util.Map, java.lang.Class)}
	 * .
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMessageWithPayloadStringTUMapOfStringStringClassOfT() {
		LoginResponse response = new LoginResponse(USER_ID, EMPTY, EMPTY, EMPTY, EMPTY, DOMAIN_ID, EMPTY, EMPTY, EMPTY,
				EMPTY);
		DomainRightsResponse domainResponse = new DomainRightsResponse(DOMAIN_ID, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, 0);

		Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("data1", "user");
		renameMap.put("data2", "domain");

		Message message = messageUtil.createMessageWithPayload(MessageConstants.LOGIN_AUTHENTICATED, response,
				domainResponse, renameMap, LoginResponse.class);
		Assert.assertNotNull(message);
		Assert.assertEquals("User is authenticated", message.getMessage());
		Assert.assertEquals("SUCCESS", message.getMessageType().toString());
	}

	/**
	 * Test method for
	 * {@link com.sinet.gage.provision.model.MessageUtil#createMessageWithPayload(java.lang.String, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.util.Map, java.lang.Class)}
	 * .
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCreateMessageWithPayloadStringTUVWMapOfStringStringClassOfT() {
		DomainResponse domainResponse = new DomainResponse("1234", "Academy School District", "ed-academy", "4321", EMPTY, EMPTY, 0, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null);
		EdivateLearn edivateLearn = new EdivateLearn("district", "Per Seat", "100", "Fixed", true, "11-14-2015", "02-15-2015", true, "11-14-2015", "11-15-2035");
		
		List<SubscriptionResponse> listCourseResponse = new ArrayList<>();
		SubscriptionResponse courseResponse = new SubscriptionResponse("345456", "76374643", "C", "11-16-2015", "11-16-2016", "1", "-1", "11-16-2015", "11-16-2015", "", "Subscription Name", "Title");
		listCourseResponse.add(courseResponse);
		
		List<SubscriptionResponse> listProviderResponse = new ArrayList<>();
		SubscriptionResponse providerResponse = new SubscriptionResponse("345456", "76374643", "P", "11-16-2015", "11-16-2016", "1", "-1", "11-16-2015", "11-16-2015", "", "Subscription Name", "Title");
		listProviderResponse.add(providerResponse);
		
		Map<String, String> renameMap = new HashMap<String, String>();
		renameMap.put("data1", "domain");
		renameMap.put("data2", "customization");
		renameMap.put("data3", "subscribedproviderlist");
		renameMap.put("data4", "subscribedcourselist");
		
		Message message = messageUtil.createMessageWithPayload(MessageConstants.LOGIN_AUTHENTICATED, domainResponse, edivateLearn, listProviderResponse, listCourseResponse, renameMap, DomainResponse.class);
		Assert.assertNotNull(message);
		Assert.assertEquals("User is authenticated", message.getMessage());
	}
	
	/**
	 * Test method for
	 * {@link com.sinet.gage.provision.model.MessageUtil#createMessage(String, boolean, Object...)}
	 * 
	 * Test case : Test case for success status
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void createMessageForSuccessStatus(){
		Message message =messageUtil.createMessage(MessageConstants.LOGIN_AUTHENTICATED, SUCCESS_STATUS);
		Assert.assertNotNull(message);
		Assert.assertEquals("User is authenticated", message.getMessage());
		Assert.assertEquals("SUCCESS" , message.getMessageType().toString());
	}
	
	/**
	 * Test method for
	 * {@link com.sinet.gage.provision.model.MessageUtil#createMessage(String, boolean, Object...)}
	 * 
	 * Test case : Test case for Error status 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void createMessageForErrorStatus(){
		Message message =messageUtil.createMessage(MessageConstants.LOGIN_AUTHENTICATED, ERROR_STATUS);
		Assert.assertNotNull(message);
		Assert.assertEquals("User is authenticated", message.getMessage());
		Assert.assertEquals("ERROR" , message.getMessageType().toString());
	}

}
