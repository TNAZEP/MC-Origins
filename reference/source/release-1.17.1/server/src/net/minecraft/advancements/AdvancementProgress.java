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
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public class AdvancementProgress implements Comparable<AdvancementProgress> {
   final Map<String, CriterionProgress> criteria;
   private String[][] requirements = new String[0][];

   private AdvancementProgress(Map<String, CriterionProgress> var1) {
      this.criteria = â˜ƒ;
   }

   public AdvancementProgress() {
      this.criteria = Maps.newHashMap();
   }

   public void update(Map<String, Criterion> var1, String[][] var2) {
      Set<String> â˜ƒ = â˜ƒ.keySet();
      this.criteria.entrySet().removeIf(var1x -> !â˜ƒ.contains(var1x.getKey()));

      for(String â˜ƒx : â˜ƒ) {
         if (!this.criteria.containsKey(â˜ƒx)) {
            this.criteria.put(â˜ƒx, new CriterionProgress());
         }
      }

      this.requirements = â˜ƒ;
   }

   public boolean isDone() {
      if (this.requirements.length == 0) {
         return false;
      } else {
         for(String[] â˜ƒ : this.requirements) {
            boolean â˜ƒx = false;

            for(String â˜ƒxx : â˜ƒ) {
               CriterionProgress â˜ƒxxx = this.getCriterion(â˜ƒxx);
               if (â˜ƒxxx != null && â˜ƒxxx.isDone()) {
                  â˜ƒx = true;
                  break;
               }
            }

            if (!â˜ƒx) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean hasProgress() {
      for(CriterionProgress â˜ƒ : this.criteria.values()) {
         if (â˜ƒ.isDone()) {
            return true;
         }
      }

      return false;
   }

   public boolean grantProgress(String var1) {
      CriterionProgress â˜ƒ = (CriterionProgress)this.criteria.get(â˜ƒ);
      if (â˜ƒ != null && !â˜ƒ.isDone()) {
         â˜ƒ.grant();
         return true;
      } else {
         return false;
      }
   }

   public boolean revokeProgress(String var1) {
      CriterionProgress â˜ƒ = (CriterionProgress)this.criteria.get(â˜ƒ);
      if (â˜ƒ != null && â˜ƒ.isDone()) {
         â˜ƒ.revoke();
         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      return "AdvancementProgress{criteria=" + this.criteria + ", requirements=" + Arrays.deepToString(this.requirements) + "}";
   }

   public void serializeToNetwork(FriendlyByteBuf var1) {
      â˜ƒ.writeMap(this.criteria, FriendlyByteBuf::writeUtf, (var0, var1x) -> var1x.serializeToNetwork(var0));
   }

   public static AdvancementProgress fromNetwork(FriendlyByteBuf var0) {
      Map<String, CriterionProgress> â˜ƒ = â˜ƒ.readMap(FriendlyByteBuf::readUtf, CriterionProgress::fromNetwork);
      return new AdvancementProgress(â˜ƒ);
   }

   @Nullable
   public CriterionProgress getCriterion(String var1) {
      return (CriterionProgress)this.criteria.get(â˜ƒ);
   }

   public float getPercent() {
      if (this.criteria.isEmpty()) {
         return 0.0F;
      } else {
         float â˜ƒ = (float)this.requirements.length;
         float â˜ƒx = (float)this.countCompletedRequirements();
         return â˜ƒx / â˜ƒ;
      }
   }

   @Nullable
   public String getProgressText() {
      if (this.criteria.isEmpty()) {
         return null;
      } else {
         int â˜ƒ = this.requirements.length;
         if (â˜ƒ <= 1) {
            return null;
         } else {
            int â˜ƒ = this.countCompletedRequirements();
            return â˜ƒ + "/" + â˜ƒ;
         }
      }
   }

   private int countCompletedRequirements() {
      int â˜ƒ = 0;

      for(String[] â˜ƒx : this.requirements) {
         boolean â˜ƒxx = false;

         for(String â˜ƒxxx : â˜ƒx) {
            CriterionProgress â˜ƒxxxx = this.getCriterion(â˜ƒxxx);
            if (â˜ƒxxxx != null && â˜ƒxxxx.isDone()) {
               â˜ƒxx = true;
               break;
            }
         }

         if (â˜ƒxx) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   public Iterable<String> getRemainingCriteria() {
      List<String> â˜ƒ = Lists.newArrayList();

      for(Entry<String, CriterionProgress> â˜ƒx : this.criteria.entrySet()) {
         if (!((CriterionProgress)â˜ƒx.getValue()).isDone()) {
            â˜ƒ.add((String)â˜ƒx.getKey());
         }
      }

      return â˜ƒ;
   }

   public Iterable<String> getCompletedCriteria() {
      List<String> â˜ƒ = Lists.newArrayList();

      for(Entry<String, CriterionProgress> â˜ƒx : this.criteria.entrySet()) {
         if (((CriterionProgress)â˜ƒx.getValue()).isDone()) {
            â˜ƒ.add((String)â˜ƒx.getKey());
         }
      }

      return â˜ƒ;
   }

   @Nullable
   public Date getFirstProgressDate() {
      Date â˜ƒ = null;

      for(CriterionProgress â˜ƒx : this.criteria.values()) {
         if (â˜ƒx.isDone() && (â˜ƒ == null || â˜ƒx.getObtained().before(â˜ƒ))) {
            â˜ƒ = â˜ƒx.getObtained();
         }
      }

      return â˜ƒ;
   }

   public int compareTo(AdvancementProgress var1) {
      Date â˜ƒ = this.getFirstProgressDate();
      Date â˜ƒx = â˜ƒ.getFirstProgressDate();
      if (â˜ƒ == null && â˜ƒx != null) {
         return 1;
      } else if (â˜ƒ != null && â˜ƒx == null) {
         return -1;
      } else {
         return â˜ƒ == null && â˜ƒx == null ? 0 : â˜ƒ.compareTo(â˜ƒx);
      }
   }

   public static class Serializer implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress> {
      public JsonElement serialize(AdvancementProgress var1, Type var2, JsonSerializationContext var3) {
         JsonObject â˜ƒ = new JsonObject();
         JsonObject â˜ƒx = new JsonObject();

         for(Entry<String, CriterionProgress> â˜ƒxx : â˜ƒ.criteria.entrySet()) {
            CriterionProgress â˜ƒxxx = (CriterionProgress)â˜ƒxx.getValue();
            if (â˜ƒxxx.isDone()) {
               â˜ƒx.add((String)â˜ƒxx.getKey(), â˜ƒxxx.serializeToJson());
            }
         }

         if (!â˜ƒx.entrySet().isEmpty()) {
            â˜ƒ.add("criteria", â˜ƒx);
         }

         â˜ƒ.addProperty("done", â˜ƒ.isDone());
         return â˜ƒ;
      }

      public AdvancementProgress deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "advancement");
         JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "criteria", new JsonObject());
         AdvancementProgress â˜ƒxx = new AdvancementProgress();

         for(Entry<String, JsonElement> â˜ƒxxx : â˜ƒx.entrySet()) {
            String â˜ƒxxxx = (String)â˜ƒxxx.getKey();
            â˜ƒxx.criteria.put(â˜ƒxxxx, CriterionProgress.fromJson(GsonHelper.convertToString((JsonElement)â˜ƒxxx.getValue(), â˜ƒxxxx)));
         }

         return â˜ƒxx;
      }
   }
}
