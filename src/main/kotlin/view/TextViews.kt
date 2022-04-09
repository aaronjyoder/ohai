package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Title(text: String) {
    Text(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp).fillMaxWidth(),
        text = text,
        fontSize = TextUnit(16F, TextUnitType.Sp),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start,
        softWrap = false
    )
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Subtitle(text: String) {
    Text(
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp).fillMaxWidth(),
        text = text,
        fontSize = TextUnit(14F, TextUnitType.Sp),
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start,
        softWrap = false
    )
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TextCard(modifier: Modifier, label: String, contentString: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.width(IntrinsicSize.Max)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(12F, TextUnitType.Sp),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 0.dp),
            softWrap = false
        )
        Card(
            shape = RoundedCornerShape(4.dp),
            backgroundColor = Color(220, 220, 220),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp).fillMaxWidth().defaultMinSize(10.dp).height(24.dp)
        ) {
            Text(
                text = contentString,
                fontSize = TextUnit(12F, TextUnitType.Sp),
                color = Color(80, 80, 80),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp).wrapContentHeight(),
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Label(text: String) {
    Text(
        text,
        fontSize = TextUnit(14F, TextUnitType.Sp)
    )
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun TextBox(text: String) {
    Box(modifier = Modifier.background(color = Color(220, 220, 220), shape = RoundedCornerShape(4.dp))) {
        Text(
            text,
            fontSize = TextUnit(14F, TextUnitType.Sp),
            modifier = Modifier.padding(2.dp)
        )
    }
}