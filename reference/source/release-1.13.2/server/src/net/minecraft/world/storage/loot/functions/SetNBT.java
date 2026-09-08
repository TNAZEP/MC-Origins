package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class SetNBT extends LootFunction {
   private final NBTTagCompound field_186570_a;

   public SetNBT(LootCondition[] var1, NBTTagCompound var2) {
      super(☃);
      this.field_186570_a = ☃;
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      ☃.func_196082_o().func_197643_a(this.field_186570_a);
      return ☃;
   }

   public static class Serializer extends LootFunction.Serializer<SetNBT> {
      public Serializer() {
         super(new ResourceLocation("set_nbt"), SetNBT.class);
      }

      public void func_186532_a(JsonObject var1, SetNBT var2, JsonSerializationContext var3) {
         ☃.addProperty("tag", ☃.field_186570_a.toString());
      }

      public SetNBT func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         try {
            NBTTagCompound ☃ = JsonToNBT.func_180713_a(JsonUtils.func_151200_h(☃, "tag"));
            return new SetNBT(☃, ☃);
         } catch (CommandSyntaxException var5) {
            throw new JsonSyntaxException(var5.getMessage());
         }
      }
   }
}
