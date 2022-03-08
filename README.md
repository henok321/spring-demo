# spring-demo

This is a simple spring demo service. It contains different pattern and setups:

* Spring Boot
* Kafka producer and consumer
* Postgres database with spring-jdbc
* Rest controller with spring-web

## Build and run tests

```
mvn clean install
```

## Run

Start kafka and database locally in docker:

```
docker-compose up -d
```

Stop and delete all container:

```
docker-compose down
```

Start application:

```
mvn spring-boot:run
```