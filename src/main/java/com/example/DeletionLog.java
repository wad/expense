package com.example;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class DeletionLog
{
	@Id
	private String id;

	private String expenseId;
	private Date deletionDate;

	// empty constructor needed for Jackson
	public DeletionLog()
	{
	}

	public DeletionLog(String expenseId, Date deletionDate)
	{
		this.expenseId = expenseId;
		this.deletionDate = deletionDate;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getExpenseId()
	{
		return expenseId;
	}

	public void setExpenseId(String expenseId)
	{
		this.expenseId = expenseId;
	}

	public Date getDeletionDate()
	{
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate)
	{
		this.deletionDate = deletionDate;
	}
}
