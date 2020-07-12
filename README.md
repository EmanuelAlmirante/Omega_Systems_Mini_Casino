# Omega Systems Mini Casino

The objective of this exercise is to implement a simplified version of an API that will serve a casino.

### Notes:

In the _DataLoader_ class is created one game when the application is started. That is the game used when interacting with the API. To create more games it is only necessary to instantiate them in that class. 

It was used an in-memory database, more precisely the _H2 Database_. By using an in-memory database all data is only persisted while the application is running and after stopping the application all data will be lost.

To manually test the API please import the file in the postman_collections to Postman and use those endpoints.

### Setup:

- Clone project to a folder
- Run the application with:
  - _mvn clean install_
  - _mvn spring-boot:run_
- Test the application with:
  - _mvn test_ -> run all tests
  - _mvn -Dtest=TestClass test_ -> run a single test class
  - _mvn -Dtest=TestClass1,TestClass2 test_ -> run multiple test classes
- Package the application with _mvn package_

## Endpoints:

Below are documented the endpoints of the API of this project. There are also some examples of possible outcomes that might happen when using the API.

* Bet:

   **Make Bet** - **POST** http://localhost:8080/casino/api/bets
   
   URL: 
   
      http://localhost:8080/casino/api/bets
      
   Response Status:
   
      200 OK
      
   Body:
   
      {
        "gameId": 1,
        "username": "John_Omega",
        "betValue": 15
      }
      
    Return:
    
      You won 5,10! 
      New balance: 1005,1
      
      OR
      
      You lost 15,00! 
      New balance: 985

* Game:

    **Get Game By Id** - **GET** http://localhost:8080/casino/api/games/{gameId}
    
    URL:  
	    
      http://localhost:8080/casino/api/games/1
      
    Response Status:
    
      200 OK
      
    Body:
    
      empty
      
    Return:
    
       {
         "id": 1,
         "name": "Test",
         "chanceOfWinning": 0.40,
         "winningMultiplier": 1.34,
         "maxBet": 100.0,
         "minBet": 10.0
       }
       
* Player:

    **Register Player** - **POST** http://localhost:8080/casino/api/players
    
    URL:
    
      http://localhost:8080/casino/api/players
      
    Response Status:
    
      201 CREATED
      
    Body:
    
      {
        "name": "John",
        "username": "John_Omega",
        "birthdate": "2002-07-11"
      }
    
    Return:
    
      {
        "id": 2,
        "name": "John",
        "username": "John_Omega",
        "birthdate": "2002-07-11T00:00:00.000+00:00",
        "balance": 0.0
      }
      
    **Get Player By Username** - **GET** http://localhost:8080/casino/api/players/{username}
    
    URL:
    
      http://localhost:8080/casino/api/players/John_Omega
      
    Response Status:
    
      200 OK
      
    Body:
    
      empty
      
    Return:
    
      {
        "id": 2,
        "name": "abc",
        "username": "abc",
        "birthdate": "2002-07-11",
        "balance": 1000.15
      }
      
    **Get Balance of Player** - **GET** http://localhost:8080/casino/api/players/balance/{username}
    
    URL:
      
      http://localhost:8080/casino/api/players/balance/John_Omega
      
    Response Status:
    
      200 OK
      
    Body:
    
      empty
      
    Return:
    
      1000.0
      
    **Make Deposit** - **POST** http://localhost:8080/casino/api/players/abc/deposit/{depositAmount}
    
    URL:
    
      http://localhost:8080/casino/api/players/abc/deposit/1000.15
      
    Response Status:
    
      200 OK
      
    Body:
    
      empty
      
    Return:
    
      none
