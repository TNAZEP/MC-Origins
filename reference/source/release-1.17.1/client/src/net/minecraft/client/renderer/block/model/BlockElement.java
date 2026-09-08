package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.math.Vector3f;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;

public class BlockElement {
   private static final boolean DEFAULT_RESCALE = false;
   private static final float MIN_EXTENT = -16.0F;
   private static final float MAX_EXTENT = 32.0F;
   public final Vector3f from;
   public final Vector3f to;
   public final Map<Direction, BlockElementFace> faces;
   public final BlockElementRotation rotation;
   public final boolean shade;

   public BlockElement(Vector3f var1, Vector3f var2, Map<Direction, BlockElementFace> var3, @Nullable BlockElementRotation var4, boolean var5) {
      this.from = â˜ƒ;
      this.to = â˜ƒ;
      this.faces = â˜ƒ;
      this.rotation = â˜ƒ;
      this.shade = â˜ƒ;
      this.fillUvs();
   }

   private void fillUvs() {
      for(Entry<Direction, BlockElementFace> â˜ƒ : this.faces.entrySet()) {
         float[] â˜ƒx = this.uvsByFace((Direction)â˜ƒ.getKey());
         ((BlockElementFace)â˜ƒ.getValue()).uv.setMissingUv(â˜ƒx);
      }
   }

   private float[] uvsByFace(Direction var1) {
      switch(â˜ƒ) {
         case DOWN:
            return new float[]{this.from.x(), 16.0F - this.to.z(), this.to.x(), 16.0F - this.from.z()};
         case UP:
            return new float[]{this.from.x(), this.from.z(), this.to.x(), this.to.z()};
         case NORTH:
         default:
            return new float[]{16.0F - this.to.x(), 16.0F - this.to.y(), 16.0F - this.from.x(), 16.0F - this.from.y()};
         case SOUTH:
            return new float[]{this.from.x(), 16.0F - this.to.y(), this.to.x(), 16.0F - this.from.y()};
         case WEST:
            return new float[]{this.from.z(), 16.0F - this.to.y(), this.to.z(), 16.0F - this.from.y()};
         case EAST:
            return new float[]{16.0F - this.to.z(), 16.0F - this.to.y(), 16.0F - this.from.z(), 16.0F - this.from.y()};
      }
   }

   protected static class Deserializer implements JsonDeserializer<BlockElement> {
      private static final boolean DEFAULT_SHADE = true;

      public BlockElement deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         Vector3f â˜ƒx = this.getFrom(â˜ƒ);
         Vector3f â˜ƒxx = this.getTo(â˜ƒ);
         BlockElementRotation â˜ƒxxx = this.getRotation(â˜ƒ);
         Map<Direction, BlockElementFace> â˜ƒxxxx = this.getFaces(â˜ƒ, â˜ƒ);
         if (â˜ƒ.has("shade") && !GsonHelper.isBooleanValue(â˜ƒ, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean â˜ƒ = GsonHelper.getAsBoolean(â˜ƒ, "shade", true);
            return new BlockElement(â˜ƒx, â˜ƒxx, â˜ƒxxxx, â˜ƒxxx, â˜ƒ);
         }
      }

      @Nullable
      private BlockElementRotation getRotation(JsonObject var1) {
         BlockElementRotation â˜ƒ = null;
         if (â˜ƒ.has("rotation")) {
            JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "rotation");
            Vector3f â˜ƒxx = this.getVector3f(â˜ƒx, "origin");
            â˜ƒxx.mul(0.0625F);
            Direction.Axis â˜ƒxxx = this.getAxis(â˜ƒx);
            float â˜ƒxxxx = this.getAngle(â˜ƒx);
            boolean â˜ƒxxxxx = GsonHelper.getAsBoolean(â˜ƒx, "rescale", false);
            â˜ƒ = new BlockElementRotation(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         }

         return â˜ƒ;
      }

      private float getAngle(JsonObject var1) {
         float â˜ƒ = GsonHelper.getAsFloat(â˜ƒ, "angle");
         if (â˜ƒ != 0.0F && Mth.abs(â˜ƒ) != 22.5F && Mth.abs(â˜ƒ) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + â˜ƒ + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return â˜ƒ;
         }
      }

      private Direction.Axis getAxis(JsonObject var1) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "axis");
         Direction.Axis â˜ƒx = Direction.Axis.byName(â˜ƒ.toLowerCase(Locale.ROOT));
         if (â˜ƒx == null) {
            throw new JsonParseException("Invalid rotation axis: " + â˜ƒ);
         } else {
            return â˜ƒx;
         }
      }

      private Map<Direction, BlockElementFace> getFaces(JsonDeserializationContext var1, JsonObject var2) {
         Map<Direction, BlockElementFace> â˜ƒ = this.filterNullFromFaces(â˜ƒ, â˜ƒ);
         if (â˜ƒ.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return â˜ƒ;
         }
      }

      private Map<Direction, BlockElementFace> filterNullFromFaces(JsonDeserializationContext var1, JsonObject var2) {
         Map<Direction, BlockElementFace> â˜ƒ = Maps.newEnumMap(Direction.class);
         JsonObject â˜ƒx = GsonHelper.getAsJsonObject(â˜ƒ, "faces");

         for(Entry<String, JsonElement> â˜ƒxx : â˜ƒx.entrySet()) {
            Direction â˜ƒxxx = this.getFacing((String)â˜ƒxx.getKey());
            â˜ƒ.put(â˜ƒxxx, (BlockElementFace)â˜ƒ.deserialize((JsonElement)â˜ƒxx.getValue(), BlockElementFace.class));
         }

         return â˜ƒ;
      }

      private Direction getFacing(String var1) {
         Direction â˜ƒ = Direction.byName(â˜ƒ);
         if (â˜ƒ == null) {
            throw new JsonParseException("Unknown facing: " + â˜ƒ);
         } else {
            return â˜ƒ;
         }
      }

      private Vector3f getTo(JsonObject var1) {
         Vector3f â˜ƒ = this.getVector3f(â˜ƒ, "to");
         if (!(â˜ƒ.x() < -16.0F) && !(â˜ƒ.y() < -16.0F) && !(â˜ƒ.z() < -16.0F) && !(â˜ƒ.x() > 32.0F) && !(â˜ƒ.y() > 32.0F) && !(â˜ƒ.z() > 32.0F)) {
            return â˜ƒ;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + â˜ƒ);
         }
      }

      private Vector3f getFrom(JsonObject var1) {
         Vector3f â˜ƒ = this.getVector3f(â˜ƒ, "from");
         if (!(â˜ƒ.x() < -16.0F) && !(â˜ƒ.y() < -16.0F) && !(â˜ƒ.z() < -16.0F) && !(â˜ƒ.x() > 32.0F) && !(â˜ƒ.y() > 32.0F) && !(â˜ƒ.z() > 32.0F)) {
            return â˜ƒ;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + â˜ƒ);
         }
      }

      private Vector3f getVector3f(JsonObject var1, String var2) {
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
