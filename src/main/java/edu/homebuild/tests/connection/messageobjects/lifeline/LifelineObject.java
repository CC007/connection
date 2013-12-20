/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.homebuild.tests.connection.messageobjects.lifeline;

import edu.homebuild.tests.connection.controller.Connection;
import edu.homebuild.tests.connection.messageobjects.ConnectionObject;
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
    public void handleMessage(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
