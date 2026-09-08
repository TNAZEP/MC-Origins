package net.minecraft.world.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

public class EndCrystalItem extends Item {
   public EndCrystalItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (!â˜ƒxx.is(Blocks.OBSIDIAN) && !â˜ƒxx.is(Blocks.BEDROCK)) {
         return InteractionResult.FAIL;
      } else {
         BlockPos â˜ƒ = â˜ƒx.above();
         if (!â˜ƒ.isEmptyBlock(â˜ƒ)) {
            return InteractionResult.FAIL;
         } else {
            double â˜ƒ = (double)â˜ƒ.getX();
            double â˜ƒx = (double)â˜ƒ.getY();
            double â˜ƒxx = (double)â˜ƒ.getZ();
            List<Entity> â˜ƒxxx = â˜ƒ.getEntities(null, new AABB(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ + 1.0, â˜ƒx + 2.0, â˜ƒxx + 1.0));
            if (!â˜ƒxxx.isEmpty()) {
               return InteractionResult.FAIL;
            } else {
               if (â˜ƒ instanceof ServerLevel) {
                  EndCrystal â˜ƒ = new EndCrystal(â˜ƒ, â˜ƒ + 0.5, â˜ƒx, â˜ƒxx + 0.5);
                  â˜ƒ.setShowBottom(false);
                  â˜ƒ.addFreshEntity(â˜ƒ);
                  â˜ƒ.gameEvent(â˜ƒ.getPlayer(), GameEvent.ENTITY_PLACE, â˜ƒ);
                  EndDragonFight â˜ƒx = ((ServerLevel)â˜ƒ).dragonFight();
                  if (â˜ƒx != null) {
                     â˜ƒx.tryRespawn();
                  }
               }

               â˜ƒ.getItemInHand().shrink(1);
               return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
            }
         }
      }
   }

   @Override
   public boolean isFoil(ItemStack var1) {
      return true;
   }
}
