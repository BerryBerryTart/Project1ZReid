import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.berry.DTO.CreateUserDTO;
import com.berry.dao.LoginRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.CreationException;
import com.berry.exception.DatabaseExeption;
import com.berry.model.Role;
import com.berry.model.Users;
import com.berry.service.LoginService;
import com.berry.util.SessionUtility;

public class testLoginServiceRegister {
	private static LoginRepo mockLoginRepo;
	private static Session mockSession;

	private LoginService loginService;

	@BeforeClass
	public static void setUp() throws CreationException, DatabaseExeption {
		mockLoginRepo = mock(LoginRepo.class);
		mockSession = mock(Session.class);

		CreateUserDTO dto1 = new CreateUserDTO("billy", "bill", "billy1", "billpass", "bill@bill.com");
		Users user1 = new Users("billy1", "billpass", "billy", "bill", "bill@bill.com");
		user1.setRole_id(new Role(1, "EMPLOYEE"));
		when(mockLoginRepo.createUser(eq(dto1))).thenReturn(user1);

		CreateUserDTO dto2 = new CreateUserDTO("willy", "will", "willy1", "willpass", "will@will.com");
		when(mockLoginRepo.createUser(eq(dto2))).thenThrow(new CreationException());
	}

	@Before
	public void beforeTest() {
		loginService = new LoginService(mockLoginRepo);
	}

	@Test
	public void testSuccessfulRegister() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "bill", "billy1", "billpass", "bill@bill.com");
			Users expected = new Users("billy1", "billpass", "billy", "bill", "bill@bill.com");
			expected.setRole_id(new Role(1, "EMPLOYEE"));

			Users actual = loginService.registerUser(dto);

			assertEquals(expected, actual);
		}
	}

	@Test(expected = CreationException.class)
	public void testRegisterUserAlreadyExists() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("willy", "will", "willy1", "willpass", "will@will.com");
			loginService.registerUser(dto);
		}
	}

	@Test(expected = BadParameterException.class)
	public void testRegisterNoUsername() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "bill", "", "billpass", "bill@bill.com");
			loginService.registerUser(dto);
		}
	}

	@Test(expected = BadParameterException.class)
	public void testRegisterNoPassword() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "bill", "billy1", "", "bill@bill.com");
			loginService.registerUser(dto);
		}
	}

	@Test(expected = BadParameterException.class)
	public void testRegisterNoFirstname() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("", "bill", "billy1", "billpass", "bill@bill.com");
			loginService.registerUser(dto);
		}
	}

	@Test(expected = BadParameterException.class)
	public void testRegisterNoLastname() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "", "billy1", "billpass", "bill@bill.com");
			loginService.registerUser(dto);
		}
	}

	@Test(expected = BadParameterException.class)
	public void testRegisterNoEmail() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "bill", "billy1", "billpass", "");
			loginService.registerUser(dto);
		}
	}
	
	//No Username With Absent Params Permutations
	
	@Test(expected = BadParameterException.class)
	public void testRegisterNoUsernameAndPassword() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "bill", "", "", "bill@bill.com");
			loginService.registerUser(dto);
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testRegisterNoUsernameAndEmail() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "bill", "", "billpass", "");
			loginService.registerUser(dto);
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testRegisterNoUsernameAndFirstName() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("", "bill", "", "billpass", "bill@bill.com");
			loginService.registerUser(dto);
		}
	}
	
	@Test(expected = BadParameterException.class)
	public void testRegisterNoUsernameAndLastName() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("billy", "", "", "billpass", "bill@bill.com");
			loginService.registerUser(dto);
		}
	}
	
	//completely empty
	
	@Test(expected = BadParameterException.class)
	public void testRegisterAllParamsEmpty() throws BadParameterException, CreationException, DatabaseExeption {
		try (MockedStatic<SessionUtility> mockedSessionUtil = mockStatic(SessionUtility.class)) {
			mockedSessionUtil.when(SessionUtility::getSession).thenReturn(mockSession);

			CreateUserDTO dto = new CreateUserDTO("", "", "", "", "");
			loginService.registerUser(dto);
		}
	}

}
