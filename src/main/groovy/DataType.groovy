//@Grab('org.codehaus.groovy:groovy-json:3.0.0')

import groovy.json.JsonSlurper

// Function to get a JSON value based on a JSON path with array index numbers
def getJsonValue(String jsonString, String jsonPath) {
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

    return currentNode
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

// JSON paths to retrieve values
def jsonPath1 = "data.records[1].values[2]"
def jsonPath2 = "data.records[0].id"
def jsonPath3 = "nonexistent.path"

// Get JSON values
def jsonValue1 = getJsonValue(jsonString, jsonPath1)
def jsonValue2 = getJsonValue(jsonString, jsonPath2)
def jsonValue3 = getJsonValue(jsonString, jsonPath3)

println("Value at $jsonPath1 is: $jsonValue1")
println("Value at $jsonPath2 is: $jsonValue2")
println("Value at $jsonPath3 is: $jsonValue3")
