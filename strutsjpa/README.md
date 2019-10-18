# strutsjpa
## Test project for studying basics ORM in web application

### Using technologies:
- [JBoss EAP 6.4]
- Struts 2 framework
- [CDI plugin]
- [Convention plugin]
- [Liquibase] database migration 
- [Hibernate]
- [JPA]
- [Docker]
- [Selenide]
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

Dockerfile is located in [sjdocker]
```
FROM daggerok/jboss-eap-6.4:6.4.21-alpine
HEALTHCHECK --timeout=1s --retries=99 \
        CMD wget -q --spider http://127.0.0.1:8080/strutssql/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ADD ./jboss/jboss-eap-6.4/modules/com/mysql ${JBOSS_HOME}/modules/com/mysql
ADD ./jboss/jboss-eap-6.4/standalone/configuration/standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
ADD ./strutsjpa/sjapp/target/*.war ${JBOSS_HOME}/standalone/deployments/
```

To configure application services is using docker-compose.yaml. 
docker-compose.yaml is located in [sjdocker] directory 

```
version: '2.1'
networks:
  strutsjpanet:
    driver: bridge
volumes:
  mysql-etc: {}
  mysql-data: {}
services:
  selenium-hub:
    image: selenium/hub:3.141.59-vanadium
    ports:
      - "4444:4444"
    networks: [strutsjpanet]
  chrome:
    image: selenium/node-chrome:3.141.59-vanadium
    volumes:
      - /dev/shm:/dev/shm
    depends_on:
      - selenium-hub
    environment:
      HUB_HOST: selenium-hub
    networks: [strutsjpanet]
  mysql:
    image: healthcheck/mysql:latest
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_PASSWORD: strutsjpa
      MYSQL_USER: strutsjpa
      MYSQL_DATABASE: strutsjpa
    volumes:
      - mysql-data:/var/lib/mysql:rw
      - mysql-etc:/etc/mysql:rw
    ports: ['3306']
    networks: [strutsjpanet]
  jboss:
    depends_on:
      mysql:
        condition: service_healthy
    build:
      context: ../../
      dockerfile: strutsjpa/sjdocker/Dockerfile

    ports: ['8080:8080']
    environment:
      MYSQL_USER: strutsjpa
      MYSQL_PASSWORD: strutsjpa
      MYSQL_DATABASE: strutsjpa
      MYSQL_HOST: mysql
    networks: [strutsjpanet]
  healthcheck:
    image: alpine
    depends_on:
      jboss:
        condition: service_healthy
```
To run and build app was used docker-compose-maven-plugin. Use following commands:

```
./mvnw -f strutsjpa/sjapp/pom.xml clean package ;
./mvnw -f strutsjpa/sjdocker/pom.xml docker-compose:up

 using docker open on  http://jboss:8080/strutsjpa/

./mvnw -f strutsjpa/sjdocker/pom.xml docker-compose:down
```

JPA use datasource configurations, which are locate in persistence.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence" version="1.0">
    <persistence-unit name="com.sigma.software" transaction-type="JTA">
        <jta-data-source>java:jboss/datasources/ExampleDS</jta-data-source>
        <properties>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="false"/>
            <property name="javax.persistence.jdbc.driver" value="org.mysql.jdbc.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://127.0.0.1:3306/strutsjpa"/>
            <property name="javax.persistence.jdbc.user" value="strutsjpa"/>
            <property name="javax.persistence.jdbc.password" value="strutsjpa"/>
        </properties>
    </persistence-unit>
</persistence>
```

<!-- 
references
-->
[CDI plugin]: https://struts.apache.org/plugins/cdi/
[JBoss EAP 6.4]: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/6.4/html-single/installation_guide/index
[Convention plugin]: https://struts.apache.org/plugins/convention/
[Liquibase]: http://www.liquibase.org/documentation/
[JDBC]: http://www.javenue.info/post/java-jdbc-api
[Selenide]:https://selenide.org/
[Docker]: https://docs.docker.com/
[Hibernate]:https://docs.jboss.org/hibernate/orm/5.4/quickstart/html_single/#_the_hibernate_modules_artifacts
[JPA]: https://docs.jboss.org/hibernate/jpa/2.2/api/javax/persistence/package-summary.html
[JNDI datasource]: https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html
[sjdocker]: sjdocker
