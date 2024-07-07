package com.serhiibaliasnyi.tunewheel.rule_screen

import android.graphics.BitmapFactory
import android.media.SoundPool
import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.matchParentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
//import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.data.EmptyGroup.box
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.constraintlayout.widget.ConstraintLayout
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
//import com.airbnb.lottie.LottieComposition
//import com.airbnb.lottie.compose.LottieAnimation
//import com.airbnb.lottie.compose.LottieClipSpec
import com.serhiibaliasnyi.tunewheel.MainActivity
import com.serhiibaliasnyi.tunewheel.R
import com.serhiibaliasnyi.tunewheel.network.SocketViewModel
import com.serhiibaliasnyi.tunewheel.network.Dashboard
import com.serhiibaliasnyi.tunewheel.network.SocketManager.connectionError
//import com.serhiibaliasnyi.luckywheel.ui.theme.GreenBackground
//import com.serhiibaliasnyi.luckywheel.ui.theme.GreenBg
//import com.serhiibaliasnyi.luckywheel.ui.theme.GreenMain
import com.serhiibaliasnyi.tunewheel.ui.theme.irishGroverFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Collections
import kotlin.time.Duration.Companion.seconds



@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun RuleScreen(sound: SoundPool?, player: ExoPlayer, playList: List<MainActivity.Music>,socketViewModel: SocketViewModel = viewModel()) {
   // val isConnected by socketViewModel.isConnected.collectAsState()
    var isConnected by remember { mutableStateOf(false) }
    var connectionError by remember { mutableStateOf<String?>(null) }
  //  val message by socketViewModel.message.collectAsState()
   // val connectionError by socketViewModel.connectionError.collectAsState()
   // val response by socketViewModel.response.collectAsState()
   // val parsedMessage by socketViewModel.message.collectAsState()
    val message by socketViewModel.message.collectAsState()
    //  var message by viewModel.message.collectAsState()
   //  var message by remember { mutableStateOf("") }

    var gameState by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit) {
        socketViewModel.connect()
        while (true) {
            isConnected = socketViewModel.isConnected.value
            connectionError = socketViewModel.connectionError.value
            delay(1000) // 5 seconds delay between checks
        }
    }


    val quantityOfSectors:Int=40

    var quantityOfButtons = remember {
        mutableStateOf(3)
    }
    val quantityOfWinCount:Int =5

    val musicDurationMs=10000;

    val volumeCoin=1f
    val infiniteTransition = rememberInfiniteTransition()
    val coroutineScope = rememberCoroutineScope()
