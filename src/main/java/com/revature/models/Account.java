package com.revature.models;

import java.io.Serializable;
import java.util.*;

public class Account implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private int balance;
	private String status;
	private ArrayList<String> owners;
	
	public Account() {
		super();
	}
	
	public Account(String id, String type, int balance, String status, ArrayList<String> owners) {
		super();
		this.id = id;
		this.type = type;
		this.balance = balance;
		this.status = status;
		this.owners = owners; 
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public ArrayList<String> getOwners(){
		return owners;
	}
	
	public void setOwners(ArrayList<String> owners) {
		this.owners = owners;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + balance;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((owners == null) ? 0 : owners.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (balance != other.balance)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (owners == null) {
			if (other.owners != null)
				return false;
		} else if (!owners.equals(other.owners))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
