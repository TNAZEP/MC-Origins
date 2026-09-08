package net.minecraft.client.renderer.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockModelShaper {
   private final Map<BlockState, BakedModel> modelByStateCache = Maps.<BlockState, BakedModel>newIdentityHashMap();
   private final ModelManager modelManager;

   public BlockModelShaper(ModelManager var1) {
      this.modelManager = â˜ƒ;
   }

   public TextureAtlasSprite getParticleIcon(BlockState var1) {
      return this.getBlockModel(â˜ƒ).getParticleIcon();
   }

   public BakedModel getBlockModel(BlockState var1) {
      BakedModel â˜ƒ = (BakedModel)this.modelByStateCache.get(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = this.modelManager.getMissingModel();
      }

      return â˜ƒ;
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   public void rebuildCache() {
      this.modelByStateCache.clear();

      for(Block â˜ƒ : Registry.BLOCK) {
         â˜ƒ.getStateDefinition().getPossibleStates().forEach(var1 -> this.modelByStateCache.put(var1, this.modelManager.getModel(stateToModelLocation(var1))));
      }
   }

   public static ModelResourceLocation stateToModelLocation(BlockState var0) {
      return stateToModelLocation(Registry.BLOCK.getKey(â˜ƒ.getBlock()), â˜ƒ);
   }

   public static ModelResourceLocation stateToModelLocation(ResourceLocation var0, BlockState var1) {
      return new ModelResourceLocation(â˜ƒ, statePropertiesToString(â˜ƒ.getValues()));
   }

   public static String statePropertiesToString(Map<Property<?>, Comparable<?>> var0) {
      StringBuilder â˜ƒ = new StringBuilder();

      for(Entry<Property<?>, Comparable<?>> â˜ƒx : â˜ƒ.entrySet()) {
         if (â˜ƒ.length() != 0) {
            â˜ƒ.append(',');
         }

         Property<?> â˜ƒxx = (Property)â˜ƒx.getKey();
         â˜ƒ.append(â˜ƒxx.getName());
         â˜ƒ.append('=');
         â˜ƒ.append(getValue(â˜ƒxx, (Comparable<?>)â˜ƒx.getValue()));
      }

      return â˜ƒ.toString();
   }

   private static <T extends Comparable<T>> String getValue(Property<T> var0, Comparable<?> var1) {
      return â˜ƒ.getName((T)â˜ƒ);
   }
}
