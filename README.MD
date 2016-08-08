## Commands for MQ container

sudo docker build -t sapqm .
sudo docker run --env LICENSE=accept --env MQ_QMGR_NAME=C1SAPQM1 --publish 1414:1414 --name SAPQM --detach sapqm


## Commands for the mule container
```
sudo docker build -t muledweet .
```

## Broker containers 

```
docker run --name IBM_MessageBroker_1 -e LICENSE=accept -e NODENAME=dweeter -p 5414:4414 -p 7800:7800 -P --name SAPBROKER --detach iibv10image
```

### creating execution group

``` 
docker exec -it `docker ps | grep IBM_MessageBroker_1 | awk {'print $1'}` /bin/bash -c "mqsicreateexecutiongroup dweeter -e dweet"
```


### Installing scope

```
sudo curl -L git.io/scope -o /usr/local/bin/scope
sudo chmod a+x /usr/local/bin/scope
scope launch --service-token=m8bx3oxgretskumjo8ezzbgxy1rqtx14
```



### Mule container
first 

``` 
git clone https://github.com/stevengonsalvez/dweeter.git
```

```
sudo docker build -t muledweet .
sudo docker run --env THANG=tenuous-lunchroom --env ELASTIC_HOST=52.30.197.84 --env ELASTIC_PORT=9200 --name MNS_ESB -d muledweet

```

### Running elasticsearch
```
docker run -p 9200:9200 -p 9300:9300 --name mns_elastic -d elasticsearch elasticsearch -Des.node.name="TestNode"
```


#### Running Kibana
```
docker run --name mns-kibana -e ELASTICSEARCH_URL=http://localhost:9200 -p 5601:5601 -d kibana

docker run --name mns-kibana --link mns_elastic:elasticsearch -p 5601:5601 -d kibana
```
