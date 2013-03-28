package edu.infectious.gui.utilities;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
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
	private String opponentIp = "";
	private boolean master = false;
	
	public MatchRequest() {
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
				opponentIp = result.getTextContent();
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

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

}
