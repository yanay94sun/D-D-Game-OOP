
public class Warrior extends Player{
    private int abilityCooldown;
    private int remainingCooldown = 0;

    public Warrior(int x, int y, String name, int HP, int AP, int DP, int abilityCooldown) {
        super(x, y);
        this.abilityCooldown = abilityCooldown;
        this.name = name;
        this.healthPool = HP;
        this.healthAmount = HP;
        this.attackPoints = AP;
        this.defensePoints = DP;
    }

    @Override
    public void castAbility(Enemey[] level_enemies) { //NEED TO CHANGE NAME level_enemies
        if (this.remainingCooldown == 0){
            System.out.println(this.name+" used Avenger's Shield, healing for " + 10*this.defensePoints+".");
            this.remainingCooldown = this.abilityCooldown;
            this.healthAmount = Integer.min(this.healthAmount + (10 * this.defensePoints), this.healthPool);
            int counter = 0;
            for (int i = 0; i < level_enemies.length; i++){
                if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < 3
                        && level_enemies[i].healthAmount > 0)
                    counter += 1;
            }
            Enemey[] target = new Enemey[counter];
            counter = 0;
            for (int i = 0; i < level_enemies.length; i++){
                if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < 3
                        && level_enemies[i].healthAmount > 0){
                    target[counter] = level_enemies[i];
                    counter += 1;
                }
            }
            if (counter > 0){
                int random = (int) (Math.random() * counter);
                int randomDefencePoints = (int) (Math.random() * target[random].defensePoints + 1); // WHY +1 ???
                System.out.println(target[random].name + " rolled " + this.defensePoints + " Defense Points.");
                int damage = Integer.max(0, (this.healthAmount / 10) - randomDefencePoints); // CAN REMOVER THIS LINE AND CONNECT IT TO THE NEXT
                System.out.println(this.name + " hit " + target[random].name + " for " + damage + " health amount ");
                target[random].healthAmount = Integer.max(0, target[random].healthAmount - damage);
            }
        }

        else{
            System.out.println(this.name + " tried to cast Avenger's Shield, but there is a cooldown: " +this.remainingCooldown+ "." );
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
        String res = this.name + " \t\t\t  Health: " + this.healthAmount + "/" + this.healthPool + " \t\t\t Attack: "
                + this.attackPoints + " \t\t\t Defense: " + this.defensePoints + " \t\t\t level: " + this.playerLevel +
                "\n\t\t\t Experience: " + this.experience + "/" + 50 * this.playerLevel + " \t\t\t Cooldown: " + this.remainingCooldown + "/" + this.abilityCooldown;
        return res;
    }

    public void levelUp(){
        super.levelUp();
        this.healthPool = (5 * this.playerLevel) + this.healthPool;
        this.healthAmount = this.healthPool;
        this.attackPoints += 2 * this.playerLevel;
        this.defensePoints += this.playerLevel;
        System.out.println(this.name + " reached level " +this.playerLevel+": "+ this.playerLevel+" Health, " + this.playerLevel +" Attack, " + this.playerLevel + " Defense.");
    }
}
