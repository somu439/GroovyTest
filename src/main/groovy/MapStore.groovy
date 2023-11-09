//@Grab('com.xlson.groovycsv:groovycsv:1.2')
//import groovyx.net.http.RESTClient
//import static groovyx.net.http.ContentType.JSON

def csvText = '''key,value
name,John
age,30
city,New York'''

// You can replace the above 'csvText' with your CSV file content or read from a file.

def mapData = [:]

csvText.readLines().drop(1).each { line ->
    def parts = line.split(',')
    if (parts.size() == 2) {
        mapData[parts[0].trim()] = parts[1].trim()
    }
}

// Retrieving values from the map
def keyToLookup = 'name'
def value = mapData[keyToLookup]
if (value != null) {
    println("Value for key '$keyToLookup': $value")
} else {
    println("Key '$keyToLookup' not found in the map.")
}
