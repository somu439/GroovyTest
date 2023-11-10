import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

// Function to update a JSON value based on a JSON path
def updateJsonValue(String jsonString, String jsonPath, String newValue) {
    // Use a try-catch block to handle invalid JSON paths
    try {
        def slurper = new JsonSlurper()
        def json = slurper.parseText(jsonString)

        // Split the JSON path into a list of path parts
        def pathParts = jsonPath.split('\\.')

        // Navigate the original JSON structure to find the node to update
        def currentNode = json
        for (String pathPart : pathParts) {
            if (pathPart.contains('[')) {
                // If the path part contains an array index, get the array element at that index
                def index = pathPart.substring(pathPart.indexOf('[') + 1, pathPart.indexOf(']')).toInteger()
                currentNode = currentNode[pathPart.substring(0, pathPart.indexOf('['))][index]
            } else {
                // Otherwise, get the child node with the given name
                currentNode = currentNode[pathPart]
            }
        }

        // Update the value of the node
        currentNode.replace(newValue)

        // Create a new JSON builder to serialize the updated JSON structure
        def builder = new JsonBuilder(json)
        return builder.toPrettyString()
    } catch (Exception e) {
        // Return a meaningful error message if the JSON path is invalid
        return "Invalid JSON path: $jsonPath"
    }
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
def jsonPath = "data.records[1].values[2]"
String newValue = '1000000'

// Update JSON value
def updatedJsonString = updateJsonValue(jsonString, jsonPath, newValue)

println("Updated JSON string:")
println(updatedJsonString)
