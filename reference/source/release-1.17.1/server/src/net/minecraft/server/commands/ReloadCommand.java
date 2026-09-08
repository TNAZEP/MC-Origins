package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.Collection;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReloadCommand {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void reloadPacks(Collection<String> var0, CommandSourceStack var1) {
      â˜ƒ.getServer().reloadResources(â˜ƒ).exceptionally(var1x -> {
         LOGGER.warn("Failed to execute reload", var1x);
         â˜ƒ.sendFailure(new TranslatableComponent("commands.reload.failure"));
         return null;
      });
   }

   private static Collection<String> discoverNewPacks(PackRepository var0, WorldData var1, Collection<String> var2) {
      â˜ƒ.reload();
      Collection<String> â˜ƒ = Lists.newArrayList(â˜ƒ);
      Collection<String> â˜ƒx = â˜ƒ.getDataPackConfig().getDisabled();

      for(String â˜ƒxx : â˜ƒ.getAvailableIds()) {
         if (!â˜ƒx.contains(â˜ƒxx) && !â˜ƒ.contains(â˜ƒxx)) {
            â˜ƒ.add(â˜ƒxx);
         }
      }

      return â˜ƒ;
   }

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(Commands.literal("reload").requires(var0x -> var0x.hasPermission(2)).executes(var0x -> {
         CommandSourceStack â˜ƒ = var0x.getSource();
         MinecraftServer â˜ƒx = â˜ƒ.getServer();
         PackRepository â˜ƒxx = â˜ƒx.getPackRepository();
         WorldData â˜ƒxxx = â˜ƒx.getWorldData();
         Collection<String> â˜ƒxxxx = â˜ƒxx.getSelectedIds();
         Collection<String> â˜ƒxxxxx = discoverNewPacks(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.reload.success"), true);
         reloadPacks(â˜ƒxxxxx, â˜ƒ);
         return 0;
      }));
   }
}
