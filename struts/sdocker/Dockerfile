FROM daggerok/jboss-eap-6.4:6.4.21-alpine
HEALTHCHECK --timeout=1s --retries=99 \
        CMD wget -q --spider http://127.0.0.1:8080/struts/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ADD ./sapp/target/*.war ${JBOSS_HOME}/standalone/deployments/
