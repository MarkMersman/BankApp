package models;

import java.text.NumberFormat;

import repos.UserRepository;

public class Account {

	private int acctId;
	private String acctName;
	private double acctBalance;
	private String acctStatus;
	private User acctOwner;
	private static UserRepository ur = new UserRepository();
	
	public Account(String acctName, double acctBalance, String acctStatus, User acctOwner) {
		super();
		this.acctId = 0;
		this.acctName = acctName;
		this.acctBalance = acctBalance;
		this.acctStatus = acctStatus;
		this.acctOwner = acctOwner;
	}
		
	public Account(int acctId, String acctName, double acctBalance, String acctStatus, User acctOwner) {
		super();
		this.acctId = acctId;
		this.acctName = acctName;
		this.acctBalance = acctBalance;
		this.acctStatus = acctStatus;
		this.acctOwner = acctOwner;
	}
	
	public Account(int acctId, String acctName, double acctBalance, String acctStatus, int acctOwnerID) {
		super();
		this.acctId = acctId;
		this.acctName = acctName;
		this.acctBalance = acctBalance;
		this.acctStatus = acctStatus;
		this.acctOwner = ur.getById(acctOwnerID);
	}



	public int getAcctId() {
		return acctId;
	}

	public void setAcctId(int acctId) {
		this.acctId = acctId;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public double getAcctBalance() {
		
		return acctBalance;
	}

	public void setAcctBalance(double acctBalance) {
		this.acctBalance = acctBalance;
	}

	public String getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	public User getAcctOwner() {
		return acctOwner;
	}

	public void setAcctOwner(User acctOwner) {
		this.acctOwner = acctOwner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(acctBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + acctId;
		result = prime * result + ((acctName == null) ? 0 : acctName.hashCode());
		result = prime * result + ((acctOwner == null) ? 0 : acctOwner.hashCode());
		result = prime * result + ((acctStatus == null) ? 0 : acctStatus.hashCode());
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
		Account other = (Account) obj;
		if (Double.doubleToLongBits(acctBalance) != Double.doubleToLongBits(other.acctBalance))
			return false;
		if (acctId != other.acctId)
			return false;
		if (acctName == null) {
			if (other.acctName != null)
				return false;
		} else if (!acctName.equals(other.acctName))
			return false;
		if (acctOwner == null) {
			if (other.acctOwner != null)
				return false;
		} else if (!acctOwner.equals(other.acctOwner))
			return false;
		if (acctStatus == null) {
			if (other.acctStatus != null)
				return false;
		} else if (!acctStatus.equals(other.acctStatus))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return acctName + " Balance=" + acctBalance;
	}
	
	public String balanceToString() {
		NumberFormat dollars = NumberFormat.getCurrencyInstance();
		
		return dollars.format(acctBalance);
	}
	
	
}
