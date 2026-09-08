package net.minecraft.command.impl.data;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.function.Function;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.NBTArgument;
import net.minecraft.command.arguments.NBTPathArgument;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCollection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;

public class DataCommand {
   private static final SimpleCommandExceptionType field_198949_b = new SimpleCommandExceptionType(new TextComponentTranslation("commands.data.merge.failed"));
   private static final DynamicCommandExceptionType field_198950_c = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.data.get.invalid", var0)
   );
   private static final DynamicCommandExceptionType field_201229_d = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("commands.data.get.unknown", var0)
   );
   public static final List<DataCommand.IDataProvider> field_198948_a = Lists.<DataCommand.IDataProvider>newArrayList(
      EntityDataAccessor.field_198926_a, BlockDataAccessor.field_198930_a
   );

   public static void func_198937_a(CommandDispatcher<CommandSource> var0) {
      LiteralArgumentBuilder<CommandSource> ☃ = Commands.func_197057_a("data").requires(var0x -> var0x.func_197034_c(2));

      for(DataCommand.IDataProvider ☃x : field_198948_a) {
         ☃.then(
               ☃x.func_198920_a(
                  Commands.func_197057_a("merge"),
                  var1x -> var1x.then(
                        Commands.func_197056_a("nbt", NBTArgument.func_197131_a())
                           .executes(var1xx -> func_198946_a(var1xx.getSource(), ☃.func_198919_a(var1xx), NBTArgument.func_197130_a(var1xx, "nbt")))
                     )
               )
            )
            .then(
               ☃x.func_198920_a(
                  Commands.func_197057_a("get"),
                  var1x -> var1x.executes(var1xx -> func_198947_a((CommandSource)var1xx.getSource(), ☃.func_198919_a(var1xx)))
                        .then(
                           Commands.func_197056_a("path", NBTPathArgument.func_197149_a())
                              .executes(var1xx -> func_201228_b(var1xx.getSource(), ☃.func_198919_a(var1xx), NBTPathArgument.func_197148_a(var1xx, "path")))
                              .then(
                                 Commands.func_197056_a("scale", DoubleArgumentType.doubleArg())
                                    .executes(
                                       var1xx -> func_198938_a(
                                             var1xx.getSource(),
                                             ☃.func_198919_a(var1xx),
                                             NBTPathArgument.func_197148_a(var1xx, "path"),
                                             DoubleArgumentType.getDouble(var1xx, "scale")
                                          )
                                    )
                              )
                        )
               )
            )
            .then(
               ☃x.func_198920_a(
                  Commands.func_197057_a("remove"),
                  var1x -> var1x.then(
                        Commands.func_197056_a("path", NBTPathArgument.func_197149_a())
                           .executes(var1xx -> func_198942_a(var1xx.getSource(), ☃.func_198919_a(var1xx), NBTPathArgument.func_197148_a(var1xx, "path")))
                     )
               )
            );
      }

      ☃.register(☃);
   }

   private static int func_198942_a(CommandSource var0, IDataAccessor var1, NBTPathArgument.NBTPath var2) throws CommandSyntaxException {
      NBTTagCompound ☃ = ☃.func_198923_a();
      NBTTagCompound ☃x = ☃.func_74737_b();
      ☃.func_197140_b(☃);
      if (☃x.equals(☃)) {
         throw field_198949_b.create();
      } else {
         ☃.func_198925_a(☃);
         ☃.func_197030_a(☃.func_198921_b(), true);
         return 1;
      }
   }

   private static int func_201228_b(CommandSource var0, IDataAccessor var1, NBTPathArgument.NBTPath var2) throws CommandSyntaxException {
      INBTBase ☃x = ☃.func_197143_a(☃.func_198923_a());
      int ☃;
      if (☃x instanceof NBTPrimitive) {
         ☃ = MathHelper.func_76128_c(((NBTPrimitive)☃x).func_150286_g());
      } else if (☃x instanceof NBTTagCollection) {
         ☃ = ((NBTTagCollection)☃x).size();
      } else if (☃x instanceof NBTTagCompound) {
         ☃ = ((NBTTagCompound)☃x).func_186856_d();
      } else {
         if (!(☃x instanceof NBTTagString)) {
            throw field_201229_d.create(☃.toString());
         }

         ☃ = ((NBTTagString)☃x).func_150285_a_().length();
      }

      ☃.func_197030_a(☃.func_198924_b(☃x), false);
      return ☃;
   }

   private static int func_198938_a(CommandSource var0, IDataAccessor var1, NBTPathArgument.NBTPath var2, double var3) throws CommandSyntaxException {
      INBTBase ☃ = ☃.func_197143_a(☃.func_198923_a());
      if (!(☃ instanceof NBTPrimitive)) {
         throw field_198950_c.create(☃.toString());
      } else {
         int ☃ = MathHelper.func_76128_c(((NBTPrimitive)☃).func_150286_g() * ☃);
         ☃.func_197030_a(☃.func_198922_a(☃, ☃, ☃), false);
         return ☃;
      }
   }

   private static int func_198947_a(CommandSource var0, IDataAccessor var1) throws CommandSyntaxException {
      ☃.func_197030_a(☃.func_198924_b(☃.func_198923_a()), false);
      return 1;
   }

   private static int func_198946_a(CommandSource var0, IDataAccessor var1, NBTTagCompound var2) throws CommandSyntaxException {
      NBTTagCompound ☃ = ☃.func_198923_a();
      NBTTagCompound ☃x = ☃.func_74737_b().func_197643_a(☃);
      if (☃.equals(☃x)) {
         throw field_198949_b.create();
      } else {
         ☃.func_198925_a(☃x);
         ☃.func_197030_a(☃.func_198921_b(), true);
         return 1;
      }
   }

   public interface IDataProvider {
      IDataAccessor func_198919_a(CommandContext<CommandSource> var1) throws CommandSyntaxException;

      ArgumentBuilder<CommandSource, ?> func_198920_a(
         ArgumentBuilder<CommandSource, ?> var1, Function<ArgumentBuilder<CommandSource, ?>, ArgumentBuilder<CommandSource, ?>> var2
      );
   }
}
