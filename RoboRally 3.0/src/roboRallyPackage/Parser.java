
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
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Parser
{
	public static Program parse(int programLevel, Robot robot, String inputProgram) throws IllegalSyntaxException
	{
		inputProgram = inputProgram.replaceAll(" ","").toLowerCase();
		inputProgram = inputProgram.replaceAll("\n","").toLowerCase();
		if(inputProgram.equals("(move)")
				|| inputProgram.equals("(turnclockwise)") 
				|| inputProgram.equals("(turncounterclockwise)")
				|| inputProgram.equals("(shoot)")
				|| inputProgram.equals("(pick-up-and-use)"))
		{
			return Parser.parseBasicCommand(programLevel, inputProgram);
		}
		if(inputProgram.startsWith("(seq")
				|| inputProgram.startsWith("(while")
				|| inputProgram.startsWith("(if"))
		{
			return Parser.parseCombinedCommand(programLevel, robot,inputProgram);
		}
		if(inputProgram.equals("(true)")
				|| inputProgram.startsWith("(energy-at-least")
				|| inputProgram.equals("(at-item)")
				|| inputProgram.equals("(can-hit-robot)")
				|| inputProgram.equals("(wall)")
				|| inputProgram.startsWith("(in-subrange")
				|| inputProgram.equals("(item)"))
		{
			return Parser.parseBasicCondition(programLevel,robot, inputProgram);
		}
		if(inputProgram.startsWith("(and")
				|| inputProgram.startsWith("(or")
				|| inputProgram.startsWith("(not"))
		{
			return Parser.parseCombinedCondition(programLevel,robot,inputProgram);
		}
		else
		{
			throw new IllegalSyntaxException();
		}
	}

	private static Program parseBasicCommand(int programLevel, String inputProgram)
	{
		if(inputProgram.equals("(move)"))
		{ 
			return new Move(programLevel);
		}
		if(inputProgram.equals("(turnclockwise)"))
		{
			return new Turn(programLevel,Direction.CLOCKWISE);
		}
		if(inputProgram.equals("(turncounterclockwise)"))
		{
			return new Turn(programLevel,Direction.COUNTER_CLOCKWISE);
		}
		if(inputProgram.equals("(shoot)"))
		{
			return new Shoot(programLevel);
		}
		if(inputProgram.equals("(pick-up-and-use)"))
		{
			return new PickupAndUse(programLevel);
		}
		assert false;
		return null;
	}

	private static Program parseCombinedCommand(int programLevel,Robot robot, String inputProgramString)
	{
		if(inputProgramString.startsWith("(seq"))
		{
			// cut off the "(seq" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(seq", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<Command> parametersSeqAsCommands = new java.util.ArrayList<Command>();
			java.util.ArrayList<String> subProgramStrings = Parser.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				Program subProgram = Parser.parse(programLevel++,robot, subProgramString);
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

			return new Sequence(programLevel, parametersSeqAsCommands);
		}

		if(inputProgramString.startsWith("(while"))
		{
			// cut off the "(while" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(while", " ").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of programs that can be given to the while-object
			// the first program is supposed to be a condition, the second is supposed to be a command
			java.util.ArrayList<Program> parametersWhileAsPrograms = new java.util.ArrayList<Program>();
			java.util.ArrayList<String> subProgramStrings = Parser.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				parametersWhileAsPrograms.add(Parser.parse(programLevel++,robot, subProgramString));
			}

			// check whether the given list has a condition as first element and a command as second element
			if(parametersWhileAsPrograms.size() == 2 && (parametersWhileAsPrograms.get(0) instanceof Condition)
					&& (parametersWhileAsPrograms.get(1) instanceof Command))
			{
				return new While(programLevel, (Condition) parametersWhileAsPrograms.get(0),
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
			java.util.ArrayList<String> subProgramStrings = Parser.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				parametersIfAsPrograms.add(Parser.parse(programLevel++,robot, subProgramString));
			}

			// check whether the given list has a condition as first element and a command as second and third element
			if(parametersIfAsPrograms.size() == 3 && (parametersIfAsPrograms.get(0) instanceof Condition)
					&& (parametersIfAsPrograms.get(1) instanceof Command)
					&& (parametersIfAsPrograms.get(2) instanceof Command))
			{
				return new If(programLevel, (Condition) parametersIfAsPrograms.get(0),
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

	private static Program parseBasicCondition(int programLevel,Robot robot, String inputProgram)
	{
		if(inputProgram.equals("(true)"))
		{
			return new True(programLevel);
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
				return new EnergyAtLeast(programLevel,robot, energyValue);
			}
			else
			{
				throw new IllegalSyntaxException();
			}
		}
		if(inputProgram.equals("(at-item)"))
		{
			return new AtItem(programLevel,robot);
		}
		if(inputProgram.equals("(can-hit-robot)"))
		{
			return new CanHitRobot(programLevel,robot);
		}
		if(inputProgram.equals("(wall)"))
		{
			return new NextToWall(programLevel,robot);
		}
		if(inputProgram.startsWith("(in-subrange"))
		{
			// cut off the "(in-subrange" and the last closing bracket of the string
			inputProgram = inputProgram.replaceFirst("\\(in-subrange", "").trim();
			inputProgram = inputProgram.substring(0, inputProgram.length() - 1);

			// split the remaining string into substrings, that should all contain a number
			String[] coordinateStrings = inputProgram.split(",");
			long[] coordinates = new long[4];
			
			// convert all the string representations to longs; if the strings are no numbers, an exception is thrown
			try
			{
				for(int i = 0; i < coordinates.length; i++)
				{
					coordinates[i] = Long.valueOf(coordinateStrings[i]).longValue();
				}
			}
			catch(NumberFormatException exc)
			{
				throw new IllegalSyntaxException();
			}

			// check whether the number of given substrings is equal to the number of longs; if not an exception is thrown
			if(coordinateStrings.length == coordinates.length)
			{
				return new InPartOfBoard(programLevel,robot,coordinates);
			}
		}
		if(inputProgram.equals("(item)"))
		{
			return new isItem(programLevel);
		}
		else
		{
			throw new IllegalSyntaxException();
		}
	}

	private static Program parseCombinedCondition(int programLevel,Robot robot, String inputProgramString)
	{
		if(inputProgramString.startsWith("(and"))
		{
			// cut off the "(and" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(and", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<Condition> parametersAndAsConditions = new java.util.ArrayList<Condition>();
			java.util.ArrayList<String> subProgramStrings = Parser.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				Program subProgram = Parser.parse(programLevel++,robot, subProgramString);
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

			return new And(programLevel, parametersAndAsConditions);
		}

		if(inputProgramString.startsWith("(or"))
		{
			// cut off the "(or" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(or", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<Condition> parametersOrAsConditions = new java.util.ArrayList<Condition>();
			java.util.ArrayList<String> subProgramStrings = Parser.getSubstringsBracketCutter(inputProgramString);
			for(String subProgramString: subProgramStrings)
			{
				Program subProgram = Parser.parse(programLevel++,robot,subProgramString);
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

			return new Or(programLevel, parametersOrAsConditions);
		}

		if(inputProgramString.startsWith("(not"))
		{
			// cut off the "(not" and the last closing bracket of the string
			inputProgramString = inputProgramString.replaceFirst("\\(not", "").trim();
			inputProgramString = inputProgramString.substring(0, inputProgramString.length() - 1);

			// make a list of commands that can be given to the sequence-object
			java.util.ArrayList<String> subProgramStrings = Parser.getSubstringsBracketCutter(inputProgramString);
			Program parametersNotAsProgram = Parser.parse(programLevel++,robot,subProgramStrings.get(0));

			// check whether the given list of programs contains only 1 programs and this programs is a condition; if not throw an exception
			if(subProgramStrings.size() == 1 && parametersNotAsProgram instanceof Condition)
			{
				return new Not(programLevel, (Condition) parametersNotAsProgram);
			}
			else
			{
				throw new IllegalSyntaxException();
			}
		}

		assert false;
		return null;
	}

	private static java.util.ArrayList<String> getSubstringsBracketCutter(String fullProgram)
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
	
	public static boolean isValidStringProgram(String inputProgram)
	{
		if(inputProgram == null)
		{
			return false;
		}
		try
		{
			Parser.parse(0, new Robot(), inputProgram);
		}
		catch(IllegalSyntaxException exc)
		{
			return false;
		}
		return true;
	}
}
