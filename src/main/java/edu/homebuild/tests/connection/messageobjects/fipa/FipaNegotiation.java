/**
 *
 */
package edu.homebuild.tests.connection.messageobjects.fipa;

import edu.homebuild.tests.connection.controller.Connection;
import java.net.InetSocketAddress;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class FipaNegotiation extends FipaObject {

    public FipaNegotiation(String content, String language, String replyWith, String inReplyTo, String ontology, String protocol, InetSocketAddress sender, InetSocketAddress receiver) {
        super(content, language, replyWith, inReplyTo, ontology, protocol, sender, receiver);
    }

    @Override
    public void handleRequest(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement handleRequest.
    }

    @Override
    public void handleReply(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement handleReply.
    }
}
