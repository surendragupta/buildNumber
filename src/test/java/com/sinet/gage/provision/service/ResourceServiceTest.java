package com.sinet.gage.provision.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.sinet.gage.dlap.clients.DlapResourceClient;
import com.sinet.gage.provision.service.impl.ResourceServiceImpl;

/**
 * Test class for {@link com.sinet.gage.provision.service.impl.ResourceService}
 * 
 * @author Team Gage
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {
	
	private static final String TOKEN = "~SYC-ihGJ|ML0jX11juQd8d8BtTPAuWC1";

	private static final String SOURCE_DOMAIN_ID = "1234";

	private static final String DESTINATION_DOMAIN_ID = "12345";

	@InjectMocks
	private ResourceService mockResourceService = new ResourceServiceImpl();
	
	@Mock
	private DlapResourceClient mockResourceClient;

	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.ResourceServiceImpl#copyResources(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testCopyResources() {
		when( mockResourceClient.copyResource(anyString(), anyString(), anyString())).thenReturn(Boolean.TRUE);
		assertTrue(mockResourceService.copyResources(TOKEN, SOURCE_DOMAIN_ID, DESTINATION_DOMAIN_ID));
		verify( mockResourceClient, times(1)).copyResource(anyString(), anyString(), anyString());
	}
	
	/**
	 * Test method for {@link com.sinet.gage.provision.service.impl.ResourceServiceImpl#copyResources(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testCopyResourcesFailed () {
		when( mockResourceClient.copyResource(anyString(), anyString(), anyString())).thenReturn(Boolean.FALSE);
		assertFalse(mockResourceService.copyResources(TOKEN, SOURCE_DOMAIN_ID, DESTINATION_DOMAIN_ID));
		verify( mockResourceClient, times(1)).copyResource(anyString(), anyString(), anyString());
	}

}
