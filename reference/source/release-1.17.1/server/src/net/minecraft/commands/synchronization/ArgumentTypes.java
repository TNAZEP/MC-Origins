package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.commands.arguments.AngleArgument;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.commands.arguments.ItemEnchantmentArgument;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.commands.arguments.MobEffectArgument;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.NbtTagArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
import net.minecraft.commands.arguments.OperationArgument;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.commands.arguments.RangeArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.commands.arguments.ScoreboardSlotArgument;
import net.minecraft.commands.arguments.SlotArgument;
import net.minecraft.commands.arguments.TeamArgument;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
import net.minecraft.commands.arguments.blocks.BlockStateArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
import net.minecraft.commands.arguments.coordinates.RotationArgument;
import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.commands.arguments.item.FunctionArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemPredicateArgument;
import net.minecraft.commands.synchronization.brigadier.BrigadierArgumentSerializers;
import net.minecraft.gametest.framework.TestClassNameArgument;
import net.minecraft.gametest.framework.TestFunctionArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentTypes {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<Class<?>, ArgumentTypes.Entry<?>> BY_CLASS = Maps.newHashMap();
   private static final Map<ResourceLocation, ArgumentTypes.Entry<?>> BY_NAME = Maps.<ResourceLocation, ArgumentTypes.Entry<?>>newHashMap();

   public static <T extends ArgumentType<?>> void register(String var0, Class<T> var1, ArgumentSerializer<T> var2) {
      ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ);
      if (BY_CLASS.containsKey(â˜ƒ)) {
         throw new IllegalArgumentException("Class " + â˜ƒ.getName() + " already has a serializer!");
      } else if (BY_NAME.containsKey(â˜ƒ)) {
         throw new IllegalArgumentException("'" + â˜ƒ + "' is already a registered serializer!");
      } else {
         ArgumentTypes.Entry<T> â˜ƒ = new ArgumentTypes.Entry<>(â˜ƒ, â˜ƒ, â˜ƒ);
         BY_CLASS.put(â˜ƒ, â˜ƒ);
         BY_NAME.put(â˜ƒ, â˜ƒ);
      }
   }

   public static void bootStrap() {
      BrigadierArgumentSerializers.bootstrap();
      register("entity", EntityArgument.class, new EntityArgument.Serializer());
      register("game_profile", GameProfileArgument.class, new EmptyArgumentSerializer(GameProfileArgument::gameProfile));
      register("block_pos", BlockPosArgument.class, new EmptyArgumentSerializer(BlockPosArgument::blockPos));
      register("column_pos", ColumnPosArgument.class, new EmptyArgumentSerializer(ColumnPosArgument::columnPos));
      register("vec3", Vec3Argument.class, new EmptyArgumentSerializer(Vec3Argument::vec3));
      register("vec2", Vec2Argument.class, new EmptyArgumentSerializer(Vec2Argument::vec2));
      register("block_state", BlockStateArgument.class, new EmptyArgumentSerializer(BlockStateArgument::block));
      register("block_predicate", BlockPredicateArgument.class, new EmptyArgumentSerializer(BlockPredicateArgument::blockPredicate));
      register("item_stack", ItemArgument.class, new EmptyArgumentSerializer(ItemArgument::item));
      register("item_predicate", ItemPredicateArgument.class, new EmptyArgumentSerializer(ItemPredicateArgument::itemPredicate));
      register("color", ColorArgument.class, new EmptyArgumentSerializer(ColorArgument::color));
      register("component", ComponentArgument.class, new EmptyArgumentSerializer(ComponentArgument::textComponent));
      register("message", MessageArgument.class, new EmptyArgumentSerializer(MessageArgument::message));
      register("nbt_compound_tag", CompoundTagArgument.class, new EmptyArgumentSerializer(CompoundTagArgument::compoundTag));
      register("nbt_tag", NbtTagArgument.class, new EmptyArgumentSerializer(NbtTagArgument::nbtTag));
      register("nbt_path", NbtPathArgument.class, new EmptyArgumentSerializer(NbtPathArgument::nbtPath));
      register("objective", ObjectiveArgument.class, new EmptyArgumentSerializer(ObjectiveArgument::objective));
      register("objective_criteria", ObjectiveCriteriaArgument.class, new EmptyArgumentSerializer(ObjectiveCriteriaArgument::criteria));
      register("operation", OperationArgument.class, new EmptyArgumentSerializer(OperationArgument::operation));
      register("particle", ParticleArgument.class, new EmptyArgumentSerializer(ParticleArgument::particle));
      register("angle", AngleArgument.class, new EmptyArgumentSerializer(AngleArgument::angle));
      register("rotation", RotationArgument.class, new EmptyArgumentSerializer(RotationArgument::rotation));
      register("scoreboard_slot", ScoreboardSlotArgument.class, new EmptyArgumentSerializer(ScoreboardSlotArgument::displaySlot));
      register("score_holder", ScoreHolderArgument.class, new ScoreHolderArgument.Serializer());
      register("swizzle", SwizzleArgument.class, new EmptyArgumentSerializer(SwizzleArgument::swizzle));
      register("team", TeamArgument.class, new EmptyArgumentSerializer(TeamArgument::team));
      register("item_slot", SlotArgument.class, new EmptyArgumentSerializer(SlotArgument::slot));
      register("resource_location", ResourceLocationArgument.class, new EmptyArgumentSerializer(ResourceLocationArgument::id));
      register("mob_effect", MobEffectArgument.class, new EmptyArgumentSerializer(MobEffectArgument::effect));
      register("function", FunctionArgument.class, new EmptyArgumentSerializer(FunctionArgument::functions));
      register("entity_anchor", EntityAnchorArgument.class, new EmptyArgumentSerializer(EntityAnchorArgument::anchor));
      register("int_range", RangeArgument.Ints.class, new EmptyArgumentSerializer(RangeArgument::intRange));
      register("float_range", RangeArgument.Floats.class, new EmptyArgumentSerializer(RangeArgument::floatRange));
      register("item_enchantment", ItemEnchantmentArgument.class, new EmptyArgumentSerializer(ItemEnchantmentArgument::enchantment));
      register("entity_summon", EntitySummonArgument.class, new EmptyArgumentSerializer(EntitySummonArgument::id));
      register("dimension", DimensionArgument.class, new EmptyArgumentSerializer(DimensionArgument::dimension));
      register("time", TimeArgument.class, new EmptyArgumentSerializer(TimeArgument::time));
      register("uuid", UuidArgument.class, new EmptyArgumentSerializer(UuidArgument::uuid));
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         register("test_argument", TestFunctionArgument.class, new EmptyArgumentSerializer(TestFunctionArgument::testFunctionArgument));
         register("test_class", TestClassNameArgument.class, new EmptyArgumentSerializer(TestClassNameArgument::testClassName));
      }
   }

   @Nullable
   private static ArgumentTypes.Entry<?> get(ResourceLocation var0) {
      return (ArgumentTypes.Entry<?>)BY_NAME.get(â˜ƒ);
   }

   @Nullable
   private static ArgumentTypes.Entry<?> get(ArgumentType<?> var0) {
      return (ArgumentTypes.Entry<?>)BY_CLASS.get(â˜ƒ.getClass());
   }

   public static <T extends ArgumentType<?>> void serialize(FriendlyByteBuf var0, T var1) {
      ArgumentTypes.Entry<T> â˜ƒ = get(â˜ƒ);
      if (â˜ƒ == null) {
         LOGGER.error("Could not serialize {} ({}) - will not be sent to client!", â˜ƒ, â˜ƒ.getClass());
         â˜ƒ.writeResourceLocation(new ResourceLocation(""));
      } else {
         â˜ƒ.writeResourceLocation(â˜ƒ.name);
         â˜ƒ.serializer.serializeToNetwork(â˜ƒ, â˜ƒ);
      }
   }

   @Nullable
   public static ArgumentType<?> deserialize(FriendlyByteBuf var0) {
      ResourceLocation â˜ƒ = â˜ƒ.readResourceLocation();
      ArgumentTypes.Entry<?> â˜ƒx = get(â˜ƒ);
      if (â˜ƒx == null) {
         LOGGER.error("Could not deserialize {}", â˜ƒ);
         return null;
      } else {
         return â˜ƒx.serializer.deserializeFromNetwork(â˜ƒ);
      }
   }

   private static <T extends ArgumentType<?>> void serializeToJson(JsonObject var0, T var1) {
      ArgumentTypes.Entry<T> â˜ƒ = get(â˜ƒ);
      if (â˜ƒ == null) {
         LOGGER.error("Could not serialize argument {} ({})!", â˜ƒ, â˜ƒ.getClass());
         â˜ƒ.addProperty("type", "unknown");
      } else {
         â˜ƒ.addProperty("type", "argument");
         â˜ƒ.addProperty("parser", â˜ƒ.name.toString());
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.serializer.serializeToJson(â˜ƒ, â˜ƒ);
         if (â˜ƒ.size() > 0) {
            â˜ƒ.add("properties", â˜ƒ);
         }
      }
   }

   public static <S> JsonObject serializeNodeToJson(CommandDispatcher<S> var0, CommandNode<S> var1) {
      JsonObject â˜ƒ = new JsonObject();
      if (â˜ƒ instanceof RootCommandNode) {
         â˜ƒ.addProperty("type", "root");
      } else if (â˜ƒ instanceof LiteralCommandNode) {
         â˜ƒ.addProperty("type", "literal");
      } else if (â˜ƒ instanceof ArgumentCommandNode) {
         serializeToJson(â˜ƒ, ((ArgumentCommandNode)â˜ƒ).getType());
      } else {
         LOGGER.error("Could not serialize node {} ({})!", â˜ƒ, â˜ƒ.getClass());
         â˜ƒ.addProperty("type", "unknown");
      }

      JsonObject â˜ƒ = new JsonObject();

      for(CommandNode<S> â˜ƒx : â˜ƒ.getChildren()) {
         â˜ƒ.add(â˜ƒx.getName(), serializeNodeToJson(â˜ƒ, â˜ƒx));
      }

      if (â˜ƒ.size() > 0) {
         â˜ƒ.add("children", â˜ƒ);
      }

      if (â˜ƒ.getCommand() != null) {
         â˜ƒ.addProperty("executable", true);
      }

      if (â˜ƒ.getRedirect() != null) {
         Collection<String> â˜ƒx = â˜ƒ.getPath(â˜ƒ.getRedirect());
         if (!â˜ƒx.isEmpty()) {
            JsonArray â˜ƒxx = new JsonArray();

            for(String â˜ƒxxx : â˜ƒx) {
               â˜ƒxx.add(â˜ƒxxx);
            }

            â˜ƒ.add("redirect", â˜ƒxx);
         }
      }

      return â˜ƒ;
   }

   public static boolean isTypeRegistered(ArgumentType<?> var0) {
      return get(â˜ƒ) != null;
   }

   public static <T> Set<ArgumentType<?>> findUsedArgumentTypes(CommandNode<T> var0) {
      Set<CommandNode<T>> â˜ƒ = Sets.newIdentityHashSet();
      Set<ArgumentType<?>> â˜ƒx = Sets.<ArgumentType<?>>newHashSet();
      findUsedArgumentTypes(â˜ƒ, â˜ƒx, â˜ƒ);
      return â˜ƒx;
   }

   private static <T> void findUsedArgumentTypes(CommandNode<T> var0, Set<ArgumentType<?>> var1, Set<CommandNode<T>> var2) {
      if (â˜ƒ.add(â˜ƒ)) {
         if (â˜ƒ instanceof ArgumentCommandNode) {
            â˜ƒ.add(((ArgumentCommandNode)â˜ƒ).getType());
         }

         â˜ƒ.getChildren().forEach(var2x -> findUsedArgumentTypes(var2x, â˜ƒ, â˜ƒ));
         CommandNode<T> â˜ƒ = â˜ƒ.getRedirect();
         if (â˜ƒ != null) {
            findUsedArgumentTypes(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   static class Entry<T extends ArgumentType<?>> {
      public final Class<T> clazz;
      public final ArgumentSerializer<T> serializer;
      public final ResourceLocation name;

      Entry(Class<T> var1, ArgumentSerializer<T> var2, ResourceLocation var3) {
         this.clazz = â˜ƒ;
         this.serializer = â˜ƒ;
         this.name = â˜ƒ;
      }
   }
}
