import java.util.LinkedList;

public class PrintHistory implements Command {
    private final CollectionManager1 collectionManager;

    public PrintHistory(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    private void printHistory() { // Made this private since it's only used inside this class
        LinkedList<String> commandHistory = collectionManager.getCommandHistory();
        System.out.println("Last 12 commands:");

        if (commandHistory.isEmpty()) {
            System.out.println("No commands recorded yet.");
            return;
        }

        for (String cmd : commandHistory) {
            System.out.println(cmd);
        }
    }

    @Override
    public void execute() {
        printHistory();
    }

    @Override
    public Command get(String commandName) {
        return null;
    }

    @Override
    public void apply(String argument) {

    }
}


