package net.minecraft.server.commands.data;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.function.Function;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.CommandStorage;

public class StorageDataAccessor implements DataAccessor {
   static final SuggestionProvider<CommandSourceStack> SUGGEST_STORAGE = (var0, var1) -> SharedSuggestionProvider.suggestResource(
         getGlobalTags(var0).keys(), var1
      );
   public static final Function<String, DataCommands.DataProvider> PROVIDER = var0 -> new DataCommands.DataProvider() {
         @Override
         public DataAccessor access(CommandContext<CommandSourceStack> var1) {
            return new StorageDataAccessor(StorageDataAccessor.getGlobalTags(â˜ƒ), ResourceLocationArgument.getId(â˜ƒ, â˜ƒ));
         }

         @Override
         public ArgumentBuilder<CommandSourceStack, ?> wrap(
            ArgumentBuilder<CommandSourceStack, ?> var1, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> var2
         ) {
            return â˜ƒ.then(
               Commands.literal("storage")
                  .then(
                     (ArgumentBuilder<CommandSourceStack, ?>)â˜ƒ.apply(
                        Commands.argument(â˜ƒ, ResourceLocationArgument.id()).suggests(StorageDataAccessor.SUGGEST_STORAGE)
                     )
                  )
            );
         }
      };
   private final CommandStorage storage;
   private final ResourceLocation id;

   static CommandStorage getGlobalTags(CommandContext<CommandSourceStack> var0) {
      return â˜ƒ.getSource().getServer().getCommandStorage();
   }

   StorageDataAccessor(CommandStorage var1, ResourceLocation var2) {
      this.storage = â˜ƒ;
      this.id = â˜ƒ;
   }

   @Override
   public void setData(CompoundTag var1) {
      this.storage.set(this.id, â˜ƒ);
   }

   @Override
   public CompoundTag getData() {
      return this.storage.get(this.id);
   }

   @Override
   public Component getModifiedSuccess() {
      return new TranslatableComponent("commands.data.storage.modified", this.id);
   }

   @Override
   public Component getPrintSuccess(Tag var1) {
      return new TranslatableComponent("commands.data.storage.query", this.id, NbtUtils.toPrettyComponent(â˜ƒ));
   }

   @Override
   public Component getPrintSuccess(NbtPathArgument.NbtPath var1, double var2, int var4) {
      return new TranslatableComponent("commands.data.storage.get", â˜ƒ, this.id, String.format(Locale.ROOT, "%.2f", â˜ƒ), â˜ƒ);
   }
}
