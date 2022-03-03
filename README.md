# bench-project
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
    "operations" : "count-words.count-key-word",
    "text" : "some long text...",
    "keyWord" : "key"
}
```

### Fields description

- _operations_ - process text with given commands  
- _text_ - source text to be processed
- _keyWord_ - need for count-key-word command (read below)

Available commands for _operations_ field: 
- count-words - count words in _text_
- count-key-word - count how many times _ketWord_ occurs in _text_  

Note: _operations_ field value must be a list of commands, delimited by dots  
More information: https://www.rabbitmq.com/tutorials/tutorial-five-python.html