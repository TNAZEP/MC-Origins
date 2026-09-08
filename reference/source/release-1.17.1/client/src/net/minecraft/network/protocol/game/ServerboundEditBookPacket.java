package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundEditBookPacket implements Packet<ServerGamePacketListener> {
   public static final int MAX_BYTES_PER_CHAR = 4;
   private static final int TITLE_MAX_CHARS = 128;
   private static final int PAGE_MAX_CHARS = 8192;
   private static final int MAX_PAGES_COUNT = 200;
   private final int slot;
   private final List<String> pages;
   private final Optional<String> title;

   public ServerboundEditBookPacket(int var1, List<String> var2, Optional<String> var3) {
      this.slot = â˜ƒ;
      this.pages = ImmutableList.copyOf(â˜ƒ);
      this.title = â˜ƒ;
   }

   public ServerboundEditBookPacket(FriendlyByteBuf var1) {
      this.slot = â˜ƒ.readVarInt();
      this.pages = â˜ƒ.readCollection(FriendlyByteBuf.limitValue(Lists::newArrayListWithCapacity, 200), var0 -> var0.readUtf(8192));
      this.title = â˜ƒ.readOptional(var0 -> var0.readUtf(128));
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.slot);
      â˜ƒ.writeCollection(this.pages, (var0, var1x) -> var0.writeUtf(var1x, 8192));
      â˜ƒ.writeOptional(this.title, (var0, var1x) -> var0.writeUtf(var1x, 128));
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleEditBook(this);
   }

   public List<String> getPages() {
      return this.pages;
   }

   public Optional<String> getTitle() {
      return this.title;
   }

   public int getSlot() {
      return this.slot;
   }
}
