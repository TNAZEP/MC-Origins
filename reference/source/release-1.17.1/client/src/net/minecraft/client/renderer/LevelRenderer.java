package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Option;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelRenderer implements ResourceManagerReloadListener, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final int CHUNK_SIZE = 16;
   public static final int MAX_CHUNKS_WIDTH = 66;
   public static final int MAX_CHUNKS_AREA = 4356;
   private static final float SKY_DISC_RADIUS = 512.0F;
   private static final int MIN_FOG_DISTANCE = 32;
   private static final int RAIN_RADIUS = 10;
   private static final int RAIN_DIAMETER = 21;
   private static final int TRANSPARENT_SORT_COUNT = 15;
   private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation CLOUDS_LOCATION = new ResourceLocation("textures/environment/clouds.png");
   private static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation FORCEFIELD_LOCATION = new ResourceLocation("textures/misc/forcefield.png");
   private static final ResourceLocation RAIN_LOCATION = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation SNOW_LOCATION = new ResourceLocation("textures/environment/snow.png");
   public static final Direction[] DIRECTIONS = Direction.values();
   private final Minecraft minecraft;
   private final TextureManager textureManager;
   private final EntityRenderDispatcher entityRenderDispatcher;
   private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
   private final RenderBuffers renderBuffers;
   private ClientLevel level;
   private Set<ChunkRenderDispatcher.RenderChunk> chunksToCompile = Sets.<ChunkRenderDispatcher.RenderChunk>newLinkedHashSet();
   private final ObjectArrayList<LevelRenderer.RenderChunkInfo> renderChunks = new ObjectArrayList<>();
   private final Set<BlockEntity> globalBlockEntities = Sets.<BlockEntity>newHashSet();
   private ViewArea viewArea;
   private LevelRenderer.RenderInfoMap renderInfoMap;
   @Nullable
   private VertexBuffer starBuffer;
   @Nullable
   private VertexBuffer skyBuffer;
   @Nullable
   private VertexBuffer darkBuffer;
   private boolean generateClouds = true;
   @Nullable
   private VertexBuffer cloudBuffer;
   private final RunningTrimmedMean frameTimes = new RunningTrimmedMean(100);
   private int ticks;
   private final Int2ObjectMap<BlockDestructionProgress> destroyingBlocks = new Int2ObjectOpenHashMap<>();
   private final Long2ObjectMap<SortedSet<BlockDestructionProgress>> destructionProgress = new Long2ObjectOpenHashMap();
   private final Map<BlockPos, SoundInstance> playingRecords = Maps.<BlockPos, SoundInstance>newHashMap();
   @Nullable
   private RenderTarget entityTarget;
   @Nullable
   private PostChain entityEffect;
   @Nullable
   private RenderTarget translucentTarget;
   @Nullable
   private RenderTarget itemEntityTarget;
   @Nullable
   private RenderTarget particlesTarget;
   @Nullable
   private RenderTarget weatherTarget;
   @Nullable
   private RenderTarget cloudsTarget;
   @Nullable
   private PostChain transparencyChain;
   private double lastCameraX = Double.MIN_VALUE;
   private double lastCameraY = Double.MIN_VALUE;
   private double lastCameraZ = Double.MIN_VALUE;
   private int lastCameraChunkX = Integer.MIN_VALUE;
   private int lastCameraChunkY = Integer.MIN_VALUE;
   private int lastCameraChunkZ = Integer.MIN_VALUE;
   private double prevCamX = Double.MIN_VALUE;
   private double prevCamY = Double.MIN_VALUE;
   private double prevCamZ = Double.MIN_VALUE;
   private double prevCamRotX = Double.MIN_VALUE;
   private double prevCamRotY = Double.MIN_VALUE;
   private int prevCloudX = Integer.MIN_VALUE;
   private int prevCloudY = Integer.MIN_VALUE;
   private int prevCloudZ = Integer.MIN_VALUE;
   private Vec3 prevCloudColor = Vec3.ZERO;
   private CloudStatus prevCloudsType;
   private ChunkRenderDispatcher chunkRenderDispatcher;
   private int lastViewDistance = -1;
   private int renderedEntities;
   private int culledEntities;
   private Frustum cullingFrustum;
   private boolean captureFrustum;
   @Nullable
   private Frustum capturedFrustum;
   private final Vector4f[] frustumPoints = new Vector4f[8];
   private final Vector3d frustumPos = new Vector3d(0.0, 0.0, 0.0);
   private double xTransparentOld;
   private double yTransparentOld;
   private double zTransparentOld;
   private boolean needsUpdate = true;
   private int frameId;
   private int rainSoundTime;
   private final float[] rainSizeX = new float[1024];
   private final float[] rainSizeZ = new float[1024];

   public LevelRenderer(Minecraft var1, RenderBuffers var2) {
      this.minecraft = â˜ƒ;
      this.entityRenderDispatcher = â˜ƒ.getEntityRenderDispatcher();
      this.blockEntityRenderDispatcher = â˜ƒ.getBlockEntityRenderDispatcher();
      this.renderBuffers = â˜ƒ;
      this.textureManager = â˜ƒ.getTextureManager();

      for(int â˜ƒ = 0; â˜ƒ < 32; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 32; ++â˜ƒx) {
            float â˜ƒxx = (float)(â˜ƒx - 16);
            float â˜ƒxxx = (float)(â˜ƒ - 16);
            float â˜ƒxxxx = Mth.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx);
            this.rainSizeX[â˜ƒ << 5 | â˜ƒx] = -â˜ƒxxx / â˜ƒxxxx;
            this.rainSizeZ[â˜ƒ << 5 | â˜ƒx] = â˜ƒxx / â˜ƒxxxx;
         }
      }

      this.createStars();
      this.createLightSky();
      this.createDarkSky();
   }

   private void renderSnowAndRain(LightTexture var1, float var2, double var3, double var5, double var7) {
      float â˜ƒ = this.minecraft.level.getRainLevel(â˜ƒ);
      if (!(â˜ƒ <= 0.0F)) {
         â˜ƒ.turnOnLightLayer();
         Level â˜ƒx = this.minecraft.level;
         int â˜ƒxx = Mth.floor(â˜ƒ);
         int â˜ƒxxx = Mth.floor(â˜ƒ);
         int â˜ƒxxxx = Mth.floor(â˜ƒ);
         Tesselator â˜ƒxxxxx = Tesselator.getInstance();
         BufferBuilder â˜ƒxxxxxx = â˜ƒxxxxx.getBuilder();
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.enableDepthTest();
         int â˜ƒxxxxxxx = 5;
         if (Minecraft.useFancyGraphics()) {
            â˜ƒxxxxxxx = 10;
         }

         RenderSystem.depthMask(Minecraft.useShaderTransparency());
         int â˜ƒx = -1;
         float â˜ƒxx = (float)this.ticks + â˜ƒ;
         RenderSystem.setShader(GameRenderer::getParticleShader);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos.MutableBlockPos â˜ƒxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxx = â˜ƒxxxx - â˜ƒxxxxxxx; â˜ƒxxxx <= â˜ƒxxxx + â˜ƒxxxxxxx; ++â˜ƒxxxx) {
            for(int â˜ƒxxxxx = â˜ƒxx - â˜ƒxxxxxxx; â˜ƒxxxxx <= â˜ƒxx + â˜ƒxxxxxxx; ++â˜ƒxxxxx) {
               int â˜ƒxxxxxx = (â˜ƒxxxx - â˜ƒxxxx + 16) * 32 + â˜ƒxxxxx - â˜ƒxx + 16;
               double â˜ƒxxxxxxx = (double)this.rainSizeX[â˜ƒxxxxxx] * 0.5;
               double â˜ƒxxxxxxxx = (double)this.rainSizeZ[â˜ƒxxxxxx] * 0.5;
               â˜ƒxxx.set(â˜ƒxxxxx, 0, â˜ƒxxxx);
               Biome â˜ƒxxxxxxxxx = â˜ƒx.getBiome(â˜ƒxxx);
               if (â˜ƒxxxxxxxxx.getPrecipitation() != Biome.Precipitation.NONE) {
                  int â˜ƒxxxxxxxxxx = â˜ƒx.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, â˜ƒxxx).getY();
                  int â˜ƒxxxxxxxxxxx = â˜ƒxxx - â˜ƒxxxxxxx;
                  int â˜ƒxxxxxxxxxxxx = â˜ƒxxx + â˜ƒxxxxxxx;
                  if (â˜ƒxxxxxxxxxxx < â˜ƒxxxxxxxxxx) {
                     â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx;
                  }

                  if (â˜ƒxxxxxxxxxxxx < â˜ƒxxxxxxxxxx) {
                     â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx;
                  }

                  int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxx;
                  if (â˜ƒxxxxxxxxxx < â˜ƒxxx) {
                     â˜ƒxxxxxxxxxx = â˜ƒxxx;
                  }

                  if (â˜ƒxxxxxxxxxxx != â˜ƒxxxxxxxxxxxx) {
                     Random â˜ƒxxxxxxxxxx = new Random((long)(â˜ƒxxxxx * â˜ƒxxxxx * 3121 + â˜ƒxxxxx * 45238971 ^ â˜ƒxxxx * â˜ƒxxxx * 418711 + â˜ƒxxxx * 13761));
                     â˜ƒxxx.set(â˜ƒxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxx);
                     float â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx.getTemperature(â˜ƒxxx);
                     if (â˜ƒxxxxxxxxxxx >= 0.15F) {
                        if (â˜ƒx != 0) {
                           if (â˜ƒx >= 0) {
                              â˜ƒxxxxx.end();
                           }

                           â˜ƒx = 0;
                           RenderSystem.setShaderTexture(0, RAIN_LOCATION);
                           â˜ƒxxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                        }

                        int â˜ƒxxxxxxxxxxxx = this.ticks + â˜ƒxxxxx * â˜ƒxxxxx * 3121 + â˜ƒxxxxx * 45238971 + â˜ƒxxxx * â˜ƒxxxx * 418711 + â˜ƒxxxx * 13761 & 31;
                        float â˜ƒxxxxxxxxxxxxx = -((float)â˜ƒxxxxxxxxxxxx + â˜ƒ) / 32.0F * (3.0F + â˜ƒxxxxxxxxxx.nextFloat());
                        double â˜ƒxxxxxxxxxxxxxx = (double)â˜ƒxxxxx + 0.5 - â˜ƒ;
                        double â˜ƒxxxxxxxxxxxxxxx = (double)â˜ƒxxxx + 0.5 - â˜ƒ;
                        float â˜ƒxxxxxxxxxxxxxxxx = (float)Math.sqrt(â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx)
                           / (float)â˜ƒxxxxxxx;
                        float â˜ƒxxxxxxxxxxxxxxxxx = ((1.0F - â˜ƒxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxx) * 0.5F + 0.5F) * â˜ƒ;
                        â˜ƒxxx.set(â˜ƒxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxx);
                        int â˜ƒxxxxxxxxxxxxxxxxxx = getLightColor(â˜ƒx, â˜ƒxxx);
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ - â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ - â˜ƒxxxxxxxx + 0.5)
                           .uv(0.0F, (float)â˜ƒxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ + â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ + â˜ƒxxxxxxxx + 0.5)
                           .uv(1.0F, (float)â˜ƒxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ + â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ + â˜ƒxxxxxxxx + 0.5)
                           .uv(1.0F, (float)â˜ƒxxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ - â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ - â˜ƒxxxxxxxx + 0.5)
                           .uv(0.0F, (float)â˜ƒxxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                     } else {
                        if (â˜ƒx != 1) {
                           if (â˜ƒx >= 0) {
                              â˜ƒxxxxx.end();
                           }

                           â˜ƒx = 1;
                           RenderSystem.setShaderTexture(0, SNOW_LOCATION);
                           â˜ƒxxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                        }

                        float â˜ƒxxxxxxxxxx = -((float)(this.ticks & 511) + â˜ƒ) / 512.0F;
                        float â˜ƒxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxxx.nextDouble() + (double)â˜ƒxx * 0.01 * (double)((float)â˜ƒxxxxxxxxxx.nextGaussian()));
                        float â˜ƒxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxxx.nextDouble() + (double)(â˜ƒxx * (float)â˜ƒxxxxxxxxxx.nextGaussian()) * 0.001);
                        double â˜ƒxxxxxxxxxxxxx = (double)â˜ƒxxxxx + 0.5 - â˜ƒ;
                        double â˜ƒxxxxxxxxxxxxxx = (double)â˜ƒxxxx + 0.5 - â˜ƒ;
                        float â˜ƒxxxxxxxxxxxxxxx = (float)Math.sqrt(â˜ƒxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx)
                           / (float)â˜ƒxxxxxxx;
                        float â˜ƒxxxxxxxxxxxxxxxx = ((1.0F - â˜ƒxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx) * 0.3F + 0.5F) * â˜ƒ;
                        â˜ƒxxx.set(â˜ƒxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxx);
                        int â˜ƒxxxxxxxxxxxxxxxxx = getLightColor(â˜ƒx, â˜ƒxxx);
                        int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx >> 16 & 65535;
                        int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx & 65535;
                        int â˜ƒxxxxxxxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxxxxxxxx * 3 + 240) / 4;
                        int â˜ƒxxxxxxxxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxxxxxxxxxx * 3 + 240) / 4;
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ - â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ - â˜ƒxxxxxxxx + 0.5)
                           .uv(0.0F + â˜ƒxxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ + â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ + â˜ƒxxxxxxxx + 0.5)
                           .uv(1.0F + â˜ƒxxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ + â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ + â˜ƒxxxxxxxx + 0.5)
                           .uv(1.0F + â˜ƒxxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                        â˜ƒxxxxxx.vertex((double)â˜ƒxxxxx - â˜ƒ - â˜ƒxxxxxxx + 0.5, (double)â˜ƒxxxxxxxxxxx - â˜ƒ, (double)â˜ƒxxxx - â˜ƒ - â˜ƒxxxxxxxx + 0.5)
                           .uv(0.0F + â˜ƒxxxxxxxxxxx, (float)â˜ƒxxxxxxxxxxxx * 0.25F + â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxxx)
                           .color(1.0F, 1.0F, 1.0F, â˜ƒxxxxxxxxxxxxxxxx)
                           .uv2(â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx)
                           .endVertex();
                     }
                  }
               }
            }
         }

         if (â˜ƒx >= 0) {
            â˜ƒxxxxx.end();
         }

         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         â˜ƒ.turnOffLightLayer();
      }
   }

   public void tickRain(Camera var1) {
      float â˜ƒ = this.minecraft.level.getRainLevel(1.0F) / (Minecraft.useFancyGraphics() ? 1.0F : 2.0F);
      if (!(â˜ƒ <= 0.0F)) {
         Random â˜ƒx = new Random((long)this.ticks * 312987231L);
         LevelReader â˜ƒxx = this.minecraft.level;
         BlockPos â˜ƒxxx = new BlockPos(â˜ƒ.getPosition());
         BlockPos â˜ƒxxxx = null;
         int â˜ƒxxxxx = (int)(100.0F * â˜ƒ * â˜ƒ) / (this.minecraft.options.particles == ParticleStatus.DECREASED ? 2 : 1);

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxx) {
            int â˜ƒxxxxxxx = â˜ƒx.nextInt(21) - 10;
            int â˜ƒxxxxxxxx = â˜ƒx.nextInt(21) - 10;
            BlockPos â˜ƒxxxxxxxxx = â˜ƒxx.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, â˜ƒxxx.offset(â˜ƒxxxxxxx, 0, â˜ƒxxxxxxxx)).below();
            Biome â˜ƒxxxxxxxxxx = â˜ƒxx.getBiome(â˜ƒxxxxxxxxx);
            if (â˜ƒxxxxxxxxx.getY() > â˜ƒxx.getMinBuildHeight()
               && â˜ƒxxxxxxxxx.getY() <= â˜ƒxxx.getY() + 10
               && â˜ƒxxxxxxxxx.getY() >= â˜ƒxxx.getY() - 10
               && â˜ƒxxxxxxxxxx.getPrecipitation() == Biome.Precipitation.RAIN
               && â˜ƒxxxxxxxxxx.getTemperature(â˜ƒxxxxxxxxx) >= 0.15F) {
               â˜ƒxxxx = â˜ƒxxxxxxxxx;
               if (this.minecraft.options.particles == ParticleStatus.MINIMAL) {
                  break;
               }

               double â˜ƒxxxxxxxxxxx = â˜ƒx.nextDouble();
               double â˜ƒxxxxxxxxxxxx = â˜ƒx.nextDouble();
               BlockState â˜ƒxxxxxxxxxxxxx = â˜ƒxx.getBlockState(â˜ƒxxxxxxxxx);
               FluidState â˜ƒxxxxxxxxxxxxxx = â˜ƒxx.getFluidState(â˜ƒxxxxxxxxx);
               VoxelShape â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.getCollisionShape(â˜ƒxx, â˜ƒxxxxxxxxx);
               double â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.max(Direction.Axis.Y, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx);
               double â˜ƒxxxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxxxxxx.getHeight(â˜ƒxx, â˜ƒxxxxxxxxx);
               double â˜ƒxxxxxxxxxxxxxxxxxx = Math.max(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx);
               ParticleOptions â˜ƒxxxxxxxxxxxxxxxxxxx = !â˜ƒxxxxxxxxxxxxxx.is(FluidTags.LAVA)
                     && !â˜ƒxxxxxxxxxxxxx.is(Blocks.MAGMA_BLOCK)
                     && !CampfireBlock.isLitCampfire(â˜ƒxxxxxxxxxxxxx)
                  ? ParticleTypes.RAIN
                  : ParticleTypes.SMOKE;
               this.minecraft
                  .level
                  .addParticle(
                     â˜ƒxxxxxxxxxxxxxxxxxxx,
                     (double)â˜ƒxxxxxxxxx.getX() + â˜ƒxxxxxxxxxxx,
                     (double)â˜ƒxxxxxxxxx.getY() + â˜ƒxxxxxxxxxxxxxxxxxx,
                     (double)â˜ƒxxxxxxxxx.getZ() + â˜ƒxxxxxxxxxxxx,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (â˜ƒxxxx != null && â˜ƒx.nextInt(3) < this.rainSoundTime++) {
            this.rainSoundTime = 0;
            if (â˜ƒxxxx.getY() > â˜ƒxxx.getY() + 1 && â˜ƒxx.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, â˜ƒxxx).getY() > Mth.floor((float)â˜ƒxxx.getY())) {
               this.minecraft.level.playLocalSound(â˜ƒxxxx, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
            } else {
               this.minecraft.level.playLocalSound(â˜ƒxxxx, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
            }
         }
      }
   }

   public void close() {
      if (this.entityEffect != null) {
         this.entityEffect.close();
      }

      if (this.transparencyChain != null) {
         this.transparencyChain.close();
      }
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      this.initOutline();
      if (Minecraft.useShaderTransparency()) {
         this.initTransparency();
      }
   }

   public void initOutline() {
      if (this.entityEffect != null) {
         this.entityEffect.close();
      }

      ResourceLocation â˜ƒ = new ResourceLocation("shaders/post/entity_outline.json");

      try {
         this.entityEffect = new PostChain(this.minecraft.getTextureManager(), this.minecraft.getResourceManager(), this.minecraft.getMainRenderTarget(), â˜ƒ);
         this.entityEffect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
         this.entityTarget = this.entityEffect.getTempTarget("final");
      } catch (IOException var3) {
         LOGGER.warn("Failed to load shader: {}", â˜ƒ, var3);
         this.entityEffect = null;
         this.entityTarget = null;
      } catch (JsonSyntaxException var4) {
         LOGGER.warn("Failed to parse shader: {}", â˜ƒ, var4);
         this.entityEffect = null;
         this.entityTarget = null;
      }
   }

   private void initTransparency() {
      this.deinitTransparency();
      ResourceLocation â˜ƒ = new ResourceLocation("shaders/post/transparency.json");

      try {
         PostChain â˜ƒx = new PostChain(this.minecraft.getTextureManager(), this.minecraft.getResourceManager(), this.minecraft.getMainRenderTarget(), â˜ƒ);
         â˜ƒx.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
         RenderTarget â˜ƒxx = â˜ƒx.getTempTarget("translucent");
         RenderTarget â˜ƒxxx = â˜ƒx.getTempTarget("itemEntity");
         RenderTarget â˜ƒxxxx = â˜ƒx.getTempTarget("particles");
         RenderTarget â˜ƒxxxxx = â˜ƒx.getTempTarget("weather");
         RenderTarget â˜ƒxxxxxx = â˜ƒx.getTempTarget("clouds");
         this.transparencyChain = â˜ƒx;
         this.translucentTarget = â˜ƒxx;
         this.itemEntityTarget = â˜ƒxxx;
         this.particlesTarget = â˜ƒxxxx;
         this.weatherTarget = â˜ƒxxxxx;
         this.cloudsTarget = â˜ƒxxxxxx;
      } catch (Exception var9) {
         String â˜ƒxxxxxxx = var9 instanceof JsonSyntaxException ? "parse" : "load";
         String â˜ƒxxxxxxxx = "Failed to " + â˜ƒxxxxxxx + " shader: " + â˜ƒ;
         LevelRenderer.TransparencyShaderException â˜ƒxxxxxxxxx = new LevelRenderer.TransparencyShaderException(â˜ƒxxxxxxxx, var9);
         if (this.minecraft.getResourcePackRepository().getSelectedIds().size() > 1) {
            Component â˜ƒ;
            try {
               â˜ƒ = new TextComponent(this.minecraft.getResourceManager().getResource(â˜ƒ).getSourceName());
            } catch (IOException var8) {
               â˜ƒ = null;
            }

            this.minecraft.options.graphicsMode = GraphicsStatus.FANCY;
            this.minecraft.clearResourcePacksOnError(â˜ƒxxxxxxxxx, â˜ƒ);
         } else {
            CrashReport â˜ƒxxxxxxx = this.minecraft.fillReport(new CrashReport(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx));
            this.minecraft.options.graphicsMode = GraphicsStatus.FANCY;
            this.minecraft.options.save();
            LOGGER.fatal(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
            this.minecraft.emergencySave();
            Minecraft.crash(â˜ƒxxxxxxx);
         }
      }
   }

   private void deinitTransparency() {
      if (this.transparencyChain != null) {
         this.transparencyChain.close();
         this.translucentTarget.destroyBuffers();
         this.itemEntityTarget.destroyBuffers();
         this.particlesTarget.destroyBuffers();
         this.weatherTarget.destroyBuffers();
         this.cloudsTarget.destroyBuffers();
         this.transparencyChain = null;
         this.translucentTarget = null;
         this.itemEntityTarget = null;
         this.particlesTarget = null;
         this.weatherTarget = null;
         this.cloudsTarget = null;
      }
   }

   public void doEntityOutline() {
      if (this.shouldShowEntityOutlines()) {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ZERO,
            GlStateManager.DestFactor.ONE
         );
         this.entityTarget.blitToScreen(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), false);
         RenderSystem.disableBlend();
      }
   }

   protected boolean shouldShowEntityOutlines() {
      return !this.minecraft.gameRenderer.isPanoramicMode() && this.entityTarget != null && this.entityEffect != null && this.minecraft.player != null;
   }

   private void createDarkSky() {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      if (this.darkBuffer != null) {
         this.darkBuffer.close();
      }

      this.darkBuffer = new VertexBuffer();
      buildSkyDisc(â˜ƒx, -16.0F);
      this.darkBuffer.upload(â˜ƒx);
   }

   private void createLightSky() {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      if (this.skyBuffer != null) {
         this.skyBuffer.close();
      }

      this.skyBuffer = new VertexBuffer();
      buildSkyDisc(â˜ƒx, 16.0F);
      this.skyBuffer.upload(â˜ƒx);
   }

   private static void buildSkyDisc(BufferBuilder var0, float var1) {
      float â˜ƒ = Math.signum(â˜ƒ) * 512.0F;
      float â˜ƒx = 512.0F;
      RenderSystem.setShader(GameRenderer::getPositionShader);
      â˜ƒ.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
      â˜ƒ.vertex(0.0, (double)â˜ƒ, 0.0).endVertex();

      for(int â˜ƒxx = -180; â˜ƒxx <= 180; â˜ƒxx += 45) {
         â˜ƒ.vertex(
               (double)(â˜ƒ * Mth.cos((float)â˜ƒxx * (float) (Math.PI / 180.0))),
               (double)â˜ƒ,
               (double)(512.0F * Mth.sin((float)â˜ƒxx * (float) (Math.PI / 180.0)))
            )
            .endVertex();
      }

      â˜ƒ.end();
   }

   private void createStars() {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionShader);
      if (this.starBuffer != null) {
         this.starBuffer.close();
      }

      this.starBuffer = new VertexBuffer();
      this.drawStars(â˜ƒx);
      â˜ƒx.end();
      this.starBuffer.upload(â˜ƒx);
   }

   private void drawStars(BufferBuilder var1) {
      Random â˜ƒ = new Random(10842L);
      â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

      for(int â˜ƒx = 0; â˜ƒx < 1500; ++â˜ƒx) {
         double â˜ƒxx = (double)(â˜ƒ.nextFloat() * 2.0F - 1.0F);
         double â˜ƒxxx = (double)(â˜ƒ.nextFloat() * 2.0F - 1.0F);
         double â˜ƒxxxx = (double)(â˜ƒ.nextFloat() * 2.0F - 1.0F);
         double â˜ƒxxxxx = (double)(0.15F + â˜ƒ.nextFloat() * 0.1F);
         double â˜ƒxxxxxx = â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx;
         if (â˜ƒxxxxxx < 1.0 && â˜ƒxxxxxx > 0.01) {
            â˜ƒxxxxxx = 1.0 / Math.sqrt(â˜ƒxxxxxx);
            â˜ƒxx *= â˜ƒxxxxxx;
            â˜ƒxxx *= â˜ƒxxxxxx;
            â˜ƒxxxx *= â˜ƒxxxxxx;
            double â˜ƒxxxxxxx = â˜ƒxx * 100.0;
            double â˜ƒxxxxxxxx = â˜ƒxxx * 100.0;
            double â˜ƒxxxxxxxxx = â˜ƒxxxx * 100.0;
            double â˜ƒxxxxxxxxxx = Math.atan2(â˜ƒxx, â˜ƒxxxx);
            double â˜ƒxxxxxxxxxxx = Math.sin(â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxx = Math.cos(â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxx = Math.atan2(Math.sqrt(â˜ƒxx * â˜ƒxx + â˜ƒxxxx * â˜ƒxxxx), â˜ƒxxx);
            double â˜ƒxxxxxxxxxxxxxx = Math.sin(â˜ƒxxxxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxxxx = Math.cos(â˜ƒxxxxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.nextDouble() * Math.PI * 2.0;
            double â˜ƒxxxxxxxxxxxxxxxxx = Math.sin(â˜ƒxxxxxxxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxxxxxxx = Math.cos(â˜ƒxxxxxxxxxxxxxxxx);

            for(int â˜ƒxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxx < 4; ++â˜ƒxxxxxxxxxxxxxxxxxxx) {
               double â˜ƒxxxxxxxxxxxxxxxxxxxx = 0.0;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxx = (double)((â˜ƒxxxxxxxxxxxxxxxxxxx & 2) - 1) * â˜ƒxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxx = (double)((â˜ƒxxxxxxxxxxxxxxxxxxx + 1 & 2) - 1) * â˜ƒxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = 0.0;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx + 0.0 * â˜ƒxxxxxxxxxxxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 * â˜ƒxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx;
               double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxx;
               â˜ƒ.vertex(
                     â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  )
                  .endVertex();
            }
         }
      }
   }

   public void setLevel(@Nullable ClientLevel var1) {
      this.lastCameraX = Double.MIN_VALUE;
      this.lastCameraY = Double.MIN_VALUE;
      this.lastCameraZ = Double.MIN_VALUE;
      this.lastCameraChunkX = Integer.MIN_VALUE;
      this.lastCameraChunkY = Integer.MIN_VALUE;
      this.lastCameraChunkZ = Integer.MIN_VALUE;
      this.entityRenderDispatcher.setLevel(â˜ƒ);
      this.level = â˜ƒ;
      if (â˜ƒ != null) {
         this.renderChunks.ensureCapacity(4356 * â˜ƒ.getSectionsCount());
         this.allChanged();
      } else {
         this.chunksToCompile.clear();
         this.renderChunks.clear();
         if (this.viewArea != null) {
            this.viewArea.releaseAllBuffers();
            this.viewArea = null;
         }

         if (this.chunkRenderDispatcher != null) {
            this.chunkRenderDispatcher.dispose();
         }

         this.chunkRenderDispatcher = null;
         this.globalBlockEntities.clear();
      }
   }

   public void graphicsChanged() {
      if (Minecraft.useShaderTransparency()) {
         this.initTransparency();
      } else {
         this.deinitTransparency();
      }
   }

   public void allChanged() {
      if (this.level != null) {
         this.graphicsChanged();
         this.level.clearTintCaches();
         if (this.chunkRenderDispatcher == null) {
            this.chunkRenderDispatcher = new ChunkRenderDispatcher(
               this.level, this, Util.backgroundExecutor(), this.minecraft.is64Bit(), this.renderBuffers.fixedBufferPack()
            );
         } else {
            this.chunkRenderDispatcher.setLevel(this.level);
         }

         this.needsUpdate = true;
         this.generateClouds = true;
         ItemBlockRenderTypes.setFancy(Minecraft.useFancyGraphics());
         this.lastViewDistance = this.minecraft.options.renderDistance;
         if (this.viewArea != null) {
            this.viewArea.releaseAllBuffers();
         }

         this.resetChunksToCompile();
         synchronized(this.globalBlockEntities) {
            this.globalBlockEntities.clear();
         }

         this.viewArea = new ViewArea(this.chunkRenderDispatcher, this.level, this.minecraft.options.renderDistance, this);
         this.renderInfoMap = new LevelRenderer.RenderInfoMap(this.viewArea.chunks.length);
         if (this.level != null) {
            Entity â˜ƒ = this.minecraft.getCameraEntity();
            if (â˜ƒ != null) {
               this.viewArea.repositionCamera(â˜ƒ.getX(), â˜ƒ.getZ());
            }
         }
      }
   }

   protected void resetChunksToCompile() {
      this.chunksToCompile.clear();
      this.chunkRenderDispatcher.blockUntilClear();
   }

   public void resize(int var1, int var2) {
      this.needsUpdate();
      if (this.entityEffect != null) {
         this.entityEffect.resize(â˜ƒ, â˜ƒ);
      }

      if (this.transparencyChain != null) {
         this.transparencyChain.resize(â˜ƒ, â˜ƒ);
      }
   }

   public String getChunkStatistics() {
      int â˜ƒ = this.viewArea.chunks.length;
      int â˜ƒx = this.countRenderedChunks();
      return String.format(
         "C: %d/%d %sD: %d, %s",
         â˜ƒx,
         â˜ƒ,
         this.minecraft.smartCull ? "(s) " : "",
         this.lastViewDistance,
         this.chunkRenderDispatcher == null ? "null" : this.chunkRenderDispatcher.getStats()
      );
   }

   public ChunkRenderDispatcher getChunkRenderDispatcher() {
      return this.chunkRenderDispatcher;
   }

   public double getTotalChunks() {
      return (double)this.viewArea.chunks.length;
   }

   public double getLastViewDistance() {
      return (double)this.lastViewDistance;
   }

   public int countRenderedChunks() {
      int â˜ƒ = 0;

      for(LevelRenderer.RenderChunkInfo â˜ƒx : this.renderChunks) {
         if (!â˜ƒx.chunk.getCompiledChunk().hasNoRenderableLayers()) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   public String getEntityStatistics() {
      return "E: " + this.renderedEntities + "/" + this.level.getEntityCount() + ", B: " + this.culledEntities;
   }

   private void setupRender(Camera var1, Frustum var2, boolean var3, int var4, boolean var5) {
      Vec3 â˜ƒ = â˜ƒ.getPosition();
      if (this.minecraft.options.renderDistance != this.lastViewDistance) {
         this.allChanged();
      }

      this.level.getProfiler().push("camera");
      double â˜ƒ = this.minecraft.player.getX();
      double â˜ƒx = this.minecraft.player.getY();
      double â˜ƒxx = this.minecraft.player.getZ();
      double â˜ƒxxx = â˜ƒ - this.lastCameraX;
      double â˜ƒxxxx = â˜ƒx - this.lastCameraY;
      double â˜ƒxxxxx = â˜ƒxx - this.lastCameraZ;
      int â˜ƒxxxxxx = SectionPos.posToSectionCoord(â˜ƒ);
      int â˜ƒxxxxxxx = SectionPos.posToSectionCoord(â˜ƒx);
      int â˜ƒxxxxxxxx = SectionPos.posToSectionCoord(â˜ƒxx);
      if (this.lastCameraChunkX != â˜ƒxxxxxx
         || this.lastCameraChunkY != â˜ƒxxxxxxx
         || this.lastCameraChunkZ != â˜ƒxxxxxxxx
         || â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx > 16.0) {
         this.lastCameraX = â˜ƒ;
         this.lastCameraY = â˜ƒx;
         this.lastCameraZ = â˜ƒxx;
         this.lastCameraChunkX = â˜ƒxxxxxx;
         this.lastCameraChunkY = â˜ƒxxxxxxx;
         this.lastCameraChunkZ = â˜ƒxxxxxxxx;
         this.viewArea.repositionCamera(â˜ƒ, â˜ƒxx);
      }

      this.chunkRenderDispatcher.setCamera(â˜ƒ);
      this.level.getProfiler().popPush("cull");
      this.minecraft.getProfiler().popPush("culling");
      BlockPos â˜ƒ = â˜ƒ.getBlockPosition();
      ChunkRenderDispatcher.RenderChunk â˜ƒx = this.viewArea.getRenderChunkAt(â˜ƒ);
      int â˜ƒxx = 16;
      BlockPos â˜ƒxxx = new BlockPos(Mth.floor(â˜ƒ.x / 16.0) * 16, Mth.floor(â˜ƒ.y / 16.0) * 16, Mth.floor(â˜ƒ.z / 16.0) * 16);
      float â˜ƒxxxx = â˜ƒ.getXRot();
      float â˜ƒxxxxx = â˜ƒ.getYRot();
      this.needsUpdate = this.needsUpdate
         || !this.chunksToCompile.isEmpty()
         || â˜ƒ.x != this.prevCamX
         || â˜ƒ.y != this.prevCamY
         || â˜ƒ.z != this.prevCamZ
         || (double)â˜ƒxxxx != this.prevCamRotX
         || (double)â˜ƒxxxxx != this.prevCamRotY;
      this.prevCamX = â˜ƒ.x;
      this.prevCamY = â˜ƒ.y;
      this.prevCamZ = â˜ƒ.z;
      this.prevCamRotX = (double)â˜ƒxxxx;
      this.prevCamRotY = (double)â˜ƒxxxxx;
      this.minecraft.getProfiler().popPush("update");
      if (!â˜ƒ && this.needsUpdate) {
         this.needsUpdate = false;
         this.updateRenderChunks(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, 16, â˜ƒxxx);
      }

      this.minecraft.getProfiler().popPush("rebuildNear");
      Set<ChunkRenderDispatcher.RenderChunk> â˜ƒ = this.chunksToCompile;
      this.chunksToCompile = Sets.<ChunkRenderDispatcher.RenderChunk>newLinkedHashSet();

      for(LevelRenderer.RenderChunkInfo â˜ƒx : this.renderChunks) {
         ChunkRenderDispatcher.RenderChunk â˜ƒxx = â˜ƒx.chunk;
         if (â˜ƒxx.isDirty() || â˜ƒ.contains(â˜ƒxx)) {
            this.needsUpdate = true;
            BlockPos â˜ƒxxx = â˜ƒxx.getOrigin().offset(8, 8, 8);
            boolean â˜ƒxxxx = â˜ƒxxx.distSqr(â˜ƒ) < 768.0;
            if (!â˜ƒxx.isDirtyFromPlayer() && !â˜ƒxxxx) {
               this.chunksToCompile.add(â˜ƒxx);
            } else {
               this.minecraft.getProfiler().push("build near");
               this.chunkRenderDispatcher.rebuildChunkSync(â˜ƒxx);
               â˜ƒxx.setNotDirty();
               this.minecraft.getProfiler().pop();
            }
         }
      }

      this.chunksToCompile.addAll(â˜ƒ);
      this.minecraft.getProfiler().pop();
   }

   private void updateRenderChunks(
      Frustum var1, int var2, boolean var3, Vec3 var4, BlockPos var5, ChunkRenderDispatcher.RenderChunk var6, int var7, BlockPos var8
   ) {
      this.renderChunks.clear();
      Queue<LevelRenderer.RenderChunkInfo> â˜ƒ = Queues.<LevelRenderer.RenderChunkInfo>newArrayDeque();
      Entity.setViewScale(Mth.clamp((double)this.minecraft.options.renderDistance / 8.0, 1.0, 2.5) * (double)this.minecraft.options.entityDistanceScaling);
      boolean â˜ƒx = this.minecraft.smartCull;
      if (â˜ƒ == null) {
         int â˜ƒxx = â˜ƒ.getY() > this.level.getMinBuildHeight() ? this.level.getMaxBuildHeight() - 8 : this.level.getMinBuildHeight() + 8;
         int â˜ƒxxx = Mth.floor(â˜ƒ.x / (double)â˜ƒ) * â˜ƒ;
         int â˜ƒxxxx = Mth.floor(â˜ƒ.z / (double)â˜ƒ) * â˜ƒ;
         List<LevelRenderer.RenderChunkInfo> â˜ƒxxxxx = Lists.<LevelRenderer.RenderChunkInfo>newArrayList();

         for(int â˜ƒxxxxxx = -this.lastViewDistance; â˜ƒxxxxxx <= this.lastViewDistance; ++â˜ƒxxxxxx) {
            for(int â˜ƒxxxxxxx = -this.lastViewDistance; â˜ƒxxxxxxx <= this.lastViewDistance; ++â˜ƒxxxxxxx) {
               ChunkRenderDispatcher.RenderChunk â˜ƒxxxxxxxx = this.viewArea
                  .getRenderChunkAt(
                     new BlockPos(â˜ƒxxx + SectionPos.sectionToBlockCoord(â˜ƒxxxxxx, 8), â˜ƒxx, â˜ƒxxxx + SectionPos.sectionToBlockCoord(â˜ƒxxxxxxx, 8))
                  );
               if (â˜ƒxxxxxxxx != null && â˜ƒ.isVisible(â˜ƒxxxxxxxx.bb)) {
                  â˜ƒxxxxxxxx.setFrame(â˜ƒ);
                  â˜ƒxxxxx.add(new LevelRenderer.RenderChunkInfo(â˜ƒxxxxxxxx, null, 0));
               }
            }
         }

         â˜ƒxxxxx.sort(Comparator.comparingDouble(var1x -> â˜ƒ.distSqr(var1x.chunk.getOrigin().offset(8, 8, 8))));
         â˜ƒ.addAll(â˜ƒxxxxx);
      } else {
         if (â˜ƒ && this.level.getBlockState(â˜ƒ).isSolidRender(this.level, â˜ƒ)) {
            â˜ƒx = false;
         }

         â˜ƒ.setFrame(â˜ƒ);
         â˜ƒ.add(new LevelRenderer.RenderChunkInfo(â˜ƒ, null, 0));
      }

      this.minecraft.getProfiler().push("iteration");
      int â˜ƒ = this.minecraft.options.renderDistance;
      this.renderInfoMap.clear();

      while(!â˜ƒ.isEmpty()) {
         LevelRenderer.RenderChunkInfo â˜ƒx = (LevelRenderer.RenderChunkInfo)â˜ƒ.poll();
         ChunkRenderDispatcher.RenderChunk â˜ƒxx = â˜ƒx.chunk;
         this.renderChunks.add(â˜ƒx);

         for(Direction â˜ƒxxx : DIRECTIONS) {
            ChunkRenderDispatcher.RenderChunk â˜ƒxxxx = this.getRelativeFrom(â˜ƒ, â˜ƒxx, â˜ƒxxx);
            if (!â˜ƒx || !â˜ƒx.hasDirection(â˜ƒxxx.getOpposite())) {
               if (â˜ƒx && â˜ƒx.hasSourceDirections()) {
                  ChunkRenderDispatcher.CompiledChunk â˜ƒxxxxx = â˜ƒxx.getCompiledChunk();
                  boolean â˜ƒxxxxxx = false;

                  for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < DIRECTIONS.length; ++â˜ƒxxxxxxx) {
                     if (â˜ƒx.hasSourceDirection(â˜ƒxxxxxxx) && â˜ƒxxxxx.facesCanSeeEachother(DIRECTIONS[â˜ƒxxxxxxx].getOpposite(), â˜ƒxxx)) {
                        â˜ƒxxxxxx = true;
                        break;
                     }
                  }

                  if (!â˜ƒxxxxxx) {
                     continue;
                  }
               }

               if (â˜ƒxxxx != null && â˜ƒxxxx.hasAllNeighbors()) {
                  if (!â˜ƒxxxx.setFrame(â˜ƒ)) {
                     LevelRenderer.RenderChunkInfo â˜ƒxxxxx = this.renderInfoMap.get(â˜ƒxxxx);
                     if (â˜ƒxxxxx != null) {
                        â˜ƒxxxxx.addSourceDirection(â˜ƒxxx);
                     }
                  } else if (â˜ƒ.isVisible(â˜ƒxxxx.bb)) {
                     LevelRenderer.RenderChunkInfo â˜ƒxxxxx = new LevelRenderer.RenderChunkInfo(â˜ƒxxxx, â˜ƒxxx, â˜ƒx.step + 1);
                     â˜ƒxxxxx.setDirections(â˜ƒx.directions, â˜ƒxxx);
                     â˜ƒ.add(â˜ƒxxxxx);
                     this.renderInfoMap.put(â˜ƒxxxx, â˜ƒxxxxx);
                  }
               }
            }
         }
      }

      this.minecraft.getProfiler().pop();
   }

   @Nullable
   private ChunkRenderDispatcher.RenderChunk getRelativeFrom(BlockPos var1, ChunkRenderDispatcher.RenderChunk var2, Direction var3) {
      BlockPos â˜ƒ = â˜ƒ.getRelativeOrigin(â˜ƒ);
      if (Mth.abs(â˜ƒ.getX() - â˜ƒ.getX()) > this.lastViewDistance * 16) {
         return null;
      } else if (â˜ƒ.getY() < this.level.getMinBuildHeight() || â˜ƒ.getY() >= this.level.getMaxBuildHeight()) {
         return null;
      } else {
         return Mth.abs(â˜ƒ.getZ() - â˜ƒ.getZ()) > this.lastViewDistance * 16 ? null : this.viewArea.getRenderChunkAt(â˜ƒ);
      }
   }

   private void captureFrustum(Matrix4f var1, Matrix4f var2, double var3, double var5, double var7, Frustum var9) {
      this.capturedFrustum = â˜ƒ;
      Matrix4f â˜ƒ = â˜ƒ.copy();
      â˜ƒ.multiply(â˜ƒ);
      â˜ƒ.invert();
      this.frustumPos.x = â˜ƒ;
      this.frustumPos.y = â˜ƒ;
      this.frustumPos.z = â˜ƒ;
      this.frustumPoints[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.frustumPoints[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.frustumPoints[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.frustumPoints[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.frustumPoints[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.frustumPoints[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.frustumPoints[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.frustumPoints[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
         this.frustumPoints[â˜ƒx].transform(â˜ƒ);
         this.frustumPoints[â˜ƒx].perspectiveDivide();
      }
   }

   public void prepareCullFrustum(PoseStack var1, Vec3 var2, Matrix4f var3) {
      Matrix4f â˜ƒ = â˜ƒ.last().pose();
      double â˜ƒx = â˜ƒ.x();
      double â˜ƒxx = â˜ƒ.y();
      double â˜ƒxxx = â˜ƒ.z();
      this.cullingFrustum = new Frustum(â˜ƒ, â˜ƒ);
      this.cullingFrustum.prepare(â˜ƒx, â˜ƒxx, â˜ƒxxx);
   }

   public void renderLevel(PoseStack var1, float var2, long var3, boolean var5, Camera var6, GameRenderer var7, LightTexture var8, Matrix4f var9) {
      RenderSystem.setShaderGameTime(this.level.getGameTime(), â˜ƒ);
      this.blockEntityRenderDispatcher.prepare(this.level, â˜ƒ, this.minecraft.hitResult);
      this.entityRenderDispatcher.prepare(this.level, â˜ƒ, this.minecraft.crosshairPickEntity);
      ProfilerFiller â˜ƒx = this.level.getProfiler();
      â˜ƒx.popPush("light_updates");
      this.minecraft.level.getChunkSource().getLightEngine().runUpdates(Integer.MAX_VALUE, true, true);
      Vec3 â˜ƒxx = â˜ƒ.getPosition();
      double â˜ƒxxx = â˜ƒxx.x();
      double â˜ƒxxxx = â˜ƒxx.y();
      double â˜ƒxxxxx = â˜ƒxx.z();
      Matrix4f â˜ƒxxxxxx = â˜ƒ.last().pose();
      â˜ƒx.popPush("culling");
      boolean â˜ƒxxxxxxx = this.capturedFrustum != null;
      Frustum â˜ƒ;
      if (â˜ƒxxxxxxx) {
         â˜ƒ = this.capturedFrustum;
         â˜ƒ.prepare(this.frustumPos.x, this.frustumPos.y, this.frustumPos.z);
      } else {
         â˜ƒ = this.cullingFrustum;
      }

      this.minecraft.getProfiler().popPush("captureFrustum");
      if (this.captureFrustum) {
         this.captureFrustum(â˜ƒxxxxxx, â˜ƒ, â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, â˜ƒxxxxxxx ? new Frustum(â˜ƒxxxxxx, â˜ƒ) : â˜ƒ);
         this.captureFrustum = false;
      }

      â˜ƒx.popPush("clear");
      FogRenderer.setupColor(â˜ƒ, â˜ƒ, this.minecraft.level, this.minecraft.options.renderDistance, â˜ƒ.getDarkenWorldAmount(â˜ƒ));
      FogRenderer.levelFogColor();
      RenderSystem.clear(16640, Minecraft.ON_OSX);
      float â˜ƒx = â˜ƒ.getRenderDistance();
      boolean â˜ƒxx = this.minecraft.level.effects().isFoggyAt(Mth.floor(â˜ƒxxx), Mth.floor(â˜ƒxxxx))
         || this.minecraft.gui.getBossOverlay().shouldCreateWorldFog();
      â˜ƒx.popPush("sky");
      RenderSystem.setShader(GameRenderer::getPositionShader);
      this.renderSky(â˜ƒ, â˜ƒ, â˜ƒ, () -> FogRenderer.setupFog(â˜ƒ, FogRenderer.FogMode.FOG_SKY, â˜ƒ, â˜ƒ));
      â˜ƒx.popPush("fog");
      FogRenderer.setupFog(â˜ƒ, FogRenderer.FogMode.FOG_TERRAIN, Math.max(â˜ƒx - 16.0F, 32.0F), â˜ƒxx);
      â˜ƒx.popPush("terrain_setup");
      this.setupRender(â˜ƒ, â˜ƒ, â˜ƒxxxxxxx, this.frameId++, this.minecraft.player.isSpectator());
      â˜ƒx.popPush("updatechunks");
      int â˜ƒxxx = 30;
      int â˜ƒxxxx = this.minecraft.options.framerateLimit;
      long â˜ƒxxxxx = 33333333L;
      long â˜ƒ;
      if ((double)â˜ƒxxxx == Option.FRAMERATE_LIMIT.getMaxValue()) {
         â˜ƒ = 0L;
      } else {
         â˜ƒ = (long)(1000000000 / â˜ƒxxxx);
      }

      long â˜ƒ = Util.getNanos() - â˜ƒ;
      long â˜ƒx = this.frameTimes.registerValueAndGetMean(â˜ƒ);
      long â˜ƒxx = â˜ƒx * 3L / 2L;
      long â˜ƒxxx = Mth.clamp(â˜ƒxx, â˜ƒ, 33333333L);
      this.compileChunksUntil(â˜ƒ + â˜ƒxxx);
      â˜ƒx.popPush("terrain");
      this.renderChunkLayer(RenderType.solid(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
      this.renderChunkLayer(RenderType.cutoutMipped(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
      this.renderChunkLayer(RenderType.cutout(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
      if (this.level.effects().constantAmbientLight()) {
         Lighting.setupNetherLevel(â˜ƒ.last().pose());
      } else {
         Lighting.setupLevel(â˜ƒ.last().pose());
      }

      â˜ƒx.popPush("entities");
      this.renderedEntities = 0;
      this.culledEntities = 0;
      if (this.itemEntityTarget != null) {
         this.itemEntityTarget.clear(Minecraft.ON_OSX);
         this.itemEntityTarget.copyDepthFrom(this.minecraft.getMainRenderTarget());
         this.minecraft.getMainRenderTarget().bindWrite(false);
      }

      if (this.weatherTarget != null) {
         this.weatherTarget.clear(Minecraft.ON_OSX);
      }

      if (this.shouldShowEntityOutlines()) {
         this.entityTarget.clear(Minecraft.ON_OSX);
         this.minecraft.getMainRenderTarget().bindWrite(false);
      }

      boolean â˜ƒ = false;
      MultiBufferSource.BufferSource â˜ƒx = this.renderBuffers.bufferSource();

      for(Entity â˜ƒxx : this.level.entitiesForRendering()) {
         if ((this.entityRenderDispatcher.shouldRender(â˜ƒxx, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx) || â˜ƒxx.hasIndirectPassenger(this.minecraft.player))
            && (â˜ƒxx != â˜ƒ.getEntity() || â˜ƒ.isDetached() || â˜ƒ.getEntity() instanceof LivingEntity && ((LivingEntity)â˜ƒ.getEntity()).isSleeping())
            && (!(â˜ƒxx instanceof LocalPlayer) || â˜ƒ.getEntity() == â˜ƒxx)) {
            ++this.renderedEntities;
            if (â˜ƒxx.tickCount == 0) {
               â˜ƒxx.xOld = â˜ƒxx.getX();
               â˜ƒxx.yOld = â˜ƒxx.getY();
               â˜ƒxx.zOld = â˜ƒxx.getZ();
            }

            MultiBufferSource â˜ƒxxx;
            if (this.shouldShowEntityOutlines() && this.minecraft.shouldEntityAppearGlowing(â˜ƒxx)) {
               â˜ƒ = true;
               OutlineBufferSource â˜ƒxxxx = this.renderBuffers.outlineBufferSource();
               â˜ƒxxx = â˜ƒxxxx;
               int â˜ƒxxxxx = â˜ƒxx.getTeamColor();
               int â˜ƒxxxxxx = 255;
               int â˜ƒxxxxxxx = â˜ƒxxxxx >> 16 & 0xFF;
               int â˜ƒxxxxxxxx = â˜ƒxxxxx >> 8 & 0xFF;
               int â˜ƒxxxxxxxxx = â˜ƒxxxxx & 0xFF;
               â˜ƒxxxx.setColor(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 255);
            } else {
               â˜ƒxxx = â˜ƒx;
            }

            this.renderEntity(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ, â˜ƒxxx);
         }
      }

      â˜ƒx.endLastBatch();
      this.checkPoseStack(â˜ƒ);
      â˜ƒx.endBatch(RenderType.entitySolid(TextureAtlas.LOCATION_BLOCKS));
      â˜ƒx.endBatch(RenderType.entityCutout(TextureAtlas.LOCATION_BLOCKS));
      â˜ƒx.endBatch(RenderType.entityCutoutNoCull(TextureAtlas.LOCATION_BLOCKS));
      â˜ƒx.endBatch(RenderType.entitySmoothCutout(TextureAtlas.LOCATION_BLOCKS));
      â˜ƒx.popPush("blockentities");

      for(LevelRenderer.RenderChunkInfo â˜ƒxx : this.renderChunks) {
         List<BlockEntity> â˜ƒxxx = â˜ƒxx.chunk.getCompiledChunk().getRenderableBlockEntities();
         if (!â˜ƒxxx.isEmpty()) {
            for(BlockEntity â˜ƒxxxx : â˜ƒxxx) {
               BlockPos â˜ƒxxxxx = â˜ƒxxxx.getBlockPos();
               MultiBufferSource â˜ƒxxxxxx = â˜ƒx;
               â˜ƒ.pushPose();
               â˜ƒ.translate((double)â˜ƒxxxxx.getX() - â˜ƒxxx, (double)â˜ƒxxxxx.getY() - â˜ƒxxxx, (double)â˜ƒxxxxx.getZ() - â˜ƒxxxxx);
               SortedSet<BlockDestructionProgress> â˜ƒxxxxxxx = (SortedSet)this.destructionProgress.get(â˜ƒxxxxx.asLong());
               if (â˜ƒxxxxxxx != null && !â˜ƒxxxxxxx.isEmpty()) {
                  int â˜ƒxxxxxxxx = ((BlockDestructionProgress)â˜ƒxxxxxxx.last()).getProgress();
                  if (â˜ƒxxxxxxxx >= 0) {
                     PoseStack.Pose â˜ƒxxxxxxxxx = â˜ƒ.last();
                     VertexConsumer â˜ƒxxxxxxxxxx = new SheetedDecalTextureGenerator(
                        this.renderBuffers.crumblingBufferSource().getBuffer((RenderType)ModelBakery.DESTROY_TYPES.get(â˜ƒxxxxxxxx)),
                        â˜ƒxxxxxxxxx.pose(),
                        â˜ƒxxxxxxxxx.normal()
                     );
                     â˜ƒxxxxxx = var2x -> {
                        VertexConsumer â˜ƒ = â˜ƒ.getBuffer(var2x);
                        return var2x.affectsCrumbling() ? VertexMultiConsumer.create(â˜ƒ, â˜ƒ) : â˜ƒ;
                     };
                  }
               }

               this.blockEntityRenderDispatcher.render(â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒxxxxxx);
               â˜ƒ.popPose();
            }
         }
      }

      synchronized(this.globalBlockEntities) {
         for(BlockEntity â˜ƒxx : this.globalBlockEntities) {
            BlockPos â˜ƒxxx = â˜ƒxx.getBlockPos();
            â˜ƒ.pushPose();
            â˜ƒ.translate((double)â˜ƒxxx.getX() - â˜ƒxxx, (double)â˜ƒxxx.getY() - â˜ƒxxxx, (double)â˜ƒxxx.getZ() - â˜ƒxxxxx);
            this.blockEntityRenderDispatcher.render(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒx);
            â˜ƒ.popPose();
         }
      }

      this.checkPoseStack(â˜ƒ);
      â˜ƒx.endBatch(RenderType.solid());
      â˜ƒx.endBatch(RenderType.endPortal());
      â˜ƒx.endBatch(RenderType.endGateway());
      â˜ƒx.endBatch(Sheets.solidBlockSheet());
      â˜ƒx.endBatch(Sheets.cutoutBlockSheet());
      â˜ƒx.endBatch(Sheets.bedSheet());
      â˜ƒx.endBatch(Sheets.shulkerBoxSheet());
      â˜ƒx.endBatch(Sheets.signSheet());
      â˜ƒx.endBatch(Sheets.chestSheet());
      this.renderBuffers.outlineBufferSource().endOutlineBatch();
      if (â˜ƒ) {
         this.entityEffect.process(â˜ƒ);
         this.minecraft.getMainRenderTarget().bindWrite(false);
      }

      â˜ƒx.popPush("destroyProgress");

      for(Entry<SortedSet<BlockDestructionProgress>> â˜ƒxx : this.destructionProgress.long2ObjectEntrySet()) {
         BlockPos â˜ƒxxx = BlockPos.of(â˜ƒxx.getLongKey());
         double â˜ƒxxxx = (double)â˜ƒxxx.getX() - â˜ƒxxx;
         double â˜ƒxxxxx = (double)â˜ƒxxx.getY() - â˜ƒxxxx;
         double â˜ƒxxxxxx = (double)â˜ƒxxx.getZ() - â˜ƒxxxxx;
         if (!(â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx > 1024.0)) {
            SortedSet<BlockDestructionProgress> â˜ƒxxxxxxx = (SortedSet)â˜ƒxx.getValue();
            if (â˜ƒxxxxxxx != null && !â˜ƒxxxxxxx.isEmpty()) {
               int â˜ƒxxxxxxxx = ((BlockDestructionProgress)â˜ƒxxxxxxx.last()).getProgress();
               â˜ƒ.pushPose();
               â˜ƒ.translate((double)â˜ƒxxx.getX() - â˜ƒxxx, (double)â˜ƒxxx.getY() - â˜ƒxxxx, (double)â˜ƒxxx.getZ() - â˜ƒxxxxx);
               PoseStack.Pose â˜ƒxxxxxxxxx = â˜ƒ.last();
               VertexConsumer â˜ƒxxxxxxxxxx = new SheetedDecalTextureGenerator(
                  this.renderBuffers.crumblingBufferSource().getBuffer((RenderType)ModelBakery.DESTROY_TYPES.get(â˜ƒxxxxxxxx)),
                  â˜ƒxxxxxxxxx.pose(),
                  â˜ƒxxxxxxxxx.normal()
               );
               this.minecraft.getBlockRenderer().renderBreakingTexture(this.level.getBlockState(â˜ƒxxx), â˜ƒxxx, this.level, â˜ƒ, â˜ƒxxxxxxxxxx);
               â˜ƒ.popPose();
            }
         }
      }

      this.checkPoseStack(â˜ƒ);
      HitResult â˜ƒxx = this.minecraft.hitResult;
      if (â˜ƒ && â˜ƒxx != null && â˜ƒxx.getType() == HitResult.Type.BLOCK) {
         â˜ƒx.popPush("outline");
         BlockPos â˜ƒxxx = ((BlockHitResult)â˜ƒxx).getBlockPos();
         BlockState â˜ƒxxxx = this.level.getBlockState(â˜ƒxxx);
         if (!â˜ƒxxxx.isAir() && this.level.getWorldBorder().isWithinBounds(â˜ƒxxx)) {
            VertexConsumer â˜ƒxxxxx = â˜ƒx.getBuffer(RenderType.lines());
            this.renderHitOutline(â˜ƒ, â˜ƒxxxxx, â˜ƒ.getEntity(), â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx);
         }
      }

      PoseStack â˜ƒxx = RenderSystem.getModelViewStack();
      â˜ƒxx.pushPose();
      â˜ƒxx.mulPoseMatrix(â˜ƒ.last().pose());
      RenderSystem.applyModelViewMatrix();
      this.minecraft.debugRenderer.render(â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      â˜ƒxx.popPose();
      RenderSystem.applyModelViewMatrix();
      â˜ƒx.endBatch(Sheets.translucentCullBlockSheet());
      â˜ƒx.endBatch(Sheets.bannerSheet());
      â˜ƒx.endBatch(Sheets.shieldSheet());
      â˜ƒx.endBatch(RenderType.armorGlint());
      â˜ƒx.endBatch(RenderType.armorEntityGlint());
      â˜ƒx.endBatch(RenderType.glint());
      â˜ƒx.endBatch(RenderType.glintDirect());
      â˜ƒx.endBatch(RenderType.glintTranslucent());
      â˜ƒx.endBatch(RenderType.entityGlint());
      â˜ƒx.endBatch(RenderType.entityGlintDirect());
      â˜ƒx.endBatch(RenderType.waterMask());
      this.renderBuffers.crumblingBufferSource().endBatch();
      if (this.transparencyChain != null) {
         â˜ƒx.endBatch(RenderType.lines());
         â˜ƒx.endBatch();
         this.translucentTarget.clear(Minecraft.ON_OSX);
         this.translucentTarget.copyDepthFrom(this.minecraft.getMainRenderTarget());
         â˜ƒx.popPush("translucent");
         this.renderChunkLayer(RenderType.translucent(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
         â˜ƒx.popPush("string");
         this.renderChunkLayer(RenderType.tripwire(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
         this.particlesTarget.clear(Minecraft.ON_OSX);
         this.particlesTarget.copyDepthFrom(this.minecraft.getMainRenderTarget());
         RenderStateShard.PARTICLES_TARGET.setupRenderState();
         â˜ƒx.popPush("particles");
         this.minecraft.particleEngine.render(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
         RenderStateShard.PARTICLES_TARGET.clearRenderState();
      } else {
         â˜ƒx.popPush("translucent");
         if (this.translucentTarget != null) {
            this.translucentTarget.clear(Minecraft.ON_OSX);
         }

         this.renderChunkLayer(RenderType.translucent(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
         â˜ƒx.endBatch(RenderType.lines());
         â˜ƒx.endBatch();
         â˜ƒx.popPush("string");
         this.renderChunkLayer(RenderType.tripwire(), â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
         â˜ƒx.popPush("particles");
         this.minecraft.particleEngine.render(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      â˜ƒxx.pushPose();
      â˜ƒxx.mulPoseMatrix(â˜ƒ.last().pose());
      RenderSystem.applyModelViewMatrix();
      if (this.minecraft.options.getCloudsType() != CloudStatus.OFF) {
         if (this.transparencyChain != null) {
            this.cloudsTarget.clear(Minecraft.ON_OSX);
            RenderStateShard.CLOUDS_TARGET.setupRenderState();
            â˜ƒx.popPush("clouds");
            this.renderClouds(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
            RenderStateShard.CLOUDS_TARGET.clearRenderState();
         } else {
            â˜ƒx.popPush("clouds");
            RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
            this.renderClouds(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         }
      }

      if (this.transparencyChain != null) {
         RenderStateShard.WEATHER_TARGET.setupRenderState();
         â˜ƒx.popPush("weather");
         this.renderSnowAndRain(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         this.renderWorldBorder(â˜ƒ);
         RenderStateShard.WEATHER_TARGET.clearRenderState();
         this.transparencyChain.process(â˜ƒ);
         this.minecraft.getMainRenderTarget().bindWrite(false);
      } else {
         RenderSystem.depthMask(false);
         â˜ƒx.popPush("weather");
         this.renderSnowAndRain(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         this.renderWorldBorder(â˜ƒ);
         RenderSystem.depthMask(true);
      }

      this.renderDebug(â˜ƒ);
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      â˜ƒxx.popPose();
      RenderSystem.applyModelViewMatrix();
      FogRenderer.setupNoFog();
   }

   private void checkPoseStack(PoseStack var1) {
      if (!â˜ƒ.clear()) {
         throw new IllegalStateException("Pose stack not empty");
      }
   }

   private void renderEntity(Entity var1, double var2, double var4, double var6, float var8, PoseStack var9, MultiBufferSource var10) {
      double â˜ƒ = Mth.lerp((double)â˜ƒ, â˜ƒ.xOld, â˜ƒ.getX());
      double â˜ƒx = Mth.lerp((double)â˜ƒ, â˜ƒ.yOld, â˜ƒ.getY());
      double â˜ƒxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zOld, â˜ƒ.getZ());
      float â˜ƒxxx = Mth.lerp(â˜ƒ, â˜ƒ.yRotO, â˜ƒ.getYRot());
      this.entityRenderDispatcher
         .render(â˜ƒ, â˜ƒ - â˜ƒ, â˜ƒx - â˜ƒ, â˜ƒxx - â˜ƒ, â˜ƒxxx, â˜ƒ, â˜ƒ, â˜ƒ, this.entityRenderDispatcher.getPackedLightCoords(â˜ƒ, â˜ƒ));
   }

   private void renderChunkLayer(RenderType var1, PoseStack var2, double var3, double var5, double var7, Matrix4f var9) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      â˜ƒ.setupRenderState();
      if (â˜ƒ == RenderType.translucent()) {
         this.minecraft.getProfiler().push("translucent_sort");
         double â˜ƒ = â˜ƒ - this.xTransparentOld;
         double â˜ƒx = â˜ƒ - this.yTransparentOld;
         double â˜ƒxx = â˜ƒ - this.zTransparentOld;
         if (â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx > 1.0) {
            this.xTransparentOld = â˜ƒ;
            this.yTransparentOld = â˜ƒ;
            this.zTransparentOld = â˜ƒ;
            int â˜ƒxxx = 0;

            for(LevelRenderer.RenderChunkInfo â˜ƒxxxx : this.renderChunks) {
               if (â˜ƒxxx < 15 && â˜ƒxxxx.chunk.resortTransparency(â˜ƒ, this.chunkRenderDispatcher)) {
                  ++â˜ƒxxx;
               }
            }
         }

         this.minecraft.getProfiler().pop();
      }

      this.minecraft.getProfiler().push("filterempty");
      this.minecraft.getProfiler().popPush((Supplier<String>)(() -> "render_" + â˜ƒ));
      boolean â˜ƒ = â˜ƒ != RenderType.translucent();
      ObjectListIterator<LevelRenderer.RenderChunkInfo> â˜ƒx = this.renderChunks.listIterator(â˜ƒ ? 0 : this.renderChunks.size());
      VertexFormat â˜ƒxx = â˜ƒ.format();
      ShaderInstance â˜ƒxxx = RenderSystem.getShader();
      BufferUploader.reset();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 12; ++â˜ƒxxxx) {
         int â˜ƒxxxxx = RenderSystem.getShaderTexture(â˜ƒxxxx);
         â˜ƒxxx.setSampler("Sampler" + â˜ƒxxxx, â˜ƒxxxxx);
      }

      if (â˜ƒxxx.MODEL_VIEW_MATRIX != null) {
         â˜ƒxxx.MODEL_VIEW_MATRIX.set(â˜ƒ.last().pose());
      }

      if (â˜ƒxxx.PROJECTION_MATRIX != null) {
         â˜ƒxxx.PROJECTION_MATRIX.set(â˜ƒ);
      }

      if (â˜ƒxxx.COLOR_MODULATOR != null) {
         â˜ƒxxx.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
      }

      if (â˜ƒxxx.FOG_START != null) {
         â˜ƒxxx.FOG_START.set(RenderSystem.getShaderFogStart());
      }

      if (â˜ƒxxx.FOG_END != null) {
         â˜ƒxxx.FOG_END.set(RenderSystem.getShaderFogEnd());
      }

      if (â˜ƒxxx.FOG_COLOR != null) {
         â˜ƒxxx.FOG_COLOR.set(RenderSystem.getShaderFogColor());
      }

      if (â˜ƒxxx.TEXTURE_MATRIX != null) {
         â˜ƒxxx.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
      }

      if (â˜ƒxxx.GAME_TIME != null) {
         â˜ƒxxx.GAME_TIME.set(RenderSystem.getShaderGameTime());
      }

      RenderSystem.setupShaderLights(â˜ƒxxx);
      â˜ƒxxx.apply();
      Uniform â˜ƒxxxx = â˜ƒxxx.CHUNK_OFFSET;
      boolean â˜ƒxxxxx = false;

      while(â˜ƒ ? â˜ƒx.hasNext() : â˜ƒx.hasPrevious()) {
         LevelRenderer.RenderChunkInfo â˜ƒxxxxxx = â˜ƒ ? (LevelRenderer.RenderChunkInfo)â˜ƒx.next() : â˜ƒx.previous();
         ChunkRenderDispatcher.RenderChunk â˜ƒxxxxxxx = â˜ƒxxxxxx.chunk;
         if (!â˜ƒxxxxxxx.getCompiledChunk().isEmpty(â˜ƒ)) {
            VertexBuffer â˜ƒxxxxxxxx = â˜ƒxxxxxxx.getBuffer(â˜ƒ);
            BlockPos â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.getOrigin();
            if (â˜ƒxxxx != null) {
               â˜ƒxxxx.set((float)((double)â˜ƒxxxxxxxxx.getX() - â˜ƒ), (float)((double)â˜ƒxxxxxxxxx.getY() - â˜ƒ), (float)((double)â˜ƒxxxxxxxxx.getZ() - â˜ƒ));
               â˜ƒxxxx.upload();
            }

            â˜ƒxxxxxxxx.drawChunkLayer();
            â˜ƒxxxxx = true;
         }
      }

      if (â˜ƒxxxx != null) {
         â˜ƒxxxx.set(Vector3f.ZERO);
      }

      â˜ƒxxx.clear();
      if (â˜ƒxxxxx) {
         â˜ƒxx.clearBufferState();
      }

      VertexBuffer.unbind();
      VertexBuffer.unbindVertexArray();
      this.minecraft.getProfiler().pop();
      â˜ƒ.clearRenderState();
   }

   private void renderDebug(Camera var1) {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      if (this.minecraft.chunkPath || this.minecraft.chunkVisibility) {
         double â˜ƒxx = â˜ƒ.getPosition().x();
         double â˜ƒxxx = â˜ƒ.getPosition().y();
         double â˜ƒxxxx = â˜ƒ.getPosition().z();
         RenderSystem.depthMask(true);
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableTexture();

         for(LevelRenderer.RenderChunkInfo â˜ƒxxxxx : this.renderChunks) {
            ChunkRenderDispatcher.RenderChunk â˜ƒxxxxxx = â˜ƒxxxxx.chunk;
            BlockPos â˜ƒxxxxxxx = â˜ƒxxxxxx.getOrigin();
            PoseStack â˜ƒxxxxxxxx = RenderSystem.getModelViewStack();
            â˜ƒxxxxxxxx.pushPose();
            â˜ƒxxxxxxxx.translate((double)â˜ƒxxxxxxx.getX() - â˜ƒxx, (double)â˜ƒxxxxxxx.getY() - â˜ƒxxx, (double)â˜ƒxxxxxxx.getZ() - â˜ƒxxxx);
            RenderSystem.applyModelViewMatrix();
            if (this.minecraft.chunkPath) {
               â˜ƒx.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
               RenderSystem.lineWidth(10.0F);
               int â˜ƒxxxxxxxxx = â˜ƒxxxxx.step == 0 ? 0 : Mth.hsvToRgb((float)â˜ƒxxxxx.step / 50.0F, 0.9F, 0.9F);
               int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx >> 16 & 0xFF;
               int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx >> 8 & 0xFF;
               int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxx & 0xFF;

               for(int â˜ƒxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxx < DIRECTIONS.length; ++â˜ƒxxxxxxxxxxxxx) {
                  if (â˜ƒxxxxx.hasSourceDirection(â˜ƒxxxxxxxxxxxxx)) {
                     Direction â˜ƒxxxxxxxxxxxxxx = DIRECTIONS[â˜ƒxxxxxxxxxxxxx];
                     â˜ƒx.vertex(8.0, 8.0, 8.0).color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 255).endVertex();
                     â˜ƒx.vertex(
                           (double)(8 - 16 * â˜ƒxxxxxxxxxxxxxx.getStepX()),
                           (double)(8 - 16 * â˜ƒxxxxxxxxxxxxxx.getStepY()),
                           (double)(8 - 16 * â˜ƒxxxxxxxxxxxxxx.getStepZ())
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 255)
                        .endVertex();
                  }
               }

               â˜ƒ.end();
               RenderSystem.lineWidth(1.0F);
            }

            if (this.minecraft.chunkVisibility && !â˜ƒxxxxxx.getCompiledChunk().hasNoRenderableLayers()) {
               â˜ƒx.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);
               RenderSystem.lineWidth(10.0F);
               int â˜ƒxxxxxx = 0;

               for(Direction â˜ƒxxxxxxx : DIRECTIONS) {
                  for(Direction â˜ƒxxxxxxxx : DIRECTIONS) {
                     boolean â˜ƒxxxxxxxxx = â˜ƒxxxxxx.getCompiledChunk().facesCanSeeEachother(â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
                     if (!â˜ƒxxxxxxxxx) {
                        ++â˜ƒxxxxxx;
                        â˜ƒx.vertex((double)(8 + 8 * â˜ƒxxxxxxx.getStepX()), (double)(8 + 8 * â˜ƒxxxxxxx.getStepY()), (double)(8 + 8 * â˜ƒxxxxxxx.getStepZ()))
                           .color(1, 0, 0, 1)
                           .endVertex();
                        â˜ƒx.vertex(
                              (double)(8 + 8 * â˜ƒxxxxxxxx.getStepX()), (double)(8 + 8 * â˜ƒxxxxxxxx.getStepY()), (double)(8 + 8 * â˜ƒxxxxxxxx.getStepZ())
                           )
                           .color(1, 0, 0, 1)
                           .endVertex();
                     }
                  }
               }

               â˜ƒ.end();
               RenderSystem.lineWidth(1.0F);
               if (â˜ƒxxxxxx > 0) {
                  â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                  float â˜ƒxxxxxxx = 0.5F;
                  float â˜ƒxxxxxxxx = 0.2F;
                  â˜ƒx.vertex(0.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 0.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 15.5, 0.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 15.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(15.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒx.vertex(0.5, 0.5, 15.5).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
                  â˜ƒ.end();
               }
            }

            â˜ƒxxxxxxxx.popPose();
            RenderSystem.applyModelViewMatrix();
         }

         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
      }

      if (this.capturedFrustum != null) {
         RenderSystem.disableCull();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(10.0F);
         PoseStack â˜ƒ = RenderSystem.getModelViewStack();
         â˜ƒ.pushPose();
         â˜ƒ.translate(
            (double)((float)(this.frustumPos.x - â˜ƒ.getPosition().x)),
            (double)((float)(this.frustumPos.y - â˜ƒ.getPosition().y)),
            (double)((float)(this.frustumPos.z - â˜ƒ.getPosition().z))
         );
         RenderSystem.applyModelViewMatrix();
         RenderSystem.depthMask(true);
         â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         this.addFrustumQuad(â˜ƒx, 0, 1, 2, 3, 0, 1, 1);
         this.addFrustumQuad(â˜ƒx, 4, 5, 6, 7, 1, 0, 0);
         this.addFrustumQuad(â˜ƒx, 0, 1, 5, 4, 1, 1, 0);
         this.addFrustumQuad(â˜ƒx, 2, 3, 7, 6, 0, 0, 1);
         this.addFrustumQuad(â˜ƒx, 0, 4, 7, 3, 0, 1, 0);
         this.addFrustumQuad(â˜ƒx, 1, 5, 6, 2, 1, 0, 1);
         â˜ƒ.end();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(GameRenderer::getPositionShader);
         â˜ƒx.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         this.addFrustumVertex(â˜ƒx, 0);
         this.addFrustumVertex(â˜ƒx, 1);
         this.addFrustumVertex(â˜ƒx, 1);
         this.addFrustumVertex(â˜ƒx, 2);
         this.addFrustumVertex(â˜ƒx, 2);
         this.addFrustumVertex(â˜ƒx, 3);
         this.addFrustumVertex(â˜ƒx, 3);
         this.addFrustumVertex(â˜ƒx, 0);
         this.addFrustumVertex(â˜ƒx, 4);
         this.addFrustumVertex(â˜ƒx, 5);
         this.addFrustumVertex(â˜ƒx, 5);
         this.addFrustumVertex(â˜ƒx, 6);
         this.addFrustumVertex(â˜ƒx, 6);
         this.addFrustumVertex(â˜ƒx, 7);
         this.addFrustumVertex(â˜ƒx, 7);
         this.addFrustumVertex(â˜ƒx, 4);
         this.addFrustumVertex(â˜ƒx, 0);
         this.addFrustumVertex(â˜ƒx, 4);
         this.addFrustumVertex(â˜ƒx, 1);
         this.addFrustumVertex(â˜ƒx, 5);
         this.addFrustumVertex(â˜ƒx, 2);
         this.addFrustumVertex(â˜ƒx, 6);
         this.addFrustumVertex(â˜ƒx, 3);
         this.addFrustumVertex(â˜ƒx, 7);
         â˜ƒ.end();
         â˜ƒ.popPose();
         RenderSystem.applyModelViewMatrix();
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
         RenderSystem.lineWidth(1.0F);
      }
   }

   private void addFrustumVertex(VertexConsumer var1, int var2) {
      â˜ƒ.vertex((double)this.frustumPoints[â˜ƒ].x(), (double)this.frustumPoints[â˜ƒ].y(), (double)this.frustumPoints[â˜ƒ].z()).endVertex();
   }

   private void addFrustumQuad(VertexConsumer var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      float â˜ƒ = 0.25F;
      â˜ƒ.vertex((double)this.frustumPoints[â˜ƒ].x(), (double)this.frustumPoints[â˜ƒ].y(), (double)this.frustumPoints[â˜ƒ].z())
         .color((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.25F)
         .endVertex();
      â˜ƒ.vertex((double)this.frustumPoints[â˜ƒ].x(), (double)this.frustumPoints[â˜ƒ].y(), (double)this.frustumPoints[â˜ƒ].z())
         .color((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.25F)
         .endVertex();
      â˜ƒ.vertex((double)this.frustumPoints[â˜ƒ].x(), (double)this.frustumPoints[â˜ƒ].y(), (double)this.frustumPoints[â˜ƒ].z())
         .color((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.25F)
         .endVertex();
      â˜ƒ.vertex((double)this.frustumPoints[â˜ƒ].x(), (double)this.frustumPoints[â˜ƒ].y(), (double)this.frustumPoints[â˜ƒ].z())
         .color((float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, 0.25F)
         .endVertex();
   }

   public void captureFrustum() {
      this.captureFrustum = true;
   }

   public void killFrustum() {
      this.capturedFrustum = null;
   }

   public void tick() {
      ++this.ticks;
      if (this.ticks % 20 == 0) {
         Iterator<BlockDestructionProgress> â˜ƒ = this.destroyingBlocks.values().iterator();

         while(â˜ƒ.hasNext()) {
            BlockDestructionProgress â˜ƒx = (BlockDestructionProgress)â˜ƒ.next();
            int â˜ƒxx = â˜ƒx.getUpdatedRenderTick();
            if (this.ticks - â˜ƒxx > 400) {
               â˜ƒ.remove();
               this.removeProgress(â˜ƒx);
            }
         }
      }
   }

   private void removeProgress(BlockDestructionProgress var1) {
      long â˜ƒ = â˜ƒ.getPos().asLong();
      Set<BlockDestructionProgress> â˜ƒx = (Set)this.destructionProgress.get(â˜ƒ);
      â˜ƒx.remove(â˜ƒ);
      if (â˜ƒx.isEmpty()) {
         this.destructionProgress.remove(â˜ƒ);
      }
   }

   private void renderEndSky(PoseStack var1) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();

      for(int â˜ƒxx = 0; â˜ƒxx < 6; ++â˜ƒxx) {
         â˜ƒ.pushPose();
         if (â˜ƒxx == 1) {
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
         }

         if (â˜ƒxx == 2) {
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
         }

         if (â˜ƒxx == 3) {
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(180.0F));
         }

         if (â˜ƒxx == 4) {
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
         }

         if (â˜ƒxx == 5) {
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
         }

         Matrix4f â˜ƒxxx = â˜ƒ.last().pose();
         â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
         â˜ƒx.vertex(â˜ƒxxx, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
         â˜ƒx.vertex(â˜ƒxxx, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
         â˜ƒx.vertex(â˜ƒxxx, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
         â˜ƒx.vertex(â˜ƒxxx, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
         â˜ƒ.end();
         â˜ƒ.popPose();
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
   }

   public void renderSky(PoseStack var1, Matrix4f var2, float var3, Runnable var4) {
      â˜ƒ.run();
      if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.END) {
         this.renderEndSky(â˜ƒ);
      } else if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL) {
         RenderSystem.disableTexture();
         Vec3 â˜ƒ = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), â˜ƒ);
         float â˜ƒx = (float)â˜ƒ.x;
         float â˜ƒxx = (float)â˜ƒ.y;
         float â˜ƒxxx = (float)â˜ƒ.z;
         FogRenderer.levelFogColor();
         BufferBuilder â˜ƒxxxx = Tesselator.getInstance().getBuilder();
         RenderSystem.depthMask(false);
         RenderSystem.setShaderColor(â˜ƒx, â˜ƒxx, â˜ƒxxx, 1.0F);
         ShaderInstance â˜ƒxxxxx = RenderSystem.getShader();
         this.skyBuffer.drawWithShader(â˜ƒ.last().pose(), â˜ƒ, â˜ƒxxxxx);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         float[] â˜ƒxxxxxx = this.level.effects().getSunriseColor(this.level.getTimeOfDay(â˜ƒ), â˜ƒ);
         if (â˜ƒxxxxxx != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            â˜ƒ.pushPose();
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            float â˜ƒxxxxxxx = Mth.sin(this.level.getSunAngle(â˜ƒ)) < 0.0F ? 180.0F : 0.0F;
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒxxxxxxx));
            â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            float â˜ƒxxxxxxxx = â˜ƒxxxxxx[0];
            float â˜ƒxxxxxxxxx = â˜ƒxxxxxx[1];
            float â˜ƒxxxxxxxxxx = â˜ƒxxxxxx[2];
            Matrix4f â˜ƒxxxxxxxxxxx = â˜ƒ.last().pose();
            â˜ƒxxxx.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            â˜ƒxxxx.vertex(â˜ƒxxxxxxxxxxx, 0.0F, 100.0F, 0.0F).color(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxx[3]).endVertex();
            int â˜ƒxxxxxxxxxxxx = 16;

            for(int â˜ƒxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxx <= 16; ++â˜ƒxxxxxxxxxxxxx) {
               float â˜ƒxxxxxxxxxxxxxx = (float)â˜ƒxxxxxxxxxxxxx * (float) (Math.PI * 2) / 16.0F;
               float â˜ƒxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxxxx);
               float â˜ƒxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxxxx);
               â˜ƒxxxx.vertex(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx * 120.0F, â˜ƒxxxxxxxxxxxxxxxx * 120.0F, -â˜ƒxxxxxxxxxxxxxxxx * 40.0F * â˜ƒxxxxxx[3])
                  .color(â˜ƒxxxxxx[0], â˜ƒxxxxxx[1], â˜ƒxxxxxx[2], 0.0F)
                  .endVertex();
            }

            â˜ƒxxxx.end();
            BufferUploader.end(â˜ƒxxxx);
            â˜ƒ.popPose();
         }

         RenderSystem.enableTexture();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
         );
         â˜ƒ.pushPose();
         float â˜ƒ = 1.0F - this.level.getRainLevel(â˜ƒ);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, â˜ƒ);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(this.level.getTimeOfDay(â˜ƒ) * 360.0F));
         Matrix4f â˜ƒx = â˜ƒ.last().pose();
         float â˜ƒxx = 30.0F;
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, SUN_LOCATION);
         â˜ƒxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
         â˜ƒxxxx.vertex(â˜ƒx, -â˜ƒxx, 100.0F, -â˜ƒxx).uv(0.0F, 0.0F).endVertex();
         â˜ƒxxxx.vertex(â˜ƒx, â˜ƒxx, 100.0F, -â˜ƒxx).uv(1.0F, 0.0F).endVertex();
         â˜ƒxxxx.vertex(â˜ƒx, â˜ƒxx, 100.0F, â˜ƒxx).uv(1.0F, 1.0F).endVertex();
         â˜ƒxxxx.vertex(â˜ƒx, -â˜ƒxx, 100.0F, â˜ƒxx).uv(0.0F, 1.0F).endVertex();
         â˜ƒxxxx.end();
         BufferUploader.end(â˜ƒxxxx);
         â˜ƒxx = 20.0F;
         RenderSystem.setShaderTexture(0, MOON_LOCATION);
         int â˜ƒxxx = this.level.getMoonPhase();
         int â˜ƒxxxx = â˜ƒxxx % 4;
         int â˜ƒxxxxx = â˜ƒxxx / 4 % 2;
         float â˜ƒxxxxxx = (float)(â˜ƒxxxx + 0) / 4.0F;
         float â˜ƒxxxxxxx = (float)(â˜ƒxxxxx + 0) / 2.0F;
         float â˜ƒxxxxxxxx = (float)(â˜ƒxxxx + 1) / 4.0F;
         float â˜ƒxxxxxxxxx = (float)(â˜ƒxxxxx + 1) / 2.0F;
         â˜ƒxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
         â˜ƒxxxx.vertex(â˜ƒx, -â˜ƒxx, -100.0F, â˜ƒxx).uv(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx).endVertex();
         â˜ƒxxxx.vertex(â˜ƒx, â˜ƒxx, -100.0F, â˜ƒxx).uv(â˜ƒxxxxxx, â˜ƒxxxxxxxxx).endVertex();
         â˜ƒxxxx.vertex(â˜ƒx, â˜ƒxx, -100.0F, -â˜ƒxx).uv(â˜ƒxxxxxx, â˜ƒxxxxxxx).endVertex();
         â˜ƒxxxx.vertex(â˜ƒx, -â˜ƒxx, -100.0F, -â˜ƒxx).uv(â˜ƒxxxxxxxx, â˜ƒxxxxxxx).endVertex();
         â˜ƒxxxx.end();
         BufferUploader.end(â˜ƒxxxx);
         RenderSystem.disableTexture();
         float â˜ƒxxxxxxxxxx = this.level.getStarBrightness(â˜ƒ) * â˜ƒ;
         if (â˜ƒxxxxxxxxxx > 0.0F) {
            RenderSystem.setShaderColor(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxx);
            FogRenderer.setupNoFog();
            this.starBuffer.drawWithShader(â˜ƒ.last().pose(), â˜ƒ, GameRenderer.getPositionShader());
            â˜ƒ.run();
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         â˜ƒ.popPose();
         RenderSystem.disableTexture();
         RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
         double â˜ƒ = this.minecraft.player.getEyePosition(â˜ƒ).y - this.level.getLevelData().getHorizonHeight(this.level);
         if (â˜ƒ < 0.0) {
            â˜ƒ.pushPose();
            â˜ƒ.translate(0.0, 12.0, 0.0);
            this.darkBuffer.drawWithShader(â˜ƒ.last().pose(), â˜ƒ, â˜ƒxxxxx);
            â˜ƒ.popPose();
         }

         if (this.level.effects().hasGround()) {
            RenderSystem.setShaderColor(â˜ƒx * 0.2F + 0.04F, â˜ƒxx * 0.2F + 0.04F, â˜ƒxxx * 0.6F + 0.1F, 1.0F);
         } else {
            RenderSystem.setShaderColor(â˜ƒx, â˜ƒxx, â˜ƒxxx, 1.0F);
         }

         RenderSystem.enableTexture();
         RenderSystem.depthMask(true);
      }
   }

   public void renderClouds(PoseStack var1, Matrix4f var2, float var3, double var4, double var6, double var8) {
      float â˜ƒ = this.level.effects().getCloudHeight();
      if (!Float.isNaN(â˜ƒ)) {
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
         );
         RenderSystem.depthMask(true);
         float â˜ƒx = 12.0F;
         float â˜ƒxx = 4.0F;
         double â˜ƒxxx = 2.0E-4;
         double â˜ƒxxxx = (double)(((float)this.ticks + â˜ƒ) * 0.03F);
         double â˜ƒxxxxx = (â˜ƒ + â˜ƒxxxx) / 12.0;
         double â˜ƒxxxxxx = (double)(â˜ƒ - (float)â˜ƒ + 0.33F);
         double â˜ƒxxxxxxx = â˜ƒ / 12.0 + 0.33F;
         â˜ƒxxxxx -= (double)(Mth.floor(â˜ƒxxxxx / 2048.0) * 2048);
         â˜ƒxxxxxxx -= (double)(Mth.floor(â˜ƒxxxxxxx / 2048.0) * 2048);
         float â˜ƒxxxxxxxx = (float)(â˜ƒxxxxx - (double)Mth.floor(â˜ƒxxxxx));
         float â˜ƒxxxxxxxxx = (float)(â˜ƒxxxxxx / 4.0 - (double)Mth.floor(â˜ƒxxxxxx / 4.0)) * 4.0F;
         float â˜ƒxxxxxxxxxx = (float)(â˜ƒxxxxxxx - (double)Mth.floor(â˜ƒxxxxxxx));
         Vec3 â˜ƒxxxxxxxxxxx = this.level.getCloudColor(â˜ƒ);
         int â˜ƒxxxxxxxxxxxx = (int)Math.floor(â˜ƒxxxxx);
         int â˜ƒxxxxxxxxxxxxx = (int)Math.floor(â˜ƒxxxxxx / 4.0);
         int â˜ƒxxxxxxxxxxxxxx = (int)Math.floor(â˜ƒxxxxxxx);
         if (â˜ƒxxxxxxxxxxxx != this.prevCloudX
            || â˜ƒxxxxxxxxxxxxx != this.prevCloudY
            || â˜ƒxxxxxxxxxxxxxx != this.prevCloudZ
            || this.minecraft.options.getCloudsType() != this.prevCloudsType
            || this.prevCloudColor.distanceToSqr(â˜ƒxxxxxxxxxxx) > 2.0E-4) {
            this.prevCloudX = â˜ƒxxxxxxxxxxxx;
            this.prevCloudY = â˜ƒxxxxxxxxxxxxx;
            this.prevCloudZ = â˜ƒxxxxxxxxxxxxxx;
            this.prevCloudColor = â˜ƒxxxxxxxxxxx;
            this.prevCloudsType = this.minecraft.options.getCloudsType();
            this.generateClouds = true;
         }

         if (this.generateClouds) {
            this.generateClouds = false;
            BufferBuilder â˜ƒx = Tesselator.getInstance().getBuilder();
            if (this.cloudBuffer != null) {
               this.cloudBuffer.close();
            }

            this.cloudBuffer = new VertexBuffer();
            this.buildClouds(â˜ƒx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx);
            â˜ƒx.end();
            this.cloudBuffer.upload(â˜ƒx);
         }

         RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
         RenderSystem.setShaderTexture(0, CLOUDS_LOCATION);
         FogRenderer.levelFogColor();
         â˜ƒ.pushPose();
         â˜ƒ.scale(12.0F, 1.0F, 12.0F);
         â˜ƒ.translate((double)(-â˜ƒxxxxxxxx), (double)â˜ƒxxxxxxxxx, (double)(-â˜ƒxxxxxxxxxx));
         if (this.cloudBuffer != null) {
            int â˜ƒx = this.prevCloudsType == CloudStatus.FANCY ? 0 : 1;

            for(int â˜ƒxx = â˜ƒx; â˜ƒxx < 2; ++â˜ƒxx) {
               if (â˜ƒxx == 0) {
                  RenderSystem.colorMask(false, false, false, false);
               } else {
                  RenderSystem.colorMask(true, true, true, true);
               }

               ShaderInstance â˜ƒxxx = RenderSystem.getShader();
               this.cloudBuffer.drawWithShader(â˜ƒ.last().pose(), â˜ƒ, â˜ƒxxx);
            }
         }

         â˜ƒ.popPose();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
      }
   }

   private void buildClouds(BufferBuilder var1, double var2, double var4, double var6, Vec3 var8) {
      float â˜ƒ = 4.0F;
      float â˜ƒx = 0.00390625F;
      int â˜ƒxx = 8;
      int â˜ƒxxx = 4;
      float â˜ƒxxxx = 9.765625E-4F;
      float â˜ƒxxxxx = (float)Mth.floor(â˜ƒ) * 0.00390625F;
      float â˜ƒxxxxxx = (float)Mth.floor(â˜ƒ) * 0.00390625F;
      float â˜ƒxxxxxxx = (float)â˜ƒ.x;
      float â˜ƒxxxxxxxx = (float)â˜ƒ.y;
      float â˜ƒxxxxxxxxx = (float)â˜ƒ.z;
      float â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx * 0.9F;
      float â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx * 0.9F;
      float â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxx * 0.9F;
      float â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxx * 0.7F;
      float â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxx * 0.7F;
      float â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx * 0.7F;
      float â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx * 0.8F;
      float â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx * 0.8F;
      float â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxx * 0.8F;
      RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
      â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
      float â˜ƒxxxxxxxxxxxxxxxxxxx = (float)Math.floor(â˜ƒ / 4.0) * 4.0F;
      if (this.prevCloudsType == CloudStatus.FANCY) {
         for(int â˜ƒxxxxxxxxxxxxxxxxxxxx = -3; â˜ƒxxxxxxxxxxxxxxxxxxxx <= 4; ++â˜ƒxxxxxxxxxxxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxxxxxxxxxxxx = -3; â˜ƒxxxxxxxxxxxxxxxxxxxxx <= 4; ++â˜ƒxxxxxxxxxxxxxxxxxxxxx) {
               float â˜ƒxxxxxxxxxxxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxxxxxxxxxxxxx * 8);
               float â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = (float)(â˜ƒxxxxxxxxxxxxxxxxxxxxx * 8);
               if (â˜ƒxxxxxxxxxxxxxxxxxxx > -5.0F) {
                  â˜ƒ.vertex((double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
                  â˜ƒ.vertex((double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
                  â˜ƒ.vertex((double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
                  â˜ƒ.vertex((double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
               }

               if (â˜ƒxxxxxxxxxxxxxxxxxxx <= 5.0F) {
                  â˜ƒ.vertex(
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                     )
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
                  â˜ƒ.vertex(
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                     )
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
                  â˜ƒ.vertex(
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                     )
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
                  â˜ƒ.vertex(
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F),
                        (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                     )
                     .uv((â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx, (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx)
                     .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
               }

               if (â˜ƒxxxxxxxxxxxxxxxxxxxx > -1) {
                  for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxx < 8; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                  }
               }

               if (â˜ƒxxxxxxxxxxxxxxxxxxxx <= 1) {
                  for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxx < 8; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                  }
               }

               if (â˜ƒxxxxxxxxxxxxxxxxxxxxx > -1) {
                  for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxx < 8; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                  }
               }

               if (â˜ƒxxxxxxxxxxxxxxxxxxxxx <= 1) {
                  for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxx < 8; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxx) {
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                     â˜ƒ.vertex(
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .uv(
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + â˜ƒxxxxx,
                           (â˜ƒxxxxxxxxxxxxxxxxxxxxxxx + (float)â˜ƒxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + â˜ƒxxxxxx
                        )
                        .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                  }
               }
            }
         }
      } else {
         int â˜ƒ = 1;
         int â˜ƒx = 32;

         for(int â˜ƒxx = -32; â˜ƒxx < 32; â˜ƒxx += 32) {
            for(int â˜ƒxxx = -32; â˜ƒxxx < 32; â˜ƒxxx += 32) {
               â˜ƒ.vertex((double)(â˜ƒxx + 0), (double)â˜ƒxxxxxxxxxxxxxxxxxxx, (double)(â˜ƒxxx + 32))
                  .uv((float)(â˜ƒxx + 0) * 0.00390625F + â˜ƒxxxxx, (float)(â˜ƒxxx + 32) * 0.00390625F + â˜ƒxxxxxx)
                  .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .endVertex();
               â˜ƒ.vertex((double)(â˜ƒxx + 32), (double)â˜ƒxxxxxxxxxxxxxxxxxxx, (double)(â˜ƒxxx + 32))
                  .uv((float)(â˜ƒxx + 32) * 0.00390625F + â˜ƒxxxxx, (float)(â˜ƒxxx + 32) * 0.00390625F + â˜ƒxxxxxx)
                  .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .endVertex();
               â˜ƒ.vertex((double)(â˜ƒxx + 32), (double)â˜ƒxxxxxxxxxxxxxxxxxxx, (double)(â˜ƒxxx + 0))
                  .uv((float)(â˜ƒxx + 32) * 0.00390625F + â˜ƒxxxxx, (float)(â˜ƒxxx + 0) * 0.00390625F + â˜ƒxxxxxx)
                  .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .endVertex();
               â˜ƒ.vertex((double)(â˜ƒxx + 0), (double)â˜ƒxxxxxxxxxxxxxxxxxxx, (double)(â˜ƒxxx + 0))
                  .uv((float)(â˜ƒxx + 0) * 0.00390625F + â˜ƒxxxxx, (float)(â˜ƒxxx + 0) * 0.00390625F + â˜ƒxxxxxx)
                  .color(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.8F)
                  .normal(0.0F, -1.0F, 0.0F)
                  .endVertex();
            }
         }
      }
   }

   private void compileChunksUntil(long var1) {
      this.needsUpdate |= this.chunkRenderDispatcher.uploadAllPendingUploads();
      long â˜ƒ = Util.getNanos();
      int â˜ƒx = 0;
      if (!this.chunksToCompile.isEmpty()) {
         Iterator<ChunkRenderDispatcher.RenderChunk> â˜ƒxx = this.chunksToCompile.iterator();

         while(â˜ƒxx.hasNext()) {
            ChunkRenderDispatcher.RenderChunk â˜ƒxxx = (ChunkRenderDispatcher.RenderChunk)â˜ƒxx.next();
            if (â˜ƒxxx.isDirtyFromPlayer()) {
               this.chunkRenderDispatcher.rebuildChunkSync(â˜ƒxxx);
            } else {
               â˜ƒxxx.rebuildChunkAsync(this.chunkRenderDispatcher);
            }

            â˜ƒxxx.setNotDirty();
            â˜ƒxx.remove();
            ++â˜ƒx;
            long â˜ƒxxx = Util.getNanos();
            long â˜ƒxxxx = â˜ƒxxx - â˜ƒ;
            long â˜ƒxxxxx = â˜ƒxxxx / (long)â˜ƒx;
            long â˜ƒxxxxxx = â˜ƒ - â˜ƒxxx;
            if (â˜ƒxxxxxx < â˜ƒxxxxx) {
               break;
            }
         }
      }
   }

   private void renderWorldBorder(Camera var1) {
      BufferBuilder â˜ƒ = Tesselator.getInstance().getBuilder();
      WorldBorder â˜ƒx = this.level.getWorldBorder();
      double â˜ƒxx = (double)(this.minecraft.options.renderDistance * 16);
      if (!(â˜ƒ.getPosition().x < â˜ƒx.getMaxX() - â˜ƒxx)
         || !(â˜ƒ.getPosition().x > â˜ƒx.getMinX() + â˜ƒxx)
         || !(â˜ƒ.getPosition().z < â˜ƒx.getMaxZ() - â˜ƒxx)
         || !(â˜ƒ.getPosition().z > â˜ƒx.getMinZ() + â˜ƒxx)) {
         double â˜ƒxxx = 1.0 - â˜ƒx.getDistanceToBorder(â˜ƒ.getPosition().x, â˜ƒ.getPosition().z) / â˜ƒxx;
         â˜ƒxxx = Math.pow(â˜ƒxxx, 4.0);
         â˜ƒxxx = Mth.clamp(â˜ƒxxx, 0.0, 1.0);
         double â˜ƒxxxx = â˜ƒ.getPosition().x;
         double â˜ƒxxxxx = â˜ƒ.getPosition().z;
         double â˜ƒxxxxxx = (double)this.minecraft.gameRenderer.getDepthFar();
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(
            GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
         );
         RenderSystem.setShaderTexture(0, FORCEFIELD_LOCATION);
         RenderSystem.depthMask(Minecraft.useShaderTransparency());
         PoseStack â˜ƒxxxxxxx = RenderSystem.getModelViewStack();
         â˜ƒxxxxxxx.pushPose();
         RenderSystem.applyModelViewMatrix();
         int â˜ƒxxxxxxxx = â˜ƒx.getStatus().getColor();
         float â˜ƒxxxxxxxxx = (float)(â˜ƒxxxxxxxx >> 16 & 0xFF) / 255.0F;
         float â˜ƒxxxxxxxxxx = (float)(â˜ƒxxxxxxxx >> 8 & 0xFF) / 255.0F;
         float â˜ƒxxxxxxxxxxx = (float)(â˜ƒxxxxxxxx & 0xFF) / 255.0F;
         RenderSystem.setShaderColor(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, (float)â˜ƒxxx);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.polygonOffset(-3.0F, -3.0F);
         RenderSystem.enablePolygonOffset();
         RenderSystem.disableCull();
         float â˜ƒxxxxxxxxxxxx = (float)(Util.getMillis() % 3000L) / 3000.0F;
         float â˜ƒxxxxxxxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxxxxxxxxx = (float)(â˜ƒxxxxxx - Mth.frac(â˜ƒ.getPosition().y));
         â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
         double â˜ƒxxxxxxxxxxxxxxxx = Math.max((double)Mth.floor(â˜ƒxxxxx - â˜ƒxx), â˜ƒx.getMinZ());
         double â˜ƒxxxxxxxxxxxxxxxxx = Math.min((double)Mth.ceil(â˜ƒxxxxx + â˜ƒxx), â˜ƒx.getMaxZ());
         if (â˜ƒxxxx > â˜ƒx.getMaxX() - â˜ƒxx) {
            float â˜ƒxxxxxxxxxxxxxxxxxx = 0.0F;

            for(double â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxxx += 0.5F) {
               double â˜ƒxxxxxxxxxxxxxxxxxxxx = Math.min(1.0, â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxx);
               float â˜ƒxxxxxxxxxxxxxxxxxxxxx = (float)â˜ƒxxxxxxxxxxxxxxxxxxxx * 0.5F;
               â˜ƒ.vertex(â˜ƒx.getMaxX() - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒx.getMaxX() - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx - (â˜ƒxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxx), â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒx.getMaxX() - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx - (â˜ƒxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxx), â˜ƒxxxxxxxxxxxx + 0.0F)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒx.getMaxX() - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx + 0.0F)
                  .endVertex();
               ++â˜ƒxxxxxxxxxxxxxxxxxxx;
            }
         }

         if (â˜ƒxxxx < â˜ƒx.getMinX() + â˜ƒxx) {
            float â˜ƒxxx = 0.0F;

            for(double â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxx; â˜ƒxxxx < â˜ƒxxxxxxxxxxxxxxxxx; â˜ƒxxx += 0.5F) {
               double â˜ƒxxxxx = Math.min(1.0, â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxx);
               float â˜ƒxxxxxx = (float)â˜ƒxxxxx * 0.5F;
               â˜ƒ.vertex(â˜ƒx.getMinX() - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒxxxx - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒx.getMinX() - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒxxxx + â˜ƒxxxxx - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒx.getMinX() - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxx + â˜ƒxxxxx - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + 0.0F)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒx.getMinX() - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxx - â˜ƒxxxxx).uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + 0.0F).endVertex();
               ++â˜ƒxxxx;
            }
         }

         â˜ƒxxxxxxxxxxxxxxxx = Math.max((double)Mth.floor(â˜ƒxxxx - â˜ƒxx), â˜ƒx.getMinX());
         â˜ƒxxxxxxxxxxxxxxxxx = Math.min((double)Mth.ceil(â˜ƒxxxx + â˜ƒxx), â˜ƒx.getMaxX());
         if (â˜ƒxxxxx > â˜ƒx.getMaxZ() - â˜ƒxx) {
            float â˜ƒxxx = 0.0F;

            for(double â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxx; â˜ƒxxxx < â˜ƒxxxxxxxxxxxxxxxxx; â˜ƒxxx += 0.5F) {
               double â˜ƒxxxxx = Math.min(1.0, â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxx);
               float â˜ƒxxxxxx = (float)â˜ƒxxxxx * 0.5F;
               â˜ƒ.vertex(â˜ƒxxxx - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒx.getMaxZ() - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒxxxx + â˜ƒxxxxx - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒx.getMaxZ() - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒxxxx + â˜ƒxxxxx - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒx.getMaxZ() - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + 0.0F)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒxxxx - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒx.getMaxZ() - â˜ƒxxxxx).uv(â˜ƒxxxxxxxxxxxx + â˜ƒxxx, â˜ƒxxxxxxxxxxxx + 0.0F).endVertex();
               ++â˜ƒxxxx;
            }
         }

         if (â˜ƒxxxxx < â˜ƒx.getMinZ() + â˜ƒxx) {
            float â˜ƒxxx = 0.0F;

            for(double â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxx; â˜ƒxxxx < â˜ƒxxxxxxxxxxxxxxxxx; â˜ƒxxx += 0.5F) {
               double â˜ƒxxxxx = Math.min(1.0, â˜ƒxxxxxxxxxxxxxxxxx - â˜ƒxxxx);
               float â˜ƒxxxxxx = (float)â˜ƒxxxxx * 0.5F;
               â˜ƒ.vertex(â˜ƒxxxx - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒx.getMinZ() - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx - â˜ƒxxx, â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒxxxx + â˜ƒxxxxx - â˜ƒxxxx, -â˜ƒxxxxxx, â˜ƒx.getMinZ() - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx - (â˜ƒxxxxxx + â˜ƒxxx), â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxx)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒxxxx + â˜ƒxxxxx - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒx.getMinZ() - â˜ƒxxxxx)
                  .uv(â˜ƒxxxxxxxxxxxx - (â˜ƒxxxxxx + â˜ƒxxx), â˜ƒxxxxxxxxxxxx + 0.0F)
                  .endVertex();
               â˜ƒ.vertex(â˜ƒxxxx - â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒx.getMinZ() - â˜ƒxxxxx).uv(â˜ƒxxxxxxxxxxxx - â˜ƒxxx, â˜ƒxxxxxxxxxxxx + 0.0F).endVertex();
               ++â˜ƒxxxx;
            }
         }

         â˜ƒ.end();
         BufferUploader.end(â˜ƒ);
         RenderSystem.enableCull();
         RenderSystem.polygonOffset(0.0F, 0.0F);
         RenderSystem.disablePolygonOffset();
         RenderSystem.disableBlend();
         â˜ƒxxxxxxx.popPose();
         RenderSystem.applyModelViewMatrix();
         RenderSystem.depthMask(true);
      }
   }

   private void renderHitOutline(PoseStack var1, VertexConsumer var2, Entity var3, double var4, double var6, double var8, BlockPos var10, BlockState var11) {
      renderShape(
         â˜ƒ,
         â˜ƒ,
         â˜ƒ.getShape(this.level, â˜ƒ, CollisionContext.of(â˜ƒ)),
         (double)â˜ƒ.getX() - â˜ƒ,
         (double)â˜ƒ.getY() - â˜ƒ,
         (double)â˜ƒ.getZ() - â˜ƒ,
         0.0F,
         0.0F,
         0.0F,
         0.4F
      );
   }

   public static void renderVoxelShape(
      PoseStack var0, VertexConsumer var1, VoxelShape var2, double var3, double var5, double var7, float var9, float var10, float var11, float var12
   ) {
      List<AABB> â˜ƒ = â˜ƒ.toAabbs();
      int â˜ƒx = Mth.ceil((double)â˜ƒ.size() / 3.0);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
         AABB â˜ƒxxx = (AABB)â˜ƒ.get(â˜ƒxx);
         float â˜ƒxxxx = ((float)â˜ƒxx % (float)â˜ƒx + 1.0F) / (float)â˜ƒx;
         float â˜ƒxxxxx = (float)(â˜ƒxx / â˜ƒx);
         float â˜ƒxxxxxx = â˜ƒxxxx * (float)(â˜ƒxxxxx == 0.0F ? 1 : 0);
         float â˜ƒxxxxxxx = â˜ƒxxxx * (float)(â˜ƒxxxxx == 1.0F ? 1 : 0);
         float â˜ƒxxxxxxxx = â˜ƒxxxx * (float)(â˜ƒxxxxx == 2.0F ? 1 : 0);
         renderShape(â˜ƒ, â˜ƒ, Shapes.create(â˜ƒxxx.move(0.0, 0.0, 0.0)), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 1.0F);
      }
   }

   private static void renderShape(
      PoseStack var0, VertexConsumer var1, VoxelShape var2, double var3, double var5, double var7, float var9, float var10, float var11, float var12
   ) {
      PoseStack.Pose â˜ƒ = â˜ƒ.last();
      â˜ƒ.forAllEdges(
         (var12x, var14, var16, var18, var20, var22) -> {
            float â˜ƒ = (float)(var18 - var12x);
            float â˜ƒx = (float)(var20 - var14);
            float â˜ƒxx = (float)(var22 - var16);
            float â˜ƒxxx = Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx);
            â˜ƒ /= â˜ƒxxx;
            â˜ƒx /= â˜ƒxxx;
            â˜ƒxx /= â˜ƒxxx;
            â˜ƒ.vertex(â˜ƒ.pose(), (float)(var12x + â˜ƒ), (float)(var14 + â˜ƒ), (float)(var16 + â˜ƒ))
               .color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
               .normal(â˜ƒ.normal(), â˜ƒ, â˜ƒx, â˜ƒxx)
               .endVertex();
            â˜ƒ.vertex(â˜ƒ.pose(), (float)(var18 + â˜ƒ), (float)(var20 + â˜ƒ), (float)(var22 + â˜ƒ))
               .color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
               .normal(â˜ƒ.normal(), â˜ƒ, â˜ƒx, â˜ƒxx)
               .endVertex();
         }
      );
   }

   public static void renderLineBox(
      VertexConsumer var0, double var1, double var3, double var5, double var7, double var9, double var11, float var13, float var14, float var15, float var16
   ) {
      renderLineBox(new PoseStack(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void renderLineBox(PoseStack var0, VertexConsumer var1, AABB var2, float var3, float var4, float var5, float var6) {
      renderLineBox(â˜ƒ, â˜ƒ, â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ, â˜ƒ.maxX, â˜ƒ.maxY, â˜ƒ.maxZ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void renderLineBox(
      PoseStack var0,
      VertexConsumer var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      float var14,
      float var15,
      float var16,
      float var17
   ) {
      renderLineBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void renderLineBox(
      PoseStack var0,
      VertexConsumer var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19,
      float var20
   ) {
      Matrix4f â˜ƒ = â˜ƒ.last().pose();
      Matrix3f â˜ƒx = â˜ƒ.last().normal();
      float â˜ƒxx = (float)â˜ƒ;
      float â˜ƒxxx = (float)â˜ƒ;
      float â˜ƒxxxx = (float)â˜ƒ;
      float â˜ƒxxxxx = (float)â˜ƒ;
      float â˜ƒxxxxxx = (float)â˜ƒ;
      float â˜ƒxxxxxxx = (float)â˜ƒ;
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, 1.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, 1.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, -1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, -1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, 1.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, 1.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, -1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, -1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, -1.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, -1.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 1.0F, 0.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 1.0F, 0.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, 1.0F).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).normal(â˜ƒx, 0.0F, 0.0F, 1.0F).endVertex();
   }

   public static void addChainedFilledBoxVertices(
      BufferBuilder var0, double var1, double var3, double var5, double var7, double var9, double var11, float var13, float var14, float var15, float var16
   ) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
   }

   public void blockChanged(BlockGetter var1, BlockPos var2, BlockState var3, BlockState var4, int var5) {
      this.setBlockDirty(â˜ƒ, (â˜ƒ & 8) != 0);
   }

   private void setBlockDirty(BlockPos var1, boolean var2) {
      for(int â˜ƒ = â˜ƒ.getZ() - 1; â˜ƒ <= â˜ƒ.getZ() + 1; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ.getX() - 1; â˜ƒx <= â˜ƒ.getX() + 1; ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ.getY() - 1; â˜ƒxx <= â˜ƒ.getY() + 1; ++â˜ƒxx) {
               this.setSectionDirty(SectionPos.blockToSectionCoord(â˜ƒx), SectionPos.blockToSectionCoord(â˜ƒxx), SectionPos.blockToSectionCoord(â˜ƒ), â˜ƒ);
            }
         }
      }
   }

   public void setBlocksDirty(int var1, int var2, int var3, int var4, int var5, int var6) {
      for(int â˜ƒ = â˜ƒ - 1; â˜ƒ <= â˜ƒ + 1; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ - 1; â˜ƒx <= â˜ƒ + 1; ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ - 1; â˜ƒxx <= â˜ƒ + 1; ++â˜ƒxx) {
               this.setSectionDirty(SectionPos.blockToSectionCoord(â˜ƒx), SectionPos.blockToSectionCoord(â˜ƒxx), SectionPos.blockToSectionCoord(â˜ƒ));
            }
         }
      }
   }

   public void setBlockDirty(BlockPos var1, BlockState var2, BlockState var3) {
      if (this.minecraft.getModelManager().requiresRender(â˜ƒ, â˜ƒ)) {
         this.setBlocksDirty(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      }
   }

   public void setSectionDirtyWithNeighbors(int var1, int var2, int var3) {
      for(int â˜ƒ = â˜ƒ - 1; â˜ƒ <= â˜ƒ + 1; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ - 1; â˜ƒx <= â˜ƒ + 1; ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ - 1; â˜ƒxx <= â˜ƒ + 1; ++â˜ƒxx) {
               this.setSectionDirty(â˜ƒx, â˜ƒxx, â˜ƒ);
            }
         }
      }
   }

   public void setSectionDirty(int var1, int var2, int var3) {
      this.setSectionDirty(â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   private void setSectionDirty(int var1, int var2, int var3, boolean var4) {
      this.viewArea.setDirty(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void playStreamingMusic(@Nullable SoundEvent var1, BlockPos var2) {
      SoundInstance â˜ƒ = (SoundInstance)this.playingRecords.get(â˜ƒ);
      if (â˜ƒ != null) {
         this.minecraft.getSoundManager().stop(â˜ƒ);
         this.playingRecords.remove(â˜ƒ);
      }

      if (â˜ƒ != null) {
         RecordItem â˜ƒ = RecordItem.getBySound(â˜ƒ);
         if (â˜ƒ != null) {
            this.minecraft.gui.setNowPlaying(â˜ƒ.getDisplayName());
         }

         SoundInstance var5 = SimpleSoundInstance.forRecord(â˜ƒ, (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
         this.playingRecords.put(â˜ƒ, var5);
         this.minecraft.getSoundManager().play(var5);
      }

      this.notifyNearbyEntities(this.level, â˜ƒ, â˜ƒ != null);
   }

   private void notifyNearbyEntities(Level var1, BlockPos var2, boolean var3) {
      for(LivingEntity â˜ƒ : â˜ƒ.getEntitiesOfClass(LivingEntity.class, new AABB(â˜ƒ).inflate(3.0))) {
         â˜ƒ.setRecordPlayingNearby(â˜ƒ, â˜ƒ);
      }
   }

   public void addParticle(ParticleOptions var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      this.addParticle(â˜ƒ, â˜ƒ, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void addParticle(ParticleOptions var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14) {
      try {
         this.addParticleInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (Throwable var19) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var19, "Exception while adding particle");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Particle being added");
         â˜ƒx.setDetail("ID", Registry.PARTICLE_TYPE.getKey(â˜ƒ.getType()));
         â˜ƒx.setDetail("Parameters", â˜ƒ.writeToString());
         â˜ƒx.setDetail("Position", (CrashReportDetail<String>)(() -> CrashReportCategory.formatLocation(this.level, â˜ƒ, â˜ƒ, â˜ƒ)));
         throw new ReportedException(â˜ƒ);
      }
   }

   private <T extends ParticleOptions> void addParticle(T var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this.addParticle(â˜ƒ, â˜ƒ.getType().getOverrideLimiter(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   private Particle addParticleInternal(ParticleOptions var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      return this.addParticleInternal(â˜ƒ, â˜ƒ, false, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   private Particle addParticleInternal(
      ParticleOptions var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14
   ) {
      Camera â˜ƒ = this.minecraft.gameRenderer.getMainCamera();
      if (this.minecraft != null && â˜ƒ.isInitialized() && this.minecraft.particleEngine != null) {
         ParticleStatus â˜ƒx = this.calculateParticleLevel(â˜ƒ);
         if (â˜ƒ) {
            return this.minecraft.particleEngine.createParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         } else if (â˜ƒ.getPosition().distanceToSqr(â˜ƒ, â˜ƒ, â˜ƒ) > 1024.0) {
            return null;
         } else {
            return â˜ƒx == ParticleStatus.MINIMAL ? null : this.minecraft.particleEngine.createParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      } else {
         return null;
      }
   }

   private ParticleStatus calculateParticleLevel(boolean var1) {
      ParticleStatus â˜ƒ = this.minecraft.options.particles;
      if (â˜ƒ && â˜ƒ == ParticleStatus.MINIMAL && this.level.random.nextInt(10) == 0) {
         â˜ƒ = ParticleStatus.DECREASED;
      }

      if (â˜ƒ == ParticleStatus.DECREASED && this.level.random.nextInt(3) == 0) {
         â˜ƒ = ParticleStatus.MINIMAL;
      }

      return â˜ƒ;
   }

   public void clear() {
   }

   public void globalLevelEvent(int var1, BlockPos var2, int var3) {
      switch(â˜ƒ) {
         case 1023:
         case 1028:
         case 1038:
            Camera â˜ƒ = this.minecraft.gameRenderer.getMainCamera();
            if (â˜ƒ.isInitialized()) {
               double â˜ƒx = (double)â˜ƒ.getX() - â˜ƒ.getPosition().x;
               double â˜ƒxx = (double)â˜ƒ.getY() - â˜ƒ.getPosition().y;
               double â˜ƒxxx = (double)â˜ƒ.getZ() - â˜ƒ.getPosition().z;
               double â˜ƒxxxx = Math.sqrt(â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx);
               double â˜ƒxxxxx = â˜ƒ.getPosition().x;
               double â˜ƒxxxxxx = â˜ƒ.getPosition().y;
               double â˜ƒxxxxxxx = â˜ƒ.getPosition().z;
               if (â˜ƒxxxx > 0.0) {
                  â˜ƒxxxxx += â˜ƒx / â˜ƒxxxx * 2.0;
                  â˜ƒxxxxxx += â˜ƒxx / â˜ƒxxxx * 2.0;
                  â˜ƒxxxxxxx += â˜ƒxxx / â˜ƒxxxx * 2.0;
               }

               if (â˜ƒ == 1023) {
                  this.level.playLocalSound(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0F, 1.0F, false);
               } else if (â˜ƒ == 1038) {
                  this.level.playLocalSound(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 1.0F, 1.0F, false);
               } else {
                  this.level.playLocalSound(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, SoundEvents.ENDER_DRAGON_DEATH, SoundSource.HOSTILE, 5.0F, 1.0F, false);
               }
            }
      }
   }

   public void levelEvent(Player var1, int var2, BlockPos var3, int var4) {
      Random â˜ƒ = this.level.random;
      switch(â˜ƒ) {
         case 1000:
            this.level.playLocalSound(â˜ƒ, SoundEvents.DISPENSER_DISPENSE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1001:
            this.level.playLocalSound(â˜ƒ, SoundEvents.DISPENSER_FAIL, SoundSource.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1002:
            this.level.playLocalSound(â˜ƒ, SoundEvents.DISPENSER_LAUNCH, SoundSource.BLOCKS, 1.0F, 1.2F, false);
            break;
         case 1003:
            this.level.playLocalSound(â˜ƒ, SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 1.0F, 1.2F, false);
            break;
         case 1004:
            this.level.playLocalSound(â˜ƒ, SoundEvents.FIREWORK_ROCKET_SHOOT, SoundSource.NEUTRAL, 1.0F, 1.2F, false);
            break;
         case 1005:
            this.level.playLocalSound(â˜ƒ, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1006:
            this.level.playLocalSound(â˜ƒ, SoundEvents.WOODEN_DOOR_OPEN, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1007:
            this.level.playLocalSound(â˜ƒ, SoundEvents.WOODEN_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1008:
            this.level.playLocalSound(â˜ƒ, SoundEvents.FENCE_GATE_OPEN, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1009:
            if (â˜ƒ == 0) {
               this.level.playLocalSound(â˜ƒ, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.8F, false);
            } else if (â˜ƒ == 1) {
               this.level
                  .playLocalSound(â˜ƒ, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.7F, 1.6F + (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.4F, false);
            }
            break;
         case 1010:
            if (Item.byId(â˜ƒ) instanceof RecordItem) {
               this.playStreamingMusic(((RecordItem)Item.byId(â˜ƒ)).getSound(), â˜ƒ);
            } else {
               this.playStreamingMusic(null, â˜ƒ);
            }
            break;
         case 1011:
            this.level.playLocalSound(â˜ƒ, SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1012:
            this.level.playLocalSound(â˜ƒ, SoundEvents.WOODEN_DOOR_CLOSE, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1013:
            this.level.playLocalSound(â˜ƒ, SoundEvents.WOODEN_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1014:
            this.level.playLocalSound(â˜ƒ, SoundEvents.FENCE_GATE_CLOSE, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1015:
            this.level.playLocalSound(â˜ƒ, SoundEvents.GHAST_WARN, SoundSource.HOSTILE, 10.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1016:
            this.level.playLocalSound(â˜ƒ, SoundEvents.GHAST_SHOOT, SoundSource.HOSTILE, 10.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1017:
            this.level
               .playLocalSound(â˜ƒ, SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.HOSTILE, 10.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1018:
            this.level.playLocalSound(â˜ƒ, SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1019:
            this.level
               .playLocalSound(â˜ƒ, SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, SoundSource.HOSTILE, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1020:
            this.level
               .playLocalSound(â˜ƒ, SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.HOSTILE, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1021:
            this.level
               .playLocalSound(â˜ƒ, SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundSource.HOSTILE, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1022:
            this.level.playLocalSound(â˜ƒ, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.HOSTILE, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1024:
            this.level.playLocalSound(â˜ƒ, SoundEvents.WITHER_SHOOT, SoundSource.HOSTILE, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1025:
            this.level.playLocalSound(â˜ƒ, SoundEvents.BAT_TAKEOFF, SoundSource.NEUTRAL, 0.05F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1026:
            this.level.playLocalSound(â˜ƒ, SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1027:
            this.level
               .playLocalSound(â˜ƒ, SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.NEUTRAL, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1029:
            this.level.playLocalSound(â˜ƒ, SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1030:
            this.level.playLocalSound(â˜ƒ, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1031:
            this.level.playLocalSound(â˜ƒ, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.3F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1032:
            this.minecraft.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRAVEL, â˜ƒ.nextFloat() * 0.4F + 0.8F, 0.25F));
            break;
         case 1033:
            this.level.playLocalSound(â˜ƒ, SoundEvents.CHORUS_FLOWER_GROW, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1034:
            this.level.playLocalSound(â˜ƒ, SoundEvents.CHORUS_FLOWER_DEATH, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1035:
            this.level.playLocalSound(â˜ƒ, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 1036:
            this.level.playLocalSound(â˜ƒ, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1037:
            this.level.playLocalSound(â˜ƒ, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1039:
            this.level.playLocalSound(â˜ƒ, SoundEvents.PHANTOM_BITE, SoundSource.HOSTILE, 0.3F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1040:
            this.level
               .playLocalSound(
                  â˜ƒ, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false
               );
            break;
         case 1041:
            this.level
               .playLocalSound(â˜ƒ, SoundEvents.HUSK_CONVERTED_TO_ZOMBIE, SoundSource.NEUTRAL, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1042:
            this.level.playLocalSound(â˜ƒ, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1043:
            this.level.playLocalSound(â˜ƒ, SoundEvents.BOOK_PAGE_TURN, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1044:
            this.level.playLocalSound(â˜ƒ, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1045:
            this.level.playLocalSound(â˜ƒ, SoundEvents.POINTED_DRIPSTONE_LAND, SoundSource.BLOCKS, 2.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1046:
            this.level
               .playLocalSound(
                  â˜ƒ, SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON, SoundSource.BLOCKS, 2.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false
               );
            break;
         case 1047:
            this.level
               .playLocalSound(
                  â˜ƒ, SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, SoundSource.BLOCKS, 2.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false
               );
            break;
         case 1048:
            this.level
               .playLocalSound(
                  â˜ƒ, SoundEvents.SKELETON_CONVERTED_TO_STRAY, SoundSource.NEUTRAL, 2.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F, false
               );
            break;
         case 1500:
            ComposterBlock.handleFill(this.level, â˜ƒ, â˜ƒ > 0);
            break;
         case 1501:
            this.level.playLocalSound(â˜ƒ, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.8F, false);

            for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
               this.level
                  .addParticle(
                     ParticleTypes.LARGE_SMOKE,
                     (double)â˜ƒ.getX() + â˜ƒ.nextDouble(),
                     (double)â˜ƒ.getY() + 1.2,
                     (double)â˜ƒ.getZ() + â˜ƒ.nextDouble(),
                     0.0,
                     0.0,
                     0.0
                  );
            }
            break;
         case 1502:
            this.level
               .playLocalSound(â˜ƒ, SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 0.5F, 2.6F + (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.8F, false);

            for(int â˜ƒx = 0; â˜ƒx < 5; ++â˜ƒx) {
               double â˜ƒxx = (double)â˜ƒ.getX() + â˜ƒ.nextDouble() * 0.6 + 0.2;
               double â˜ƒxxx = (double)â˜ƒ.getY() + â˜ƒ.nextDouble() * 0.6 + 0.2;
               double â˜ƒxxxx = (double)â˜ƒ.getZ() + â˜ƒ.nextDouble() * 0.6 + 0.2;
               this.level.addParticle(ParticleTypes.SMOKE, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 1503:
            this.level.playLocalSound(â˜ƒ, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F, false);

            for(int â˜ƒx = 0; â˜ƒx < 16; ++â˜ƒx) {
               double â˜ƒxx = (double)â˜ƒ.getX() + (5.0 + â˜ƒ.nextDouble() * 6.0) / 16.0;
               double â˜ƒxxx = (double)â˜ƒ.getY() + 0.8125;
               double â˜ƒxxxx = (double)â˜ƒ.getZ() + (5.0 + â˜ƒ.nextDouble() * 6.0) / 16.0;
               this.level.addParticle(ParticleTypes.SMOKE, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 1504:
            PointedDripstoneBlock.spawnDripParticle(this.level, â˜ƒ, this.level.getBlockState(â˜ƒ));
            break;
         case 1505:
            BoneMealItem.addGrowthParticles(this.level, â˜ƒ, â˜ƒ);
            this.level.playLocalSound(â˜ƒ, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 2000:
            Direction â˜ƒx = Direction.from3DDataValue(â˜ƒ);
            int â˜ƒxx = â˜ƒx.getStepX();
            int â˜ƒxxx = â˜ƒx.getStepY();
            int â˜ƒxxxx = â˜ƒx.getStepZ();
            double â˜ƒxxxxx = (double)â˜ƒ.getX() + (double)â˜ƒxx * 0.6 + 0.5;
            double â˜ƒxxxxxx = (double)â˜ƒ.getY() + (double)â˜ƒxxx * 0.6 + 0.5;
            double â˜ƒxxxxxxx = (double)â˜ƒ.getZ() + (double)â˜ƒxxxx * 0.6 + 0.5;

            for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 10; ++â˜ƒxxxxxxxx) {
               double â˜ƒxxxxxxxxx = â˜ƒ.nextDouble() * 0.2 + 0.01;
               double â˜ƒxxxxxxxxxx = â˜ƒxxxxx + (double)â˜ƒxx * 0.01 + (â˜ƒ.nextDouble() - 0.5) * (double)â˜ƒxxxx * 0.5;
               double â˜ƒxxxxxxxxxxx = â˜ƒxxxxxx + (double)â˜ƒxxx * 0.01 + (â˜ƒ.nextDouble() - 0.5) * (double)â˜ƒxxx * 0.5;
               double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxx + (double)â˜ƒxxxx * 0.01 + (â˜ƒ.nextDouble() - 0.5) * (double)â˜ƒxx * 0.5;
               double â˜ƒxxxxxxxxxxxxx = (double)â˜ƒxx * â˜ƒxxxxxxxxx + â˜ƒ.nextGaussian() * 0.01;
               double â˜ƒxxxxxxxxxxxxxx = (double)â˜ƒxxx * â˜ƒxxxxxxxxx + â˜ƒ.nextGaussian() * 0.01;
               double â˜ƒxxxxxxxxxxxxxxx = (double)â˜ƒxxxx * â˜ƒxxxxxxxxx + â˜ƒ.nextGaussian() * 0.01;
               this.addParticle(ParticleTypes.SMOKE, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
            }
            break;
         case 2001:
            BlockState â˜ƒx = Block.stateById(â˜ƒ);
            if (!â˜ƒx.isAir()) {
               SoundType â˜ƒxx = â˜ƒx.getSoundType();
               this.level.playLocalSound(â˜ƒ, â˜ƒxx.getBreakSound(), SoundSource.BLOCKS, (â˜ƒxx.getVolume() + 1.0F) / 2.0F, â˜ƒxx.getPitch() * 0.8F, false);
            }

            this.level.addDestroyBlockEffect(â˜ƒ, â˜ƒx);
            break;
         case 2002:
         case 2007:
            Vec3 â˜ƒx = Vec3.atBottomCenterOf(â˜ƒ);

            for(int â˜ƒxx = 0; â˜ƒxx < 8; ++â˜ƒxx) {
               this.addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)),
                  â˜ƒx.x,
                  â˜ƒx.y,
                  â˜ƒx.z,
                  â˜ƒ.nextGaussian() * 0.15,
                  â˜ƒ.nextDouble() * 0.2,
                  â˜ƒ.nextGaussian() * 0.15
               );
            }

            float â˜ƒxx = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F;
            float â˜ƒxxx = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F;
            float â˜ƒxxxx = (float)(â˜ƒ >> 0 & 0xFF) / 255.0F;
            ParticleOptions â˜ƒxxxxx = â˜ƒ == 2007 ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 100; ++â˜ƒxxxxxx) {
               double â˜ƒxxxxxxx = â˜ƒ.nextDouble() * 4.0;
               double â˜ƒxxxxxxxx = â˜ƒ.nextDouble() * Math.PI * 2.0;
               double â˜ƒxxxxxxxxx = Math.cos(â˜ƒxxxxxxxx) * â˜ƒxxxxxxx;
               double â˜ƒxxxxxxxxxx = 0.01 + â˜ƒ.nextDouble() * 0.5;
               double â˜ƒxxxxxxxxxxx = Math.sin(â˜ƒxxxxxxxx) * â˜ƒxxxxxxx;
               Particle â˜ƒxxxxxxxxxxxx = this.addParticleInternal(
                  â˜ƒxxxxx,
                  â˜ƒxxxxx.getType().getOverrideLimiter(),
                  â˜ƒx.x + â˜ƒxxxxxxxxx * 0.1,
                  â˜ƒx.y + 0.3,
                  â˜ƒx.z + â˜ƒxxxxxxxxxxx * 0.1,
                  â˜ƒxxxxxxxxx,
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxx
               );
               if (â˜ƒxxxxxxxxxxxx != null) {
                  float â˜ƒxxxxxxxxxxxxx = 0.75F + â˜ƒ.nextFloat() * 0.25F;
                  â˜ƒxxxxxxxxxxxx.setColor(â˜ƒxx * â˜ƒxxxxxxxxxxxxx, â˜ƒxxx * â˜ƒxxxxxxxxxxxxx, â˜ƒxxxx * â˜ƒxxxxxxxxxxxxx);
                  â˜ƒxxxxxxxxxxxx.setPower((float)â˜ƒxxxxxxx);
               }
            }

            this.level.playLocalSound(â˜ƒ, SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2003:
            double â˜ƒx = (double)â˜ƒ.getX() + 0.5;
            double â˜ƒxx = (double)â˜ƒ.getY();
            double â˜ƒxxx = (double)â˜ƒ.getZ() + 0.5;

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 8; ++â˜ƒxxxx) {
               this.addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.ENDER_EYE)),
                  â˜ƒx,
                  â˜ƒxx,
                  â˜ƒxxx,
                  â˜ƒ.nextGaussian() * 0.15,
                  â˜ƒ.nextDouble() * 0.2,
                  â˜ƒ.nextGaussian() * 0.15
               );
            }

            for(double â˜ƒxxxx = 0.0; â˜ƒxxxx < Math.PI * 2; â˜ƒxxxx += Math.PI / 20) {
               this.addParticle(
                  ParticleTypes.PORTAL,
                  â˜ƒx + Math.cos(â˜ƒxxxx) * 5.0,
                  â˜ƒxx - 0.4,
                  â˜ƒxxx + Math.sin(â˜ƒxxxx) * 5.0,
                  Math.cos(â˜ƒxxxx) * -5.0,
                  0.0,
                  Math.sin(â˜ƒxxxx) * -5.0
               );
               this.addParticle(
                  ParticleTypes.PORTAL,
                  â˜ƒx + Math.cos(â˜ƒxxxx) * 5.0,
                  â˜ƒxx - 0.4,
                  â˜ƒxxx + Math.sin(â˜ƒxxxx) * 5.0,
                  Math.cos(â˜ƒxxxx) * -7.0,
                  0.0,
                  Math.sin(â˜ƒxxxx) * -7.0
               );
            }
            break;
         case 2004:
            for(int â˜ƒx = 0; â˜ƒx < 20; ++â˜ƒx) {
               double â˜ƒxx = (double)â˜ƒ.getX() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 2.0;
               double â˜ƒxxx = (double)â˜ƒ.getY() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 2.0;
               double â˜ƒxxxx = (double)â˜ƒ.getZ() + 0.5 + (â˜ƒ.nextDouble() - 0.5) * 2.0;
               this.level.addParticle(ParticleTypes.SMOKE, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
               this.level.addParticle(ParticleTypes.FLAME, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 2005:
            BoneMealItem.addGrowthParticles(this.level, â˜ƒ, â˜ƒ);
            break;
         case 2006:
            for(int â˜ƒx = 0; â˜ƒx < 200; ++â˜ƒx) {
               float â˜ƒxx = â˜ƒ.nextFloat() * 4.0F;
               float â˜ƒxxx = â˜ƒ.nextFloat() * (float) (Math.PI * 2);
               double â˜ƒxxxx = (double)(Mth.cos(â˜ƒxxx) * â˜ƒxx);
               double â˜ƒxxxxx = 0.01 + â˜ƒ.nextDouble() * 0.5;
               double â˜ƒxxxxxx = (double)(Mth.sin(â˜ƒxxx) * â˜ƒxx);
               Particle â˜ƒxxxxxxx = this.addParticleInternal(
                  ParticleTypes.DRAGON_BREATH,
                  false,
                  (double)â˜ƒ.getX() + â˜ƒxxxx * 0.1,
                  (double)â˜ƒ.getY() + 0.3,
                  (double)â˜ƒ.getZ() + â˜ƒxxxxxx * 0.1,
                  â˜ƒxxxx,
                  â˜ƒxxxxx,
                  â˜ƒxxxxxx
               );
               if (â˜ƒxxxxxxx != null) {
                  â˜ƒxxxxxxx.setPower(â˜ƒxx);
               }
            }

            if (â˜ƒ == 1) {
               this.level.playLocalSound(â˜ƒ, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.HOSTILE, 1.0F, â˜ƒ.nextFloat() * 0.1F + 0.9F, false);
            }
            break;
         case 2008:
            this.level.addParticle(ParticleTypes.EXPLOSION, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, 0.0, 0.0, 0.0);
            break;
         case 2009:
            for(int â˜ƒx = 0; â˜ƒx < 8; ++â˜ƒx) {
               this.level
                  .addParticle(
                     ParticleTypes.CLOUD, (double)â˜ƒ.getX() + â˜ƒ.nextDouble(), (double)â˜ƒ.getY() + 1.2, (double)â˜ƒ.getZ() + â˜ƒ.nextDouble(), 0.0, 0.0, 0.0
                  );
            }
            break;
         case 3000:
            this.level
               .addParticle(ParticleTypes.EXPLOSION_EMITTER, true, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY() + 0.5, (double)â˜ƒ.getZ() + 0.5, 0.0, 0.0, 0.0);
            this.level
               .playLocalSound(
                  â˜ƒ,
                  SoundEvents.END_GATEWAY_SPAWN,
                  SoundSource.BLOCKS,
                  10.0F,
                  (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F,
                  false
               );
            break;
         case 3001:
            this.level.playLocalSound(â˜ƒ, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.HOSTILE, 64.0F, 0.8F + this.level.random.nextFloat() * 0.3F, false);
            break;
         case 3002:
            if (â˜ƒ >= 0 && â˜ƒ < Direction.Axis.VALUES.length) {
               ParticleUtils.spawnParticlesAlongAxis(Direction.Axis.VALUES[â˜ƒ], this.level, â˜ƒ, 0.125, ParticleTypes.ELECTRIC_SPARK, UniformInt.of(10, 19));
            } else {
               ParticleUtils.spawnParticlesOnBlockFaces(this.level, â˜ƒ, ParticleTypes.ELECTRIC_SPARK, UniformInt.of(3, 5));
            }
            break;
         case 3003:
            ParticleUtils.spawnParticlesOnBlockFaces(this.level, â˜ƒ, ParticleTypes.WAX_ON, UniformInt.of(3, 5));
            this.level.playLocalSound(â˜ƒ, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            break;
         case 3004:
            ParticleUtils.spawnParticlesOnBlockFaces(this.level, â˜ƒ, ParticleTypes.WAX_OFF, UniformInt.of(3, 5));
            break;
         case 3005:
            ParticleUtils.spawnParticlesOnBlockFaces(this.level, â˜ƒ, ParticleTypes.SCRAPE, UniformInt.of(3, 5));
      }
   }

   public void destroyBlockProgress(int var1, BlockPos var2, int var3) {
      if (â˜ƒ >= 0 && â˜ƒ < 10) {
         BlockDestructionProgress â˜ƒ = this.destroyingBlocks.get(â˜ƒ);
         if (â˜ƒ != null) {
            this.removeProgress(â˜ƒ);
         }

         if (â˜ƒ == null || â˜ƒ.getPos().getX() != â˜ƒ.getX() || â˜ƒ.getPos().getY() != â˜ƒ.getY() || â˜ƒ.getPos().getZ() != â˜ƒ.getZ()) {
            â˜ƒ = new BlockDestructionProgress(â˜ƒ, â˜ƒ);
            this.destroyingBlocks.put(â˜ƒ, â˜ƒ);
         }

         â˜ƒ.setProgress(â˜ƒ);
         â˜ƒ.updateTick(this.ticks);
         ((SortedSet)this.destructionProgress.computeIfAbsent(â˜ƒ.getPos().asLong(), var0 -> Sets.newTreeSet())).add(â˜ƒ);
      } else {
         BlockDestructionProgress â˜ƒ = this.destroyingBlocks.remove(â˜ƒ);
         if (â˜ƒ != null) {
            this.removeProgress(â˜ƒ);
         }
      }
   }

   public boolean hasRenderedAllChunks() {
      return this.chunksToCompile.isEmpty() && this.chunkRenderDispatcher.isQueueEmpty();
   }

   public void needsUpdate() {
      this.needsUpdate = true;
      this.generateClouds = true;
   }

   public void updateGlobalBlockEntities(Collection<BlockEntity> var1, Collection<BlockEntity> var2) {
      synchronized(this.globalBlockEntities) {
         this.globalBlockEntities.removeAll(â˜ƒ);
         this.globalBlockEntities.addAll(â˜ƒ);
      }
   }

   public static int getLightColor(BlockAndTintGetter var0, BlockPos var1) {
      return getLightColor(â˜ƒ, â˜ƒ.getBlockState(â˜ƒ), â˜ƒ);
   }

   public static int getLightColor(BlockAndTintGetter var0, BlockState var1, BlockPos var2) {
      if (â˜ƒ.emissiveRendering(â˜ƒ, â˜ƒ)) {
         return 15728880;
      } else {
         int â˜ƒ = â˜ƒ.getBrightness(LightLayer.SKY, â˜ƒ);
         int â˜ƒx = â˜ƒ.getBrightness(LightLayer.BLOCK, â˜ƒ);
         int â˜ƒxx = â˜ƒ.getLightEmission();
         if (â˜ƒx < â˜ƒxx) {
            â˜ƒx = â˜ƒxx;
         }

         return â˜ƒ << 20 | â˜ƒx << 4;
      }
   }

   @Nullable
   public RenderTarget entityTarget() {
      return this.entityTarget;
   }

   @Nullable
   public RenderTarget getTranslucentTarget() {
      return this.translucentTarget;
   }

   @Nullable
   public RenderTarget getItemEntityTarget() {
      return this.itemEntityTarget;
   }

   @Nullable
   public RenderTarget getParticlesTarget() {
      return this.particlesTarget;
   }

   @Nullable
   public RenderTarget getWeatherTarget() {
      return this.weatherTarget;
   }

   @Nullable
   public RenderTarget getCloudsTarget() {
      return this.cloudsTarget;
   }

   static class RenderChunkInfo {
      final ChunkRenderDispatcher.RenderChunk chunk;
      private byte sourceDirections;
      byte directions;
      final int step;

      RenderChunkInfo(ChunkRenderDispatcher.RenderChunk var1, @Nullable Direction var2, int var3) {
         this.chunk = â˜ƒ;
         if (â˜ƒ != null) {
            this.addSourceDirection(â˜ƒ);
         }

         this.step = â˜ƒ;
      }

      public void setDirections(byte var1, Direction var2) {
         this.directions = (byte)(this.directions | â˜ƒ | 1 << â˜ƒ.ordinal());
      }

      public boolean hasDirection(Direction var1) {
         return (this.directions & 1 << â˜ƒ.ordinal()) > 0;
      }

      public void addSourceDirection(Direction var1) {
         this.sourceDirections = (byte)(this.sourceDirections | this.sourceDirections | 1 << â˜ƒ.ordinal());
      }

      public boolean hasSourceDirection(int var1) {
         return (this.sourceDirections & 1 << â˜ƒ) > 0;
      }

      public boolean hasSourceDirections() {
         return this.sourceDirections != 0;
      }
   }

   static class RenderInfoMap {
      private final LevelRenderer.RenderChunkInfo[] infos;
      private final LevelRenderer.RenderChunkInfo[] blank;

      RenderInfoMap(int var1) {
         this.infos = new LevelRenderer.RenderChunkInfo[â˜ƒ];
         this.blank = new LevelRenderer.RenderChunkInfo[â˜ƒ];
      }

      void clear() {
         System.arraycopy(this.blank, 0, this.infos, 0, this.infos.length);
      }

      public void put(ChunkRenderDispatcher.RenderChunk var1, LevelRenderer.RenderChunkInfo var2) {
         this.infos[â˜ƒ.index] = â˜ƒ;
      }

      public LevelRenderer.RenderChunkInfo get(ChunkRenderDispatcher.RenderChunk var1) {
         return this.infos[â˜ƒ.index];
      }
   }

   public static class TransparencyShaderException extends RuntimeException {
      public TransparencyShaderException(String var1, Throwable var2) {
         super(â˜ƒ, â˜ƒ);
      }
   }
}
