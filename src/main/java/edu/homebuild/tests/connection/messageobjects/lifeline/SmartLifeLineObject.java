/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.homebuild.tests.connection.messageobjects.lifeline;

import java.net.InetSocketAddress;

/**
 *
 * @author Rik
 */
public class SmartLifeLineObject extends LifelineObject {

    public SmartLifeLineObject(InetSocketAddress sender, InetSocketAddress receiver) {
        super(sender, receiver);
    }

    @Override
    public void handleMessage(edu.homebuild.tests.connection.controller.Connection con, Object obj) {
        super.handleMessage(con, obj); //TODO change body of generated method
    }

}
