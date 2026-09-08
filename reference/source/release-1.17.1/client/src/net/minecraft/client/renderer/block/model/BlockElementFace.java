package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.util.GsonHelper;

public class BlockElementFace {
   public static final int NO_TINT = -1;
   public final Direction cullForDirection;
   public final int tintIndex;
   public final String texture;
   public final BlockFaceUV uv;

   public BlockElementFace(@Nullable Direction var1, int var2, String var3, BlockFaceUV var4) {
      this.cullForDirection = â˜ƒ;
      this.tintIndex = â˜ƒ;
      this.texture = â˜ƒ;
      this.uv = â˜ƒ;
   }

   protected static class Deserializer implements JsonDeserializer<BlockElementFace> {
      private static final int DEFAULT_TINT_INDEX = -1;

      public BlockElementFace deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
         Direction â˜ƒx = this.getCullFacing(â˜ƒ);
         int â˜ƒxx = this.getTintIndex(â˜ƒ);
         String â˜ƒxxx = this.getTexture(â˜ƒ);
         BlockFaceUV â˜ƒxxxx = â˜ƒ.deserialize(â˜ƒ, BlockFaceUV.class);
         return new BlockElementFace(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }

      protected int getTintIndex(JsonObject var1) {
         return GsonHelper.getAsInt(â˜ƒ, "tintindex", -1);
      }

      private String getTexture(JsonObject var1) {
         return GsonHelper.getAsString(â˜ƒ, "texture");
      }

      @Nullable
      private Direction getCullFacing(JsonObject var1) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "cullface", "");
         return Direction.byName(â˜ƒ);
      }
   }
}
