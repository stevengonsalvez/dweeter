FROM stevengonsalvez/mulece

MAINTAINER steven gonsalvez <steven.gonsalvez@gmail.com>

ADD ./target/order-api* /opt/mule/apps

CMD [ "/opt/mule/bin/mule" ]

EXPOSE 8080