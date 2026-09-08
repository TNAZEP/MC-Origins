package net.minecraft.world.gen.feature.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
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

public class ShipwreckPieces {
   private static final BlockPos field_207663_a = new BlockPos(4, 0, 15);
   private static final ResourceLocation[] field_204761_a = new ResourceLocation[]{
      new ResourceLocation("shipwreck/with_mast"),
      new ResourceLocation("shipwreck/sideways_full"),
      new ResourceLocation("shipwreck/sideways_fronthalf"),
      new ResourceLocation("shipwreck/sideways_backhalf"),
      new ResourceLocation("shipwreck/rightsideup_full"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf"),
      new ResourceLocation("shipwreck/rightsideup_backhalf"),
      new ResourceLocation("shipwreck/with_mast_degraded"),
      new ResourceLocation("shipwreck/rightsideup_full_degraded"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"),
      new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")
   };
   private static final ResourceLocation[] field_204762_b = new ResourceLocation[]{
      new ResourceLocation("shipwreck/with_mast"),
      new ResourceLocation("shipwreck/upsidedown_full"),
      new ResourceLocation("shipwreck/upsidedown_fronthalf"),
      new ResourceLocation("shipwreck/upsidedown_backhalf"),
      new ResourceLocation("shipwreck/sideways_full"),
      new ResourceLocation("shipwreck/sideways_fronthalf"),
      new ResourceLocation("shipwreck/sideways_backhalf"),
      new ResourceLocation("shipwreck/rightsideup_full"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf"),
      new ResourceLocation("shipwreck/rightsideup_backhalf"),
      new ResourceLocation("shipwreck/with_mast_degraded"),
      new ResourceLocation("shipwreck/upsidedown_full_degraded"),
      new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"),
      new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"),
      new ResourceLocation("shipwreck/sideways_full_degraded"),
      new ResourceLocation("shipwreck/sideways_fronthalf_degraded"),
      new ResourceLocation("shipwreck/sideways_backhalf_degraded"),
      new ResourceLocation("shipwreck/rightsideup_full_degraded"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"),
      new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")
   };

   public static void func_204759_a() {
      StructureIO.func_143031_a(ShipwreckPieces.Piece.class, "Shipwreck");
   }

   public static void func_204760_a(TemplateManager var0, BlockPos var1, Rotation var2, List<StructurePiece> var3, Random var4, ShipwreckConfig var5) {
      ResourceLocation ☃ = ☃.field_204753_a ? field_204761_a[☃.nextInt(field_204761_a.length)] : field_204762_b[☃.nextInt(field_204762_b.length)];
      ☃.add(new ShipwreckPieces.Piece(☃, ☃, ☃, ☃, ☃.field_204753_a));
   }

   public static class Piece extends TemplateStructurePiece {
      private Rotation field_204755_d;
      private ResourceLocation field_204756_e;
      private boolean field_204757_f;

      public Piece() {
      }

      public Piece(TemplateManager var1, ResourceLocation var2, BlockPos var3, Rotation var4, boolean var5) {
         super(0);
         this.field_186178_c = ☃;
         this.field_204755_d = ☃;
         this.field_204756_e = ☃;
         this.field_204757_f = ☃;
         this.func_204754_a(☃);
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
         super.func_143012_a(☃);
         ☃.func_74778_a("Template", this.field_204756_e.toString());
         ☃.func_74757_a("isBeached", this.field_204757_f);
         ☃.func_74778_a("Rot", this.field_204755_d.name());
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
         super.func_143011_b(☃, ☃);
         this.field_204756_e = new ResourceLocation(☃.func_74779_i("Template"));
         this.field_204757_f = ☃.func_74767_n("isBeached");
         this.field_204755_d = Rotation.valueOf(☃.func_74779_i("Rot"));
         this.func_204754_a(☃);
      }

      private void func_204754_a(TemplateManager var1) {
         Template ☃ = ☃.func_200220_a(this.field_204756_e);
         PlacementSettings ☃x = new PlacementSettings()
            .func_186220_a(this.field_204755_d)
            .func_186225_a(Blocks.field_150350_a)
            .func_186214_a(Mirror.NONE)
            .func_207665_a(ShipwreckPieces.field_207663_a);
         this.func_186173_a(☃, this.field_186178_c, ☃x);
      }

      @Override
      protected void func_186175_a(String var1, BlockPos var2, IWorld var3, Random var4, MutableBoundingBox var5) {
         if ("map_chest".equals(☃)) {
            TileEntityLockableLoot.func_195479_a(☃, ☃, ☃.func_177977_b(), LootTableList.field_204771_s);
         } else if ("treasure_chest".equals(☃)) {
            TileEntityLockableLoot.func_195479_a(☃, ☃, ☃.func_177977_b(), LootTableList.field_204773_u);
         } else if ("supply_chest".equals(☃)) {
            TileEntityLockableLoot.func_195479_a(☃, ☃, ☃.func_177977_b(), LootTableList.field_204772_t);
         }
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         int ☃ = 256;
         int ☃x = 0;
         BlockPos ☃xx = this.field_186178_c
            .func_177982_a(this.field_186176_a.func_186259_a().func_177958_n() - 1, 0, this.field_186176_a.func_186259_a().func_177952_p() - 1);

         for(BlockPos ☃xxx : BlockPos.func_177980_a(this.field_186178_c, ☃xx)) {
            int ☃xxxx = ☃.func_201676_a(
               this.field_204757_f ? Heightmap.Type.WORLD_SURFACE_WG : Heightmap.Type.OCEAN_FLOOR_WG, ☃xxx.func_177958_n(), ☃xxx.func_177952_p()
            );
            ☃x += ☃xxxx;
            ☃ = Math.min(☃, ☃xxxx);
         }

         ☃x /= this.field_186176_a.func_186259_a().func_177958_n() * this.field_186176_a.func_186259_a().func_177952_p();
         int ☃xxx = this.field_204757_f ? ☃ - this.field_186176_a.func_186259_a().func_177956_o() / 2 - ☃.nextInt(3) : ☃x;
         this.field_186178_c = new BlockPos(this.field_186178_c.func_177958_n(), ☃xxx, this.field_186178_c.func_177952_p());
         return super.func_74875_a(☃, ☃, ☃, ☃);
      }
   }
}
