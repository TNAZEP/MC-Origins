package net.minecraft.tags;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class NetworkTagCollection<T> extends TagCollection<T> {
   private final IRegistry<T> field_200044_a;

   public NetworkTagCollection(IRegistry<T> var1, String var2, String var3) {
      super(☃::func_212607_c, ☃::func_212608_b, ☃, false, ☃);
      this.field_200044_a = ☃;
   }

   public void func_200042_a(PacketBuffer var1) {
      ☃.func_150787_b(this.func_200039_c().size());

      for(Entry<ResourceLocation, Tag<T>> ☃ : this.func_200039_c().entrySet()) {
         ☃.func_192572_a((ResourceLocation)☃.getKey());
         ☃.func_150787_b(((Tag)☃.getValue()).func_199885_a().size());

         for(T ☃x : ((Tag)☃.getValue()).func_199885_a()) {
            ☃.func_150787_b(this.field_200044_a.func_148757_b(☃x));
         }
      }
   }

   public void func_200043_b(PacketBuffer var1) {
      int ☃ = ☃.func_150792_a();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         ResourceLocation ☃xx = ☃.func_192575_l();
         int ☃xxx = ☃.func_150792_a();
         List<T> ☃xxxx = Lists.<T>newArrayList();

         for(int ☃xxxxx = 0; ☃xxxxx < ☃xxx; ++☃xxxxx) {
            ☃xxxx.add(this.field_200044_a.func_148754_a(☃.func_150792_a()));
         }

         this.func_200039_c().put(☃xx, Tag.Builder.func_200047_a().func_200046_a(☃xxxx).func_200051_a(☃xx));
      }
   }
}
