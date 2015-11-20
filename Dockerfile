FROM stevengonsalvez/mvnmuleelastic:2.0
MAINTAINER steven gonsalvez <steven.gonsalvez@gmail.com>

COPY target/cs-integration-0.1.0-SNAPSHOT.zip /opt/mule/apps/
RUN mv /opt/mule/conf/wrapper.conf /opt/mule/conf/wrapper_bkp.conf
COPY wrapper.conf /opt/mule/conf/
ENV MULE_ENCRYPTION_PASSWORD cscpassword

EXPOSE 8082
EXPOSE 9200

CMD service elasticsearch start && /opt/mule/bin/mule -D-M-DrunEnv=dev -D-M-DsekoLocation=33 -D-M-Delk=Y

