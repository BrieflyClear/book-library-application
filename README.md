# book-library-application
A Spring web application providing basic data about books from Google Books API. The sysyem is backed by REST API providing serveral endpoint listed below.

It is a project for the recruitment test in Cognifide (Poznań, Poland).

## Build info:
  - Java 13
  - Maven 3.8.1
  - Spring Boot 2.2.5
  - Gson 2.8.6
  - Lombok - to skip methods like Getters, Setters, Equals, etc.
  - MapStruct - to map raw POJO to DTO
  - Log4J - to log to external files
  
**+Add description**

## Testing:
  - JUnit 5.5.2
  - Mockito 1.10.19
  - Rest Assured
  - Hamcrest
  - Maven Surefire Plugin - to generate tests' reports in "/target/surefire-reports" 
  
**+Add description**

## Code Quality
Built-in IDE plugin `Sonarlint` to check quality issues.
  
## Design Patterns:
  - **Builder** - the `Book` class contains multiple fields. It is better for readability to not initialize them all in the constructor. Also we want the Book object to be immutable, so it is sure the client will get unchanged data. Also `BookDTO` and `AuthorRatingDTO` classes
  - **Data Transfer Object (DTO)** - used to display data as primitive types and when we don't want the clients to see some of the data stored in the POJOs. The `AuthorRatingDTO` objects are used to transfer data of the Author and its average rating due to a HTTP GET request ("/ratings"). Also the `BookDTO` objects
  - **Service layer** - separates the API endpoints from the data layer, establishes a set of available operations and coordinates the application’s response in each operation.
  - **Immutable objects** - to secure the data during processing. Concerns the same classes as the `Builder` desingn pattern
  - **Strategy** - the algorithm responsible for calculating the author's average rating. `IAverageRatingCalculator`
  - **Singleton** - the `DataProvider` must be a singleton because it holds cached data. Initialization is done by static factory method and is lazy, so an instance won't be created until it is needed.
  - **Static factory method** - for the `DataProvider` singleton class

## Instructions
### Building 
 To build the project use following command: `mvn clean package`
### Running 
 After building the application run following command to start it: `java -jar target/library-1.0.0.jar`
 
 To provide any external JSON datasource: `java -jar target/library-1.0.0.jar -Dspring-boot.run.arguments= {absolute file path}`
 
 The default JSON datasource is provided. It is "/misc/books.json". There is no need to include it when running commands. When multiple arguments are provided, only the last one is set as the JSON source.
### Testing
 To run the test, run the following command: `mvn test`
 
### API Endpoints
  - /api
    - /books
      - /isbn/{isbn}
      - /category/{category}
    - /rating
    - /google
      - /search/{value}
      - /search/{value}/startIndex/{index}
      - /search/{value}/limit/{maxResults}
      - /search/{value}/startIndex/{index}/limit/{maxResults}

### Dashboard (fullstack taks)

