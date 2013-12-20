/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.homebuild.tests.connection.messageobjects;

import edu.homebuild.tests.connection.controller.Connection;
import java.net.InetSocketAddress;

/**
 *
 * @author Rik
 */
public abstract class CommunicationObject extends ConnectionObject{

    public CommunicationObject(InetSocketAddress sender, InetSocketAddress receiver) {
        super(sender, receiver);
    }

    public abstract void handleReply(Connection con);
}
