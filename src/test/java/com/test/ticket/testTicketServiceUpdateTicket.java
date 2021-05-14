package com.test.ticket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.berry.DTO.TicketStatusDTO;
import com.berry.dao.TicketRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.ImproperTypeException;
import com.berry.exception.NotFoundException;
import com.berry.exception.ServiceLayerException;
import com.berry.model.Reimbursement;
import com.berry.model.Status;
import com.berry.model.StatusEnum;
import com.berry.model.Type;
import com.berry.model.TypeEnum;
import com.berry.model.Users;
import com.berry.service.TicketService;
import com.berry.util.SessionUtility;

public class testTicketServiceUpdateTicket {
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
		
		TicketStatusDTO dto1 = new TicketStatusDTO();
		dto1.setStatus("completed");
		
		Reimbursement ticket1 = new Reimbursement();
		ticket1.setAmount(500.30);
		ticket1.setDescription("Description");
		ticket1.setType_id(new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString()));
		ticket1.setReceipt(Base64Conversion.base64toByteArray(blobString));
		ticket1.setStatus_id(new Status(StatusEnum.COMPLETED.getIndex(), StatusEnum.COMPLETED.toString()));
		
		when(mockTicketRepo.updateAdminTicketById( eq(1), eq(user1), eq(dto1) )).thenReturn(ticket1);
		
		//not found ticket
		when(mockTicketRepo.updateAdminTicketById( eq(2), eq(user1), eq(dto1) )).thenThrow(NotFoundException.class);
	}
	
	@Test
	public void testSuccessfulTicketCreation() throws BadParameterException, ServiceLayerException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			TicketStatusDTO dto = new TicketStatusDTO();
			dto.setStatus("completed");
			
			Reimbursement expected = new Reimbursement();
			expected.setAmount(500.30);
			expected.setDescription("Description");
			expected.setType_id(new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString()));
			expected.setReceipt(Base64Conversion.base64toByteArray(blobString));
			expected.setStatus_id(new Status(StatusEnum.COMPLETED.getIndex(), StatusEnum.COMPLETED.toString()));
			
			Reimbursement actual = ticketService.updateAdminTicketById("1", user1, dto);
			
			assertEquals(actual, expected);
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testTicketCreationInvalidDTOParam() throws BadParameterException, ServiceLayerException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			TicketStatusDTO dto = new TicketStatusDTO();
			dto.setStatus("invalid");
						
			ticketService.updateAdminTicketById("1", user1, dto);
			
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testTicketCreationEmptyDTOParam() throws BadParameterException, ServiceLayerException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			TicketStatusDTO dto = new TicketStatusDTO();
			dto.setStatus("");
			
			ticketService.updateAdminTicketById("1", user1, dto);
			
		}
	}
	
	@Test(expected = NotFoundException.class)
	public void testTicketCreationNotFound() throws BadParameterException, ServiceLayerException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			TicketStatusDTO dto = new TicketStatusDTO();
			dto.setStatus("completed");				
			
			ticketService.updateAdminTicketById("2", user1, dto);
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testTicketCreationInvalidTicketIndex() throws BadParameterException, ServiceLayerException, NotFoundException {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			TicketStatusDTO dto = new TicketStatusDTO();
			dto.setStatus("invalid");				
			
			ticketService.updateAdminTicketById("two", user1, dto);
		}
	}
	
}
