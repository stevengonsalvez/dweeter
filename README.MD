# Docker Run Command

```
docker run -d -p 8082:8082 -p 9200:9200  stevengonsalvez/csc:0.6 /bin/bash -c "service elasticsearch start; /opt/mule/bin/mule -D-M-DrunEnv=dev"
```

### Container Repository to use

```
tutum.co/stevengonsalvez/csc:0.5
stevengonsalvez/csc:0.6 - This is not committed
```



### To do's Container tasks

 - Commit the csc:0.6 container to tutum
 - create a private fl3x repo for the container
