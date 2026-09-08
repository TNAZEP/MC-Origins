package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ItemParticleData implements IParticleData {
   public static final IParticleData.IDeserializer<ItemParticleData> field_197557_a = new IParticleData.IDeserializer<ItemParticleData>() {
      public ItemParticleData func_197544_b(ParticleType<ItemParticleData> var1, StringReader var2) throws CommandSyntaxException {
         ☃.expect(' ');
         ItemParser ☃ = new ItemParser(☃, false).func_197327_f();
         ItemStack ☃x = new ItemInput(☃.func_197326_b(), ☃.func_197325_c()).func_197320_a(1, false);
         return new ItemParticleData(☃, ☃x);
      }

      public ItemParticleData func_197543_b(ParticleType<ItemParticleData> var1, PacketBuffer var2) {
         return new ItemParticleData(☃, ☃.func_150791_c());
      }
   };
   private final ParticleType<ItemParticleData> field_197558_b;
   private final ItemStack field_197559_c;

   public ItemParticleData(ParticleType<ItemParticleData> var1, ItemStack var2) {
      this.field_197558_b = ☃;
      this.field_197559_c = ☃;
   }

   @Override
   public void func_197553_a(PacketBuffer var1) {
      ☃.func_150788_a(this.field_197559_c);
   }

   @Override
   public String func_197555_a() {
      return this.func_197554_b().func_197570_d() + " " + new ItemInput(this.field_197559_c.func_77973_b(), this.field_197559_c.func_77978_p()).func_197321_c();
   }

   @Override
   public ParticleType<ItemParticleData> func_197554_b() {
      return this.field_197558_b;
   }

   public ItemStack func_197556_c() {
      return this.field_197559_c;
   }
}
