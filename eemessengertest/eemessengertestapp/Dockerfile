FROM daggerok/jboss-eap-7.2:7.2.0-alpine
HEALTHCHECK --retries=33 \
            --timeout=3s \
            --interval=3s \
            --start-period=3s \
        CMD wget -q --spider http://127.0.0.1:8080/eemessenger/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ENV POSTGRES_DB=eemessenger
ENV POSTGRES_USER=eemessenger
ENV POSTGRES_PASSWORD=eemessenger
ENV POSTGRES_HOST=postgresql
ENV POSTGRES_PORT=5432
ADD --chown=jboss ./jboss-eap-7.2/modules/org/postgresql ${JBOSS_HOME}/modules/org/postgresql
ADD --chown=jboss ./jboss-eap-7.2/standalone/configuration/standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
ADD --chown=jboss ./target/*.war ${JBOSS_HOME}/standalone/deployments/eemessenger.war
