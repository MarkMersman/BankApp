package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Account;
import models.Transaction;
import utils.JDBCConnection;

public class TransactionRepository implements DataRepository<Transaction>{

	private String sqlcommand;
	private PreparedStatement ps;
	private ResultSet rs;
	public static  Connection conn = JDBCConnection.getConnection();
	
	
	
	@Override
	public boolean add(Transaction t) {

		if (t.getToAccount() != null) {
			sqlcommand = "insert into transactiontable values (default, ?,?,?,?,?,?,?);";
		}
		else {
			sqlcommand = "insert into transactiontable values (default, ?,?,?,?,?);";
		}
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, t.getType() );
			ps.setDouble(2, t.getAmount());
			ps.setInt(3, t.getAccount().getAcctId());
			ps.setInt(4, t.getUser().getUserId());
			ps.setTimestamp(5, t.getTimestamp());			
			if (t.getToAccount() != null) {
				ps.setString(6, t.getStatus());
				ps.setInt(7, t.getToAccount().getAcctId());
			}
			
			
			boolean success = ps.execute();
			
			if (success) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			System.out.println("add transaction issue");
		}
		
		return false;
	}
	
	@Override
	public Transaction getById(Integer id) {

		
		Transaction tran = null;
		sqlcommand = "select * from transactiontable where transactionid = ?;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setInt(1, id);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				tran = new Transaction();
				tran.setId(rs.getInt("transactionid"));
				tran.setStatus(rs.getString("status"));
				tran.setType(rs.getString("trans_type"));
				tran.setTimestamp(rs.getTimestamp("trans_timestamp"));
				tran.setAmount(rs.getDouble("amount"));
				tran.setAccount(rs.getInt("account_id"));
				tran.setUser(rs.getInt("user_id"));
				tran.setToAccount(rs.getInt("to_account"));
								
			}
			
			return tran;
		}
		catch(SQLException e) {
			System.out.println("Isssue with getalltransactions method");
		}
			
		return null;
	}
	@Override
	public Transaction getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Transaction> getAll() {
		ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
		Transaction tran = null;
		sqlcommand = "select * from transactiontable Order By trans_timestamp;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				tran = new Transaction();
				tran.setId(rs.getInt("transactionid"));
				tran.setStatus(rs.getString("status"));
				tran.setType(rs.getString("trans_type"));
				tran.setTimestamp(rs.getTimestamp("trans_timestamp"));
				tran.setAmount(rs.getDouble("amount"));
				tran.setAccount(rs.getInt("account_id"));
				tran.setUser(rs.getInt("user_id"));
				tran.setToAccount(rs.getInt("to_account"));
				
				transactionsList.add(tran);
			}
			
			return transactionsList;
		}
		catch(SQLException e) {
			System.out.println("Isssue with getalltransactions method");
		}
			
		return null;
	}
	@Override
	public void update(Transaction t) {
		
		Transaction fromDB = this.getById(t.getId());
		
		if (t.equals(fromDB)) {
			System.out.println("This account hasn't been changed");
			return;
		}
		else {
			
			fromDB.setStatus(t.getStatus());
				
			
		}
		
		sqlcommand = "update transactiontable set status = ? "
				+ "where transactionid = ?;";
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, t.getStatus() );
			ps.setInt(2, t.getId());
			
			
			boolean success = ps.execute();
			
			
		}
		catch(SQLException e) {
			System.out.println("updating transaction status error");
			e.printStackTrace();
		}
		
	}
	@Override
	public void delete(Transaction t) {
		// TODO Auto-generated method stub
		
	}
	public ArrayList<Transaction> getPending(Account t){
		
		ArrayList<Transaction> pendingList = new ArrayList<Transaction>();
		Transaction tran = null;
		sqlcommand = "select * from transactiontable where status = 'pending' and to_account = ? Order By trans_timestamp;";
		
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setInt(1, t.getAcctId());
			rs = ps.executeQuery();
		
			while(rs.next()) {
				tran = new Transaction();
				tran.setId(rs.getInt("transactionid"));
				tran.setStatus(rs.getString("status"));
				tran.setType(rs.getString("trans_type"));
				tran.setTimestamp(rs.getTimestamp("trans_timestamp"));
				tran.setAmount(rs.getDouble("amount"));
				tran.setAccount(rs.getInt("account_id"));
				tran.setUser(rs.getInt("user_id"));
				tran.setToAccount(rs.getInt("to_account"));
				
				pendingList.add(tran);
			}
			
			return pendingList;
		}
		catch(SQLException e) {
			System.out.println("Issue with getpendingtransactions method");
		}
			
		return null;
	}
	
	

}
