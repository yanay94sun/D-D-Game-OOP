public class Rogue extends Player {
    private int cost;
    private int currentEnergy = 100;

    public Rogue(int x, int y, String name, int HP, int AP, int DP, int cost){
        super(x, y);
        this.cost = cost;
        this.name = name;
        this.healthPool = HP;
        this.healthAmount = HP;
        this.attackPoints = AP;
        this.defensePoints = DP;
    }

    @Override
    public void castAbility(Enemey[] level_enemies) {
        if (this.currentEnergy >= this.cost) {
            System.out.println(this.name+" used Fan of Knives");
            this.currentEnergy -= this.cost;
            int counter = 0;
            for (int i = 0; i < level_enemies.length; i++) {
                // check live enemy around
                if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < 2
                        && level_enemies[i].healthAmount > 0) //HERE WE NEED TO DO ARRAYLIST INSTEAD OF MAKE THEM ZERO
                    counter += 1;
            }
            Enemey[] target = new Enemey[counter];
            counter = 0;
            for (int i = 0; i < level_enemies.length; i++) { // NEED to check if we can do this once
                if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < 2
                        && level_enemies[i].healthAmount > 0) { //HERE WE NEED TO DO ARRAYLIST INSTEAD OF MAKE THEM ZERO
                    target[counter] = level_enemies[i];
                    counter += 1;
                }
            }
            for (int i = 0; i < counter; i++) {
                int random = (int) (Math.random() * target.length);
                int randomDefencePoints = (int) (Math.random() * target[random].defensePoints + 1); // WHY +1 ???
                System.out.println(target[random].name + " rolled " + randomDefencePoints + " Defense Points.");
                System.out.println(this.name + " hit " + target[random].name + "for " + this.attackPoints + " health amount.");
                target[random].healthAmount = Integer.max(0, target[random].healthAmount - this.attackPoints);
            }
        } else {
            System.out.println(this.name +" tried to cast Fan of Knives, but there is not enough Energy: " +this.currentEnergy+ "." );
        }
    }

    @Override
    public void gameTick() {
        this.currentEnergy = Math.min(this.currentEnergy + 10, 100);
    }

    @Override
    protected String getDescription() {
        String res = this.name + " \t\t\t  Health: " + this.healthAmount + "/" + this.healthPool + " \t\t\t Attack: " // NEED TO CHANGE THIS FORMAT
                + this.attackPoints + " \t\t\t Defense: " + this.defensePoints + " \t\t\t level: " + this.playerLevel +
                "\n\t\t\t Experience: " + this.experience + "/" + 50 * this.playerLevel +
                " \t\t\t Energy: " + this.currentEnergy + "/" + "100";
        return res;
    }

    public void levelUp(){
        super.levelUp();
        this.currentEnergy = 100;
        this.attackPoints += 3 * this.playerLevel;
        System.out.println(this.name + " reached level " +this.playerLevel+": +"+ this.playerLevel+" Health, +" + this.playerLevel +" Attack, +" + this.playerLevel + " Defense.");
    }
}
