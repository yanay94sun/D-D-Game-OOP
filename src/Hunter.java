public class Hunter extends Player{
    int shootingRange;
    int arrowsCount;
    int ticksCount;

    public Hunter(int x, int y, String name, int HP, int AP, int DP, int shootingRange) {
        super(x, y);
        this.shootingRange = shootingRange;
        this.arrowsCount = 10;
        this.ticksCount = 0;
        this.name = name;
        this.healthPool = HP;
        this.healthAmount = HP;
        this.attackPoints = AP;
        this.defensePoints = DP;

    }
    public void levelUp(){
        super.levelUp();
        this.arrowsCount += 10 * this.playerLevel;
        this.attackPoints += 2 * this.playerLevel;
        this.defensePoints += this.playerLevel;
        System.out.println(this.name + " reached level " +this.playerLevel+": "+ this.playerLevel+" Health, " + this.playerLevel +" Attack, " + this.playerLevel + " Defense, " + this.arrowsCount + " arrows.");
    }

    @Override
    public void castAbility(Enemey[] level_enemies) {
        if (this.arrowsCount == 0) {
            System.out.println(this.name + " tried to cast SHOOT , but there is no arrows left: " +this.arrowsCount+ " arrows." );
        }
        else{
            int counter = 0;
            for (int i = 0; i < level_enemies.length; i++){
                if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < this.shootingRange
                        && level_enemies[i].healthAmount > 0)
                    counter += 1;
            }
            Enemey[] target = new Enemey[counter];
            counter = 0;
            for (int i = 0; i < level_enemies.length; i++){
                if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < this.shootingRange
                        && level_enemies[i].healthAmount > 0){
                    target[counter] = level_enemies[i];
                    counter += 1;
                }
            }
            if (counter == 0){
                System.out.println(this.name + " tried to cast SHOOT , but there is no enemies in the shooting range.");
            }
            else{
                this.arrowsCount --;
                System.out.println(this.name + " used SHOOT ! , shooting arrows for " + this.attackPoints + " damage.");
                Enemey closestEnemey = target[0];
                for (int i = 0; i < target.length; i++){
                    if (Math.sqrt(Math.pow(this.getCoordinate().getX() - target[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - target[i].coordinate.getY(), 2)) > Math.sqrt(Math.pow(this.getCoordinate().getX() - closestEnemey.coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - closestEnemey.coordinate.getY(), 2))){
                        closestEnemey = target[i];
                    }
                }
                int random = (int) (Math.random() * counter);
                int randomDefencePoints = (int) (Math.random() * closestEnemey.defensePoints + 1);
                System.out.println(closestEnemey.name + " rolled " + this.defensePoints + " Defense Points.");
                int damage = Integer.max(0, (this.healthAmount / 10) - randomDefencePoints);
                System.out.println(this.name + " hit " + closestEnemey.name + " for " + damage + " health amount ");
                closestEnemey.healthAmount = Integer.max(0, target[random].healthAmount - damage);

            }

        }

    }

    @Override
    public void gameTick() {
        this.checkExp();
        if (this.ticksCount == 10){
            this.arrowsCount += this.playerLevel;
            this.ticksCount = 0;
        }
        else {
            this.ticksCount ++;
        }
    }

    @Override
    protected String getDescription() {
        String res = this.name + " \t\t\t  Health: " + this.healthAmount + "/" + this.healthPool + " \t\t\t Attack: "
                + this.attackPoints + " \t\t\t Defense: " + this.defensePoints + " \t\t\t level: " + this.playerLevel +
                "\n\t\t\t Experience: " + this.experience + "/" + 50 * this.playerLevel + " \t\t\t arrows: " + this.arrowsCount + " \t\t\t shooting range: " + this.shootingRange;
        return res;
    }
}
