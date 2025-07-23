# 🛠️ Service Management Application

A full-stack system combining a Spring Boot RESTful backend with an Angular frontend to manage hierarchical service data—where each service contains resources, and each resource has owners. Includes a Go-based batch client for stress testing the API.

---

## 🧩 Features

- ✅ Create, Read, Update operations for:
  - **Services**
  - **Resources** nested within services
  - **Owners** nested within resources
- 🧪 Custom exception handling
- 🌐 Angular UI integration
- 🐳 Docker + Docker Compose setup
- 🔄 GitHub Actions CI/CD pipeline
- 🧠 LRU cache support
- 🏎️ Go batch client for high-volume API load testing

---

## 📦 Tech Stack

| Layer        | Technology                    |
|--------------|-------------------------------|
| Backend      | Spring Boot 3.x               |
| Frontend     | Angular                       |
| Database     | MongoDB                       |
| Client       | Golang                        |
| Build Tool   | Maven                         |
| CI/CD        | GitHub Actions                |
| Container    | Docker, Docker Compose        |
| Documentation| OpenAPI                       |
| Logging      | SLF4J                         |
| Testing      | JUnit, Mockito, Jasmine, Karma|

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Java 17+
- Maven 3.x
- Node.js + Angular CLI
- Docker & Docker Compose

---

### 🐳 Local Setup

```bash
git clone https://github.com/SakshiMahajan899/service-resource-owner-app.git
cd service-resource-owner-app
docker-compose up
```

### Execute the Go client manually
After bringing up all services via Docker Compose.Then execute the Go client manually inside the running container:

```bash
cd service-resource-owner-app
docker exec -it go-client ./batch-client --parallel=100 --steps=10 --url=http://backend-app:8080/v1/services



