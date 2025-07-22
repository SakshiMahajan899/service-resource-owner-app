[//]: # (# ✈️ Flight Data Management Application)

[//]: # ()
[//]: # (A Spring Boot-based RESTful application for managing flight records, with search and integration capabilities via the external CrazySupplier API.)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🧩 Features)

[//]: # ()
[//]: # (- ✅ CRUD for flight data &#40;Airline, Fare, Time, etc.&#41;)

[//]: # (- 🔎 Advanced search with filtering &#40;origin, destination, airline, time&#41;)

[//]: # (- 🌐 Real-time integration with **CrazySupplier API**)

[//]: # (- 🧪 Validation and error handling)

[//]: # (- 📊 Health checks via Spring Boot Actuator)

[//]: # (- 🐳 Docker + Docker Compose)

[//]: # (- 🛠️ GitHub Actions CI/CD Pipeline)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 📦 Tech Stack)

[//]: # ()
[//]: # (| Layer        | Technology              |)

[//]: # (|--------------|--------------------------|)

[//]: # (| Language     | Java 17+                 |)

[//]: # (| Framework    | Spring Boot              |)

[//]: # (| REST         | Spring Web               |)

[//]: # (| Persistence  | Spring Data JPA + PostgreSQL |)

[//]: # (| Validation   | Hibernate Validator &#40;`@Valid`&#41; |)

[//]: # (| External API | CrazySupplier &#40;via WebClient&#41; |)

[//]: # (| Monitoring   | Spring Boot Actuator     |)

[//]: # (| Build Tool   | Maven                    |)

[//]: # (| CI/CD        | GitHub Actions           |)

[//]: # (| Container    | Docker, Docker Compose   |)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🚀 Getting Started)

[//]: # ()
[//]: # (### 🔧 Prerequisites)

[//]: # ()
[//]: # (- Java 17+)

[//]: # (- Maven 3.x)

[//]: # (- Docker & Docker Compose)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (###  🐳 Run with Docker Compose)

[//]: # ()
[//]: # (- Clone the repository:)

[//]: # (    - git clone https://github.com/SakshiMahajan899/flight-data-app.git)

[//]: # (    - cd flight-data-app)

[//]: # (- Docker compose file that can be run with **docker-compose up** which should start up a application)

[//]: # (  at port 8080 &#40;including dependencies like a database&#41;)

[//]: # ()
[//]: # (### Spring Security)

[//]: # ()
[//]: # (- This application uses the Spring Security for authentication)

[//]: # (- Use **Basic Auth** inside Authorization and pass **Username** - user and **Password** - password)

[//]: # ()
[//]: # (  )
[//]: # (## Testing and Validation)

[//]: # ()
[//]: # (### Unit Tests &#40;UT&#41; )

[//]: # ()
[//]: # (    - As part of testing strategy, i have ensured that all primary use cases are covered by unit test. )

[//]: # (    - This approach guarantees comprehensive validation and reliability of the system.)

[//]: # ()
[//]: # (## Logging and Monitoring)

[//]: # ()
[//]: # (- Proper logging is done to log requests, responses, and errors.)

[//]: # (- Used Spring Boot Actuator for monitoring the health of application in production , exposed **/actuator/health** and *)

[//]: # (  */actuator/metrics** endpoints for real-time monitoring)

[//]: # ()
[//]: # (## Continuous Integration and Deployment)

[//]: # ()
[//]: # (- Within the .github/workflows/ directory, you'll find the ci-cd.yml file, which is used to)

[//]: # (  build the code & deploy the application image to dockerHub.)

[//]: # (- All the Unit and Integration Test Cases are automatically triggered once pipeline execute.)

[//]: # ()
[//]: # (    )
[//]: # (## Custom Exceptions for Better Error Handling)

[//]: # ()
[//]: # ( - Instead of generic exceptions, I have specific exceptions for better debugging.)

[//]: # ()
[//]: # ()
