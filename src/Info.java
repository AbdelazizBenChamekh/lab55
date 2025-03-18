import java.time.format.DateTimeFormatter;

public class Info implements Command {
    private final CollectionManager1 collectionManager;

    public Info(CollectionManager1 collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void info() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("Collection Information:");
        System.out.println(" - Type: " + collectionManager.getCollectionType());
        System.out.println(" - Initialization Time: " + collectionManager.getInitializationTime().format(formatter));
        System.out.println(" - Number of Elements: " + collectionManager.getCollectionSize());
    }

    @Override
    public void execute() {
        info();
    }

    @Override
    public Command get(String commandName) {
        return null;
    }

    @Override
    public void apply(String argument) {
        info();  // Since "info" doesn't need arguments, we ignore `argument`
    }
}



