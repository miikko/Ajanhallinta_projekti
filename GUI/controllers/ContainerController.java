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
	private Screen parent;
	private HashMap<String, Container> cMapping;

	public ContainerController(Screen parent) {
		this.parent = parent;
		cMapping = new HashMap<String, Container>();
	}

	public boolean addContainer(String name, Container container) {
		if (cMapping.containsKey(name)) {
			return false;
		}
		cMapping.put(name, container);
		return true;
	}

	public Container removeContainer(String name) {
		return cMapping.remove(name);
	}

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
