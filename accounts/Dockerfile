#start with a base image containing java runtime
FROM eclipse-temurin:25-jre
#info author
LABEL authors="nicolae"
# add the application jar to the image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
# excute the application
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]

#command da metterei nterminale per creare image il punto . server da folder tipo accoutns
# docker build -t usernamedocker/nameapp:s4 .
# ilsecodno 8080: serve per decide noi devolper dove far runnare quel conitner percio posso mettereq quello che mi pare
# il primo 8080:   serve per il container e il host
# docker run -p 8080:8080 namedocker/accounts:s4

#detach mode cioe non vedo la console del app
## docker run -d -p 8080:8080 namedocker/accounts:s4

## docker run -d --name accounts-8080 -p 8080:8080 namedocker/accounts:s4
## docker run -d --name accounts-8081 -p 8081:8080 namedocker/accounts:s4
# per visualizare i logs docker logs contaienrid
# e per riaprie il temrianel del app docker logs -f
# docker ps vedere cosa is running
#docker images


