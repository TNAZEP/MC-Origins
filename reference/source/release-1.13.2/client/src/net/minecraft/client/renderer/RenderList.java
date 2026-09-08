package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;

public class RenderList extends ChunkRenderContainer {
   @Override
   public void func_178001_a(BlockRenderLayer var1) {
      if (this.field_178007_b) {
         for(RenderChunk ☃ : this.field_178009_a) {
            ListedRenderChunk ☃x = (ListedRenderChunk)☃;
            GlStateManager.func_179094_E();
            this.func_178003_a(☃);
            GlStateManager.func_179148_o(☃x.func_178600_a(☃, ☃x.func_178571_g()));
            GlStateManager.func_179121_F();
         }

         GlStateManager.func_179117_G();
         this.field_178009_a.clear();
      }
   }
}
