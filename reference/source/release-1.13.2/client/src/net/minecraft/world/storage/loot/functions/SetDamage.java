package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetDamage extends LootFunction {
   private static final Logger field_186565_a = LogManager.getLogger();
   private final RandomValueRange field_186566_b;

   public SetDamage(LootCondition[] var1, RandomValueRange var2) {
      super(☃);
      this.field_186566_b = ☃;
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      if (☃.func_77984_f()) {
         float ☃ = 1.0F - this.field_186566_b.func_186507_b(☃);
         ☃.func_196085_b(MathHelper.func_76141_d(☃ * (float)☃.func_77958_k()));
      } else {
         field_186565_a.warn("Couldn't set damage of loot item {}", ☃);
      }

      return ☃;
   }

   public static class Serializer extends LootFunction.Serializer<SetDamage> {
      protected Serializer() {
         super(new ResourceLocation("set_damage"), SetDamage.class);
      }

      public void func_186532_a(JsonObject var1, SetDamage var2, JsonSerializationContext var3) {
         ☃.add("damage", ☃.serialize(☃.field_186566_b));
      }

      public SetDamage func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         return new SetDamage(☃, JsonUtils.func_188174_a(☃, "damage", ☃, RandomValueRange.class));
      }
   }
}
