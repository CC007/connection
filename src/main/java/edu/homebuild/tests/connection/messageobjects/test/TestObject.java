/**
 *
 */
package edu.homebuild.tests.connection.messageobjects.test;

import edu.homebuild.tests.connection.controller.Connection;
import edu.homebuild.tests.connection.message.test.TestMessage;
import edu.homebuild.tests.connection.messageobjects.CommunicationObject;
import java.net.InetSocketAddress;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class TestObject extends CommunicationObject {

    public static final int PERFORM_TEST = 1;
    public static final int TEST_OK = 10;
    public static final int TEST_FAILED = 11;
    private final int status;

    public TestObject(InetSocketAddress sender, InetSocketAddress receiver, int status) {
        super(sender, receiver);
        this.status = status;
    }

    @Override
    public void handleMessage(edu.homebuild.tests.connection.controller.Connection con, Object obj) {
        if (status == PERFORM_TEST) {
            con.sendMessage(new TestMessage(TestMessage.MESSAGE_REPLY, TestMessage.TEST, new TestObject(receiver, sender, TestObject.TEST_OK)));
            System.out.println("Request received, reply send.");
        }
    }

    @Override
    public void handleReply(edu.homebuild.tests.connection.controller.Connection con, Object obj) {
        if (status == TEST_OK) {
            System.out.println("Reply received, test succeeded!");
        }
    }
}
