package net.minecraft.util.text;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class Style {
   private Style field_150249_a;
   private TextFormatting field_150247_b;
   private Boolean field_150248_c;
   private Boolean field_150245_d;
   private Boolean field_150246_e;
   private Boolean field_150243_f;
   private Boolean field_150244_g;
   private ClickEvent field_150251_h;
   private HoverEvent field_150252_i;
   private String field_179990_j;
   private static final Style field_150250_j = new Style() {
      @Nullable
      @Override
      public TextFormatting func_150215_a() {
         return null;
      }

      @Override
      public boolean func_150223_b() {
         return false;
      }

      @Override
      public boolean func_150242_c() {
         return false;
      }

      @Override
      public boolean func_150236_d() {
         return false;
      }

      @Override
      public boolean func_150234_e() {
         return false;
      }

      @Override
      public boolean func_150233_f() {
         return false;
      }

      @Nullable
      @Override
      public ClickEvent func_150235_h() {
         return null;
      }

      @Nullable
      @Override
      public HoverEvent func_150210_i() {
         return null;
      }

      @Nullable
      @Override
      public String func_179986_j() {
         return null;
      }

      @Override
      public Style func_150238_a(TextFormatting var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150227_a(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150217_b(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150225_c(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150228_d(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150237_e(Boolean var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150241_a(ClickEvent var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150209_a(HoverEvent var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Style func_150221_a(Style var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public String toString() {
         return "Style.ROOT";
      }

      @Override
      public Style func_150232_l() {
         return this;
      }

      @Override
      public Style func_150206_m() {
         return this;
      }

      @Override
      public String func_150218_j() {
         return "";
      }
   };

   @Nullable
   public TextFormatting func_150215_a() {
      return this.field_150247_b == null ? this.func_150224_n().func_150215_a() : this.field_150247_b;
   }

   public boolean func_150223_b() {
      return this.field_150248_c == null ? this.func_150224_n().func_150223_b() : this.field_150248_c;
   }

   public boolean func_150242_c() {
      return this.field_150245_d == null ? this.func_150224_n().func_150242_c() : this.field_150245_d;
   }

   public boolean func_150236_d() {
      return this.field_150243_f == null ? this.func_150224_n().func_150236_d() : this.field_150243_f;
   }

   public boolean func_150234_e() {
      return this.field_150246_e == null ? this.func_150224_n().func_150234_e() : this.field_150246_e;
   }

   public boolean func_150233_f() {
      return this.field_150244_g == null ? this.func_150224_n().func_150233_f() : this.field_150244_g;
   }

   public boolean func_150229_g() {
      return this.field_150248_c == null
         && this.field_150245_d == null
         && this.field_150243_f == null
         && this.field_150246_e == null
         && this.field_150244_g == null
         && this.field_150247_b == null
         && this.field_150251_h == null
         && this.field_150252_i == null
         && this.field_179990_j == null;
   }

   @Nullable
   public ClickEvent func_150235_h() {
      return this.field_150251_h == null ? this.func_150224_n().func_150235_h() : this.field_150251_h;
   }

   @Nullable
   public HoverEvent func_150210_i() {
      return this.field_150252_i == null ? this.func_150224_n().func_150210_i() : this.field_150252_i;
   }

   @Nullable
   public String func_179986_j() {
      return this.field_179990_j == null ? this.func_150224_n().func_179986_j() : this.field_179990_j;
   }

   public Style func_150238_a(TextFormatting var1) {
      this.field_150247_b = ☃;
      return this;
   }

   public Style func_150227_a(Boolean var1) {
      this.field_150248_c = ☃;
      return this;
   }

   public Style func_150217_b(Boolean var1) {
      this.field_150245_d = ☃;
      return this;
   }

   public Style func_150225_c(Boolean var1) {
      this.field_150243_f = ☃;
      return this;
   }

   public Style func_150228_d(Boolean var1) {
      this.field_150246_e = ☃;
      return this;
   }

   public Style func_150237_e(Boolean var1) {
      this.field_150244_g = ☃;
      return this;
   }

   public Style func_150241_a(ClickEvent var1) {
      this.field_150251_h = ☃;
      return this;
   }

   public Style func_150209_a(HoverEvent var1) {
      this.field_150252_i = ☃;
      return this;
   }

   public Style func_179989_a(String var1) {
      this.field_179990_j = ☃;
      return this;
   }

   public Style func_150221_a(Style var1) {
      this.field_150249_a = ☃;
      return this;
   }

   public String func_150218_j() {
      if (this.func_150229_g()) {
         return this.field_150249_a != null ? this.field_150249_a.func_150218_j() : "";
      } else {
         StringBuilder ☃ = new StringBuilder();
         if (this.func_150215_a() != null) {
            ☃.append(this.func_150215_a());
         }

         if (this.func_150223_b()) {
            ☃.append(TextFormatting.BOLD);
         }

         if (this.func_150242_c()) {
            ☃.append(TextFormatting.ITALIC);
         }

         if (this.func_150234_e()) {
            ☃.append(TextFormatting.UNDERLINE);
         }

         if (this.func_150233_f()) {
            ☃.append(TextFormatting.OBFUSCATED);
         }

         if (this.func_150236_d()) {
            ☃.append(TextFormatting.STRIKETHROUGH);
         }

         return ☃.toString();
      }
   }

   private Style func_150224_n() {
      return this.field_150249_a == null ? field_150250_j : this.field_150249_a;
   }

   public String toString() {
      return "Style{hasParent="
         + (this.field_150249_a != null)
         + ", color="
         + this.field_150247_b
         + ", bold="
         + this.field_150248_c
         + ", italic="
         + this.field_150245_d
         + ", underlined="
         + this.field_150246_e
         + ", obfuscated="
         + this.field_150244_g
         + ", clickEvent="
         + this.func_150235_h()
         + ", hoverEvent="
         + this.func_150210_i()
         + ", insertion="
         + this.func_179986_j()
         + '}';
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof Style)) {
         return false;
      } else {
         Style ☃ = (Style)☃;
         return this.func_150223_b() == ☃.func_150223_b()
            && this.func_150215_a() == ☃.func_150215_a()
            && this.func_150242_c() == ☃.func_150242_c()
            && this.func_150233_f() == ☃.func_150233_f()
            && this.func_150236_d() == ☃.func_150236_d()
            && this.func_150234_e() == ☃.func_150234_e()
            && (this.func_150235_h() != null ? this.func_150235_h().equals(☃.func_150235_h()) : ☃.func_150235_h() == null)
            && (this.func_150210_i() != null ? this.func_150210_i().equals(☃.func_150210_i()) : ☃.func_150210_i() == null)
            && (this.func_179986_j() != null ? this.func_179986_j().equals(☃.func_179986_j()) : ☃.func_179986_j() == null);
      }
   }

   public int hashCode() {
      return Objects.hash(
         new Object[]{
            this.field_150247_b,
            this.field_150248_c,
            this.field_150245_d,
            this.field_150246_e,
            this.field_150243_f,
            this.field_150244_g,
            this.field_150251_h,
            this.field_150252_i,
            this.field_179990_j
         }
      );
   }

   public Style func_150232_l() {
      Style ☃ = new Style();
      ☃.field_150248_c = this.field_150248_c;
      ☃.field_150245_d = this.field_150245_d;
      ☃.field_150243_f = this.field_150243_f;
      ☃.field_150246_e = this.field_150246_e;
      ☃.field_150244_g = this.field_150244_g;
      ☃.field_150247_b = this.field_150247_b;
      ☃.field_150251_h = this.field_150251_h;
      ☃.field_150252_i = this.field_150252_i;
      ☃.field_150249_a = this.field_150249_a;
      ☃.field_179990_j = this.field_179990_j;
      return ☃;
   }

   public Style func_150206_m() {
      Style ☃ = new Style();
      ☃.func_150227_a(this.func_150223_b());
      ☃.func_150217_b(this.func_150242_c());
      ☃.func_150225_c(this.func_150236_d());
      ☃.func_150228_d(this.func_150234_e());
      ☃.func_150237_e(this.func_150233_f());
      ☃.func_150238_a(this.func_150215_a());
      ☃.func_150241_a(this.func_150235_h());
      ☃.func_150209_a(this.func_150210_i());
      ☃.func_179989_a(this.func_179986_j());
      return ☃;
   }

   public static class Serializer implements JsonDeserializer<Style>, JsonSerializer<Style> {
      @Nullable
      public Style deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (☃.isJsonObject()) {
            Style ☃ = new Style();
            JsonObject ☃x = ☃.getAsJsonObject();
            if (☃x == null) {
               return null;
            } else {
               if (☃x.has("bold")) {
                  ☃.field_150248_c = ☃x.get("bold").getAsBoolean();
               }

               if (☃x.has("italic")) {
                  ☃.field_150245_d = ☃x.get("italic").getAsBoolean();
               }

               if (☃x.has("underlined")) {
                  ☃.field_150246_e = ☃x.get("underlined").getAsBoolean();
               }

               if (☃x.has("strikethrough")) {
                  ☃.field_150243_f = ☃x.get("strikethrough").getAsBoolean();
               }

               if (☃x.has("obfuscated")) {
                  ☃.field_150244_g = ☃x.get("obfuscated").getAsBoolean();
               }

               if (☃x.has("color")) {
                  ☃.field_150247_b = ☃.deserialize(☃x.get("color"), TextFormatting.class);
               }

               if (☃x.has("insertion")) {
                  ☃.field_179990_j = ☃x.get("insertion").getAsString();
               }

               if (☃x.has("clickEvent")) {
                  JsonObject ☃ = ☃x.getAsJsonObject("clickEvent");
                  if (☃ != null) {
                     JsonPrimitive ☃x = ☃.getAsJsonPrimitive("action");
                     ClickEvent.Action ☃xx = ☃x == null ? null : ClickEvent.Action.func_150672_a(☃x.getAsString());
                     JsonPrimitive ☃xxx = ☃.getAsJsonPrimitive("value");
                     String ☃xxxx = ☃xxx == null ? null : ☃xxx.getAsString();
                     if (☃xx != null && ☃xxxx != null && ☃xx.func_150674_a()) {
                        ☃.field_150251_h = new ClickEvent(☃xx, ☃xxxx);
                     }
                  }
               }

               if (☃x.has("hoverEvent")) {
                  JsonObject ☃ = ☃x.getAsJsonObject("hoverEvent");
                  if (☃ != null) {
                     JsonPrimitive ☃x = ☃.getAsJsonPrimitive("action");
                     HoverEvent.Action ☃xx = ☃x == null ? null : HoverEvent.Action.func_150684_a(☃x.getAsString());
                     ITextComponent ☃xxx = ☃.deserialize(☃.get("value"), ITextComponent.class);
                     if (☃xx != null && ☃xxx != null && ☃xx.func_150686_a()) {
                        ☃.field_150252_i = new HoverEvent(☃xx, ☃xxx);
                     }
                  }
               }

               return ☃;
            }
         } else {
            return null;
         }
      }

      @Nullable
      public JsonElement serialize(Style var1, Type var2, JsonSerializationContext var3) {
         if (☃.func_150229_g()) {
            return null;
         } else {
            JsonObject ☃ = new JsonObject();
            if (☃.field_150248_c != null) {
               ☃.addProperty("bold", ☃.field_150248_c);
            }

            if (☃.field_150245_d != null) {
               ☃.addProperty("italic", ☃.field_150245_d);
            }

            if (☃.field_150246_e != null) {
               ☃.addProperty("underlined", ☃.field_150246_e);
            }

            if (☃.field_150243_f != null) {
               ☃.addProperty("strikethrough", ☃.field_150243_f);
            }

            if (☃.field_150244_g != null) {
               ☃.addProperty("obfuscated", ☃.field_150244_g);
            }

            if (☃.field_150247_b != null) {
               ☃.add("color", ☃.serialize(☃.field_150247_b));
            }

            if (☃.field_179990_j != null) {
               ☃.add("insertion", ☃.serialize(☃.field_179990_j));
            }

            if (☃.field_150251_h != null) {
               JsonObject ☃ = new JsonObject();
               ☃.addProperty("action", ☃.field_150251_h.func_150669_a().func_150673_b());
               ☃.addProperty("value", ☃.field_150251_h.func_150668_b());
               ☃.add("clickEvent", ☃);
            }

            if (☃.field_150252_i != null) {
               JsonObject ☃ = new JsonObject();
               ☃.addProperty("action", ☃.field_150252_i.func_150701_a().func_150685_b());
               ☃.add("value", ☃.serialize(☃.field_150252_i.func_150702_b()));
               ☃.add("hoverEvent", ☃);
            }

            return ☃;
         }
      }
   }
}
