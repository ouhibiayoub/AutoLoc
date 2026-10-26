# Validation Atelier 5

## Collection Postman

Import `AutoLoc-Atelier5.postman_collection.json` in Postman. The collection runs the CRUD sequence for Agence, Client, and Vehicule and checks the HTTP status for every request.

The default `baseUrl` is `http://localhost:8081`. Start the application before running the collection.

## Run with MySQL

Start the configured MySQL database, then run the application from the project root:

```powershell
./mvnw.cmd spring-boot:run
```

## Run with an in-memory H2 database

For a disposable local demo, set the H2 connection through environment variables. H2 is included in test scope; `useTestClasspath` makes it available to the Spring Boot Maven plugin.

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:h2:mem:autoloc;DB_CLOSE_DELAY=-1'
$env:SPRING_DATASOURCE_USERNAME = 'sa'
$env:SPRING_DATASOURCE_PASSWORD = ''
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME = 'org.h2.Driver'
$env:SPRING_JPA_HIBERNATE_DDL_AUTO = 'create-drop'
./mvnw.cmd spring-boot:run '-Dspring-boot.run.useTestClasspath=true'
```

Open `http://localhost:8081/swagger-ui.html`; it redirects to Swagger UI. The OpenAPI document is available at `http://localhost:8081/v3/api-docs`.

The `captures` directory contains the Swagger UI screenshot and the Newman JSON execution report produced by the collection run.
