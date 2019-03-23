package database;

import javax.swing.JOptionPane;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * This Singleton class contains methods for opening and closing a tunnel to the database computer.<br>
 * Tunneling is done with the help of JSch library that contains methods for SSH-connections.
 * 
 * @author miikko
 * @since 23.3.2019
 */
public class ConnectionHandler {
	private JSch jsch;
	private Session session;

	private ConnectionHandler() {
		jsch = new JSch();
	}

	private static class SingletonHolder {
		private static final ConnectionHandler INSTANCE = new ConnectionHandler();
	}

	public static ConnectionHandler getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	/**
	 * Opens a tunnel to the database computer using Metropolias "edunix" computer as the middleman.<br>
	 * User is asked for their Metropolia credentials for authentication before creating the tunnel.   
	 */
	public void openTunnel() {
		if (session == null || !session.isConnected()) {
			try {
				String username = JOptionPane.showInputDialog("Enter Metropolia username");
				String password = JOptionPane.showInputDialog("Enter Metropolia password");
				String host = "edunix.metropolia.fi";
				session = jsch.getSession(username, host);
				session.setPassword(password);
				int lport = 4444;
				String rhost = "10.114.32.17";
				int rport = 3306;
				session.setConfig("StrictHostKeyChecking", "no");
				session.connect();
				int assinged_port = session.setPortForwardingL(lport, rhost, rport);
				System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
				// TODO: Close tunnel when it is no longer needed.
				// session.disconnect();
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Closes the opened tunnel if it exists
	 */
	public void closeTunnel() {
		if (session != null && session.isConnected()) {
			session.disconnect();
		}
	}
}
