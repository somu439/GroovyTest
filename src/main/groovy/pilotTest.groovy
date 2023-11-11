String s='data.records[0].values[0].val[1000]'
def ai= s.split('\\[')[-1].replace(']','')
def ArrayIndex = ai.isNumber()?ai:null
print(ArrayIndex)
//print x.isNumber()? x:"no"