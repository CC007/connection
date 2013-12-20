/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.homebuild.tests.connection.message.lifeline;

import edu.homebuild.tests.connection.message.ConnectionMessage;
import edu.homebuild.tests.connection.messageobjects.ConnectionObject;

/**
 *
 * @author Rik
 */
public class LifelineMessage extends ConnectionMessage{

    public static final int SIMPLE_LIFELINE = 1;
    public static final int SMART_LIFELINE = 1;
    public LifelineMessage(ConnectionObject message, int messageType) {
        super(message, messageType);
    }
    
}
