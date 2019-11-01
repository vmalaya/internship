# eemessenger 
## Java Enterprise Edition application

```shell script
mvnw -pl :eemessengerapp package docker:build docker:start

open on http://127.0.0.1:8080/eemessenger

mvnw -pl :eemessengerapp docker:stop docker:remove clean
```

### Project contains listed below technologies:

- [JPA]
- [Hibernate]
- [Struts 2 framework] 
- [JBoss 7.2.0]
- [PostgreSQL]
- [Liquibase]
- [Docker]
- [Selenide]
- [CDI plugin]
- [Convention plugin]
- [Log4j2]

web.xml is represented by java class [WebXML] 

<!-- References -->
[JPA]:https://docs.jboss.org/hibernate/jpa/2.2/api/javax/persistence/package-summary.html
[Hibernate]:https://docs.jboss.org/hibernate/orm/5.4/quickstart/html_single/#_the_hibernate_modules_artifacts
[Struts 2 framework]: https://struts.apache.org
[JBoss 7.2.0]: https://github.com/jboss-developer/jboss-eap-quickstarts
[PostgreSQL]: https://www.postgresql.org/docs/
[Convention plugin]:https://struts.apache.org/plugins/convention/
[Liquibase]:http://www.liquibase.org/documentation/
[Docker]: https://docs.docker.com/
[Selenide]: https://selenide.org/
[CDI plugin]: https://struts.apache.org/plugins/cdi/
[Log4j2]: https://logging.apache.org/log4j/2.x/
[WebXML]: eemessengerapp/src/main/java/com/sigma/software/WebXml.java
