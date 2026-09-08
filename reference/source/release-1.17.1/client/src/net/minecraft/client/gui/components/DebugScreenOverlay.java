package net.minecraft.client.gui.components;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.LongSets;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.network.Connection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class DebugScreenOverlay extends GuiComponent {
   private static final int COLOR_GREY = 14737632;
   private static final int MARGIN_RIGHT = 2;
   private static final int MARGIN_LEFT = 2;
   private static final int MARGIN_TOP = 2;
   private static final Map<Heightmap.Types, String> HEIGHTMAP_NAMES = Util.make(new EnumMap(Heightmap.Types.class), var0 -> {
      var0.put(Heightmap.Types.WORLD_SURFACE_WG, "SW");
      var0.put(Heightmap.Types.WORLD_SURFACE, "S");
      var0.put(Heightmap.Types.OCEAN_FLOOR_WG, "OW");
      var0.put(Heightmap.Types.OCEAN_FLOOR, "O");
      var0.put(Heightmap.Types.MOTION_BLOCKING, "M");
      var0.put(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, "ML");
   });
   private final Minecraft minecraft;
   private final Font font;
   private HitResult block;
   private HitResult liquid;
   @Nullable
   private ChunkPos lastPos;
   @Nullable
   private LevelChunk clientChunk;
   @Nullable
   private CompletableFuture<LevelChunk> serverChunk;
   private static final int RED = -65536;
   private static final int YELLOW = -256;
   private static final int GREEN = -16711936;

   public DebugScreenOverlay(Minecraft var1) {
      this.minecraft = â˜ƒ;
      this.font = â˜ƒ.font;
   }

   public void clearChunkCache() {
      this.serverChunk = null;
      this.clientChunk = null;
   }

   public void render(PoseStack var1) {
      this.minecraft.getProfiler().push("debug");
      Entity â˜ƒ = this.minecraft.getCameraEntity();
      this.block = â˜ƒ.pick(20.0, 0.0F, false);
      this.liquid = â˜ƒ.pick(20.0, 0.0F, true);
      this.drawGameInformation(â˜ƒ);
      this.drawSystemInformation(â˜ƒ);
      if (this.minecraft.options.renderFpsChart) {
         int â˜ƒx = this.minecraft.getWindow().getGuiScaledWidth();
         this.drawChart(â˜ƒ, this.minecraft.getFrameTimer(), 0, â˜ƒx / 2, true);
         IntegratedServer â˜ƒxx = this.minecraft.getSingleplayerServer();
         if (â˜ƒxx != null) {
            this.drawChart(â˜ƒ, â˜ƒxx.getFrameTimer(), â˜ƒx - Math.min(â˜ƒx / 2, 240), â˜ƒx / 2, false);
         }
      }

      this.minecraft.getProfiler().pop();
   }

   protected void drawGameInformation(PoseStack var1) {
      List<String> â˜ƒ = this.getGameInformation();
      â˜ƒ.add("");
      boolean â˜ƒx = this.minecraft.getSingleplayerServer() != null;
      â˜ƒ.add(
         "Debug: Pie [shift]: "
            + (this.minecraft.options.renderDebugCharts ? "visible" : "hidden")
            + (â˜ƒx ? " FPS + TPS" : " FPS")
            + " [alt]: "
            + (this.minecraft.options.renderFpsChart ? "visible" : "hidden")
      );
      â˜ƒ.add("For help: press F3 + Q");

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.size(); ++â˜ƒxx) {
         String â˜ƒxxx = (String)â˜ƒ.get(â˜ƒxx);
         if (!Strings.isNullOrEmpty(â˜ƒxxx)) {
            int â˜ƒxxxx = 9;
            int â˜ƒxxxxx = this.font.width(â˜ƒxxx);
            int â˜ƒxxxxxx = 2;
            int â˜ƒxxxxxxx = 2 + â˜ƒxxxx * â˜ƒxx;
            fill(â˜ƒ, 1, â˜ƒxxxxxxx - 1, 2 + â˜ƒxxxxx + 1, â˜ƒxxxxxxx + â˜ƒxxxx - 1, -1873784752);
            this.font.draw(â˜ƒ, â˜ƒxxx, 2.0F, (float)â˜ƒxxxxxxx, 14737632);
         }
      }
   }

   protected void drawSystemInformation(PoseStack var1) {
      List<String> â˜ƒ = this.getSystemInformation();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         String â˜ƒxx = (String)â˜ƒ.get(â˜ƒx);
         if (!Strings.isNullOrEmpty(â˜ƒxx)) {
            int â˜ƒxxx = 9;
            int â˜ƒxxxx = this.font.width(â˜ƒxx);
            int â˜ƒxxxxx = this.minecraft.getWindow().getGuiScaledWidth() - 2 - â˜ƒxxxx;
            int â˜ƒxxxxxx = 2 + â˜ƒxxx * â˜ƒx;
            fill(â˜ƒ, â˜ƒxxxxx - 1, â˜ƒxxxxxx - 1, â˜ƒxxxxx + â˜ƒxxxx + 1, â˜ƒxxxxxx + â˜ƒxxx - 1, -1873784752);
            this.font.draw(â˜ƒ, â˜ƒxx, (float)â˜ƒxxxxx, (float)â˜ƒxxxxxx, 14737632);
         }
      }
   }

   protected List<String> getGameInformation() {
      IntegratedServer â˜ƒx = this.minecraft.getSingleplayerServer();
      Connection â˜ƒxx = this.minecraft.getConnection().getConnection();
      float â˜ƒxxx = â˜ƒxx.getAverageSentPackets();
      float â˜ƒxxxx = â˜ƒxx.getAverageReceivedPackets();
      String â˜ƒ;
      if (â˜ƒx != null) {
         â˜ƒ = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", â˜ƒx.getAverageTickTime(), â˜ƒxxx, â˜ƒxxxx);
      } else {
         â˜ƒ = String.format("\"%s\" server, %.0f tx, %.0f rx", this.minecraft.player.getServerBrand(), â˜ƒxxx, â˜ƒxxxx);
      }

      BlockPos â˜ƒ = this.minecraft.getCameraEntity().blockPosition();
      if (this.minecraft.showOnlyReducedInfo()) {
         return Lists.newArrayList(
            "Minecraft "
               + SharedConstants.getCurrentVersion().getName()
               + " ("
               + this.minecraft.getLaunchedVersion()
               + "/"
               + ClientBrandRetriever.getClientModName()
               + ")",
            this.minecraft.fpsString,
            â˜ƒ,
            this.minecraft.levelRenderer.getChunkStatistics(),
            this.minecraft.levelRenderer.getEntityStatistics(),
            "P: " + this.minecraft.particleEngine.countParticles() + ". T: " + this.minecraft.level.getEntityCount(),
            this.minecraft.level.gatherChunkSourceStats(),
            "",
            String.format("Chunk-relative: %d %d %d", â˜ƒ.getX() & 15, â˜ƒ.getY() & 15, â˜ƒ.getZ() & 15)
         );
      } else {
         Entity â˜ƒ = this.minecraft.getCameraEntity();
         Direction â˜ƒ = â˜ƒ.getDirection();

         String var9 = switch(â˜ƒ) {
            case NORTH -> "Towards negative Z";
            case SOUTH -> "Towards positive Z";
            case WEST -> "Towards negative X";
            case EAST -> "Towards positive X";
            default -> "Invalid";
         };
         ChunkPos â˜ƒ = new ChunkPos(â˜ƒ);
         if (!Objects.equals(this.lastPos, â˜ƒ)) {
            this.lastPos = â˜ƒ;
            this.clearChunkCache();
         }

         Level â˜ƒ = this.getLevel();
         LongSet â˜ƒx = (LongSet)(â˜ƒ instanceof ServerLevel ? ((ServerLevel)â˜ƒ).getForcedChunks() : LongSets.EMPTY_SET);
         List<String> â˜ƒxx = Lists.newArrayList(
            "Minecraft "
               + SharedConstants.getCurrentVersion().getName()
               + " ("
               + this.minecraft.getLaunchedVersion()
               + "/"
               + ClientBrandRetriever.getClientModName()
               + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType())
               + ")",
            this.minecraft.fpsString,
            â˜ƒ,
            this.minecraft.levelRenderer.getChunkStatistics(),
            this.minecraft.levelRenderer.getEntityStatistics(),
            "P: " + this.minecraft.particleEngine.countParticles() + ". T: " + this.minecraft.level.getEntityCount(),
            this.minecraft.level.gatherChunkSourceStats()
         );
         String â˜ƒxxx = this.getServerChunkStats();
         if (â˜ƒxxx != null) {
            â˜ƒxx.add(â˜ƒxxx);
         }

         â˜ƒxx.add(this.minecraft.level.dimension().location() + " FC: " + â˜ƒx.size());
         â˜ƒxx.add("");
         â˜ƒxx.add(
            String.format(
               Locale.ROOT,
               "XYZ: %.3f / %.5f / %.3f",
               this.minecraft.getCameraEntity().getX(),
               this.minecraft.getCameraEntity().getY(),
               this.minecraft.getCameraEntity().getZ()
            )
         );
         â˜ƒxx.add(String.format("Block: %d %d %d", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ()));
         â˜ƒxx.add(
            String.format(
               "Chunk: %d %d %d in %d %d %d",
               â˜ƒ.getX() & 15,
               â˜ƒ.getY() & 15,
               â˜ƒ.getZ() & 15,
               SectionPos.blockToSectionCoord(â˜ƒ.getX()),
               SectionPos.blockToSectionCoord(â˜ƒ.getY()),
               SectionPos.blockToSectionCoord(â˜ƒ.getZ())
            )
         );
         â˜ƒxx.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", â˜ƒ, var9, Mth.wrapDegrees(â˜ƒ.getYRot()), Mth.wrapDegrees(â˜ƒ.getXRot())));
         LevelChunk â˜ƒ = this.getClientChunk();
         if (â˜ƒ.isEmpty()) {
            â˜ƒxx.add("Waiting for chunk...");
         } else {
            int â˜ƒ = this.minecraft.level.getChunkSource().getLightEngine().getRawBrightness(â˜ƒ, 0);
            int â˜ƒx = this.minecraft.level.getBrightness(LightLayer.SKY, â˜ƒ);
            int â˜ƒxx = this.minecraft.level.getBrightness(LightLayer.BLOCK, â˜ƒ);
            â˜ƒxx.add("Client Light: " + â˜ƒ + " (" + â˜ƒx + " sky, " + â˜ƒxx + " block)");
            LevelChunk â˜ƒxxx = this.getServerChunk();
            StringBuilder â˜ƒxxxx = new StringBuilder("CH");

            for(Heightmap.Types â˜ƒxxxxx : Heightmap.Types.values()) {
               if (â˜ƒxxxxx.sendToClient()) {
                  â˜ƒxxxx.append(" ").append((String)HEIGHTMAP_NAMES.get(â˜ƒxxxxx)).append(": ").append(â˜ƒ.getHeight(â˜ƒxxxxx, â˜ƒ.getX(), â˜ƒ.getZ()));
               }
            }

            â˜ƒxx.add(â˜ƒxxxx.toString());
            â˜ƒxxxx.setLength(0);
            â˜ƒxxxx.append("SH");

            for(Heightmap.Types â˜ƒxxxxx : Heightmap.Types.values()) {
               if (â˜ƒxxxxx.keepAfterWorldgen()) {
                  â˜ƒxxxx.append(" ").append((String)HEIGHTMAP_NAMES.get(â˜ƒxxxxx)).append(": ");
                  if (â˜ƒxxx != null) {
                     â˜ƒxxxx.append(â˜ƒxxx.getHeight(â˜ƒxxxxx, â˜ƒ.getX(), â˜ƒ.getZ()));
                  } else {
                     â˜ƒxxxx.append("??");
                  }
               }
            }

            â˜ƒxx.add(â˜ƒxxxx.toString());
            if (â˜ƒ.getY() >= this.minecraft.level.getMinBuildHeight() && â˜ƒ.getY() < this.minecraft.level.getMaxBuildHeight()) {
               â˜ƒxx.add("Biome: " + this.minecraft.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(this.minecraft.level.getBiome(â˜ƒ)));
               long â˜ƒxxxxx = 0L;
               float â˜ƒxxxxxx = 0.0F;
               if (â˜ƒxxx != null) {
                  â˜ƒxxxxxx = â˜ƒ.getMoonBrightness();
                  â˜ƒxxxxx = â˜ƒxxx.getInhabitedTime();
               }

               DifficultyInstance â˜ƒxxxxx = new DifficultyInstance(â˜ƒ.getDifficulty(), â˜ƒ.getDayTime(), â˜ƒxxxxx, â˜ƒxxxxxx);
               â˜ƒxx.add(
                  String.format(
                     Locale.ROOT,
                     "Local Difficulty: %.2f // %.2f (Day %d)",
                     â˜ƒxxxxx.getEffectiveDifficulty(),
                     â˜ƒxxxxx.getSpecialMultiplier(),
                     this.minecraft.level.getDayTime() / 24000L
                  )
               );
            }
         }

         ServerLevel â˜ƒ = this.getServerLevel();
         if (â˜ƒ != null) {
            NaturalSpawner.SpawnState â˜ƒx = â˜ƒ.getChunkSource().getLastSpawnState();
            if (â˜ƒx != null) {
               Object2IntMap<MobCategory> â˜ƒxx = â˜ƒx.getMobCategoryCounts();
               int â˜ƒxxx = â˜ƒx.getSpawnableChunkCount();
               â˜ƒxx.add(
                  "SC: "
                     + â˜ƒxxx
                     + ", "
                     + (String)Stream.of(MobCategory.values())
                        .map(var1x -> Character.toUpperCase(var1x.getName().charAt(0)) + ": " + â˜ƒ.getInt(var1x))
                        .collect(Collectors.joining(", "))
               );
            } else {
               â˜ƒxx.add("SC: N/A");
            }
         }

         PostChain â˜ƒ = this.minecraft.gameRenderer.currentEffect();
         if (â˜ƒ != null) {
            â˜ƒxx.add("Shader: " + â˜ƒ.getName());
         }

         â˜ƒxx.add(
            this.minecraft.getSoundManager().getDebugString() + String.format(" (Mood %d%%)", Math.round(this.minecraft.player.getCurrentMood() * 100.0F))
         );
         return â˜ƒxx;
      }
   }

   @Nullable
   private ServerLevel getServerLevel() {
      IntegratedServer â˜ƒ = this.minecraft.getSingleplayerServer();
      return â˜ƒ != null ? â˜ƒ.getLevel(this.minecraft.level.dimension()) : null;
   }

   @Nullable
   private String getServerChunkStats() {
      ServerLevel â˜ƒ = this.getServerLevel();
      return â˜ƒ != null ? â˜ƒ.gatherChunkSourceStats() : null;
   }

   private Level getLevel() {
      return DataFixUtils.orElse(
         Optional.ofNullable(this.minecraft.getSingleplayerServer()).flatMap(var1 -> Optional.ofNullable(var1.getLevel(this.minecraft.level.dimension()))),
         this.minecraft.level
      );
   }

   @Nullable
   private LevelChunk getServerChunk() {
      if (this.serverChunk == null) {
         ServerLevel â˜ƒ = this.getServerLevel();
         if (â˜ƒ != null) {
            this.serverChunk = â˜ƒ.getChunkSource()
               .getChunkFuture(this.lastPos.x, this.lastPos.z, ChunkStatus.FULL, false)
               .thenApply(var0 -> var0.map(var0x -> (LevelChunk)var0x, var0x -> null));
         }

         if (this.serverChunk == null) {
            this.serverChunk = CompletableFuture.completedFuture(this.getClientChunk());
         }
      }

      return (LevelChunk)this.serverChunk.getNow(null);
   }

   private LevelChunk getClientChunk() {
      if (this.clientChunk == null) {
         this.clientChunk = this.minecraft.level.getChunk(this.lastPos.x, this.lastPos.z);
      }

      return this.clientChunk;
   }

   protected List<String> getSystemInformation() {
      long â˜ƒ = Runtime.getRuntime().maxMemory();
      long â˜ƒx = Runtime.getRuntime().totalMemory();
      long â˜ƒxx = Runtime.getRuntime().freeMemory();
      long â˜ƒxxx = â˜ƒx - â˜ƒxx;
      List<String> â˜ƒxxxx = Lists.newArrayList(
         String.format("Java: %s %dbit", System.getProperty("java.version"), this.minecraft.is64Bit() ? 64 : 32),
         String.format("Mem: % 2d%% %03d/%03dMB", â˜ƒxxx * 100L / â˜ƒ, bytesToMegabytes(â˜ƒxxx), bytesToMegabytes(â˜ƒ)),
         String.format("Allocated: % 2d%% %03dMB", â˜ƒx * 100L / â˜ƒ, bytesToMegabytes(â˜ƒx)),
         "",
         String.format("CPU: %s", GlUtil.getCpuInfo()),
         "",
         String.format(
            "Display: %dx%d (%s)", Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(), GlUtil.getVendor()
         ),
         GlUtil.getRenderer(),
         GlUtil.getOpenGLVersion()
      );
      if (this.minecraft.showOnlyReducedInfo()) {
         return â˜ƒxxxx;
      } else {
         if (this.block.getType() == HitResult.Type.BLOCK) {
            BlockPos â˜ƒ = ((BlockHitResult)this.block).getBlockPos();
            BlockState â˜ƒx = this.minecraft.level.getBlockState(â˜ƒ);
            â˜ƒxxxx.add("");
            â˜ƒxxxx.add(ChatFormatting.UNDERLINE + "Targeted Block: " + â˜ƒ.getX() + ", " + â˜ƒ.getY() + ", " + â˜ƒ.getZ());
            â˜ƒxxxx.add(String.valueOf(Registry.BLOCK.getKey(â˜ƒx.getBlock())));

            for(Entry<Property<?>, Comparable<?>> â˜ƒxx : â˜ƒx.getValues().entrySet()) {
               â˜ƒxxxx.add(this.getPropertyValueString(â˜ƒxx));
            }

            for(ResourceLocation â˜ƒxx : this.minecraft.getConnection().getTags().getOrEmpty(Registry.BLOCK_REGISTRY).getMatchingTags(â˜ƒx.getBlock())) {
               â˜ƒxxxx.add("#" + â˜ƒxx);
            }
         }

         if (this.liquid.getType() == HitResult.Type.BLOCK) {
            BlockPos â˜ƒ = ((BlockHitResult)this.liquid).getBlockPos();
            FluidState â˜ƒx = this.minecraft.level.getFluidState(â˜ƒ);
            â˜ƒxxxx.add("");
            â˜ƒxxxx.add(ChatFormatting.UNDERLINE + "Targeted Fluid: " + â˜ƒ.getX() + ", " + â˜ƒ.getY() + ", " + â˜ƒ.getZ());
            â˜ƒxxxx.add(String.valueOf(Registry.FLUID.getKey(â˜ƒx.getType())));

            for(Entry<Property<?>, Comparable<?>> â˜ƒxx : â˜ƒx.getValues().entrySet()) {
               â˜ƒxxxx.add(this.getPropertyValueString(â˜ƒxx));
            }

            for(ResourceLocation â˜ƒxx : this.minecraft.getConnection().getTags().getOrEmpty(Registry.FLUID_REGISTRY).getMatchingTags(â˜ƒx.getType())) {
               â˜ƒxxxx.add("#" + â˜ƒxx);
            }
         }

         Entity â˜ƒ = this.minecraft.crosshairPickEntity;
         if (â˜ƒ != null) {
            â˜ƒxxxx.add("");
            â˜ƒxxxx.add(ChatFormatting.UNDERLINE + "Targeted Entity");
            â˜ƒxxxx.add(String.valueOf(Registry.ENTITY_TYPE.getKey(â˜ƒ.getType())));
         }

         return â˜ƒxxxx;
      }
   }

   private String getPropertyValueString(Entry<Property<?>, Comparable<?>> var1) {
      Property<?> â˜ƒ = (Property)â˜ƒ.getKey();
      Comparable<?> â˜ƒx = (Comparable)â˜ƒ.getValue();
      String â˜ƒxx = Util.getPropertyName(â˜ƒ, â˜ƒx);
      if (Boolean.TRUE.equals(â˜ƒx)) {
         â˜ƒxx = ChatFormatting.GREEN + â˜ƒxx;
      } else if (Boolean.FALSE.equals(â˜ƒx)) {
         â˜ƒxx = ChatFormatting.RED + â˜ƒxx;
      }

      return â˜ƒ.getName() + ": " + â˜ƒxx;
   }

   private void drawChart(PoseStack var1, FrameTimer var2, int var3, int var4, boolean var5) {
      RenderSystem.disableDepthTest();
      int â˜ƒ = â˜ƒ.getLogStart();
      int â˜ƒx = â˜ƒ.getLogEnd();
      long[] â˜ƒxx = â˜ƒ.getLog();
      int â˜ƒxxx = â˜ƒ;
      int â˜ƒxxxx = Math.max(0, â˜ƒxx.length - â˜ƒ);
      int â˜ƒxxxxx = â˜ƒxx.length - â˜ƒxxxx;
      int var9 = â˜ƒ.wrapIndex(â˜ƒ + â˜ƒxxxx);
      long â˜ƒxxxxxx = 0L;
      int â˜ƒxxxxxxx = Integer.MAX_VALUE;
      int â˜ƒxxxxxxxx = Integer.MIN_VALUE;

      for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxxxxx) {
         int â˜ƒxxxxxxxxxx = (int)(â˜ƒxx[â˜ƒ.wrapIndex(var9 + â˜ƒxxxxxxxxx)] / 1000000L);
         â˜ƒxxxxxxx = Math.min(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxx);
         â˜ƒxxxxxxxx = Math.max(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx);
         â˜ƒxxxxxx += (long)â˜ƒxxxxxxxxxx;
      }

      int â˜ƒxxxxxxxxx = this.minecraft.getWindow().getGuiScaledHeight();
      fill(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxx - 60, â˜ƒ + â˜ƒxxxxx, â˜ƒxxxxxxxxx, -1873784752);
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      BufferBuilder â˜ƒxxxxxxxxxx = Tesselator.getInstance().getBuilder();
      RenderSystem.enableBlend();
      RenderSystem.disableTexture();
      RenderSystem.defaultBlendFunc();
      â˜ƒxxxxxxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

      for(Matrix4f â˜ƒxxxxxxxxxxx = Transformation.identity().getMatrix(); var9 != â˜ƒx; var9 = â˜ƒ.wrapIndex(var9 + 1)) {
         int â˜ƒxxxxxxxxxxxx = â˜ƒ.scaleSampleTo(â˜ƒxx[var9], â˜ƒ ? 30 : 60, â˜ƒ ? 60 : 20);
         int â˜ƒxxxxxxxxxxxxx = â˜ƒ ? 100 : 60;
         int â˜ƒxxxxxxxxxxxxxx = this.getSampleColor(Mth.clamp(â˜ƒxxxxxxxxxxxx, 0, â˜ƒxxxxxxxxxxxxx), 0, â˜ƒxxxxxxxxxxxxx / 2, â˜ƒxxxxxxxxxxxxx);
         int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx >> 24 & 0xFF;
         int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx >> 16 & 0xFF;
         int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx >> 8 & 0xFF;
         int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx & 0xFF;
         â˜ƒxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxx, (float)(â˜ƒxxx + 1), (float)â˜ƒxxxxxxxxx, 0.0F)
            .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx)
            .endVertex();
         â˜ƒxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxx, (float)(â˜ƒxxx + 1), (float)(â˜ƒxxxxxxxxx - â˜ƒxxxxxxxxxxxx + 1), 0.0F)
            .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx)
            .endVertex();
         â˜ƒxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxx, (float)â˜ƒxxx, (float)(â˜ƒxxxxxxxxx - â˜ƒxxxxxxxxxxxx + 1), 0.0F)
            .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx)
            .endVertex();
         â˜ƒxxxxxxxxxx.vertex(â˜ƒxxxxxxxxxxx, (float)â˜ƒxxx, (float)â˜ƒxxxxxxxxx, 0.0F)
            .color(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx)
            .endVertex();
         ++â˜ƒxxx;
      }

      â˜ƒxxxxxxxxxx.end();
      BufferUploader.end(â˜ƒxxxxxxxxxx);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      if (â˜ƒ) {
         fill(â˜ƒ, â˜ƒ + 1, â˜ƒxxxxxxxxx - 30 + 1, â˜ƒ + 14, â˜ƒxxxxxxxxx - 30 + 10, -1873784752);
         this.font.draw(â˜ƒ, "60 FPS", (float)(â˜ƒ + 2), (float)(â˜ƒxxxxxxxxx - 30 + 2), 14737632);
         this.hLine(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒxxxxx - 1, â˜ƒxxxxxxxxx - 30, -1);
         fill(â˜ƒ, â˜ƒ + 1, â˜ƒxxxxxxxxx - 60 + 1, â˜ƒ + 14, â˜ƒxxxxxxxxx - 60 + 10, -1873784752);
         this.font.draw(â˜ƒ, "30 FPS", (float)(â˜ƒ + 2), (float)(â˜ƒxxxxxxxxx - 60 + 2), 14737632);
         this.hLine(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒxxxxx - 1, â˜ƒxxxxxxxxx - 60, -1);
      } else {
         fill(â˜ƒ, â˜ƒ + 1, â˜ƒxxxxxxxxx - 60 + 1, â˜ƒ + 14, â˜ƒxxxxxxxxx - 60 + 10, -1873784752);
         this.font.draw(â˜ƒ, "20 TPS", (float)(â˜ƒ + 2), (float)(â˜ƒxxxxxxxxx - 60 + 2), 14737632);
         this.hLine(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒxxxxx - 1, â˜ƒxxxxxxxxx - 60, -1);
      }

      this.hLine(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒxxxxx - 1, â˜ƒxxxxxxxxx - 1, -1);
      this.vLine(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxx - 60, â˜ƒxxxxxxxxx, -1);
      this.vLine(â˜ƒ, â˜ƒ + â˜ƒxxxxx - 1, â˜ƒxxxxxxxxx - 60, â˜ƒxxxxxxxxx, -1);
      if (â˜ƒ && this.minecraft.options.framerateLimit > 0 && this.minecraft.options.framerateLimit <= 250) {
         this.hLine(â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒxxxxx - 1, â˜ƒxxxxxxxxx - 1 - (int)(1800.0 / (double)this.minecraft.options.framerateLimit), -16711681);
      }

      String â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxx + " ms min";
      String â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx / (long)â˜ƒxxxxx + " ms avg";
      String â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxx + " ms max";
      this.font.drawShadow(â˜ƒ, â˜ƒxxxxxxxxxxx, (float)(â˜ƒ + 2), (float)(â˜ƒxxxxxxxxx - 60 - 9), 14737632);
      this.font.drawShadow(â˜ƒ, â˜ƒxxxxxxxxxxxx, (float)(â˜ƒ + â˜ƒxxxxx / 2 - this.font.width(â˜ƒxxxxxxxxxxxx) / 2), (float)(â˜ƒxxxxxxxxx - 60 - 9), 14737632);
      this.font.drawShadow(â˜ƒ, â˜ƒxxxxxxxxxxxxx, (float)(â˜ƒ + â˜ƒxxxxx - this.font.width(â˜ƒxxxxxxxxxxxxx)), (float)(â˜ƒxxxxxxxxx - 60 - 9), 14737632);
      RenderSystem.enableDepthTest();
   }

   private int getSampleColor(int var1, int var2, int var3, int var4) {
      return â˜ƒ < â˜ƒ ? this.colorLerp(-16711936, -256, (float)â˜ƒ / (float)â˜ƒ) : this.colorLerp(-256, -65536, (float)(â˜ƒ - â˜ƒ) / (float)(â˜ƒ - â˜ƒ));
   }

   private int colorLerp(int var1, int var2, float var3) {
      int â˜ƒ = â˜ƒ >> 24 & 0xFF;
      int â˜ƒx = â˜ƒ >> 16 & 0xFF;
      int â˜ƒxx = â˜ƒ >> 8 & 0xFF;
      int â˜ƒxxx = â˜ƒ & 0xFF;
      int â˜ƒxxxx = â˜ƒ >> 24 & 0xFF;
      int â˜ƒxxxxx = â˜ƒ >> 16 & 0xFF;
      int â˜ƒxxxxxx = â˜ƒ >> 8 & 0xFF;
      int â˜ƒxxxxxxx = â˜ƒ & 0xFF;
      int â˜ƒxxxxxxxx = Mth.clamp((int)Mth.lerp(â˜ƒ, (float)â˜ƒ, (float)â˜ƒxxxx), 0, 255);
      int â˜ƒxxxxxxxxx = Mth.clamp((int)Mth.lerp(â˜ƒ, (float)â˜ƒx, (float)â˜ƒxxxxx), 0, 255);
      int â˜ƒxxxxxxxxxx = Mth.clamp((int)Mth.lerp(â˜ƒ, (float)â˜ƒxx, (float)â˜ƒxxxxxx), 0, 255);
      int â˜ƒxxxxxxxxxxx = Mth.clamp((int)Mth.lerp(â˜ƒ, (float)â˜ƒxxx, (float)â˜ƒxxxxxxx), 0, 255);
      return â˜ƒxxxxxxxx << 24 | â˜ƒxxxxxxxxx << 16 | â˜ƒxxxxxxxxxx << 8 | â˜ƒxxxxxxxxxxx;
   }

   private static long bytesToMegabytes(long var0) {
      return â˜ƒ / 1024L / 1024L;
   }
}
