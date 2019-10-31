```shell script
To up application local 
Build and up application:
./mvnw -f eemessengertest/pom.xml
./mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P compose-create
./mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P up,app

Down application
./mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P down,app

To run test 
Build and up application:
./mvnw -f eemessengertest/pom.xml
./mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P compose-create
./mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P up,e2e
Run test:
./mvnw -f eemessengertest/eemessengerteste2e/pom.xml -P teste2e -DargLine="-Deemessenger.host=app -Dselenide.browser=chrome -Dselenide.remote=http://127.0.0.1:4444/wd/hub"
Down application
./mvnw -f eemessengertest/eemessengertestcompose/pom.xml -P down,e2e
```
