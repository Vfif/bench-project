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
        "id": "8260cc36-583a-4c51-a205-2b8c70323f3f",
        "requestId": "1eb6af8f-84b6-44c3-be31-d5d562852813",
        "operation": "count-words",
        "keyWord": null,
        "text": "some long text...",
        "result": "3"
    },
    {
        "id": "222e5b7f-9d48-4967-8a2f-905fa56e5cf5",
        "requestId": "1eb6af8f-84b6-44c3-be31-d5d562852813",
        "operation": "count-keywords",
        "keyWord": "long",
        "text": "some long text...",
        "result": "1"
    },
    ....
]
```

Url - http://localhost:8080/results/1eb6af8f-84b6-44c3-be31-d5d562852813

### Response example

```
[
    {
        "id": "8260cc36-583a-4c51-a205-2b8c70323f3f",
        "requestId": "1eb6af8f-84b6-44c3-be31-d5d562852813",
        "operation": "count-words",
        "keyWord": null,
        "text": "some long text...",
        "result": "3"
    },
    {
        "id": "222e5b7f-9d48-4967-8a2f-905fa56e5cf5",
        "requestId": "1eb6af8f-84b6-44c3-be31-d5d562852813",
        "operation": "count-keywords",
        "keyWord": "long",
        "text": "some long text...",
        "result": "1"
    }
]
```

Url - http://localhost:8080/result/8260cc36-583a-4c51-a205-2b8c70323f3f

### Response example

```
{
    "id": "8260cc36-583a-4c51-a205-2b8c70323f3f",
    "requestId": "1eb6af8f-84b6-44c3-be31-d5d562852813",
    "operation": "count-words",
    "keyWord": null,
    "text": "some long text...",
    "result": "3"
}
```