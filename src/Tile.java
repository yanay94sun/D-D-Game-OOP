public abstract class Tile {
    protected char tile;
    protected Coordinate coordinate;

//    public Tile(char tile, Coordinate coordinate){
//        this.tile = tile;
//        this.coordinate = coordinate;
//    }

    public char getTile() {
        return this.tile;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
