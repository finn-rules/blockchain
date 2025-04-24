package edu.grinnell.csc207.blockchain;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A single block of a Blockchain.
 */
public class Block {

    public int currentNum;
    private int transferred;
    private Hash previousHash;
    private long nonce;
    private Hash currentHash;

    /**
     * Block : constructor for a block
     *
     * @param num : the number block that the block is in the blockchain
     * @param amount : the "amount" of the transaction
     * @param prevHash : the hash of the previous block (if there is one)
     * @throws NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
        this.currentNum = num;
        this.transferred = amount;
        this.previousHash = prevHash;
        this.nonce = miner();
        this.currentHash = calculateHash();

    }

    /**
     * Block : constructor for a block : features nonce
     *
     * @param num : the number block that the block is in the blockchain
     * @param amount : the "amount" of the transaction
     * @param prevHash : the hash of the previous block (if there is one)
     * @param nonce : the nonce value obtained for this block
     * @throws NoSuchAlgorithmException
     */
    public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
        this.currentNum = num;
        this.transferred = amount;
        this.previousHash = prevHash;
        this.nonce = nonce;
        this.currentHash = calculateHash();
    }

    /**
     * Miner: uses the sha algorithm to calculate a valid nonce.
     *
     * @return that valid nonce, as a long.
     * @throws NoSuchAlgorithmException
     */
    public long miner() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        long calculatedNonce = 0;
        Hash newHash = calculateNoncelessHash(calculatedNonce);
        while (!newHash.isValid()) {
            newHash = calculateNoncelessHash(++calculatedNonce);
        }
        return calculatedNonce;
    }

    /**
     * calculateHash : using the sha algorithm and a byte buffer, create a valid
     * hash for a specific block and its values.
     *
     * @return a calculated Hash.
     * @throws NoSuchAlgorithmException
     */
    public Hash calculateHash() throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer b = 
                ByteBuffer.allocate(64).putInt(this.currentNum).
                        putInt(this.transferred).putLong(this.nonce);

        if (this.previousHash != null) {
            b.put(this.previousHash.getData());
        }

        md.update(b.array());
        byte[] hash = md.digest();
        Hash calculated = new Hash(hash);
        return calculated;
    }

    /**
     * calculateNoncelessHash : calculate a Hash, without fully using a nonce
     *
     * @param testNonce : a nonce to use in the algorithm to create a hash
     * @return a calculated hash
     * @throws NoSuchAlgorithmException
     */
    public Hash calculateNoncelessHash(long testNonce) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("sha-256");
        ByteBuffer b = 
                ByteBuffer.allocate(64).putInt(this.currentNum).
                        putInt(this.transferred).putLong(testNonce);

        if (this.previousHash != null) {
            b.put(this.previousHash.getData());
        }

        md.update(b.array());
        byte[] hash = md.digest();
        Hash calculated = new Hash(hash);
        return calculated;
    }

    /**
     * getNum : gets the number of the block
     *
     * @return the number of the block
     */
    public int getNum() {
        return this.currentNum;
    }

    /**
     * getAmount : gets the transaction amount of the block
     *
     * @return the amount of the block
     */
    public int getAmount() {
        return this.transferred;
    }

    /**
     * getNonce : gets the nonce of the block
     *
     * @return the nonce of the block
     */
    public long getNonce() {
        return this.nonce;
    }

    /**
     * getNum : gets the Hash of the block
     *
     * @return the Hash of the block
     */
    public Hash getHash() {
        return this.currentHash;
    }

    /**
     * getPrev : gets the previous Hash of the block
     *
     * @return the previous Hash of the block
     */
    public Hash getPrevHash() {
        return this.previousHash;
    }

    /**
     * toString : create a string format for the block.
     *
     * @return the String format of the block.
     */
    @Override
    public String toString() {
        String s = "";
        s = s + "Block "
                + this.currentNum
                + " (Amount: < "
                + this.transferred
                + " > , Nonce: < "
                + this.nonce
                + " >, prevHash: < "
                + this.previousHash
                + " >, hash: < "
                + this.currentHash
                + " >";
        return s;
    }
}
