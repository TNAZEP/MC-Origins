package net.minecraft.client.gui.screens.inventory;

import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.level.BaseCommandBlock;

public class MinecartCommandBlockEditScreen extends AbstractCommandBlockEditScreen {
   private final BaseCommandBlock commandBlock;

   public MinecartCommandBlockEditScreen(BaseCommandBlock var1) {
      this.commandBlock = â˜ƒ;
   }

   @Override
   public BaseCommandBlock getCommandBlock() {
      return this.commandBlock;
   }

   @Override
   int getPreviousY() {
      return 150;
   }

   @Override
   protected void init() {
      super.init();
      this.commandEdit.setValue(this.getCommandBlock().getCommand());
   }

   @Override
   protected void populateAndSendPacket(BaseCommandBlock var1) {
      if (â˜ƒ instanceof MinecartCommandBlock.MinecartCommandBase â˜ƒ) {
         this.minecraft
            .getConnection()
            .send(new ServerboundSetCommandMinecartPacket(â˜ƒ.getMinecart().getId(), this.commandEdit.getValue(), â˜ƒ.isTrackOutput()));
      }
   }
}
