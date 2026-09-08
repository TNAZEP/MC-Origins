package net.minecraft.server.commands.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class DataCommands {
   private static final SimpleCommandExceptionType ERROR_MERGE_UNCHANGED = new SimpleCommandExceptionType(
      new TranslatableComponent("commands.data.merge.failed")
   );
   private static final DynamicCommandExceptionType ERROR_GET_NOT_NUMBER = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.data.get.invalid", var0)
   );
   private static final DynamicCommandExceptionType ERROR_GET_NON_EXISTENT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.data.get.unknown", var0)
   );
   private static final SimpleCommandExceptionType ERROR_MULTIPLE_TAGS = new SimpleCommandExceptionType(new TranslatableComponent("commands.data.get.multiple"));
   private static final DynamicCommandExceptionType ERROR_EXPECTED_LIST = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.data.modify.expected_list", var0)
   );
   private static final DynamicCommandExceptionType ERROR_EXPECTED_OBJECT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.data.modify.expected_object", var0)
   );
   private static final DynamicCommandExceptionType ERROR_INVALID_INDEX = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.data.modify.invalid_index", var0)
   );
   public static final List<Function<String, DataCommands.DataProvider>> ALL_PROVIDERS = ImmutableList.of(
      EntityDataAccessor.PROVIDER, BlockDataAccessor.PROVIDER, StorageDataAccessor.PROVIDER
   );
   public static final List<DataCommands.DataProvider> TARGET_PROVIDERS = (List<DataCommands.DataProvider>)ALL_PROVIDERS.stream()
      .map(var0 -> (DataCommands.DataProvider)var0.apply("target"))
      .collect(ImmutableList.toImmutableList());
   public static final List<DataCommands.DataProvider> SOURCE_PROVIDERS = (List<DataCommands.DataProvider>)ALL_PROVIDERS.stream()
      .map(var0 -> (DataCommands.DataProvider)var0.apply("source"))
      .collect(ImmutableList.toImmutableList());

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("data").requires(var0x -> var0x.hasPermission(2));

      for(DataCommands.DataProvider â˜ƒx : TARGET_PROVIDERS) {
         â˜ƒ.then(
               â˜ƒx.wrap(
                  Commands.literal("merge"),
                  var1x -> var1x.then(
                        Commands.argument("nbt", CompoundTagArgument.compoundTag())
                           .executes(var1xx -> mergeData(var1xx.getSource(), â˜ƒ.access(var1xx), CompoundTagArgument.getCompoundTag(var1xx, "nbt")))
                     )
               )
            )
            .then(
               â˜ƒx.wrap(
                  Commands.literal("get"),
                  var1x -> var1x.executes(var1xx -> getData((CommandSourceStack)var1xx.getSource(), â˜ƒ.access(var1xx)))
                        .then(
                           Commands.argument("path", NbtPathArgument.nbtPath())
                              .executes(var1xx -> getData(var1xx.getSource(), â˜ƒ.access(var1xx), NbtPathArgument.getPath(var1xx, "path")))
                              .then(
                                 Commands.argument("scale", DoubleArgumentType.doubleArg())
                                    .executes(
                                       var1xx -> getNumeric(
                                             var1xx.getSource(),
                                             â˜ƒ.access(var1xx),
                                             NbtPathArgument.getPath(var1xx, "path"),
                                             DoubleArgumentType.getDouble(var1xx, "scale")
                                          )
                                    )
                              )
                        )
               )
            )
            .then(
               â˜ƒx.wrap(
                  Commands.literal("remove"),
                  var1x -> var1x.then(
                        Commands.argument("path", NbtPathArgument.nbtPath())
                           .executes(var1xx -> removeData(var1xx.getSource(), â˜ƒ.access(var1xx), NbtPathArgument.getPath(var1xx, "path")))
                     )
               )
            )
            .then(
               decorateModification(
                  (var0x, var1x) -> var0x.then(
                           Commands.literal("insert")
                              .then(Commands.argument("index", IntegerArgumentType.integer()).then(var1x.create((var0xx, var1xx, var2, var3x) -> {
                                 int â˜ƒ = IntegerArgumentType.getInteger(var0xx, "index");
                                 return insertAtIndex(â˜ƒ, var1xx, var2, var3x);
                              })))
                        )
                        .then(Commands.literal("prepend").then(var1x.create((var0xx, var1xx, var2, var3x) -> insertAtIndex(0, var1xx, var2, var3x))))
                        .then(Commands.literal("append").then(var1x.create((var0xx, var1xx, var2, var3x) -> insertAtIndex(-1, var1xx, var2, var3x))))
                        .then(Commands.literal("set").then(var1x.create((var0xx, var1xx, var2, var3x) -> var2.set(var1xx, Iterables.getLast(var3x)::copy))))
                        .then(Commands.literal("merge").then(var1x.create((var0xx, var1xx, var2, var3x) -> {
                           Collection<Tag> â˜ƒ = var2.getOrCreate(var1xx, CompoundTag::new);
                           int â˜ƒx = 0;
            
                           for(Tag â˜ƒxx : â˜ƒ) {
                              if (!(â˜ƒxx instanceof CompoundTag)) {
                                 throw ERROR_EXPECTED_OBJECT.create(â˜ƒxx);
                              }
            
                              CompoundTag â˜ƒxxx = (CompoundTag)â˜ƒxx;
                              CompoundTag â˜ƒxxxx = â˜ƒxxx.copy();
            
                              for(Tag â˜ƒxxxxx : var3x) {
                                 if (!(â˜ƒxxxxx instanceof CompoundTag)) {
                                    throw ERROR_EXPECTED_OBJECT.create(â˜ƒxxxxx);
                                 }
            
                                 â˜ƒxxx.merge((CompoundTag)â˜ƒxxxxx);
                              }
            
                              â˜ƒx += â˜ƒxxxx.equals(â˜ƒxxx) ? 0 : 1;
                           }
            
                           return â˜ƒx;
                        })))
               )
            );
      }

      â˜ƒ.register(â˜ƒ);
   }

   private static int insertAtIndex(int var0, CompoundTag var1, NbtPathArgument.NbtPath var2, List<Tag> var3) throws CommandSyntaxException {
      Collection<Tag> â˜ƒ = â˜ƒ.getOrCreate(â˜ƒ, ListTag::new);
      int â˜ƒx = 0;

      for(Tag â˜ƒxx : â˜ƒ) {
         if (!(â˜ƒxx instanceof CollectionTag)) {
            throw ERROR_EXPECTED_LIST.create(â˜ƒxx);
         }

         boolean â˜ƒxxx = false;
         CollectionTag<?> â˜ƒxxxx = (CollectionTag)â˜ƒxx;
         int â˜ƒxxxxx = â˜ƒ < 0 ? â˜ƒxxxx.size() + â˜ƒ + 1 : â˜ƒ;

         for(Tag â˜ƒxxxxxx : â˜ƒ) {
            try {
               if (â˜ƒxxxx.addTag(â˜ƒxxxxx, â˜ƒxxxxxx.copy())) {
                  ++â˜ƒxxxxx;
                  â˜ƒxxx = true;
               }
            } catch (IndexOutOfBoundsException var14) {
               throw ERROR_INVALID_INDEX.create(â˜ƒxxxxx);
            }
         }

         â˜ƒx += â˜ƒxxx ? 1 : 0;
      }

      return â˜ƒx;
   }

   private static ArgumentBuilder<CommandSourceStack, ?> decorateModification(
      BiConsumer<ArgumentBuilder<CommandSourceStack, ?>, DataCommands.DataManipulatorDecorator> var0
   ) {
      LiteralArgumentBuilder<CommandSourceStack> â˜ƒ = Commands.literal("modify");

      for(DataCommands.DataProvider â˜ƒx : TARGET_PROVIDERS) {
         â˜ƒx.wrap(
            â˜ƒ,
            var2 -> {
               ArgumentBuilder<CommandSourceStack, ?> â˜ƒ = Commands.argument("targetPath", NbtPathArgument.nbtPath());
   
               for(DataCommands.DataProvider â˜ƒx : SOURCE_PROVIDERS) {
                  â˜ƒ.accept(â˜ƒ, (DataCommands.DataManipulatorDecorator)var2x -> â˜ƒ.wrap(Commands.literal("from"), var3x -> var3x.executes(var3xx -> {
                           List<Tag> â˜ƒ = Collections.singletonList(â˜ƒ.access(var3xx).getData());
                           return manipulateData(var3xx, â˜ƒ, var2x, â˜ƒ);
                        }).then(Commands.argument("sourcePath", NbtPathArgument.nbtPath()).executes(var3xx -> {
                           DataAccessor â˜ƒ = â˜ƒ.access(var3xx);
                           NbtPathArgument.NbtPath â˜ƒx = NbtPathArgument.getPath(var3xx, "sourcePath");
                           List<Tag> â˜ƒxx = â˜ƒx.get(â˜ƒ.getData());
                           return manipulateData(var3xx, â˜ƒ, var2x, â˜ƒxx);
                        }))));
               }
   
               â˜ƒ.accept(
                  â˜ƒ,
                  (DataCommands.DataManipulatorDecorator)var1x -> Commands.literal("value")
                        .then(Commands.argument("value", NbtTagArgument.nbtTag()).executes(var2x -> {
                           List<Tag> â˜ƒ = Collections.singletonList(NbtTagArgument.getNbtTag(var2x, "value"));
                           return manipulateData(var2x, â˜ƒ, var1x, â˜ƒ);
                        }))
               );
               return var2.then(â˜ƒ);
            }
         );
      }

      return â˜ƒ;
   }

   private static int manipulateData(CommandContext<CommandSourceStack> var0, DataCommands.DataProvider var1, DataCommands.DataManipulator var2, List<Tag> var3) throws CommandSyntaxException {
      DataAccessor â˜ƒ = â˜ƒ.access(â˜ƒ);
      NbtPathArgument.NbtPath â˜ƒx = NbtPathArgument.getPath(â˜ƒ, "targetPath");
      CompoundTag â˜ƒxx = â˜ƒ.getData();
      int â˜ƒxxx = â˜ƒ.modify(â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒ);
      if (â˜ƒxxx == 0) {
         throw ERROR_MERGE_UNCHANGED.create();
      } else {
         â˜ƒ.setData(â˜ƒxx);
         â˜ƒ.getSource().sendSuccess(â˜ƒ.getModifiedSuccess(), true);
         return â˜ƒxxx;
      }
   }

   private static int removeData(CommandSourceStack var0, DataAccessor var1, NbtPathArgument.NbtPath var2) throws CommandSyntaxException {
      CompoundTag â˜ƒ = â˜ƒ.getData();
      int â˜ƒx = â˜ƒ.remove(â˜ƒ);
      if (â˜ƒx == 0) {
         throw ERROR_MERGE_UNCHANGED.create();
      } else {
         â˜ƒ.setData(â˜ƒ);
         â˜ƒ.sendSuccess(â˜ƒ.getModifiedSuccess(), true);
         return â˜ƒx;
      }
   }

   private static Tag getSingleTag(NbtPathArgument.NbtPath var0, DataAccessor var1) throws CommandSyntaxException {
      Collection<Tag> â˜ƒ = â˜ƒ.get(â˜ƒ.getData());
      Iterator<Tag> â˜ƒx = â˜ƒ.iterator();
      Tag â˜ƒxx = (Tag)â˜ƒx.next();
      if (â˜ƒx.hasNext()) {
         throw ERROR_MULTIPLE_TAGS.create();
      } else {
         return â˜ƒxx;
      }
   }

   private static int getData(CommandSourceStack var0, DataAccessor var1, NbtPathArgument.NbtPath var2) throws CommandSyntaxException {
      Tag â˜ƒx = getSingleTag(â˜ƒ, â˜ƒ);
      int â˜ƒ;
      if (â˜ƒx instanceof NumericTag) {
         â˜ƒ = Mth.floor(((NumericTag)â˜ƒx).getAsDouble());
      } else if (â˜ƒx instanceof CollectionTag) {
         â˜ƒ = ((CollectionTag)â˜ƒx).size();
      } else if (â˜ƒx instanceof CompoundTag) {
         â˜ƒ = ((CompoundTag)â˜ƒx).size();
      } else {
         if (!(â˜ƒx instanceof StringTag)) {
            throw ERROR_GET_NON_EXISTENT.create(â˜ƒ.toString());
         }

         â˜ƒ = â˜ƒx.getAsString().length();
      }

      â˜ƒ.sendSuccess(â˜ƒ.getPrintSuccess(â˜ƒx), false);
      return â˜ƒ;
   }

   private static int getNumeric(CommandSourceStack var0, DataAccessor var1, NbtPathArgument.NbtPath var2, double var3) throws CommandSyntaxException {
      Tag â˜ƒ = getSingleTag(â˜ƒ, â˜ƒ);
      if (!(â˜ƒ instanceof NumericTag)) {
         throw ERROR_GET_NOT_NUMBER.create(â˜ƒ.toString());
      } else {
         int â˜ƒ = Mth.floor(((NumericTag)â˜ƒ).getAsDouble() * â˜ƒ);
         â˜ƒ.sendSuccess(â˜ƒ.getPrintSuccess(â˜ƒ, â˜ƒ, â˜ƒ), false);
         return â˜ƒ;
      }
   }

   private static int getData(CommandSourceStack var0, DataAccessor var1) throws CommandSyntaxException {
      â˜ƒ.sendSuccess(â˜ƒ.getPrintSuccess(â˜ƒ.getData()), false);
      return 1;
   }

   private static int mergeData(CommandSourceStack var0, DataAccessor var1, CompoundTag var2) throws CommandSyntaxException {
      CompoundTag â˜ƒ = â˜ƒ.getData();
      CompoundTag â˜ƒx = â˜ƒ.copy().merge(â˜ƒ);
      if (â˜ƒ.equals(â˜ƒx)) {
         throw ERROR_MERGE_UNCHANGED.create();
      } else {
         â˜ƒ.setData(â˜ƒx);
         â˜ƒ.sendSuccess(â˜ƒ.getModifiedSuccess(), true);
         return 1;
      }
   }

   interface DataManipulator {
      int modify(CommandContext<CommandSourceStack> var1, CompoundTag var2, NbtPathArgument.NbtPath var3, List<Tag> var4) throws CommandSyntaxException;
   }

   interface DataManipulatorDecorator {
      ArgumentBuilder<CommandSourceStack, ?> create(DataCommands.DataManipulator var1);
   }

   public interface DataProvider {
      DataAccessor access(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;

      ArgumentBuilder<CommandSourceStack, ?> wrap(
         ArgumentBuilder<CommandSourceStack, ?> var1, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> var2
      );
   }
}