/*
    val heartbeatAnimation by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )


    val flashAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        //durationMillis = 2000,
       // animationSpec = tween(
       //     durationMillis = 2000,
       //     easing = LinearOutSlowInEasing
       // )
        animationSpec = infiniteRepeatable(
          //  durationMillis = 2000,
            tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )


    )
*/
    val imageVisible = remember { mutableStateListOf(false, false, false, false) }
    //val borderColour =remember {mutableStateListOf(0, 0, 0)}

    //var currentProgress by remember { mutableStateOf(0f) }
    //var loading by remember { mutableStateOf(false) }
  //  val scope = rememberCoroutineScope() // Create a coroutine scope
  //  val taskVeriable = remember {mutableStateOf("Hello World")}

    var toggleState = remember {
        mutableStateOf(0)
    }
    var imageLittleCoin:MutableState<Int> = remember {
        mutableStateOf(0)
    }

    var visibleCount by remember{
        mutableStateOf(1f)
    }
    var visibleWinImage by remember{
        mutableStateOf(0f)
    }

    var playListShuffle:MutableList<MainActivity.Music> = remember{
        mutableStateListOf<MainActivity.Music>()
    }
    var  listUtilSongs:MutableList<MainActivity.Music> = remember{
        mutableStateListOf<MainActivity.Music>()

    }

    var songId:Int by remember{
        mutableStateOf(-1)
    }

    var winCount = remember{
        mutableStateOf(0)
    }
   // val gameStateScreen = remember{
   //     mutableStateOf(0)
   // }

    var totalWinCount = remember{
        mutableStateOf(0)
    }

    var isButtonStartEnabled:Boolean by remember{
        mutableStateOf(true)
    }
    var isButtonsEnabled:Boolean by remember{
        mutableStateOf(false)
    }

    var winVisible:Boolean by remember{
        mutableStateOf(false)
    }
    var currentValue by remember { mutableStateOf(0L) }
    var sliderPosition = remember {
        mutableLongStateOf(0)
    }


    var isPlaying by remember { mutableStateOf(false) }

    val numberOfTrack = remember {
        mutableStateOf(-1)
    }



   // val playingSongIndex = remember {
   //     mutableIntStateOf(0)
   // }

    LaunchedEffect(numberOfTrack.value) {
       // Log.d("counter", "Launch1")
   //     playingSongIndex.intValue = numberOfTrack.value - 1
        //  player.seekTo(numberOfTrack.value-1, 0)
    }

    LaunchedEffect(Unit) {
      //   initSongs(playListShuffle,quantytyOfSectors,playList,player )
    /*
        playListShuffle.clear()
        getRandomElements(quantytyOfSectors,playList).forEach {
           playListShuffle.add(it)
       }

        Log.d("rul","Launch="+ playListShuffle.toList())

        playListShuffle.forEach {
            val path = "android.resource://" + "com.serhiibaliasnyi.luckywheel" + "/" + it.music
            val mediaItem = MediaItem.fromUri(Uri.parse(path))
            player.addMediaItem(mediaItem)
            list.add(it.name)

        }
        Log.d("rul","Launch2="+ playListShuffle.toList())
        Log.d("rul", "Launch2s="+list)
        player.prepare()
        */
    }


    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying_: Boolean) {
                isPlaying = isPlaying_
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
        }
    }
    if (isPlaying) {
        LaunchedEffect(Unit) {
            while (true) {
                currentValue = player.currentPosition
              //  Log.d("rul", currentValue.toString())
                if(currentValue>musicDurationMs){
                    player.pause()
                    Log.d("rul", "pause="+currentValue.toString())
                    currentValue =0;
                    sliderPosition.longValue=0
                    isButtonStartEnabled=true
                    isButtonsEnabled=false

                    if(winCount.value>0) winCount.value--
                    //alphaCoin1 = 1f
                    imageLittleCoin.value=R.drawable.fire_coin2
                    for(x in 0 .. quantityOfButtons.value-1){
                        imageVisible.set(x, true)
                    }
                    sound?.play(4, volumeCoin, volumeCoin, 0, 0, 1F)
                    if(toggleState.value==2) {
                        coroutineScope.launch {
                            for (n in 1..2) {
                                delay(1000)
                            }
                            listUtilSongs.clear()
                        }
                    }

                }
                delay(1.seconds/10 )
                Log.d("rul", "play="+currentValue.toString())
                // delay(1000 )
               // currentPosition.longValue = currentValue
                sliderPosition.longValue = currentValue.toLong()
            }
        }
    }

   /* var isPlayingLottie by remember {
        mutableStateOf(false)
    }
    val animSpec= LottieClipSpec.Progress(
        0f,
        0.8f
    ) */
    var rotationValue by remember {
        mutableStateOf(0f)
    }

    var flashValue by remember {
        mutableStateOf(0f)
    }
    var number by remember{
          mutableStateOf(0)
    }


    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearOutSlowInEasing
        ),
        finishedListener = {
        /*     number=((360f-(it%360))/(360f/quantityOfSectors)).toInt()+1
             if(number>quantityOfSectors) number=quantityOfSectors
             Log.d("rul2","rotationValue="+rotationValue+" it="+ it+ " number="+number+" Before song="+ playListShuffle.toList().toString())

             var song:MainActivity.Music=playListShuffle.get(number-1)
             songId=song.id
           //  listUtilSongs=getUtilSongs(song, playList)
            listUtilSongs.clear()
             getUtilSongs( song, playList).forEach {
                 listUtilSongs.add(it)
             }
                Log.d("rul2","Song="+ song.name)
                */

            /*   loading = true
            scope.launch {
                loadProgress {loading,  progress ->
                    currentProgress = progress as Float
                }

           */
            if (player.isPlaying) {
                player.pause()
            } else {

                isButtonsEnabled=true
             //555   alphaButtons=1f
                player.seekTo(number-1, C.TIME_UNSET);
                player.setPlayWhenReady(true);
                player.play()
              //  Log.d("rul2","Song=2"+ song.name)
            }

        }
    )


    val color by infiniteTransition.animateColor(
        initialValue = Red,
        targetValue = Color(0xff800000), // Dark Red
        animationSpec = infiniteRepeatable(
            // Linearly interpolate between initialValue and targetValue every 1000ms.
            animation = tween(1000, easing = LinearEasing),
            // Once [TargetValue] is reached, starts the next iteration in reverse (i.e. from
            // TargetValue to InitialValue). Then again from InitialValue to TargetValue. This
            // [RepeatMode] ensures that the animation value is *always continuous*.
            repeatMode = RepeatMode.Reverse
        )
    )

    if(message.message=="Spin"){

        if (winCount.value == quantityOfWinCount) {
            visibleCount = 0f
            winCount.value = 0
        }
        sliderPosition.longValue=0
        visibleCount = 1f
        visibleWinImage = 0f
        flashValue = 0f
        winVisible = false
        listUtilSongs.clear()

        // for (x in 0..quantityOfButtons.value - 1) {
        for (x in 0..3) {
            imageVisible.set(x, false)
        }
        //  for (x in 0..quantityOfButtons.value - 1) {
        //      borderColour.set(x, 0)
        //  }
        // alphaCoin1 = 0f
        //  buttonTextStart = ""
        //  alphaStartButton = alphaDisabled
        isButtonsEnabled = false
        //555   alphaButtons = alphaDisabled
        //555    alphaRulette = 1f
        isButtonStartEnabled = false
        songId = -1

      //999  initSongs(playListShuffle, quantityOfSectors, playList, player)
        //Log.d("rul","playListShuffleButton="+playListShuffle)
        //  isPlayingLottie = false
        val inputStr=message.extra
        val inputNumbers = remember { inputStr.split(",").map { it.toFloat() }.toTypedArray() }
        rotationValue=inputNumbers[0]
        number=inputNumbers[1].toInt()
        socketViewModel.clearMessage()
        Log.d("spin","SpinReceived="+message.extra+" rotationValue="+rotationValue)
        //   Log.d("rul", "angle="+(angle%360).toString() +" rotationValue "+rotationValue.toString())
        sound?.play(1, 1F, 1F, 0, 0, 1F)

    }


    /*   var isHeartBeating by remember { mutableStateOf(true) }
       val heartScale:Float by animateFloatAsState(
           animationSpec = tween(
               durationMillis = 4000,
               easing = LinearOutSlowInEasing
           ),

         //  targetValue = if (isHeartBeating) 1.2f else 1f,
           targetValue =  1.2f,
                   finishedListener = {
                       visibleWinImage=0f
                       isHeartBeating=false
                       Log.d("rul","heartScaleStop")
           }
       )
   */
    if(winCount.value==quantityOfWinCount){
        winCount.value=0
        winVisible=true
        visibleWinImage = 1f
       // flashValue = 1f
        sound?.play(2, 1F, 1F, 0, 0, 1F)
        coroutineScope.launch {
            for(n in 1..3){
                delay(1000)
            }
            winVisible = false
            visibleWinImage=0f
            for (x in 0..quantityOfButtons.value - 1) {
                imageVisible.set(x, false)
            }
            totalWinCount.value++
            listUtilSongs.clear()
        }
       // totalWinCount.value++
    }
    Image(painter = painterResource(id = R.drawable.bg6),
        contentDescription = "bg",
        modifier= Modifier
            .fillMaxSize()
            .alpha(0.6f),
        contentScale = ContentScale.FillBounds)
    Row(
        modifier= Modifier
            .fillMaxSize()
            .padding(2.dp)
         //   .background(color = GreenMain)
    ) {

        Column(
            modifier = Modifier
                // .background(Black)
                .fillMaxHeight()
                .fillMaxWidth(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally
           // horizontalArrangement = Arrangement.Center
        ) {
            //top Row
            Row(modifier = Modifier
               //  .background(Blue)
                .padding(0.dp)
                .weight(0.1f)
                //.fillMaxHeight(0.1f)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
                Dashboard(isConnected, connectionError, gameState = gameState,
                    onGameStateChange = { newState -> gameState = newState }, socketViewModel)
               // gameStateScreen.value = updatedGameStateScreen.value
              //  gameStateScreen=Dashboard(socketViewModel = socketViewModel)
         /*       val connectionState = ConnectionStatus(viewModel = socketViewModel)
            Text( modifier=Modifier
                //.background(Yellow)
                .weight(0.25f),
              //  .fillMaxWidth(0.25f),
                text = "Player 1",
                textAlign = TextAlign.Left,
                fontFamily = irishGroverFontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = White,
            )

            /*  Row(modifier=Modifier
                 // .background(White)
                  .weight(0.5f),
                  //.fillMaxWidth(0.68f),
                 horizontalArrangement = Arrangement.Center
                  ) { */
                  if (totalWinCount.value > 0) {
                      val formattedNumber = String.format("%02d", totalWinCount.value)
                      Text(
                          text = formattedNumber,
                          fontFamily = irishGroverFontFamily,
                          fontSize = 24.sp,
                          fontWeight = FontWeight.Bold,
                          color = White,
                          //  style = MaterialTheme.typography.bodyLarge
                      )
                  }
             // }
                  /* for (x in 1..totalWinCount.value) {
                      Image(
                          painter = painterResource(id = R.drawable.crown),
                          contentDescription = "small crowns",
                          modifier = Modifier
                              .padding(0.dp, 0.dp, 0.dp, 0.dp)
                              //.fillMaxSize()
                              // .width(200.dp)
                              .height(140.dp)
                              //  .weight(0.2f)
                              .alpha(1f)
                      )
                  } */

               Row(modifier=Modifier
                 //  .background(Red)
                   .weight(0.25f),){
                   Text(text="")
               } */

            }
            //Second Row
            Row(modifier = Modifier
               //  .background(Yellow)
                .weight(0.8f)
                //.fillMaxHeight()
                .fillMaxWidth()) {
                Column( //first column
                    modifier = Modifier
                        .fillMaxHeight()
                        //.weight(0.1f)
                        .fillMaxWidth(0.1f)
                      //  .background(Color.Red)
                    //horizontalAlignment = Alignment.CenterHorizontally
                    // horizontalArrangement = Arrangement.Center
                ) {

                    Column(
                        modifier = Modifier
                            //  .background(color = Yellow)
                            .fillMaxHeight(),
                        //.fillMaxWidth()
                        //   .width(100.dp),
                        // .weight(1f),
                        // horizontalArrangement = Arrangement.spacedBy(-30.dp)
                        verticalArrangement = Arrangement.Top
                    ) {
                         Text(text="8- ${gameState}",color = White  )
                        for (x in 1..winCount.value) {
                            Image(

                                painter = painterResource(id = R.drawable.coin3),
                                contentDescription = "coin",
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 0.dp, 0.dp)
                                    // .width(200.dp)
                                    .height(60.dp)
                                    //  .weight(0.2f)
                                    .alpha(1f)
                            )
                        }
                    }
                    //}


                } //end of first column


                  //start second column
                    Box(
                        // interactionSource = remember { NoRippleInteractionSource() },
                        modifier = Modifier
                            //.background(Green)
                            .weight(1f)
                            .fillMaxSize()
                            .clickable {
                                if (!isButtonStartEnabled) return@clickable
                                if (winVisible == true) return@clickable
                                if (winCount.value == quantityOfWinCount) {
                                    visibleCount = 0f
                                    winCount.value = 0
                                }
                                sliderPosition.longValue=0
                                visibleCount = 1f
                                visibleWinImage = 0f
                                flashValue = 0f
                                winVisible = false
                                listUtilSongs.clear()

                                // for (x in 0..quantityOfButtons.value - 1) {
                                for (x in 0..3) {
                                    imageVisible.set(x, false)
                                }
                                //  for (x in 0..quantityOfButtons.value - 1) {
                                //      borderColour.set(x, 0)
                                //  }
                                // alphaCoin1 = 0f
                                //  buttonTextStart = ""
                                //  alphaStartButton = alphaDisabled
                                isButtonsEnabled = false
                                //555   alphaButtons = alphaDisabled
                                //555    alphaRulette = 1f
                                isButtonStartEnabled = false
                                songId = -1

                               // initSongs(playListShuffle, quantityOfSectors, playList, player)
                                //Log.d("rul","playListShuffleButton="+playListShuffle)
                                //  isPlayingLottie = false
                                if(gameState==1) {
                                    val rotationValue0 = ((0..360)
                                        .random()
                                        .toFloat() + 720) + angle

                                 /*   number=((360f-(rotationValue0%360))/(360f/quantityOfSectors)).toInt()+1
                                    if(number>quantityOfSectors) number=quantityOfSectors
                                    Log.d("rul2","rotationValue="+rotationValue+ " number="+number+" Before song="+ playListShuffle.toList().toString())
                                    initSongs(playListShuffle, quantityOfSectors, playList, player)
                                    var song:MainActivity.Music=playListShuffle.get(number-1)
                                    songId=song.id
                                    //  listUtilSongs=getUtilSongs(song, playList)
                                    listUtilSongs.clear()
                                    getUtilSongs( song, playList).forEach {
                                        listUtilSongs.add(it)
                                    } */
                                    val randomString = generateRandomNumbersAsString(1, playList.size, 5, ", ")

                                    socketViewModel.sendMessage("Spin", rotationValue0.toString()+","+randomString)
                                    socketViewModel.clearMessage()
                                }else if(gameState==0) {
                                    rotationValue = ((0..360)
                                        .random()
                                        .toFloat() + 720) + angle

                                    //   Log.d("rul", "angle="+(angle%360).toString() +" rotationValue "+rotationValue.toString())
                                    sound?.play(1, 1F, 1F, 0, 0, 1F)
                                }
                            }
                    ) {

                        Image(
                            //painter= painterResource(id = R.drawable.lucky_wheel_bg),
                            painter = painterResource(id = R.drawable.external_rul23),
                            contentDescription = "lucky wheel",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp)
                            //  .rotate(angle)
                            //555  .alpha(alphaRulette)
                        )
                        Image(
                            //painter= painterResource(id = R.drawable.lucky_wheel_bg),
                            painter = painterResource(id = R.drawable.internal_rul9),
                            contentDescription = "arrow",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .rotate(angle)
                            //555    .alpha(alphaRulette)
                        )
                        if (isButtonStartEnabled && visibleWinImage == 0f) {
                            Image(
                                painter = painterResource(id = R.drawable.btn_spin4),
                                contentDescription = "arrow",
                                colorFilter = ColorFilter.tint(color),
                                //tint=color,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp)
                                //  .alpha(alphaStartButton)
                                //    .scale(heartbeatAnimation)
                                //    .background(Color.Cyan.copy(flashAnimation))
                                //   .rotate(angle)
                            )
                        }
                        Row() {

                            if (visibleWinImage == 1f) {
                                //    flashValue=1f
                                Image(
                                    //painter= painterResource(id = R.drawable.lucky_wheel_bg),
                                    painter = painterResource(id = R.drawable.crown),
                                    contentDescription = "crown",

                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp)
                                        .alpha(visibleWinImage)
                                        //   .alpha(flashState)
                                        //   .scale(heartbeatAnimation)
                                        .scale(1.4f),
                                    // .background(Color.Cyan.copy(flashAnimation))
                                    //   .rotate(angle)
                                )
                                // if (this.transition.currentState == this.transition.targetState){
                                //  Log.d("rul","Ok!!!!!!!!!!")
                                //   winVisible=false
                                //  visibleWinImage=0f
                                //  }
                            }
                        }
                    }

            //Box
            //third column
                Column(
                    modifier = Modifier
                        //  .background(Magenta)
                        .padding(0.dp, 0.dp, 0.dp, 20.dp)
                        .fillMaxHeight()
                        .fillMaxWidth(0.1f)
                        .alpha(0.8f),
                    verticalArrangement = Arrangement.Center
                    //horizontalAlignment = Alignment.CenterHorizontally
                    // horizontalArrangement = Arrangement.Center
                ) {
                 //ToggleMode(toggleState, quantityOfButtons)

                }

            }// bottom Row
            //ToggleModeRow(toggleState , quantityOfButtons)
            Row(modifier = Modifier
                 //.background(White)
                //.fillMaxHeight(0.1f)
                .weight(0.09f)
                .alpha(0.8f)
                .fillMaxWidth(),
                Arrangement.Center
                ){
                //Text(text = "Text")
                ToggleModeRow(toggleState, quantityOfButtons, listUtilSongs, imageVisible, isButtonStartEnabled)
            }
