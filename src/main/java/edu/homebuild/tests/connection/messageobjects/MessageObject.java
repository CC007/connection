/**
 * 
 */

package edu.homebuild.tests.connection.messageobjects;

import edu.homebuild.tests.connection.controller.Connection;
import java.io.Serializable;
import java.net.InetSocketAddress;

/**
 * @author Rik Schaaf, University of Groningen
 */

public abstract class MessageObject implements Serializable{
    
    protected InetSocketAddress sender;
    protected InetSocketAddress receiver;

    public MessageObject(InetSocketAddress sender, InetSocketAddress receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    
    
    public abstract void handleRequest(Connection con);
    public abstract void handleReply(Connection con);
}
