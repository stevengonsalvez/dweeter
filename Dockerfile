FROM stevengonsalvez/mulece
MAINTAINER steven gonsalvez <steven.gonsalvez@gmail.com>

COPY target/order-api-0.1.0-SNAPSHOT.zip /opt/mule/apps/

EXPOSE 8080

CMD [ "/opt/mule/bin/mule" ]

