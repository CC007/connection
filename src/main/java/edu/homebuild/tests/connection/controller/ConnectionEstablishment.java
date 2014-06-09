/*
 * edu.homebuild.test.connection and its classes and sub-packages by Rik Schaaf are licensed under a Creative Commons Attribution-ShareAlike 4.0 International License. 
 * Based on a work at https://github.com/killje/asteroids2.
 *
 * This project is a WIP. No guarantees are given that everyting will work as it should.
 */
package edu.homebuild.tests.connection.controller;

import edu.homebuild.tests.connection.message.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rik Schaaf, University of Groningen
 */
public class ConnectionEstablishment extends Thread implements Connection {

    public static final int DEFAULT_TIMEOUT = 10;
    private ByteArrayInputStream bin = null;
    private ByteArrayOutputStream bout = null;
    protected ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private DatagramPacket receivePacket = null;
    private DatagramPacket sendPacket = null;
    private DatagramSocket socket = null;
    protected ArrayList<Message> messageBuffer;
    private InetSocketAddress address;
    private InetSocketAddress sendAddress;
    private boolean running;

    /**
     * Create a <code>ConnectionEstablishment</code> object without setting a
     * socket address from where to connect or setting the connection timeout.
     */
    public ConnectionEstablishment() {
        this.messageBuffer = new ArrayList<>();
    }

    /**
     * Create a <code>ConnectionEstablishment</code> object, set a socket
     * address from where to connect and set the connection timeout.
     *
     * @param timeout the connection timeout for receiving data
     * @param address the socket addres from where to connect
     * @throws java.net.SocketException
     */
    public ConnectionEstablishment(int timeout, InetSocketAddress address) throws SocketException {
        this.openConnection(timeout, address);
        this.messageBuffer = new ArrayList<>();
    }

    /**
     * Open a socket to send and receive data. Also set the connection timeout
     * of the connection.
     *
     * @param timeout the connection timeout for receiving data
     * @param address the <code>InetSocketAddress</code> from where you want to
     * connect.
     * @throws SocketException
     */
    public final void openConnection(int timeout, InetSocketAddress address) throws SocketException {
        this.socket = new DatagramSocket(address);
        this.socket.setSoTimeout(timeout);
        this.address = (InetSocketAddress) this.socket.getLocalSocketAddress();
    }

    /**
     * Open a socket to send and receive data.
     *
     * @param address the <code>InetSocketAddress</code> from where you want to
     * connect.
     * @throws SocketException
     */
    @Override
    public final void openConnection(InetSocketAddress address) throws SocketException {
        openConnection(DEFAULT_TIMEOUT, address);
    }

    /**
     * Close the socket that was opened by this connection.
     */
    @Override
    public final void closeConnection() {
        running = false;
    }

    /**
     * Set the <code>InetSocketAddress</code> to which you want connect
     *
     * @param sendAddress the <code>InetSocketAddress</code> to which you want
     * connect
     */
    @Override
    public final void setSendAddress(InetSocketAddress sendAddress) {
        this.sendAddress = sendAddress;
    }

    /**
     * Send a message to the socket you are connected to.
     *
     * @param msg The message of type <code>Message</code> that you want to send
     * over the connection.
     */
    @Override
    public void sendMessage(Message msg) {
        sendPacket(msg);
    }

    @Override
    public Message receiveMessage() {
        if (!messageBuffer.isEmpty()) {
            return messageBuffer.remove(0);
        }
        return null;
    }

    @Override
    public final InetSocketAddress getAddress() {
        return address;
    }

    @Override
    public final InetSocketAddress getSendAddress() {
        return sendAddress;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public final void run() {
        running = true;
        while (running) {
            try {
                receivePacket();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ConnectionEstablishment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        socket.close();
    }

    private void prepareInputStream() {
        try {
            bin = new ByteArrayInputStream(receivePacket.getData());
            in = new ObjectInputStream(bin);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionEstablishment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareOutputStream() throws IOException {
        bout = new ByteArrayOutputStream(4096);
        out = new ObjectOutputStream(bout);
    }

    protected final void sendPacket(Object sendObject) {
        try {
            prepareOutputStream();
            out.writeObject(sendObject);
            out.flush();
            sendPacket = new DatagramPacket(bout.toByteArray(), bout.toByteArray().length, sendAddress);
            socket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionEstablishment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected final void receivePacket() throws IOException, ClassNotFoundException {
        receivePacket = new DatagramPacket(new byte[4096], 4096);
        while (running) {
            try {
                socket.receive(receivePacket);
                prepareInputStream();
                addMessageToBuffer();
                break;
            } catch (SocketTimeoutException ex) {
                //needed for stopping the socket, continue in the loop
            }
        }
    }

    protected void addMessageToBuffer() throws IOException, ClassNotFoundException {
        Object obj = in.readObject();
        if (obj instanceof ConnectionMessage) {
            ConnectionMessage msg = (ConnectionMessage) obj;
            messageBuffer.add(msg);
        }
    }
}
