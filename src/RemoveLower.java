import java.util.Iterator;
import java.util.LinkedHashSet;

public class RemoveLower implements Command {
    private final CollectionManager1 collectionManager;

    public RemoveLower(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void removeLower(StudyGroup threshold) {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        Iterator<StudyGroup> iterator = studyGroups.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.getStudentsCount() < threshold.getStudentsCount()) { // Compare by studentsCount
                iterator.remove();
                removed = true;
                System.out.println("Removed StudyGroup: " + group.getName());
            }
        }

        if (!removed) {
            System.out.println("No elements were removed.");
        }
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet.");
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

