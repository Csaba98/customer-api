## Description
A simple API to manage customers data with in-memory database

## Authentication
* username: vcsaba
* password: Abcd1234*-

## API

#### /api/customer
* `GET` : Get all customers data
* `POST` : Create a new customer
* `DELETE` : Delete all customers

#### /api/customer/:id
* `GET` : Get customer data
* `PUT` : Update existing customer
* `DELETE` : Delete customer

#### /api/customer/count
* `GET` : Get customers count

#### /api/customer/avgage
* `GET` : Get customers average age

#### /api/customer/filterbyage?minAge=x&maxAge=y
* `GET` : Get customers filter by age between x and y
