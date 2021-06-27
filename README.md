# scratch-bling


SOLUTION
---------------------

Please note that you may have to edit the .\src\main\resources\application.yml file in order
to properly set up PostgreSQL DB access.

Tech Stack
- Java 11
- Spring Boot
- Maven
- PostgreSQL
- Swagger
- Jackson
- Jacoco

In order to run the app locally it is possible to use mvn commands (PostgreSQL or H2 DB options)
   
For PostgreSQL:

    mvn spring-boot:run -Dspring-boot.run.profiles=local

For H2:

    mvn spring-boot:run -Dspring-boot.run.profiles=test

It is possible to check the health of the service by running the command below:

    curl -X POST http://localhost:8080/message/echo -H "accept: application/json" -H "cache-control: no-cache" -H "content-type: application/json" -d "{\"message\":\"mensaje de prueba\"}"

For getting a code coverage report:

1) Run:
    
    mvn clean package

2) Open the report file below:

   .\target\site\jacoco\index.html

To get API information and test all the calls, open this url in a browser:

    http://localhost:8080/swagger-ui.html

To use postman to test the project, please import the scratch-bling.postman_collection.json file to postman

In order to use AWS EC2 instance, please use the URL below:

	ec2-3-143-223-80.us-east-2.compute.amazonaws.com

	Ex: http://ec2-3-143-223-80.us-east-2.compute.amazonaws.com:8080/swagger-ui.html
	
