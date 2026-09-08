package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.dimension.DimensionType;

public abstract class VerticalAnchor {
   public static final Codec<VerticalAnchor> CODEC = ExtraCodecs.xor(
         VerticalAnchor.Absolute.CODEC, ExtraCodecs.xor(VerticalAnchor.AboveBottom.CODEC, VerticalAnchor.BelowTop.CODEC)
      )
      .xmap(VerticalAnchor::merge, VerticalAnchor::split);
   private static final VerticalAnchor BOTTOM = aboveBottom(0);
   private static final VerticalAnchor TOP = belowTop(0);
   private final int value;

   protected VerticalAnchor(int var1) {
      this.value = â˜ƒ;
   }

   public static VerticalAnchor absolute(int var0) {
      return new VerticalAnchor.Absolute(â˜ƒ);
   }

   public static VerticalAnchor aboveBottom(int var0) {
      return new VerticalAnchor.AboveBottom(â˜ƒ);
   }

   public static VerticalAnchor belowTop(int var0) {
      return new VerticalAnchor.BelowTop(â˜ƒ);
   }

   public static VerticalAnchor bottom() {
      return BOTTOM;
   }

   public static VerticalAnchor top() {
      return TOP;
   }

   private static VerticalAnchor merge(Either<VerticalAnchor.Absolute, Either<VerticalAnchor.AboveBottom, VerticalAnchor.BelowTop>> var0) {
      return â˜ƒ.map(Function.identity(), var0x -> var0x.map(Function.identity(), Function.identity()));
   }

   private static Either<VerticalAnchor.Absolute, Either<VerticalAnchor.AboveBottom, VerticalAnchor.BelowTop>> split(VerticalAnchor var0) {
      return â˜ƒ instanceof VerticalAnchor.Absolute
         ? Either.left((VerticalAnchor.Absolute)â˜ƒ)
         : Either.right(â˜ƒ instanceof VerticalAnchor.AboveBottom ? Either.left((VerticalAnchor.AboveBottom)â˜ƒ) : Either.right((VerticalAnchor.BelowTop)â˜ƒ));
   }

   protected int value() {
      return this.value;
   }

   public abstract int resolveY(WorldGenerationContext var1);

   static final class AboveBottom extends VerticalAnchor {
      public static final Codec<VerticalAnchor.AboveBottom> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y)
         .fieldOf("above_bottom")
         .<VerticalAnchor.AboveBottom>xmap(VerticalAnchor.AboveBottom::new, VerticalAnchor::value)
         .codec();

      protected AboveBottom(int var1) {
         super(â˜ƒ);
      }

      @Override
      public int resolveY(WorldGenerationContext var1) {
         return â˜ƒ.getMinGenY() + this.value();
      }

      public String toString() {
         return this.value() + " above bottom";
      }
   }

   static final class Absolute extends VerticalAnchor {
      public static final Codec<VerticalAnchor.Absolute> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y)
         .fieldOf("absolute")
         .<VerticalAnchor.Absolute>xmap(VerticalAnchor.Absolute::new, VerticalAnchor::value)
         .codec();

      protected Absolute(int var1) {
         super(â˜ƒ);
      }

      @Override
      public int resolveY(WorldGenerationContext var1) {
         return this.value();
      }

      public String toString() {
         return this.value() + " absolute";
      }
   }

   static final class BelowTop extends VerticalAnchor {
      public static final Codec<VerticalAnchor.BelowTop> CODEC = Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y)
         .fieldOf("below_top")
         .<VerticalAnchor.BelowTop>xmap(VerticalAnchor.BelowTop::new, VerticalAnchor::value)
         .codec();

      protected BelowTop(int var1) {
         super(â˜ƒ);
      }

      @Override
      public int resolveY(WorldGenerationContext var1) {
         return â˜ƒ.getGenDepth() - 1 + â˜ƒ.getMinGenY() - this.value();
      }

      public String toString() {
         return this.value() + " below top";
      }
   }
}
