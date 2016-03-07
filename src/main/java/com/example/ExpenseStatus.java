package com.example;

public enum ExpenseStatus
{
	// todo: this should persist the "new", not "newRecord", to the persistence store.
	newRecord("new"),
	reimbursed("reimbursed");

	private String name;

	ExpenseStatus(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
