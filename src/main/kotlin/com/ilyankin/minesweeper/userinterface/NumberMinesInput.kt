package com.ilyankin.minesweeper.userinterface

import java.lang.Exception

sealed class NumberMinesInput {
    data class Success(val mines: UInt): NumberMinesInput()
    data class Failure(val exception: Exception): NumberMinesInput()
}
