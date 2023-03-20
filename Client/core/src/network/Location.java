package network;

public class Location {
    float x, y;

    /**
     * Empty constructor is needed here to receive Player objects over the network.
     * @param x
     * @param y
     */
    public Location(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Location() {}
}
