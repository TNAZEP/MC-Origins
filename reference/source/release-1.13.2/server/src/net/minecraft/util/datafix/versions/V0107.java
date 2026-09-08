package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V0107 extends Schema {
   public V0107(int var1, Schema var2) {
      super(☃, ☃);
   }

   @Override
   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> ☃ = super.registerEntities(☃);
      ☃.remove("Minecart");
      return ☃;
   }
}
