#FROM openjdk:8u222-jdk-stretch
#ENV JBOSS_EAP_BASE_URL=https://github.com/daggerok/jboss/releases/download/eap
#ENV JBOSS_ARCHIVE=jboss-eap-6.4.0.zip
#RUN wget ${JBOSS_EAP_BASE_URL}/${JBOSS_ARCHIVE} -O /tmp/${JBOSS_ARCHIVE} \
# && unzip -d /tmp/ /tmp/${JBOSS_ARCHIVE} \
#ENTRYPOINT ["/bin/bash", "-c", "/tmp/jboss-eap-6.4/bin/standalone.sh"]
#CMD /bin/bash
#EXPOSE 8080
#COPY ./struts/target/*.war /tmp/jboss-eap-6.4/standalone/deployments/
FROM daggerok/jboss-eap-6.4:6.4.21-alpine
HEALTHCHECK --timeout=1s --retries=99 \
        CMD wget -q --spider http://127.0.0.1:8080/struts/ \
         || exit 1
ENTRYPOINT standalone.sh -b 0.0.0.0
ADD ./struts/target/*.war ${JBOSS_HOME}/standalone/deployments/
