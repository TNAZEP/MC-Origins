package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

public class AnimationMetadataSection {
   public static final AnimationMetadataSectionSerializer field_195817_a = new AnimationMetadataSectionSerializer();
   private final List<AnimationFrame> field_110478_a;
   private final int field_110476_b;
   private final int field_110477_c;
   private final int field_110475_d;
   private final boolean field_177220_e;

   public AnimationMetadataSection(List<AnimationFrame> var1, int var2, int var3, int var4, boolean var5) {
      this.field_110478_a = ☃;
      this.field_110476_b = ☃;
      this.field_110477_c = ☃;
      this.field_110475_d = ☃;
      this.field_177220_e = ☃;
   }

   public int func_110471_a() {
      return this.field_110477_c;
   }

   public int func_110474_b() {
      return this.field_110476_b;
   }

   public int func_110473_c() {
      return this.field_110478_a.size();
   }

   public int func_110469_d() {
      return this.field_110475_d;
   }

   public boolean func_177219_e() {
      return this.field_177220_e;
   }

   private AnimationFrame func_130072_d(int var1) {
      return (AnimationFrame)this.field_110478_a.get(☃);
   }

   public int func_110472_a(int var1) {
      AnimationFrame ☃ = this.func_130072_d(☃);
      return ☃.func_110495_a() ? this.field_110475_d : ☃.func_110497_b();
   }

   public int func_110468_c(int var1) {
      return ((AnimationFrame)this.field_110478_a.get(☃)).func_110496_c();
   }

   public Set<Integer> func_130073_e() {
      Set<Integer> ☃ = Sets.newHashSet();

      for(AnimationFrame ☃x : this.field_110478_a) {
         ☃.add(☃x.func_110496_c());
      }

      return ☃;
   }
}
