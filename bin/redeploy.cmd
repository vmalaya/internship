@echo on
call mvnw -pl :struts clean package
set deployments_dir=C:\Users\valentyna.mala\jboss-eap-6.4\standalone\deployments
copy /Y struts\target\struts.war %deployments_dir%\
