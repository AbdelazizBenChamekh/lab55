import java.util.LinkedHashSet;

public class ShowStudyGroups implements Command {
    private final CollectionManager1 collectionManager;

    public ShowStudyGroups(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void showStudyGroups() {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        if (studyGroups.isEmpty()) {
            System.out.println("The collection is empty.");
            return;
        }

        System.out.println("Study Groups in the collection:");
        for (StudyGroup group : studyGroups) {
            System.out.println(group);
        }
    }

    @Override
    public void execute() {
        showStudyGroups();
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

