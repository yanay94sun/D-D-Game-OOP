import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner char_sc = new Scanner(System.in);
        char c;
        int i;
        boolean gamecomplete = false;
        boolean gameover = false;
        boolean valid;

        Player player_tile = new Warrior(0, 0, "Jon Snow", 300, 30, 4, 3);

        boolean choose_again = true;
        while (choose_again) {
            System.out.println("Select player:");
            System.out.println("1. Jon Snow \t\t\t Health: 300/300 \t\t\t Attack: 30 \t\t\t Defense: 4 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Cooldown: 0/3");
            System.out.println("2. The Hound \t\t\t Health: 400/400 \t\t\t Attack: 20 \t\t\t Defense: 6 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Cooldown: 0/5");
            System.out.println("3. Melisandre \t\t\t Health: 100/100 \t\t\t Attack: 5 \t\t\t Defense: 1 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Mana: 75/300 \t\t\t Spell Power: 15");
            System.out.println("4. Thoros of Myr \t\t\t Health: 250/250 \t\t\t Attack: 25 \t\t\t Defense: 4 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Mana: 37/150 \t\t\t Spell Power: 20");
            System.out.println("5. Arya Stark \t\t\t Health: 150/150 \t\t\t Attack: 40 \t\t\t Defense: 2 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Energy: 100/100");
            System.out.println("6. Bronn \t\t\t Health: 250/250 \t\t\t Attack: 35 \t\t\t Defense: 3 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Energy: 100/100");

            c = char_sc.next().charAt(0);

            switch (c) {
                case 49:
                    player_tile = new Warrior(0, 0, "Jon Snow", 300, 30, 4, 3);
                    choose_again = false;
                    break;
                case 50:
                    player_tile = new Warrior(0, 0, "The Hound", 400, 20, 6, 5);
                    choose_again = false;
                    break;
                case 51:
                    player_tile = new Mage(0, 0, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
                    choose_again = false;
                    break;
                case 52:
                    player_tile = new Mage(0, 0, "Thoros of Myr", 250, 25, 4, 150, 20, 20, 3, 4);
                    choose_again = false;
                    break;
                case 53:
                    player_tile = new Rogue(0, 0, "Arya Stark", 150, 40, 2, 20);
                    choose_again = false;
                    break;
                case 54:
                    player_tile = new Rogue(0, 0, "Bronn", 250, 35, 3, 50);
                    choose_again = false;
                    break;
            }
        }

        System.out.println("You have selected:\n" + player_tile.name);
        int level_num = 1;
        int y;
        int x;
        Tile[][] level_data = load_next_level(player_tile, level_num, args[0]);
        int y_size = level_data.length;
        int x_size = level_data[0].length;
        int enemy_counter = 0;

        for (y = 0; y < y_size; y++) {
            for (x = 0; x < x_size; x++) {
                if (level_data[y][x] instanceof Enemey) {
                    enemy_counter += 1;
                }
            }
        }

        Enemey[] level_enemies = new Enemey[enemy_counter];
        enemy_counter = 0;

        for (y = 0; y < y_size; y++) {
            for (x = 0; x < x_size; x++) {
                if (level_data[y][x] instanceof Enemey) {
                    level_enemies[enemy_counter] = (Enemey) level_data[y][x];
                    enemy_counter += 1;
                }
            }
        }

        while (true) {
            for (y = 0; y < y_size; y++) {
                for (x = 0; x < x_size; x++) {
                    System.out.println(level_data[y][x].getTile());
                }
                System.out.println("\n");
            }
            System.out.println("\n" + player_tile.getDescription());

            valid = false;
            while (!valid) {
                c = char_sc.next().charAt(0);
                switch ((int) c) {
                    case 119:
                        move(level_data, player_tile, "up");
                        valid = true;
                        break;
                    case 115:
                        move(level_data, player_tile, "down");
                        valid = true;
                    case 97:
                        move(level_data, player_tile, "left");
                        valid = true;
                    case 100:
                        move(level_data, player_tile, "right");
                        valid = true;
                        break;
                    case 101:
                        player_tile.castAbility(level_enemies);
                        valid = true;
                        break;
                    case 113:
                        valid = true;
                        break;

                }
            }

            Monster monster;
            Trap trap;
            enemy_counter = 0;
            for (i = 0; i < level_enemies.length; i++) {
                if (level_enemies[i].getClass() == Monster.class && level_enemies[i].healthAmount > 0) {
                    enemy_counter += 1;
                    monster = (Monster) level_enemies[i];
                    switch (monster.playTurn(player_tile.getCoordinate().getX(), player_tile.getCoordinate().getY())) {
                        case 0:
                            break;
                        case 1:
                            move(level_data, monster, "left");
                            break;
                        case 2:
                            move(level_data, monster, "right");
                            break;
                        case 3:
                            move(level_data, monster, "up");
                            break;
                        case 4:
                            move(level_data, monster, "down");
                            break;

                    }
                }

                if (level_enemies[i].getClass() == Trap.class && level_enemies[i].healthAmount > 0) {
                    enemy_counter += 1;
                    trap = (Trap) level_enemies[i];
                    trap.gameTick();
                    if ((Math.sqrt(Math.pow((trap.getCoordinate().getX() - player_tile.getCoordinate().getX()), 2) + Math.pow((trap.getCoordinate().getY() - player_tile.getCoordinate().getY()), 2)) < 2)) {
                        Combat(trap, player_tile);
                    }
                }

                if (level_enemies[i].healthAmount <= 0 && level_enemies[i].alive) {
                    level_enemies[i].alive = false;
                    level_data[level_enemies[i].getCoordinate().getY()][level_enemies[i].getCoordinate().getX()] =
                            new EmptyTile(level_enemies[i].getCoordinate().getX(), level_enemies[i].getCoordinate().getY());
                    player_tile.experience += level_enemies[i].experienceValue;
                    System.out.println(level_enemies[i].name + " died. " + player_tile.name + " gained "
                            + level_enemies[i].experienceValue + " experience.");
                }

                if (player_tile.healthAmount <= 0) break;

            }

            if (player_tile.healthAmount <= 0) {
                gameover = true;
                break;
            } else if (enemy_counter == 0) {
                try {
                    level_num += 1;
                    level_data = load_next_level(player_tile, level_num, args[0]);
                    y_size = level_data.length;
                    x_size = level_data[0].length;
                    enemy_counter = 0;

                    for (x = 0; y < y_size; y++) {
                        for (x = 0; x < x_size; x++) {
                            if (level_data[y][x] instanceof Enemey) {
                                enemy_counter += 1;
                            }
                        }
                    }


                    level_enemies = new Enemey[enemy_counter];
                    enemy_counter = 0;

                    for (y = 0; y < y_size; y++) {
                        for (x = 0; x < x_size; x++) {
                            if (level_data[y][x] instanceof Enemey) {
                                level_enemies[enemy_counter] = (Enemey) level_data[y][x];
                                enemy_counter += 1;
                            }
                        }
                    }
                } catch (Exception e) {
                    gamecomplete = true;
                }
            }

            player_tile.gameTick();
            if (gamecomplete) break;


        }

        if (gameover) {
            System.out.println("You lost.");
            for (y = 0; y < y_size; y++) {
                for (x = 0; x < x_size; x++) {
                    System.out.println(level_data[y][x].getTile());
                }
                System.out.println("\n");
            }
            System.out.println("\n" + player_tile.getDescription());
            System.out.println("Game Over.");
        }

        if (gamecomplete) {
            System.out.println("You won!");
        }
    }

    public static Tile[][] load_next_level(Player player_tile, int level_num, String path) throws FileNotFoundException {
        File file = new File(path + "\\level" + level_num + ".txt");
        Scanner file_sc = new Scanner(file);
        int y_size = 0;
        String temp = "";

        while (file_sc.hasNextLine()) {
            y_size += 1;
            temp = file_sc.nextLine();
        }
        int x_size = temp.length();
        Tile[][] level_data = new Tile[y_size][x_size];
        int y = 0;
        int x;
        file_sc = new Scanner(file);
        while (file_sc.hasNextLine()) {
            String curr_line = file_sc.nextLine();
            for (x = 0; x < temp.length(); x++) {
                char cell = curr_line.charAt(x);

                switch ((int) cell) {
                    case 35: // #
                        level_data[y][x] = new WallTile(x, y);
                        break;
                    case 46: // .
                        level_data[y][x] = new EmptyTile(x, y);
                        break;
                    case 64: // @
                        level_data[y][x] = player_tile;
                        player_tile.setCoordinate(new Coordinate(x, y));
                        break;
                    case 115: // s
                        level_data[y][x] = new Monster(x, y, "Lannister Solider", 's', 80, 8, 3, 3, 25);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 107: // k
                        level_data[y][x] = new Monster(x, y, "Lannister Knight", 'k', 200, 14, 8, 4, 50);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 113: // q
                        level_data[y][x] = new Monster(x, y, "Queen’s Guard", 'q', 400, 20, 15, 5, 100);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 122: // z
                        level_data[y][x] = new Monster(x, y, "Wright", 'z', 600, 30, 15, 3, 100);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 98: // b
                        level_data[y][x] = new Monster(x, y, "Bear-Wright", 'b', 1000, 75, 30, 4, 250);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 103: // g
                        level_data[y][x] = new Monster(x, y, "Giant-Wright", 'g', 1500, 100, 40, 5, 500);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 119: // w
                        level_data[y][x] = new Monster(x, y, "White Walker", 'w', 2000, 150, 50, 6, 1000);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 77: // M
                        level_data[y][x] = new Monster(x, y, "The Mountain ", 'M', 1000, 60, 25, 6, 500);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 67: // C
                        level_data[y][x] = new Monster(x, y, "Queen Cersei ", 'C', 100, 10, 10, 1, 1000);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 75: // K
                        level_data[y][x] = new Monster(x, y, "Night’s King ", 'K', 5000, 300, 150, 8, 5000);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 66: // B
                        level_data[y][x] = new Trap(x, y, "Bonus Trap ", 'B', 1, 1, 1, 250, 1, 5);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 81: // Q
                        level_data[y][x] = new Trap(x, y, "Queen’s Trap ", 'Q', 250, 50, 10, 100, 3, 7);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                    case 68: // D
                        level_data[y][x] = new Trap(x, y, "Death Trap ", 'D', 500, 100, 20, 250, 1, 10);
                        level_data[y][x].setCoordinate(new Coordinate(x, y));
                        break;
                }
            }
            y += 1;
        }
        return level_data;
    }

    public static void move(Tile[][] LD, Tile tile, String direrection) {
        Coordinate temp_player_position;
        Tile temp_player;
        switch (direrection) {
            case "up":
                Tile up_tile = LD[tile.getCoordinate().getY() - 1][tile.getCoordinate().getX()];
                if (up_tile.getClass() == EmptyTile.class) {
                    temp_player_position = tile.getCoordinate();
                    temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate temp_up_tile_position = up_tile.getCoordinate();
                    Tile temp_up_tile = LD[tile.getCoordinate().getY() - 1][tile.getCoordinate().getX()];
                    tile.setCoordinate(temp_up_tile_position);
                    up_tile.setCoordinate(temp_player_position);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                    LD[up_tile.getCoordinate().getY()][up_tile.getCoordinate().getX()] = temp_up_tile;
                } else if ((tile instanceof Player && up_tile instanceof Enemey) || (tile instanceof Enemey && up_tile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (up_tile));
                    if (up_tile instanceof Enemey && ((Unit) up_tile).healthAmount <= 0) {
                        temp_player_position = tile.getCoordinate();
                        temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate temp_up_tile_position = up_tile.getCoordinate();
                        Tile temp_up_tile = LD[tile.getCoordinate().getY() - 1][tile.getCoordinate().getX()];
                        tile.setCoordinate(temp_up_tile_position);
                        up_tile.setCoordinate(temp_player_position);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                        LD[up_tile.getCoordinate().getY()][up_tile.getCoordinate().getX()] = temp_up_tile;
                    }
                }
                break;

            case "left":
                Tile left_tile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() - 1];
                if (left_tile.getClass() == EmptyTile.class) {
                    temp_player_position = tile.getCoordinate();
                    temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate temp_left_tile_position = left_tile.getCoordinate();
                    Tile temp_left_tile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() - 1];
                    tile.setCoordinate(temp_left_tile_position);
                    left_tile.setCoordinate(temp_player_position);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                    LD[left_tile.getCoordinate().getY()][left_tile.getCoordinate().getX()] = temp_left_tile;
                } else if ((tile instanceof Player && left_tile instanceof Enemey) || (tile instanceof Enemey && left_tile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (left_tile));
                    if (left_tile instanceof Enemey && ((Unit) left_tile).healthAmount <= 0) {
                        temp_player_position = tile.getCoordinate();
                        temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate temp_left_tile_position = left_tile.getCoordinate();
                        Tile temp_left_tile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() - 1];
                        tile.setCoordinate(temp_left_tile_position);
                        left_tile.setCoordinate(temp_player_position);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                        LD[left_tile.getCoordinate().getY()][left_tile.getCoordinate().getX()] = temp_left_tile;
                    }
                }
                break;

            case "right":
                Tile right_tile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() + 1];
                if (right_tile.getClass() == EmptyTile.class) {
                    temp_player_position = tile.getCoordinate();
                    temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate temp_right_tile_position = right_tile.getCoordinate();
                    Tile temp_right_tile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() + 1];
                    tile.setCoordinate(temp_right_tile_position);
                    right_tile.setCoordinate(temp_player_position);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                    LD[right_tile.getCoordinate().getY()][right_tile.getCoordinate().getX()] = temp_right_tile;
                } else if ((tile instanceof Player && right_tile instanceof Enemey) || (tile instanceof Enemey && right_tile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (right_tile));
                    if (right_tile instanceof Enemey && ((Unit) right_tile).healthAmount <= 0) {
                        temp_player_position = tile.getCoordinate();
                        temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate temp_right_tile_position = right_tile.getCoordinate();
                        Tile temp_right_tile = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX() + 1];
                        tile.setCoordinate(temp_right_tile_position);
                        right_tile.setCoordinate(temp_player_position);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                        LD[right_tile.getCoordinate().getY()][right_tile.getCoordinate().getX()] = temp_right_tile;
                    }
                }
                break;

            case "down":
                Tile down_tile = LD[tile.getCoordinate().getY() + 1][tile.getCoordinate().getX()];
                if (down_tile.getClass() == EmptyTile.class) {
                    temp_player_position = tile.getCoordinate();
                    temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                    Coordinate temp_down_tile_position = down_tile.getCoordinate();
                    Tile temp_down_tile = LD[tile.getCoordinate().getY() + 1][tile.getCoordinate().getX()];
                    tile.setCoordinate(temp_down_tile_position);
                    down_tile.setCoordinate(temp_player_position);
                    LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                    LD[down_tile.getCoordinate().getY()][down_tile.getCoordinate().getX()] = temp_down_tile;
                } else if ((tile instanceof Player && down_tile instanceof Enemey) || (tile instanceof Enemey && down_tile instanceof Player)) {
                    Combat((Unit) (tile), (Unit) (down_tile));
                    if (down_tile instanceof Enemey && ((Unit) down_tile).healthAmount <= 0) {
                        temp_player_position = tile.getCoordinate();
                        temp_player = LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()];
                        Coordinate temp_down_tile_position = down_tile.getCoordinate();
                        Tile temp_down_tile = LD[tile.getCoordinate().getY() + 1][tile.getCoordinate().getX()];
                        tile.setCoordinate(temp_down_tile_position);
                        down_tile.setCoordinate(temp_player_position);
                        LD[tile.getCoordinate().getY()][tile.getCoordinate().getX()] = temp_player;
                        LD[down_tile.getCoordinate().getY()][down_tile.getCoordinate().getX()] = temp_down_tile;
                    }
                }
                break;


        }
    }

    public static void Combat(Unit Attacker, Unit Defender) {
        System.out.println(Attacker.name + "engaged in combat with " + Defender.name + ".");
        System.out.println(Attacker.getDescription());
        System.out.println(Defender.getDescription());
        int attack_points = (int) (Math.random() * (Attacker.attackPoints + 1));
        System.out.println(Attacker.name + " rolled " + attack_points + " attack points.");
        int defence_points = (int) (Math.random() * (Defender.defensePoints + 1));
        System.out.println(Defender.name + " rolled " + defence_points + " defence points.");
        int total_damage = Integer.max(0, attack_points - defence_points);
        Defender.healthAmount = Integer.max(0, Defender.healthAmount - total_damage);
        System.out.println(Attacker.name + " dealt " + total_damage + " damage to " + Defender.name + ".");
        if (Defender.getTile() == '@' && Defender.healthAmount <= 0) {
            System.out.println(Defender.name + "was killed by " + Attacker.name);
        }
    }
}
