import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;

public class Save implements Command {
    private final CollectionManager1 collectionManager;
    private final String fileName;

    public Save(CollectionManager1 collectionManager, String fileName) {
        this.collectionManager = collectionManager;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        saveToFile();
    }

    @Override
    public Command get(String commandName) {
        return null;
    }

    @Override
    public void apply(String argument) {

    }

    private void saveToFile() {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        File file = new File(fileName);

        System.out.println("Saving to file: " + file.getAbsolutePath()); // Debugging path

        try (PrintWriter writer = new PrintWriter(file)) {
            if (studyGroups.isEmpty()) {
                System.out.println("⚠️ Warning: Collection is empty. Saving an empty file.");
            } else {
                // Write CSV header
                writer.println("ID,Name,Coordinates_X,Coordinates_Y,StudentsCount,ShouldBeExpelled,FormOfEducation,Semester,Admin_Name,Admin_Weight,Admin_EyeColor,Admin_HairColor,Admin_Nationality,Location_X,Location_Y,Location_Name");

                // Write data
                for (StudyGroup group : studyGroups) {
                    writer.println(group.toCSV()); // ✅ Ensure `toCSV()` formats correctly
                }

                writer.flush(); // Ensure data is written to the file

                System.out.println("✅ Collection saved successfully! " + fileName);
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving collection: " + e.getMessage());
        }
    }


    /*@Override
    public Command get(String commandName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }*/
}


