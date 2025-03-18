import java.util.Objects;

public class Person {
    private String name; // Cannot be null or empty
    private Double weight; // Cannot be null, must be > 0
    private Color eyeColor; // Can be null
    private Color hairColor; // Cannot be null
    private Country nationality; // Can be null
    private Location location; // Cannot be null

    /**
     * Constructor to create a Person with all attributes.
     */
    public Person(String name, Double weight, Color eyeColor, Color hairColor, Country nationality, Location location) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (weight == null || weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        if (hairColor == null) {
            throw new IllegalArgumentException("Hair color cannot be null.");
        }
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null.");
        }

        this.name = name;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    /**
     * Constructor to create a Person with minimal attributes (for group admins).
     * Assuming adminAge corresponds to weight.
     */
    public Person(String name, Double weight) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (weight == null || weight <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }

        this.name = name;
        this.weight = weight;
        this.hairColor = Color.BLACK; // Default value
        this.location = new Location(0, 0, "Unknown"); // Default location
    }

    public String getName() {
        return name;
    }

    public Double getWeight() {
        return weight;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", eyeColor=" + (eyeColor != null ? eyeColor : "N/A") +
                ", hairColor=" + hairColor +
                ", nationality=" + (nationality != null ? nationality : "N/A") +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
                weight.equals(person.weight) &&
                Objects.equals(eyeColor, person.eyeColor) &&
                Objects.equals(hairColor, person.hairColor) &&
                Objects.equals(nationality, person.nationality) &&
                location.equals(person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, eyeColor, hairColor, nationality, location);
    }
}




