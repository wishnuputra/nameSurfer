/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
	
	/** The name that appears on the left the each line */
	private String name;
	
	/** The ArrayList that contains all the ranks of each line*/
	private ArrayList<Integer> rankList = new ArrayList<Integer>();

	/**
	 * Constructor: NameSurferEntry(line)
	 * ----------------------------------
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		Scanner tokens = new Scanner(line);
		name = tokens.next();
		while(tokens.hasNextInt()) {
			rankList.add(tokens.nextInt());
		}
		tokens.close();
	}

	/**
	 * Public Method: getName()
	 * ------------------------
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Public Method: getRank(decade)
	 * ------------------------------
	 * Returns the rank associated with an entry for a particular
	 * decade.  The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		if (decade >= 0 && decade < NDECADES) {
			return rankList.get(decade);
		}
		return -1;
	}

	/**
	 * Public Method: toString()
	 * -------------------------
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		return name + rankList;
	}
}

