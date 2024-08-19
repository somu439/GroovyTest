import groovy.json.JsonSlurper

def inputFile = new File('src/main/resources/json/test.json')
def outputFile = new File('src/main/resources/json/test_result.csv')

// Load and parse the JSON file
def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parse(inputFile)

// Prepare CSV header
def csvHeader = ["ID", "Name", "Address", "Phone", "External"]

// Create a new CSV file
outputFile.withWriter { writer ->
    // Write the header
    writer.writeLine(csvHeader.join(','))

    // Group entries by name and select the one with the largest ID
    def groupedData = data.groupBy { it.name }.collectEntries { name, entries ->
        [name, entries.max { it.id }]
    }

    // Write each selected entry to the CSV
    groupedData.each { name, entry ->
        def id = entry.id
        def address = entry.address
        def phone = entry.phone

        // Format 'External' block to include only specific fields
        def external = entry.others?.external?.collect { externalBlock ->
            [
                    "external_sub3=${externalBlock.external_sub3}",
                    "ext_subid1=${externalBlock.external_sub1?.ext_subid1}",
                    "ext1_detail2=${externalBlock.external_sub2?.ext1?.ext1_detail2}",
                    "ext1_sub2=[sub_ext1_sub2:${externalBlock.external_sub2?.ext1?.ext1_sub2?.sub_ext1_sub2}]",
                    "ext2_detail1=${externalBlock.external_sub2?.ext2?.ext2_detail1}"
            ].findAll { it != null }.join('|')
        }?.join(';') ?: ''

        // Write the CSV line
        writer.writeLine([id, name, address, phone, external].join(','))
    }
}

println "CSV file has been created successfully."