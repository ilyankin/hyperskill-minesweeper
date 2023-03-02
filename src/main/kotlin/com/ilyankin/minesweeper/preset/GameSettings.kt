package minesweeper.preset

import minesweeper.minefield.Hyperskill.DEFAULT_MINEFIELD_HEIGHT
import minesweeper.minefield.Hyperskill.DEFAULT_MINEFIELD_WIDTH
import minesweeper.minefield.Hyperskill.DEFAULT_NUMBER_MINES
import minesweeper.minefield.Hyperskill.DEFAULT_RANDOM
import kotlin.random.Random

sealed class GameSettings(
    val width: UInt,
    val height: UInt,
    val mines: UInt,
    val random: Random
) {
    class Hyperskill :
        GameSettings(
            DEFAULT_MINEFIELD_WIDTH,
            DEFAULT_MINEFIELD_HEIGHT,
            DEFAULT_NUMBER_MINES,
            DEFAULT_RANDOM
        )
}
