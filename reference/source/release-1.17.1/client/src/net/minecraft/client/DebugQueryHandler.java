package net.minecraft.client;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;

public class DebugQueryHandler {
   private final ClientPacketListener connection;
   private int transactionId = -1;
   @Nullable
   private Consumer<CompoundTag> callback;

   public DebugQueryHandler(ClientPacketListener var1) {
      this.connection = â˜ƒ;
   }

   public boolean handleResponse(int var1, @Nullable CompoundTag var2) {
      if (this.transactionId == â˜ƒ && this.callback != null) {
         this.callback.accept(â˜ƒ);
         this.callback = null;
         return true;
      } else {
         return false;
      }
   }

   private int startTransaction(Consumer<CompoundTag> var1) {
      this.callback = â˜ƒ;
      return ++this.transactionId;
   }

   public void queryEntityTag(int var1, Consumer<CompoundTag> var2) {
      int â˜ƒ = this.startTransaction(â˜ƒ);
      this.connection.send(new ServerboundEntityTagQuery(â˜ƒ, â˜ƒ));
   }

   public void queryBlockEntityTag(BlockPos var1, Consumer<CompoundTag> var2) {
      int â˜ƒ = this.startTransaction(â˜ƒ);
      this.connection.send(new ServerboundBlockEntityTagQuery(â˜ƒ, â˜ƒ));
   }
}
