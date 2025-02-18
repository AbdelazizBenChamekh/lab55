import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point
 */
public class Main {
    public static void main(String[] args) {

        List<String> commandHistory = new ArrayList<>();
        String fileName = System.getenv("STUDY_GROUPS_FILE");
        if (fileName == null || fileName.isEmpty()) {
            fileName = "data/test.csv";
            System.err.println("Please set the STUDY_GROUPS_FILE environment variable");
            return;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("The file " + fileName + " does not exist");
            return;
        }

        CollectionManager manager = new CollectionManager(fileName);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim().toLowerCase();

            if (!line.isEmpty()) {
                manager.addToHistory(line); // ✅ Now handled inside CollectionManager
            }


            switch (line) {
                case "help":
                    System.out.println("Available commands: add, show, remove_by_id, save, exit, history, update, remove_lower, clear, execute_script, remove_any_by_form_of_education, add_if_min, info, print_ascending, print_field_ascending_group_admin");
                    break;

                case "history":
                    manager.printHistory();
                    break;

                case "add":
                    manager.addStudyGroupFromUser();
                    break;

                /*case "update":
                    System.out.print("Enter StudyGroup ID: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Fix: Consume the leftover newline
                    manager.updateStudyGroup(updateId);
                    break;*/

                case "remove_by_id":
                    System.out.print("Enter ID: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine(); // Fix: Consume the leftover newline
                    manager.removeById(removeId);
                    break;

                case "remove_lower":
                    System.out.println("Enter reference StudyGroup details:");
                    StudyGroup referenceGroup = manager.addStudyGroupFromUser();
                    manager.removeLower(referenceGroup);
                    break;

                case "show":
                    manager.showStudyGroups();
                    break;

                case "save":
                    manager.saveToFile();
                    break;

                case "clear":
                    manager.clearCollection();
                    break;

                case "execute_script":
                    System.out.print("Enter script file name: ");
                    String scriptFile = scanner.next();
                    scanner.nextLine(); // Fix: Consume the leftover newline
                    manager.executeScript(scriptFile);
                    break;

                case "exit":
                    System.out.println("Exiting without saving ... ");
                    return;

                case "info":
                    manager.info();
                    break;

                case "update":
                    System.out.print("Enter StudyGroup ID: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    manager.updateStudyGroup(updateId);
                    break;

                case "add_if_min":
                    System.out.println("Enter details for the new StudyGroup:");
                    StudyGroup newGroup = manager.addStudyGroupFromUser();
                    manager.addIfMin(newGroup);
                    break;

                case "remove_any_by_form_of_education":
                    System.out.print("Enter FormOfEducation (DISTANCE_EDUCATION, FULL_TIME_EDUCATION, EVENING_CLASSES): ");
                    try {
                        FormOfEducation form = FormOfEducation.valueOf(scanner.next().toUpperCase());
                        scanner.nextLine(); // Consume leftover newline
                        manager.removeAnyByFormOfEducation(form);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid FormOfEducation. Please enter a valid value.");
                    }
                    break;
                case "print_ascending":
                    manager.printAscending();
                    break;
                case "print_field_ascending_group_admin":
                    manager.printFieldAscendingGroupAdmin();
                    break;




                default:
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
                    break;
            }
        }
    }
}
