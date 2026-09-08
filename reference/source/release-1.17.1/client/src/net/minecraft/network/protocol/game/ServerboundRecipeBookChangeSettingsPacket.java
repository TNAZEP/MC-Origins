package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.inventory.RecipeBookType;

public class ServerboundRecipeBookChangeSettingsPacket implements Packet<ServerGamePacketListener> {
   private final RecipeBookType bookType;
   private final boolean isOpen;
   private final boolean isFiltering;

   public ServerboundRecipeBookChangeSettingsPacket(RecipeBookType var1, boolean var2, boolean var3) {
      this.bookType = â˜ƒ;
      this.isOpen = â˜ƒ;
      this.isFiltering = â˜ƒ;
   }

   public ServerboundRecipeBookChangeSettingsPacket(FriendlyByteBuf var1) {
      this.bookType = â˜ƒ.readEnum(RecipeBookType.class);
      this.isOpen = â˜ƒ.readBoolean();
      this.isFiltering = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeEnum(this.bookType);
      â˜ƒ.writeBoolean(this.isOpen);
      â˜ƒ.writeBoolean(this.isFiltering);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleRecipeBookChangeSettingsPacket(this);
   }

   public RecipeBookType getBookType() {
      return this.bookType;
   }

   public boolean isOpen() {
      return this.isOpen;
   }

   public boolean isFiltering() {
      return this.isFiltering;
   }
}
