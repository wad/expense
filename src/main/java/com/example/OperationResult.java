package com.example;

public class OperationResult
{
	boolean passed;
	String failureExplanation;

	public static OperationResult succeeded()
	{
		return new OperationResult(true, null);
	}

	public static OperationResult failed(String failureExplanation)
	{
		return new OperationResult(false, failureExplanation);
	}

	private OperationResult(
			boolean passed,
			String failureExplanation)
	{
		this.passed = passed;
		this.failureExplanation = failureExplanation;
	}

	public boolean wasSuccessful()
	{
		return passed;
	}

	public String getFailureExplanation()
	{
		return failureExplanation;
	}
}
