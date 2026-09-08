package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;

public class SPacketPlaceGhostRecipe implements Packet<INetHandlerPlayClient> {
   private int field_194314_a;
   private ResourceLocation field_194315_b;

   public SPacketPlaceGhostRecipe() {
   }

   public SPacketPlaceGhostRecipe(int var1, IRecipe var2) {
      this.field_194314_a = ☃;
      this.field_194315_b = ☃.func_199560_c();
   }

   public ResourceLocation func_199615_a() {
      return this.field_194315_b;
   }

   public int func_194313_b() {
      return this.field_194314_a;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_194314_a = ☃.readByte();
      this.field_194315_b = ☃.func_192575_l();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_194314_a);
      ☃.func_192572_a(this.field_194315_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_194307_a(this);
   }
}
