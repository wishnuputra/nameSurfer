/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import acm.util.*;

public class NameSurfer extends GraphicsProgram implements NameSurferConstants {

	/** The text field where user type in the name to plot the graph*/
	private JTextField textField;
	
	/** The list of name that user has entered is collected inside this ArrayList */
	private ArrayList<NameSurferEntry> entryList = new ArrayList<NameSurferEntry>();
		
	/**
	 * Public Method: init()
	 * ---------------------- 
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
		addComponentListener(this);
		setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		drawBackground();
		
		// Interactors		
		JLabel name = new JLabel("Name");
		add(name, NORTH);
		
		textField = new JTextField(TEXT_FIELD_WIDTH);
		textField.addActionListener(this);
		add(textField, NORTH);
		
		JButton graph = new JButton("Graph");
		add(graph, NORTH);
		
		JButton clear = new JButton("Clear");
		add(clear, NORTH);
		
		addActionListeners();
	}
	

	/**
	 * Public Method: actionPerformed(ActionEvent e)
	 * ---------------------------------------------
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		Object obj = e.getSource();
		
		// If user click Graph button or press Enter key
		if(command.equals("Graph") || obj == textField) {
			String text = textField.getText();
			NameSurferDataBase nameDatabase = new NameSurferDataBase(NAMES_DATA_FILE);
			NameSurferEntry babyName = nameDatabase.findEntry(text);
			if (babyName != null) {
				entryList.add(babyName);
				redraw();
			}
		}
		
		// If user click Clear button
		if(command.equals("Clear")) {
			entryList.removeAll(entryList);
			redraw();
		}
	}
	
	/**
	 * Public Method: componentResized(ComponentEvent e)
	 * -------------------------------------------------
	 * This class is responsible for detecting when the the canvas
	 * is resized. This method is called on each resize!
	 */
	public void componentResized(ComponentEvent e) { 
		redraw();
	}
	
	/**
	 * Public Method: redraw()
	 * -----------------------
	 * A helper method that we *strongly* recommend. Redraw clears the
	 * entire display and repaints it. Consider calling it when you change
	 * anything about the display.
	 */
	private void redraw() {
		removeAll();
		drawBackground();
		drawGraph();
	}
	
	/**
	 * Private Method: drawBackground()
	 * --------------------------------
	 * Create the background including grids and decade labels.
	 * All components will be resized when the user resize the window.
	 */
	private void drawBackground() {
		double x = getWidth() / NDECADES;
		double y0 = 0;
		double y1 = getHeight();
		
		for (int i = 0; i < NDECADES; i++) {
			// Creates lines on each decade
			GLine line = new GLine(x*i, y0, x*i, y1);
			add(line);
			
			// Creates decade labels from START_DECADE
			String decade = String.valueOf(START_DECADE + 10*i);
			GLabel label = new GLabel(decade);
			add(label, x*i, y1 - DECADE_LABEL_MARGIN_SIZE);
		}
	}
	
	/**
	 * Private Method: drawGraph()
	 * ---------------------------
	 * Create a graph of name rank based on the names that the user
	 * has entered.
	 */
	private void drawGraph() {		
		Color[] colorPallete = 
			{Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA};
				
		// Plot the entire list of name that user has entered
		int colorIndex = 0;		
		for (NameSurferEntry babyName: entryList) {
			if (colorIndex > 3) {
				colorIndex = 0;
			}
			Color color = colorPallete[colorIndex];
			plotLine(babyName, color);
			colorIndex++;
		}
	}
	
	/**
	 * Private Method: plotLine(NameSurferEntry babyName, Color color)
	 * ---------------------------------------------------------------
	 * Creates line and label based on the name and rank from the
	 * NameSurferEntry.
	 * 
	 * @param babyName
	 * @param color
	 */
	private void plotLine(NameSurferEntry babyName, Color color) {
		// dy is the difference in y coordinate per one rank
		double dy = (getHeight() - 2*GRAPH_MARGIN_SIZE) / (double) MAX_RANK;			
		
		// x0 and y0 are the starting point for the line
		double x0 = 0;
		double y0 = GRAPH_MARGIN_SIZE;
		
		// plot the line for each decade until NDECADES
		for (int i = 0; i < NDECADES; i++) {
			String name = babyName.getName();
			int rank = babyName.getRank(i);
			
			// x1 and y1 is the end point of the line
			double x1 = (getWidth() / NDECADES) * i;
			double y1 = GRAPH_MARGIN_SIZE + dy * rank;
			
			if(rank == 0) {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
			}
			
			// Create the name and rank label
			GLabel nameLabel = new GLabel(name + " " + rank);
			nameLabel.setColor(color);
			add(nameLabel, x1, y1);
			
			// Create line based on the rank
			GLine line = new GLine(x0, y0, x1, y1);
			line.setColor(color);
			add(line);
			
			x0 = x1;
			y0 = y1;
		}
	}
}
