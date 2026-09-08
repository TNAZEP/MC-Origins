package net.minecraft.server.commands;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ColumnPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class ForceLoadCommand {
   private static final int MAX_CHUNK_LIMIT = 256;
   private static final Dynamic2CommandExceptionType ERROR_TOO_MANY_CHUNKS = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.forceload.toobig", var0, var1)
   );
   private static final Dynamic2CommandExceptionType ERROR_NOT_TICKING = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.forceload.query.failure", var0, var1)
   );
   private static final SimpleCommandExceptionType ERROR_ALL_ADDED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.forceload.added.failure")
   );
   private static final SimpleCommandExceptionType ERROR_NONE_REMOVED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.forceload.removed.failure")
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("forceload")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("add")
                  .then(
                     Commands.argument("from", ColumnPosArgument.columnPos())
                        .executes(
                           var0x -> changeForceLoad(
                                 var0x.getSource(), ColumnPosArgument.getColumnPos(var0x, "from"), ColumnPosArgument.getColumnPos(var0x, "from"), true
                              )
                        )
                        .then(
                           Commands.argument("to", ColumnPosArgument.columnPos())
                              .executes(
                                 var0x -> changeForceLoad(
                                       var0x.getSource(), ColumnPosArgument.getColumnPos(var0x, "from"), ColumnPosArgument.getColumnPos(var0x, "to"), true
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("remove")
                  .then(
                     Commands.argument("from", ColumnPosArgument.columnPos())
                        .executes(
                           var0x -> changeForceLoad(
                                 var0x.getSource(), ColumnPosArgument.getColumnPos(var0x, "from"), ColumnPosArgument.getColumnPos(var0x, "from"), false
                              )
                        )
                        .then(
                           Commands.argument("to", ColumnPosArgument.columnPos())
                              .executes(
                                 var0x -> changeForceLoad(
                                       var0x.getSource(), ColumnPosArgument.getColumnPos(var0x, "from"), ColumnPosArgument.getColumnPos(var0x, "to"), false
                                    )
                              )
                        )
                  )
                  .then(Commands.literal("all").executes(var0x -> removeAll(var0x.getSource())))
            )
            .then(
               Commands.literal("query")
                  .executes(var0x -> listForceLoad(var0x.getSource()))
                  .then(
                     Commands.argument("pos", ColumnPosArgument.columnPos())
                        .executes(var0x -> queryForceLoad(var0x.getSource(), ColumnPosArgument.getColumnPos(var0x, "pos")))
                  )
            )
      );
   }

   private static int queryForceLoad(CommandSourceStack var0, ColumnPos var1) throws CommandSyntaxException {
      ChunkPos â˜ƒ = new ChunkPos(SectionPos.blockToSectionCoord(â˜ƒ.x), SectionPos.blockToSectionCoord(â˜ƒ.z));
      ServerLevel â˜ƒx = â˜ƒ.getLevel();
      ResourceKey<Level> â˜ƒxx = â˜ƒx.dimension();
      boolean â˜ƒxxx = â˜ƒx.getForcedChunks().contains(â˜ƒ.toLong());
      if (â˜ƒxxx) {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.forceload.query.success", â˜ƒ, â˜ƒxx.location()), false);
         return 1;
      } else {
         throw ERROR_NOT_TICKING.create(â˜ƒ, â˜ƒxx.location());
      }
   }

   private static int listForceLoad(CommandSourceStack var0) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      ResourceKey<Level> â˜ƒx = â˜ƒ.dimension();
      LongSet â˜ƒxx = â˜ƒ.getForcedChunks();
      int â˜ƒxxx = â˜ƒxx.size();
      if (â˜ƒxxx > 0) {
         String â˜ƒxxxx = Joiner.on(", ").join(â˜ƒxx.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
         if (â˜ƒxxx == 1) {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.forceload.list.single", â˜ƒx.location(), â˜ƒxxxx), false);
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.forceload.list.multiple", â˜ƒxxx, â˜ƒx.location(), â˜ƒxxxx), false);
         }
      } else {
         â˜ƒ.sendFailure(new TranslatableComponent("commands.forceload.added.none", â˜ƒx.location()));
      }

      return â˜ƒxxx;
   }

   private static int removeAll(CommandSourceStack var0) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      ResourceKey<Level> â˜ƒx = â˜ƒ.dimension();
      LongSet â˜ƒxx = â˜ƒ.getForcedChunks();
      â˜ƒxx.forEach(var1x -> â˜ƒ.setChunkForced(ChunkPos.getX(var1x), ChunkPos.getZ(var1x), false));
      â˜ƒ.sendSuccess(new TranslatableComponent("commands.forceload.removed.all", â˜ƒx.location()), true);
      return 0;
   }

   private static int changeForceLoad(CommandSourceStack var0, ColumnPos var1, ColumnPos var2, boolean var3) throws CommandSyntaxException {
      int â˜ƒ = Math.min(â˜ƒ.x, â˜ƒ.x);
      int â˜ƒx = Math.min(â˜ƒ.z, â˜ƒ.z);
      int â˜ƒxx = Math.max(â˜ƒ.x, â˜ƒ.x);
      int â˜ƒxxx = Math.max(â˜ƒ.z, â˜ƒ.z);
      if (â˜ƒ >= -30000000 && â˜ƒx >= -30000000 && â˜ƒxx < 30000000 && â˜ƒxxx < 30000000) {
         int â˜ƒxxxx = SectionPos.blockToSectionCoord(â˜ƒ);
         int â˜ƒxxxxx = SectionPos.blockToSectionCoord(â˜ƒx);
         int â˜ƒxxxxxx = SectionPos.blockToSectionCoord(â˜ƒxx);
         int â˜ƒxxxxxxx = SectionPos.blockToSectionCoord(â˜ƒxxx);
         long â˜ƒxxxxxxxx = ((long)(â˜ƒxxxxxx - â˜ƒxxxx) + 1L) * ((long)(â˜ƒxxxxxxx - â˜ƒxxxxx) + 1L);
         if (â˜ƒxxxxxxxx > 256L) {
            throw ERROR_TOO_MANY_CHUNKS.create(256, â˜ƒxxxxxxxx);
         } else {
            ServerLevel â˜ƒxxxx = â˜ƒ.getLevel();
            ResourceKey<Level> â˜ƒxxxxx = â˜ƒxxxx.dimension();
            ChunkPos â˜ƒxxxxxx = null;
            int â˜ƒxxxxxxx = 0;

            for(int â˜ƒxxxxxxxx = â˜ƒxxxx; â˜ƒxxxxxxxx <= â˜ƒxxxxxx; ++â˜ƒxxxxxxxx) {
               for(int â˜ƒxxxxxxxxx = â˜ƒxxxxx; â˜ƒxxxxxxxxx <= â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxx) {
                  boolean â˜ƒxxxxxxxxxx = â˜ƒxxxx.setChunkForced(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒ);
                  if (â˜ƒxxxxxxxxxx) {
                     ++â˜ƒxxxxxxx;
                     if (â˜ƒxxxxxx == null) {
                        â˜ƒxxxxxx = new ChunkPos(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
                     }
                  }
               }
            }

            if (â˜ƒxxxxxxx == 0) {
               throw (â˜ƒ ? ERROR_ALL_ADDED : ERROR_NONE_REMOVED).create();
            } else {
               if (â˜ƒxxxxxxx == 1) {
                  â˜ƒ.sendSuccess(
                     new TranslatableComponent("commands.forceload." + (â˜ƒ ? "added" : "removed") + ".single", â˜ƒxxxxxx, â˜ƒxxxxx.location()), true
                  );
               } else {
                  ChunkPos â˜ƒxxxxxxxx = new ChunkPos(â˜ƒxxxx, â˜ƒxxxxx);
                  ChunkPos â˜ƒxxxxxxxxx = new ChunkPos(â˜ƒxxxxxx, â˜ƒxxxxxxx);
                  â˜ƒ.sendSuccess(
                     new TranslatableComponent(
                        "commands.forceload." + (â˜ƒ ? "added" : "removed") + ".multiple", â˜ƒxxxxxxx, â˜ƒxxxxx.location(), â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx
                     ),
                     true
                  );
               }

               return â˜ƒxxxxxxx;
            }
         }
      } else {
         throw BlockPosArgument.ERROR_OUT_OF_WORLD.create();
      }
   }
}
