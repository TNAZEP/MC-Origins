package net.minecraft.client.renderer.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;

public class BlockPartFace {
   public final EnumFacing field_178244_b;
   public final int field_178245_c;
   public final String field_178242_d;
   public final BlockFaceUV field_178243_e;

   public BlockPartFace(@Nullable EnumFacing var1, int var2, String var3, BlockFaceUV var4) {
      this.field_178244_b = ☃;
      this.field_178245_c = ☃;
      this.field_178242_d = ☃;
      this.field_178243_e = ☃;
   }

   static class Deserializer implements JsonDeserializer<BlockPartFace> {
      public BlockPartFace deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject ☃ = ☃.getAsJsonObject();
         EnumFacing ☃x = this.func_178339_c(☃);
         int ☃xx = this.func_178337_a(☃);
         String ☃xxx = this.func_178340_b(☃);
         BlockFaceUV ☃xxxx = ☃.deserialize(☃, BlockFaceUV.class);
         return new BlockPartFace(☃x, ☃xx, ☃xxx, ☃xxxx);
      }

      protected int func_178337_a(JsonObject var1) {
         return JsonUtils.func_151208_a(☃, "tintindex", -1);
      }

      private String func_178340_b(JsonObject var1) {
         return JsonUtils.func_151200_h(☃, "texture");
      }

      @Nullable
      private EnumFacing func_178339_c(JsonObject var1) {
         String ☃ = JsonUtils.func_151219_a(☃, "cullface", "");
         return EnumFacing.func_176739_a(☃);
      }
   }
}
