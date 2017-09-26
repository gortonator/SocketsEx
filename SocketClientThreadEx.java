package socketexamples.bsds.edu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ian Gortan
 */ 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;



public class SocketClientThreadEx extends Thread{
   
    private long clientID;
    String hostName;
    int port;
    CyclicBarrier synk;
    
    public SocketClientThreadEx(String hostName, int port, CyclicBarrier barrier) {
        this.hostName = hostName;
        this.port = port;
        clientID = Thread.currentThread().getId();
        synk = barrier;
    }
    
    public void run() {
        
        try {
            for (int i = 0; i < 100 ; i++ ) {
                Socket s = new Socket(hostName, port);
            
                PrintWriter out =
                    new PrintWriter(s.getOutputStream(), true);
                BufferedReader in =
                    new BufferedReader(
                        new InputStreamReader(s.getInputStream()));
            
                out.println("Client ID is " +  Long.toString(clientID));
                System.out.println(in.readLine());
                s.close();
            } 
                       
        
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
        try {
           System.out.println("=====Thread waiting at barrier") ;
           synk.await();
           System.out.println("Thread finishing");
         } catch (InterruptedException ex) {
           return;
         } catch (BrokenBarrierException ex) {
           return;
         }
        
    }
}

    

