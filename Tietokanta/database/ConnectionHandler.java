package database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

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
	private final int LPORT = 4444;
	private final String AUTH_PATH = "./dev_auth/metropolia_login.txt";
	private String mUsername;
	private String mPassword;

	private ConnectionHandler() {
		jsch = new JSch();
		readMetropoliaCredentials();
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
		if (!portAvailable(LPORT)) {
			killProcessesUsingPort(LPORT);
		}
		if (session == null || !session.isConnected()) {
			try {
				if (!readMetropoliaCredentials()) {
					mUsername = JOptionPane.showInputDialog("Enter Metropolia username");
					mPassword = JOptionPane.showInputDialog("Enter Metropolia password");
					writeMetropoliaCredentials(mUsername, mPassword);
				}
				String host = "edunix.metropolia.fi";
				session = jsch.getSession(mUsername, host);
				session.setPassword(mPassword);
				String rhost = "10.114.32.17";
				int rport = 3306;
				session.setConfig("StrictHostKeyChecking", "no");
				session.setDaemonThread(true);
				session.connect();
				int assinged_port = session.setPortForwardingL(LPORT, rhost, rport);
				System.out.println("localhost:" + assinged_port + " -> " + rhost + ":" + rport);
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
	/**
	 * Tries to create a socket on the given port to test if it can be created.
	 * @param port
	 * @return false if the port is listening, true otherwise. 
	 */
	private boolean portAvailable(int port) {
		try (Socket socket = new Socket("localhost", port)) {
			return false;
		} catch (IOException e) {
			return true;
		}
	}
	
	/**
	 * Finds processes that are using the port and kills them.
	 * @param port
	 * @return true if the processes were killed successfully, false otherwise
	 */
	private boolean killProcessesUsingPort(int port) {
		Runtime rt = Runtime.getRuntime();
		String findCommand = "netstat -ano | findstr " + port;
		Set<String> pids = new HashSet<>();
		Process process = null;
		BufferedReader stdInput = null;
		try {
			process = rt.exec(findCommand);
			stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String input;
			while ((input = stdInput.readLine()) != null) {
				String[] inputSplit = input.split(" ");
				pids.add(inputSplit[inputSplit.length - 1]);
			}
			for (String pid : pids) {
				process = rt.exec("taskkill /PID " + pid + " /F");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (process != null) {
				process.destroy();
			}
			if (stdInput != null) {
				try {
					stdInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	/**Reads Metropolia login credentials from a file.<br>
	 * The credentials are required for creating a tunnel to database.
	 * @return true if the credentials were read successfully, false if the file didn't exist or if the credentials were not in the file.
	 */
	private boolean readMetropoliaCredentials() {
		try (BufferedReader br = new BufferedReader(new FileReader(AUTH_PATH))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] lineSplit = line.split(":");
				if (lineSplit[0].equals("Username")) {
					mUsername = lineSplit[1];
				} else if (lineSplit[0].equals("Password")) {
					mPassword = lineSplit[1];
				}
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if (mUsername == null || mPassword == null) {
			return false;
		}
		return true;
	}
	
	/**Creates a "metropolia_login.txt" file and writes the given credentials to the file so that they can be read on later compilations.<br>
	 * Also creates a "dev_auth" directory on the root-directory for the file if the folder does not exist.
	 * @param mUsername
	 * @param mPassword
	 * @return true if the directory and file were created and the credentials were written successfully, false if the directory already existed or if the credentials were not written.
	 */
	private boolean writeMetropoliaCredentials(String mUsername, String mPassword) {
		int fileIndex = 0;
		for (int i = 0; i < AUTH_PATH.length(); i++) {
			if (AUTH_PATH.charAt(i) == '/') {
				fileIndex = i;
			}
		}
		String folderPath = AUTH_PATH.substring(0, fileIndex);
		if (!new File(folderPath).mkdirs()) {
			System.out.println("flag");
			return false;
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(AUTH_PATH))) {
			bw.write("Username:" + mUsername + System.lineSeparator() + "Password:" + mPassword);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
