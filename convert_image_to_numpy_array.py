from PIL import Image
import os
import numpy as np

path_to_files = "./<path_to_image_files>"    
vectorized_images = []

# Convert images to NumPy arrays to form 'MNIST-like' dataset.
for _, file in enumerate(os.listdir(path_to_files)):
    image = Image.open(path_to_files + file)
    image_array = np.array(image)
    vectorized_images.append(image_array)        
# save as DataX or any other name. But the same element name is to be used while loading it back. 
np.savez("./images.npz",DataX=vectorized_images)


# Load the converted NumPy arrays.
path = "./images.npz"
with np.load(path) as data:
    #load DataX as train_data
    train_data = data['DataX']
    print(train_data)
    print("Shape is:", train_data.shape) 