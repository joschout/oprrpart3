
package roboRallyPackage.gameElementClasses;

import java.util.Random;

import roboRallyPackage.Board;
import roboRallyPackage.Orientation;
import roboRallyPackage.Position;
import roboRallyPackage.exceptionClasses.IllegalPositionException;

/**
 * @author Nele
 *
 */
public class SurpriseBox extends Item {


	/**
	 * Initialize this new SurpriseBox with the given weight.
	 * @param	weight
	 * 			The weight for this new SurpriseBox in grams.
	 */
	public SurpriseBox(int weight){
		super(weight);
	}

	/**
	 * A robot uses this SurpriseBox. One of the following three events will occur:
	 * 1. The SurpriseBox explodes, hitting the robot and all other Elements on the position of this robot and its adjacent positions.
	 * 2. The robot is teleported to another location.
	 * 3. The SurpriseBox contained another Item (either a battery, a repairkit or 	 new surprisebox),
	 * 		which will be added to the list of items carried by the robot
	 * 
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated() || robot.isTerminates()
	 */
	@Override
	public void use(Robot robot) {
		
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
			Teleporter tele = new Teleporter(robot);
			tele.teleport();
		}
		// a new random item is created
		if(randomInt == 2){
			int randomIntTwo = generator.nextInt(3);
			if(randomIntTwo == 0){
				Battery bat = new Battery(2500, 5);
				robot.getPossessions().add(bat);
			}
	
		
			if(randomIntTwo == 1){
				RepairKit kit = new RepairKit();
				robot.getPossessions().add(kit);
			}
			if(randomIntTwo == 2){
				SurpriseBox newBox = new SurpriseBox(5);
				robot.getPossessions().add(newBox);
			}
		}

		this.terminate();
	}
	@Override
	public void takeHit() {
		for(Element element:this.getBoard().getElements(this.getPosition())){
			element.takeHit();
		}
		for(Orientation orientation: Orientation.values()){
			for(Element element: this.getBoard().getElements(this.getPosition().getNeighbour(orientation))){
				element.takeHit();
			}
		}
		this.terminate();

	}


}

// randomiser
// teleporteer, ander item (bat, repair, surprise)
// voeg dat ander item toe aan de lijst van items van de robot

class Teleporter
{

	private Board board;
	private Robot robot;

	public Teleporter(Robot robot){
		this.robot = robot;
		this.board = robot.getBoard();
	}

	public void teleport(){
		Random generator =  new Random();
		long maxCoordX = board.getWidth();
		long maxCoordY = board.getHeight();

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

}

