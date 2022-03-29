# Rummikub
A recreation of the board game "Rummikub" in Java

In this version, the board is represented by a large grid, with your hand at the bottom of the screen.
To play, click a tile in your hand to select it, and click on the board to place it. The goal is to run out of pieces.
On your turn you can:
- make sets (groups of the same number, all different colors: ex. red 3, blue 3, black 3)
- make runs (groups of same color, in an unbroken sequence: ex. black 3, black 4, black 5)
- pick a piece (end turn without doing anything will pick a piece from the pile)

Some more rules / info:
Sets and runs are organized on the board in coulmns, with each column being a run/set.
Each set and run must be at least 3 pieces long (ex. cant put out a run that is only blue 9, blue 10).
Sets cannot have duplicate colors in them (can't have a set with red 5, blue 5, red 5).
There are 2 of each piece in the pile. The pieces are 1-13 in each color (red, blue, black, green), and 2 wild cards, and each player gets randomly distributed 14 pieces at the beginning of the game.
The wild cards will appear as a purple W in a player's hand, and when selecting it to place down, a player must choose a color and number for it. The wild card will be a slightly different color than the one chosen to signify it is a wild card.
To end your turn, the board must be valid. this means all sets/runs are long enough, runs are unbroken number sequences, and sets don't have repeating colors.
If you cant figure out why the board isn't valid, or you just want to reset your progress, you can press the reset button to reset the board and your hand to the valid positions they were at the beginning of the turn.
