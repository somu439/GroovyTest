import groovy.json.*

def jsonString = '''
{
    "context": {
        "parameters": [
            {
                "name": "stub",
                "value": {
                    "item value": "abcdefg"
                }
            },
            {
                "name": "category",
                "value": {
                    "item value": "cars"
                }
            },
            {
                "name": "year",
                "value": {
                    "item value": "2012"
                }
            },
            {
                "name": "make",
                "value": {
                    "item value": "toyota"
                }
            },
            {
                "name": "cars",
                "value": {
                    "item value": "corolla"
                }
            }
        ]
    }
}
'''

def jsonSlurper = new JsonSlurper().parseText(jsonString)
def category = jsonSlurper.context.parameters.find { it.name == "cars" }
category.value."item value" = "accord"
println new JsonBuilder(jsonSlurper).toPrettyString()