//========================================================
            /*
            LinearProgressIndicator(
              //  currentProgress,
                (sliderPosition.longValue/100000).toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
                color = MainActionColor,
                trackColor = White

            )
         */
            Row(modifier = Modifier
                //.background(White)
                //.fillMaxHeight(0.1f)
                .weight(0.01f)
                .fillMaxWidth()) {
               // Text(text = "Text")
              /*  Slider(
                    value = (sliderPosition.longValue / 1000).toFloat(),
                    modifier = Modifier.weight(0.1f),
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
            */
           SliderMusic(sliderPosition)
           }


        } //Column
//========================================================
        Column(
            modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)
            // .fillMaxHeight()
            // .fillMaxWidth(0.6f)
        ) {
            for (x in 0..2) {

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .weight(0.33f)
                        .fillMaxSize()
                        .clickable {
                            //onClick: ()->Unit
                            if (!isButtonsEnabled) return@clickable
                            isButtonStartEnabled = true
                            isButtonsEnabled = false

                            choiceSong(
                                x, songId, volumeCoin, sound,
                                imageLittleCoin, winCount, listUtilSongs,
                                imageVisible,
                                sliderPosition, player
                            )

                           // coroutineScope.launch {
                           //     for (n in 1..1) {
                           //         delay(1000)
                           //     }
                           //  listUtilSongs.clear()
                           // }

                        },

                ) {


               //     if(toggleState.value ==0) {
                        var cloud: Int = R.drawable.button0
                        if (x == 1) cloud = R.drawable.button2
                        else if (x == 2) cloud = R.drawable.button4

                        Image(
                            //painter= painterResource(id = R.drawable.lucky_wheel_bg),
                            painter = painterResource(cloud),
                            contentDescription = "count",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)
                            //  .rotate(angle)
                            //  .alpha(alphaRulette)
                        )

                    if(toggleState.value ==1) {
                        var buttonImage: String = ""
                        if (!listUtilSongs.isEmpty()) {
                            buttonImage = listUtilSongs.get(x).cover
                            AssetImage(buttonImage.toString())
                        }
                    }
                    var buttonText = ""
                    if (!listUtilSongs.isEmpty() && toggleState.value ==0) {
                        buttonText = listUtilSongs.get(x).name
                    }
                    if(toggleState.value ==0 || toggleState.value ==1) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AnimatedVisibility(
                                visible = imageVisible.get(x),
                                enter = fadeIn(animationSpec = tween(1000)),
                                exit = fadeOut(animationSpec = tween(1))
                            ) {
                                Image(
                                    alignment = Alignment.Center,
                                    painter = painterResource(id = imageLittleCoin.value),
                                    contentDescription = "coin",
                                    modifier = Modifier
                                        .padding(15.dp, 0.dp, 0.dp, 0.dp)
                                        .width(50.dp)
                                        .height(50.dp)
                                    //   .alpha(alphaCoin1)

                                )
                            }

                            Text(
                                text = buttonText,
                                textAlign = TextAlign.Center,
                                //fontFamily = FontFamily.Serif,
                                fontFamily = irishGroverFontFamily,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth()
                                    .padding(15.dp, 5.dp)
                                    //   .background(color = Yellow)
                                    .wrapContentHeight(align = Alignment.CenterVertically),
                            )
                        }
                    }
                }

            }
            //--------------------------------------------

        }

    }

    Row(
    modifier= Modifier
        .fillMaxSize()
        .padding(1.dp)

       // .background(Yellow)
    ) {
       // for (x in 0..quantityOfButtons - 1) {

        if ( toggleState.value ==2 && !listUtilSongs.isEmpty() ) {
       // val (settingsImage, settingsText, darkModeButton) = createRefs()

       ConstraintLayout {
           val screenWidth = LocalConfiguration.current.screenWidthDp.dp
           var (box, box1, box2, box3, box4) = createRefs()
           for(x in 0..3) {
           if(x==0) box=box1
           if(x==1) box=box2
           if(x==2) box=box3
           if(x==3) box=box4
           Box(
                   modifier = Modifier
                       .fillMaxWidth(0.5f)
                       .fillMaxHeight(0.5f)
                       .clickable {
                         //  if (!isButtonsEnabled) return@clickable
                           if ( !isButtonsEnabled) return@clickable
                           isButtonStartEnabled = true

                           isButtonsEnabled = false
                           sliderPosition.longValue=0
                           choiceSong(
                               x, songId, volumeCoin, sound,
                               imageLittleCoin, winCount, listUtilSongs,
                               imageVisible,
                               sliderPosition, player
                           )

                           coroutineScope.launch {
                               for (n in 1..1) {
                                   delay(1000)
                               }
                               listUtilSongs.clear()
                           }

                       }
                       .constrainAs(box) {
                           if(x==0 || x==1)top.linkTo(parent.top) else top.linkTo(box1.bottom)
                           if(x==0 || x==2) start.linkTo(parent.start)
                           if(x==1) start.linkTo(box1.end)
                           if(x==3) start.linkTo(box3.end)
                       }


              ) {
               AssetImage(listUtilSongs.get(x).cover)
               if (imageVisible.get(x)) {
                   Image(
                       alignment = Alignment.Center,
                       painter = painterResource(id = imageLittleCoin.value),
                       contentDescription = "coin",
                       modifier = Modifier
                           .padding(15.dp, 0.dp, 0.dp, 0.dp)
                           .width(50.dp)
                           .height(50.dp)
                       //   .alpha(alphaCoin1)

                   )
               }
              }


       }
   }

            }
        }


  /*  LottieAnimation(composition = composition,
        isPlaying = isPlayingLottie,
        speed = 1.5f,
        iterations = 2,
        clipSpec = animSpec
    ) */


}

