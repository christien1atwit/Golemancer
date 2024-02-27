Golemancer

Created by: Nathan Christie
Version: 0.7.0
Build Date: 5/3/21


--Change Log--

0.5.1

-Added Water

-Placement of Blockades is fixed

-Added Bases

-Updated textures for: Golems, Blockades, Clay, Club

0.5.2

-Added Sound

-Music Track Added (StreetFood)

-Added Sound Effects (Maro, Summon, Plink)

0.5.3

-Added Paths

-Added: jumpTo, follow, backFollow, onPath, startLayPath, endLayPath

0.5.4

-Golem info window now shows the direction the golem is facing

-Added: faceClk, faceCclk, faceN, faceE, faceS, faceW

0.5.5

-Attacks now are directed toward where the golem is facing

-The item the golem is holding is now displayed in the info window

-Spikes were added

-Added sound for successful script save

-Added sound: (save)

0.5.6

-Control help window shows up displaying basic controls

-Trees were added

-Wood was added (dropped when trees are destroyed)

-Crafting on base tiles now can create Blockade Kits (Wood on base)

-Music Track Added (Sweaty)

-Added: orientToPath, moveForward, moveBackward

-Implemented: Rename, Destroy in Golem menu

-Drop-down menus now close when the final action is used

0.5.7

-Script Creator now tells user when Save is unsuccessful and successful

-Script Creator now prevents files with invalid characters from being made

-Conditional Commands no longer use an action

-New Map: Rivers

0.5.8

-Added: Clay Rocks, Nuts

-New Path tile

-Changed onPath

0.5.8.1

-Added: Sapling

-NEED TO CREATE PATHING :)

-Toggle Paths on/off

0.5.9

-Added: senseItem

-Resolution is now 720p

-New Tile: Goal

-The starting Golem is now named

-Tile Screen and Map Selection menu added

-Added music to Rivers map

-Added Goal to both Rivers and Water Base

0.6.0

-Added new Map: Mount Biter

-Added enemy: Biter

-Added tile: Dirt

-Fixed Conditional (again...)

-Map Editor is now accessable

-Maps now are in the .gsinf (Game State Information) format

-Map Editor now creates newMap.gsinf when saving

0.6.1

-Fixed map loading crash

-Players can now save the game

-Players can now load saved games

0.7.0

-Added 'Abandoned Labortory' map

-Added Locked Doors and Key Cards


--Controls--

Arrow Keys - Move Cursor / Menu Navigation

WASD - Move Camera

Z - Accept / Action

X - Cancel / Open Dropdown menu

P - Toggle Path Visibillity

0 - Save Game

Space - Center Camera on Cursor

Enter - Open/Close Script Creator Confirm Save

Backspace - Erase Character/Instruction (Script Creator only)



--Description--

Create sets of instructions to direct your golem. Victory is achieved when all objectives are met. There are no objectives in this version.

Moving your cursor over Golems and items displays information about them. Pressing 'X' on a golem opens a menu for it.

Rename- changes the name of the Golem to a random name

Destroy - Instantly destroys the Golem


SCRIPT CREATOR:
The first screen on the Script Creator is to add new labels (sections of code), name the script, and to save the script (saved in the /res/scripts folder as a .gol file).
The Main label is needed in every script, when the golem reaches the end of Main it starts from the beginning. Some instructions like 'assess' may cause the golem to follow
the instructions in another label if the conditions are met. When a label other than Main is completed, the golem will go back to the previous label it was following and
continue where it left off, unless it is Main in which it will start at the beginning of Main as previously stated. When you are done with a label make sure to use the
"Save Label" option. If there is only 1 label in your script you cannot use any instructions that can change the label because there is no label to change to. A script will
not save if a label has no instructions or has no name. When you have finished with the Script Creator press ENTER to return to the Action Scene. The following is a description
of each instruction currently in the game.

--Instructions--

-Movement-

moveUp - Moves the golem up one space if it is not blocked.

moveDown - Moves the golem down one space if it is not blocked.

moveLeft - Moves the golem left one space if it is not blocked.

moveRight - Moves the golem right one space if it is not blocked.

moveForward - Moves the Golem in the direction it is facing

moveBackward - Moves the Golem in the opposite direction it is facing

senseItem - Moves the Golem to an adjacent item (Even Diagonal)

-Basic Conditional-

jumpTo - jumps to specified label

assess - The golem will change to another label based on its current stats.

 Health - If the golem's current health is lower than 90% it will change what label it is reading

 Mhealth - Unused does nothing

 Initative- Unsued does nothing



-Combat-

attack - Uses the golem's fight stat to inflict damage to object next to it.

selfDestruct - The golem destroys itself, leaving behind a pile of clay.



-Item-

pickUp - If the golem is standing on the top of an item it will pick it up.

dropItem - If the golem has an item it will drop it where it is standing.

useItem - If the golem is holding an item it will use that item.


-Paths-

onPath - Jumps if on path, exception if followed to end of path

startLayPath - starts making a path where golem moves

endLayPath - stops making a path

follow - when on path it follows the path

backFollow - when on path it follows the path in the reverse order



-Orientation-

faceClk - Turns the golem clockwise 

faceCclk - Turns the golem counter-clockwise

faceN - Turns the golem North

faceE - Turns the golem East

faceS - Turns the golem South

faceW - Turns the golem West

orientToPath - Turns the Golem towards the closest accessable path

