package net.minecraft.world.entity;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.scores.Team;

public final class EntitySelector {
   public static final Predicate<Entity> ENTITY_STILL_ALIVE = Entity::isAlive;
   public static final Predicate<Entity> LIVING_ENTITY_STILL_ALIVE = var0 -> var0.isAlive() && var0 instanceof LivingEntity;
   public static final Predicate<Entity> ENTITY_NOT_BEING_RIDDEN = var0 -> var0.isAlive() && !var0.isVehicle() && !var0.isPassenger();
   public static final Predicate<Entity> CONTAINER_ENTITY_SELECTOR = var0 -> var0 instanceof Container && var0.isAlive();
   public static final Predicate<Entity> NO_CREATIVE_OR_SPECTATOR = var0 -> !(var0 instanceof Player) || !var0.isSpectator() && !((Player)var0).isCreative();
   public static final Predicate<Entity> NO_SPECTATORS = var0 -> !var0.isSpectator();

   private EntitySelector() {
   }

   public static Predicate<Entity> withinDistance(double var0, double var2, double var4, double var6) {
      double â˜ƒ = â˜ƒ * â˜ƒ;
      return var8x -> var8x != null && var8x.distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ) <= â˜ƒ;
   }

   public static Predicate<Entity> pushableBy(Entity var0) {
      Team â˜ƒ = â˜ƒ.getTeam();
      Team.CollisionRule â˜ƒx = â˜ƒ == null ? Team.CollisionRule.ALWAYS : â˜ƒ.getCollisionRule();
      return (Predicate<Entity>)(â˜ƒx == Team.CollisionRule.NEVER ? Predicates.alwaysFalse() : NO_SPECTATORS.and(var3 -> {
         if (!var3.isPushable()) {
            return false;
         } else if (!â˜ƒ.level.isClientSide || var3 instanceof Player && ((Player)var3).isLocalPlayer()) {
            Team â˜ƒ = var3.getTeam();
            Team.CollisionRule â˜ƒx = â˜ƒ == null ? Team.CollisionRule.ALWAYS : â˜ƒ.getCollisionRule();
            if (â˜ƒx == Team.CollisionRule.NEVER) {
               return false;
            } else {
               boolean â˜ƒ = â˜ƒ != null && â˜ƒ.isAlliedTo(â˜ƒ);
               if ((â˜ƒ == Team.CollisionRule.PUSH_OWN_TEAM || â˜ƒx == Team.CollisionRule.PUSH_OWN_TEAM) && â˜ƒ) {
                  return false;
               } else {
                  return â˜ƒ != Team.CollisionRule.PUSH_OTHER_TEAMS && â˜ƒx != Team.CollisionRule.PUSH_OTHER_TEAMS || â˜ƒ;
               }
            }
         } else {
            return false;
         }
      }));
   }

   public static Predicate<Entity> notRiding(Entity var0) {
      return var1 -> {
         while(var1.isPassenger()) {
            var1 = var1.getVehicle();
            if (var1 == â˜ƒ) {
               return false;
            }
         }

         return true;
      };
   }

   public static class MobCanWearArmorEntitySelector implements Predicate<Entity> {
      private final ItemStack itemStack;

      public MobCanWearArmorEntitySelector(ItemStack var1) {
         this.itemStack = â˜ƒ;
      }

      public boolean test(@Nullable Entity var1) {
         if (!â˜ƒ.isAlive()) {
            return false;
         } else if (!(â˜ƒ instanceof LivingEntity)) {
            return false;
         } else {
            LivingEntity â˜ƒ = (LivingEntity)â˜ƒ;
            return â˜ƒ.canTakeItem(this.itemStack);
         }
      }
   }
}
