package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.FunctionObject;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.LootContext;

public class AdvancementRewards {
   public static final AdvancementRewards field_192114_a = new AdvancementRewards(
      0, new ResourceLocation[0], new ResourceLocation[0], FunctionObject.CacheableFunction.field_193519_a
   );
   private final int field_192115_b;
   private final ResourceLocation[] field_192116_c;
   private final ResourceLocation[] field_192117_d;
   private final FunctionObject.CacheableFunction field_193129_e;

   public AdvancementRewards(int var1, ResourceLocation[] var2, ResourceLocation[] var3, FunctionObject.CacheableFunction var4) {
      this.field_192115_b = ☃;
      this.field_192116_c = ☃;
      this.field_192117_d = ☃;
      this.field_193129_e = ☃;
   }

   public void func_192113_a(EntityPlayerMP var1) {
      ☃.func_195068_e(this.field_192115_b);
      LootContext ☃ = new LootContext.Builder(☃.func_71121_q()).func_186472_a(☃).func_204313_a(new BlockPos(☃)).func_186471_a();
      boolean ☃x = false;

      for(ResourceLocation ☃xx : this.field_192116_c) {
         for(ItemStack ☃xxx : ☃.field_71133_b.func_200249_aQ().func_186521_a(☃xx).func_186462_a(☃.func_70681_au(), ☃)) {
            if (☃.func_191521_c(☃xxx)) {
               ☃.field_70170_p
                  .func_184148_a(
                     null,
                     ☃.field_70165_t,
                     ☃.field_70163_u,
                     ☃.field_70161_v,
                     SoundEvents.field_187638_cR,
                     SoundCategory.PLAYERS,
                     0.2F,
                     ((☃.func_70681_au().nextFloat() - ☃.func_70681_au().nextFloat()) * 0.7F + 1.0F) * 2.0F
                  );
               ☃x = true;
            } else {
               EntityItem ☃xxxx = ☃.func_71019_a(☃xxx, false);
               if (☃xxxx != null) {
                  ☃xxxx.func_174868_q();
                  ☃xxxx.func_200217_b(☃.func_110124_au());
               }
            }
         }
      }

      if (☃x) {
         ☃.field_71069_bz.func_75142_b();
      }

      if (this.field_192117_d.length > 0) {
         ☃.func_193102_a(this.field_192117_d);
      }

      MinecraftServer ☃xx = ☃.field_71133_b;
      FunctionObject ☃xxx = this.field_193129_e.func_193518_a(☃xx.func_193030_aL());
      if (☃xxx != null) {
         ☃xx.func_193030_aL().func_195447_a(☃xxx, ☃.func_195051_bN().func_197031_a().func_197033_a(2));
      }
   }

   public String toString() {
      return "AdvancementRewards{experience="
         + this.field_192115_b
         + ", loot="
         + Arrays.toString(this.field_192116_c)
         + ", recipes="
         + Arrays.toString(this.field_192117_d)
         + ", function="
         + this.field_193129_e
         + '}';
   }

   public JsonElement func_200286_b() {
      if (this == field_192114_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject ☃ = new JsonObject();
         if (this.field_192115_b != 0) {
            ☃.addProperty("experience", this.field_192115_b);
         }

         if (this.field_192116_c.length > 0) {
            JsonArray ☃ = new JsonArray();

            for(ResourceLocation ☃x : this.field_192116_c) {
               ☃.add(☃x.toString());
            }

            ☃.add("loot", ☃);
         }

         if (this.field_192117_d.length > 0) {
            JsonArray ☃ = new JsonArray();

            for(ResourceLocation ☃x : this.field_192117_d) {
               ☃.add(☃x.toString());
            }

            ☃.add("recipes", ☃);
         }

         if (this.field_193129_e.func_200376_a() != null) {
            ☃.addProperty("function", this.field_193129_e.func_200376_a().toString());
         }

         return ☃;
      }
   }

   public static class Builder {
      private int field_200282_a;
      private final List<ResourceLocation> field_200283_b = Lists.<ResourceLocation>newArrayList();
      private final List<ResourceLocation> field_200284_c = Lists.<ResourceLocation>newArrayList();
      @Nullable
      private ResourceLocation field_200285_d;

      public static AdvancementRewards.Builder func_203907_a(int var0) {
         return new AdvancementRewards.Builder().func_203906_b(☃);
      }

      public AdvancementRewards.Builder func_203906_b(int var1) {
         this.field_200282_a += ☃;
         return this;
      }

      public static AdvancementRewards.Builder func_200280_c(ResourceLocation var0) {
         return new AdvancementRewards.Builder().func_200279_d(☃);
      }

      public AdvancementRewards.Builder func_200279_d(ResourceLocation var1) {
         this.field_200284_c.add(☃);
         return this;
      }

      public AdvancementRewards func_200281_a() {
         return new AdvancementRewards(
            this.field_200282_a,
            (ResourceLocation[])this.field_200283_b.toArray(new ResourceLocation[0]),
            (ResourceLocation[])this.field_200284_c.toArray(new ResourceLocation[0]),
            this.field_200285_d == null ? FunctionObject.CacheableFunction.field_193519_a : new FunctionObject.CacheableFunction(this.field_200285_d)
         );
      }
   }

   public static class Deserializer implements JsonDeserializer<AdvancementRewards> {
      public AdvancementRewards deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "rewards");
         int ☃x = JsonUtils.func_151208_a(☃, "experience", 0);
         JsonArray ☃xx = JsonUtils.func_151213_a(☃, "loot", new JsonArray());
         ResourceLocation[] ☃xxx = new ResourceLocation[☃xx.size()];

         for(int ☃xxxx = 0; ☃xxxx < ☃xxx.length; ++☃xxxx) {
            ☃xxx[☃xxxx] = new ResourceLocation(JsonUtils.func_151206_a(☃xx.get(☃xxxx), "loot[" + ☃xxxx + "]"));
         }

         JsonArray ☃xxxx = JsonUtils.func_151213_a(☃, "recipes", new JsonArray());
         ResourceLocation[] ☃xxxxx = new ResourceLocation[☃xxxx.size()];

         for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxxx.length; ++☃xxxxxx) {
            ☃xxxxx[☃xxxxxx] = new ResourceLocation(JsonUtils.func_151206_a(☃xxxx.get(☃xxxxxx), "recipes[" + ☃xxxxxx + "]"));
         }

         FunctionObject.CacheableFunction ☃xxxxxx;
         if (☃.has("function")) {
            ☃xxxxxx = new FunctionObject.CacheableFunction(new ResourceLocation(JsonUtils.func_151200_h(☃, "function")));
         } else {
            ☃xxxxxx = FunctionObject.CacheableFunction.field_193519_a;
         }

         return new AdvancementRewards(☃x, ☃xxx, ☃xxxxx, ☃xxxxxx);
      }
   }
}
