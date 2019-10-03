
start fresh new clean JBoss

```cmd
rmdir /s /q jboss\jboss-eap-6.4
unzip -d jboss jboss\jboss-eap-6.4.0.zip
call jboss\jboss-eap-6.4\bin\standalone.bat
```

kill if address already in use:

```cmd
jps
taskkill /f /pid xxx
```
