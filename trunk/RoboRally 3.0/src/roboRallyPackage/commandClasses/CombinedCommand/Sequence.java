
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Sequence extends CombinedCommand
{
	public Sequence(Robot robot, java.util.List<Command> seqCommands)
	{
		super(robot);
		this.seqCommands = seqCommands;
	}
	
	java.util.List<Command> seqCommands;
	int nextCommandToExecute = 0;

	public int getNextCommandToExecute() 
	{
		return this.nextCommandToExecute;
	}

	public void increaseNextCommandToExecute() 
	{
		this.nextCommandToExecute = (this.getNextCommandToExecute() + 1)%this.getSeqCommands().size();
	}

	public java.util.List<Command> getSeqCommands()
	{
		return this.seqCommands;
	}
	
	public void execute()
	{
		for(Command command: seqCommands)
		{
			command.execute();
		}
	}
	
	public void executeStep()
	{
		this.getSeqCommands().get(this.getNextCommandToExecute()).executeStep();
		this.increaseNextCommandToExecute();
	}
	
	@Override
	public String toString()
	{
		String result ="(seq";
		for(Command command: this.getSeqCommands())
		{
			result =result  + "\n" + "  " +command.toString();
		}
		result =result + "\n" + ")";
		return result;
	}
}
