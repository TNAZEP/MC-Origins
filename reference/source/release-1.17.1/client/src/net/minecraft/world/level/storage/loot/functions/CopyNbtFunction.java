package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;

public class CopyNbtFunction extends LootItemConditionalFunction {
   final NbtProvider source;
   final List<CopyNbtFunction.CopyOperation> operations;

   CopyNbtFunction(LootItemCondition[] var1, NbtProvider var2, List<CopyNbtFunction.CopyOperation> var3) {
      super(â˜ƒ);
      this.source = â˜ƒ;
      this.operations = ImmutableList.copyOf(â˜ƒ);
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.COPY_NBT;
   }

   static NbtPathArgument.NbtPath compileNbtPath(String var0) {
      try {
         return new NbtPathArgument().parse(new StringReader(â˜ƒ));
      } catch (CommandSyntaxException var2) {
         throw new IllegalArgumentException("Failed to parse path " + â˜ƒ, var2);
      }
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.source.getReferencedContextParams();
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Tag â˜ƒ = this.source.get(â˜ƒ);
      if (â˜ƒ != null) {
         this.operations.forEach(var2x -> var2x.apply(â˜ƒ::getOrCreateTag, â˜ƒ));
      }

      return â˜ƒ;
   }

   public static CopyNbtFunction.Builder copyData(NbtProvider var0) {
      return new CopyNbtFunction.Builder(â˜ƒ);
   }

   public static CopyNbtFunction.Builder copyData(LootContext.EntityTarget var0) {
      return new CopyNbtFunction.Builder(ContextNbtProvider.forContextEntity(â˜ƒ));
   }

   public static class Builder extends LootItemConditionalFunction.Builder<CopyNbtFunction.Builder> {
      private final NbtProvider source;
      private final List<CopyNbtFunction.CopyOperation> ops = Lists.<CopyNbtFunction.CopyOperation>newArrayList();

      Builder(NbtProvider var1) {
         this.source = â˜ƒ;
      }

      public CopyNbtFunction.Builder copy(String var1, String var2, CopyNbtFunction.MergeStrategy var3) {
         this.ops.add(new CopyNbtFunction.CopyOperation(â˜ƒ, â˜ƒ, â˜ƒ));
         return this;
      }

      public CopyNbtFunction.Builder copy(String var1, String var2) {
         return this.copy(â˜ƒ, â˜ƒ, CopyNbtFunction.MergeStrategy.REPLACE);
      }

      protected CopyNbtFunction.Builder getThis() {
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new CopyNbtFunction(this.getConditions(), this.source, this.ops);
      }
   }

   static class CopyOperation {
      private final String sourcePathText;
      private final NbtPathArgument.NbtPath sourcePath;
      private final String targetPathText;
      private final NbtPathArgument.NbtPath targetPath;
      private final CopyNbtFunction.MergeStrategy op;

      CopyOperation(String var1, String var2, CopyNbtFunction.MergeStrategy var3) {
         this.sourcePathText = â˜ƒ;
         this.sourcePath = CopyNbtFunction.compileNbtPath(â˜ƒ);
         this.targetPathText = â˜ƒ;
         this.targetPath = CopyNbtFunction.compileNbtPath(â˜ƒ);
         this.op = â˜ƒ;
      }

      public void apply(Supplier<Tag> var1, Tag var2) {
         try {
            List<Tag> â˜ƒ = this.sourcePath.get(â˜ƒ);
            if (!â˜ƒ.isEmpty()) {
               this.op.merge((Tag)â˜ƒ.get(), this.targetPath, â˜ƒ);
            }
         } catch (CommandSyntaxException var4) {
         }
      }

      public JsonObject toJson() {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("source", this.sourcePathText);
         â˜ƒ.addProperty("target", this.targetPathText);
         â˜ƒ.addProperty("op", this.op.name);
         return â˜ƒ;
      }

      public static CopyNbtFunction.CopyOperation fromJson(JsonObject var0) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "source");
         String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "target");
         CopyNbtFunction.MergeStrategy â˜ƒxx = CopyNbtFunction.MergeStrategy.getByName(GsonHelper.getAsString(â˜ƒ, "op"));
         return new CopyNbtFunction.CopyOperation(â˜ƒ, â˜ƒx, â˜ƒxx);
      }
   }

   public static enum MergeStrategy {
      REPLACE("replace") {
         @Override
         public void merge(Tag var1, NbtPathArgument.NbtPath var2, List<Tag> var3) throws CommandSyntaxException {
            â˜ƒ.set(â˜ƒ, Iterables.getLast(â˜ƒ)::copy);
         }
      },
      APPEND("append") {
         @Override
         public void merge(Tag var1, NbtPathArgument.NbtPath var2, List<Tag> var3) throws CommandSyntaxException {
            List<Tag> â˜ƒ = â˜ƒ.getOrCreate(â˜ƒ, ListTag::new);
            â˜ƒ.forEach(var1x -> {
               if (var1x instanceof ListTag) {
                  â˜ƒ.forEach(var1xx -> ((ListTag)var1x).add(var1xx.copy()));
               }
            });
         }
      },
      MERGE("merge") {
         @Override
         public void merge(Tag var1, NbtPathArgument.NbtPath var2, List<Tag> var3) throws CommandSyntaxException {
            List<Tag> â˜ƒ = â˜ƒ.getOrCreate(â˜ƒ, CompoundTag::new);
            â˜ƒ.forEach(var1x -> {
               if (var1x instanceof CompoundTag) {
                  â˜ƒ.forEach(var1xx -> {
                     if (var1xx instanceof CompoundTag) {
                        ((CompoundTag)var1x).merge((CompoundTag)var1xx);
                     }
                  });
               }
            });
         }
      };

      final String name;

      public abstract void merge(Tag var1, NbtPathArgument.NbtPath var2, List<Tag> var3) throws CommandSyntaxException;

      MergeStrategy(String var3) {
         this.name = â˜ƒ;
      }

      public static CopyNbtFunction.MergeStrategy getByName(String var0) {
         for(CopyNbtFunction.MergeStrategy â˜ƒ : values()) {
            if (â˜ƒ.name.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         throw new IllegalArgumentException("Invalid merge strategy" + â˜ƒ);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<CopyNbtFunction> {
      public void serialize(JsonObject var1, CopyNbtFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("source", â˜ƒ.serialize(â˜ƒ.source));
         JsonArray â˜ƒ = new JsonArray();
         â˜ƒ.operations.stream().map(CopyNbtFunction.CopyOperation::toJson).forEach(â˜ƒ::add);
         â˜ƒ.add("ops", â˜ƒ);
      }

      public CopyNbtFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         NbtProvider â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "source", â˜ƒ, NbtProvider.class);
         List<CopyNbtFunction.CopyOperation> â˜ƒx = Lists.<CopyNbtFunction.CopyOperation>newArrayList();

         for(JsonElement â˜ƒxx : GsonHelper.getAsJsonArray(â˜ƒ, "ops")) {
            JsonObject â˜ƒxxx = GsonHelper.convertToJsonObject(â˜ƒxx, "op");
            â˜ƒx.add(CopyNbtFunction.CopyOperation.fromJson(â˜ƒxxx));
         }

         return new CopyNbtFunction(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }
}
