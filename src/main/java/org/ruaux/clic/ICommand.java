package org.ruaux.clic;

import org.springframework.boot.ApplicationArguments;

public interface ICommand {

	void execute(ApplicationArguments args);

}
