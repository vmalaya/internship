# eemessengertest

## local development 

_build and run application_

```shell script
mvnw -f eemessengertest/pom.xml
mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P compose-create
mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P up,app
```

## cleanup

```shell script
mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P down,app
```

## test 

```shell script
# prepare
mvnw -f eemessengertest/pom.xml
mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P compose-create
mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P up,e2e
# run tests
mvnw -f eemessengertest/eemessengerteste2e/pom.xml -P teste2e -DargLine="-Deemessenger.host=app -Dselenide.browser=chrome -Dselenide.remote=http://127.0.0.1:4444/wd/hub"
# cleanup
mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P down,e2e
```

Application functionality:
- sign up;
- sign in;
- sign out;
- send message user;
- get message from user;
- obtain chat with particular user;
 
Was used following technologies:
- [Bootstrap]
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
- [JBoss Database Login Module]

<!--references-->
[Bootstrap]: https://getbootstrap.com/
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
[JBoss Database Login Module]: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/7.0/html-single/login_module_reference/index#database_login_module
