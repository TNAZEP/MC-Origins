package net.minecraft.world.entity.decoration;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddPaintingPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class Painting extends HangingEntity {
   public Motive motive;

   public Painting(EntityType<? extends Painting> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Painting(Level var1, BlockPos var2, Direction var3) {
      super(EntityType.PAINTING, â˜ƒ, â˜ƒ);
      List<Motive> â˜ƒ = Lists.<Motive>newArrayList();
      int â˜ƒx = 0;

      for(Motive â˜ƒxx : Registry.MOTIVE) {
         this.motive = â˜ƒxx;
         this.setDirection(â˜ƒ);
         if (this.survives()) {
            â˜ƒ.add(â˜ƒxx);
            int â˜ƒxxx = â˜ƒxx.getWidth() * â˜ƒxx.getHeight();
            if (â˜ƒxxx > â˜ƒx) {
               â˜ƒx = â˜ƒxxx;
            }
         }
      }

      if (!â˜ƒ.isEmpty()) {
         Iterator<Motive> â˜ƒxx = â˜ƒ.iterator();

         while(â˜ƒxx.hasNext()) {
            Motive â˜ƒxxx = (Motive)â˜ƒxx.next();
            if (â˜ƒxxx.getWidth() * â˜ƒxxx.getHeight() < â˜ƒx) {
               â˜ƒxx.remove();
            }
         }

         this.motive = (Motive)â˜ƒ.get(this.random.nextInt(â˜ƒ.size()));
      }

      this.setDirection(â˜ƒ);
   }

   public Painting(Level var1, BlockPos var2, Direction var3, Motive var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ);
      this.motive = â˜ƒ;
      this.setDirection(â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putString("Motive", Registry.MOTIVE.getKey(this.motive).toString());
      â˜ƒ.putByte("Facing", (byte)this.direction.get2DDataValue());
      super.addAdditionalSaveData(â˜ƒ);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      this.motive = Registry.MOTIVE.get(ResourceLocation.tryParse(â˜ƒ.getString("Motive")));
      this.direction = Direction.from2DDataValue(â˜ƒ.getByte("Facing"));
      super.readAdditionalSaveData(â˜ƒ);
      this.setDirection(this.direction);
   }

   @Override
   public int getWidth() {
      return this.motive == null ? 1 : this.motive.getWidth();
   }

   @Override
   public int getHeight() {
      return this.motive == null ? 1 : this.motive.getHeight();
   }

   @Override
   public void dropItem(@Nullable Entity var1) {
      if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
         this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
         if (â˜ƒ instanceof Player â˜ƒ && â˜ƒ.getAbilities().instabuild) {
            return;
         }

         this.spawnAtLocation(Items.PAINTING);
      }
   }

   @Override
   public void playPlacementSound() {
      this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
   }

   @Override
   public void moveTo(double var1, double var3, double var5, float var7, float var8) {
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void lerpTo(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      BlockPos â˜ƒ = this.pos.offset(â˜ƒ - this.getX(), â˜ƒ - this.getY(), â˜ƒ - this.getZ());
      this.setPos((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddPaintingPacket(this);
   }

   @Override
   public ItemStack getPickResult() {
      return new ItemStack(Items.PAINTING);
   }
}
