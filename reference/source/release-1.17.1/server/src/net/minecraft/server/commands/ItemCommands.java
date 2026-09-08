package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ItemCommands {
   static final Dynamic3CommandExceptionType ERROR_TARGET_NOT_A_CONTAINER = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new TranslatableComponent("commands.item.target.not_a_container", var0, var1, var2)
   );
   private static final Dynamic3CommandExceptionType ERROR_SOURCE_NOT_A_CONTAINER = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new TranslatableComponent("commands.item.source.not_a_container", var0, var1, var2)
   );
   static final DynamicCommandExceptionType ERROR_TARGET_INAPPLICABLE_SLOT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.item.target.no_such_slot", var0)
   );
   private static final DynamicCommandExceptionType ERROR_SOURCE_INAPPLICABLE_SLOT = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.item.source.no_such_slot", var0)
   );
   private static final DynamicCommandExceptionType ERROR_TARGET_NO_CHANGES = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.item.target.no_changes", var0)
   );
   private static final Dynamic2CommandExceptionType ERROR_TARGET_NO_CHANGES_KNOWN_ITEM = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.item.target.no_changed.known_item", var0, var1)
   );
   private static final SuggestionProvider<CommandSourceStack> SUGGEST_MODIFIER = (var0, var1) -> {
      ItemModifierManager â˜ƒ = var0.getSource().getServer().getItemModifierManager();
      return SharedSuggestionProvider.suggestResource(â˜ƒ.getKeys(), var1);
   };

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("item")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.literal("replace")
                  .then(
                     Commands.literal("block")
                        .then(
                           Commands.argument("pos", BlockPosArgument.blockPos())
                              .then(
                                 ((RequiredArgumentBuilder)Commands.argument("slot", SlotArgument.slot())
                                       .then(
                                          Commands.literal("with")
                                             .then(
                                                Commands.argument("item", ItemArgument.item())
                                                   .executes(
                                                      var0x -> setBlockItem(
                                                            var0x.getSource(),
                                                            BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                                            SlotArgument.getSlot(var0x, "slot"),
                                                            ItemArgument.getItem(var0x, "item").createItemStack(1, false)
                                                         )
                                                   )
                                                   .then(
                                                      Commands.argument("count", IntegerArgumentType.integer(1, 64))
                                                         .executes(
                                                            var0x -> setBlockItem(
                                                                  var0x.getSource(),
                                                                  BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                                                  SlotArgument.getSlot(var0x, "slot"),
                                                                  ItemArgument.getItem(var0x, "item")
                                                                     .createItemStack(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.literal("from")
                                          .then(
                                             Commands.literal("block")
                                                .then(
                                                   Commands.argument("source", BlockPosArgument.blockPos())
                                                      .then(
                                                         ((RequiredArgumentBuilder)Commands.argument("sourceSlot", SlotArgument.slot())
                                                               .executes(
                                                                  var0x -> blockToBlock(
                                                                        (CommandSourceStack)var0x.getSource(),
                                                                        BlockPosArgument.getLoadedBlockPos(var0x, "source"),
                                                                        SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                        BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                                                        SlotArgument.getSlot(var0x, "slot")
                                                                     )
                                                               ))
                                                            .then(
                                                               Commands.argument("modifier", ResourceLocationArgument.id())
                                                                  .suggests(SUGGEST_MODIFIER)
                                                                  .executes(
                                                                     var0x -> blockToBlock(
                                                                           var0x.getSource(),
                                                                           BlockPosArgument.getLoadedBlockPos(var0x, "source"),
                                                                           SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                           BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                                                           SlotArgument.getSlot(var0x, "slot"),
                                                                           ResourceLocationArgument.getItemModifier(var0x, "modifier")
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.literal("entity")
                                                .then(
                                                   Commands.argument("source", EntityArgument.entity())
                                                      .then(
                                                         ((RequiredArgumentBuilder)Commands.argument("sourceSlot", SlotArgument.slot())
                                                               .executes(
                                                                  var0x -> entityToBlock(
                                                                        (CommandSourceStack)var0x.getSource(),
                                                                        EntityArgument.getEntity(var0x, "source"),
                                                                        SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                        BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                                                        SlotArgument.getSlot(var0x, "slot")
                                                                     )
                                                               ))
                                                            .then(
                                                               Commands.argument("modifier", ResourceLocationArgument.id())
                                                                  .suggests(SUGGEST_MODIFIER)
                                                                  .executes(
                                                                     var0x -> entityToBlock(
                                                                           var0x.getSource(),
                                                                           EntityArgument.getEntity(var0x, "source"),
                                                                           SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                           BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                                                           SlotArgument.getSlot(var0x, "slot"),
                                                                           ResourceLocationArgument.getItemModifier(var0x, "modifier")
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.literal("entity")
                        .then(
                           Commands.argument("targets", EntityArgument.entities())
                              .then(
                                 ((RequiredArgumentBuilder)Commands.argument("slot", SlotArgument.slot())
                                       .then(
                                          Commands.literal("with")
                                             .then(
                                                Commands.argument("item", ItemArgument.item())
                                                   .executes(
                                                      var0x -> setEntityItem(
                                                            var0x.getSource(),
                                                            EntityArgument.getEntities(var0x, "targets"),
                                                            SlotArgument.getSlot(var0x, "slot"),
                                                            ItemArgument.getItem(var0x, "item").createItemStack(1, false)
                                                         )
                                                   )
                                                   .then(
                                                      Commands.argument("count", IntegerArgumentType.integer(1, 64))
                                                         .executes(
                                                            var0x -> setEntityItem(
                                                                  var0x.getSource(),
                                                                  EntityArgument.getEntities(var0x, "targets"),
                                                                  SlotArgument.getSlot(var0x, "slot"),
                                                                  ItemArgument.getItem(var0x, "item")
                                                                     .createItemStack(IntegerArgumentType.getInteger(var0x, "count"), true)
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.literal("from")
                                          .then(
                                             Commands.literal("block")
                                                .then(
                                                   Commands.argument("source", BlockPosArgument.blockPos())
                                                      .then(
                                                         ((RequiredArgumentBuilder)Commands.argument("sourceSlot", SlotArgument.slot())
                                                               .executes(
                                                                  var0x -> blockToEntities(
                                                                        (CommandSourceStack)var0x.getSource(),
                                                                        BlockPosArgument.getLoadedBlockPos(var0x, "source"),
                                                                        SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                        EntityArgument.getEntities(var0x, "targets"),
                                                                        SlotArgument.getSlot(var0x, "slot")
                                                                     )
                                                               ))
                                                            .then(
                                                               Commands.argument("modifier", ResourceLocationArgument.id())
                                                                  .suggests(SUGGEST_MODIFIER)
                                                                  .executes(
                                                                     var0x -> blockToEntities(
                                                                           var0x.getSource(),
                                                                           BlockPosArgument.getLoadedBlockPos(var0x, "source"),
                                                                           SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                           EntityArgument.getEntities(var0x, "targets"),
                                                                           SlotArgument.getSlot(var0x, "slot"),
                                                                           ResourceLocationArgument.getItemModifier(var0x, "modifier")
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                          .then(
                                             Commands.literal("entity")
                                                .then(
                                                   Commands.argument("source", EntityArgument.entity())
                                                      .then(
                                                         ((RequiredArgumentBuilder)Commands.argument("sourceSlot", SlotArgument.slot())
                                                               .executes(
                                                                  var0x -> entityToEntities(
                                                                        (CommandSourceStack)var0x.getSource(),
                                                                        EntityArgument.getEntity(var0x, "source"),
                                                                        SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                        EntityArgument.getEntities(var0x, "targets"),
                                                                        SlotArgument.getSlot(var0x, "slot")
                                                                     )
                                                               ))
                                                            .then(
                                                               Commands.argument("modifier", ResourceLocationArgument.id())
                                                                  .suggests(SUGGEST_MODIFIER)
                                                                  .executes(
                                                                     var0x -> entityToEntities(
                                                                           var0x.getSource(),
                                                                           EntityArgument.getEntity(var0x, "source"),
                                                                           SlotArgument.getSlot(var0x, "sourceSlot"),
                                                                           EntityArgument.getEntities(var0x, "targets"),
                                                                           SlotArgument.getSlot(var0x, "slot"),
                                                                           ResourceLocationArgument.getItemModifier(var0x, "modifier")
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("modify")
                  .then(
                     Commands.literal("block")
                        .then(
                           Commands.argument("pos", BlockPosArgument.blockPos())
                              .then(
                                 Commands.argument("slot", SlotArgument.slot())
                                    .then(
                                       Commands.argument("modifier", ResourceLocationArgument.id())
                                          .suggests(SUGGEST_MODIFIER)
                                          .executes(
                                             var0x -> modifyBlockItem(
                                                   var0x.getSource(),
                                                   BlockPosArgument.getLoadedBlockPos(var0x, "pos"),
                                                   SlotArgument.getSlot(var0x, "slot"),
                                                   ResourceLocationArgument.getItemModifier(var0x, "modifier")
                                                )
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.literal("entity")
                        .then(
                           Commands.argument("targets", EntityArgument.entities())
                              .then(
                                 Commands.argument("slot", SlotArgument.slot())
                                    .then(
                                       Commands.argument("modifier", ResourceLocationArgument.id())
                                          .suggests(SUGGEST_MODIFIER)
                                          .executes(
                                             var0x -> modifyEntityItem(
                                                   var0x.getSource(),
                                                   EntityArgument.getEntities(var0x, "targets"),
                                                   SlotArgument.getSlot(var0x, "slot"),
                                                   ResourceLocationArgument.getItemModifier(var0x, "modifier")
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int modifyBlockItem(CommandSourceStack var0, BlockPos var1, int var2, LootItemFunction var3) throws CommandSyntaxException {
      Container â˜ƒ = getContainer(â˜ƒ, â˜ƒ, ERROR_TARGET_NOT_A_CONTAINER);
      if (â˜ƒ >= 0 && â˜ƒ < â˜ƒ.getContainerSize()) {
         ItemStack â˜ƒx = applyModifier(â˜ƒ, â˜ƒ, â˜ƒ.getItem(â˜ƒ));
         â˜ƒ.setItem(â˜ƒ, â˜ƒx);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.item.block.set.success", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒx.getDisplayName()), true);
         return 1;
      } else {
         throw ERROR_TARGET_INAPPLICABLE_SLOT.create(â˜ƒ);
      }
   }

   private static int modifyEntityItem(CommandSourceStack var0, Collection<? extends Entity> var1, int var2, LootItemFunction var3) throws CommandSyntaxException {
      Map<Entity, ItemStack> â˜ƒ = Maps.<Entity, ItemStack>newHashMapWithExpectedSize(â˜ƒ.size());

      for(Entity â˜ƒx : â˜ƒ) {
         SlotAccess â˜ƒxx = â˜ƒx.getSlot(â˜ƒ);
         if (â˜ƒxx != SlotAccess.NULL) {
            ItemStack â˜ƒxxx = applyModifier(â˜ƒ, â˜ƒ, â˜ƒxx.get().copy());
            if (â˜ƒxx.set(â˜ƒxxx)) {
               â˜ƒ.put(â˜ƒx, â˜ƒxxx);
               if (â˜ƒx instanceof ServerPlayer) {
                  ((ServerPlayer)â˜ƒx).containerMenu.broadcastChanges();
               }
            }
         }
      }

      if (â˜ƒ.isEmpty()) {
         throw ERROR_TARGET_NO_CHANGES.create(â˜ƒ);
      } else {
         if (â˜ƒ.size() == 1) {
            Entry<Entity, ItemStack> â˜ƒx = (Entry)â˜ƒ.entrySet().iterator().next();
            â˜ƒ.sendSuccess(
               new TranslatableComponent(
                  "commands.item.entity.set.success.single", ((Entity)â˜ƒx.getKey()).getDisplayName(), ((ItemStack)â˜ƒx.getValue()).getDisplayName()
               ),
               true
            );
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.item.entity.set.success.multiple", â˜ƒ.size()), true);
         }

         return â˜ƒ.size();
      }
   }

   private static int setBlockItem(CommandSourceStack var0, BlockPos var1, int var2, ItemStack var3) throws CommandSyntaxException {
      Container â˜ƒ = getContainer(â˜ƒ, â˜ƒ, ERROR_TARGET_NOT_A_CONTAINER);
      if (â˜ƒ >= 0 && â˜ƒ < â˜ƒ.getContainerSize()) {
         â˜ƒ.setItem(â˜ƒ, â˜ƒ);
         â˜ƒ.sendSuccess(new TranslatableComponent("commands.item.block.set.success", â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getDisplayName()), true);
         return 1;
      } else {
         throw ERROR_TARGET_INAPPLICABLE_SLOT.create(â˜ƒ);
      }
   }

   private static Container getContainer(CommandSourceStack var0, BlockPos var1, Dynamic3CommandExceptionType var2) throws CommandSyntaxException {
      BlockEntity â˜ƒ = â˜ƒ.getLevel().getBlockEntity(â˜ƒ);
      if (!(â˜ƒ instanceof Container)) {
         throw â˜ƒ.create(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      } else {
         return (Container)â˜ƒ;
      }
   }

   private static int setEntityItem(CommandSourceStack var0, Collection<? extends Entity> var1, int var2, ItemStack var3) throws CommandSyntaxException {
      List<Entity> â˜ƒ = Lists.<Entity>newArrayListWithCapacity(â˜ƒ.size());

      for(Entity â˜ƒx : â˜ƒ) {
         SlotAccess â˜ƒxx = â˜ƒx.getSlot(â˜ƒ);
         if (â˜ƒxx != SlotAccess.NULL && â˜ƒxx.set(â˜ƒ.copy())) {
            â˜ƒ.add(â˜ƒx);
            if (â˜ƒx instanceof ServerPlayer) {
               ((ServerPlayer)â˜ƒx).containerMenu.broadcastChanges();
            }
         }
      }

      if (â˜ƒ.isEmpty()) {
         throw ERROR_TARGET_NO_CHANGES_KNOWN_ITEM.create(â˜ƒ.getDisplayName(), â˜ƒ);
      } else {
         if (â˜ƒ.size() == 1) {
            â˜ƒ.sendSuccess(
               new TranslatableComponent("commands.item.entity.set.success.single", ((Entity)â˜ƒ.iterator().next()).getDisplayName(), â˜ƒ.getDisplayName()),
               true
            );
         } else {
            â˜ƒ.sendSuccess(new TranslatableComponent("commands.item.entity.set.success.multiple", â˜ƒ.size(), â˜ƒ.getDisplayName()), true);
         }

         return â˜ƒ.size();
      }
   }

   private static int blockToEntities(CommandSourceStack var0, BlockPos var1, int var2, Collection<? extends Entity> var3, int var4) throws CommandSyntaxException {
      return setEntityItem(â˜ƒ, â˜ƒ, â˜ƒ, getBlockItem(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   private static int blockToEntities(CommandSourceStack var0, BlockPos var1, int var2, Collection<? extends Entity> var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return setEntityItem(â˜ƒ, â˜ƒ, â˜ƒ, applyModifier(â˜ƒ, â˜ƒ, getBlockItem(â˜ƒ, â˜ƒ, â˜ƒ)));
   }

   private static int blockToBlock(CommandSourceStack var0, BlockPos var1, int var2, BlockPos var3, int var4) throws CommandSyntaxException {
      return setBlockItem(â˜ƒ, â˜ƒ, â˜ƒ, getBlockItem(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   private static int blockToBlock(CommandSourceStack var0, BlockPos var1, int var2, BlockPos var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return setBlockItem(â˜ƒ, â˜ƒ, â˜ƒ, applyModifier(â˜ƒ, â˜ƒ, getBlockItem(â˜ƒ, â˜ƒ, â˜ƒ)));
   }

   private static int entityToBlock(CommandSourceStack var0, Entity var1, int var2, BlockPos var3, int var4) throws CommandSyntaxException {
      return setBlockItem(â˜ƒ, â˜ƒ, â˜ƒ, getEntityItem(â˜ƒ, â˜ƒ));
   }

   private static int entityToBlock(CommandSourceStack var0, Entity var1, int var2, BlockPos var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return setBlockItem(â˜ƒ, â˜ƒ, â˜ƒ, applyModifier(â˜ƒ, â˜ƒ, getEntityItem(â˜ƒ, â˜ƒ)));
   }

   private static int entityToEntities(CommandSourceStack var0, Entity var1, int var2, Collection<? extends Entity> var3, int var4) throws CommandSyntaxException {
      return setEntityItem(â˜ƒ, â˜ƒ, â˜ƒ, getEntityItem(â˜ƒ, â˜ƒ));
   }

   private static int entityToEntities(CommandSourceStack var0, Entity var1, int var2, Collection<? extends Entity> var3, int var4, LootItemFunction var5) throws CommandSyntaxException {
      return setEntityItem(â˜ƒ, â˜ƒ, â˜ƒ, applyModifier(â˜ƒ, â˜ƒ, getEntityItem(â˜ƒ, â˜ƒ)));
   }

   private static ItemStack applyModifier(CommandSourceStack var0, LootItemFunction var1, ItemStack var2) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      LootContext.Builder â˜ƒx = new LootContext.Builder(â˜ƒ)
         .withParameter(LootContextParams.ORIGIN, â˜ƒ.getPosition())
         .withOptionalParameter(LootContextParams.THIS_ENTITY, â˜ƒ.getEntity());
      return (ItemStack)â˜ƒ.apply(â˜ƒ, â˜ƒx.create(LootContextParamSets.COMMAND));
   }

   private static ItemStack getEntityItem(Entity var0, int var1) throws CommandSyntaxException {
      SlotAccess â˜ƒ = â˜ƒ.getSlot(â˜ƒ);
      if (â˜ƒ == SlotAccess.NULL) {
         throw ERROR_SOURCE_INAPPLICABLE_SLOT.create(â˜ƒ);
      } else {
         return â˜ƒ.get().copy();
      }
   }

   private static ItemStack getBlockItem(CommandSourceStack var0, BlockPos var1, int var2) throws CommandSyntaxException {
      Container â˜ƒ = getContainer(â˜ƒ, â˜ƒ, ERROR_SOURCE_NOT_A_CONTAINER);
      if (â˜ƒ >= 0 && â˜ƒ < â˜ƒ.getContainerSize()) {
         return â˜ƒ.getItem(â˜ƒ).copy();
      } else {
         throw ERROR_SOURCE_INAPPLICABLE_SLOT.create(â˜ƒ);
      }
   }
}
