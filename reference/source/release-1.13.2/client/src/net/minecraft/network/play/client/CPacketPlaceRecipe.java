package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.ResourceLocation;

public class CPacketPlaceRecipe implements Packet<INetHandlerPlayServer> {
   private int field_194320_a;
   private ResourceLocation field_194321_b;
   private boolean field_194322_c;

   public CPacketPlaceRecipe() {
   }

   public CPacketPlaceRecipe(int var1, IRecipe var2, boolean var3) {
      this.field_194320_a = ☃;
      this.field_194321_b = ☃.func_199560_c();
      this.field_194322_c = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_194320_a = ☃.readByte();
      this.field_194321_b = ☃.func_192575_l();
      this.field_194322_c = ☃.readBoolean();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_194320_a);
      ☃.func_192572_a(this.field_194321_b);
      ☃.writeBoolean(this.field_194322_c);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_194308_a(this);
   }

   public int func_194318_a() {
      return this.field_194320_a;
   }

   public ResourceLocation func_199618_b() {
      return this.field_194321_b;
   }

   public boolean func_194319_c() {
      return this.field_194322_c;
   }
}
