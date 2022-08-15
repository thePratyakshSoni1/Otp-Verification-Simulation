package com.example.otpverify

import android.graphics.fonts.FontFamily
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily.Companion.SansSerif
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import com.example.otpverify.ui.theme.OtpVerifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            window.statusBarColor = Color(0xFF350099).toArgb()
            val code = remember { mutableStateOf("") }
            val showSuccessDialog = remember {
                mutableStateOf(false)
            }

            Box(modifier = Modifier.fillMaxSize()) {

                OtpTextFieldScreen( window, onVerifyClick = {
                    showSuccessDialog.value = true
                })

                if(showSuccessDialog.value){
                    SuccessLoginDialog(isSuccess = false,codeTxt = code, showSuccessDialog)
                }
            }
        }
    }

}

@Composable
fun SuccessLoginDialog(isSuccess:Boolean, codeTxt:MutableState<String>,showDialog:MutableState<Boolean>){

    Dialog(onDismissRequest = {
        codeTxt.value = ""
        showDialog.value = false
    },
    ) {

        Card(
            Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.7f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(Modifier.fillMaxSize()){

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Successfull login",
                        modifier = Modifier.width(120.dp),
                        colorFilter = ColorFilter.tint(Color.Green),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.height(10.dp))
                    Text(text = "Login Successfull :)", fontSize = 18.sp, fontFamily = SansSerif, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(18.dp))

                    Button(onClick = { showDialog.value = false }, shape = RoundedCornerShape(100.dp), colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green
                    )) {
                        Text(
                            text = "DONE",
                            color=Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier=Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }

                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp), contentAlignment = Alignment.TopEnd){

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier= Modifier
                            .clip(CircleShape)
                            .clickable { showDialog.value = false }
                            .padding(6.dp)
                    )
                }
            }
        }

    }

}