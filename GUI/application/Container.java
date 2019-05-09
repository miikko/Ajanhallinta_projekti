package application;

import javafx.scene.Node;

/**
 * The purpose of this interface is to encapsulate the content-elements and
 * provide a clear way to refresh content in the GUI. <br>
 * When an update is needed, Containers can be recycled by calling the refresh()-method. This is better performance-wise when compared to always creating a new Container.
 * 
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
