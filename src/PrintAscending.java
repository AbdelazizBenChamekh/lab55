import java.util.LinkedHashSet;

public class PrintAscending implements Command {
    private final CollectionManager1 collectionManager;

    public PrintAscending(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void printAscending() {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();

        if (studyGroups.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }

        studyGroups.stream()
                .sorted()
                .forEach(System.out::println);
    }

    @Override
    public void execute() { // Ensure the signature matches Command interface
        collectionManager.getStudyGroups().stream()
                .sorted()
                .forEach(System.out::println);
    }

    @Override
    public Command get(String commandName) {
        return null;
    }

    @Override
    public void apply(String argument) {

    }

}

