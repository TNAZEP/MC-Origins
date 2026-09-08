package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ItemTransforms {
   public static final ItemTransforms NO_TRANSFORMS = new ItemTransforms();
   public final ItemTransform thirdPersonLeftHand;
   public final ItemTransform thirdPersonRightHand;
   public final ItemTransform firstPersonLeftHand;
   public final ItemTransform firstPersonRightHand;
   public final ItemTransform head;
   public final ItemTransform gui;
   public final ItemTransform ground;
   public final ItemTransform fixed;

   private ItemTransforms() {
      this(
         ItemTransform.NO_TRANSFORM,
         ItemTransform.NO_TRANSFORM,
         ItemTransform.NO_TRANSFORM,
         ItemTransform.NO_TRANSFORM,
         ItemTransform.NO_TRANSFORM,
         ItemTransform.NO_TRANSFORM,
         ItemTransform.NO_TRANSFORM,
         ItemTransform.NO_TRANSFORM
      );
   }

   public ItemTransforms(ItemTransforms var1) {
      this.thirdPersonLeftHand = â˜ƒ.thirdPersonLeftHand;
      this.thirdPersonRightHand = â˜ƒ.thirdPersonRightHand;
      this.firstPersonLeftHand = â˜ƒ.firstPersonLeftHand;
      this.firstPersonRightHand = â˜ƒ.firstPersonRightHand;
      this.head = â˜ƒ.head;
      this.gui = â˜ƒ.gui;
      this.ground = â˜ƒ.ground;
      this.fixed = â˜ƒ.fixed;
   }

   public ItemTransforms(
      ItemTransform var1,
      ItemTransform var2,
      ItemTransform var3,
      ItemTransform var4,
      ItemTransform var5,
      ItemTransform var6,
      ItemTransform var7,
      ItemTransform var8
   ) {
      this.thirdPersonLeftHand = â˜ƒ;
      this.thirdPersonRightHand = â˜ƒ;
      this.firstPersonLeftHand = â˜ƒ;
      this.firstPersonRightHand = â˜ƒ;
      this.head = â˜ƒ;
      this.gui = â˜ƒ;
      this.ground = â˜ƒ;
      this.fixed = â˜ƒ;
   }

   public ItemTransform getTransform(ItemTransforms.TransformType var1) {
      switch(â˜ƒ) {
         case THIRD_PERSON_LEFT_HAND:
            return this.thirdPersonLeftHand;
         case THIRD_PERSON_RIGHT_HAND:
            return this.thirdPersonRightHand;
         case FIRST_PERSON_LEFT_HAND:
            return this.firstPersonLeftHand;
         case FIRST_PERSON_RIGHT_HAND:
            return this.firstPersonRightHand;
         case HEAD:
            return this.head;
         case GUI:
            return this.gui;
         case GROUND:
            return this.ground;
         case FIXED:
            return this.fixed;
         default:
            return ItemTransform.NO_TRANSFORM;
      }
   }

   public boolean hasTransform(ItemTransforms.TransformType var1) {
      return this.getTransform(â˜ƒ) != ItemTransform.NO_TRANSFORM;
   }

   protected static class Deserializer implements JsonDeserializer<ItemTransforms> {
      public ItemTransforms deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         ItemTransform â˜ƒx = this.getTransform(â˜ƒ, â˜ƒ, "thirdperson_righthand");
         ItemTransform â˜ƒxx = this.getTransform(â˜ƒ, â˜ƒ, "thirdperson_lefthand");
         if (â˜ƒxx == ItemTransform.NO_TRANSFORM) {
            â˜ƒxx = â˜ƒx;
         }

         ItemTransform â˜ƒ = this.getTransform(â˜ƒ, â˜ƒ, "firstperson_righthand");
         ItemTransform â˜ƒx = this.getTransform(â˜ƒ, â˜ƒ, "firstperson_lefthand");
         if (â˜ƒx == ItemTransform.NO_TRANSFORM) {
            â˜ƒx = â˜ƒ;
         }

         ItemTransform â˜ƒ = this.getTransform(â˜ƒ, â˜ƒ, "head");
         ItemTransform â˜ƒx = this.getTransform(â˜ƒ, â˜ƒ, "gui");
         ItemTransform â˜ƒxx = this.getTransform(â˜ƒ, â˜ƒ, "ground");
         ItemTransform â˜ƒxxx = this.getTransform(â˜ƒ, â˜ƒ, "fixed");
         return new ItemTransforms(â˜ƒxx, â˜ƒx, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }

      private ItemTransform getTransform(JsonDeserializationContext var1, JsonObject var2, String var3) {
         return â˜ƒ.has(â˜ƒ) ? â˜ƒ.deserialize(â˜ƒ.get(â˜ƒ), ItemTransform.class) : ItemTransform.NO_TRANSFORM;
      }
   }

   public static enum TransformType {
      NONE,
      THIRD_PERSON_LEFT_HAND,
      THIRD_PERSON_RIGHT_HAND,
      FIRST_PERSON_LEFT_HAND,
      FIRST_PERSON_RIGHT_HAND,
      HEAD,
      GUI,
      GROUND,
      FIXED;

      public boolean firstPerson() {
         return this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND;
      }
   }
}
