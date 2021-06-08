package services;

import java.util.ArrayList;
import java.util.Scanner;

import models.User;
import repos.UserRepository;
import utils.AppLogger;

public class UserServiceImpl implements UserService{
	
	private  UserRepository ur = new UserRepository();
	
	@Override
	public boolean addUser(User u) {
		return ur.add(u);
	}

	@Override
	public User getUserById(int id) {
		
		return ur.getById(id);
	}

	@Override
	public ArrayList<User> getAll() {
		
		return ur.getAll();
	}

	@Override
	public void update(User u) {
		
		ur.update(u);
	}

	@Override
	public void delete(User u) {
		ur.delete(u);
	}

	@Override
	public User login(Scanner scan) {
		User loggedUser = new User();
		System.out.println("Username: ");
		String un = scan.next();
		System.out.println("Password: ");
		String pw = scan.next();
		loggedUser = ur.getByCredentials(un,pw);
		if(loggedUser == null) {
			System.out.println("Login Failed. \n");
			AppLogger.logger.debug("User failed to login with these credentials: username: "+ un + " password: " +pw);
			return null;
			
		}
		else {
			System.out.println("Welcome " + loggedUser.getFirstName() + ". \n");
			AppLogger.logger.info(loggedUser.getUsername()+" Logged In.");
			return loggedUser;
			
		}
		
	}

	@Override
	public void register(Scanner scan) {
		
		System.out.println("Enter a username: ");
		String un = scan.next();
		System.out.println("Enter a password: ");
		String pw = scan.next();
		System.out.println("Enter your first name: ");
		String fn = scan.next();
		System.out.println("Enter you last name: ");
		String ln = scan.next();
		
		User newUser = new User(un,pw,fn,ln);
		ur.add(newUser);
		User test = ur.getByCredentials(un, pw);
		if (test != null) {
			System.out.println("User Registered! Please login.");
			AppLogger.logger.info(un + "registered as a new user.");
		}
		else {
			System.out.println("User not save, please try again.");
			AppLogger.logger.debug("User registration failed. input was: UN: "+ un + " pw: " + pw + " fn: " + fn + " ln: " +ln);
		}
		
		
	}

}
