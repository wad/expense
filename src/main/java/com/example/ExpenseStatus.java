package com.example;

public enum ExpenseStatus
{
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
