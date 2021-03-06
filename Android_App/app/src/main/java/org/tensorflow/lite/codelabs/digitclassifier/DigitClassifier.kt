/* Copyright 2019 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package org.tensorflow.lite.codelabs.digitclassifier

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import android.widget.TextView
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.tensorflow.lite.Interpreter

class DigitClassifier(private val context: Context) {
  // TODO: Add a TF Lite interpreter as a field.
  private var interpreter: Interpreter? = null
  var isInitialized = false
    private set

  /** Executor to run inference task in the background. */
  private val executorService: ExecutorService = Executors.newCachedThreadPool()

  private var inputImageWidth: Int = 0 // will be inferred from TF Lite model.
  private var inputImageHeight: Int = 0 // will be inferred from TF Lite model.
  private var modelInputSize: Int = 0 // will be inferred from TF Lite model.

  fun initialize(): Task<Void?> {
    val task = TaskCompletionSource<Void?>()
    executorService.execute {
      try {
        initializeInterpreter()
        task.setResult(null)
      } catch (e: IOException) {
        task.setException(e)
      }
    }
    return task.task
  }

  @Throws(IOException::class)
  private fun initializeInterpreter() {
    // TODO: Load the TF Lite model from file and initialize an interpreter.

    // Load the TF Lite model from asset folder and initialize TF Lite Interpreter with NNAPI enabled.
    val assetManager = context.assets
    val model = loadModelFile(assetManager, "mnist.tflite")
    val interpreter = Interpreter(model)

    // TODO: Read the model input shape from model file.

    // Read input shape from model file.
    val inputShape = interpreter.getInputTensor(0).shape()
    inputImageWidth = inputShape[1]
    inputImageHeight = inputShape[2]
    modelInputSize = FLOAT_TYPE_SIZE * inputImageWidth *
      inputImageHeight * PIXEL_SIZE

    // Finish interpreter initialization.
    this.interpreter = interpreter

    isInitialized = true
    Log.d(TAG, "Initialized TFLite interpreter.")
  }

  @Throws(IOException::class)
  private fun loadModelFile(assetManager: AssetManager, filename: String): ByteBuffer {
    val fileDescriptor = assetManager.openFd(filename)
    val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
    val fileChannel = inputStream.channel
    val startOffset = fileDescriptor.startOffset
    val declaredLength = fileDescriptor.declaredLength
    return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
  }

  private fun classify(bitmap: Bitmap): String {
    check(isInitialized) { "TF Lite Interpreter is not initialized yet." }

    // TODO: Add code to run inference with TF Lite.
    // Pre-processing: resize the input image to match the model input shape.
    val resizedImage = Bitmap.createScaledBitmap(
      bitmap,
      inputImageWidth,
      inputImageHeight,
      true
    )
    val byteBuffer = convertBitmapToByteBuffer(resizedImage)

    // Define an array to store the model output.
    val output = Array(1) { FloatArray(OUTPUT_CLASSES_COUNT) }

    // Run inference with the input data.
    interpreter?.run(byteBuffer, output)

    // Post-processing: find the digit that has the highest probability
    // and return it a human-readable string.
    val result = output[0]
    val maxIndex = result.indices.maxByOrNull { result[it] } ?: -1
    var label = "";

    if(maxIndex==0) {
      label = "???";
    }
    else if(maxIndex==1) {
      label = "???";
    }
    else if(maxIndex==2) {
      label = "???";
    }
    else if(maxIndex==3) {
      label = "???";
    }
    else if(maxIndex==4) {
      label = "???";
    }

    /*if(maxIndex==0) {
      label = "???";
    }
    else if(maxIndex==1) {
      label = "???";
    }
    else if(maxIndex==2) {
      label = "???";
    }
    else if(maxIndex==3) {
      label = "???";
    }
    else if(maxIndex==4) {
      label = "???";
    }
    else if(maxIndex==5) {
      label = "???";
    }
    else if(maxIndex==6) {
      label = "???";
    } else if(maxIndex==7) {
      label = "???";
    } else if(maxIndex==8) {
      label = "???";
    }
    else if(maxIndex==9) {
      label = "???";
    } else if(maxIndex==10) {
      label = "???";
    } else if(maxIndex==11) {
      label = "???";
    } else if(maxIndex==12) {
      label = "???";
    } else if(maxIndex==13) {
      label = "???";
    }
    else if(maxIndex==14) {
      label = "???";
    }
    else if(maxIndex==15) {
      label = "???";
    }
    else if(maxIndex==16) {
      label = "???";
    }
    else if(maxIndex==17) {
      label = "???";
    }
    else if(maxIndex==18) {
      label = "???";
    } else if(maxIndex==19) {
      label = "???";
    } else if(maxIndex==20) {
      label = "??????";
    }
    else if(maxIndex==21) {
      label = "??????";
    } else if(maxIndex==22) {
      label = "???";
    }
    else if(maxIndex==23) {
      label = "???";
    } else if(maxIndex==24) {
      label = "???";
    }
    else if(maxIndex==25) {
    label = "???";
    }
    else if(maxIndex==26) {
      label = "???";
    }
    else if(maxIndex==27) {
      label = "???";
    }
    else if(maxIndex==28) {
      label = "???";
    }
    else if(maxIndex==29) {
      label = "???";
    }else if(maxIndex==30) {
      label = "???";
    }else if(maxIndex==31) {
      label = "???";
    }else if(maxIndex==32) {
      label = "???";
    }else if(maxIndex==33) {
      label = "???";
    }else if(maxIndex==34) {
      label = "???";
    }else if(maxIndex==35) {
      label = "???";
    }else if(maxIndex==36) {
      label = "???";
    }else if(maxIndex==37) {
      label = "???";
    }else if(maxIndex==38) {
      label = "???";
    }
    else if(maxIndex==39) {
      label = "???";
    }
    else if(maxIndex==40) {
      label = "???";
    }
    else if(maxIndex==41) {
      label = "???";
    }
    else if(maxIndex==42) {
      label = "???";
    }
    else if(maxIndex==43) {
      label = "???";
    }
    else if(maxIndex==44) {
      label = "???";
    }
    else if(maxIndex==45) {
      label = "???";
    }
    else if(maxIndex==46) {
      label = "???";
    }
    else if(maxIndex==47) {
      label = "???";
    }
    else if(maxIndex==48) {
      label = "???";
    }
    else if(maxIndex==49) {
      label = "???";
    }
    else if(maxIndex==50) {
      label = "???";
    }
    else if(maxIndex==51) {
      label = "???";
    }
    else if(maxIndex==52) {
      label = "???";
    }
    else if(maxIndex==53) {
      label = "?????????";
    }
    else if(maxIndex==54) {
      label = "?????????";
    }
    else {
      label = "?????????";
    }*/

    val resultString =
      "%s"
        .format(label)

    return resultString
  }

  fun classifyAsync(bitmap: Bitmap): Task<String> {
    val task = TaskCompletionSource<String>()
    executorService.execute {
      val result = classify(bitmap)
      task.setResult(result)
    }
    return task.task
  }

  fun close() {
    executorService.execute {
      interpreter?.close()
      Log.d(TAG, "Closed TFLite interpreter.")
    }
  }

  private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
    val byteBuffer = ByteBuffer.allocateDirect(modelInputSize)
    byteBuffer.order(ByteOrder.nativeOrder())

    val pixels = IntArray(inputImageWidth * inputImageHeight)
    bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

    for (pixelValue in pixels) {
      val r = (pixelValue shr 16 and 0xFF)
      val g = (pixelValue shr 8 and 0xFF)
      val b = (pixelValue and 0xFF)

      // Convert RGB to grayscale and normalize pixel value to [0..1].
      val normalizedPixelValue = (r + g + b) / 3.0f / 255.0f
      byteBuffer.putFloat(normalizedPixelValue)
    }

    return byteBuffer
  }

  companion object {
    private const val TAG = "DigitClassifier"

    private const val FLOAT_TYPE_SIZE = 4
    private const val PIXEL_SIZE = 1

    private const val OUTPUT_CLASSES_COUNT = <your_no_of_output_classes>
  }
}
