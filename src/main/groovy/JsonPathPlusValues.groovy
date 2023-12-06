


//@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def jsonFilePath = '/Users/kavish/IdeaProjects/GroovyTest/src/main/resources/json/sample.json'
def csvFilePath = '/Users/kavish/IdeaProjects/GroovyTest/src/main/resources/json/sample.csv'

def jsonContent = new JsonSlurper().parseText(new File(jsonFilePath).text)

def csvData = jsonToCsv(jsonContent)

new File(csvFilePath).text = csvData

def jsonToCsv(json, parentPath = '', csvLines = []) {
    if (json instanceof List) {
        json.eachWithIndex { element, index ->
            def currentPath = parentPath + (parentPath ? '.' : '') + "[${index}]"
            if (element instanceof Map || element instanceof List) {
                jsonToCsv(element, currentPath, csvLines)
            } else {
                csvLines << "${currentPath},${JsonOutput.toJson(element)}"
            }
        }
    } else if (json instanceof Map) {
        json.each { key, value ->
            def currentPath = parentPath + (parentPath ? '.' : '') + key
            if (value instanceof Map || value instanceof List) {
                jsonToCsv(value, currentPath, csvLines)
            } else {
                csvLines << "${currentPath},${JsonOutput.toJson(value)}"
            }
        }
    }
    return csvLines.join('\n')
}

