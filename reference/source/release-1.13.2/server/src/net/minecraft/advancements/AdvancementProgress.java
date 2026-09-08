package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JsonUtils;

public class AdvancementProgress implements Comparable<AdvancementProgress> {
   private final Map<String, CriterionProgress> field_192110_a = Maps.newHashMap();
   private String[][] field_192111_b = new String[0][];

   public void func_192099_a(Map<String, Criterion> var1, String[][] var2) {
      Set<String> ☃ = ☃.keySet();
      this.field_192110_a.entrySet().removeIf(var1x -> !☃.contains(var1x.getKey()));

      for(String ☃x : ☃) {
         if (!this.field_192110_a.containsKey(☃x)) {
            this.field_192110_a.put(☃x, new CriterionProgress());
         }
      }

      this.field_192111_b = ☃;
   }

   public boolean func_192105_a() {
      if (this.field_192111_b.length == 0) {
         return false;
      } else {
         for(String[] ☃ : this.field_192111_b) {
            boolean ☃x = false;

            for(String ☃xx : ☃) {
               CriterionProgress ☃xxx = this.func_192106_c(☃xx);
               if (☃xxx != null && ☃xxx.func_192151_a()) {
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
   }

   public boolean func_192108_b() {
      for(CriterionProgress ☃ : this.field_192110_a.values()) {
         if (☃.func_192151_a()) {
            return true;
         }
      }

      return false;
   }

   public boolean func_192109_a(String var1) {
      CriterionProgress ☃ = (CriterionProgress)this.field_192110_a.get(☃);
      if (☃ != null && !☃.func_192151_a()) {
         ☃.func_192153_b();
         return true;
      } else {
         return false;
      }
   }

   public boolean func_192101_b(String var1) {
      CriterionProgress ☃ = (CriterionProgress)this.field_192110_a.get(☃);
      if (☃ != null && ☃.func_192151_a()) {
         ☃.func_192154_c();
         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      return "AdvancementProgress{criteria=" + this.field_192110_a + ", requirements=" + Arrays.deepToString(this.field_192111_b) + '}';
   }

   public void func_192104_a(PacketBuffer var1) {
      ☃.func_150787_b(this.field_192110_a.size());

      for(Entry<String, CriterionProgress> ☃ : this.field_192110_a.entrySet()) {
         ☃.func_180714_a((String)☃.getKey());
         ((CriterionProgress)☃.getValue()).func_192150_a(☃);
      }
   }

   public static AdvancementProgress func_192100_b(PacketBuffer var0) {
      AdvancementProgress ☃ = new AdvancementProgress();
      int ☃x = ☃.func_150792_a();

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         ☃.field_192110_a.put(☃.func_150789_c(32767), CriterionProgress.func_192149_a(☃));
      }

      return ☃;
   }

   @Nullable
   public CriterionProgress func_192106_c(String var1) {
      return (CriterionProgress)this.field_192110_a.get(☃);
   }

   public Iterable<String> func_192107_d() {
      List<String> ☃ = Lists.newArrayList();

      for(Entry<String, CriterionProgress> ☃x : this.field_192110_a.entrySet()) {
         if (!((CriterionProgress)☃x.getValue()).func_192151_a()) {
            ☃.add(☃x.getKey());
         }
      }

      return ☃;
   }

   public Iterable<String> func_192102_e() {
      List<String> ☃ = Lists.newArrayList();

      for(Entry<String, CriterionProgress> ☃x : this.field_192110_a.entrySet()) {
         if (((CriterionProgress)☃x.getValue()).func_192151_a()) {
            ☃.add(☃x.getKey());
         }
      }

      return ☃;
   }

   @Nullable
   public Date func_193128_g() {
      Date ☃ = null;

      for(CriterionProgress ☃x : this.field_192110_a.values()) {
         if (☃x.func_192151_a() && (☃ == null || ☃x.func_193140_d().before(☃))) {
            ☃ = ☃x.func_193140_d();
         }
      }

      return ☃;
   }

   public int compareTo(AdvancementProgress var1) {
      Date ☃ = this.func_193128_g();
      Date ☃x = ☃.func_193128_g();
      if (☃ == null && ☃x != null) {
         return 1;
      } else if (☃ != null && ☃x == null) {
         return -1;
      } else {
         return ☃ == null && ☃x == null ? 0 : ☃.compareTo(☃x);
      }
   }

   public static class Serializer implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress> {
      public JsonElement serialize(AdvancementProgress var1, Type var2, JsonSerializationContext var3) {
         JsonObject ☃ = new JsonObject();
         JsonObject ☃x = new JsonObject();

         for(Entry<String, CriterionProgress> ☃xx : ☃.field_192110_a.entrySet()) {
            CriterionProgress ☃xxx = (CriterionProgress)☃xx.getValue();
            if (☃xxx.func_192151_a()) {
               ☃x.add((String)☃xx.getKey(), ☃xxx.func_192148_e());
            }
         }

         if (!☃x.entrySet().isEmpty()) {
            ☃.add("criteria", ☃x);
         }

         ☃.addProperty("done", ☃.func_192105_a());
         return ☃;
      }

      public AdvancementProgress deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "advancement");
         JsonObject ☃x = JsonUtils.func_151218_a(☃, "criteria", new JsonObject());
         AdvancementProgress ☃xx = new AdvancementProgress();

         for(Entry<String, JsonElement> ☃xxx : ☃x.entrySet()) {
            String ☃xxxx = (String)☃xxx.getKey();
            ☃xx.field_192110_a.put(☃xxxx, CriterionProgress.func_209541_a(JsonUtils.func_151206_a((JsonElement)☃xxx.getValue(), ☃xxxx)));
         }

         return ☃xx;
      }
   }
}
