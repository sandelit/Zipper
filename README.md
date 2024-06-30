# Zipper

## Getting Started

Clone the repository:
```bash
git clone https://github.com/sandelit/Zipper
```
build the project:
```bash
./mvnw clean install
```

run the application:
```bash
./mvnw spring-boot:run
```

run the tests:
```bash
./mvnw clean test
```


## Improvement ideas
### Adding multiple archiving methods (eg. 7zip)
To support multiple archiving methods I would create an interface eg. Archiver that has a method archiveFiles
Then I would create different classes for each archiving technique that extends the Archiver interface.
Finally, adjust the controller and service to use the correct archiver depending on user input.

### Facing a significant increase in request count
To handle a significant increase in request count I would implement asynchronous request handling as well as deploying the app behind a load balancer to distribute the requests across multiple instances of the application

### Allow 1GB file size
To handle larger files I would use a file streaming solution to handle the archiving without loading the entire files into memory at once. 