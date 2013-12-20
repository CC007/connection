/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.homebuild.tests.connection.controller;

import edu.homebuild.tests.connection.message.CommunicationMessage;

/**
 *
 * @author Rik
 */
public interface Communication {
    public CommunicationMessage receiveReply();
}
