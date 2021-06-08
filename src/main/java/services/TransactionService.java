package services;

import java.util.ArrayList;

import models.Account;
import models.Transaction;

public interface TransactionService {

	boolean add(Transaction t);
	
	Transaction getById(Integer id);
	
	ArrayList<Transaction> getAll();
	
	void update(Transaction t);
	
	ArrayList<Transaction> getPending(Account t);
}
