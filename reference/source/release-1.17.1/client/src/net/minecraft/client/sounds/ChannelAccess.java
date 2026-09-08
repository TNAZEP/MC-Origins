package net.minecraft.client.sounds;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.audio.Channel;
import com.mojang.blaze3d.audio.Library;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class ChannelAccess {
   private final Set<ChannelAccess.ChannelHandle> channels = Sets.newIdentityHashSet();
   final Library library;
   final Executor executor;

   public ChannelAccess(Library var1, Executor var2) {
      this.library = â˜ƒ;
      this.executor = â˜ƒ;
   }

   public CompletableFuture<ChannelAccess.ChannelHandle> createHandle(Library.Pool var1) {
      CompletableFuture<ChannelAccess.ChannelHandle> â˜ƒ = new CompletableFuture();
      this.executor.execute(() -> {
         Channel â˜ƒ = this.library.acquireChannel(â˜ƒ);
         if (â˜ƒ != null) {
            ChannelAccess.ChannelHandle â˜ƒx = new ChannelAccess.ChannelHandle(â˜ƒ);
            this.channels.add(â˜ƒx);
            â˜ƒ.complete(â˜ƒx);
         } else {
            â˜ƒ.complete(null);
         }
      });
      return â˜ƒ;
   }

   public void executeOnChannels(Consumer<Stream<Channel>> var1) {
      this.executor.execute(() -> â˜ƒ.accept(this.channels.stream().map(var0 -> var0.channel).filter(Objects::nonNull)));
   }

   public void scheduleTick() {
      this.executor.execute(() -> {
         Iterator<ChannelAccess.ChannelHandle> â˜ƒ = this.channels.iterator();

         while(â˜ƒ.hasNext()) {
            ChannelAccess.ChannelHandle â˜ƒx = (ChannelAccess.ChannelHandle)â˜ƒ.next();
            â˜ƒx.channel.updateStream();
            if (â˜ƒx.channel.stopped()) {
               â˜ƒx.release();
               â˜ƒ.remove();
            }
         }
      });
   }

   public void clear() {
      this.channels.forEach(ChannelAccess.ChannelHandle::release);
      this.channels.clear();
   }

   public class ChannelHandle {
      @Nullable
      Channel channel;
      private boolean stopped;

      public boolean isStopped() {
         return this.stopped;
      }

      public ChannelHandle(Channel var2) {
         this.channel = â˜ƒ;
      }

      public void execute(Consumer<Channel> var1) {
         ChannelAccess.this.executor.execute(() -> {
            if (this.channel != null) {
               â˜ƒ.accept(this.channel);
            }
         });
      }

      public void release() {
         this.stopped = true;
         ChannelAccess.this.library.releaseChannel(this.channel);
         this.channel = null;
      }
   }
}
