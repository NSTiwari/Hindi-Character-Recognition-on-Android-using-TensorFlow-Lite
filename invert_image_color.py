import PIL.Image
import PIL.ImageOps
import glob
import os

if not "converted" in os.listdir():
	os.mkdir("converted")

lst_imgs = [i for i in glob.glob("*.jpg")]
print(lst_imgs)

for i in lst_imgs:

	img = PIL.Image.open(i)
	img = PIL.ImageOps.invert(img)
	
	img.save("converted\\"+"अ"+str(lst_imgs.index(i))+".jpg", "JPEG")


print("Done.")
os.startfile("converted")