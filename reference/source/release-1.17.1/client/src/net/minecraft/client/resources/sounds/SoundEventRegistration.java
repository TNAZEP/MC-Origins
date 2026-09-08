package net.minecraft.client.resources.sounds;

import java.util.List;
import javax.annotation.Nullable;

public class SoundEventRegistration {
   private final List<Sound> sounds;
   private final boolean replace;
   private final String subtitle;

   public SoundEventRegistration(List<Sound> var1, boolean var2, String var3) {
      this.sounds = â˜ƒ;
      this.replace = â˜ƒ;
      this.subtitle = â˜ƒ;
   }

   public List<Sound> getSounds() {
      return this.sounds;
   }

   public boolean isReplace() {
      return this.replace;
   }

   @Nullable
   public String getSubtitle() {
      return this.subtitle;
   }
}
