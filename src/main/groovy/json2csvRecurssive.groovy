import groovy.json.JsonSlurper

def inputFile = new File('../resources/json/test.json')
def outputFile = new File('../resources/json/test_result.csv')

def jsonSlurper = new JsonSlurper()
def data = jsonSlurper.parse(inputFile)

def csvHeader = ["ID", "Level1_Name", "Address", "Phone", "Level2_ext_subid1", "Level3_ext_subid1", "ext1_detail1", "ext1_detail2"]

def followExtSubid1(entry, allEntries, visited = [], level = 1, maxLevels = 3) {
    def result = [:]
    result["Level${level}_Name"] = entry.name

    if (level < maxLevels) {
        def extSubid1 = entry.others?.external?.find { it.external_sub1 }?.external_sub1?.ext_subid1
        if (extSubid1 && !visited.contains(extSubid1)) {
            result["Level${level + 1}_ext_subid1"] = extSubid1
            def nextEntry = allEntries.find { it.name == extSubid1 }
            if (nextEntry) {
                visited.add(extSubid1)
                result += followExtSubid1(nextEntry, allEntries, visited, level + 1, maxLevels)
            }
        }
    }

    if (level == 1) {
        def ext1Detail1 = entry.others?.external?.find { it.external_sub2 }?.external_sub2?.ext1?.ext1_detail1
        def ext1Detail2 = entry.others?.external?.find { it.external_sub2 }?.external_sub2?.ext1?.ext1_detail2
        result["ext1_detail1"] = ext1Detail1
        result["ext1_detail2"] = ext1Detail2
    }

    return result
}

def processedData = [:]

data.each { entry ->
    if (!processedData.containsKey(entry.id)) {
        def result = followExtSubid1(entry, data)
        processedData[entry.id] = [
                id: entry.id,
                name: entry.name,
                address: entry.address,
                phone: entry.phone,
                level2: result.Level2_ext_subid1,
                level3: result.Level3_ext_subid1,
                ext1_detail1: result.ext1_detail1,
                ext1_detail2: result.ext1_detail2
        ]
    }
}

outputFile.withWriter { writer ->
    writer.writeLine(csvHeader.join(','))

    processedData.each { id, entry ->
        def line = [
                entry.id,
                entry.name,
                entry.address,
                entry.phone,
                entry.level2 ?: '',
                entry.level3 ?: '',
                entry.ext1_detail1 ?: '',
                entry.ext1_detail2 ?: ''
        ].join(',')

        writer.writeLine(line)
    }
}

println "CSV file has been created successfully."