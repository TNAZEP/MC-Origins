package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class ServerTickList<T> implements TickList<T> {
   public static final int MAX_TICK_BLOCKS_PER_TICK = 65536;
   protected final Predicate<T> ignore;
   private final Function<T, ResourceLocation> toId;
   private final Set<TickNextTickData<T>> tickNextTickSet = Sets.<TickNextTickData<T>>newHashSet();
   private final Set<TickNextTickData<T>> tickNextTickList = Sets.<TickNextTickData<T>>newTreeSet(TickNextTickData.createTimeComparator());
   private final ServerLevel level;
   private final Queue<TickNextTickData<T>> currentlyTicking = Queues.<TickNextTickData<T>>newArrayDeque();
   private final List<TickNextTickData<T>> alreadyTicked = Lists.<TickNextTickData<T>>newArrayList();
   private final Consumer<TickNextTickData<T>> ticker;

   public ServerTickList(ServerLevel var1, Predicate<T> var2, Function<T, ResourceLocation> var3, Consumer<TickNextTickData<T>> var4) {
      this.ignore = â˜ƒ;
      this.toId = â˜ƒ;
      this.level = â˜ƒ;
      this.ticker = â˜ƒ;
   }

   public void tick() {
      int â˜ƒ = this.tickNextTickList.size();
      if (â˜ƒ != this.tickNextTickSet.size()) {
         throw new IllegalStateException("TickNextTick list out of synch");
      } else {
         if (â˜ƒ > 65536) {
            â˜ƒ = 65536;
         }

         Iterator<TickNextTickData<T>> â˜ƒ = this.tickNextTickList.iterator();
         this.level.getProfiler().push("cleaning");

         while(â˜ƒ > 0 && â˜ƒ.hasNext()) {
            TickNextTickData<T> â˜ƒx = (TickNextTickData)â˜ƒ.next();
            if (â˜ƒx.triggerTick > this.level.getGameTime()) {
               break;
            }

            if (this.level.isPositionTickingWithEntitiesLoaded(â˜ƒx.pos)) {
               â˜ƒ.remove();
               this.tickNextTickSet.remove(â˜ƒx);
               this.currentlyTicking.add(â˜ƒx);
               --â˜ƒ;
            }
         }

         this.level.getProfiler().popPush("ticking");

         TickNextTickData<T> â˜ƒ;
         while((â˜ƒ = (TickNextTickData)this.currentlyTicking.poll()) != null) {
            if (this.level.isPositionTickingWithEntitiesLoaded(â˜ƒ.pos)) {
               try {
                  this.alreadyTicked.add(â˜ƒ);
                  this.ticker.accept(â˜ƒ);
               } catch (Throwable var7) {
                  CrashReport â˜ƒx = CrashReport.forThrowable(var7, "Exception while ticking");
                  CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Block being ticked");
                  CrashReportCategory.populateBlockDetails(â˜ƒxx, this.level, â˜ƒ.pos, null);
                  throw new ReportedException(â˜ƒx);
               }
            } else {
               this.scheduleTick(â˜ƒ.pos, â˜ƒ.getType(), 0);
            }
         }

         this.level.getProfiler().pop();
         this.alreadyTicked.clear();
         this.currentlyTicking.clear();
      }
   }

   @Override
   public boolean willTickThisTick(BlockPos var1, T var2) {
      return this.currentlyTicking.contains(new TickNextTickData(â˜ƒ, â˜ƒ));
   }

   public List<TickNextTickData<T>> fetchTicksInChunk(ChunkPos var1, boolean var2, boolean var3) {
      int â˜ƒ = â˜ƒ.getMinBlockX() - 2;
      int â˜ƒx = â˜ƒ + 16 + 2;
      int â˜ƒxx = â˜ƒ.getMinBlockZ() - 2;
      int â˜ƒxxx = â˜ƒxx + 16 + 2;
      return this.fetchTicksInArea(new BoundingBox(â˜ƒ, this.level.getMinBuildHeight(), â˜ƒxx, â˜ƒx, this.level.getMaxBuildHeight(), â˜ƒxxx), â˜ƒ, â˜ƒ);
   }

   public List<TickNextTickData<T>> fetchTicksInArea(BoundingBox var1, boolean var2, boolean var3) {
      List<TickNextTickData<T>> â˜ƒ = this.fetchTicksInArea(null, this.tickNextTickList, â˜ƒ, â˜ƒ);
      if (â˜ƒ && â˜ƒ != null) {
         this.tickNextTickSet.removeAll(â˜ƒ);
      }

      â˜ƒ = this.fetchTicksInArea(â˜ƒ, this.currentlyTicking, â˜ƒ, â˜ƒ);
      if (!â˜ƒ) {
         â˜ƒ = this.fetchTicksInArea(â˜ƒ, this.alreadyTicked, â˜ƒ, â˜ƒ);
      }

      return â˜ƒ == null ? Collections.emptyList() : â˜ƒ;
   }

   @Nullable
   private List<TickNextTickData<T>> fetchTicksInArea(
      @Nullable List<TickNextTickData<T>> var1, Collection<TickNextTickData<T>> var2, BoundingBox var3, boolean var4
   ) {
      Iterator<TickNextTickData<T>> â˜ƒ = â˜ƒ.iterator();

      while(â˜ƒ.hasNext()) {
         TickNextTickData<T> â˜ƒx = (TickNextTickData)â˜ƒ.next();
         BlockPos â˜ƒxx = â˜ƒx.pos;
         if (â˜ƒxx.getX() >= â˜ƒ.minX() && â˜ƒxx.getX() < â˜ƒ.maxX() && â˜ƒxx.getZ() >= â˜ƒ.minZ() && â˜ƒxx.getZ() < â˜ƒ.maxZ()) {
            if (â˜ƒ) {
               â˜ƒ.remove();
            }

            if (â˜ƒ == null) {
               â˜ƒ = Lists.<TickNextTickData<T>>newArrayList();
            }

            â˜ƒ.add(â˜ƒx);
         }
      }

      return â˜ƒ;
   }

   public void copy(BoundingBox var1, BlockPos var2) {
      for(TickNextTickData<T> â˜ƒ : this.fetchTicksInArea(â˜ƒ, false, false)) {
         if (â˜ƒ.isInside(â˜ƒ.pos)) {
            BlockPos â˜ƒx = â˜ƒ.pos.offset(â˜ƒ);
            T â˜ƒxx = â˜ƒ.getType();
            this.addTickData(new TickNextTickData<>(â˜ƒx, â˜ƒxx, â˜ƒ.triggerTick, â˜ƒ.priority));
         }
      }
   }

   public ListTag save(ChunkPos var1) {
      List<TickNextTickData<T>> â˜ƒ = this.fetchTicksInChunk(â˜ƒ, false, true);
      return saveTickList(this.toId, â˜ƒ, this.level.getGameTime());
   }

   private static <T> ListTag saveTickList(Function<T, ResourceLocation> var0, Iterable<TickNextTickData<T>> var1, long var2) {
      ListTag â˜ƒ = new ListTag();

      for(TickNextTickData<T> â˜ƒx : â˜ƒ) {
         CompoundTag â˜ƒxx = new CompoundTag();
         â˜ƒxx.putString("i", ((ResourceLocation)â˜ƒ.apply(â˜ƒx.getType())).toString());
         â˜ƒxx.putInt("x", â˜ƒx.pos.getX());
         â˜ƒxx.putInt("y", â˜ƒx.pos.getY());
         â˜ƒxx.putInt("z", â˜ƒx.pos.getZ());
         â˜ƒxx.putInt("t", (int)(â˜ƒx.triggerTick - â˜ƒ));
         â˜ƒxx.putInt("p", â˜ƒx.priority.getValue());
         â˜ƒ.add(â˜ƒxx);
      }

      return â˜ƒ;
   }

   @Override
   public boolean hasScheduledTick(BlockPos var1, T var2) {
      return this.tickNextTickSet.contains(new TickNextTickData(â˜ƒ, â˜ƒ));
   }

   @Override
   public void scheduleTick(BlockPos var1, T var2, int var3, TickPriority var4) {
      if (!this.ignore.test(â˜ƒ)) {
         this.addTickData(new TickNextTickData<>(â˜ƒ, â˜ƒ, (long)â˜ƒ + this.level.getGameTime(), â˜ƒ));
      }
   }

   private void addTickData(TickNextTickData<T> var1) {
      if (!this.tickNextTickSet.contains(â˜ƒ)) {
         this.tickNextTickSet.add(â˜ƒ);
         this.tickNextTickList.add(â˜ƒ);
      }
   }

   @Override
   public int size() {
      return this.tickNextTickSet.size();
   }
}
