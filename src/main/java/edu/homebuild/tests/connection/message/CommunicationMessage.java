/**
 *
 */
package edu.homebuild.tests.connection.message;

import edu.homebuild.tests.connection.messageobjects.ConnectionObject;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class CommunicationMessage extends ConnectionMessage implements MessageDirection{

    protected final int messageDirection;

    public CommunicationMessage(int messageDirection, int messageType, ConnectionObject message) {
        super(messageType, message);
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
