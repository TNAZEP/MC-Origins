package net.minecraft.client.resources.model;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.texture.AtlasSet;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class ModelManager extends SimplePreparableReloadListener<ModelBakery> implements AutoCloseable {
   private Map<ResourceLocation, BakedModel> bakedRegistry;
   @Nullable
   private AtlasSet atlases;
   private final BlockModelShaper blockModelShaper;
   private final TextureManager textureManager;
   private final BlockColors blockColors;
   private int maxMipmapLevels;
   private BakedModel missingModel;
   private Object2IntMap<BlockState> modelGroups;

   public ModelManager(TextureManager var1, BlockColors var2, int var3) {
      this.textureManager = â˜ƒ;
      this.blockColors = â˜ƒ;
      this.maxMipmapLevels = â˜ƒ;
      this.blockModelShaper = new BlockModelShaper(this);
   }

   public BakedModel getModel(ModelResourceLocation var1) {
      return (BakedModel)this.bakedRegistry.getOrDefault(â˜ƒ, this.missingModel);
   }

   public BakedModel getMissingModel() {
      return this.missingModel;
   }

   public BlockModelShaper getBlockModelShaper() {
      return this.blockModelShaper;
   }

   protected ModelBakery prepare(ResourceManager var1, ProfilerFiller var2) {
      â˜ƒ.startTick();
      ModelBakery â˜ƒ = new ModelBakery(â˜ƒ, this.blockColors, â˜ƒ, this.maxMipmapLevels);
      â˜ƒ.endTick();
      return â˜ƒ;
   }

   protected void apply(ModelBakery var1, ResourceManager var2, ProfilerFiller var3) {
      â˜ƒ.startTick();
      â˜ƒ.push("upload");
      if (this.atlases != null) {
         this.atlases.close();
      }

      this.atlases = â˜ƒ.uploadTextures(this.textureManager, â˜ƒ);
      this.bakedRegistry = â˜ƒ.getBakedTopLevelModels();
      this.modelGroups = â˜ƒ.getModelGroups();
      this.missingModel = (BakedModel)this.bakedRegistry.get(ModelBakery.MISSING_MODEL_LOCATION);
      â˜ƒ.popPush("cache");
      this.blockModelShaper.rebuildCache();
      â˜ƒ.pop();
      â˜ƒ.endTick();
   }

   public boolean requiresRender(BlockState var1, BlockState var2) {
      if (â˜ƒ == â˜ƒ) {
         return false;
      } else {
         int â˜ƒ = this.modelGroups.getInt(â˜ƒ);
         if (â˜ƒ != -1) {
            int â˜ƒx = this.modelGroups.getInt(â˜ƒ);
            if (â˜ƒ == â˜ƒx) {
               FluidState â˜ƒxx = â˜ƒ.getFluidState();
               FluidState â˜ƒxxx = â˜ƒ.getFluidState();
               return â˜ƒxx != â˜ƒxxx;
            }
         }

         return true;
      }
   }

   public TextureAtlas getAtlas(ResourceLocation var1) {
      return this.atlases.getAtlas(â˜ƒ);
   }

   public void close() {
      if (this.atlases != null) {
         this.atlases.close();
      }
   }

   public void updateMaxMipLevel(int var1) {
      this.maxMipmapLevels = â˜ƒ;
   }
}
