/**
 *
 */
package edu.homebuild.tests.connection.message.test;

import edu.homebuild.tests.connection.message.DefaultMessage;
import edu.homebuild.tests.connection.messageobjects.MessageObject;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class TestMessage extends DefaultMessage {

    public static final int TEST = 1;

    public TestMessage(int messageDirection, int messageType, MessageObject message) {
        super(messageDirection, messageType, message);
    }
}
