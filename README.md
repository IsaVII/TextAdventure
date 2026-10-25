# TextAdventure ![CI](https://github.com/IsaVII/TextAdventure/actions/workflows/ci.yml/badge.svg)

A small text-based RPG written in Java. The project exists primarily as an **object-oriented programming (OOP) training exercise**: it's a sandbox for practicing core OOP concepts (classes, inheritance, interfaces, composition, encapsulation) by building a turn-based combat system from scratch.

> This is a learning project, not a finished game. Some features (items, fleeing, exploring between areas) are stubbed out or not yet implemented, and the code is still evolving.

## What it does

The game currently centers on a turn-based fight system:

- The player controls a `MainCharacter` with health and a set of learned skills.
- Entering an area (e.g. the forest) spawns a group of enemies, randomly chosen and weighted based on a per-area spawn configuration (enemy types, group size, and health scaling).
- Each enemy has its own health pool and skill set, and attacks back during the fight.
- Combat proceeds round by round through a menu: the player chooses to attack, use an item, or flee, then selects one of their skills to use.
- Skills deal damage to one or more enemies (some skills hit multiple targets at once) and go on cooldown for a number of rounds after use.
- Defeated enemies are removed from the fight, and the fight ends once all enemies are dead.

Skills, enemies, and spawn rules are data-driven: enemy types and spawn weights per area are loaded from a JSON config (`src/main/resources/enemies/enemy_spawns.json`), and skills are defined centrally and assigned to the character or enemies.

## Features

- Turn-based fight loop with a simple text menu (attack / item / flee)
- Health system shared between the player and enemies via a common interface
- Skill system with damage, cooldowns, and single- or multi-target effects
- Enemy spawning per area, with randomized group size and weighted enemy selection loaded from JSON
- Color-coded console output for combat feedback

## Tech stack

- Java (compiled with the Maven compiler plugin)
- [Gson](https://github.com/google/gson) for loading JSON spawn configuration
- JUnit 5 (Jupiter) for unit tests
- Maven for build/dependency management

## Getting started

### Prerequisites

- JDK compatible with the project's configured compiler version (see `pom.xml`)
- Maven (or an IDE with built-in Maven support, e.g. IntelliJ IDEA)

### Build

```bash
mvn compile
```

### Run

The entry point is `IsaVII.AdventureApp`, which starts a fight in the forest area with a character named "Xemnas". Run it from your IDE, or via Maven:

```bash
mvn compile exec:java -Dexec.mainClass="IsaVII.AdventureApp"
```

> Note: `exec-maven-plugin` is not currently declared in `pom.xml`. If the command above fails, run `AdventureApp.main()` directly from your IDE instead.

### Run tests

```bash
mvn test
```

## Project structure

```
src/main/java/IsaVII/
├── AdventureApp.java        # Entry point; sets up managers and starts a fight
├── character/                # Player character, skills held by a character
├── enemies/                   # Enemy definitions, enemy manager, spawn config types
├── fight/                      # Fight orchestration (FightManager, FightHandler) and health
├── skills/                     # Skill definitions, skill types/names, skill manager
├── ui/                          # Console input/output helpers
└── world/                     # Area types

src/main/resources/enemies/enemy_spawns.json   # Per-area enemy spawn configuration

src/test/java/IsaVII/        # Unit tests (JUnit 5) for enemies, fight, and skills
```

## Author

Isa
