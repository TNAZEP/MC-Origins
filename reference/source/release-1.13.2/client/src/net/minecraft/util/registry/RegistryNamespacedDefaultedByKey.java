package net.minecraft.util.registry;

import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;

public class RegistryNamespacedDefaultedByKey<V> extends RegistryNamespaced<V> {
   private final ResourceLocation field_148760_d;
   private V field_148761_e;

   public RegistryNamespacedDefaultedByKey(ResourceLocation var1) {
      this.field_148760_d = ☃;
   }

   @Override
   public void func_177775_a(int var1, ResourceLocation var2, V var3) {
      if (this.field_148760_d.equals(☃)) {
         this.field_148761_e = ☃;
      }

      super.func_177775_a(☃, ☃, ☃);
   }

   @Override
   public int func_148757_b(@Nullable V var1) {
      int ☃ = super.func_148757_b(☃);
      return ☃ == -1 ? super.func_148757_b(this.field_148761_e) : ☃;
   }

   @Override
   public ResourceLocation func_177774_c(V var1) {
      ResourceLocation ☃ = super.func_177774_c(☃);
      return ☃ == null ? this.field_148760_d : ☃;
   }

   @Override
   public V func_82594_a(@Nullable ResourceLocation var1) {
      V ☃ = this.func_212608_b(☃);
      return (V)(☃ == null ? this.field_148761_e : ☃);
   }

   @Nonnull
   @Override
   public V func_148754_a(int var1) {
      V ☃ = super.func_148754_a(☃);
      return (V)(☃ == null ? this.field_148761_e : ☃);
   }

   @Nonnull
   @Override
   public V func_186801_a(Random var1) {
      V ☃ = super.func_186801_a(☃);
      return (V)(☃ == null ? this.field_148761_e : ☃);
   }

   @Override
   public ResourceLocation func_212609_b() {
      return this.field_148760_d;
   }
}
