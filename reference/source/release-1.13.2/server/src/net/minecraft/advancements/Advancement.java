package net.minecraft.advancements;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.lang3.ArrayUtils;

public class Advancement {
   private final Advancement field_192076_a;
   private final DisplayInfo field_192077_b;
   private final AdvancementRewards field_192078_c;
   private final ResourceLocation field_192079_d;
   private final Map<String, Criterion> field_192080_e;
   private final String[][] field_192081_f;
   private final Set<Advancement> field_192082_g = Sets.<Advancement>newLinkedHashSet();
   private final ITextComponent field_193125_h;

   public Advancement(
      ResourceLocation var1, @Nullable Advancement var2, @Nullable DisplayInfo var3, AdvancementRewards var4, Map<String, Criterion> var5, String[][] var6
   ) {
      this.field_192079_d = ☃;
      this.field_192077_b = ☃;
      this.field_192080_e = ImmutableMap.copyOf(☃);
      this.field_192076_a = ☃;
      this.field_192078_c = ☃;
      this.field_192081_f = ☃;
      if (☃ != null) {
         ☃.func_192071_a(this);
      }

      if (☃ == null) {
         this.field_193125_h = new TextComponentString(☃.toString());
      } else {
         ITextComponent ☃ = ☃.func_192297_a();
         TextFormatting ☃x = ☃.func_192291_d().func_193229_c();
         ITextComponent ☃xx = ☃.func_212638_h().func_211708_a(☃x).func_150258_a("\n").func_150257_a(☃.func_193222_b());
         ITextComponent ☃xxx = ☃.func_212638_h().func_211710_a(var1x -> var1x.func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, ☃)));
         this.field_193125_h = new TextComponentString("[").func_150257_a(☃xxx).func_150258_a("]").func_211708_a(☃x);
      }
   }

   public Advancement.Builder func_192075_a() {
      return new Advancement.Builder(
         this.field_192076_a == null ? null : this.field_192076_a.func_192067_g(),
         this.field_192077_b,
         this.field_192078_c,
         this.field_192080_e,
         this.field_192081_f
      );
   }

   @Nullable
   public Advancement func_192070_b() {
      return this.field_192076_a;
   }

   @Nullable
   public DisplayInfo func_192068_c() {
      return this.field_192077_b;
   }

   public AdvancementRewards func_192072_d() {
      return this.field_192078_c;
   }

   public String toString() {
      return "SimpleAdvancement{id="
         + this.func_192067_g()
         + ", parent="
         + (this.field_192076_a == null ? "null" : this.field_192076_a.func_192067_g())
         + ", display="
         + this.field_192077_b
         + ", rewards="
         + this.field_192078_c
         + ", criteria="
         + this.field_192080_e
         + ", requirements="
         + Arrays.deepToString(this.field_192081_f)
         + '}';
   }

   public Iterable<Advancement> func_192069_e() {
      return this.field_192082_g;
   }

   public Map<String, Criterion> func_192073_f() {
      return this.field_192080_e;
   }

   public void func_192071_a(Advancement var1) {
      this.field_192082_g.add(☃);
   }

   public ResourceLocation func_192067_g() {
      return this.field_192079_d;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof Advancement)) {
         return false;
      } else {
         Advancement ☃ = (Advancement)☃;
         return this.field_192079_d.equals(☃.field_192079_d);
      }
   }

   public int hashCode() {
      return this.field_192079_d.hashCode();
   }

   public String[][] func_192074_h() {
      return this.field_192081_f;
   }

   public ITextComponent func_193123_j() {
      return this.field_193125_h;
   }

   public static class Builder {
      private ResourceLocation field_192061_a;
      private Advancement field_192062_b;
      private DisplayInfo field_192063_c;
      private AdvancementRewards field_192064_d = AdvancementRewards.field_192114_a;
      private Map<String, Criterion> field_192065_e = Maps.newLinkedHashMap();
      private String[][] field_192066_f;
      private RequirementsStrategy field_199751_g = RequirementsStrategy.AND;

      private Builder(@Nullable ResourceLocation var1, @Nullable DisplayInfo var2, AdvancementRewards var3, Map<String, Criterion> var4, String[][] var5) {
         this.field_192061_a = ☃;
         this.field_192063_c = ☃;
         this.field_192064_d = ☃;
         this.field_192065_e = ☃;
         this.field_192066_f = ☃;
      }

      private Builder() {
      }

      public static Advancement.Builder func_200278_a() {
         return new Advancement.Builder();
      }

      public Advancement.Builder func_203905_a(Advancement var1) {
         this.field_192062_b = ☃;
         return this;
      }

      public Advancement.Builder func_200272_a(ResourceLocation var1) {
         this.field_192061_a = ☃;
         return this;
      }

      public Advancement.Builder func_203902_a(
         IItemProvider var1,
         ITextComponent var2,
         ITextComponent var3,
         @Nullable ResourceLocation var4,
         FrameType var5,
         boolean var6,
         boolean var7,
         boolean var8
      ) {
         return this.func_203903_a(new DisplayInfo(new ItemStack(☃.func_199767_j()), ☃, ☃, ☃, ☃, ☃, ☃, ☃));
      }

      public Advancement.Builder func_203903_a(DisplayInfo var1) {
         this.field_192063_c = ☃;
         return this;
      }

      public Advancement.Builder func_200271_a(AdvancementRewards.Builder var1) {
         return this.func_200274_a(☃.func_200281_a());
      }

      public Advancement.Builder func_200274_a(AdvancementRewards var1) {
         this.field_192064_d = ☃;
         return this;
      }

      public Advancement.Builder func_200275_a(String var1, ICriterionInstance var2) {
         return this.func_200276_a(☃, new Criterion(☃));
      }

      public Advancement.Builder func_200276_a(String var1, Criterion var2) {
         if (this.field_192065_e.containsKey(☃)) {
            throw new IllegalArgumentException("Duplicate criterion " + ☃);
         } else {
            this.field_192065_e.put(☃, ☃);
            return this;
         }
      }

      public Advancement.Builder func_200270_a(RequirementsStrategy var1) {
         this.field_199751_g = ☃;
         return this;
      }

      public boolean func_192058_a(Function<ResourceLocation, Advancement> var1) {
         if (this.field_192061_a == null) {
            return true;
         } else {
            if (this.field_192062_b == null) {
               this.field_192062_b = (Advancement)☃.apply(this.field_192061_a);
            }

            return this.field_192062_b != null;
         }
      }

      public Advancement func_192056_a(ResourceLocation var1) {
         if (!this.func_192058_a(var0 -> null)) {
            throw new IllegalStateException("Tried to build incomplete advancement!");
         } else {
            if (this.field_192066_f == null) {
               this.field_192066_f = this.field_199751_g.createRequirements(this.field_192065_e.keySet());
            }

            return new Advancement(☃, this.field_192062_b, this.field_192063_c, this.field_192064_d, this.field_192065_e, this.field_192066_f);
         }
      }

      public Advancement func_203904_a(Consumer<Advancement> var1, String var2) {
         Advancement ☃ = this.func_192056_a(new ResourceLocation(☃));
         ☃.accept(☃);
         return ☃;
      }

      public JsonObject func_200273_b() {
         if (this.field_192066_f == null) {
            this.field_192066_f = this.field_199751_g.createRequirements(this.field_192065_e.keySet());
         }

         JsonObject ☃ = new JsonObject();
         if (this.field_192062_b != null) {
            ☃.addProperty("parent", this.field_192062_b.func_192067_g().toString());
         } else if (this.field_192061_a != null) {
            ☃.addProperty("parent", this.field_192061_a.toString());
         }

         if (this.field_192063_c != null) {
            ☃.add("display", this.field_192063_c.func_200290_k());
         }

         ☃.add("rewards", this.field_192064_d.func_200286_b());
         JsonObject ☃ = new JsonObject();

         for(Entry<String, Criterion> ☃x : this.field_192065_e.entrySet()) {
            ☃.add((String)☃x.getKey(), ((Criterion)☃x.getValue()).func_200287_b());
         }

         ☃.add("criteria", ☃);
         JsonArray ☃x = new JsonArray();

         for(String[] ☃xx : this.field_192066_f) {
            JsonArray ☃xxx = new JsonArray();

            for(String ☃xxxx : ☃xx) {
               ☃xxx.add(☃xxxx);
            }

            ☃x.add(☃xxx);
         }

         ☃.add("requirements", ☃x);
         return ☃;
      }

      public void func_192057_a(PacketBuffer var1) {
         if (this.field_192061_a == null) {
            ☃.writeBoolean(false);
         } else {
            ☃.writeBoolean(true);
            ☃.func_192572_a(this.field_192061_a);
         }

         if (this.field_192063_c == null) {
            ☃.writeBoolean(false);
         } else {
            ☃.writeBoolean(true);
            this.field_192063_c.func_192290_a(☃);
         }

         Criterion.func_192141_a(this.field_192065_e, ☃);
         ☃.func_150787_b(this.field_192066_f.length);

         for(String[] ☃ : this.field_192066_f) {
            ☃.func_150787_b(☃.length);

            for(String ☃x : ☃) {
               ☃.func_180714_a(☃x);
            }
         }
      }

      public String toString() {
         return "Task Advancement{parentId="
            + this.field_192061_a
            + ", display="
            + this.field_192063_c
            + ", rewards="
            + this.field_192064_d
            + ", criteria="
            + this.field_192065_e
            + ", requirements="
            + Arrays.deepToString(this.field_192066_f)
            + '}';
      }

      public static Advancement.Builder func_192059_a(JsonObject var0, JsonDeserializationContext var1) {
         ResourceLocation ☃ = ☃.has("parent") ? new ResourceLocation(JsonUtils.func_151200_h(☃, "parent")) : null;
         DisplayInfo ☃x = ☃.has("display") ? DisplayInfo.func_192294_a(JsonUtils.func_152754_s(☃, "display"), ☃) : null;
         AdvancementRewards ☃xx = JsonUtils.func_188177_a(☃, "rewards", AdvancementRewards.field_192114_a, ☃, AdvancementRewards.class);
         Map<String, Criterion> ☃xxx = Criterion.func_192144_b(JsonUtils.func_152754_s(☃, "criteria"), ☃);
         if (☃xxx.isEmpty()) {
            throw new JsonSyntaxException("Advancement criteria cannot be empty");
         } else {
            JsonArray ☃ = JsonUtils.func_151213_a(☃, "requirements", new JsonArray());
            String[][] ☃x = new String[☃.size()][];

            for(int ☃xx = 0; ☃xx < ☃.size(); ++☃xx) {
               JsonArray ☃xxx = JsonUtils.func_151207_m(☃.get(☃xx), "requirements[" + ☃xx + "]");
               ☃x[☃xx] = new String[☃xxx.size()];

               for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
                  ☃x[☃xx][☃xxxx] = JsonUtils.func_151206_a(☃xxx.get(☃xxxx), "requirements[" + ☃xx + "][" + ☃xxxx + "]");
               }
            }

            if (☃x.length == 0) {
               ☃x = new String[☃xxx.size()][];
               int ☃xx = 0;

               for(String ☃xxx : ☃xxx.keySet()) {
                  ☃x[☃xx++] = new String[]{☃xxx};
               }
            }

            for(String[] ☃xx : ☃x) {
               if (☃xx.length == 0 && ☃xxx.isEmpty()) {
                  throw new JsonSyntaxException("Requirement entry cannot be empty");
               }

               for(String ☃xxx : ☃xx) {
                  if (!☃xxx.containsKey(☃xxx)) {
                     throw new JsonSyntaxException("Unknown required criterion '" + ☃xxx + "'");
                  }
               }
            }

            for(String ☃xx : ☃xxx.keySet()) {
               boolean ☃xxx = false;

               for(String[] ☃xxxx : ☃x) {
                  if (ArrayUtils.contains(☃xxxx, ☃xx)) {
                     ☃xxx = true;
                     break;
                  }
               }

               if (!☃xxx) {
                  throw new JsonSyntaxException(
                     "Criterion '" + ☃xx + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required."
                  );
               }
            }

            return new Advancement.Builder(☃, ☃x, ☃xx, ☃xxx, ☃x);
         }
      }

      public static Advancement.Builder func_192060_b(PacketBuffer var0) {
         ResourceLocation ☃ = ☃.readBoolean() ? ☃.func_192575_l() : null;
         DisplayInfo ☃x = ☃.readBoolean() ? DisplayInfo.func_192295_b(☃) : null;
         Map<String, Criterion> ☃xx = Criterion.func_192142_c(☃);
         String[][] ☃xxx = new String[☃.func_150792_a()][];

         for(int ☃xxxx = 0; ☃xxxx < ☃xxx.length; ++☃xxxx) {
            ☃xxx[☃xxxx] = new String[☃.func_150792_a()];

            for(int ☃xxxxx = 0; ☃xxxxx < ☃xxx[☃xxxx].length; ++☃xxxxx) {
               ☃xxx[☃xxxx][☃xxxxx] = ☃.func_150789_c(32767);
            }
         }

         return new Advancement.Builder(☃, ☃x, AdvancementRewards.field_192114_a, ☃xx, ☃xxx);
      }

      public Map<String, Criterion> func_200277_c() {
         return this.field_192065_e;
      }
   }
}
