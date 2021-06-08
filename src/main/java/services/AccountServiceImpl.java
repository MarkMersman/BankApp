package services;

import java.util.ArrayList;

import models.Account;
import models.User;
import repos.AccountRepository;

public class AccountServiceImpl implements AccountService{

	private AccountRepository ar = new AccountRepository();
	
	@Override
	public boolean add(Account t) {
		
		return ar.add(t);
		
	}

	@Override
	public Account getById(Integer id) {
		
		return ar.getById(id);
	}

	@Override
	public ArrayList<Account> getPending() {

		return ar.getPending();
	}

	@Override
	public ArrayList<Account> getActive() {
		
		return ar.getActive();
	}

	@Override
	public Account getByName(String name) {
		
		return ar.getByName(name);
	}

	@Override
	public ArrayList<Account> getByUser(User u) {
		
		return ar.getByUser(u);
	}

	@Override
	public ArrayList<Account> getAll() {

		return ar.getAll();
	}

	@Override
	public void update(Account t) {
		
		ar.update(t);
		
	}

	@Override
	public void delete(Account t) {

		ar.delete(t);
		
	}

}
