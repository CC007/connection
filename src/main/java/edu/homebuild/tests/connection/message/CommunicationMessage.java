/**
 *
 */
package edu.homebuild.tests.connection.message;

import edu.homebuild.tests.connection.messageobjects.ConnectionObject;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class CommunicationMessage extends ConnectionMessage implements Communication{

    protected final int messageDirection;

    public CommunicationMessage(int messageDirection, ConnectionObject message, int messageType) {
        super(message, messageType);
        this.messageDirection = messageDirection;
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
