package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;

public class SPacketUpdateBossInfo implements Packet<INetHandlerPlayClient> {
   private UUID field_186911_a;
   private SPacketUpdateBossInfo.Operation field_186912_b;
   private ITextComponent field_186913_c;
   private float field_186914_d;
   private BossInfo.Color field_186915_e;
   private BossInfo.Overlay field_186916_f;
   private boolean field_186917_g;
   private boolean field_186918_h;
   private boolean field_186919_i;

   public SPacketUpdateBossInfo() {
   }

   public SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation var1, BossInfo var2) {
      this.field_186912_b = ☃;
      this.field_186911_a = ☃.func_186737_d();
      this.field_186913_c = ☃.func_186744_e();
      this.field_186914_d = ☃.func_186738_f();
      this.field_186915_e = ☃.func_186736_g();
      this.field_186916_f = ☃.func_186740_h();
      this.field_186917_g = ☃.func_186734_i();
      this.field_186918_h = ☃.func_186747_j();
      this.field_186919_i = ☃.func_186748_k();
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_186911_a = ☃.func_179253_g();
      this.field_186912_b = ☃.func_179257_a(SPacketUpdateBossInfo.Operation.class);
      switch(this.field_186912_b) {
         case ADD:
            this.field_186913_c = ☃.func_179258_d();
            this.field_186914_d = ☃.readFloat();
            this.field_186915_e = ☃.func_179257_a(BossInfo.Color.class);
            this.field_186916_f = ☃.func_179257_a(BossInfo.Overlay.class);
            this.func_186903_a(☃.readUnsignedByte());
         case REMOVE:
         default:
            break;
         case UPDATE_PCT:
            this.field_186914_d = ☃.readFloat();
            break;
         case UPDATE_NAME:
            this.field_186913_c = ☃.func_179258_d();
            break;
         case UPDATE_STYLE:
            this.field_186915_e = ☃.func_179257_a(BossInfo.Color.class);
            this.field_186916_f = ☃.func_179257_a(BossInfo.Overlay.class);
            break;
         case UPDATE_PROPERTIES:
            this.func_186903_a(☃.readUnsignedByte());
      }
   }

   private void func_186903_a(int var1) {
      this.field_186917_g = (☃ & 1) > 0;
      this.field_186918_h = (☃ & 2) > 0;
      this.field_186919_i = (☃ & 4) > 0;
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179252_a(this.field_186911_a);
      ☃.func_179249_a(this.field_186912_b);
      switch(this.field_186912_b) {
         case ADD:
            ☃.func_179256_a(this.field_186913_c);
            ☃.writeFloat(this.field_186914_d);
            ☃.func_179249_a(this.field_186915_e);
            ☃.func_179249_a(this.field_186916_f);
            ☃.writeByte(this.func_186905_j());
         case REMOVE:
         default:
            break;
         case UPDATE_PCT:
            ☃.writeFloat(this.field_186914_d);
            break;
         case UPDATE_NAME:
            ☃.func_179256_a(this.field_186913_c);
            break;
         case UPDATE_STYLE:
            ☃.func_179249_a(this.field_186915_e);
            ☃.func_179249_a(this.field_186916_f);
            break;
         case UPDATE_PROPERTIES:
            ☃.writeByte(this.func_186905_j());
      }
   }

   private int func_186905_j() {
      int ☃ = 0;
      if (this.field_186917_g) {
         ☃ |= 1;
      }

      if (this.field_186918_h) {
         ☃ |= 2;
      }

      if (this.field_186919_i) {
         ☃ |= 4;
      }

      return ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184325_a(this);
   }

   public static enum Operation {
      ADD,
      REMOVE,
      UPDATE_PCT,
      UPDATE_NAME,
      UPDATE_STYLE,
      UPDATE_PROPERTIES;
   }
}
