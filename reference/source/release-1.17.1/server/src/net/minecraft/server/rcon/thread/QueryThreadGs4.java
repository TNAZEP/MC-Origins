package net.minecraft.server.rcon.thread;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.rcon.NetworkDataOutputStream;
import net.minecraft.server.rcon.PktUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryThreadGs4 extends GenericThread {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String GAME_TYPE = "SMP";
   private static final String GAME_ID = "MINECRAFT";
   private static final long CHALLENGE_CHECK_INTERVAL = 30000L;
   private static final long RESPONSE_CACHE_TIME = 5000L;
   private long lastChallengeCheck;
   private final int port;
   private final int serverPort;
   private final int maxPlayers;
   private final String serverName;
   private final String worldName;
   private DatagramSocket socket;
   private final byte[] buffer = new byte[1460];
   private String hostIp;
   private String serverIp;
   private final Map<SocketAddress, QueryThreadGs4.RequestChallenge> validChallenges;
   private final NetworkDataOutputStream rulesResponse;
   private long lastRulesResponse;
   private final ServerInterface serverInterface;

   private QueryThreadGs4(ServerInterface var1, int var2) {
      super("Query Listener");
      this.serverInterface = â˜ƒ;
      this.port = â˜ƒ;
      this.serverIp = â˜ƒ.getServerIp();
      this.serverPort = â˜ƒ.getServerPort();
      this.serverName = â˜ƒ.getServerName();
      this.maxPlayers = â˜ƒ.getMaxPlayers();
      this.worldName = â˜ƒ.getLevelIdName();
      this.lastRulesResponse = 0L;
      this.hostIp = "0.0.0.0";
      if (!this.serverIp.isEmpty() && !this.hostIp.equals(this.serverIp)) {
         this.hostIp = this.serverIp;
      } else {
         this.serverIp = "0.0.0.0";

         try {
            InetAddress â˜ƒ = InetAddress.getLocalHost();
            this.hostIp = â˜ƒ.getHostAddress();
         } catch (UnknownHostException var4) {
            LOGGER.warn("Unable to determine local host IP, please set server-ip in server.properties", var4);
         }
      }

      this.rulesResponse = new NetworkDataOutputStream(1460);
      this.validChallenges = Maps.newHashMap();
   }

   @Nullable
   public static QueryThreadGs4 create(ServerInterface var0) {
      int â˜ƒ = â˜ƒ.getProperties().queryPort;
      if (0 < â˜ƒ && 65535 >= â˜ƒ) {
         QueryThreadGs4 â˜ƒx = new QueryThreadGs4(â˜ƒ, â˜ƒ);
         return !â˜ƒx.start() ? null : â˜ƒx;
      } else {
         LOGGER.warn("Invalid query port {} found in server.properties (queries disabled)", â˜ƒ);
         return null;
      }
   }

   private void sendTo(byte[] var1, DatagramPacket var2) throws IOException {
      this.socket.send(new DatagramPacket(â˜ƒ, â˜ƒ.length, â˜ƒ.getSocketAddress()));
   }

   private boolean processPacket(DatagramPacket var1) throws IOException {
      byte[] â˜ƒ = â˜ƒ.getData();
      int â˜ƒx = â˜ƒ.getLength();
      SocketAddress â˜ƒxx = â˜ƒ.getSocketAddress();
      LOGGER.debug("Packet len {} [{}]", â˜ƒx, â˜ƒxx);
      if (3 <= â˜ƒx && -2 == â˜ƒ[0] && -3 == â˜ƒ[1]) {
         LOGGER.debug("Packet '{}' [{}]", PktUtils.toHexString(â˜ƒ[2]), â˜ƒxx);
         switch(â˜ƒ[2]) {
            case 0:
               if (!this.validChallenge(â˜ƒ)) {
                  LOGGER.debug("Invalid challenge [{}]", â˜ƒxx);
                  return false;
               } else if (15 == â˜ƒx) {
                  this.sendTo(this.buildRuleResponse(â˜ƒ), â˜ƒ);
                  LOGGER.debug("Rules [{}]", â˜ƒxx);
               } else {
                  NetworkDataOutputStream â˜ƒxxx = new NetworkDataOutputStream(1460);
                  â˜ƒxxx.write(0);
                  â˜ƒxxx.writeBytes(this.getIdentBytes(â˜ƒ.getSocketAddress()));
                  â˜ƒxxx.writeString(this.serverName);
                  â˜ƒxxx.writeString("SMP");
                  â˜ƒxxx.writeString(this.worldName);
                  â˜ƒxxx.writeString(Integer.toString(this.serverInterface.getPlayerCount()));
                  â˜ƒxxx.writeString(Integer.toString(this.maxPlayers));
                  â˜ƒxxx.writeShort((short)this.serverPort);
                  â˜ƒxxx.writeString(this.hostIp);
                  this.sendTo(â˜ƒxxx.toByteArray(), â˜ƒ);
                  LOGGER.debug("Status [{}]", â˜ƒxx);
               }
            default:
               return true;
            case 9:
               this.sendChallenge(â˜ƒ);
               LOGGER.debug("Challenge [{}]", â˜ƒxx);
               return true;
         }
      } else {
         LOGGER.debug("Invalid packet [{}]", â˜ƒxx);
         return false;
      }
   }

   private byte[] buildRuleResponse(DatagramPacket var1) throws IOException {
      long â˜ƒ = Util.getMillis();
      if (â˜ƒ < this.lastRulesResponse + 5000L) {
         byte[] â˜ƒx = this.rulesResponse.toByteArray();
         byte[] â˜ƒxx = this.getIdentBytes(â˜ƒ.getSocketAddress());
         â˜ƒx[1] = â˜ƒxx[0];
         â˜ƒx[2] = â˜ƒxx[1];
         â˜ƒx[3] = â˜ƒxx[2];
         â˜ƒx[4] = â˜ƒxx[3];
         return â˜ƒx;
      } else {
         this.lastRulesResponse = â˜ƒ;
         this.rulesResponse.reset();
         this.rulesResponse.write(0);
         this.rulesResponse.writeBytes(this.getIdentBytes(â˜ƒ.getSocketAddress()));
         this.rulesResponse.writeString("splitnum");
         this.rulesResponse.write(128);
         this.rulesResponse.write(0);
         this.rulesResponse.writeString("hostname");
         this.rulesResponse.writeString(this.serverName);
         this.rulesResponse.writeString("gametype");
         this.rulesResponse.writeString("SMP");
         this.rulesResponse.writeString("game_id");
         this.rulesResponse.writeString("MINECRAFT");
         this.rulesResponse.writeString("version");
         this.rulesResponse.writeString(this.serverInterface.getServerVersion());
         this.rulesResponse.writeString("plugins");
         this.rulesResponse.writeString(this.serverInterface.getPluginNames());
         this.rulesResponse.writeString("map");
         this.rulesResponse.writeString(this.worldName);
         this.rulesResponse.writeString("numplayers");
         this.rulesResponse.writeString(this.serverInterface.getPlayerCount() + "");
         this.rulesResponse.writeString("maxplayers");
         this.rulesResponse.writeString(this.maxPlayers + "");
         this.rulesResponse.writeString("hostport");
         this.rulesResponse.writeString(this.serverPort + "");
         this.rulesResponse.writeString("hostip");
         this.rulesResponse.writeString(this.hostIp);
         this.rulesResponse.write(0);
         this.rulesResponse.write(1);
         this.rulesResponse.writeString("player_");
         this.rulesResponse.write(0);
         String[] â˜ƒ = this.serverInterface.getPlayerNames();

         for(String â˜ƒx : â˜ƒ) {
            this.rulesResponse.writeString(â˜ƒx);
         }

         this.rulesResponse.write(0);
         return this.rulesResponse.toByteArray();
      }
   }

   private byte[] getIdentBytes(SocketAddress var1) {
      return ((QueryThreadGs4.RequestChallenge)this.validChallenges.get(â˜ƒ)).getIdentBytes();
   }

   private Boolean validChallenge(DatagramPacket var1) {
      SocketAddress â˜ƒ = â˜ƒ.getSocketAddress();
      if (!this.validChallenges.containsKey(â˜ƒ)) {
         return false;
      } else {
         byte[] â˜ƒ = â˜ƒ.getData();
         return ((QueryThreadGs4.RequestChallenge)this.validChallenges.get(â˜ƒ)).getChallenge() == PktUtils.intFromNetworkByteArray(â˜ƒ, 7, â˜ƒ.getLength());
      }
   }

   private void sendChallenge(DatagramPacket var1) throws IOException {
      QueryThreadGs4.RequestChallenge â˜ƒ = new QueryThreadGs4.RequestChallenge(â˜ƒ);
      this.validChallenges.put(â˜ƒ.getSocketAddress(), â˜ƒ);
      this.sendTo(â˜ƒ.getChallengeBytes(), â˜ƒ);
   }

   private void pruneChallenges() {
      if (this.running) {
         long â˜ƒ = Util.getMillis();
         if (â˜ƒ >= this.lastChallengeCheck + 30000L) {
            this.lastChallengeCheck = â˜ƒ;
            this.validChallenges.values().removeIf(var2 -> var2.before(â˜ƒ));
         }
      }
   }

   public void run() {
      LOGGER.info("Query running on {}:{}", this.serverIp, this.port);
      this.lastChallengeCheck = Util.getMillis();
      DatagramPacket â˜ƒ = new DatagramPacket(this.buffer, this.buffer.length);

      try {
         while(this.running) {
            try {
               this.socket.receive(â˜ƒ);
               this.pruneChallenges();
               this.processPacket(â˜ƒ);
            } catch (SocketTimeoutException var8) {
               this.pruneChallenges();
            } catch (PortUnreachableException var9) {
            } catch (IOException var10) {
               this.recoverSocketError(var10);
            }
         }
      } finally {
         LOGGER.debug("closeSocket: {}:{}", this.serverIp, this.port);
         this.socket.close();
      }
   }

   @Override
   public boolean start() {
      if (this.running) {
         return true;
      } else {
         return !this.initSocket() ? false : super.start();
      }
   }

   private void recoverSocketError(Exception var1) {
      if (this.running) {
         LOGGER.warn("Unexpected exception", â˜ƒ);
         if (!this.initSocket()) {
            LOGGER.error("Failed to recover from exception, shutting down!");
            this.running = false;
         }
      }
   }

   private boolean initSocket() {
      try {
         this.socket = new DatagramSocket(this.port, InetAddress.getByName(this.serverIp));
         this.socket.setSoTimeout(500);
         return true;
      } catch (Exception var2) {
         LOGGER.warn("Unable to initialise query system on {}:{}", this.serverIp, this.port, var2);
         return false;
      }
   }

   static class RequestChallenge {
      private final long time = new Date().getTime();
      private final int challenge;
      private final byte[] identBytes;
      private final byte[] challengeBytes;
      private final String ident;

      public RequestChallenge(DatagramPacket var1) {
         byte[] â˜ƒ = â˜ƒ.getData();
         this.identBytes = new byte[4];
         this.identBytes[0] = â˜ƒ[3];
         this.identBytes[1] = â˜ƒ[4];
         this.identBytes[2] = â˜ƒ[5];
         this.identBytes[3] = â˜ƒ[6];
         this.ident = new String(this.identBytes, StandardCharsets.UTF_8);
         this.challenge = new Random().nextInt(16777216);
         this.challengeBytes = String.format("\t%s%d\u0000", this.ident, this.challenge).getBytes(StandardCharsets.UTF_8);
      }

      public Boolean before(long var1) {
         return this.time < â˜ƒ;
      }

      public int getChallenge() {
         return this.challenge;
      }

      public byte[] getChallengeBytes() {
         return this.challengeBytes;
      }

      public byte[] getIdentBytes() {
         return this.identBytes;
      }

      public String getIdent() {
         return this.ident;
      }
   }
}
