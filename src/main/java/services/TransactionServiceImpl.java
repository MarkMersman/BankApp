package services;

import java.util.ArrayList;
import java.util.Scanner;

import models.Account;
import models.Transaction;
import repos.AccountRepository;
import repos.TransactionRepository;
import utils.AppLogger;

public class TransactionServiceImpl implements TransactionService{

	private TransactionRepository tr = new TransactionRepository();
	private AccountRepository ar = new AccountRepository();
	
	@Override
	public boolean add(Transaction t) {
		return tr.add(t);
	}

	@Override
	public Transaction getById(Integer id) {
		
		return tr.getById(id);
	}

	@Override
	public ArrayList<Transaction> getAll() {
		
		return tr.getAll();
	}

	@Override
	public void update(Transaction t) {
		
		tr.update(t);
		
	}

	@Override
	public ArrayList<Transaction> getPending(Account t) {
		
		return tr.getPending(t);
	}
	
		
	public void accept(Transaction t) {
		Account fromAcct = t.getAccount();
		Account toAcct = t.getToAccount();
		fromAcct.setAcctBalance(fromAcct.getAcctBalance()-t.getAmount());
		toAcct.setAcctBalance(toAcct.getAcctBalance()+t.getAmount());
		ar.update(fromAcct);
		ar.update(toAcct);
		t.setStatus("accepted");
		tr.update(t);
		System.out.println("Transfer accepted! The accounts have been updated.");
		AppLogger.logger.info("Transfer accepted from" + t.getAccount().getAcctName() + " to " + t.getToAccount().getAcctName() );
	}
	public void reject(Transaction t) {
		t.setStatus("rejected");
		tr.update(t);
		System.out.println("Transfer rejected! There will be no change to the accounts.");
		AppLogger.logger.info("Transfer rejected from" + t.getAccount().getAcctName() + " to " + t.getToAccount().getAcctName() );
	}

}