fun choiceSong(x: Int,songId:Int,volumeCoin:Float, sound: SoundPool?,
               imageLittleCoin:MutableState<Int>,winCount:MutableState<Int>, listUtilSongs:MutableList<MainActivity.Music> ,
               imageVisible: SnapshotStateList<Boolean>,
               sliderPosition: MutableLongState, player:ExoPlayer ){
   //onClick() {
       sliderPosition.longValue = 0;
       player.pause()
       //currentValue.longValue = 0;

      // alphaButtons.value = alphaDisabled
       imageVisible.set(x, true)
       //alphaCoin1.value = 1f
       if (songId == listUtilSongs.get(x).id) {
           winCount.value++;
           imageLittleCoin.value = R.drawable.coin3
           sound?.play(3, volumeCoin, volumeCoin, 0, 0, 1F)

       } else {
           if (winCount.value > 0) winCount.value--
           imageLittleCoin.value = R.drawable.fire_coin2
           sound?.play(4, volumeCoin, volumeCoin, 0, 0, 1F)
       }
  // }
}
/*suspend fun loadProgress( load:Boolean, updateProgress: (Float) -> Unit) {
    if (load) {
        for (i in 1..(10000 / 100).toInt()) {
            updateProgress(i.toFloat() / 100)
            delay(100)
        }
    }
} */
fun generateRandomNumbersAsString(
    start: Int,
    end: Int,
    count: Int,
    separator: String = ","
): String {
    require(count <= (end - start + 1)) { "Count cannot be larger than the range size" }

    val taken = mutableSetOf<Int>()
    return buildString {
        repeat(count) { i ->
            if (i > 0) append(separator)
            var number: Int
            do {
                number = (start..end).random()
            } while (!taken.add(number))
            append(number)
        }
    }
}

