package net.minecraft.world.item;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ArmorStandItem extends Item {
   public ArmorStandItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Direction â˜ƒ = â˜ƒ.getClickedFace();
      if (â˜ƒ == Direction.DOWN) {
         return InteractionResult.FAIL;
      } else {
         Level â˜ƒ = â˜ƒ.getLevel();
         BlockPlaceContext â˜ƒx = new BlockPlaceContext(â˜ƒ);
         BlockPos â˜ƒxx = â˜ƒx.getClickedPos();
         ItemStack â˜ƒxxx = â˜ƒ.getItemInHand();
         Vec3 â˜ƒxxxx = Vec3.atBottomCenterOf(â˜ƒxx);
         AABB â˜ƒxxxxx = EntityType.ARMOR_STAND.getDimensions().makeBoundingBox(â˜ƒxxxx.x(), â˜ƒxxxx.y(), â˜ƒxxxx.z());
         if (â˜ƒ.noCollision(null, â˜ƒxxxxx, var0 -> true) && â˜ƒ.getEntities(null, â˜ƒxxxxx).isEmpty()) {
            if (â˜ƒ instanceof ServerLevel â˜ƒxxxxxx) {
               ArmorStand â˜ƒxxxxxxx = EntityType.ARMOR_STAND
                  .create(â˜ƒxxxxxx, â˜ƒxxx.getTag(), null, â˜ƒ.getPlayer(), â˜ƒxx, MobSpawnType.SPAWN_EGG, true, true);
               if (â˜ƒxxxxxxx == null) {
                  return InteractionResult.FAIL;
               }

               float â˜ƒxxxxxxx = (float)Mth.floor((Mth.wrapDegrees(â˜ƒ.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
               â˜ƒxxxxxxx.moveTo(â˜ƒxxxxxxx.getX(), â˜ƒxxxxxxx.getY(), â˜ƒxxxxxxx.getZ(), â˜ƒxxxxxxx, 0.0F);
               this.randomizePose(â˜ƒxxxxxxx, â˜ƒ.random);
               â˜ƒxxxxxx.addFreshEntityWithPassengers(â˜ƒxxxxxxx);
               â˜ƒ.playSound(null, â˜ƒxxxxxxx.getX(), â˜ƒxxxxxxx.getY(), â˜ƒxxxxxxx.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
               â˜ƒ.gameEvent(â˜ƒ.getPlayer(), GameEvent.ENTITY_PLACE, â˜ƒxxxxxxx);
            }

            â˜ƒxxx.shrink(1);
            return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
         } else {
            return InteractionResult.FAIL;
         }
      }
   }

   private void randomizePose(ArmorStand var1, Random var2) {
      Rotations â˜ƒ = â˜ƒ.getHeadPose();
      float â˜ƒx = â˜ƒ.nextFloat() * 5.0F;
      float â˜ƒxx = â˜ƒ.nextFloat() * 20.0F - 10.0F;
      Rotations â˜ƒxxx = new Rotations(â˜ƒ.getX() + â˜ƒx, â˜ƒ.getY() + â˜ƒxx, â˜ƒ.getZ());
      â˜ƒ.setHeadPose(â˜ƒxxx);
      â˜ƒ = â˜ƒ.getBodyPose();
      â˜ƒx = â˜ƒ.nextFloat() * 10.0F - 5.0F;
      â˜ƒxxx = new Rotations(â˜ƒ.getX(), â˜ƒ.getY() + â˜ƒx, â˜ƒ.getZ());
      â˜ƒ.setBodyPose(â˜ƒxxx);
   }
}
