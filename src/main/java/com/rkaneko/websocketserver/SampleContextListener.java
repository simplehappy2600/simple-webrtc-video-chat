package com.rkaneko.websocketserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.eclipse.jetty.websocket.WebSocket;

/**
 * 
 * @author rkaneko
 *
 */
@WebListener
public class SampleContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
        @SuppressWarnings("unchecked")
        List<WebSocket.Connection> connections = (List<WebSocket.Connection>) context.getAttribute("connections");
        for (WebSocket.Connection connection : connections) {
            connection.close();
        }
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();

        List<String> accesslog = new ArrayList<String>();
        List<WebSocket.Connection> connections = new LinkedList<WebSocket.Connection>();
        context.setAttribute("accesslog", accesslog);
        context.setAttribute("connections", connections);
	}

}
