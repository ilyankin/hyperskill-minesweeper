package minesweeper

import com.ilyankin.minesweeper.minefield.CellState
import com.ilyankin.minesweeper.minefield.CellType
import com.ilyankin.minesweeper.userinterface.AbstractUserInterface
import com.ilyankin.minesweeper.userinterface.ActionType
import com.ilyankin.minesweeper.userinterface.NumberMinesInput
import com.ilyankin.minesweeper.userinterface.TurnInput

class Minesweeper(private val userInterface: AbstractUserInterface) {
    private val minefield = userInterface.field

    fun start() {
        println("How many mines do you want on the field?")
        val numberMines = getNumberMines()
        minefield.mines = numberMines
        while (minefield.isInProgress()) {
            println(userInterface.displayField())
            println("Set/unset mines marks or claim a cell as free:")
            val (x, y, action) = getParsedUserTurnInput()

            val (cellState, cellType) = performAction(x - 1, y - 1, action)

            if (cellState == CellState.FREE) {
                if (cellType == CellType.EMPTY) {
                    println("There is already free!")
                } else {
                    println("There is a number here!")
                }
            }

            if (minefield.isWin()) println(userInterface.displayField("Congratulations! You found all the mines!"))

            if (minefield.isLose()) println(userInterface.displayField("You stepped on a mine and failed!"))
        }
    }

    private fun getParsedUserTurnInput(): Triple<Int, Int, ActionType> {
        var turnInput: TurnInput
        while (true) {
            turnInput = userInterface.readTurn()
            when (turnInput) {
                is TurnInput.Failure -> println(turnInput.exception.message)
                is TurnInput.Success -> {
                    return Triple(turnInput.cell.toInt(), turnInput.column.toInt(), turnInput.actionType)
                }
            }
        }
    }

    private fun getNumberMines(): Int {
        var minesInput: NumberMinesInput
        while (true) {
            minesInput = userInterface.readNumberMines()
            when (minesInput) {
                is NumberMinesInput.Failure -> println(minesInput.exception.message)
                is NumberMinesInput.Success -> return minesInput.mines.toInt()
            }
        }
    }

    private fun performAction(x: Int, y: Int, action: ActionType): Pair<CellState, CellType> = when (action) {
        ActionType.MINE -> minefield.markMine(x, y)
        ActionType.FREE -> minefield.markFree(x, y)
    }
}





