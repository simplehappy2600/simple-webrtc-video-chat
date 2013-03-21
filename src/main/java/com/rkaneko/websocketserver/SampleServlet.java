package com.rkaneko.websocketserver;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

@WebServlet(
	    name="SampleServlet"
	    , urlPatterns={"/SampleServlet"}
)
public class SampleServlet extends WebSocketServlet {

	/** serial id */
	private static final long serialVersionUID = 1L;
	
	/** logger */
	static final Logger LOG = Logger.getLogger( SampleServlet.class.getName() );
	
	/**
	 * Constructor
	 * @see WebSocketServlet#WebSocketServlet()
	 */
	public SampleServlet() {
		super();
		LOG.log(Level.INFO, "start SampleServlet");
	}
	
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		LOG.log(Level.INFO, "protocol is : " + protocol);
		return new SampleWebSocket(this.getServletContext());
	}

}
