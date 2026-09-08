package net.minecraft.world.entity.ai.village.poi;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.VisibleForDebug;

public class PoiRecord {
   private final BlockPos pos;
   private final PoiType poiType;
   private int freeTickets;
   private final Runnable setDirty;

   public static Codec<PoiRecord> codec(Runnable var0) {
      return RecordCodecBuilder.create(
         var1 -> var1.group(
                  BlockPos.CODEC.fieldOf("pos").forGetter(var0x -> var0x.pos),
                  Registry.POINT_OF_INTEREST_TYPE.fieldOf("type").forGetter(var0x -> var0x.poiType),
                  Codec.INT.fieldOf("free_tickets").orElse(0).forGetter(var0x -> var0x.freeTickets),
                  RecordCodecBuilder.point(â˜ƒ)
               )
               .apply(var1, PoiRecord::new)
      );
   }

   private PoiRecord(BlockPos var1, PoiType var2, int var3, Runnable var4) {
      this.pos = â˜ƒ.immutable();
      this.poiType = â˜ƒ;
      this.freeTickets = â˜ƒ;
      this.setDirty = â˜ƒ;
   }

   public PoiRecord(BlockPos var1, PoiType var2, Runnable var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ.getMaxTickets(), â˜ƒ);
   }

   @Deprecated
   @VisibleForDebug
   public int getFreeTickets() {
      return this.freeTickets;
   }

   protected boolean acquireTicket() {
      if (this.freeTickets <= 0) {
         return false;
      } else {
         --this.freeTickets;
         this.setDirty.run();
         return true;
      }
   }

   protected boolean releaseTicket() {
      if (this.freeTickets >= this.poiType.getMaxTickets()) {
         return false;
      } else {
         ++this.freeTickets;
         this.setDirty.run();
         return true;
      }
   }

   public boolean hasSpace() {
      return this.freeTickets > 0;
   }

   public boolean isOccupied() {
      return this.freeTickets != this.poiType.getMaxTickets();
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public PoiType getPoiType() {
      return this.poiType;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ != null && this.getClass() == â˜ƒ.getClass() ? Objects.equals(this.pos, ((PoiRecord)â˜ƒ).pos) : false;
      }
   }

   public int hashCode() {
      return this.pos.hashCode();
   }
}
