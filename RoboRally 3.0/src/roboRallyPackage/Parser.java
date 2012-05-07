/**
 * 
 */
package roboRallyPackage;

import static java.lang.System.out;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.BasicCommand.*;
import roboRallyPackage.commandClasses.BooleanCommand.*;
import roboRallyPackage.commandClasses.ConditionCommand.*;
import roboRallyPackage.commandClasses.ControleStatementCommand.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.gameElementClasses.*;

/**
 * @author Nele
 *
 */
public class Parser
{
	Parser(Robot robot)
	{
		this.robot = robot;
	}
	
	private Robot robot;
	
	public Robot getRobot()
	{
		return this.robot;
	}

	public Command parse(String fullProgram)
	{
		fullProgram = fullProgram.trim().toLowerCase();
		Command resultCommand  = null;
		
		// BasicCommands
		if(fullProgram.startsWith("(move)"))
		{
			resultCommand = new Move(this.getRobot());
		}
		if(fullProgram.startsWith("(turn"))
		{
			String subProgram = fullProgram.substring("(turn".length()).trim();
			if(subProgram.startsWith("clockwise)"))
			{
				resultCommand = new Turn(this.getRobot(),Direction.CLOCKWISE);
			}
			if(subProgram.startsWith("counterclockwise)"))
			{
				resultCommand = new Turn(this.getRobot(),Direction.COUNTER_CLOCKWISE);
			}
		}
		if(fullProgram.startsWith("(shoot)"))
		{
			resultCommand = new Shoot(this.getRobot());
		}
		if(fullProgram.startsWith("(pick-up-and-use)"))
		{
			resultCommand = new PickupAndUse(this.getRobot());
		}
		
		// ControleStatementCommands
		if(fullProgram.startsWith("(seq"))
		{
			String subProgram = fullProgram.substring("(seq".length()).trim();
			
			java.util.List<Executable> commands = new java.util.ArrayList<Executable>();
			boolean stop = false;
			
			while(! stop)
			{
				if(subProgram.startsWith(")"))
				{
					stop = true;
				}
				else
				{
					// get the first command of the current subProgram
					commands.add((Executable) this.parse(subProgram));
					// cut the command that we just found off the current subProgram-string
					int index = subProgram.indexOf(")");
					subProgram = subProgram.substring(index + 1).trim();
				}
			}
				
			resultCommand = new Sequence(this.getRobot(), commands);
		}
		if(fullProgram.startsWith("(while"))
		{
			String subProgram = fullProgram.substring("(while".length()).trim();

			Condition condition;
			Executable executable;

			condition = (Condition) this.parse(subProgram);
			
			subProgram.substring(subProgram.indexOf("(")).trim();
			if(subProgram.substring(0,subProgram.indexOf(")")).contains("("))
			{
				
			}
			subProgram.substring(subProgram.indexOf(")")).trim();
			
					if(this.parse(subProgram) instanceof Condition)
					{
						condition = this.parse(subProgram) instanceof Condition;
					}
				}
			}

			resultCommand = new While(this.getRobot(),condition,executable);
		}
		if(fullProgram.startsWith("(if"))
		{
			resultCommand = new If();
		}
		
		return resultCommand;
	}
	

}
