# Library Management System — Week 3

A REST API-based Library Management System built with **Spring Boot** and **Java 25**.

## Features

- **Book Management**: Add, remove, and search books by title/author/ISBN
- **Member Management**: Register and remove library members
- **Issue & Return**: Issue books to members and process returns
- **Borrow Limit**: Members can borrow up to 3 books at a time
- **Availability Tracking**: Track total and available copies of each book
- **Sample Data**: Pre-loaded with 5 books and 3 members for testing

## Tech Stack

- Java 25
- Spring Boot 3.5.0
- Maven (with wrapper)

## How to Run

```bash
cd week3-library-system
.\mvnw.cmd spring-boot:run
```

Server starts at: **http://localhost:8080**

## API Endpoints

### Books

| Method   | Endpoint                  | Description          | Body                                                                 |
|----------|---------------------------|----------------------|----------------------------------------------------------------------|
| `GET`    | `/api/books`              | Get all books        | —                                                                    |
| `GET`    | `/api/books/{isbn}`       | Get book by ISBN     | —                                                                    |
| `POST`   | `/api/books`              | Add a new book       | `{"isbn":"...","title":"...","author":"...","totalCopies":3}`        |
| `DELETE` | `/api/books/{isbn}`       | Remove a book        | —                                                                    |
| `GET`    | `/api/books/search?q=...` | Search books         | —                                                                    |
| `POST`   | `/api/books/issue`        | Issue book to member | `{"isbn":"...","memberId":"..."}`                                   |
| `POST`   | `/api/books/return`       | Return a book        | `{"isbn":"...","memberId":"..."}`                                   |

### Members

| Method   | Endpoint               | Description         | Body                                              |
|----------|------------------------|---------------------|---------------------------------------------------|
| `GET`    | `/api/members`         | Get all members     | —                                                 |
| `GET`    | `/api/members/{id}`    | Get member by ID    | —                                                 |
| `POST`   | `/api/members`         | Register a member   | `{"memberId":"...","name":"...","email":"..."}`   |
| `DELETE` | `/api/members/{id}`    | Remove a member     | —                                                 |

## Testing with Postman

1. Start the server: `.\mvnw.cmd spring-boot:run`
2. Open Postman
3. Use `http://localhost:8080` as base URL
4. Set `Content-Type: application/json` for POST requests

### Example: Add a Book (POST)
```
POST http://localhost:8080/api/books
Content-Type: application/json

{
  "isbn": "978-0-07-246352-3",
  "title": "Data Structures and Algorithms",
  "author": "Narasimha Karumanchi",
  "totalCopies": 5
}
```

### Example: Issue a Book (POST)
```
POST http://localhost:8080/api/books/issue
Content-Type: application/json

{
  "isbn": "978-0-13-235088-4",
  "memberId": "M001"
}
```

### Example: Search Books (GET)
```
GET http://localhost:8080/api/books/search?q=java
```

## Project Structure

```
week3-library-system/
├── pom.xml
├── mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/com/lms/
│   │   │   ├── LmsApplication.java
│   │   │   ├── model/
│   │   │   │   ├── Book.java
│   │   │   │   └── Member.java
│   │   │   ├── dto/
│   │   │   │   ├── BookRequest.java
│   │   │   │   ├── MemberRequest.java
│   │   │   │   ├── IssueReturnRequest.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── service/
│   │   │   │   └── LibraryService.java
│   │   │   └── controller/
│   │   │       ├── BookController.java
│   │   │       └── MemberController.java
│   │   └── resources/
│   │       └── application.properties
│   └── Book.java, Member.java, Library.java, Main.java  (legacy console version)
└── README.md
```
