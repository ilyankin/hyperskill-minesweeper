package minesweeper.minefield

import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random

object Hyperskill {
    const val DEFAULT_MINEFIELD_WIDTH = 9u
    const val DEFAULT_MINEFIELD_HEIGHT = 9u
    const val DEFAULT_NUMBER_MINES = 10u

    const val MINE_CELL_SYMBOL = 'X'
    const val FREE_CELL_SYMBOL = '/'
    const val MARKED_CELL_SYMBOL = '*'
    const val UNMARKED_CELL_SYMBOL = '.'

    const val DEFAULT_ROW_SEPARATOR = '—'
    const val DEFAULT_COLUMN_SEPARATOR = '|'

    val DEFAULT_SEED = ThreadLocalRandom.current().nextLong()
    val DEFAULT_RANDOM = Random(DEFAULT_SEED)
}
