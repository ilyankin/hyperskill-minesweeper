package com.ilyankin.minesweeper.userinterface

sealed class TurnInput {
    data class Success(val cell: UInt, val column: UInt, val actionType: ActionType) : TurnInput()
    data class Failure(val exception: Exception) : TurnInput()
}

enum class ActionType(private val value: String) {
    FREE("free"),
    MINE("mine");

    companion object {
        fun from(value: String): ActionType {
            return ActionType.values().firstOrNull { it.value == value.lowercase() } ?: throw IllegalArgumentException(
                """
            Unknown ActionType value: $value.
            ActionType can only be one of these:
            ${ActionType.values().joinToString(separator = ", ")}.
            """.trimIndent()
            )
        }
    }
}