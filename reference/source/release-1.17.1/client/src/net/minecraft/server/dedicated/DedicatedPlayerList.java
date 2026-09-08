package net.minecraft.server.dedicated;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.PlayerDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedPlayerList extends PlayerList {
   private static final Logger LOGGER = LogManager.getLogger();

   public DedicatedPlayerList(DedicatedServer var1, RegistryAccess.RegistryHolder var2, PlayerDataStorage var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getProperties().maxPlayers);
      DedicatedServerProperties â˜ƒ = â˜ƒ.getProperties();
      this.setViewDistance(â˜ƒ.viewDistance);
      super.setUsingWhiteList(â˜ƒ.whiteList.get());
      this.loadUserBanList();
      this.saveUserBanList();
      this.loadIpBanList();
      this.saveIpBanList();
      this.loadOps();
      this.loadWhiteList();
      this.saveOps();
      if (!this.getWhiteList().getFile().exists()) {
         this.saveWhiteList();
      }
   }

   @Override
   public void setUsingWhiteList(boolean var1) {
      super.setUsingWhiteList(â˜ƒ);
      this.getServer().storeUsingWhiteList(â˜ƒ);
   }

   @Override
   public void op(GameProfile var1) {
      super.op(â˜ƒ);
      this.saveOps();
   }

   @Override
   public void deop(GameProfile var1) {
      super.deop(â˜ƒ);
      this.saveOps();
   }

   @Override
   public void reloadWhiteList() {
      this.loadWhiteList();
   }

   private void saveIpBanList() {
      try {
         this.getIpBans().save();
      } catch (IOException var2) {
         LOGGER.warn("Failed to save ip banlist: ", var2);
      }
   }

   private void saveUserBanList() {
      try {
         this.getBans().save();
      } catch (IOException var2) {
         LOGGER.warn("Failed to save user banlist: ", var2);
      }
   }

   private void loadIpBanList() {
      try {
         this.getIpBans().load();
      } catch (IOException var2) {
         LOGGER.warn("Failed to load ip banlist: ", var2);
      }
   }

   private void loadUserBanList() {
      try {
         this.getBans().load();
      } catch (IOException var2) {
         LOGGER.warn("Failed to load user banlist: ", var2);
      }
   }

   private void loadOps() {
      try {
         this.getOps().load();
      } catch (Exception var2) {
         LOGGER.warn("Failed to load operators list: ", var2);
      }
   }

   private void saveOps() {
      try {
         this.getOps().save();
      } catch (Exception var2) {
         LOGGER.warn("Failed to save operators list: ", var2);
      }
   }

   private void loadWhiteList() {
      try {
         this.getWhiteList().load();
      } catch (Exception var2) {
         LOGGER.warn("Failed to load white-list: ", var2);
      }
   }

   private void saveWhiteList() {
      try {
         this.getWhiteList().save();
      } catch (Exception var2) {
         LOGGER.warn("Failed to save white-list: ", var2);
      }
   }

   @Override
   public boolean isWhiteListed(GameProfile var1) {
      return !this.isUsingWhitelist() || this.isOp(â˜ƒ) || this.getWhiteList().isWhiteListed(â˜ƒ);
   }

   public DedicatedServer getServer() {
      return (DedicatedServer)super.getServer();
   }

   @Override
   public boolean canBypassPlayerLimit(GameProfile var1) {
      return this.getOps().canBypassPlayerLimit(â˜ƒ);
   }
}
