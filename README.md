# IP range filter - the challenge

### Description

As a Developer I want to see all the ip ranges for selectable regions from AWS ip ranges in order to create ip filter on
security groups.

### Acceptance Criteria:

- A Spring Boot Application is created

- The application has a REST controller and lists the expected data

- The datasource is public and readable from here: https://ip-ranges.amazonaws.com/ip-ranges.json

- The REST controller takes URL query parameter like "?region=EU" or "?region=US" or "?region=ALL" to filter the data (
  valid regions are EU, US, AP, CN, SA, AF, CA)

- The data is presented as MIME type text/plain

- Each value is shown as single line in the output

- The data (from AWS ip-ranges.json) is not stored

- The code should be stored in github

- github users stefankuerten1234, sergey-kolodiev should have read access to the code

### Hints / Remarks:

- Remember to write unit tests for the application.

- Splitting your work in more than one commit is appreciated.

- There is no expectation of creating a security group in AWS

- A pipeline in GitHub Actions is not required but would be a plus.

- Putting the app into a docker container is not required but would be plus.

## Usage
You can build and run the tests with
```
./gradlew build
```
If you want to see this running, you can build a docker image and run the application locally.
It is as easy as
```
./gradlew jibDockerBuild 

docker run -d -p 8081:8081 challenge:0.0.1-SNAPSHOT

curl localhost:8081/health
```
and you will get a nice greeting.

Now, things are getting serious. Let's face the challenge! Try for instance
```
curl localhost:8081?region=US
```
and enjoy.

That's it. Don't forget to clean up running containers and unnecessary images to keep your computer clean ;-).