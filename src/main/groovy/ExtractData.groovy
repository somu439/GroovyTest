import groovy.json.JsonSlurper

def inputFile = new File('test.json')
def outputFile = new File('test.csv')

def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parse(inputFile)

def nameMap = [:]

// Extract the relevant information
data.each { entry ->
    def name = entry.name
    def id = entry.id
    if (!nameMap.containsKey(name) || nameMap[name].id < id) {
        nameMap[name] = [id: id, address: entry.address, phone: entry.phone]
    }
}

// Write to CSV
outputFile.withWriter { writer ->
    writer.writeLine('ID,Name,Address,Phone')
    nameMap.each { name, info ->
        writer.writeLine("${info.id},${name},${info.address},${info.phone}")
    }
}
println 'CSV file created successfully. '
