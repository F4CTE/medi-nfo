# Medi-nfo

This app was created with to help hospital's managing their patients informations

## Development

When starting the application `docker compose up` is called and the app will connect to the contained services.
[Docker](https://www.docker.com/get-started/) must be available on the current system.

During development it is recommended to use the profile `local`. In IntelliJ `-Dspring.profiles.active=local` can be
added in the VM options of the Run Configuration after enabling this property in "Modify options".

Lombok must be supported by your IDE. For IntelliJ install the Lombok plugin and enable annotation processing

In addition to the Spring Boot application, the development server must also be started - for this
[Node.js](https://nodejs.org/) version 20 is required. Angular CLI and required dependencies must be installed once:

```
npm install -g @angular/cli
npm install
```

The development server can be started as follows:

```
ng serve
```

Your application is now accessible under `localhost:4200`.

Add code using Angular schematics with `ng generate ...`.
Generate a messages.json for translation with `ng extract-i18n –format=json`.

## Testing requirements

Testcontainers is used for running the integration tests. Due
to the reuse flag, the container will not shut down after the tests. It can be stopped manually if needed.

Frontend unit tests can be executed with `ng test`.

## Build

The application can be tested and built using the following command:

```
mvnw clean package
```

Start your application with the following command - here with the profile `production`:

```
java -Dspring.profiles.active=production -jar ./target/medi-nfo-0.0.1-SNAPSHOT.jar
```

If required, a Docker image can be created with the Spring Boot plugin. Add `SPRING_PROFILES_ACTIVE=production` as
environment variable when running the container.

```
mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=com.b3al.med/medi-nfo
```

## Further readings

* [Maven docs](https://maven.apache.org/guides/index.html)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/reference/jpa.html)
* [Learn Angular](https://angular.dev/tutorials/learn-angular)  
* [Angular CLI](https://angular.dev/tools/cli)
* [Bootstrap docs](https://getbootstrap.com/docs/5.3/getting-started/introduction/)  
