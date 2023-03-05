package com.ilyankin.minesweeper.userinterface

import com.ilyankin.minesweeper.minefield.Minefield


interface UserInterface {
    fun readMinefieldSize(): Pair<UInt, UInt>

    fun readNumberMines(): NumberMinesInput

    fun readTurn(): TurnInput

    fun displayField(): String = displayField("")

    fun displayField(message: String): String
}

abstract class AbstractUserInterface(val field: Minefield) : UserInterface {
    protected val fieldWidth = field.width.toUInt()
    protected val fieldHeight = field.height.toUInt()
    protected val fieldSize = field.size.toUInt()

    override fun readMinefieldSize() = fieldWidth to fieldHeight

    override fun readNumberMines(): NumberMinesInput = NumberMinesInput.Success(field.mines.toUInt())
}

class ConsoleUserInterface(field: Minefield) : AbstractUserInterface(field) {

    override fun readNumberMines(): NumberMinesInput {
        val input = readln().trim()
        return try {
            val mines: UInt = input.toUInt()
            if (mines > fieldSize) minesMoreThanMinefieldSize(mines)
            NumberMinesInput.Success(mines)
        } catch (e: NumberFormatException) {
            NumberMinesInput.Failure(
                NumberFormatException(
                    """
                    ${e.message}!
                    Amount of mines must be an integer and must be greater than zero!
                    """.trimIndent()
                )
            )
        } catch (e: IllegalArgumentException) {
            NumberMinesInput.Failure(e)
        }
    }

    override fun readTurn(): TurnInput {
        val input = readln().trim()
        return try {
            val (x, y, action) = input.split(" ", limit = 3)
            val cell = x.toUInt()
            val column = y.toUInt()

            if (cell > fieldWidth || column > fieldHeight) coordinatesOutOfMinefieldBoundsError(cell, column)

            TurnInput.Success(cell, column, ActionType.from(action))
        } catch (e: NumberFormatException) {
            TurnInput.Failure(
                NumberFormatException(
                    """
                    ${e.message}!
                    Field coordinates must be an integer and must be greater than zero!
                    """.trimIndent()
                )
            )
        } catch (e: CoordinatesOutOfMinefieldBoundsException) {
            TurnInput.Failure(e)
        } catch (e: IndexOutOfBoundsException) {
            TurnInput.Failure(
                IndexOutOfBoundsException(
                    """
                    ${e.message}!
                    Your input turn must contain 3 parameters separated by a space: [row number] [column number] [free|mine]
                    """.trimIndent()
                )
            )
        } catch (e: IllegalArgumentException) {
            TurnInput.Failure(e)
        }
    }


    override fun displayField(message: String) =
        """
        |${field.convertToString(field.gameStatus)}
        |$message
        """.trimMargin()

    class CoordinatesOutOfMinefieldBoundsException(message: String) : IndexOutOfBoundsException(message)

    private fun coordinatesOutOfMinefieldBoundsError(x: UInt, y: UInt): Nothing =
        throw CoordinatesOutOfMinefieldBoundsException(
            """
            Field coordinates out of minefield bounds! ($x, $y)
            x must be in the range from 0 to $fieldWidth
            y must be in the range from 0 to $fieldHeight
            """.trimIndent()
        )

    private fun minesMoreThanMinefieldSize(mines: UInt): Nothing =
        throw IllegalArgumentException(
            """
            Amount of mines must be less a minefield size!
            Your entered amount of mines: $mines
            Total minefield size: $fieldSize
            """.trimIndent()
        )
}

