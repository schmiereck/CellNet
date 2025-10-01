# CellNet

## Computational Irreducibility (Stephen Wolfram)

Computational irreducibility is a concept introduced by Stephen Wolfram in his work on cellular automata and complex systems. It refers to the idea that certain systems cannot be simplified or predicted without simulating each step of their evolution. In other words, there is no shortcut to understanding the behavior of these systems; one must go through the entire computational process to see the outcome.

This concept has significant implications in various fields, including physics, biology, and computer science. It suggests that some phenomena in nature may be inherently unpredictable and that their complexity arises from simple rules applied iteratively over time. As a result, even if we know the initial conditions and the rules governing a system, we may still need to perform extensive computations to determine its future state.

Cellular automata (CA) are discrete, abstract computational systems that have found application in various fields, including mathematics, computer science, physics, and biology. They consist of a grid of cells, each of which can be in one of a finite number of states. The state of each cell at the next time step is determined by a set of rules that consider the current state of the cell and the states of its neighboring cells.

## Application Architecture
see [AGENTS.md](AGENTS.md)

## CellNet State-3
Classical cellular automata -  
one-dimensional,  
**three** input states results in 256 different output rules.

The cells in the grid in rows n+1 are aligned with the cells of the row n.  
So every Cell has three inputs.

table of rules (in states => out (per ruleNr)):

|   | in    | out | 0 | 1 | 2 | 3 | 4 |...| 255 |
|---|-------|-----|---|---|---|---|---|---|-----|
| 0 | 0 0 0 |     | 0 | 1 | 0 | 1 | 0 |...| 1   |
| 1 | 0 0 1 |     | 0 | 0 | 1 | 1 | 0 |...| 1   |
| 2 | 0 1 0 |     | 0 | 0 | 0 | 0 | 1 |...| 1   |
| 3 | 0 1 1 |     | 0 | 0 | 0 | 0 | 0 |...| 1   |
| 4 | 1 0 0 |     | 0 | 0 | 0 | 0 | 0 |...| 1   |
| 5 | 1 0 1 |     | 0 | 0 | 0 | 0 | 0 |...| 1   |
| 6 | 1 1 0 |     | 0 | 0 | 0 | 0 | 0 |...| 1   |
| 7 | 1 1 1 |     | 0 | 0 | 0 | 0 | 0 |...| 1   |

## CellNet State-2
cellular automata -  
one-dimensional,  
**two** input states results in 256 different output rules.

The cells in the grid in rows n+1 are "shifted" between the cells of the row n.  
So every Cell has two inputs.

table of rules (in states => out (per ruleNr)):

|   | in  | out | 0 | 1 | 2 | 3 | 4 |...| 15 |
|---|-----|-----|---|---|---|---|---|---|----|
| 0 | 0 0 |     | 0 | 1 | 0 | 1 | 0 |...| 1  |
| 1 | 0 1 |     | 0 | 0 | 1 | 1 | 0 |...| 1  |
| 2 | 1 0 |     | 0 | 0 | 0 | 0 | 1 |...| 1  |
| 3 | 1 1 |     | 0 | 0 | 0 | 0 | 0 |...| 1  |

## CellNet State-2-free
cellular automata -  
one-dimensional,  
**two** input states results in 16 different output rules.  
The input parent Cells are freely selectable from all the positions in the parent row.

The explored space of possibilities is much larger than in the State-2 and State-3 versions.  
The ID-Number of a Grid is addressed by the ruleNr and a offNr specifying the offsets of the inputs.

## TODO CellNet State-2-fry
cellular automata -  
one-dimensional,  
**two** input states results in 16 different output rules.  
The input parent Cells are freely selectable from all the positions in any of the parent rows.

### TODO  
* Search systematically for parent cells that deliver the output that you need for a expected output result
  * Add Cells to the Grid until the expected output is reached
