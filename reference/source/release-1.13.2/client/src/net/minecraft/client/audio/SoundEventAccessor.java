package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class SoundEventAccessor implements ISoundEventAccessor<Sound> {
   private final List<ISoundEventAccessor<Sound>> field_188716_a = Lists.<ISoundEventAccessor<Sound>>newArrayList();
   private final Random field_148734_b = new Random();
   private final ResourceLocation field_188717_c;
   private final ITextComponent field_188718_d;

   public SoundEventAccessor(ResourceLocation var1, @Nullable String var2) {
      this.field_188717_c = ☃;
      this.field_188718_d = ☃ == null ? null : new TextComponentTranslation(☃);
   }

   @Override
   public int func_148721_a() {
      int ☃ = 0;

      for(ISoundEventAccessor<Sound> ☃x : this.field_188716_a) {
         ☃ += ☃x.func_148721_a();
      }

      return ☃;
   }

   public Sound func_148720_g() {
      int ☃ = this.func_148721_a();
      if (!this.field_188716_a.isEmpty() && ☃ != 0) {
         int ☃x = this.field_148734_b.nextInt(☃);

         for(ISoundEventAccessor<Sound> ☃xx : this.field_188716_a) {
            ☃x -= ☃xx.func_148721_a();
            if (☃x < 0) {
               return ☃xx.func_148720_g();
            }
         }

         return SoundHandler.field_147700_a;
      } else {
         return SoundHandler.field_147700_a;
      }
   }

   public void func_188715_a(ISoundEventAccessor<Sound> var1) {
      this.field_188716_a.add(☃);
   }

   @Nullable
   public ITextComponent func_188712_c() {
      return this.field_188718_d;
   }
}
