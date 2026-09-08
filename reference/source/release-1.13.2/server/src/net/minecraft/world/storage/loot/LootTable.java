package net.minecraft.world.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable {
   private static final Logger field_186465_b = LogManager.getLogger();
   public static final LootTable field_186464_a = new LootTable(new LootPool[0]);
   private final LootPool[] field_186466_c;

   public LootTable(LootPool[] var1) {
      this.field_186466_c = ☃;
   }

   public List<ItemStack> func_186462_a(Random var1, LootContext var2) {
      List<ItemStack> ☃ = Lists.<ItemStack>newArrayList();
      if (☃.func_186496_a(this)) {
         for(LootPool ☃x : this.field_186466_c) {
            ☃x.func_186449_b(☃, ☃, ☃);
         }

         ☃.func_186490_b(this);
      } else {
         field_186465_b.warn("Detected infinite loop in loot tables");
      }

      return ☃;
   }

   public void func_186460_a(IInventory var1, Random var2, LootContext var3) {
      List<ItemStack> ☃ = this.func_186462_a(☃, ☃);
      List<Integer> ☃x = this.func_186459_a(☃, ☃);
      this.func_186463_a(☃, ☃x.size(), ☃);

      for(ItemStack ☃xx : ☃) {
         if (☃x.isEmpty()) {
            field_186465_b.warn("Tried to over-fill a container");
            return;
         }

         if (☃xx.func_190926_b()) {
            ☃.func_70299_a(☃x.remove(☃x.size() - 1), ItemStack.field_190927_a);
         } else {
            ☃.func_70299_a(☃x.remove(☃x.size() - 1), ☃xx);
         }
      }
   }

   private void func_186463_a(List<ItemStack> var1, int var2, Random var3) {
      List<ItemStack> ☃ = Lists.<ItemStack>newArrayList();
      Iterator<ItemStack> ☃x = ☃.iterator();

      while(☃x.hasNext()) {
         ItemStack ☃xx = (ItemStack)☃x.next();
         if (☃xx.func_190926_b()) {
            ☃x.remove();
         } else if (☃xx.func_190916_E() > 1) {
            ☃.add(☃xx);
            ☃x.remove();
         }
      }

      while(☃ - ☃.size() - ☃.size() > 0 && !☃.isEmpty()) {
         ItemStack ☃xx = (ItemStack)☃.remove(MathHelper.func_76136_a(☃, 0, ☃.size() - 1));
         int ☃xxx = MathHelper.func_76136_a(☃, 1, ☃xx.func_190916_E() / 2);
         ItemStack ☃xxxx = ☃xx.func_77979_a(☃xxx);
         if (☃xx.func_190916_E() > 1 && ☃.nextBoolean()) {
            ☃.add(☃xx);
         } else {
            ☃.add(☃xx);
         }

         if (☃xxxx.func_190916_E() > 1 && ☃.nextBoolean()) {
            ☃.add(☃xxxx);
         } else {
            ☃.add(☃xxxx);
         }
      }

      ☃.addAll(☃);
      Collections.shuffle(☃, ☃);
   }

   private List<Integer> func_186459_a(IInventory var1, Random var2) {
      List<Integer> ☃ = Lists.newArrayList();

      for(int ☃x = 0; ☃x < ☃.func_70302_i_(); ++☃x) {
         if (☃.func_70301_a(☃x).func_190926_b()) {
            ☃.add(☃x);
         }
      }

      Collections.shuffle(☃, ☃);
      return ☃;
   }

   public static class Serializer implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
      public LootTable deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "loot table");
         LootPool[] ☃x = (LootPool[])JsonUtils.func_188177_a(☃, "pools", new LootPool[0], ☃, LootPool[].class);
         return new LootTable(☃x);
      }

      public JsonElement serialize(LootTable var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         ☃.add("pools", ☃.serialize(☃.field_186466_c));
         return ☃;
      }
   }
}
