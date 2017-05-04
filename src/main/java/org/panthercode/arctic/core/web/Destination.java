package org.panthercode.arctic.core.web;

/**
 * Created by architect on 04.05.17.
 */
public class Destination extends Node {

    private Node proxy;

    public Destination(String address) {
        super(address);
    }

    public Destination(String address, Node proxy) {
        super(address);

        this.proxy = proxy;
    }

    public Node getProxy() {
        return this.proxy;
    }

    public void setProxy(Node proxy) {
        this.proxy = proxy;
    }

    public boolean hasProxy() {
        return this.proxy != null;
    }
}
