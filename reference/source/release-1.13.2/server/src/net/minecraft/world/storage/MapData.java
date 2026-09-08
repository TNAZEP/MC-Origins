package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;

public class MapData extends WorldSavedData {
   public int field_76201_a;
   public int field_76199_b;
   public DimensionType field_76200_c;
   public boolean field_186210_e;
   public boolean field_191096_f;
   public byte field_76197_d;
   public byte[] field_76198_e = new byte[16384];
   public List<MapData.MapInfo> field_76196_g = Lists.<MapData.MapInfo>newArrayList();
   private final Map<EntityPlayer, MapData.MapInfo> field_76202_j = Maps.<EntityPlayer, MapData.MapInfo>newHashMap();
   private final Map<String, MapBanner> field_204270_k = Maps.newHashMap();
   public Map<String, MapDecoration> field_76203_h = Maps.newLinkedHashMap();
   private final Map<String, MapFrame> field_212442_l = Maps.newHashMap();

   public MapData(String var1) {
      super(☃);
   }

   public void func_212440_a(int var1, int var2, int var3, boolean var4, boolean var5, DimensionType var6) {
      this.field_76197_d = (byte)☃;
      this.func_176054_a((double)☃, (double)☃, this.field_76197_d);
      this.field_76200_c = ☃;
      this.field_186210_e = ☃;
      this.field_191096_f = ☃;
      this.func_76185_a();
   }

   public void func_176054_a(double var1, double var3, int var5) {
      int ☃ = 128 * (1 << ☃);
      int ☃x = MathHelper.func_76128_c((☃ + 64.0) / (double)☃);
      int ☃xx = MathHelper.func_76128_c((☃ + 64.0) / (double)☃);
      this.field_76201_a = ☃x * ☃ + ☃ / 2 - 64;
      this.field_76199_b = ☃xx * ☃ + ☃ / 2 - 64;
   }

   @Override
   public void func_76184_a(NBTTagCompound var1) {
      this.field_76200_c = DimensionType.func_186069_a(☃.func_74762_e("dimension"));
      this.field_76201_a = ☃.func_74762_e("xCenter");
      this.field_76199_b = ☃.func_74762_e("zCenter");
      this.field_76197_d = (byte)MathHelper.func_76125_a(☃.func_74771_c("scale"), 0, 4);
      this.field_186210_e = !☃.func_150297_b("trackingPosition", 1) || ☃.func_74767_n("trackingPosition");
      this.field_191096_f = ☃.func_74767_n("unlimitedTracking");
      this.field_76198_e = ☃.func_74770_j("colors");
      if (this.field_76198_e.length != 16384) {
         this.field_76198_e = new byte[16384];
      }

      NBTTagList ☃ = ☃.func_150295_c("banners", 10);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         MapBanner ☃xx = MapBanner.func_204300_a(☃.func_150305_b(☃x));
         this.field_204270_k.put(☃xx.func_204299_f(), ☃xx);
         this.func_191095_a(
            ☃xx.func_204305_c(),
            null,
            ☃xx.func_204299_f(),
            (double)☃xx.func_204304_a().func_177958_n(),
            (double)☃xx.func_204304_a().func_177952_p(),
            180.0,
            ☃xx.func_204302_d()
         );
      }

      NBTTagList ☃x = ☃.func_150295_c("frames", 10);

