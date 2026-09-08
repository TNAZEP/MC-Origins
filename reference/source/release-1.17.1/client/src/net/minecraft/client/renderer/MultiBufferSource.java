package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface MultiBufferSource {
   static MultiBufferSource.BufferSource immediate(BufferBuilder var0) {
      return immediateWithBuffers(ImmutableMap.of(), â˜ƒ);
   }

   static MultiBufferSource.BufferSource immediateWithBuffers(Map<RenderType, BufferBuilder> var0, BufferBuilder var1) {
      return new MultiBufferSource.BufferSource(â˜ƒ, â˜ƒ);
   }

   VertexConsumer getBuffer(RenderType var1);

   public static class BufferSource implements MultiBufferSource {
      protected final BufferBuilder builder;
      protected final Map<RenderType, BufferBuilder> fixedBuffers;
      protected Optional<RenderType> lastState = Optional.empty();
      protected final Set<BufferBuilder> startedBuffers = Sets.<BufferBuilder>newHashSet();

      protected BufferSource(BufferBuilder var1, Map<RenderType, BufferBuilder> var2) {
         this.builder = â˜ƒ;
         this.fixedBuffers = â˜ƒ;
      }

      @Override
      public VertexConsumer getBuffer(RenderType var1) {
         Optional<RenderType> â˜ƒ = â˜ƒ.asOptional();
         BufferBuilder â˜ƒx = this.getBuilderRaw(â˜ƒ);
         if (!Objects.equals(this.lastState, â˜ƒ)) {
            if (this.lastState.isPresent()) {
               RenderType â˜ƒxx = (RenderType)this.lastState.get();
               if (!this.fixedBuffers.containsKey(â˜ƒxx)) {
                  this.endBatch(â˜ƒxx);
               }
            }

            if (this.startedBuffers.add(â˜ƒx)) {
               â˜ƒx.begin(â˜ƒ.mode(), â˜ƒ.format());
            }

            this.lastState = â˜ƒ;
         }

         return â˜ƒx;
      }

      private BufferBuilder getBuilderRaw(RenderType var1) {
         return (BufferBuilder)this.fixedBuffers.getOrDefault(â˜ƒ, this.builder);
      }

      public void endLastBatch() {
         if (this.lastState.isPresent()) {
            RenderType â˜ƒ = (RenderType)this.lastState.get();
            if (!this.fixedBuffers.containsKey(â˜ƒ)) {
               this.endBatch(â˜ƒ);
            }

            this.lastState = Optional.empty();
         }
      }

      public void endBatch() {
         this.lastState.ifPresent(var1 -> {
            VertexConsumer â˜ƒ = this.getBuffer(var1);
            if (â˜ƒ == this.builder) {
               this.endBatch(var1);
            }
         });

         for(RenderType â˜ƒ : this.fixedBuffers.keySet()) {
            this.endBatch(â˜ƒ);
         }
      }

      public void endBatch(RenderType var1) {
         BufferBuilder â˜ƒ = this.getBuilderRaw(â˜ƒ);
         boolean â˜ƒx = Objects.equals(this.lastState, â˜ƒ.asOptional());
         if (â˜ƒx || â˜ƒ != this.builder) {
            if (this.startedBuffers.remove(â˜ƒ)) {
               â˜ƒ.end(â˜ƒ, 0, 0, 0);
               if (â˜ƒx) {
                  this.lastState = Optional.empty();
               }
            }
         }
      }
   }
}
