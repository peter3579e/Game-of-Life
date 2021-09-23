package com.peter.gameoflife

import java.util.*
import kotlin.collections.ArrayList

class World(var width: Int, var height: Int) {
    private val board: Array<Array<Cell?>>

    private fun init() {
        for (i in 0 until width) {
            for (j in 0 until height) {
                board[i][j] = Cell(i, j, RANDOM.nextBoolean())
            }
        }
    }

    operator fun get(i: Int, j: Int): Cell? {
        return board[i][j]
    }

    fun nbNeighboursOf(i: Int, j: Int): Int {
        var nb = 0
        for (k in i - 1..i + 1) {
            for (l in j - 1..j + 1) {
                if ((k != i || l != j) && k >= 0 && k < width && l >= 0 && l < height) {
                    val cell = board[k][l]
                    if (cell!!.alive) {
                        nb++
                    }
                }
            }
        }
        return nb
    }

    fun nextGeneration() {
        val liveCells: MutableList<Cell?> = ArrayList()
        val deadCells: MutableList<Cell?> = ArrayList()
        for (i in 0 until width) {
            for (j in 0 until height) {
                val cell = board[i][j]
                val nbNeighbours = nbNeighboursOf(cell!!.x, cell.y)

                // rule 1 & rule 3
                if (cell.alive &&
                    (nbNeighbours < 2 || nbNeighbours > 3)
                ) {
                    deadCells.add(cell)
                }

                // rule 2 & rule 4
                if (cell.alive && (nbNeighbours == 3 || nbNeighbours == 2)
                    ||
                    !cell.alive && nbNeighbours == 3
                ) {
                    liveCells.add(cell)
                }
            }
        }

        // update future live and dead cells
        for (cell in liveCells) {
            cell!!.reborn()
        }
        for (cell in deadCells) {
            cell!!.die()
        }
    }

    companion object {
        val RANDOM: Random = Random()
    }

    init {
        board = Array(width) {
            arrayOfNulls(
                height
            )
        }
        init()
    }
}