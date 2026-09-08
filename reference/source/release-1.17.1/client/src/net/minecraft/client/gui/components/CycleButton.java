package net.minecraft.client.gui.components;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class CycleButton<T> extends AbstractButton implements TooltipAccessor {
   static final BooleanSupplier DEFAULT_ALT_LIST_SELECTOR = Screen::hasAltDown;
   private static final List<Boolean> BOOLEAN_OPTIONS = ImmutableList.of(Boolean.TRUE, Boolean.FALSE);
   private final Component name;
   private int index;
   private T value;
   private final CycleButton.ValueListSupplier<T> values;
   private final Function<T, Component> valueStringifier;
   private final Function<CycleButton<T>, MutableComponent> narrationProvider;
   private final CycleButton.OnValueChange<T> onValueChange;
   private final CycleButton.TooltipSupplier<T> tooltipSupplier;
   private final boolean displayOnlyValue;

   CycleButton(
      int var1,
      int var2,
      int var3,
      int var4,
      Component var5,
      Component var6,
      int var7,
      T var8,
      CycleButton.ValueListSupplier<T> var9,
      Function<T, Component> var10,
      Function<CycleButton<T>, MutableComponent> var11,
      CycleButton.OnValueChange<T> var12,
      CycleButton.TooltipSupplier<T> var13,
      boolean var14
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.name = â˜ƒ;
      this.index = â˜ƒ;
      this.value = â˜ƒ;
      this.values = â˜ƒ;
      this.valueStringifier = â˜ƒ;
      this.narrationProvider = â˜ƒ;
      this.onValueChange = â˜ƒ;
      this.tooltipSupplier = â˜ƒ;
      this.displayOnlyValue = â˜ƒ;
   }

   @Override
   public void onPress() {
      if (Screen.hasShiftDown()) {
         this.cycleValue(-1);
      } else {
         this.cycleValue(1);
      }
   }

   private void cycleValue(int var1) {
      List<T> â˜ƒ = this.values.getSelectedList();
      this.index = Mth.positiveModulo(this.index + â˜ƒ, â˜ƒ.size());
      T â˜ƒx = (T)â˜ƒ.get(this.index);
      this.updateValue(â˜ƒx);
      this.onValueChange.onValueChange(this, â˜ƒx);
   }

   private T getCycledValue(int var1) {
      List<T> â˜ƒ = this.values.getSelectedList();
      return (T)â˜ƒ.get(Mth.positiveModulo(this.index + â˜ƒ, â˜ƒ.size()));
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5) {
      if (â˜ƒ > 0.0) {
         this.cycleValue(-1);
      } else if (â˜ƒ < 0.0) {
         this.cycleValue(1);
      }

      return true;
   }

   public void setValue(T var1) {
      List<T> â˜ƒ = this.values.getSelectedList();
      int â˜ƒx = â˜ƒ.indexOf(â˜ƒ);
      if (â˜ƒx != -1) {
         this.index = â˜ƒx;
      }

      this.updateValue(â˜ƒ);
   }

   private void updateValue(T var1) {
      Component â˜ƒ = this.createLabelForValue(â˜ƒ);
      this.setMessage(â˜ƒ);
      this.value = â˜ƒ;
   }

   private Component createLabelForValue(T var1) {
      return (Component)(this.displayOnlyValue ? (Component)this.valueStringifier.apply(â˜ƒ) : this.createFullName(â˜ƒ));
   }

   private MutableComponent createFullName(T var1) {
      return CommonComponents.optionNameValue(this.name, (Component)this.valueStringifier.apply(â˜ƒ));
   }

   public T getValue() {
      return this.value;
   }

   @Override
   protected MutableComponent createNarrationMessage() {
      return (MutableComponent)this.narrationProvider.apply(this);
   }

   @Override
   public void updateNarration(NarrationElementOutput var1) {
      â˜ƒ.add(NarratedElementType.TITLE, this.createNarrationMessage());
      if (this.active) {
         T â˜ƒ = this.getCycledValue(1);
         Component â˜ƒx = this.createLabelForValue(â˜ƒ);
         if (this.isFocused()) {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.cycle_button.usage.focused", â˜ƒx));
         } else {
            â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.cycle_button.usage.hovered", â˜ƒx));
         }
      }
   }

   public MutableComponent createDefaultNarrationMessage() {
      return wrapDefaultNarrationMessage((Component)(this.displayOnlyValue ? this.createFullName(this.value) : this.getMessage()));
   }

   @Override
   public List<FormattedCharSequence> getTooltip() {
      return (List<FormattedCharSequence>)this.tooltipSupplier.apply(this.value);
   }

   public static <T> CycleButton.Builder<T> builder(Function<T, Component> var0) {
      return new CycleButton.Builder<>(â˜ƒ);
   }

   public static CycleButton.Builder<Boolean> booleanBuilder(Component var0, Component var1) {
      return new CycleButton.Builder(var2 -> var2 ? â˜ƒ : â˜ƒ).withValues(BOOLEAN_OPTIONS);
   }

   public static CycleButton.Builder<Boolean> onOffBuilder() {
      return new CycleButton.Builder(var0 -> var0 ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF).withValues(BOOLEAN_OPTIONS);
   }

   public static CycleButton.Builder<Boolean> onOffBuilder(boolean var0) {
      return onOffBuilder().withInitialValue((T)â˜ƒ);
   }

   public static class Builder<T> {
      private int initialIndex;
      @Nullable
      private T initialValue;
      private final Function<T, Component> valueStringifier;
      private CycleButton.TooltipSupplier<T> tooltipSupplier = var0 -> ImmutableList.of();
      private Function<CycleButton<T>, MutableComponent> narrationProvider = CycleButton::createDefaultNarrationMessage;
      private CycleButton.ValueListSupplier<T> values = CycleButton.ValueListSupplier.create(ImmutableList.of());
      private boolean displayOnlyValue;

      public Builder(Function<T, Component> var1) {
         this.valueStringifier = â˜ƒ;
      }

      public CycleButton.Builder<T> withValues(List<T> var1) {
         this.values = CycleButton.ValueListSupplier.create(â˜ƒ);
         return this;
      }

      @SafeVarargs
      public final CycleButton.Builder<T> withValues(T... var1) {
         return this.withValues(ImmutableList.copyOf(â˜ƒ));
      }

      public CycleButton.Builder<T> withValues(List<T> var1, List<T> var2) {
         this.values = CycleButton.ValueListSupplier.create(CycleButton.DEFAULT_ALT_LIST_SELECTOR, â˜ƒ, â˜ƒ);
         return this;
      }

      public CycleButton.Builder<T> withValues(BooleanSupplier var1, List<T> var2, List<T> var3) {
         this.values = CycleButton.ValueListSupplier.create(â˜ƒ, â˜ƒ, â˜ƒ);
         return this;
      }

      public CycleButton.Builder<T> withTooltip(CycleButton.TooltipSupplier<T> var1) {
         this.tooltipSupplier = â˜ƒ;
         return this;
      }

      public CycleButton.Builder<T> withInitialValue(T var1) {
         this.initialValue = â˜ƒ;
         int â˜ƒ = this.values.getDefaultList().indexOf(â˜ƒ);
         if (â˜ƒ != -1) {
            this.initialIndex = â˜ƒ;
         }

         return this;
      }

      public CycleButton.Builder<T> withCustomNarration(Function<CycleButton<T>, MutableComponent> var1) {
         this.narrationProvider = â˜ƒ;
         return this;
      }

      public CycleButton.Builder<T> displayOnlyValue() {
         this.displayOnlyValue = true;
         return this;
      }

      public CycleButton<T> create(int var1, int var2, int var3, int var4, Component var5) {
         return this.create(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (var0, var1x) -> {
         });
      }

      public CycleButton<T> create(int var1, int var2, int var3, int var4, Component var5, CycleButton.OnValueChange<T> var6) {
         List<T> â˜ƒ = this.values.getDefaultList();
         if (â˜ƒ.isEmpty()) {
            throw new IllegalStateException("No values for cycle button");
         } else {
            T â˜ƒ = (T)(this.initialValue != null ? this.initialValue : â˜ƒ.get(this.initialIndex));
            Component â˜ƒx = (Component)this.valueStringifier.apply(â˜ƒ);
            Component â˜ƒxx = (Component)(this.displayOnlyValue ? â˜ƒx : CommonComponents.optionNameValue(â˜ƒ, â˜ƒx));
            return new CycleButton<>(
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒ,
               â˜ƒxx,
               â˜ƒ,
               this.initialIndex,
               â˜ƒ,
               this.values,
               this.valueStringifier,
               this.narrationProvider,
               â˜ƒ,
               this.tooltipSupplier,
               this.displayOnlyValue
            );
         }
      }
   }

   public interface OnValueChange<T> {
      void onValueChange(CycleButton var1, T var2);
   }

   @FunctionalInterface
   public interface TooltipSupplier<T> extends Function<T, List<FormattedCharSequence>> {
   }

   interface ValueListSupplier<T> {
      List<T> getSelectedList();

      List<T> getDefaultList();

      static <T> CycleButton.ValueListSupplier<T> create(List<T> var0) {
         final List<T> â˜ƒ = ImmutableList.copyOf(â˜ƒ);
         return new CycleButton.ValueListSupplier<T>() {
            @Override
            public List<T> getSelectedList() {
               return â˜ƒ;
            }

            @Override
            public List<T> getDefaultList() {
               return â˜ƒ;
            }
         };
      }

      static <T> CycleButton.ValueListSupplier<T> create(final BooleanSupplier var0, List<T> var1, List<T> var2) {
         final List<T> â˜ƒ = ImmutableList.copyOf(â˜ƒ);
         final List<T> â˜ƒx = ImmutableList.copyOf(â˜ƒ);
         return new CycleButton.ValueListSupplier<T>() {
            @Override
            public List<T> getSelectedList() {
               return â˜ƒ.getAsBoolean() ? â˜ƒ : â˜ƒ;
            }

            @Override
            public List<T> getDefaultList() {
               return â˜ƒ;
            }
         };
      }
   }
}
