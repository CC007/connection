/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.homebuild.tests.connection.message;

/**
 *
 * @author Rik
 */
public interface MessageDirection {

    // Two message directions that should be included
    public static final int MESSAGE_REQUEST = 1;
    public static final int MESSAGE_REPLY = 2;

    public boolean isRequest();

    public boolean isReply();

}
