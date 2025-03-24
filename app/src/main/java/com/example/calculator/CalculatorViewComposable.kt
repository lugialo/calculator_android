package com.example.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EqualsButton(displayValue: MutableState<String>, buttonWidth: Modifier) {
    Button(
        onClick = {
            val result = calculateExpression(displayValue.value)
            displayValue.value = result
        },
        modifier = buttonWidth
    ) {
        Text(text = "=", fontSize = 20.sp)
    }
}

@Composable
fun ValueButton(value: String, displayValue: MutableState<String>, buttonWidth: Modifier) {
    Button(
        onClick = { displayValue.value += value },
        modifier = buttonWidth
    ) {
        Text(text = value, fontSize = 20.sp)
    }
}

@Composable
fun ValueRow(
    value1: String,
    value2: String,
    value3: String,
    value4: String,
    displayValue: MutableState<String>,
    buttonWidth: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ValueButton(value1, displayValue, buttonWidth)
        ValueButton(value2, displayValue, buttonWidth)
        ValueButton(value3, displayValue, buttonWidth)
        ValueButton(value4, displayValue, buttonWidth)
    }
}

@Composable
fun LastRow(value1: String, value2: String, displayValue: MutableState<String>, buttonWidth: Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { displayValue.value = "" },
            modifier = buttonWidth
        ) {
            Text(text = "C", fontSize = 20.sp)
        }
        ValueButton(value1, displayValue, buttonWidth)
        EqualsButton(displayValue, buttonWidth)
        ValueButton(value2, displayValue, buttonWidth)
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorLayout() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val buttonWidth = Modifier.width((screenWidth - (8.dp * 5)) / 4)

    var displayValue = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            Modifier
                .background(color = Color.Black)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = displayValue.value, color = Color.White, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(Modifier.fillMaxWidth()) {
            ValueRow("1", "2", "3", "+", displayValue, buttonWidth)
            Spacer(modifier = Modifier.height(8.dp))
            ValueRow("4", "5", "6", "-", displayValue, buttonWidth)
            Spacer(modifier = Modifier.height(8.dp))
            ValueRow("7", "8", "9", "*", displayValue, buttonWidth)
            Spacer(modifier = Modifier.height(8.dp))
            LastRow("0", "/", displayValue, buttonWidth)
        }
    }
}