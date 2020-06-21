public abstract class Unit extends Tile {
    private String name;
    private int healthPool;
    private int healthAmount;
    private int attackPoints;
    private int defensePoints;
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
