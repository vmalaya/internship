# strutssql
## Test project for studying basic connection web application and databases in java

### This projects contains web-application:
- deployment on [JBoss EAP 6.4]
- Struts 2 framework 

### Step-by-step
- Copy the existing app with struts + jboss
- Create a solution for saving input messages into in-memory storage 
- Implement H2 database  (inject JNDI DataSource to store the message in database)
- Add a module into JBoss. Replace embedded database with MySQL in standalone.xml.
- Save message into database
- Dockerize it 
<!-- 
references
-->

[JBoss EAP 6.4]: https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/6.4/html-single/installation_guide/index