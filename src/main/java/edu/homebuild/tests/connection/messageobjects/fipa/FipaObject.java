/**
 * 
 */

package edu.homebuild.tests.connection.messageobjects.fipa;

import edu.homebuild.tests.connection.messageobjects.MessageObject;
import java.net.InetSocketAddress;

/**
 * @author Rik Schaaf, University of Groningen
 */
public abstract class FipaObject extends MessageObject{
    protected String content;
    protected String language;
    protected String replyWith;
    protected String inReplyTo;
    protected String ontology;
    protected String protocol;

    public FipaObject(String content, String language, String replyWith, String inReplyTo, String ontology, String protocol, InetSocketAddress sender, InetSocketAddress receiver) {
        super(sender, receiver);
        this.content = content;
        this.language = language;
        this.replyWith = replyWith;
        this.inReplyTo = inReplyTo;
        this.ontology = ontology;
        this.protocol = protocol;
    }
    
}