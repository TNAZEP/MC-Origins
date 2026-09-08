package com.mojang.realmsclient.dto;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServer extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public long id;
   public String remoteSubscriptionId;
   public String name;
   public String motd;
   public RealmsServer.State state;
   public String owner;
   public String ownerUUID;
   public List<PlayerInfo> players;
   public Map<Integer, RealmsWorldOptions> slots;
   public boolean expired;
   public boolean expiredTrial;
   public int daysLeft;
   public RealmsServer.WorldType worldType;
   public int activeSlot;
   public String minigameName;
   public int minigameId;
   public String minigameImage;
   public RealmsServerPing serverPing = new RealmsServerPing();

   public String getDescription() {
      return this.motd;
   }

   public String getName() {
      return this.name;
   }

   public String getMinigameName() {
      return this.minigameName;
   }

   public void setName(String var1) {
      this.name = â˜ƒ;
   }

   public void setDescription(String var1) {
      this.motd = â˜ƒ;
   }

   public void updateServerPing(RealmsServerPlayerList var1) {
      List<String> â˜ƒ = Lists.newArrayList();
      int â˜ƒx = 0;

      for(String â˜ƒxx : â˜ƒ.players) {
         if (!â˜ƒxx.equals(Minecraft.getInstance().getUser().getUuid())) {
            String â˜ƒxxx = "";

            try {
               â˜ƒxxx = RealmsUtil.uuidToName(â˜ƒxx);
            } catch (Exception var8) {
               LOGGER.error("Could not get name for {}", â˜ƒxx, var8);
               continue;
            }

            â˜ƒ.add(â˜ƒxxx);
            ++â˜ƒx;
         }
      }

      this.serverPing.nrOfPlayers = String.valueOf(â˜ƒx);
      this.serverPing.playerList = Joiner.on('\n').join(â˜ƒ);
   }

   public static RealmsServer parse(JsonObject var0) {
      RealmsServer â˜ƒ = new RealmsServer();

      try {
         â˜ƒ.id = JsonUtils.getLongOr("id", â˜ƒ, -1L);
         â˜ƒ.remoteSubscriptionId = JsonUtils.getStringOr("remoteSubscriptionId", â˜ƒ, null);
         â˜ƒ.name = JsonUtils.getStringOr("name", â˜ƒ, null);
         â˜ƒ.motd = JsonUtils.getStringOr("motd", â˜ƒ, null);
         â˜ƒ.state = getState(JsonUtils.getStringOr("state", â˜ƒ, RealmsServer.State.CLOSED.name()));
         â˜ƒ.owner = JsonUtils.getStringOr("owner", â˜ƒ, null);
         if (â˜ƒ.get("players") != null && â˜ƒ.get("players").isJsonArray()) {
            â˜ƒ.players = parseInvited(â˜ƒ.get("players").getAsJsonArray());
            sortInvited(â˜ƒ);
         } else {
            â˜ƒ.players = Lists.<PlayerInfo>newArrayList();
         }

         â˜ƒ.daysLeft = JsonUtils.getIntOr("daysLeft", â˜ƒ, 0);
         â˜ƒ.expired = JsonUtils.getBooleanOr("expired", â˜ƒ, false);
         â˜ƒ.expiredTrial = JsonUtils.getBooleanOr("expiredTrial", â˜ƒ, false);
         â˜ƒ.worldType = getWorldType(JsonUtils.getStringOr("worldType", â˜ƒ, RealmsServer.WorldType.NORMAL.name()));
         â˜ƒ.ownerUUID = JsonUtils.getStringOr("ownerUUID", â˜ƒ, "");
         if (â˜ƒ.get("slots") != null && â˜ƒ.get("slots").isJsonArray()) {
            â˜ƒ.slots = parseSlots(â˜ƒ.get("slots").getAsJsonArray());
         } else {
            â˜ƒ.slots = createEmptySlots();
         }

         â˜ƒ.minigameName = JsonUtils.getStringOr("minigameName", â˜ƒ, null);
         â˜ƒ.activeSlot = JsonUtils.getIntOr("activeSlot", â˜ƒ, -1);
         â˜ƒ.minigameId = JsonUtils.getIntOr("minigameId", â˜ƒ, -1);
         â˜ƒ.minigameImage = JsonUtils.getStringOr("minigameImage", â˜ƒ, null);
      } catch (Exception var3) {
         LOGGER.error("Could not parse McoServer: {}", var3.getMessage());
      }

      return â˜ƒ;
   }

   private static void sortInvited(RealmsServer var0) {
      â˜ƒ.players
         .sort(
            (var0x, var1) -> ComparisonChain.start()
                  .compareFalseFirst(var1.getAccepted(), var0x.getAccepted())
                  .compare(var0x.getName().toLowerCase(Locale.ROOT), var1.getName().toLowerCase(Locale.ROOT))
                  .result()
         );
   }

   private static List<PlayerInfo> parseInvited(JsonArray var0) {
      List<PlayerInfo> â˜ƒ = Lists.<PlayerInfo>newArrayList();

      for(JsonElement â˜ƒx : â˜ƒ) {
         try {
            JsonObject â˜ƒxx = â˜ƒx.getAsJsonObject();
            PlayerInfo â˜ƒxxx = new PlayerInfo();
            â˜ƒxxx.setName(JsonUtils.getStringOr("name", â˜ƒxx, null));
            â˜ƒxxx.setUuid(JsonUtils.getStringOr("uuid", â˜ƒxx, null));
            â˜ƒxxx.setOperator(JsonUtils.getBooleanOr("operator", â˜ƒxx, false));
            â˜ƒxxx.setAccepted(JsonUtils.getBooleanOr("accepted", â˜ƒxx, false));
            â˜ƒxxx.setOnline(JsonUtils.getBooleanOr("online", â˜ƒxx, false));
            â˜ƒ.add(â˜ƒxxx);
         } catch (Exception var6) {
         }
      }

      return â˜ƒ;
   }

   private static Map<Integer, RealmsWorldOptions> parseSlots(JsonArray var0) {
      Map<Integer, RealmsWorldOptions> â˜ƒ = Maps.newHashMap();

      for(JsonElement â˜ƒx : â˜ƒ) {
         try {
            JsonObject â˜ƒxxx = â˜ƒx.getAsJsonObject();
            JsonParser â˜ƒxxxx = new JsonParser();
            JsonElement â˜ƒxxxxx = â˜ƒxxxx.parse(â˜ƒxxx.get("options").getAsString());
            RealmsWorldOptions â˜ƒxx;
            if (â˜ƒxxxxx == null) {
               â˜ƒxx = RealmsWorldOptions.createDefaults();
            } else {
               â˜ƒxx = RealmsWorldOptions.parse(â˜ƒxxxxx.getAsJsonObject());
            }

            int â˜ƒxx = JsonUtils.getIntOr("slotId", â˜ƒxxx, -1);
            â˜ƒ.put(â˜ƒxx, â˜ƒxx);
         } catch (Exception var9) {
         }
      }

      for(int â˜ƒx = 1; â˜ƒx <= 3; ++â˜ƒx) {
         if (!â˜ƒ.containsKey(â˜ƒx)) {
            â˜ƒ.put(â˜ƒx, RealmsWorldOptions.createEmptyDefaults());
         }
      }

      return â˜ƒ;
   }

   private static Map<Integer, RealmsWorldOptions> createEmptySlots() {
      Map<Integer, RealmsWorldOptions> â˜ƒ = Maps.newHashMap();
      â˜ƒ.put(1, RealmsWorldOptions.createEmptyDefaults());
      â˜ƒ.put(2, RealmsWorldOptions.createEmptyDefaults());
      â˜ƒ.put(3, RealmsWorldOptions.createEmptyDefaults());
      return â˜ƒ;
   }

   public static RealmsServer parse(String var0) {
      try {
         return parse(new JsonParser().parse(â˜ƒ).getAsJsonObject());
      } catch (Exception var2) {
         LOGGER.error("Could not parse McoServer: {}", var2.getMessage());
         return new RealmsServer();
      }
   }

   private static RealmsServer.State getState(String var0) {
      try {
         return RealmsServer.State.valueOf(â˜ƒ);
      } catch (Exception var2) {
         return RealmsServer.State.CLOSED;
      }
   }

   private static RealmsServer.WorldType getWorldType(String var0) {
      try {
         return RealmsServer.WorldType.valueOf(â˜ƒ);
      } catch (Exception var2) {
         return RealmsServer.WorldType.NORMAL;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.id, this.name, this.motd, this.state, this.owner, this.expired});
   }

   public boolean equals(Object var1) {
      if (â˜ƒ == null) {
         return false;
      } else if (â˜ƒ == this) {
         return true;
      } else if (â˜ƒ.getClass() != this.getClass()) {
         return false;
      } else {
         RealmsServer â˜ƒ = (RealmsServer)â˜ƒ;
         return new EqualsBuilder()
            .append(this.id, â˜ƒ.id)
            .append(this.name, â˜ƒ.name)
            .append(this.motd, â˜ƒ.motd)
            .append(this.state, â˜ƒ.state)
            .append(this.owner, â˜ƒ.owner)
            .append(this.expired, â˜ƒ.expired)
            .append(this.worldType, this.worldType)
            .isEquals();
      }
   }

   public RealmsServer clone() {
      RealmsServer â˜ƒ = new RealmsServer();
      â˜ƒ.id = this.id;
      â˜ƒ.remoteSubscriptionId = this.remoteSubscriptionId;
      â˜ƒ.name = this.name;
      â˜ƒ.motd = this.motd;
      â˜ƒ.state = this.state;
      â˜ƒ.owner = this.owner;
      â˜ƒ.players = this.players;
      â˜ƒ.slots = this.cloneSlots(this.slots);
      â˜ƒ.expired = this.expired;
      â˜ƒ.expiredTrial = this.expiredTrial;
      â˜ƒ.daysLeft = this.daysLeft;
      â˜ƒ.serverPing = new RealmsServerPing();
      â˜ƒ.serverPing.nrOfPlayers = this.serverPing.nrOfPlayers;
      â˜ƒ.serverPing.playerList = this.serverPing.playerList;
      â˜ƒ.worldType = this.worldType;
      â˜ƒ.ownerUUID = this.ownerUUID;
      â˜ƒ.minigameName = this.minigameName;
      â˜ƒ.activeSlot = this.activeSlot;
      â˜ƒ.minigameId = this.minigameId;
      â˜ƒ.minigameImage = this.minigameImage;
      return â˜ƒ;
   }

   public Map<Integer, RealmsWorldOptions> cloneSlots(Map<Integer, RealmsWorldOptions> var1) {
      Map<Integer, RealmsWorldOptions> â˜ƒ = Maps.newHashMap();

      for(Entry<Integer, RealmsWorldOptions> â˜ƒx : â˜ƒ.entrySet()) {
         â˜ƒ.put((Integer)â˜ƒx.getKey(), ((RealmsWorldOptions)â˜ƒx.getValue()).clone());
      }

      return â˜ƒ;
   }

   public String getWorldName(int var1) {
      return this.name + " (" + ((RealmsWorldOptions)this.slots.get(â˜ƒ)).getSlotName(â˜ƒ) + ")";
   }

   public ServerData toServerData(String var1) {
      return new ServerData(this.name, â˜ƒ, false);
   }

   public static class McoServerComparator implements Comparator<RealmsServer> {
      private final String refOwner;

      public McoServerComparator(String var1) {
         this.refOwner = â˜ƒ;
      }

      public int compare(RealmsServer var1, RealmsServer var2) {
         return ComparisonChain.start()
            .compareTrueFirst(â˜ƒ.state == RealmsServer.State.UNINITIALIZED, â˜ƒ.state == RealmsServer.State.UNINITIALIZED)
            .compareTrueFirst(â˜ƒ.expiredTrial, â˜ƒ.expiredTrial)
            .compareTrueFirst(â˜ƒ.owner.equals(this.refOwner), â˜ƒ.owner.equals(this.refOwner))
            .compareFalseFirst(â˜ƒ.expired, â˜ƒ.expired)
            .compareTrueFirst(â˜ƒ.state == RealmsServer.State.OPEN, â˜ƒ.state == RealmsServer.State.OPEN)
            .compare(â˜ƒ.id, â˜ƒ.id)
            .result();
      }
   }

   public static enum State {
      CLOSED,
      OPEN,
      UNINITIALIZED;
   }

   public static enum WorldType {
      NORMAL,
      MINIGAME,
      ADVENTUREMAP,
      EXPERIENCE,
      INSPIRATION;
   }
}
