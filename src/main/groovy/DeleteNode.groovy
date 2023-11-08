import groovy.json.JsonSlurper

//def str = '{"id":"12345678","name":"Sharon","email":"sharon\u0040example.com"}'
String str = new File('' +
        '/Users/kavish/Documents/SpringBoot/restAPI/src/main/resources/json/sample.json').getText('UTF-8')
def slurper = new JsonSlurper().parseText(str)

assert slurper.medications[0].aceInhibitors[0].name == 'lisinopril1'
//assert slurper.name  == 'Sharon'