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

* Never use java.util.java, use Joda instead
* Version the API endpoints
* Currently just logging delete events to Mongo. Could use a log file instead.
* When logging delete events to Mongo, make it in two stages, second one to confirm, after the delete committed.
* Getting records doesn't properly format the date as a string
