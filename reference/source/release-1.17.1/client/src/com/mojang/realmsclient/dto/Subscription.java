package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Subscription extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public long startDate;
   public int daysLeft;
   public Subscription.SubscriptionType type = Subscription.SubscriptionType.NORMAL;

   public static Subscription parse(String var0) {
      Subscription â˜ƒ = new Subscription();

      try {
         JsonParser â˜ƒx = new JsonParser();
         JsonObject â˜ƒxx = â˜ƒx.parse(â˜ƒ).getAsJsonObject();
         â˜ƒ.startDate = JsonUtils.getLongOr("startDate", â˜ƒxx, 0L);
         â˜ƒ.daysLeft = JsonUtils.getIntOr("daysLeft", â˜ƒxx, 0);
         â˜ƒ.type = typeFrom(JsonUtils.getStringOr("subscriptionType", â˜ƒxx, Subscription.SubscriptionType.NORMAL.name()));
      } catch (Exception var4) {
         LOGGER.error("Could not parse Subscription: {}", var4.getMessage());
      }

      return â˜ƒ;
   }

   private static Subscription.SubscriptionType typeFrom(String var0) {
      try {
         return Subscription.SubscriptionType.valueOf(â˜ƒ);
      } catch (Exception var2) {
         return Subscription.SubscriptionType.NORMAL;
      }
   }

   public static enum SubscriptionType {
      NORMAL,
      RECURRING;
   }
}
