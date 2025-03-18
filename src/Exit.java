public class Exit implements Command {
    private final CollectionManager1 collectionManager;

    public Exit(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        System.out.println("Saving data before exit...");
        collectionManager.saveToFile(); // Save before exiting
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public Command get(String commandName) {
        return null;
    }

    @Override
    public void apply(String argument) {
        execute();

    }
}


