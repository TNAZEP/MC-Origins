package net.minecraft.client.particle;

import com.google.common.base.Charsets;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ParticleEngine implements PreparableReloadListener {
   private static final int MAX_PARTICLES_PER_LAYER = 16384;
   private static final List<ParticleRenderType> RENDER_ORDER = ImmutableList.of(
      ParticleRenderType.TERRAIN_SHEET,
      ParticleRenderType.PARTICLE_SHEET_OPAQUE,
      ParticleRenderType.PARTICLE_SHEET_LIT,
      ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT,
      ParticleRenderType.CUSTOM
   );
   protected ClientLevel level;
   private final Map<ParticleRenderType, Queue<Particle>> particles = Maps.newIdentityHashMap();
   private final Queue<TrackingEmitter> trackingEmitters = Queues.<TrackingEmitter>newArrayDeque();
   private final TextureManager textureManager;
   private final Random random = new Random();
   private final Int2ObjectMap<ParticleProvider<?>> providers = new Int2ObjectOpenHashMap<>();
   private final Queue<Particle> particlesToAdd = Queues.<Particle>newArrayDeque();
   private final Map<ResourceLocation, ParticleEngine.MutableSpriteSet> spriteSets = Maps.<ResourceLocation, ParticleEngine.MutableSpriteSet>newHashMap();
   private final TextureAtlas textureAtlas;
   private final Object2IntOpenHashMap<ParticleGroup> trackedParticleCounts = new Object2IntOpenHashMap<>();

   public ParticleEngine(ClientLevel var1, TextureManager var2) {
      this.textureAtlas = new TextureAtlas(TextureAtlas.LOCATION_PARTICLES);
      â˜ƒ.register(this.textureAtlas.location(), this.textureAtlas);
      this.level = â˜ƒ;
      this.textureManager = â˜ƒ;
      this.registerProviders();
   }

   private void registerProviders() {
      this.register(ParticleTypes.AMBIENT_ENTITY_EFFECT, SpellParticle.AmbientMobProvider::new);
      this.register(ParticleTypes.ANGRY_VILLAGER, HeartParticle.AngryVillagerProvider::new);
      this.register(ParticleTypes.BARRIER, new StationaryItemParticle.BarrierProvider());
      this.register(ParticleTypes.LIGHT, new StationaryItemParticle.LightProvider());
      this.register(ParticleTypes.BLOCK, new TerrainParticle.Provider());
      this.register(ParticleTypes.BUBBLE, BubbleParticle.Provider::new);
      this.register(ParticleTypes.BUBBLE_COLUMN_UP, BubbleColumnUpParticle.Provider::new);
      this.register(ParticleTypes.BUBBLE_POP, BubblePopParticle.Provider::new);
      this.register(ParticleTypes.CAMPFIRE_COSY_SMOKE, CampfireSmokeParticle.CosyProvider::new);
      this.register(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, CampfireSmokeParticle.SignalProvider::new);
      this.register(ParticleTypes.CLOUD, PlayerCloudParticle.Provider::new);
      this.register(ParticleTypes.COMPOSTER, SuspendedTownParticle.ComposterFillProvider::new);
      this.register(ParticleTypes.CRIT, CritParticle.Provider::new);
      this.register(ParticleTypes.CURRENT_DOWN, WaterCurrentDownParticle.Provider::new);
      this.register(ParticleTypes.DAMAGE_INDICATOR, CritParticle.DamageIndicatorProvider::new);
      this.register(ParticleTypes.DRAGON_BREATH, DragonBreathParticle.Provider::new);
      this.register(ParticleTypes.DOLPHIN, SuspendedTownParticle.DolphinSpeedProvider::new);
      this.register(ParticleTypes.DRIPPING_LAVA, DripParticle.LavaHangProvider::new);
      this.register(ParticleTypes.FALLING_LAVA, DripParticle.LavaFallProvider::new);
      this.register(ParticleTypes.LANDING_LAVA, DripParticle.LavaLandProvider::new);
      this.register(ParticleTypes.DRIPPING_WATER, DripParticle.WaterHangProvider::new);
      this.register(ParticleTypes.FALLING_WATER, DripParticle.WaterFallProvider::new);
      this.register(ParticleTypes.DUST, DustParticle.Provider::new);
      this.register(ParticleTypes.DUST_COLOR_TRANSITION, DustColorTransitionParticle.Provider::new);
      this.register(ParticleTypes.EFFECT, SpellParticle.Provider::new);
      this.register(ParticleTypes.ELDER_GUARDIAN, new MobAppearanceParticle.Provider());
      this.register(ParticleTypes.ENCHANTED_HIT, CritParticle.MagicProvider::new);
      this.register(ParticleTypes.ENCHANT, EnchantmentTableParticle.Provider::new);
      this.register(ParticleTypes.END_ROD, EndRodParticle.Provider::new);
      this.register(ParticleTypes.ENTITY_EFFECT, SpellParticle.MobProvider::new);
      this.register(ParticleTypes.EXPLOSION_EMITTER, new HugeExplosionSeedParticle.Provider());
      this.register(ParticleTypes.EXPLOSION, HugeExplosionParticle.Provider::new);
      this.register(ParticleTypes.FALLING_DUST, FallingDustParticle.Provider::new);
      this.register(ParticleTypes.FIREWORK, FireworkParticles.SparkProvider::new);
      this.register(ParticleTypes.FISHING, WakeParticle.Provider::new);
      this.register(ParticleTypes.FLAME, FlameParticle.Provider::new);
      this.register(ParticleTypes.SOUL, SoulParticle.Provider::new);
      this.register(ParticleTypes.SOUL_FIRE_FLAME, FlameParticle.Provider::new);
      this.register(ParticleTypes.FLASH, FireworkParticles.FlashProvider::new);
      this.register(ParticleTypes.HAPPY_VILLAGER, SuspendedTownParticle.HappyVillagerProvider::new);
      this.register(ParticleTypes.HEART, HeartParticle.Provider::new);
      this.register(ParticleTypes.INSTANT_EFFECT, SpellParticle.InstantProvider::new);
      this.register(ParticleTypes.ITEM, new BreakingItemParticle.Provider());
      this.register(ParticleTypes.ITEM_SLIME, new BreakingItemParticle.SlimeProvider());
      this.register(ParticleTypes.ITEM_SNOWBALL, new BreakingItemParticle.SnowballProvider());
      this.register(ParticleTypes.LARGE_SMOKE, LargeSmokeParticle.Provider::new);
      this.register(ParticleTypes.LAVA, LavaParticle.Provider::new);
      this.register(ParticleTypes.MYCELIUM, SuspendedTownParticle.Provider::new);
      this.register(ParticleTypes.NAUTILUS, EnchantmentTableParticle.NautilusProvider::new);
      this.register(ParticleTypes.NOTE, NoteParticle.Provider::new);
      this.register(ParticleTypes.POOF, ExplodeParticle.Provider::new);
      this.register(ParticleTypes.PORTAL, PortalParticle.Provider::new);
      this.register(ParticleTypes.RAIN, WaterDropParticle.Provider::new);
      this.register(ParticleTypes.SMOKE, SmokeParticle.Provider::new);
      this.register(ParticleTypes.SNEEZE, PlayerCloudParticle.SneezeProvider::new);
      this.register(ParticleTypes.SNOWFLAKE, SnowflakeParticle.Provider::new);
      this.register(ParticleTypes.SPIT, SpitParticle.Provider::new);
      this.register(ParticleTypes.SWEEP_ATTACK, AttackSweepParticle.Provider::new);
      this.register(ParticleTypes.TOTEM_OF_UNDYING, TotemParticle.Provider::new);
      this.register(ParticleTypes.SQUID_INK, SquidInkParticle.Provider::new);
      this.register(ParticleTypes.UNDERWATER, SuspendedParticle.UnderwaterProvider::new);
      this.register(ParticleTypes.SPLASH, SplashParticle.Provider::new);
      this.register(ParticleTypes.WITCH, SpellParticle.WitchProvider::new);
      this.register(ParticleTypes.DRIPPING_HONEY, DripParticle.HoneyHangProvider::new);
      this.register(ParticleTypes.FALLING_HONEY, DripParticle.HoneyFallProvider::new);
      this.register(ParticleTypes.LANDING_HONEY, DripParticle.HoneyLandProvider::new);
      this.register(ParticleTypes.FALLING_NECTAR, DripParticle.NectarFallProvider::new);
      this.register(ParticleTypes.FALLING_SPORE_BLOSSOM, DripParticle.SporeBlossomFallProvider::new);
      this.register(ParticleTypes.SPORE_BLOSSOM_AIR, SuspendedParticle.SporeBlossomAirProvider::new);
      this.register(ParticleTypes.ASH, AshParticle.Provider::new);
      this.register(ParticleTypes.CRIMSON_SPORE, SuspendedParticle.CrimsonSporeProvider::new);
      this.register(ParticleTypes.WARPED_SPORE, SuspendedParticle.WarpedSporeProvider::new);
      this.register(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, DripParticle.ObsidianTearHangProvider::new);
      this.register(ParticleTypes.FALLING_OBSIDIAN_TEAR, DripParticle.ObsidianTearFallProvider::new);
      this.register(ParticleTypes.LANDING_OBSIDIAN_TEAR, DripParticle.ObsidianTearLandProvider::new);
      this.register(ParticleTypes.REVERSE_PORTAL, ReversePortalParticle.ReversePortalProvider::new);
      this.register(ParticleTypes.WHITE_ASH, WhiteAshParticle.Provider::new);
      this.register(ParticleTypes.SMALL_FLAME, FlameParticle.SmallFlameProvider::new);
      this.register(ParticleTypes.DRIPPING_DRIPSTONE_WATER, DripParticle.DripstoneWaterHangProvider::new);
      this.register(ParticleTypes.FALLING_DRIPSTONE_WATER, DripParticle.DripstoneWaterFallProvider::new);
      this.register(ParticleTypes.DRIPPING_DRIPSTONE_LAVA, DripParticle.DripstoneLavaHangProvider::new);
      this.register(ParticleTypes.FALLING_DRIPSTONE_LAVA, DripParticle.DripstoneLavaFallProvider::new);
      this.register(ParticleTypes.VIBRATION, VibrationSignalParticle.Provider::new);
      this.register(ParticleTypes.GLOW_SQUID_INK, SquidInkParticle.GlowInkProvider::new);
      this.register(ParticleTypes.GLOW, GlowParticle.GlowSquidProvider::new);
      this.register(ParticleTypes.WAX_ON, GlowParticle.WaxOnProvider::new);
      this.register(ParticleTypes.WAX_OFF, GlowParticle.WaxOffProvider::new);
      this.register(ParticleTypes.ELECTRIC_SPARK, GlowParticle.ElectricSparkProvider::new);
      this.register(ParticleTypes.SCRAPE, GlowParticle.ScrapeProvider::new);
   }

   private <T extends ParticleOptions> void register(ParticleType<T> var1, ParticleProvider<T> var2) {
      this.providers.put(Registry.PARTICLE_TYPE.getId(â˜ƒ), â˜ƒ);
   }

   private <T extends ParticleOptions> void register(ParticleType<T> var1, ParticleEngine.SpriteParticleRegistration<T> var2) {
      ParticleEngine.MutableSpriteSet â˜ƒ = new ParticleEngine.MutableSpriteSet();
      this.spriteSets.put(Registry.PARTICLE_TYPE.getKey(â˜ƒ), â˜ƒ);
      this.providers.put(Registry.PARTICLE_TYPE.getId(â˜ƒ), â˜ƒ.create(â˜ƒ));
   }

   @Override
   public CompletableFuture<Void> reload(
      PreparableReloadListener.PreparationBarrier var1, ResourceManager var2, ProfilerFiller var3, ProfilerFiller var4, Executor var5, Executor var6
   ) {
      Map<ResourceLocation, List<ResourceLocation>> â˜ƒ = Maps.newConcurrentMap();
      CompletableFuture<?>[] â˜ƒx = (CompletableFuture[])Registry.PARTICLE_TYPE
         .keySet()
         .stream()
         .map(var4x -> CompletableFuture.runAsync(() -> this.loadParticleDescription(â˜ƒ, var4x, â˜ƒ), â˜ƒ))
         .toArray(var0 -> new CompletableFuture[var0]);
      return CompletableFuture.allOf(â˜ƒx)
         .thenApplyAsync(var4x -> {
            â˜ƒ.startTick();
            â˜ƒ.push("stitching");
            TextureAtlas.Preparations â˜ƒ = this.textureAtlas.prepareToStitch(â˜ƒ, â˜ƒ.values().stream().flatMap(Collection::stream), â˜ƒ, 0);
            â˜ƒ.pop();
            â˜ƒ.endTick();
            return â˜ƒ;
         }, â˜ƒ)
         .thenCompose(â˜ƒ::wait)
         .thenAcceptAsync(
            var3x -> {
               this.particles.clear();
               â˜ƒ.startTick();
               â˜ƒ.push("upload");
               this.textureAtlas.reload(var3x);
               â˜ƒ.popPush("bindSpriteSets");
               TextureAtlasSprite â˜ƒ = this.textureAtlas.getSprite(MissingTextureAtlasSprite.getLocation());
               â˜ƒ.forEach(
                  (var2x, var3xx) -> {
                     ImmutableList<TextureAtlasSprite> â˜ƒ = var3xx.isEmpty()
                        ? ImmutableList.of(â˜ƒ)
                        : (ImmutableList)var3xx.stream().map(this.textureAtlas::getSprite).collect(ImmutableList.toImmutableList());
                     ((ParticleEngine.MutableSpriteSet)this.spriteSets.get(var2x)).rebind(â˜ƒ);
                  }
               );
               â˜ƒ.pop();
               â˜ƒ.endTick();
            },
            â˜ƒ
         );
   }

   public void close() {
      this.textureAtlas.clearTextureData();
   }

   private void loadParticleDescription(ResourceManager var1, ResourceLocation var2, Map<ResourceLocation, List<ResourceLocation>> var3) {
      ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ.getNamespace(), "particles/" + â˜ƒ.getPath() + ".json");

      try {
         Resource â˜ƒx = â˜ƒ.getResource(â˜ƒ);

         try {
            Reader â˜ƒxx = new InputStreamReader(â˜ƒx.getInputStream(), Charsets.UTF_8);

            try {
               ParticleDescription â˜ƒxxx = ParticleDescription.fromJson(GsonHelper.parse(â˜ƒxx));
               List<ResourceLocation> â˜ƒxxxx = â˜ƒxxx.getTextures();
               boolean â˜ƒxxxxx = this.spriteSets.containsKey(â˜ƒ);
               if (â˜ƒxxxx == null) {
                  if (â˜ƒxxxxx) {
                     throw new IllegalStateException("Missing texture list for particle " + â˜ƒ);
                  }
               } else {
                  if (!â˜ƒxxxxx) {
                     throw new IllegalStateException("Redundant texture list for particle " + â˜ƒ);
                  }

                  â˜ƒ.put(
                     â˜ƒ,
                     (List)â˜ƒxxxx.stream().map(var0 -> new ResourceLocation(var0.getNamespace(), "particle/" + var0.getPath())).collect(Collectors.toList())
                  );
               }
            } catch (Throwable var12) {
               try {
                  â˜ƒxx.close();
               } catch (Throwable var11) {
                  var12.addSuppressed(var11);
               }

               throw var12;
            }

            â˜ƒxx.close();
         } catch (Throwable var13) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var10) {
                  var13.addSuppressed(var10);
               }
            }

            throw var13;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }
      } catch (IOException var14) {
         throw new IllegalStateException("Failed to load description for particle " + â˜ƒ, var14);
      }
   }

   public void createTrackingEmitter(Entity var1, ParticleOptions var2) {
      this.trackingEmitters.add(new TrackingEmitter(this.level, â˜ƒ, â˜ƒ));
   }

   public void createTrackingEmitter(Entity var1, ParticleOptions var2, int var3) {
      this.trackingEmitters.add(new TrackingEmitter(this.level, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Nullable
   public Particle createParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      Particle â˜ƒ = this.makeParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         this.add(â˜ƒ);
         return â˜ƒ;
      } else {
         return null;
      }
   }

   @Nullable
   private <T extends ParticleOptions> Particle makeParticle(T var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      ParticleProvider<T> â˜ƒ = (ParticleProvider)this.providers.get(Registry.PARTICLE_TYPE.getId(â˜ƒ.getType()));
      return â˜ƒ == null ? null : â˜ƒ.createParticle(â˜ƒ, this.level, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void add(Particle var1) {
      Optional<ParticleGroup> â˜ƒ = â˜ƒ.getParticleGroup();
      if (â˜ƒ.isPresent()) {
         if (this.hasSpaceInParticleLimit((ParticleGroup)â˜ƒ.get())) {
            this.particlesToAdd.add(â˜ƒ);
            this.updateCount((ParticleGroup)â˜ƒ.get(), 1);
         }
      } else {
         this.particlesToAdd.add(â˜ƒ);
      }
   }

   public void tick() {
      this.particles.forEach((var1x, var2) -> {
         this.level.getProfiler().push(var1x.toString());
         this.tickParticleList(var2);
         this.level.getProfiler().pop();
      });
      if (!this.trackingEmitters.isEmpty()) {
         List<TrackingEmitter> â˜ƒ = Lists.<TrackingEmitter>newArrayList();

         for(TrackingEmitter â˜ƒx : this.trackingEmitters) {
            â˜ƒx.tick();
            if (!â˜ƒx.isAlive()) {
               â˜ƒ.add(â˜ƒx);
            }
         }

         this.trackingEmitters.removeAll(â˜ƒ);
      }

      Particle â˜ƒ;
      if (!this.particlesToAdd.isEmpty()) {
         while((â˜ƒ = (Particle)this.particlesToAdd.poll()) != null) {
            ((Queue)this.particles.computeIfAbsent(â˜ƒ.getRenderType(), var0 -> EvictingQueue.create(16384))).add(â˜ƒ);
         }
      }
   }

   private void tickParticleList(Collection<Particle> var1) {
      if (!â˜ƒ.isEmpty()) {
         Iterator<Particle> â˜ƒ = â˜ƒ.iterator();

         while(â˜ƒ.hasNext()) {
            Particle â˜ƒx = (Particle)â˜ƒ.next();
            this.tickParticle(â˜ƒx);
            if (!â˜ƒx.isAlive()) {
               â˜ƒx.getParticleGroup().ifPresent(var1x -> this.updateCount(var1x, -1));
               â˜ƒ.remove();
            }
         }
      }
   }

   private void updateCount(ParticleGroup var1, int var2) {
      this.trackedParticleCounts.addTo(â˜ƒ, â˜ƒ);
   }

   private void tickParticle(Particle var1) {
      try {
         â˜ƒ.tick();
      } catch (Throwable var5) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var5, "Ticking Particle");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Particle being ticked");
         â˜ƒx.setDetail("Particle", â˜ƒ::toString);
         â˜ƒx.setDetail("Particle Type", â˜ƒ.getRenderType()::toString);
         throw new ReportedException(â˜ƒ);
      }
   }

   public void render(PoseStack var1, MultiBufferSource.BufferSource var2, LightTexture var3, Camera var4, float var5) {
      â˜ƒ.turnOnLightLayer();
      RenderSystem.enableDepthTest();
      PoseStack â˜ƒ = RenderSystem.getModelViewStack();
      â˜ƒ.pushPose();
      â˜ƒ.mulPoseMatrix(â˜ƒ.last().pose());
      RenderSystem.applyModelViewMatrix();

      for(ParticleRenderType â˜ƒx : RENDER_ORDER) {
         Iterable<Particle> â˜ƒxx = (Iterable)this.particles.get(â˜ƒx);
         if (â˜ƒxx != null) {
            RenderSystem.setShader(GameRenderer::getParticleShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            Tesselator â˜ƒxxx = Tesselator.getInstance();
            BufferBuilder â˜ƒxxxx = â˜ƒxxx.getBuilder();
            â˜ƒx.begin(â˜ƒxxxx, this.textureManager);

            for(Particle â˜ƒxxxxx : â˜ƒxx) {
               try {
                  â˜ƒxxxxx.render(â˜ƒxxxx, â˜ƒ, â˜ƒ);
               } catch (Throwable var17) {
                  CrashReport â˜ƒxxxxxx = CrashReport.forThrowable(var17, "Rendering Particle");
                  CrashReportCategory â˜ƒxxxxxxx = â˜ƒxxxxxx.addCategory("Particle being rendered");
                  â˜ƒxxxxxxx.setDetail("Particle", â˜ƒxxxxx::toString);
                  â˜ƒxxxxxxx.setDetail("Particle Type", â˜ƒx::toString);
                  throw new ReportedException(â˜ƒxxxxxx);
               }
            }

            â˜ƒx.end(â˜ƒxxx);
         }
      }

      â˜ƒ.popPose();
      RenderSystem.applyModelViewMatrix();
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      â˜ƒ.turnOffLightLayer();
   }

   public void setLevel(@Nullable ClientLevel var1) {
      this.level = â˜ƒ;
      this.particles.clear();
      this.trackingEmitters.clear();
      this.trackedParticleCounts.clear();
   }

   public void destroy(BlockPos var1, BlockState var2) {
      if (!â˜ƒ.isAir()) {
         VoxelShape â˜ƒ = â˜ƒ.getShape(this.level, â˜ƒ);
         double â˜ƒx = 0.25;
         â˜ƒ.forAllBoxes(
            (var3x, var5, var7, var9, var11, var13) -> {
               double â˜ƒ = Math.min(1.0, var9 - var3x);
               double â˜ƒx = Math.min(1.0, var11 - var5);
               double â˜ƒxx = Math.min(1.0, var13 - var7);
               int â˜ƒxxx = Math.max(2, Mth.ceil(â˜ƒ / 0.25));
               int â˜ƒxxxx = Math.max(2, Mth.ceil(â˜ƒx / 0.25));
               int â˜ƒxxxxx = Math.max(2, Mth.ceil(â˜ƒxx / 0.25));
   
               for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxx) {
                  for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxxx; ++â˜ƒxxxxxxx) {
                     for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxxx) {
                        double â˜ƒxxxxxxxxx = ((double)â˜ƒxxxxxx + 0.5) / (double)â˜ƒxxx;
                        double â˜ƒxxxxxxxxxx = ((double)â˜ƒxxxxxxx + 0.5) / (double)â˜ƒxxxx;
                        double â˜ƒxxxxxxxxxxx = ((double)â˜ƒxxxxxxxx + 0.5) / (double)â˜ƒxxxxx;
                        double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxx * â˜ƒ + var3x;
                        double â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx * â˜ƒx + var5;
                        double â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx * â˜ƒxx + var7;
                        this.add(
                           new TerrainParticle(
                              this.level,
                              (double)â˜ƒ.getX() + â˜ƒxxxxxxxxxxxx,
                              (double)â˜ƒ.getY() + â˜ƒxxxxxxxxxxxxx,
                              (double)â˜ƒ.getZ() + â˜ƒxxxxxxxxxxxxxx,
                              â˜ƒxxxxxxxxx - 0.5,
                              â˜ƒxxxxxxxxxx - 0.5,
                              â˜ƒxxxxxxxxxxx - 0.5,
                              â˜ƒ,
                              â˜ƒ
                           )
                        );
                     }
                  }
               }
            }
         );
      }
   }

   public void crack(BlockPos var1, Direction var2) {
      BlockState â˜ƒ = this.level.getBlockState(â˜ƒ);
      if (â˜ƒ.getRenderShape() != RenderShape.INVISIBLE) {
         int â˜ƒx = â˜ƒ.getX();
         int â˜ƒxx = â˜ƒ.getY();
         int â˜ƒxxx = â˜ƒ.getZ();
         float â˜ƒxxxx = 0.1F;
         AABB â˜ƒxxxxx = â˜ƒ.getShape(this.level, â˜ƒ).bounds();
         double â˜ƒxxxxxx = (double)â˜ƒx + this.random.nextDouble() * (â˜ƒxxxxx.maxX - â˜ƒxxxxx.minX - 0.2F) + 0.1F + â˜ƒxxxxx.minX;
         double â˜ƒxxxxxxx = (double)â˜ƒxx + this.random.nextDouble() * (â˜ƒxxxxx.maxY - â˜ƒxxxxx.minY - 0.2F) + 0.1F + â˜ƒxxxxx.minY;
         double â˜ƒxxxxxxxx = (double)â˜ƒxxx + this.random.nextDouble() * (â˜ƒxxxxx.maxZ - â˜ƒxxxxx.minZ - 0.2F) + 0.1F + â˜ƒxxxxx.minZ;
         if (â˜ƒ == Direction.DOWN) {
            â˜ƒxxxxxxx = (double)â˜ƒxx + â˜ƒxxxxx.minY - 0.1F;
         }

         if (â˜ƒ == Direction.UP) {
            â˜ƒxxxxxxx = (double)â˜ƒxx + â˜ƒxxxxx.maxY + 0.1F;
         }

         if (â˜ƒ == Direction.NORTH) {
            â˜ƒxxxxxxxx = (double)â˜ƒxxx + â˜ƒxxxxx.minZ - 0.1F;
         }

         if (â˜ƒ == Direction.SOUTH) {
            â˜ƒxxxxxxxx = (double)â˜ƒxxx + â˜ƒxxxxx.maxZ + 0.1F;
         }

         if (â˜ƒ == Direction.WEST) {
            â˜ƒxxxxxx = (double)â˜ƒx + â˜ƒxxxxx.minX - 0.1F;
         }

         if (â˜ƒ == Direction.EAST) {
            â˜ƒxxxxxx = (double)â˜ƒx + â˜ƒxxxxx.maxX + 0.1F;
         }

         this.add(new TerrainParticle(this.level, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 0.0, 0.0, 0.0, â˜ƒ, â˜ƒ).setPower(0.2F).scale(0.6F));
      }
   }

   public String countParticles() {
      return String.valueOf(this.particles.values().stream().mapToInt(Collection::size).sum());
   }

   private boolean hasSpaceInParticleLimit(ParticleGroup var1) {
      return this.trackedParticleCounts.getInt(â˜ƒ) < â˜ƒ.getLimit();
   }

   class MutableSpriteSet implements SpriteSet {
      private List<TextureAtlasSprite> sprites;

      @Override
      public TextureAtlasSprite get(int var1, int var2) {
         return (TextureAtlasSprite)this.sprites.get(â˜ƒ * (this.sprites.size() - 1) / â˜ƒ);
      }

      @Override
      public TextureAtlasSprite get(Random var1) {
         return (TextureAtlasSprite)this.sprites.get(â˜ƒ.nextInt(this.sprites.size()));
      }

      public void rebind(List<TextureAtlasSprite> var1) {
         this.sprites = ImmutableList.copyOf(â˜ƒ);
      }
   }

   @FunctionalInterface
   interface SpriteParticleRegistration<T extends ParticleOptions> {
      ParticleProvider<T> create(SpriteSet var1);
   }
}
