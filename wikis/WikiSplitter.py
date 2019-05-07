import re
fileName = input("File Name: ")
string = open(fileName,encoding="utf8").read()
new_str = re.sub('[^a-zA-Z0-9\n\./_-]', ' ', string)

newerStr = ""
badList = ['Category', 'Wikipedia', 'File', 'Template', 'Main_Page', 'Portal', 'Special', 'Help']
listy = re.findall('(?<=\/wiki\/)(.*?) ', new_str)
for i in range(len(listy)):
    if (listy[i]!=""):
        if ((listy[i] in badList)):
            newerStr+=''
        else:
            newerStr+=listy[i] + "\n"
    else:
        break;
open(fileName+'.txt', 'w').write(newerStr)


'''
fileName = "PhysicsWikipedia"

f = open(fileName, 'r')

textStr = ""
print(f.read())
'''
