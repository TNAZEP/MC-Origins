package net.minecraft.world.item;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.Mth;

public class ItemCooldowns {
   private final Map<Item, ItemCooldowns.CooldownInstance> cooldowns = Maps.<Item, ItemCooldowns.CooldownInstance>newHashMap();
   private int tickCount;

   public boolean isOnCooldown(Item var1) {
      return this.getCooldownPercent(â˜ƒ, 0.0F) > 0.0F;
   }

   public float getCooldownPercent(Item var1, float var2) {
      ItemCooldowns.CooldownInstance â˜ƒ = (ItemCooldowns.CooldownInstance)this.cooldowns.get(â˜ƒ);
      if (â˜ƒ != null) {
         float â˜ƒx = (float)(â˜ƒ.endTime - â˜ƒ.startTime);
         float â˜ƒxx = (float)â˜ƒ.endTime - ((float)this.tickCount + â˜ƒ);
         return Mth.clamp(â˜ƒxx / â˜ƒx, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   public void tick() {
      ++this.tickCount;
      if (!this.cooldowns.isEmpty()) {
         Iterator<Entry<Item, ItemCooldowns.CooldownInstance>> â˜ƒ = this.cooldowns.entrySet().iterator();

         while(â˜ƒ.hasNext()) {
            Entry<Item, ItemCooldowns.CooldownInstance> â˜ƒx = (Entry)â˜ƒ.next();
            if (((ItemCooldowns.CooldownInstance)â˜ƒx.getValue()).endTime <= this.tickCount) {
               â˜ƒ.remove();
               this.onCooldownEnded((Item)â˜ƒx.getKey());
            }
         }
      }
   }

   public void addCooldown(Item var1, int var2) {
      this.cooldowns.put(â˜ƒ, new ItemCooldowns.CooldownInstance(this.tickCount, this.tickCount + â˜ƒ));
      this.onCooldownStarted(â˜ƒ, â˜ƒ);
   }

   public void removeCooldown(Item var1) {
      this.cooldowns.remove(â˜ƒ);
      this.onCooldownEnded(â˜ƒ);
   }

   protected void onCooldownStarted(Item var1, int var2) {
   }

   protected void onCooldownEnded(Item var1) {
   }

   class CooldownInstance {
      final int startTime;
      final int endTime;

      CooldownInstance(int var2, int var3) {
         this.startTime = â˜ƒ;
         this.endTime = â˜ƒ;
      }
   }
}
