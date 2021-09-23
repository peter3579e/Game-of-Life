package com.peter.gameoflife

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.WindowManager


class GameOfLifeView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs), Runnable {
    // Thread which will be responsible to manage the evolution of the World
    private var thread: Thread? = null

    // Boolean indicating if the World is evolving or not
    private var isRunning = false
    private var columnWidth = 1
    private var rowHeight = 1
    private var nbColumns = 1
    private var nbRows = 1
    private var world: World? = null

    // Utilitaries objects : a Rectangle instance and a Paint instance used to draw the elements
    private val r: Rect = Rect()
    private val p: Paint = Paint()


    init {
        initWorld()
    }

    override fun run() {
        // while the world is evolving
        while (isRunning) {
            if (!holder.surface.isValid) continue

            // Pause of 300 ms to better visualize the evolution of the world
            try {
                Thread.sleep(300)
                val canvas: Canvas = holder.lockCanvas()
                world!!.nextGeneration()
                drawCells(canvas)
                holder.unlockCanvasAndPost(canvas)
            } catch (e: InterruptedException) {
            }
        }
    }

    fun start() {
        // World is evolving
        isRunning = true
        thread = Thread(this)
        // we start the Thread for the World's evolution
        thread!!.start()
    }

    fun stop() {
        isRunning = false
        while (true) {
            try {
                thread!!.join()
            } catch (e: InterruptedException) {
            }
            break
        }
    }

    private fun initWorld() {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        display.getSize(point)
        // we calculate the number of columns and rows for our World
        nbColumns = point.x / DEFAULT_SIZE
        nbRows = point.y / DEFAULT_SIZE
        // we calculate the column width and row height
        columnWidth = point.x / nbColumns
        rowHeight = point.y / nbRows
        world = World(nbColumns, nbRows)
    }

    // Method to draw each cell of the world on the canvas
    private fun drawCells(canvas: Canvas) {
        for (i in 0 until nbColumns) {
            for (j in 0 until nbRows) {
                val cell = world!![i, j]
                r.set(
                    cell!!.x * columnWidth - 1, cell.y * rowHeight - 1,
                    cell.x * columnWidth + columnWidth - 1,
                    cell.y * rowHeight + rowHeight - 1
                )
                // we change the color according the alive status of the cell
                p.setColor(if (cell.alive) DEFAULT_ALIVE_COLOR else DEFAULT_DEAD_COLOR)
                canvas.drawRect(r, p)
            }
        }
    }

    // We let the user to interact with the Cells of the World
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            // we get the coordinates of the touch and we convert it in coordinates for the board
            val i = (event.x / columnWidth).toInt()
            val j = (event.y / rowHeight).toInt()
            // we get the cell associated to these positions
            val cell = world!![i, j]
            // we call the invert method of the cell got to change its state
            cell!!.invert()
            invalidate()
        }
        return super.onTouchEvent(event)
    }

    companion object {
        // Default size of a cell
        const val DEFAULT_SIZE = 50

        // Default color of an alive color (white in our case)
        val DEFAULT_ALIVE_COLOR: Int = Color.WHITE

        // Default color of a dead color (black in our case)
        val DEFAULT_DEAD_COLOR: Int = Color.BLACK
    }
}