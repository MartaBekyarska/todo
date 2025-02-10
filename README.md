# ToDo application
* Simple Springboot web application which can be used to manage a Todo tasks list. It consists of various different endpoints like:
    * fetching all tasks:
      ```http
      GET /v1/todos 200 OK
      ```
      Response body:
      ```json
      [
        {
          "id": "sdfds",
          "title": "Some title",
          "description": "Some description"
        }
      ]
      ```
    * fetching a task by id:
      ```http
      GET /v1/todo/{id} 200 OK
      ```
      Response body:
      ```json
        {
          "id": "sddsdc",
          "title": "Some title",
          "description": "Some description"
        }
      ```
    * adding a task:
      ```http
      POST /v1/todo 200 OK
      ```
      with request body:
      ```json
      {
        "title": "Some title",
        "description": "Some description"
      }
      ```
      Response body:
      ```json
        {
          "id": "abcbsd",
          "title": "Some title",
          "description": "Some description"
        }
      ```
    * updating a task:
      ```http
      PUT /v1/todo/{id} 200 OK
      ```
      with request body:
      ```json
      {
        "title": "Some title",
        "description": "Some new description"
      }
      ```
      Response body:
      ```json
        {
          "id": "dffdsf",
          "title": "Some title",
          "description": "Some new description"
        }
      ```
    * deleting a task:
      ```http
      DELETE /v1/todo/{id} 204 NO CONTENT
      ```
  If the id is not found, it will return:
  ```http
  404 Not Found
  ```
### Pre-requisites
 * Oracle JDK/Open JDK 21 or higher

### To run the application
 * Clone the repository
 * Run the following command and the application will start on port 8080 by default
```./gradlew bootRun```

### To run all tests
 * Run the following command
```./gradlew test```

### Bruno collection (use Postman alternatively)
 * Import the todo.json collection from this repository into Bruno or Postman and run the requests to test the application


