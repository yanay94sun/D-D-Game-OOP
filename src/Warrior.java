import java.util.logging.Logger;

public class Warrior extends Player{
    private int abilityCooldown;
    private int remainingCooldown;

    public Warrior(int abilityCooldown, int x, int y, String Name, char Tile, int HP, int AP, int DP, int EXP, int PL) {
        super(x, y);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
        this.name = name;
        this.healthPool = HP;
        this.healthAmount = HP;
        this.attackPoints = AP;
        this.defensePoints = DP;
        this.experience = EXP;
        this.playerLevel = PL;
    }

    @Override
    public void castAbility(Enemey[] level_enemies) { //NEED TO CHANGE NAME level_enemies
        if (this.remainingCooldown == 0)
            try { //    MAYBE NOT NEED TRY CATCH
                this.remainingCooldown = this.abilityCooldown;
                this.healthAmount = Integer.min(this.healthAmount + (10 * this.defensePoints), this.healthPool);
                int cnt = 0; // NEED TO CHANGE IT TO COUNTER
                for (int i = 0; i < level_enemies.length; i++){
                    // check live enemy around
                    if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < 3
                            && level_enemies[i].healthAmount > 0) //HERE WE NEED TO DO ARRAYLIST INSTEAD OF MAKE THEM ZERO
                        cnt += 1;
                }
                Enemey[] target = new Enemey[cnt];
                cnt = 0;
                for (int i = 0; i < level_enemies.length; i++){ // NEED to check if we can do this once
                    if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < 3
                            && level_enemies[i].healthAmount > 0){ //HERE WE NEED TO DO ARRAYLIST INSTEAD OF MAKE THEM ZERO
                        target[cnt] = level_enemies[i];
                        cnt += 1;
                    }
                }
                if (cnt > 0){
                    int random = (int) (Math.random() * cnt);
                    int randomDefencePoints = (int) (Math.random() * target[random].defensePoints + 1); // WHY +1 ???
                    System.out.println(target[random].name + "rolled" + this.defensePoints + "Defense Points.");
                    int damage = Integer.max(0, (this.healthAmount / 10) - randomDefencePoints); // CAN REMOVER THIS LINE AND CONNECT IT TO THE NEXT
                    System.out.println(this.name + " hit" + target[random].name + "for" + damage + "health amount");
                    target[random].healthAmount = Integer.max(0, target[random].healthAmount - damage);
                }


            } catch (Exception e){
                System.out.println("Error message: ability cooldown more then zero");
            }
     }

    @Override
    public void gameTick() {
        this.checkExp();
        if (this.remainingCooldown > 0)
            this.remainingCooldown -= 1;
        }

    @Override
    protected String getDescription() {
        String res = this.name + " \t\t\t  Health: " + this.healthAmount + "/" + this.healthPool + " \t\t\t Attack: " // NEED TO CHANGE THIS FORMAT
                + this.attackPoints + " \t\t\t Defense: " + this.defensePoints + " \t\t\t level: " + this.playerLevel +
                "\n\t\t\t Experience: " + this.experience + "/" + 50 * this.playerLevel + " \t\t\t Cooldown: " + this.remainingCooldown + "/" + this.abilityCooldown;
        return res;
    }

    public void levelUp(){
        super.levelUp();
        this.remainingCooldown = 0;
        this.healthPool = (5 * this.playerLevel) + this.healthPool;
        this.attackPoints += 2 * this.playerLevel;
        this.defensePoints += this.playerLevel;
        System.out.println("...." + this.name + "went up a level !!");
    }
}