@Composable
//fun AssetImage(trackName: MutableState<String>) {
fun AssetImage(trackName: String) {
    /* var imageVisible by remember { mutableStateOf(false) }
     val imageAlpha: Float by animateFloatAsState(
         targetValue = if (imageVisible) 1f else 0f,
         animationSpec = tween(
             durationMillis = 2000,
             easing = LinearEasing,
         )
     )
    */

    val imageAlpha = 1f
    val context = LocalContext.current
    val assetManger = context.assets
    val inputStream = assetManger.open(trackName.toString() + ".jpg")
    val bitMap = BitmapFactory.decodeStream(inputStream)
    Image(
        bitmap = bitMap.asImageBitmap(),
        contentDescription = "",
        contentScale = ContentScale.FillWidth,
        alpha = imageAlpha,
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
            .border(2.dp, White)
    )
}
fun  getUtilSongs(song : MainActivity.Music,list:List<MainActivity.Music> ): MutableList<MainActivity.Music> {

    var squeezeListUtil:MutableList<MainActivity.Music> =list.toMutableStateList()
    squeezeListUtil.remove(song)
    var returnListUtil:ArrayList<MainActivity.Music> = ArrayList(squeezeListUtil);
    //Log.d("rul","In1="+squeezeList.toList())
    Collections.shuffle(returnListUtil); // тут делаем рандом
    returnListUtil.add(0,song)
    if (returnListUtil.size > 4) { // отрезаем не нужную часть

        // тут отрезаем не нужную часть
        //  list.subList(returnList.size - amount, returnList.size).clear()
        // list.subList(0, returnList.size)
        Collections.shuffle(returnListUtil.subList(0,3))
        squeezeListUtil= returnListUtil

        // squeezeList= returnList.subList(returnList.size - amount, returnList.size)
    }
    // Log.d("rul","In="+list)
    Log.d("rul","InThree="+squeezeListUtil.toList())
    return squeezeListUtil;
}
fun  getRandomElements(amount:Int,  list:List<MainActivity.Music> ): MutableList<MainActivity.Music> {
     var returnList:ArrayList<MainActivity.Music> = ArrayList(list);
     var squeezeList:MutableList<MainActivity.Music> =list.toMutableStateList()
    Log.d("rul","In1="+squeezeList.toList())
       Collections.shuffle(returnList); // тут делаем рандом
    if (returnList.size > amount) { // отрезаем не нужную часть
        Log.d("rul", "delSize")
        // тут отрезаем не нужную часть
        //  list.subList(returnList.size - amount, returnList.size).clear()
       // list.subList(0, returnList.size)
        squeezeList= returnList.subList(0,amount)
       // squeezeList= returnList.subList(returnList.size - amount, returnList.size)
    }
   // Log.d("rul","In="+list)
    Log.d("rul","In2="+squeezeList.toList())
        return squeezeList;
    }

