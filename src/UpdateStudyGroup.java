import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class UpdateStudyGroup implements Command {
    private final CollectionManager1 collectionManager;
    private final Scanner scanner;

    public UpdateStudyGroup(CollectionManager1 collectionManager, Scanner scanner) {
        this.collectionManager = collectionManager;
        this.scanner = scanner;
    }

    public void updateStudyGroup(int id) {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        Iterator<StudyGroup> iterator = studyGroups.iterator();
        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.getId() == id) {
                System.out.println("Updating StudyGroup: " + group);

                iterator.remove(); // Remove the old StudyGroup

                // Get new StudyGroup data from user input
                AddStudyGroupFromUser addStudyGroupFromUser = new AddStudyGroupFromUser(collectionManager, scanner);
                StudyGroup updatedGroup = addStudyGroupFromUser.addStudyGroupFromUser();

                // Ensure the original ID is preserved
                updatedGroup.setId(group.getId());

                studyGroups.add(updatedGroup); // Add the updated group back to the collection

                System.out.println("StudyGroup updated successfully.");
                return;
            }
        }
        System.out.println("StudyGroup with ID " + id + " not found.");
    }

    @Override
    public void execute() {
        System.out.print("Enter the ID of the StudyGroup to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        updateStudyGroup(id);
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
        return null;
    }*/
}


