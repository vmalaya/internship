# strutssql
## Test project for studying basic connection web application and databases in java

### This projects contains web-application:
- deployment on [JBoss EAP 6.4]
- Struts 2 framework
- [CDI plugin]
- [Convention plugin]
- [Flyway] database migration 
- Mysql database

Application use [JDBC] to obtain connection to [JNDI datasource] via JNDI lookup.

**At least empty beam.xml is required for successful project building!**

*JBoss datasource configuration*

jboss-eap-6.4\standalone\configuration\standalone.xml contains 

```
<datasources>
    <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="MySqlPool" enabled="true" use-java-context="true" jta="true" use-ccm="true">
     <connection-url>jdbc:mysql://${mysqlHost, env.MYSQL_HOST:127.0.0.1}:${mysqlPort, env.MYSQL_PORT:3306}/${mysqlDb, env.MYSQL_DATABASE:strutssql}</connection-url>
      <driver>mysql</driver>
         <security>
            <user-name>${mysqlUser, env.MYSQL_USER:root}</user-name>
            <password>${mysqlPassword, env.MYSQL_PASSWORD:root}</password>
         </security>
    </datasource>
    <drivers>
        <driver name="mysql" module="com.mysql">
            <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
        </driver>
    </drivers>
</datasources>
```
In modules/com exists directory mysql with mysql-connector-java-5.1.48.jar and module.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="com.mysql">
    <resources>
        <resource-root path="mysql-connector-java-5.1.48.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
        <module name="javax.servlet.api" optional="true"/>
    </dependencies>
</module>
```
*JBoss in docker configuration*

Dockerfile is located in docker/strutssql/
```
FROM daggerok/jboss-eap-6.4:6.4.21-alpine
HEALTHCHECK --timeout=1s --retries=99 \
        CMD wget -q --spider http://127.0.0.1:8080/strutssql/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ADD ./jboss/jboss-eap-6.4/modules/com/mysql ${JBOSS_HOME}/modules/com/mysql
ADD ./jboss/jboss-eap-6.4/standalone/configuration/standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
ADD ./strutssql/target/*.war ${JBOSS_HOME}/standalone/deployments/
```

docker-compose.yaml

```
version: '2.1'
networks:
  strutssqlnet:
    driver: bridge
volumes:
  mysql-etc: {}
  mysql-data: {}
services:
  mysql:
    image: healthcheck/mysql:latest
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_PASSWORD: strutssql
      MYSQL_USER: strutssql
      MYSQL_DATABASE: strutssql
    volumes:
      - mysql-data:/var/lib/mysql:rw
      - mysql-etc:/etc/mysql:rw
    ports: ['3306']
    networks: [strutssqlnet]
  jboss:
    depends_on:
      mysql:
        condition: service_healthy
    build:
      context: ../
      dockerfile: ./docker/strutssql/Dockerfile

    ports: ['8080:8080']
    environment:
      MYSQL_USER: strutssql
      MYSQL_PASSWORD: strutssql
      MYSQL_DATABASE: strutssql
      MYSQL_HOST: mysql
    networks: [strutssqlnet]
```
To run and build app was used docker-compose-maven-plugin. Use following commands:

```
./mvnw ; ./mvnw -pl :docker docker-compose:up

 open http://127.0.0.1:8080/strutssql/

./mvnw -pl :docker docker-compose:down
```
<!-- 
references
-->
[CDI plugin]: https://struts.apache.org/plugins/cdi/
[JBoss EAP 6.4]: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/6.4/html-single/installation_guide/index
[Convention plugin]: https://struts.apache.org/plugins/convention/
[Flyway]: https://flywaydb.org/documentation/
[JDBC]: http://www.javenue.info/post/java-jdbc-api
[JNDI datasource]: https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html
