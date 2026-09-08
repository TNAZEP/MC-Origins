package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.io.IOException;
import java.util.List;
import java.util.function.IntSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;

public class PostPass implements AutoCloseable {
   private final EffectInstance effect;
   public final RenderTarget inTarget;
   public final RenderTarget outTarget;
   private final List<IntSupplier> auxAssets = Lists.newArrayList();
   private final List<String> auxNames = Lists.newArrayList();
   private final List<Integer> auxWidths = Lists.newArrayList();
   private final List<Integer> auxHeights = Lists.newArrayList();
   private Matrix4f shaderOrthoMatrix;

   public PostPass(ResourceManager var1, String var2, RenderTarget var3, RenderTarget var4) throws IOException {
      this.effect = new EffectInstance(â˜ƒ, â˜ƒ);
      this.inTarget = â˜ƒ;
      this.outTarget = â˜ƒ;
   }

   public void close() {
      this.effect.close();
   }

   public final String getName() {
      return this.effect.getName();
   }

   public void addAuxAsset(String var1, IntSupplier var2, int var3, int var4) {
      this.auxNames.add(this.auxNames.size(), â˜ƒ);
      this.auxAssets.add(this.auxAssets.size(), â˜ƒ);
      this.auxWidths.add(this.auxWidths.size(), â˜ƒ);
      this.auxHeights.add(this.auxHeights.size(), â˜ƒ);
   }

   public void setOrthoMatrix(Matrix4f var1) {
      this.shaderOrthoMatrix = â˜ƒ;
   }

   public void process(float var1) {
      this.inTarget.unbindWrite();
      float â˜ƒ = (float)this.outTarget.width;
      float â˜ƒx = (float)this.outTarget.height;
      RenderSystem.viewport(0, 0, (int)â˜ƒ, (int)â˜ƒx);
      this.effect.setSampler("DiffuseSampler", this.inTarget::getColorTextureId);

      for(int â˜ƒxx = 0; â˜ƒxx < this.auxAssets.size(); ++â˜ƒxx) {
         this.effect.setSampler((String)this.auxNames.get(â˜ƒxx), (IntSupplier)this.auxAssets.get(â˜ƒxx));
         this.effect
            .safeGetUniform("AuxSize" + â˜ƒxx)
            .set((float)((Integer)this.auxWidths.get(â˜ƒxx)).intValue(), (float)((Integer)this.auxHeights.get(â˜ƒxx)).intValue());
      }

      this.effect.safeGetUniform("ProjMat").set(this.shaderOrthoMatrix);
      this.effect.safeGetUniform("InSize").set((float)this.inTarget.width, (float)this.inTarget.height);
      this.effect.safeGetUniform("OutSize").set(â˜ƒ, â˜ƒx);
      this.effect.safeGetUniform("Time").set(â˜ƒ);
      Minecraft â˜ƒxx = Minecraft.getInstance();
      this.effect.safeGetUniform("ScreenSize").set((float)â˜ƒxx.getWindow().getWidth(), (float)â˜ƒxx.getWindow().getHeight());
      this.effect.apply();
      this.outTarget.clear(Minecraft.ON_OSX);
      this.outTarget.bindWrite(false);
      RenderSystem.depthFunc(519);
      BufferBuilder â˜ƒxxx = Tesselator.getInstance().getBuilder();
      â˜ƒxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
      â˜ƒxxx.vertex(0.0, 0.0, 500.0).endVertex();
      â˜ƒxxx.vertex((double)â˜ƒ, 0.0, 500.0).endVertex();
      â˜ƒxxx.vertex((double)â˜ƒ, (double)â˜ƒx, 500.0).endVertex();
      â˜ƒxxx.vertex(0.0, (double)â˜ƒx, 500.0).endVertex();
      â˜ƒxxx.end();
      BufferUploader._endInternal(â˜ƒxxx);
      RenderSystem.depthFunc(515);
      this.effect.clear();
      this.outTarget.unbindWrite();
      this.inTarget.unbindRead();

      for(Object â˜ƒxxxx : this.auxAssets) {
         if (â˜ƒxxxx instanceof RenderTarget) {
            ((RenderTarget)â˜ƒxxxx).unbindRead();
         }
      }
   }

   public EffectInstance getEffect() {
      return this.effect;
   }
}
