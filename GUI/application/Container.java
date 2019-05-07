package application;

import javafx.scene.Node;

/**
 * @author miikk
 *
 */
public interface Container {

	/**
	 * Resets all instance variables and selections
	 */
	void refresh();

	/**
	 * Gets this container's content
	 * 
	 * @return this container's content
	 */
	Node getContent();
}
