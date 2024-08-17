import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def inputFile = new File('src\\main\\resources\\json\\test.json')
def outputFile = new File('src\\main\\resources\\json\\test.csv')

// Load and parse the JSON file
def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parse(inputFile)

// Prepare CSV header
def csvHeader = ["ID", "Name", "Address", "Phone", "Others", "Internal", "External"]

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

        // Convert 'Others' block to a JSON string
        def others = JsonOutput.toJson(entry.others)

        // Extract and concatenate key-value pairs from the 'Internal' block
        def internal = entry.others?.internal?.collect { internalBlock ->
            internalBlock.collect { key, value ->
                "${key}:${value}"
            }.join(';')
        }?.join(';') ?: ''

        // Extract and concatenate key-value pairs from the 'External' block
        def external = entry.others?.external?.collect { externalBlock ->
            externalBlock.collect { key, value ->
                "${key}:${value}"
            }.join(';')
        }?.join(';') ?: ''

        // Write the CSV line
        writer.writeLine([id, name, address, phone, others, internal, external].join(','))
    }
}

println "CSV file has been created successfully."
