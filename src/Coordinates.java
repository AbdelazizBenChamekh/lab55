public class Coordinates {
    private final Integer x;
    private final Integer y;

    /**
     * Constructor to create coordinates.
     * @param x X-coordinate (cannot be null)
     * @param y Y-coordinate (cannot be null, max: 405)
     */
    public Coordinates(Integer x, Integer y) {
        if (x == null) {
            throw new IllegalArgumentException("x cannot be null");
        }
        if (y == null || y > 405) {
            throw new IllegalArgumentException("y cannot be null and must be ≤ 405");
        }
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{x=" + x + ", y=" + y + "}";
    }
}

