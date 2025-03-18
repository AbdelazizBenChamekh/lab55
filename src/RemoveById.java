import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class RemoveById implements ApplyCommand {
    private final CollectionManager1 collectionManager;

    public RemoveById(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    // Removes the StudyGroup with the matching ID
    public void removeById(int id) {
        LinkedHashSet<StudyGroup> studyGroups = collectionManager.getStudyGroups();
        Iterator<StudyGroup> iterator = studyGroups.iterator();

        while (iterator.hasNext()) {
            StudyGroup group = iterator.next();
            if (group.getId() == id) {
                iterator.remove();
                System.out.println("StudyGroup removed successfully.");
                return;
            }
        }
        System.out.println("StudyGroup with ID " + id + " not found.");
    }

    // Interactive execution: prompt user for an ID.
    @Override
    public void execute() {
        System.out.println("Enter the ID of the StudyGroup to remove:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        try {
            int id = Integer.parseInt(input);
            removeById(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format: " + input);
        }
    }

    // Non-interactive execution using a single argument (ID as string).
    @Override
    public void apply(String argument) {
        if (argument == null || argument.isEmpty()) {
            System.out.println("Error: ID argument is required.");
            return;
        }
        try {
            int id = Integer.parseInt(argument.trim());
            removeById(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format: " + argument);
        }
    }

    // Non-interactive execution using a list of arguments.
    @Override
    public void apply(List<String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            System.out.println("Error: ID argument is required.");
            return;
        }
        apply(arguments.get(0));
    }

    // Implement the get method if required by the Command interface.
    @Override
    public Command get(String commandName) {
        // RemoveById does not support retrieving subcommands.
        return null;
    }
}

