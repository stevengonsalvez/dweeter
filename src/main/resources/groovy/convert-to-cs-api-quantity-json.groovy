import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def slurper = new JsonSlurper()

def jsonMap = slurper.parseText(payload).response
def oldUpdateList = jsonMap.updates;
//System.out.println(oldUpdateList);
def stockUpdateList = new java.util.ArrayList();



oldUpdateList.each{
    def stockNewUpdate = [:];
    stockNewUpdate.productSkuNumber = (it).productSkuNumber;
    stockNewUpdate.locationId = (it).locationId.toInteger();
    def operation=[:];
    operation.name=(it).operation.name;
    operation.value=(it).operation.value.toInteger();
    stockNewUpdate.operation = operation;
    stockNewUpdate.reasonCode= (it).reasonCode;
    stockNewUpdate.userNote= (it).userNote;
    stockUpdateList.add(stockNewUpdate);

}
jsonMap.updates=stockUpdateList;
def user =[:];
user.name="Steven Gonsalvez";
jsonMap.user=user;

payload = JsonOutput.toJson(jsonMap)