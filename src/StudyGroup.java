import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class StudyGroup implements Comparable<StudyGroup> {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
    private long studentsCount;
    private Long shouldBeExpelled;
    private FormOfEducation formOfEducation;
    private Semester semesterEnum;
    private Person groupAdmin;
    private Location location;

    // ✅ Full Constructor
    public StudyGroup(String name, Coordinates coordinates, long studentsCount, Long shouldBeExpelled,
                      FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin, Location location) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Name cannot be null or empty.");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("❌ Coordinates cannot be null.");
        }
        if (studentsCount <= 0) {
            throw new IllegalArgumentException("❌ Students count must be greater than 0.");
        }
        if (shouldBeExpelled != null && shouldBeExpelled <= 0) {
            throw new IllegalArgumentException("❌ Should be expelled count must be greater than 0.");
        }
        if (formOfEducation == null) {
            throw new IllegalArgumentException("❌ FormOfEducation cannot be null.");
        }
        if (groupAdmin == null) {
            throw new IllegalArgumentException("❌ GroupAdmin cannot be null.");
        }

        this.id = idGenerator.getAndIncrement();
        this.creationDate = LocalDate.now();
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
        this.location = location;
    }

    // ✅ Constructor without Location (Optional)
    public StudyGroup(String name, Coordinates coordinates, long studentsCount, Long shouldBeExpelled,
                      FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin) {
        this(name, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin, null);
    }

    // ✅ Safe `toString()` with null checks
    @Override
    public String toString() {
        return "StudyGroup{" +
                "ID=" + id +
                ", Name='" + name + '\'' +
                ", Coordinates=(" + (coordinates != null ? coordinates.getX() + ", " + coordinates.getY() : "N/A") + ')' +
                ", StudentsCount=" + studentsCount +
                ", ShouldBeExpelled=" + (shouldBeExpelled != null ? shouldBeExpelled : "N/A") +
                ", FormOfEducation=" + formOfEducation +
                ", Semester=" + (semesterEnum != null ? semesterEnum : "N/A") +
                ", GroupAdmin=" + (groupAdmin != null ? groupAdmin : "None") +
                ", Location=" + (location != null ? location.toString() : "None") +
                '}';
    }

    // ✅ Safe CSV conversion
    public String toCSV() {
        return String.join(",",
                String.valueOf(id),
                name,
                (coordinates != null ? String.valueOf(coordinates.getX()) : ""),
                (coordinates != null ? String.valueOf(coordinates.getY()) : ""),
                String.valueOf(studentsCount),
                (shouldBeExpelled != null ? String.valueOf(shouldBeExpelled) : ""),
                formOfEducation.toString(),
                (semesterEnum != null ? semesterEnum.toString() : ""),
                (groupAdmin != null ? groupAdmin.getName() : ""),
                (groupAdmin != null && groupAdmin.getWeight() != null ? String.valueOf(groupAdmin.getWeight()) : ""),
                (groupAdmin != null && groupAdmin.getEyeColor() != null ? groupAdmin.getEyeColor().toString() : ""),
                (groupAdmin != null && groupAdmin.getHairColor() != null ? groupAdmin.getHairColor().toString() : ""),
                (groupAdmin != null && groupAdmin.getNationality() != null ? groupAdmin.getNationality().toString() : ""),
                (location != null ? String.valueOf(location.getX()) : ""),
                (location != null ? String.valueOf(location.getY()) : ""),
                (location != null ? location.getName() : "")
        );
    }

    // ✅ Implementing compareTo() based on studentsCount
    @Override
    public int compareTo(StudyGroup other) {
        return Long.compare(this.studentsCount, other.studentsCount);
    }

    // ✅ Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public long getStudentsCount() { return studentsCount; }
    public Long getShouldBeExpelled() { return shouldBeExpelled; }
    public FormOfEducation getFormOfEducation() { return formOfEducation; }
    public Semester getSemesterEnum() { return semesterEnum; }
    public Person getGroupAdmin() { return groupAdmin; }
    public Location getLocation() { return location; }
    public LocalDate getCreationDate() { return creationDate; }

    // ✅ Setters (if needed)
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    public void setStudentsCount(long studentsCount) { this.studentsCount = studentsCount; }
    public void setShouldBeExpelled(Long shouldBeExpelled) { this.shouldBeExpelled = shouldBeExpelled; }
    public void setFormOfEducation(FormOfEducation formOfEducation) { this.formOfEducation = formOfEducation; }
    public void setSemesterEnum(Semester semesterEnum) { this.semesterEnum = semesterEnum; }
    public void setGroupAdmin(Person groupAdmin) { this.groupAdmin = groupAdmin; }
    public void setLocation(Location location) { this.location = location; }
}

