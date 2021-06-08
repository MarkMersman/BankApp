package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Account;
import models.User;
import utils.JDBCConnection;

public class AccountRepository implements DataRepository<Account>{

	private String sqlcommand;
	private PreparedStatement ps;
	private ResultSet rs;
	public static  Connection conn = JDBCConnection.getConnection();
	
	//using stored procedure in this method
	@Override
	public boolean add(Account t) {
		
		//stored procedure
		sqlcommand = "call add_account(?, ?, ?, ?);";
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, t.getAcctName());
			ps.setDouble(2, t.getAcctBalance());
			ps.setString(3, t.getAcctStatus());
			ps.setInt(4, t.getAcctOwner().getUserId());
			
			
			boolean success = ps.execute();
			
			if (!success) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			System.out.println("add user issue");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Account getById(Integer id) {
		
		Account acct = null;
		sqlcommand = "select * from accounttable where accountid = ?;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setInt(1, id);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				acct = new Account(rs.getInt("accountid"),rs.getString("account_name"),rs.getDouble("account_balance"),rs.getString("account_status"),rs.getInt("user_id"));
			}
			
			return acct;
		}
		catch(SQLException e) {
			System.out.println("Problem from getAcctByID method");
		}
		
		return null;
	}

	public ArrayList<Account> getPending(){
		
		ArrayList<Account> accountsList = new ArrayList<Account>();
		Account acct = null;
		sqlcommand = "select * from accounttable where account_status = 'pending';";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				acct = new Account(rs.getInt("accountId"),rs.getString("account_name"),rs.getDouble("account_balance"),rs.getString("account_status"),rs.getInt("user_id"));
				
				accountsList.add(acct);
			}
			
			return accountsList;
		}
		catch(SQLException e) {
			System.out.println("Problem from getallaccounts method");
		}
		
		return null;	
	}
	public ArrayList<Account> getActive(){
		
		ArrayList<Account> accountsList = new ArrayList<Account>();
		Account acct = null;
		sqlcommand = "select * from accounttable where account_status = 'active';";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				acct = new Account(rs.getInt("accountId"),rs.getString("account_name"),rs.getDouble("account_balance"),rs.getString("account_status"),rs.getInt("user_id"));
				
				accountsList.add(acct);
			}
			
			return accountsList;
		}
		catch(SQLException e) {
			System.out.println("Problem from getallaccounts method");
		}
		
		return null;
	}
	@Override
	public Account getByName(String name) {
		
		Account acct = null;
		sqlcommand = "select * from accounttable where account_name = ?;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, name);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				acct = new Account(rs.getInt("accountId"),rs.getString("account_name"),rs.getDouble("account_balance"),rs.getString("account_status"),rs.getInt("user_id"));
			}
			
			return acct;
		}
		catch(SQLException e) {
			System.out.println("Problem from getAcctByName method");
		}
		
		return null;
	}
	public ArrayList<Account> getByUser(User u){
		
		ArrayList<Account> accountsList = new ArrayList<Account>();
		Account acct = null;
		sqlcommand = "select * from accounttable where user_id = ? Order By account_status;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setInt(1, u.getUserId());
			rs = ps.executeQuery();
		
			while(rs.next()) {
				acct = new Account(rs.getInt("accountId"),rs.getString("account_name"),rs.getDouble("account_balance"),rs.getString("account_status"),rs.getInt("user_id"));
				
				accountsList.add(acct);
			}
			
			return accountsList;
		}
		catch(SQLException e) {
			System.out.println("Problem from getallaccounts method");
		}
		
		return null;
	}
	@Override
	public ArrayList<Account> getAll() {
		
		ArrayList<Account> accountsList = new ArrayList<Account>();
		Account acct = null;
		sqlcommand = "select * from accounttable Order By user_id;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				acct = new Account(rs.getInt("accountId"),rs.getString("account_name"),rs.getDouble("account_balance"),rs.getString("account_status"),rs.getInt("user_id"));
				
				accountsList.add(acct);
			}
			
			return accountsList;
		}
		catch(SQLException e) {
			System.out.println("Problem from getallaccounts method");
		}
		
		return null;		
	}

	@Override
	public void update(Account t) {
		
		Account fromDB = this.getById(t.getAcctId());
		
		if (t.equals(fromDB)) {
			System.out.println("This account hasn't been changed");
			return;
		}
		else {
			fromDB.setAcctName(t.getAcctName());;
			fromDB.setAcctBalance(t.getAcctBalance());;
			fromDB.setAcctStatus(t.getAcctStatus());
			fromDB.setAcctOwner(t.getAcctOwner());
			
			
		}
		
		sqlcommand = "update accounttable set account_name = ?, account_balance = ?, account_status = ?, user_id = ? "
				+ "where accountid = ?;";
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, fromDB.getAcctName());
			ps.setDouble(2, fromDB.getAcctBalance());
			ps.setString(3, fromDB.getAcctStatus());
			ps.setInt(4, fromDB.getAcctOwner().getUserId());
			ps.setInt(5, fromDB.getAcctId());
			
			
			boolean success = ps.execute();
			
			
		}
		catch(SQLException e) {
			System.out.println("updating account error");
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(Account t) {

		sqlcommand = "Delete from accounttable where accountid = ? returning *;";
		
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setInt(1, t.getAcctId());
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("Deleted account " + rs.getString("account_name"));
			}
		}
		catch(SQLException e) {
			System.out.println("Deleting didn't work.");
		}
		
	}

}
