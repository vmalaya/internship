FROM daggerok/jboss-eap-6.4:6.4.21-alpine
HEALTHCHECK --timeout=1s --retries=99 \
        CMD wget -q --spider http://127.0.0.1:8080/strutsjpa/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ADD ./jboss/jboss-eap-6.4/modules/com/mysql ${JBOSS_HOME}/modules/com/mysql
ADD ./jboss/jboss-eap-6.4/standalone/configuration/standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
ADD ./strutsjpa/target/*.war ${JBOSS_HOME}/standalone/deployments/
