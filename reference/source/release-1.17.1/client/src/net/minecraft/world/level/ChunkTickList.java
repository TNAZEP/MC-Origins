package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;

public class ChunkTickList<T> implements TickList<T> {
   private final List<ChunkTickList.ScheduledTick<T>> ticks;
   private final Function<T, ResourceLocation> toId;

   public ChunkTickList(Function<T, ResourceLocation> var1, List<TickNextTickData<T>> var2, long var3) {
      this(
         â˜ƒ,
         (List<ChunkTickList.ScheduledTick<T>>)â˜ƒ.stream()
            .map(var2x -> new ChunkTickList.ScheduledTick<>(var2x.getType(), var2x.pos, (int)(var2x.triggerTick - â˜ƒ), var2x.priority))
            .collect(Collectors.toList())
      );
   }

   private ChunkTickList(Function<T, ResourceLocation> var1, List<ChunkTickList.ScheduledTick<T>> var2) {
      this.ticks = â˜ƒ;
      this.toId = â˜ƒ;
   }

   @Override
   public boolean hasScheduledTick(BlockPos var1, T var2) {
      return false;
   }

   @Override
   public void scheduleTick(BlockPos var1, T var2, int var3, TickPriority var4) {
      this.ticks.add(new ChunkTickList.ScheduledTick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   @Override
   public boolean willTickThisTick(BlockPos var1, T var2) {
      return false;
   }

   public ListTag save() {
      ListTag â˜ƒ = new ListTag();

      for(ChunkTickList.ScheduledTick<T> â˜ƒx : this.ticks) {
         CompoundTag â˜ƒxx = new CompoundTag();
         â˜ƒxx.putString("i", ((ResourceLocation)this.toId.apply(â˜ƒx.type)).toString());
         â˜ƒxx.putInt("x", â˜ƒx.pos.getX());
         â˜ƒxx.putInt("y", â˜ƒx.pos.getY());
         â˜ƒxx.putInt("z", â˜ƒx.pos.getZ());
         â˜ƒxx.putInt("t", â˜ƒx.delay);
         â˜ƒxx.putInt("p", â˜ƒx.priority.getValue());
         â˜ƒ.add(â˜ƒxx);
      }

      return â˜ƒ;
   }

   public static <T> ChunkTickList<T> create(ListTag var0, Function<T, ResourceLocation> var1, Function<ResourceLocation, T> var2) {
      List<ChunkTickList.ScheduledTick<T>> â˜ƒ = Lists.<ChunkTickList.ScheduledTick<T>>newArrayList();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
         T â˜ƒxxx = (T)â˜ƒ.apply(new ResourceLocation(â˜ƒxx.getString("i")));
         if (â˜ƒxxx != null) {
            BlockPos â˜ƒxxxx = new BlockPos(â˜ƒxx.getInt("x"), â˜ƒxx.getInt("y"), â˜ƒxx.getInt("z"));
            â˜ƒ.add(new ChunkTickList.ScheduledTick(â˜ƒxxx, â˜ƒxxxx, â˜ƒxx.getInt("t"), TickPriority.byValue(â˜ƒxx.getInt("p"))));
         }
      }

      return new ChunkTickList<>(â˜ƒ, â˜ƒ);
   }

   public void copyOut(TickList<T> var1) {
      this.ticks.forEach(var1x -> â˜ƒ.scheduleTick(var1x.pos, var1x.type, var1x.delay, var1x.priority));
   }

   @Override
   public int size() {
      return this.ticks.size();
   }

   static class ScheduledTick<T> {
      final T type;
      public final BlockPos pos;
      public final int delay;
      public final TickPriority priority;

      ScheduledTick(T var1, BlockPos var2, int var3, TickPriority var4) {
         this.type = â˜ƒ;
         this.pos = â˜ƒ;
         this.delay = â˜ƒ;
         this.priority = â˜ƒ;
      }

      public String toString() {
         return this.type + ": " + this.pos + ", " + this.delay + ", " + this.priority;
      }
   }
}
