package net.minecraft.client.gui;

import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.network.play.client.CPacketUpdateCommandMinecart;
import net.minecraft.tileentity.CommandBlockBaseLogic;

public class GuiEditCommandBlockMinecart extends GuiCommandBlockBase {
   private final CommandBlockBaseLogic field_184093_g;

   public GuiEditCommandBlockMinecart(CommandBlockBaseLogic var1) {
      this.field_184093_g = ☃;
   }

   @Override
   public CommandBlockBaseLogic func_195231_h() {
      return this.field_184093_g;
   }

   @Override
   int func_195236_i() {
      return 150;
   }

   @Override
   protected void func_73866_w_() {
      super.func_73866_w_();
      this.field_195238_s = this.func_195231_h().func_175571_m();
      this.func_195233_j();
      this.field_195237_a.func_146180_a(this.func_195231_h().func_145753_i());
   }

   @Override
   protected void func_195235_a(CommandBlockBaseLogic var1) {
      if (☃ instanceof EntityMinecartCommandBlock.MinecartCommandLogic) {
         EntityMinecartCommandBlock.MinecartCommandLogic ☃ = (EntityMinecartCommandBlock.MinecartCommandLogic)☃;
         this.field_146297_k
            .func_147114_u()
            .func_147297_a(new CPacketUpdateCommandMinecart(☃.func_210167_g().func_145782_y(), this.field_195237_a.func_146179_b(), ☃.func_175571_m()));
      }
   }
}
