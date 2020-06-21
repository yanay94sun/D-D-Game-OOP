public class Monster extends Enemey{
    private int visionRange;

    public Monster(int x, int y, String Name, char Tile, int HP, int AP, int DP, int VR, int EV){
        super(x, y);
        this.name = Name;
        this.tile = Tile;
        this.healthPool = HP;
        this.healthAmount = HP;
        this.attackPoints = AP;
        this.defensePoints = DP;
        this.visionRange = VR;
        this.experienceValue = EV;

    }

    public int playTurn(int player_x, int player_y){
        if (Math.sqrt(Math.pow(( this.getCoordinate().getX() - player_x) , 2) + Math.pow((this.getCoordinate().getY() - player_y), 2) ) < this.visionRange) {
            int dx = this.getCoordinate().getX() - player_x;
            int dy = this.getCoordinate().getY() - player_y;
            if (Math.abs(dx) > Math.abs(dy)) {
                if ( dx > 0) {
                    return 1; //move left
                }else {
                    return 2; //move right
                }
            }else  {
                if (dy > 0) {
                    return 3; //move up
                }else {
                    return 4; //move down
                }
            }
        }else{
            return (int) (Math.random() * 5); // random number between 0 , 5 for move
        }
    }

}
