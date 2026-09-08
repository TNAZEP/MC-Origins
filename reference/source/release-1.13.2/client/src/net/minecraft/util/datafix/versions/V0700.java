package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V0700 extends Schema {
   public V0700(int var1, Schema var2) {
      super(☃, ☃);
   }

   protected static void func_206627_a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      ☃.register(☃, ☃, (Supplier<TypeTemplate>)(() -> V0100.func_206605_a(☃)));
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerEntities(☃);
      func_206627_a(☃, ☃, "ElderGuardian");
      return ☃;
   }
}
