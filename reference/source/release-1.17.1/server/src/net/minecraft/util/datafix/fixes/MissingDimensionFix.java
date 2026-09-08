package net.minecraft.util.datafix.fixes;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public class MissingDimensionFix extends DataFix {
   public MissingDimensionFix(Schema var1, boolean var2) {
      super(â˜ƒ, â˜ƒ);
   }

   private static <A> Type<Pair<A, Dynamic<?>>> fields(String var0, Type<A> var1) {
      return DSL.and(DSL.field(â˜ƒ, â˜ƒ), DSL.remainderType());
   }

   private static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> optionalFields(String var0, Type<A> var1) {
      return DSL.and(DSL.optional(DSL.field(â˜ƒ, â˜ƒ)), DSL.remainderType());
   }

   private static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> optionalFields(
      String var0, Type<A1> var1, String var2, Type<A2> var3
   ) {
      return DSL.and(DSL.optional(DSL.field(â˜ƒ, â˜ƒ)), DSL.optional(DSL.field(â˜ƒ, â˜ƒ)), DSL.remainderType());
   }

   @Override
   protected TypeRewriteRule makeRule() {
      Schema â˜ƒ = this.getInputSchema();
      TaggedChoiceType<String> â˜ƒx = new TaggedChoiceType(
         "type",
         DSL.string(),
         ImmutableMap.of(
            "minecraft:debug",
            DSL.remainderType(),
            "minecraft:flat",
            optionalFields(
               "settings",
               optionalFields("biome", â˜ƒ.getType(References.BIOME), "layers", DSL.list(optionalFields("block", â˜ƒ.getType(References.BLOCK_NAME))))
            ),
            "minecraft:noise",
            optionalFields(
               "biome_source",
               DSL.taggedChoiceType(
                  "type",
                  DSL.string(),
                  ImmutableMap.of(
                     "minecraft:fixed",
                     fields("biome", â˜ƒ.getType(References.BIOME)),
                     "minecraft:multi_noise",
                     DSL.list(fields("biome", â˜ƒ.getType(References.BIOME))),
                     "minecraft:checkerboard",
                     fields("biomes", DSL.list(â˜ƒ.getType(References.BIOME))),
                     "minecraft:vanilla_layered",
                     DSL.remainderType(),
                     "minecraft:the_end",
                     DSL.remainderType()
                  )
               ),
               "settings",
               DSL.or(DSL.string(), optionalFields("default_block", â˜ƒ.getType(References.BLOCK_NAME), "default_fluid", â˜ƒ.getType(References.BLOCK_NAME)))
            )
         )
      );
      CompoundListType<String, ?> â˜ƒxx = DSL.compoundList(NamespacedSchema.namespacedString(), fields("generator", â˜ƒx));
      Type<?> â˜ƒxxx = DSL.and(â˜ƒxx, DSL.remainderType());
      Type<?> â˜ƒxxxx = â˜ƒ.getType(References.WORLD_GEN_SETTINGS);
      FieldFinder<?> â˜ƒxxxxx = new FieldFinder<>("dimensions", â˜ƒxxx);
      if (!â˜ƒxxxx.findFieldType("dimensions").equals(â˜ƒxxx)) {
         throw new IllegalStateException();
      } else {
         OpticFinder<? extends List<? extends Pair<String, ?>>> â˜ƒ = â˜ƒxx.finder();
         return this.fixTypeEverywhereTyped("MissingDimensionFix", â˜ƒxxxx, var4x -> var4x.updateTyped(â˜ƒ, var4xx -> var4xx.updateTyped(â˜ƒ, var3x -> {
                  if (!(var3x.getValue() instanceof List)) {
                     throw new IllegalStateException("List exptected");
                  } else if (((List)var3x.getValue()).isEmpty()) {
                     Dynamic<?> â˜ƒ = var4x.get(DSL.remainderFinder());
                     Dynamic<?> â˜ƒx = this.recreateSettings(â˜ƒ);
                     return DataFixUtils.orElse(â˜ƒ.readTyped(â˜ƒx).result().map(Pair::getFirst), var3x);
                  } else {
                     return var3x;
                  }
               })));
      }
   }

   private <T> Dynamic<T> recreateSettings(Dynamic<T> var1) {
      long â˜ƒ = â˜ƒ.get("seed").asLong(0L);
      return new Dynamic<>(â˜ƒ.getOps(), WorldGenSettingsFix.vanillaLevels(â˜ƒ, â˜ƒ, WorldGenSettingsFix.defaultOverworld(â˜ƒ, â˜ƒ), false));
   }
}
