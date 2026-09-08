package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;

public class SPacketJoinGame implements Packet<INetHandlerPlayClient> {
   private int field_149206_a;
   private boolean field_149204_b;
   private GameType field_149205_c;
   private DimensionType field_149202_d;
   private EnumDifficulty field_149203_e;
   private int field_149200_f;
   private WorldType field_149201_g;
   private boolean field_179745_h;

   public SPacketJoinGame() {
   }

   public SPacketJoinGame(int var1, GameType var2, boolean var3, DimensionType var4, EnumDifficulty var5, int var6, WorldType var7, boolean var8) {
      this.field_149206_a = ☃;
      this.field_149202_d = ☃;
      this.field_149203_e = ☃;
      this.field_149205_c = ☃;
      this.field_149200_f = ☃;
      this.field_149204_b = ☃;
      this.field_149201_g = ☃;
      this.field_179745_h = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149206_a = ☃.readInt();
      int ☃ = ☃.readUnsignedByte();
      this.field_149204_b = (☃ & 8) == 8;
      ☃ &= -9;
      this.field_149205_c = GameType.func_77146_a(☃);
      this.field_149202_d = DimensionType.func_186069_a(☃.readInt());
      this.field_149203_e = EnumDifficulty.func_151523_a(☃.readUnsignedByte());
      this.field_149200_f = ☃.readUnsignedByte();
      this.field_149201_g = WorldType.func_77130_a(☃.func_150789_c(16));
      if (this.field_149201_g == null) {
         this.field_149201_g = WorldType.field_77137_b;
      }

      this.field_179745_h = ☃.readBoolean();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_149206_a);
      int ☃ = this.field_149205_c.func_77148_a();
      if (this.field_149204_b) {
         ☃ |= 8;
      }

      ☃.writeByte(☃);
      ☃.writeInt(this.field_149202_d.func_186068_a());
      ☃.writeByte(this.field_149203_e.func_151525_a());
      ☃.writeByte(this.field_149200_f);
      ☃.func_180714_a(this.field_149201_g.func_211888_a());
      ☃.writeBoolean(this.field_179745_h);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147282_a(this);
   }
}
