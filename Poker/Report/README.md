# Poker Game – Two-Card Hand Evaluator

## About This Project

This poker game was developed as an assignment following our teacher's instructions. The goal was to build a simple card game where a player receives two cards, and the program evaluates whether they form a pair or just a high card. Our teacher gave us this project to learn how to work with packages, import them correctly, and understand how Java organizes related classes.

This project reviews several units covered in our course – including object-oriented programming (enums, classes, lists), collections, shuffling, and basic hand evaluation logic.

## Files

| File | Description |
|------|-------------|
| `Card.java` | Defines `Card` with `Suit` (HEARTS, DIAMONDS, CLUBS, SPADES) and `Rank` (TWO through ACE). |
| `Deck.java` | Creates a standard 52‑card deck, shuffles it, and deals one card at a time. |
| `Player.java` | Stores a player's hand, allows adding cards, and displays the hand. |
| `PokerGame.java` | Main game logic: shuffles deck, deals two cards to player, evaluates if the two ranks match (pair) or not (high card). |

## Requirements

- Java 17 or newer (no external libraries required)

## How to Run

1. Compile all Java files (they are in the `poker` package).  
2. Run the `PokerGame` class.

### Command line (from the parent directory of `poker/`):
```bash
javac poker/*.java
java poker.PokerGame
