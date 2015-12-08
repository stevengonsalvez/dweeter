# Docker Run Command

```
docker run --env MULE_ENCRYPTION_PASSWORD=<whateverpassword> -d -p 8082:8082 -p 9200:9200  stevengonsalvez/csc:1.0 /bin/bash -c "service elasticsearch start; /opt/mule/bin/mule -M-DrunEnv=dev -M-DsekoLocation=33 -M-Delk=Y"
```

### Container Repository to use

```
tutum.co/stevengonsalvez/csc:0.5
stevengonsalvez/csc:0.6 - This is not committed
```



### To do's Container tasks

 - Commit the csc:0.6 container to tutum
 - create a private fl3x repo for the container
 - 1.0 is the latest with the production changes not yet commited


### Jasypt

```
./encrypt.sh input="checking" password=<whateverpassword> algorithm=PBEWithMD5AndDES
```