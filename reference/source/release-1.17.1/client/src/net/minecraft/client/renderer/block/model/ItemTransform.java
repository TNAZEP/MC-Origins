package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import java.lang.reflect.Type;
import net.minecraft.util.GsonHelper;

public class ItemTransform {
   public static final ItemTransform NO_TRANSFORM = new ItemTransform(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
   public final Vector3f rotation;
   public final Vector3f translation;
   public final Vector3f scale;

   public ItemTransform(Vector3f var1, Vector3f var2, Vector3f var3) {
      this.rotation = â˜ƒ.copy();
      this.translation = â˜ƒ.copy();
      this.scale = â˜ƒ.copy();
   }

   public void apply(boolean var1, PoseStack var2) {
      if (this != NO_TRANSFORM) {
         float â˜ƒ = this.rotation.x();
         float â˜ƒx = this.rotation.y();
         float â˜ƒxx = this.rotation.z();
         if (â˜ƒ) {
            â˜ƒx = -â˜ƒx;
            â˜ƒxx = -â˜ƒxx;
         }

         int â˜ƒ = â˜ƒ ? -1 : 1;
         â˜ƒ.translate((double)((float)â˜ƒ * this.translation.x()), (double)this.translation.y(), (double)this.translation.z());
         â˜ƒ.mulPose(new Quaternion(â˜ƒ, â˜ƒx, â˜ƒxx, true));
         â˜ƒ.scale(this.scale.x(), this.scale.y(), this.scale.z());
      }
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (this.getClass() != â˜ƒ.getClass()) {
         return false;
      } else {
         ItemTransform â˜ƒ = (ItemTransform)â˜ƒ;
         return this.rotation.equals(â˜ƒ.rotation) && this.scale.equals(â˜ƒ.scale) && this.translation.equals(â˜ƒ.translation);
      }
   }

   public int hashCode() {
      int â˜ƒ = this.rotation.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.translation.hashCode();
      return 31 * â˜ƒ + this.scale.hashCode();
   }

   protected static class Deserializer implements JsonDeserializer<ItemTransform> {
      private static final Vector3f DEFAULT_ROTATION = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f DEFAULT_TRANSLATION = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f DEFAULT_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);
      public static final float MAX_TRANSLATION = 5.0F;
      public static final float MAX_SCALE = 4.0F;

      public ItemTransform deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         Vector3f â˜ƒx = this.getVector3f(â˜ƒ, "rotation", DEFAULT_ROTATION);
         Vector3f â˜ƒxx = this.getVector3f(â˜ƒ, "translation", DEFAULT_TRANSLATION);
         â˜ƒxx.mul(0.0625F);
         â˜ƒxx.clamp(-5.0F, 5.0F);
         Vector3f â˜ƒxxx = this.getVector3f(â˜ƒ, "scale", DEFAULT_SCALE);
         â˜ƒxxx.clamp(-4.0F, 4.0F);
         return new ItemTransform(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }

      private Vector3f getVector3f(JsonObject var1, String var2, Vector3f var3) {
         if (!â˜ƒ.has(â˜ƒ)) {
            return â˜ƒ;
         } else {
            JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, â˜ƒ);
            if (â˜ƒ.size() != 3) {
               throw new JsonParseException("Expected 3 " + â˜ƒ + " values, found: " + â˜ƒ.size());
            } else {
               float[] â˜ƒ = new float[3];

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
                  â˜ƒ[â˜ƒx] = GsonHelper.convertToFloat(â˜ƒ.get(â˜ƒx), â˜ƒ + "[" + â˜ƒx + "]");
               }

               return new Vector3f(â˜ƒ[0], â˜ƒ[1], â˜ƒ[2]);
            }
         }
      }
   }
}
