package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.User;
import utils.JDBCConnection;

public class UserRepository implements DataRepository<User> {

	private String sqlcommand;
	private PreparedStatement ps;
	private ResultSet rs;
	public static  Connection conn = JDBCConnection.getConnection();
	
	@Override
	public ArrayList<User> getAll(){
		
		ArrayList<User> userList = new ArrayList<User>();
		
		try {
			
			sqlcommand = "Select * From userTable where status = true Order By userid;";
			ps = conn.prepareStatement(sqlcommand);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				User user = new User(rs.getInt("userId"),rs.getString("user_name"),rs.getString("user_pass"),rs.getBoolean("status"),rs.getString("first_name"), rs.getString("last_name"));
				
				userList.add(user);
			}
			
			return userList;
			
		}
		catch(SQLException e) {
			System.out.println("Invalid SQL command");
		}
		return null;
	}
	
	@Override
	public User getById(Integer id) {
		
		User user = null;
		sqlcommand = "select * from usertable where userid = ?;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setInt(1, id);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				user = new User(rs.getInt("userId"),rs.getString("user_name"),rs.getString("user_pass"),rs.getBoolean("status"),rs.getString("first_name"),rs.getString("last_name"));
			}
			
			return user;
		}
		catch(SQLException e) {
			System.out.println("Problem from getUserByID method");
		}
		
		return null;
	}

	
	
	@Override
	public boolean add(User u) {
		
		sqlcommand = "insert into usertable values (default, ?,?,?,?,?);";
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, u.getLastName());
			ps.setString(2, u.getFirstName());
			ps.setString(3, u.getUsername());
			ps.setString(4, u.getPassword());
			ps.setBoolean(5, u.getStatus());
			
			boolean success = ps.execute();
			
			if (success) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(SQLException e) {
			System.out.println("add user issue");
		}
		
		return false;
	}

	
	
	@Override
	public void update(User u) {
		User fromDB = this.getById(u.getUserId());
		
		if (u.equals(fromDB)) {
			System.out.println("This user hasn't been changed");
			return;
		}
		else {
			fromDB.setFirstName(u.getFirstName());
			fromDB.setLastName(u.getLastName());
			fromDB.setPassword(u.getPassword());
			fromDB.setStatus(u.getStatus());
			fromDB.setUsername(u.getUsername());
			
		}
		
		sqlcommand = "update usertable set last_name = ?, first_name = ?, user_name = ?, user_pass = ?, status = ? "
				+ "where userid = ?;";
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, fromDB.getLastName());
			ps.setString(2, fromDB.getFirstName());
			ps.setString(3, fromDB.getUsername());
			ps.setString(4, fromDB.getPassword());
			ps.setBoolean(5, fromDB.getStatus());
			ps.setInt(6, fromDB.getUserId());
			
			boolean success = ps.execute();
			
			
		}
		catch(SQLException e) {
			System.out.println("updating user error");
		}
		
	}

	
	@Override
	public void delete(User u) {
		
		sqlcommand = "Delete from usertable where userid = ? returning *;";
		
		//tried to add a check to make sure it exists before trying to delete but this broke the method.
		/*
		 * User delete = this.getById(u.getUserId());
		 * 
		 * if ((delete == null) || (!delete.equals(u))) {
		 * System.out.println("This user doesn't exist, try again"); return; }
		 */
		
		try {
			ps = conn.prepareStatement(sqlcommand);
			ps.setInt(1, u.getUserId());
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("Deleted user " + rs.getString("user_name"));
			}
		}
		catch(SQLException e) {
			System.out.println("Deleting didn't work.");
		}
		
	}

	@Override
	public User getByName(String name) {
		User user = null;
		sqlcommand = "select * from usertable where user_name = ?;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, name);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				user = new User(rs.getInt("userId"),rs.getString("user_name"),rs.getString("user_pass"),rs.getBoolean("status"),rs.getString("first_name"),rs.getString("last_name"));
			}
			
			return user;
		}
		catch(SQLException e) {
			System.out.println("Problem from getUserByName method");
		}
		
		return null;
	}
	
	public User getByCredentials(String username, String password) {
		
		User user = null;
		sqlcommand = "select * from usertable where user_name = ? and user_pass = ?;";
		try {
			
			ps = conn.prepareStatement(sqlcommand);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
		
			while(rs.next()) {
				user = new User(rs.getInt("userId"),rs.getString("user_name"),rs.getString("user_pass"),rs.getBoolean("status"),rs.getString("first_name"),rs.getString("last_name"));
			}
			
			return user;
		}
		catch(SQLException e) {
			System.out.println("Problem from getUserByName method");
		}
		
		return null;
	}
	
	
	
	
}
