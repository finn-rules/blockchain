package edu.grinnell.csc207.blockchain;

import java.util.*;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {

    private byte[] hashData;

    /**
     * Hash : constructor for a hash
     *
     * @param data an array of bytes.
     */
    public Hash(byte[] data) {
        this.hashData = data;
    }

    /**
     * getData : returns the array of bytes.
     * @return the hash's byte array
     */
    public byte[] getData() {
        return this.hashData;
    }

    /**
     * isValid: checks whether the given hash meets the criteria for validity.
     *
     * @return (t/f): whether the hash is valid or not
     */
    public boolean isValid() {
        return Byte.toUnsignedInt(hashData[0]) == 0
                && Byte.toUnsignedInt(hashData[1]) == 0
                && Byte.toUnsignedInt(hashData[2]) == 0;
        /*
        if (this.hashData == null) {
            return false;
        }
        if (hashData.length >= 3) {
            for (int i = 0; i < 3; i++) {
               if (Byte.toUnsignedInt(hashData[i]) != 0) {
                return false;
               }
            }
            return true;
        }
        return false;
         */
    }

    /**
     * toString: returns a string from the hash array using format library utils
     * String: returns a string of form hexadecimal digits, 2 digits per byte
     *
     * @return the string format of the hash.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.hashData.length; i++) {
            s.append(String.format("%02x", hashData[i]));
        }
        return s.toString();
    }

    /**
     * equals: check to see if a hash is equal to another object.
     *
     * @param other : an object to compare
     * @return (t/f) whether the object and hash are identical
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Hash) {
            Hash o = (Hash) other;
            return Arrays.equals(o.getData(), this.getData());
        }
        return false;
    }
}

