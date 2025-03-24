package com.example.calculator

class CalculatorMethods {
    fun calculate(expression: String): Double {
        if (expression.isBlank()) return 0.0
        var modifiedExpression = handleMultiplicationAndDivision(expression)

        return handleAdditionAndSubtraction(modifiedExpression)
    }

    // Método para calcular multiplicação e divisão
    private fun handleMultiplicationAndDivision(expression: String): String {
        var modifiedExpression = expression
        while (modifiedExpression.contains("*") || modifiedExpression.contains("/")) {
            val parts = modifiedExpression.split(Regex("(?<=[*/])|(?=[*/])"))
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toMutableList()

            val opIndex = parts.indexOfFirst { it == "*" || it == "/" }
            if (opIndex == -1) break

            val num1 = parts[opIndex - 1].toDouble()
            val num2 = parts[opIndex + 1].toDouble()
            val result = when (parts[opIndex]) {
                "*" -> num1 * num2
                "/" -> {
                    if (num2 == 0.0) throw ArithmeticException("Division by zero")
                    num1 / num2
                }
                else -> throw IllegalArgumentException("Invalid operator")
            }
            parts.subList(opIndex - 1, opIndex + 2).clear()
            parts.add(opIndex - 1, result.toString())
            modifiedExpression = parts.joinToString("")
        }
        return modifiedExpression
    }

    // Método para calcular adição e subtração
    private fun handleAdditionAndSubtraction(expression: String): Double {
        var modifiedExpression = expression
        while (modifiedExpression.contains("+") || modifiedExpression.contains("-")) {
            val parts = modifiedExpression.split(Regex("(?<=[+\\-])|(?=[+\\-])"))
                .map { it.trim() }
                .filter { it.isNotEmpty() }
                .toMutableList()
            if (parts.all { it.toDoubleOrNull() != null }) return parts.first().toDouble()

            val opIndex = parts.indexOfFirst { it == "+" || it == "-" }

            if (opIndex == -1) break

            val num1 = parts[opIndex - 1].toDouble()
            val num2 = parts[opIndex + 1].toDouble()

            val result = when (parts[opIndex]) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                else -> throw IllegalArgumentException("Invalid operator")
            }
            parts.subList(opIndex - 1, opIndex + 2).clear()
            parts.add(opIndex - 1, result.toString())
            modifiedExpression = parts.joinToString("")
        }
        return modifiedExpression.toDouble()
    }
}

// Método pra avaliar e calcular a expressão
fun calculateExpression(expression: String): String {
    return try {
        val calculator = CalculatorMethods()
        val result = calculator.calculate(expression)
        result.toString()
    } catch (e: Exception) {
        "Error"
    }
}