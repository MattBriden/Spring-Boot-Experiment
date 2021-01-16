# Spring Boot Experiment
This project serves as a sandbox to experiment with different Spring Boot features.

## Build
Build the jar and docker image.
```
Spring-Boot-Experiment $ mvn clean install
Spring-Boot-Experiment $ docker build -t mattbriden/spring-experiment-api .
```

## Run
There is a Docker environment included with this project to run the API with a Postgres DB initialized. Use the below commands to bring up the Docker environment.
```
Spring-Boot-Experiment/util $ docker-compose run start_db
Spring-Boot-Experiment/util $ docker-compose up
```

## Use
Use the following cURL command to call the API.
```
Spring-Boot-Experiment $ curl localhost:8090/api/data?companyId=1 -i
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Sat, 16 Jan 2021 00:49:07 GMT

[{"id":"6efb7d84-a83e-45d3-a29e-5a3e13ec80a2","companyId":1},{"id":"86dc2aee-b586-4705-94c6-2a3ef2abd059","companyId":1}]
```
Currently this project contains a CRON job that will add a new data entry to the db every minute.

## Shutdown
Use the following command to shut down the docker environment.
```
Spring-Boot-Experiment/util $ docker-compose down
```
