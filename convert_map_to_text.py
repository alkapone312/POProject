from pickle import FALSE, TRUE
from PIL import Image
imag = Image.open('Desktop\\mapa256x192.png',mode='r')  #path here/imggrab/anything that returns image
px = imag.load()

width , height = imag.size

data = []

for x in range(height):
    for y in range(width):
        data.append(px[y,x])
        

types = list()

def check(tocheck):
    for i in range(len(types)):
        if(tocheck==types[i]):
            return FALSE
    return TRUE

for x in range((height*width)-1):
    if(data[x]!=data[x+1] and check(data[x+1])==TRUE):
       types.append(data[x+1])


textobj = open('Desktop\\mapatext.txt' , mode='w')

def convert(x):
    for i in range(len(types)):
        if(data[x]==types[i]):
           return chr(97+i)

for x in range(width*height):
    textobj.write(convert(x))
    if(x%width==0 and x!=0):
        textobj.write('\n')


textobj.close()
imag.close()
    

#for 1st version of the code
# Black  - Cyan - DarkGray - Gray - Brown - Green - Orange - Red
# 0--------1------2----------6-------7--------5-------3-------4



