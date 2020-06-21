public abstract class Enemey extends Unit  {

    protected boolean alive = true;
    protected int experienceValue;

    protected Enemey(int x, int y){
        this.coordinate = new Coordinate(x, y);

    }

    protected String getDescription(){
        String res = this.name + " \t\t\t  Health: " + this.healthAmount + "/" + this.healthPool + " \t\t\t Attack: "
                + this.attackPoints + " \t\t\t Defense: " + this.defensePoints + " \t\t\t Experience Value " + this.experienceValue;
        return res;
    }


}
