import java.util.List;
import java.util.Scanner;

public class AddStudyGroupFromUser implements ApplyCommand {
    private final CollectionManager1 collectionManager;
    private final Scanner scanner;

    public AddStudyGroupFromUser(CollectionManager1 collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    /**
     * Method to manually add a StudyGroup from user input.
     */
    public StudyGroup addStudyGroupFromUser() {
        System.out.println("\n📌 Creating a new StudyGroup...");

        try {
            // Name
            System.out.print("Enter StudyGroup name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("❌ Error: StudyGroup name cannot be empty.");
                return null;
            }

            // Coordinates
            int x = parseCoordinate("X coordinate");
            int y = parseCoordinate("Y coordinate");
            Coordinates coordinates = new Coordinates(x, y);

            // Number of students
            long studentsCount = parseLong("Enter number of students");

            // Students to be expelled (optional)
            Long shouldBeExpelled = parseOptionalLong("Enter number of students to be expelled (or press Enter to skip)");

            // Form of Education
            FormOfEducation formOfEducation = parseEnum(FormOfEducation.class, "Select Form of Education (FULL_TIME_EDUCATION, DISTANCE_EDUCATION, EVENING_CLASSES)");

            // Semester (optional)
            Semester semester = parseOptionalEnum(Semester.class, "Select Semester (FIRST, SECOND, etc.) or press Enter to skip");

            // Admin Information
            System.out.print("Enter Admin Name: ");
            String adminName = scanner.nextLine().trim();
            if (adminName.isEmpty()) {
                System.out.println("❌ Error: Admin name cannot be empty.");
                return null;
            }

            Double adminWeight = parseOptionalDouble("Enter Admin Weight (or press Enter to skip)");

            Color eyeColor = parseEnum(Color.class, "Enter Admin Eye Color (BLUE, GREEN, BROWN, etc.)");
            Color hairColor = parseEnum(Color.class, "Enter Admin Hair Color (BLACK, BROWN, BLONDE, etc.)");
            Country nationality = parseEnum(Country.class, "Enter Admin Nationality (USA, INDIA, FRANCE, etc.)");

            // Admin Location
            int locationX = parseCoordinate("Admin Location X");
            int locationY = parseCoordinate("Admin Location Y");

            System.out.print("Enter Admin Location Name: ");
            String locationName = scanner.nextLine().trim();
            if (locationName.isEmpty()) {
                System.out.println("❌ Error: Location name cannot be empty.");
                return null;
            }

            Location location = new Location(locationX, locationY, locationName);
            Person admin = new Person(adminName, adminWeight, eyeColor, hairColor, nationality, location);

            // Create StudyGroup object
            StudyGroup studyGroup = new StudyGroup(name, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semester, admin);

            // Add to collection
            collectionManager.addStudyGroup(studyGroup);
            System.out.println("✅ StudyGroup added successfully!");
            System.out.println("📊 Collection now has " + collectionManager.getStudyGroups().size() + " study groups.");

            return studyGroup;
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void execute() {
        addStudyGroupFromUser();
    }

    @Override
    public Command get(String commandName) {
        if (commandName.equalsIgnoreCase("add")) {
            return this;
        }
        return null;
    }

    @Override
    public void apply(String argument) {
        String[] parts = argument.split(",");

        if (parts.length < 15) {
            System.out.println("❌ Error: Not enough arguments provided for 'add'.");
            return;
        }

        try {
            String name = parts[0].trim();
            int x = Integer.parseInt(parts[1].trim());
            int y = Integer.parseInt(parts[2].trim());
            Coordinates coordinates = new Coordinates(x, y);

            long studentsCount = Long.parseLong(parts[3].trim());
            Long shouldBeExpelled = parts[4].trim().isEmpty() ? null : Long.parseLong(parts[4].trim());
            FormOfEducation formOfEducation = FormOfEducation.valueOf(parts[5].trim().toUpperCase());
            Semester semester = Semester.valueOf(parts[6].trim().toUpperCase());

            String adminName = parts[7].trim();
            double adminWeight = Double.parseDouble(parts[8].trim());
            Color eyeColor = Color.valueOf(parts[9].trim().toUpperCase());
            Color hairColor = Color.valueOf(parts[10].trim().toUpperCase());
            Country nationality = Country.valueOf(parts[11].trim().toUpperCase());

            int locationX = Integer.parseInt(parts[12].trim());
            double locationY = Double.parseDouble(parts[13].trim());
            String locationName = parts[14].trim();

            Location location = new Location(locationX, locationY, locationName);
            Person groupAdmin = new Person(adminName, adminWeight, eyeColor, hairColor, nationality, location);

            StudyGroup studyGroup = new StudyGroup(name, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semester, groupAdmin);
            collectionManager.addStudyGroup(studyGroup);

            System.out.println("✅ Successfully added study group: " + name);
        } catch (Exception e) {
            System.err.println("❌ Error adding study group: " + e.getMessage());
        }
    }

    @Override
    public void apply(List<String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            System.out.println("❌ Error: Not enough arguments provided for 'add'.");
            return;
        }

        apply(String.join(",", arguments));
    }

    /** 🛠 Utility Methods to Handle Parsing **/
    private int parseCoordinate(String prompt) {
        while (true) {
            try {
                System.out.print("Enter " + prompt + " (integer): ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid integer.");
            }
        }
    }

    private long parseLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid long number.");
            }
        }
    }

    private Long parseOptionalLong(String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Defaulting to null.");
            return null;
        }
    }

    private Double parseOptionalDouble(String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Defaulting to null.");
            return null;
        }
    }

    private <T extends Enum<T>> T parseEnum(Class<T> enumClass, String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Enum.valueOf(enumClass, scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid selection. Please enter a valid option.");
            }
        }
    }

    private <T extends Enum<T>> T parseOptionalEnum(Class<T> enumClass, String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Enum.valueOf(enumClass, input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid input. Defaulting to null.");
            return null;
        }
    }
}





