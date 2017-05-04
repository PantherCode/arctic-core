package org.panthercode.arctic.core.web;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.net.URI;

/**
 * Created by architect on 04.05.17.
 */
public class Node {
    private String address;

    public Node(String address) {
        this.address = ArgumentUtils.checkNotNull(address, "address");
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRemote() {
        throw new NotImplementedException();
    }

    public URI toURI() {
        return URI.create(this.address);
    }

    @Override
    public String toString() {
        return this.address;
    }
}
