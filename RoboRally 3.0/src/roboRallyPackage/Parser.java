/**
 * 
 */
package roboRallyPackage;

import static java.lang.System.out;
import roboRallyPackage.commandClasses.*;

/**
 * @author Nele
 *
 */
public class Parser
{
	public Command parse(String fullProgram)
	{
		String[] words = fullProgram.split(" ");
		
		if(words.contains("while("))
		{
			
		}
		
		
		return null;
	}
	
	private void processCommand(String command) {
		
		if(words[0].equals("addrobot") && 4 <= words.length && words.length <= 5) {
			String name = words[1];
			if(robots.containsKey(name)) {
				out.println("robot named " + name + " already exists");
				return;
			}
			long x, y;
			try {
				x = Long.parseLong(words[2]);
				y = Long.parseLong(words[3]);
			} catch(NumberFormatException ex) {
				out.println("position expected but found " + words[2] + " " + words[3]);
				return;
			}
			double initialEnergy = 10000;
			if(5 <= words.length ) {
				try {
					initialEnergy = Double.parseDouble(words[4]);
				} catch(NumberFormatException ex) {
					out.println("double expected but found " + words[4]);
					return;
				}
			}
			Robot newRobot = facade.createRobot(1, initialEnergy);
			if(newRobot != null) {
				robots.put(words[1], newRobot);
				facade.putRobot(board, x, y, newRobot);
			}
		} else if(words[0].equals("addbattery") && 4 <= words.length && words.length <= 6) {
			String name = words[1];
			if(batteries.containsKey(name)) {
				out.println("battery named " + name + " already exists");
				return;
			}
			long x, y;
			try {
				x = Long.parseLong(words[2]);
				y = Long.parseLong(words[3]);
			} catch(NumberFormatException ex) {
				out.println("position expected but found " + words[2] + " " + words[3]);
				return;
			}
			double initialEnergy = 1000;
			if(5 <= words.length ) {
				try {
					initialEnergy = Double.parseDouble(words[4]);
				} catch(NumberFormatException ex) {
					out.println("double expected but found " + words[4]);
					return;
				}
			}
			int weight = 1500;
			if(6 <= words.length) {
				try {
					weight = Integer.parseInt(words[5]);
				} catch(NumberFormatException ex) {
					out.println("integer expected but found " + words[5]);
					return;
				}
			}
			Battery newBattery = facade.createBattery(initialEnergy, weight);
			if(newBattery != null) {
				batteries.put(words[1], newBattery);
				facade.putBattery(board, x, y, newBattery);
			}
		} else if(words[0].equals("addwall") && words.length == 3) {
			int x, y;
			try {
				x = Integer.parseInt(words[1]);
				y = Integer.parseInt(words[2]);
			} catch(NumberFormatException ex) {
				out.println("position expected but found " + words[1] + " " + words[2]);
				return;
			}
			Wall wall = facade.createWall();
			if(wall != null) {
				facade.putWall(board, x, y, wall);
			}
		} else if(words[0].equals("move") && words.length == 2) {
			String name = words[1];
			if(! robots.containsKey(name)) {
				out.println("robot named " + name + " does not exist");
				return;
			}
			facade.move(robots.get(name));
		} else if(words[0].equals("turn") && words.length == 2) {
			String name = words[1];
			if(! robots.containsKey(name)) {
				out.println("robot named " + name + " does not exist");
				return;
			}
			facade.turn(robots.get(name));
		} else if(words[0].equals("pickup") && words.length == 3) {
			String rname = words[1];
			if(! robots.containsKey(rname)) {
				out.println("robot named " + rname + " does not exist");
				return;
			}
			String iname = words[2];
			if(! batteries.containsKey(iname)) {
				out.println("battery named " + iname + " does not exist");
				return;
			}
			facade.pickUp(robots.get(rname), batteries.get(iname));
		} else if(words[0].equals("use") && words.length == 3) {
			String rname = words[1];
			if(! robots.containsKey(rname)) {
				out.println("robot named " + rname + " does not exist");
				return;
			}
			String iname = words[2];
			if(! batteries.containsKey(iname)) {
				out.println("battery named " + iname + " does not exist");
				return;
			}
			facade.use(robots.get(rname), batteries.get(iname));
		} else if(words[0].equals("drop") && words.length == 3) {
			String rname = words[1];
			if(! robots.containsKey(rname)) {
				out.println("robot named " + rname + " does not exist");
				return;
			}
			String iname = words[2];
			if(! batteries.containsKey(iname)) {
				out.println("battery named " + iname + " does not exist");
				return;
			}
			facade.drop(robots.get(rname), batteries.get(iname));
		} 
		else if(words[0].equals("moveto") && words.length == 3) {
			String rname = words[1];
			if(! robots.containsKey(rname)) {
				out.println("robot named " + rname + " does not exist");
				return;
			}
			String rname2 = words[2];
			if(! robots.containsKey(rname2)) {
				out.println("robot named " + rname2 + " does not exist");
				return;
			}
			facade.moveNextTo(robots.get(rname), robots.get(rname2));
		} else if(words[0].equals("shoot") && words.length == 2) {
			String rname = words[1];
			if(! robots.containsKey(rname)) {
				out.println("robot named " + rname + " does not exist");
				return;
			}
			facade.shoot(robots.get(rname));
		} else if(words[0].equals("canreach") && words.length == 4) {
			String name = words[1];
			if(! robots.containsKey(name)) {
				out.println("robot named " + name + " does not exist");
				return;
			}
			int x, y;
			try {
				x = Integer.parseInt(words[2]);
				y = Integer.parseInt(words[3]);
			} catch(NumberFormatException ex) {
				out.println("position expected but found " + words[2] + " " + words[3]);
				return;
			}
			
			double required = facade.getMinimalCostToReach(robots.get(name), x, y);
			if(required == -1) {
				out.println("no (blocked by obstacles)");
			} else if(required == -2) {
				out.println("no (insufficient energy)");
			} else {
				out.println("yes (consuming " + required + " ws)");
			}
		} else if(words[0].equals("help") && words.length == 1)  {
			out.println("commands:");
			out.println("\taddbattery <bname> <long> <long> [<double>] [<int>]");
			out.println("\taddwall <long> <long>");
			out.println("\taddrobot <rname> <long> <long> [<double>]");
			out.println("\tmove <rname>");
			out.println("\tturn <rname>");
			out.println("\tshoot <rname>");
			out.println("\tpickup <rname> <bname>");
			out.println("\tuse <rname> <bname>");
			out.println("\tdrop <rname> <bname>");
			out.println("\tcanreach <rname> <long> <long>");
			out.println("\tmoveto <rname> <long> <long>");
			out.println("\texit");
		} else {
			out.println("unknown command");
		}
	}
}
