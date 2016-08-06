import org.mule.api.transport.PropertyScope
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

def slurper = new JsonSlurper()
def idList = slurper.parseText(payload).with;

//def dateTime = new Date().format("YYYY.MM.dd")

def thangie = message.getProperty('thang',PropertyScope.SESSION );

//def staticString =  '{"index":{"_index":"dweetie", "_type":"'+ thangie +'"}}'
def staticString =  '{"update":{"_index":"dweetie", "_retry_on_conflict" : 3, "_type":"'+ thangie +'", "_id":"'
def docPre = '{ "doc" :'
def docPost = ', "doc_as_upsert" : true }'


String test;
for (java.util.HashMap item : idList) {

    if (test == null){
        test =   staticString + item.created + '"}}' + '\n' +
                docPre +
                new JsonBuilder(item).toString() +  docPost + '\n'
    }
    else
    {
        test = test + staticString + item.created + '"}}' + '\n' +
                docPre +
                new JsonBuilder(item).toString() +  docPost + '\n'
    }
}

payload = test
return payload