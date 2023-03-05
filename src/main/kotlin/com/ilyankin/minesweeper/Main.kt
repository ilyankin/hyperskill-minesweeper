package com.ilyankin.minesweeper

import minesweeper.Minesweeper
import com.ilyankin.minesweeper.minefield.Minefield
import com.ilyankin.minesweeper.userinterface.ConsoleUserInterface

fun main() = Minesweeper(ConsoleUserInterface(Minefield())).start()
