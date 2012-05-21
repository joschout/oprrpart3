/**
 * 
 */
package roboRallyPackage;

import java.util.ArrayList;

import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.BasicCommandClasses.*;
import roboRallyPackage.commandClasses.BasicConditionClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
import roboRallyPackage.commandClasses.CombinedCommand.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.exceptionClasses.*;

/**
 * @author Nele
 *
 */
public class Parser
{
	
	public Parser(){
		
	}
//	public Parser(Robot robot)
//	{
//		this.robot = robot;
//	}
//
//	private Robot robot;
//
//	public Robot getRobot()
//	{
//		return this.robot;
//	}

	//	public Command parse(String fullProgram)
	//	{
	//		fullProgram = fullProgram.replaceAll(" ","").toLowerCase();
	//		
	//		Command resultCommand  = null;
	//		
	//		// BasicCommands
	//		if(fullProgram.startsWith("(move)"))
	//		{
	//			resultCommand = new Move(this.getRobot());
	//		}
	//		if(fullProgram.startsWith("(turn"))
	//		{
	//			String subProgram = fullProgram.substring("(turn".length()).trim();
	//			if(subProgram.startsWith("clockwise)"))
	//			{
	//				resultCommand = new Turn(this.getRobot(),Direction.CLOCKWISE);
	//			}
	//			if(subProgram.startsWith("counterclockwise)"))
	//			{
	//				resultCommand = new Turn(this.getRobot(),Direction.COUNTER_CLOCKWISE);
	//			}
	//		}
	//		if(fullProgram.startsWith("(shoot)"))
	//		{
	//			resultCommand = new Shoot(this.getRobot());
	//		}
	//		if(fullProgram.startsWith("(pick-up-and-use)"))
	//		{
	//			resultCommand = new PickupAndUse(this.getRobot());
	//		}
	//		
	//		// ControleStatementCommands
	//		if(fullProgram.startsWith("(seq"))
	//		{
	//			String subProgram = fullProgram.substring("(seq".length()).trim();
	//			
	//			java.util.List<Executable> commands = new java.util.ArrayList<Executable>();
	//			boolean stop = false;
	//			
	//			while(! stop)
	//			{
	//				if(subProgram.startsWith(")"))
	//				{
	//					stop = true;
	//				}
	//				else
	//				{
	//					// get the first command of the current subProgram
	//					commands.add((Executable) this.parse(subProgram));
	//					// cut the command that we just found off the current subProgram-string
	//					int index = subProgram.indexOf(")");
	//					subProgram = subProgram.substring(index + 1).trim();
	//				}
	//			}
	//				
	//			resultCommand = new Sequence(this.getRobot(), commands);
	//		}
	//		if(fullProgram.startsWith("(while"))
	//		{
	//			String subProgram = fullProgram.substring("(while".length()).trim();
	//
	//			Condition condition;
	//			Executable executable;
	//
	//			condition = (Condition) this.parse(subProgram);
	//			
	//			subProgram.substring(subProgram.indexOf("(")).trim();
	//			if(subProgram.substring(0,subProgram.indexOf(")")).contains("("))
	//			{
	//				
	//			}
	//			subProgram.substring(subProgram.indexOf(")")).trim();
	//			
	//					if(this.parse(subProgram) instanceof Condition)
	//					{
	//						condition = this.parse(subProgram) instanceof Condition;
	//					}
	//				}
	//			}
	//
	//			resultCommand = new While(this.getRobot(),condition,executable);
	//		}
	//		if(fullProgram.startsWith("(if"))
	//		{
	//			resultCommand = new If();
	//		}
	//		
	//		return resultCommand;
	//	}
	//	



