import groovy.json.JsonSlurper

def inputFile = new File('C:\\Users\\Lakshmi\\IdeaProjects\\GroovyTest\\src\\main\\resources\\json\\test.json')
def outputFile = new File('C:\\Users\\Lakshmi\\IdeaProjects\\GroovyTest\\src\\main\\resources\\json\\test.csv')

// Load and parse the JSON file
def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parse(inputFile)

// Prepare CSV header
def csvHeader = ["ID", "Name", "Address", "Phone", "Internal", "External"]

// Create a new CSV file
outputFile.withWriter { writer ->
    // Write the header
    writer.writeLine(csvHeader.join(','))

    // Write each entry to the CSV
    data.each { entry ->
        def id = entry.id
        def name = entry.name
        def address = entry.address
        def phone = entry.phone

        // Format 'Internal' block
        def internal = entry.others?.internal?.collect { internalBlock ->
            "internal[${internalBlock.collect { key, value -> "${key}:${value}"}.join(';')}]"
        }?.join(';') ?: ''

        // Format 'External' block
        def external = entry.others?.external?.collect { externalBlock ->
            "[${externalBlock.collect { key, value ->"${key}:${value}"}.join(';')}]"
        }?.join(';') ?: ''

        // Replace commas with pipe symbols in 'Internal' and 'External'
        internal = internal.replace(',', '|')
        external = external.replace(',', '|')

        // Write the CSV line
        writer.writeLine([id, name, address, phone, internal, external].join(','))
    }
}

println "CSV file has been created successfully."