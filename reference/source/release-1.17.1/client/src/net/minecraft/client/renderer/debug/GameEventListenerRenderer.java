package net.minecraft.client.renderer.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

public class GameEventListenerRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft minecraft;
   private static final int LISTENER_RENDER_DIST = 32;
   private static final float BOX_HEIGHT = 1.0F;
   private final List<GameEventListenerRenderer.TrackedGameEvent> trackedGameEvents = Lists.<GameEventListenerRenderer.TrackedGameEvent>newArrayList();
   private final List<GameEventListenerRenderer.TrackedListener> trackedListeners = Lists.<GameEventListenerRenderer.TrackedListener>newArrayList();

   public GameEventListenerRenderer(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, double var3, double var5, double var7) {
      Level â˜ƒ = this.minecraft.level;
      if (â˜ƒ == null) {
         this.trackedGameEvents.clear();
         this.trackedListeners.clear();
      } else {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ, 0.0, â˜ƒ);
         this.trackedGameEvents.removeIf(GameEventListenerRenderer.TrackedGameEvent::isExpired);
         this.trackedListeners.removeIf(var2x -> var2x.isExpired(â˜ƒ, â˜ƒ));
         RenderSystem.disableTexture();
         RenderSystem.enableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         VertexConsumer â˜ƒx = â˜ƒ.getBuffer(RenderType.lines());

         for(GameEventListenerRenderer.TrackedListener â˜ƒxx : this.trackedListeners) {
            â˜ƒxx.getPosition(â˜ƒ)
               .ifPresent(
                  var9x -> {
                     int â˜ƒ = var9x.getX() - â˜ƒ.getListenerRadius();
                     int â˜ƒx = var9x.getY() - â˜ƒ.getListenerRadius();
                     int â˜ƒxx = var9x.getZ() - â˜ƒ.getListenerRadius();
                     int â˜ƒxxx = var9x.getX() + â˜ƒ.getListenerRadius();
                     int â˜ƒxxxx = var9x.getY() + â˜ƒ.getListenerRadius();
                     int â˜ƒxxxxx = var9x.getZ() + â˜ƒ.getListenerRadius();
                     Vector3f â˜ƒxxxxxx = new Vector3f(1.0F, 1.0F, 0.0F);
                     LevelRenderer.renderVoxelShape(
                        â˜ƒ,
                        â˜ƒ,
                        Shapes.create(new AABB((double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒxxxx, (double)â˜ƒxxxxx)),
                        -â˜ƒ,
                        -â˜ƒ,
                        -â˜ƒ,
                        â˜ƒxxxxxx.x(),
                        â˜ƒxxxxxx.y(),
                        â˜ƒxxxxxx.z(),
                        0.35F
                     );
                  }
               );
         }

         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         Tesselator â˜ƒxx = Tesselator.getInstance();
         BufferBuilder â˜ƒxxx = â˜ƒxx.getBuilder();
         â˜ƒxxx.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

         for(GameEventListenerRenderer.TrackedListener â˜ƒxxxx : this.trackedListeners) {
            â˜ƒxxxx.getPosition(â˜ƒ)
               .ifPresent(
                  var7x -> {
                     Vector3f â˜ƒ = new Vector3f(1.0F, 1.0F, 0.0F);
                     LevelRenderer.addChainedFilledBoxVertices(
                        â˜ƒ,
                        (double)((float)var7x.getX() - 0.25F) - â˜ƒ,
                        (double)var7x.getY() - â˜ƒ,
                        (double)((float)var7x.getZ() - 0.25F) - â˜ƒ,
                        (double)((float)var7x.getX() + 0.25F) - â˜ƒ,
                        (double)var7x.getY() - â˜ƒ + 1.0,
                        (double)((float)var7x.getZ() + 0.25F) - â˜ƒ,
                        â˜ƒ.x(),
                        â˜ƒ.y(),
                        â˜ƒ.z(),
                        0.35F
                     );
                  }
               );
         }

         â˜ƒxx.end();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(2.0F);
         RenderSystem.depthMask(false);

         for(GameEventListenerRenderer.TrackedListener â˜ƒxxxx : this.trackedListeners) {
            â˜ƒxxxx.getPosition(â˜ƒ)
               .ifPresent(
                  var0 -> {
                     DebugRenderer.renderFloatingText(
                        "Listener Origin", (double)var0.getX(), (double)((float)var0.getY() + 1.8F), (double)var0.getZ(), -1, 0.025F
                     );
                     DebugRenderer.renderFloatingText(
                        new BlockPos(var0).toString(), (double)var0.getX(), (double)((float)var0.getY() + 1.5F), (double)var0.getZ(), -6959665, 0.025F
                     );
                  }
               );
         }

         for(GameEventListenerRenderer.TrackedGameEvent â˜ƒxxxx : this.trackedGameEvents) {
            Vec3 â˜ƒxxxxx = â˜ƒxxxx.position;
            double â˜ƒxxxxxx = 0.2F;
            double â˜ƒxxxxxxx = â˜ƒxxxxx.x - 0.2F;
            double â˜ƒxxxxxxxx = â˜ƒxxxxx.y - 0.2F;
            double â˜ƒxxxxxxxxx = â˜ƒxxxxx.z - 0.2F;
            double â˜ƒxxxxxxxxxx = â˜ƒxxxxx.x + 0.2F;
            double â˜ƒxxxxxxxxxxx = â˜ƒxxxxx.y + 0.2F + 0.5;
            double â˜ƒxxxxxxxxxxxx = â˜ƒxxxxx.z + 0.2F;
            renderTransparentFilledBox(new AABB(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx), 1.0F, 1.0F, 1.0F, 0.2F);
            DebugRenderer.renderFloatingText(â˜ƒxxxx.gameEvent.getName(), â˜ƒxxxxx.x, â˜ƒxxxxx.y + 0.85F, â˜ƒxxxxx.z, -7564911, 0.0075F);
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableTexture();
         RenderSystem.disableBlend();
      }
   }

   private static void renderTransparentFilledBox(AABB var0, float var1, float var2, float var3, float var4) {
      Camera â˜ƒ = Minecraft.getInstance().gameRenderer.getMainCamera();
      if (â˜ƒ.isInitialized()) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         Vec3 â˜ƒx = â˜ƒ.getPosition().reverse();
         DebugRenderer.renderFilledBox(â˜ƒ.move(â˜ƒx), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void trackGameEvent(GameEvent var1, BlockPos var2) {
      this.trackedGameEvents.add(new GameEventListenerRenderer.TrackedGameEvent(Util.getMillis(), â˜ƒ, Vec3.atBottomCenterOf(â˜ƒ)));
   }

   public void trackListener(PositionSource var1, int var2) {
      this.trackedListeners.add(new GameEventListenerRenderer.TrackedListener(â˜ƒ, â˜ƒ));
   }

   static class TrackedGameEvent {
      public final long timeStamp;
      public final GameEvent gameEvent;
      public final Vec3 position;

      public TrackedGameEvent(long var1, GameEvent var3, Vec3 var4) {
         this.timeStamp = â˜ƒ;
         this.gameEvent = â˜ƒ;
         this.position = â˜ƒ;
      }

      public boolean isExpired() {
         return Util.getMillis() - this.timeStamp > 3000L;
      }
   }

   static class TrackedListener implements GameEventListener {
      public final PositionSource listenerSource;
      public final int listenerRange;

      public TrackedListener(PositionSource var1, int var2) {
         this.listenerSource = â˜ƒ;
         this.listenerRange = â˜ƒ;
      }

      public boolean isExpired(Level var1, BlockPos var2) {
         Optional<BlockPos> â˜ƒ = this.listenerSource.getPosition(â˜ƒ);
         return !â˜ƒ.isPresent() || ((BlockPos)â˜ƒ.get()).distSqr(â˜ƒ) <= 1024.0;
      }

      public Optional<BlockPos> getPosition(Level var1) {
         return this.listenerSource.getPosition(â˜ƒ);
      }

      @Override
      public PositionSource getListenerSource() {
         return this.listenerSource;
      }

      @Override
      public int getListenerRadius() {
         return this.listenerRange;
      }

      @Override
      public boolean handleGameEvent(Level var1, GameEvent var2, @Nullable Entity var3, BlockPos var4) {
         return false;
      }
   }
}
