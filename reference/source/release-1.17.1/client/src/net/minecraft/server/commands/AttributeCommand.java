package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.UUID;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttributeCommand {
   private static final SuggestionProvider<CommandSourceStack> AVAILABLE_ATTRIBUTES = (var0, var1) -> SharedSuggestionProvider.suggestResource(
         Registry.ATTRIBUTE.keySet(), var1
      );
   private static final DynamicCommandExceptionType ERROR_NOT_LIVING_ENTITY = new DynamicCommandExceptionType(
      var0 -> new TranslatableComponent("commands.attribute.failed.entity", var0)
   );
   private static final Dynamic2CommandExceptionType ERROR_NO_SUCH_ATTRIBUTE = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TranslatableComponent("commands.attribute.failed.no_attribute", var0, var1)
   );
   private static final Dynamic3CommandExceptionType ERROR_NO_SUCH_MODIFIER = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new TranslatableComponent("commands.attribute.failed.no_modifier", var1, var0, var2)
   );
   private static final Dynamic3CommandExceptionType ERROR_MODIFIER_ALREADY_PRESENT = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new TranslatableComponent("commands.attribute.failed.modifier_already_present", var2, var1, var0)
   );

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("attribute")
            .requires(var0x -> var0x.hasPermission(2))
            .then(
               Commands.argument("target", EntityArgument.entity())
                  .then(
                     Commands.argument("attribute", ResourceLocationArgument.id())
                        .suggests(AVAILABLE_ATTRIBUTES)
                        .then(
                           Commands.literal("get")
                              .executes(
                                 var0x -> getAttributeValue(
                                       var0x.getSource(),
                                       EntityArgument.getEntity(var0x, "target"),
                                       ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                       1.0
                                    )
                              )
                              .then(
                                 Commands.argument("scale", DoubleArgumentType.doubleArg())
                                    .executes(
                                       var0x -> getAttributeValue(
                                             var0x.getSource(),
                                             EntityArgument.getEntity(var0x, "target"),
                                             ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                             DoubleArgumentType.getDouble(var0x, "scale")
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("base")
                              .then(
                                 Commands.literal("set")
                                    .then(
                                       Commands.argument("value", DoubleArgumentType.doubleArg())
                                          .executes(
                                             var0x -> setAttributeBase(
                                                   var0x.getSource(),
                                                   EntityArgument.getEntity(var0x, "target"),
                                                   ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                   DoubleArgumentType.getDouble(var0x, "value")
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("get")
                                    .executes(
                                       var0x -> getAttributeBase(
                                             var0x.getSource(),
                                             EntityArgument.getEntity(var0x, "target"),
                                             ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                             1.0
                                          )
                                    )
                                    .then(
                                       Commands.argument("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             var0x -> getAttributeBase(
                                                   var0x.getSource(),
                                                   EntityArgument.getEntity(var0x, "target"),
                                                   ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                   DoubleArgumentType.getDouble(var0x, "scale")
                                                )
                                          )
                                    )
                              )
                        )
                        .then(
                           Commands.literal("modifier")
                              .then(
                                 Commands.literal("add")
                                    .then(
                                       Commands.argument("uuid", UuidArgument.uuid())
                                          .then(
                                             Commands.argument("name", StringArgumentType.string())
                                                .then(
                                                   ((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument(
                                                               "value", DoubleArgumentType.doubleArg()
                                                            )
                                                            .then(
                                                               Commands.literal("add")
                                                                  .executes(
                                                                     var0x -> addModifier(
                                                                           var0x.getSource(),
                                                                           EntityArgument.getEntity(var0x, "target"),
                                                                           ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                                           UuidArgument.getUuid(var0x, "uuid"),
                                                                           StringArgumentType.getString(var0x, "name"),
                                                                           DoubleArgumentType.getDouble(var0x, "value"),
                                                                           AttributeModifier.Operation.ADDITION
                                                                        )
                                                                  )
                                                            ))
                                                         .then(
                                                            Commands.literal("multiply")
                                                               .executes(
                                                                  var0x -> addModifier(
                                                                        var0x.getSource(),
                                                                        EntityArgument.getEntity(var0x, "target"),
                                                                        ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                                        UuidArgument.getUuid(var0x, "uuid"),
                                                                        StringArgumentType.getString(var0x, "name"),
                                                                        DoubleArgumentType.getDouble(var0x, "value"),
                                                                        AttributeModifier.Operation.MULTIPLY_TOTAL
                                                                     )
                                                               )
                                                         ))
                                                      .then(
                                                         Commands.literal("multiply_base")
                                                            .executes(
                                                               var0x -> addModifier(
                                                                     var0x.getSource(),
                                                                     EntityArgument.getEntity(var0x, "target"),
                                                                     ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                                     UuidArgument.getUuid(var0x, "uuid"),
                                                                     StringArgumentType.getString(var0x, "name"),
                                                                     DoubleArgumentType.getDouble(var0x, "value"),
                                                                     AttributeModifier.Operation.MULTIPLY_BASE
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("remove")
                                    .then(
                                       Commands.argument("uuid", UuidArgument.uuid())
                                          .executes(
                                             var0x -> removeModifier(
                                                   var0x.getSource(),
                                                   EntityArgument.getEntity(var0x, "target"),
                                                   ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                   UuidArgument.getUuid(var0x, "uuid")
                                                )
                                          )
                                    )
                              )
                              .then(
                                 Commands.literal("value")
                                    .then(
                                       Commands.literal("get")
                                          .then(
                                             Commands.argument("uuid", UuidArgument.uuid())
                                                .executes(
                                                   var0x -> getAttributeModifier(
                                                         var0x.getSource(),
                                                         EntityArgument.getEntity(var0x, "target"),
                                                         ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                         UuidArgument.getUuid(var0x, "uuid"),
                                                         1.0
                                                      )
                                                )
                                                .then(
                                                   Commands.argument("scale", DoubleArgumentType.doubleArg())
                                                      .executes(
                                                         var0x -> getAttributeModifier(
                                                               var0x.getSource(),
                                                               EntityArgument.getEntity(var0x, "target"),
                                                               ResourceLocationArgument.getAttribute(var0x, "attribute"),
                                                               UuidArgument.getUuid(var0x, "uuid"),
                                                               DoubleArgumentType.getDouble(var0x, "scale")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static AttributeInstance getAttributeInstance(Entity var0, Attribute var1) throws CommandSyntaxException {
      AttributeInstance â˜ƒ = getLivingEntity(â˜ƒ).getAttributes().getInstance(â˜ƒ);
      if (â˜ƒ == null) {
         throw ERROR_NO_SUCH_ATTRIBUTE.create(â˜ƒ.getName(), new TranslatableComponent(â˜ƒ.getDescriptionId()));
      } else {
         return â˜ƒ;
      }
   }

   private static LivingEntity getLivingEntity(Entity var0) throws CommandSyntaxException {
      if (!(â˜ƒ instanceof LivingEntity)) {
         throw ERROR_NOT_LIVING_ENTITY.create(â˜ƒ.getName());
      } else {
         return (LivingEntity)â˜ƒ;
      }
   }

   private static LivingEntity getEntityWithAttribute(Entity var0, Attribute var1) throws CommandSyntaxException {
      LivingEntity â˜ƒ = getLivingEntity(â˜ƒ);
      if (!â˜ƒ.getAttributes().hasAttribute(â˜ƒ)) {
         throw ERROR_NO_SUCH_ATTRIBUTE.create(â˜ƒ.getName(), new TranslatableComponent(â˜ƒ.getDescriptionId()));
      } else {
         return â˜ƒ;
      }
   }

   private static int getAttributeValue(CommandSourceStack var0, Entity var1, Attribute var2, double var3) throws CommandSyntaxException {
      LivingEntity â˜ƒ = getEntityWithAttribute(â˜ƒ, â˜ƒ);
      double â˜ƒx = â˜ƒ.getAttributeValue(â˜ƒ);
      â˜ƒ.sendSuccess(
         new TranslatableComponent("commands.attribute.value.get.success", new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ.getName(), â˜ƒx), false
      );
      return (int)(â˜ƒx * â˜ƒ);
   }

   private static int getAttributeBase(CommandSourceStack var0, Entity var1, Attribute var2, double var3) throws CommandSyntaxException {
      LivingEntity â˜ƒ = getEntityWithAttribute(â˜ƒ, â˜ƒ);
      double â˜ƒx = â˜ƒ.getAttributeBaseValue(â˜ƒ);
      â˜ƒ.sendSuccess(
         new TranslatableComponent("commands.attribute.base_value.get.success", new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ.getName(), â˜ƒx), false
      );
      return (int)(â˜ƒx * â˜ƒ);
   }

   private static int getAttributeModifier(CommandSourceStack var0, Entity var1, Attribute var2, UUID var3, double var4) throws CommandSyntaxException {
      LivingEntity â˜ƒ = getEntityWithAttribute(â˜ƒ, â˜ƒ);
      AttributeMap â˜ƒx = â˜ƒ.getAttributes();
      if (!â˜ƒx.hasModifier(â˜ƒ, â˜ƒ)) {
         throw ERROR_NO_SUCH_MODIFIER.create(â˜ƒ.getName(), new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ);
      } else {
         double â˜ƒ = â˜ƒx.getModifierValue(â˜ƒ, â˜ƒ);
         â˜ƒ.sendSuccess(
            new TranslatableComponent(
               "commands.attribute.modifier.value.get.success", â˜ƒ, new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ.getName(), â˜ƒ
            ),
            false
         );
         return (int)(â˜ƒ * â˜ƒ);
      }
   }

   private static int setAttributeBase(CommandSourceStack var0, Entity var1, Attribute var2, double var3) throws CommandSyntaxException {
      getAttributeInstance(â˜ƒ, â˜ƒ).setBaseValue(â˜ƒ);
      â˜ƒ.sendSuccess(
         new TranslatableComponent("commands.attribute.base_value.set.success", new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ.getName(), â˜ƒ), false
      );
      return 1;
   }

   private static int addModifier(CommandSourceStack var0, Entity var1, Attribute var2, UUID var3, String var4, double var5, AttributeModifier.Operation var7) throws CommandSyntaxException {
      AttributeInstance â˜ƒ = getAttributeInstance(â˜ƒ, â˜ƒ);
      AttributeModifier â˜ƒx = new AttributeModifier(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.hasModifier(â˜ƒx)) {
         throw ERROR_MODIFIER_ALREADY_PRESENT.create(â˜ƒ.getName(), new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ);
      } else {
         â˜ƒ.addPermanentModifier(â˜ƒx);
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.attribute.modifier.add.success", â˜ƒ, new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ.getName()), false
         );
         return 1;
      }
   }

   private static int removeModifier(CommandSourceStack var0, Entity var1, Attribute var2, UUID var3) throws CommandSyntaxException {
      AttributeInstance â˜ƒ = getAttributeInstance(â˜ƒ, â˜ƒ);
      if (â˜ƒ.removePermanentModifier(â˜ƒ)) {
         â˜ƒ.sendSuccess(
            new TranslatableComponent("commands.attribute.modifier.remove.success", â˜ƒ, new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ.getName()),
            false
         );
         return 1;
      } else {
         throw ERROR_NO_SUCH_MODIFIER.create(â˜ƒ.getName(), new TranslatableComponent(â˜ƒ.getDescriptionId()), â˜ƒ);
      }
   }
}
