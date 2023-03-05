package com.ilyankin.minesweeper.preset

import com.ilyankin.minesweeper.minefield.Hyperskill.DEFAULT_COLUMN_SEPARATOR
import com.ilyankin.minesweeper.minefield.Hyperskill.DEFAULT_ROW_SEPARATOR
import com.ilyankin.minesweeper.minefield.Hyperskill.FREE_CELL_SYMBOL
import com.ilyankin.minesweeper.minefield.Hyperskill.MARKED_CELL_SYMBOL
import com.ilyankin.minesweeper.minefield.Hyperskill.MINE_CELL_SYMBOL
import com.ilyankin.minesweeper.minefield.Hyperskill.UNMARKED_CELL_SYMBOL
import com.ilyankin.minesweeper.minefield.CellState

sealed class VisualSettings(
    val mineCellSymbol: Char,
    val freeCellSymbol: Char,
    val markedCellSymbol: Char,
    val unmarkedCellSymbol: Char,
    val rowSeparator: Char,
    val columnSeparator: Char
) {
    private val cellStateToSymbol = mapOf(
        CellState.FREE to freeCellSymbol,
        CellState.MARKED to markedCellSymbol,
        CellState.UNMARKED to unmarkedCellSymbol
    )

    fun getCellSymbol(cellState: CellState) = cellStateToSymbol[cellState]!!

    class Hyperskill : VisualSettings(
        MINE_CELL_SYMBOL,
        FREE_CELL_SYMBOL,
        MARKED_CELL_SYMBOL,
        UNMARKED_CELL_SYMBOL,
        DEFAULT_ROW_SEPARATOR,
        DEFAULT_COLUMN_SEPARATOR
    )
}