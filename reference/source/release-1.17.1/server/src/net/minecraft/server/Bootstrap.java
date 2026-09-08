package net.minecraft.server;

import java.io.PrintStream;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.selector.options.EntitySelectorOptions;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StaticTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bootstrap {
   public static final PrintStream STDOUT = System.out;
   private static volatile boolean isBootstrapped;
   private static final Logger LOGGER = LogManager.getLogger();

   public static void bootStrap() {
      if (!isBootstrapped) {
         isBootstrapped = true;
         if (Registry.REGISTRY.keySet().isEmpty()) {
            throw new IllegalStateException("Unable to load registries");
         } else {
            FireBlock.bootStrap();
            ComposterBlock.bootStrap();
            if (EntityType.getKey(EntityType.PLAYER) == null) {
               throw new IllegalStateException("Failed loading EntityTypes");
            } else {
               PotionBrewing.bootStrap();
               EntitySelectorOptions.bootStrap();
               DispenseItemBehavior.bootStrap();
               CauldronInteraction.bootStrap();
               ArgumentTypes.bootStrap();
               StaticTags.bootStrap();
               wrapStreams();
            }
         }
      }
   }

   private static <T> void checkTranslations(Iterable<T> var0, Function<T, String> var1, Set<String> var2) {
      Language â˜ƒ = Language.getInstance();
      â˜ƒ.forEach(var3x -> {
         String â˜ƒ = (String)â˜ƒ.apply(var3x);
         if (!â˜ƒ.has(â˜ƒ)) {
            â˜ƒ.add(â˜ƒ);
         }
      });
   }

   private static void checkGameruleTranslations(final Set<String> var0) {
      final Language â˜ƒ = Language.getInstance();
      GameRules.visitGameRuleTypes(new GameRules.GameRuleTypeVisitor() {
         @Override
         public <T extends GameRules.Value<T>> void visit(GameRules.Key<T> var1x, GameRules.Type<T> var2) {
            if (!â˜ƒ.has(â˜ƒ.getDescriptionId())) {
               â˜ƒ.add(â˜ƒ.getId());
            }
         }
      });
   }

   public static Set<String> getMissingTranslations() {
      Set<String> â˜ƒ = new TreeSet();
      checkTranslations(Registry.ATTRIBUTE, Attribute::getDescriptionId, â˜ƒ);
      checkTranslations(Registry.ENTITY_TYPE, EntityType::getDescriptionId, â˜ƒ);
      checkTranslations(Registry.MOB_EFFECT, MobEffect::getDescriptionId, â˜ƒ);
      checkTranslations(Registry.ITEM, Item::getDescriptionId, â˜ƒ);
      checkTranslations(Registry.ENCHANTMENT, Enchantment::getDescriptionId, â˜ƒ);
      checkTranslations(Registry.BLOCK, Block::getDescriptionId, â˜ƒ);
      checkTranslations(Registry.CUSTOM_STAT, var0x -> "stat." + var0x.toString().replace(':', '.'), â˜ƒ);
      checkGameruleTranslations(â˜ƒ);
      return â˜ƒ;
   }

   public static void checkBootstrapCalled(Supplier<String> var0) {
      if (!isBootstrapped) {
         throw createBootstrapException(â˜ƒ);
      }
   }

   private static RuntimeException createBootstrapException(Supplier<String> var0) {
      try {
         String â˜ƒ = (String)â˜ƒ.get();
         return new IllegalArgumentException("Not bootstrapped (called from " + â˜ƒ + ")");
      } catch (Exception var3) {
         RuntimeException â˜ƒx = new IllegalArgumentException("Not bootstrapped (failed to resolve location)");
         â˜ƒx.addSuppressed(var3);
         return â˜ƒx;
      }
   }

   public static void validate() {
      checkBootstrapCalled(() -> "validate");
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         getMissingTranslations().forEach(var0 -> LOGGER.error("Missing translations: {}", var0));
         Commands.validate();
      }

      DefaultAttributes.validate();
   }

   private static void wrapStreams() {
      if (LOGGER.isDebugEnabled()) {
         System.setErr(new DebugLoggedPrintStream("STDERR", System.err));
         System.setOut(new DebugLoggedPrintStream("STDOUT", STDOUT));
      } else {
         System.setErr(new LoggedPrintStream("STDERR", System.err));
         System.setOut(new LoggedPrintStream("STDOUT", STDOUT));
      }
   }

   public static void realStdoutPrintln(String var0) {
      STDOUT.println(â˜ƒ);
   }
}
