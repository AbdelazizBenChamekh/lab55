import java.util.LinkedHashSet;

public class ClearCollection implements Command {

    private final LinkedHashSet<StudyGroup> studyGroups = new LinkedHashSet<>();
    public void clearCollection() {
        studyGroups.clear();
        System.out.println("Collection cleared successfully!");
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



}
