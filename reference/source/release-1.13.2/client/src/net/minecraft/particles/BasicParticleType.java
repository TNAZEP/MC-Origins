package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class BasicParticleType extends ParticleType<BasicParticleType> implements IParticleData {
   private static final IParticleData.IDeserializer<BasicParticleType> field_197583_b = new IParticleData.IDeserializer<BasicParticleType>() {
      public BasicParticleType func_197544_b(ParticleType<BasicParticleType> var1, StringReader var2) throws CommandSyntaxException {
         return (BasicParticleType)☃;
      }

      public BasicParticleType func_197543_b(ParticleType<BasicParticleType> var1, PacketBuffer var2) {
         return (BasicParticleType)☃;
      }
   };

   protected BasicParticleType(ResourceLocation var1, boolean var2) {
      super(☃, ☃, field_197583_b);
   }

   @Override
   public ParticleType<BasicParticleType> func_197554_b() {
      return this;
   }

   @Override
   public void func_197553_a(PacketBuffer var1) {
   }

   @Override
   public String func_197555_a() {
      return this.func_197570_d().toString();
   }
}
