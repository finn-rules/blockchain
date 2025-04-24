package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of monetary
 * transactions.
 */
public class BlockChain {

    public Node first;
    public Node last;
    private int size;

    /**
     * A subclass representing a Node in a linked list.
     */
    public class Node {

        Block block;
        Node next;

        /**
         * Constructor for a node.
         *
         * @param block : the block (see block class)
         * @param next : the next node
         */
        public Node(Block block, Node next) {
            this.block = block;
            this.next = next;
        }
    }

    /**
     * BlockChain: constructor for a blockchain object
     *
     * @param initial : the value of the initial transaction (therefore the
     * block)
     * @throws NoSuchAlgorithmException
     */
    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, initial, null);
        Node firstNode = new Node(firstBlock, null);
        this.first = firstNode;
        this.last = firstNode;
        this.size = 1;
    }

    /**
     * mine : creates a block and then mines for it. Updates the block as such.
     *
     * @param amount the integer amount of the initial transaction
     * @return the updated, mined block.
     * @throws NoSuchAlgorithmException
     */
    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block block = new Block(this.size, amount, this.last.block.getHash());
        block.miner();
        return block;
    }

    /**
     * append: appends a block to the chain.
     *
     * @param blk : the block to append
     * @throws IllegalArgumentException
     */
    public void append(Block blk) throws IllegalArgumentException {
        if (blk.getPrevHash().equals(this.last.block.getHash()) && blk.getHash().isValid()) {
            this.last.next = new Node(blk, null);
            this.last = this.last.next;
            this.size++;
        } else {
            throw new IllegalArgumentException("Error: the block cannot be added to the list!");
        }
    }

    /**
     * getSize: returns the size of the blockChain.
     *
     * @return the size of the block chain.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * getHash: returns the hash of the current (or last) block on the chain.
     *
     * @return the hash of the last block of the chain.
     */
    public Hash getHash() {
        return last.block.getHash();
    }

    /**
     * removeLast : gets rid of the last block on the chain.
     *
     * @return whether or not the block could be removed (t/f)
     */
    public boolean removeLast() {
        if (this.first == null || this.size == 0) {
            return false;
        }
        int endPos = this.size - 1;
        Node cursor = this.first;
        for (int i = 0; i < endPos; i++) {
            cursor = cursor.next;
        }
        cursor.next = null;
        this.last = cursor;
        this.size--;
        return true;
    }

    /**
     * isValidBlockChain : assesses properties of the BlockChain to check if
     * it's valid.
     *
     * @return (t/f) whether or not the chain is "valid"
     */
    public boolean isValidBlockChain() {
        if (this.first == null) {
            return false;
        }
        Node cursor = this.first;
        int totalMoney = 0;
        Hash prevHash = null;
        Block blk = cursor.block;
        totalMoney += blk.getAmount();
        if (totalMoney < 0) {
            return false;
        } else if (!blk.getHash().isValid()) {
            return false;
        }
        prevHash = blk.getHash();
        cursor = cursor.next;
        while (cursor != null) {
            blk = cursor.block;
            totalMoney += blk.getAmount();
            if (totalMoney < 0) {
                return false;
            } else if (!blk.getPrevHash().equals(prevHash)) {
                return false;
            } else if (!blk.getHash().isValid()) {
                return false;
            }
            prevHash = blk.getHash();
            cursor = cursor.next;
        }
        return true;
    }

    /**
     * printBalances : calculate the balances of the two from the chain and
     * print them.
     */
    public void printBalances() {
        int bobBalance = 0;
        int aliceBalance = this.first.block.getAmount();
        Node cursor = this.first.next;
        while (cursor != null) {
            int transferred = cursor.block.getAmount();
            if (transferred > 0) {
                bobBalance -= transferred;
                aliceBalance += transferred;
            } else {
                aliceBalance += transferred;
                bobBalance -= transferred;
            }
            cursor = cursor.next;
        }
        String balancesString = "Alice: <"
                + aliceBalance
                + ">, Bob: <"
                + bobBalance
                + ">";
        System.out.println(balancesString);
    }

    /**
     * toString : turn the entire chain to a string using Block's toString
     * method.
     *
     * @return the String representing the entire train.
     */
    @Override
    public String toString() {
        Node cursor = this.first;
        String s = "";
        while (cursor != null) {
            s = s + "\n" + cursor.block.toString();
            cursor = cursor.next;
        }
        return s;
    }
}
