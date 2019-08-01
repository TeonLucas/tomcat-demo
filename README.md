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
