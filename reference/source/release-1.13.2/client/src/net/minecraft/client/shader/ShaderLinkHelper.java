package net.minecraft.client.shader;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper {
   private static final Logger field_148080_a = LogManager.getLogger();
   private static ShaderLinkHelper field_148079_b;

   public static void func_148076_a() {
      field_148079_b = new ShaderLinkHelper();
   }

   public static ShaderLinkHelper func_148074_b() {
      return field_148079_b;
   }

   private ShaderLinkHelper() {
   }

   public void func_148077_a(ShaderManager var1) {
      ☃.func_147994_f().func_195656_a();
      ☃.func_147989_e().func_195656_a();
      OpenGlHelper.func_153187_e(☃.func_147986_h());
   }

   public int func_148078_c() throws JsonException {
      int ☃ = OpenGlHelper.func_153183_d();
      if (☃ <= 0) {
         throw new JsonException("Could not create shader program (returned program ID " + ☃ + ")");
      } else {
         return ☃;
      }
   }

   public void func_148075_b(ShaderManager var1) throws IOException {
      ☃.func_147994_f().func_148056_a(☃);
      ☃.func_147989_e().func_148056_a(☃);
      OpenGlHelper.func_153179_f(☃.func_147986_h());
      int ☃ = OpenGlHelper.func_153175_a(☃.func_147986_h(), OpenGlHelper.field_153207_o);
      if (☃ == 0) {
         field_148080_a.warn(
            "Error encountered when linking program containing VS {} and FS {}. Log output:",
            ☃.func_147989_e().func_148055_a(),
            ☃.func_147994_f().func_148055_a()
         );
         field_148080_a.warn(OpenGlHelper.func_153166_e(☃.func_147986_h(), 32768));
      }
   }
}
