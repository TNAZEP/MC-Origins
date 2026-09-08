package net.minecraft.server.packs.repository;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pack implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String id;
   private final Supplier<PackResources> supplier;
   private final Component title;
   private final Component description;
   private final PackCompatibility compatibility;
   private final Pack.Position defaultPosition;
   private final boolean required;
   private final boolean fixedPosition;
   private final PackSource packSource;

   @Nullable
   public static Pack create(String var0, boolean var1, Supplier<PackResources> var2, Pack.PackConstructor var3, Pack.Position var4, PackSource var5) {
      try {
         Pack var8;
         try (PackResources â˜ƒ = (PackResources)â˜ƒ.get()) {
            PackMetadataSection â˜ƒx = â˜ƒ.getMetadataSection(PackMetadataSection.SERIALIZER);
            if (â˜ƒx == null) {
               LOGGER.warn("Couldn't find pack meta for pack {}", â˜ƒ);
               return null;
            }

            var8 = â˜ƒ.create(â˜ƒ, new TextComponent(â˜ƒ.getName()), â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
         }

         return var8;
      } catch (IOException var11) {
         LOGGER.warn("Couldn't get pack info for: {}", var11.toString());
         return null;
      }
   }

   public Pack(
      String var1,
      boolean var2,
      Supplier<PackResources> var3,
      Component var4,
      Component var5,
      PackCompatibility var6,
      Pack.Position var7,
      boolean var8,
      PackSource var9
   ) {
      this.id = â˜ƒ;
      this.supplier = â˜ƒ;
      this.title = â˜ƒ;
      this.description = â˜ƒ;
      this.compatibility = â˜ƒ;
      this.required = â˜ƒ;
      this.defaultPosition = â˜ƒ;
      this.fixedPosition = â˜ƒ;
      this.packSource = â˜ƒ;
   }

   public Pack(
      String var1, Component var2, boolean var3, Supplier<PackResources> var4, PackMetadataSection var5, PackType var6, Pack.Position var7, PackSource var8
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getDescription(), PackCompatibility.forMetadata(â˜ƒ, â˜ƒ), â˜ƒ, false, â˜ƒ);
   }

   public Component getTitle() {
      return this.title;
   }

   public Component getDescription() {
      return this.description;
   }

   public Component getChatLink(boolean var1) {
      return ComponentUtils.wrapInSquareBrackets(this.packSource.decorate(new TextComponent(this.id)))
         .withStyle(
            var2 -> var2.withColor(â˜ƒ ? ChatFormatting.GREEN : ChatFormatting.RED)
                  .withInsertion(StringArgumentType.escapeIfRequired(this.id))
                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent("").append(this.title).append("\n").append(this.description)))
         );
   }

   public PackCompatibility getCompatibility() {
      return this.compatibility;
   }

   public PackResources open() {
      return (PackResources)this.supplier.get();
   }

   public String getId() {
      return this.id;
   }

   public boolean isRequired() {
      return this.required;
   }

   public boolean isFixedPosition() {
      return this.fixedPosition;
   }

   public Pack.Position getDefaultPosition() {
      return this.defaultPosition;
   }

   public PackSource getPackSource() {
      return this.packSource;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (!(â˜ƒ instanceof Pack)) {
         return false;
      } else {
         Pack â˜ƒ = (Pack)â˜ƒ;
         return this.id.equals(â˜ƒ.id);
      }
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public void close() {
   }

   @FunctionalInterface
   public interface PackConstructor {
      @Nullable
      Pack create(String var1, Component var2, boolean var3, Supplier<PackResources> var4, PackMetadataSection var5, Pack.Position var6, PackSource var7);
   }

   public static enum Position {
      TOP,
      BOTTOM;

      public <T> int insert(List<T> var1, T var2, Function<T, Pack> var3, boolean var4) {
         Pack.Position â˜ƒ = â˜ƒ ? this.opposite() : this;
         if (â˜ƒ == BOTTOM) {
            int â˜ƒ;
            for(â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
               Pack â˜ƒx = (Pack)â˜ƒ.apply(â˜ƒ.get(â˜ƒ));
               if (!â˜ƒx.isFixedPosition() || â˜ƒx.getDefaultPosition() != this) {
                  break;
               }
            }

            â˜ƒ.add(â˜ƒ, â˜ƒ);
            return â˜ƒ;
         } else {
            int â˜ƒ;
            for(â˜ƒ = â˜ƒ.size() - 1; â˜ƒ >= 0; --â˜ƒ) {
               Pack â˜ƒ = (Pack)â˜ƒ.apply(â˜ƒ.get(â˜ƒ));
               if (!â˜ƒ.isFixedPosition() || â˜ƒ.getDefaultPosition() != this) {
                  break;
               }
            }

            â˜ƒ.add(â˜ƒ + 1, â˜ƒ);
            return â˜ƒ + 1;
         }
      }

      public Pack.Position opposite() {
         return this == TOP ? BOTTOM : TOP;
      }
   }
}
