/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class NameSurferDataBase implements NameSurferConstants {
	
	/** The nameMap contains all the lines of the input file*/
	private HashMap<String, String> nameMap = new HashMap<String, String>();
	
	
	/**
	 * Constructor: NameSurferDataBase(filename)
	 * -----------------------------------------
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		try {
			Scanner input = new Scanner(new File(filename));
			createNameMap(input);
		} catch(IOException e) {
			System.out.print("Oops the file didn't open!");
		}
	}

	/**
	 * Public Method: findEntry(name)
	 * ------------------------------
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		String line = nameMap.get(name.toUpperCase());
		if (line != null ) {
			NameSurferEntry nameEntry = new NameSurferEntry(line);
			return nameEntry;
		}
		return null;
	}
	
	/**
	 * Private Method: createNameMap(Scanner input)
	 * --------------------------------------------
	 * Creates a HashMap of name and ranks from the input file
	 * given by the Constructor. The key of the HasMap contains 
	 * the name and the value contains name and ranks of each
	 * decade.  
	 */
	private void createNameMap(Scanner input) {
		while(input.hasNextLine()) {
			String line = input.nextLine();
			Scanner tokens = new Scanner(line);
			String name = tokens.next();
			nameMap.put(name.toUpperCase(), line);
			tokens.close();
		}
		input.close();
	}
}

