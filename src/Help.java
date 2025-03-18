import java.util.Map;

public class Help implements Command {
    private final CollectionManager1 collectionManager;

    public Help(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void showHelp() {
        Map<String, Command> commands = collectionManager.getCommandInvoker().getCommands();
        System.out.println("\nAvailable commands:");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + " : " + getDescription(entry.getKey()));
        }
    }

    private String getDescription(String command) {
        return switch (command) {
            case "help" -> "Display help information for available commands.";
            case "show" -> "Display all StudyGroups in the collection.";
            case "clear" -> "Clear the collection.";
            case "save" -> "Save the collection to a file.";
            case "info" -> "Display information about the collection.";
            case "update" -> "Update a StudyGroup by its ID.";
            case "add" -> "Add a new StudyGroup.";
            case "remove_by_id" -> "Remove a StudyGroup by its ID.";
            case "add_if_min" -> "Add a StudyGroup if it has the lowest student count.";
            case "remove_lower" -> "Remove all StudyGroups smaller than the given one.";
            case "history" -> "Show the last 12 commands.";
            case "remove_any_by_form_of_education" -> "Remove one StudyGroup by its form of education.";
            case "print_ascending" -> "Print StudyGroups in ascending order.";
            case "print_field_ascending_group_admin" -> "Print group admins in ascending order.";
            case "execute_script" -> "Execute a script from a file.";
            case "exit" -> "Exit the program.";
            default -> "No description available.";
        };
    }

    @Override
    public void execute() {
        showHelp();
    }

    @Override
    public Command get(String commandName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public void apply(String argument) {
        showHelp();
    }
}

