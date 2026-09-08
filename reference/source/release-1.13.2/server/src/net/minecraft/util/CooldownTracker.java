package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class CooldownTracker {
   private final Map<Item, CooldownTracker.Cooldown> field_185147_a = Maps.<Item, CooldownTracker.Cooldown>newHashMap();
   private int field_185148_b;

   public boolean func_185141_a(Item var1) {
      return this.func_185143_a(☃, 0.0F) > 0.0F;
   }

   public float func_185143_a(Item var1, float var2) {
      CooldownTracker.Cooldown ☃ = (CooldownTracker.Cooldown)this.field_185147_a.get(☃);
      if (☃ != null) {
         float ☃x = (float)(☃.field_185138_b - ☃.field_185137_a);
         float ☃xx = (float)☃.field_185138_b - ((float)this.field_185148_b + ☃);
         return MathHelper.func_76131_a(☃xx / ☃x, 0.0F, 1.0F);
      } else {
         return 0.0F;
      }
   }

   public void func_185144_a() {
      ++this.field_185148_b;
      if (!this.field_185147_a.isEmpty()) {
         Iterator<Entry<Item, CooldownTracker.Cooldown>> ☃ = this.field_185147_a.entrySet().iterator();

         while(☃.hasNext()) {
            Entry<Item, CooldownTracker.Cooldown> ☃x = (Entry)☃.next();
            if (((CooldownTracker.Cooldown)☃x.getValue()).field_185138_b <= this.field_185148_b) {
               ☃.remove();
               this.func_185146_c((Item)☃x.getKey());
            }
         }
      }
   }

   public void func_185145_a(Item var1, int var2) {
      this.field_185147_a.put(☃, new CooldownTracker.Cooldown(this.field_185148_b, this.field_185148_b + ☃));
      this.func_185140_b(☃, ☃);
   }

   protected void func_185140_b(Item var1, int var2) {
   }

   protected void func_185146_c(Item var1) {
   }

   class Cooldown {
      private final int field_185137_a;
      private final int field_185138_b;

      private Cooldown(int var2, int var3) {
         this.field_185137_a = ☃;
         this.field_185138_b = ☃;
      }
   }
}
