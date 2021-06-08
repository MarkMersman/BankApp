import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import models.Account;
import models.Transaction;
import models.User;
import repos.AccountRepository;
import repos.TransactionRepository;
import repos.UserRepository;

public class TransactionRepoTests {

	AccountRepository ar = new AccountRepository();
	UserRepository ur = new UserRepository();
	TransactionRepository tr = new TransactionRepository();
	
	@Ignore
	@Test
	public void addTransactionTest() {
		
		//User u = ur.getById(4);
		//Account a = ar.getById(5);
		
		Transaction addTrans = new Transaction(0,"transfer",250.00,6,3,"pending",1);
		
		tr.add(addTrans);
		

	}
	
	@Ignore
	@Test
	public void getAllTest() {
		ArrayList<Transaction> list = tr.getAll();
		
		for(Transaction t : list) {
			System.out.println(t.toString());
		}
	}
	
	@Ignore
	@Test
	public void getByIdTest() {
		Transaction t = tr.getById(2);
		
		System.out.println(t.toString());
	}
	
	@Ignore
	@Test
	public void updateTest() {
		Transaction t = tr.getById(7);
		t.setStatus("accepted");
		tr.update(t);
	}
	
	@Ignore
	@Test
	public void getPendingTest() {
		Account t = ar.getById(1);
		
		ArrayList<Transaction> tl = tr.getPending(t);
		
		for (Transaction a : tl) {
			System.out.println(a.toString());
		}
	}

}
