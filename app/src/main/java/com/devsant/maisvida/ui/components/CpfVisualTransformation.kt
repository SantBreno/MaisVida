package com.devsant.maisvida.ui.components

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CpfVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Format the text with CPF mask
        val formatted = formatCpf(text.text)

        return TransformedText(
            AnnotatedString(formatted),
            CpfOffsetMapping(text.text)
        )
    }

    private fun formatCpf(input: String): String {
        val digits = input.filter { it.isDigit() }
        return when {
            digits.isEmpty() -> ""
            digits.length <= 3 -> digits
            digits.length <= 6 -> "${digits.substring(0, 3)}.${digits.substring(3)}"
            digits.length <= 9 -> "${digits.substring(0, 3)}.${digits.substring(3, 6)}.${digits.substring(6)}"
            else -> "${digits.substring(0, 3)}.${digits.substring(3, 6)}.${digits.substring(6, 9)}-${digits.substring(9)}"
        }
    }
}

class CpfOffsetMapping(private val originalText: String) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        // Count how many non-digit characters we have before this offset
        val digitsBefore = originalText.substring(0, offset).count { it.isDigit() }

        return when {
            digitsBefore <= 3 -> digitsBefore
            digitsBefore <= 6 -> digitsBefore + 1 // +1 for the first dot
            digitsBefore <= 9 -> digitsBefore + 2 // +2 for two dots
            else -> digitsBefore + 3 // +3 for two dots and one dash
        }
    }

    override fun transformedToOriginal(offset: Int): Int {
        // Count how many digits we have up to this position in the formatted text
        var digitCount = 0
        var formattedIndex = 0

        while (formattedIndex < offset && digitCount < originalText.length) {
            val char = if (formattedIndex < originalText.length) originalText[formattedIndex] else ' '
            if (char.isDigit()) {
                digitCount++
            }
            formattedIndex++
        }

        return digitCount
    }
}