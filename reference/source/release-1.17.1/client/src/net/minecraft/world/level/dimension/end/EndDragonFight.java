package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.Features;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EndDragonFight {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int MAX_TICKS_BEFORE_DRAGON_RESPAWN = 1200;
   private static final int TIME_BETWEEN_CRYSTAL_SCANS = 100;
   private static final int TIME_BETWEEN_PLAYER_SCANS = 20;
   private static final int ARENA_SIZE_CHUNKS = 8;
   public static final int ARENA_TICKET_LEVEL = 9;
   private static final int GATEWAY_COUNT = 20;
   private static final int GATEWAY_DISTANCE = 96;
   public static final int DRAGON_SPAWN_Y = 128;
   private static final Predicate<Entity> VALID_PLAYER = EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0.0, 128.0, 0.0, 192.0));
   private final ServerBossEvent dragonEvent = (ServerBossEvent)new ServerBossEvent(
         new TranslatableComponent("entity.minecraft.ender_dragon"), BossEvent.BossBarColor.PINK, BossEvent.BossBarOverlay.PROGRESS
      )
      .setPlayBossMusic(true)
      .setCreateWorldFog(true);
   private final ServerLevel level;
   private final List<Integer> gateways = Lists.newArrayList();
   private final BlockPattern exitPortalPattern;
   private int ticksSinceDragonSeen;
   private int crystalsAlive;
   private int ticksSinceCrystalsScanned;
   private int ticksSinceLastPlayerScan;
   private boolean dragonKilled;
   private boolean previouslyKilled;
   private UUID dragonUUID;
   private boolean needsStateScanning = true;
   private BlockPos portalLocation;
   private DragonRespawnAnimation respawnStage;
   private int respawnTime;
   private List<EndCrystal> respawnCrystals;

   public EndDragonFight(ServerLevel var1, long var2, CompoundTag var4) {
      this.level = â˜ƒ;
      if (â˜ƒ.contains("NeedsStateScanning")) {
         this.needsStateScanning = â˜ƒ.getBoolean("NeedsStateScanning");
      }

      if (â˜ƒ.contains("DragonKilled", 99)) {
         if (â˜ƒ.hasUUID("Dragon")) {
            this.dragonUUID = â˜ƒ.getUUID("Dragon");
         }

         this.dragonKilled = â˜ƒ.getBoolean("DragonKilled");
         this.previouslyKilled = â˜ƒ.getBoolean("PreviouslyKilled");
         if (â˜ƒ.getBoolean("IsRespawning")) {
            this.respawnStage = DragonRespawnAnimation.START;
         }

         if (â˜ƒ.contains("ExitPortalLocation", 10)) {
            this.portalLocation = NbtUtils.readBlockPos(â˜ƒ.getCompound("ExitPortalLocation"));
         }
      } else {
         this.dragonKilled = true;
         this.previouslyKilled = true;
      }

      if (â˜ƒ.contains("Gateways", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("Gateways", 3);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            this.gateways.add(â˜ƒ.getInt(â˜ƒx));
         }
      } else {
         this.gateways.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
         Collections.shuffle(this.gateways, new Random(â˜ƒ));
      }

      this.exitPortalPattern = BlockPatternBuilder.start()
         .aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ")
         .aisle("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ")
         .aisle("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ")
         .where('#', BlockInWorld.hasState(BlockPredicate.forBlock(Blocks.BEDROCK)))
         .build();
   }

   public CompoundTag saveData() {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putBoolean("NeedsStateScanning", this.needsStateScanning);
      if (this.dragonUUID != null) {
         â˜ƒ.putUUID("Dragon", this.dragonUUID);
      }

      â˜ƒ.putBoolean("DragonKilled", this.dragonKilled);
      â˜ƒ.putBoolean("PreviouslyKilled", this.previouslyKilled);
      if (this.portalLocation != null) {
         â˜ƒ.put("ExitPortalLocation", NbtUtils.writeBlockPos(this.portalLocation));
      }

      ListTag â˜ƒ = new ListTag();

      for(int â˜ƒx : this.gateways) {
         â˜ƒ.add(IntTag.valueOf(â˜ƒx));
      }

      â˜ƒ.put("Gateways", â˜ƒ);
      return â˜ƒ;
   }

   public void tick() {
      this.dragonEvent.setVisible(!this.dragonKilled);
      if (++this.ticksSinceLastPlayerScan >= 20) {
         this.updatePlayers();
         this.ticksSinceLastPlayerScan = 0;
      }

      if (!this.dragonEvent.getPlayers().isEmpty()) {
         this.level.getChunkSource().addRegionTicket(TicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
         boolean â˜ƒ = this.isArenaLoaded();
         if (this.needsStateScanning && â˜ƒ) {
            this.scanState();
            this.needsStateScanning = false;
         }

         if (this.respawnStage != null) {
            if (this.respawnCrystals == null && â˜ƒ) {
               this.respawnStage = null;
               this.tryRespawn();
            }

            this.respawnStage.tick(this.level, this, this.respawnCrystals, this.respawnTime++, this.portalLocation);
         }

         if (!this.dragonKilled) {
            if ((this.dragonUUID == null || ++this.ticksSinceDragonSeen >= 1200) && â˜ƒ) {
               this.findOrCreateDragon();
               this.ticksSinceDragonSeen = 0;
            }

            if (++this.ticksSinceCrystalsScanned >= 100 && â˜ƒ) {
               this.updateCrystalCount();
               this.ticksSinceCrystalsScanned = 0;
            }
         }
      } else {
         this.level.getChunkSource().removeRegionTicket(TicketType.DRAGON, new ChunkPos(0, 0), 9, Unit.INSTANCE);
      }
   }

   private void scanState() {
      LOGGER.info("Scanning for legacy world dragon fight...");
      boolean â˜ƒ = this.hasActiveExitPortal();
      if (â˜ƒ) {
         LOGGER.info("Found that the dragon has been killed in this world already.");
         this.previouslyKilled = true;
      } else {
         LOGGER.info("Found that the dragon has not yet been killed in this world.");
         this.previouslyKilled = false;
         if (this.findExitPortal() == null) {
            this.spawnExitPortal(false);
         }
      }

      List<? extends EnderDragon> â˜ƒ = this.level.getDragons();
      if (â˜ƒ.isEmpty()) {
         this.dragonKilled = true;
      } else {
         EnderDragon â˜ƒ = (EnderDragon)â˜ƒ.get(0);
         this.dragonUUID = â˜ƒ.getUUID();
         LOGGER.info("Found that there's a dragon still alive ({})", â˜ƒ);
         this.dragonKilled = false;
         if (!â˜ƒ) {
            LOGGER.info("But we didn't have a portal, let's remove it.");
            â˜ƒ.discard();
            this.dragonUUID = null;
         }
      }

      if (!this.previouslyKilled && this.dragonKilled) {
         this.dragonKilled = false;
      }
   }

   private void findOrCreateDragon() {
      List<? extends EnderDragon> â˜ƒ = this.level.getDragons();
      if (â˜ƒ.isEmpty()) {
         LOGGER.debug("Haven't seen the dragon, respawning it");
         this.createNewDragon();
      } else {
         LOGGER.debug("Haven't seen our dragon, but found another one to use.");
         this.dragonUUID = ((EnderDragon)â˜ƒ.get(0)).getUUID();
      }
   }

   protected void setRespawnStage(DragonRespawnAnimation var1) {
      if (this.respawnStage == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.respawnTime = 0;
         if (â˜ƒ == DragonRespawnAnimation.END) {
            this.respawnStage = null;
            this.dragonKilled = false;
            EnderDragon â˜ƒ = this.createNewDragon();

            for(ServerPlayer â˜ƒx : this.dragonEvent.getPlayers()) {
               CriteriaTriggers.SUMMONED_ENTITY.trigger(â˜ƒx, â˜ƒ);
            }
         } else {
            this.respawnStage = â˜ƒ;
         }
      }
   }

   private boolean hasActiveExitPortal() {
      for(int â˜ƒ = -8; â˜ƒ <= 8; ++â˜ƒ) {
         for(int â˜ƒx = -8; â˜ƒx <= 8; ++â˜ƒx) {
            LevelChunk â˜ƒxx = this.level.getChunk(â˜ƒ, â˜ƒx);

            for(BlockEntity â˜ƒxxx : â˜ƒxx.getBlockEntities().values()) {
               if (â˜ƒxxx instanceof TheEndPortalBlockEntity) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockPattern.BlockPatternMatch findExitPortal() {
      for(int â˜ƒ = -8; â˜ƒ <= 8; ++â˜ƒ) {
         for(int â˜ƒx = -8; â˜ƒx <= 8; ++â˜ƒx) {
            LevelChunk â˜ƒxx = this.level.getChunk(â˜ƒ, â˜ƒx);

            for(BlockEntity â˜ƒxxx : â˜ƒxx.getBlockEntities().values()) {
               if (â˜ƒxxx instanceof TheEndPortalBlockEntity) {
                  BlockPattern.BlockPatternMatch â˜ƒxxxx = this.exitPortalPattern.find(this.level, â˜ƒxxx.getBlockPos());
                  if (â˜ƒxxxx != null) {
                     BlockPos â˜ƒxxxxx = â˜ƒxxxx.getBlock(3, 3, 3).getPos();
                     if (this.portalLocation == null) {
                        this.portalLocation = â˜ƒxxxxx;
                     }

                     return â˜ƒxxxx;
                  }
               }
            }
         }
      }

      int â˜ƒ = this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION).getY();

      for(int â˜ƒx = â˜ƒ; â˜ƒx >= this.level.getMinBuildHeight(); --â˜ƒx) {
         BlockPattern.BlockPatternMatch â˜ƒxx = this.exitPortalPattern
            .find(this.level, new BlockPos(EndPodiumFeature.END_PODIUM_LOCATION.getX(), â˜ƒx, EndPodiumFeature.END_PODIUM_LOCATION.getZ()));
         if (â˜ƒxx != null) {
            if (this.portalLocation == null) {
               this.portalLocation = â˜ƒxx.getBlock(3, 3, 3).getPos();
            }

            return â˜ƒxx;
         }
      }

      return null;
   }

   private boolean isArenaLoaded() {
      for(int â˜ƒ = -8; â˜ƒ <= 8; ++â˜ƒ) {
         for(int â˜ƒx = 8; â˜ƒx <= 8; ++â˜ƒx) {
            ChunkAccess â˜ƒxx = this.level.getChunk(â˜ƒ, â˜ƒx, ChunkStatus.FULL, false);
            if (!(â˜ƒxx instanceof LevelChunk)) {
               return false;
            }

            ChunkHolder.FullChunkStatus â˜ƒxx = ((LevelChunk)â˜ƒxx).getFullStatus();
            if (!â˜ƒxx.isOrAfter(ChunkHolder.FullChunkStatus.TICKING)) {
               return false;
            }
         }
      }

      return true;
   }

   private void updatePlayers() {
      Set<ServerPlayer> â˜ƒ = Sets.<ServerPlayer>newHashSet();

      for(ServerPlayer â˜ƒx : this.level.getPlayers(VALID_PLAYER)) {
         this.dragonEvent.addPlayer(â˜ƒx);
         â˜ƒ.add(â˜ƒx);
      }

      Set<ServerPlayer> â˜ƒx = Sets.<ServerPlayer>newHashSet(this.dragonEvent.getPlayers());
      â˜ƒx.removeAll(â˜ƒ);

      for(ServerPlayer â˜ƒxx : â˜ƒx) {
         this.dragonEvent.removePlayer(â˜ƒxx);
      }
   }

   private void updateCrystalCount() {
      this.ticksSinceCrystalsScanned = 0;
      this.crystalsAlive = 0;

      for(SpikeFeature.EndSpike â˜ƒ : SpikeFeature.getSpikesForLevel(this.level)) {
         this.crystalsAlive += this.level.getEntitiesOfClass(EndCrystal.class, â˜ƒ.getTopBoundingBox()).size();
      }

      LOGGER.debug("Found {} end crystals still alive", this.crystalsAlive);
   }

   public void setDragonKilled(EnderDragon var1) {
      if (â˜ƒ.getUUID().equals(this.dragonUUID)) {
         this.dragonEvent.setProgress(0.0F);
         this.dragonEvent.setVisible(false);
         this.spawnExitPortal(true);
         this.spawnNewGateway();
         if (!this.previouslyKilled) {
            this.level
               .setBlockAndUpdate(
                  this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.defaultBlockState()
               );
         }

         this.previouslyKilled = true;
         this.dragonKilled = true;
      }
   }

   private void spawnNewGateway() {
      if (!this.gateways.isEmpty()) {
         int â˜ƒ = this.gateways.remove(this.gateways.size() - 1);
         int â˜ƒx = Mth.floor(96.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 20) * (double)â˜ƒ)));
         int â˜ƒxx = Mth.floor(96.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 20) * (double)â˜ƒ)));
         this.spawnNewGateway(new BlockPos(â˜ƒx, 75, â˜ƒxx));
      }
   }

   private void spawnNewGateway(BlockPos var1) {
      this.level.levelEvent(3000, â˜ƒ, 0);
      Features.END_GATEWAY_DELAYED.place(this.level, this.level.getChunkSource().getGenerator(), new Random(), â˜ƒ);
   }

   private void spawnExitPortal(boolean var1) {
      EndPodiumFeature â˜ƒ = new EndPodiumFeature(â˜ƒ);
      if (this.portalLocation == null) {
         this.portalLocation = this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).below();

         while(this.level.getBlockState(this.portalLocation).is(Blocks.BEDROCK) && this.portalLocation.getY() > this.level.getSeaLevel()) {
            this.portalLocation = this.portalLocation.below();
         }
      }

      â˜ƒ.configured(FeatureConfiguration.NONE).place(this.level, this.level.getChunkSource().getGenerator(), new Random(), this.portalLocation);
   }

   private EnderDragon createNewDragon() {
      this.level.getChunkAt(new BlockPos(0, 128, 0));
      EnderDragon â˜ƒ = EntityType.ENDER_DRAGON.create(this.level);
      â˜ƒ.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
      â˜ƒ.moveTo(0.0, 128.0, 0.0, this.level.random.nextFloat() * 360.0F, 0.0F);
      this.level.addFreshEntity(â˜ƒ);
      this.dragonUUID = â˜ƒ.getUUID();
      return â˜ƒ;
   }

   public void updateDragon(EnderDragon var1) {
      if (â˜ƒ.getUUID().equals(this.dragonUUID)) {
         this.dragonEvent.setProgress(â˜ƒ.getHealth() / â˜ƒ.getMaxHealth());
         this.ticksSinceDragonSeen = 0;
         if (â˜ƒ.hasCustomName()) {
            this.dragonEvent.setName(â˜ƒ.getDisplayName());
         }
      }
   }

   public int getCrystalsAlive() {
      return this.crystalsAlive;
   }

   public void onCrystalDestroyed(EndCrystal var1, DamageSource var2) {
      if (this.respawnStage != null && this.respawnCrystals.contains(â˜ƒ)) {
         LOGGER.debug("Aborting respawn sequence");
         this.respawnStage = null;
         this.respawnTime = 0;
         this.resetSpikeCrystals();
         this.spawnExitPortal(true);
      } else {
         this.updateCrystalCount();
         Entity â˜ƒ = this.level.getEntity(this.dragonUUID);
         if (â˜ƒ instanceof EnderDragon) {
            ((EnderDragon)â˜ƒ).onCrystalDestroyed(â˜ƒ, â˜ƒ.blockPosition(), â˜ƒ);
         }
      }
   }

   public boolean hasPreviouslyKilledDragon() {
      return this.previouslyKilled;
   }

   public void tryRespawn() {
      if (this.dragonKilled && this.respawnStage == null) {
         BlockPos â˜ƒ = this.portalLocation;
         if (â˜ƒ == null) {
            LOGGER.debug("Tried to respawn, but need to find the portal first.");
            BlockPattern.BlockPatternMatch â˜ƒx = this.findExitPortal();
            if (â˜ƒx == null) {
               LOGGER.debug("Couldn't find a portal, so we made one.");
               this.spawnExitPortal(true);
            } else {
               LOGGER.debug("Found the exit portal & saved its location for next time.");
            }

            â˜ƒ = this.portalLocation;
         }

         List<EndCrystal> â˜ƒ = Lists.<EndCrystal>newArrayList();
         BlockPos â˜ƒx = â˜ƒ.above(1);

         for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
            List<EndCrystal> â˜ƒxxx = this.level.getEntitiesOfClass(EndCrystal.class, new AABB(â˜ƒx.relative(â˜ƒxx, 2)));
            if (â˜ƒxxx.isEmpty()) {
               return;
            }

            â˜ƒ.addAll(â˜ƒxxx);
         }

         LOGGER.debug("Found all crystals, respawning dragon.");
         this.respawnDragon(â˜ƒ);
      }
   }

   private void respawnDragon(List<EndCrystal> var1) {
      if (this.dragonKilled && this.respawnStage == null) {
         for(BlockPattern.BlockPatternMatch â˜ƒ = this.findExitPortal(); â˜ƒ != null; â˜ƒ = this.findExitPortal()) {
            for(int â˜ƒx = 0; â˜ƒx < this.exitPortalPattern.getWidth(); ++â˜ƒx) {
               for(int â˜ƒxx = 0; â˜ƒxx < this.exitPortalPattern.getHeight(); ++â˜ƒxx) {
                  for(int â˜ƒxxx = 0; â˜ƒxxx < this.exitPortalPattern.getDepth(); ++â˜ƒxxx) {
                     BlockInWorld â˜ƒxxxx = â˜ƒ.getBlock(â˜ƒx, â˜ƒxx, â˜ƒxxx);
                     if (â˜ƒxxxx.getState().is(Blocks.BEDROCK) || â˜ƒxxxx.getState().is(Blocks.END_PORTAL)) {
                        this.level.setBlockAndUpdate(â˜ƒxxxx.getPos(), Blocks.END_STONE.defaultBlockState());
                     }
                  }
               }
            }
         }

         this.respawnStage = DragonRespawnAnimation.START;
         this.respawnTime = 0;
         this.spawnExitPortal(false);
         this.respawnCrystals = â˜ƒ;
      }
   }

   public void resetSpikeCrystals() {
      for(SpikeFeature.EndSpike â˜ƒ : SpikeFeature.getSpikesForLevel(this.level)) {
         for(EndCrystal â˜ƒx : this.level.getEntitiesOfClass(EndCrystal.class, â˜ƒ.getTopBoundingBox())) {
            â˜ƒx.setInvulnerable(false);
            â˜ƒx.setBeamTarget(null);
         }
      }
   }
}
