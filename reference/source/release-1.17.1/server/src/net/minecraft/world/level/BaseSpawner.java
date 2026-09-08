package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringUtil;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseSpawner {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int EVENT_SPAWN = 1;
   private static WeightedRandomList<SpawnData> EMPTY_POTENTIALS = WeightedRandomList.create();
   private int spawnDelay = 20;
   private WeightedRandomList<SpawnData> spawnPotentials = EMPTY_POTENTIALS;
   private SpawnData nextSpawnData = new SpawnData();
   private double spin;
   private double oSpin;
   private int minSpawnDelay = 200;
   private int maxSpawnDelay = 800;
   private int spawnCount = 4;
   @Nullable
   private Entity displayEntity;
   private int maxNearbyEntities = 6;
   private int requiredPlayerRange = 16;
   private int spawnRange = 4;
   private final Random random = new Random();

   @Nullable
   private ResourceLocation getEntityId(@Nullable Level var1, BlockPos var2) {
      String â˜ƒ = this.nextSpawnData.getTag().getString("id");

      try {
         return StringUtil.isNullOrEmpty(â˜ƒ) ? null : new ResourceLocation(â˜ƒ);
      } catch (ResourceLocationException var5) {
         LOGGER.warn(
            "Invalid entity id '{}' at spawner {}:[{},{},{}]", â˜ƒ, â˜ƒ != null ? â˜ƒ.dimension().location() : "<null>", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ()
         );
         return null;
      }
   }

   public void setEntityId(EntityType<?> var1) {
      this.nextSpawnData.getTag().putString("id", Registry.ENTITY_TYPE.getKey(â˜ƒ).toString());
   }

   private boolean isNearPlayer(Level var1, BlockPos var2) {
      return â˜ƒ.hasNearbyAlivePlayer((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, (double)this.requiredPlayerRange);
   }

   public void clientTick(Level var1, BlockPos var2) {
      if (!this.isNearPlayer(â˜ƒ, â˜ƒ)) {
         this.oSpin = this.spin;
      } else {
         double â˜ƒ = (double)â˜ƒ.getX() + â˜ƒ.random.nextDouble();
         double â˜ƒx = (double)â˜ƒ.getY() + â˜ƒ.random.nextDouble();
         double â˜ƒxx = (double)â˜ƒ.getZ() + â˜ƒ.random.nextDouble();
         â˜ƒ.addParticle(ParticleTypes.SMOKE, â˜ƒ, â˜ƒx, â˜ƒxx, 0.0, 0.0, 0.0);
         â˜ƒ.addParticle(ParticleTypes.FLAME, â˜ƒ, â˜ƒx, â˜ƒxx, 0.0, 0.0, 0.0);
         if (this.spawnDelay > 0) {
            --this.spawnDelay;
         }

         this.oSpin = this.spin;
         this.spin = (this.spin + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0;
      }
   }

   public void serverTick(ServerLevel var1, BlockPos var2) {
      if (this.isNearPlayer(â˜ƒ, â˜ƒ)) {
         if (this.spawnDelay == -1) {
            this.delay(â˜ƒ, â˜ƒ);
         }

         if (this.spawnDelay > 0) {
            --this.spawnDelay;
         } else {
            boolean â˜ƒ = false;

            for(int â˜ƒx = 0; â˜ƒx < this.spawnCount; ++â˜ƒx) {
               CompoundTag â˜ƒxx = this.nextSpawnData.getTag();
               Optional<EntityType<?>> â˜ƒxxx = EntityType.by(â˜ƒxx);
               if (!â˜ƒxxx.isPresent()) {
                  this.delay(â˜ƒ, â˜ƒ);
                  return;
               }

               ListTag â˜ƒxx = â˜ƒxx.getList("Pos", 6);
               int â˜ƒxxx = â˜ƒxx.size();
               double â˜ƒxxxx = â˜ƒxxx >= 1
                  ? â˜ƒxx.getDouble(0)
                  : (double)â˜ƒ.getX() + (â˜ƒ.random.nextDouble() - â˜ƒ.random.nextDouble()) * (double)this.spawnRange + 0.5;
               double â˜ƒxxxxx = â˜ƒxxx >= 2 ? â˜ƒxx.getDouble(1) : (double)(â˜ƒ.getY() + â˜ƒ.random.nextInt(3) - 1);
               double â˜ƒxxxxxx = â˜ƒxxx >= 3
                  ? â˜ƒxx.getDouble(2)
                  : (double)â˜ƒ.getZ() + (â˜ƒ.random.nextDouble() - â˜ƒ.random.nextDouble()) * (double)this.spawnRange + 0.5;
               if (â˜ƒ.noCollision(((EntityType)â˜ƒxxx.get()).getAABB(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx))
                  && SpawnPlacements.checkSpawnRules(
                     (EntityType)â˜ƒxxx.get(), â˜ƒ, MobSpawnType.SPAWNER, new BlockPos(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx), â˜ƒ.getRandom()
                  )) {
                  Entity â˜ƒxxxxxxx = EntityType.loadEntityRecursive(â˜ƒxx, â˜ƒ, var6x -> {
                     var6x.moveTo(â˜ƒ, â˜ƒ, â˜ƒ, var6x.getYRot(), var6x.getXRot());
                     return var6x;
                  });
                  if (â˜ƒxxxxxxx == null) {
                     this.delay(â˜ƒ, â˜ƒ);
                     return;
                  }

                  int â˜ƒxxxxxxx = â˜ƒ.getEntitiesOfClass(
                        â˜ƒxxxxxxx.getClass(),
                        new AABB(
                              (double)â˜ƒ.getX(),
                              (double)â˜ƒ.getY(),
                              (double)â˜ƒ.getZ(),
                              (double)(â˜ƒ.getX() + 1),
                              (double)(â˜ƒ.getY() + 1),
                              (double)(â˜ƒ.getZ() + 1)
                           )
                           .inflate((double)this.spawnRange)
                     )
                     .size();
                  if (â˜ƒxxxxxxx >= this.maxNearbyEntities) {
                     this.delay(â˜ƒ, â˜ƒ);
                     return;
                  }

                  â˜ƒxxxxxxx.moveTo(â˜ƒxxxxxxx.getX(), â˜ƒxxxxxxx.getY(), â˜ƒxxxxxxx.getZ(), â˜ƒ.random.nextFloat() * 360.0F, 0.0F);
                  if (â˜ƒxxxxxxx instanceof Mob â˜ƒxxxxxxx) {
                     if (!â˜ƒxxxxxxx.checkSpawnRules(â˜ƒ, MobSpawnType.SPAWNER) || !â˜ƒxxxxxxx.checkSpawnObstruction(â˜ƒ)) {
                        continue;
                     }

                     if (this.nextSpawnData.getTag().size() == 1 && this.nextSpawnData.getTag().contains("id", 8)) {
                        ((Mob)â˜ƒxxxxxxx).finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒxxxxxxx.blockPosition()), MobSpawnType.SPAWNER, null, null);
                     }
                  }

                  if (!â˜ƒ.tryAddFreshEntityWithPassengers(â˜ƒxxxxxxx)) {
                     this.delay(â˜ƒ, â˜ƒ);
                     return;
                  }

                  â˜ƒ.levelEvent(2004, â˜ƒ, 0);
                  if (â˜ƒxxxxxxx instanceof Mob) {
                     ((Mob)â˜ƒxxxxxxx).spawnAnim();
                  }

                  â˜ƒ = true;
               }
            }

            if (â˜ƒ) {
               this.delay(â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   private void delay(Level var1, BlockPos var2) {
      if (this.maxSpawnDelay <= this.minSpawnDelay) {
         this.spawnDelay = this.minSpawnDelay;
      } else {
         this.spawnDelay = this.minSpawnDelay + this.random.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
      }

      this.spawnPotentials.getRandom(this.random).ifPresent(var3 -> this.setNextSpawnData(â˜ƒ, â˜ƒ, var3));
      this.broadcastEvent(â˜ƒ, â˜ƒ, 1);
   }

   public void load(@Nullable Level var1, BlockPos var2, CompoundTag var3) {
      this.spawnDelay = â˜ƒ.getShort("Delay");
      List<SpawnData> â˜ƒ = Lists.<SpawnData>newArrayList();
      if (â˜ƒ.contains("SpawnPotentials", 9)) {
         ListTag â˜ƒx = â˜ƒ.getList("SpawnPotentials", 10);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            â˜ƒ.add(new SpawnData(â˜ƒx.getCompound(â˜ƒxx)));
         }
      }

      this.spawnPotentials = WeightedRandomList.create(â˜ƒ);
      if (â˜ƒ.contains("SpawnData", 10)) {
         this.setNextSpawnData(â˜ƒ, â˜ƒ, new SpawnData(1, â˜ƒ.getCompound("SpawnData")));
      } else if (!â˜ƒ.isEmpty()) {
         this.spawnPotentials.getRandom(this.random).ifPresent(var3x -> this.setNextSpawnData(â˜ƒ, â˜ƒ, var3x));
      }

      if (â˜ƒ.contains("MinSpawnDelay", 99)) {
         this.minSpawnDelay = â˜ƒ.getShort("MinSpawnDelay");
         this.maxSpawnDelay = â˜ƒ.getShort("MaxSpawnDelay");
         this.spawnCount = â˜ƒ.getShort("SpawnCount");
      }

      if (â˜ƒ.contains("MaxNearbyEntities", 99)) {
         this.maxNearbyEntities = â˜ƒ.getShort("MaxNearbyEntities");
         this.requiredPlayerRange = â˜ƒ.getShort("RequiredPlayerRange");
      }

      if (â˜ƒ.contains("SpawnRange", 99)) {
         this.spawnRange = â˜ƒ.getShort("SpawnRange");
      }

      this.displayEntity = null;
   }

   public CompoundTag save(@Nullable Level var1, BlockPos var2, CompoundTag var3) {
      ResourceLocation â˜ƒ = this.getEntityId(â˜ƒ, â˜ƒ);
      if (â˜ƒ == null) {
         return â˜ƒ;
      } else {
         â˜ƒ.putShort("Delay", (short)this.spawnDelay);
         â˜ƒ.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
         â˜ƒ.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
         â˜ƒ.putShort("SpawnCount", (short)this.spawnCount);
         â˜ƒ.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
         â˜ƒ.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
         â˜ƒ.putShort("SpawnRange", (short)this.spawnRange);
         â˜ƒ.put("SpawnData", this.nextSpawnData.getTag().copy());
         ListTag â˜ƒ = new ListTag();
         if (this.spawnPotentials.isEmpty()) {
            â˜ƒ.add(this.nextSpawnData.save());
         } else {
            for(SpawnData â˜ƒ : this.spawnPotentials.unwrap()) {
               â˜ƒ.add(â˜ƒ.save());
            }
         }

         â˜ƒ.put("SpawnPotentials", â˜ƒ);
         return â˜ƒ;
      }
   }

   @Nullable
   public Entity getOrCreateDisplayEntity(Level var1) {
      if (this.displayEntity == null) {
         this.displayEntity = EntityType.loadEntityRecursive(this.nextSpawnData.getTag(), â˜ƒ, Function.identity());
         if (this.nextSpawnData.getTag().size() == 1 && this.nextSpawnData.getTag().contains("id", 8) && this.displayEntity instanceof Mob) {
         }
      }

      return this.displayEntity;
   }

   public boolean onEventTriggered(Level var1, int var2) {
      if (â˜ƒ == 1) {
         if (â˜ƒ.isClientSide) {
            this.spawnDelay = this.minSpawnDelay;
         }

         return true;
      } else {
         return false;
      }
   }

   public void setNextSpawnData(@Nullable Level var1, BlockPos var2, SpawnData var3) {
      this.nextSpawnData = â˜ƒ;
   }

   public abstract void broadcastEvent(Level var1, BlockPos var2, int var3);

   public double getSpin() {
      return this.spin;
   }

   public double getoSpin() {
      return this.oSpin;
   }
}
