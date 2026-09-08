package net.minecraft.client.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.DefaultUncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LanServerDetection {
   static final AtomicInteger UNIQUE_THREAD_ID = new AtomicInteger(0);
   static final Logger LOGGER = LogManager.getLogger();

   public static class LanServerDetector extends Thread {
      private final LanServerDetection.LanServerList serverList;
      private final InetAddress pingGroup;
      private final MulticastSocket socket;

      public LanServerDetector(LanServerDetection.LanServerList var1) throws IOException {
         super("LanServerDetector #" + LanServerDetection.UNIQUE_THREAD_ID.incrementAndGet());
         this.serverList = â˜ƒ;
         this.setDaemon(true);
         this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LanServerDetection.LOGGER));
         this.socket = new MulticastSocket(4445);
         this.pingGroup = InetAddress.getByName("224.0.2.60");
         this.socket.setSoTimeout(5000);
         this.socket.joinGroup(this.pingGroup);
      }

      public void run() {
         byte[] â˜ƒ = new byte[1024];

         while(!this.isInterrupted()) {
            DatagramPacket â˜ƒx = new DatagramPacket(â˜ƒ, â˜ƒ.length);

            try {
               this.socket.receive(â˜ƒx);
            } catch (SocketTimeoutException var5) {
               continue;
            } catch (IOException var6) {
               LanServerDetection.LOGGER.error("Couldn't ping server", var6);
               break;
            }

            String â˜ƒxx = new String(â˜ƒx.getData(), â˜ƒx.getOffset(), â˜ƒx.getLength(), StandardCharsets.UTF_8);
            LanServerDetection.LOGGER.debug("{}: {}", â˜ƒx.getAddress(), â˜ƒxx);
            this.serverList.addServer(â˜ƒxx, â˜ƒx.getAddress());
         }

         try {
            this.socket.leaveGroup(this.pingGroup);
         } catch (IOException var4) {
         }

         this.socket.close();
      }
   }

   public static class LanServerList {
      private final List<LanServer> servers = Lists.<LanServer>newArrayList();
      private boolean isDirty;

      public synchronized boolean isDirty() {
         return this.isDirty;
      }

      public synchronized void markClean() {
         this.isDirty = false;
      }

      public synchronized List<LanServer> getServers() {
         return Collections.unmodifiableList(this.servers);
      }

      public synchronized void addServer(String var1, InetAddress var2) {
         String â˜ƒ = LanServerPinger.parseMotd(â˜ƒ);
         String â˜ƒx = LanServerPinger.parseAddress(â˜ƒ);
         if (â˜ƒx != null) {
            â˜ƒx = â˜ƒ.getHostAddress() + ":" + â˜ƒx;
            boolean â˜ƒxx = false;

            for(LanServer â˜ƒxxx : this.servers) {
               if (â˜ƒxxx.getAddress().equals(â˜ƒx)) {
                  â˜ƒxxx.updatePingTime();
                  â˜ƒxx = true;
                  break;
               }
            }

            if (!â˜ƒxx) {
               this.servers.add(new LanServer(â˜ƒ, â˜ƒx));
               this.isDirty = true;
            }
         }
      }
   }
}
