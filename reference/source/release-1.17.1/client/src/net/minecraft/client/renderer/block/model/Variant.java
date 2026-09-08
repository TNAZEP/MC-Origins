package net.minecraft.client.renderer.block.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Transformation;
import java.lang.reflect.Type;
import java.util.Objects;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Variant implements ModelState {
   private final ResourceLocation modelLocation;
   private final Transformation rotation;
   private final boolean uvLock;
   private final int weight;

   public Variant(ResourceLocation var1, Transformation var2, boolean var3, int var4) {
      this.modelLocation = â˜ƒ;
      this.rotation = â˜ƒ;
      this.uvLock = â˜ƒ;
      this.weight = â˜ƒ;
   }

   public ResourceLocation getModelLocation() {
      return this.modelLocation;
   }

   @Override
   public Transformation getRotation() {
      return this.rotation;
   }

   @Override
   public boolean isUvLocked() {
      return this.uvLock;
   }

   public int getWeight() {
      return this.weight;
   }

   public String toString() {
      return "Variant{modelLocation=" + this.modelLocation + ", rotation=" + this.rotation + ", uvLock=" + this.uvLock + ", weight=" + this.weight + "}";
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Variant)) {
         return false;
      } else {
         Variant â˜ƒ = (Variant)â˜ƒ;
         return this.modelLocation.equals(â˜ƒ.modelLocation)
            && Objects.equals(this.rotation, â˜ƒ.rotation)
            && this.uvLock == â˜ƒ.uvLock
            && this.weight == â˜ƒ.weight;
      }
   }

   public int hashCode() {
      int â˜ƒ = this.modelLocation.hashCode();
      â˜ƒ = 31 * â˜ƒ + this.rotation.hashCode();
      â˜ƒ = 31 * â˜ƒ + Boolean.valueOf(this.uvLock).hashCode();
      return 31 * â˜ƒ + this.weight;
   }

   public static class Deserializer implements JsonDeserializer<Variant> {
      @VisibleForTesting
      static final boolean DEFAULT_UVLOCK = false;
      @VisibleForTesting
      static final int DEFAULT_WEIGHT = 1;
      @VisibleForTesting
      static final int DEFAULT_X_ROTATION = 0;
      @VisibleForTesting
      static final int DEFAULT_Y_ROTATION = 0;

      public Variant deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         ResourceLocation â˜ƒx = this.getModel(â˜ƒ);
         BlockModelRotation â˜ƒxx = this.getBlockRotation(â˜ƒ);
         boolean â˜ƒxxx = this.getUvLock(â˜ƒ);
         int â˜ƒxxxx = this.getWeight(â˜ƒ);
         return new Variant(â˜ƒx, â˜ƒxx.getRotation(), â˜ƒxxx, â˜ƒxxxx);
      }

      private boolean getUvLock(JsonObject var1) {
         return GsonHelper.getAsBoolean(â˜ƒ, "uvlock", false);
      }

      protected BlockModelRotation getBlockRotation(JsonObject var1) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "x", 0);
         int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "y", 0);
         BlockModelRotation â˜ƒxx = BlockModelRotation.by(â˜ƒ, â˜ƒx);
         if (â˜ƒxx == null) {
            throw new JsonParseException("Invalid BlockModelRotation x: " + â˜ƒ + ", y: " + â˜ƒx);
         } else {
            return â˜ƒxx;
         }
      }

      protected ResourceLocation getModel(JsonObject var1) {
         return new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "model"));
      }

      protected int getWeight(JsonObject var1) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "weight", 1);
         if (â˜ƒ < 1) {
            throw new JsonParseException("Invalid weight " + â˜ƒ + " found, expected integer >= 1");
         } else {
            return â˜ƒ;
         }
      }
   }
}
