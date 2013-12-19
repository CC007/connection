/**
 *
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
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private DatagramPacket receivePacket = null;
    private DatagramPacket sendPacket = null;
    private DatagramSocket socket = null;
    private ArrayList<Message> requestBuffer;
    private ArrayList<Message> replyBuffer;
    private InetSocketAddress address;
    private InetSocketAddress sendAddress;
    private boolean running;

    public ConnectionEstablishment() {
        this.requestBuffer = new ArrayList<>();
        this.replyBuffer = new ArrayList<>();

    }

    public ConnectionEstablishment(int timeout, InetSocketAddress address) throws SocketException {
        this();
        this.openConnection(timeout, address);
    }

    @Override
    public void sendMessage(Message msg) {
        sendPacket(msg);
    }

    @Override
    public Message receiveRequest() {
        if (!requestBuffer.isEmpty()) {
            return requestBuffer.remove(0);
        }
        return null;
    }

    @Override
    public Message receiveReply() {
        if (!replyBuffer.isEmpty()) {
            return replyBuffer.remove(0);
        }
        return null;
    }

    @Override
    public void run() {
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

    public void openConnection(int timeout, InetSocketAddress address) throws SocketException {
        this.socket = new DatagramSocket(address);
        this.socket.setSoTimeout(timeout);
        this.address = (InetSocketAddress) this.socket.getLocalSocketAddress();
    }

    @Override
    public void openConnection(InetSocketAddress address) throws SocketException {
        openConnection(DEFAULT_TIMEOUT, address);
    }

    @Override
    public void closeConnection() {
        running = false;
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

    private void sendPacket(Object sendObject) {
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

    private void receivePacket() throws IOException, ClassNotFoundException {
        receivePacket = new DatagramPacket(new byte[4096], 4096);
        while (running) {
            try {
                socket.receive(receivePacket);
                prepareInputStream();
                addMessageToBuffer();
                break;
            } catch (SocketTimeoutException ex) {
                continue;
            }
        }
    }

    private void addMessageToBuffer() throws IOException, ClassNotFoundException {
        Object obj = in.readObject();
        if (obj instanceof CommunicationMessage) {
            CommunicationMessage msg = (CommunicationMessage) obj;
            if (msg.isRequest()) {
                requestBuffer.add(msg);
            } else if (msg.isReply()) {
                replyBuffer.add(msg);
            }
        }
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public InetSocketAddress getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(InetSocketAddress sendAddress) {
        this.sendAddress = sendAddress;
    }

}
