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
        if (this.arrowsCount < 0) {
            System.out.println(this.name + " used SHOOT ! , shooting arrows for " + this.attackPoints + " damage.");
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
