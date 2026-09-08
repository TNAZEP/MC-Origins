package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;

public class CompiledChunk {
   public static final CompiledChunk field_178502_a = new CompiledChunk() {
      @Override
      protected void func_178486_a(BlockRenderLayer var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void func_178493_c(BlockRenderLayer var1) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean func_178495_a(EnumFacing var1, EnumFacing var2) {
         return false;
      }
   };
   private final boolean[] field_178500_b = new boolean[BlockRenderLayer.values().length];
   private final boolean[] field_178501_c = new boolean[BlockRenderLayer.values().length];
   private boolean field_178498_d = true;
   private final List<TileEntity> field_178499_e = Lists.<TileEntity>newArrayList();
   private SetVisibility field_178496_f = new SetVisibility();
   private BufferBuilder.State field_178497_g;

   public boolean func_178489_a() {
      return this.field_178498_d;
   }

   protected void func_178486_a(BlockRenderLayer var1) {
      this.field_178498_d = false;
      this.field_178500_b[☃.ordinal()] = true;
   }

   public boolean func_178491_b(BlockRenderLayer var1) {
      return !this.field_178500_b[☃.ordinal()];
   }

   public void func_178493_c(BlockRenderLayer var1) {
      this.field_178501_c[☃.ordinal()] = true;
   }

   public boolean func_178492_d(BlockRenderLayer var1) {
      return this.field_178501_c[☃.ordinal()];
   }

   public List<TileEntity> func_178485_b() {
      return this.field_178499_e;
   }

   public void func_178490_a(TileEntity var1) {
      this.field_178499_e.add(☃);
   }

   public boolean func_178495_a(EnumFacing var1, EnumFacing var2) {
      return this.field_178496_f.func_178621_a(☃, ☃);
   }

   public void func_178488_a(SetVisibility var1) {
      this.field_178496_f = ☃;
   }

   public BufferBuilder.State func_178487_c() {
      return this.field_178497_g;
   }

   public void func_178494_a(BufferBuilder.State var1) {
      this.field_178497_g = ☃;
   }
}
