package net.minecraft.client.gui.screens;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PresetFlatWorldScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int SLOT_TEX_SIZE = 128;
   private static final int SLOT_BG_SIZE = 18;
   private static final int SLOT_STAT_HEIGHT = 20;
   private static final int SLOT_BG_X = 1;
   private static final int SLOT_BG_Y = 1;
   private static final int SLOT_FG_X = 2;
   private static final int SLOT_FG_Y = 2;
   static final List<PresetFlatWorldScreen.PresetInfo> PRESETS = Lists.<PresetFlatWorldScreen.PresetInfo>newArrayList();
   private static final ResourceKey<Biome> DEFAULT_BIOME = Biomes.PLAINS;
   final CreateFlatWorldScreen parent;
   private Component shareText;
   private Component listText;
   private PresetFlatWorldScreen.PresetsList list;
   private Button selectButton;
   EditBox export;
   FlatLevelGeneratorSettings settings;

   public PresetFlatWorldScreen(CreateFlatWorldScreen var1) {
      super(new TranslatableComponent("createWorld.customize.presets.title"));
      this.parent = â˜ƒ;
   }

   @Nullable
   private static FlatLayerInfo getLayerInfoFromString(String var0, int var1) {
      String[] â˜ƒx = â˜ƒ.split("\\*", 2);
      int â˜ƒ;
      if (â˜ƒx.length == 2) {
         try {
            â˜ƒ = Math.max(Integer.parseInt(â˜ƒx[0]), 0);
         } catch (NumberFormatException var10) {
            LOGGER.error("Error while parsing flat world string => {}", var10.getMessage());
            return null;
         }
      } else {
         â˜ƒ = 1;
      }

      int â˜ƒ = Math.min(â˜ƒ + â˜ƒ, DimensionType.Y_SIZE);
      int â˜ƒx = â˜ƒ - â˜ƒ;
      String â˜ƒxx = â˜ƒx[â˜ƒx.length - 1];

      Block â˜ƒ;
      try {
         â˜ƒ = (Block)Registry.BLOCK.getOptional(new ResourceLocation(â˜ƒxx)).orElse(null);
      } catch (Exception var9) {
         LOGGER.error("Error while parsing flat world string => {}", var9.getMessage());
         return null;
      }

      if (â˜ƒ == null) {
         LOGGER.error("Error while parsing flat world string => Unknown block, {}", â˜ƒxx);
         return null;
      } else {
         return new FlatLayerInfo(â˜ƒx, â˜ƒ);
      }
   }

   private static List<FlatLayerInfo> getLayersInfoFromString(String var0) {
      List<FlatLayerInfo> â˜ƒ = Lists.<FlatLayerInfo>newArrayList();
      String[] â˜ƒx = â˜ƒ.split(",");
      int â˜ƒxx = 0;

      for(String â˜ƒxxx : â˜ƒx) {
         FlatLayerInfo â˜ƒxxxx = getLayerInfoFromString(â˜ƒxxx, â˜ƒxx);
         if (â˜ƒxxxx == null) {
            return Collections.emptyList();
         }

         â˜ƒ.add(â˜ƒxxxx);
         â˜ƒxx += â˜ƒxxxx.getHeight();
      }

      return â˜ƒ;
   }

   public static FlatLevelGeneratorSettings fromString(Registry<Biome> var0, String var1, FlatLevelGeneratorSettings var2) {
      Iterator<String> â˜ƒ = Splitter.on(';').split(â˜ƒ).iterator();
      if (!â˜ƒ.hasNext()) {
         return FlatLevelGeneratorSettings.getDefault(â˜ƒ);
      } else {
         List<FlatLayerInfo> â˜ƒ = getLayersInfoFromString((String)â˜ƒ.next());
         if (â˜ƒ.isEmpty()) {
            return FlatLevelGeneratorSettings.getDefault(â˜ƒ);
         } else {
            FlatLevelGeneratorSettings â˜ƒ = â˜ƒ.withLayers(â˜ƒ, â˜ƒ.structureSettings());
            ResourceKey<Biome> â˜ƒx = DEFAULT_BIOME;
            if (â˜ƒ.hasNext()) {
               try {
                  ResourceLocation â˜ƒxx = new ResourceLocation((String)â˜ƒ.next());
                  â˜ƒx = ResourceKey.create(Registry.BIOME_REGISTRY, â˜ƒxx);
                  â˜ƒ.getOptional(â˜ƒx).orElseThrow(() -> new IllegalArgumentException("Invalid Biome: " + â˜ƒ));
               } catch (Exception var8) {
                  LOGGER.error("Error while parsing flat world string => {}", var8.getMessage());
                  â˜ƒx = DEFAULT_BIOME;
               }
            }

            ResourceKey<Biome> â˜ƒ = â˜ƒx;
            â˜ƒ.setBiome(() -> â˜ƒ.getOrThrow(â˜ƒ));
            return â˜ƒ;
         }
      }
   }

   static String save(Registry<Biome> var0, FlatLevelGeneratorSettings var1) {
      StringBuilder â˜ƒ = new StringBuilder();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getLayersInfo().size(); ++â˜ƒx) {
         if (â˜ƒx > 0) {
            â˜ƒ.append(",");
         }

         â˜ƒ.append(â˜ƒ.getLayersInfo().get(â˜ƒx));
      }

      â˜ƒ.append(";");
      â˜ƒ.append(â˜ƒ.getKey(â˜ƒ.getBiome()));
      return â˜ƒ.toString();
   }

   @Override
   protected void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.shareText = new TranslatableComponent("createWorld.customize.presets.share");
      this.listText = new TranslatableComponent("createWorld.customize.presets.list");
      this.export = new EditBox(this.font, 50, 40, this.width - 100, 20, this.shareText);
      this.export.setMaxLength(1230);
      Registry<Biome> â˜ƒ = this.parent.parent.worldGenSettingsComponent.registryHolder().registryOrThrow(Registry.BIOME_REGISTRY);
      this.export.setValue(save(â˜ƒ, this.parent.settings()));
      this.settings = this.parent.settings();
      this.addWidget(this.export);
      this.list = new PresetFlatWorldScreen.PresetsList();
      this.addWidget(this.list);
      this.selectButton = this.addRenderableWidget(
         new Button(this.width / 2 - 155, this.height - 28, 150, 20, new TranslatableComponent("createWorld.customize.presets.select"), var2 -> {
            FlatLevelGeneratorSettings â˜ƒ = fromString(â˜ƒ, this.export.getValue(), this.settings);
            this.parent.setConfig(â˜ƒ);
            this.minecraft.setScreen(this.parent);
         })
      );
      this.addRenderableWidget(
         new Button(this.width / 2 + 5, this.height - 28, 150, 20, CommonComponents.GUI_CANCEL, var1x -> this.minecraft.setScreen(this.parent))
      );
      this.updateButtonValidity(this.list.getSelected() != null);
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      return this.list.mouseScrolled(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void resize(Minecraft var1, int var2, int var3) {
      String â˜ƒ = this.export.getValue();
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
      this.export.setValue(â˜ƒ);
   }

   @Override
   public void onClose() {
      this.minecraft.setScreen(this.parent);
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      this.list.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.0, 400.0);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 8, 16777215);
      drawString(â˜ƒ, this.font, this.shareText, 50, 30, 10526880);
      drawString(â˜ƒ, this.font, this.listText, 50, 70, 10526880);
      â˜ƒ.popPose();
      this.export.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick() {
      this.export.tick();
      super.tick();
   }

   public void updateButtonValidity(boolean var1) {
      this.selectButton.active = â˜ƒ || this.export.getValue().length() > 1;
   }

   private static void preset(
      Component var0, ItemLike var1, ResourceKey<Biome> var2, List<StructureFeature<?>> var3, boolean var4, boolean var5, boolean var6, FlatLayerInfo... var7
   ) {
      PRESETS.add(new PresetFlatWorldScreen.PresetInfo(â˜ƒ.asItem(), â˜ƒ, var6x -> {
         Map<StructureFeature<?>, StructureFeatureConfiguration> â˜ƒ = Maps.<StructureFeature<?>, StructureFeatureConfiguration>newHashMap();

         for(StructureFeature<?> â˜ƒx : â˜ƒ) {
            â˜ƒ.put(â˜ƒx, StructureSettings.DEFAULTS.get(â˜ƒx));
         }

         StructureSettings â˜ƒx = new StructureSettings(â˜ƒ ? Optional.of(StructureSettings.DEFAULT_STRONGHOLD) : Optional.empty(), â˜ƒ);
         FlatLevelGeneratorSettings â˜ƒxx = new FlatLevelGeneratorSettings(â˜ƒx, var6x);
         if (â˜ƒ) {
            â˜ƒxx.setDecoration();
         }

         if (â˜ƒ) {
            â˜ƒxx.setAddLakes();
         }

         for(int â˜ƒx = â˜ƒ.length - 1; â˜ƒx >= 0; --â˜ƒx) {
            â˜ƒxx.getLayersInfo().add(â˜ƒ[â˜ƒx]);
         }

         â˜ƒxx.setBiome(() -> var6x.getOrThrow(â˜ƒ));
         â˜ƒxx.updateLayers();
         return â˜ƒxx.withStructureSettings(â˜ƒx);
      }));
   }

   static {
      preset(
         new TranslatableComponent("createWorld.customize.preset.classic_flat"),
         Blocks.GRASS_BLOCK,
         Biomes.PLAINS,
         Arrays.asList(StructureFeature.VILLAGE),
         false,
         false,
         false,
         new FlatLayerInfo(1, Blocks.GRASS_BLOCK),
         new FlatLayerInfo(2, Blocks.DIRT),
         new FlatLayerInfo(1, Blocks.BEDROCK)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.tunnelers_dream"),
         Blocks.STONE,
         Biomes.MOUNTAINS,
         Arrays.asList(StructureFeature.MINESHAFT),
         true,
         true,
         false,
         new FlatLayerInfo(1, Blocks.GRASS_BLOCK),
         new FlatLayerInfo(5, Blocks.DIRT),
         new FlatLayerInfo(230, Blocks.STONE),
         new FlatLayerInfo(1, Blocks.BEDROCK)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.water_world"),
         Items.WATER_BUCKET,
         Biomes.DEEP_OCEAN,
         Arrays.asList(StructureFeature.OCEAN_RUIN, StructureFeature.SHIPWRECK, StructureFeature.OCEAN_MONUMENT),
         false,
         false,
         false,
         new FlatLayerInfo(90, Blocks.WATER),
         new FlatLayerInfo(5, Blocks.SAND),
         new FlatLayerInfo(5, Blocks.DIRT),
         new FlatLayerInfo(5, Blocks.STONE),
         new FlatLayerInfo(1, Blocks.BEDROCK)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.overworld"),
         Blocks.GRASS,
         Biomes.PLAINS,
         Arrays.asList(StructureFeature.VILLAGE, StructureFeature.MINESHAFT, StructureFeature.PILLAGER_OUTPOST, StructureFeature.RUINED_PORTAL),
         true,
         true,
         true,
         new FlatLayerInfo(1, Blocks.GRASS_BLOCK),
         new FlatLayerInfo(3, Blocks.DIRT),
         new FlatLayerInfo(59, Blocks.STONE),
         new FlatLayerInfo(1, Blocks.BEDROCK)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.snowy_kingdom"),
         Blocks.SNOW,
         Biomes.SNOWY_TUNDRA,
         Arrays.asList(StructureFeature.VILLAGE, StructureFeature.IGLOO),
         false,
         false,
         false,
         new FlatLayerInfo(1, Blocks.SNOW),
         new FlatLayerInfo(1, Blocks.GRASS_BLOCK),
         new FlatLayerInfo(3, Blocks.DIRT),
         new FlatLayerInfo(59, Blocks.STONE),
         new FlatLayerInfo(1, Blocks.BEDROCK)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.bottomless_pit"),
         Items.FEATHER,
         Biomes.PLAINS,
         Arrays.asList(StructureFeature.VILLAGE),
         false,
         false,
         false,
         new FlatLayerInfo(1, Blocks.GRASS_BLOCK),
         new FlatLayerInfo(3, Blocks.DIRT),
         new FlatLayerInfo(2, Blocks.COBBLESTONE)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.desert"),
         Blocks.SAND,
         Biomes.DESERT,
         Arrays.asList(StructureFeature.VILLAGE, StructureFeature.DESERT_PYRAMID, StructureFeature.MINESHAFT),
         true,
         true,
         false,
         new FlatLayerInfo(8, Blocks.SAND),
         new FlatLayerInfo(52, Blocks.SANDSTONE),
         new FlatLayerInfo(3, Blocks.STONE),
         new FlatLayerInfo(1, Blocks.BEDROCK)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.redstone_ready"),
         Items.REDSTONE,
         Biomes.DESERT,
         Collections.emptyList(),
         false,
         false,
         false,
         new FlatLayerInfo(52, Blocks.SANDSTONE),
         new FlatLayerInfo(3, Blocks.STONE),
         new FlatLayerInfo(1, Blocks.BEDROCK)
      );
      preset(
         new TranslatableComponent("createWorld.customize.preset.the_void"),
         Blocks.BARRIER,
         Biomes.THE_VOID,
         Collections.emptyList(),
         false,
         true,
         false,
         new FlatLayerInfo(1, Blocks.AIR)
      );
   }

   static class PresetInfo {
      public final Item icon;
      public final Component name;
      public final Function<Registry<Biome>, FlatLevelGeneratorSettings> settings;

      public PresetInfo(Item var1, Component var2, Function<Registry<Biome>, FlatLevelGeneratorSettings> var3) {
         this.icon = â˜ƒ;
         this.name = â˜ƒ;
         this.settings = â˜ƒ;
      }

      public Component getName() {
         return this.name;
      }
   }

   class PresetsList extends ObjectSelectionList<PresetFlatWorldScreen.PresetsList.Entry> {
      public PresetsList() {
         super(
            PresetFlatWorldScreen.this.minecraft,
            PresetFlatWorldScreen.this.width,
            PresetFlatWorldScreen.this.height,
            80,
            PresetFlatWorldScreen.this.height - 37,
            24
         );

         for(PresetFlatWorldScreen.PresetInfo â˜ƒ : PresetFlatWorldScreen.PRESETS) {
            this.addEntry(new PresetFlatWorldScreen.PresetsList.Entry(â˜ƒ));
         }
      }

      public void setSelected(@Nullable PresetFlatWorldScreen.PresetsList.Entry var1) {
         super.setSelected(â˜ƒ);
         PresetFlatWorldScreen.this.updateButtonValidity(â˜ƒ != null);
      }

      @Override
      protected boolean isFocused() {
         return PresetFlatWorldScreen.this.getFocused() == this;
      }

      @Override
      public boolean keyPressed(int var1, int var2, int var3) {
         if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
            return true;
         } else {
            if ((â˜ƒ == 257 || â˜ƒ == 335) && this.getSelected() != null) {
               this.getSelected().select();
            }

            return false;
         }
      }

      public class Entry extends ObjectSelectionList.Entry<PresetFlatWorldScreen.PresetsList.Entry> {
         private final PresetFlatWorldScreen.PresetInfo preset;

         public Entry(PresetFlatWorldScreen.PresetInfo var2) {
            this.preset = â˜ƒ;
         }

         @Override
         public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            this.blitSlot(â˜ƒ, â˜ƒ, â˜ƒ, this.preset.icon);
            PresetFlatWorldScreen.this.font.draw(â˜ƒ, this.preset.name, (float)(â˜ƒ + 18 + 5), (float)(â˜ƒ + 6), 16777215);
         }

         @Override
         public boolean mouseClicked(double var1, double var3, int var5) {
            if (â˜ƒ == 0) {
               this.select();
            }

            return false;
         }

         void select() {
            PresetsList.this.setSelected(this);
            Registry<Biome> â˜ƒ = PresetFlatWorldScreen.this.parent.parent.worldGenSettingsComponent.registryHolder().registryOrThrow(Registry.BIOME_REGISTRY);
            PresetFlatWorldScreen.this.settings = (FlatLevelGeneratorSettings)this.preset.settings.apply(â˜ƒ);
            PresetFlatWorldScreen.this.export.setValue(PresetFlatWorldScreen.save(â˜ƒ, PresetFlatWorldScreen.this.settings));
            PresetFlatWorldScreen.this.export.moveCursorToStart();
         }

         private void blitSlot(PoseStack var1, int var2, int var3, Item var4) {
            this.blitSlotBg(â˜ƒ, â˜ƒ + 1, â˜ƒ + 1);
            PresetFlatWorldScreen.this.itemRenderer.renderGuiItem(new ItemStack(â˜ƒ), â˜ƒ + 2, â˜ƒ + 2);
         }

         private void blitSlotBg(PoseStack var1, int var2, int var3) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, GuiComponent.STATS_ICON_LOCATION);
            GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, PresetFlatWorldScreen.this.getBlitOffset(), 0.0F, 0.0F, 18, 18, 128, 128);
         }

         @Override
         public Component getNarration() {
            return new TranslatableComponent("narrator.select", this.preset.getName());
         }
      }
   }
}
