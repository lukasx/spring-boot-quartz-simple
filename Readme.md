#Description
Simple spring boot project showing how to schedule Quartz jobs

## Requirements

- Java: 1.8
- Maven:3+


## Running

```
$ mvn spring-boot:run
```


###Rescheduling using cron expression
```bash
curl -i -H "Content-Type: application/json" -X PUT \
-d '{"cron":"*/5 * * * * ?"}' \
http://localhost:8080/rescheduleLogger
```

###Running immediately (outside of schedule)
```bash
curl -i -H "Content-Type: application/json" -X PUT \
-d '{"entry": "Hello world"}' \
http://localhost:8080/log
```