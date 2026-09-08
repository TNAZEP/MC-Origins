package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPacketCooldown;

public class CooldownTrackerServer extends CooldownTracker {
   private final EntityPlayerMP field_185149_a;

   public CooldownTrackerServer(EntityPlayerMP var1) {
      this.field_185149_a = ☃;
   }

   @Override
   protected void func_185140_b(Item var1, int var2) {
      super.func_185140_b(☃, ☃);
      this.field_185149_a.field_71135_a.func_147359_a(new SPacketCooldown(☃, ☃));
   }

   @Override
   protected void func_185146_c(Item var1) {
      super.func_185146_c(☃);
      this.field_185149_a.field_71135_a.func_147359_a(new SPacketCooldown(☃, 0));
   }
}
