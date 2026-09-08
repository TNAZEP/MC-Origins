package net.minecraft.server.rcon.thread;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.rcon.PktUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RconClient extends GenericThread {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SERVERDATA_AUTH = 3;
   private static final int SERVERDATA_EXECCOMMAND = 2;
   private static final int SERVERDATA_RESPONSE_VALUE = 0;
   private static final int SERVERDATA_AUTH_RESPONSE = 2;
   private static final int SERVERDATA_AUTH_FAILURE = -1;
   private boolean authed;
   private final Socket client;
   private final byte[] buf = new byte[1460];
   private final String rconPassword;
   private final ServerInterface serverInterface;

   RconClient(ServerInterface var1, String var2, Socket var3) {
      super("RCON Client " + â˜ƒ.getInetAddress());
      this.serverInterface = â˜ƒ;
      this.client = â˜ƒ;

      try {
         this.client.setSoTimeout(0);
      } catch (Exception var5) {
         this.running = false;
      }

      this.rconPassword = â˜ƒ;
   }

   public void run() {
      try {
         try {
            while(this.running) {
               BufferedInputStream â˜ƒ = new BufferedInputStream(this.client.getInputStream());
               int â˜ƒx = â˜ƒ.read(this.buf, 0, 1460);
               if (10 > â˜ƒx) {
                  return;
               }

               int â˜ƒ = 0;
               int â˜ƒx = PktUtils.intFromByteArray(this.buf, 0, â˜ƒx);
               if (â˜ƒx != â˜ƒx - 4) {
                  return;
               }

               â˜ƒ += 4;
               int â˜ƒ = PktUtils.intFromByteArray(this.buf, â˜ƒ, â˜ƒx);
               â˜ƒ += 4;
               int â˜ƒx = PktUtils.intFromByteArray(this.buf, â˜ƒ);
               â˜ƒ += 4;
               switch(â˜ƒx) {
                  case 2:
                     if (this.authed) {
                        String â˜ƒxx = PktUtils.stringFromByteArray(this.buf, â˜ƒ, â˜ƒx);

                        try {
                           this.sendCmdResponse(â˜ƒ, this.serverInterface.runCommand(â˜ƒxx));
                        } catch (Exception var15) {
                           this.sendCmdResponse(â˜ƒ, "Error executing: " + â˜ƒxx + " (" + var15.getMessage() + ")");
                        }
                        break;
                     }

                     this.sendAuthFailure();
                     break;
                  case 3:
                     String â˜ƒxx = PktUtils.stringFromByteArray(this.buf, â˜ƒ, â˜ƒx);
                     â˜ƒ += â˜ƒxx.length();
                     if (!â˜ƒxx.isEmpty() && â˜ƒxx.equals(this.rconPassword)) {
                        this.authed = true;
                        this.send(â˜ƒ, 2, "");
                        break;
                     }

                     this.authed = false;
                     this.sendAuthFailure();
                     break;
                  default:
                     this.sendCmdResponse(â˜ƒ, String.format("Unknown request %s", Integer.toHexString(â˜ƒx)));
               }
            }

            return;
         } catch (IOException var16) {
         } catch (Exception var17) {
            LOGGER.error("Exception whilst parsing RCON input", var17);
         }
      } finally {
         this.closeSocket();
         LOGGER.info("Thread {} shutting down", this.name);
         this.running = false;
      }
   }

   private void send(int var1, int var2, String var3) throws IOException {
      ByteArrayOutputStream â˜ƒ = new ByteArrayOutputStream(1248);
      DataOutputStream â˜ƒx = new DataOutputStream(â˜ƒ);
      byte[] â˜ƒxx = â˜ƒ.getBytes(StandardCharsets.UTF_8);
      â˜ƒx.writeInt(Integer.reverseBytes(â˜ƒxx.length + 10));
      â˜ƒx.writeInt(Integer.reverseBytes(â˜ƒ));
      â˜ƒx.writeInt(Integer.reverseBytes(â˜ƒ));
      â˜ƒx.write(â˜ƒxx);
      â˜ƒx.write(0);
      â˜ƒx.write(0);
      this.client.getOutputStream().write(â˜ƒ.toByteArray());
   }

   private void sendAuthFailure() throws IOException {
      this.send(-1, 2, "");
   }

   private void sendCmdResponse(int var1, String var2) throws IOException {
      int â˜ƒ = â˜ƒ.length();

      do {
         int â˜ƒx = 4096 <= â˜ƒ ? 4096 : â˜ƒ;
         this.send(â˜ƒ, 0, â˜ƒ.substring(0, â˜ƒx));
         â˜ƒ = â˜ƒ.substring(â˜ƒx);
         â˜ƒ = â˜ƒ.length();
      } while(0 != â˜ƒ);
   }

   @Override
   public void stop() {
      this.running = false;
      this.closeSocket();
      super.stop();
   }

   private void closeSocket() {
      try {
         this.client.close();
      } catch (IOException var2) {
         LOGGER.warn("Failed to close socket", var2);
      }
   }
}
