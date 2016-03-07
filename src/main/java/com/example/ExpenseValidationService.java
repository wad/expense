package com.example;

import org.springframework.stereotype.Service;

@Service
public class ExpenseValidationService
{
	public OperationResult validateForNewExpense(Expense expense)
	{
		if (expense.getExpenseAmountInCents() < 0)
			return OperationResult.failed("Negative expense amount detected");

		String merchant = expense.getMerchant();
		if (merchant == null || merchant.isEmpty())
			return OperationResult.failed("Missing merchant");

		if (expense.getDateOfExpense() == null)
			return OperationResult.failed("Missing expense date");

		return OperationResult.succeeded();
	}

	public OperationResult validateForUpdate(
			Expense originalExpense,
			Expense expenseContainingUpdates)
	{
		if (originalExpense.getExpenseStatus() == ExpenseStatus.Reimbursed)
			return OperationResult.failed("Cannot update reimbursed expenses.");

		Long amount = expenseContainingUpdates.getExpenseAmountInCents();
		if (amount != null && amount < 0)
			return OperationResult.failed("Cannot update to a negative expense amount.");

		return OperationResult.succeeded();
	}

	public OperationResult validateForDelete(Expense expenseRead)
	{
		if (expenseRead.getExpenseStatus() != ExpenseStatus.New)
			return OperationResult.failed("Can only delete records with 'new' status.");

		return OperationResult.succeeded();
	}
}
