package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
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
import javax.annotation.Nullable;
import net.minecraft.command.arguments.serializers.BrigadierSerializers;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentTypes {
   private static final Logger field_197488_a = LogManager.getLogger();
   private static final Map<Class<?>, ArgumentTypes.Entry<?>> field_197489_b = Maps.newHashMap();
   private static final Map<ResourceLocation, ArgumentTypes.Entry<?>> field_197490_c = Maps.<ResourceLocation, ArgumentTypes.Entry<?>>newHashMap();

   public static <T extends ArgumentType<?>> void func_197487_a(ResourceLocation var0, Class<T> var1, IArgumentSerializer<T> var2) {
      if (field_197489_b.containsKey(☃)) {
         throw new IllegalArgumentException("Class " + ☃.getName() + " already has a serializer!");
      } else if (field_197490_c.containsKey(☃)) {
         throw new IllegalArgumentException("'" + ☃ + "' is already a registered serializer!");
      } else {
         ArgumentTypes.Entry<T> ☃ = new ArgumentTypes.Entry<>(☃, ☃, ☃);
         field_197489_b.put(☃, ☃);
         field_197490_c.put(☃, ☃);
      }
   }

   public static void func_197483_a() {
      BrigadierSerializers.func_197511_a();
      func_197487_a(new ResourceLocation("minecraft:entity"), EntityArgument.class, new EntityArgument.Serializer());
      func_197487_a(new ResourceLocation("minecraft:game_profile"), GameProfileArgument.class, new ArgumentSerializer(GameProfileArgument::func_197108_a));
      func_197487_a(new ResourceLocation("minecraft:block_pos"), BlockPosArgument.class, new ArgumentSerializer(BlockPosArgument::func_197276_a));
      func_197487_a(new ResourceLocation("minecraft:column_pos"), ColumnPosArgument.class, new ArgumentSerializer(ColumnPosArgument::func_212603_a));
      func_197487_a(new ResourceLocation("minecraft:vec3"), Vec3Argument.class, new ArgumentSerializer(Vec3Argument::func_197301_a));
      func_197487_a(new ResourceLocation("minecraft:vec2"), Vec2Argument.class, new ArgumentSerializer(Vec2Argument::func_197296_a));
      func_197487_a(new ResourceLocation("minecraft:block_state"), BlockStateArgument.class, new ArgumentSerializer(BlockStateArgument::func_197239_a));
      func_197487_a(
         new ResourceLocation("minecraft:block_predicate"), BlockPredicateArgument.class, new ArgumentSerializer(BlockPredicateArgument::func_199824_a)
      );
      func_197487_a(new ResourceLocation("minecraft:item_stack"), ItemArgument.class, new ArgumentSerializer(ItemArgument::func_197317_a));
      func_197487_a(new ResourceLocation("minecraft:item_predicate"), ItemPredicateArgument.class, new ArgumentSerializer(ItemPredicateArgument::func_199846_a));
      func_197487_a(new ResourceLocation("minecraft:color"), ColorArgument.class, new ArgumentSerializer(ColorArgument::func_197063_a));
      func_197487_a(new ResourceLocation("minecraft:component"), ComponentArgument.class, new ArgumentSerializer(ComponentArgument::func_197067_a));
      func_197487_a(new ResourceLocation("minecraft:message"), MessageArgument.class, new ArgumentSerializer(MessageArgument::func_197123_a));
      func_197487_a(new ResourceLocation("minecraft:nbt"), NBTArgument.class, new ArgumentSerializer(NBTArgument::func_197131_a));
      func_197487_a(new ResourceLocation("minecraft:nbt_path"), NBTPathArgument.class, new ArgumentSerializer(NBTPathArgument::func_197149_a));
      func_197487_a(new ResourceLocation("minecraft:objective"), ObjectiveArgument.class, new ArgumentSerializer(ObjectiveArgument::func_197157_a));
      func_197487_a(
         new ResourceLocation("minecraft:objective_criteria"),
         ObjectiveCriteriaArgument.class,
         new ArgumentSerializer(ObjectiveCriteriaArgument::func_197162_a)
      );
      func_197487_a(new ResourceLocation("minecraft:operation"), OperationArgument.class, new ArgumentSerializer(OperationArgument::func_197184_a));
      func_197487_a(new ResourceLocation("minecraft:particle"), ParticleArgument.class, new ArgumentSerializer(ParticleArgument::func_197190_a));
      func_197487_a(new ResourceLocation("minecraft:rotation"), RotationArgument.class, new ArgumentSerializer(RotationArgument::func_197288_a));
      func_197487_a(
         new ResourceLocation("minecraft:scoreboard_slot"), ScoreboardSlotArgument.class, new ArgumentSerializer(ScoreboardSlotArgument::func_197219_a)
      );
      func_197487_a(new ResourceLocation("minecraft:score_holder"), ScoreHolderArgument.class, new ScoreHolderArgument.Serializer());
      func_197487_a(new ResourceLocation("minecraft:swizzle"), SwizzleArgument.class, new ArgumentSerializer(SwizzleArgument::func_197293_a));
      func_197487_a(new ResourceLocation("minecraft:team"), TeamArgument.class, new ArgumentSerializer(TeamArgument::func_197227_a));
      func_197487_a(new ResourceLocation("minecraft:item_slot"), SlotArgument.class, new ArgumentSerializer(SlotArgument::func_197223_a));
      func_197487_a(
         new ResourceLocation("minecraft:resource_location"), ResourceLocationArgument.class, new ArgumentSerializer(ResourceLocationArgument::func_197197_a)
      );
      func_197487_a(new ResourceLocation("minecraft:mob_effect"), PotionArgument.class, new ArgumentSerializer(PotionArgument::func_197126_a));
      func_197487_a(new ResourceLocation("minecraft:function"), FunctionArgument.class, new ArgumentSerializer(FunctionArgument::func_200021_a));
      func_197487_a(new ResourceLocation("minecraft:entity_anchor"), EntityAnchorArgument.class, new ArgumentSerializer(EntityAnchorArgument::func_201024_a));
      func_197487_a(new ResourceLocation("minecraft:int_range"), RangeArgument.IntRange.class, new RangeArgument.IntRange.Serializer());
      func_197487_a(new ResourceLocation("minecraft:float_range"), RangeArgument.FloatRange.class, new RangeArgument.FloatRange.Serializer());
      func_197487_a(new ResourceLocation("minecraft:item_enchantment"), EnchantmentArgument.class, new ArgumentSerializer(EnchantmentArgument::func_201945_a));
      func_197487_a(new ResourceLocation("minecraft:entity_summon"), EntitySummonArgument.class, new ArgumentSerializer(EntitySummonArgument::func_211366_a));
      func_197487_a(new ResourceLocation("minecraft:dimension"), DimensionArgument.class, new ArgumentSerializer(DimensionArgument::func_212595_a));
   }

   @Nullable
   private static ArgumentTypes.Entry<?> func_197482_a(ResourceLocation var0) {
      return (ArgumentTypes.Entry<?>)field_197490_c.get(☃);
   }

   @Nullable
   private static ArgumentTypes.Entry<?> func_201040_a(ArgumentType<?> var0) {
      return (ArgumentTypes.Entry<?>)field_197489_b.get(☃.getClass());
   }

   public static <T extends ArgumentType<?>> void func_197484_a(PacketBuffer var0, T var1) {
      ArgumentTypes.Entry<T> ☃ = func_201040_a(☃);
      if (☃ == null) {
         field_197488_a.error("Could not serialize {} ({}) - will not be sent to client!", ☃, ☃.getClass());
         ☃.func_192572_a(new ResourceLocation(""));
      } else {
         ☃.func_192572_a(☃.field_197481_c);
         ☃.field_197480_b.func_197072_a(☃, ☃);
      }
   }

   @Nullable
   public static ArgumentType<?> func_197486_a(PacketBuffer var0) {
      ResourceLocation ☃ = ☃.func_192575_l();
      ArgumentTypes.Entry<?> ☃x = func_197482_a(☃);
      if (☃x == null) {
         field_197488_a.error("Could not deserialize {}", ☃);
         return null;
      } else {
         return ☃x.field_197480_b.func_197071_b(☃);
      }
   }

   private static <T extends ArgumentType<?>> void func_201042_a(JsonObject var0, T var1) {
      ArgumentTypes.Entry<T> ☃ = func_201040_a(☃);
      if (☃ == null) {
         field_197488_a.error("Could not serialize argument {} ({})!", ☃, ☃.getClass());
         ☃.addProperty("type", "unknown");
      } else {
         ☃.addProperty("type", "argument");
         ☃.addProperty("parser", ☃.field_197481_c.toString());
         JsonObject ☃ = new JsonObject();
         ☃.field_197480_b.func_212244_a(☃, ☃);
         if (☃.size() > 0) {
            ☃.add("properties", ☃);
         }
      }
   }

   public static <S> JsonObject func_200388_a(CommandDispatcher<S> var0, CommandNode<S> var1) {
      JsonObject ☃ = new JsonObject();
      if (☃ instanceof RootCommandNode) {
         ☃.addProperty("type", "root");
      } else if (☃ instanceof LiteralCommandNode) {
         ☃.addProperty("type", "literal");
      } else if (☃ instanceof ArgumentCommandNode) {
         func_201042_a(☃, ((ArgumentCommandNode)☃).getType());
      } else {
         field_197488_a.error("Could not serialize node {} ({})!", ☃, ☃.getClass());
         ☃.addProperty("type", "unknown");
      }

      JsonObject ☃ = new JsonObject();

      for(CommandNode<S> ☃x : ☃.getChildren()) {
         ☃.add(☃x.getName(), func_200388_a(☃, ☃x));
      }

      if (☃.size() > 0) {
         ☃.add("children", ☃);
      }

      if (☃.getCommand() != null) {
         ☃.addProperty("executable", true);
      }

      if (☃.getRedirect() != null) {
         Collection<String> ☃x = ☃.getPath(☃.getRedirect());
         if (!☃x.isEmpty()) {
            JsonArray ☃xx = new JsonArray();

            for(String ☃xxx : ☃x) {
               ☃xx.add(☃xxx);
            }

            ☃.add("redirect", ☃xx);
         }
      }

      return ☃;
   }

   static class Entry<T extends ArgumentType<?>> {
      public final Class<T> field_197479_a;
      public final IArgumentSerializer<T> field_197480_b;
      public final ResourceLocation field_197481_c;

      private Entry(Class<T> var1, IArgumentSerializer<T> var2, ResourceLocation var3) {
         this.field_197479_a = ☃;
         this.field_197480_b = ☃;
         this.field_197481_c = ☃;
      }
   }
}
