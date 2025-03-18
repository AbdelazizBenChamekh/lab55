import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;

public class AddIfMin implements Command {
    private final CollectionManager1 collectionManager;

    public AddIfMin(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void addIfMin(StudyGroup newGroup) {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();

        if (studyGroups.isEmpty()) {
            studyGroups.add(newGroup);
            System.out.println("Collection was empty. StudyGroup added.");
            return;
        }

        // Find the smallest StudyGroup based on studentsCount
        StudyGroup minGroup = Collections.min(studyGroups, Comparator.comparingLong(StudyGroup::getStudentsCount));

        if (newGroup.getStudentsCount() < minGroup.getStudentsCount()) {
            studyGroups.add(newGroup);
            System.out.println("StudyGroup added as it is the smallest.");
        } else {
            System.out.println("StudyGroup not added. It is not smaller than the current smallest element.");
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
