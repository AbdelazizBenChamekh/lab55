import java.util.Objects;

/**
 * Represents a geographical location.
 */
public class Location {
    private final Integer x; // Cannot be null
    private final double y;
    private final String name; // Can be null, but if not null, cannot be empty

    /**
     * Constructs a new {@code Location} object.
     *
     * @param x    X coordinate (cannot be null).
     * @param y    Y coordinate.
     * @param name Location name (can be null, but must not be empty if provided).
     * @throws IllegalArgumentException If x is null or name is an empty string.
     */
    public Location(Integer x, double y, String name) {
        if (x == null) {
            throw new IllegalArgumentException("X coordinate cannot be null.");
        }
        if (name != null && name.trim().isEmpty()) {
            throw new IllegalArgumentException("Location name cannot be empty.");
        }

        this.x = x;
        this.y = y;
        this.name = name;
    }

    // Getters
    public Integer getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + (name != null ? name : "N/A") + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.y, y) == 0 &&
                x.equals(location.x) &&
                Objects.equals(name, location.name);
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }
}
