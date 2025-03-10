package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a blockchain.
 */
public class Block {
    private int currentNum;
    private int transferred;
    private Hash previousHash;
    private long nonce;
    private Hash currentHash;

    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.currentNum = num;
        this.transferred = amount;
        this.previousHash = prevHash;
        this.nonce = miner();
        this.currentHash = calculateHash();

    }

    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.currentNum = num;
        this.transferred = amount;
        this.previousHash = prevHash;
        this.nonce = nonce;
        this.currentHash = calculateHash();
    }

    public long miner() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        long calculatedNonce = 0;
        Hash newHash = calculateNoncelessHash(calculatedNonce);
        while (!newHash.isValid()) {
            newHash = calculateNoncelessHash(++calculatedNonce);
            calculatedNonce++;
        }
        return calculatedNonce;
    }

    public Hash calculateHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer b = ByteBuffer.allocate(64).putInt(this.currentNum).putInt(this.transferred).putLong(this.nonce);

        if (this.previousHash != null) {
            b.put(this.previousHash.getData());
        }

        md.update(b.array());
        byte[] hash = md.digest();
        Hash calculated = new Hash(hash);
        return calculated;
    }
    
    public Hash calculateNoncelessHash(long testNonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer b = ByteBuffer.allocate(64).putInt(this.currentNum).putInt(this.transferred).putLong(testNonce);

        if (this.previousHash != null) {
            b.put(this.previousHash.getData());
        }

        md.update(b.array());
        byte[] hash = md.digest();
        Hash calculated = new Hash(hash);
        return calculated;
    }


    public int getNum() {
        return this.currentNum;
    }

    public int getAmount() {
        return this.transferred;
    }

    public long getNonce() {
        return this.nonce;
    }

    public Hash getHash() {
        return this.currentHash;
    }

    public Hash getPrevHash() {
        return this.previousHash;
    }

    @Override
    public String toString() {
        String s = "";
        s = s + "Block " + 
        this.currentNum + 
        " (Amount: < " + 
        this.transferred +
        " > , Nonce: < " +
        this.nonce +
        " >, prevHash: < " +
        this.previousHash +
        " >, hash: < " +
        this.currentHash +
        " >";
        return s;
    }
}
