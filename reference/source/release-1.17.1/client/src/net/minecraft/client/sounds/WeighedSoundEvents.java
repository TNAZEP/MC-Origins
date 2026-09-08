package net.minecraft.client.sounds;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class WeighedSoundEvents implements Weighted<Sound> {
   private final List<Weighted<Sound>> list = Lists.<Weighted<Sound>>newArrayList();
   private final Random random = new Random();
   private final ResourceLocation location;
   @Nullable
   private final Component subtitle;

   public WeighedSoundEvents(ResourceLocation var1, @Nullable String var2) {
      this.location = â˜ƒ;
      this.subtitle = â˜ƒ == null ? null : new TranslatableComponent(â˜ƒ);
   }

   @Override
   public int getWeight() {
      int â˜ƒ = 0;

      for(Weighted<Sound> â˜ƒx : this.list) {
         â˜ƒ += â˜ƒx.getWeight();
      }

      return â˜ƒ;
   }

   public Sound getSound() {
      int â˜ƒ = this.getWeight();
      if (!this.list.isEmpty() && â˜ƒ != 0) {
         int â˜ƒx = this.random.nextInt(â˜ƒ);

         for(Weighted<Sound> â˜ƒxx : this.list) {
            â˜ƒx -= â˜ƒxx.getWeight();
            if (â˜ƒx < 0) {
               return â˜ƒxx.getSound();
            }
         }

         return SoundManager.EMPTY_SOUND;
      } else {
         return SoundManager.EMPTY_SOUND;
      }
   }

   public void addSound(Weighted<Sound> var1) {
      this.list.add(â˜ƒ);
   }

   public ResourceLocation getResourceLocation() {
      return this.location;
   }

   @Nullable
   public Component getSubtitle() {
      return this.subtitle;
   }

   @Override
   public void preloadIfRequired(SoundEngine var1) {
      for(Weighted<Sound> â˜ƒ : this.list) {
         â˜ƒ.preloadIfRequired(â˜ƒ);
      }
   }
}
