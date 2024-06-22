package com.serhiibaliasnyi.socketgame.rule_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiibaliasnyi.socketgame.ui.theme.GreenMain
import com.serhiibaliasnyi.socketgame.ui.theme.MainActionColor
import com.serhiibaliasnyi.socketgame.ui.theme.Purple40
import com.serhiibaliasnyi.socketgame.ui.theme.irishGroverFontFamily


@Composable
fun ToggleMode( toggleState:MutableState<Int>,quantityOfButtons:MutableState<Int>  ) {

    Box(modifier = Modifier
        .width(70.dp)
        .height(70.dp)
        //.size(70.dp)
        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        // .clip(CutCornerShape(bottomEnd = 30.dp))
        .background(GreenMain)
        .clickable {
            toggleState.value =2
            quantityOfButtons.value = 4
            Log.d("rul", "state=0")
        }
    ){
        //var color=if(toggleState.value==2) MainActionColor else White
        Text(
            text = "B",
            textAlign = TextAlign.Center,
            //fontFamily = FontFamily.Serif,
            fontFamily = irishGroverFontFamily,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = if(toggleState.value==2) MainActionColor else White,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .padding(15.dp, 10.dp)
                //   .background(color = Yellow)
                .wrapContentHeight(align = Alignment.CenterVertically),
        )
    }
    Box(modifier = Modifier
        .width(70.dp)
        .height(70.dp)
        //.size(70.dp)
        //.clip(RoundedCornerShape(topStart = 30.dp, topEnd=30.dp))
        // .clip(CutCornerShape(bottomEnd = 30.dp))
        .background(Purple40)
        .clickable {
            toggleState.value = 1
            quantityOfButtons.value = 3
            Log.d("rul", "state=1")
        }
    ){
        //var color=if(toggleState.value==1) MainActionColor else White
        Text(
            text = "S",
            textAlign = TextAlign.Center,
            //fontFamily = FontFamily.Serif,
            fontFamily = irishGroverFontFamily,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = if(toggleState.value==1) MainActionColor else White,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .padding(15.dp, 10.dp)
                //   .background(color = Yellow)
                .wrapContentHeight(align = Alignment.CenterVertically),
        )
    }
    Box(modifier = Modifier
        .width(70.dp)
        .height(70.dp)
        .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
        .background(Color.Red)
        .clickable {
            toggleState.value = 0
            quantityOfButtons.value = 3
            Log.d("rul", "state=0")
        }
    ){
        //var color=if(toggleState.value==0) MainActionColor else White
        Text(
            text = "T",
            textAlign = TextAlign.Center,
            //fontFamily = FontFamily.Serif,
            fontFamily = irishGroverFontFamily,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = if(toggleState.value==0) MainActionColor else White,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .padding(15.dp, 10.dp)
                //   .background(color = Yellow)
                .wrapContentHeight(align = Alignment.CenterVertically),
        )
    }
}