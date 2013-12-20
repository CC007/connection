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

    public TestObject(int status, InetSocketAddress sender, InetSocketAddress receiver) {
        super(sender, receiver);
        this.status = status;
    }

    @Override
    public void handleMessage(Connection con) {
        if (status == PERFORM_TEST) {
            con.sendMessage(new TestMessage(TestMessage.MESSAGE_REPLY, TestMessage.TEST, new TestObject(TestObject.TEST_OK, receiver, sender)));
            System.out.println("Request received, reply send.");
        }
    }

    @Override
    public void handleReply(Connection con) {
        if (status == TEST_OK) {
            System.out.println("Reply received, test succeeded!");
        }
    }
}
