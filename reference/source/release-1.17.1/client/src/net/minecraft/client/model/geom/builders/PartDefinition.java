package net.minecraft.client.model.geom.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

public class PartDefinition {
   private final List<CubeDefinition> cubes;
   private final PartPose partPose;
   private final Map<String, PartDefinition> children = Maps.newHashMap();

   PartDefinition(List<CubeDefinition> var1, PartPose var2) {
      this.cubes = â˜ƒ;
      this.partPose = â˜ƒ;
   }

   public PartDefinition addOrReplaceChild(String var1, CubeListBuilder var2, PartPose var3) {
      PartDefinition â˜ƒ = new PartDefinition(â˜ƒ.getCubes(), â˜ƒ);
      PartDefinition â˜ƒx = (PartDefinition)this.children.put(â˜ƒ, â˜ƒ);
      if (â˜ƒx != null) {
         â˜ƒ.children.putAll(â˜ƒx.children);
      }

      return â˜ƒ;
   }

   public ModelPart bake(int var1, int var2) {
      Object2ObjectArrayMap<String, ModelPart> â˜ƒ = (Object2ObjectArrayMap)this.children
         .entrySet()
         .stream()
         .collect(
            Collectors.toMap(Entry::getKey, var2x -> ((PartDefinition)var2x.getValue()).bake(â˜ƒ, â˜ƒ), (var0, var1x) -> var0, Object2ObjectArrayMap::new)
         );
      List<ModelPart.Cube> â˜ƒx = (List)this.cubes.stream().map(var2x -> var2x.bake(â˜ƒ, â˜ƒ)).collect(ImmutableList.toImmutableList());
      ModelPart â˜ƒxx = new ModelPart(â˜ƒx, â˜ƒ);
      â˜ƒxx.loadPose(this.partPose);
      return â˜ƒxx;
   }

   public PartDefinition getChild(String var1) {
      return (PartDefinition)this.children.get(â˜ƒ);
   }
}
