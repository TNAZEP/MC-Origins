package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public abstract class ContainerOpenersCounter {
   private static final int CHECK_TICK_DELAY = 5;
   private int openCount;

   protected abstract void onOpen(Level var1, BlockPos var2, BlockState var3);

   protected abstract void onClose(Level var1, BlockPos var2, BlockState var3);

   protected abstract void openerCountChanged(Level var1, BlockPos var2, BlockState var3, int var4, int var5);

   protected abstract boolean isOwnContainer(Player var1);

   public void incrementOpeners(Player var1, Level var2, BlockPos var3, BlockState var4) {
      int â˜ƒ = this.openCount++;
      if (â˜ƒ == 0) {
         this.onOpen(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.CONTAINER_OPEN, â˜ƒ);
         scheduleRecheck(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      this.openerCountChanged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.openCount);
   }

   public void decrementOpeners(Player var1, Level var2, BlockPos var3, BlockState var4) {
      int â˜ƒ = this.openCount--;
      if (this.openCount == 0) {
         this.onClose(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.CONTAINER_CLOSE, â˜ƒ);
      }

      this.openerCountChanged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.openCount);
   }

   private int getOpenCount(Level var1, BlockPos var2) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      float â˜ƒxxx = 5.0F;
      AABB â˜ƒxxxx = new AABB(
         (double)((float)â˜ƒ - 5.0F),
         (double)((float)â˜ƒx - 5.0F),
         (double)((float)â˜ƒxx - 5.0F),
         (double)((float)(â˜ƒ + 1) + 5.0F),
         (double)((float)(â˜ƒx + 1) + 5.0F),
         (double)((float)(â˜ƒxx + 1) + 5.0F)
      );
      return â˜ƒ.getEntities(EntityTypeTest.forClass(Player.class), â˜ƒxxxx, this::isOwnContainer).size();
   }

   public void recheckOpeners(Level var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = this.getOpenCount(â˜ƒ, â˜ƒ);
      int â˜ƒx = this.openCount;
      if (â˜ƒx != â˜ƒ) {
         boolean â˜ƒxx = â˜ƒ != 0;
         boolean â˜ƒxxx = â˜ƒx != 0;
         if (â˜ƒxx && !â˜ƒxxx) {
            this.onOpen(â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.gameEvent(null, GameEvent.CONTAINER_OPEN, â˜ƒ);
         } else if (!â˜ƒxx) {
            this.onClose(â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.gameEvent(null, GameEvent.CONTAINER_CLOSE, â˜ƒ);
         }

         this.openCount = â˜ƒ;
      }

      this.openerCountChanged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
      if (â˜ƒ > 0) {
         scheduleRecheck(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public int getOpenerCount() {
      return this.openCount;
   }

   private static void scheduleRecheck(Level var0, BlockPos var1, BlockState var2) {
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, â˜ƒ.getBlock(), 5);
   }
}
