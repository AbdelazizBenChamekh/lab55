import java.io.*;
import java.util.LinkedHashSet;

public class LoadFromFile implements Command {
    private String fileName;
        private final LinkedHashSet<StudyGroup> studyGroups = new LinkedHashSet<>();
    private Location location;

    public LoadFromFile(String fileName) {
            this.fileName = fileName;
        }
    
        public void setFileName(String fileName) {
            this.fileName = fileName;
    }

    public void load() {
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("⚠️ File not found: " + fileName);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { // Skip the header
                    isFirstLine = false;
                    continue;
                }
                
                StudyGroup group = parseCSVLine(line);
                if (group != null) {
                    studyGroups.add(group);
                }
            }
            System.out.println("✅ Successfully loaded " + studyGroups.size() + " study groups.");
        } catch (IOException e) {
            System.err.println("❌ Error reading file: " + e.getMessage());
        }
    }

    private StudyGroup parseCSVLine(String line) {
        try {
            String[] parts = line.split(",");

            if (parts.length < 16) { // Ensure we have all required fields
                throw new IllegalArgumentException("Invalid CSV format (missing fields): " + line);
            }

            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            int x = Integer.parseInt(parts[2].trim());
            int y = Integer.parseInt(parts[3].trim());
            Coordinates coordinates = new Coordinates(x, y);

            long studentsCount = Long.parseLong(parts[4].trim());
            Long shouldBeExpelled = parts[5].trim().isEmpty() ? null : Long.parseLong(parts[5].trim());

            FormOfEducation formOfEducation = FormOfEducation.valueOf(parts[6].trim().toUpperCase());

            Semester semesterEnum = parts[7].trim().isEmpty() ? null : Semester.valueOf(parts[7].trim().toUpperCase());

            // Parsing Admin (Person)
            String adminName = parts[8].trim();
            Double adminWeight = parts[9].trim().isEmpty() ? null : Double.parseDouble(parts[9].trim());

            Color eyeColor = parts[10].trim().isEmpty() ? null : Color.valueOf(parts[10].trim().toUpperCase());
            Color hairColor = parts[11].trim().isEmpty() ? null : Color.valueOf(parts[11].trim().toUpperCase());
            Country nationality = parts[12].trim().isEmpty() ? null : Country.valueOf(parts[12].trim().toUpperCase());

            // Parsing Location
            int locationX = Integer.parseInt(parts[13].trim());
            double locationY = Double.parseDouble(parts[14].trim());

            String locationName = parts[15].trim();

            Location location = new Location(locationX, locationY, locationName);

            Person groupAdmin = new Person(adminName, adminWeight, eyeColor, hairColor, nationality, location);

            // Creating StudyGroup object
            return new StudyGroup(name, coordinates, studentsCount, shouldBeExpelled, formOfEducation, semesterEnum, groupAdmin, location);

        } catch (Exception e) {
            System.err.println("❌ Error parsing CSV line: " + line);
            System.err.println("   ➡ " + e.getMessage()); // Print exact error
            return null;
        }
    }


    public LinkedHashSet<StudyGroup> getStudyGroups() {
        return new LinkedHashSet<>(studyGroups);
    }

    @Override
    public void execute() {
        load();
    }

    @Override
    public Command get(String commandName) {
        return null;
    }

    @Override
    public void apply(String argument) {

    }

    /*@Override
    public Command get(String commandName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }*/
}
