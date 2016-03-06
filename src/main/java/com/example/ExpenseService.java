package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService
{
	@Autowired
	ExpenseDao expenseDao;

	public boolean createExpense(ExpenseDto expenseDto)
	{
		ExpenseDto inserted = expenseDao.insert(expenseDto);
		return inserted != null;
	}
}
