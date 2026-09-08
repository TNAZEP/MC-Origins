package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class LocateBiomeCommand {
   public static final DynamicCommandExceptionType ERROR_INVALID_BIOME = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.locatebiome.invalid", var0)
   );
   private static final DynamicCommandExceptionType ERROR_BIOME_NOT_FOUND = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.locatebiome.notFound", var0)
   );
   private static final int MAX_SEARCH_RADIUS = 6400;
   private static final int SEARCH_STEP = 8;

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("locatebiome")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("biome", ResourceLocationArgument.id())
                  .suggests(SuggestionProviders.AVAILABLE_BIOMES)
                  .executes(var0x -> locateBiome(var0x.getSource(), var0x.getArgument("biome", ResourceLocation.class)))
            )
      );
   }

   private static int locateBiome(CommandSourceStack var0, ResourceLocation var1) throws CommandSyntaxException {
      Biome â˜ƒ = (Biome)â˜ƒ.getServer()
         .registryAccess()
         .registryOrThrow(Registry.BIOME_REGISTRY)
         .getOptional(â˜ƒ)
         .orElseThrow(() -> ERROR_INVALID_BIOME.create(â˜ƒ));
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getPosition());
      BlockPos â˜ƒxx = â˜ƒ.getLevel().findNearestBiome(â˜ƒ, â˜ƒx, 6400, 8);
      String â˜ƒxxx = â˜ƒ.toString();
      if (â˜ƒxx == null) {
         throw ERROR_BIOME_NOT_FOUND.create(â˜ƒxxx);
      } else {
         return LocateCommand.showLocateResult(â˜ƒ, â˜ƒxxx, â˜ƒx, â˜ƒxx, "commands.locatebiome.success");
      }
   }
}
