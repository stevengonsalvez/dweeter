FROM stevengonsalvez/mvnmuleelastic:2.0
MAINTAINER steven gonsalvez <steven.gonsalvez@gmail.com>

COPY target/cs-integration-1.0.4-SNAPSHOT.zip /opt/mule/apps/
#RUN mv /opt/mule/conf/wrapper.conf /opt/mule/conf/wrapper_bkp.conf
#COPY wrapper.conf /opt/mule/conf/
ENV MULE_ENCRYPTION_PASSWORD cscpassword

EXPOSE 8082
EXPOSE 9200

CMD /opt/mule/bin/mule -M-DrunEnv=prod -M-DsekoLocation=33 -M-Delk=Y -M-Dspring.profiles.active=dummy -D-Mlog.threshold.console=INFO -D-Mlog.threshold.filelog=INFO

