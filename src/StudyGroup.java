import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class StudyGroup implements Comparable<StudyGroup> {
    public static AtomicInteger idGenerator = new AtomicInteger(1);

    private final int id; // Значение должно быть уникальным и больше 0
    private String name; // Поле не может быть null или пустым
    private Coordinates coordinates; // Поле не может быть null
    private LocalDate creationDate; // Поле не может быть null, генерируется автоматически
    private long studentsCount; // Должно быть больше 0
    private Long shouldBeExpelled; // Может быть null, если не null - должно быть больше 0
    private FormOfEducation formOfEducation; // Поле не может быть null
    private Semester semesterEnum; // Поле может быть null
    private Person groupAdmin; // Поле не может быть null

    /**
     * Constructor for creating a new StudyGroup.
     * @param name Name of the study group (cannot be null or empty).
     * @param coordinates Location coordinates (cannot be null).
     * @param studentsCount Number of students (>0).
     * @param shouldBeExpelled Number of students to be expelled (can be null, must be > 0 if present).
     * @param formOfEducation Form of education (cannot be null).
     * @param semesterEnum Current semester (can be null).
     * @param groupAdmin Group administrator (cannot be null).
     */
    public StudyGroup(String name, Coordinates coordinates, long studentsCount, Long shouldBeExpelled,
                      FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates cannot be null.");
        }
        if (studentsCount <= 0) {
            throw new IllegalArgumentException("Students count must be greater than 0.");
        }
        if (shouldBeExpelled != null && shouldBeExpelled <= 0) {
            throw new IllegalArgumentException("Should be expelled count must be greater than 0.");
        }
        if (formOfEducation == null) {
            throw new IllegalArgumentException("FormOfEducation cannot be null.");
        }
        if (groupAdmin == null) {
            throw new IllegalArgumentException("GroupAdmin cannot be null.");
        }

        this.id = StudyGroup.idGenerator.getAndIncrement();
        this.creationDate = LocalDate.now(); // Fixed typo
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.shouldBeExpelled = shouldBeExpelled;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    } // Fixed: Added missing closing bracket

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public Long getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    @Override
    public int compareTo(StudyGroup other) {
        return Long.compare(this.studentsCount, other.studentsCount);
    }


    @Override
    public String toString() {
        return "StudyGroup{id=" + id + ", name='" + name + "', studentsCount=" + studentsCount + "}";
    }

    public String toCSV() {
        return id + "," + name + "," + coordinates.getX() + "," + coordinates.getY() + "," +
                studentsCount + "," + (shouldBeExpelled != null ? shouldBeExpelled : "") + "," +
                formOfEducation + "," + (semesterEnum != null ? semesterEnum : "") + "," +
                groupAdmin.getName();
    }

}
