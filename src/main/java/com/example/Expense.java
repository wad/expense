package com.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Expense
{
	@Id
	private String id;

	private String merchant;

	// storing in cents, to avoid silliness with float types
	private Long expenseAmountInCents;

	// todo: Switch to Joda time
	private Date dateOfExpense;

	private ExpenseStatus expenseStatus;
	private List<String> comments;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	// empty constructor needed for serialization
	public Expense()
	{
		// new records are always of this status
		expenseStatus = ExpenseStatus.newRecord;

		comments = new ArrayList<>();
	}

	public Expense(
			String merchant,
			Long expenseAmountInCents,
			Date dateOfExpense,
			ExpenseStatus expenseStatus,
			List<String> comments)
	{
		this.merchant = merchant;
		this.expenseAmountInCents = expenseAmountInCents;
		this.dateOfExpense = dateOfExpense;
		this.expenseStatus = expenseStatus;

		if(comments == null)
			this.comments = new ArrayList<>();
		else
			this.comments = comments;
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

	public Float getExpenseAmountInDollars()
	{
		if (expenseAmountInCents == null)
			return null;
		return Float.valueOf(String.valueOf(expenseAmountInCents)) / 100f;
	}

	public void setExpenseAmountInDollars(Float expenseAmountInDollars)
	{
		if (expenseAmountInDollars == null)
			expenseAmountInCents = null;
		else
			expenseAmountInCents = (long)(expenseAmountInDollars * 100f);
	}

	public Long getExpenseAmountInCents()
	{
		return expenseAmountInCents;
	}

	public void setExpenseAmountInCents(Long expenseAmountInCents)
	{
		this.expenseAmountInCents = expenseAmountInCents;
	}

	public Date getDateOfExpense()
	{
		return dateOfExpense;
	}

	public void setDateOfExpense(Date dateOfExpense)
	{
		this.dateOfExpense = dateOfExpense;
	}

	public ExpenseStatus getExpenseStatus()
	{
		if (expenseStatus == null)
			return ExpenseStatus.newRecord;
		return expenseStatus;
	}

	public void setExpenseStatus(ExpenseStatus expenseStatus)
	{
		this.expenseStatus = expenseStatus;
	}

	public String getComment()
	{
		return comments.stream().collect(joining("\n"));
	}

	public void setComment(String comment)
	{
		addComment(comment);
	}

	@JsonIgnore
	public void addComment(String comment)
	{
		if (comment != null && !comment.isEmpty())
			comments.add(comment);
	}

	public List<String> getComments()
	{
		return comments;
	}

	public void setComments(List<String> comments)
	{
		if (comments != null)
			this.comments = comments;
	}

	@JsonIgnore
	public OperationResult applyUpdate(Expense expenseContainingUpdates)
	{
		int numUpdatesApplied = 0;

		if (expenseContainingUpdates.merchant != null)
		{
			this.merchant = expenseContainingUpdates.merchant;
			numUpdatesApplied++;
		}

		if (expenseContainingUpdates.expenseAmountInCents != null)
		{
			expenseAmountInCents = expenseContainingUpdates.expenseAmountInCents;
			numUpdatesApplied++;
		}

		if (expenseContainingUpdates.dateOfExpense != null)
		{
			dateOfExpense = expenseContainingUpdates.dateOfExpense;
			numUpdatesApplied++;
		}

		if (expenseContainingUpdates.expenseStatus != null)
		{
			expenseStatus = expenseContainingUpdates.expenseStatus;
			numUpdatesApplied++;
		}

		if (expenseContainingUpdates.comments != null && !expenseContainingUpdates.comments.isEmpty())
		{
			comments.addAll(expenseContainingUpdates.comments);
			numUpdatesApplied++;
		}

		if (numUpdatesApplied <= 0)
			return OperationResult.failed("No updates to apply.");

		return OperationResult.succeeded();
	}

	@JsonIgnore
	public JsonNode toJson() throws JsonProcessingException
	{
		return OBJECT_MAPPER.valueToTree(this);
	}
}
