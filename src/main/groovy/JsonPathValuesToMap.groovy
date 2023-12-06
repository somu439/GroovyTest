import groovy.json.JsonSlurper

def jsonFilePath = 'path/to/your/json/file.json'

def jsonContent = new JsonSlurper().parseText(new File(jsonFilePath).text)

def jsonMap = jsonToMap(jsonContent)

println jsonMap

def jsonToMap(json, parentPath = '', map = [:]) {
    if (json instanceof List) {
        json.eachWithIndex { element, index ->
            def currentPath = parentPath + (parentPath ? '.' : '') + "[${index}]"
            if (element instanceof Map || element instanceof List) {
                jsonToMap(element, currentPath, map)
            } else {
                map[currentPath] = element
            }
        }
    } else if (json instanceof Map) {
        json.each { key, value ->
            def currentPath = parentPath + (parentPath ? '.' : '') + key
            if (value instanceof Map || value instanceof List) {
                jsonToMap(value, currentPath, map)
            } else {
                map[currentPath] = value
            }
        }
    }
    return map
}
