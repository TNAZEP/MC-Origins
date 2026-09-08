package net.minecraft.world;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class BossInfoServer extends BossInfo {
   private final Set<EntityPlayerMP> field_186762_h = Sets.<EntityPlayerMP>newHashSet();
   private final Set<EntityPlayerMP> field_186763_i = Collections.unmodifiableSet(this.field_186762_h);
   private boolean field_186764_j = true;

   public BossInfoServer(ITextComponent var1, BossInfo.Color var2, BossInfo.Overlay var3) {
      super(MathHelper.func_188210_a(), ☃, ☃, ☃);
   }

   @Override
   public void func_186735_a(float var1) {
      if (☃ != this.field_186750_b) {
         super.func_186735_a(☃);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PCT);
      }
   }

   @Override
   public void func_186745_a(BossInfo.Color var1) {
      if (☃ != this.field_186751_c) {
         super.func_186745_a(☃);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
      }
   }

   @Override
   public void func_186746_a(BossInfo.Overlay var1) {
      if (☃ != this.field_186752_d) {
         super.func_186746_a(☃);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
      }
   }

   @Override
   public BossInfo func_186741_a(boolean var1) {
      if (☃ != this.field_186753_e) {
         super.func_186741_a(☃);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
      }

      return this;
   }

   @Override
   public BossInfo func_186742_b(boolean var1) {
      if (☃ != this.field_186754_f) {
         super.func_186742_b(☃);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
      }

      return this;
   }

   @Override
   public BossInfo func_186743_c(boolean var1) {
      if (☃ != this.field_186755_g) {
         super.func_186743_c(☃);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
      }

      return this;
   }

   @Override
   public void func_186739_a(ITextComponent var1) {
      if (!Objects.equal(☃, this.field_186749_a)) {
         super.func_186739_a(☃);
         this.func_186759_a(SPacketUpdateBossInfo.Operation.UPDATE_NAME);
      }
   }

   private void func_186759_a(SPacketUpdateBossInfo.Operation var1) {
      if (this.field_186764_j) {
         SPacketUpdateBossInfo ☃ = new SPacketUpdateBossInfo(☃, this);

         for(EntityPlayerMP ☃x : this.field_186762_h) {
            ☃x.field_71135_a.func_147359_a(☃);
         }
      }
   }

   public void func_186760_a(EntityPlayerMP var1) {
      if (this.field_186762_h.add(☃) && this.field_186764_j) {
         ☃.field_71135_a.func_147359_a(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.ADD, this));
      }
   }

   public void func_186761_b(EntityPlayerMP var1) {
      if (this.field_186762_h.remove(☃) && this.field_186764_j) {
         ☃.field_71135_a.func_147359_a(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.REMOVE, this));
      }
   }

   public void func_201360_b() {
      if (!this.field_186762_h.isEmpty()) {
         for(EntityPlayerMP ☃ : this.field_186762_h) {
            this.func_186761_b(☃);
         }
      }
   }

   public boolean func_201359_g() {
      return this.field_186764_j;
   }

   public void func_186758_d(boolean var1) {
      if (☃ != this.field_186764_j) {
         this.field_186764_j = ☃;

         for(EntityPlayerMP ☃ : this.field_186762_h) {
            ☃.field_71135_a.func_147359_a(new SPacketUpdateBossInfo(☃ ? SPacketUpdateBossInfo.Operation.ADD : SPacketUpdateBossInfo.Operation.REMOVE, this));
         }
      }
   }

   public Collection<EntityPlayerMP> func_186757_c() {
      return this.field_186763_i;
   }
}
