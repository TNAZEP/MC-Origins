package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class ListedRenderChunk extends RenderChunk {
   private final int field_178601_d = GLAllocation.func_74526_a(BlockRenderLayer.values().length);

   public ListedRenderChunk(World var1, WorldRenderer var2) {
      super(☃, ☃);
   }

   public int func_178600_a(BlockRenderLayer var1, CompiledChunk var2) {
      return !☃.func_178491_b(☃) ? this.field_178601_d + ☃.ordinal() : -1;
   }

   @Override
   public void func_178566_a() {
      super.func_178566_a();
      GLAllocation.func_178874_a(this.field_178601_d, BlockRenderLayer.values().length);
   }
}
