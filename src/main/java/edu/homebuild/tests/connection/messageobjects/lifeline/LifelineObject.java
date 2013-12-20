/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.homebuild.tests.connection.messageobjects.lifeline;

import edu.homebuild.tests.connection.messageobjects.ConnectionObject;
import edu.homebuild.tests.connection.model.lifeline.DisconnectionDetector;
import java.net.InetSocketAddress;

/**
 *
 * @author Rik
 */
public class LifelineObject extends ConnectionObject{

    public LifelineObject(InetSocketAddress sender, InetSocketAddress receiver) {
        super(sender, receiver);
    }

    @Override
    public void handleMessage(edu.homebuild.tests.connection.controller.Connection con, Object obj) {
        if(obj instanceof DisconnectionDetector){
           ((DisconnectionDetector)obj).setActive(true);
        }else{
            throw new IllegalArgumentException("The object should be a DisconnectionDetector");
        }
    }
    
}
