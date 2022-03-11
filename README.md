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

## Get Request

Url - http://localhost:8080/results

### Response example 
```
[
    {
        "id": "aa4c95fe-af67-4f22-b9b4-5e381038433d",
        "operation": "count-words",
        "keyWord": null,
        "text": "some long text...",
        "result": "3"
    },
    {
        "id": "30025e42-105e-4339-a62c-188a0f94e6c3",
        "operation": "count-keywords",
        "keyWord": "long",
        "text": "some long text...",
        "result": "1"
    },
    ....
]
```

Url - http://localhost:8080/results/{id}

### Response example 
```
[
    {
        "id": "aa4c95fe-af67-4f22-b9b4-5e381038433d",
        "operation": "count-words",
        "keyWord": null,
        "text": "some long text...",
        "result": "3"
    }
]
```