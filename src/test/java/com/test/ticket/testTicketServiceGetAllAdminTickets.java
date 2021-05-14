package com.test.ticket;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.berry.model.Reimbursement;
import com.berry.model.Type;
import com.berry.model.TypeEnum;
import com.berry.model.Users;
import com.berry.service.TicketService;
import com.berry.util.SessionUtility;

public class testTicketServiceGetAllAdminTickets {
	private static Session mockSession;
	private static TicketRepo mockTicketRepo;
	
	private TicketService ticketService = new TicketService(mockTicketRepo);
	
	//one pixel base 64 image of one pixel. just for brevity
	private static final String blobString = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAADElEQVQImWP4//8/AAX+Av5Y8msOAAAAAElFTkSuQmCC";
	
	@Before
	public void beforeTest(){
		ticketService = new TicketService(mockTicketRepo);
	}
	
	@BeforeClass
	public static void setUp() throws Exception {
		mockTicketRepo = mock(TicketRepo.class);
		mockSession = mock(Session.class);
		
		List<Reimbursement> ticketList = new ArrayList<Reimbursement>();
		
		Reimbursement ticket = new Reimbursement();
		ticket.setAmount(500.30);
		ticket.setDescription("Description");
		ticket.setType_id(new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString()));
		ticket.setReceipt(Base64Conversion.base64toByteArray(blobString));
		
		ticketList.add(ticket);
		
		when(mockTicketRepo.getAllAdminTickets()).thenReturn(ticketList);
	}
	
	@Test
	public void testSuccessfulUserFetchAllTickets() throws BadParameterException, ServiceLayerException, ImproperTypeException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			List<Reimbursement> actual = new ArrayList<Reimbursement>();
			
			Reimbursement ticket = new Reimbursement();
			ticket.setAmount(500.30);
			ticket.setDescription("Description");
			ticket.setType_id(new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString()));
			ticket.setReceipt(Base64Conversion.base64toByteArray(blobString));
			
			actual.add(ticket);
			
			List<Reimbursement> expected = ticketService.getAllAdminTickets();
			
			assertEquals(actual, expected);
		}
	}

}
