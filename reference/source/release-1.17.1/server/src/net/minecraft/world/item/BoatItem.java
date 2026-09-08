package net.minecraft.world.item;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class BoatItem extends Item {
   private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
   private final Boat.Type type;

   public BoatItem(Boat.Type var1, Item.Properties var2) {
      super(â˜ƒ);
      this.type = â˜ƒ;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      HitResult â˜ƒx = getPlayerPOVHitResult(â˜ƒ, â˜ƒ, ClipContext.Fluid.ANY);
      if (â˜ƒx.getType() == HitResult.Type.MISS) {
         return InteractionResultHolder.pass(â˜ƒ);
      } else {
         Vec3 â˜ƒ = â˜ƒ.getViewVector(1.0F);
         double â˜ƒx = 5.0;
         List<Entity> â˜ƒxx = â˜ƒ.getEntities(â˜ƒ, â˜ƒ.getBoundingBox().expandTowards(â˜ƒ.scale(5.0)).inflate(1.0), ENTITY_PREDICATE);
         if (!â˜ƒxx.isEmpty()) {
            Vec3 â˜ƒxxx = â˜ƒ.getEyePosition();

            for(Entity â˜ƒxxxx : â˜ƒxx) {
               AABB â˜ƒxxxxx = â˜ƒxxxx.getBoundingBox().inflate((double)â˜ƒxxxx.getPickRadius());
               if (â˜ƒxxxxx.contains(â˜ƒxxx)) {
                  return InteractionResultHolder.pass(â˜ƒ);
               }
            }
         }

         if (â˜ƒx.getType() == HitResult.Type.BLOCK) {
            Boat â˜ƒ = new Boat(â˜ƒ, â˜ƒx.getLocation().x, â˜ƒx.getLocation().y, â˜ƒx.getLocation().z);
            â˜ƒ.setType(this.type);
            â˜ƒ.setYRot(â˜ƒ.getYRot());
            if (!â˜ƒ.noCollision(â˜ƒ, â˜ƒ.getBoundingBox().inflate(-0.1))) {
               return InteractionResultHolder.fail(â˜ƒ);
            } else {
               if (!â˜ƒ.isClientSide) {
                  â˜ƒ.addFreshEntity(â˜ƒ);
                  â˜ƒ.gameEvent(â˜ƒ, GameEvent.ENTITY_PLACE, new BlockPos(â˜ƒx.getLocation()));
                  if (!â˜ƒ.getAbilities().instabuild) {
                     â˜ƒ.shrink(1);
                  }
               }

               â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
               return InteractionResultHolder.sidedSuccess(â˜ƒ, â˜ƒ.isClientSide());
            }
         } else {
            return InteractionResultHolder.pass(â˜ƒ);
         }
      }
   }
}
