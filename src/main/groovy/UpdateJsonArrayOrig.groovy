//@Grab('org.codehaus.groovy:groovy-json:3.0.0')

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

// Function to update a JSON value based on a JSON path with array index
def updateJsonValueWithArrayIndex(String jsonString, String jsonPathAndIndex, newValue) {
    def slurper = new JsonSlurper()
    def json = slurper.parseText(jsonString)

    def pathAndIndexParts = jsonPathAndIndex.split('\\.')
    def pathParts = pathAndIndexParts.take(pathAndIndexParts.size() - 1) // Extract path parts
    def arrayIndex = pathAndIndexParts.last().toInteger() // Extract array index

    def currentNode = json
    for (int i = 0; i < pathParts.size(); i++) {
        def pathPart = pathParts[i]

        if (currentNode instanceof List) {
            def index = pathPart.toInteger()
            if (index < 0 || index >= currentNode.size()) {
                return "Invalid array index"
            }
            currentNode = currentNode[index]
        } else if (i < pathParts.size() - 1 && currentNode[pathPart] != null) {
            currentNode = currentNode[pathPart]
        } else if (i == pathParts.size() - 1) {
            currentNode[pathPart][arrayIndex] = newValue
        } else {
            return "Path not found"
        }
    }

    def jsonBuilder = new JsonBuilder(json)
    return jsonBuilder.toPrettyString()
}

// Sample JSON with arrays
def jsonString = '''
{
    "data": {
        "records": [
            {
                "id": 1,
                "values": [10, 20, 30]
            },
            {
                "id": 2,
                "values": [40, 50, 60]
            }
        ]
    }
}
'''

// JSON path with array index and new value to update
def jsonPathAndIndex = "data.records.1.values.2"
def newValue = 70

// Update JSON value
def updatedJson = updateJsonValueWithArrayIndex(jsonString, jsonPathAndIndex, newValue)
println(updatedJson)
