public abstract class Player extends Unit {
    protected int experience;
    protected int playerLevel;
//    private Tile tile;
//    private Coordinate coordinate;

    public Player(int x, int y){
        this.playerLevel = 1;
        this.experience = 0;
        this.tile = '@';
        this.coordinate = new Coordinate(x, y);
    }

    public void levelUp(){
        this.experience = 0;
        this.playerLevel +=1;
        this.healthPool += 10 * this.playerLevel;
        this.healthAmount = this.healthPool;
        this.attackPoints += 4 * this.playerLevel;
        this.defensePoints += this.playerLevel;
    }

    public char getTile(){
        if (this.healthAmount > 0)
            return this.tile;
        return 'X';
    }

    public void checkExp(){
        if (this.experience >= 50 * this.playerLevel)
            this.levelUp();
    }

    @Override
    public String getDescription() {
        return null;
    }

    public abstract void castAbility(Enemey[] level_enemies);

    public abstract void gameTick();

}
