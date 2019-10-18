# struts
## Test project for studying basics of web application in java

### This projects contains web-application:
- deployment on [JBoss EAP 6.4]
- Struts 2 framework 
- [Docker]
- [Selenide]
*JBoss in docker configuration*

Dockerfile is located in [sdocker]
```
FROM daggerok/jboss-eap-6.4:6.4.21-alpine
HEALTHCHECK --timeout=1s --retries=99 \
        CMD wget -q --spider http://127.0.0.1:8080/struts/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ADD ./sapp/target/*.war ${JBOSS_HOME}/standalone/deployments/
```

To configure application services is using docker-compose.yaml. 
docker-compose.yaml is located in [sdocker] directory 

```
version: '2.1'
networks:
  strutsnet:
    driver: bridge
volumes:
  mysql-etc: {}
  mysql-data: {}
services:
  selenium-hub:
    image: selenium/hub:3.141.59-vanadium
    ports:
      - "4444:4444"
    networks: [strutsnet]
  chrome:
    image: selenium/node-chrome:3.141.59-vanadium
    volumes:
      - /dev/shm:/dev/shm
    depends_on:
      - selenium-hub
    environment:
      HUB_HOST: selenium-hub
    networks: [strutsnet]
  jboss:
    build:
      context: ../
      dockerfile: sdocker/Dockerfile
    ports: ['8080:8080']
    networks: [strutsnet]
  healthcheck:
    image: alpine
    depends_on:
      jboss:
        condition: service_healthy
```
To run and build app was used docker-compose-maven-plugin. Use following commands:

```
./mvnw -f struts/sapp/pom.xml clean package ;
./mvnw -f struts/sdocker/pom.xml docker-compose:up

 using docker open on  http://jboss:8080/struts/

./mvnw -f struts/sdocker/pom.xml docker-compose:down
```

<!-- 
references
-->

[JBoss EAP 6.4]: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/6.4/html-single/installation_guide/index
[sdocker]: sdocker
[Selenide]:https://selenide.org/
[Docker]: https://docs.docker.com/
