/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.homebuild.tests.connection.model.lifeline;

import edu.homebuild.tests.connection.controller.ConnectionEstablishment;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rik
 */
public class DisconnectionDetector extends Thread {

    private boolean active;
    private final ConnectionEstablishment connection;

    public DisconnectionDetector(boolean active, ConnectionEstablishment connection) {
        this.active = active;
        this.connection = connection;
        this.setName("Disconnection detector");
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
I     */
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(DisconnectionDetector.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (active) {
            this.active = false;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(DisconnectionDetector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        connection.setRunning(false);
    }

}
