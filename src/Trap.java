public class Trap extends Enemey{
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;
    private boolean visible;

    public Trap(int x, int y, String name, char tile, int HP, int AP, int DP, int EV, int VT, int IT){
        super(x, y);
        this.visibilityTime = VT;
        this.invisibilityTime = IT;
        this.name = name;
        this.tile = tile;
        this.healthPool = HP;
        this.healthAmount = HP;
        this.attackPoints = AP;
        this.defensePoints = DP;
        this.experienceValue = EV;
    }

    public void gameTick() {
        this.visible = this.ticksCount < this.visibilityTime;
        if (this.ticksCount == (this.visibilityTime + this.invisibilityTime))
            this.ticksCount = 0;
        else
            this.ticksCount += 1;
    }
    public char getTile(){
        if (this.visible)
            return this.tile;
        return '.';
    }
}
