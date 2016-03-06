package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseController
{
	@Autowired
	ExpenseService expenseService;

	static final String JSON_LABEL_SUCCESS = "success";
	static final String JSON_LABEL_ERROR = "error";

	private static final JsonNodeFactory factory = JsonNodeFactory.instance;

	@RequestMapping(
			value = "/health",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public JsonNode checkHealth(
			@RequestParam(required = false, defaultValue = "bar") String foo)
	{
		ObjectNode result = factory.objectNode();
		result.put("foo", "baz");
		return result;
	}

	@RequestMapping(
			value = "/",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK) // (normally this would be CREATED)
	public JsonNode createExpense(@RequestBody ExpenseDto expense)
	{
		boolean success;
		ObjectNode result = factory.objectNode();
		try
		{
			success = expenseService.createExpense(expense);
			if (!success)
				result.put(JSON_LABEL_ERROR, "Something went wrong");
		}
		catch (Throwable t)
		{
			success = false;
			result.put(JSON_LABEL_ERROR, t.getMessage());
		}
		result.put(JSON_LABEL_SUCCESS, success);
		return result;
	}
}
