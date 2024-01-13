# Hindi Character Recognition on Android using TensorFlow Lite
An end-to-end tutorial to train a Hindi character recognition model and deploy it on Android using TensorFlow Lite.


## Steps:
 
1. Clone the repository on your local machine.
 
2. Sign in to your Google account and upload the `Hindi_Character_Recognition.ipynb` notebook on Colab.

3. Run the notebook cells one-by-one by following the instructions.

4. Once the TF Lite model is downloaded, copy the `mnist.tflite` model file inside `Hindi-Character-Recognition-on-Android-using-TensorFlow-Lite/Android_App/app/src/main/assets` directory.

5. Open the project in Android Studio and let it build itself for some time.

6. Open the `DigitClassifier.kt` file, edit **Line 333** by replacing `<your_no_of_output_classes>` with the no. of output classes in your model.

7. Again, in the `DigitClassifier.kt` file, edit **Line 118 to Line 132** by setting the label names according to your custom dataset. 

8. Build the project and install it on your phone. Enjoy your own custom-built Hindi character recognition app.



## Output:

![GitHub Logo](Output.gif)

- Read the [Medium blog](https://medium.com/nerd-for-tech/hindi-character-recognition-on-android-using-tensorflow-lite-9948b428905c) for step-by-step implementation.

