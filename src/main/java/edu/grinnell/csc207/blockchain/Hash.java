package edu.grinnell.csc207.blockchain;
import java.lang.Byte;
import java.util.Formatter;
import java.util.*;

/**
 * A wrapper class over a hash value (a byte array).
 */
public class Hash {
    private byte[] hashData;
    public Hash(byte[] data) {
        hashData = data;
    }

    public byte[] getData() {
        return this.hashData;
    }
    /**
     * isValid: checks whether the given hash meets the criteria for validity. 
     * @return boolean: whether the has is valid or not
     */
    public boolean isValid() {
        if (hashData.length >= 3) {
            for (int i = 0; i < 3; i++) {
               if (Byte.toUnsignedInt(hashData[i]) != 0) {
                return false;
               }
            }
            return true;
        }
        return false;
    }
    /**
     * toString: returns a string from the hash array using format library utils
     * String: returns a string of form hexadecimal digits, 2 digits per byte
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < hashData.length; i++) {
            s.append(String.format("%02x", hashData[i]));
        }
        return s.toString(); 
    }

    public boolean equals(Object other) {
        if(other instanceof Hash) {
            Hash o = (Hash) other;
            return Arrays.equals(o.getData(), this.getData());
        }
        return false;
    }


    }
