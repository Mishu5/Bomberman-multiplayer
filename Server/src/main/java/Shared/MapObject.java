package Shared;

public class MapObject {

    private boolean destructible;

    private boolean transparent;

    private int positionX;
    private int positionY;

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public MapObject(int positionX, int positionY, boolean destructible, boolean transparent) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.destructible = destructible;
        this.transparent = transparent;
    }

}
