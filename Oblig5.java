import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
The main class of the SuDoku solver. Is used for <code>main()</code> and as the controller.
@see ProgramWindow
@author H&aring;kon Antonsen
@version 1.0
*/
public class Oblig5
{
	private Board model;				// The board to solve
	private ProgramWindow window;		// The GUI
	private SudokuCollection solutions;	// Collection to store the solutions

	/**
	Opens an empty window
	*/
	public Oblig5()
	{
		window = new ProgramWindow(this);

		SwingUtilities.invokeLater(window);
	} // End constructor

	/**
	Opens a window with the file <code>ff</code> loaded
	@param ff The file name of the board to solve
	*/
	public Oblig5(String ff)
	{
		window = new ProgramWindow(this);
		SwingUtilities.invokeLater(window);

		File f = new File(ff);
		notifyFile(f);
		window.enableSolveButton();
		window.displayError("Loaded " + ff);

	} // End constructor

	/**
	Opens a window with the file <code>ff/code> loaded. Solves it and stores the solution to the file <code>tf</code>
	@param ff The file to solve
	@param tf The file to store solution in
	*/
	public Oblig5(String ff, String tf)
	{
		window = new ProgramWindow(this);
		SwingUtilities.invokeLater(window);

		File f = new File(ff);
		notifyFile(f);
		solveBoard();
		window.displayError("The solutions are saved to " + tf);
		try
		{
			solutions.saveToFile(tf);
		}
		catch(FileNotFoundException e)
		{
			window.displayError("Could not locate solution file");
		}
		catch(IOException e)
		{
			window.displayError("There was an error writing solutions to file");
		}
		catch(Exception e)
		{
			window.displayError("There was an error while saving solutions");
		} // ENDTRY
	} // End constructor

	/**
	The main method. The program can be invoked with 0, 1 and 2 arguments.
	0 Arguments - The program opens and is ready for a file
	1 Argument - The argument given is the file to solve
	2 Arguments - The first argument is the file to solve, the second argument is the file to store it in.
	*/
	public static void main(String[] args)
	{
		if(args.length == 1)
			new Oblig5(args[0]);
		else if(args.length >= 2)
			new Oblig5(args[0], args[1]);
		else
			new Oblig5();

	} // End main

	/**
	Used by the GUI to tell the controller that there is a file to get.
	*/
	public void notifyFile()
	{
		notifyFile(window.getFile());
	} // End notifyFile

	/**
	Takes the file <code>f</code> and creates a board from it, then gives the board to the GUI
	@param f The file to create board from
	*/
	public void notifyFile(File f)
	{
		try{
			model = new Board();
			solutions = new SudokuCollection();
			model.fillBoard(f, solutions);
			window.boardFilled(model.getBoard(), model.getWidth(), model.getHeight());
		}
		catch(FileNotFoundException e)
		{
			window.displayError("Could not locate the file");
		}
		catch(IOException e)
		{
			window.displayError("There was an error reading from the file");
		}
		catch(WrongSizeException e)
		{
			window.displayError("There was an error in the size specifications in the file");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			window.displayError("There was en error in the file");
		}
		catch(Exception e)
		{
			window.displayError("There was an unknown error\n" + e.getClass().getName());
		} // ENDTRY
	} // End notifyFile

	/**
	Used by the GUI to solve the board and get the first solution. If no solutions exists, an error message will be displayed
	*/
	public void solveBoard()
	{
		// Tries to solve the board
		model.solve();

		// Check whether there are any solutions
		if(solutions.getSolutionCount() > 0)
		{
			window.giveSolution(solutions.getNextSolution(), model.getWidth(), model.getHeight(), solutions.getLastGivenSolution(), solutions.getStoredSolutionCount());
			window.displayError("Found " + solutions.getSolutionCount() + " solutions for this board\n" +
								 "Displaying " + solutions.getStoredSolutionCount() + " of them");
		}
		else
			window.displayError("There are no solutions for that board");
		// ENDIF
	} // End solveBoard

	/**
	Gives the next solution to the GUI
	*/
	public void getNext()
	{
		window.giveSolution(solutions.getNextSolution(), model.getWidth(), model.getHeight(), solutions.getLastGivenSolution(), solutions.getStoredSolutionCount());
	} // End getNext

	/**
	Gives the previous solution to the GUI
	*/
	public void getPrevious()
	{
		solutions.setBackGetNext();
		solutions.setBackGetNext();
		getNext();
	} // End getPrevious
	
} // End Oblig5