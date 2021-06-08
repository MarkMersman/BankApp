import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


import models.User;
import repos.UserRepository;

public class UserRepoTests {

	UserRepository ur = new UserRepository();
	
	@Ignore
	@Test
	public void allUsersTest() {
		
		ArrayList<User> users = ur.getAll();
		
		for (User u: users) {
			System.out.println(u.toString());
		}
		
		System.out.println();
		
	}
	@Ignore
	@Test
	public void getByIdTest() {
		User aUser = ur.getById(9);
		System.out.println(aUser.toString());
		System.out.println();
	}
	
	@Test
	public void getByNameTest() {
		User control = new User(1,"markmersman","password",false,"Mark","Mersman");
		User aUser = ur.getByName("markmersman");
		Assert.assertEquals(control, aUser);
		//System.out.println(aUser.toString());
		//System.out.println();
	}
	
	@Ignore
	@Test
	public void updateTest() {
		User uUser = ur.getById(3);
		uUser.setFirstName("Harry");
		uUser.setPassword("expelliarmus");
		ur.update(uUser);
	}
	
	@Ignore
	@Test
	public void deleteTest() {
		User dUser = ur.getById(8);
		ur.delete(dUser);
	}
	
	@Ignore
	@Test
	public void addTest() {
		
		User bUser = new User(1,"elyp", "password", true, "Ely", "Phillips");
		Assert.assertEquals(true, ur.add(bUser));
		
	}
	
	
}
