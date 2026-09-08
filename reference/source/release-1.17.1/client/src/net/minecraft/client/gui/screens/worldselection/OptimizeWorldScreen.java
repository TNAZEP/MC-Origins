package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.util.worldupdate.WorldUpgrader;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptimizeWorldScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Object2IntMap<ResourceKey<Level>> DIMENSION_COLORS = Util.make(new Object2IntOpenCustomHashMap<>(Util.identityStrategy()), var0 -> {
      var0.put(Level.OVERWORLD, -13408734);
      var0.put(Level.NETHER, -10075085);
      var0.put(Level.END, -8943531);
      var0.defaultReturnValue(-2236963);
   });
   private final BooleanConsumer callback;
   private final WorldUpgrader upgrader;

   @Nullable
   public static OptimizeWorldScreen create(Minecraft var0, BooleanConsumer var1, DataFixer var2, LevelStorageSource.LevelStorageAccess var3, boolean var4) {
      RegistryAccess.RegistryHolder â˜ƒ = RegistryAccess.builtin();

      try {
         OptimizeWorldScreen var9;
         try (Minecraft.ServerStem â˜ƒx = â˜ƒ.makeServerStem(â˜ƒ, Minecraft::loadDataPacks, Minecraft::loadWorldData, false, â˜ƒ)) {
            WorldData â˜ƒxx = â˜ƒx.worldData();
            â˜ƒ.saveDataTag(â˜ƒ, â˜ƒxx);
            ImmutableSet<ResourceKey<Level>> â˜ƒxxx = â˜ƒxx.worldGenSettings().levels();
            var9 = new OptimizeWorldScreen(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx.getLevelSettings(), â˜ƒ, â˜ƒxxx);
         }

         return var9;
      } catch (Exception var12) {
         LOGGER.warn("Failed to load datapacks, can't optimize world", var12);
         return null;
      }
   }

   private OptimizeWorldScreen(
      BooleanConsumer var1, DataFixer var2, LevelStorageSource.LevelStorageAccess var3, LevelSettings var4, boolean var5, ImmutableSet<ResourceKey<Level>> var6
   ) {
      super(new TranslatableComponent("optimizeWorld.title", â˜ƒ.levelName()));
      this.callback = â˜ƒ;
      this.upgrader = new WorldUpgrader(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void init() {
      super.init();
      this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 150, 200, 20, CommonComponents.GUI_CANCEL, var1 -> {
         this.upgrader.cancel();
         this.callback.accept(false);
      }));
   }

   @Override
   public void tick() {
      if (this.upgrader.isFinished()) {
         this.callback.accept(true);
      }
   }

   @Override
   public void onClose() {
      this.callback.accept(false);
   }

   @Override
   public void removed() {
      this.upgrader.cancel();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, 16777215);
      int â˜ƒ = this.width / 2 - 150;
      int â˜ƒx = this.width / 2 + 150;
      int â˜ƒxx = this.height / 4 + 100;
      int â˜ƒxxx = â˜ƒxx + 10;
      drawCenteredString(â˜ƒ, this.font, this.upgrader.getStatus(), this.width / 2, â˜ƒxx - 9 - 2, 10526880);
      if (this.upgrader.getTotalChunks() > 0) {
         fill(â˜ƒ, â˜ƒ - 1, â˜ƒxx - 1, â˜ƒx + 1, â˜ƒxxx + 1, -16777216);
         drawString(â˜ƒ, this.font, new TranslatableComponent("optimizeWorld.info.converted", this.upgrader.getConverted()), â˜ƒ, 40, 10526880);
         drawString(â˜ƒ, this.font, new TranslatableComponent("optimizeWorld.info.skipped", this.upgrader.getSkipped()), â˜ƒ, 40 + 9 + 3, 10526880);
         drawString(â˜ƒ, this.font, new TranslatableComponent("optimizeWorld.info.total", this.upgrader.getTotalChunks()), â˜ƒ, 40 + (9 + 3) * 2, 10526880);
         int â˜ƒxxxx = 0;

         for(ResourceKey<Level> â˜ƒxxxxx : this.upgrader.levels()) {
            int â˜ƒxxxxxx = Mth.floor(this.upgrader.dimensionProgress(â˜ƒxxxxx) * (float)(â˜ƒx - â˜ƒ));
            fill(â˜ƒ, â˜ƒ + â˜ƒxxxx, â˜ƒxx, â˜ƒ + â˜ƒxxxx + â˜ƒxxxxxx, â˜ƒxxx, DIMENSION_COLORS.getInt(â˜ƒxxxxx));
            â˜ƒxxxx += â˜ƒxxxxxx;
         }

         int â˜ƒxxxxx = this.upgrader.getConverted() + this.upgrader.getSkipped();
         drawCenteredString(â˜ƒ, this.font, â˜ƒxxxxx + " / " + this.upgrader.getTotalChunks(), this.width / 2, â˜ƒxx + 2 * 9 + 2, 10526880);
         drawCenteredString(
            â˜ƒ, this.font, Mth.floor(this.upgrader.getProgress() * 100.0F) + "%", this.width / 2, â˜ƒxx + (â˜ƒxxx - â˜ƒxx) / 2 - 9 / 2, 10526880
         );
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
