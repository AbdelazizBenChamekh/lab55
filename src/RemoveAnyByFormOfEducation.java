import java.util.Iterator;
import java.util.LinkedHashSet;

public class RemoveAnyByFormOfEducation implements Command {
    private final CollectionManager1 collectionManager;

    public RemoveAnyByFormOfEducation(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void removeAnyByFormOfEducation(FormOfEducation formOfEducation) {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
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
