package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;

public class BlockFaceUV {
   public float[] uvs;
   public final int rotation;

   public BlockFaceUV(@Nullable float[] var1, int var2) {
      this.uvs = â˜ƒ;
      this.rotation = â˜ƒ;
   }

   public float getU(int var1) {
      if (this.uvs == null) {
         throw new NullPointerException("uvs");
      } else {
         int â˜ƒ = this.getShiftedIndex(â˜ƒ);
         return this.uvs[â˜ƒ != 0 && â˜ƒ != 1 ? 2 : 0];
      }
   }

   public float getV(int var1) {
      if (this.uvs == null) {
         throw new NullPointerException("uvs");
      } else {
         int â˜ƒ = this.getShiftedIndex(â˜ƒ);
         return this.uvs[â˜ƒ != 0 && â˜ƒ != 3 ? 3 : 1];
      }
   }

   private int getShiftedIndex(int var1) {
      return (â˜ƒ + this.rotation / 90) % 4;
   }

   public int getReverseIndex(int var1) {
      return (â˜ƒ + 4 - this.rotation / 90) % 4;
   }

   public void setMissingUv(float[] var1) {
      if (this.uvs == null) {
         this.uvs = â˜ƒ;
      }
   }

   protected static class Deserializer implements JsonDeserializer<BlockFaceUV> {
      private static final int DEFAULT_ROTATION = 0;

      public BlockFaceUV deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         float[] â˜ƒx = this.getUVs(â˜ƒ);
         int â˜ƒxx = this.getRotation(â˜ƒ);
         return new BlockFaceUV(â˜ƒx, â˜ƒxx);
      }

      protected int getRotation(JsonObject var1) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "rotation", 0);
         if (â˜ƒ >= 0 && â˜ƒ % 90 == 0 && â˜ƒ / 90 <= 3) {
            return â˜ƒ;
         } else {
            throw new JsonParseException("Invalid rotation " + â˜ƒ + " found, only 0/90/180/270 allowed");
         }
      }

      @Nullable
      private float[] getUVs(JsonObject var1) {
         if (!â˜ƒ.has("uv")) {
            return null;
         } else {
            JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "uv");
            if (â˜ƒ.size() != 4) {
               throw new JsonParseException("Expected 4 uv values, found: " + â˜ƒ.size());
            } else {
               float[] â˜ƒ = new float[4];

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
                  â˜ƒ[â˜ƒx] = GsonHelper.convertToFloat(â˜ƒ.get(â˜ƒx), "uv[" + â˜ƒx + "]");
               }

               return â˜ƒ;
            }
         }
      }
   }
}
