package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class IglooPieces {
   private static final ResourceLocation field_202592_e = new ResourceLocation("igloo/top");
   private static final ResourceLocation field_202593_f = new ResourceLocation("igloo/middle");
   private static final ResourceLocation field_202594_g = new ResourceLocation("igloo/bottom");
   private static final Map<ResourceLocation, BlockPos> field_207621_d = ImmutableMap.of(
      field_202592_e, new BlockPos(3, 5, 5), field_202593_f, new BlockPos(1, 3, 1), field_202594_g, new BlockPos(3, 6, 7)
   );
   private static final Map<ResourceLocation, BlockPos> field_207622_e = ImmutableMap.of(
      field_202592_e, new BlockPos(0, 0, 0), field_202593_f, new BlockPos(2, -3, 4), field_202594_g, new BlockPos(0, -3, -2)
   );

   public static void func_202591_ae_() {
      StructureIO.func_143031_a(IglooPieces.Piece.class, "Iglu");
   }

   public static void func_207617_a(TemplateManager var0, BlockPos var1, Rotation var2, List<StructurePiece> var3, Random var4, IglooConfig var5) {
      if (☃.nextDouble() < 0.5) {
         int ☃ = ☃.nextInt(8) + 4;
         ☃.add(new IglooPieces.Piece(☃, field_202594_g, ☃, ☃, ☃ * 3));

         for(int ☃x = 0; ☃x < ☃ - 1; ++☃x) {
            ☃.add(new IglooPieces.Piece(☃, field_202593_f, ☃, ☃, ☃x * 3));
         }
      }

      ☃.add(new IglooPieces.Piece(☃, field_202592_e, ☃, ☃, 0));
   }

   public static class Piece extends TemplateStructurePiece {
      private ResourceLocation field_207615_d;
      private Rotation field_207616_e;

      public Piece() {
      }

      public Piece(TemplateManager var1, ResourceLocation var2, BlockPos var3, Rotation var4, int var5) {
         super(0);
         this.field_207615_d = ☃;
         BlockPos ☃ = (BlockPos)IglooPieces.field_207622_e.get(☃);
         this.field_186178_c = ☃.func_177982_a(☃.func_177958_n(), ☃.func_177956_o() - ☃, ☃.func_177952_p());
         this.field_207616_e = ☃;
         this.func_207614_a(☃);
      }

      private void func_207614_a(TemplateManager var1) {
         Template ☃ = ☃.func_200220_a(this.field_207615_d);
         PlacementSettings ☃x = new PlacementSettings()
            .func_186220_a(this.field_207616_e)
            .func_186214_a(Mirror.NONE)
            .func_207665_a((BlockPos)IglooPieces.field_207621_d.get(this.field_207615_d));
         this.func_186173_a(☃, this.field_186178_c, ☃x);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74778_a("Template", this.field_207615_d.toString());
         ☃.func_74778_a("Rot", this.field_207616_e.name());
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_207615_d = new ResourceLocation(☃.func_74779_i("Template"));
         this.field_207616_e = Rotation.valueOf(☃.func_74779_i("Rot"));
         this.func_207614_a(☃);
      }

      @Override
      protected void func_186175_a(String var1, BlockPos var2, IWorld var3, Random var4, MutableBoundingBox var5) {
         if ("chest".equals(☃)) {
            ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 3);
            TileEntity ☃ = ☃.func_175625_s(☃.func_177977_b());
            if (☃ instanceof TileEntityChest) {
               ((TileEntityChest)☃).func_189404_a(LootTableList.field_186431_m, ☃.nextLong());
            }
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         PlacementSettings ☃ = new PlacementSettings()
            .func_186220_a(this.field_207616_e)
            .func_186214_a(Mirror.NONE)
            .func_207665_a((BlockPos)IglooPieces.field_207621_d.get(this.field_207615_d));
         BlockPos ☃x = (BlockPos)IglooPieces.field_207622_e.get(this.field_207615_d);
         BlockPos ☃xx = this.field_186178_c.func_177971_a(Template.func_186266_a(☃, new BlockPos(3 - ☃x.func_177958_n(), 0, 0 - ☃x.func_177952_p())));
         int ☃xxx = ☃.func_201676_a(Heightmap.Type.WORLD_SURFACE_WG, ☃xx.func_177958_n(), ☃xx.func_177952_p());
         BlockPos ☃xxxx = this.field_186178_c;
         this.field_186178_c = this.field_186178_c.func_177982_a(0, ☃xxx - 90 - 1, 0);
         boolean ☃xxxxx = super.func_74875_a(☃, ☃, ☃, ☃);
         if (this.field_207615_d.equals(IglooPieces.field_202592_e)) {
            BlockPos ☃xxxxxx = this.field_186178_c.func_177971_a(Template.func_186266_a(☃, new BlockPos(3, 0, 5)));
            IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxxxxx.func_177977_b());
            if (!☃xxxxxxx.func_196958_f() && ☃xxxxxxx.func_177230_c() != Blocks.field_150468_ap) {
               ☃.func_180501_a(☃xxxxxx, Blocks.field_196604_cC.func_176223_P(), 3);
            }
         }

         this.field_186178_c = ☃xxxx;
         return ☃xxxxx;
      }
   }
}
