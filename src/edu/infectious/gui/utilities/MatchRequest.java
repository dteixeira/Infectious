package edu.infectious.gui.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MatchRequest {

	/*
	 * Constants
	 */
	private static final String	SERVER_NAME	= "http://gnomo.fe.up.pt/~ei09086/ppro/infectious.php";
	private static final int	SERVER_PORT	= 6882;

	/*
	 * Instance fields
	 */
	private boolean				master		= false;
	private String				opponentIp	= "";
	private Socket				socket		= null;

	/*
	 * Constructor
	 */
	public MatchRequest() {}

	/*
	 * Instance methods
	 */
	public boolean connectToOpponent() {
		ServerSocket server = null;
		try {
			// Act as server
			if (master) {
				server = new ServerSocket(SERVER_PORT);
				server.setSoTimeout(15000);
				socket = server.accept();
				server.close();
			}
			// Act as client
			else {
				socket = new Socket(opponentIp, SERVER_PORT);
			}
			return true;
		} catch (Exception e) {
			if (server != null)
				try {
					server.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
			return false;
		}
	}

	public String getOpponentIp() {
		return opponentIp;
	}

	public Socket getSocket() {
		return socket;
	}

	public boolean isMaster() {
		return master;
	}

	public boolean makeRequest() {
		try {
			// Make request and read response
			String line, response = "";
			InputStream input = new URL(SERVER_NAME).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			while ((line = reader.readLine()) != null) {
				response += line;
			}

			// Close the connection and parse the response
			input.close();
			return parseXmlResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public void setOpponentIp(String opponentIp) {
		this.opponentIp = opponentIp;
	}

	private boolean parseXmlResponse(String xml) {
		try {
			// Create DOM object
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);

			Element result = (Element) (doc.getElementsByTagName("result").item(0));
			if (result.getAttribute("value").equals(""))
				return false;
			else {
				if (!result.getAttribute("master").equals(""))
					master = true;
				opponentIp = result.getTextContent();
				return true;
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
	}

}
