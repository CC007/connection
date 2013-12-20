/**
 * 
 */

package edu.homebuild.tests.connection.messageobjects;

import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 * @author Rik Schaaf, University of Groningen
 */

public abstract class ConnectionObject implements Serializable{
    
    protected InetSocketAddress sender;
    protected InetSocketAddress receiver;
    
    public ConnectionObject(InetSocketAddress sender, InetSocketAddress receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    
    public abstract void handleMessage(edu.homebuild.tests.connection.controller.Connection con, Object obj);
}
