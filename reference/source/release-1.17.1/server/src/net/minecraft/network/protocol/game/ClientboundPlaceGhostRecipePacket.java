package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class ClientboundPlaceGhostRecipePacket implements Packet<ClientGamePacketListener> {
   private final int containerId;
   private final ResourceLocation recipe;

   public ClientboundPlaceGhostRecipePacket(int var1, Recipe<?> var2) {
      this.containerId = â˜ƒ;
      this.recipe = â˜ƒ.getId();
   }

   public ClientboundPlaceGhostRecipePacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readByte();
      this.recipe = â˜ƒ.readResourceLocation();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
      â˜ƒ.writeResourceLocation(this.recipe);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handlePlaceRecipe(this);
   }

   public ResourceLocation getRecipe() {
      return this.recipe;
   }

   public int getContainerId() {
      return this.containerId;
   }
}
