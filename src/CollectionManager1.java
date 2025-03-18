import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CollectionManager1 {
    private final CommandInvoker commandInvoker = new CommandInvoker();
    private final LinkedHashSet<StudyGroup> studyGroups = new LinkedHashSet<>();
    private final LinkedList<String> commandHistory = new LinkedList<>();
    private final LocalDateTime initializationTime;
    private final String fileName;
    private static final int HISTORY_LIMIT = 12;
    private final LoadFromFile loadFromFile;
    private final Save saveToFile;
    private static CollectionManager1 instance;  // Singleton instance

    private final Scanner scanner;  // Moved scanner to a field

    public CollectionManager1(String fileName) {
        this.fileName = fileName;
        this.saveToFile = new Save(this, fileName);
        this.loadFromFile = new LoadFromFile(fileName);
        this.initializationTime = LocalDateTime.now();
        this.scanner = new Scanner(System.in); // Initialize scanner once

        registerCommands();

        // ✅ Load data when CollectionManager1 is created
        loadFromFile.load();

        // ✅ Ensure we only clear if new data is available
        LinkedHashSet<StudyGroup> loadedGroups = loadFromFile.getStudyGroups();
        if (!loadedGroups.isEmpty()) {
            studyGroups.clear();
            studyGroups.addAll(loadedGroups);
        }

        if (studyGroups.isEmpty()) {
            System.out.println("⚠️ Warning: No study groups were loaded from the file.");
        } else {
            System.out.println("✅ Loaded " + studyGroups.size() + " study groups from the file.");
        }
    }

    // Singleton pattern
    public static CollectionManager1 getInstance(String fileName) {
        if (instance == null) {
            instance = new CollectionManager1(fileName);
        }
        return instance;
    }

    // Method to add a StudyGroup
    public void addStudyGroup(StudyGroup group) {
        if (group != null) {
            studyGroups.add(group);
        } else {
            System.out.println("❌ Error: Cannot add a null StudyGroup.");
        }
    }

    public void addCommandToHistory(String command) {
        if (commandHistory.size() >= HISTORY_LIMIT) {
            commandHistory.removeFirst();  // Keep only the last 12 commands
        }
        commandHistory.add(command);
    }

    private void loadFromFileData() {
        LinkedHashSet<StudyGroup> loadedGroups = loadFromFile.getStudyGroups();

        if (loadedGroups == null || loadedGroups.isEmpty()) {
            System.out.println("⚠️ Warning: No study groups were loaded from the file.");
            return;
        }

        studyGroups.clear(); // 🔄 Clear only if new data is available
        studyGroups.addAll(loadedGroups);

        System.out.println("✅ Loaded " + studyGroups.size() + " study groups from the file.");
    }

    private void registerCommands() {
        AddStudyGroupFromUser addCommand = new AddStudyGroupFromUser(this, scanner);
        UpdateStudyGroup updateStudyGroup = new UpdateStudyGroup(this, scanner);

        commandInvoker.register("help", new Help(this));
        commandInvoker.register("info", new Info(this));
        commandInvoker.register("show", new ShowStudyGroups(this));
        commandInvoker.register("add", addCommand);
        commandInvoker.register("update", updateStudyGroup);
        commandInvoker.register("remove_by_id", new RemoveById(this));
        commandInvoker.register("clear", new Clear(this));
        commandInvoker.register("save", saveToFile);
        commandInvoker.register("exit", new Exit(this));
        commandInvoker.register("add_if_min", new AddIfMin(this));
        commandInvoker.register("remove_lower", new RemoveLower(this));
        commandInvoker.register("history", new PrintHistory(this));
        commandInvoker.register("remove_any_by_form_of_education", new RemoveAnyByFormOfEducation(this));
        commandInvoker.register("print_ascending", new PrintAscending(this));
        commandInvoker.register("print_field_ascending_group_admin", new PrintFieldAscendingGroupAdmin(this));
        commandInvoker.register("load", loadFromFile);
        commandInvoker.register("execute_script", new ExecuteScript(commandInvoker));
    }

    // Execute command with arguments
    public void executeCommand(String commandName, List<String> arguments) {
        addCommandToHistory(commandName);
        commandInvoker.execute(commandName, arguments);
    }

    // Execute command with input string
    public void executeCommand(String input) {
        String[] parts = input.trim().split(" ", 2);
        String commandName = parts[0];
        String argument = (parts.length > 1) ? parts[1] : ""; // Ensure argument is never null

        Command command = commandInvoker.getCommand(commandName);
        if (command != null) {
            command.execute(argument);
        } else {
            System.out.println("❌ Unknown command: " + commandName);
        }
    }

    public void show() {
        if (studyGroups.isEmpty()) {
            System.out.println("The collection is empty.");
        } else {
            System.out.println("Study Groups in the collection:");
            studyGroups.forEach(System.out::println);
        }
    }

    public LinkedHashSet<StudyGroup> getStudyGroups() {
        return studyGroups;
    }

    public LinkedList<String> getCommandHistory() {
        return commandHistory;
    }

    public String getCollectionType() {
        return studyGroups.getClass().getSimpleName();
    }

    public LocalDateTime getInitializationTime() {
        return initializationTime;
    }

    public int getCollectionSize() {
        return studyGroups.size();
    }

    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }

    public void saveToFile() {
        saveToFile.execute();
    }
}









