package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.stream.Stream;
import net.minecraft.util.datafix.TypeReferences;

public class IglooMetadataRemoval extends DataFix {
   public IglooMetadataRemoval(Schema var1, boolean var2) {
      super(☃, ☃);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> ☃ = this.getInputSchema().getType(TypeReferences.field_211303_s);
      Type<?> ☃x = this.getOutputSchema().getType(TypeReferences.field_211303_s);
      return this.writeFixAndRead("IglooMetadataRemovalFix", ☃, ☃x, IglooMetadataRemoval::func_211926_a);
   }

   private static <T> Dynamic<T> func_211926_a(Dynamic<T> var0) {
      boolean ☃ = ☃.get("Children").flatMap(Dynamic::getStream).map(var0x -> var0x.allMatch(IglooMetadataRemoval::func_211930_c)).orElse(false);
      return ☃ ? ☃.set("id", ☃.createString("Igloo")).remove("Children") : ☃.update("Children", IglooMetadataRemoval::func_211929_b);
   }

   private static <T> Dynamic<T> func_211929_b(Dynamic<T> var0) {
      return (Dynamic<T>)☃.getStream().map(var0x -> var0x.filter(var0xx -> !func_211930_c(var0xx))).map(☃::createList).orElse(☃);
   }

   private static boolean func_211930_c(Dynamic<?> var0) {
      return ☃.getString("id").equals("Iglu");
   }
}
