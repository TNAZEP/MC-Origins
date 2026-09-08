package net.minecraft.client.multiplayer;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ServerData {
   public String name;
   public String ip;
   public Component status;
   public Component motd;
   public long ping;
   public int protocol = SharedConstants.getCurrentVersion().getProtocolVersion();
   public Component version = new TextComponent(SharedConstants.getCurrentVersion().getName());
   public boolean pinged;
   public List<Component> playerList = Collections.emptyList();
   private ServerData.ServerPackStatus packStatus = ServerData.ServerPackStatus.PROMPT;
   @Nullable
   private String iconB64;
   private boolean lan;

   public ServerData(String var1, String var2, boolean var3) {
      this.name = â˜ƒ;
      this.ip = â˜ƒ;
      this.lan = â˜ƒ;
   }

   public CompoundTag write() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("name", this.name);
      â˜ƒ.putString("ip", this.ip);
      if (this.iconB64 != null) {
         â˜ƒ.putString("icon", this.iconB64);
      }

      if (this.packStatus == ServerData.ServerPackStatus.ENABLED) {
         â˜ƒ.putBoolean("acceptTextures", true);
      } else if (this.packStatus == ServerData.ServerPackStatus.DISABLED) {
         â˜ƒ.putBoolean("acceptTextures", false);
      }

      return â˜ƒ;
   }

   public ServerData.ServerPackStatus getResourcePackStatus() {
      return this.packStatus;
   }

   public void setResourcePackStatus(ServerData.ServerPackStatus var1) {
      this.packStatus = â˜ƒ;
   }

   public static ServerData read(CompoundTag var0) {
      ServerData â˜ƒ = new ServerData(â˜ƒ.getString("name"), â˜ƒ.getString("ip"), false);
      if (â˜ƒ.contains("icon", 8)) {
         â˜ƒ.setIconB64(â˜ƒ.getString("icon"));
      }

      if (â˜ƒ.contains("acceptTextures", 1)) {
         if (â˜ƒ.getBoolean("acceptTextures")) {
            â˜ƒ.setResourcePackStatus(ServerData.ServerPackStatus.ENABLED);
         } else {
            â˜ƒ.setResourcePackStatus(ServerData.ServerPackStatus.DISABLED);
         }
      } else {
         â˜ƒ.setResourcePackStatus(ServerData.ServerPackStatus.PROMPT);
      }

      return â˜ƒ;
   }

   @Nullable
   public String getIconB64() {
      return this.iconB64;
   }

   public void setIconB64(@Nullable String var1) {
      this.iconB64 = â˜ƒ;
   }

   public boolean isLan() {
      return this.lan;
   }

   public void copyFrom(ServerData var1) {
      this.ip = â˜ƒ.ip;
      this.name = â˜ƒ.name;
      this.setResourcePackStatus(â˜ƒ.getResourcePackStatus());
      this.iconB64 = â˜ƒ.iconB64;
      this.lan = â˜ƒ.lan;
   }

   public static enum ServerPackStatus {
      ENABLED("enabled"),
      DISABLED("disabled"),
      PROMPT("prompt");

      private final Component name;

      private ServerPackStatus(String var3) {
         this.name = new TranslatableComponent("addServer.resourcePack." + â˜ƒ);
      }

      public Component getName() {
         return this.name;
      }
   }
}
