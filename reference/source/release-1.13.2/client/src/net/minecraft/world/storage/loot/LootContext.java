package net.minecraft.world.storage.loot;

import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

public class LootContext {
   private final float field_186498_a;
   private final WorldServer field_186499_b;
   private final LootTableManager field_186500_c;
   @Nullable
   private final Entity field_186501_d;
   @Nullable
   private final EntityPlayer field_186502_e;
   @Nullable
   private final DamageSource field_186503_f;
   @Nullable
   private final BlockPos field_204316_g;
   private final Set<LootTable> field_186504_g = Sets.<LootTable>newLinkedHashSet();

   public LootContext(
      float var1,
      WorldServer var2,
      LootTableManager var3,
      @Nullable Entity var4,
      @Nullable EntityPlayer var5,
      @Nullable DamageSource var6,
      @Nullable BlockPos var7
   ) {
      this.field_186498_a = ☃;
      this.field_186499_b = ☃;
      this.field_186500_c = ☃;
      this.field_186501_d = ☃;
      this.field_186502_e = ☃;
      this.field_186503_f = ☃;
      this.field_204316_g = ☃;
   }

   @Nullable
   public Entity func_186493_a() {
      return this.field_186501_d;
   }

   @Nullable
   public Entity func_186495_b() {
      return this.field_186502_e;
   }

   @Nullable
   public Entity func_186492_c() {
      return this.field_186503_f == null ? null : this.field_186503_f.func_76346_g();
   }

   @Nullable
   public BlockPos func_204315_e() {
      return this.field_204316_g;
   }

   public boolean func_186496_a(LootTable var1) {
      return this.field_186504_g.add(☃);
   }

   public void func_186490_b(LootTable var1) {
      this.field_186504_g.remove(☃);
   }

   public LootTableManager func_186497_e() {
      return this.field_186500_c;
   }

   public float func_186491_f() {
      return this.field_186498_a;
   }

   public WorldServer func_202879_g() {
      return this.field_186499_b;
   }

   @Nullable
   public Entity func_186494_a(LootContext.EntityTarget var1) {
      switch(☃) {
         case THIS:
            return this.func_186493_a();
         case KILLER:
            return this.func_186492_c();
         case KILLER_PLAYER:
            return this.func_186495_b();
         default:
            return null;
      }
   }

   public static class Builder {
      private final WorldServer field_186474_a;
      private float field_186475_b;
      private Entity field_186476_c;
      private EntityPlayer field_186477_d;
      private DamageSource field_186478_e;
      private BlockPos field_204314_f;

      public Builder(WorldServer var1) {
         this.field_186474_a = ☃;
      }

      public LootContext.Builder func_186469_a(float var1) {
         this.field_186475_b = ☃;
         return this;
      }

      public LootContext.Builder func_186472_a(Entity var1) {
         this.field_186476_c = ☃;
         return this;
      }

      public LootContext.Builder func_186470_a(EntityPlayer var1) {
         this.field_186477_d = ☃;
         return this;
      }

      public LootContext.Builder func_186473_a(DamageSource var1) {
         this.field_186478_e = ☃;
         return this;
      }

      public LootContext.Builder func_204313_a(BlockPos var1) {
         this.field_204314_f = ☃;
         return this;
      }

      public LootContext func_186471_a() {
         return new LootContext(
            this.field_186475_b,
            this.field_186474_a,
            this.field_186474_a.func_73046_m().func_200249_aQ(),
            this.field_186476_c,
            this.field_186477_d,
            this.field_186478_e,
            this.field_204314_f
         );
      }
   }

   public static enum EntityTarget {
      THIS("this"),
      KILLER("killer"),
      KILLER_PLAYER("killer_player");

      private final String field_186488_d;

      private EntityTarget(String var3) {
         this.field_186488_d = ☃;
      }

      public static LootContext.EntityTarget func_186482_a(String var0) {
         for(LootContext.EntityTarget ☃ : values()) {
            if (☃.field_186488_d.equals(☃)) {
               return ☃;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + ☃);
      }

      public static class Serializer extends TypeAdapter<LootContext.EntityTarget> {
         public void write(JsonWriter var1, LootContext.EntityTarget var2) throws IOException {
            ☃.value(☃.field_186488_d);
         }

         public LootContext.EntityTarget read(JsonReader var1) throws IOException {
            return LootContext.EntityTarget.func_186482_a(☃.nextString());
         }
      }
   }
}
