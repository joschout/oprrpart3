
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
	public Sequence(int programLevel, java.util.List<Command> seqCommands)
	{
		super(programLevel);
		this.seqCommands = seqCommands;
	}
//	public Sequence(Robot robot, java.util.List<Command> seqCommands)
//	{
//		super(robot);
//		this.seqCommands = seqCommands;
//	}
	
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
	
	public void execute(Robot robot)
	{
		for(Command command: seqCommands)
		{
			command.execute(robot);
		}
	}
	
	public void executeStep(Robot robot)
	{
		this.getSeqCommands().get(this.getNextCommandToExecute()).executeStep(robot);
		this.increaseNextCommandToExecute();
	}
	
	public boolean isFullyExecuted()
	{
		return (this.getNextCommandToExecute() == 0);
	}
	
	@Override
	public String toString()
	{
		String result ="(seq";
		String indentation = "";
		for(int i = 1; i <= this.getProgramLevel(); i++)
		{
			indentation = indentation + "  ";
		}
		for(Command command: this.getSeqCommands())
		{
			result = result  + "\n" + indentation+ "  " + command.toString();
		}
		result = result + "\n" + indentation + ")";
		return result;
	}
	
	@Override
	public String getNotationExample()
	{
		return "(seq '(command)' '(command)' ... '(command)')";
	}

}
