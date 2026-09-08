package net.minecraft.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.network.PacketBuffer;

public class BlockParticleData implements IParticleData {
   public static final IParticleData.IDeserializer<BlockParticleData> field_197585_a = new IParticleData.IDeserializer<BlockParticleData>() {
      public BlockParticleData func_197544_b(ParticleType<BlockParticleData> var1, StringReader var2) throws CommandSyntaxException {
         ☃.expect(' ');
         return new BlockParticleData(☃, new BlockStateParser(☃, false).func_197243_a(false).func_197249_b());
      }

      public BlockParticleData func_197543_b(ParticleType<BlockParticleData> var1, PacketBuffer var2) {
         return new BlockParticleData(☃, Block.field_176229_d.func_148745_a(☃.func_150792_a()));
      }
   };
   private final ParticleType<BlockParticleData> field_197586_b;
   private final IBlockState field_197587_c;

   public BlockParticleData(ParticleType<BlockParticleData> var1, IBlockState var2) {
      this.field_197586_b = ☃;
      this.field_197587_c = ☃;
   }

   @Override
   public void func_197553_a(PacketBuffer var1) {
      ☃.func_150787_b(Block.field_176229_d.func_148747_b(this.field_197587_c));
   }

   @Override
   public String func_197555_a() {
      return this.func_197554_b().func_197570_d() + " " + BlockStateParser.func_197247_a(this.field_197587_c, null);
   }

   @Override
   public ParticleType<BlockParticleData> func_197554_b() {
      return this.field_197586_b;
   }
}
