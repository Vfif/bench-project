# bench-project
Java 17 + Spring + RabbitMQ


## Before running

Install RabbitMQ server using docker:

for Linux
```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

More information: https://www.rabbitmq.com/download.html

## Get Request
```
http://localhost:8080/hello/{message}
```