package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextComponentTranslation;

public class EntitySummonArgument implements ArgumentType<ResourceLocation> {
   private static final Collection<String> field_211370_b = Arrays.asList("minecraft:pig", "cow");
   public static final DynamicCommandExceptionType field_211369_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("entity.notFound", var0)
   );

   public static EntitySummonArgument func_211366_a() {
      return new EntitySummonArgument();
   }

   public static ResourceLocation func_211368_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return func_211365_a(☃.getArgument(☃, ResourceLocation.class));
   }

   private static final ResourceLocation func_211365_a(ResourceLocation var0) throws CommandSyntaxException {
      EntityType<?> ☃ = IRegistry.field_212629_r.func_212608_b(☃);
      if (☃ != null && ☃.func_200720_b()) {
         return ☃;
      } else {
         throw field_211369_a.create(☃);
      }
   }

   public ResourceLocation parse(StringReader var1) throws CommandSyntaxException {
      return func_211365_a(ResourceLocation.func_195826_a(☃));
   }

   @Override
   public Collection<String> getExamples() {
      return field_211370_b;
   }
}
