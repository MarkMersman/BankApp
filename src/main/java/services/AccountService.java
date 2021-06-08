package services;

import java.util.ArrayList;

import models.Account;
import models.User;

public interface AccountService {

	boolean add(Account t);
	
	Account getById(Integer id);
	
	ArrayList<Account> getPending();
	
	ArrayList<Account> getActive();
	
	Account getByName(String name);
	
	ArrayList<Account> getByUser(User u);
	
	ArrayList<Account> getAll();
	
	void update(Account t);
	
	void delete(Account t);
	
}
