# Take-home assignment.

### Service Description:

Our frontend displays a weather widget that requests data from the server every 10 minutes. The average number of online users is 15,000 per hour.

### Current Solution Description:

The server-side application communicates with two third-party services:

* IP API - a service for determining geolocation by IP address
* Open Meteo - a service for obtaining weather information by geolocation.

The application is deployed in two instances.
Both instances share a single database.
* Environment (per instance):
  * CPU = 2 cores
  * RAM = 1GB
* Tech stack:
  * Java 17
  * Sping boot 3
  * PostgreSQL
  * Liquibase

### Problem:
Our Junior Developer created the server-side application, and it has some drawbacks. 
Also, this morning we discovered that many calls to Open Meteo are failing with errors.
It has become very slow, inconsistent, and unreliable. Request times are unpredictable, and it fails with 5xx errors from time to time
We don't know what the problem is, possibly even with our proxy server. 

But the DevOps team cannot help us in the next few days. We need a quick fix so that users don't suffer. The team discussed and decided to add caching for successful responses. When a request to Open Meteo fails with an error, we simply return the last saved result. At least this will reduce the number of errors.

### Conditions:
Without the DevOps team, we do not have access to the infrastructure. 

The problem must be solved in the existing environment. 

You can change the code as you like, use any libraries, and use the existing database.

### Task:
You need to perform several tasks:
1. Add caching for successful responses, read from cache when there are errors in the response.
2. Improve the existing code written by the Junior Developer.
3. Describe your opinion about the team's decision. Is it good, optimal, or bad? What decision could be better?

### How to run app
Use /docker/docker-compose.yaml to launch the database.

The application is written in JDK 17 and uses Preview Features. If you encounter the following error:
```
> Task :compileJava FAILED
error: invalid source release 17 with --enable-preview
  (preview language features are only supported for release 18)
```
Then check the following:
1. Gradle is running and using JDK 17.
2. In the project settings, the language level is set to 17 (Preview) - Pattern matching for switch.

If everything is set correctly, but you still receive the error, then pass the JDK path to the Gradle command.
```
./gradlew build -Dorg.gradle.java.home={PATH TO JDK 17}

Example: 
./gradlew test -Dorg.gradle.java.home=/Library/Java/JavaVirtualMachines/jdk-17.0.3.1.jdk/Contents/Home
```

### How to make request
1. GET http://localhost:8080/weather
2. Request should contain 2 headers: 
   * X-Forwarded-For (your external IP address)
   * X-API-KEY (auth api key, default = `M2ZjNDZmOWItMzJmMC00YzhlLWE3ZTctNDY3YzQ2YzAzZjli`)
Example:
```
curl --location 'http://localhost:8080/weather' \
--header 'X-Forwarded-For: 81.198.20.21' \
--header 'X-API-KEY: M2ZjNDZmOWItMzJmMC00YzhlLWE3ZTctNDY3YzQ2YzAzZjli'
```


---

# Solution
### Solution points 
- Adding Redis caching: to improve performance and reduce the load on APIs.
- Restructuring the project: to align with Domain-Driven Design (DDD) principles, promoting better organization and separation of concerns.
- Applying Clean Code principles: by refactoring and renaming methods, class fields, and class names to improve code readability and maintainability.
- Utilizing reactive programming: to leverage the benefits of non-blocking and event-driven architectures, Reactive code does more work with fewer resources, Project Reactor and Spring WebFlux let developers take advantage of multi-core, next-generation processors—handling potentially massive numbers of concurrent connections, With reactive processing, you can satisfy more concurrent users with fewer microservice instances,enabling better scalability and responsiveness.
- Leveraging Resilience4j: to enhance the system's resilience and fault tolerance, with features like circuit breakers, rate limiting, and retry strategies.
- Integrating OpenAPI: to document and describe APIs in a standardized and machine-readable format, making it easier for developers to understand and consume the APIs.
- Disabling the use of preview features in Gradle: as it is not recommended for production environments to ensure stability and compatibility.
- Adding Custom Exception Handling: to detect the specific Exception.

### about the Team's decision: it's good but just for Quick Solution as the cached data maybe not up-to-date and my suggest solution that we can use reactive programing and i already implement this solution as it gives us:
- the non-blocking code is efficiency.
- Reactive code does more work with fewer resources.
- Project Reactor and Spring WebFlux let developers take advantage of multi-core, next-generation processors—handling potentially massive numbers of concurrent connections.
- With reactive processing, you can satisfy more concurrent users with fewer microservice instances.

## And here is the Flowchart for the final solution:

![](D:\Flowchart.drawio.png)