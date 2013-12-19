package edu.homebuild.tests.connection;

import edu.homebuild.tests.connection.controller.ConnectionEstablishment;
import edu.homebuild.tests.connection.message.Message;
import edu.homebuild.tests.connection.message.test.TestMessage;
import edu.homebuild.tests.connection.messageobjects.test.TestObject;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        try {
            InetSocketAddress a1 = new InetSocketAddress(InetAddress.getLocalHost(), 0);
            InetSocketAddress a2 = new InetSocketAddress(InetAddress.getLocalHost(), 0);
            ConnectionEstablishment c1 = new ConnectionEstablishment();
            ConnectionEstablishment c2 = new ConnectionEstablishment();
            c1.setName("Request thread");
            c2.setName("Reply thread");
            c1.openConnection(a1);
            c2.openConnection(a2);
            c1.setSendAddress(c2.getAddress());
            c2.setSendAddress(c1.getAddress());
            c1.start();
            c2.start();
            System.out.println("Attempt testmessage from c1 to c2");
            c1.sendMessage(new TestMessage(TestMessage.MESSAGE_REQUEST, TestMessage.TEST, new TestObject(TestObject.PERFORM_TEST, c1.getAddress(), c1.getSendAddress())));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            Message request = c2.receiveRequest();
            if(request.getMessageType() == TestMessage.TEST){
                System.out.println("Testrequest ontvangen");
                ((TestObject)request.getMessage()).handleRequest(c2);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            Message reply = c1.receiveReply();
            if(request.getMessageType() == TestMessage.TEST){
                System.out.println("Testreply ontvangen");
                ((TestObject)reply.getMessage()).handleReply(c1);
            }
            c1.closeConnection();
            c2.closeConnection();
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
