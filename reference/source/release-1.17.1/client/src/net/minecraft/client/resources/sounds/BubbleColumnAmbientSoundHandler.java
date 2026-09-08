package net.minecraft.client.resources.sounds;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BubbleColumnAmbientSoundHandler implements AmbientSoundHandler {
   private final LocalPlayer player;
   private boolean wasInBubbleColumn;
   private boolean firstTick = true;

   public BubbleColumnAmbientSoundHandler(LocalPlayer var1) {
      this.player = â˜ƒ;
   }

   @Override
   public void tick() {
      Level â˜ƒ = this.player.level;
      BlockState â˜ƒx = (BlockState)â˜ƒ.getBlockStatesIfLoaded(this.player.getBoundingBox().inflate(0.0, -0.4F, 0.0).deflate(1.0E-6))
         .filter(var0 -> var0.is(Blocks.BUBBLE_COLUMN))
         .findFirst()
         .orElse(null);
      if (â˜ƒx != null) {
         if (!this.wasInBubbleColumn && !this.firstTick && â˜ƒx.is(Blocks.BUBBLE_COLUMN) && !this.player.isSpectator()) {
            boolean â˜ƒxx = â˜ƒx.getValue(BubbleColumnBlock.DRAG_DOWN);
            if (â˜ƒxx) {
               this.player.playSound(SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0F, 1.0F);
            } else {
               this.player.playSound(SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
            }
         }

         this.wasInBubbleColumn = true;
      } else {
         this.wasInBubbleColumn = false;
      }

      this.firstTick = false;
   }
}
