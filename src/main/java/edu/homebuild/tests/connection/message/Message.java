/*
 * 
 */

package edu.homebuild.tests.connection.message;

import java.io.Serializable;

/**
 * @author Rik Schaaf, University of Groningen
 */

public interface Message extends Serializable {
    // Two message directions that should be included
    public static final int MESSAGE_REQUEST = 1;
    public static final int MESSAGE_REPLY = 2;
    
    public int getMessageType();
    public Object getMessage();
    public boolean isRequest();
    public boolean isReply();
    
}
