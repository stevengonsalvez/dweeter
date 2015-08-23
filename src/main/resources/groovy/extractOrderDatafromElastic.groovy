import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def slurper = new JsonSlurper()

def jsonMap= slurper.parseText(payload)

def aggregateOrders = jsonMap.hits.hits

def testOut = []
aggregateOrders.eachWithIndex{ item , index -> 
								println index 
								testOut.add(item._source)
								}

payload = JsonOutput.toJson(testOut)