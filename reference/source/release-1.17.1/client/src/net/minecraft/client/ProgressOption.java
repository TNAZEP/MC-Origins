package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.SliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;

public class ProgressOption extends Option {
   protected final float steps;
   protected final double minValue;
   protected double maxValue;
   private final Function<Options, Double> getter;
   private final BiConsumer<Options, Double> setter;
   private final BiFunction<Options, ProgressOption, Component> toString;
   private final Function<Minecraft, List<FormattedCharSequence>> tooltipSupplier;

   public ProgressOption(
      String var1,
      double var2,
      double var4,
      float var6,
      Function<Options, Double> var7,
      BiConsumer<Options, Double> var8,
      BiFunction<Options, ProgressOption, Component> var9,
      Function<Minecraft, List<FormattedCharSequence>> var10
   ) {
      super(â˜ƒ);
      this.minValue = â˜ƒ;
      this.maxValue = â˜ƒ;
      this.steps = â˜ƒ;
      this.getter = â˜ƒ;
      this.setter = â˜ƒ;
      this.toString = â˜ƒ;
      this.tooltipSupplier = â˜ƒ;
   }

   public ProgressOption(
      String var1,
      double var2,
      double var4,
      float var6,
      Function<Options, Double> var7,
      BiConsumer<Options, Double> var8,
      BiFunction<Options, ProgressOption, Component> var9
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, var0 -> ImmutableList.of());
   }

   @Override
   public AbstractWidget createButton(Options var1, int var2, int var3, int var4) {
      List<FormattedCharSequence> â˜ƒ = (List)this.tooltipSupplier.apply(Minecraft.getInstance());
      return new SliderButton(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 20, this, â˜ƒ);
   }

   public double toPct(double var1) {
      return Mth.clamp((this.clamp(â˜ƒ) - this.minValue) / (this.maxValue - this.minValue), 0.0, 1.0);
   }

   public double toValue(double var1) {
      return this.clamp(Mth.lerp(Mth.clamp(â˜ƒ, 0.0, 1.0), this.minValue, this.maxValue));
   }

   private double clamp(double var1) {
      if (this.steps > 0.0F) {
         â˜ƒ = (double)(this.steps * (float)Math.round(â˜ƒ / (double)this.steps));
      }

      return Mth.clamp(â˜ƒ, this.minValue, this.maxValue);
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public void setMaxValue(float var1) {
      this.maxValue = (double)â˜ƒ;
   }

   public void set(Options var1, double var2) {
      this.setter.accept(â˜ƒ, â˜ƒ);
   }

   public double get(Options var1) {
      return this.getter.apply(â˜ƒ);
   }

   public Component getMessage(Options var1) {
      return (Component)this.toString.apply(â˜ƒ, this);
   }
}
