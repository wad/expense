package com.example;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ExpenseDto
{
	@Id
	private String id;

	private String merchant;
	private long amountInCents;
	Date dateOfExpense;
	String comment;

	// empty constructor needed for serialization
	public ExpenseDto()
	{
	}

	public ExpenseDto(
			String merchant,
			long amountInCents,
			Date dateOfExpense,
			String comment)
	{
		this.merchant = merchant;
		this.amountInCents = amountInCents;
		this.dateOfExpense = dateOfExpense;
		this.comment = comment;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMerchant()
	{
		return merchant;
	}

	public void setMerchant(String merchant)
	{
		this.merchant = merchant;
	}

	public long getAmountInCents()
	{
		return amountInCents;
	}

	public void setAmountInCents(long amountInCents)
	{
		this.amountInCents = amountInCents;
	}

	public Date getDateOfExpense()
	{
		return dateOfExpense;
	}

	public void setDateOfExpense(Date dateOfExpense)
	{
		this.dateOfExpense = dateOfExpense;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
}
