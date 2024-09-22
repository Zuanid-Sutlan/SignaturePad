package com.devstudio.signaturepad

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devstudio.signaturepad.ui.theme.SignaturePadTheme
import com.devstudio.signaturepad.utils.Utils
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.rememberDrawController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val drawController = rememberDrawController()

            var image by remember { mutableStateOf<Bitmap?>(null) }

            var stringBitmap by remember { mutableStateOf<String?>("") }

            SignaturePadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        DrawBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            drawController = drawController,
                            backgroundColor = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                            bitmapCallback = { bitmap, e ->

                                Log.i("bitmapCallBack", "Bitmap: $bitmap")
                                Log.i("bitmapCallBack", "Exception: $e")
                                image = bitmap?.asAndroidBitmap()

                                stringBitmap = bitmap?.asAndroidBitmap()?.let { Utils.bitmapToBase64(it) }

                            }
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            Button(onClick = { drawController.reset() }) {
                                Text(text = "Clear")
                            }

                            Button(onClick = { drawController.saveBitmap() }) {
                                Text(text = "Save")
                            }
                        }




                        image?.let {
                            Text(text = "Image bitmap")
                            Image(
                                modifier = Modifier
                                    .weight(1f),
                                bitmap = it.asImageBitmap(),
                                contentDescription = "null"
                            )
                        }

                        stringBitmap?.let {
                            Text(text = "String bitmap")
                            Utils.base64ToBitmap(it)?.asImageBitmap()?.let { it1 ->
                                Image(
                                    modifier = Modifier
                                        .weight(1f),
                                    bitmap = it1,
                                    contentDescription = "null"
                                )
                            }
                        }

                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SignaturePadTheme {
        Greeting("Android")
    }
}