package net.minecraft.client.model.geom;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class EntityModelSet implements ResourceManagerReloadListener {
   private Map<ModelLayerLocation, LayerDefinition> roots = ImmutableMap.of();

   public ModelPart bakeLayer(ModelLayerLocation var1) {
      LayerDefinition â˜ƒ = (LayerDefinition)this.roots.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("No model for layer " + â˜ƒ);
      } else {
         return â˜ƒ.bakeRoot();
      }
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      this.roots = ImmutableMap.copyOf(LayerDefinitions.createRoots());
   }
}
