package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class LeadItem extends Item {
   public LeadItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (â˜ƒxx.is(BlockTags.FENCES)) {
         Player â˜ƒxxx = â˜ƒ.getPlayer();
         if (!â˜ƒ.isClientSide && â˜ƒxxx != null) {
            bindPlayerMobs(â˜ƒxxx, â˜ƒ, â˜ƒx);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public static InteractionResult bindPlayerMobs(Player var0, Level var1, BlockPos var2) {
      LeashFenceKnotEntity â˜ƒ = null;
      boolean â˜ƒx = false;
      double â˜ƒxx = 7.0;
      int â˜ƒxxx = â˜ƒ.getX();
      int â˜ƒxxxx = â˜ƒ.getY();
      int â˜ƒxxxxx = â˜ƒ.getZ();

      for(Mob â˜ƒxxxxxx : â˜ƒ.getEntitiesOfClass(
         Mob.class,
         new AABB((double)â˜ƒxxx - 7.0, (double)â˜ƒxxxx - 7.0, (double)â˜ƒxxxxx - 7.0, (double)â˜ƒxxx + 7.0, (double)â˜ƒxxxx + 7.0, (double)â˜ƒxxxxx + 7.0)
      )) {
         if (â˜ƒxxxxxx.getLeashHolder() == â˜ƒ) {
            if (â˜ƒ == null) {
               â˜ƒ = LeashFenceKnotEntity.getOrCreateKnot(â˜ƒ, â˜ƒ);
               â˜ƒ.playPlacementSound();
            }

            â˜ƒxxxxxx.setLeashedTo(â˜ƒ, true);
            â˜ƒx = true;
         }
      }

      return â˜ƒx ? InteractionResult.SUCCESS : InteractionResult.PASS;
   }
}
