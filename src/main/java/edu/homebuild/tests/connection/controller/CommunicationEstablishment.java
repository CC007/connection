/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.homebuild.tests.connection.controller;

import edu.homebuild.tests.connection.message.CommunicationMessage;
import edu.homebuild.tests.connection.message.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

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
    protected void addMessageToBuffer() throws IOException, ClassNotFoundException {
        Object obj = in.readObject();
        if (obj instanceof Message && obj instanceof edu.homebuild.tests.connection.message.Communication) {
            CommunicationMessage msg = (CommunicationMessage) obj;
            if (msg.isRequest()) {
                messageBuffer.add(msg);
            } else if (msg.isReply()) {
                replyBuffer.add(msg);
            }
        }
    }

}
