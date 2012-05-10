
package roboRallyPackage.gameElementClasses;

import java.util.Random;

import roboRallyPackage.Board;
import roboRallyPackage.Orientation;
import roboRallyPackage.Position;
import roboRallyPackage.Terminatable;
import roboRallyPackage.exceptionClasses.IllegalPositionException;

/**
 * A class of SurpriseBoxes. Extends Item.
 * 
 * @version 3 mei 2012
 * @author Nele & Jonas
 *
 */
public class SurpriseBox extends Item {


	/**
	 * Initialize this new SurpriseBox with the given weight.
	 * @param	weight
	 * 			The weight for this new SurpriseBox in grams.
	 */
	public SurpriseBox(int weight)
	{
		super(weight);
	}

	/**
	 * A robot uses this SurpriseBox. One of the following three events will occur:
	 * 1. The SurpriseBox explodes, hitting the robot and all other Elements on the position of this robot and its adjacent positions.
	 * 2. The robot is teleported to another location.
	 * 3. The SurpriseBox contained another Item (either a battery, a repairkit or 	 new surprisebox),
	 * 		which will be added to the list of items carried by the robot
	 * 
	 * @effect	...
	 * 			| for one random integer i in 0..2:
	 * 			|	if(i == 0)
	 * 			|		than this.takeHit();
	 * 			|	if(i == 1)
	 * 			|		than new Teleporter(robot)
	 * 			|	if(i == 2)
	 * 			|		than for one random integer j in 0..2:
	 * 			|			if( j == 0) than  robot.getPossessions().add(new Battery(2500,5))
	 * 			|			if( j == 1) than  robot.getPossessions().add(new RepairKit(500,5))
	 * 			|			if( j == 1) than  robot.getPossessions().add(new SurpriseBox(5))
	 * @effect	...
	 * 			| this.terminate()
	 * 						
	 * 
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated() || robot.isTerminates()
	 */
	@Override
	public void use(Robot robot) 
	{
		Robot tempRobot = robot;
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		if(robot.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		// the robot has enough 'room' to store the total amount of energy this battery holds.

		Random generator = new Random();
		// Variable containing a random number between 0 and the number of elements on the checked position
		int randomInt = generator.nextInt(3);

		//the box explodes and the robot is hit.
		if(randomInt == 0){
			this.takeHit();
		}
		//the robot is teleported.
		if(randomInt == 1){
			//			Teleporter tele = new Teleporter(robot);
			//			tele.teleport();
			new Terminatable(){
				public void teleport(){
					Random generator =  new Random();
					//long maxCoordX = board.getWidth();
					//long maxCoordY = board.getHeight();

					long maxCoordX = tempRobot.getBoard().getWidth();
					long maxCoordY = tempRobot.getBoard().getHeight();



					int randomIntCoordX = generator.nextInt(((int) maxCoordX));
					int randomIntCoordY = generator.nextInt(((int) maxCoordY));
					Position teleLocation = new Position(randomIntCoordX, randomIntCoordY);
					try{
						robot.setPosition(teleLocation);
					} catch(IllegalPositionException exc){
						robot.terminate();
						System.out.println("Uw robot is nu dood. Hoe jammer!");
					}

				}
			};

		}
		// a new random item is created
		if(randomInt == 2){
			int randomIntTwo = generator.nextInt(3);
			if(randomIntTwo == 0){
				Battery bat = new Battery(2500, 5);
				robot.getPossessions().add(bat);
			}


			if(randomIntTwo == 1){
				RepairKit kit = new RepairKit(500,5);
				robot.getPossessions().add(kit);
			}
			if(randomIntTwo == 2){
				SurpriseBox newBox = new SurpriseBox(5);
				robot.getPossessions().add(newBox);
			}
		}

		this.terminate();
	}


	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * A surprise box explodes and hits all elements on the same position as the box itself
	 * and all the elements on the neighbouring positions of the position of the surprise box.
	 * In the end the surprise box is terminated.
	 * 
	 * @effect	All the elements on the position of this surprise box are hit.
	 * 			| for each element in this.getBoard().getElements(this.getPosition()): element.takeHit()
	 * @effect	All the elements that are standing on a position next to the position of this surprise box are hit.
	 * 			| for each position in this.getPosition().getAllNeighbours():
	 * 			|   for each element in this.getBoard().getElements(position): element.takeHit()
	 * @effect	The surprise box is terminated in the end.
	 * 			| this.terminate()
	 * @throws	IllegalStateException
	 * 			When this surprise box is terminated.
	 * 			| this.isTerminated()
	 */

	@Override
	public void takeHit() throws IllegalStateException
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated surprise box cannot be hit.");
		}
		
		// all the elements on the position of this surprise box are hit.
		for(Element element : this.getBoard().getElements(this.getPosition()))
		{
			element.takeHit();
		}
		
		// all the elements on the neighouring position of the position of this surprise box are hit.
		for(Position position: this.getPosition().getAllNeighbours())
		{
			for(Element element: this.getBoard().getElements(position))
			{
				element.takeHit();
			}
		}
		
		// this surprise box is hit.
		this.terminate();
	}

	/**
	 * Returns a string representation of this surprise box.
	 * 
	 * @return	...
	 * 			| result == "Surprise box with:" + "\n"
	 *			| 			+ super.toString() + "\n"
	 */
	@Override
	public java.lang.String toString()
	{
		return "Surprise box with:" + "\n"
				+ super.toString() +  ";  " + "\n";
	}

}

// randomiser
// teleporteer, ander item (bat, repair, surprise)
// voeg dat ander item toe aan de lijst van items van de robot

//class Teleporter
//{
//
//	//private Board board;
//	//private Robot robot;
//
//	//public Teleporter(Robot robot){
//	//	this.robot = robot;
//	//	this.board = robot.getBoard();
//	//}
//	
//	public Teleporter(Robot robot){
//		this.teleport(robot);
//	}
//
//	public void teleport(Robot robot){
//		Random generator =  new Random();
//		//long maxCoordX = board.getWidth();
//		//long maxCoordY = board.getHeight();
//
//		long maxCoordX = robot.getBoard().getWidth();
//		long maxCoordY = robot.getBoard().getHeight();
//		
//		
//		
//		int randomIntCoordX = generator.nextInt(((int) maxCoordX));
//		int randomIntCoordY = generator.nextInt(((int) maxCoordY));
//		Position teleLocation = new Position(randomIntCoordX, randomIntCoordY);
//		try{
//			robot.setPosition(teleLocation);
//		} catch(IllegalPositionException exc){
//			robot.terminate();
//			System.out.println("Uw robot is nu dood. Hoe jammer!");
//		}
//		
//	}
//
//}
//
