package net.minecraft.world.gen;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.world.gen.area.AreaDimension;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.traits.IPixelTransformer;

public class LazyAreaLayerContext extends LayerContext<LazyArea> {
   private final Long2IntLinkedOpenHashMap field_202703_b = new Long2IntLinkedOpenHashMap(16, 0.25F);
   private final int field_202704_c;
   private final int field_202705_d;

   public LazyAreaLayerContext(int var1, int var2, long var3, long var5) {
      super(☃);
      this.field_202703_b.defaultReturnValue(Integer.MIN_VALUE);
      this.field_202704_c = ☃;
      this.field_202705_d = ☃;
      this.func_202699_a(☃);
   }

   public LazyArea func_201490_a_(AreaDimension var1, IPixelTransformer var2) {
      return new LazyArea(this.field_202703_b, this.field_202704_c, ☃, ☃);
   }

   public LazyArea func_201489_a_(AreaDimension var1, IPixelTransformer var2, LazyArea var3) {
      return new LazyArea(this.field_202703_b, Math.min(256, ☃.func_202680_a() * 4), ☃, ☃);
   }

   public LazyArea func_201488_a_(AreaDimension var1, IPixelTransformer var2, LazyArea var3, LazyArea var4) {
      return new LazyArea(this.field_202703_b, Math.min(256, Math.max(☃.func_202680_a(), ☃.func_202680_a()) * 4), ☃, ☃);
   }
}
