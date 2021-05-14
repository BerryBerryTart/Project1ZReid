package com.test.ticket;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.Base64;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.berry.DTO.CreateTicketDTO;
import com.berry.dao.TicketRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.ImproperTypeException;
import com.berry.exception.ServiceLayerException;
import com.berry.model.Reimbursement;
import com.berry.model.Type;
import com.berry.model.TypeEnum;
import com.berry.model.Users;
import com.berry.service.TicketService;
import com.berry.util.SessionUtility;

public class testTicketServiceCreateTicket {
	private static Session mockSession;
	private static TicketRepo mockTicketRepo;
	
	private TicketService ticketService = new TicketService(mockTicketRepo);
	
	//one pixel base 64 image of one pixel. just for brevity
	private static final String blobString = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAIAAACQd1PeAAAADElEQVQImWP4//8/AAX+Av5Y8msOAAAAAElFTkSuQmCC";
	private static final Users user1 = new Users();
	
	@BeforeClass
	public static void setUp() throws Exception {
		mockTicketRepo = mock(TicketRepo.class);
		mockSession = mock(Session.class);
		
		//Known good ticket
		CreateTicketDTO dto1 = new CreateTicketDTO();
		dto1.setAmount(500.30);
		dto1.setDescription("Description");
		dto1.setType(TypeEnum.BUISNESS.toString());
		dto1.setReceipt(Base64Conversion.base64toByteArray(blobString));
		
		Reimbursement ticket1 = new Reimbursement();
		ticket1.setAmount(500.30);
		ticket1.setDescription("Description");
		ticket1.setType_id(new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString()));
		ticket1.setReceipt(Base64Conversion.base64toByteArray(blobString));
		
		when(mockTicketRepo.createTicket( eq(user1) ,  eq(dto1) )).thenReturn(ticket1);
		
		//Ticket with bad blob
		CreateTicketDTO dto2 = new CreateTicketDTO();
		dto2.setAmount(500.30);
		dto2.setDescription("Description");
		dto2.setType(TypeEnum.BUISNESS.toString());
		dto2.setReceipt(Base64Conversion.base64toByteArray("ZWNobyBvZmYgfCBjbGlw"));
		
		Reimbursement ticket2 = new Reimbursement();
		ticket2.setAmount(500.30);
		ticket2.setDescription("Description");
		ticket2.setType_id(new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString()));
		ticket2.setReceipt(Base64Conversion.base64toByteArray("ZWNobyBvZmYgfCBjbGlw"));
		
		when(mockTicketRepo.createTicket( eq(user1) ,  eq(dto2) )).thenReturn(ticket2);
		
	}

	@Before
	public void beforeTest(){
		ticketService = new TicketService(mockTicketRepo);
	}

	@Test
	public void testSuccessfulTicketCreation() throws BadParameterException, ServiceLayerException, ImproperTypeException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			CreateTicketDTO dto = new CreateTicketDTO();
			dto.setAmount(500.30);
			dto.setDescription("Description");
			dto.setType(TypeEnum.BUISNESS.toString());
			dto.setReceipt(Base64Conversion.base64toByteArray(blobString));
			
			Reimbursement expected = new Reimbursement();
			expected.setAmount(500.30);
			expected.setDescription("Description");
			expected.setType_id(new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString()));
			expected.setReceipt(Base64Conversion.base64toByteArray(blobString));
			
			Reimbursement actual = ticketService.CreateTicket(user1, dto);
			
			assertEquals(actual, expected);
		}
	}
	
	@Test(expected = ImproperTypeException.class)
	public void testTicketCreationBadBlob() throws BadParameterException, ServiceLayerException, ImproperTypeException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			CreateTicketDTO dto = new CreateTicketDTO();
			dto.setAmount(500.30);
			dto.setDescription("Description");
			dto.setType(TypeEnum.BUISNESS.toString());
			dto.setReceipt(Base64Conversion.base64toByteArray("ZWNobyBvZmYgfCBjbGlw"));
			
			ticketService.CreateTicket(user1, dto);;
		}
	}
	
	//Empty Field Tests
	@Test(expected = BadParameterException.class)
	public void testTicketCreationNoAmount() throws BadParameterException, ServiceLayerException, ImproperTypeException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			CreateTicketDTO dto = new CreateTicketDTO();
			dto.setDescription("Description");
			dto.setType(TypeEnum.BUISNESS.toString());
			dto.setReceipt(Base64Conversion.base64toByteArray(blobString));
			ticketService.CreateTicket(user1, dto);
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testTicketCreationNoDescription() throws BadParameterException, ServiceLayerException, ImproperTypeException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			CreateTicketDTO dto = new CreateTicketDTO();
			dto.setAmount(500.30);
			dto.setType(TypeEnum.BUISNESS.toString());
			dto.setReceipt(Base64Conversion.base64toByteArray(blobString));
			
			ticketService.CreateTicket(user1, dto);			
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testTicketCreationNoType() throws BadParameterException, ServiceLayerException, ImproperTypeException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			CreateTicketDTO dto = new CreateTicketDTO();
			dto.setAmount(500.30);
			dto.setDescription("Description");
			dto.setReceipt(Base64Conversion.base64toByteArray(blobString));
			
			ticketService.CreateTicket(user1, dto);			
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testTicketCreationNoReceipt() throws BadParameterException, ServiceLayerException, ImproperTypeException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			CreateTicketDTO dto = new CreateTicketDTO();
			dto.setAmount(500.30);
			dto.setDescription("Description");
			dto.setType(TypeEnum.BUISNESS.toString());
			
			ticketService.CreateTicket(user1, dto);			
		}
	}
	
	//Test Bad status type
	
	@Test(expected = BadParameterException.class)
	public void testTicketCreationIllegalTypeParam() throws BadParameterException, ServiceLayerException, ImproperTypeException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			CreateTicketDTO dto = new CreateTicketDTO();
			dto.setAmount(500.30);
			dto.setDescription("Description");
			dto.setType("wrong");
			dto.setReceipt(Base64Conversion.base64toByteArray(blobString));
			
			ticketService.CreateTicket(user1, dto);			
		}
	}
}
