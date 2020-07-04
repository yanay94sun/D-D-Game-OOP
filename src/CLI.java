import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner charSC = new Scanner(System.in);
        char c;
        int i;
        boolean gameComplete = false;
        boolean gameOver = false;
        boolean valid;

        Player playerTile = new Warrior(0, 0, "Jon Snow", 300, 30, 4, 3);

        boolean choose = true;
        while (choose) {
            System.out.println("Select player:");
            System.out.println("1. Jon Snow \t\t\t Health: 300/300 \t\t\t Attack: 30 \t\t\t Defense: 4 \t\t\t " +
                    "Level: 1 \t\t\t Experience: 0/50 \t\t\t Cooldown: 0/3");
            System.out.println("2. The Hound \t\t\t Health: 400/400 \t\t\t Attack: 20 \t\t\t Defense: 6 \t\t\t " +
                    "Level: 1 \t\t\t Experience: 0/50 \t\t\t Cooldown: 0/5");
            System.out.println("3. Melisandre \t\t\t Health: 100/100 \t\t\t Attack: 5 \t\t\t Defense: 1 \t\t\t " +
                    "Level: 1 \t\t\t Experience: 0/50 \t\t\t Mana: 75/300 \t\t\t Spell Power: 15");
            System.out.println("4. Thoros of Myr \t\t\t Health: 250/250 \t\t\t Attack: 25 \t\t\t Defense: 4 \t\t\t " +
                    "Level: 1 \t\t\t Experience: 0/50 \t\t\t Mana: 37/150 \t\t\t Spell Power: 20");
            System.out.println("5. Arya Stark \t\t\t Health: 150/150 \t\t\t Attack: 40 \t\t\t Defense: 2 \t\t\t " +
                    "Level: 1 \t\t\t Experience: 0/50 \t\t\t Energy: 100/100");
            System.out.println("6. Bronn \t\t\t Health: 250/250 \t\t\t Attack: 35 \t\t\t Defense: 3 \t\t\t " +
                    "Level: 1 \t\t\t Experience: 0/50 \t\t\t Energy: 100/100");

            c = charSC.next().charAt(0);

            switch (c) {
                case 49:
                    playerTile = new Warrior(0, 0, "Jon Snow", 300, 30, 4, 3);
                    choose = false;
                    break;
                case 50:
                    playerTile = new Warrior(0, 0, "The Hound", 400, 20, 6, 5);
                    choose = false;
                    break;
                case 51:
                    playerTile = new Mage(0, 0, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
                    choose = false;
                    break;
                case 52:
                    playerTile = new Mage(0, 0, "Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4);
                    choose = false;
                    break;
                case 53:
                    playerTile = new Rogue(0, 0, "Arya Stark", 150, 40, 2, 20);
                    choose = false;
                    break;
                case 54:
                    playerTile = new Rogue(0, 0, "Bronn", 250, 35, 3, 50);
                    choose = false;
                    break;
            }
        }

        System.out.println("You have selected:\n" + playerTile.name + " !");
        int levelNum = 1;
        int y;
        int x;
        Tile[][] levelData = load_next_level(playerTile, levelNum, args[0]);
        int ySize = levelData.length;
        int xSize = levelData[0].length;
        int enemyCounter = 0;

        for (y = 0; y < ySize; y++) {
            for (x = 0; x < xSize; x++) {
                if (levelData[y][x] instanceof Enemey) {
                    enemyCounter += 1;
                }
            }
        }

        Enemey[] levelEnemies = new Enemey[enemyCounter];
        enemyCounter = 0;

        for (y = 0; y < ySize; y++) {
            for (x = 0; x < xSize; x++) {
                if (levelData[y][x] instanceof Enemey) {
                    levelEnemies[enemyCounter] = (Enemey) levelData[y][x];
                    enemyCounter += 1;
                }
            }
        }

        while (true) {
            for (y = 0; y < ySize; y++) {
                for (x = 0; x < xSize; x++) {
                    System.out.print(levelData[y][x].getTile());
                }
                System.out.print("\n");
            }
            System.out.println("\n" + playerTile.getDescription());

            valid = false;
            while (!valid) {
                c = charSC.next().charAt(0);
                switch ((int) c) {
                    case 119: //w
                        move(levelData, playerTile, "up");
                        valid = true;
                        break;
                    case 115: //s
                        move(levelData, playerTile, "down");
                        valid = true;
                        break;
                    case 97: //a
                        move(levelData, playerTile, "left");
                        valid = true;
                        break;
                    case 100: //d
                        move(levelData, playerTile, "right");
                        valid = true;
                        break;
                    case 101: //e
                        playerTile.castAbility(levelEnemies);
                        valid = true;
                        break;
                    case 113: //q
                        valid = true;
                        break;

                }
            }

            Monster monster;
            Trap trap;
            enemyCounter = 0;
            for (i = 0; i < levelEnemies.length; i++) {
                if (levelEnemies[i].getClass() == Monster.class && levelEnemies[i].healthAmount > 0) {
                    enemyCounter += 1;
                    monster = (Monster) levelEnemies[i];
                    switch (monster.playTurn(playerTile.getCoordinate().getX(), playerTile.getCoordinate().getY())) {
                        case 0:
                            break;
                        case 1:
                            move(levelData, monster, "left");
                            break;
                        case 2:
                            move(levelData, monster, "right");
                            break;
                        case 3:
                            move(levelData, monster, "up");
                            break;
                        case 4:
                            move(levelData, monster, "down");
                            break;

                    }
                }

                if (levelEnemies[i].getClass() == Trap.class && levelEnemies[i].healthAmount > 0) {
                    enemyCounter += 1;
                    trap = (Trap) levelEnemies[i];
                    trap.gameTick();
                    if ((Math.sqrt(Math.pow((trap.getCoordinate().getX() - playerTile.getCoordinate().getX()), 2) + Math.pow((trap.getCoordinate().getY() - playerTile.getCoordinate().getY()), 2)) < 2)) {
                        Combat(trap, playerTile);
                    }
                }

                if (levelEnemies[i].healthAmount <= 0 && levelEnemies[i].alive) {
                    levelEnemies[i].alive = false;
                    levelData[levelEnemies[i].getCoordinate().getY()][levelEnemies[i].getCoordinate().getX()] =
                            new EmptyTile(levelEnemies[i].getCoordinate().getX(), levelEnemies[i].getCoordinate().getY());
                    playerTile.experience += levelEnemies[i].experienceValue;
                    System.out.println(levelEnemies[i].name + " died. " + playerTile.name + " gained "
                            + levelEnemies[i].experienceValue + " experience.");
                }

                if (playerTile.healthAmount <= 0) break;

            }

            if (playerTile.healthAmount <= 0) {
                gameOver = true;
                break;
            } else if (enemyCounter == 0) {
                try {
                    levelNum += 1;
                    levelData = load_next_level(playerTile, levelNum, args[0]);
                    ySize = levelData.length;
                    xSize = levelData[0].length;
                    enemyCounter = 0;

                    for (y = 0; y < ySize; y++) {
                        for (x = 0; x < xSize; x++) {
                            if (levelData[y][x] instanceof Enemey) {
                                enemyCounter += 1;
                            }
                        }
                    }


                    levelEnemies = new Enemey[enemyCounter];
                    enemyCounter = 0;

                    for (y = 0; y < ySize; y++) {
                        for (x = 0; x < xSize; x++) {
                            if (levelData[y][x] instanceof Enemey) {
                                levelEnemies[enemyCounter] = (Enemey) levelData[y][x];
                                enemyCounter += 1;
                            }
                        }
                    }
                } catch (Exception e) {
                    gameComplete = true;
                }
            }

            playerTile.gameTick();
            if (gameComplete) break;


        }

        if (gameOver) {
            System.out.println("You lost.");
            for (y = 0; y < ySize; y++) {
                for (x = 0; x < xSize; x++) {
                    System.out.print(levelData[y][x].getTile());
                }
                System.out.print("\n");
            }
            System.out.println("\n" + playerTile.getDescription());
            System.out.println("Game Over.");
        }

        if (gameComplete) {
            System.out.println("You won!");
        }
    }

    public static Tile[][] load_next_level(Player playerTile, int levelNum, String path) throws FileNotFoundException {
        File file = new File(path + "\\level" + levelNum + ".txt");
        Scanner file_sc = new Scanner(file);
        int ySize = 0;
        String temp = "";

        while (file_sc.hasNextLine()) {
            ySize += 1;
            temp = file_sc.nextLine();
        }
        int xSize = temp.length();
        Tile[][] levelData = new Tile[ySize][xSize];
        int y = 0;
        int x;
        file_sc = new Scanner(file);
        while (file_sc.hasNextLine()) {
            String currLine = file_sc.nextLine();
            for (x = 0; x < temp.length(); x++) {
                char cell = currLine.charAt(x);

                switch ((int) cell) {
                    case 35: // #
                        levelData[y][x] = new WallTile(x, y);
                        break;
                    case 46: // .
                        levelData[y][x] = new EmptyTile(x, y);
                        break;
                    case 64: // @
                        levelData[y][x] = playerTile;
                        playerTile.setCoordinate(new Coordinate(x, y));
                        break;
                    case 115: // s
                        levelData[y][x] = new Monster(x, y, "Lannister Solider", 's', 80, 8, 3, 3, 25);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 107: // k
                        levelData[y][x] = new Monster(x, y, "Lannister Knight", 'k', 200, 14, 8, 4, 50);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 113: // q
                        levelData[y][x] = new Monster(x, y, "Queen’s Guard", 'q', 400, 20, 15, 5, 100);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 122: // z
                        levelData[y][x] = new Monster(x, y, "Wright", 'z', 600, 30, 15, 3, 100);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 98: // b
                        levelData[y][x] = new Monster(x, y, "Bear-Wright", 'b', 1000, 75, 30, 4, 250);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 103: // g
                        levelData[y][x] = new Monster(x, y, "Giant-Wright", 'g', 1500, 100, 40, 5, 500);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 119: // w
                        levelData[y][x] = new Monster(x, y, "White Walker", 'w', 2000, 150, 50, 6, 1000);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 77: // M
                        levelData[y][x] = new Monster(x, y, "The Mountain ", 'M', 1000, 60, 25, 6, 500);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 67: // C
                        levelData[y][x] = new Monster(x, y, "Queen Cersei ", 'C', 100, 10, 10, 1, 1000);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 75: // K
                        levelData[y][x] = new Monster(x, y, "Night’s King ", 'K', 5000, 300, 150, 8, 5000);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 66: // B
                        levelData[y][x] = new Trap(x, y, "Bonus Trap ", 'B', 1, 1, 1, 250, 1, 5);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 81: // Q
                        levelData[y][x] = new Trap(x, y, "Queen’s Trap ", 'Q', 250, 50, 10, 100, 3, 7);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 68: // D
                        levelData[y][x] = new Trap(x, y, "Death Trap ", 'D', 500, 100, 20, 250, 1, 10);
                        levelData[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                }
            }
            y += 1;
        }
        return levelData;
    }

    public static void move(Tile[][] LD, Tile tile, String direction) {
        Coordinate tempPlayerPosition;
        Tile tempPlayer;
        switch (direction) {
            case "up":
                Tile upTile = LD[tile.getCoordinate().getY() - 1][tile.getCoordinate().getX()];
                if (upTile.getClass() == EmptyTile.class) {
                    tempPlayerPosition = tile.getCoordinate();
                    tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate tempUpTile_position = upTile.getCoordinate();
                    Tile tempUpTile = LD[tile.getCoordinate().getY() - 1][tile.getCoordinate().getX()];
                    tile.setCoordinate(tempUpTile_position);
                    upTile.setCoordinate(tempPlayerPosition);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                    LD[upTile.getCoordinate().getY()][upTile.getCoordinate().getX()] = tempUpTile;
                } else if ((tile instanceof Player && upTile instanceof Enemey) || (tile instanceof Enemey && upTile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (upTile));
                    if (upTile instanceof Enemey && ((Unit) upTile).healthAmount <= 0) {
                        tempPlayerPosition = tile.getCoordinate();
                        tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate tempUpTile_position = upTile.getCoordinate();
                        Tile tempUpTile = LD[tile.getCoordinate().getY() - 1][tile.getCoordinate().getX()];
                        tile.setCoordinate(tempUpTile_position);
                        upTile.setCoordinate(tempPlayerPosition);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                        LD[upTile.getCoordinate().getY()][upTile.getCoordinate().getX()] = tempUpTile;
                    }
                }
                break;

            case "left":
                Tile leftTile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() - 1];
                if (leftTile.getClass() == EmptyTile.class) {
                    tempPlayerPosition = tile.getCoordinate();
                    tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate tempLeftTilePosition = leftTile.getCoordinate();
                    Tile tempLeftTile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() - 1];
                    tile.setCoordinate(tempLeftTilePosition);
                    leftTile.setCoordinate(tempPlayerPosition);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                    LD[leftTile.getCoordinate().getY()][leftTile.getCoordinate().getX()] = tempLeftTile;
                } else if ((tile instanceof Player && leftTile instanceof Enemey) || (tile instanceof Enemey && leftTile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (leftTile));
                    if (leftTile instanceof Enemey && ((Unit) leftTile).healthAmount <= 0) {
                        tempPlayerPosition = tile.getCoordinate();
                        tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate tempLeftTilePosition = leftTile.getCoordinate();
                        Tile tempLeftTile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() - 1];
                        tile.setCoordinate(tempLeftTilePosition);
                        leftTile.setCoordinate(tempPlayerPosition);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                        LD[leftTile.getCoordinate().getY()][leftTile.getCoordinate().getX()] = tempLeftTile;
                    }
                }
                break;

            case "right":
                Tile rightTile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() + 1];
                if (rightTile.getClass() == EmptyTile.class) {
                    tempPlayerPosition = tile.getCoordinate();
                    tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate tempRightTile_position = rightTile.getCoordinate();
                    Tile tempRightTile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() + 1];
                    tile.setCoordinate(tempRightTile_position);
                    rightTile.setCoordinate(tempPlayerPosition);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                    LD[rightTile.getCoordinate().getY()][rightTile.getCoordinate().getX()] = tempRightTile;
                } else if ((tile instanceof Player && rightTile instanceof Enemey) || (tile instanceof Enemey && rightTile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (rightTile));
                    if (rightTile instanceof Enemey && ((Unit) rightTile).healthAmount <= 0) {
                        tempPlayerPosition = tile.getCoordinate();
                        tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate tempRightTile_position = rightTile.getCoordinate();
                        Tile tempRightTile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() + 1];
                        tile.setCoordinate(tempRightTile_position);
                        rightTile.setCoordinate(tempPlayerPosition);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                        LD[rightTile.getCoordinate().getY()][rightTile.getCoordinate().getX()] = tempRightTile;
                    }
                }
                break;

            case "down":
                Tile downTile = LD[tile.getCoordinate().getY() + 1][tile.getCoordinate().getX()];
                if (downTile.getClass() == EmptyTile.class) {
                    tempPlayerPosition = tile.getCoordinate();
                    tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate tempDownTile_position = downTile.getCoordinate();
                    Tile tempDownTile = LD[tile.getCoordinate().getY() + 1][tile.getCoordinate().getX()];
                    tile.setCoordinate(tempDownTile_position);
                    downTile.setCoordinate(tempPlayerPosition);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                    LD[downTile.getCoordinate().getY()][downTile.getCoordinate().getX()] = tempDownTile;
                } else if ((tile instanceof Player && downTile instanceof Enemey) || (tile instanceof Enemey && downTile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (downTile));

                    if (downTile instanceof Enemey && ((Unit) downTile).healthAmount <= 0) {
                        tempPlayerPosition = tile.getCoordinate();
                        tempPlayer = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate tempDownTile_position = downTile.getCoordinate();
                        Tile tempDownTile = LD[tile.getCoordinate().getY() + 1][tile.getCoordinate().getX()];
                        tile.setCoordinate(tempDownTile_position);
                        downTile.setCoordinate(tempPlayerPosition);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = tempPlayer;
                        LD[downTile.getCoordinate().getY()][downTile.getCoordinate().getX()] = tempDownTile;
                    }
                }
                break;


        }
    }

    public static void Combat(Unit Attacker, Unit Defender) {
        System.out.println(Attacker.name + " engaged in combat with " + Defender.name + ".");
        System.out.println(Attacker.getDescription());
        System.out.println(Defender.getDescription());
        int attackPoints = (int) (Math.random() * (Attacker.attackPoints + 1));
        System.out.println(Attacker.name + " rolled " + attackPoints + " attack points.");
        int defencePoints = (int) (Math.random() * (Defender.defensePoints + 1));
        System.out.println(Defender.name + " rolled " + defencePoints + " defence points.");
        int totalDamage = Integer.max(0, attackPoints - defencePoints);
        Defender.healthAmount = Integer.max(0, Defender.healthAmount - totalDamage);
        System.out.println(Attacker.name + " dealt " + totalDamage + " damage to " + Defender.name + ".");
        if (Defender.getTile() == '@' && Defender.healthAmount <= 0) {
            System.out.println(Defender.name + "was killed by " + Attacker.name);
        }
    }
}
