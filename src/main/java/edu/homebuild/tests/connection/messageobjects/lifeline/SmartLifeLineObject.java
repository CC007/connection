/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.homebuild.tests.connection.messageobjects.lifeline;

import edu.homebuild.tests.connection.controller.Connection;
import java.net.InetSocketAddress;

/**
 *
 * @author Rik
 */
public class SmartLifeLineObject extends LifelineObject{

    public SmartLifeLineObject(InetSocketAddress sender, InetSocketAddress receiver) {
        super(sender, receiver);
    }

    @Override
    public void handleMessage(Connection con) {
        super.handleMessage(con); //To change body of generated methods, choose Tools | Templates.
    }
    
}
