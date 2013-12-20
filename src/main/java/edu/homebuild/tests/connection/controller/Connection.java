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

    public void openConnection(InetSocketAddress address) throws SocketException;

    public void closeConnection();

    public void sendMessage(Message msg);

    public Message receiveMessage();

    public InetSocketAddress getAddress();

    public InetSocketAddress getSendAddress();
}
