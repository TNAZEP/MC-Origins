package net.minecraft.client.renderer.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOverride {
   private final ResourceLocation field_188028_a;
   private final Map<ResourceLocation, Float> field_188029_b;

   public ItemOverride(ResourceLocation var1, Map<ResourceLocation, Float> var2) {
      this.field_188028_a = ☃;
      this.field_188029_b = ☃;
   }

   public ResourceLocation func_188026_a() {
      return this.field_188028_a;
   }

   boolean func_188027_a(ItemStack var1, @Nullable World var2, @Nullable EntityLivingBase var3) {
      Item ☃ = ☃.func_77973_b();

      for(Entry<ResourceLocation, Float> ☃x : this.field_188029_b.entrySet()) {
         IItemPropertyGetter ☃xx = ☃.func_185045_a((ResourceLocation)☃x.getKey());
         if (☃xx == null || ☃xx.call(☃, ☃, ☃) < ☃x.getValue()) {
            return false;
         }
      }

      return true;
   }

   static class Deserializer implements JsonDeserializer<ItemOverride> {
      public ItemOverride deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         ResourceLocation ☃x = new ResourceLocation(JsonUtils.func_151200_h(☃, "model"));
         Map<ResourceLocation, Float> ☃xx = this.func_188025_a(☃);
         return new ItemOverride(☃x, ☃xx);
      }

      protected Map<ResourceLocation, Float> func_188025_a(JsonObject var1) {
         Map<ResourceLocation, Float> ☃ = Maps.newLinkedHashMap();
         JsonObject ☃x = JsonUtils.func_152754_s(☃, "predicate");

         for(Entry<String, JsonElement> ☃xx : ☃x.entrySet()) {
            ☃.put(new ResourceLocation((String)☃xx.getKey()), JsonUtils.func_151220_d((JsonElement)☃xx.getValue(), (String)☃xx.getKey()));
         }

         return ☃;
      }
   }
}
