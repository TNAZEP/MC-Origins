package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import com.mojang.blaze3d.platform.DisplayData;
import java.io.File;
import java.net.Proxy;
import javax.annotation.Nullable;
import net.minecraft.client.User;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.client.resources.DirectAssetIndex;

public class GameConfig {
   public final GameConfig.UserData user;
   public final DisplayData display;
   public final GameConfig.FolderData location;
   public final GameConfig.GameData game;
   public final GameConfig.ServerData server;

   public GameConfig(GameConfig.UserData var1, DisplayData var2, GameConfig.FolderData var3, GameConfig.GameData var4, GameConfig.ServerData var5) {
      this.user = â˜ƒ;
      this.display = â˜ƒ;
      this.location = â˜ƒ;
      this.game = â˜ƒ;
      this.server = â˜ƒ;
   }

   public static class FolderData {
      public final File gameDirectory;
      public final File resourcePackDirectory;
      public final File assetDirectory;
      @Nullable
      public final String assetIndex;

      public FolderData(File var1, File var2, File var3, @Nullable String var4) {
         this.gameDirectory = â˜ƒ;
         this.resourcePackDirectory = â˜ƒ;
         this.assetDirectory = â˜ƒ;
         this.assetIndex = â˜ƒ;
      }

      public AssetIndex getAssetIndex() {
         return (AssetIndex)(this.assetIndex == null ? new DirectAssetIndex(this.assetDirectory) : new AssetIndex(this.assetDirectory, this.assetIndex));
      }
   }

   public static class GameData {
      public final boolean demo;
      public final String launchVersion;
      public final String versionType;
      public final boolean disableMultiplayer;
      public final boolean disableChat;

      public GameData(boolean var1, String var2, String var3, boolean var4, boolean var5) {
         this.demo = â˜ƒ;
         this.launchVersion = â˜ƒ;
         this.versionType = â˜ƒ;
         this.disableMultiplayer = â˜ƒ;
         this.disableChat = â˜ƒ;
      }
   }

   public static class ServerData {
      @Nullable
      public final String hostname;
      public final int port;

      public ServerData(@Nullable String var1, int var2) {
         this.hostname = â˜ƒ;
         this.port = â˜ƒ;
      }
   }

   public static class UserData {
      public final User user;
      public final PropertyMap userProperties;
      public final PropertyMap profileProperties;
      public final Proxy proxy;

      public UserData(User var1, PropertyMap var2, PropertyMap var3, Proxy var4) {
         this.user = â˜ƒ;
         this.userProperties = â˜ƒ;
         this.profileProperties = â˜ƒ;
         this.proxy = â˜ƒ;
      }
   }
}
