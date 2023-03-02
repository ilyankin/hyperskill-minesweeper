package minesweeper.preset


sealed class MinesweeperPresets(val gameSettings: GameSettings, val visualSettings: VisualSettings) {
    class Hyperskill : MinesweeperPresets(GameSettings.Hyperskill(), VisualSettings.Hyperskill())
}