import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInvoker {
    private final Map<String, Command> commands = new HashMap<>();

    public void register(String name, Command command) {
        commands.put(name.toLowerCase(), command); // 🔥 Store commands in lowercase
    }

    public void execute(String input) {
        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0].toLowerCase(); // 🔥 Fix: Convert input to lowercase
        String argument = (parts.length > 1) ? parts[1] : null;

        Command command = commands.get(commandName);

        if (command == null) {
            System.out.println("❌ Error: Command '" + commandName + "' not found. Type 'help' for a list of commands.");
            return;
        }

        if (command instanceof ApplyCommand) {
            ((ApplyCommand) command).apply(argument);
        } else {
            command.execute();
        }
    }

    public void execute(String commandName, List<String> arguments) {
        Command command = commands.get(commandName.toLowerCase());
        if (command instanceof ApplyCommand) {
            ((ApplyCommand) command).apply(arguments);
        } else {
            System.out.println("❌ Error: Command '" + commandName + "' does not support arguments.");
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public Command get(String commandName) {
        return commands.get(commandName.toLowerCase());
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName.toLowerCase()); // 🔥 Fix: Return actual command
    }
}









