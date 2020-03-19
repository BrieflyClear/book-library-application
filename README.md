# book-library-application
A Spring web application providing basic data about books from Google Books API. The sysyem is backed by REST API providing serveral endpoint listed below.
It is a project for the recruitment test in Cognifide (Poznań, Poland).

## Build info:
  - Java 13
  - Maven 3.8.1
  - Spring Boot 2.2.5
  - Lombok
  - GSon 2.8.6
  
**+Add description**

## Testing:
  - JUnit 5.5.2
  - Mockito 1.10.19
  - Maven Surefire Plugin
  
**+Add description**
  
## Design Patterns:
  - Singleton - the "JsonParser" class. It gets created multiple times in the application (always as the same object), so keeping its static instance adds performance. - It's a anti-pattern **Delete and make it a child of the Service class**
  - Builder - the "Book" class contains multiple fields. It is better for readability to not initialize them all in the constructor. Also we want the Book object to be immutable, so it is sure the client will get unchanged data.
  - Data Transfer Object (DTO) - the "AuthorRatingDTO" objects are used to transfer data of the Author and its average rating due to a HTTP GET request ("/ratings").
  - Service layer - separates the API endpoints from the data layer, establishes a set of available operations and coordinates the application’s response in each operation

## Instructions
### Building 
 To build the project use following command: `mvn clean package`
### Running 
 After building the application run following command to start it: `java -jar target/library-1.0.0.jar`
 To provide any external JSON datasource: `java -jar target/library-1.0.0.jar -Dspring-boot.run.arguments= {absolute file path}`
 
 The default JSON datasource is provided. It is "/misc/books.json". There is no need to include it when running commands.
### Testing
 To run the test, run the following command: `mvn test`


  
