# Blackjack
Game Logs analyzer written on pure Java. Main functions: read file, process data, write analysis results into new file(s).

## Introduction
The program is a game data analyzer designed to identify potential faults in the logic of a custom version of blackjack. The game is played with one deck of cards, between two players - the player and the dealer. The objective is to have a hand total of 21 or as close to 21 as possible without going over (going bust). The player is dealt two cards face up, while the dealer gets one card face up and one card face down. The player can choose to "hit" and receive additional cards to improve their hand or "stand" and keep their current hand. If the player's hand exceeds 21, they bust and lose the game automatically. Once the player decides to stand, the dealer reveals their face-down card and hits until their hand totals 17 or more. If the dealer busts, the player wins. Otherwise, the dealer compares their hand with the players hand, and the one with a higher hand total wins.

## Functionality
The program reads in the game data from a .txt file named game_data.txt, located in the resources folder of the project, and writes the output to a file named analyzer_results.txt, which is placed in the project root. The output is sorted by game session ID. The program must be written in Java versions between (including) 8 and 19, and no third party libraries are allowed, only base Java.

## Input
The game data is presented in a text file, with each line describing one turn of a game session. The line includes a timestamp, game session ID, player ID, the action that will happen in the turn, the dealer's hand before the action is put into effect, and the player's hand before the action is put into effect.

## Output
The output is a text file with a list of the first faulty move of a game session. If a flaw is detected, only the first faulty move will be reported. The output result format is the same as the input game data format.

## Notes
The logs are not sequential, and a player can have multiple wins and losses within one game session. Java objects are used to facilitate the analysis, and the '?' character in a hand represents hidden cards. The card names are in a short format: S – spade, H – heart, C – clubs, and D – diamond. The analyzer logic only needs to check information that is directly related to blackjack itself, not the game session creation. For the game session to be faulty, only one flaw needs to be found.
