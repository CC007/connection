/**
 *
 */
package edu.homebuild.tests.connection.message.fipa;

import edu.homebuild.tests.connection.message.CommunicationMessage;
import edu.homebuild.tests.connection.messageobjects.ConnectionObject;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class FipaMessage extends CommunicationMessage {

    public static final int FIPA_REQUEST = 1;
    public static final int FIPA_REPLY = 2;
    public static final int FIPA_NEGOTIATION = 3;
    public static final int FIPA_ACTION = 4;
    public static final int FIPA_ERROR = 5;

    public FipaMessage(int messageDirection, int messageType, ConnectionObject message) {
        super(messageDirection, message, messageType);
    }
}
