/*
 * 
 */
package edu.homebuild.tests.connection.controller;

import edu.homebuild.tests.connection.message.Message;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @author Rik Schaaf, University of Groningen
 */
public interface Connection {
     /**
      * Open a socket to send data from.
      * 
      * @param address the <code>InetSocketAddress</code> from where you want to connect.
      * @throws SocketException 
      */
    public void openConnection(InetSocketAddress address) throws SocketException;

    /**
     * Close the socket that was opened by this connection.
     */
    public void closeConnection();

    /**
     * Set the <code>InetSocketAddress</code> to which you want to send data
     * 
     * @param sendAddress the <code>InetSocketAddress</code> to which you want to send data.
     */
    public void setSendAddress(InetSocketAddress sendAddress);

    public void sendMessage(Message msg);

    public Message receiveMessage();

    public InetSocketAddress getAddress();

    public InetSocketAddress getSendAddress();
}
