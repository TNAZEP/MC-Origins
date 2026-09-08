package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class ServerboundPlaceRecipePacket implements Packet<ServerGamePacketListener> {
   private final int containerId;
   private final ResourceLocation recipe;
   private final boolean shiftDown;

   public ServerboundPlaceRecipePacket(int var1, Recipe<?> var2, boolean var3) {
      this.containerId = â˜ƒ;
      this.recipe = â˜ƒ.getId();
      this.shiftDown = â˜ƒ;
   }

   public ServerboundPlaceRecipePacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readByte();
      this.recipe = â˜ƒ.readResourceLocation();
      this.shiftDown = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
      â˜ƒ.writeResourceLocation(this.recipe);
      â˜ƒ.writeBoolean(this.shiftDown);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handlePlaceRecipe(this);
   }

   public int getContainerId() {
      return this.containerId;
   }

   public ResourceLocation getRecipe() {
      return this.recipe;
   }

   public boolean isShiftDown() {
      return this.shiftDown;
   }
}
