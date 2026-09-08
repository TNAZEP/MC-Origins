package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class Criterion {
   private final ICriterionInstance field_192147_a;

   public Criterion(ICriterionInstance var1) {
      this.field_192147_a = ☃;
   }

   public Criterion() {
      this.field_192147_a = null;
   }

   public void func_192140_a(PacketBuffer var1) {
   }

   public static Criterion func_192145_a(JsonObject var0, JsonDeserializationContext var1) {
      ResourceLocation ☃ = new ResourceLocation(JsonUtils.func_151200_h(☃, "trigger"));
      ICriterionTrigger<?> ☃x = CriteriaTriggers.func_192119_a(☃);
      if (☃x == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + ☃);
      } else {
         ICriterionInstance ☃ = ☃x.func_192166_a(JsonUtils.func_151218_a(☃, "conditions", new JsonObject()), ☃);
         return new Criterion(☃);
      }
   }

   public static Criterion func_192146_b(PacketBuffer var0) {
      return new Criterion();
   }

   public static Map<String, Criterion> func_192144_b(JsonObject var0, JsonDeserializationContext var1) {
      Map<String, Criterion> ☃ = Maps.newHashMap();

      for(Entry<String, JsonElement> ☃x : ☃.entrySet()) {
         ☃.put(☃x.getKey(), func_192145_a(JsonUtils.func_151210_l((JsonElement)☃x.getValue(), "criterion"), ☃));
      }

      return ☃;
   }

   public static Map<String, Criterion> func_192142_c(PacketBuffer var0) {
      Map<String, Criterion> ☃ = Maps.newHashMap();
      int ☃x = ☃.func_150792_a();

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         ☃.put(☃.func_150789_c(32767), func_192146_b(☃));
      }

      return ☃;
   }

   public static void func_192141_a(Map<String, Criterion> var0, PacketBuffer var1) {
      ☃.func_150787_b(☃.size());

      for(Entry<String, Criterion> ☃ : ☃.entrySet()) {
         ☃.func_180714_a((String)☃.getKey());
         ((Criterion)☃.getValue()).func_192140_a(☃);
      }
   }

   @Nullable
   public ICriterionInstance func_192143_a() {
      return this.field_192147_a;
   }

   public JsonElement func_200287_b() {
      JsonObject ☃ = new JsonObject();
      ☃.addProperty("trigger", this.field_192147_a.func_192244_a().toString());
      ☃.add("conditions", this.field_192147_a.func_200288_b());
      return ☃;
   }
}
