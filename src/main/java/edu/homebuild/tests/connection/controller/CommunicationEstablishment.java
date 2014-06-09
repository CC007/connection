/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.homebuild.tests.connection.controller;

import edu.homebuild.tests.connection.message.CommunicationMessage;
import edu.homebuild.tests.connection.message.Message;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rik
 */
public class CommunicationEstablishment extends ConnectionEstablishment implements Communication {

    protected ArrayList<CommunicationMessage> replyBuffer;

    public CommunicationEstablishment() {
        this.replyBuffer = new ArrayList<>();
    }

    public CommunicationEstablishment(int timeout, InetSocketAddress address) throws SocketException {
        super(timeout, address);
        this.replyBuffer = new ArrayList<>();
    }

    @Override
    public CommunicationMessage receiveReply() {
        if (!replyBuffer.isEmpty()) {
            return replyBuffer.remove(0);
        }
        return null;
    }

    @Override
    protected void addMessageToBuffer() throws StreamCorruptedException, ClassNotFoundException, IllegalArgumentException {
        try {
            Object obj = in.readObject();
            if (obj instanceof Message && obj instanceof edu.homebuild.tests.connection.message.Communication) {
                CommunicationMessage msg = (CommunicationMessage) obj;
                if (msg.isRequest()) {
                    messageBuffer.add(msg);
                } else if (msg.isReply()) {
                    replyBuffer.add(msg);
                }
            } else {
                throw new IllegalArgumentException("Class has to be of type CommunicationMessage!");
            }
        } catch (IOException ex) {
            if (ex instanceof StreamCorruptedException) {
                throw (StreamCorruptedException) ex;
            } else {
                Logger.getLogger(ConnectionEstablishment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
