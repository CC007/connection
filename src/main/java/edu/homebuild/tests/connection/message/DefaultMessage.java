/**
 * 
 */

package edu.homebuild.tests.connection.message;

import edu.homebuild.tests.connection.messageobjects.MessageObject;


/**
 * @author Rik Schaaf, University of Groningen
 */
public class DefaultMessage implements Message{

    private int messageDirection;
    private int messageType;
    private MessageObject message;

    public DefaultMessage(int messageDirection, int messageType, MessageObject message) {
        this.messageDirection = messageDirection;
        this.messageType = messageType;
        this.message = message;
    }
    

    @Override
    public int getMessageType() {
        return messageType;
    }

    @Override
    public Object getMessage() {
        return message;
    }

    @Override
    public boolean isRequest() {
        return messageDirection == MESSAGE_REQUEST;
    }

    @Override
    public boolean isReply() {
        return messageDirection == MESSAGE_REPLY;
    }

}
