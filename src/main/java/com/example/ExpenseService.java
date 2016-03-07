package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService
{
	@Autowired
	ExpenseDao expenseDao;

	@Autowired
	ExpenseValidationService expenseValidationService;

	public OperationResult createExpense(Expense expense)
	{
		OperationResult operationResult = expenseValidationService.validateForNewExpense(expense);
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

	public List<Expense> getAllExpenses()
	{
		return expenseDao.findAll();
	}

	public OperationResult updateExpense(Expense expense)
	{
		Expense expenseRead = expenseDao.findOne(expense.getId());
		if (expenseRead == null)
			return OperationResult.failed("Could not find an expense with id " + expense.getId() + " to update.");

		OperationResult validationResult = expenseValidationService.validateForUpdate(expenseRead, expense);
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

	public OperationResult deleteExpense(String id)
	{
		Expense expenseRead = expenseDao.findOne(id);
		if (expenseRead == null)
			return OperationResult.failed("Could not find an expense with id " + id + " to delete.");

		OperationResult validationResult = expenseValidationService.validateForDelete(expenseRead);
		if (!validationResult.wasSuccessful())
			return validationResult;

		expenseDao.delete(id);

		return OperationResult.succeeded();
	}
}
