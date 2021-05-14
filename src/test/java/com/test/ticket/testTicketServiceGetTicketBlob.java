package com.test.ticket;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.berry.dao.TicketRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.ImproperTypeException;
import com.berry.exception.NotFoundException;
import com.berry.exception.ServiceLayerException;
import com.berry.model.Users;
import com.berry.service.TicketService;
import com.berry.util.SessionUtility;

public class testTicketServiceGetTicketBlob {
	private static Session mockSession;
	private static TicketRepo mockTicketRepo;
	
	private TicketService ticketService = new TicketService(mockTicketRepo);
	
	//one pixel base 64 image of one pixel. just for brevity
	private static final String blobString = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAADElEQVQImWP4//8/AAX+Av5Y8msOAAAAAElFTkSuQmCC";
	private static final Users user1 = new Users();
	
	@Before
	public void beforeTest(){
		ticketService = new TicketService(mockTicketRepo);
	}
	
	@BeforeClass
	public static void setUp() throws Exception {
		mockTicketRepo = mock(TicketRepo.class);
		mockSession = mock(Session.class);
		
		byte[] ticketBlob = Base64Conversion.base64toByteArray(blobString);
		
		when(mockTicketRepo.fetchTicketBlob( eq(user1), eq(1) )).thenReturn(ticketBlob);
	}
	
	@Test
	public void testSuccessfulUserFetchAllTickets() throws BadParameterException, ServiceLayerException, ImproperTypeException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			byte[] actual = Base64Conversion.base64toByteArray(blobString);
						
			byte[] expected = ticketService.fetchTicketBlob(user1, "1");
			
			assertArrayEquals(actual, expected);
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testFetchAllTicketsInvalidParam() throws BadParameterException, ServiceLayerException, ImproperTypeException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			ticketService.fetchTicketBlob(user1, "one");
			
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testFetchAllTicketsEmptyParam() throws BadParameterException, ServiceLayerException, ImproperTypeException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			ticketService.fetchTicketBlob(user1, "");
			
		}
	}

}
