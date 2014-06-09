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
     * Open a socket to send and receive data.
     *
     * @param address the <code>InetSocketAddress</code> from where you want to
     * connect.
     * @throws SocketException
     */
    public void openConnection(InetSocketAddress address) throws SocketException;

    /**
     * Close the socket that was opened by this connection.
     */
    public void closeConnection();

    /**
     * Set the <code>InetSocketAddress</code> to which you want connect
     *
     * @param sendAddress the <code>InetSocketAddress</code> to which you want
     * connect
     */
    public void setSendAddress(InetSocketAddress sendAddress);

    /**
     * Send a message to the socket you are connected to.
     *
     * @param msg The message of type <code>Message</code> that you want to send
     * over the connection.
     */
    public void sendMessage(Message msg);

    /**
     * Receive a message from the socket you are connected to.
     *
     * @return The message of type <code>Message</code> that was received over
     * this connection
     */
    public Message receiveMessage();

    /**
     * Get the <code>InetSocketAddress</code> that was set from where you want
     * to connect.
     *
     * @return the <code>InetSocketAddress</code> from where you want to connect
     * or <code>null</code> if it was not set.
     */
    public InetSocketAddress getAddress();

    /**
     * Get the <code>InetSocketAddress</code> that was set to which you want to
     * connect.
     *
     * @return the <code>InetSocketAddress</code> to which you want to connect
     * or <code>null</code> if it was not set.
     */
    public InetSocketAddress getSendAddress();
}
