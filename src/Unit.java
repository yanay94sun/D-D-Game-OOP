public abstract class Unit extends Tile {
    protected String name;
    protected int healthPool;
    protected int healthAmount;
    protected int attackPoints;
    protected int defensePoints;
    public abstract String getDescription();

    public String getName() {
        return name;
    }

    public int getHealthPool() {
        return healthPool;
    }
    public int getHealthAmount() {
        return healthAmount;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }




}
