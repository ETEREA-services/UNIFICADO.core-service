# unificado.core-service

[![unificado.core-service CI](https://github.com/ETEREA-services/UNIFICADO.api.rest/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/ETEREA-services/UNIFICADO.api.rest/actions/workflows/maven.yml)

![Java](https://img.shields.io/badge/Java-24-blue.svg?logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen.svg?logo=spring&logoColor=white) ![Maven](https://img.shields.io/badge/build-Maven-red.svg?logo=apache-maven&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-blue.svg?logo=mysql&logoColor=white) ![Docker](https://img.shields.io/badge/Docker-blue.svg?logo=docker&logoColor=white)

**Version:** 0.5.0

## Descripción General

`unificado.core-service` es un servicio central de API REST diseñado para gestionar entidades de negocio, cuentas y sus movimientos contables. Proporciona endpoints para operaciones CRUD sobre estas entidades y para realizar cálculos de balances.

## Tecnologías Involucradas

- **Java:** 24
- **Framework:** Spring Boot 3.5.3
- **Base de Datos:** MySQL
- **Build Tool:** Maven
- **API Documentation:** SpringDoc OpenAPI (Swagger)
- **Containerización:** Docker

## Cómo Empezar

### Prerrequisitos

- JDK 24 o superior
- Apache Maven
- Docker (opcional)

### Ejecución Local

1. Clona el repositorio:
   ```sh
   git clone <repository-url>
   ```
2. Navega al directorio del proyecto:
   ```sh
   cd unificado.core-service
   ```
3. Ejecuta la aplicación usando el wrapper de Maven:
   ```sh
   ./mvnw spring-boot:run
   ```
La aplicación estará disponible en `http://localhost:8080`.

### Ejecución con Docker

1. Construye la imagen de Docker:
   ```sh
   docker build -t unificado/core-service .
   ```
2. Ejecuta el contenedor:
   ```sh
   docker run -p 8080:8080 unificado/core-service
   ```

## Documentación de la API

Una vez que la aplicación está en ejecución, la documentación de la API de Swagger UI se puede encontrar en:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
