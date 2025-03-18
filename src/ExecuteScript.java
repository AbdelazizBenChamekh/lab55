import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExecuteScript implements ApplyCommand {
    private final CommandInvoker commandInvoker;

    public ExecuteScript(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    // Override execute() with no arguments to signal error if called directly.
    @Override
    public void execute() {
        System.out.println("❌ Error: Execute method requires arguments.");
        throw new UnsupportedOperationException("Execute method requires arguments.");
    }

    // Override execute(String arguments) to forward to apply(String)
    @Override
    public void execute(String arguments) {
        apply(arguments);
    }

    // This method handles a file path argument, reads the script, and executes commands.
    @Override
    public void apply(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            System.out.println("❌ Error: File path is required.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            System.out.println("📖 Reading script file: " + Path.of(filePath).toAbsolutePath());

            int currentIndex = 0; // Start from the first line
            while (currentIndex < lines.size()) {
                String line = lines.get(currentIndex).trim();
                if (line.isEmpty() || line.startsWith("#")) { // Skip empty lines and comments
                    currentIndex++;
                    continue;
                }

                // Handle multiline commands like 'add'
                if (line.equalsIgnoreCase("add")) {
                    List<String> addArguments = new ArrayList<>();
                    currentIndex++; // Move to the next line after 'add'

                    // Collect arguments until an empty line or end of file
                    while (currentIndex < lines.size()) {
                        String nextLine = lines.get(currentIndex).trim();
                        if (nextLine.isEmpty()) break;
                        addArguments.add(nextLine);
                        currentIndex++;
                    }

                    // Execute 'add' with collected arguments
                    ApplyCommand addCommand = (ApplyCommand) commandInvoker.get("add");
                    if (addCommand != null) {
                        addCommand.apply(addArguments); // Pass list of arguments
                    } else {
                        System.out.println("❌ Error: 'add' command is not registered.");
                    }
                    continue; // Skip to next command after handling 'add'
                }

                // Process single-line commands:
                Command command = commandInvoker.get(line.toLowerCase());
                if (command != null) {
                    System.out.println("> Executing command: " + line);
                    command.execute();
                } else {
                    System.out.println("❌ Error: Unrecognized command '" + line + "'.");
                }

                currentIndex++; // Move to the next line
            }
        } catch (IOException e) {
            System.out.println("❌ Error: Unable to read script file '" + filePath + "'.");
        }
    }

    @Override
    public void apply(List<String> arguments) {
        if (arguments == null || arguments.isEmpty()) {
            System.out.println("❌ Error: No script file provided.");
            return;
        }
        apply(arguments.get(0));  // Use the first argument as the file path
    }

    @Override
    public Command get(String commandName) {
        // This method is not used in ExecuteScript, so we can leave it unimplemented.
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}













