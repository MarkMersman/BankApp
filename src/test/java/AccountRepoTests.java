import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import repos.AccountRepository;
import repos.UserRepository;
import models.Account;
import models.User;

public class AccountRepoTests {

	AccountRepository ar = new AccountRepository();
	UserRepository ur = new UserRepository();
	
	@Ignore
	@Test
	public void addAccountTest() {
		User addUser = ur.getById(5);
		Account addAccount = new Account("Raistlin Account", 320.00,"active",addUser);
		ar.add(addAccount);
		
	}
	
	@Ignore
	@Test
	public void getByIdTest() {
		Account getAccount = ar.getById(5);
		System.out.println(getAccount.toString());
	}
	
	@Ignore
	@Test
	public void allAccountsTest() {
		User u = ur.getById(11);
		ArrayList<Account> accounts = ar.getByUser(u);
		
		for (Account a: accounts) {
			System.out.println(a.toString());
		}
		
		System.out.println();
		
	}

	@Ignore
	@Test
	public void updateTest() {
		Account ua = ar.getById(8);
		ua.setAcctStatus("active");;
		ar.update(ua);
	}
	
	@Ignore
	@Test
	public void deleteTest() {
		Account ua = ar.getById(9);
		//ua.setAcctName("Ely 3rd Account");
		ar.delete(ua);
	}
	
	
}
