package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService
{
	@Autowired
	ExpenseDao expenseDao;

	@Autowired
	ExpenseValidationService expenseValidationService;

	public OperationResult createExpense(Expense expense)
	{
		OperationResult operationResult = expenseValidationService.validateAsNewExpense(expense);
		if (!operationResult.wasSuccessful())
			return operationResult;

		Expense inserted = expenseDao.insert(expense);
		if (inserted == null)
			return OperationResult.failed("Failed to persist new expense for unknown reason.");

		expense.setId(inserted.getId());
		return OperationResult.succeeded();
	}

	public Expense getExpense(String id)
	{
		return expenseDao.findOne(id);
	}

	public OperationResult updateExpense(Expense expense)
	{
		Expense expenseRead = expenseDao.findOne(expense.getId());
		if (expenseRead == null)
			return OperationResult.failed("Could not find an expense with id " + expense.getId() + " to update.");

		OperationResult validationResult = expenseValidationService.validateUpdate(expenseRead, expense);
		if (!validationResult.wasSuccessful())
			return validationResult;

		OperationResult updateResult = expenseRead.applyUpdate(expense);
		if (!updateResult.wasSuccessful())
			return updateResult;

		Expense expenseSaved = expenseDao.save(expenseRead);
		if (expenseSaved == null)
			return OperationResult.failed("Failed to persist update for unknown reason.");

		return OperationResult.succeeded();
	}
}
