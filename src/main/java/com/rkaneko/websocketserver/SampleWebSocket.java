package com.rkaneko.websocketserver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.websocket.WebSocket;

/**
 * 
 * @author rkaneko
 *
 */
public class SampleWebSocket implements WebSocket.OnTextMessage {

	/** logger */
	static final Logger LOG = Logger.getLogger( SampleWebSocket.class.getName() );
	
	/** ServletContext */
	private ServletContext context;

	/** access log */
    final List<String> accesslog;
    /** connections */
    final List<Connection> connections;

    /** connection temporary valuable */
    Connection connection;
    
    /**
     * Constructor
     * @param context
     */
    @SuppressWarnings("unchecked")
    public SampleWebSocket(ServletContext context) {
    	this.context = context;
    	accesslog = (List<String>) context.getAttribute("accesslog");
        connections = (List<Connection>) context.getAttribute("connections");
    }
    
    @Override
	public void onClose(int closeCode, String message) {
		connections.remove(connection);
        connection.close();
        LOG.log(Level.INFO, "close socket: {}" + connection);
	}

	@Override
	public void onOpen(Connection connection) {
		LOG.log(Level.INFO, "open socket: {}" + connection);
        this.connection = connection;
        connections.add(connection);
        
        try {
			connection.sendMessage(String.format("{\"type\":\"%s\",\"id\":\"%d\"}", "assigned_id", connection.hashCode()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onMessage(String message) {
		LOG.log(Level.INFO, "onMessage: " + message);
		
		Object obj = JSON.parse(message);
		if (obj instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) obj;
			
			String type = (String)map.get("type");
			
			if ("received_offer".equalsIgnoreCase(type) 
					|| "received_candidate".equalsIgnoreCase(type) 
					|| "received_answer".equalsIgnoreCase(type)){
		    	for( Connection c : connections ) {
		    		try {
		    			if (this.connection.hashCode() != c.hashCode()){
		    				c.sendMessage(message);	
		    			}	    			    			
		    		} catch( IOException e ) {
		    			LOG.log(Level.SEVERE, e.getMessage());
		    		}
		    	}				
			}			
			
		}
		else{
			LOG.log(Level.WARNING, "未知数据" + message);
		}
		
		//sendMessage(message);
	}
	
//	private void sendMessage(String message) {
//    	LOG.log(Level.INFO, "sent \n" + new SimpleDateFormat("yy/MM/dd HH:mm:ss").format( new Date() ).toString() + message);
//    	for( Connection c : connections ) {
//    		try {
//    			c.sendMessage(message);    			
//    		} catch( IOException e ) {
//    			LOG.log(Level.SEVERE, e.getMessage());
//    		}
//    	}
//    }
}
