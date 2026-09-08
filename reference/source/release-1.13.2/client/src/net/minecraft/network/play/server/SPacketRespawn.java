package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;

public class SPacketRespawn implements Packet<INetHandlerPlayClient> {
   private DimensionType field_149088_a;
   private EnumDifficulty field_149086_b;
   private GameType field_149087_c;
   private WorldType field_149085_d;

   public SPacketRespawn() {
   }

   public SPacketRespawn(DimensionType var1, EnumDifficulty var2, WorldType var3, GameType var4) {
      this.field_149088_a = ☃;
      this.field_149086_b = ☃;
      this.field_149087_c = ☃;
      this.field_149085_d = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147280_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149088_a = DimensionType.func_186069_a(☃.readInt());
      this.field_149086_b = EnumDifficulty.func_151523_a(☃.readUnsignedByte());
      this.field_149087_c = GameType.func_77146_a(☃.readUnsignedByte());
      this.field_149085_d = WorldType.func_77130_a(☃.func_150789_c(16));
      if (this.field_149085_d == null) {
         this.field_149085_d = WorldType.field_77137_b;
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_149088_a.func_186068_a());
      ☃.writeByte(this.field_149086_b.func_151525_a());
      ☃.writeByte(this.field_149087_c.func_77148_a());
      ☃.func_180714_a(this.field_149085_d.func_211888_a());
   }

   public DimensionType func_212643_b() {
      return this.field_149088_a;
   }

   public EnumDifficulty func_149081_d() {
      return this.field_149086_b;
   }

   public GameType func_149083_e() {
      return this.field_149087_c;
   }

   public WorldType func_149080_f() {
      return this.field_149085_d;
   }
}
