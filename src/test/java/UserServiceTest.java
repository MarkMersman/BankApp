import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

import services.UserService;
import services.UserServiceImpl;

public class UserServiceTest {
	
	UserService us = new UserServiceImpl();
	Scanner sc = new Scanner(System.in);
	
	@Ignore
	@Test
	public void loginTest() {
		us.login(sc);
	}
	
	@Test
	public void registerTest() {
		us.register(sc);
	}

}
