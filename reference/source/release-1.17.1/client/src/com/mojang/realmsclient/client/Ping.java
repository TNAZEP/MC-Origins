package com.mojang.realmsclient.client;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.dto.RegionPingResult;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Comparator;
import java.util.List;
import net.minecraft.Util;

public class Ping {
   public static List<RegionPingResult> ping(Ping.Region... var0) {
      for(Ping.Region â˜ƒ : â˜ƒ) {
         ping(â˜ƒ.endpoint);
      }

      List<RegionPingResult> â˜ƒ = Lists.<RegionPingResult>newArrayList();

      for(Ping.Region â˜ƒx : â˜ƒ) {
         â˜ƒ.add(new RegionPingResult(â˜ƒx.name, ping(â˜ƒx.endpoint)));
      }

      â˜ƒ.sort(Comparator.comparingInt(RegionPingResult::ping));
      return â˜ƒ;
   }

   private static int ping(String var0) {
      int â˜ƒ = 700;
      long â˜ƒx = 0L;
      Socket â˜ƒxx = null;

      for(int â˜ƒxxx = 0; â˜ƒxxx < 5; ++â˜ƒxxx) {
         try {
            SocketAddress â˜ƒxxxx = new InetSocketAddress(â˜ƒ, 80);
            â˜ƒxx = new Socket();
            long â˜ƒxxxxx = now();
            â˜ƒxx.connect(â˜ƒxxxx, 700);
            â˜ƒx += now() - â˜ƒxxxxx;
         } catch (Exception var12) {
            â˜ƒx += 700L;
         } finally {
            close(â˜ƒxx);
         }
      }

      return (int)((double)â˜ƒx / 5.0);
   }

   private static void close(Socket var0) {
      try {
         if (â˜ƒ != null) {
            â˜ƒ.close();
         }
      } catch (Throwable var2) {
      }
   }

   private static long now() {
      return Util.getMillis();
   }

   public static List<RegionPingResult> pingAllRegions() {
      return ping(Ping.Region.values());
   }

   static enum Region {
      US_EAST_1("us-east-1", "ec2.us-east-1.amazonaws.com"),
      US_WEST_2("us-west-2", "ec2.us-west-2.amazonaws.com"),
      US_WEST_1("us-west-1", "ec2.us-west-1.amazonaws.com"),
      EU_WEST_1("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
      AP_SOUTHEAST_1("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
      AP_SOUTHEAST_2("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
      AP_NORTHEAST_1("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
      SA_EAST_1("sa-east-1", "ec2.sa-east-1.amazonaws.com");

      final String name;
      final String endpoint;

      private Region(String var3, String var4) {
         this.name = â˜ƒ;
         this.endpoint = â˜ƒ;
      }
   }
}
