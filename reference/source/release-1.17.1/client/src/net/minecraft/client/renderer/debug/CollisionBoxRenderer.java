package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CollisionBoxRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;
   private double lastUpdateTime = Double.MIN_VALUE;
   private List<VoxelShape> shapes = Collections.emptyList();

   public CollisionBoxRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      double â˜ƒ = (double)Util.getNanos();
      if (â˜ƒ - this.lastUpdateTime > 1.0E8) {
         this.lastUpdateTime = â˜ƒ;
         Entity â˜ƒx = this.minecraft.gameRenderer.getMainCamera().getEntity();
         this.shapes = (List)â˜ƒx.level.getCollisions(â˜ƒx, â˜ƒx.getBoundingBox().inflate(6.0), var0 -> true).collect(Collectors.toList());
      }

      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(RenderType.lines());

      for(VoxelShape â˜ƒx : this.shapes) {
         LevelRenderer.renderVoxelShape(â˜ƒ, â˜ƒ, â˜ƒx, -â˜ƒ, -â˜ƒ, -â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
