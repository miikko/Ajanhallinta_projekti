package controllers;

import java.util.HashMap;

import application.Container;
import application.Screen;

/**
 * Class is responsible of storing containers and changing which containers are
 * displayed in the given Screen parent.<br>
 * Main purpose of this class is to be passed to nested containers instead of
 * the Screen-parent. Without this class nested Containers would need a
 * reference to the Screen parent in order to display themselves.
 * 
 * @author miikk
 *
 */
public class ContainerController {
	private final Screen parent;
	private HashMap<String, Container> cMapping;

	public ContainerController(Screen parent) {
		this.parent = parent;
		cMapping = new HashMap<String, Container>();
	}

	/**
	 * Adds mapping of the given container with the given key. If a mapping with the
	 * given already exists, nothing happens.
	 * 
	 * @param name      String key that can be used to call the container from this
	 *                  Class's other methods
	 * @param container The container to be mapped.
	 * @return true if a new mapping was created, false if a mapping with the given
	 *         key already existed.
	 */
	public boolean addContainer(String name, Container container) {
		if (cMapping.containsKey(name)) {
			return false;
		}
		cMapping.put(name, container);
		return true;
	}

	/**
	 * Removes and returns the Container belonging to the given String-key.
	 * 
	 * @param name String key used to map the Container
	 * @return the Container belonging to the given String key, null if no mapping
	 *         existed.
	 */
	public Container removeContainer(String name) {
		return cMapping.remove(name);
	}

	/**
	 * Displays the mapped Container belonging to the given String-key in the Screen
	 * parent. The parent is specified during object instantiation. <br>
	 * The caller can specify if the displayed Container should be refreshed before
	 * displaying with the boolean-flag.
	 * 
	 * @param name         String key used to map the Container
	 * @param refreshFirst Boolean-flag used to determine whether the Container
	 *                     should be refreshed before displaying.
	 * @returns false if the Screen parent is null or if no mapping exists for the
	 *          given key. True otherwise.
	 */
	public boolean display(String name, boolean refreshFirst) {
		if (parent == null || !cMapping.containsKey(name)) {
			return false;
		}
		Container container = cMapping.get(name);
		if (refreshFirst) {
			container.refresh();
		}
		parent.display(container);
		return true;
	}

}
