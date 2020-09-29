package com.github.ajalt.mordant.rendering

import com.github.ajalt.mordant.rendering.TextAlign.*
import com.github.ajalt.mordant.rendering.VerticalAlign.*

typealias Line = List<Span>
internal val EMPTY_LINES = Lines(emptyList())

data class Lines(
        val lines: List<Line>,
) {
    val size: Int get() = lines.size

    internal fun withStyle(style: TextStyle?): Lines {
        return when (style) {
            null, DEFAULT_STYLE -> this
            else -> Lines(lines.map { l -> l.map { it.withStyle(style) } })
        }
    }

    internal operator fun plus(other: Lines): Lines {
        return when {
            lines.isEmpty() -> other
            other.lines.isEmpty() -> this
            else -> {
                Lines(listOf(
                        lines.dropLast(1),
                        listOf(lines.last() + other.lines.first()),
                        other.lines.drop(1)
                ).flatten())
            }
        }
    }
}

/**
 * Pad or crop every line so its width is exactly [newWidth], and add or remove lines so its height
 * is exactly [newHeight]
 */
internal fun Lines.setSize(
        newWidth: Int,
        newHeight: Int = lines.size,
        verticalAlign: VerticalAlign = TOP,
        textAlign: TextAlign = NONE
): Lines {
    if (newHeight == 0) return EMPTY_LINES
    if (newWidth == 0) return Lines(List(newHeight) { emptyList() })

    val heightToAdd = (newHeight - lines.size).coerceAtLeast(0)

    val emptyLine = listOf(Span.space(newWidth))
    val lines = ArrayList<Line>(newHeight)

    val topEmptyLines = when (verticalAlign) {
        TOP -> 0
        MIDDLE -> heightToAdd / 2 + heightToAdd % 2
        BOTTOM -> heightToAdd
    }

    repeat(topEmptyLines) {
        lines.add(emptyLine)
    }

    line@ for ((i, line) in this.lines.withIndex()) {
        if (i >= newHeight) break

        var width = 0
        for ((j, span) in line.withIndex()) {
            when {
                width + span.cellWidth <= newWidth -> {
                    width += span.cellWidth

                }
                width == newWidth -> {
                    lines.add(line.subList(0, j))
                    continue@line
                }
                else -> {
                    lines.add(line.subList(0, j) + span.take(newWidth - width))
                    continue@line
                }
            }
        }

        val remainingWidth = newWidth - width
        if (remainingWidth > 0) {
            val beginStyle by lazy(NONE) {
                (i downTo 0).firstOrNull { this.lines.getOrNull(it)?.isEmpty() == false }
                        ?.let { this.lines[it].first().style } ?: DEFAULT_STYLE
            }
            val endStyle by lazy(NONE) {
                (line.lastOrNull()?.style
                        ?: (i..this.lines.lastIndex).firstOrNull { this.lines[it].isNotEmpty() }
                                ?.let { this.lines[it].first().style })
                        ?: DEFAULT_STYLE
            }
            when (textAlign) {
                CENTER, JUSTIFY -> {
                    val l = Span.space(remainingWidth / 2, beginStyle)
                    val r = Span.space(remainingWidth / 2 + remainingWidth % 2, endStyle)
                    lines.add(listOf(listOf(l), line, listOf(r)).flatten())
                }
                LEFT -> {
                    lines.add(line + Span.space(remainingWidth, endStyle))
                }
                NONE -> {
                    lines.add(line + Span.space(remainingWidth)) // No style spaces in this alignment
                }
                RIGHT -> {
                    lines.add(listOf(Span.space(remainingWidth, beginStyle)) + line)
                }
            }
        } else {
            lines.add(line)
        }
    }

    if (newHeight != lines.size) {
        if (newHeight < lines.size) {
            return Lines(lines.take(newHeight))
        } else {
            val line = if (newWidth == 0) emptyList() else listOf(Span.space(newWidth))
            repeat(newHeight - lines.size) {
                lines.add(line)
            }
        }
    }
    return Lines(lines)
}
