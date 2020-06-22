public class Mage extends Player{
    private int manaPool;
    private int currentMana;
    private int manaCost;
    private int spellPower;
    private int hitsCount;
    private int abilityRange;

    public Mage(int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange, int x, int y, String Name, char Tile, int HP, int AP, int DP, int EXP, int PL){
        super(x, y);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
        this.name = name;
        this.healthPool = HP;
        this.healthAmount = HP;
        this.attackPoints = AP;
        this.defensePoints = DP;
        this.experience = EXP;
        this.playerLevel = PL;

    }

    @Override
    public void castAbility(Enemey[] level_enemies) { // CHECK IF INSTEAD castAbility, DO abilityCast
        if (this.currentMana >= this.manaCost)
            try {
                this.currentMana -= this.manaCost;
                int hits = 0;
                int counter = 0;
                for (int i = 0; i < level_enemies.length; i++){
                    // check live enemy around
                    if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < this.abilityRange
                            && level_enemies[i].healthAmount > 0) //HERE WE NEED TO DO ARRAYLIST INSTEAD OF MAKE THEM ZERO
                        counter += 1;
                }
                Enemey[] target = new Enemey[counter];
                counter = 0;
                for (int i = 0; i < level_enemies.length; i++){ // NEED to check if we can do this once
                    if (Math.sqrt(Math.pow(this.getCoordinate().getX() - level_enemies[i].coordinate.getX(), 2) + Math.pow(this.coordinate.getY() - level_enemies[i].coordinate.getY(), 2)) < this.abilityRange
                            && level_enemies[i].healthAmount > 0){ //HERE WE NEED TO DO ARRAYLIST INSTEAD OF MAKE THEM ZERO
                        target[counter] = level_enemies[i];
                        counter += 1;
                    }
                }
                int lengthCounter = 0;
                while (hits < this.hitsCount && lengthCounter > target.length){
                    int random = (int) (Math.random() * target.length);
                    int randomDefencePoints = (int) (Math.random() * target[random].defensePoints + 1); // WHY +1 ???
                    System.out.println(target[random].name + "rolled" + randomDefencePoints + "Defense Points.");
                    System.out.println(this.name + " hit" + target[random].name + "for" + this.spellPower + "health amount");
                    target[random].healthAmount = Integer.max(0, target[random].healthAmount - this.spellPower);
                }
            } catch (Exception e){
                System.out.println("Error message: you don't have enough mana");
            }
    }

    @Override
    public void gameTick() {
        this.currentMana = Math.min(this.manaPool, (this.currentMana + 1) * this.playerLevel);
    }

    @Override
    protected String getDescription() {
        String res = this.name + " \t\t\t  Health: " + this.healthAmount + "/" + this.healthPool + " \t\t\t Attack: " // NEED TO CHANGE THIS FORMAT
                + this.attackPoints + " \t\t\t Defense: " + this.defensePoints + " \t\t\t level: " + this.playerLevel +
                "\n\t\t\t Experience: " + this.experience + "/" + 50 * this.playerLevel +
                " \t\t\t Mana: " + this.currentMana + "/" + this.manaPool + " \t\t\t Spell Power: " + this.spellPower;
        return res;
    }

    public void levelUp(){ // NEED TO DO INHERITANCE TO THIS
        super.levelUp();
        this.manaPool += 25 * this.playerLevel;
        this.currentMana = Math.min(this.currentMana + (manaPool / 4), this.manaPool);
        this.spellPower += 10 * this.playerLevel;
        System.out.println("...." + this.name + "went up a level !!");

    }
}