	public Program parse(Robot robot, String inputProgram) throws IllegalSyntaxException
	{
		inputProgram = inputProgram.replaceAll(" ","").toLowerCase();
		inputProgram = inputProgram.replaceAll("\n","").toLowerCase();
		if(inputProgram.equals("(move)")
				|| inputProgram.equals("(turnclockwise)") 
				|| inputProgram.equals("(turncounterclockwise)")
				|| inputProgram.equals("(shoot)")
				|| inputProgram.equals("(pick-up-and-use)"))
		{
			return this.parseBasicCommand(inputProgram);
		}
		if(inputProgram.startsWith("(seq")
				|| inputProgram.startsWith("(while")
				|| inputProgram.startsWith("(if"))
		{
			return this.parseCombinedCommand(robot,inputProgram);
		}
		if(inputProgram.equals("(true)")
				|| inputProgram.startsWith("(energy-at-least")
				|| inputProgram.equals("(at-item)")
				|| inputProgram.equals("(can-hit-robot)")
				|| inputProgram.equals("(wall)"))
		{
			return this.parseBasicCondition(robot, inputProgram);
		}
		if(inputProgram.startsWith("(and")
				|| inputProgram.startsWith("(or")
				|| inputProgram.startsWith("(not"))
		{
			return this.parseCombinedCondition(robot,inputProgram);
		}
		else
		{
			throw new IllegalSyntaxException();
		}
	}

	public Program parseBasicCommand(String inputProgram)
	{
		if(inputProgram.equals("(move)"))
		{ 
			return new Move();
		}
		if(inputProgram.equals("(turnclockwise)"))
		{
			return new Turn(Direction.CLOCKWISE);
		}
		if(inputProgram.equals("(turncounterclockwise)"))
		{
			return new Turn(Direction.COUNTER_CLOCKWISE);
		}
		if(inputProgram.equals("(shoot)"))
		{
			return new Shoot();
		}
		if(inputProgram.equals("(pick-up-and-use)"))
		{
			return new PickupAndUse();
		}
		assert false;
		return null;
	}

	public Program parseCombinedCommand(Robot robot, String inputProgramString)
	{
		if(inputProgramString.startsWith("(seq"))
		{
			// cut off the "(seq" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(seq", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<Command> parametersSeqAsCommands = new java.util.ArrayList<Command>();
			java.util.ArrayList<String> subProgramStrings = this.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				Program subProgram = this.parse(robot, subProgramString);
				// check whether the given program is a command; if so add it to the list of commands, if not throw an exception
				if(subProgram instanceof Command)
				{
					parametersSeqAsCommands.add((Command) subProgram);
				}
				else
				{
					throw new IllegalSyntaxException();
				}
			}

			return new Sequence( parametersSeqAsCommands);
		}

		if(inputProgramString.startsWith("(while"))
		{
			// cut off the "(while" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(while", " ").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of programs that can be given to the while-object
			// the first program is supposed to be a condition, the second is supposed to be a command
			java.util.ArrayList<Program> parametersWhileAsPrograms = new java.util.ArrayList<Program>();
			java.util.ArrayList<String> subProgramStrings = this.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				parametersWhileAsPrograms.add(this.parse(robot, subProgramString));
			}