      for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
         MapFrame ☃xxx = MapFrame.func_212765_a(☃x.func_150305_b(☃xx));
         this.field_212442_l.put(☃xxx.func_212767_e(), ☃xxx);
         this.func_191095_a(
            MapDecoration.Type.FRAME,
            null,
            "frame-" + ☃xxx.func_212769_d(),
            (double)☃xxx.func_212764_b().func_177958_n(),
            (double)☃xxx.func_212764_b().func_177952_p(),
            (double)☃xxx.func_212768_c(),
            null
         );
      }
   }

   @Override
   public NBTTagCompound func_189551_b(NBTTagCompound var1) {
      ☃.func_74768_a("dimension", this.field_76200_c.func_186068_a());
      ☃.func_74768_a("xCenter", this.field_76201_a);
      ☃.func_74768_a("zCenter", this.field_76199_b);
      ☃.func_74774_a("scale", this.field_76197_d);
      ☃.func_74773_a("colors", this.field_76198_e);
      ☃.func_74757_a("trackingPosition", this.field_186210_e);
      ☃.func_74757_a("unlimitedTracking", this.field_191096_f);
      NBTTagList ☃ = new NBTTagList();

      for(MapBanner ☃x : this.field_204270_k.values()) {
         ☃.add((INBTBase)☃x.func_204303_e());
      }

      ☃.func_74782_a("banners", ☃);
      NBTTagList ☃x = new NBTTagList();

      for(MapFrame ☃xx : this.field_212442_l.values()) {
         ☃x.add((INBTBase)☃xx.func_212770_a());
      }

      ☃.func_74782_a("frames", ☃x);
      return ☃;
   }

   public void func_76191_a(EntityPlayer var1, ItemStack var2) {
      if (!this.field_76202_j.containsKey(☃)) {
         MapData.MapInfo ☃ = new MapData.MapInfo(☃);
         this.field_76202_j.put(☃, ☃);
         this.field_76196_g.add(☃);
      }

      if (!☃.field_71071_by.func_70431_c(☃)) {
         this.field_76203_h.remove(☃.func_200200_C_().getString());
      }

      for(int ☃ = 0; ☃ < this.field_76196_g.size(); ++☃) {
         MapData.MapInfo ☃x = (MapData.MapInfo)this.field_76196_g.get(☃);
         String ☃xx = ☃x.field_76211_a.func_200200_C_().getString();
         if (!☃x.field_76211_a.field_70128_L && (☃x.field_76211_a.field_71071_by.func_70431_c(☃) || ☃.func_82839_y())) {
            if (!☃.func_82839_y() && ☃x.field_76211_a.field_71093_bK == this.field_76200_c && this.field_186210_e) {
               this.func_191095_a(
                  MapDecoration.Type.PLAYER,
                  ☃x.field_76211_a.field_70170_p,
                  ☃xx,
                  ☃x.field_76211_a.field_70165_t,
                  ☃x.field_76211_a.field_70161_v,
                  (double)☃x.field_76211_a.field_70177_z,
                  null
               );
            }
         } else {
            this.field_76202_j.remove(☃x.field_76211_a);
            this.field_76196_g.remove(☃x);
            this.field_76203_h.remove(☃xx);
         }
      }

      if (☃.func_82839_y() && this.field_186210_e) {
         EntityItemFrame ☃ = ☃.func_82836_z();
         BlockPos ☃x = ☃.func_174857_n();
         MapFrame ☃xx = (MapFrame)this.field_212442_l.get(MapFrame.func_212766_a(☃x));
         if (☃xx != null && ☃.func_145782_y() != ☃xx.func_212769_d() && this.field_212442_l.containsKey(☃xx.func_212767_e())) {
            this.field_76203_h.remove("frame-" + ☃xx.func_212769_d());
         }

         MapFrame ☃ = new MapFrame(☃x, ☃.field_174860_b.func_176736_b() * 90, ☃.func_145782_y());
         this.func_191095_a(
            MapDecoration.Type.FRAME,
            ☃.field_70170_p,
            "frame-" + ☃.func_145782_y(),
            (double)☃x.func_177958_n(),
            (double)☃x.func_177952_p(),
            (double)(☃.field_174860_b.func_176736_b() * 90),
            null
         );
         this.field_212442_l.put(☃.func_212767_e(), ☃);
      }

      NBTTagCompound ☃ = ☃.func_77978_p();
      if (☃ != null && ☃.func_150297_b("Decorations", 9)) {
         NBTTagList ☃x = ☃.func_150295_c("Decorations", 10);

         for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
            NBTTagCompound ☃xxx = ☃x.func_150305_b(☃xx);
            if (!this.field_76203_h.containsKey(☃xxx.func_74779_i("id"))) {
               this.func_191095_a(
                  MapDecoration.Type.func_191159_a(☃xxx.func_74771_c("type")),
                  ☃.field_70170_p,
                  ☃xxx.func_74779_i("id"),
                  ☃xxx.func_74769_h("x"),
                  ☃xxx.func_74769_h("z"),
                  ☃xxx.func_74769_h("rot"),
                  null
               );
            }
         }
      }
   }

   public static void func_191094_a(ItemStack var0, BlockPos var1, String var2, MapDecoration.Type var3) {
      NBTTagList ☃;
      if (☃.func_77942_o() && ☃.func_77978_p().func_150297_b("Decorations", 9)) {
         ☃ = ☃.func_77978_p().func_150295_c("Decorations", 10);
      } else {
         ☃ = new NBTTagList();
         ☃.func_77983_a("Decorations", ☃);
      }

      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74774_a("type", ☃.func_191163_a());
      ☃.func_74778_a("id", ☃);
      ☃.func_74780_a("x", (double)☃.func_177958_n());
      ☃.func_74780_a("z", (double)☃.func_177952_p());
      ☃.func_74780_a("rot", 180.0);
      ☃.add((INBTBase)☃);
      if (☃.func_191162_c()) {
         NBTTagCompound ☃x = ☃.func_190925_c("display");
         ☃x.func_74768_a("MapColor", ☃.func_191161_d());
      }
   }

   private void func_191095_a(
      MapDecoration.Type var1, @Nullable IWorld var2, String var3, double var4, double var6, double var8, @Nullable ITextComponent var10
   ) {
      int ☃x = 1 << this.field_76197_d;
      float ☃xx = (float)(☃ - (double)this.field_76201_a) / (float)☃x;
      float ☃xxx = (float)(☃ - (double)this.field_76199_b) / (float)☃x;
      byte ☃xxxx = (byte)((int)((double)(☃xx * 2.0F) + 0.5));
      byte ☃xxxxx = (byte)((int)((double)(☃xxx * 2.0F) + 0.5));
      int ☃xxxxxx = 63;
      byte ☃;
      if (☃xx >= -63.0F && ☃xxx >= -63.0F && ☃xx <= 63.0F && ☃xxx <= 63.0F) {
         ☃ += ☃ < 0.0 ? -8.0 : 8.0;
         ☃ = (byte)((int)(☃ * 16.0 / 360.0));
         if (this.field_76200_c == DimensionType.NETHER && ☃ != null) {
            int ☃xxxxxxx = (int)(☃.func_72912_H().func_76073_f() / 10L);
            ☃ = (byte)(☃xxxxxxx * ☃xxxxxxx * 34187121 + ☃xxxxxxx * 121 >> 15 & 15);
         }
      } else {
         if (☃ != MapDecoration.Type.PLAYER) {
            this.field_76203_h.remove(☃);
            return;
         }

         int ☃ = 320;
         if (Math.abs(☃xx) < 320.0F && Math.abs(☃xxx) < 320.0F) {
            ☃ = MapDecoration.Type.PLAYER_OFF_MAP;
         } else {
            if (!this.field_191096_f) {
               this.field_76203_h.remove(☃);
               return;
            }

            ☃ = MapDecoration.Type.PLAYER_OFF_LIMITS;
         }

         ☃ = 0;
         if (☃xx <= -63.0F) {
            ☃xxxx = -128;
         }

         if (☃xxx <= -63.0F) {
            ☃xxxxx = -128;
         }

         if (☃xx >= 63.0F) {
            ☃xxxx = 127;
         }

         if (☃xxx >= 63.0F) {
            ☃xxxxx = 127;
         }
      }

      this.field_76203_h.put(☃, new MapDecoration(☃, ☃xxxx, ☃xxxxx, ☃, ☃));
   }

   @Nullable
   public Packet<?> func_176052_a(ItemStack var1, IBlockReader var2, EntityPlayer var3) {
      MapData.MapInfo ☃ = (MapData.MapInfo)this.field_76202_j.get(☃);
      return ☃ == null ? null : ☃.func_176101_a(☃);
   }

   public void func_176053_a(int var1, int var2) {
      this.func_76185_a();

      for(MapData.MapInfo ☃ : this.field_76196_g) {
         ☃.func_176102_a(☃, ☃);
      }
   }

   public MapData.MapInfo func_82568_a(EntityPlayer var1) {
      MapData.MapInfo ☃ = (MapData.MapInfo)this.field_76202_j.get(☃);
      if (☃ == null) {
         ☃ = new MapData.MapInfo(☃);
         this.field_76202_j.put(☃, ☃);
         this.field_76196_g.add(☃);
      }

      return ☃;
   }

   public void func_204269_a(IWorld var1, BlockPos var2) {
      float ☃ = (float)☃.func_177958_n() + 0.5F;
      float ☃x = (float)☃.func_177952_p() + 0.5F;
      int ☃xx = 1 << this.field_76197_d;
      float ☃xxx = (☃ - (float)this.field_76201_a) / (float)☃xx;
      float ☃xxxx = (☃x - (float)this.field_76199_b) / (float)☃xx;
      int ☃xxxxx = 63;
      boolean ☃xxxxxx = false;
      if (☃xxx >= -63.0F && ☃xxxx >= -63.0F && ☃xxx <= 63.0F && ☃xxxx <= 63.0F) {
         MapBanner ☃xxxxxxx = MapBanner.func_204301_a(☃, ☃);
         if (☃xxxxxxx == null) {
            return;
         }

         boolean ☃xxxxxxx = true;
         if (this.field_204270_k.containsKey(☃xxxxxxx.func_204299_f()) && ((MapBanner)this.field_204270_k.get(☃xxxxxxx.func_204299_f())).equals(☃xxxxxxx)) {
            this.field_204270_k.remove(☃xxxxxxx.func_204299_f());
            this.field_76203_h.remove(☃xxxxxxx.func_204299_f());
            ☃xxxxxxx = false;
            ☃xxxxxx = true;
         }

         if (☃xxxxxxx) {
            this.field_204270_k.put(☃xxxxxxx.func_204299_f(), ☃xxxxxxx);
            this.func_191095_a(☃xxxxxxx.func_204305_c(), ☃, ☃xxxxxxx.func_204299_f(), (double)☃, (double)☃x, 180.0, ☃xxxxxxx.func_204302_d());
            ☃xxxxxx = true;
         }

         if (☃xxxxxx) {
            this.func_76185_a();
         }
      }
   }

   public void func_204268_a(IBlockReader var1, int var2, int var3) {
      Iterator<MapBanner> ☃ = this.field_204270_k.values().iterator();

      while(☃.hasNext()) {
         MapBanner ☃x = (MapBanner)☃.next();
         if (☃x.func_204304_a().func_177958_n() == ☃ && ☃x.func_204304_a().func_177952_p() == ☃) {
            MapBanner ☃xx = MapBanner.func_204301_a(☃, ☃x.func_204304_a());
            if (!☃x.equals(☃xx)) {
               ☃.remove();
               this.field_76203_h.remove(☃x.func_204299_f());
            }
         }
      }
   }

   public void func_212441_a(BlockPos var1, int var2) {
      this.field_76203_h.remove("frame-" + ☃);
      this.field_212442_l.remove(MapFrame.func_212766_a(☃));
   }

   public class MapInfo {
      public final EntityPlayer field_76211_a;
      private boolean field_176105_d = true;
      private int field_176106_e;
      private int field_176103_f;
      private int field_176104_g = 127;
      private int field_176108_h = 127;
      private int field_176109_i;
      public int field_82569_d;

      public MapInfo(EntityPlayer var2) {
         this.field_76211_a = ☃;
      }

      @Nullable
      public Packet<?> func_176101_a(ItemStack var1) {
         if (this.field_176105_d) {
            this.field_176105_d = false;
            return new SPacketMaps(
               ItemMap.func_195949_f(☃),
               MapData.this.field_76197_d,
               MapData.this.field_186210_e,
               MapData.this.field_76203_h.values(),
               MapData.this.field_76198_e,
               this.field_176106_e,
               this.field_176103_f,
               this.field_176104_g + 1 - this.field_176106_e,
               this.field_176108_h + 1 - this.field_176103_f
            );
         } else {
            return this.field_176109_i++ % 5 == 0
               ? new SPacketMaps(
                  ItemMap.func_195949_f(☃),
                  MapData.this.field_76197_d,
                  MapData.this.field_186210_e,
                  MapData.this.field_76203_h.values(),
                  MapData.this.field_76198_e,
                  0,
                  0,
                  0,
                  0
               )
               : null;
         }
      }

      public void func_176102_a(int var1, int var2) {
         if (this.field_176105_d) {
            this.field_176106_e = Math.min(this.field_176106_e, ☃);
            this.field_176103_f = Math.min(this.field_176103_f, ☃);
            this.field_176104_g = Math.max(this.field_176104_g, ☃);
            this.field_176108_h = Math.max(this.field_176108_h, ☃);
         } else {
            this.field_176105_d = true;
            this.field_176106_e = ☃;
            this.field_176103_f = ☃;
            this.field_176104_g = ☃;
            this.field_176108_h = ☃;
         }
      }
   }
}
