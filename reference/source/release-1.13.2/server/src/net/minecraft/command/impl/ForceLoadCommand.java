package net.minecraft.command.impl;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.ColumnPosArgument;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.dimension.DimensionType;

public class ForceLoadCommand {
   private static final Dynamic2CommandExceptionType field_212726_a = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.forceload.toobig", var0, var1)
   );
   private static final Dynamic2CommandExceptionType field_212727_b = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("commands.forceload.query.failure", var0, var1)
   );
   private static final SimpleCommandExceptionType field_212728_c = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.forceload.added.failure")
   );
   private static final SimpleCommandExceptionType field_212729_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("commands.forceload.removed.failure")
   );

   public static void func_212712_a(CommandDispatcher<CommandSource> var0) {
      ☃.register(
         Commands.func_197057_a("forceload")
            .requires(var0x -> var0x.func_197034_c(4))
            .then(
               Commands.func_197057_a("add")
                  .then(
                     Commands.func_197056_a("from", ColumnPosArgument.func_212603_a())
                        .executes(
                           var0x -> func_212719_a(
                                 var0x.getSource(), ColumnPosArgument.func_212602_a(var0x, "from"), ColumnPosArgument.func_212602_a(var0x, "from"), true
                              )
                        )
                        .then(
                           Commands.func_197056_a("to", ColumnPosArgument.func_212603_a())
                              .executes(
                                 var0x -> func_212719_a(
                                       var0x.getSource(), ColumnPosArgument.func_212602_a(var0x, "from"), ColumnPosArgument.func_212602_a(var0x, "to"), true
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.func_197057_a("remove")
                  .then(
                     Commands.func_197056_a("from", ColumnPosArgument.func_212603_a())
                        .executes(
                           var0x -> func_212719_a(
                                 var0x.getSource(), ColumnPosArgument.func_212602_a(var0x, "from"), ColumnPosArgument.func_212602_a(var0x, "from"), false
                              )
                        )
                        .then(
                           Commands.func_197056_a("to", ColumnPosArgument.func_212603_a())
                              .executes(
                                 var0x -> func_212719_a(
                                       var0x.getSource(), ColumnPosArgument.func_212602_a(var0x, "from"), ColumnPosArgument.func_212602_a(var0x, "to"), false
                                    )
                              )
                        )
                  )
                  .then(Commands.func_197057_a("all").executes(var0x -> func_212722_b(var0x.getSource())))
            )
            .then(
               Commands.func_197057_a("query")
                  .executes(var0x -> func_212721_a(var0x.getSource()))
                  .then(
                     Commands.func_197056_a("pos", ColumnPosArgument.func_212603_a())
                        .executes(var0x -> func_212713_a(var0x.getSource(), ColumnPosArgument.func_212602_a(var0x, "pos")))
                  )
            )
      );
   }

   private static int func_212713_a(CommandSource var0, ColumnPosArgument.ColumnPos var1) throws CommandSyntaxException {
      ChunkPos ☃ = new ChunkPos(☃.field_212600_a >> 4, ☃.field_212601_b >> 4);
      DimensionType ☃x = ☃.func_197023_e().func_201675_m().func_186058_p();
      boolean ☃xx = ☃.func_197028_i().func_71218_a(☃x).func_212416_f(☃.field_77276_a, ☃.field_77275_b);
      if (☃xx) {
         ☃.func_197030_a(new TextComponentTranslation("commands.forceload.query.success", ☃, ☃x), false);
         return 1;
      } else {
         throw field_212727_b.create(☃, ☃x);
      }
   }

   private static int func_212721_a(CommandSource var0) {
      DimensionType ☃ = ☃.func_197023_e().func_201675_m().func_186058_p();
      LongSet ☃x = ☃.func_197028_i().func_71218_a(☃).func_212412_ag();
      int ☃xx = ☃x.size();
      if (☃xx > 0) {
         String ☃xxx = Joiner.on(", ").join(☃x.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
         if (☃xx == 1) {
            ☃.func_197030_a(new TextComponentTranslation("commands.forceload.list.single", ☃, ☃xxx), false);
         } else {
            ☃.func_197030_a(new TextComponentTranslation("commands.forceload.list.multiple", ☃xx, ☃, ☃xxx), false);
         }
      } else {
         ☃.func_197021_a(new TextComponentTranslation("commands.forceload.added.none", ☃));
      }

      return ☃xx;
   }

   private static int func_212722_b(CommandSource var0) {
      DimensionType ☃ = ☃.func_197023_e().func_201675_m().func_186058_p();
      WorldServer ☃x = ☃.func_197028_i().func_71218_a(☃);
      LongSet ☃xx = ☃x.func_212412_ag();
      ☃xx.forEach(var1x -> ☃.func_212414_b(ChunkPos.func_212578_a(var1x), ChunkPos.func_212579_b(var1x), false));
      ☃.func_197030_a(new TextComponentTranslation("commands.forceload.removed.all", ☃), true);
      return 0;
   }

   private static int func_212719_a(CommandSource var0, ColumnPosArgument.ColumnPos var1, ColumnPosArgument.ColumnPos var2, boolean var3) throws CommandSyntaxException {
      int ☃ = Math.min(☃.field_212600_a, ☃.field_212600_a);
      int ☃x = Math.min(☃.field_212601_b, ☃.field_212601_b);
      int ☃xx = Math.max(☃.field_212600_a, ☃.field_212600_a);
      int ☃xxx = Math.max(☃.field_212601_b, ☃.field_212601_b);
      if (☃ >= -30000000 && ☃x >= -30000000 && ☃xx < 30000000 && ☃xxx < 30000000) {
         int ☃xxxx = ☃ >> 4;
         int ☃xxxxx = ☃x >> 4;
         int ☃xxxxxx = ☃xx >> 4;
         int ☃xxxxxxx = ☃xxx >> 4;
         long ☃xxxxxxxx = ((long)(☃xxxxxx - ☃xxxx) + 1L) * ((long)(☃xxxxxxx - ☃xxxxx) + 1L);
         if (☃xxxxxxxx > 256L) {
            throw field_212726_a.create(256, ☃xxxxxxxx);
         } else {
            DimensionType ☃xxxx = ☃.func_197023_e().func_201675_m().func_186058_p();
            WorldServer ☃xxxxx = ☃.func_197028_i().func_71218_a(☃xxxx);
            ChunkPos ☃xxxxxx = null;
            int ☃xxxxxxx = 0;

            for(int ☃xxxxxxxx = ☃xxxx; ☃xxxxxxxx <= ☃xxxxxx; ++☃xxxxxxxx) {
               for(int ☃xxxxxxxxx = ☃xxxxx; ☃xxxxxxxxx <= ☃xxxxxxx; ++☃xxxxxxxxx) {
                  boolean ☃xxxxxxxxxx = ☃xxxxx.func_212414_b(☃xxxxxxxx, ☃xxxxxxxxx, ☃);
                  if (☃xxxxxxxxxx) {
                     ++☃xxxxxxx;
                     if (☃xxxxxx == null) {
                        ☃xxxxxx = new ChunkPos(☃xxxxxxxx, ☃xxxxxxxxx);
                     }
                  }
               }
            }

            if (☃xxxxxxx == 0) {
               throw (☃ ? field_212728_c : field_212729_d).create();
            } else {
               if (☃xxxxxxx == 1) {
                  ☃.func_197030_a(new TextComponentTranslation("commands.forceload." + (☃ ? "added" : "removed") + ".single", ☃xxxxxx, ☃xxxx), true);
               } else {
                  ChunkPos ☃xxxxxxxx = new ChunkPos(☃xxxx, ☃xxxxx);
                  ChunkPos ☃xxxxxxxxx = new ChunkPos(☃xxxxxx, ☃xxxxxxx);
                  ☃.func_197030_a(
                     new TextComponentTranslation("commands.forceload." + (☃ ? "added" : "removed") + ".multiple", ☃xxxxxxx, ☃xxxx, ☃xxxxxxxx, ☃xxxxxxxxx),
                     true
                  );
               }

               return ☃xxxxxxx;
            }
         }
      } else {
         throw BlockPosArgument.field_197279_c.create();
      }
   }
}
