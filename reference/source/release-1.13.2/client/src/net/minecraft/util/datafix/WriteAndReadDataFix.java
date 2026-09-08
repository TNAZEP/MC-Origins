package net.minecraft.util.datafix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;

public class WriteAndReadDataFix extends DataFix {
   private final String field_210598_a;
   private final TypeReference field_210599_b;

   public WriteAndReadDataFix(Schema var1, String var2, TypeReference var3) {
      super(☃, true);
      this.field_210598_a = ☃;
      this.field_210599_b = ☃;
   }

   @Override
   protected TypeRewriteRule makeRule() {
      return this.writeAndRead(this.field_210598_a, this.getInputSchema().getType(this.field_210599_b), this.getOutputSchema().getType(this.field_210599_b));
   }
}
