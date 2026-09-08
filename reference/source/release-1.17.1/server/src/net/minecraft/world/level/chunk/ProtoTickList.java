package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.TickList;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;

public class ProtoTickList<T> implements TickList<T> {
   protected final Predicate<T> ignore;
   private final ChunkPos chunkPos;
   private final ShortList[] toBeTicked;
   private LevelHeightAccessor levelHeightAccessor;

   public ProtoTickList(Predicate<T> var1, ChunkPos var2, LevelHeightAccessor var3) {
      this(â˜ƒ, â˜ƒ, new ListTag(), â˜ƒ);
   }

   public ProtoTickList(Predicate<T> var1, ChunkPos var2, ListTag var3, LevelHeightAccessor var4) {
      this.ignore = â˜ƒ;
      this.chunkPos = â˜ƒ;
      this.levelHeightAccessor = â˜ƒ;
      this.toBeTicked = new ShortList[â˜ƒ.getSectionsCount()];

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         ListTag â˜ƒx = â˜ƒ.getList(â˜ƒ);

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.size(); ++â˜ƒxx) {
            ChunkAccess.getOrCreateOffsetList(this.toBeTicked, â˜ƒ).add(â˜ƒx.getShort(â˜ƒxx));
         }
      }
   }

   public ListTag save() {
      return ChunkSerializer.packOffsets(this.toBeTicked);
   }

   public void copyOut(TickList<T> var1, Function<BlockPos, T> var2) {
      for(int â˜ƒ = 0; â˜ƒ < this.toBeTicked.length; ++â˜ƒ) {
         if (this.toBeTicked[â˜ƒ] != null) {
            for(Short â˜ƒx : this.toBeTicked[â˜ƒ]) {
               BlockPos â˜ƒxx = ProtoChunk.unpackOffsetCoordinates(â˜ƒx, this.levelHeightAccessor.getSectionYFromSectionIndex(â˜ƒ), this.chunkPos);
               â˜ƒ.scheduleTick(â˜ƒxx, (T)â˜ƒ.apply(â˜ƒxx), 0);
            }

            this.toBeTicked[â˜ƒ].clear();
         }
      }
   }

   @Override
   public boolean hasScheduledTick(BlockPos var1, T var2) {
      return false;
   }

   @Override
   public void scheduleTick(BlockPos var1, T var2, int var3, TickPriority var4) {
      int â˜ƒ = this.levelHeightAccessor.getSectionIndex(â˜ƒ.getY());
      if (â˜ƒ >= 0 && â˜ƒ < this.levelHeightAccessor.getSectionsCount()) {
         ChunkAccess.getOrCreateOffsetList(this.toBeTicked, â˜ƒ).add(ProtoChunk.packOffsetCoordinates(â˜ƒ));
      }
   }

   @Override
   public boolean willTickThisTick(BlockPos var1, T var2) {
      return false;
   }

   @Override
   public int size() {
      return Stream.of(this.toBeTicked).filter(Objects::nonNull).mapToInt(List::size).sum();
   }
}
