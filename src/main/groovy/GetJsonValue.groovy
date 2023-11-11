import groovy.json.JsonSlurper

// Function to get a JSON value based on a JSON path
def getJsonValue(String jsonString, String jsonPath) {
    // Use a try-catch block to handle invalid JSON paths
    try {
        def slurper = new JsonSlurper()
        def json = slurper.parseText(jsonString)

        def pathParts = jsonPath.split('\\.')

        def currentNode = json
        for (String pathPart : pathParts) {
            if (pathPart.contains('[')) {
                def index = pathPart.substring(pathPart.indexOf('[') + 1, pathPart.indexOf(']'))
                currentNode = currentNode[pathPart.substring(0, pathPart.indexOf('['))][index.toInteger()]
            } else {
                currentNode = currentNode[pathPart]
            }
        }

        return currentNode
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
                "values": [
                {
                "index": 1,
                "val": [10, 20, 30]
            }]
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
def jsonPath1 = "data.records[0].values[0].val[0]"
def jsonPath2 = "data.records[0].id"
def jsonPath3 = "nonexistent.path"

// Get JSON values
def jsonValue1 = getJsonValue(jsonString, jsonPath1)
def jsonValue2 = getJsonValue(jsonString, jsonPath2)
def jsonValue3 = getJsonValue(jsonString, jsonPath3)

println("Value at $jsonPath1 is: $jsonValue1")
println("Value at $jsonPath2 is: $jsonValue2")
println("Value at $jsonPath3 is: $jsonValue3")


def csvContent = "$jsonValue1 : $jsonValue1\n$jsonValue2 : $jsonValue2\n$jsonValue3 : $jsonValue3\\n "

def filePath = "C:\\Users\\Lakshmi\\IdeaProjects\\GroovyTest\\src\\main\\resources\\output\\example.csv"

// Write to CSV file
new File(filePath).withWriter { writer ->
    writer.write(csvContent)
    writer.close()
}

