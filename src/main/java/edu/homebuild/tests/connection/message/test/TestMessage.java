/**
 *
 */
package edu.homebuild.tests.connection.message.test;

import edu.homebuild.tests.connection.message.CommunicationMessage;
import edu.homebuild.tests.connection.messageobjects.MessageObject;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class TestMessage extends CommunicationMessage {

    public static final int TEST = 1;

    public TestMessage(int messageDirection, int messageType, MessageObject message) {
        super(messageDirection, message, messageType);
    }
}
