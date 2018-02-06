package org.ruaux.clic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public abstract class AbstractApplication implements ApplicationRunner {

	public void run(ApplicationArguments args) throws Exception {
		Map<String, ICommand> commands = getCommandMap();
		if (args.getNonOptionArgs().size() > 0) {
			for (String commandName : args.getNonOptionArgs()) {
				ICommand command = commands.get(commandName);
				if (command == null) {
					System.err.println("Invalid command: " + commandName);
				} else {
					command.execute();
				}
			}
		} else {
			System.out.println("No command passed. Valid commands are: "
					+ Arrays.toString(commands.keySet().toArray(new String[0])));
		}
	}

	private Map<String, ICommand> getCommandMap() {
		Map<String, ICommand> commandMap = new HashMap<String, ICommand>();
		for (ICommand command : getCommands()) {
			commandMap.put(getCommandName(command), command);
		}
		return commandMap;
	}

	private String getCommandName(ICommand command) {
		if (command.getClass().isAnnotationPresent(Command.class)) {
			return ((Command) command.getClass().getAnnotation(Command.class)).value();
		}
		return command.getClass().getSimpleName();
	}

	protected abstract List<ICommand> getCommands();

}