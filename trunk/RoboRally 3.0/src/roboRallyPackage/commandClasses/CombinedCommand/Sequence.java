/**
 * 
 */
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
/**
 * @author Nele
 *
 */
public class Sequence extends CombinedCommand
{
	public Sequence(Robot robot, java.util.List<Command> seqCommands)
	{
		super(robot);
		this.seqCommands = seqCommands;
	}
	
	java.util.List<Command> seqCommands;

	/**
	 * @return	...
	 *			| result == ...
	 */
	public java.util.List<Command> getSeqCommands() {
		return this.seqCommands;
	}
	
	public void execute()
	{
		for(Command command: seqCommands)
		{
			command.execute();
		}
	}
}
