package net.minecraft.world.storage.loot.functions;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnchantRandomly extends LootFunction {
   private static final Logger field_186557_a = LogManager.getLogger();
   private final List<Enchantment> field_186558_b;

   public EnchantRandomly(LootCondition[] var1, @Nullable List<Enchantment> var2) {
      super(☃);
      this.field_186558_b = ☃ == null ? Collections.emptyList() : ☃;
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      Enchantment ☃;
      if (this.field_186558_b.isEmpty()) {
         List<Enchantment> ☃x = Lists.<Enchantment>newArrayList();

         for(Enchantment ☃xx : IRegistry.field_212628_q) {
            if (☃.func_77973_b() == Items.field_151122_aG || ☃xx.func_92089_a(☃)) {
               ☃x.add(☃xx);
            }
         }

         if (☃x.isEmpty()) {
            field_186557_a.warn("Couldn't find a compatible enchantment for {}", ☃);
            return ☃;
         }

         ☃ = (Enchantment)☃x.get(☃.nextInt(☃x.size()));
      } else {
         ☃ = (Enchantment)this.field_186558_b.get(☃.nextInt(this.field_186558_b.size()));
      }

      int ☃ = MathHelper.func_76136_a(☃, ☃.func_77319_d(), ☃.func_77325_b());
      if (☃.func_77973_b() == Items.field_151122_aG) {
         ☃ = new ItemStack(Items.field_151134_bR);
         ItemEnchantedBook.func_92115_a(☃, new EnchantmentData(☃, ☃));
      } else {
         ☃.func_77966_a(☃, ☃);
      }

      return ☃;
   }

   public static class Serializer extends LootFunction.Serializer<EnchantRandomly> {
      public Serializer() {
         super(new ResourceLocation("enchant_randomly"), EnchantRandomly.class);
      }

      public void func_186532_a(JsonObject var1, EnchantRandomly var2, JsonSerializationContext var3) {
         if (!☃.field_186558_b.isEmpty()) {
            JsonArray ☃ = new JsonArray();

            for(Enchantment ☃x : ☃.field_186558_b) {
               ResourceLocation ☃xx = IRegistry.field_212628_q.func_177774_c(☃x);
               if (☃xx == null) {
                  throw new IllegalArgumentException("Don't know how to serialize enchantment " + ☃x);
               }

               ☃.add(new JsonPrimitive(☃xx.toString()));
            }

            ☃.add("enchantments", ☃);
         }
      }

      public EnchantRandomly func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         List<Enchantment> ☃ = Lists.<Enchantment>newArrayList();
         if (☃.has("enchantments")) {
            for(JsonElement ☃x : JsonUtils.func_151214_t(☃, "enchantments")) {
               String ☃xx = JsonUtils.func_151206_a(☃x, "enchantment");
               Enchantment ☃xxx = IRegistry.field_212628_q.func_212608_b(new ResourceLocation(☃xx));
               if (☃xxx == null) {
                  throw new JsonSyntaxException("Unknown enchantment '" + ☃xx + "'");
               }

               ☃.add(☃xxx);
            }
         }

         return new EnchantRandomly(☃, ☃);
      }
   }
}
