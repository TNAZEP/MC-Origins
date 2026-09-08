package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

public class ServerboundRecipeBookSeenRecipePacket implements Packet<ServerGamePacketListener> {
   private final ResourceLocation recipe;

   public ServerboundRecipeBookSeenRecipePacket(Recipe<?> var1) {
      this.recipe = â˜ƒ.getId();
   }

   public ServerboundRecipeBookSeenRecipePacket(FriendlyByteBuf var1) {
      this.recipe = â˜ƒ.readResourceLocation();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeResourceLocation(this.recipe);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleRecipeBookSeenRecipePacket(this);
   }

   public ResourceLocation getRecipe() {
      return this.recipe;
   }
}
