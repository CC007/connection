/**
 *
 */
package edu.homebuild.tests.connection.messageobjects.fipa;

import java.net.InetSocketAddress;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class FipaNegotiation extends FipaObject {

    public FipaNegotiation(String content, String language, String replyWith, String inReplyTo, String ontology, String protocol, InetSocketAddress sender, InetSocketAddress receiver) {
        super(content, language, replyWith, inReplyTo, ontology, protocol, sender, receiver);
    }

    @Override
    public void handleReply(edu.homebuild.tests.connection.controller.Connection con, Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleMessage(edu.homebuild.tests.connection.controller.Connection con, Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
