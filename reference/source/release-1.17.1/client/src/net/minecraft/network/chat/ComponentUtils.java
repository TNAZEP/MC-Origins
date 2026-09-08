package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;

public class ComponentUtils {
   public static final String DEFAULT_SEPARATOR_TEXT = ", ";
   public static final Component DEFAULT_SEPARATOR = new TextComponent(", ").withStyle(ChatFormatting.GRAY);
   public static final Component DEFAULT_NO_STYLE_SEPARATOR = new TextComponent(", ");

   public static MutableComponent mergeStyles(MutableComponent var0, Style var1) {
      if (â˜ƒ.isEmpty()) {
         return â˜ƒ;
      } else {
         Style â˜ƒ = â˜ƒ.getStyle();
         if (â˜ƒ.isEmpty()) {
            return â˜ƒ.setStyle(â˜ƒ);
         } else {
            return â˜ƒ.equals(â˜ƒ) ? â˜ƒ : â˜ƒ.setStyle(â˜ƒ.applyTo(â˜ƒ));
         }
      }
   }

   public static Optional<MutableComponent> updateForEntity(@Nullable CommandSourceStack var0, Optional<Component> var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      return â˜ƒ.isPresent() ? Optional.of(updateForEntity(â˜ƒ, (Component)â˜ƒ.get(), â˜ƒ, â˜ƒ)) : Optional.empty();
   }

   public static MutableComponent updateForEntity(@Nullable CommandSourceStack var0, Component var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      if (â˜ƒ > 100) {
         return â˜ƒ.copy();
      } else {
         MutableComponent â˜ƒ = â˜ƒ instanceof ContextAwareComponent ? ((ContextAwareComponent)â˜ƒ).resolve(â˜ƒ, â˜ƒ, â˜ƒ + 1) : â˜ƒ.plainCopy();

         for(Component â˜ƒx : â˜ƒ.getSiblings()) {
            â˜ƒ.append(updateForEntity(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ + 1));
         }

         return â˜ƒ.withStyle(resolveStyle(â˜ƒ, â˜ƒ.getStyle(), â˜ƒ, â˜ƒ));
      }
   }

   private static Style resolveStyle(@Nullable CommandSourceStack var0, Style var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      HoverEvent â˜ƒ = â˜ƒ.getHoverEvent();
      if (â˜ƒ != null) {
         Component â˜ƒx = â˜ƒ.getValue(HoverEvent.Action.SHOW_TEXT);
         if (â˜ƒx != null) {
            HoverEvent â˜ƒxx = new HoverEvent(HoverEvent.Action.SHOW_TEXT, updateForEntity(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ + 1));
            return â˜ƒ.withHoverEvent(â˜ƒxx);
         }
      }

      return â˜ƒ;
   }

   public static Component getDisplayName(GameProfile var0) {
      if (â˜ƒ.getName() != null) {
         return new TextComponent(â˜ƒ.getName());
      } else {
         return â˜ƒ.getId() != null ? new TextComponent(â˜ƒ.getId().toString()) : new TextComponent("(unknown)");
      }
   }

   public static Component formatList(Collection<String> var0) {
      return formatAndSortList(â˜ƒ, var0x -> new TextComponent(var0x).withStyle(ChatFormatting.GREEN));
   }

   public static <T extends Comparable<T>> Component formatAndSortList(Collection<T> var0, Function<T, Component> var1) {
      if (â˜ƒ.isEmpty()) {
         return TextComponent.EMPTY;
      } else if (â˜ƒ.size() == 1) {
         return (Component)â˜ƒ.apply((Comparable)â˜ƒ.iterator().next());
      } else {
         List<T> â˜ƒ = Lists.newArrayList(â˜ƒ);
         â˜ƒ.sort(Comparable::compareTo);
         return formatList(â˜ƒ, â˜ƒ);
      }
   }

   public static <T> Component formatList(Collection<? extends T> var0, Function<T, Component> var1) {
      return formatList(â˜ƒ, DEFAULT_SEPARATOR, â˜ƒ);
   }

   public static <T> MutableComponent formatList(Collection<? extends T> var0, Optional<? extends Component> var1, Function<T, Component> var2) {
      return formatList(â˜ƒ, DataFixUtils.orElse(â˜ƒ, DEFAULT_SEPARATOR), â˜ƒ);
   }

   public static Component formatList(Collection<? extends Component> var0, Component var1) {
      return formatList(â˜ƒ, â˜ƒ, Function.identity());
   }

   public static <T> MutableComponent formatList(Collection<? extends T> var0, Component var1, Function<T, Component> var2) {
      if (â˜ƒ.isEmpty()) {
         return new TextComponent("");
      } else if (â˜ƒ.size() == 1) {
         return ((Component)â˜ƒ.apply(â˜ƒ.iterator().next())).copy();
      } else {
         MutableComponent â˜ƒ = new TextComponent("");
         boolean â˜ƒx = true;

         for(T â˜ƒxx : â˜ƒ) {
            if (!â˜ƒx) {
               â˜ƒ.append(â˜ƒ);
            }

            â˜ƒ.append((Component)â˜ƒ.apply(â˜ƒxx));
            â˜ƒx = false;
         }

         return â˜ƒ;
      }
   }

   public static MutableComponent wrapInSquareBrackets(Component var0) {
      return new TranslatableComponent("chat.square_brackets", â˜ƒ);
   }

   public static Component fromMessage(Message var0) {
      return (Component)(â˜ƒ instanceof Component ? (Component)â˜ƒ : new TextComponent(â˜ƒ.getString()));
   }
}
