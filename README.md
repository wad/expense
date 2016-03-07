## Synopsis

Sample RESTFul Expense implementation, by Eric Wadsworth (2016-03-05)

## Overall Design

Controller handles REST endpoints, talks to Service layer.
Service classes talk to DAOs, which talk to the persistence store.
Data is passed around via DTO objects.

## Assumptions Made

Project uses only:

* Spring Boot
* Spring Web dependency
* Spring Security dependency
* Spring Data MongoDB dependency

## Necessary Improvements

* Replace java.util.Date with Joda
* Version the API endpoints
* Currently just logging delete events to Mongo. Could use log4j instead.
* When logging delete events to Mongo, make it in two stages, second one to confirm, after the delete committed.
* Needs the unit tests, using mocks
* Needs a couple of black-box full-stack integration tests, ideally with an in-memory MongoDB database of some sort
* To handle queries with filters, it needs a way to structure filters with AND and OR, along with operations such as <, >, ==, !=, in, notIn, etc.
