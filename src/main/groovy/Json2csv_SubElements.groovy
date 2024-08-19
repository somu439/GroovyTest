import groovy.json.JsonSlurper

def inputFile = new File('resources/json/test.json')
def outputFile = new File('/resources/json/test_result.csv')

// Load and parse the JSON file
def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parse(inputFile)

// Prepare CSV header
def csvHeader = ["ID", "Name", "Address", "Phone", "Internal", "External"]

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

        // Format 'Internal' block (if needed, otherwise leave empty)
        def internal = ''

        // Format 'External' block to include only specific fields
        def external = entry.others?.external?.collect { externalBlock ->
            externalBlock.collect { key, value ->
                if (key == "external_sub1" && value instanceof Map) {
                    def subFields = value.findAll { subKey, subValue ->
                        subKey in ["ext_subid1", "ext_subid2"] ||
                                (subKey == "ext1" && subValue instanceof Map && subValue.findAll { ext1Key, _ ->
                                    ext1Key in ["ext1_detail1", "ext1_detail2"]
                                }) ||
                                (subKey == "ext2" && subValue instanceof Map && subValue.findAll { ext2Key, _ ->
                                    ext2Key in ["ext2_detail1", "ext2_detail2"]
                                })
                    }
                    subFields.collect { subKey, subValue ->
                        if (subKey == "ext1" || subKey == "ext2") {
                            subValue.collect { nestedKey, nestedValue ->
                                "${nestedKey}=${nestedValue}"
                            }.join('|')
                        } else if (subKey == "ext1_sub3" && subValue instanceof Map) {
                            subValue.collect { nestedKey, nestedValue ->
                                "${nestedKey}=${nestedValue}"
                            }.join('|')
                        } else {
                            "${subKey}=${subValue}"
                        }
                    }.join('|')
                }
            }.findAll { it != null }.join('|')
        }?.join('|') ?: ''

        // Extract the value for "sub_ext1_sub3" directly
        def subExt1Sub3 = entry.others?.external?.collect { externalBlock ->
            externalBlock.external_sub1?.ext1?.ext1_sub3?.sub_ext1_sub3
        }.find { it != null } ?: ''

        // Write the CSV line
        writer.writeLine([id, name, address, phone, internal, external, subExt1Sub3].join(','))
    }
}

println "CSV file has been created successfully."