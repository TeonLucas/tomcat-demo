# tomcat-demo
Tomcat Docker image with sample app and New Relic monitoring agent

## How to build tomcat-java-agent image
Pull tomcat jre8 image, add monitoring agent and config script
```sh
mvn clean install
docker-compose build
```

## How to run tomcat-demo container
```sh
docker-compose up
```

You can then browse to

* [localhost:8080](http://localhost:8080) to see the Tomcat main page
* [localhost:8080/demo](http://localhost:8080/demo) to see the demo app index page
* [localhost:8080/demo/session](http://localhost:8080/demo/session) to get a user session token
* [localhost:8080/demo/count](http://localhost:8080/demo/count) to increment the count variable

