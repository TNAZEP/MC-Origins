package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface CollisionGetter extends BlockGetter {
   WorldBorder getWorldBorder();

   @Nullable
   BlockGetter getChunkForCollisions(int var1, int var2);

   default boolean isUnobstructed(@Nullable Entity var1, VoxelShape var2) {
      return true;
   }

   default boolean isUnobstructed(BlockState var1, BlockPos var2, CollisionContext var3) {
      VoxelShape â˜ƒ = â˜ƒ.getCollisionShape(this, â˜ƒ, â˜ƒ);
      return â˜ƒ.isEmpty() || this.isUnobstructed(null, â˜ƒ.move((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ()));
   }

   default boolean isUnobstructed(Entity var1) {
      return this.isUnobstructed(â˜ƒ, Shapes.create(â˜ƒ.getBoundingBox()));
   }

   default boolean noCollision(AABB var1) {
      return this.noCollision(null, â˜ƒ, var0 -> true);
   }

   default boolean noCollision(Entity var1) {
      return this.noCollision(â˜ƒ, â˜ƒ.getBoundingBox(), var0 -> true);
   }

   default boolean noCollision(Entity var1, AABB var2) {
      return this.noCollision(â˜ƒ, â˜ƒ, var0 -> true);
   }

   default boolean noCollision(@Nullable Entity var1, AABB var2, Predicate<Entity> var3) {
      return this.getCollisions(â˜ƒ, â˜ƒ, â˜ƒ).allMatch(VoxelShape::isEmpty);
   }

   Stream<VoxelShape> getEntityCollisions(@Nullable Entity var1, AABB var2, Predicate<Entity> var3);

   default Stream<VoxelShape> getCollisions(@Nullable Entity var1, AABB var2, Predicate<Entity> var3) {
      return Stream.concat(this.getBlockCollisions(â˜ƒ, â˜ƒ), this.getEntityCollisions(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   default Stream<VoxelShape> getBlockCollisions(@Nullable Entity var1, AABB var2) {
      return StreamSupport.stream(new CollisionSpliterator(this, â˜ƒ, â˜ƒ), false);
   }

   default boolean hasBlockCollision(@Nullable Entity var1, AABB var2, BiPredicate<BlockState, BlockPos> var3) {
      return !this.getBlockCollisions(â˜ƒ, â˜ƒ, â˜ƒ).allMatch(VoxelShape::isEmpty);
   }

   default Stream<VoxelShape> getBlockCollisions(@Nullable Entity var1, AABB var2, BiPredicate<BlockState, BlockPos> var3) {
      return StreamSupport.stream(new CollisionSpliterator(this, â˜ƒ, â˜ƒ, â˜ƒ), false);
   }

   default Optional<Vec3> findFreePosition(@Nullable Entity var1, VoxelShape var2, Vec3 var3, double var4, double var6, double var8) {
      if (â˜ƒ.isEmpty()) {
         return Optional.empty();
      } else {
         AABB â˜ƒ = â˜ƒ.bounds().inflate(â˜ƒ, â˜ƒ, â˜ƒ);
         VoxelShape â˜ƒx = (VoxelShape)this.getBlockCollisions(â˜ƒ, â˜ƒ)
            .flatMap(var0 -> var0.toAabbs().stream())
            .map(var6x -> var6x.inflate(â˜ƒ / 2.0, â˜ƒ / 2.0, â˜ƒ / 2.0))
            .map(Shapes::create)
            .reduce(Shapes.empty(), Shapes::or);
         VoxelShape â˜ƒxx = Shapes.join(â˜ƒ, â˜ƒx, BooleanOp.ONLY_FIRST);
         return â˜ƒxx.closestPointTo(â˜ƒ);
      }
   }
}
