package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class HangingEntityItem extends Item {
   private final EntityType<? extends HangingEntity> type;

   public HangingEntityItem(EntityType<? extends HangingEntity> var1, Item.Properties var2) {
      super(â˜ƒ);
      this.type = â˜ƒ;
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      Direction â˜ƒx = â˜ƒ.getClickedFace();
      BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
      Player â˜ƒxxx = â˜ƒ.getPlayer();
      ItemStack â˜ƒxxxx = â˜ƒ.getItemInHand();
      if (â˜ƒxxx != null && !this.mayPlace(â˜ƒxxx, â˜ƒx, â˜ƒxxxx, â˜ƒxx)) {
         return InteractionResult.FAIL;
      } else {
         Level â˜ƒx = â˜ƒ.getLevel();
         HangingEntity â˜ƒ;
         if (this.type == EntityType.PAINTING) {
            â˜ƒ = new Painting(â˜ƒx, â˜ƒxx, â˜ƒx);
         } else if (this.type == EntityType.ITEM_FRAME) {
            â˜ƒ = new ItemFrame(â˜ƒx, â˜ƒxx, â˜ƒx);
         } else {
            if (this.type != EntityType.GLOW_ITEM_FRAME) {
               return InteractionResult.sidedSuccess(â˜ƒx.isClientSide);
            }

            â˜ƒ = new GlowItemFrame(â˜ƒx, â˜ƒxx, â˜ƒx);
         }

         CompoundTag â˜ƒ = â˜ƒxxxx.getTag();
         if (â˜ƒ != null) {
            EntityType.updateCustomEntityTag(â˜ƒx, â˜ƒxxx, â˜ƒ, â˜ƒ);
         }

         if (â˜ƒ.survives()) {
            if (!â˜ƒx.isClientSide) {
               â˜ƒ.playPlacementSound();
               â˜ƒx.gameEvent(â˜ƒxxx, GameEvent.ENTITY_PLACE, â˜ƒ);
               â˜ƒx.addFreshEntity(â˜ƒ);
            }

            â˜ƒxxxx.shrink(1);
            return InteractionResult.sidedSuccess(â˜ƒx.isClientSide);
         } else {
            return InteractionResult.CONSUME;
         }
      }
   }

   protected boolean mayPlace(Player var1, Direction var2, ItemStack var3, BlockPos var4) {
      return !â˜ƒ.getAxis().isVertical() && â˜ƒ.mayUseItemAt(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
