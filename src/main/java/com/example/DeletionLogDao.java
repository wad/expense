package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletionLogDao extends MongoRepository<DeletionLog, String>
{
	@Override
	DeletionLog insert(DeletionLog deletionLog);
}
