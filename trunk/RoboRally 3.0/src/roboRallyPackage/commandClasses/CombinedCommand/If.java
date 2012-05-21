
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class If extends CombinedCommand
{
	public If(Robot robot, Condition condition, Command ifCommand, Command elseCommand)
	{
		super(robot);
		this.condition = condition;
		this.ifCommand = ifCommand;
		this.elseCommand = elseCommand;
	}
	
	private Condition condition;
	
	public Condition getCondition()
	{
		return this.condition;
	}

	public Command getIfCommand()
	{
		return this.ifCommand;
	}

	
	public Command getElseCommand() 
	{
		return this.elseCommand;
	}

	private Command ifCommand;
	private Command elseCommand;
	
	public void execute()
	{
		if(this.getCondition().results())
		{
			this.getIfCommand().execute();
		}
		else
		{
			this.getElseCommand().execute();
		}
	}
	
	public void executeStep()
	{
		if(this.getCondition().results())
		{
			this.getIfCommand().executeStep();
		}
		else
		{
			this.getElseCommand().executeStep();
		}
	}
	
	@Override
	public String toString()
	{
		return "(if"+ "\n" + "  " + this.getCondition().toString()
				+ "\n" + "  " + this.getIfCommand().toString()
				+ "\n" + "  " + this.getElseCommand().toString()
				+ "\n" +  ")";
	}
}
