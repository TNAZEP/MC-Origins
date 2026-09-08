package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LootCommand {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_LOOT_TABLE = (var0, var1) -> {
      LootTables â˜ƒ = var0.getSource().getServer().getLootTables();
      return SharedSuggestionProvider.suggestResource(â˜ƒ.getIds(), var1);
   };
   private static final DynamicCommandExceptionType ERROR_NO_HELD_ITEMS = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.drop.no_held_items", var0)
   );
   private static final DynamicCommandExceptionType ERROR_NO_LOOT_TABLE = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.drop.no_loot_table", var0)
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         addTargets(
            Commands.literal("loot").requires(var0x -> var0x.hasPermission(2)),
            (var0x, var1) -> var0x.then(
                     Commands.literal("fish")
                        .then(
                           Commands.argument("loot_table", ResourceLocationArgument.id())
                              .suggests(SUGGEST_LOOT_TABLE)
                              .then(
                                 Commands.argument("pos", BlockPosArgument.blockPos())
                                    .executes(
                                       var1x -> dropFishingLoot(
                                             var1x,
                                             ResourceLocationArgument.getId(var1x, "loot_table"),
                                             BlockPosArgument.getLoadedBlockPos(var1x, "pos"),
                                             ItemStack.EMPTY,
                                             var1
                                          )
                                    )
                                    .then(
                                       Commands.argument("tool", ItemArgument.item())
                                          .executes(
                                             var1x -> dropFishingLoot(
                                                   var1x,
                                                   ResourceLocationArgument.getId(var1x, "loot_table"),
                                                   BlockPosArgument.getLoadedBlockPos(var1x, "pos"),
                                                   ItemArgument.getItem(var1x, "tool").createItemStack(1, false),
                                                   var1
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.literal("mainhand")
                                          .executes(
                                             var1x -> dropFishingLoot(
                                                   var1x,
                                                   ResourceLocationArgument.getId(var1x, "loot_table"),
                                                   BlockPosArgument.getLoadedBlockPos(var1x, "pos"),
                                                   getSourceHandItem(var1x.getSource(), EquipmentSlot.MAINHAND),
                                                   var1
                                                )
                                          )
                                    )
                                    .then(
                                       Commands.literal("offhand")
                                          .executes(
                                             var1x -> dropFishingLoot(
                                                   var1x,
                                                   ResourceLocationArgument.getId(var1x, "loot_table"),
                                                   BlockPosArgument.getLoadedBlockPos(var1x, "pos"),
                                                   getSourceHandItem(var1x.getSource(), EquipmentSlot.OFFHAND),
                                                   var1
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.literal("loot")
                        .then(
                           Commands.argument("loot_table", ResourceLocationArgument.id())
                              .suggests(SUGGEST_LOOT_TABLE)
                              .executes(var1x -> dropChestLoot(var1x, ResourceLocationArgument.getId(var1x, "loot_table"), var1))
                        )
                  )
                  .then(
                     Commands.literal("kill")
                        .then(
                           Commands.argument("target", EntityArgument.entity())
                              .executes(var1x -> dropKillLoot(var1x, EntityArgument.getEntity(var1x, "target"), var1))
                        )
                  )
                  .then(
                     Commands.literal("mine")
                        .then(
                           Commands.argument("pos", BlockPosArgument.blockPos())
                              .executes(var1x -> dropBlockLoot(var1x, BlockPosArgument.getLoadedBlockPos(var1x, "pos"), ItemStack.EMPTY, var1))
                              .then(
                                 Commands.argument("tool", ItemArgument.item())
                                    .executes(
                                       var1x -> dropBlockLoot(
                                             var1x,
                                             BlockPosArgument.getLoadedBlockPos(var1x, "pos"),
                                             ItemArgument.getItem(var1x, "tool").createItemStack(1, false),
                                             var1
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("mainhand")
                                    .executes(
                                       var1x -> dropBlockLoot(
                                             var1x,
                                             BlockPosArgument.getLoadedBlockPos(var1x, "pos"),
                                             getSourceHandItem(var1x.getSource(), EquipmentSlot.MAINHAND),
                                             var1
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("offhand")
                                    .executes(
                                       var1x -> dropBlockLoot(
                                             var1x,
                                             BlockPosArgument.getLoadedBlockPos(var1x, "pos"),
                                             getSourceHandItem(var1x.getSource(), EquipmentSlot.OFFHAND),
                                             var1
                                          )
                                    )
                              )
                        )
                  )
         )
      );
   }

   private static <T extends ArgumentBuilder<CommandSourceStack, T>> T addTargets(T var0, LootCommand.TailProvider var1) {
      return â˜ƒ.then(
            Commands.literal("replace")
               .then(
                  Commands.literal("entity")
                     .then(
                        Commands.argument("entities", EntityArgument.entities())
                           .then(
                              â˜ƒ.construct(
                                    Commands.argument("slot", SlotArgument.slot()),
                                    (var0x, var1x, var2) -> entityReplace(
                                          EntityArgument.getEntities(var0x, "entities"), SlotArgument.getSlot(var0x, "slot"), var1x.size(), var1x, var2
                                       )
                                 )
                                 .then(
                                    â˜ƒ.construct(
                                       Commands.argument("count", IntegerArgumentType.integer(0)),
                                       (var0x, var1x, var2) -> entityReplace(
                                             EntityArgument.getEntities(var0x, "entities"),
                                             SlotArgument.getSlot(var0x, "slot"),
                                             IntegerArgumentType.getInteger(var0x, "count"),
                                             var1x,
                                             var2
                                          )
                                    )
                                 )
                           )
                     )
               )
               .then(
                  Commands.literal("block")
                     .then(
                        Commands.argument("targetPos", BlockPosArgument.blockPos())
                           .then(
                              â˜ƒ.construct(
                                    Commands.argument("slot", SlotArgument.slot()),
                                    (var0x, var1x, var2) -> blockReplace(
                                          var0x.getSource(),
                                          BlockPosArgument.getLoadedBlockPos(var0x, "targetPos"),
                                          SlotArgument.getSlot(var0x, "slot"),
                                          var1x.size(),
                                          var1x,
                                          var2
                                       )
                                 )
                                 .then(
                                    â˜ƒ.construct(
                                       Commands.argument("count", IntegerArgumentType.integer(0)),
                                       (var0x, var1x, var2) -> blockReplace(
                                             var0x.getSource(),
                                             BlockPosArgument.getLoadedBlockPos(var0x, "targetPos"),
                                             IntegerArgumentType.getInteger(var0x, "slot"),
                                             IntegerArgumentType.getInteger(var0x, "count"),
                                             var1x,
                                             var2
                                          )
                                    )
                                 )
                           )
                     )
               )
         )
         .then(
            Commands.literal("insert")
               .then(
                  â˜ƒ.construct(
                     Commands.argument("targetPos", BlockPosArgument.blockPos()),
                     (var0x, var1x, var2) -> blockDistribute(var0x.getSource(), BlockPosArgument.getLoadedBlockPos(var0x, "targetPos"), var1x, var2)
                  )
               )
         )
         .then(
            Commands.literal("give")
               .then(
                  â˜ƒ.construct(
                     Commands.argument("players", EntityArgument.players()),
                     (var0x, var1x, var2) -> playerGive(EntityArgument.getPlayers(var0x, "players"), var1x, var2)
                  )
               )
         )
         .then(
            Commands.literal("spawn")
               .then(
                  â˜ƒ.construct(
                     Commands.argument("targetPos", Vec3Argument.vec3()),
                     (var0x, var1x, var2) -> dropInWorld(var0x.getSource(), Vec3Argument.getVec3(var0x, "targetPos"), var1x, var2)
                  )
               )
         );
   }

   private static Container getContainer(CommandSourceStack var0, BlockPos var1) throws CommandSyntaxException {
      BlockEntity â˜ƒ = â˜ƒ.getLevel().getBlockEntity(â˜ƒ);
      if (!(â˜ƒ instanceof Container)) {
         throw ItemCommands.ERROR_TARGET_NOT_A_CONTAINER.create(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      } else {
         return (Container)â˜ƒ;
      }
   }

   private static int blockDistribute(CommandSourceStack var0, BlockPos var1, List<ItemStack> var2, LootCommand.Callback var3) throws CommandSyntaxException {
      Container â˜ƒ = getContainer(â˜ƒ, â˜ƒ);
      List<ItemStack> â˜ƒx = Lists.<ItemStack>newArrayListWithCapacity(â˜ƒ.size());

      for(ItemStack â˜ƒxx : â˜ƒ) {
         if (distributeToContainer(â˜ƒ, â˜ƒxx.copy())) {
            â˜ƒ.setChanged();
            â˜ƒx.add(â˜ƒxx);
         }
      }

      â˜ƒ.accept(â˜ƒx);
      return â˜ƒx.size();
   }

   private static boolean distributeToContainer(Container var0, ItemStack var1) {
      boolean â˜ƒ = false;

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getContainerSize() && !â˜ƒ.isEmpty(); ++â˜ƒx) {
         ItemStack â˜ƒxx = â˜ƒ.getItem(â˜ƒx);
         if (â˜ƒ.canPlaceItem(â˜ƒx, â˜ƒ)) {
            if (â˜ƒxx.isEmpty()) {
               â˜ƒ.setItem(â˜ƒx, â˜ƒ);
               â˜ƒ = true;
               break;
            }

            if (canMergeItems(â˜ƒxx, â˜ƒ)) {
               int â˜ƒxxx = â˜ƒ.getMaxStackSize() - â˜ƒxx.getCount();
               int â˜ƒxxxx = Math.min(â˜ƒ.getCount(), â˜ƒxxx);
               â˜ƒ.shrink(â˜ƒxxxx);
               â˜ƒxx.grow(â˜ƒxxxx);
               â˜ƒ = true;
            }
         }
      }

      return â˜ƒ;
   }

   private static int blockReplace(CommandSourceStack var0, BlockPos var1, int var2, int var3, List<ItemStack> var4, LootCommand.Callback var5) throws CommandSyntaxException {
      Container â˜ƒ = getContainer(â˜ƒ, â˜ƒ);
      int â˜ƒx = â˜ƒ.getContainerSize();
      if (â˜ƒ >= 0 && â˜ƒ < â˜ƒx) {
         List<ItemStack> â˜ƒxx = Lists.<ItemStack>newArrayListWithCapacity(â˜ƒ.size());

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
            int â˜ƒxxxx = â˜ƒ + â˜ƒxxx;
            ItemStack â˜ƒxxxxx = â˜ƒxxx < â˜ƒ.size() ? (ItemStack)â˜ƒ.get(â˜ƒxxx) : ItemStack.EMPTY;
            if (â˜ƒ.canPlaceItem(â˜ƒxxxx, â˜ƒxxxxx)) {
               â˜ƒ.setItem(â˜ƒxxxx, â˜ƒxxxxx);
               â˜ƒxx.add(â˜ƒxxxxx);
            }
         }

         â˜ƒ.accept(â˜ƒxx);
         return â˜ƒxx.size();
      } else {
         throw ItemCommands.ERROR_TARGET_INAPPLICABLE_SLOT.create(â˜ƒ);
      }
   }

   private static boolean canMergeItems(ItemStack var0, ItemStack var1) {
      return â˜ƒ.is(â˜ƒ.getItem())
         && â˜ƒ.getDamageValue() == â˜ƒ.getDamageValue()
         && â˜ƒ.getCount() <= â˜ƒ.getMaxStackSize()
         && Objects.equals(â˜ƒ.getTag(), â˜ƒ.getTag());
   }

   private static int playerGive(Collection<ServerPlayer> var0, List<ItemStack> var1, LootCommand.Callback var2) throws CommandSyntaxException {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayListWithCapacity(â˜ƒ.size());

      for(ItemStack â˜ƒx : â˜ƒ) {
         for(ServerPlayer â˜ƒxx : â˜ƒ) {
            if (â˜ƒxx.getInventory().add(â˜ƒx.copy())) {
               â˜ƒ.add(â˜ƒx);
            }
         }
      }

      â˜ƒ.accept(â˜ƒ);
      return â˜ƒ.size();
   }

   private static void setSlots(Entity var0, List<ItemStack> var1, int var2, int var3, List<ItemStack> var4) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         ItemStack â˜ƒx = â˜ƒ < â˜ƒ.size() ? (ItemStack)â˜ƒ.get(â˜ƒ) : ItemStack.EMPTY;
         SlotAccess â˜ƒxx = â˜ƒ.getSlot(â˜ƒ + â˜ƒ);
         if (â˜ƒxx != SlotAccess.NULL && â˜ƒxx.set(â˜ƒx.copy())) {
            â˜ƒ.add(â˜ƒx);
         }
      }
   }

   private static int entityReplace(Collection<? extends Entity> var0, int var1, int var2, List<ItemStack> var3, LootCommand.Callback var4) throws CommandSyntaxException {
      List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayListWithCapacity(â˜ƒ.size());

      for(Entity â˜ƒx : â˜ƒ) {
         if (â˜ƒx instanceof ServerPlayer â˜ƒxx) {
            setSlots(â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒxx.containerMenu.broadcastChanges();
         } else {
            setSlots(â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      â˜ƒ.accept(â˜ƒ);
      return â˜ƒ.size();
   }

   private static int dropInWorld(CommandSourceStack var0, Vec3 var1, List<ItemStack> var2, LootCommand.Callback var3) throws CommandSyntaxException {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      â˜ƒ.forEach(var2x -> {
         ItemEntity â˜ƒ = new ItemEntity(â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, var2x.copy());
         â˜ƒ.setDefaultPickUpDelay();
         â˜ƒ.addFreshEntity(â˜ƒ);
      });
      â˜ƒ.accept(â˜ƒ);
      return â˜ƒ.size();
   }

   private static void callback(CommandSourceStack var0, List<ItemStack> var1) {
      if (â˜ƒ.size() == 1) {
         ItemStack â˜ƒ = (ItemStack)â˜ƒ.get(0);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.drop.success.single", â˜ƒ.getCount(), â˜ƒ.getDisplayName()), false);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.drop.success.multiple", â˜ƒ.size()), false);
      }
   }

   private static void callback(CommandSourceStack var0, List<ItemStack> var1, ResourceLocation var2) {
      if (â˜ƒ.size() == 1) {
         ItemStack â˜ƒ = (ItemStack)â˜ƒ.get(0);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.drop.success.single_with_table", â˜ƒ.getCount(), â˜ƒ.getDisplayName(), â˜ƒ), false);
      } else {
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.drop.success.multiple_with_table", â˜ƒ.size(), â˜ƒ), false);
      }
   }

   private static ItemStack getSourceHandItem(CommandSourceStack var0, EquipmentSlot var1) throws CommandSyntaxException {
      Entity â˜ƒ = â˜ƒ.getEntityOrException();
      if (â˜ƒ instanceof LivingEntity) {
         return ((LivingEntity)â˜ƒ).getItemBySlot(â˜ƒ);
      } else {
         throw ERROR_NO_HELD_ITEMS.create(â˜ƒ.getDisplayName());
      }
   }

   private static int dropBlockLoot(CommandContext<CommandSourceStack> var0, BlockPos var1, ItemStack var2, LootCommand.DropConsumer var3) throws CommandSyntaxException {
      CommandSourceStack â˜ƒ = â˜ƒ.getSource();
      ServerLevel â˜ƒx = â˜ƒ.getLevel();
      BlockState â˜ƒxx = â˜ƒx.getBlockState(â˜ƒ);
      BlockEntity â˜ƒxxx = â˜ƒx.getBlockEntity(â˜ƒ);
      LootContext.Builder â˜ƒxxxx = new LootContext.Builder(â˜ƒx)
         .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(â˜ƒ))
         .withParameter(LootContextParams.BLOCK_STATE, â˜ƒxx)
         .withOptionalParameter(LootContextParams.BLOCK_ENTITY, â˜ƒxxx)
         .withOptionalParameter(LootContextParams.THIS_ENTITY, â˜ƒ.getEntity())
         .withParameter(LootContextParams.TOOL, â˜ƒ);
      List<ItemStack> â˜ƒxxxxx = â˜ƒxx.getDrops(â˜ƒxxxx);
      return â˜ƒ.accept(â˜ƒ, â˜ƒxxxxx, var2x -> callback(â˜ƒ, var2x, â˜ƒ.getBlock().getLootTable()));
   }

   private static int dropKillLoot(CommandContext<CommandSourceStack> var0, Entity var1, LootCommand.DropConsumer var2) throws CommandSyntaxException {
      if (!(â˜ƒ instanceof LivingEntity)) {
         throw ERROR_NO_LOOT_TABLE.create(â˜ƒ.getDisplayName());
      } else {
         ResourceLocation â˜ƒ = ((LivingEntity)â˜ƒ).getLootTable();
         CommandSourceStack â˜ƒx = â˜ƒ.getSource();
         LootContext.Builder â˜ƒxx = new LootContext.Builder(â˜ƒx.getLevel());
         Entity â˜ƒxxx = â˜ƒx.getEntity();
         if (â˜ƒxxx instanceof Player) {
            â˜ƒxx.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, (Player)â˜ƒxxx);
         }

         â˜ƒxx.withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.MAGIC);
         â˜ƒxx.withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, â˜ƒxxx);
         â˜ƒxx.withOptionalParameter(LootContextParams.KILLER_ENTITY, â˜ƒxxx);
         â˜ƒxx.withParameter(LootContextParams.THIS_ENTITY, â˜ƒ);
         â˜ƒxx.withParameter(LootContextParams.ORIGIN, â˜ƒx.getPosition());
         LootTable â˜ƒ = â˜ƒx.getServer().getLootTables().get(â˜ƒ);
         List<ItemStack> â˜ƒx = â˜ƒ.getRandomItems(â˜ƒxx.create(LootContextParamSets.ENTITY));
         return â˜ƒ.accept(â˜ƒ, â˜ƒx, var2x -> callback(â˜ƒ, var2x, â˜ƒ));
      }
   }

   private static int dropChestLoot(CommandContext<CommandSourceStack> var0, ResourceLocation var1, LootCommand.DropConsumer var2) throws CommandSyntaxException {
      CommandSourceStack â˜ƒ = â˜ƒ.getSource();
      LootContext.Builder â˜ƒx = new LootContext.Builder(â˜ƒ.getLevel())
         .withOptionalParameter(LootContextParams.THIS_ENTITY, â˜ƒ.getEntity())
         .withParameter(LootContextParams.ORIGIN, â˜ƒ.getPosition());
      return drop(â˜ƒ, â˜ƒ, â˜ƒx.create(LootContextParamSets.CHEST), â˜ƒ);
   }

   private static int dropFishingLoot(
      CommandContext<CommandSourceStack> var0, ResourceLocation var1, BlockPos var2, ItemStack var3, LootCommand.DropConsumer var4
   ) throws CommandSyntaxException {
      CommandSourceStack â˜ƒ = â˜ƒ.getSource();
      LootContext â˜ƒx = new LootContext.Builder(â˜ƒ.getLevel())
         .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(â˜ƒ))
         .withParameter(LootContextParams.TOOL, â˜ƒ)
         .withOptionalParameter(LootContextParams.THIS_ENTITY, â˜ƒ.getEntity())
         .create(LootContextParamSets.FISHING);
      return drop(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
   }

   private static int drop(CommandContext<CommandSourceStack> var0, ResourceLocation var1, LootContext var2, LootCommand.DropConsumer var3) throws CommandSyntaxException {
      CommandSourceStack â˜ƒ = â˜ƒ.getSource();
      LootTable â˜ƒx = â˜ƒ.getServer().getLootTables().get(â˜ƒ);
      List<ItemStack> â˜ƒxx = â˜ƒx.getRandomItems(â˜ƒ);
      return â˜ƒ.accept(â˜ƒ, â˜ƒxx, var1x -> callback(â˜ƒ, var1x));
   }

   @FunctionalInterface
   interface Callback {
      void accept(List<ItemStack> var1) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface DropConsumer {
      int accept(CommandContext<CommandSourceStack> var1, List<ItemStack> var2, LootCommand.Callback var3) throws CommandSyntaxException;
   }

   @FunctionalInterface
   interface TailProvider {
      ArgumentBuilder<CommandSourceStack, ?> construct(ArgumentBuilder<CommandSourceStack, ?> var1, LootCommand.DropConsumer var2);
   }
}
