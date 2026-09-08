package net.minecraft.client.renderer.debug;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;

public class GoalSelectorDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
   private static final int MAX_RENDER_DIST = 160;
   private final Minecraft minecraft;
   private final Map<Integer, List<GoalSelectorDebugRenderer.DebugGoal>> goalSelectors = Maps.newHashMap();

   @Override
   public void clear() {
      this.goalSelectors.clear();
   }

   public void addGoalSelector(int var1, List<GoalSelectorDebugRenderer.DebugGoal> var2) {
      this.goalSelectors.put(â˜ƒ, â˜ƒ);
   }

   public void removeGoalSelector(int var1) {
      this.goalSelectors.remove(â˜ƒ);
   }

   public GoalSelectorDebugRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      Camera â˜ƒ = this.minecraft.gameRenderer.getMainCamera();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableTexture();
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getPosition().x, 0.0, â˜ƒ.getPosition().z);
      this.goalSelectors.forEach((var1x, var2x) -> {
         for(int â˜ƒ = 0; â˜ƒ < var2x.size(); ++â˜ƒ) {
            GoalSelectorDebugRenderer.DebugGoal â˜ƒx = (GoalSelectorDebugRenderer.DebugGoal)var2x.get(â˜ƒ);
            if (â˜ƒ.closerThan(â˜ƒx.pos, 160.0)) {
               double â˜ƒxx = (double)â˜ƒx.pos.getX() + 0.5;
               double â˜ƒxxx = (double)â˜ƒx.pos.getY() + 2.0 + (double)â˜ƒ * 0.25;
               double â˜ƒxxxx = (double)â˜ƒx.pos.getZ() + 0.5;
               int â˜ƒxxxxx = â˜ƒx.isRunning ? -16711936 : -3355444;
               DebugRenderer.renderFloatingText(â˜ƒx.name, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
            }
         }
      });
      RenderSystem.enableDepthTest();
      RenderSystem.enableTexture();
   }

   public static class DebugGoal {
      public final BlockPos pos;
      public final int priority;
      public final String name;
      public final boolean isRunning;

      public DebugGoal(BlockPos var1, int var2, String var3, boolean var4) {
         this.pos = â˜ƒ;
         this.priority = â˜ƒ;
         this.name = â˜ƒ;
         this.isRunning = â˜ƒ;
      }
   }
}
