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
import java.io.StreamCorruptedException;
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

    /**
     * Receive a message from the socket you are connected to.
     *
     * @return The message of type <code>Message</code> that was received over
     * this connection
     */
    @Override
    public Message receiveMessage() {
        if (!messageBuffer.isEmpty()) {
            return messageBuffer.remove(0);
        }
        return null;
    }

    /**
     * Get the <code>InetSocketAddress</code> that was set from where you want
     * to connect.
     *
     * @return the <code>InetSocketAddress</code> from where you want to connect
     * or <code>null</code> if it was not set.
     */
    @Override
    public final InetSocketAddress getAddress() {
        return address;
    }

    /**
     * Get the <code>InetSocketAddress</code> that was set to which you want to
     * connect.
     *
     * @return the <code>InetSocketAddress</code> to which you want to connect
     * or <code>null</code> if it was not set.
     */
    @Override
    public final InetSocketAddress getSendAddress() {
        return sendAddress;
    }

    /**
     * Set whether or not the connection should stay opened
     *
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Run method of the thread. It handles the packet receiving until the
     * connection should be closed
     */
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

    /**
     * Used for receiving a <code>DatagramPacket</code> over the connection.
     * While the connection is open, it will try to receive a message. The
     * amount of times that it checks whether or not the connection is still
     * open depends on the connection timeout that was set.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
            } catch (SocketException ex) {
                Logger.getLogger(ConnectionEstablishment.class.getName()).log(Level.SEVERE, "There is an error in the underlying protocol, such as an UDP error.", ex);
            }
        }
    }

    /**
     * Used for sending a <code>DatagramPacket</code> over the connection
     *
     * @param sendObject
     */
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

    /**
     * Prepare <code>ObjectInputStream in</code> for use by the receiver
     */
    private void prepareInputStream() {
        try {
            bin = new ByteArrayInputStream(receivePacket.getData());
            in = new ObjectInputStream(bin);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionEstablishment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prepare <code>ObjectOutputStream out</code> for use by the sender
     */
    private void prepareOutputStream() throws IOException {
        bout = new ByteArrayOutputStream(4096);
        out = new ObjectOutputStream(bout);
    }

    /**
     * This method takes the contents of <code>ObjectInputStream in</code> and
     * checks if it is a message. If so, it adds the message to the message
     * buffer.
     *
     * @throws StreamCorruptedException Control information in the stream is
     * inconsistent
     * @throws ClassNotFoundException Class of a serialized object cannot be
     * found.
     * @throws IllegalArgumentException Class of the read object is not of type
     * ConnectionMessage.
     */
    protected void addMessageToBuffer() throws StreamCorruptedException, ClassNotFoundException, IllegalArgumentException {
        try {
            Object obj = in.readObject();
            if (obj instanceof ConnectionMessage) {
                ConnectionMessage msg = (ConnectionMessage) obj;
                messageBuffer.add(msg);
            } else {
                throw new IllegalArgumentException("Class has to be of type ConnectionMessage!");
            }
        } catch (IOException ex) {
            if (ex instanceof StreamCorruptedException) {
                throw (StreamCorruptedException) ex;
            } else {
                Logger.getLogger(ConnectionEstablishment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
