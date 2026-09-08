package net.minecraft.server.level;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class DemoMode extends ServerPlayerGameMode {
   public static final int DEMO_DAYS = 5;
   public static final int TOTAL_PLAY_TICKS = 120500;
   private boolean displayedIntro;
   private boolean demoHasEnded;
   private int demoEndedReminder;
   private int gameModeTicks;

   public DemoMode(ServerPlayer var1) {
      super(â˜ƒ);
   }

   @Override
   public void tick() {
      super.tick();
      ++this.gameModeTicks;
      long â˜ƒ = this.level.getGameTime();
      long â˜ƒx = â˜ƒ / 24000L + 1L;
      if (!this.displayedIntro && this.gameModeTicks > 20) {
         this.displayedIntro = true;
         this.player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 0.0F));
      }

      this.demoHasEnded = â˜ƒ > 120500L;
      if (this.demoHasEnded) {
         ++this.demoEndedReminder;
      }

      if (â˜ƒ % 24000L == 500L) {
         if (â˜ƒx <= 6L) {
            if (â˜ƒx == 6L) {
               this.player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 104.0F));
            } else {
               this.player.sendMessage(new TranslatableComponent("demo.day." + â˜ƒx), Util.NIL_UUID);
            }
         }
      } else if (â˜ƒx == 1L) {
         if (â˜ƒ == 100L) {
            this.player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 101.0F));
         } else if (â˜ƒ == 175L) {
            this.player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 102.0F));
         } else if (â˜ƒ == 250L) {
            this.player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 103.0F));
         }
      } else if (â˜ƒx == 5L && â˜ƒ % 24000L == 22000L) {
         this.player.sendMessage(new TranslatableComponent("demo.day.warning"), Util.NIL_UUID);
      }
   }

   private void outputDemoReminder() {
      if (this.demoEndedReminder > 100) {
         this.player.sendMessage(new TranslatableComponent("demo.reminder"), Util.NIL_UUID);
         this.demoEndedReminder = 0;
      }
   }

   @Override
   public void handleBlockBreakAction(BlockPos var1, ServerboundPlayerActionPacket.Action var2, Direction var3, int var4) {
      if (this.demoHasEnded) {
         this.outputDemoReminder();
      } else {
         super.handleBlockBreakAction(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public InteractionResult useItem(ServerPlayer var1, Level var2, ItemStack var3, InteractionHand var4) {
      if (this.demoHasEnded) {
         this.outputDemoReminder();
         return InteractionResult.PASS;
      } else {
         return super.useItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public InteractionResult useItemOn(ServerPlayer var1, Level var2, ItemStack var3, InteractionHand var4, BlockHitResult var5) {
      if (this.demoHasEnded) {
         this.outputDemoReminder();
         return InteractionResult.PASS;
      } else {
         return super.useItemOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
