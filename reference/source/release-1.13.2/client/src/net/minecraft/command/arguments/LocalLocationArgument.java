package net.minecraft.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class LocalLocationArgument implements ILocationArgument {
   private final double field_200144_a;
   private final double field_200145_b;
   private final double field_200146_c;

   public LocalLocationArgument(double var1, double var3, double var5) {
      this.field_200144_a = ☃;
      this.field_200145_b = ☃;
      this.field_200146_c = ☃;
   }

   @Override
   public Vec3d func_197281_a(CommandSource var1) {
      Vec2f ☃ = ☃.func_201004_i();
      Vec3d ☃x = ☃.func_201008_k().func_201015_a(☃);
      float ☃xx = MathHelper.func_76134_b((☃.field_189983_j + 90.0F) * (float) (Math.PI / 180.0));
      float ☃xxx = MathHelper.func_76126_a((☃.field_189983_j + 90.0F) * (float) (Math.PI / 180.0));
      float ☃xxxx = MathHelper.func_76134_b(-☃.field_189982_i * (float) (Math.PI / 180.0));
      float ☃xxxxx = MathHelper.func_76126_a(-☃.field_189982_i * (float) (Math.PI / 180.0));
      float ☃xxxxxx = MathHelper.func_76134_b((-☃.field_189982_i + 90.0F) * (float) (Math.PI / 180.0));
      float ☃xxxxxxx = MathHelper.func_76126_a((-☃.field_189982_i + 90.0F) * (float) (Math.PI / 180.0));
      Vec3d ☃xxxxxxxx = new Vec3d((double)(☃xx * ☃xxxx), (double)☃xxxxx, (double)(☃xxx * ☃xxxx));
      Vec3d ☃xxxxxxxxx = new Vec3d((double)(☃xx * ☃xxxxxx), (double)☃xxxxxxx, (double)(☃xxx * ☃xxxxxx));
      Vec3d ☃xxxxxxxxxx = ☃xxxxxxxx.func_72431_c(☃xxxxxxxxx).func_186678_a(-1.0);
      double ☃xxxxxxxxxxx = ☃xxxxxxxx.field_72450_a * this.field_200146_c
         + ☃xxxxxxxxx.field_72450_a * this.field_200145_b
         + ☃xxxxxxxxxx.field_72450_a * this.field_200144_a;
      double ☃xxxxxxxxxxxx = ☃xxxxxxxx.field_72448_b * this.field_200146_c
         + ☃xxxxxxxxx.field_72448_b * this.field_200145_b
         + ☃xxxxxxxxxx.field_72448_b * this.field_200144_a;
      double ☃xxxxxxxxxxxxx = ☃xxxxxxxx.field_72449_c * this.field_200146_c
         + ☃xxxxxxxxx.field_72449_c * this.field_200145_b
         + ☃xxxxxxxxxx.field_72449_c * this.field_200144_a;
      return new Vec3d(☃x.field_72450_a + ☃xxxxxxxxxxx, ☃x.field_72448_b + ☃xxxxxxxxxxxx, ☃x.field_72449_c + ☃xxxxxxxxxxxxx);
   }

   @Override
   public Vec2f func_197282_b(CommandSource var1) {
      return Vec2f.field_189974_a;
   }

   @Override
   public boolean func_200380_a() {
      return true;
   }

   @Override
   public boolean func_200381_b() {
      return true;
   }

   @Override
   public boolean func_200382_c() {
      return true;
   }

   public static LocalLocationArgument func_200142_a(StringReader var0) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();
      double ☃x = func_200143_a(☃, ☃);
      if (☃.canRead() && ☃.peek() == ' ') {
         ☃.skip();
         double ☃xx = func_200143_a(☃, ☃);
         if (☃.canRead() && ☃.peek() == ' ') {
            ☃.skip();
            double ☃xxx = func_200143_a(☃, ☃);
            return new LocalLocationArgument(☃x, ☃xx, ☃xxx);
         } else {
            ☃.setCursor(☃);
            throw Vec3Argument.field_197304_a.createWithContext(☃);
         }
      } else {
         ☃.setCursor(☃);
         throw Vec3Argument.field_197304_a.createWithContext(☃);
      }
   }

   private static double func_200143_a(StringReader var0, int var1) throws CommandSyntaxException {
      if (!☃.canRead()) {
         throw LocationPart.field_197311_b.createWithContext(☃);
      } else if (☃.peek() != '^') {
         ☃.setCursor(☃);
         throw Vec3Argument.field_200149_b.createWithContext(☃);
      } else {
         ☃.skip();
         return ☃.canRead() && ☃.peek() != ' ' ? ☃.readDouble() : 0.0;
      }
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof LocalLocationArgument)) {
         return false;
      } else {
         LocalLocationArgument ☃ = (LocalLocationArgument)☃;
         return this.field_200144_a == ☃.field_200144_a && this.field_200145_b == ☃.field_200145_b && this.field_200146_c == ☃.field_200146_c;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.field_200144_a, this.field_200145_b, this.field_200146_c});
   }
}
