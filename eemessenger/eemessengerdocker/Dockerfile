FROM daggerok/jboss-eap-7.2:7.2.0-alpine
HEALTHCHECK --start-period=10s --timeout=2s --retries=99 \
        CMD wget -q --spider http://127.0.0.1:8080/eemessenger/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ADD --chown=jboss ./eemessengerdocker/jboss-eap-7.2/modules/org/postgresql ${JBOSS_HOME}/modules/org/postgresql
ADD --chown=jboss ./eemessengerdocker/jboss-eap-7.2/standalone/configuration/standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
ADD --chown=jboss ./eemessengerapp/target/*.war ${JBOSS_HOME}/standalone/deployments/eemessenger.war
