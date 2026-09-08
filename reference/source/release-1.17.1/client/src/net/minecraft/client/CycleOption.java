package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class CycleOption<T> extends Option {
   private final CycleOption.OptionSetter<T> setter;
   private final Function<Options, T> getter;
   private final Supplier<CycleButton.Builder<T>> buttonSetup;
   private Function<Minecraft, CycleButton.TooltipSupplier<T>> tooltip = var0 -> var0x -> ImmutableList.of();

   private CycleOption(String var1, Function<Options, T> var2, CycleOption.OptionSetter<T> var3, Supplier<CycleButton.Builder<T>> var4) {
      super(â˜ƒ);
      this.getter = â˜ƒ;
      this.setter = â˜ƒ;
      this.buttonSetup = â˜ƒ;
   }

   public static <T> CycleOption<T> create(String var0, List<T> var1, Function<T, Component> var2, Function<Options, T> var3, CycleOption.OptionSetter<T> var4) {
      return new CycleOption<>(â˜ƒ, â˜ƒ, â˜ƒ, () -> CycleButton.builder(â˜ƒ).withValues(â˜ƒ));
   }

   public static <T> CycleOption<T> create(
      String var0, Supplier<List<T>> var1, Function<T, Component> var2, Function<Options, T> var3, CycleOption.OptionSetter<T> var4
   ) {
      return new CycleOption<>(â˜ƒ, â˜ƒ, â˜ƒ, () -> CycleButton.builder(â˜ƒ).withValues((List<T>)â˜ƒ.get()));
   }

   public static <T> CycleOption<T> create(
      String var0, List<T> var1, List<T> var2, BooleanSupplier var3, Function<T, Component> var4, Function<Options, T> var5, CycleOption.OptionSetter<T> var6
   ) {
      return new CycleOption<>(â˜ƒ, â˜ƒ, â˜ƒ, () -> CycleButton.builder(â˜ƒ).withValues(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static <T> CycleOption<T> create(String var0, T[] var1, Function<T, Component> var2, Function<Options, T> var3, CycleOption.OptionSetter<T> var4) {
      return new CycleOption<>(â˜ƒ, â˜ƒ, â˜ƒ, () -> CycleButton.builder(â˜ƒ).withValues(â˜ƒ));
   }

   public static CycleOption<Boolean> createBinaryOption(
      String var0, Component var1, Component var2, Function<Options, Boolean> var3, CycleOption.OptionSetter<Boolean> var4
   ) {
      return new CycleOption(â˜ƒ, â˜ƒ, â˜ƒ, () -> CycleButton.booleanBuilder(â˜ƒ, â˜ƒ));
   }

   public static CycleOption<Boolean> createOnOff(String var0, Function<Options, Boolean> var1, CycleOption.OptionSetter<Boolean> var2) {
      return new CycleOption(â˜ƒ, â˜ƒ, â˜ƒ, CycleButton::onOffBuilder);
   }

   public static CycleOption<Boolean> createOnOff(String var0, Component var1, Function<Options, Boolean> var2, CycleOption.OptionSetter<Boolean> var3) {
      return createOnOff(â˜ƒ, â˜ƒ, â˜ƒ).setTooltip(var1x -> {
         List<FormattedCharSequence> â˜ƒ = var1x.font.split(â˜ƒ, 200);
         return var1xx -> â˜ƒ;
      });
   }

   public CycleOption<T> setTooltip(Function<Minecraft, CycleButton.TooltipSupplier<T>> var1) {
      this.tooltip = â˜ƒ;
      return this;
   }

   @Override
   public AbstractWidget createButton(Options var1, int var2, int var3, int var4) {
      CycleButton.TooltipSupplier<T> â˜ƒ = (CycleButton.TooltipSupplier)this.tooltip.apply(Minecraft.getInstance());
      return ((CycleButton.Builder)this.buttonSetup.get())
         .withTooltip(â˜ƒ)
         .withInitialValue((T)this.getter.apply(â˜ƒ))
         .create(â˜ƒ, â˜ƒ, â˜ƒ, 20, this.getCaption(), (var2x, var3x) -> {
            this.setter.accept(â˜ƒ, this, var3x);
            â˜ƒ.save();
         });
   }

   @FunctionalInterface
   public interface OptionSetter<T> {
      void accept(Options var1, Option var2, T var3);
   }
}
