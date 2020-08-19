import com.revature.daos.AccountDAO;
import com.revature.daos.UserDAO;
import com.revature.models.*;
import com.revature.service.ServiceTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.*;

public class MenuTesters {
	private static ServiceTime testServs;
	private static Scanner sin = new Scanner(System.in);
	private static User u;
	private static Account a;
	private static Account save;
	private static UserDAO userDAO = UserDAO.getUserDAO();
	private static AccountDAO acctDAO = AccountDAO.getAcctDAO();
	
	@BeforeClass
	public static void setServLay() {
		testServs = new ServiceTime(acctDAO,userDAO);
		
	}
	
	@BeforeClass
	public static void setValues() {
		u = userDAO.findUserByName("Groot");
		a = acctDAO.getAccountByID(1);
		save = acctDAO.getAccountByID(1);
	}
	
	@Before
	public void setBal() {
		a.setBalance(200);
		u.setActive(a);
	}
	
	@Test
	public void testaBW() {
		int bW = a.getBalance();
		testServs.alterBalance('W', 100, u);
		int aW = a.getBalance();
		assertTrue(bW == aW+100);
		
	}
	
	@Test
	public void testaBD() {
		int bD = a.getBalance();
		testServs.alterBalance('D',100,u);
		int aD = a.getBalance();
		assertTrue(bD == aD-100);
	}
	
	@Test
	public void testaBC() {
		assertFalse(testServs.alterBalance('C',100,u));
		assertFalse(testServs.alterBalance('W', 1000, u));
		assertFalse(testServs.alterBalance('W', -6, u));
		assertFalse(testServs.alterBalance('D', -6, u));
	}
	

	@Test
	public void testSwitch() {
		Account bS = u.getActive();
		testServs.switchAccount(sin,u);
		Account aS = u.getActive();
		assertTrue(!bS.equals(aS));
	}
	
	@AfterClass
	public static void resetAccts() {
		acctDAO.updateAccount(save);
	}

}
