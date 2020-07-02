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

        Player player_tile = new Warrior(0,0,"Jon Snow", 300, 30, 4, 3);

        boolean choose_again = true;
        while (choose_again) {
            System.out.println("Select player:");
            System.out.println("1. Jon Snow \t\t\t Health: 300/300 \t\t\t Attack: 30 \t\t\t Defense: 4 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Cooldown: 0/3");
            System.out.println("2. The Hound \t\t\t Health: 400/400 \t\t\t Attack: 20 \t\t\t Defense: 6 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Cooldown: 0/5");
            System.out.println("3. Melisandre \t\t\t Health: 100/100 \t\t\t Attack: 5 \t\t\t Defense: 1 \t\t\t " +
                    "Level: 1 \n\t\t\t Experience: 0/50 \t\t\t Mana: 75/300 \t\t\t Spell Power: 15");
            System.out.println("4. Thoros of myr \t\t\t Health: 250/250 \t\t\t Attack: 25 \t\t\t Defense: 4 \t\t\t " +
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
                    player_tile = new Mage(0, 0, "Thoros of myr", 250, 25, 4, 150, 20, 20, 3, 4);
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

        for (y=0; y < y_size; y++) {
            for (x=0; x < x_size; x++) {
                if (level_data[y][x] instanceof Enemey) {
                    enemy_counter += 1;
                }
            }
        }

        Enemey[] level_enemies = new Enemey[enemy_counter];
        enemy_counter = 0;

        for (y=0; y < y_size; y++) {
            for (x=0; x < x_size; x++) {
                if (level_data[y][x] instanceof Enemey){
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
                        move(level_data, player_tile);
                        valid = true;
                    case 113:
                        move(level_data, player_tile);
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
                }








        }








    }


}
