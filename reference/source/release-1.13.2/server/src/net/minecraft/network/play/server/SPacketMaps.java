package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.storage.MapDecoration;

public class SPacketMaps implements Packet<INetHandlerPlayClient> {
   private int field_149191_a;
   private byte field_179739_b;
   private boolean field_186950_c;
   private MapDecoration[] field_179740_c;
   private int field_179737_d;
   private int field_179738_e;
   private int field_179735_f;
   private int field_179736_g;
   private byte[] field_179741_h;

   public SPacketMaps() {
   }

   public SPacketMaps(int var1, byte var2, boolean var3, Collection<MapDecoration> var4, byte[] var5, int var6, int var7, int var8, int var9) {
      this.field_149191_a = ☃;
      this.field_179739_b = ☃;
      this.field_186950_c = ☃;
      this.field_179740_c = (MapDecoration[])☃.toArray(new MapDecoration[☃.size()]);
      this.field_179737_d = ☃;
      this.field_179738_e = ☃;
      this.field_179735_f = ☃;
      this.field_179736_g = ☃;
      this.field_179741_h = new byte[☃ * ☃];

      for(int ☃ = 0; ☃ < ☃; ++☃) {
         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            this.field_179741_h[☃ + ☃x * ☃] = ☃[☃ + ☃ + (☃ + ☃x) * 128];
         }
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149191_a = ☃.func_150792_a();
      this.field_179739_b = ☃.readByte();
      this.field_186950_c = ☃.readBoolean();
      this.field_179740_c = new MapDecoration[☃.func_150792_a()];

      for(int ☃ = 0; ☃ < this.field_179740_c.length; ++☃) {
         MapDecoration.Type ☃x = ☃.func_179257_a(MapDecoration.Type.class);
         this.field_179740_c[☃] = new MapDecoration(☃x, ☃.readByte(), ☃.readByte(), (byte)(☃.readByte() & 15), ☃.readBoolean() ? ☃.func_179258_d() : null);
      }

      this.field_179735_f = ☃.readUnsignedByte();
      if (this.field_179735_f > 0) {
         this.field_179736_g = ☃.readUnsignedByte();
         this.field_179737_d = ☃.readUnsignedByte();
         this.field_179738_e = ☃.readUnsignedByte();
         this.field_179741_h = ☃.func_179251_a();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149191_a);
      ☃.writeByte(this.field_179739_b);
      ☃.writeBoolean(this.field_186950_c);
      ☃.func_150787_b(this.field_179740_c.length);

      for(MapDecoration ☃ : this.field_179740_c) {
         ☃.func_179249_a(☃.func_191179_b());
         ☃.writeByte(☃.func_176112_b());
         ☃.writeByte(☃.func_176113_c());
         ☃.writeByte(☃.func_176111_d() & 15);
         if (☃.func_204309_g() != null) {
            ☃.writeBoolean(true);
            ☃.func_179256_a(☃.func_204309_g());
         } else {
            ☃.writeBoolean(false);
         }
      }

      ☃.writeByte(this.field_179735_f);
      if (this.field_179735_f > 0) {
         ☃.writeByte(this.field_179736_g);
         ☃.writeByte(this.field_179737_d);
         ☃.writeByte(this.field_179738_e);
         ☃.func_179250_a(this.field_179741_h);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147264_a(this);
   }
}
