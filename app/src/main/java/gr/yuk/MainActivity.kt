package gr.yuk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import gr.yuk.ui.theme.GrowingYukTheme
import kotlinx.coroutines.delay
import kotlin.math.floor
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GrowingYukTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Info()
                }
            }
        }
    }
}

@Composable
fun Info() {
    Column {
        val cm = remember { mutableStateOf(51.0) }
        val cmCost = remember { mutableStateOf(4) }
        val cmLevel = remember { mutableStateOf(0) }

        val kg = remember { mutableStateOf(3.5) }
        val kgCost = remember { mutableStateOf(2) }
        val kgLevel = remember { mutableStateOf(0) }

        val coin = remember { mutableStateOf(5) }

        val coinPerSecond = remember { mutableStateOf(1) }
        val coinPerSecondLevel = remember { mutableStateOf(0) }
        val coinPerSecondCost = remember { mutableStateOf(2) }

        LaunchedEffect(Unit) {
            while(true) {
                coin.value += coinPerSecond.value
                delay(1000)
            }
        }

        Text("유크")
        Text("키 : ${cm.value}cm")
        Text("몸무게 : ${kg.value}kg")
        Text("")
        Text("코인 : ${coin.value}")
        Text("1초당 코인 버는 양 : ${coinPerSecond.value}")

        Button(onClick ={
            if (coin.value >= cmCost.value) {
                cmLevel.value ++
                coin.value -= cmCost.value
                cm.value += 0.5
                cm.value = flo(cm.value)
                cmCost.value = flo(cmCost.value.toDouble().pow(1.6)).toInt()
            }
        }, enabled = coin.value >= cmCost.value)
        { Text("키 키우기  ${cmCost.value}코인 ${cmLevel.value}레벨") }
        Button(onClick ={
            if (coin.value >= kgCost.value) {
                coin.value -= kgCost.value
                kgLevel.value++
                kg.value += 0.2
                kg.value = flo(kg.value)
                kgCost.value = flo(kgCost.value.toDouble().pow(1.6)).toInt()
            }
        }, enabled = coin.value >= kgCost.value)
        { Text("몸무게 키우기 ${kgCost.value}코인 ${kgLevel.value}레벨") }
        Text("")
        Button(onClick ={
            coin.value += 1
        }) { Text("돈 벌기") }
        Button(onClick ={
            if (coin.value >= coinPerSecondCost.value) {
                coin.value -= coinPerSecondCost.value
                coinPerSecondCost.value *= 3
                coinPerSecond.value *= 2
                coinPerSecondLevel.value++
            }
        }, enabled = coin.value >= coinPerSecondCost.value)
        { Text("1초당 코인 버는 양 늘리기 ${coinPerSecondCost.value}코인 ${coinPerSecondLevel.value}레벨") }
    }
}

fun flo(double: Double) : Double {
    return  floor(double * 10) / 10
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GrowingYukTheme {
        Info()
    }
}