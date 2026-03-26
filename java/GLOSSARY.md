## Annotations

| Annotation                                            | Where      | What it does                                                                |
| ----------------------------------------------------- | ---------- | --------------------------------------------------------------------------- |
| `@SpringBootApplication`                              | Main class | Marks the entry point; bootstraps the whole app                             |
| `@RestController`                                     | Class      | Marks class as a REST controller; all methods return response body directly |
| `@RequestMapping("/path")`                            | Class      | Sets the base URL prefix for all endpoints in the class                     |
| `@GetMapping`                                         | Method     | Handles HTTP GET requests                                                   |
| `@GetMapping("/{id}")`                                | Method     | Handles GET with a path variable                                            |
| `@PostMapping`                                        | Method     | Handles HTTP POST requests                                                  |
| `@PutMapping("/{id}")`                                | Method     | Handles HTTP PUT requests                                                   |
| `@DeleteMapping("/{id}")`                             | Method     | Handles HTTP DELETE requests                                                |
| `@PathVariable`                                       | Parameter  | Binds a method parameter to a URL path segment                              |
| `@RequestBody`                                        | Parameter  | Deserialises the HTTP request body (JSON) into a Java object                |
| `@Entity`                                             | Class      | Marks class as a JPA entity — maps to a database table                      |
| `@Table(name = "...")`                                | Class      | Overrides the default table name                                            |
| `@Id`                                                 | Field      | Marks the primary key field                                                 |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | Field      | Auto-increments the id — database handles generation                        |

## Key Classes

| Class               | What it does                                                          |
| ------------------- | --------------------------------------------------------------------- |
| `ResponseEntity<T>` | Wraps a response with both a body and HTTP status code                |
| `AtomicLong`        | Thread-safe counter; use for id generation                            |
| `Optional<T>`       | Represents a value that may or may not be present; avoids null checks |

## Key Interfaces

| Interface              | What it does                                                                          |
| ---------------------- | ------------------------------------------------------------------------------------- |
| `JpaRepository<T, ID>` | Gives you `findAll`, `findById`, `save`, `deleteById`, `existsById` and more for free |

## Concepts

| Concept         | Notes                                                                                                            |
| --------------- | ---------------------------------------------------------------------------------------------------------------- |
| String equality | Never use `==` for Strings or objects. Always use `.equals()`                                                    |
| `List.of(...)`  | Creates an **immutable** list — you cannot add/remove. Use `new ArrayList<>(List.of(...))` if you need to mutate |
| Stream API      | Functional-style operations on collections: `filter`, `map`, `findFirst` etc.                                    |
| Reserved words  | Some common words like `user` are reserved in SQL — use `@Table(name = "users")` to avoid conflicts              |
