package application;

/**
 * Interface meant for Top-Level GUI-elements. Screens should contain
 * Containers, much like Containers contain Content.
 * 
 * @author miikk
 *
 */
public interface Screen {

	/**
	 * Displays the given container in this screen.
	 * 
	 * @param container the Container to be displayed
	 */
	void display(Container container);
}