fun initSongs(playListShuffle:MutableList<MainActivity.Music>,quantytyOfSectors:Int,playList:List<MainActivity.Music>,player:ExoPlayer ){
    // playListShuffle = getRandomElements(quantytyOfSectors,playList)
    playListShuffle.clear()
    getRandomElements(quantytyOfSectors,playList).forEach {
        playListShuffle.add(it)
    }
    // playListShuffle = playList as SnapshotStateList<MainActivity.Music>
    //playListShuffle = playList.toMutableStateList()
    //  playListShuffle.add(playList[0])

    Log.d("rul","Launch="+ playListShuffle.toList())
    //   Log.d("counter", "Launch0=" + player.currentMediaItemIndex.toString())
    //playList.forEach {
    player.clearMediaItems()
    playListShuffle.forEach {
        val path = "android.resource://" + "com.serhiibaliasnyi.tunewheel" + "/" + it.music
        val mediaItem = MediaItem.fromUri(Uri.parse(path))
        player.addMediaItem(mediaItem)
        //list.add(it.name)

    }
    Log.d("rul","Launch2="+ playListShuffle.toList())
    //Log.d("rul", "Launch2s="+list)
    player.prepare()
}
//fun  getRandomElementsString(amount:Int,  list:MutableList<String> ):MutableList<String> {
//    var returnList:ArrayList<String> = ArrayList(list);
//    var squeezeList:MutableList<String> =list
//
//    Collections.shuffle(returnList); // тут делаем рандом
//    if (returnList.size > amount) { // отрезаем не нужную часть
//        Log.d("rul", "delString")
//        // тут отрезаем не нужную часть
//        //  list.subList(returnList.size - amount, returnList.size).clear()
//        // list.subList(0, returnList.size)
//        squeezeList= returnList.subList(0,amount)
//    }
//    return squeezeList;
//}