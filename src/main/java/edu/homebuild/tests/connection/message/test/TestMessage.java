/**
 *
 */
package edu.homebuild.tests.connection.message.test;

import edu.homebuild.tests.connection.message.CommunicationMessage;
import edu.homebuild.tests.connection.messageobjects.ConnectionObject;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class TestMessage extends CommunicationMessage {

    public static final int TEST = 1;

    public TestMessage(int messageDirection, int messageType, ConnectionObject message) {
        super(messageDirection, messageType, message);
    }
}
