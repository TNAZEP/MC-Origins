package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class ChanneledLightningTrigger implements ICriterionTrigger<ChanneledLightningTrigger.Instance> {
   private static final ResourceLocation field_204815_a = new ResourceLocation("channeled_lightning");
   private final Map<PlayerAdvancements, ChanneledLightningTrigger.Listeners> field_204816_b = Maps.<PlayerAdvancements, ChanneledLightningTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_204815_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance> var2) {
      ChanneledLightningTrigger.Listeners ☃ = (ChanneledLightningTrigger.Listeners)this.field_204816_b.get(☃);
      if (☃ == null) {
         ☃ = new ChanneledLightningTrigger.Listeners(☃);
         this.field_204816_b.put(☃, ☃);
      }

      ☃.func_204843_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance> var2) {
      ChanneledLightningTrigger.Listeners ☃ = (ChanneledLightningTrigger.Listeners)this.field_204816_b.get(☃);
      if (☃ != null) {
         ☃.func_204845_b(☃);
         if (☃.func_204844_a()) {
            this.field_204816_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_204816_b.remove(☃);
   }

   public ChanneledLightningTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      EntityPredicate[] ☃ = EntityPredicate.func_204849_b(☃.get("victims"));
      return new ChanneledLightningTrigger.Instance(☃);
   }

   public void func_204814_a(EntityPlayerMP var1, Collection<? extends Entity> var2) {
      ChanneledLightningTrigger.Listeners ☃ = (ChanneledLightningTrigger.Listeners)this.field_204816_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_204846_a(☃, ☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final EntityPredicate[] field_204825_a;

      public Instance(EntityPredicate[] var1) {
         super(ChanneledLightningTrigger.field_204815_a);
         this.field_204825_a = ☃;
      }

      public static ChanneledLightningTrigger.Instance func_204824_a(EntityPredicate... var0) {
         return new ChanneledLightningTrigger.Instance(☃);
      }

      public boolean func_204823_a(EntityPlayerMP var1, Collection<? extends Entity> var2) {
         for(EntityPredicate ☃ : this.field_204825_a) {
            boolean ☃x = false;

            for(Entity ☃xx : ☃) {
               if (☃.func_192482_a(☃, ☃xx)) {
                  ☃x = true;
                  break;
               }
            }

            if (!☃x) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.add("victims", EntityPredicate.func_204850_a(this.field_204825_a));
         return ☃;
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_204847_a;
      private final Set<ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance>> field_204848_b = Sets.<ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_204847_a = ☃;
      }

      public boolean func_204844_a() {
         return this.field_204848_b.isEmpty();
      }

      public void func_204843_a(ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance> var1) {
         this.field_204848_b.add(☃);
      }

      public void func_204845_b(ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance> var1) {
         this.field_204848_b.remove(☃);
      }

      public void func_204846_a(EntityPlayerMP var1, Collection<? extends Entity> var2) {
         List<ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance> ☃x : this.field_204848_b) {
            if (☃x.func_192158_a().func_204823_a(☃, ☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<ChanneledLightningTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_204847_a);
            }
         }
      }
   }
}
