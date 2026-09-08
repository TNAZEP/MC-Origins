package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Locale;
import net.minecraft.init.Particles;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;

public class RedstoneParticleData implements IParticleData {
   public static final RedstoneParticleData field_197564_a = new RedstoneParticleData(1.0F, 0.0F, 0.0F, 1.0F);
   public static final IParticleData.IDeserializer<RedstoneParticleData> field_197565_b = new IParticleData.IDeserializer<RedstoneParticleData>() {
      public RedstoneParticleData func_197544_b(ParticleType<RedstoneParticleData> var1, StringReader var2) throws CommandSyntaxException {
         ☃.expect(' ');
         float ☃ = (float)☃.readDouble();
         ☃.expect(' ');
         float ☃x = (float)☃.readDouble();
         ☃.expect(' ');
         float ☃xx = (float)☃.readDouble();
         ☃.expect(' ');
         float ☃xxx = (float)☃.readDouble();
         return new RedstoneParticleData(☃, ☃x, ☃xx, ☃xxx);
      }

      public RedstoneParticleData func_197543_b(ParticleType<RedstoneParticleData> var1, PacketBuffer var2) {
         return new RedstoneParticleData(☃.readFloat(), ☃.readFloat(), ☃.readFloat(), ☃.readFloat());
      }
   };
   private final float field_197566_c;
   private final float field_197567_d;
   private final float field_197568_e;
   private final float field_197569_f;

   public RedstoneParticleData(float var1, float var2, float var3, float var4) {
      this.field_197566_c = ☃;
      this.field_197567_d = ☃;
      this.field_197568_e = ☃;
      this.field_197569_f = MathHelper.func_76131_a(☃, 0.01F, 4.0F);
   }

   @Override
   public void func_197553_a(PacketBuffer var1) {
      ☃.writeFloat(this.field_197566_c);
      ☃.writeFloat(this.field_197567_d);
      ☃.writeFloat(this.field_197568_e);
      ☃.writeFloat(this.field_197569_f);
   }

   @Override
   public String func_197555_a() {
      return String.format(
         Locale.ROOT,
         "%s %.2f %.2f %.2f %.2f",
         this.func_197554_b().func_197570_d(),
         this.field_197566_c,
         this.field_197567_d,
         this.field_197568_e,
         this.field_197569_f
      );
   }

   @Override
   public ParticleType<RedstoneParticleData> func_197554_b() {
      return Particles.field_197619_l;
   }

   public float func_197562_c() {
      return this.field_197566_c;
   }

   public float func_197563_d() {
      return this.field_197567_d;
   }

   public float func_197561_e() {
      return this.field_197568_e;
   }

   public float func_197560_f() {
      return this.field_197569_f;
   }
}
