import java.util.LinkedHashSet;

public class Clear implements Command {
    private final CollectionManager1 collectionManager;

    public Clear(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        studyGroups.clear();
        System.out.println("Collection cleared!");
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

