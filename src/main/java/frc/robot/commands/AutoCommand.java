package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.*;

public class AutoCommand extends CommandBase {
	//Is red alliance or blue alliance
	private final boolean isRedAlliance;
	//Position on the field 'L', 'M', or 'R'
	private final char position;
	/*The positions on the field that the cagro starts at
	 *<String position, char colorOccupying>
	 *In position: The first character is the alliance "R" or "B", the second is the side of the field the cargo is on
	 *"L", "M", or "R", and the third is whether it is the right, left, or middle cargo on that side "L", "M", or "R"
	 *colorOccupying holds a single character which what alliance the cargo belongs to or if it is empty 'R', 'B', or 'E'
	 */
	private final Hashtable<String, ArrayList<E>> cargoPositions=new Hashtable<>() {{
		put("RLL", new ArrayList<E>(){{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RLR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RML", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RMR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RRL", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RRR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RBL", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RBM", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("RBR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BLL", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BLR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BML", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BMR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BRL", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BRR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BBL", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BBM", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
		put("BBR", new ArrayList<E>() {{
			add('E');
			add(0.0);
			add(0.0);
		}});
	}};

	//Constructor
	public AutoCommand() {

	}


}
