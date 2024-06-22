package com.serhiibaliasnyi.socketgame.rule_screen


//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScopeInstance.weight
//import androidx.compose.foundation.layout.RowScopeInstance.weight
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.serhiibaliasnyi.socketgame.ui.theme.MainActionColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderMusic(sliderPosition: MutableLongState) {
    /*Row(modifier = Modifier
        .background(Color.Red)
        //.fillMaxHeight(0.1f)
        //.weight(0.01f)
        .fillMaxWidth()) { */
        Slider(
            value = (sliderPosition.longValue / 1000).toFloat(),
           // modifier = Modifier.weight(0.1f),
            //onValueChange = { sliderPosition = it },
            onValueChange = { },
            thumb = {
                // val shape = RectangleShape
                Spacer(
                    modifier = Modifier
                        .size(2.dp)

                    //        .hoverable(interactionSource = interactionSource)
                    //        .shadow(if (enabled) 6.dp else 0.dp, shape, clip = false)
                    //        .background(Red, shape)
                )
            },

            colors = SliderDefaults.colors(
                thumbColor = MainActionColor,
                activeTrackColor = MainActionColor,
                inactiveTrackColor = White,
            ),
            // steps = 100,
            valueRange = 0f..9f

        )
        // Text(text = (currentProgress/100000).toString()+"-"+(sliderPosition.longValue/1000).toFloat())
        //  Spacer(Modifier.requiredHeight(3.dp))
    //}
}