			// check whether the given list has a condition as first element and a command as second element
			if(parametersWhileAsPrograms.size() == 2 && (parametersWhileAsPrograms.get(0) instanceof Condition)
					&& (parametersWhileAsPrograms.get(1) instanceof Command))
			{
				return new While( (Condition) parametersWhileAsPrograms.get(0),
												  (Command) parametersWhileAsPrograms.get(1));
			}
			else
			{
				throw new IllegalSyntaxException();
			}
		}

		if(inputProgramString.startsWith("(if"))
		{
			// cut off the "(if" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(if", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of programs that can be given to the if-object
			// the first program is supposed to be a condition, the second and third are supposed to be a command
			java.util.ArrayList<Program> parametersIfAsPrograms = new java.util.ArrayList<Program>();
			java.util.ArrayList<String> subProgramStrings = this.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				parametersIfAsPrograms.add(this.parse(robot, subProgramString));
			}

			// check whether the given list has a condition as first element and a command as second and third element
			if(parametersIfAsPrograms.size() == 3 && (parametersIfAsPrograms.get(0) instanceof Condition)
					&& (parametersIfAsPrograms.get(1) instanceof Command)
					&& (parametersIfAsPrograms.get(2) instanceof Command))
			{
				return new If( (Condition) parametersIfAsPrograms.get(0),
						(Command) parametersIfAsPrograms.get(1),
						(Command) parametersIfAsPrograms.get(2));
			}
			else
			{
				throw new IllegalSyntaxException();
			}
		}
		assert false;
		return null;
	}

	public Program parseBasicCondition(Robot robot, String inputProgram)
	{
		if(inputProgram.equals("(true)"))
		{
			return new True();
		}
		if(inputProgram.startsWith("(energy-at-least"))
		{
			// cut off the "(energy-at-least" and the last closing bracket of the string
			inputProgram = inputProgram.replaceFirst("\\(energy-at-least", "").trim();
			inputProgram = inputProgram.substring(0, inputProgram.length() - 1);

			// convert the remaining string to a double; if the remaining string is not a number, an exception in thrown
			double energyValue = -1;
			try
			{
				energyValue = Double.valueOf(inputProgram).doubleValue();
			}
			catch(NumberFormatException exc)
			{
				throw new IllegalSyntaxException();
			}

			// check whether the energyValue is a valid energy amount; if not an exception is thrown
			if (EnergyAmount.isValidEnergyAmount(energyValue))
			{
				return new EnergyAtLeast(robot, energyValue);
			}
			else
			{
				throw new IllegalSyntaxException();
			}
		}
		if(inputProgram.equals("(at-item)"))
		{
			return new AtItem(robot);
		}
		if(inputProgram.equals("(can-hit-robot)"))
		{
			return new CanHitRobot(robot);
		}
		if(inputProgram.equals("(wall)"))
		{
			return new NextToWall(robot);
		}
		assert false;
		return null;
	}

	public Program parseCombinedCondition(Robot robot, String inputProgramString)
	{
		if(inputProgramString.startsWith("(and"))
		{
			// cut off the "(and" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(and", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<Condition> parametersAndAsConditions = new java.util.ArrayList<Condition>();
			java.util.ArrayList<String> subProgramStrings = this.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				Program subProgram = this.parse(robot, subProgramString);
				// check whether the given program is a condition; if so add it to the list of commands, if not throw an exception
				if(subProgram instanceof Condition)
				{
					parametersAndAsConditions.add((Condition) subProgram);
				}
				else
				{
					throw new IllegalSyntaxException();
				}
			}

			return new And( parametersAndAsConditions);
		}

		if(inputProgramString.startsWith("(or"))
		{
			// cut off the "(or" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(or", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<Condition> parametersOrAsConditions = new java.util.ArrayList<Condition>();
			java.util.ArrayList<String> subProgramStrings = this.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				Program subProgram = this.parse(robot,subProgramString);
				// check whether the given program is a condition; if so add it to the list of commands, if not throw an exception
				if(subProgram instanceof Condition)
				{
					parametersOrAsConditions.add((Condition) subProgram);
				}
				else
				{
					throw new IllegalSyntaxException();
				}
			}

			return new Or( parametersOrAsConditions);
		}

		if(inputProgramString.startsWith("(not"))
		{
			// cut off the "(not" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(not", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<String> subProgramStrings = this.getSubstringsBracketCutter(inputProgramString);
			Program parametersNotAsProgram = this.parse(robot,subProgramStrings.get(0));

			// check whether the given list of programs contains only 1 programs and this programs is a condition; if not throw an exception
			if(subProgramStrings.size() == 1 && parametersNotAsProgram instanceof Condition)
			{
				return new Not( (Condition) parametersNotAsProgram);
			}
			else
			{
				throw new IllegalSyntaxException();
			}
		}

		assert false;
		return null;
	}

	public java.util.ArrayList<String> getSubstringsBracketCutter(String fullProgram)
	{
		// stores the index of the first '(' in the given String
		int openingBracketIndex = 0;
		// stores the index of the ')' that closes the first '('
		int closingBracketIndex = 0;

		// counts the number of opening brackets '(' the loop encounters until the loop encounters its closing bracket ')'
		int openingBracketCounter = 0;
		// counts the number of closing brackets ')' the loop encounters 
		// until the loop encounters the closing bracket ')' of the first opening bracket.
		int closingBracketCounter = 0;


		char[] fullPrStrings = fullProgram.toCharArray();
		java.util.ArrayList<String> subStringList = new ArrayList<String>();

		for(int index = 0; index <= fullPrStrings.length - 1; index++)
		{
			// the first opening bracket is not yet closed by a closing bracket
			if(closingBracketCounter <= openingBracketCounter)
			{
				if (fullPrStrings[index] == '(')
				{
					if(openingBracketCounter == 0)
					{
						openingBracketIndex = index;
					}
					openingBracketCounter++;
				}
				if (fullPrStrings[index] == ')')
				{
					closingBracketCounter++;
					// the first opening bracket is closed; one substring is found
					if (openingBracketCounter == closingBracketCounter)
					{
						closingBracketIndex = index;

						openingBracketCounter = 0;
						closingBracketCounter = 0;

						subStringList.add(fullProgram.substring(openingBracketIndex, closingBracketIndex + 1));
					}
				}
			}

		}
		return subStringList;
	}


	//	public java.util.ArrayList<Program> parse2(String inputProgram)
	//	{
	//		
	//		inputProgram = inputProgram.replaceAll(" ","").toLowerCase();
	//		java.util.ArrayList<String> progAsSubProgs = this.getSubstringsBracketCutter(inputProgram);
	//		java.util.ArrayList<Program> program = new ArrayList<Program>();
	//		
	//		for(int i=0; i<=progAsSubProgs.size()-1;i++)
	//		{
	//		// BasicCommands
	//				if(progAsSubProgs.get(i).equals("(move)"))
	//				{
	//					program.add(new Move(this.getRobot()));
	//				}
	//				if(progAsSubProgs.get(i).equals("(turnclockwise)"))
	//				{
	//					program.add( new Turn(this.getRobot(),Direction.CLOCKWISE));
	//				}
	//				if(progAsSubProgs.get(i).equals("(turncounterclockwise)"))
	//				{
	//					program.add( new Turn(this.getRobot(),Direction.COUNTER_CLOCKWISE));
	//				}
	//				if(progAsSubProgs.get(i).equals("(shoot)"))
	//				{
	//					program.add(new Shoot(this.getRobot()));
	//				}
	//				if(progAsSubProgs.get(i).equals("(pick-up-and-use)"))
	//				{
	//					program.add(new PickupAndUse(this.getRobot()));
	//				}
	//				
	//				// ControleStatementCommands
	//				if(progAsSubProgs.get(i).startsWith("(seq"))
	//				{
	//					String subProgram = progAsSubProgs.get(i).replaceFirst("(seq", "").trim();
	//					// het haakje ')' van "(seq" moet er nog vanachter afgeknipt worden
	//					subProgram = subProgram.substring(0, subProgram.length()-2);
	//					java.util.ArrayList<Program> subProgAsSubProgs = this.parse2(subProgram);
	//					// mag dit?
	//					if(subProgAsSubProgs.contains(Condition.class))
	//					{
	//						throw new IllegalSyntaxException();
	//					}
	//					
	//					program.add(new Sequence(this.getRobot(), ((java.util.ArrayList<Command>) subProgAsSubProgs)));
	//				}
	//				if(progAsSubProgs.get(i).startsWith("(while"))
	//				{
	//					String subProgram = progAsSubProgs.get(i).replaceFirst("(while", "").trim();
	//					subProgram = subProgram.substring(0, subProgram.length()-2);
	//					java.util.ArrayList<Program> subProgAsSubProgs = this.parse2(subProgram);
	//					
	//					if(subProgAsSubProgs.size() == 2 
	//							&& (subProgAsSubProgs.get(0) instanceof  Condition)
	//							&& (subProgAsSubProgs.get(1) instanceof  Command))
	//					{
	//						program.add(new While(this.getRobot(), (Condition) subProgAsSubProgs.get(0), (Command) subProgAsSubProgs.get(1)));
	//					}
	//					else{
	//						throw new IllegalSyntaxException();
	//					}
	//				}
	//				if(progAsSubProgs.get(i).startsWith("(if"))
	//				{
	//					String subProgram = progAsSubProgs.get(i).replaceFirst("(if", "").trim();
	//					subProgram = subProgram.substring(0, subProgram.length()-2);
	//					java.util.ArrayList<Program> subProgAsSubProgs=this.parse2(subProgram);
	//	
	//					if(subProgAsSubProgs.size() == 3
	//							&& (subProgAsSubProgs.get(0) instanceof Condition)
	//							&& (subProgAsSubProgs.get(1) instanceof Command)
	//							&&(subProgAsSubProgs.get(2) instanceof Command))
	//					{
	//						program.add(new If(this.getRobot(), ((Condition) subProgAsSubProgs.get(0)), ((Command)subProgAsSubProgs.get(1)), ((Command )subProgAsSubProgs.get(2))));
	//					} 
	//					else
	//					{
	//						throw new IllegalSyntaxExeption();
	//					}
	//				}
	//				
	//				
	//				//the BasicConditions
	//				if(progAsSubProgs.get(i).equals("(true)"))
	//				{
	//					program.add(new True(this.getRobot()));
	//				}
	//				if(progAsSubProgs.get(i).startsWith("(energy-at-least"))
	//				{
	//					String subProgram = progAsSubProgs.get(i).replaceFirst("(energy-at-least", "").trim();
	//					subProgram = subProgram.substring(0, subProgram.length()-2);
	//					double energyValue = Double.valueOf(subProgram).doubleValue();
	//					if (EnergyAmount.isValidEnergyAmount(energyValue))
	//					{
	//						program.add(new EnergyAtLeast(this.getRobot(), energyValue));
	//					}
	//					else
	//					{
	//						throw new IllegalSyntaxException();
	//					}
	//				}
	//				if(progAsSubProgs.get(i).equals("(at-item)"))
	//				{
	//					program.add(new AtItem(this.getRobot()));
	//				}
	//				if(progAsSubProgs.get(i).equals("(can-hit-robot)"))
	//				{
	//					program.add(new CanHitRobot(this.getRobot()));
	//				}
	//				if(progAsSubProgs.get(i).equals("(wall)")){
	//					program.add(new NextToWall(this.getRobot()));
	//				}
	//				
	//				
	//				//the CombinedConditions
	//				if(progAsSubProgs.get(i).startsWith("(and"))
	//				{
	//					String subProgram = progAsSubProgs.get(i).replaceFirst("(and", "").trim();
	//					// het haakje ')' van "(seq" moet er nog vanachter afgeknipt worden
	//					subProgram = subProgram.substring(0, subProgram.length()-2);
	//					java.util.ArrayList subProgAsSubProgs=this.parse2(subProgram);
	//					//mag dit?
	//					if(subProgAsSubProgs.contains(Command.class))
	//					{
	//						throw new IllegalSyntaxException();
	//					}
	//				
	//					program.add(new And(this.getRobot(), ((java.util.ArrayList<Condition>) subProgAsSubProgs)));
	//				}
	//				if(progAsSubProgs.get(i).startsWith("(or"))
	//				{
	//					String subProgram = progAsSubProgs.get(i).replaceFirst("(or", "").trim();
	//					// het haakje ')' van "(seq" moet er nog vanachter afgeknipt worden
	//					subProgram = subProgram.substring(0, subProgram.length()-2);
	//					java.util.ArrayList subProgAsSubProgs=this.parse2(subProgram);
	//					// mag dit?
	//					if(subProgAsSubProgs.contains(Command.class))
	//					{
	//						throw new IllegalSyntaxException();
	//					}
	//				// ook hier is zijn de condities een array terwijl een lijst handiger zou zijn
	//					program.add(new And(this.getRobot(), ((java.util.ArrayList<Condition>) subProgAsSubProgs)));
	//				}
	//				if(progAsSubProgs.get(i).startsWith("(not"))
	//				{
	//					String subProgram = progAsSubProgs.get(i).replaceFirst("(not", "").trim();
	//					// het haakje ')' van "(seq" moet er nog vanachter afgeknipt worden
	//					subProgram = subProgram.substring(0, subProgram.length()-2);
	//					java.util.ArrayList subProgAsSubProgs=this.parse2(subProgram);
	//					
	//				
	//					if(subProgAsSubProgs.size() == 1 &&(subProgAsSubProgs.get(0) instanceof  Condition))
	//					{
	//						program.add(new Not(this.getRobot(), ((Condition)subProgAsSubProgs.get(0))));
	//					}
	//					else
	//					{
	//						throw new IllegalSyntaxException();
	//					}
	//				}
	//				return program;
	//			}
	//	}
}
