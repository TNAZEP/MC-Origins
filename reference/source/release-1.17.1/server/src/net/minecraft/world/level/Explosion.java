package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Explosion {
   private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
   private static final int MAX_DROPS_PER_COMBINED_STACK = 16;
   private final boolean fire;
   private final Explosion.BlockInteraction blockInteraction;
   private final Random random = new Random();
   private final Level level;
   private final double x;
   private final double y;
   private final double z;
   @Nullable
   private final Entity source;
   private final float radius;
   private final DamageSource damageSource;
   private final ExplosionDamageCalculator damageCalculator;
   private final List<BlockPos> toBlow = Lists.<BlockPos>newArrayList();
   private final Map<Player, Vec3> hitPlayers = Maps.<Player, Vec3>newHashMap();

   public Explosion(Level var1, @Nullable Entity var2, double var3, double var5, double var7, float var9) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, Explosion.BlockInteraction.DESTROY);
   }

   public Explosion(Level var1, @Nullable Entity var2, double var3, double var5, double var7, float var9, List<BlockPos> var10) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false, Explosion.BlockInteraction.DESTROY, â˜ƒ);
   }

   public Explosion(
      Level var1,
      @Nullable Entity var2,
      double var3,
      double var5,
      double var7,
      float var9,
      boolean var10,
      Explosion.BlockInteraction var11,
      List<BlockPos> var12
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.toBlow.addAll(â˜ƒ);
   }

   public Explosion(Level var1, @Nullable Entity var2, double var3, double var5, double var7, float var9, boolean var10, Explosion.BlockInteraction var11) {
      this(â˜ƒ, â˜ƒ, null, null, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Explosion(
      Level var1,
      @Nullable Entity var2,
      @Nullable DamageSource var3,
      @Nullable ExplosionDamageCalculator var4,
      double var5,
      double var7,
      double var9,
      float var11,
      boolean var12,
      Explosion.BlockInteraction var13
   ) {
      this.level = â˜ƒ;
      this.source = â˜ƒ;
      this.radius = â˜ƒ;
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.fire = â˜ƒ;
      this.blockInteraction = â˜ƒ;
      this.damageSource = â˜ƒ == null ? DamageSource.explosion(this) : â˜ƒ;
      this.damageCalculator = â˜ƒ == null ? this.makeDamageCalculator(â˜ƒ) : â˜ƒ;
   }

   private ExplosionDamageCalculator makeDamageCalculator(@Nullable Entity var1) {
      return (ExplosionDamageCalculator)(â˜ƒ == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(â˜ƒ));
   }

   public static float getSeenPercent(Vec3 var0, Entity var1) {
      AABB â˜ƒ = â˜ƒ.getBoundingBox();
      double â˜ƒx = 1.0 / ((â˜ƒ.maxX - â˜ƒ.minX) * 2.0 + 1.0);
      double â˜ƒxx = 1.0 / ((â˜ƒ.maxY - â˜ƒ.minY) * 2.0 + 1.0);
      double â˜ƒxxx = 1.0 / ((â˜ƒ.maxZ - â˜ƒ.minZ) * 2.0 + 1.0);
      double â˜ƒxxxx = (1.0 - Math.floor(1.0 / â˜ƒx) * â˜ƒx) / 2.0;
      double â˜ƒxxxxx = (1.0 - Math.floor(1.0 / â˜ƒxxx) * â˜ƒxxx) / 2.0;
      if (!(â˜ƒx < 0.0) && !(â˜ƒxx < 0.0) && !(â˜ƒxxx < 0.0)) {
         int â˜ƒxxxxxx = 0;
         int â˜ƒxxxxxxx = 0;

         for(float â˜ƒxxxxxxxx = 0.0F; â˜ƒxxxxxxxx <= 1.0F; â˜ƒxxxxxxxx = (float)((double)â˜ƒxxxxxxxx + â˜ƒx)) {
            for(float â˜ƒxxxxxxxxx = 0.0F; â˜ƒxxxxxxxxx <= 1.0F; â˜ƒxxxxxxxxx = (float)((double)â˜ƒxxxxxxxxx + â˜ƒxx)) {
               for(float â˜ƒxxxxxxxxxx = 0.0F; â˜ƒxxxxxxxxxx <= 1.0F; â˜ƒxxxxxxxxxx = (float)((double)â˜ƒxxxxxxxxxx + â˜ƒxxx)) {
                  double â˜ƒxxxxxxxxxxx = Mth.lerp((double)â˜ƒxxxxxxxx, â˜ƒ.minX, â˜ƒ.maxX);
                  double â˜ƒxxxxxxxxxxxx = Mth.lerp((double)â˜ƒxxxxxxxxx, â˜ƒ.minY, â˜ƒ.maxY);
                  double â˜ƒxxxxxxxxxxxxx = Mth.lerp((double)â˜ƒxxxxxxxxxx, â˜ƒ.minZ, â˜ƒ.maxZ);
                  Vec3 â˜ƒxxxxxxxxxxxxxx = new Vec3(â˜ƒxxxxxxxxxxx + â˜ƒxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx + â˜ƒxxxxx);
                  if (â˜ƒ.level.clip(new ClipContext(â˜ƒxxxxxxxxxxxxxx, â˜ƒ, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, â˜ƒ)).getType()
                     == HitResult.Type.MISS) {
                     ++â˜ƒxxxxxx;
                  }

                  ++â˜ƒxxxxxxx;
               }
            }
         }

         return (float)â˜ƒxxxxxx / (float)â˜ƒxxxxxxx;
      } else {
         return 0.0F;
      }
   }

   public void explode() {
      this.level.gameEvent(this.source, GameEvent.EXPLODE, new BlockPos(this.x, this.y, this.z));
      Set<BlockPos> â˜ƒ = Sets.<BlockPos>newHashSet();
      int â˜ƒx = 16;

      for(int â˜ƒxx = 0; â˜ƒxx < 16; ++â˜ƒxx) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < 16; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
               if (â˜ƒxx == 0 || â˜ƒxx == 15 || â˜ƒxxx == 0 || â˜ƒxxx == 15 || â˜ƒxxxx == 0 || â˜ƒxxxx == 15) {
                  double â˜ƒxxxxx = (double)((float)â˜ƒxx / 15.0F * 2.0F - 1.0F);
                  double â˜ƒxxxxxx = (double)((float)â˜ƒxxx / 15.0F * 2.0F - 1.0F);
                  double â˜ƒxxxxxxx = (double)((float)â˜ƒxxxx / 15.0F * 2.0F - 1.0F);
                  double â˜ƒxxxxxxxx = Math.sqrt(â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxx * â˜ƒxxxxxxx);
                  â˜ƒxxxxx /= â˜ƒxxxxxxxx;
                  â˜ƒxxxxxx /= â˜ƒxxxxxxxx;
                  â˜ƒxxxxxxx /= â˜ƒxxxxxxxx;
                  float â˜ƒxxxxxxxxx = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                  double â˜ƒxxxxxxxxxx = this.x;
                  double â˜ƒxxxxxxxxxxx = this.y;
                  double â˜ƒxxxxxxxxxxxx = this.z;

                  for(float â˜ƒxxxxxxxxxxxxx = 0.3F; â˜ƒxxxxxxxxx > 0.0F; â˜ƒxxxxxxxxx -= 0.22500001F) {
                     BlockPos â˜ƒxxxxxxxxxxxxxx = new BlockPos(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx);
                     BlockState â˜ƒxxxxxxxxxxxxxxx = this.level.getBlockState(â˜ƒxxxxxxxxxxxxxx);
                     FluidState â˜ƒxxxxxxxxxxxxxxxx = this.level.getFluidState(â˜ƒxxxxxxxxxxxxxx);
                     if (!this.level.isInWorldBounds(â˜ƒxxxxxxxxxxxxxx)) {
                        break;
                     }

                     Optional<Float> â˜ƒxxxxxxxxxxxxxx = this.damageCalculator
                        .getBlockExplosionResistance(this, this.level, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
                     if (â˜ƒxxxxxxxxxxxxxx.isPresent()) {
                        â˜ƒxxxxxxxxx -= (â˜ƒxxxxxxxxxxxxxx.get() + 0.3F) * 0.3F;
                     }

                     if (â˜ƒxxxxxxxxx > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxx)
                        )
                      {
                        â˜ƒ.add(â˜ƒxxxxxxxxxxxxxx);
                     }

                     â˜ƒxxxxxxxxxx += â˜ƒxxxxx * 0.3F;
                     â˜ƒxxxxxxxxxxx += â˜ƒxxxxxx * 0.3F;
                     â˜ƒxxxxxxxxxxxx += â˜ƒxxxxxxx * 0.3F;
                  }
               }
            }
         }
      }

      this.toBlow.addAll(â˜ƒ);
      float â˜ƒxx = this.radius * 2.0F;
      int â˜ƒxxx = Mth.floor(this.x - (double)â˜ƒxx - 1.0);
      int â˜ƒxxxx = Mth.floor(this.x + (double)â˜ƒxx + 1.0);
      int â˜ƒxxxxx = Mth.floor(this.y - (double)â˜ƒxx - 1.0);
      int â˜ƒxxxxxx = Mth.floor(this.y + (double)â˜ƒxx + 1.0);
      int â˜ƒxxxxxxx = Mth.floor(this.z - (double)â˜ƒxx - 1.0);
      int â˜ƒxxxxxxxx = Mth.floor(this.z + (double)â˜ƒxx + 1.0);
      List<Entity> â˜ƒxxxxxxxxx = this.level
         .getEntities(this.source, new AABB((double)â˜ƒxxx, (double)â˜ƒxxxxx, (double)â˜ƒxxxxxxx, (double)â˜ƒxxxx, (double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxxx));
      Vec3 â˜ƒxxxxxxxxxx = new Vec3(this.x, this.y, this.z);

      for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < â˜ƒxxxxxxxxx.size(); ++â˜ƒxxxxxxxxxxx) {
         Entity â˜ƒxxxxxxxxxxxx = (Entity)â˜ƒxxxxxxxxx.get(â˜ƒxxxxxxxxxxx);
         if (!â˜ƒxxxxxxxxxxxx.ignoreExplosion()) {
            double â˜ƒxxxxxxxxxxxxx = Math.sqrt(â˜ƒxxxxxxxxxxxx.distanceToSqr(â˜ƒxxxxxxxxxx)) / (double)â˜ƒxx;
            if (â˜ƒxxxxxxxxxxxxx <= 1.0) {
               double â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getX() - this.x;
               double â˜ƒxxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxx instanceof PrimedTnt ? â˜ƒxxxxxxxxxxxx.getY() : â˜ƒxxxxxxxxxxxx.getEyeY()) - this.y;
               double â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx.getZ() - this.z;
               double â˜ƒxxxxxxxxxxxxxxxxx = Math.sqrt(
                  â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxx
               );
               if (â˜ƒxxxxxxxxxxxxxxxxx != 0.0) {
                  â˜ƒxxxxxxxxxxxxxx /= â˜ƒxxxxxxxxxxxxxxxxx;
                  â˜ƒxxxxxxxxxxxxxxx /= â˜ƒxxxxxxxxxxxxxxxxx;
                  â˜ƒxxxxxxxxxxxxxxxx /= â˜ƒxxxxxxxxxxxxxxxxx;
                  double â˜ƒxxxxxxxxxxxxxxxxxx = (double)getSeenPercent(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx);
                  double â˜ƒxxxxxxxxxxxxxxxxxxx = (1.0 - â˜ƒxxxxxxxxxxxxx) * â˜ƒxxxxxxxxxxxxxxxxxx;
                  â˜ƒxxxxxxxxxxxx.hurt(
                     this.getDamageSource(),
                     (float)((int)((â˜ƒxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxx) / 2.0 * 7.0 * (double)â˜ƒxx + 1.0))
                  );
                  double â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx;
                  if (â˜ƒxxxxxxxxxxxx instanceof LivingEntity) {
                     â˜ƒxxxxxxxxxxxxxxxxxxxx = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity)â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx);
                  }

                  â˜ƒxxxxxxxxxxxx.setDeltaMovement(
                     â˜ƒxxxxxxxxxxxx.getDeltaMovement()
                        .add(
                           â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx,
                           â˜ƒxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx,
                           â˜ƒxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx
                        )
                  );
                  if (â˜ƒxxxxxxxxxxxx instanceof Player â˜ƒxxxxxxxxxxxxxxxxxx
                     && !â˜ƒxxxxxxxxxxxxxxxxxx.isSpectator()
                     && (!â˜ƒxxxxxxxxxxxxxxxxxx.isCreative() || !â˜ƒxxxxxxxxxxxxxxxxxx.getAbilities().flying)) {
                     this.hitPlayers
                        .put(
                           â˜ƒxxxxxxxxxxxxxxxxxx,
                           new Vec3(
                              â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxx
                           )
                        );
                  }
               }
            }
         }
      }
   }

   public void finalizeExplosion(boolean var1) {
      if (this.level.isClientSide) {
         this.level
            .playLocalSound(
               this.x,
               this.y,
               this.z,
               SoundEvents.GENERIC_EXPLODE,
               SoundSource.BLOCKS,
               4.0F,
               (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F,
               false
            );
      }

      boolean â˜ƒ = this.blockInteraction != Explosion.BlockInteraction.NONE;
      if (â˜ƒ) {
         if (!(this.radius < 2.0F) && â˜ƒ) {
            this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         } else {
            this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
         }
      }

      if (â˜ƒ) {
         ObjectArrayList<Pair<ItemStack, BlockPos>> â˜ƒ = new ObjectArrayList<>();
         Collections.shuffle(this.toBlow, this.level.random);

         for(BlockPos â˜ƒx : this.toBlow) {
            BlockState â˜ƒxx = this.level.getBlockState(â˜ƒx);
            Block â˜ƒxxx = â˜ƒxx.getBlock();
            if (!â˜ƒxx.isAir()) {
               BlockPos â˜ƒxxxx = â˜ƒx.immutable();
               this.level.getProfiler().push("explosion_blocks");
               if (â˜ƒxxx.dropFromExplosion(this) && this.level instanceof ServerLevel) {
                  BlockEntity â˜ƒxxxxx = â˜ƒxx.hasBlockEntity() ? this.level.getBlockEntity(â˜ƒx) : null;
                  LootContext.Builder â˜ƒxxxxxx = new LootContext.Builder((ServerLevel)this.level)
                     .withRandom(this.level.random)
                     .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(â˜ƒx))
                     .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                     .withOptionalParameter(LootContextParams.BLOCK_ENTITY, â˜ƒxxxxx)
                     .withOptionalParameter(LootContextParams.THIS_ENTITY, this.source);
                  if (this.blockInteraction == Explosion.BlockInteraction.DESTROY) {
                     â˜ƒxxxxxx.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
                  }

                  â˜ƒxx.getDrops(â˜ƒxxxxxx).forEach(var2x -> addBlockDrops(â˜ƒ, var2x, â˜ƒ));
               }

               this.level.setBlock(â˜ƒx, Blocks.AIR.defaultBlockState(), 3);
               â˜ƒxxx.wasExploded(this.level, â˜ƒx, this);
               this.level.getProfiler().pop();
            }
         }

         for(Pair<ItemStack, BlockPos> â˜ƒx : â˜ƒ) {
            Block.popResource(this.level, â˜ƒx.getSecond(), â˜ƒx.getFirst());
         }
      }

      if (this.fire) {
         for(BlockPos â˜ƒ : this.toBlow) {
            if (this.random.nextInt(3) == 0
               && this.level.getBlockState(â˜ƒ).isAir()
               && this.level.getBlockState(â˜ƒ.below()).isSolidRender(this.level, â˜ƒ.below())) {
               this.level.setBlockAndUpdate(â˜ƒ, BaseFireBlock.getState(this.level, â˜ƒ));
            }
         }
      }
   }

   private static void addBlockDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> var0, ItemStack var1, BlockPos var2) {
      int â˜ƒ = â˜ƒ.size();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         Pair<ItemStack, BlockPos> â˜ƒxx = â˜ƒ.get(â˜ƒx);
         ItemStack â˜ƒxxx = â˜ƒxx.getFirst();
         if (ItemEntity.areMergable(â˜ƒxxx, â˜ƒ)) {
            ItemStack â˜ƒxxxx = ItemEntity.merge(â˜ƒxxx, â˜ƒ, 16);
            â˜ƒ.set(â˜ƒx, Pair.of(â˜ƒxxxx, â˜ƒxx.getSecond()));
            if (â˜ƒ.isEmpty()) {
               return;
            }
         }
      }

      â˜ƒ.add(Pair.of(â˜ƒ, â˜ƒ));
   }

   public DamageSource getDamageSource() {
      return this.damageSource;
   }

   public Map<Player, Vec3> getHitPlayers() {
      return this.hitPlayers;
   }

   @Nullable
   public LivingEntity getSourceMob() {
      if (this.source == null) {
         return null;
      } else if (this.source instanceof PrimedTnt) {
         return ((PrimedTnt)this.source).getOwner();
      } else if (this.source instanceof LivingEntity) {
         return (LivingEntity)this.source;
      } else {
         if (this.source instanceof Projectile) {
            Entity â˜ƒ = ((Projectile)this.source).getOwner();
            if (â˜ƒ instanceof LivingEntity) {
               return (LivingEntity)â˜ƒ;
            }
         }

         return null;
      }
   }

   public void clearToBlow() {
      this.toBlow.clear();
   }

   public List<BlockPos> getToBlow() {
      return this.toBlow;
   }

   public static enum BlockInteraction {
      NONE,
      BREAK,
      DESTROY;
   }
}
