package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;

public class EntityAnchorArgument implements ArgumentType<EntityAnchorArgument.Type> {
   private static final Collection<String> field_201308_a = Arrays.asList("eyes", "feet");
   private static final DynamicCommandExceptionType field_201025_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.anchor.invalid", var0)
   );

   public static EntityAnchorArgument.Type func_201023_a(CommandContext<CommandSource> var0, String var1) {
      return ☃.getArgument(☃, EntityAnchorArgument.Type.class);
   }

   public static EntityAnchorArgument func_201024_a() {
      return new EntityAnchorArgument();
   }

   public EntityAnchorArgument.Type parse(StringReader var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();
      String ☃x = ☃.readUnquotedString();
      EntityAnchorArgument.Type ☃xx = EntityAnchorArgument.Type.func_201016_a(☃x);
      if (☃xx == null) {
         ☃.setCursor(☃);
         throw field_201025_a.createWithContext(☃, ☃x);
      } else {
         return ☃xx;
      }
   }

   @Override
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return ISuggestionProvider.func_197005_b(EntityAnchorArgument.Type.field_201020_c.keySet(), ☃);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201308_a;
   }

   public static enum Type {
      FEET("feet", (var0, var1) -> var0),
      EYES("eyes", (var0, var1) -> new Vec3d(var0.field_72450_a, var0.field_72448_b + (double)var1.func_70047_e(), var0.field_72449_c));

      private static final Map<String, EntityAnchorArgument.Type> field_201020_c = Util.func_200696_a(Maps.newHashMap(), var0 -> {
         for(EntityAnchorArgument.Type ☃ : values()) {
            var0.put(☃.field_201021_d, ☃);
         }
      });
      private final String field_201021_d;
      private final BiFunction<Vec3d, Entity, Vec3d> field_201022_e;

      private Type(String var3, BiFunction<Vec3d, Entity, Vec3d> var4) {
         this.field_201021_d = ☃;
         this.field_201022_e = ☃;
      }

      @Nullable
      public static EntityAnchorArgument.Type func_201016_a(String var0) {
         return (EntityAnchorArgument.Type)field_201020_c.get(☃);
      }

      public Vec3d func_201017_a(Entity var1) {
         return (Vec3d)this.field_201022_e.apply(new Vec3d(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v), ☃);
      }

      public Vec3d func_201015_a(CommandSource var1) {
         Entity ☃ = ☃.func_197022_f();
         return ☃ == null ? ☃.func_197036_d() : (Vec3d)this.field_201022_e.apply(☃.func_197036_d(), ☃);
      }
   }
}
