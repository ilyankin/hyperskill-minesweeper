# Minesweeper

This project is a console implementation of the popular game - **Minesweeper**.
The [task](https://hyperskill.org/projects/8) was taken from the JetBrains Academy educational platform (Hyperskill).

# Description

Minesweeper is a game of logic where the player is presented with a field full of hidden mines.
The goal is to mark the positions of all mines without setting any of them off. It's not a game of wild guessing:
it offers hints showing the number of mines around each cell.
One wrong move, and game over!

## Game rules

1. A square's "neighbors/adjacent" are the squares above, below, left, right, and all 4 diagonals.
   Squares on the sides of the board or in a corner have fewer neighbors (the board does not wrap around the edges).
2. If you open a square with no neighboring mines, all its neighbors will automatically open.
   This can cause a large area to open up quickly
3. The first square you open is never a mine
4. You can mark/unmark any cell.
5. If you mark a mine incorrectly, you will have to correct the mistake before you can win.
   Incorrect mine marking doesn't kill you, but it can lead to mistakes which do.
6. You don't have to mark all the mines to win, you just need to open all non-mine squares!

# Features

1. **Set the number of minutes**

   Enter the number of mines at the first application request.

2. **Open(free) cell**

   Enter `[column number] [row number] free`.

3. **Mark/unmark cell**

   Enter `[column number] [row number] mine`.

# Graphic game symbols

- `.` as unexplored cells
- `/` as explored free cells without mines around it
- Numbers from 1 to 8 as explored free cells with 1 to 8 mines around them, respectively
- `X` as mines
- `*` as unexplored marked cells

# How to run

1. Install JDK (newer than version 16)
2. Set `JAVA_HOME` environment variable for Windows. ([Guide](https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html))
3. Open console/terminal and move to path with game
4. Run `./gradlew run --console=plain` command
