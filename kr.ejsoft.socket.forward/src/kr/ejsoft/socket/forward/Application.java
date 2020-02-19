package kr.ejsoft.socket.forward;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Application {
	public static void main(String[] args) {
		String propertiesFile = "forward.properties";
		if(args != null && args.length > 0) {
			propertiesFile = args[0];
		}
		
		Properties prop = new Properties();
		FileInputStream stream = null;
		try {
			File file = new File(propertiesFile);
			System.out.println("Properties File Path : " + file.getAbsolutePath());
			stream = new FileInputStream(file);
			prop.load(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(stream != null) try { stream.close(); } catch(Exception e) { }
		}
		
		String keynames = prop.getProperty("forward.keys", "");
		String[] keys = keynames.split(",");
		for(String key : keys) {
			String host = prop.getProperty("forward." + key + ".host", "").trim();
			String listen1 = prop.getProperty("forward." + key + ".listen", "0");
			String port1 = prop.getProperty("forward." + key + ".port", "0");
			int listen = -1;
			int port = -1;
			try { listen = Integer.parseInt(listen1); } catch(Exception e) { };
			try { port = Integer.parseInt(port1); } catch(Exception e) { };
			if(port <= 0) port = listen;
			
			if(host != null && !"".equals(host.trim()) && listen > 0) {
				new SocketServer(listen, host, port).start();
			}
		}
	}
}
