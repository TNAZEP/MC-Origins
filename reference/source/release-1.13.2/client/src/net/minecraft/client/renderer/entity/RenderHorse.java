package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.ModelHorseArmorBase;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;

public class RenderHorse extends RenderAbstractHorse<EntityHorse> {
   private static final Map<String, ResourceLocation> field_110852_a = Maps.newHashMap();

   public RenderHorse(RenderManager var1) {
      super(☃, new ModelHorseArmorBase(), 1.1F);
   }

   protected ResourceLocation func_110775_a(AbstractHorse var1) {
      EntityHorse ☃ = (EntityHorse)☃;
      String ☃x = ☃.func_110264_co();
      ResourceLocation ☃xx = (ResourceLocation)field_110852_a.get(☃x);
      if (☃xx == null) {
         ☃xx = new ResourceLocation(☃x);
         Minecraft.func_71410_x().func_110434_K().func_110579_a(☃xx, new LayeredTexture(☃.func_110212_cp()));
         field_110852_a.put(☃x, ☃xx);
      }

      return ☃xx;
   }
}
