README
Game Description:
ScrabbleWithStuff is like regular Scrabble but with the added option of
special tiles that have different properties when activated. 
The implementation assumes that you cannot stack special tiles on top of
each other just like you can't stack letter tiles. Once a letter tile is placed
on top of a special tile, that special tile is activated and then removed from
the board, since its effect will be used up. Special tiles are only visible to
their owners. If a player chooses his/her move to be playing a special tile, 
he/she can only place 1 tile for that move.

Instructions for playing the game and interacting with the GUI:
When the GUI starts it will prompt you for player names. Enter 2 to 4
different names. Then you can hit Start Scrabble.
Player scores and current player are listed at the top.
In the top right corner there are 5 move choices. A player can only make one
move per turn.

To pass, just hit "Pass", and it'll be the next player's turn.

To buy a special tile, hit
"Buy Special Tile" and in the dialog that appears, select the tile of your
choice. If you have enough points to buy that tile and if there are still that
kind of tile available in the tilebag, the game will proceed to
the next player and in future turns you will be able to see the special tile
in the rack labeled "Specials". The special tile labels are as follows:
	NEG - Negative
	BM - Boom (this is implemented so it removes both special tiles and
		   letter tiles from a square within the boom radius)
	REV - Reverse
	F&S - Freebie Skip
	SWI - Switch
If the purchase isn't successful (you may not have enough points or there are no
more special tiles of that type left), just select another move. You can only
purchase one special tile per turn.

To play letter tiles, hit "Play Letter Tiles". Click a tile, and click a
location on the board where you want that tile to go (alternatively, you could
first click all the tiles you want to play and then click the locations you 
want those tiles to go, but the clicks for locations must be in the same order
as you clicked the tiles in order for your tiles to be matched to your intended
location). 
Selected tiles and locations will be highlighted in yellow. When you are done 
placing your tiles, hit "Submit Move". If the placement is valid, the tiles 
will appear on the board, the score should be updated, and it's the next
player's turn. If the placement is invalid hit "Cancel Move" to reset everything 
and start the process over again. If at any point you want to undo a location 
or tile selection, you'll have to hit "Cancel Move" and start the process over.

To exchange letter tiles, hit "Exchange Tiles", select the tiles that you
want to exchange, and hit "Submit Move". If the exchange can be done, it will
be the next player's turn and you'll be able to see your new rack on your next
turn. A dialog will pop up if there aren't enough tiles to be exchanged and you
will have to pick a different move.

To play a special tile, hit "Play Special Tile", select the special tile you
want to play and the location, then click "Submit Move". Special tiles on the
board are only visible to their owners. One square can also only have up to
one special tile, so if there's an invalid placement error, it's probably due
to the square already being occupied by a tile you can't see. 
You can cancel move if you wish and choose something else.

Note: If you highlight locations and tiles before you select a move, when you
pick a move, the highlights will disappear, and you'll have to select your
tiles and locations again before clicking "Submit Move". 

Game Over Condition: The game over condition defined by this implementation is
when the tilebag has no more letter tiles, and when at least one player has 0 
letter tiles left. Given the complexity of Scrabble With Stuff, there is no 
check for whether or not all possible moves have been made, since special tiles
(like the Boom) can change the state of the board and open up moves.


My Special Tile - FreebieSkipTile:
The tile that I implemented is one that after being activated for the
first time it will give the player who played it a random SpecialTile for
free and then it will skip the next player.
There are 5 of these special tiles in a biag.