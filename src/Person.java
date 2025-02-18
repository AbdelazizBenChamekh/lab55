import java.util.Objects;

public class Person {
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Integer weight; // Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; // Поле может быть null
    private Color hairColor; // Поле не может быть null
    private Country nationality; // Поле может быть null
    private Location location; // Поле не может быть null

    /**
     * Constructor to create a Person.
     * @param name Name of the Person (cannot be null or empty).
     * @param weight The weight of the person (must be positive and non-null).
     * @param eyeColor The person's eye color (can be null).
     * @param hairColor The person's hair color (cannot be null).
     * @param nationality The person's nationality (can be null).
     * @param location The person's location (cannot be null).
     * @throws IllegalArgumentException If any required field is invalid (e.g., null name, weight <= 0).
     */
    public Person(String name, Integer weight, Color eyeColor, Color hairColor, Country nationality, Location location) {
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

    // Getters
    public String getName() {
        return name;
    }

    public Integer getWeight() {
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
    } // FIXED: Added missing closing bracket

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
                weight.equals(person.weight) &&
                Objects.equals(eyeColor, person.eyeColor) &&
                Objects.equals(hairColor, person.hairColor) && // FIXED: Using `Objects.equals()`
                Objects.equals(nationality, person.nationality) && // FIXED: Using `Objects.equals()`
                location.equals(person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, eyeColor, hairColor, nationality, location);
    }
}




