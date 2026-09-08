package net.minecraft.client.multiplayer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.EmptyTickList;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.entity.LevelCallback;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.Scoreboard;

public class ClientLevel extends Level {
   private static final double FLUID_PARTICLE_SPAWN_OFFSET = 0.05;
   final EntityTickList tickingEntities = new EntityTickList();
   private final TransientEntitySectionManager<Entity> entityStorage = new TransientEntitySectionManager<>(Entity.class, new ClientLevel.EntityCallbacks());
   private final ClientPacketListener connection;
   private final LevelRenderer levelRenderer;
   private final ClientLevel.ClientLevelData clientLevelData;
   private final DimensionSpecialEffects effects;
   private final Minecraft minecraft = Minecraft.getInstance();
   final List<AbstractClientPlayer> players = Lists.<AbstractClientPlayer>newArrayList();
   private Scoreboard scoreboard = new Scoreboard();
   private final Map<String, MapItemSavedData> mapData = Maps.newHashMap();
   private static final long CLOUD_COLOR = 16777215L;
   private int skyFlashTime;
   private final Object2ObjectArrayMap<ColorResolver, BlockTintCache> tintCaches = Util.make(new Object2ObjectArrayMap<>(3), var0 -> {
      var0.put(BiomeColors.GRASS_COLOR_RESOLVER, new BlockTintCache());
      var0.put(BiomeColors.FOLIAGE_COLOR_RESOLVER, new BlockTintCache());
      var0.put(BiomeColors.WATER_COLOR_RESOLVER, new BlockTintCache());
   });
   private final ClientChunkCache chunkSource;

