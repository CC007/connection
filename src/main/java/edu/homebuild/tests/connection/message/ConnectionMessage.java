/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.homebuild.tests.connection.message;

import edu.homebuild.tests.connection.messageobjects.MessageObject;

/**
 *
 * @author Rik
 */
public class ConnectionMessage implements Message {

    protected final MessageObject message;
    protected final int messageType;

    public ConnectionMessage(MessageObject message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    @Override
    public Object getMessage() {
        return message;
    }

    @Override
    public int getMessageType() {
        return messageType;
    }

}
