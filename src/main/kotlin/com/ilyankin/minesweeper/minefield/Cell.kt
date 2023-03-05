package com.ilyankin.minesweeper.minefield

data class Cell(
    val row: Int,
    val column: Int,
    var mine: Boolean = false,
    var type: CellType = CellType.EMPTY,
    var state: CellState = CellState.UNMARKED
) {

    fun isMarked() = state == CellState.MARKED
    fun isUnmarked() = state == CellState.UNMARKED
    fun isFree() = state == CellState.FREE

    fun isEmpty() = type == CellType.EMPTY
    fun isNotEmpty() = !isEmpty()

    fun mark() = when (state) {
        CellState.FREE -> CellState.FREE
        CellState.MARKED -> CellState.UNMARKED
        CellState.UNMARKED -> CellState.MARKED
    }.also { state = it }

    fun free() = when (state) {
        CellState.FREE,
        CellState.MARKED,
        CellState.UNMARKED -> CellState.FREE
    }.also { state = it }
}

enum class CellType(val adjacentMine: Int) {
    EMPTY(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    companion object {
        fun from(value: Int): CellType {
            return CellType.values().firstOrNull { it.adjacentMine == value } ?: throw IllegalArgumentException(
                """
                Unknown CellType value: $value.
                CellType can only be one of these:
                ${CellType.values().joinToString(separator = ", ")}.
                """.trimIndent()
            )
        }
    }
}

enum class CellState {
    FREE,
    MARKED,
    UNMARKED,
}