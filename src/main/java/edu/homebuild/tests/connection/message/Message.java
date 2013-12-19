/*
 * 
 */

package edu.homebuild.tests.connection.message;

import java.io.Serializable;

/**
 * @author Rik Schaaf, University of Groningen
 */

public interface Message extends Serializable {
    
    public int getMessageType();
    public Object getMessage();
    
}
