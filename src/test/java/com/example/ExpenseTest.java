package com.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ExpenseTest
{
	@Test
	public void testConstruction()
	{
		final String merchant = "m";
		final Long amount = 123L;
		Date date = new Date(11111L);
		ExpenseStatus expenseStatus = ExpenseStatus.New;
		List<String> comments = Arrays.asList("aaa", "bbb");
		Expense expense = new Expense(merchant, amount, date, expenseStatus, comments);

		assertEquals(merchant, expense.getMerchant());
		assertEquals(amount, expense.getExpenseAmountInCents());
		assertEquals(1.23f, expense.getExpenseAmountInDollars(), .01f);
		assertEquals(date, expense.getDateOfExpense());
		assertEquals(ExpenseStatus.New, expense.getExpenseStatus());
		assertEquals("aaa\nbbb", expense.getComment());
		assertEquals(2, expense.getComments().size());
		assertTrue(expense.getComments().contains("aaa"));
		assertTrue(expense.getComments().contains("bbb"));
		assertNull(expense.getId());
	}

	@Test
	public void testUpdate()
	{
		// todo
	}
}
