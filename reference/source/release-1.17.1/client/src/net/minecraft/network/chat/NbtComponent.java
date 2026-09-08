package net.minecraft.network.chat;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class NbtComponent extends BaseComponent implements ContextAwareComponent {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final boolean interpreting;
   protected final Optional<Component> separator;
   protected final String nbtPathPattern;
   @Nullable
   protected final NbtPathArgument.NbtPath compiledNbtPath;

   @Nullable
   private static NbtPathArgument.NbtPath compileNbtPath(String var0) {
      try {
         return new NbtPathArgument().parse(new StringReader(â˜ƒ));
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public NbtComponent(String var1, boolean var2, Optional<Component> var3) {
      this(â˜ƒ, compileNbtPath(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   protected NbtComponent(String var1, @Nullable NbtPathArgument.NbtPath var2, boolean var3, Optional<Component> var4) {
      this.nbtPathPattern = â˜ƒ;
      this.compiledNbtPath = â˜ƒ;
      this.interpreting = â˜ƒ;
      this.separator = â˜ƒ;
   }

   protected abstract Stream<CompoundTag> getData(CommandSourceStack var1) throws CommandSyntaxException;

   public String getNbtPath() {
      return this.nbtPathPattern;
   }

   public boolean isInterpreting() {
      return this.interpreting;
   }

   @Override
   public MutableComponent resolve(@Nullable CommandSourceStack var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      if (â˜ƒ != null && this.compiledNbtPath != null) {
         Stream<String> â˜ƒ = this.getData(â˜ƒ).flatMap(var1x -> {
            try {
               return this.compiledNbtPath.get(var1x).stream();
            } catch (CommandSyntaxException var3xx) {
               return Stream.empty();
            }
         }).map(Tag::getAsString);
         if (this.interpreting) {
            Component â˜ƒx = DataFixUtils.orElse(ComponentUtils.updateForEntity(â˜ƒ, this.separator, â˜ƒ, â˜ƒ), ComponentUtils.DEFAULT_NO_STYLE_SEPARATOR);
            return (MutableComponent)â˜ƒ.flatMap(var3x -> {
               try {
                  MutableComponent â˜ƒ = Component.Serializer.fromJson(var3x);
                  return Stream.of(ComponentUtils.updateForEntity(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
               } catch (Exception var5xx) {
                  LOGGER.warn("Failed to parse component: {}", var3x, var5xx);
                  return Stream.of();
               }
            }).reduce((var1x, var2x) -> var1x.append(â˜ƒ).append(var2x)).orElseGet(() -> new TextComponent(""));
         } else {
            return (MutableComponent)ComponentUtils.updateForEntity(â˜ƒ, this.separator, â˜ƒ, â˜ƒ)
               .map(
                  var1x -> (MutableComponent)â˜ƒ.map(var0x -> new TextComponent(var0x))
                        .reduce((var1xx, var2x) -> var1xx.append(var1x).append(var2x))
                        .orElseGet(() -> new TextComponent(""))
               )
               .orElseGet(() -> new TextComponent((String)â˜ƒ.collect(Collectors.joining(", "))));
         }
      } else {
         return new TextComponent("");
      }
   }

   public static class BlockNbtComponent extends NbtComponent {
      private final String posPattern;
      @Nullable
      private final Coordinates compiledPos;

      public BlockNbtComponent(String var1, boolean var2, String var3, Optional<Component> var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.posPattern = â˜ƒ;
         this.compiledPos = this.compilePos(this.posPattern);
      }

      @Nullable
      private Coordinates compilePos(String var1) {
         try {
            return BlockPosArgument.blockPos().parse(new StringReader(â˜ƒ));
         } catch (CommandSyntaxException var3) {
            return null;
         }
      }

      private BlockNbtComponent(
         String var1, @Nullable NbtPathArgument.NbtPath var2, boolean var3, String var4, @Nullable Coordinates var5, Optional<Component> var6
      ) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.posPattern = â˜ƒ;
         this.compiledPos = â˜ƒ;
      }

      @Nullable
      public String getPos() {
         return this.posPattern;
      }

      public NbtComponent.BlockNbtComponent plainCopy() {
         return new NbtComponent.BlockNbtComponent(
            this.nbtPathPattern, this.compiledNbtPath, this.interpreting, this.posPattern, this.compiledPos, this.separator
         );
      }

      @Override
      protected Stream<CompoundTag> getData(CommandSourceStack var1) {
         if (this.compiledPos != null) {
            ServerLevel â˜ƒ = â˜ƒ.getLevel();
            BlockPos â˜ƒx = this.compiledPos.getBlockPos(â˜ƒ);
            if (â˜ƒ.isLoaded(â˜ƒx)) {
               BlockEntity â˜ƒxx = â˜ƒ.getBlockEntity(â˜ƒx);
               if (â˜ƒxx != null) {
                  return Stream.of(â˜ƒxx.save(new CompoundTag()));
               }
            }
         }

         return Stream.empty();
      }

      @Override
      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (!(â˜ƒ instanceof NbtComponent.BlockNbtComponent)) {
            return false;
         } else {
            NbtComponent.BlockNbtComponent â˜ƒ = (NbtComponent.BlockNbtComponent)â˜ƒ;
            return Objects.equals(this.posPattern, â˜ƒ.posPattern) && Objects.equals(this.nbtPathPattern, â˜ƒ.nbtPathPattern) && super.equals(â˜ƒ);
         }
      }

      @Override
      public String toString() {
         return "BlockPosArgument{pos='"
            + this.posPattern
            + "'path='"
            + this.nbtPathPattern
            + "', siblings="
            + this.siblings
            + ", style="
            + this.getStyle()
            + "}";
      }
   }

   public static class EntityNbtComponent extends NbtComponent {
      private final String selectorPattern;
      @Nullable
      private final EntitySelector compiledSelector;

      public EntityNbtComponent(String var1, boolean var2, String var3, Optional<Component> var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.selectorPattern = â˜ƒ;
         this.compiledSelector = compileSelector(â˜ƒ);
      }

      @Nullable
      private static EntitySelector compileSelector(String var0) {
         try {
            EntitySelectorParser â˜ƒ = new EntitySelectorParser(new StringReader(â˜ƒ));
            return â˜ƒ.parse();
         } catch (CommandSyntaxException var2) {
            return null;
         }
      }

      private EntityNbtComponent(
         String var1, @Nullable NbtPathArgument.NbtPath var2, boolean var3, String var4, @Nullable EntitySelector var5, Optional<Component> var6
      ) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.selectorPattern = â˜ƒ;
         this.compiledSelector = â˜ƒ;
      }

      public String getSelector() {
         return this.selectorPattern;
      }

      public NbtComponent.EntityNbtComponent plainCopy() {
         return new NbtComponent.EntityNbtComponent(
            this.nbtPathPattern, this.compiledNbtPath, this.interpreting, this.selectorPattern, this.compiledSelector, this.separator
         );
      }

      @Override
      protected Stream<CompoundTag> getData(CommandSourceStack var1) throws CommandSyntaxException {
         if (this.compiledSelector != null) {
            List<? extends Entity> â˜ƒ = this.compiledSelector.findEntities(â˜ƒ);
            return â˜ƒ.stream().map(NbtPredicate::getEntityTagToCompare);
         } else {
            return Stream.empty();
         }
      }

      @Override
      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (!(â˜ƒ instanceof NbtComponent.EntityNbtComponent)) {
            return false;
         } else {
            NbtComponent.EntityNbtComponent â˜ƒ = (NbtComponent.EntityNbtComponent)â˜ƒ;
            return Objects.equals(this.selectorPattern, â˜ƒ.selectorPattern) && Objects.equals(this.nbtPathPattern, â˜ƒ.nbtPathPattern) && super.equals(â˜ƒ);
         }
      }

      @Override
      public String toString() {
         return "EntityNbtComponent{selector='"
            + this.selectorPattern
            + "'path='"
            + this.nbtPathPattern
            + "', siblings="
            + this.siblings
            + ", style="
            + this.getStyle()
            + "}";
      }
   }

   public static class StorageNbtComponent extends NbtComponent {
      private final ResourceLocation id;

      public StorageNbtComponent(String var1, boolean var2, ResourceLocation var3, Optional<Component> var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ);
         this.id = â˜ƒ;
      }

      public StorageNbtComponent(String var1, @Nullable NbtPathArgument.NbtPath var2, boolean var3, ResourceLocation var4, Optional<Component> var5) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.id = â˜ƒ;
      }

      public ResourceLocation getId() {
         return this.id;
      }

      public NbtComponent.StorageNbtComponent plainCopy() {
         return new NbtComponent.StorageNbtComponent(this.nbtPathPattern, this.compiledNbtPath, this.interpreting, this.id, this.separator);
      }

      @Override
      protected Stream<CompoundTag> getData(CommandSourceStack var1) {
         CompoundTag â˜ƒ = â˜ƒ.getServer().getCommandStorage().get(this.id);
         return Stream.of(â˜ƒ);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == â˜ƒ) {
            return true;
         } else if (!(â˜ƒ instanceof NbtComponent.StorageNbtComponent)) {
            return false;
         } else {
            NbtComponent.StorageNbtComponent â˜ƒ = (NbtComponent.StorageNbtComponent)â˜ƒ;
            return Objects.equals(this.id, â˜ƒ.id) && Objects.equals(this.nbtPathPattern, â˜ƒ.nbtPathPattern) && super.equals(â˜ƒ);
         }
      }

      @Override
      public String toString() {
         return "StorageNbtComponent{id='" + this.id + "'path='" + this.nbtPathPattern + "', siblings=" + this.siblings + ", style=" + this.getStyle() + "}";
      }
   }
}
