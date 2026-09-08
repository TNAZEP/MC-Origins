package net.minecraft.client.server;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.storage.PlayerDataStorage;

public class IntegratedPlayerList extends PlayerList {
   private CompoundTag playerData;

   public IntegratedPlayerList(IntegratedServer var1, RegistryAccess.RegistryHolder var2, PlayerDataStorage var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, 8);
      this.setViewDistance(10);
   }

   @Override
   protected void save(ServerPlayer var1) {
      if (â˜ƒ.getName().getString().equals(this.getServer().getSingleplayerName())) {
         this.playerData = â˜ƒ.saveWithoutId(new CompoundTag());
      }

      super.save(â˜ƒ);
   }

   @Override
   public Component canPlayerLogin(SocketAddress var1, GameProfile var2) {
      return (Component)(â˜ƒ.getName().equalsIgnoreCase(this.getServer().getSingleplayerName()) && this.getPlayerByName(â˜ƒ.getName()) != null
         ? new TranslatableComponent("multiplayer.disconnect.name_taken")
         : super.canPlayerLogin(â˜ƒ, â˜ƒ));
   }

   public IntegratedServer getServer() {
      return (IntegratedServer)super.getServer();
   }

   @Override
   public CompoundTag getSingleplayerData() {
      return this.playerData;
   }
}
