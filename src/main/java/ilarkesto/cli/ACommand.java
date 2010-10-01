package ilarkesto.cli;

import ilarkesto.auth.LoginRequiredException;
import ilarkesto.base.Str;
import ilarkesto.di.Context;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ACommand<A extends Arguments> {

	public abstract A createArguments();

	public abstract void assertPermissions() throws LoginRequiredException;

	public abstract Object execute(A arguments) throws BadSyntaxException, CommandExecutionFailedException;

	public String getName() {
		String name = getClass().getSimpleName();
		name = Str.removeSuffix(name, "Command");
		name = name.toLowerCase();
		return name;
	}

	private String description;

	public ACommand(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public Collection getAliases() {
		return new ArrayList(1);
	}

	public String getUsage() {
		return getName() + createArguments().getUsage();
	}

	protected final <T> T autowire(T target) {
		return Context.get().autowire(target);
	}

}
