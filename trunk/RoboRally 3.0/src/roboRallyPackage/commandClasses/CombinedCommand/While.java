
package roboRallyPackage.commandClasses.CombinedCommand;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class While extends CombinedCommand
{
	public While(int programLevel,Condition condition, Command whileCommand)
	{
		super(programLevel);
		this.condition = condition;
		this.whileCommand = whileCommand;
	}
//	public While(Robot robot, Condition condition, Command whileCommand)
//	{
//		super(robot);
//		this.condition = condition;
//		this.whileCommand = whileCommand;
//	}
	
	Condition condition;

	public Condition getCondition()
	{
		return this.condition;
	}

	public Command getWhileCommand()
	{
		return this.whileCommand;
	}

	Command whileCommand;
	
	public void execute(Robot robot)
	{
		while(this.getCondition().results())
		{
			this.getWhileCommand().execute(robot);
		}
	}
	
	public void executeStep(Robot robot)
	{
		if(this.getCondition().results() )
		{
			
			this.getWhileCommand().executeStep(robot);
		}
	}
	
	@Override
	public String toString()
	{
		String indentation = "";
		for(int i = 0; i <= this.getProgramLevel(); i++){
			indentation = indentation + "  ";
		}
		return "(while" + "\n" + indentation+ "  " + this.getCondition().toString()
				+ "\n" + indentation+"  " + this.getWhileCommand().toString() 
				+ "\n" +indentation+")";
	}
}
