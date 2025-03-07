package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;

/**
 * A linked list of hash-consistent blocks representing a ledger of
 * monetary transactions.
 */
public class BlockChain {
    public Node first;
    public Node last;
    private int size;

    public class Node {
        Block block;
        Node next;

        public Node(Block block, Node next) {
            this.block = block;
            this.next = next;
        }
    }

    public BlockChain(int initial) throws NoSuchAlgorithmException {
        Block firstBlock = new Block(0, initial, null);
        Node firstNode = new Node(firstBlock, null);
        this.first = firstNode;
        this.last = firstNode;
        this.size = 1;
    }

    public Block mine(int amount) throws NoSuchAlgorithmException {
        Block block = new Block(this.size, amount, this.last.block.getHash());
        block.miner();
        return block;
    }

    public void append(Block blk) throws IllegalArgumentException {
        if (blk.getPrevHash().equals(this.last.block.getHash()) && blk.getHash().isValid()) {
            this.last.next = new Node(blk, null);
            this.last = this.last.next;
            this.size++;
        } else {
            throw new IllegalArgumentException("Error: the block cannot be added to the list!");
        }
    }

    public int getSize() {
        return this.size;
    }

    public Hash getHash() {
        return last.block.getHash();
    }

    public boolean removeLast() {
        if (this.first == null || this.size == 1) {
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

    public boolean isValidBlockChain() {
        if (this.first == null) {
            return false;
        }
        Node cursor = this.first;
        int totalMoney = 0;
        Hash prevHash = null;
        while (cursor != null) {
            Block blk = cursor.block;
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

    public void printBalances() {
        int BobBalance = 0;
        int AliceBalance = this.first.block.getAmount();
        Node cursor = this.first;
        while (cursor != null) {
            int transferred = cursor.block.getAmount();
            BobBalance -= transferred;
            AliceBalance += transferred; // worried about this math.
            cursor = cursor.next;
        }
        String balancesString = "Alice: <" +
         AliceBalance + 
         ">, Bob: <" + 
         BobBalance +
         ">";
         System.out.println(balancesString);
    }

    @Override
    public String toString() {
        Node cursor = this.first;
        String s = "";
        while (cursor != null) {
            s = s  + "\n" + cursor.block.toString();
            cursor = cursor.next;
        }
        return s;
    }
}
