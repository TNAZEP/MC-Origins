package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface EntityGetter {
   List<Entity> getEntities(@Nullable Entity var1, AABB var2, Predicate<? super Entity> var3);

   <T extends Entity> List<T> getEntities(EntityTypeTest<Entity, T> var1, AABB var2, Predicate<? super T> var3);

   default <T extends Entity> List<T> getEntitiesOfClass(Class<T> var1, AABB var2, Predicate<? super T> var3) {
      return this.getEntities(EntityTypeTest.forClass(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   List<? extends Player> players();

   default List<Entity> getEntities(@Nullable Entity var1, AABB var2) {
      return this.getEntities(â˜ƒ, â˜ƒ, EntitySelector.NO_SPECTATORS);
   }

   default boolean isUnobstructed(@Nullable Entity var1, VoxelShape var2) {
      if (â˜ƒ.isEmpty()) {
         return true;
      } else {
         for(Entity â˜ƒ : this.getEntities(â˜ƒ, â˜ƒ.bounds())) {
            if (!â˜ƒ.isRemoved()
               && â˜ƒ.blocksBuilding
               && (â˜ƒ == null || !â˜ƒ.isPassengerOfSameVehicle(â˜ƒ))
               && Shapes.joinIsNotEmpty(â˜ƒ, Shapes.create(â˜ƒ.getBoundingBox()), BooleanOp.AND)) {
               return false;
            }
         }

         return true;
      }
   }

   default <T extends Entity> List<T> getEntitiesOfClass(Class<T> var1, AABB var2) {
      return this.getEntitiesOfClass(â˜ƒ, â˜ƒ, EntitySelector.NO_SPECTATORS);
   }

   default Stream<VoxelShape> getEntityCollisions(@Nullable Entity var1, AABB var2, Predicate<Entity> var3) {
      if (â˜ƒ.getSize() < 1.0E-7) {
         return Stream.empty();
      } else {
         AABB â˜ƒ = â˜ƒ.inflate(1.0E-7);
         return this.getEntities(
               â˜ƒ, â˜ƒ, â˜ƒ.and(var2x -> var2x.getBoundingBox().intersects(â˜ƒ) && (â˜ƒ == null ? var2x.canBeCollidedWith() : â˜ƒ.canCollideWith(var2x)))
            )
            .stream()
            .map(Entity::getBoundingBox)
            .map(Shapes::create);
      }
   }

   @Nullable
   default Player getNearestPlayer(double var1, double var3, double var5, double var7, @Nullable Predicate<Entity> var9) {
      double â˜ƒ = -1.0;
      Player â˜ƒx = null;

      for(Player â˜ƒxx : this.players()) {
         if (â˜ƒ == null || â˜ƒ.test(â˜ƒxx)) {
            double â˜ƒxxx = â˜ƒxx.distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ);
            if ((â˜ƒ < 0.0 || â˜ƒxxx < â˜ƒ * â˜ƒ) && (â˜ƒ == -1.0 || â˜ƒxxx < â˜ƒ)) {
               â˜ƒ = â˜ƒxxx;
               â˜ƒx = â˜ƒxx;
            }
         }
      }

      return â˜ƒx;
   }

   @Nullable
   default Player getNearestPlayer(Entity var1, double var2) {
      return this.getNearestPlayer(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ, false);
   }

   @Nullable
   default Player getNearestPlayer(double var1, double var3, double var5, double var7, boolean var9) {
      Predicate<Entity> â˜ƒ = â˜ƒ ? EntitySelector.NO_CREATIVE_OR_SPECTATOR : EntitySelector.NO_SPECTATORS;
      return this.getNearestPlayer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   default boolean hasNearbyAlivePlayer(double var1, double var3, double var5, double var7) {
      for(Player â˜ƒ : this.players()) {
         if (EntitySelector.NO_SPECTATORS.test(â˜ƒ) && EntitySelector.LIVING_ENTITY_STILL_ALIVE.test(â˜ƒ)) {
            double â˜ƒx = â˜ƒ.distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ < 0.0 || â˜ƒx < â˜ƒ * â˜ƒ) {
               return true;
            }
         }
      }

      return false;
   }

   @Nullable
   default Player getNearestPlayer(TargetingConditions var1, LivingEntity var2) {
      return this.getNearestEntity(this.players(), â˜ƒ, â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   @Nullable
   default Player getNearestPlayer(TargetingConditions var1, LivingEntity var2, double var3, double var5, double var7) {
      return this.getNearestEntity(this.players(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   default Player getNearestPlayer(TargetingConditions var1, double var2, double var4, double var6) {
      return this.getNearestEntity(this.players(), â˜ƒ, null, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   default <T extends LivingEntity> T getNearestEntity(
      Class<? extends T> var1, TargetingConditions var2, @Nullable LivingEntity var3, double var4, double var6, double var8, AABB var10
   ) {
      return this.getNearestEntity(this.getEntitiesOfClass(â˜ƒ, â˜ƒ, var0 -> true), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   default <T extends LivingEntity> T getNearestEntity(
      List<? extends T> var1, TargetingConditions var2, @Nullable LivingEntity var3, double var4, double var6, double var8
   ) {
      double â˜ƒ = -1.0;
      T â˜ƒx = null;

      for(T â˜ƒxx : â˜ƒ) {
         if (â˜ƒ.test(â˜ƒ, â˜ƒxx)) {
            double â˜ƒxxx = â˜ƒxx.distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ == -1.0 || â˜ƒxxx < â˜ƒ) {
               â˜ƒ = â˜ƒxxx;
               â˜ƒx = â˜ƒxx;
            }
         }
      }

      return â˜ƒx;
   }

   default List<Player> getNearbyPlayers(TargetingConditions var1, LivingEntity var2, AABB var3) {
      List<Player> â˜ƒ = Lists.<Player>newArrayList();

      for(Player â˜ƒx : this.players()) {
         if (â˜ƒ.contains(â˜ƒx.getX(), â˜ƒx.getY(), â˜ƒx.getZ()) && â˜ƒ.test(â˜ƒ, â˜ƒx)) {
            â˜ƒ.add(â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   default <T extends LivingEntity> List<T> getNearbyEntities(Class<T> var1, TargetingConditions var2, LivingEntity var3, AABB var4) {
      List<T> â˜ƒ = this.getEntitiesOfClass(â˜ƒ, â˜ƒ, var0 -> true);
      List<T> â˜ƒx = Lists.<T>newArrayList();

      for(T â˜ƒxx : â˜ƒ) {
         if (â˜ƒ.test(â˜ƒ, â˜ƒxx)) {
            â˜ƒx.add(â˜ƒxx);
         }
      }

      return â˜ƒx;
   }

   @Nullable
   default Player getPlayerByUUID(UUID var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.players().size(); ++â˜ƒ) {
         Player â˜ƒx = (Player)this.players().get(â˜ƒ);
         if (â˜ƒ.equals(â˜ƒx.getUUID())) {
            return â˜ƒx;
         }
      }

      return null;
   }
}
