Objective:
To design a RESTful API service for a unique digital product management system.
The service manages basic CRUD operations for digital products in the system.

Install:
I have Used Java, IntelliJ IDEA, MySql server to implement this project.

Project Structure:

	It contains 2 Folders:
	   --Main Application
	   --Client Application
   
	--ImmutlyProductsApplication.Java -- The main Application

	--ImmutlyClientApplication.java--The Client Application

Usage:
  --The main Application runs on : http://localhost:8080/swagger-ui/index.html#/
  --The Client Application runs on : http://localhost:8081/swagger-ui/index.html#/
  
Main Application CRUD operations (Runs on port 8080):
---Here we can peform CRUD operations:

---Create a product Request structure:
  Url:
	  curl -X 'POST' \
	  'http://localhost:8080/immutly/products' \
	  -H 'accept: */*' \
	  -H 'Content-Type: application/json' \
	  -d '{
	  "name": "Samsung",
	  "description": "Samsung",
	  "price": 12000.00,
	  "isAvailable": true
	}'
	
	Request Structure:
	
	{
	  "name": "string",
	  "description": "string",
	  "price": 0,
	  "isAvailable": true
	}
	
	
	Resposnse Structure:
	{
	  "id": 9,
	  "name": "Samsung",
	  "description": "Samsung",
	  "price": 12000.00,
	  "isAvailable": true
	}
	
	-------------
	
---Get all products:

Url : 
	curl -X 'GET' \
	'http://localhost:8080/immutly/products' \
	-H 'accept: */*'
	
--Get product by id:
Url:
curl -X 'GET' \
  'http://localhost:8080/immutly/products/1' \
  -H 'accept: */*'
  
 Resposnse:
	 {
	  "id": 1,
	  "name": "Apple",
	  "description": "Iphone 15",
	  "price": 150000.00,
	  "isAvailable": true
	}

--Update product by id:

Url :

	curl -X 'PUT' \
	  'http://localhost:8080/immutly/products/1' \
	  -H 'accept: */*' \
	  -H 'Content-Type: application/json' \
	  -d '{
	  "name": "Nokia",
	  "description": "Nokia",
	  "price": 12000,
	  "isAvailable": true
	}'
	
	
	Resposnse structure:
	
	{
	  "id": 1,
	  "name": "Nokia",
	  "description": "Nokia",
	  "price": 12000,
	  "isAvailable": true
	}

--Delete a product:
Url :
curl -X 'DELETE' \
  'http://localhost:8080/immutly/products/3' \
  -H 'accept: */*'
  
  Resposnse : Successfully Deleted!!!


--Update multiple entries:
Url:
curl -X 'PUT' \
  'http://localhost:8080/immutly/products/price_update' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '[
  {
    "id": 8,
    "price": 123400.00
  },
  {
    "id": 9,
    "price": 1234.00
  }

]'

Resposnse structure:
	[
	  {
		"id": 8,
		"name": "Motorola",
		"description": "Motorola",
		"price": 123400,
		"isAvailable": true
	  },
	  {
		"id": 9,
		"name": "Samsung",
		"description": "Samsung",
		"price": 1234,
		"isAvailable": true
	  }
	]
	
	
------------
In Client Side(Runs on port 8081):

Updating multiple Price Entries:

Url:
	curl -X 'PUT' \
	  'http://localhost:8081/immutly/priceUpdate' \
	  -H 'accept: */*' \
	  -H 'Content-Type: application/json' \
	  -d '[
	  {
		"id": 8,
		"price": 5000.00
	  },
	  {
		"id": 9,
		"price": 20000.00
	  }
	]'
	
Resposnse structure:
	[
	  {
		"id": 8,
		"name": "Motorola",
		"description": "Motorola",
		"price": 5000,
		"isAvailable": true
	  },
	  {
		"id": 9,
		"name": "Samsung",
		"description": "Samsung",
		"price": 20000,
		"isAvailable": true
	  }
	]



 
 

 

