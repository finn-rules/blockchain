package edu.grinnell.csc207.blockchain;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main driver for the block chain program.
 */
public class BlockChainDriver {

    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     * @throws NoSuchAlgorithmException 
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        if(args.length != 1) {
            System.out.println("Error! The blockchain takes one argument which should be the initial positive transaction");
            System.exit(0);
    }  
    int original = Integer.parseInt(args[0]);
    BlockChain blockChain = new BlockChain(original);

    boolean resume = true;
    while (resume) {
        System.out.println(blockChain.toString());
        Scanner s = new Scanner(System.in);
        System.out.print("Command? ");
        String input = s.nextLine();

        if (input != null) {
            switch (input) {
                case "mine" -> {
                    System.out.print("Amount Transfered? ");
                    int num = Integer.parseInt(s.nextLine());
                    long nonce = blockChain.mine(num).getNonce();
                    System.out.println("Amount = " + num + ", nonce = " + nonce);
                }
                case "append" -> {
                    System.out.print("Amount Transfered? ");
                    int num = Integer.parseInt(s.nextLine());
                    System.out.print("Nonce? ");
                    long nonce = Long.parseLong(s.nextLine());
                    Block addBlock = new Block(blockChain.getSize(), num, blockChain.last.block.getHash(), nonce);
                    blockChain.append(addBlock);
                }
                case "remove" -> {
                    blockChain.removeLast();
                }
                case "check" -> {
                    if(blockChain.isValidBlockChain()) {
                        System.out.println("Chain is valid!");
                    } else {
                        System.out.println("Chain is invalid!");
                    }
                }
                case "report" -> {
                    blockChain.printBalances();
                }
                case "help" -> {
                    System.out.println("Valid commands:");
                    System.out.println("\tappend: appends a new block onto the end of the chain");
                    System.out.println("\tremove: removes the last block from the end of the chain");
                    System.out.println("\tcheck: checks that the block chain is valid");
                    System.out.println("\treport: reports the balances of Alice and Bob");
                    System.out.println("\thelp: prints this list of commands");
                    System.out.println("\tquit: quits the program");
                }
                case "quit" -> {
                    resume = false;
                }
                default -> {
                    
                }
                }
            }
            s.close();
        }  
    }
}
