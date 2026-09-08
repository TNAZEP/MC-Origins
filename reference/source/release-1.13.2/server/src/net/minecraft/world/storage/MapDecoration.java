package net.minecraft.world.storage;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class MapDecoration {
   private final MapDecoration.Type field_191181_a;
   private byte field_176115_b;
   private byte field_176116_c;
   private byte field_176114_d;
   private final ITextComponent field_204310_e;

   public MapDecoration(MapDecoration.Type var1, byte var2, byte var3, byte var4, @Nullable ITextComponent var5) {
      this.field_191181_a = ☃;
      this.field_176115_b = ☃;
      this.field_176116_c = ☃;
      this.field_176114_d = ☃;
      this.field_204310_e = ☃;
   }

   public MapDecoration.Type func_191179_b() {
      return this.field_191181_a;
   }

   public byte func_176112_b() {
      return this.field_176115_b;
   }

   public byte func_176113_c() {
      return this.field_176116_c;
   }

   public byte func_176111_d() {
      return this.field_176114_d;
   }

   @Nullable
   public ITextComponent func_204309_g() {
      return this.field_204310_e;
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof MapDecoration)) {
         return false;
      } else {
         MapDecoration ☃ = (MapDecoration)☃;
         if (this.field_191181_a != ☃.field_191181_a) {
            return false;
         } else if (this.field_176114_d != ☃.field_176114_d) {
            return false;
         } else if (this.field_176115_b != ☃.field_176115_b) {
            return false;
         } else if (this.field_176116_c != ☃.field_176116_c) {
            return false;
         } else {
            return Objects.equals(this.field_204310_e, ☃.field_204310_e);
         }
      }
   }

   public int hashCode() {
      int ☃ = this.field_191181_a.func_191163_a();
      ☃ = 31 * ☃ + this.field_176115_b;
      ☃ = 31 * ☃ + this.field_176116_c;
      ☃ = 31 * ☃ + this.field_176114_d;
      return 31 * ☃ + Objects.hashCode(this.field_204310_e);
   }

   public static enum Type {
      PLAYER(false),
      FRAME(true),
      RED_MARKER(false),
      BLUE_MARKER(false),
      TARGET_X(true),
      TARGET_POINT(true),
      PLAYER_OFF_MAP(false),
      PLAYER_OFF_LIMITS(false),
      MANSION(true, 5393476),
      MONUMENT(true, 3830373),
      BANNER_WHITE(true),
      BANNER_ORANGE(true),
      BANNER_MAGENTA(true),
      BANNER_LIGHT_BLUE(true),
      BANNER_YELLOW(true),
      BANNER_LIME(true),
      BANNER_PINK(true),
      BANNER_GRAY(true),
      BANNER_LIGHT_GRAY(true),
      BANNER_CYAN(true),
      BANNER_PURPLE(true),
      BANNER_BLUE(true),
      BANNER_BROWN(true),
      BANNER_GREEN(true),
      BANNER_RED(true),
      BANNER_BLACK(true),
      RED_X(true);

      private final byte field_191175_k = (byte)this.ordinal();
      private final boolean field_191176_l;
      private final int field_191177_m;

      private Type(boolean var3) {
         this(☃, -1);
      }

      private Type(boolean var3, int var4) {
         this.field_191176_l = ☃;
         this.field_191177_m = ☃;
      }

      public byte func_191163_a() {
         return this.field_191175_k;
      }

      public boolean func_191162_c() {
         return this.field_191177_m >= 0;
      }

      public int func_191161_d() {
         return this.field_191177_m;
      }

      public static MapDecoration.Type func_191159_a(byte var0) {
         return values()[MathHelper.func_76125_a(☃, 0, values().length - 1)];
      }
   }
}