   public ClientLevel(
      ClientPacketListener var1,
      ClientLevel.ClientLevelData var2,
      ResourceKey<Level> var3,
      DimensionType var4,
      int var5,
      Supplier<ProfilerFiller> var6,
      LevelRenderer var7,
      boolean var8,
      long var9
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, â˜ƒ, â˜ƒ);
      this.connection = â˜ƒ;
      this.chunkSource = new ClientChunkCache(this, â˜ƒ);
      this.clientLevelData = â˜ƒ;
      this.levelRenderer = â˜ƒ;
      this.effects = DimensionSpecialEffects.forType(â˜ƒ);
      this.setDefaultSpawnPos(new BlockPos(8, 64, 8), 0.0F);
      this.updateSkyBrightness();
      this.prepareWeather();
   }

   public DimensionSpecialEffects effects() {
      return this.effects;
   }

   public void tick(BooleanSupplier var1) {
      this.getWorldBorder().tick();
      this.tickTime();
      this.getProfiler().push("blocks");
      this.chunkSource.tick(â˜ƒ);
      this.getProfiler().pop();
   }

   private void tickTime() {
      this.setGameTime(this.levelData.getGameTime() + 1L);
      if (this.levelData.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
         this.setDayTime(this.levelData.getDayTime() + 1L);
      }
   }

   public void setGameTime(long var1) {
      this.clientLevelData.setGameTime(â˜ƒ);
   }

   public void setDayTime(long var1) {
      if (â˜ƒ < 0L) {
         â˜ƒ = -â˜ƒ;
         this.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(false, null);
      } else {
         this.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(true, null);
      }

      this.clientLevelData.setDayTime(â˜ƒ);
   }

   public Iterable<Entity> entitiesForRendering() {
      return this.getEntities().getAll();
   }

   public void tickEntities() {
      ProfilerFiller â˜ƒ = this.getProfiler();
      â˜ƒ.push("entities");
      this.tickingEntities.forEach(var1x -> {
         if (!var1x.isRemoved() && !var1x.isPassenger()) {
            this.guardEntityTick(this::tickNonPassenger, var1x);
         }
      });
      â˜ƒ.pop();
      this.tickBlockEntities();
   }

   public void tickNonPassenger(Entity var1) {
      â˜ƒ.setOldPosAndRot();
      ++â˜ƒ.tickCount;
      this.getProfiler().push((Supplier<String>)(() -> Registry.ENTITY_TYPE.getKey(â˜ƒ.getType()).toString()));
      â˜ƒ.tick();
      this.getProfiler().pop();

      for(Entity â˜ƒ : â˜ƒ.getPassengers()) {
         this.tickPassenger(â˜ƒ, â˜ƒ);
      }
   }

   private void tickPassenger(Entity var1, Entity var2) {
      if (â˜ƒ.isRemoved() || â˜ƒ.getVehicle() != â˜ƒ) {
         â˜ƒ.stopRiding();
      } else if (â˜ƒ instanceof Player || this.tickingEntities.contains(â˜ƒ)) {
         â˜ƒ.setOldPosAndRot();
         ++â˜ƒ.tickCount;
         â˜ƒ.rideTick();

         for(Entity â˜ƒ : â˜ƒ.getPassengers()) {
            this.tickPassenger(â˜ƒ, â˜ƒ);
         }
      }
   }

   public void unload(LevelChunk var1) {
      â˜ƒ.invalidateAllBlockEntities();
      this.chunkSource.getLightEngine().enableLightSources(â˜ƒ.getPos(), false);
      this.entityStorage.stopTicking(â˜ƒ.getPos());
   }

   public void onChunkLoaded(ChunkPos var1) {
      this.tintCaches.forEach((var1x, var2) -> var2.invalidateForChunk(â˜ƒ.x, â˜ƒ.z));
      this.entityStorage.startTicking(â˜ƒ);
   }

   public void clearTintCaches() {
      this.tintCaches.forEach((var0, var1) -> var1.invalidateAll());
   }

   @Override
   public boolean hasChunk(int var1, int var2) {
      return true;
   }

   public int getEntityCount() {
      return this.entityStorage.count();
   }

   public void addPlayer(int var1, AbstractClientPlayer var2) {
      this.addEntity(â˜ƒ, â˜ƒ);
   }

   public void putNonPlayerEntity(int var1, Entity var2) {
      this.addEntity(â˜ƒ, â˜ƒ);
   }

   private void addEntity(int var1, Entity var2) {
      this.removeEntity(â˜ƒ, Entity.RemovalReason.DISCARDED);
      this.entityStorage.addEntity(â˜ƒ);
   }

   public void removeEntity(int var1, Entity.RemovalReason var2) {
      Entity â˜ƒ = this.getEntities().get(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.setRemoved(â˜ƒ);
         â˜ƒ.onClientRemoval();
      }
   }

   @Nullable
   @Override
   public Entity getEntity(int var1) {
      return this.getEntities().get(â˜ƒ);
   }

   public void setKnownState(BlockPos var1, BlockState var2) {
      this.setBlock(â˜ƒ, â˜ƒ, 19);
   }

   @Override
   public void disconnect() {
      this.connection.getConnection().disconnect(new TranslatableComponent("multiplayer.status.quitting"));
   }

   public void animateTick(int var1, int var2, int var3) {
      int â˜ƒ = 32;
      Random â˜ƒx = new Random();
      ClientLevel.MarkerParticleStatus â˜ƒxx = this.getMarkerParticleStatus();
      BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 667; ++â˜ƒxxxx) {
         this.doAnimateTick(â˜ƒ, â˜ƒ, â˜ƒ, 16, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         this.doAnimateTick(â˜ƒ, â˜ƒ, â˜ƒ, 32, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }
   }

   @Nullable
   private ClientLevel.MarkerParticleStatus getMarkerParticleStatus() {
      if (this.minecraft.gameMode.getPlayerMode() == GameType.CREATIVE) {
         ItemStack â˜ƒ = this.minecraft.player.getMainHandItem();
         if (â˜ƒ.getItem() == Items.BARRIER) {
            return ClientLevel.MarkerParticleStatus.BARRIER;
         }

         if (â˜ƒ.getItem() == Items.LIGHT) {
            return ClientLevel.MarkerParticleStatus.LIGHT;
         }
      }

      return null;
   }

   public void doAnimateTick(
      int var1, int var2, int var3, int var4, Random var5, @Nullable ClientLevel.MarkerParticleStatus var6, BlockPos.MutableBlockPos var7
   ) {
      int â˜ƒ = â˜ƒ + this.random.nextInt(â˜ƒ) - this.random.nextInt(â˜ƒ);
      int â˜ƒx = â˜ƒ + this.random.nextInt(â˜ƒ) - this.random.nextInt(â˜ƒ);
      int â˜ƒxx = â˜ƒ + this.random.nextInt(â˜ƒ) - this.random.nextInt(â˜ƒ);
      â˜ƒ.set(â˜ƒ, â˜ƒx, â˜ƒxx);
      BlockState â˜ƒxxx = this.getBlockState(â˜ƒ);
      â˜ƒxxx.getBlock().animateTick(â˜ƒxxx, this, â˜ƒ, â˜ƒ);
      FluidState â˜ƒxxxx = this.getFluidState(â˜ƒ);
      if (!â˜ƒxxxx.isEmpty()) {
         â˜ƒxxxx.animateTick(this, â˜ƒ, â˜ƒ);
         ParticleOptions â˜ƒxxxxx = â˜ƒxxxx.getDripParticle();
         if (â˜ƒxxxxx != null && this.random.nextInt(10) == 0) {
            boolean â˜ƒxxxxxx = â˜ƒxxx.isFaceSturdy(this, â˜ƒ, Direction.DOWN);
            BlockPos â˜ƒxxxxxxx = â˜ƒ.below();
            this.trySpawnDripParticles(â˜ƒxxxxxxx, this.getBlockState(â˜ƒxxxxxxx), â˜ƒxxxxx, â˜ƒxxxxxx);
         }
      }

      if (â˜ƒ != null && â˜ƒxxx.getBlock() == â˜ƒ.block) {
         this.addParticle(â˜ƒ.particle, (double)â˜ƒ + 0.5, (double)â˜ƒx + 0.5, (double)â˜ƒxx + 0.5, 0.0, 0.0, 0.0);
      }

      if (!â˜ƒxxx.isCollisionShapeFullBlock(this, â˜ƒ)) {
         this.getBiome(â˜ƒ)
            .getAmbientParticle()
            .ifPresent(
               var2x -> {
                  if (var2x.canSpawn(this.random)) {
                     this.addParticle(
                        var2x.getOptions(),
                        (double)â˜ƒ.getX() + this.random.nextDouble(),
                        (double)â˜ƒ.getY() + this.random.nextDouble(),
                        (double)â˜ƒ.getZ() + this.random.nextDouble(),
                        0.0,
                        0.0,
                        0.0
                     );
                  }
               }
            );
      }
   }

   private void trySpawnDripParticles(BlockPos var1, BlockState var2, ParticleOptions var3, boolean var4) {
      if (â˜ƒ.getFluidState().isEmpty()) {
         VoxelShape â˜ƒ = â˜ƒ.getCollisionShape(this, â˜ƒ);
         double â˜ƒx = â˜ƒ.max(Direction.Axis.Y);
         if (â˜ƒx < 1.0) {
            if (â˜ƒ) {
               this.spawnFluidParticle(
                  (double)â˜ƒ.getX(), (double)(â˜ƒ.getX() + 1), (double)â˜ƒ.getZ(), (double)(â˜ƒ.getZ() + 1), (double)(â˜ƒ.getY() + 1) - 0.05, â˜ƒ
               );
            }
         } else if (!â˜ƒ.is(BlockTags.IMPERMEABLE)) {
            double â˜ƒ = â˜ƒ.min(Direction.Axis.Y);
            if (â˜ƒ > 0.0) {
               this.spawnParticle(â˜ƒ, â˜ƒ, â˜ƒ, (double)â˜ƒ.getY() + â˜ƒ - 0.05);
            } else {
               BlockPos â˜ƒ = â˜ƒ.below();
               BlockState â˜ƒx = this.getBlockState(â˜ƒ);
               VoxelShape â˜ƒxx = â˜ƒx.getCollisionShape(this, â˜ƒ);
               double â˜ƒxxx = â˜ƒxx.max(Direction.Axis.Y);
               if (â˜ƒxxx < 1.0 && â˜ƒx.getFluidState().isEmpty()) {
                  this.spawnParticle(â˜ƒ, â˜ƒ, â˜ƒ, (double)â˜ƒ.getY() - 0.05);
               }
            }
         }
      }
   }

   private void spawnParticle(BlockPos var1, ParticleOptions var2, VoxelShape var3, double var4) {
      this.spawnFluidParticle(
         (double)â˜ƒ.getX() + â˜ƒ.min(Direction.Axis.X),
         (double)â˜ƒ.getX() + â˜ƒ.max(Direction.Axis.X),
         (double)â˜ƒ.getZ() + â˜ƒ.min(Direction.Axis.Z),
         (double)â˜ƒ.getZ() + â˜ƒ.max(Direction.Axis.Z),
         â˜ƒ,
         â˜ƒ
      );
   }

   private void spawnFluidParticle(double var1, double var3, double var5, double var7, double var9, ParticleOptions var11) {
      this.addParticle(â˜ƒ, Mth.lerp(this.random.nextDouble(), â˜ƒ, â˜ƒ), â˜ƒ, Mth.lerp(this.random.nextDouble(), â˜ƒ, â˜ƒ), 0.0, 0.0, 0.0);
   }

   @Override
   public CrashReportCategory fillReportDetails(CrashReport var1) {
      CrashReportCategory â˜ƒ = super.fillReportDetails(â˜ƒ);
      â˜ƒ.setDetail("Server brand", (CrashReportDetail<String>)(() -> this.minecraft.player.getServerBrand()));
      â˜ƒ.setDetail(
         "Server type",
         (CrashReportDetail<String>)(() -> this.minecraft.getSingleplayerServer() == null
               ? "Non-integrated multiplayer server"
               : "Integrated singleplayer server")
      );
      return â˜ƒ;
   }

   @Override
   public void playSound(@Nullable Player var1, double var2, double var4, double var6, SoundEvent var8, SoundSource var9, float var10, float var11) {
      if (â˜ƒ == this.minecraft.player) {
         this.playLocalSound(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
      }
   }

   @Override
   public void playSound(@Nullable Player var1, Entity var2, SoundEvent var3, SoundSource var4, float var5, float var6) {
      if (â˜ƒ == this.minecraft.player) {
         this.minecraft.getSoundManager().play(new EntityBoundSoundInstance(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      }
   }

   public void playLocalSound(BlockPos var1, SoundEvent var2, SoundSource var3, float var4, float var5, boolean var6) {
      this.playLocalSound((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void playLocalSound(double var1, double var3, double var5, SoundEvent var7, SoundSource var8, float var9, float var10, boolean var11) {
      double â˜ƒ = this.minecraft.gameRenderer.getMainCamera().getPosition().distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ);
      SimpleSoundInstance â˜ƒx = new SimpleSoundInstance(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ && â˜ƒ > 100.0) {
         double â˜ƒxx = Math.sqrt(â˜ƒ) / 40.0;
         this.minecraft.getSoundManager().playDelayed(â˜ƒx, (int)(â˜ƒxx * 20.0));
      } else {
         this.minecraft.getSoundManager().play(â˜ƒx);
      }
   }

   @Override
   public void createFireworks(double var1, double var3, double var5, double var7, double var9, double var11, @Nullable CompoundTag var13) {
      this.minecraft.particleEngine.add(new FireworkParticles.Starter(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.minecraft.particleEngine, â˜ƒ));
   }

   @Override
   public void sendPacketToServer(Packet<?> var1) {
      this.connection.send(â˜ƒ);
   }

   @Override
   public RecipeManager getRecipeManager() {
      return this.connection.getRecipeManager();
   }

   public void setScoreboard(Scoreboard var1) {
      this.scoreboard = â˜ƒ;
   }

   @Override
   public TickList<Block> getBlockTicks() {
      return EmptyTickList.empty();
   }

   @Override
   public TickList<Fluid> getLiquidTicks() {
      return EmptyTickList.empty();
   }

   public ClientChunkCache getChunkSource() {
      return this.chunkSource;
   }

   @Nullable
   @Override
   public MapItemSavedData getMapData(String var1) {
      return (MapItemSavedData)this.mapData.get(â˜ƒ);
   }

   @Override
   public void setMapData(String var1, MapItemSavedData var2) {
      this.mapData.put(â˜ƒ, â˜ƒ);
   }

   @Override
   public int getFreeMapId() {
      return 0;
   }

   @Override
   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   @Override
   public TagContainer getTagManager() {
      return this.connection.getTags();
   }

   @Override
   public RegistryAccess registryAccess() {
      return this.connection.registryAccess();
   }

   @Override
   public void sendBlockUpdated(BlockPos var1, BlockState var2, BlockState var3, int var4) {
      this.levelRenderer.blockChanged(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void setBlocksDirty(BlockPos var1, BlockState var2, BlockState var3) {
      this.levelRenderer.setBlockDirty(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setSectionDirtyWithNeighbors(int var1, int var2, int var3) {
      this.levelRenderer.setSectionDirtyWithNeighbors(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void destroyBlockProgress(int var1, BlockPos var2, int var3) {
      this.levelRenderer.destroyBlockProgress(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void globalLevelEvent(int var1, BlockPos var2, int var3) {
      this.levelRenderer.globalLevelEvent(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void levelEvent(@Nullable Player var1, int var2, BlockPos var3, int var4) {
      try {
         this.levelRenderer.levelEvent(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (Throwable var8) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var8, "Playing level event");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Level event being played");
         â˜ƒx.setDetail("Block coordinates", CrashReportCategory.formatLocation(this, â˜ƒ));
         â˜ƒx.setDetail("Event source", â˜ƒ);
         â˜ƒx.setDetail("Event type", â˜ƒ);
         â˜ƒx.setDetail("Event data", â˜ƒ);
         throw new ReportedException(â˜ƒ);
      }
   }

   @Override
   public void addParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this.levelRenderer.addParticle(â˜ƒ, â˜ƒ.getType().getOverrideLimiter(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void addParticle(ParticleOptions var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      this.levelRenderer.addParticle(â˜ƒ, â˜ƒ.getType().getOverrideLimiter() || â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void addAlwaysVisibleParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this.levelRenderer.addParticle(â˜ƒ, false, true, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void addAlwaysVisibleParticle(ParticleOptions var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      this.levelRenderer.addParticle(â˜ƒ, â˜ƒ.getType().getOverrideLimiter() || â˜ƒ, true, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public List<AbstractClientPlayer> players() {
      return this.players;
   }

   @Override
   public Biome getUncachedNoiseBiome(int var1, int var2, int var3) {
      return this.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getOrThrow(Biomes.PLAINS);
   }

   public float getSkyDarken(float var1) {
      float â˜ƒ = this.getTimeOfDay(â˜ƒ);
      float â˜ƒx = 1.0F - (Mth.cos(â˜ƒ * (float) (Math.PI * 2)) * 2.0F + 0.2F);
      â˜ƒx = Mth.clamp(â˜ƒx, 0.0F, 1.0F);
      â˜ƒx = 1.0F - â˜ƒx;
      â˜ƒx = (float)((double)â˜ƒx * (1.0 - (double)(this.getRainLevel(â˜ƒ) * 5.0F) / 16.0));
      â˜ƒx = (float)((double)â˜ƒx * (1.0 - (double)(this.getThunderLevel(â˜ƒ) * 5.0F) / 16.0));
      return â˜ƒx * 0.8F + 0.2F;
   }

   public Vec3 getSkyColor(Vec3 var1, float var2) {
      float â˜ƒ = this.getTimeOfDay(â˜ƒ);
      Vec3 â˜ƒx = â˜ƒ.subtract(2.0, 2.0, 2.0).scale(0.25);
      BiomeManager â˜ƒxx = this.getBiomeManager();
      Vec3 â˜ƒxxx = CubicSampler.gaussianSampleVec3(â˜ƒx, (var1x, var2x, var3x) -> Vec3.fromRGB24(â˜ƒ.getNoiseBiomeAtQuart(var1x, var2x, var3x).getSkyColor()));
      float â˜ƒxxxx = Mth.cos(â˜ƒ * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      â˜ƒxxxx = Mth.clamp(â˜ƒxxxx, 0.0F, 1.0F);
      float â˜ƒxxxxx = (float)â˜ƒxxx.x * â˜ƒxxxx;
      float â˜ƒxxxxxx = (float)â˜ƒxxx.y * â˜ƒxxxx;
      float â˜ƒxxxxxxx = (float)â˜ƒxxx.z * â˜ƒxxxx;
      float â˜ƒxxxxxxxx = this.getRainLevel(â˜ƒ);
      if (â˜ƒxxxxxxxx > 0.0F) {
         float â˜ƒxxxxxxxxx = (â˜ƒxxxxx * 0.3F + â˜ƒxxxxxx * 0.59F + â˜ƒxxxxxxx * 0.11F) * 0.6F;
         float â˜ƒxxxxxxxxxx = 1.0F - â˜ƒxxxxxxxx * 0.75F;
         â˜ƒxxxxx = â˜ƒxxxxx * â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxx * (1.0F - â˜ƒxxxxxxxxxx);
         â˜ƒxxxxxx = â˜ƒxxxxxx * â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxx * (1.0F - â˜ƒxxxxxxxxxx);
         â˜ƒxxxxxxx = â˜ƒxxxxxxx * â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxx * (1.0F - â˜ƒxxxxxxxxxx);
      }

      float â˜ƒ = this.getThunderLevel(â˜ƒ);
      if (â˜ƒ > 0.0F) {
         float â˜ƒx = (â˜ƒxxxxx * 0.3F + â˜ƒxxxxxx * 0.59F + â˜ƒxxxxxxx * 0.11F) * 0.2F;
         float â˜ƒxx = 1.0F - â˜ƒ * 0.75F;
         â˜ƒxxxxx = â˜ƒxxxxx * â˜ƒxx + â˜ƒx * (1.0F - â˜ƒxx);
         â˜ƒxxxxxx = â˜ƒxxxxxx * â˜ƒxx + â˜ƒx * (1.0F - â˜ƒxx);
         â˜ƒxxxxxxx = â˜ƒxxxxxxx * â˜ƒxx + â˜ƒx * (1.0F - â˜ƒxx);
      }

      if (this.skyFlashTime > 0) {
         float â˜ƒ = (float)this.skyFlashTime - â˜ƒ;
         if (â˜ƒ > 1.0F) {
            â˜ƒ = 1.0F;
         }

         â˜ƒ *= 0.45F;
         â˜ƒxxxxx = â˜ƒxxxxx * (1.0F - â˜ƒ) + 0.8F * â˜ƒ;
         â˜ƒxxxxxx = â˜ƒxxxxxx * (1.0F - â˜ƒ) + 0.8F * â˜ƒ;
         â˜ƒxxxxxxx = â˜ƒxxxxxxx * (1.0F - â˜ƒ) + 1.0F * â˜ƒ;
      }

      return new Vec3((double)â˜ƒxxxxx, (double)â˜ƒxxxxxx, (double)â˜ƒxxxxxxx);
   }

   public Vec3 getCloudColor(float var1) {
      float â˜ƒ = this.getTimeOfDay(â˜ƒ);
      float â˜ƒx = Mth.cos(â˜ƒ * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      â˜ƒx = Mth.clamp(â˜ƒx, 0.0F, 1.0F);
      float â˜ƒxx = 1.0F;
      float â˜ƒxxx = 1.0F;
      float â˜ƒxxxx = 1.0F;
      float â˜ƒxxxxx = this.getRainLevel(â˜ƒ);
      if (â˜ƒxxxxx > 0.0F) {
         float â˜ƒxxxxxx = (â˜ƒxx * 0.3F + â˜ƒxxx * 0.59F + â˜ƒxxxx * 0.11F) * 0.6F;
         float â˜ƒxxxxxxx = 1.0F - â˜ƒxxxxx * 0.95F;
         â˜ƒxx = â˜ƒxx * â˜ƒxxxxxxx + â˜ƒxxxxxx * (1.0F - â˜ƒxxxxxxx);
         â˜ƒxxx = â˜ƒxxx * â˜ƒxxxxxxx + â˜ƒxxxxxx * (1.0F - â˜ƒxxxxxxx);
         â˜ƒxxxx = â˜ƒxxxx * â˜ƒxxxxxxx + â˜ƒxxxxxx * (1.0F - â˜ƒxxxxxxx);
      }

      â˜ƒxx *= â˜ƒx * 0.9F + 0.1F;
      â˜ƒxxx *= â˜ƒx * 0.9F + 0.1F;
      â˜ƒxxxx *= â˜ƒx * 0.85F + 0.15F;
      float â˜ƒ = this.getThunderLevel(â˜ƒ);
      if (â˜ƒ > 0.0F) {
         float â˜ƒx = (â˜ƒxx * 0.3F + â˜ƒxxx * 0.59F + â˜ƒxxxx * 0.11F) * 0.2F;
         float â˜ƒxx = 1.0F - â˜ƒ * 0.95F;
         â˜ƒxx = â˜ƒxx * â˜ƒxx + â˜ƒx * (1.0F - â˜ƒxx);
         â˜ƒxxx = â˜ƒxxx * â˜ƒxx + â˜ƒx * (1.0F - â˜ƒxx);
         â˜ƒxxxx = â˜ƒxxxx * â˜ƒxx + â˜ƒx * (1.0F - â˜ƒxx);
      }

      return new Vec3((double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒxxxx);
   }

   public float getStarBrightness(float var1) {
      float â˜ƒ = this.getTimeOfDay(â˜ƒ);
      float â˜ƒx = 1.0F - (Mth.cos(â˜ƒ * (float) (Math.PI * 2)) * 2.0F + 0.25F);
      â˜ƒx = Mth.clamp(â˜ƒx, 0.0F, 1.0F);
      return â˜ƒx * â˜ƒx * 0.5F;
   }

   public int getSkyFlashTime() {
      return this.skyFlashTime;
   }

   @Override
   public void setSkyFlashTime(int var1) {
      this.skyFlashTime = â˜ƒ;
   }

   @Override
   public float getShade(Direction var1, boolean var2) {
      boolean â˜ƒ = this.effects().constantAmbientLight();
      if (!â˜ƒ) {
         return â˜ƒ ? 0.9F : 1.0F;
      } else {
         switch(â˜ƒ) {
            case DOWN:
               return â˜ƒ ? 0.9F : 0.5F;
            case UP:
               return â˜ƒ ? 0.9F : 1.0F;
            case NORTH:
            case SOUTH:
               return 0.8F;
            case WEST:
            case EAST:
               return 0.6F;
            default:
               return 1.0F;
         }
      }
   }

   @Override
   public int getBlockTint(BlockPos var1, ColorResolver var2) {
      BlockTintCache â˜ƒ = this.tintCaches.get(â˜ƒ);
      return â˜ƒ.getColor(â˜ƒ, () -> this.calculateBlockTint(â˜ƒ, â˜ƒ));
   }

   public int calculateBlockTint(BlockPos var1, ColorResolver var2) {
      int â˜ƒ = Minecraft.getInstance().options.biomeBlendRadius;
      if (â˜ƒ == 0) {
         return â˜ƒ.getColor(this.getBiome(â˜ƒ), (double)â˜ƒ.getX(), (double)â˜ƒ.getZ());
      } else {
         int â˜ƒ = (â˜ƒ * 2 + 1) * (â˜ƒ * 2 + 1);
         int â˜ƒx = 0;
         int â˜ƒxx = 0;
         int â˜ƒxxx = 0;
         Cursor3D â˜ƒxxxx = new Cursor3D(â˜ƒ.getX() - â˜ƒ, â˜ƒ.getY(), â˜ƒ.getZ() - â˜ƒ, â˜ƒ.getX() + â˜ƒ, â˜ƒ.getY(), â˜ƒ.getZ() + â˜ƒ);

         int â˜ƒ;
         for(BlockPos.MutableBlockPos â˜ƒxxxxx = new BlockPos.MutableBlockPos(); â˜ƒxxxx.advance(); â˜ƒxxx += â˜ƒ & 0xFF) {
            â˜ƒxxxxx.set(â˜ƒxxxx.nextX(), â˜ƒxxxx.nextY(), â˜ƒxxxx.nextZ());
            â˜ƒ = â˜ƒ.getColor(this.getBiome(â˜ƒxxxxx), (double)â˜ƒxxxxx.getX(), (double)â˜ƒxxxxx.getZ());
            â˜ƒx += (â˜ƒ & 0xFF0000) >> 16;
            â˜ƒxx += (â˜ƒ & 0xFF00) >> 8;
         }

         return (â˜ƒx / â˜ƒ & 0xFF) << 16 | (â˜ƒxx / â˜ƒ & 0xFF) << 8 | â˜ƒxxx / â˜ƒ & 0xFF;
      }
   }

   public BlockPos getSharedSpawnPos() {
      BlockPos â˜ƒ = new BlockPos(this.levelData.getXSpawn(), this.levelData.getYSpawn(), this.levelData.getZSpawn());
      if (!this.getWorldBorder().isWithinBounds(â˜ƒ)) {
         â˜ƒ = this.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, new BlockPos(this.getWorldBorder().getCenterX(), 0.0, this.getWorldBorder().getCenterZ()));
      }

      return â˜ƒ;
   }

   public float getSharedSpawnAngle() {
      return this.levelData.getSpawnAngle();
   }

   public void setDefaultSpawnPos(BlockPos var1, float var2) {
      this.levelData.setSpawn(â˜ƒ, â˜ƒ);
   }

   public String toString() {
      return "ClientLevel";
   }

   public ClientLevel.ClientLevelData getLevelData() {
      return this.clientLevelData;
   }

   @Override
   public void gameEvent(@Nullable Entity var1, GameEvent var2, BlockPos var3) {
   }

   protected Map<String, MapItemSavedData> getAllMapData() {
      return ImmutableMap.copyOf(this.mapData);
   }

   protected void addMapData(Map<String, MapItemSavedData> var1) {
      this.mapData.putAll(â˜ƒ);
   }

   @Override
   protected LevelEntityGetter<Entity> getEntities() {
      return this.entityStorage.getEntityGetter();
   }

   @Override
   public String gatherChunkSourceStats() {
      return "Chunks[C] W: " + this.chunkSource.gatherStats() + " E: " + this.entityStorage.gatherStats();
   }

   @Override
   public void addDestroyBlockEffect(BlockPos var1, BlockState var2) {
      this.minecraft.particleEngine.destroy(â˜ƒ, â˜ƒ);
   }

   public static class ClientLevelData implements WritableLevelData {
      private final boolean hardcore;
      private final GameRules gameRules;
      private final boolean isFlat;
      private int xSpawn;
      private int ySpawn;
      private int zSpawn;
      private float spawnAngle;
      private long gameTime;
      private long dayTime;
      private boolean raining;
      private Difficulty difficulty;
      private boolean difficultyLocked;

      public ClientLevelData(Difficulty var1, boolean var2, boolean var3) {
         this.difficulty = â˜ƒ;
         this.hardcore = â˜ƒ;
         this.isFlat = â˜ƒ;
         this.gameRules = new GameRules();
      }

      @Override
      public int getXSpawn() {
         return this.xSpawn;
      }

      @Override
      public int getYSpawn() {
         return this.ySpawn;
      }

      @Override
      public int getZSpawn() {
         return this.zSpawn;
      }

      @Override
      public float getSpawnAngle() {
         return this.spawnAngle;
      }

      @Override
      public long getGameTime() {
         return this.gameTime;
      }

      @Override
      public long getDayTime() {
         return this.dayTime;
      }

      @Override
      public void setXSpawn(int var1) {
         this.xSpawn = â˜ƒ;
      }

      @Override
      public void setYSpawn(int var1) {
         this.ySpawn = â˜ƒ;
      }

      @Override
      public void setZSpawn(int var1) {
         this.zSpawn = â˜ƒ;
      }

      @Override
      public void setSpawnAngle(float var1) {
         this.spawnAngle = â˜ƒ;
      }

      public void setGameTime(long var1) {
         this.gameTime = â˜ƒ;
      }

      public void setDayTime(long var1) {
         this.dayTime = â˜ƒ;
      }

      @Override
      public void setSpawn(BlockPos var1, float var2) {
         this.xSpawn = â˜ƒ.getX();
         this.ySpawn = â˜ƒ.getY();
         this.zSpawn = â˜ƒ.getZ();
         this.spawnAngle = â˜ƒ;
      }

      @Override
      public boolean isThundering() {
         return false;
      }

      @Override
      public boolean isRaining() {
         return this.raining;
      }

      @Override
      public void setRaining(boolean var1) {
         this.raining = â˜ƒ;
      }

      @Override
      public boolean isHardcore() {
         return this.hardcore;
      }

      @Override
      public GameRules getGameRules() {
         return this.gameRules;
      }

      @Override
      public Difficulty getDifficulty() {
         return this.difficulty;
      }

      @Override
      public boolean isDifficultyLocked() {
         return this.difficultyLocked;
      }

      @Override
      public void fillCrashReportCategory(CrashReportCategory var1, LevelHeightAccessor var2) {
         WritableLevelData.super.fillCrashReportCategory(â˜ƒ, â˜ƒ);
      }

      public void setDifficulty(Difficulty var1) {
         this.difficulty = â˜ƒ;
      }

      public void setDifficultyLocked(boolean var1) {
         this.difficultyLocked = â˜ƒ;
      }

      public double getHorizonHeight(LevelHeightAccessor var1) {
         return this.isFlat ? (double)â˜ƒ.getMinBuildHeight() : 63.0;
      }

      public double getClearColorScale() {
         return this.isFlat ? 1.0 : 0.03125;
      }
   }

   final class EntityCallbacks implements LevelCallback<Entity> {
      public void onCreated(Entity var1) {
      }

      public void onDestroyed(Entity var1) {
      }

      public void onTickingStart(Entity var1) {
         ClientLevel.this.tickingEntities.add(â˜ƒ);
      }

      public void onTickingEnd(Entity var1) {
         ClientLevel.this.tickingEntities.remove(â˜ƒ);
      }

      public void onTrackingStart(Entity var1) {
         if (â˜ƒ instanceof AbstractClientPlayer) {
            ClientLevel.this.players.add((AbstractClientPlayer)â˜ƒ);
         }
      }

      public void onTrackingEnd(Entity var1) {
         â˜ƒ.unRide();
         ClientLevel.this.players.remove(â˜ƒ);
      }
   }

   static enum MarkerParticleStatus {
      BARRIER(Blocks.BARRIER, ParticleTypes.BARRIER),
      LIGHT(Blocks.LIGHT, ParticleTypes.LIGHT);

      final Block block;
      final ParticleOptions particle;

      private MarkerParticleStatus(Block var3, ParticleOptions var4) {
         this.block = â˜ƒ;
         this.particle = â˜ƒ;
      }
   }
}
