package models;

import java.sql.Timestamp;
import repos.AccountRepository;
import repos.UserRepository;

public class Transaction {

	private int id;
	private String type;
	private double amount;
	private Account account;
	private User user;
	private  Timestamp timestamp;
	private Account toAccount;
	private String status;
	
	private static AccountRepository ar = new AccountRepository();
	private static UserRepository ur = new UserRepository();
	
	public Transaction(int id,String type, double amount, Account accountId, User userId, Timestamp ts) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.account = accountId;
		this.user = userId;
		this.timestamp = ts;
	}
	
	public Transaction(int id,String type, double amount, int accountId, int userId, Timestamp ts) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.account = ar.getById(accountId);
		this.user = ur.getById(userId);
		this.timestamp = ts;
	}
	
	public Transaction(int id,String type, double amount, Account accountId, User userId, Timestamp ts, String status, Account toAcct) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.account = accountId;
		this.user = userId;
		this.timestamp = ts;
		this.status = status;
		if (toAcct != null){
			this.toAccount = toAcct;
		}
		else {
			this.toAccount = null;
		}
	}
	
	public Transaction(int id,String type, double amount, int accountId, int userId, Timestamp ts, String status, Integer toAcct) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.account = ar.getById(accountId);
		this.user = ur.getById(userId);
		this.timestamp = ts;
		this.status = status;
		if (toAcct != null){
			this.toAccount = ar.getById(toAcct);
		}
		else {
			this.toAccount = null;
		}
	}
	
	public Transaction(int id,String type, double amount, int accountId, int userId,String status, Integer toAcct) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.account = ar.getById(accountId);
		this.user = ur.getById(userId);
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.status = status;
		if (toAcct != null){
			this.toAccount = ar.getById(toAcct);
		}
		else {
			this.toAccount = null;
		}
	}
	
	
	public Transaction(String type, double amount, Account accountId, User userId, Timestamp ts) {
		super();
		this.id = 0;
		this.type = type;
		this.amount = amount;
		this.account = accountId;
		this.user = userId;
		this.timestamp = ts;
	}
	
	public Transaction(String type, double amount, Account accountId, User userId) {
		super();
		this.id = 0;
		this.type = type;
		this.amount = amount;
		this.account = accountId;
		this.user = userId;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public Transaction(String type, double amount, Account accountId, User userId,String status,Account toAcct) {
		super();
		this.id = 0;
		this.type = type;
		this.amount = amount;
		this.account = accountId;
		this.user = userId;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.status=status;
		this.toAccount=toAcct;
	}
	
	public Transaction() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public void setAccount(int accountid) {
		this.account = ar.getById(accountid);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUser(int user) {
		this.user = ur.getById(user);
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
	
	public void setToAccount(Integer toAccount) {
		if (toAccount == null) {
			this.toAccount = null;
		}
		else {
			this.toAccount = ar.getById(toAccount);
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((toAccount == null) ? 0 : toAccount.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (id != other.id)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (toAccount == null) {
			if (other.toAccount != null)
				return false;
		} else if (!toAccount.equals(other.toAccount))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		/*
		 * return "Transaction [id=" + id + ", type=" + type + ", amount=" + amount +
		 * ", account=" + account + ", user=" + user + ", timestamp=" + timestamp +
		 * ", toAccount=" + toAccount + ", status=" + status + "]";
		 */
		if (this.toAccount != null) {
			return timestamp +" type: " + type + " Amount: " + amount + " From: " + user + " " +
					account+ " To: " + toAccount + " Status : " + status;
		}
		else {
			return timestamp +" type: " + type + " Amount: " + amount + " Account: " + account + " User: "+
					user;
		}
	}

	

	

	

	
	
	
}
