/**
 *
 */
package edu.homebuild.tests.connection.messageobjects.fipa;

import edu.homebuild.tests.connection.controller.Connection;
import java.net.InetSocketAddress;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class FipaReply extends FipaObject {

    public FipaReply(String content, String language, String replyWith, String inReplyTo, String ontology, String protocol, InetSocketAddress sender, InetSocketAddress receiver) {
        super(content, language, replyWith, inReplyTo, ontology, protocol, sender, receiver);
    }

    @Override
    public void handleMessage(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement handleRequest.
    }

    @Override
    public void handleReply(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO implement handleReply.
    }
}
