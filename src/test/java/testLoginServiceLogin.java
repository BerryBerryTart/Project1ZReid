import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.mockito.MockedStatic;

import org.hibernate.Session;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.berry.DTO.LoginDTO;
import com.berry.dao.LoginRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.DatabaseExeption;
import com.berry.exception.NotFoundException;
import com.berry.model.Users;
import com.berry.service.LoginService;
import com.berry.util.SessionUtility;

public class testLoginServiceLogin {
	private static LoginRepo mockLoginRepo;
	private static Session mockSession;

	private LoginService loginService;	

	@BeforeClass
	public static void setUp() throws NotFoundException, DatabaseExeption {
		mockLoginRepo = mock(LoginRepo.class);
		mockSession = mock(Session.class);

		when(mockLoginRepo.loginUser( eq(new LoginDTO("bill1", "bill1")) ))
				.thenReturn(new Users("bill1", "bill1", "billy", "bill", "bill@bill.com"));
	}
	
	@Before
	public void beforeTest() {
		loginService = new LoginService(mockLoginRepo);
	}

	@Test
	public void testSuccessfulLogin() throws BadParameterException, NotFoundException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);
			
			LoginDTO dto = new LoginDTO("bill1", "bill1");			
			Users actual = loginService.loginUser(dto);
			Users expected = new Users("bill1", "bill1", "billy", "bill", "bill@bill.com");

			assertEquals(expected, actual);
		}
	}
}
