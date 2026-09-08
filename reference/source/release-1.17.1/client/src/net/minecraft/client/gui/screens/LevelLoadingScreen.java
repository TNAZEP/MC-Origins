package net.minecraft.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.progress.StoringChunkProgressListener;
import net.minecraft.util.Mth;
import net.minecraft.world.level.chunk.ChunkStatus;

public class LevelLoadingScreen extends Screen {
   private static final long NARRATION_DELAY_MS = 2000L;
   private final StoringChunkProgressListener progressListener;
   private long lastNarration = -1L;
   private boolean done;
   private static final Object2IntMap<ChunkStatus> COLORS = Util.make(new Object2IntOpenHashMap<>(), var0 -> {
      var0.defaultReturnValue(0);
      var0.put(ChunkStatus.EMPTY, 5526612);
      var0.put(ChunkStatus.STRUCTURE_STARTS, 10066329);
      var0.put(ChunkStatus.STRUCTURE_REFERENCES, 6250897);
      var0.put(ChunkStatus.BIOMES, 8434258);
      var0.put(ChunkStatus.NOISE, 13750737);
      var0.put(ChunkStatus.SURFACE, 7497737);
      var0.put(ChunkStatus.CARVERS, 7169628);
      var0.put(ChunkStatus.LIQUID_CARVERS, 3159410);
      var0.put(ChunkStatus.FEATURES, 2213376);
      var0.put(ChunkStatus.LIGHT, 13421772);
      var0.put(ChunkStatus.SPAWN, 15884384);
      var0.put(ChunkStatus.HEIGHTMAPS, 15658734);
      var0.put(ChunkStatus.FULL, 16777215);
   });

   public LevelLoadingScreen(StoringChunkProgressListener var1) {
      super(NarratorChatListener.NO_TITLE);
      this.progressListener = â˜ƒ;
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return false;
   }

   @Override
   public void removed() {
      this.done = true;
      this.triggerImmediateNarration(true);
   }

   @Override
   protected void updateNarratedWidget(NarrationElementOutput var1) {
      if (this.done) {
         â˜ƒ.add(NarratedElementType.TITLE, new TranslatableComponent("narrator.loading.done"));
      } else {
         String â˜ƒ = this.getFormattedProgress();
         â˜ƒ.add(NarratedElementType.TITLE, â˜ƒ);
      }
   }

   private String getFormattedProgress() {
      return Mth.clamp(this.progressListener.getProgress(), 0, 100) + "%";
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      long â˜ƒ = Util.getMillis();
      if (â˜ƒ - this.lastNarration > 2000L) {
         this.lastNarration = â˜ƒ;
         this.triggerImmediateNarration(true);
      }

      int â˜ƒ = this.width / 2;
      int â˜ƒx = this.height / 2;
      int â˜ƒxx = 30;
      renderChunks(â˜ƒ, this.progressListener, â˜ƒ, â˜ƒx + 30, 2, 0);
      drawCenteredString(â˜ƒ, this.font, this.getFormattedProgress(), â˜ƒ, â˜ƒx - 9 / 2 - 30, 16777215);
   }

   public static void renderChunks(PoseStack var0, StoringChunkProgressListener var1, int var2, int var3, int var4, int var5) {
      int â˜ƒ = â˜ƒ + â˜ƒ;
      int â˜ƒx = â˜ƒ.getFullDiameter();
      int â˜ƒxx = â˜ƒx * â˜ƒ - â˜ƒ;
      int â˜ƒxxx = â˜ƒ.getDiameter();
      int â˜ƒxxxx = â˜ƒxxx * â˜ƒ - â˜ƒ;
      int â˜ƒxxxxx = â˜ƒ - â˜ƒxxxx / 2;
      int â˜ƒxxxxxx = â˜ƒ - â˜ƒxxxx / 2;
      int â˜ƒxxxxxxx = â˜ƒxx / 2 + 1;
      int â˜ƒxxxxxxxx = -16772609;
      if (â˜ƒ != 0) {
         fill(â˜ƒ, â˜ƒ - â˜ƒxxxxxxx, â˜ƒ - â˜ƒxxxxxxx, â˜ƒ - â˜ƒxxxxxxx + 1, â˜ƒ + â˜ƒxxxxxxx, -16772609);
         fill(â˜ƒ, â˜ƒ + â˜ƒxxxxxxx - 1, â˜ƒ - â˜ƒxxxxxxx, â˜ƒ + â˜ƒxxxxxxx, â˜ƒ + â˜ƒxxxxxxx, -16772609);
         fill(â˜ƒ, â˜ƒ - â˜ƒxxxxxxx, â˜ƒ - â˜ƒxxxxxxx, â˜ƒ + â˜ƒxxxxxxx, â˜ƒ - â˜ƒxxxxxxx + 1, -16772609);
         fill(â˜ƒ, â˜ƒ - â˜ƒxxxxxxx, â˜ƒ + â˜ƒxxxxxxx - 1, â˜ƒ + â˜ƒxxxxxxx, â˜ƒ + â˜ƒxxxxxxx, -16772609);
      }

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒxxx; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒxxx; ++â˜ƒx) {
            ChunkStatus â˜ƒxx = â˜ƒ.getStatus(â˜ƒ, â˜ƒx);
            int â˜ƒxxx = â˜ƒxxxxx + â˜ƒ * â˜ƒ;
            int â˜ƒxxxx = â˜ƒxxxxxx + â˜ƒx * â˜ƒ;
            fill(â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxx + â˜ƒ, â˜ƒxxxx + â˜ƒ, COLORS.getInt(â˜ƒxx) | 0xFF000000);
         }
      }
   }
}
