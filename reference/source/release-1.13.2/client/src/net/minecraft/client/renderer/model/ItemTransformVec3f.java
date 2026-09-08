package net.minecraft.client.renderer.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.JsonUtils;

public class ItemTransformVec3f {
   public static final ItemTransformVec3f field_178366_a = new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
   public final Vector3f field_178364_b;
   public final Vector3f field_178365_c;
   public final Vector3f field_178363_d;

   public ItemTransformVec3f(Vector3f var1, Vector3f var2, Vector3f var3) {
      this.field_178364_b = new Vector3f(☃);
      this.field_178365_c = new Vector3f(☃);
      this.field_178363_d = new Vector3f(☃);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (this.getClass() != ☃.getClass()) {
         return false;
      } else {
         ItemTransformVec3f ☃ = (ItemTransformVec3f)☃;
         return this.field_178364_b.equals(☃.field_178364_b) && this.field_178363_d.equals(☃.field_178363_d) && this.field_178365_c.equals(☃.field_178365_c);
      }
   }

   public int hashCode() {
      int ☃ = this.field_178364_b.hashCode();
      ☃ = 31 * ☃ + this.field_178365_c.hashCode();
      return 31 * ☃ + this.field_178363_d.hashCode();
   }

   static class Deserializer implements JsonDeserializer<ItemTransformVec3f> {
      private static final Vector3f field_178362_a = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f field_178360_b = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f field_178361_c = new Vector3f(1.0F, 1.0F, 1.0F);

      public ItemTransformVec3f deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         Vector3f ☃x = this.func_199340_a(☃, "rotation", field_178362_a);
         Vector3f ☃xx = this.func_199340_a(☃, "translation", field_178360_b);
         ☃xx.func_195898_a(0.0625F);
         ☃xx.func_195901_a(-5.0F, 5.0F);
         Vector3f ☃xxx = this.func_199340_a(☃, "scale", field_178361_c);
         ☃xxx.func_195901_a(-4.0F, 4.0F);
         return new ItemTransformVec3f(☃x, ☃xx, ☃xxx);
      }

      private Vector3f func_199340_a(JsonObject var1, String var2, Vector3f var3) {
         if (!☃.has(☃)) {
            return ☃;
         } else {
            JsonArray ☃ = JsonUtils.func_151214_t(☃, ☃);
            if (☃.size() != 3) {
               throw new JsonParseException("Expected 3 " + ☃ + " values, found: " + ☃.size());
            } else {
               float[] ☃ = new float[3];

               for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
                  ☃[☃x] = JsonUtils.func_151220_d(☃.get(☃x), ☃ + "[" + ☃x + "]");
               }

               return new Vector3f(☃[0], ☃[1], ☃[2]);
            }
         }
      }
   }
}
