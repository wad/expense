package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseDao extends MongoRepository<Expense, String>
{
	@Override
	Expense insert(Expense expense);

	@Override
	Expense save(Expense expense);

	@Override
	Expense findOne(String id);
}
