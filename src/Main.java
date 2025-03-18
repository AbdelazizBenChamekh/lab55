import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Get the file name from the environment variable or default to "data/test.csv"
        String fileName = System.getenv("STUDY_GROUPS_FILE");
        if (fileName == null || fileName.isEmpty()) {
            fileName = "data/test.csv";
            System.err.println("Warning: STUDY_GROUPS_FILE environment variable not set. Using default: " + fileName);
        }

        // Ensure the file exists
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("Error: The file " + fileName + " does not exist.");
            return;
        }

        // Initialize CollectionManager1 (which already contains Save and Load functionality)
        CollectionManager1 collectionManager = new CollectionManager1(fileName);

        // Display available commands at the start
        showAvailableCommands();

        // Start user interaction
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Study Group Management System!");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                collectionManager.executeCommand(input);
            } else {
                System.out.println("Invalid input. Type 'help' for a list of commands.");
            }
        }
    }

    // Method to display available commands
    private static void showAvailableCommands() {
        System.out.println("Available Commands:");
        System.out.println("1.  add - Add a new StudyGroup");
        System.out.println("2.  save - Save the collection");
        System.out.println("3.  info - Display information about the collection");
        System.out.println("4.  show - Show all StudyGroups");
        System.out.println("5.  update - Update an existing StudyGroup");
        System.out.println("6.  remove_by_id - Remove StudyGroup by ID");
        System.out.println("7.  clear - Clear the collection");
        System.out.println("8.  execute_script - Execute commands from a script file");
        System.out.println("9.  exit - Exit the program");
        System.out.println("10. add_if_min - Add StudyGroup if it's the smallest by students count");
        System.out.println("11. remove_lower - Remove all StudyGroups with a lower students count");
        System.out.println("12. history - Show command history");
        System.out.println("13. remove_any_by_form_of_education - Remove StudyGroup by form of education");
        System.out.println("14. print_ascending - Print StudyGroups in ascending order");
        System.out.println("15. print_field_ascending_group_admin - Print StudyGroups sorted by group admin");
        System.out.println("16. load - Load the collection from file");
        System.out.println("Type 'help' for more details on each command.");
    }
}






