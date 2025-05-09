## Description
A simple API to manage customers data with in-memory database

## Authentication
* username: test
* password: Abcd1234*-

## API

#### /api/customer
* `POST` : Create a new customer
* `PUT` : Update existing customer

#### /api/customers
* `GET` : Get all customers
* `POST` : Create new customers
* `PUT` : Update existing customers
* `DELETE` : Delete all customers

#### /api/customers/:id
* `GET` : Get customer data
* `DELETE` : Delete customer

#### /api/customers/count
* `GET` : Get customers count

#### /api/customers/avgage
* `GET` : Get customers average age

#### /api/customers/filterbyage?minAge=x&maxAge=y
* `GET` : Get customers filter by age between x and y
