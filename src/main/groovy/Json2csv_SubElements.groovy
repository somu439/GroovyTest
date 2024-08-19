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

        // Format 'Internal' block (if needed, otherwise leave empty)
        def internal = ''

        // Format 'External' block to include only specific fields
        def external = entry.others?.external?.collect { externalBlock ->
            externalBlock.collect { key, value ->
                if (key == "external_sub1" && value instanceof Map) {
                    def subFields = value.findAll { subKey, _ ->
                        subKey in ["ext_subid1", "ext_subid2", "ext1_detail1", "ext1_detail2", "ext2_detail1", "ext2_detail2", "sub_ext1_sub3"]
                    }
                    subFields.collect { subKey, subValue -> "${subKey}=${subValue}" }.join('|')
                }
            }.findAll { it != null }.join('|')
        }?.join('|') ?: ''

        // Write the CSV line
        writer.writeLine([id, name, address, phone, internal, external].join(','))
    }
}

println "CSV file has been created successfully."