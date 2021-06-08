package services;

import java.util.ArrayList;
import java.util.Scanner;

import models.User;

public interface UserService {
	
	 
	boolean addUser(User u);
	
	User getUserById(int id);
	
	ArrayList<User> getAll();
	
	void update(User u);
	
	void delete(User u);
	
	User login(Scanner scan);
	 
	void register(Scanner scan);
}
