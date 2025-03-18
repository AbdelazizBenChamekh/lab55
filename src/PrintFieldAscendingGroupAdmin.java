import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class PrintFieldAscendingGroupAdmin implements Command {
    private final CollectionManager1 collectionManager;

    public PrintFieldAscendingGroupAdmin(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void printFieldAscendingGroupAdmin() {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();

        if (studyGroups.isEmpty()) {
            System.out.println("📢 The collection is empty.");
            return;
        }

        // Filter out null groupAdmins, sort, and print
        String result = studyGroups.stream()
                .map(StudyGroup::getGroupAdmin)
                .filter(admin -> admin != null) // Avoid NullPointerException
                .sorted(Comparator.comparing(Person::getName, String.CASE_INSENSITIVE_ORDER)) // Case-insensitive sorting
                .map(Person::toString) // Convert to string for printing
                .collect(Collectors.joining("\n")); // Collect results in a single output

        if (result.isEmpty()) {
            System.out.println("📢 No valid group administrators found in the collection.");
        } else {
            System.out.println("📜 Group Administrators in Ascending Order:\n" + result);
        }
    }

    @Override
    public void execute() {
        printFieldAscendingGroupAdmin();
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
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }*/
}

