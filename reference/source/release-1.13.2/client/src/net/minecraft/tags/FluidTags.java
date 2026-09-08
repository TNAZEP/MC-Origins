package net.minecraft.tags;

import java.util.Collection;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;

public class FluidTags {
   private static TagCollection<Fluid> field_206961_c = new TagCollection<>(var0 -> false, var0 -> null, "", false, "");
   private static int field_206962_d;
   public static final Tag<Fluid> field_206959_a = func_206956_a("water");
   public static final Tag<Fluid> field_206960_b = func_206956_a("lava");

   public static void func_206953_a(TagCollection<Fluid> var0) {
      field_206961_c = ☃;
      ++field_206962_d;
   }

   private static Tag<Fluid> func_206956_a(String var0) {
      return new FluidTags.Wrapper(new ResourceLocation(☃));
   }

   public static class Wrapper extends Tag<Fluid> {
      private int field_206950_a = -1;
      private Tag<Fluid> field_206951_b;

      public Wrapper(ResourceLocation var1) {
         super(☃);
      }

      public boolean func_199685_a_(Fluid var1) {
         if (this.field_206950_a != FluidTags.field_206962_d) {
            this.field_206951_b = FluidTags.field_206961_c.func_199915_b(this.func_199886_b());
            this.field_206950_a = FluidTags.field_206962_d;
         }

         return this.field_206951_b.func_199685_a_(☃);
      }

      @Override
      public Collection<Fluid> func_199885_a() {
         if (this.field_206950_a != FluidTags.field_206962_d) {
            this.field_206951_b = FluidTags.field_206961_c.func_199915_b(this.func_199886_b());
            this.field_206950_a = FluidTags.field_206962_d;
         }

         return this.field_206951_b.func_199885_a();
      }

      @Override
      public Collection<Tag.ITagEntry<Fluid>> func_200570_b() {
         if (this.field_206950_a != FluidTags.field_206962_d) {
            this.field_206951_b = FluidTags.field_206961_c.func_199915_b(this.func_199886_b());
            this.field_206950_a = FluidTags.field_206962_d;
         }

         return this.field_206951_b.func_200570_b();
      }
   }
}
