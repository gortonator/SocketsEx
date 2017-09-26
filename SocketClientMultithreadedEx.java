package socketexamples.bsds.edu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClientMultithreadedEx {
    
    static CyclicBarrier barrier; 
    
    public static void main(String[] args)  {
        String hostName;
        final int MAX_THREADS = 100 ;
        int port;
        
        if (args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            hostName= null;
            port = 12031;  // default port in SocketServer
        }
        barrier = new CyclicBarrier(MAX_THREADS+1);
        SocketClientThreadEx clients[] = new SocketClientThreadEx[MAX_THREADS];
        for (int i = 0; i < MAX_THREADS ; i++) {
            clients[i] = new SocketClientThreadEx (hostName, port, barrier);
            clients[i].start();
           
        }
        try {
           System.out.println("=====Main Thread waiting at barrier") ;
           barrier.await();
           System.out.println("Maim thread Thread finishing");
         } catch (InterruptedException ex) {
           return;
         } catch (BrokenBarrierException ex) {
           return;
         }
        
     
                
    }

      
}
