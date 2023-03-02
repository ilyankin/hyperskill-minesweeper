package minesweeper

import minesweeper.minefield.Minefield
import minesweeper.userinterface.ConsoleUserInterface

fun main() = Minesweeper(ConsoleUserInterface(Minefield())).start()
