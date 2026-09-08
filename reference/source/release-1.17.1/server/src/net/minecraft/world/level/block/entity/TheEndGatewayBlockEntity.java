package net.minecraft.world.level.block.entity;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.Features;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TheEndGatewayBlockEntity extends TheEndPortalBlockEntity {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SPAWN_TIME = 200;
   private static final int COOLDOWN_TIME = 40;
   private static final int ATTENTION_INTERVAL = 2400;
   private static final int EVENT_COOLDOWN = 1;
   private static final int GATEWAY_HEIGHT_ABOVE_SURFACE = 10;
   private long age;
   private int teleportCooldown;
   @Nullable
   private BlockPos exitPortal;
   private boolean exactTeleport;

   public TheEndGatewayBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.END_GATEWAY, â˜ƒ, â˜ƒ);
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.putLong("Age", this.age);
      if (this.exitPortal != null) {
         â˜ƒ.put("ExitPortal", NbtUtils.writeBlockPos(this.exitPortal));
      }

      if (this.exactTeleport) {
         â˜ƒ.putBoolean("ExactTeleport", this.exactTeleport);
      }

      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.age = â˜ƒ.getLong("Age");
      if (â˜ƒ.contains("ExitPortal", 10)) {
         BlockPos â˜ƒ = NbtUtils.readBlockPos(â˜ƒ.getCompound("ExitPortal"));
         if (Level.isInSpawnableBounds(â˜ƒ)) {
            this.exitPortal = â˜ƒ;
         }
      }

      this.exactTeleport = â˜ƒ.getBoolean("ExactTeleport");
   }

   public static void beamAnimationTick(Level var0, BlockPos var1, BlockState var2, TheEndGatewayBlockEntity var3) {
      ++â˜ƒ.age;
      if (â˜ƒ.isCoolingDown()) {
         --â˜ƒ.teleportCooldown;
      }
   }

   public static void teleportTick(Level var0, BlockPos var1, BlockState var2, TheEndGatewayBlockEntity var3) {
      boolean â˜ƒ = â˜ƒ.isSpawning();
      boolean â˜ƒx = â˜ƒ.isCoolingDown();
      ++â˜ƒ.age;
      if (â˜ƒx) {
         --â˜ƒ.teleportCooldown;
      } else {
         List<Entity> â˜ƒ = â˜ƒ.getEntitiesOfClass(Entity.class, new AABB(â˜ƒ), TheEndGatewayBlockEntity::canEntityTeleport);
         if (!â˜ƒ.isEmpty()) {
            teleportEntity(â˜ƒ, â˜ƒ, â˜ƒ, (Entity)â˜ƒ.get(â˜ƒ.random.nextInt(â˜ƒ.size())), â˜ƒ);
         }

         if (â˜ƒ.age % 2400L == 0L) {
            triggerCooldown(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      if (â˜ƒ != â˜ƒ.isSpawning() || â˜ƒx != â˜ƒ.isCoolingDown()) {
         setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static boolean canEntityTeleport(Entity var0) {
      return EntitySelector.NO_SPECTATORS.test(â˜ƒ) && !â˜ƒ.getRootVehicle().isOnPortalCooldown();
   }

   public boolean isSpawning() {
      return this.age < 200L;
   }

   public boolean isCoolingDown() {
      return this.teleportCooldown > 0;
   }

   public float getSpawnPercent(float var1) {
      return Mth.clamp(((float)this.age + â˜ƒ) / 200.0F, 0.0F, 1.0F);
   }

   public float getCooldownPercent(float var1) {
      return 1.0F - Mth.clamp(((float)this.teleportCooldown - â˜ƒ) / 40.0F, 0.0F, 1.0F);
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 8, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   private static void triggerCooldown(Level var0, BlockPos var1, BlockState var2, TheEndGatewayBlockEntity var3) {
      if (!â˜ƒ.isClientSide) {
         â˜ƒ.teleportCooldown = 40;
         â˜ƒ.blockEvent(â˜ƒ, â˜ƒ.getBlock(), 1, 0);
         setChanged(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean triggerEvent(int var1, int var2) {
      if (â˜ƒ == 1) {
         this.teleportCooldown = 40;
         return true;
      } else {
         return super.triggerEvent(â˜ƒ, â˜ƒ);
      }
   }

   public static void teleportEntity(Level var0, BlockPos var1, BlockState var2, Entity var3, TheEndGatewayBlockEntity var4) {
      if (â˜ƒ instanceof ServerLevel â˜ƒ && !â˜ƒ.isCoolingDown()) {
         â˜ƒ.teleportCooldown = 100;
         if (â˜ƒ.exitPortal == null && â˜ƒ.dimension() == Level.END) {
            BlockPos â˜ƒx = findOrCreateValidTeleportPos(â˜ƒ, â˜ƒ);
            â˜ƒx = â˜ƒx.above(10);
            LOGGER.debug("Creating portal at {}", â˜ƒx);
            spawnGatewayPortal(â˜ƒ, â˜ƒx, EndGatewayConfiguration.knownExit(â˜ƒ, false));
            â˜ƒ.exitPortal = â˜ƒx;
         }

         if (â˜ƒ.exitPortal != null) {
            BlockPos â˜ƒxx = â˜ƒ.exactTeleport ? â˜ƒ.exitPortal : findExitPosition(â˜ƒ, â˜ƒ.exitPortal);
            Entity â˜ƒx;
            if (â˜ƒ instanceof ThrownEnderpearl) {
               Entity â˜ƒxxx = ((ThrownEnderpearl)â˜ƒ).getOwner();
               if (â˜ƒxxx instanceof ServerPlayer) {
                  CriteriaTriggers.ENTER_BLOCK.trigger((ServerPlayer)â˜ƒxxx, â˜ƒ);
               }

               if (â˜ƒxxx != null) {
                  â˜ƒx = â˜ƒxxx;
                  â˜ƒ.discard();
               } else {
                  â˜ƒx = â˜ƒ;
               }
            } else {
               â˜ƒx = â˜ƒ.getRootVehicle();
            }

            â˜ƒx.setPortalCooldown();
            â˜ƒx.teleportToWithTicket((double)â˜ƒxx.getX() + 0.5, (double)â˜ƒxx.getY(), (double)â˜ƒxx.getZ() + 0.5);
         }

         triggerCooldown(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static BlockPos findExitPosition(Level var0, BlockPos var1) {
      BlockPos â˜ƒ = findTallestBlock(â˜ƒ, â˜ƒ.offset(0, 2, 0), 5, false);
      LOGGER.debug("Best exit position for portal at {} is {}", â˜ƒ, â˜ƒ);
      return â˜ƒ.above();
   }

   private static BlockPos findOrCreateValidTeleportPos(ServerLevel var0, BlockPos var1) {
      Vec3 â˜ƒ = findExitPortalXZPosTentative(â˜ƒ, â˜ƒ);
      LevelChunk â˜ƒx = getChunk(â˜ƒ, â˜ƒ);
      BlockPos â˜ƒxx = findValidSpawnInChunk(â˜ƒx);
      if (â˜ƒxx == null) {
         â˜ƒxx = new BlockPos(â˜ƒ.x + 0.5, 75.0, â˜ƒ.z + 0.5);
         LOGGER.debug("Failed to find a suitable block to teleport to, spawning an island on {}", â˜ƒxx);
         Features.END_ISLAND.place(â˜ƒ, â˜ƒ.getChunkSource().getGenerator(), new Random(â˜ƒxx.asLong()), â˜ƒxx);
      } else {
         LOGGER.debug("Found suitable block to teleport to: {}", â˜ƒxx);
      }

      return findTallestBlock(â˜ƒ, â˜ƒxx, 16, true);
   }

   private static Vec3 findExitPortalXZPosTentative(ServerLevel var0, BlockPos var1) {
      Vec3 â˜ƒ = new Vec3((double)â˜ƒ.getX(), 0.0, (double)â˜ƒ.getZ()).normalize();
      int â˜ƒx = 1024;
      Vec3 â˜ƒxx = â˜ƒ.scale(1024.0);

      for(int â˜ƒxxx = 16; !isChunkEmpty(â˜ƒ, â˜ƒxx) && â˜ƒxxx-- > 0; â˜ƒxx = â˜ƒxx.add(â˜ƒ.scale(-16.0))) {
         LOGGER.debug("Skipping backwards past nonempty chunk at {}", â˜ƒxx);
      }

      for(int var6 = 16; isChunkEmpty(â˜ƒ, â˜ƒxx) && var6-- > 0; â˜ƒxx = â˜ƒxx.add(â˜ƒ.scale(16.0))) {
         LOGGER.debug("Skipping forward past empty chunk at {}", â˜ƒxx);
      }

      LOGGER.debug("Found chunk at {}", â˜ƒxx);
      return â˜ƒxx;
   }

   private static boolean isChunkEmpty(ServerLevel var0, Vec3 var1) {
      return getChunk(â˜ƒ, â˜ƒ).getHighestSectionPosition() <= â˜ƒ.getMinBuildHeight();
   }

   private static BlockPos findTallestBlock(BlockGetter var0, BlockPos var1, int var2, boolean var3) {
      BlockPos â˜ƒ = null;

      for(int â˜ƒx = -â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
         for(int â˜ƒxx = -â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
            if (â˜ƒx != 0 || â˜ƒxx != 0 || â˜ƒ) {
               for(int â˜ƒxxx = â˜ƒ.getMaxBuildHeight() - 1; â˜ƒxxx > (â˜ƒ == null ? â˜ƒ.getMinBuildHeight() : â˜ƒ.getY()); --â˜ƒxxx) {
                  BlockPos â˜ƒxxxx = new BlockPos(â˜ƒ.getX() + â˜ƒx, â˜ƒxxx, â˜ƒ.getZ() + â˜ƒxx);
                  BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
                  if (â˜ƒxxxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxx) && (â˜ƒ || !â˜ƒxxxxx.is(Blocks.BEDROCK))) {
                     â˜ƒ = â˜ƒxxxx;
                     break;
                  }
               }
            }
         }
      }

      return â˜ƒ == null ? â˜ƒ : â˜ƒ;
   }

   private static LevelChunk getChunk(Level var0, Vec3 var1) {
      return â˜ƒ.getChunk(Mth.floor(â˜ƒ.x / 16.0), Mth.floor(â˜ƒ.z / 16.0));
   }

   @Nullable
   private static BlockPos findValidSpawnInChunk(LevelChunk var0) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getMinBlockX(), 30, â˜ƒ.getMinBlockZ());
      int â˜ƒxx = â˜ƒ.getHighestSectionPosition() + 16 - 1;
      BlockPos â˜ƒxxx = new BlockPos(â˜ƒ.getMaxBlockX(), â˜ƒxx, â˜ƒ.getMaxBlockZ());
      BlockPos â˜ƒxxxx = null;
      double â˜ƒxxxxx = 0.0;

      for(BlockPos â˜ƒxxxxxx : BlockPos.betweenClosed(â˜ƒx, â˜ƒxxx)) {
         BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
         BlockPos â˜ƒxxxxxxxx = â˜ƒxxxxxx.above();
         BlockPos â˜ƒxxxxxxxxx = â˜ƒxxxxxx.above(2);
         if (â˜ƒxxxxxxx.is(Blocks.END_STONE)
            && !â˜ƒ.getBlockState(â˜ƒxxxxxxxx).isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxxxxx)
            && !â˜ƒ.getBlockState(â˜ƒxxxxxxxxx).isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxxxxxx)) {
            double â˜ƒxxxxxxxxxx = â˜ƒxxxxxx.distSqr(0.0, 0.0, 0.0, true);
            if (â˜ƒxxxx == null || â˜ƒxxxxxxxxxx < â˜ƒxxxxx) {
               â˜ƒxxxx = â˜ƒxxxxxx;
               â˜ƒxxxxx = â˜ƒxxxxxxxxxx;
            }
         }
      }

      return â˜ƒxxxx;
   }

   private static void spawnGatewayPortal(ServerLevel var0, BlockPos var1, EndGatewayConfiguration var2) {
      Feature.END_GATEWAY.configured(â˜ƒ).place(â˜ƒ, â˜ƒ.getChunkSource().getGenerator(), new Random(), â˜ƒ);
   }

   @Override
   public boolean shouldRenderFace(Direction var1) {
      return Block.shouldRenderFace(this.getBlockState(), this.level, this.getBlockPos(), â˜ƒ, this.getBlockPos().relative(â˜ƒ));
   }

   public int getParticleAmount() {
      int â˜ƒ = 0;

      for(Direction â˜ƒx : Direction.values()) {
         â˜ƒ += this.shouldRenderFace(â˜ƒx) ? 1 : 0;
      }

      return â˜ƒ;
   }

   public void setExitPosition(BlockPos var1, boolean var2) {
      this.exactTeleport = â˜ƒ;
      this.exitPortal = â˜ƒ;
   }
}
