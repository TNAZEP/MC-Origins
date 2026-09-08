package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;

public class IglooMetadataRemovalFix extends DataFix {
   public IglooMetadataRemovalFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Type<?> â˜ƒ = this.getInputSchema().getType(References.STRUCTURE_FEATURE);
      Type<?> â˜ƒx = this.getOutputSchema().getType(References.STRUCTURE_FEATURE);
      return this.writeFixAndRead("IglooMetadataRemovalFix", â˜ƒ, â˜ƒx, IglooMetadataRemovalFix::fixTag);
   }

   private static <T> Dynamic<T> fixTag(Dynamic<T> var0) {
      boolean â˜ƒ = â˜ƒ.get("Children").asStreamOpt().map(var0x -> var0x.allMatch(IglooMetadataRemovalFix::isIglooPiece)).result().orElse(false);
      return â˜ƒ ? â˜ƒ.set("id", â˜ƒ.createString("Igloo")).remove("Children") : â˜ƒ.update("Children", IglooMetadataRemovalFix::removeIglooPieces);
   }

   private static <T> Dynamic<T> removeIglooPieces(Dynamic<T> var0) {
      return (Dynamic<T>)â˜ƒ.asStreamOpt().map(var0x -> var0x.filter(var0xx -> !isIglooPiece(var0xx))).map(â˜ƒ::createList).result().orElse(â˜ƒ);
   }

   private static boolean isIglooPiece(Dynamic<?> var0) {
      return â˜ƒ.get("id").asString("").equals("Iglu");
   }
}
