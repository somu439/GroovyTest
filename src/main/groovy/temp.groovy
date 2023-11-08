//v@Grab('org.codehaus.groovy:groovy-json:3.0.0')

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

// Function to update a JSON value based on a JSON path
def updateJsonValue(String jsonString, String jsonPath, newValue) {
    def slurper = new JsonSlurper()
    def json = slurper.parseText(jsonString)

    def pathParts = jsonPath.split('\\.')

    def currentNode = json
    for (String pathPart : pathParts) {
        if (currentNode instanceof List) {
            // Extract array index if present in the pathPart
            def matcher = pathPart =~ /\[(\d+)\]/
            if (matcher) {
                def index = matcher[0][1].toInteger()
                if (index < 0 || index >= currentNode.size()) {
                    return "Invalid array index"
                }
                currentNode = currentNode[index]
            } else {
                return "Array index missing in path"
            }
        } else if (currentNode[pathPart] == null) {
            return "Path not found"
        } else {
            currentNode = currentNode[pathPart]
        }
    }

    currentNode = newValue

    def jsonBuilder = new JsonBuilder(json)
    return jsonBuilder.toPrettyString()
}

// Sample JSON
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

// JSON path and new value to update
def jsonPath1 = "data.records[0].id"
def newValue1 = 70

def jsonPath2 = "data.records[0].values[1]"
def newValue2 = 25

// Update JSON values
def updatedJson1 = updateJsonValue(jsonString, jsonPath1, newValue1)
println(updatedJson1)

def updatedJson2 = updateJsonValue(jsonString, jsonPath2, newValue2)
println(updatedJson2)
