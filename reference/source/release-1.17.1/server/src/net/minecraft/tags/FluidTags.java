package net.minecraft.tags;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.Registry;
import net.minecraft.world.level.material.Fluid;

public final class FluidTags {
   protected static final StaticTagHelper<Fluid> HELPER = StaticTags.create(Registry.FLUID_REGISTRY, "tags/fluids");
   private static final List<Tag<Fluid>> KNOWN_TAGS = Lists.<Tag<Fluid>>newArrayList();
   public static final Tag.Named<Fluid> WATER = bind("water");
   public static final Tag.Named<Fluid> LAVA = bind("lava");

   private FluidTags() {
   }

   private static Tag.Named<Fluid> bind(String var0) {
      Tag.Named<Fluid> â˜ƒ = HELPER.bind(â˜ƒ);
      KNOWN_TAGS.add(â˜ƒ);
      return â˜ƒ;
   }

   public static TagCollection<Fluid> getAllTags() {
      return HELPER.getAllTags();
   }

   @Deprecated
   public static List<Tag<Fluid>> getStaticTags() {
      return KNOWN_TAGS;
   }
}
