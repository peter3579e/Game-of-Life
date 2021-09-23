# Game-of-Life

### Implement a Game of Life.

Requirements:
- Your solution should use a Custom Android View
- The view of the game should extend android.View
- Live cells are added to the where you touch the custom view
- Follow the same rules from the wiki

Bonus:
- Add kdoc comments to functions and classes
- Unit tests - add any test you can think of
- Capability of pausing/resuming the game
- Control the FPS of the game with a slide bar
- Add some custom attributes that can apply on the custom view that changes the appearance of it (color, size, style…)

### The rules of Game of Life.
The universe of the Game of Life is an infinite, two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, live or dead, (or populated and unpopulated, respectively). Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:

- Any live cell with fewer than two live neighbours dies, as if by underpopulation.
- Any live cell with two or three live neighbours lives on to the next generation.
- Any live cell with more than three live neighbours dies, as if by overpopulation.
- Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

### Decription of the Design
1. Firstly, I create a data class to represents each cell's status (live or dead) and its coordinate (x and y). 
2. Secondly, Creating a World class with dimension (width and height)
3. For the rules
```
// rule 1 & rule 3
if (cell.alive && (nbNeighbours < 2 || nbNeighbours > 3)
    ) {
        deadCells.add(cell)
    }

// rule 2 & rule 4
if (cell.alive && (nbNeighbours == 3 || nbNeighbours == 2) ||
    !cell.alive && nbNeighbours == 3
   ) {
       liveCells.add(cell)
   }
```
4. For the custom View, I decided to extend SurfaceView class and implement runnable interface for the evolution of World. The difference between surfaceView and CostumeView: https://medium.com/mobile-app-development-publication/surface-view-vs-view-the-differences-b8ad7808dc3c

### To make the game interesting
User are able to drag and select a block of live cels and replace to other positon, to evolve different possibility of evolution.




