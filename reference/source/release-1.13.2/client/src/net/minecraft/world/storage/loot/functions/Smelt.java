package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Smelt extends LootFunction {
   private static final Logger field_186574_a = LogManager.getLogger();

   public Smelt(LootCondition[] var1) {
      super(☃);
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      if (☃.func_190926_b()) {
         return ☃;
      } else {
         IRecipe ☃ = func_202880_a(☃, ☃);
         if (☃ != null) {
            ItemStack ☃x = ☃.func_77571_b();
            if (!☃x.func_190926_b()) {
               ItemStack ☃xx = ☃x.func_77946_l();
               ☃xx.func_190920_e(☃.func_190916_E());
               return ☃xx;
            }
         }

         field_186574_a.warn("Couldn't smelt {} because there is no smelting recipe", ☃);
         return ☃;
      }
   }

   @Nullable
   public static IRecipe func_202880_a(LootContext var0, ItemStack var1) {
      for(IRecipe ☃ : ☃.func_202879_g().func_199532_z().func_199510_b()) {
         if (☃ instanceof FurnaceRecipe && ☃.func_192400_c().get(0).test(☃)) {
            return ☃;
         }
      }

      return null;
   }

   public static class Serializer extends LootFunction.Serializer<Smelt> {
      protected Serializer() {
         super(new ResourceLocation("furnace_smelt"), Smelt.class);
      }

      public void func_186532_a(JsonObject var1, Smelt var2, JsonSerializationContext var3) {
      }

      public Smelt func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         return new Smelt(☃);
      }
   }
}
