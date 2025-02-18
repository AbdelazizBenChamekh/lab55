import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

/**
 * Manages a collection of StudyGroup objects.
 */
public class CollectionManager {
    private final LinkedHashSet<StudyGroup> studyGroups = new LinkedHashSet<>();
    private final String fileName;
    private static final int HISTORY_LIMIT = 12;
    private final LinkedList<String> commandHistory = new LinkedList<>();
    private final Set<String> executedScripts = new HashSet<>();
    private final LocalDateTime initializationTime;

    public CollectionManager(String fileName) {
        this.fileName = fileName;
        this.initializationTime = LocalDateTime.now();
        loadFromFile();
    }

    public void addToHistory(String command) {
        if (commandHistory.size() >= HISTORY_LIMIT) {
            commandHistory.removeFirst();
        }
        commandHistory.add(command);
    }

    public void printHistory() {
        System.out.println("Last 12 commands:");

        if (commandHistory.isEmpty()) {
            System.out.println("No commands recorded yet.");
            return;
        }

        for (String cmd : commandHistory) {
            System.out.println(cmd);
        }
    }



    /**
     * Loads study groups from a CSV file.
     */
    private void loadFromFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("CSV file not found. Starting with an empty collection.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true; // Flag to skip the first row (header)

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                StudyGroup group = parseCSVLine(line);
                if (group != null) {
                    studyGroups.add(group);
                }
            }
            System.out.println("Collection loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }


    /**
     * Parses a line from the CSV file into a StudyGroup object.
     */
    private StudyGroup parseCSVLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 8) return null;

            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            Coordinates coordinates = new Coordinates(Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()));
            long studentsCount = Long.parseLong(parts[4].trim());
            Long shouldBeExpelled = parts[5].trim().isEmpty() ? null : Long.parseLong(parts[5].trim());
            FormOfEducation formOfEducation = FormOfEducation.valueOf(parts[6].trim());
            Semester semesterEnum = parts[7].trim().isEmpty() ? null : Semester.valueOf(parts[7].trim());

            // Dummy group admin (Modify this part to properly parse Person)
            Person groupAdmin = new Person("Admin", 60, Color.BLUE, Color.BLACK, Country.USA, new Location(0, 0.0, "Unknown"));

            return new StudyGroup(name, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin);
        } catch (Exception e) {
            System.err.println("Error parsing CSV line: " + line);
            return null;
        }
    }

    /**
     * Saves the collection to the CSV file.
     */
    public void saveToFile() {
        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            if (file.length() == 0) {
                writer.println("ID,Name,Coordinates_X,Coordinates_Y,StudentsCount,ShouldBeExpelled,FormOfEducation,Semester,Admin_Name,Admin_Weight,Admin_EyeColor,Admin_HairColor,Admin_Nationality,Location_X,Location_Y,Location_Name");
            }

            for (StudyGroup group : studyGroups) {
                writer.println(group.toCSV());
            }
            System.out.println("Saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    /**
     * Removes a StudyGroup by ID.
     */
    public void removeById(int id) {
        Iterator<StudyGroup> iterator = studyGroups.iterator();
        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.getId() == id) {
                iterator.remove();
                System.out.println("StudyGroup removed successfully.");
                return;
            }
        }
        System.out.println("StudyGroup with ID " + id + " not found.");
    }

    /**
     * Clears the collection.
     */
    public void clear() {
        studyGroups.clear();
        System.out.println("Collection cleared!");
    }

    /**
     * Saves and exits the program.
     */
    public void exit() {
        saveToFile();
        System.out.println("Exiting the program...");
        System.exit(0);
    }

    /**
     * Executes a script from a file.
     */
    public void executeScript(String fileName) {
        if (executedScripts.contains(fileName)) {
            System.out.println("Warning: Script '" + fileName + "' is already being executed. Skipping to prevent recursion.");
            return;
        }

        executedScripts.add(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String command;
            while ((command = reader.readLine()) != null) {
                System.out.println("> " + command);
                executeCommand(command);
            }
        } catch (IOException e) {
            System.err.println("Error reading script file: " + e.getMessage());
        }

        executedScripts.remove(fileName);
    }

    public void executeCommand(String command) {
        switch (command.toLowerCase()) {
            case "add":
                addStudyGroupFromUser();
                break;
            case "clear":
                clear();
                break;
            case "exit":
                exit();
                break;
            case "history":
                printHistory();
                break;
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    public StudyGroup addStudyGroupFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding a new StudyGroup...");

        // Get Name (Cannot be Empty)
        String name;
        while (true) {
            System.out.print("Enter StudyGroup name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) break;
            System.out.println("Error: Name cannot be empty. Try again.");
        }

        // Get Coordinates (Valid Integer)
        int x, y;
        while (true) {
            try {
                System.out.print("Enter X coordinate: ");
                x = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Enter Y coordinate: ");
                y = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Coordinates must be valid integers. Try again.");
            }
        }
        Coordinates coordinates = new Coordinates(x, y);

        // Get Students Count (> 0)
        long studentsCount;
        while (true) {
            try {
                System.out.print("Enter students count: ");
                studentsCount = Long.parseLong(scanner.nextLine().trim());
                if (studentsCount > 0) break;
                System.out.println("Error: Students count must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            }
        }

        // Get Should Be Expelled (Optional, Must Be > 0)
        Long shouldBeExpelled = null;
        while (true) {
            try {
                System.out.print("Enter should be expelled (or press Enter to skip): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) break;
                shouldBeExpelled = Long.parseLong(input);
                if (shouldBeExpelled > 0) break;
                System.out.println("Error: Should be expelled must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            }
        }

        // Get FormOfEducation (Valid Enum)
        FormOfEducation formOfEducation;
        while (true) {
            try {
                System.out.print("Enter form of education (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES): ");
                formOfEducation = FormOfEducation.valueOf(scanner.nextLine().trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid form of education. Try again.");
            }
        }

        // Get Semester (Optional)
        Semester semesterEnum = null;
        while (true) {
            try {
                System.out.print("Enter semester (FIRST, FOURTH, SEVENTH) (or press Enter to skip): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) break;
                semesterEnum = Semester.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid semester. Try again.");
            }
        }

        // Get Admin Name (Not Empty)
        String adminName;
        while (true) {
            System.out.print("Enter admin name: ");
            adminName = scanner.nextLine().trim();
            if (!adminName.isEmpty()) break;
            System.out.println("Error: Admin name cannot be empty.");
        }

        // Get Admin Weight (> 0)
        int weight;
        while (true) {
            try {
                System.out.print("Enter admin weight: ");
                weight = Integer.parseInt(scanner.nextLine().trim());
                if (weight > 0) break;
                System.out.println("Error: Weight must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a valid number.");
            }
        }

        // Get Eye Color (Optional)
        Color eyeColor = null;
        while (true) {
            try {
                System.out.print("Enter admin eye color (GREEN, RED, BLACK, ORANGE, BLUE, BROWN) (or press Enter to skip): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) break;
                eyeColor = Color.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid eye color. Try again.");
            }
        }

        // Get Hair Color (Valid Enum)
        Color hairColor;
        while (true) {
            try {
                System.out.print("Enter admin hair color: ");
                hairColor = Color.valueOf(scanner.nextLine().trim().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid hair color. Try again.");
            }
        }

        // Get Nationality (Optional)
        Country nationality = null;
        while (true) {
            try {
                System.out.print("Enter admin nationality(USA, INDIA, THAILAND, SOUTH_KOREA, JAPAN) (or press Enter to skip): ");
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) break;
                nationality = Country.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid nationality. Try again.");
            }
        }

        // Get Location (Valid Numbers)
        int locX;
        float locY;
        while (true) {
            try {
                System.out.print("Enter location X: ");
                locX = (int) Float.parseFloat(scanner.nextLine().trim());

                System.out.print("Enter location Y: ");
                locY = Float.parseFloat(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter valid numbers for location.");
            }
        }

        // Get Location Name (Not Empty)
        String locName;
        while (true) {
            System.out.print("Enter location name: ");
            locName = scanner.nextLine().trim();
            if (!locName.isEmpty()) break;
            System.out.println("Error: Location name cannot be empty.");
        }

        Location location = new Location(locX, locY, locName);
        Person admin = new Person(adminName, weight, eyeColor, hairColor, nationality, location);

        // Create and Add StudyGroup
        StudyGroup newGroup = new StudyGroup(name, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, admin);
        studyGroups.add(newGroup);
        System.out.println("Successfully added: " + newGroup);

        return newGroup;
    }


    public void clearCollection() {
        studyGroups.clear();
        System.out.println("Collection cleared successfully!");
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            for (StudyGroup group : studyGroups) {
                writer.println(group.toCSV());
            }
            System.out.println("Collection saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving collection: " + e.getMessage());
        }
    }

    public void removeLower(StudyGroup threshold) {
        Iterator<StudyGroup> iterator = studyGroups.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.getStudentsCount() < threshold.getStudentsCount()) {  // FIXED
                iterator.remove();
                removed = true;
                System.out.println("Removed StudyGroup: " + group.getName());
            }
        }

        if (!removed) {
            System.out.println("No elements were removed.");
        }
    }


    public void showStudyGroups() {
        if (studyGroups.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }

        System.out.println("Study Groups in the collection:");
        for (StudyGroup group : studyGroups) {
            System.out.println(group);
        }}




    public void info() {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            System.out.println("Collection Information:");
            System.out.println(" - Type: " + studyGroups.getClass().getSimpleName()); // Collection type
            System.out.println(" - Initialization Time: " + initializationTime.format(formatter)); // Time of creation
            System.out.println(" - Number of Elements: " + studyGroups.size()); // Current size
    }


    public void updateStudyGroup(int id) {
        Iterator<StudyGroup> iterator = studyGroups.iterator();
        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.getId() == id) {
                System.out.println("Updating StudyGroup: " + group);

                iterator.remove(); // Remove the old StudyGroup

                StudyGroup updatedGroup = addStudyGroupFromUser(id); // Create a new StudyGroup with same ID
                studyGroups.add(updatedGroup);

                System.out.println("StudyGroup updated successfully.");
                return;
            }
        }
        System.out.println("StudyGroup with ID " + id + " not found.");
    }

    private StudyGroup addStudyGroupFromUser(int id) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter coordinates (x y): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter students count: ");
        long studentsCount = scanner.nextLong();

        System.out.print("Enter should be expelled (or leave empty): ");
        scanner.nextLine();
        String expelledInput = scanner.nextLine().trim();
        Long shouldBeExpelled = expelledInput.isEmpty() ? null : Long.parseLong(expelledInput);

        System.out.print("Enter form of education (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES): ");
        FormOfEducation formOfEducation = FormOfEducation.valueOf(scanner.nextLine().trim());

        System.out.print("Enter semester (leave empty if none): ");
        String semesterInput = scanner.nextLine().trim();
        Semester semesterEnum = semesterInput.isEmpty() ? null : Semester.valueOf(semesterInput);

        System.out.print("Enter admin name: ");
        String adminName = scanner.nextLine().trim();

        System.out.print("Enter admin weight: ");
        int weight = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter admin eye color (GREEN, BLUE, BROWN): ");
        Color eyeColor = Color.valueOf(scanner.nextLine().trim());

        System.out.print("Enter admin hair color (BLACK, RED, BLONDE, BROWN): ");
        Color hairColor = Color.valueOf(scanner.nextLine().trim());

        System.out.print("Enter admin nationality (USA, GERMANY, FRANCE, INDIA, CHINA): ");
        Country nationality = Country.valueOf(scanner.nextLine().trim());

        System.out.print("Enter location (x y name): ");
        int locX = scanner.nextInt();
        double locY = scanner.nextDouble();
        scanner.nextLine();
        String locName = scanner.nextLine().trim();

        Location location = new Location(locX, locY, locName);
        Person admin = new Person(adminName, weight, eyeColor, hairColor, nationality, location);

        return new StudyGroup(name, new Coordinates(x, y), studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, admin);
    }


    public void addIfMin(StudyGroup newGroup) {
        if (studyGroups.isEmpty()) {
            studyGroups.add(newGroup);
            System.out.println("Collection was empty. StudyGroup added.");
            return;
        }

        // Find the smallest StudyGroup (e.g., by studentsCount)
        StudyGroup minGroup = Collections.min(studyGroups, Comparator.comparingLong(StudyGroup::getStudentsCount));

        if (newGroup.getStudentsCount() < minGroup.getStudentsCount()) {
            studyGroups.add(newGroup);
            System.out.println("StudyGroup added as it is the smallest.");
        } else {
            System.out.println("StudyGroup not added. It is not smaller than the current smallest element.");
        }
    }

    public void removeAnyByFormOfEducation(FormOfEducation formOfEducation) {
        Iterator<StudyGroup> iterator = studyGroups.iterator();

        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.getFormOfEducation() == formOfEducation) {
                iterator.remove();
                System.out.println("Removed StudyGroup: " + group.getName());
                return; // Remove only one matching element
            }
        }

        System.out.println("No StudyGroup found with FormOfEducation: " + formOfEducation);
    }

    public void printAscending() {
        if (studyGroups.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }

        studyGroups.stream()
                .sorted()
                .forEach(System.out::println);
    }
    public void printFieldAscendingGroupAdmin() {
        if (studyGroups.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }

        studyGroups.stream()
                .map(StudyGroup::getGroupAdmin) // Extract groupAdmin field
                .sorted(Comparator.comparing(Person::getName)) // Sort by name (or other criteria)
                .forEach(System.out::println);
    }




}











