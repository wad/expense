package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
public class ExpenseController
{
	@Autowired
	ExpenseService expenseService;

	static final String JSON_LABEL_SUCCESS = "success";
	static final String JSON_LABEL_ERROR = "error";
	static final String JSON_LABEL_ID = "id";
	static final String JSON_LABEL_STATUS = "status";
	static final String JSON_VALUE_DELETED = "deleted";

	private static final JsonNodeFactory factory = JsonNodeFactory.instance;

	@RequestMapping(
			value = "/expense/",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK) // (normally this would be CREATED)
	public JsonNode createExpense(
			@RequestBody Expense expense)
	{
		boolean success;
		ObjectNode result = factory.objectNode();
		try
		{
			OperationResult operationResult = expenseService.createExpense(expense);
			success = operationResult.wasSuccessful();
			if (success)
				result.put(JSON_LABEL_ID, expense.getId());
			else
				result.put(JSON_LABEL_ERROR, operationResult.getFailureExplanation());
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			success = false;
			result.put(JSON_LABEL_ERROR, t.getMessage());
		}
		result.put(JSON_LABEL_SUCCESS, success);
		return result;
	}

	@RequestMapping(
			value = "/expense/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public JsonNode getExpense(
			@PathVariable String id)
	{
		try
		{
			Expense expense = expenseService.getExpense(id);
			if (expense != null)
				return expense.toJson();
			else
			{
				ObjectNode result = factory.objectNode();
				result.put(JSON_LABEL_SUCCESS, false);
				result.put(JSON_LABEL_ERROR, "Could not find expense with id " + id);
				return result;
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			ObjectNode result = factory.objectNode();
			result.put(JSON_LABEL_SUCCESS, false);
			result.put(JSON_LABEL_ERROR, t.getMessage());
			return result;
		}
	}

	@RequestMapping(
			value = "/expenses/",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public JsonNode getExpenses()
	{
		try
		{
			List<Expense> expenses = expenseService.getAllExpenses();
			if (!expenses.isEmpty())
			{
				ArrayNode arrayNode = factory.arrayNode();
				for (Expense expense : expenses)
					arrayNode.add(expense.toJson());
				return arrayNode;
			}
			else
			{
				ObjectNode result = factory.objectNode();
				result.put(JSON_LABEL_SUCCESS, false);
				result.put(JSON_LABEL_ERROR, "No expenses found");
				return result;
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			ObjectNode result = factory.objectNode();
			result.put(JSON_LABEL_SUCCESS, false);
			result.put(JSON_LABEL_ERROR, t.getMessage());
			return result;
		}
	}

	@RequestMapping(
			value = "/expense/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public JsonNode updateExpense(
			@RequestBody Expense expense,
			@PathVariable String id)
	{
		boolean success;
		ObjectNode result = factory.objectNode();
		try
		{
			String idFromExpense = expense.getId();
			if (idFromExpense == null)
				expense.setId(id);

			if (!id.equals(idFromExpense))
			{
				success = false;
				result.put(JSON_LABEL_ERROR, "The id supplied in the expense didn't match the specified id.");
			}
			else
			{
				OperationResult operationResult = expenseService.updateExpense(expense);
				success = operationResult.wasSuccessful();
				if (!success)
					result.put(JSON_LABEL_ERROR, operationResult.getFailureExplanation());
			}
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			success = false;
			result.put(JSON_LABEL_ERROR, t.getMessage());
		}
		result.put(JSON_LABEL_SUCCESS, success);
		return result;
	}

	@RequestMapping(
			value = "/expense/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public JsonNode deleteExpense(
			@PathVariable String id)
	{
		boolean success;
		ObjectNode result = factory.objectNode();
		try
		{
			OperationResult operationResult = expenseService.deleteExpense(id);
			success = operationResult.wasSuccessful();
			if (success)
			{
				result.put(JSON_LABEL_ID, id);
				result.put(JSON_LABEL_STATUS, JSON_VALUE_DELETED);
			}
			else
				result.put(JSON_LABEL_ERROR, operationResult.getFailureExplanation());
		}
		catch (Throwable t)
		{
			t.printStackTrace();
			success = false;
			result.put(JSON_LABEL_ERROR, t.getMessage());
		}
		result.put(JSON_LABEL_SUCCESS, success);
		return result;
	}
}
