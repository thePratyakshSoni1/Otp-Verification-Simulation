package com.example.otpverify

import android.app.ActionBar
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.otpverify.ui.theme.otpCode

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpTextFieldScreen(window:Window, onVerifyClick:(String)->Unit) {

    val codeTxtFieldTxt = remember { mutableStateOf("") }
    val textFieldRequester = remember { FocusRequester() }
    WindowCompat.setDecorFitsSystemWindows(window,false)

    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color.Blue, Color(0xFF9500FF)),
                    startX = -600f,
                    endX = 600f
                )
            )
            .imePadding()
    ) {

        ScreenTitle()
        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 33.dp, topEnd = 33.dp))
                .background(Color.White)
                .padding(end = 18.dp, start = 18.dp, top = 38.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val keyboardController = LocalSoftwareKeyboardController.current
            val focusManager = LocalFocusManager.current

            Box (modifier=Modifier.weight(1f), contentAlignment = Alignment.Center){

                TextField(
                    value = codeTxtFieldTxt.value,
                    onValueChange = {
                        if (it.length <= 4) {
                            codeTxtFieldTxt.value = it
                            if(it.length == 4){
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        } else {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                        Log.d("CODE", "Now: ${codeTxtFieldTxt.value}")
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .size(0.dp)
                        .focusRequester(textFieldRequester)
                        .alpha(0f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    )
                )

                Column{
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Otp is send to 6300000000",
                        fontFamily = FontFamily.SansSerif,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(18.dp))

                    OtpTextField(codeText = codeTxtFieldTxt) {
                        focusManager.clearFocus()
                        textFieldRequester.requestFocus()
                    }

                    Spacer(Modifier.height(12.dp))
                    BottomText()

                }


            }

            BottomActionButtons { onVerifyClick(codeTxtFieldTxt.value) }
            Spacer(Modifier.height(8.dp))

        }

    }



}

@Composable
private fun ColumnScope.BottomActionButtons(onVerifyClick: () -> Unit){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .weight(1f),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color(0xFF9300FC),
                disabledBackgroundColor = Color(0x009300FC)
            ),
            enabled = true,
            modifier = Modifier
                .weight(1f)
                .border(2.5.dp, Color(0xFF9500FF), shape = RoundedCornerShape(100.dp)),
            shape = RoundedCornerShape(100.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            )
        ) {
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Cancel ?"
            )
            Spacer(Modifier.width(6.dp))
            Text(
                "Cancel",
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 6.dp, top = 6.dp, bottom = 6.dp)
            )

        }

        Spacer(Modifier.width(6.dp))

        Button(
            onClick = {
                onVerifyClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF9500FF),
                contentColor = Color.White,
                disabledBackgroundColor = Color(0x7A9300FC)
            ),
            enabled = true,
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.weight(1f),

            ) {

            Text(
                "Verify",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            )

        }


    }


}

@Composable
private fun BottomText(){

    val bottomText = buildAnnotatedString {

        append("Didn't get code ? ")
        this.addStyle(
            SpanStyle(color = Color.LightGray, fontFamily = FontFamily.SansSerif),
            0,
            17
        )
        append("Resend Otp")
        this.addStyle(
            SpanStyle(color = Color.Blue, fontFamily = FontFamily.SansSerif),
            18,
            28
        )

    }

    val context = LocalContext.current
    Text(
        text = bottomText, textAlign = TextAlign.Center, modifier = Modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                otpCode.generateNewCode()
                OtpCodesNotificationService(context).showNotification(otpCode.code!!.toInt())
            }
            .fillMaxWidth()
    )

}

@Composable
private fun ScreenTitle(){

    Box(
        Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {

        val title = buildAnnotatedString {

            append("OTP ")
            this.addStyle(
                SpanStyle(color = Color.White, fontFamily = FontFamily.SansSerif),
                0,
                4
            )
            append("Simulation")
            this.addStyle(
                SpanStyle(color = Color.Yellow, fontFamily = FontFamily.SansSerif),
                4,
                14
            )

        }


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(
                "Don't share this code with anyone.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

        }
    }
}

@Composable
private fun OtpTextField(codeText: MutableState<String>, onOtpFieldClick:()->Unit) {


    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
        .fillMaxWidth()
        .clickable(
            interactionSource = MutableInteractionSource(),
            indication = null
        ) {
            onOtpFieldClick()
        }){
        OtpTextFieldBox(
            text = if (codeText.value.isNotEmpty() ) codeText.value[0].toString() else "")

        OtpTextFieldBox(
            text = if (codeText.value.isNotEmpty() && codeText.value.length >= 2) codeText.value[1].toString() else "")

        OtpTextFieldBox(
            text = if (codeText.value.isNotEmpty() && codeText.value.length >= 3) codeText.value[2].toString() else "")

        OtpTextFieldBox(
            text = if (codeText.value.isNotEmpty() && codeText.value.length >= 4) codeText.value[3].toString() else "")

    }

}

@Composable
private fun OtpTextFieldBox(text:String) {

    Box(
        modifier = Modifier
            .width(50.dp)
            .height(TextFieldDefaults.MinHeight)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF1F1F1)),
            contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

    }

}
