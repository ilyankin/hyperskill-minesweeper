package com.ilyankin.minesweeper.minefield

import com.ilyankin.minesweeper.preset.MinesweeperPresets

enum class GameStatus {
    WIN,
    LOSE,
    IN_PROGRESS;
}

class Minefield(preset: MinesweeperPresets = MinesweeperPresets.Hyperskill()) {
    private val gameSettings = preset.gameSettings
    val width = gameSettings.width.toInt()
    val height = gameSettings.height.toInt()
    val size = width * height
    val random = gameSettings.random
    var mines = preset.gameSettings.mines.toInt()

    private val visualSettings = preset.visualSettings
    val rowSep = visualSettings.rowSeparator
    val columnSep = visualSettings.columnSeparator

    private val lineSep = System.lineSeparator()

    var gameStatus: GameStatus = GameStatus.IN_PROGRESS

    private var board = initBoard()

    private fun initBoard(): Map<Pair<Int, Int>, Cell> {
        val board = mutableListOf<Pair<Pair<Int, Int>, Cell>>()
        for (i in 0 until width) {
            for (j in 0 until height) {
                board.add((j to i) to Cell(j, i))
            }
        }
        return board.toMap()
    }

    private var initialized = false

    fun isWin() = gameStatus == GameStatus.WIN
    fun isLose() = gameStatus == GameStatus.LOSE
    fun isInProgress() = gameStatus == GameStatus.IN_PROGRESS

    fun markMine(i: Int, j: Int): Pair<CellState, CellType> {
        val cell = board[i to j]!!
        cell.mark()
        if (initialized) gameStatus = determineGameStatus()
        return cell.state to cell.type
    }

    private fun determineGameStatus(): GameStatus {
        val mines = board.values.filter(Cell::mine)
        val allUnmarkedCellsIsMine = board.values.filter(Cell::isUnmarked).all(Cell::mine)
        return if (mines.size == mines.count(Cell::isMarked) || allUnmarkedCellsIsMine) GameStatus.WIN
        else GameStatus.IN_PROGRESS
    }

    fun markFree(i: Int, j: Int): Pair<CellState, CellType> {
        val cell = board[i to j]!!
        val (state, type) = cell.state to cell.type
        initialize(cell)

        if (cell.mine) {
            gameStatus = GameStatus.LOSE
            return state to type
        }

        if (cell.isMarked()) return state to type

        markFreeAdjacentEmptyCells(cell.row, cell.column)
        gameStatus = determineGameStatus()
        return state to type
    }

    private fun initialize(firstCell: Cell) {
        if (initialized) return
        layMines(firstCell)
        setNumberMinesForEachEmptyCell()
        initialized = true
    }

    private fun layMines(firstCell: Cell, mines: Int = this.mines): Minefield {
        var countMines = 0
        while (mines != countMines) {
            val cell = board[random.nextInt(width) to random.nextInt(height)]!!
            if (cell.mine || cell == firstCell) continue
            cell.mine = true
            countMines++
        }
        return this
    }

    private fun setNumberMinesForEachEmptyCell(): Minefield {
        board.values.filter(Cell::isEmpty).forEach {
            val adjacentMine = getNumberAdjacentMines(it)
            it.type = CellType.from(adjacentMine)
        }
        return this
    }

    private fun getNumberAdjacentMines(cell: Cell) = cell.getAdjacentCells().filter(Cell::mine).size

    private fun Cell.getAdjacentCells(): List<Cell> {
        val cells = mutableListOf<Cell>()
        walkAroundAdjacentCells(this) { i, j -> cells.add(board[i to j]!!) }
        return cells
    }

    private fun markFreeAdjacentEmptyCells(row: Int, column: Int) {
        val cell = board[row to column]!!
        if (cell.mine || cell.isFree()) return

        cell.free()

        if (cell.isNotEmpty()) return
        walkAroundAdjacentCells(cell) { i, j -> markFreeAdjacentEmptyCells(i, j) }
    }

    private fun walkAroundAdjacentCells(cell: Cell, action: (Int, Int) -> Any) {
        val row = cell.row
        val column = cell.column
        for (i in row - 1..row + 1) {
            for (j in column - 1..column + 1) {
                if ((i == row && j == column) || invalidCellIndexes(i, j)) continue
                action(i, j)
            }
        }
    }

    private fun invalidCellIndexes(i: Int, j: Int) = i < 0 || j < 0 || i >= width || j >= height

    private val tableSeparator =
        String.format("$rowSep$columnSep%s$columnSep${System.lineSeparator()}", "$rowSep".repeat(width))

    fun convertToString(gameStatus: GameStatus) =
        (1..width).joinToString("", prefix = " $columnSep", postfix = "$columnSep$lineSep") +
                tableSeparator +
                board.values
                    .map { it.mapCellToChar(gameStatus) }
                    .joinToString(separator = "")
                    .chunked(height)
                    .mapIndexed { index, line -> minefieldLine(index + 1, line) }
                    .joinToString("") +
                tableSeparator

    private fun Cell.mapCellToChar(gameStatus: GameStatus) =
        if (gameStatus == GameStatus.LOSE && this.mine) visualSettings.mineCellSymbol
        else if (this.isNotEmpty() && this.isFree()) this.type.adjacentMine.digitToChar()
        else visualSettings.getCellSymbol(this.state)

    private fun minefieldLine(index: Int, line: String) = "$index$columnSep$line$columnSep$lineSep"
}