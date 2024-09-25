 package com.example.note.components

import android.annotation.SuppressLint
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("SuspiciousIndentation")
@Composable
fun NoteInputText(modifier: Modifier=Modifier,
                 Text:String,
                 label:String, //label comes above the text field
                 maxLine:Int=5,
                 onTextChange :(String)->Unit,
                 onImeAction:()->Unit={}) {

    /*
  //making rainbow effect for text field
  val gradientColors = listOf(
  Color.Red,
  Color(0xFFFFA500), // Orange
  Color.Yellow,
  Color.Green,
  Color.Blue,
  Color(0xFF4B0082), // Indigo
  Color(0xFF8A2BE2)  // Violet
  )
  val brush = remember {
  Brush.linearGradient(
  colors =gradientColors
  )
  }
   */

    //i want to hide keyboard after user presses done so


      val keyboardController = LocalSoftwareKeyboardController.current

        //creating text field

        TextField(
            value = Text,
            onValueChange = onTextChange,
            colors = TextFieldDefaults.colors(Color.Unspecified),
            maxLines = maxLine,
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onAny = {
                    onImeAction()
                    keyboardController?.hide()
                }),
            modifier = modifier
            //textStyle = TextStyle(brush=brush),
        )
    }

@Composable
fun button(
    modifier:Modifier=Modifier,
    text:String,
    onclick:()->Unit,
    enabled:Boolean=true
) {
    Button(onClick = onclick,//or onClick={onclick.invoke()}
        shape =ButtonDefaults.shape ,
        enabled = enabled,
        modifier = modifier,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 10.dp)
        ) {
        //what you want inside button ? i want a text
        Text(text=text, style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        ) )
    }

}

 @Composable
 fun Description(modifier: Modifier=Modifier,
                   Text:String,
                   label:String, //label comes above the text field
                   maxLine:Int=5,
                   onTextChange :(String)->Unit,
                   onImeAction:()->Unit={}) {

     /*
   //making rainbow effect for text field
   val gradientColors = listOf(
   Color.Red,
   Color(0xFFFFA500), // Orange
   Color.Yellow,
   Color.Green,
   Color.Blue,
   Color(0xFF4B0082), // Indigo
   Color(0xFF8A2BE2)  // Violet
   )
   val brush = remember {
   Brush.linearGradient(
   colors =gradientColors
   )
   }
    */

     //i want to hide keyboard after user presses done so


     //creating text field

     TextField(
         value = Text,
         onValueChange = onTextChange,
         colors = TextFieldDefaults.colors(Color.Unspecified),
         maxLines = maxLine,
         label = { Text(text = label) },
         keyboardOptions = KeyboardOptions.Default.copy(
             imeAction = ImeAction.Default
         ),
         keyboardActions = KeyboardActions(
             onAny = {
                 onImeAction()
             }),
         modifier = modifier
         //textStyle = TextStyle(brush=brush),
     )
 }