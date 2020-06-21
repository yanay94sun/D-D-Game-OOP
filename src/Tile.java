public abstract class Tile {
    private char tile;
    private Coordinate coordinate;

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
