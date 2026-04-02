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
| `@Configuration` | Class | Marks class as a source of bean definitions |
| `@EnableWebSecurity` | Class | Enables Spring Security's web security support |
| `@Bean` | Method | Registers the method's return value as a Spring-managed bean |
| `@Service` | Class | Specialisation of `@Component` — semantically marks service layer classes |
| `@Component` | Class | Marks class as a Spring-managed bean, available for injection |
| `@EnableEurekaServer` | Main class | Turns the application into a Eureka service registry |
| `@EnableConfigServer` | Main class | Turns the application into a Spring Cloud Config Server |

## Key Classes

| Class               | What it does                                                          |
| ------------------- | --------------------------------------------------------------------- |
| `ResponseEntity<T>` | Wraps a response with both a body and HTTP status code                |
| `AtomicLong`        | Thread-safe counter; use for id generation                            |
| `Optional<T>`       | Represents a value that may or may not be present; avoids null checks |
| `RestTemplate` | Spring's synchronous HTTP client for making HTTP calls to external services |
| `ParameterizedTypeReference<T>` | Spring workaround for Java type erasure when deserializing generic types like `List<T>` |

## Key Interfaces

| Interface              | What it does                                                                          |
| ---------------------- | ------------------------------------------------------------------------------------- |
| `JpaRepository<T, ID>` | Gives you `findAll`, `findById`, `save`, `deleteById`, `existsById` and more for free |
| `UserDetails` | Spring Security interface your `User` must implement for authentication to work |
| `UserDetailsService` | Interface with one method `loadUserByUsername` — Spring calls this during authentication |

## Concepts

| Concept         | Notes                                                                                                            |
| --------------- | ---------------------------------------------------------------------------------------------------------------- |
| String equality | Never use `==` for Strings or objects. Always use `.equals()`                                                    |
| `List.of(...)`  | Creates an **immutable** list — you cannot add/remove. Use `new ArrayList<>(List.of(...))` if you need to mutate |
| Stream API      | Functional-style operations on collections: `filter`, `map`, `findFirst` etc.                                    |
| Reserved words  | Some common words like `user` are reserved in SQL — use `@Table(name = "users")` to avoid conflicts              |
| CSRF | Cross-Site Request Forgery protection — disable for stateless JWT REST APIs |
| JWT filter | Extends `OncePerRequestFilter` — intercepts every request to validate the token |
| `SecurityContextHolder` | Spring's holder for the current authenticated user — set this to authenticate a request |
| `BCryptPasswordEncoder` | Standard password hashing — never store plain text passwords |
| Spring IoC container | Manages all beans (`@Component`, `@Service`, `@Repository`, `@Bean`) — handles creation and injection |
| Type erasure | Java removes generic type parameters at runtime — `List<String>` becomes `List` at bytecode level |
| Bounded context | DDD concept — a service boundary drawn around a coherent business domain |
| Multi-module Maven project | Parent pom with `<packaging>pom</packaging>` coordinating child modules, each producing independent jars |
| Modular monolith | Single deployable unit structured with strict internal module boundaries — stepping stone before microservices |
| Spring Cloud Config | Centralised config server that serves config files from a Git repo. Services fetch config on startup instead of owning it locally |
| Config Bootstrap Phase | Spring fetches config from Config Server before the application context is fully built — Config Server must be up first |
| Spring Cloud Gateway | Reactive API gateway that routes requests to downstream services via YAML rules. Replaces hand-rolled RestTemplate proxying |
| Eureka Server | Service registry — a phone book for microservices. Services register here on startup |
| Service Discovery | Services find each other by logical name via Eureka instead of hardcoded host and port |
| Heartbeat | Periodic signal a service sends to Eureka to prove it is alive. Missed heartbeats trigger eviction |
| Client-side load balancing | The calling service holds a local registry cache and makes routing decisions itself. Traffic never flows through Eureka |
| `lb://` URI scheme | Used in Gateway routes — resolves service name via Eureka instead of a static URL |
| `register-with-eureka: false` | Prevents a service registering with Eureka. Set on the Eureka Server itself — it is the registry, not a client of it |
| `fetch-registry: false` | Prevents a service pulling the registry from Eureka. Also set on Eureka Server for the same reason |
| Spring Cloud BOM | Manages version alignment across all Spring Cloud dependencies. Declared once in parent pom, inherited by all modules |
| `optional:configserver:` | Config import prefix — allows fallback to local config if Config Server is unreachable instead of failing on startup |