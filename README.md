# bench-project
## Project for text processing
Java 17 + Spring + RabbitMQ


## Before running

Install RabbitMQ server using docker:

for Linux
```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
```

More information: https://www.rabbitmq.com/download.html

## Post Request

Url - http://localhost:8080/text
```
{
    "operations" : ["count-words", "count-keywords"]
    "text" : "some long text...",
    "extraInfo" : { "keyword" : "long" }
}
```

### Fields description

- _operations_ - process text with given commands  
- _text_ - source text to be processed
- _extraInfo_ - auxiliary information for _operations_

Available commands for _operations_ field: 
- count-words - count words in _text_
- count-keywords - count how many times _keyword_ (set in _extraInfo_) occurs in _text_
- random - reorder words in text