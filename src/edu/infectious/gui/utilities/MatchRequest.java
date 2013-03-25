package edu.infectious.gui.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MatchRequest {
	
	private static final String SERVER_NAME = "http://gnomo.fe.up.pt/~ei09086/ppro/infectious.php";
	
	private final String clientIp;
	private final int clientPort;
	private String opponentIp = "";
	private int opponentPort = -1;
	private boolean master = false;
	
	public MatchRequest(String clientIp, int clientPort) {
		this.clientIp = clientIp;
		this.clientPort = clientPort;
	}
	
	public boolean makeRequest() {
		DataOutputStream wr;
		try {
			// Builds parameter string
			String params = "client_ip=" + clientIp + ":" + clientPort;
			
			// Open and configure connection to server
			URL url = new URL(SERVER_NAME); 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setInstanceFollowRedirects(false); 
			connection.setRequestMethod("POST"); 
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
			connection.setUseCaches (false);
			
			// Send request
			wr = new DataOutputStream(connection.getOutputStream ());
			wr.writeBytes(params);
			wr.flush();
			wr.close();
			
			// Read response
			String line, response = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				response += line;
			}
			
			// Close the connection and parse the response
			connection.disconnect();
			return parseXmlResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean parseXmlResponse(String xml) {
        try {
        	// Create DOM object
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
			Document doc = builder.parse(is);
			
			Element result = (Element)(doc.getElementsByTagName("result").item(0));
			if(result.getAttribute("value").equals(""))
				return false;
			else {
				if(!result.getAttribute("master").equals(""))
					master = true;
				String[] ip = result.getTextContent().split(":");
				opponentIp = ip[0];
				opponentPort = Integer.parseInt(ip[1]);
				return true;
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getOpponentIp() {
		return opponentIp;
	}

	public void setOpponentIp(String opponentIp) {
		this.opponentIp = opponentIp;
	}

	public int getOpponentPort() {
		return opponentPort;
	}

	public void setOpponentPort(int opponentPort) {
		this.opponentPort = opponentPort;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public String getClientIp() {
		return clientIp;
	}

	public int getClientPort() {
		return clientPort;
	}

}
