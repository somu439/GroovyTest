def csvContent = "Name, Age, City\nJohn, 25, New York\nAlice, 30, London"

def filePath = "C:\\Users\\Lakshmi\\IdeaProjects\\GroovyTest\\src\\main\\resources\\output\\example.csv"

// Write to CSV file
new File(filePath).withWriter { writer ->
    writer.write(csvContent)
}

