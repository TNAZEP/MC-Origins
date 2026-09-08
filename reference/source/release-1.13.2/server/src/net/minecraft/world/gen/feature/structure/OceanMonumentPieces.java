package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class OceanMonumentPieces {
   public static void func_175970_a() {
      StructureIO.func_143031_a(OceanMonumentPieces.MonumentBuilding.class, "OMB");
      StructureIO.func_143031_a(OceanMonumentPieces.MonumentCoreRoom.class, "OMCR");
      StructureIO.func_143031_a(OceanMonumentPieces.DoubleXRoom.class, "OMDXR");
      StructureIO.func_143031_a(OceanMonumentPieces.DoubleXYRoom.class, "OMDXYR");
      StructureIO.func_143031_a(OceanMonumentPieces.DoubleYRoom.class, "OMDYR");
      StructureIO.func_143031_a(OceanMonumentPieces.DoubleYZRoom.class, "OMDYZR");
      StructureIO.func_143031_a(OceanMonumentPieces.DoubleZRoom.class, "OMDZR");
      StructureIO.func_143031_a(OceanMonumentPieces.EntryRoom.class, "OMEntry");
      StructureIO.func_143031_a(OceanMonumentPieces.Penthouse.class, "OMPenthouse");
      StructureIO.func_143031_a(OceanMonumentPieces.SimpleRoom.class, "OMSimple");
      StructureIO.func_143031_a(OceanMonumentPieces.SimpleTopRoom.class, "OMSimpleT");
   }

   public static class DoubleXRoom extends OceanMonumentPieces.Piece {
      public DoubleXRoom() {
      }

      public DoubleXRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 2, 1, 1);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         OceanMonumentPieces.RoomDefinition ☃ = this.field_175830_k.field_175965_b[EnumFacing.EAST.func_176745_a()];
         OceanMonumentPieces.RoomDefinition ☃x = this.field_175830_k;
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(☃, ☃, 8, 0, ☃.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
            this.func_175821_a(☃, ☃, 0, 0, ☃x.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
         }

         if (☃x.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 4, 1, 7, 4, 6, field_175828_a);
         }

         if (☃.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 8, 4, 1, 14, 4, 6, field_175828_a);
         }

         this.func_175804_a(☃, ☃, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 15, 3, 0, 15, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 0, 15, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 7, 14, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 2, 7, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 15, 2, 0, 15, 2, 7, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 1, 2, 0, 15, 2, 0, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 1, 2, 7, 14, 2, 7, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 15, 1, 0, 15, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 0, 15, 1, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 1, 0, 10, 1, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 2, 0, 9, 2, 3, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 5, 3, 0, 10, 3, 4, field_175826_b, field_175826_b, false);
         this.func_175811_a(☃, field_175825_e, 6, 2, 3, ☃);
         this.func_175811_a(☃, field_175825_e, 9, 2, 3, ☃);
         if (☃x.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 0, 4, 2, 0);
         }

         if (☃x.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 7, 4, 2, 7);
         }

         if (☃x.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 1, 3, 0, 2, 4);
         }

         if (☃.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 11, 1, 0, 12, 2, 0);
         }

         if (☃.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 11, 1, 7, 12, 2, 7);
         }

         if (☃.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 15, 1, 3, 15, 2, 4);
         }

         return true;
      }
   }

   public static class DoubleXYRoom extends OceanMonumentPieces.Piece {
      public DoubleXYRoom() {
      }

      public DoubleXYRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 2, 2, 1);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         OceanMonumentPieces.RoomDefinition ☃ = this.field_175830_k.field_175965_b[EnumFacing.EAST.func_176745_a()];
         OceanMonumentPieces.RoomDefinition ☃x = this.field_175830_k;
         OceanMonumentPieces.RoomDefinition ☃xx = ☃x.field_175965_b[EnumFacing.UP.func_176745_a()];
         OceanMonumentPieces.RoomDefinition ☃xxx = ☃.field_175965_b[EnumFacing.UP.func_176745_a()];
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(☃, ☃, 8, 0, ☃.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
            this.func_175821_a(☃, ☃, 0, 0, ☃x.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
         }

         if (☃xx.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 8, 1, 7, 8, 6, field_175828_a);
         }

         if (☃xxx.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 8, 8, 1, 14, 8, 6, field_175828_a);
         }

         for(int ☃ = 1; ☃ <= 7; ++☃) {
            IBlockState ☃x = field_175826_b;
            if (☃ == 2 || ☃ == 6) {
               ☃x = field_175828_a;
            }

            this.func_175804_a(☃, ☃, 0, ☃, 0, 0, ☃, 7, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 15, ☃, 0, 15, ☃, 7, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 1, ☃, 0, 15, ☃, 0, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 1, ☃, 7, 14, ☃, 7, ☃x, ☃x, false);
         }

         this.func_175804_a(☃, ☃, 2, 1, 3, 2, 7, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 3, 1, 2, 4, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 3, 1, 5, 4, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 13, 1, 3, 13, 7, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 11, 1, 2, 12, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 11, 1, 5, 12, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 1, 3, 5, 3, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 1, 3, 10, 3, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 7, 2, 10, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 5, 2, 5, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 5, 2, 10, 7, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 5, 5, 5, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 5, 5, 10, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175811_a(☃, field_175826_b, 6, 6, 2, ☃);
         this.func_175811_a(☃, field_175826_b, 9, 6, 2, ☃);
         this.func_175811_a(☃, field_175826_b, 6, 6, 5, ☃);
         this.func_175811_a(☃, field_175826_b, 9, 6, 5, ☃);
         this.func_175804_a(☃, ☃, 5, 4, 3, 6, 4, 4, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 9, 4, 3, 10, 4, 4, field_175826_b, field_175826_b, false);
         this.func_175811_a(☃, field_175825_e, 5, 4, 2, ☃);
         this.func_175811_a(☃, field_175825_e, 5, 4, 5, ☃);
         this.func_175811_a(☃, field_175825_e, 10, 4, 2, ☃);
         this.func_175811_a(☃, field_175825_e, 10, 4, 5, ☃);
         if (☃x.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 0, 4, 2, 0);
         }

         if (☃x.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 7, 4, 2, 7);
         }

         if (☃x.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 1, 3, 0, 2, 4);
         }

         if (☃.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 11, 1, 0, 12, 2, 0);
         }

         if (☃.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 11, 1, 7, 12, 2, 7);
         }

         if (☃.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 15, 1, 3, 15, 2, 4);
         }

         if (☃xx.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 5, 0, 4, 6, 0);
         }

         if (☃xx.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 5, 7, 4, 6, 7);
         }

         if (☃xx.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 5, 3, 0, 6, 4);
         }

         if (☃xxx.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 11, 5, 0, 12, 6, 0);
         }

         if (☃xxx.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 11, 5, 7, 12, 6, 7);
         }

         if (☃xxx.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 15, 5, 3, 15, 6, 4);
         }

         return true;
      }
   }

   public static class DoubleYRoom extends OceanMonumentPieces.Piece {
      public DoubleYRoom() {
      }

      public DoubleYRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 1, 2, 1);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(☃, ☃, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
         }

         OceanMonumentPieces.RoomDefinition ☃ = this.field_175830_k.field_175965_b[EnumFacing.UP.func_176745_a()];
         if (☃.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 8, 1, 6, 8, 6, field_175828_a);
         }

         this.func_175804_a(☃, ☃, 0, 4, 0, 0, 4, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 7, 4, 0, 7, 4, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 4, 0, 6, 4, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 4, 7, 6, 4, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 2, 4, 1, 2, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 4, 2, 1, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 4, 1, 5, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 4, 2, 6, 4, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 2, 4, 5, 2, 4, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 4, 5, 1, 4, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 4, 5, 5, 4, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 4, 5, 6, 4, 5, field_175826_b, field_175826_b, false);
         OceanMonumentPieces.RoomDefinition ☃ = this.field_175830_k;

         for(int ☃x = 1; ☃x <= 5; ☃x += 4) {
            int ☃xx = 0;
            if (☃.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 2, ☃x, ☃xx, 2, ☃x + 2, ☃xx, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 5, ☃x, ☃xx, 5, ☃x + 2, ☃xx, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 3, ☃x + 2, ☃xx, 4, ☃x + 2, ☃xx, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, 0, ☃x, ☃xx, 7, ☃x + 2, ☃xx, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 0, ☃x + 1, ☃xx, 7, ☃x + 1, ☃xx, field_175828_a, field_175828_a, false);
            }

            int var10 = 7;
            if (☃.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 2, ☃x, var10, 2, ☃x + 2, var10, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 5, ☃x, var10, 5, ☃x + 2, var10, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 3, ☃x + 2, var10, 4, ☃x + 2, var10, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, 0, ☃x, var10, 7, ☃x + 2, var10, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 0, ☃x + 1, var10, 7, ☃x + 1, var10, field_175828_a, field_175828_a, false);
            }

            int ☃xx = 0;
            if (☃.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
               this.func_175804_a(☃, ☃, ☃xx, ☃x, 2, ☃xx, ☃x + 2, 2, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃xx, ☃x, 5, ☃xx, ☃x + 2, 5, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃xx, ☃x + 2, 3, ☃xx, ☃x + 2, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, ☃xx, ☃x, 0, ☃xx, ☃x + 2, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃xx, ☃x + 1, 0, ☃xx, ☃x + 1, 7, field_175828_a, field_175828_a, false);
            }

            int var11 = 7;
            if (☃.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
               this.func_175804_a(☃, ☃, var11, ☃x, 2, var11, ☃x + 2, 2, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, var11, ☃x, 5, var11, ☃x + 2, 5, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, var11, ☃x + 2, 3, var11, ☃x + 2, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, var11, ☃x, 0, var11, ☃x + 2, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, var11, ☃x + 1, 0, var11, ☃x + 1, 7, field_175828_a, field_175828_a, false);
            }

            ☃ = ☃;
         }

         return true;
      }
   }

   public static class DoubleYZRoom extends OceanMonumentPieces.Piece {
      public DoubleYZRoom() {
      }

      public DoubleYZRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 1, 2, 2);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         OceanMonumentPieces.RoomDefinition ☃ = this.field_175830_k.field_175965_b[EnumFacing.NORTH.func_176745_a()];
         OceanMonumentPieces.RoomDefinition ☃x = this.field_175830_k;
         OceanMonumentPieces.RoomDefinition ☃xx = ☃.field_175965_b[EnumFacing.UP.func_176745_a()];
         OceanMonumentPieces.RoomDefinition ☃xxx = ☃x.field_175965_b[EnumFacing.UP.func_176745_a()];
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(☃, ☃, 0, 8, ☃.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
            this.func_175821_a(☃, ☃, 0, 0, ☃x.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
         }

         if (☃xxx.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 8, 1, 6, 8, 7, field_175828_a);
         }

         if (☃xx.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 8, 8, 6, 8, 14, field_175828_a);
         }

         for(int ☃ = 1; ☃ <= 7; ++☃) {
            IBlockState ☃x = field_175826_b;
            if (☃ == 2 || ☃ == 6) {
               ☃x = field_175828_a;
            }

            this.func_175804_a(☃, ☃, 0, ☃, 0, 0, ☃, 15, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 7, ☃, 0, 7, ☃, 15, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 1, ☃, 0, 6, ☃, 0, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 1, ☃, 15, 6, ☃, 15, ☃x, ☃x, false);
         }

         for(int ☃ = 1; ☃ <= 7; ++☃) {
            IBlockState ☃x = field_175827_c;
            if (☃ == 2 || ☃ == 6) {
               ☃x = field_175825_e;
            }

            this.func_175804_a(☃, ☃, 3, ☃, 7, 4, ☃, 8, ☃x, ☃x, false);
         }

         if (☃x.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 0, 4, 2, 0);
         }

         if (☃x.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 7, 1, 3, 7, 2, 4);
         }

         if (☃x.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 1, 3, 0, 2, 4);
         }

         if (☃.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 15, 4, 2, 15);
         }

         if (☃.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 1, 11, 0, 2, 12);
         }

         if (☃.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 7, 1, 11, 7, 2, 12);
         }

         if (☃xxx.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 5, 0, 4, 6, 0);
         }

         if (☃xxx.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 7, 5, 3, 7, 6, 4);
            this.func_175804_a(☃, ☃, 5, 4, 2, 6, 4, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 6, 1, 2, 6, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 6, 1, 5, 6, 3, 5, field_175826_b, field_175826_b, false);
         }

         if (☃xxx.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 5, 3, 0, 6, 4);
            this.func_175804_a(☃, ☃, 1, 4, 2, 2, 4, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 1, 2, 1, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 1, 5, 1, 3, 5, field_175826_b, field_175826_b, false);
         }

         if (☃xx.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 5, 15, 4, 6, 15);
         }

         if (☃xx.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 5, 11, 0, 6, 12);
            this.func_175804_a(☃, ☃, 1, 4, 10, 2, 4, 13, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 1, 10, 1, 3, 10, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 1, 13, 1, 3, 13, field_175826_b, field_175826_b, false);
         }

         if (☃xx.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 7, 5, 11, 7, 6, 12);
            this.func_175804_a(☃, ☃, 5, 4, 10, 6, 4, 13, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 6, 1, 10, 6, 3, 10, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 6, 1, 13, 6, 3, 13, field_175826_b, field_175826_b, false);
         }

         return true;
      }
   }

   public static class DoubleZRoom extends OceanMonumentPieces.Piece {
      public DoubleZRoom() {
      }

      public DoubleZRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 1, 1, 2);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         OceanMonumentPieces.RoomDefinition ☃ = this.field_175830_k.field_175965_b[EnumFacing.NORTH.func_176745_a()];
         OceanMonumentPieces.RoomDefinition ☃x = this.field_175830_k;
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(☃, ☃, 0, 8, ☃.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
            this.func_175821_a(☃, ☃, 0, 0, ☃x.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
         }

         if (☃x.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 4, 1, 6, 4, 7, field_175828_a);
         }

         if (☃.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 4, 8, 6, 4, 14, field_175828_a);
         }

         this.func_175804_a(☃, ☃, 0, 3, 0, 0, 3, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 7, 3, 0, 7, 3, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 15, 6, 3, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 2, 15, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 7, 2, 0, 7, 2, 15, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 1, 2, 0, 7, 2, 0, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 1, 2, 15, 6, 2, 15, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 0, 1, 0, 0, 1, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 7, 1, 0, 7, 1, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 0, 7, 1, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 15, 6, 1, 15, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 1, 1, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 1, 1, 6, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 1, 1, 3, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 3, 1, 6, 3, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 13, 1, 1, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 1, 13, 6, 1, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 13, 1, 3, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 3, 13, 6, 3, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 2, 1, 6, 2, 3, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 1, 6, 5, 3, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 2, 1, 9, 2, 3, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 1, 9, 5, 3, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 3, 2, 6, 4, 2, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 3, 2, 9, 4, 2, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 2, 2, 7, 2, 2, 8, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 2, 7, 5, 2, 8, field_175826_b, field_175826_b, false);
         this.func_175811_a(☃, field_175825_e, 2, 2, 5, ☃);
         this.func_175811_a(☃, field_175825_e, 5, 2, 5, ☃);
         this.func_175811_a(☃, field_175825_e, 2, 2, 10, ☃);
         this.func_175811_a(☃, field_175825_e, 5, 2, 10, ☃);
         this.func_175811_a(☃, field_175826_b, 2, 3, 5, ☃);
         this.func_175811_a(☃, field_175826_b, 5, 3, 5, ☃);
         this.func_175811_a(☃, field_175826_b, 2, 3, 10, ☃);
         this.func_175811_a(☃, field_175826_b, 5, 3, 10, ☃);
         if (☃x.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 0, 4, 2, 0);
         }

         if (☃x.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 7, 1, 3, 7, 2, 4);
         }

         if (☃x.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 1, 3, 0, 2, 4);
         }

         if (☃.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 15, 4, 2, 15);
         }

         if (☃.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 1, 11, 0, 2, 12);
         }

         if (☃.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 7, 1, 11, 7, 2, 12);
         }

         return true;
      }
   }

   public static class EntryRoom extends OceanMonumentPieces.Piece {
      public EntryRoom() {
      }

      public EntryRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2) {
         super(1, ☃, ☃, 1, 1, 1);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 0, 3, 0, 2, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 1, 2, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 2, 0, 7, 2, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 0, 2, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 1, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
         if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 7, 4, 2, 7);
         }

         if (this.field_175830_k.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 0, 1, 3, 1, 2, 4);
         }

         if (this.field_175830_k.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 6, 1, 3, 7, 2, 4);
         }

         return true;
      }
   }

   static class FitSimpleRoomHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private FitSimpleRoomHelper() {
      }

      @Override
      public boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1) {
         return true;
      }

      @Override
      public OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         ☃.field_175963_d = true;
         return new OceanMonumentPieces.SimpleRoom(☃, ☃, ☃);
      }
   }

   static class FitSimpleRoomTopHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private FitSimpleRoomTopHelper() {
      }

      @Override
      public boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1) {
         return !☃.field_175966_c[EnumFacing.WEST.func_176745_a()]
            && !☃.field_175966_c[EnumFacing.EAST.func_176745_a()]
            && !☃.field_175966_c[EnumFacing.NORTH.func_176745_a()]
            && !☃.field_175966_c[EnumFacing.SOUTH.func_176745_a()]
            && !☃.field_175966_c[EnumFacing.UP.func_176745_a()];
      }

      @Override
      public OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         ☃.field_175963_d = true;
         return new OceanMonumentPieces.SimpleTopRoom(☃, ☃, ☃);
      }
   }

   public static class MonumentBuilding extends OceanMonumentPieces.Piece {
      private OceanMonumentPieces.RoomDefinition field_175845_o;
      private OceanMonumentPieces.RoomDefinition field_175844_p;
      private final List<OceanMonumentPieces.Piece> field_175843_q = Lists.<OceanMonumentPieces.Piece>newArrayList();

      public MonumentBuilding() {
      }

      public MonumentBuilding(Random var1, int var2, int var3, EnumFacing var4) {
         super(0);
         this.func_186164_a(☃);
         EnumFacing ☃ = this.func_186165_e();
         if (☃.func_176740_k() == EnumFacing.Axis.Z) {
            this.field_74887_e = new MutableBoundingBox(☃, 39, ☃, ☃ + 58 - 1, 61, ☃ + 58 - 1);
         } else {
            this.field_74887_e = new MutableBoundingBox(☃, 39, ☃, ☃ + 58 - 1, 61, ☃ + 58 - 1);
         }

         List<OceanMonumentPieces.RoomDefinition> ☃ = this.func_175836_a(☃);
         this.field_175845_o.field_175963_d = true;
         this.field_175843_q.add(new OceanMonumentPieces.EntryRoom(☃, this.field_175845_o));
         this.field_175843_q.add(new OceanMonumentPieces.MonumentCoreRoom(☃, this.field_175844_p, ☃));
         List<OceanMonumentPieces.MonumentRoomFitHelper> ☃x = Lists.<OceanMonumentPieces.MonumentRoomFitHelper>newArrayList();
         ☃x.add(new OceanMonumentPieces.XYDoubleRoomFitHelper());
         ☃x.add(new OceanMonumentPieces.YZDoubleRoomFitHelper());
         ☃x.add(new OceanMonumentPieces.ZDoubleRoomFitHelper());
         ☃x.add(new OceanMonumentPieces.XDoubleRoomFitHelper());
         ☃x.add(new OceanMonumentPieces.YDoubleRoomFitHelper());
         ☃x.add(new OceanMonumentPieces.FitSimpleRoomTopHelper());
         ☃x.add(new OceanMonumentPieces.FitSimpleRoomHelper());

         for(OceanMonumentPieces.RoomDefinition ☃xx : ☃) {
            if (!☃xx.field_175963_d && !☃xx.func_175961_b()) {
               for(OceanMonumentPieces.MonumentRoomFitHelper ☃xxx : ☃x) {
                  if (☃xxx.func_175969_a(☃xx)) {
                     this.field_175843_q.add(☃xxx.func_175968_a(☃, ☃xx, ☃));
                     break;
                  }
               }
            }
         }

         int ☃xx = this.field_74887_e.field_78895_b;
         int ☃xxx = this.func_74865_a(9, 22);
         int ☃xxxx = this.func_74873_b(9, 22);

         for(OceanMonumentPieces.Piece ☃xxxxx : this.field_175843_q) {
            ☃xxxxx.func_74874_b().func_78886_a(☃xxx, ☃xx, ☃xxxx);
         }

         MutableBoundingBox ☃xxxxx = MutableBoundingBox.func_175899_a(
            this.func_74865_a(1, 1), this.func_74862_a(1), this.func_74873_b(1, 1), this.func_74865_a(23, 21), this.func_74862_a(8), this.func_74873_b(23, 21)
         );
         MutableBoundingBox ☃xxxxxx = MutableBoundingBox.func_175899_a(
            this.func_74865_a(34, 1),
            this.func_74862_a(1),
            this.func_74873_b(34, 1),
            this.func_74865_a(56, 21),
            this.func_74862_a(8),
            this.func_74873_b(56, 21)
         );
         MutableBoundingBox ☃xxxxxxx = MutableBoundingBox.func_175899_a(
            this.func_74865_a(22, 22),
            this.func_74862_a(13),
            this.func_74873_b(22, 22),
            this.func_74865_a(35, 35),
            this.func_74862_a(17),
            this.func_74873_b(35, 35)
         );
         int ☃xxxxxxxx = ☃.nextInt();
         this.field_175843_q.add(new OceanMonumentPieces.WingRoom(☃, ☃xxxxx, ☃xxxxxxxx++));
         this.field_175843_q.add(new OceanMonumentPieces.WingRoom(☃, ☃xxxxxx, ☃xxxxxxxx++));
         this.field_175843_q.add(new OceanMonumentPieces.Penthouse(☃, ☃xxxxxxx));
      }

      private List<OceanMonumentPieces.RoomDefinition> func_175836_a(Random var1) {
         OceanMonumentPieces.RoomDefinition[] ☃ = new OceanMonumentPieces.RoomDefinition[75];

         for(int ☃x = 0; ☃x < 5; ++☃x) {
            for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
               int ☃xxx = 0;
               int ☃xxxx = func_175820_a(☃x, 0, ☃xx);
               ☃[☃xxxx] = new OceanMonumentPieces.RoomDefinition(☃xxxx);
            }
         }

         for(int ☃x = 0; ☃x < 5; ++☃x) {
            for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
               int ☃xxx = 1;
               int ☃xxxx = func_175820_a(☃x, 1, ☃xx);
               ☃[☃xxxx] = new OceanMonumentPieces.RoomDefinition(☃xxxx);
            }
         }

         for(int ☃x = 1; ☃x < 4; ++☃x) {
            for(int ☃xx = 0; ☃xx < 2; ++☃xx) {
               int ☃xxx = 2;
               int ☃xxxx = func_175820_a(☃x, 2, ☃xx);
               ☃[☃xxxx] = new OceanMonumentPieces.RoomDefinition(☃xxxx);
            }
         }

         this.field_175845_o = ☃[field_175823_g];

         for(int ☃x = 0; ☃x < 5; ++☃x) {
            for(int ☃xx = 0; ☃xx < 5; ++☃xx) {
               for(int ☃xxx = 0; ☃xxx < 3; ++☃xxx) {
                  int ☃xxxx = func_175820_a(☃x, ☃xxx, ☃xx);
                  if (☃[☃xxxx] != null) {
                     for(EnumFacing ☃xxxxx : EnumFacing.values()) {
                        int ☃xxxxxx = ☃x + ☃xxxxx.func_82601_c();
                        int ☃xxxxxxx = ☃xxx + ☃xxxxx.func_96559_d();
                        int ☃xxxxxxxx = ☃xx + ☃xxxxx.func_82599_e();
                        if (☃xxxxxx >= 0 && ☃xxxxxx < 5 && ☃xxxxxxxx >= 0 && ☃xxxxxxxx < 5 && ☃xxxxxxx >= 0 && ☃xxxxxxx < 3) {
                           int ☃xxxxxxxxx = func_175820_a(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
                           if (☃[☃xxxxxxxxx] != null) {
                              if (☃xxxxxxxx == ☃xx) {
                                 ☃[☃xxxx].func_175957_a(☃xxxxx, ☃[☃xxxxxxxxx]);
                              } else {
                                 ☃[☃xxxx].func_175957_a(☃xxxxx.func_176734_d(), ☃[☃xxxxxxxxx]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         OceanMonumentPieces.RoomDefinition ☃x = new OceanMonumentPieces.RoomDefinition(1003);
         OceanMonumentPieces.RoomDefinition ☃xx = new OceanMonumentPieces.RoomDefinition(1001);
         OceanMonumentPieces.RoomDefinition ☃xxx = new OceanMonumentPieces.RoomDefinition(1002);
         ☃[field_175831_h].func_175957_a(EnumFacing.UP, ☃x);
         ☃[field_175832_i].func_175957_a(EnumFacing.SOUTH, ☃xx);
         ☃[field_175829_j].func_175957_a(EnumFacing.SOUTH, ☃xxx);
         ☃x.field_175963_d = true;
         ☃xx.field_175963_d = true;
         ☃xxx.field_175963_d = true;
         this.field_175845_o.field_175964_e = true;
         this.field_175844_p = ☃[func_175820_a(☃.nextInt(4), 0, 2)];
         this.field_175844_p.field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175965_b[EnumFacing.UP
               .func_176745_a()]
            .field_175963_d = true;
         List<OceanMonumentPieces.RoomDefinition> ☃xxxx = Lists.<OceanMonumentPieces.RoomDefinition>newArrayList();

         for(OceanMonumentPieces.RoomDefinition ☃xxxxx : ☃) {
            if (☃xxxxx != null) {
               ☃xxxxx.func_175958_a();
               ☃xxxx.add(☃xxxxx);
            }
         }

         ☃x.func_175958_a();
         Collections.shuffle(☃xxxx, ☃);
         int ☃xxxxx = 1;

         for(OceanMonumentPieces.RoomDefinition ☃xxxxxx : ☃xxxx) {
            int ☃xxxxxxx = 0;
            int ☃xxxxxxxx = 0;

            while(☃xxxxxxx < 2 && ☃xxxxxxxx < 5) {
               ++☃xxxxxxxx;
               int ☃xxxxxxxxx = ☃.nextInt(6);
               if (☃xxxxxx.field_175966_c[☃xxxxxxxxx]) {
                  int ☃xxxxxxxxxx = EnumFacing.func_82600_a(☃xxxxxxxxx).func_176734_d().func_176745_a();
                  ☃xxxxxx.field_175966_c[☃xxxxxxxxx] = false;
                  ☃xxxxxx.field_175965_b[☃xxxxxxxxx].field_175966_c[☃xxxxxxxxxx] = false;
                  if (☃xxxxxx.func_175959_a(☃xxxxx++) && ☃xxxxxx.field_175965_b[☃xxxxxxxxx].func_175959_a(☃xxxxx++)) {
                     ++☃xxxxxxx;
                  } else {
                     ☃xxxxxx.field_175966_c[☃xxxxxxxxx] = true;
                     ☃xxxxxx.field_175965_b[☃xxxxxxxxx].field_175966_c[☃xxxxxxxxxx] = true;
                  }
               }
            }
         }

         ☃xxxx.add(☃x);
         ☃xxxx.add(☃xx);
         ☃xxxx.add(☃xxx);
         return ☃xxxx;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         int ☃ = Math.max(☃.func_181545_F(), 64) - this.field_74887_e.field_78895_b;
         this.func_209179_a(☃, ☃, 0, 0, 0, 58, ☃, 58);
         this.func_175840_a(false, 0, ☃, ☃, ☃);
         this.func_175840_a(true, 33, ☃, ☃, ☃);
         this.func_175839_b(☃, ☃, ☃);
         this.func_175837_c(☃, ☃, ☃);
         this.func_175841_d(☃, ☃, ☃);
         this.func_175835_e(☃, ☃, ☃);
         this.func_175842_f(☃, ☃, ☃);
         this.func_175838_g(☃, ☃, ☃);

         for(int ☃x = 0; ☃x < 7; ++☃x) {
            int ☃xx = 0;

            while(☃xx < 7) {
               if (☃xx == 0 && ☃x == 3) {
                  ☃xx = 6;
               }

               int ☃xxx = ☃x * 9;
               int ☃xxxx = ☃xx * 9;

               for(int ☃xxxxx = 0; ☃xxxxx < 4; ++☃xxxxx) {
                  for(int ☃xxxxxx = 0; ☃xxxxxx < 4; ++☃xxxxxx) {
                     this.func_175811_a(☃, field_175826_b, ☃xxx + ☃xxxxx, 0, ☃xxxx + ☃xxxxxx, ☃);
                     this.func_175808_b(☃, field_175826_b, ☃xxx + ☃xxxxx, -1, ☃xxxx + ☃xxxxxx, ☃);
                  }
               }

               if (☃x != 0 && ☃x != 6) {
                  ☃xx += 6;
               } else {
                  ++☃xx;
               }
            }
         }

         for(int ☃x = 0; ☃x < 5; ++☃x) {
            this.func_209179_a(☃, ☃, -1 - ☃x, 0 + ☃x * 2, -1 - ☃x, -1 - ☃x, 23, 58 + ☃x);
            this.func_209179_a(☃, ☃, 58 + ☃x, 0 + ☃x * 2, -1 - ☃x, 58 + ☃x, 23, 58 + ☃x);
            this.func_209179_a(☃, ☃, 0 - ☃x, 0 + ☃x * 2, -1 - ☃x, 57 + ☃x, 23, -1 - ☃x);
            this.func_209179_a(☃, ☃, 0 - ☃x, 0 + ☃x * 2, 58 + ☃x, 57 + ☃x, 23, 58 + ☃x);
         }

         for(OceanMonumentPieces.Piece ☃x : this.field_175843_q) {
            if (☃x.func_74874_b().func_78884_a(☃)) {
               ☃x.func_74875_a(☃, ☃, ☃, ☃);
            }
         }

         return true;
      }

      private void func_175840_a(boolean var1, int var2, IWorld var3, Random var4, MutableBoundingBox var5) {
         int ☃ = 24;
         if (this.func_175818_a(☃, ☃, 0, ☃ + 23, 20)) {
            this.func_175804_a(☃, ☃, ☃ + 0, 0, 0, ☃ + 24, 0, 20, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, ☃ + 0, 1, 0, ☃ + 24, 10, 20);

            for(int ☃x = 0; ☃x < 4; ++☃x) {
               this.func_175804_a(☃, ☃, ☃ + ☃x, ☃x + 1, ☃x, ☃ + ☃x, ☃x + 1, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃ + ☃x + 7, ☃x + 5, ☃x + 7, ☃ + ☃x + 7, ☃x + 5, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃ + 17 - ☃x, ☃x + 5, ☃x + 7, ☃ + 17 - ☃x, ☃x + 5, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃ + 24 - ☃x, ☃x + 1, ☃x, ☃ + 24 - ☃x, ☃x + 1, 20, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃ + ☃x + 1, ☃x + 1, ☃x, ☃ + 23 - ☃x, ☃x + 1, ☃x, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, ☃ + ☃x + 8, ☃x + 5, ☃x + 7, ☃ + 16 - ☃x, ☃x + 5, ☃x + 7, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(☃, ☃, ☃ + 4, 4, 4, ☃ + 6, 4, 20, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 7, 4, 4, ☃ + 17, 4, 6, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 18, 4, 4, ☃ + 20, 4, 20, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 11, 8, 11, ☃ + 13, 8, 20, field_175828_a, field_175828_a, false);
            this.func_175811_a(☃, field_175824_d, ☃ + 12, 9, 12, ☃);
            this.func_175811_a(☃, field_175824_d, ☃ + 12, 9, 15, ☃);
            this.func_175811_a(☃, field_175824_d, ☃ + 12, 9, 18, ☃);
            int ☃x = ☃ + (☃ ? 19 : 5);
            int ☃xx = ☃ + (☃ ? 5 : 19);

            for(int ☃xxx = 20; ☃xxx >= 5; ☃xxx -= 3) {
               this.func_175811_a(☃, field_175824_d, ☃x, 5, ☃xxx, ☃);
            }

            for(int ☃xxx = 19; ☃xxx >= 7; ☃xxx -= 3) {
               this.func_175811_a(☃, field_175824_d, ☃xx, 5, ☃xxx, ☃);
            }

            for(int ☃xxx = 0; ☃xxx < 4; ++☃xxx) {
               int ☃xxxx = ☃ ? ☃ + 24 - (17 - ☃xxx * 3) : ☃ + 17 - ☃xxx * 3;
               this.func_175811_a(☃, field_175824_d, ☃xxxx, 5, 5, ☃);
            }

            this.func_175811_a(☃, field_175824_d, ☃xx, 5, 5, ☃);
            this.func_175804_a(☃, ☃, ☃ + 11, 1, 12, ☃ + 13, 7, 12, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 12, 1, 11, ☃ + 12, 7, 13, field_175828_a, field_175828_a, false);
         }
      }

      private void func_175839_b(IWorld var1, Random var2, MutableBoundingBox var3) {
         if (this.func_175818_a(☃, 22, 5, 35, 17)) {
            this.func_209179_a(☃, ☃, 25, 0, 0, 32, 8, 20);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, 24, 2, 5 + ☃ * 4, 24, 4, 5 + ☃ * 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 22, 4, 5 + ☃ * 4, 23, 4, 5 + ☃ * 4, field_175826_b, field_175826_b, false);
               this.func_175811_a(☃, field_175826_b, 25, 5, 5 + ☃ * 4, ☃);
               this.func_175811_a(☃, field_175826_b, 26, 6, 5 + ☃ * 4, ☃);
               this.func_175811_a(☃, field_175825_e, 26, 5, 5 + ☃ * 4, ☃);
               this.func_175804_a(☃, ☃, 33, 2, 5 + ☃ * 4, 33, 4, 5 + ☃ * 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 34, 4, 5 + ☃ * 4, 35, 4, 5 + ☃ * 4, field_175826_b, field_175826_b, false);
               this.func_175811_a(☃, field_175826_b, 32, 5, 5 + ☃ * 4, ☃);
               this.func_175811_a(☃, field_175826_b, 31, 6, 5 + ☃ * 4, ☃);
               this.func_175811_a(☃, field_175825_e, 31, 5, 5 + ☃ * 4, ☃);
               this.func_175804_a(☃, ☃, 27, 6, 5 + ☃ * 4, 30, 6, 5 + ☃ * 4, field_175828_a, field_175828_a, false);
            }
         }
      }

      private void func_175837_c(IWorld var1, Random var2, MutableBoundingBox var3) {
         if (this.func_175818_a(☃, 15, 20, 42, 21)) {
            this.func_175804_a(☃, ☃, 15, 0, 21, 42, 0, 21, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 26, 1, 21, 31, 3, 21);
            this.func_175804_a(☃, ☃, 21, 12, 21, 36, 12, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 17, 11, 21, 40, 11, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 16, 10, 21, 41, 10, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 15, 7, 21, 42, 9, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 16, 6, 21, 41, 6, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 17, 5, 21, 40, 5, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 21, 4, 21, 36, 4, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 22, 3, 21, 26, 3, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 31, 3, 21, 35, 3, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 23, 2, 21, 25, 2, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 32, 2, 21, 34, 2, 21, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 28, 4, 20, 29, 4, 21, field_175826_b, field_175826_b, false);
            this.func_175811_a(☃, field_175826_b, 27, 3, 21, ☃);
            this.func_175811_a(☃, field_175826_b, 30, 3, 21, ☃);
            this.func_175811_a(☃, field_175826_b, 26, 2, 21, ☃);
            this.func_175811_a(☃, field_175826_b, 31, 2, 21, ☃);
            this.func_175811_a(☃, field_175826_b, 25, 1, 21, ☃);
            this.func_175811_a(☃, field_175826_b, 32, 1, 21, ☃);

            for(int ☃ = 0; ☃ < 7; ++☃) {
               this.func_175811_a(☃, field_175827_c, 28 - ☃, 6 + ☃, 21, ☃);
               this.func_175811_a(☃, field_175827_c, 29 + ☃, 6 + ☃, 21, ☃);
            }

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175811_a(☃, field_175827_c, 28 - ☃, 9 + ☃, 21, ☃);
               this.func_175811_a(☃, field_175827_c, 29 + ☃, 9 + ☃, 21, ☃);
            }

            this.func_175811_a(☃, field_175827_c, 28, 12, 21, ☃);
            this.func_175811_a(☃, field_175827_c, 29, 12, 21, ☃);

            for(int ☃ = 0; ☃ < 3; ++☃) {
               this.func_175811_a(☃, field_175827_c, 22 - ☃ * 2, 8, 21, ☃);
               this.func_175811_a(☃, field_175827_c, 22 - ☃ * 2, 9, 21, ☃);
               this.func_175811_a(☃, field_175827_c, 35 + ☃ * 2, 8, 21, ☃);
               this.func_175811_a(☃, field_175827_c, 35 + ☃ * 2, 9, 21, ☃);
            }

            this.func_209179_a(☃, ☃, 15, 13, 21, 42, 15, 21);
            this.func_209179_a(☃, ☃, 15, 1, 21, 15, 6, 21);
            this.func_209179_a(☃, ☃, 16, 1, 21, 16, 5, 21);
            this.func_209179_a(☃, ☃, 17, 1, 21, 20, 4, 21);
            this.func_209179_a(☃, ☃, 21, 1, 21, 21, 3, 21);
            this.func_209179_a(☃, ☃, 22, 1, 21, 22, 2, 21);
            this.func_209179_a(☃, ☃, 23, 1, 21, 24, 1, 21);
            this.func_209179_a(☃, ☃, 42, 1, 21, 42, 6, 21);
            this.func_209179_a(☃, ☃, 41, 1, 21, 41, 5, 21);
            this.func_209179_a(☃, ☃, 37, 1, 21, 40, 4, 21);
            this.func_209179_a(☃, ☃, 36, 1, 21, 36, 3, 21);
            this.func_209179_a(☃, ☃, 33, 1, 21, 34, 1, 21);
            this.func_209179_a(☃, ☃, 35, 1, 21, 35, 2, 21);
         }
      }

      private void func_175841_d(IWorld var1, Random var2, MutableBoundingBox var3) {
         if (this.func_175818_a(☃, 21, 21, 36, 36)) {
            this.func_175804_a(☃, ☃, 21, 0, 22, 36, 0, 36, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 21, 1, 22, 36, 23, 36);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, 21 + ☃, 13 + ☃, 21 + ☃, 36 - ☃, 13 + ☃, 21 + ☃, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 21 + ☃, 13 + ☃, 36 - ☃, 36 - ☃, 13 + ☃, 36 - ☃, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 21 + ☃, 13 + ☃, 22 + ☃, 21 + ☃, 13 + ☃, 35 - ☃, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 36 - ☃, 13 + ☃, 22 + ☃, 36 - ☃, 13 + ☃, 35 - ☃, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(☃, ☃, 25, 16, 25, 32, 16, 32, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 25, 17, 25, 25, 19, 25, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 32, 17, 25, 32, 19, 25, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 25, 17, 32, 25, 19, 32, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 32, 17, 32, 32, 19, 32, field_175826_b, field_175826_b, false);
            this.func_175811_a(☃, field_175826_b, 26, 20, 26, ☃);
            this.func_175811_a(☃, field_175826_b, 27, 21, 27, ☃);
            this.func_175811_a(☃, field_175825_e, 27, 20, 27, ☃);
            this.func_175811_a(☃, field_175826_b, 26, 20, 31, ☃);
            this.func_175811_a(☃, field_175826_b, 27, 21, 30, ☃);
            this.func_175811_a(☃, field_175825_e, 27, 20, 30, ☃);
            this.func_175811_a(☃, field_175826_b, 31, 20, 31, ☃);
            this.func_175811_a(☃, field_175826_b, 30, 21, 30, ☃);
            this.func_175811_a(☃, field_175825_e, 30, 20, 30, ☃);
            this.func_175811_a(☃, field_175826_b, 31, 20, 26, ☃);
            this.func_175811_a(☃, field_175826_b, 30, 21, 27, ☃);
            this.func_175811_a(☃, field_175825_e, 30, 20, 27, ☃);
            this.func_175804_a(☃, ☃, 28, 21, 27, 29, 21, 27, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 27, 21, 28, 27, 21, 29, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 28, 21, 30, 29, 21, 30, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 30, 21, 28, 30, 21, 29, field_175828_a, field_175828_a, false);
         }
      }

      private void func_175835_e(IWorld var1, Random var2, MutableBoundingBox var3) {
         if (this.func_175818_a(☃, 0, 21, 6, 58)) {
            this.func_175804_a(☃, ☃, 0, 0, 21, 6, 0, 57, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 0, 1, 21, 6, 7, 57);
            this.func_175804_a(☃, ☃, 4, 4, 21, 6, 4, 53, field_175828_a, field_175828_a, false);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, ☃, ☃ + 1, 21, ☃, ☃ + 1, 57 - ☃, field_175826_b, field_175826_b, false);
            }

            for(int ☃ = 23; ☃ < 53; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, 5, 5, ☃, ☃);
            }

            this.func_175811_a(☃, field_175824_d, 5, 5, 52, ☃);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, ☃, ☃ + 1, 21, ☃, ☃ + 1, 57 - ☃, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(☃, ☃, 4, 1, 52, 6, 3, 52, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 5, 1, 51, 5, 3, 53, field_175828_a, field_175828_a, false);
         }

         if (this.func_175818_a(☃, 51, 21, 58, 58)) {
            this.func_175804_a(☃, ☃, 51, 0, 21, 57, 0, 57, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 51, 1, 21, 57, 7, 57);
            this.func_175804_a(☃, ☃, 51, 4, 21, 53, 4, 53, field_175828_a, field_175828_a, false);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, 57 - ☃, ☃ + 1, 21, 57 - ☃, ☃ + 1, 57 - ☃, field_175826_b, field_175826_b, false);
            }

            for(int ☃ = 23; ☃ < 53; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, 52, 5, ☃, ☃);
            }

            this.func_175811_a(☃, field_175824_d, 52, 5, 52, ☃);
            this.func_175804_a(☃, ☃, 51, 1, 52, 53, 3, 52, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 52, 1, 51, 52, 3, 53, field_175828_a, field_175828_a, false);
         }

         if (this.func_175818_a(☃, 0, 51, 57, 57)) {
            this.func_175804_a(☃, ☃, 7, 0, 51, 50, 0, 57, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 7, 1, 51, 50, 10, 57);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, ☃ + 1, ☃ + 1, 57 - ☃, 56 - ☃, ☃ + 1, 57 - ☃, field_175826_b, field_175826_b, false);
            }
         }
      }

      private void func_175842_f(IWorld var1, Random var2, MutableBoundingBox var3) {
         if (this.func_175818_a(☃, 7, 21, 13, 50)) {
            this.func_175804_a(☃, ☃, 7, 0, 21, 13, 0, 50, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 7, 1, 21, 13, 10, 50);
            this.func_175804_a(☃, ☃, 11, 8, 21, 13, 8, 53, field_175828_a, field_175828_a, false);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, ☃ + 7, ☃ + 5, 21, ☃ + 7, ☃ + 5, 54, field_175826_b, field_175826_b, false);
            }

            for(int ☃ = 21; ☃ <= 45; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, 12, 9, ☃, ☃);
            }
         }

         if (this.func_175818_a(☃, 44, 21, 50, 54)) {
            this.func_175804_a(☃, ☃, 44, 0, 21, 50, 0, 50, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 44, 1, 21, 50, 10, 50);
            this.func_175804_a(☃, ☃, 44, 8, 21, 46, 8, 53, field_175828_a, field_175828_a, false);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, 50 - ☃, ☃ + 5, 21, 50 - ☃, ☃ + 5, 54, field_175826_b, field_175826_b, false);
            }

            for(int ☃ = 21; ☃ <= 45; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, 45, 9, ☃, ☃);
            }
         }

         if (this.func_175818_a(☃, 8, 44, 49, 54)) {
            this.func_175804_a(☃, ☃, 14, 0, 44, 43, 0, 50, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 14, 1, 44, 43, 10, 50);

            for(int ☃ = 12; ☃ <= 45; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, ☃, 9, 45, ☃);
               this.func_175811_a(☃, field_175824_d, ☃, 9, 52, ☃);
               if (☃ == 12 || ☃ == 18 || ☃ == 24 || ☃ == 33 || ☃ == 39 || ☃ == 45) {
                  this.func_175811_a(☃, field_175824_d, ☃, 9, 47, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 9, 50, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 10, 45, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 10, 46, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 10, 51, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 10, 52, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 11, 47, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 11, 50, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 12, 48, ☃);
                  this.func_175811_a(☃, field_175824_d, ☃, 12, 49, ☃);
               }
            }

            for(int ☃ = 0; ☃ < 3; ++☃) {
               this.func_175804_a(☃, ☃, 8 + ☃, 5 + ☃, 54, 49 - ☃, 5 + ☃, 54, field_175828_a, field_175828_a, false);
            }

            this.func_175804_a(☃, ☃, 11, 8, 54, 46, 8, 54, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 14, 8, 44, 43, 8, 53, field_175828_a, field_175828_a, false);
         }
      }

      private void func_175838_g(IWorld var1, Random var2, MutableBoundingBox var3) {
         if (this.func_175818_a(☃, 14, 21, 20, 43)) {
            this.func_175804_a(☃, ☃, 14, 0, 21, 20, 0, 43, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 14, 1, 22, 20, 14, 43);
            this.func_175804_a(☃, ☃, 18, 12, 22, 20, 12, 39, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 18, 12, 21, 20, 12, 21, field_175826_b, field_175826_b, false);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, ☃ + 14, ☃ + 9, 21, ☃ + 14, ☃ + 9, 43 - ☃, field_175826_b, field_175826_b, false);
            }

            for(int ☃ = 23; ☃ <= 39; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, 19, 13, ☃, ☃);
            }
         }

         if (this.func_175818_a(☃, 37, 21, 43, 43)) {
            this.func_175804_a(☃, ☃, 37, 0, 21, 43, 0, 43, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 37, 1, 22, 43, 14, 43);
            this.func_175804_a(☃, ☃, 37, 12, 22, 39, 12, 39, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 37, 12, 21, 39, 12, 21, field_175826_b, field_175826_b, false);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, 43 - ☃, ☃ + 9, 21, 43 - ☃, ☃ + 9, 43 - ☃, field_175826_b, field_175826_b, false);
            }

            for(int ☃ = 23; ☃ <= 39; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, 38, 13, ☃, ☃);
            }
         }

         if (this.func_175818_a(☃, 15, 37, 42, 43)) {
            this.func_175804_a(☃, ☃, 21, 0, 37, 36, 0, 43, field_175828_a, field_175828_a, false);
            this.func_209179_a(☃, ☃, 21, 1, 37, 36, 14, 43);
            this.func_175804_a(☃, ☃, 21, 12, 37, 36, 12, 39, field_175828_a, field_175828_a, false);

            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, 15 + ☃, ☃ + 9, 43 - ☃, 42 - ☃, ☃ + 9, 43 - ☃, field_175826_b, field_175826_b, false);
            }

            for(int ☃ = 21; ☃ <= 36; ☃ += 3) {
               this.func_175811_a(☃, field_175824_d, ☃, 13, 38, ☃);
            }
         }
      }
   }

   public static class MonumentCoreRoom extends OceanMonumentPieces.Piece {
      public MonumentCoreRoom() {
      }

      public MonumentCoreRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 2, 2, 2);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175819_a(☃, ☃, 1, 8, 0, 14, 8, 14, field_175828_a);
         int ☃ = 7;
         IBlockState ☃x = field_175826_b;
         this.func_175804_a(☃, ☃, 0, 7, 0, 0, 7, 15, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 15, 7, 0, 15, 7, 15, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 1, 7, 0, 15, 7, 0, ☃x, ☃x, false);
         this.func_175804_a(☃, ☃, 1, 7, 15, 14, 7, 15, ☃x, ☃x, false);

         for(int ☃xx = 1; ☃xx <= 6; ++☃xx) {
            ☃x = field_175826_b;
            if (☃xx == 2 || ☃xx == 6) {
               ☃x = field_175828_a;
            }

            for(int ☃xxx = 0; ☃xxx <= 15; ☃xxx += 15) {
               this.func_175804_a(☃, ☃, ☃xxx, ☃xx, 0, ☃xxx, ☃xx, 1, ☃x, ☃x, false);
               this.func_175804_a(☃, ☃, ☃xxx, ☃xx, 6, ☃xxx, ☃xx, 9, ☃x, ☃x, false);
               this.func_175804_a(☃, ☃, ☃xxx, ☃xx, 14, ☃xxx, ☃xx, 15, ☃x, ☃x, false);
            }

            this.func_175804_a(☃, ☃, 1, ☃xx, 0, 1, ☃xx, 0, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 6, ☃xx, 0, 9, ☃xx, 0, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 14, ☃xx, 0, 14, ☃xx, 0, ☃x, ☃x, false);
            this.func_175804_a(☃, ☃, 1, ☃xx, 15, 14, ☃xx, 15, ☃x, ☃x, false);
         }

         this.func_175804_a(☃, ☃, 6, 3, 6, 9, 6, 9, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 7, 4, 7, 8, 5, 8, Blocks.field_150340_R.func_176223_P(), Blocks.field_150340_R.func_176223_P(), false);

         for(int ☃xx = 3; ☃xx <= 6; ☃xx += 3) {
            for(int ☃xxx = 6; ☃xxx <= 9; ☃xxx += 3) {
               this.func_175811_a(☃, field_175825_e, ☃xxx, ☃xx, 6, ☃);
               this.func_175811_a(☃, field_175825_e, ☃xxx, ☃xx, 9, ☃);
            }
         }

         this.func_175804_a(☃, ☃, 5, 1, 6, 5, 2, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 1, 9, 5, 2, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 1, 6, 10, 2, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 1, 9, 10, 2, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 1, 5, 6, 2, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 9, 1, 5, 9, 2, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, 1, 10, 6, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 9, 1, 10, 9, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 2, 5, 5, 6, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 2, 10, 5, 6, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 2, 5, 10, 6, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 2, 10, 10, 6, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 7, 1, 5, 7, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 7, 1, 10, 7, 6, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 5, 7, 9, 5, 7, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 10, 7, 9, 10, 7, 14, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 7, 5, 6, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 7, 10, 6, 7, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 9, 7, 5, 14, 7, 5, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 9, 7, 10, 14, 7, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 2, 1, 2, 2, 1, 3, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 3, 1, 2, 3, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 13, 1, 2, 13, 1, 3, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 12, 1, 2, 12, 1, 2, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 2, 1, 12, 2, 1, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 3, 1, 13, 3, 1, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 13, 1, 12, 13, 1, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 12, 1, 13, 12, 1, 13, field_175826_b, field_175826_b, false);
         return true;
      }
   }

   interface MonumentRoomFitHelper {
      boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1);

      OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3);
   }

   public static class Penthouse extends OceanMonumentPieces.Piece {
      public Penthouse() {
      }

      public Penthouse(EnumFacing var1, MutableBoundingBox var2) {
         super(☃, ☃);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         this.func_175804_a(☃, ☃, 2, -1, 2, 11, -1, 11, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, -1, 0, 1, -1, 11, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 12, -1, 0, 13, -1, 11, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 2, -1, 0, 11, -1, 1, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 2, -1, 12, 11, -1, 13, field_175828_a, field_175828_a, false);
         this.func_175804_a(☃, ☃, 0, 0, 0, 0, 0, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 13, 0, 0, 13, 0, 13, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 0, 0, 12, 0, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 0, 13, 12, 0, 13, field_175826_b, field_175826_b, false);

         for(int ☃ = 2; ☃ <= 11; ☃ += 3) {
            this.func_175811_a(☃, field_175825_e, 0, 0, ☃, ☃);
            this.func_175811_a(☃, field_175825_e, 13, 0, ☃, ☃);
            this.func_175811_a(☃, field_175825_e, ☃, 0, 0, ☃);
         }

         this.func_175804_a(☃, ☃, 2, 0, 3, 4, 0, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 9, 0, 3, 11, 0, 9, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 4, 0, 9, 9, 0, 11, field_175826_b, field_175826_b, false);
         this.func_175811_a(☃, field_175826_b, 5, 0, 8, ☃);
         this.func_175811_a(☃, field_175826_b, 8, 0, 8, ☃);
         this.func_175811_a(☃, field_175826_b, 10, 0, 10, ☃);
         this.func_175811_a(☃, field_175826_b, 3, 0, 10, ☃);
         this.func_175804_a(☃, ☃, 3, 0, 3, 3, 0, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 10, 0, 3, 10, 0, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 6, 0, 10, 7, 0, 10, field_175827_c, field_175827_c, false);
         int ☃ = 3;

         for(int ☃x = 0; ☃x < 2; ++☃x) {
            for(int ☃xx = 2; ☃xx <= 8; ☃xx += 3) {
               this.func_175804_a(☃, ☃, ☃, 0, ☃xx, ☃, 2, ☃xx, field_175826_b, field_175826_b, false);
            }

            ☃ = 10;
         }

         this.func_175804_a(☃, ☃, 5, 0, 10, 5, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 8, 0, 10, 8, 2, 10, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 6, -1, 7, 7, -1, 8, field_175827_c, field_175827_c, false);
         this.func_209179_a(☃, ☃, 6, -1, 3, 7, -1, 4);
         this.func_175817_a(☃, ☃, 6, 1, 6);
         return true;
      }
   }

   public abstract static class Piece extends StructurePiece {
      protected static final IBlockState field_175828_a = Blocks.field_180397_cI.func_176223_P();
      protected static final IBlockState field_175826_b = Blocks.field_196779_gQ.func_176223_P();
      protected static final IBlockState field_175827_c = Blocks.field_196781_gR.func_176223_P();
      protected static final IBlockState field_175824_d = field_175826_b;
      protected static final IBlockState field_175825_e = Blocks.field_180398_cJ.func_176223_P();
      protected static final IBlockState field_175822_f = Blocks.field_150355_j.func_176223_P();
      protected static final Set<Block> field_212180_g = ImmutableSet.<Block>builder()
         .add(Blocks.field_150432_aD)
         .add(Blocks.field_150403_cj)
         .add(Blocks.field_205164_gk)
         .add(field_175822_f.func_177230_c())
         .build();
      protected static final int field_175823_g = func_175820_a(2, 0, 0);
      protected static final int field_175831_h = func_175820_a(2, 2, 0);
      protected static final int field_175832_i = func_175820_a(0, 1, 0);
      protected static final int field_175829_j = func_175820_a(4, 1, 0);
      protected OceanMonumentPieces.RoomDefinition field_175830_k;

      protected static final int func_175820_a(int var0, int var1, int var2) {
         return ☃ * 25 + ☃ * 5 + ☃;
      }

      public Piece() {
         super(0);
      }

      public Piece(int var1) {
         super(☃);
      }

      public Piece(EnumFacing var1, MutableBoundingBox var2) {
         super(1);
         this.func_186164_a(☃);
         this.field_74887_e = ☃;
      }

      protected Piece(int var1, EnumFacing var2, OceanMonumentPieces.RoomDefinition var3, int var4, int var5, int var6) {
         super(☃);
         this.func_186164_a(☃);
         this.field_175830_k = ☃;
         int ☃ = ☃.field_175967_a;
         int ☃x = ☃ % 5;
         int ☃xx = ☃ / 5 % 5;
         int ☃xxx = ☃ / 25;
         if (☃ != EnumFacing.NORTH && ☃ != EnumFacing.SOUTH) {
            this.field_74887_e = new MutableBoundingBox(0, 0, 0, ☃ * 8 - 1, ☃ * 4 - 1, ☃ * 8 - 1);
         } else {
            this.field_74887_e = new MutableBoundingBox(0, 0, 0, ☃ * 8 - 1, ☃ * 4 - 1, ☃ * 8 - 1);
         }

         switch(☃) {
            case NORTH:
               this.field_74887_e.func_78886_a(☃x * 8, ☃xxx * 4, -(☃xx + ☃) * 8 + 1);
               break;
            case SOUTH:
               this.field_74887_e.func_78886_a(☃x * 8, ☃xxx * 4, ☃xx * 8);
               break;
            case WEST:
               this.field_74887_e.func_78886_a(-(☃xx + ☃) * 8 + 1, ☃xxx * 4, ☃x * 8);
               break;
            default:
               this.field_74887_e.func_78886_a(☃xx * 8, ☃xxx * 4, ☃x * 8);
         }
      }

      @Override
      protected void func_143012_a(NBTTagCompound var1) {
      }

      @Override
      protected void func_143011_b(NBTTagCompound var1, TemplateManager var2) {
      }

      protected void func_209179_a(IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         for(int ☃ = ☃; ☃ <= ☃; ++☃) {
            for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
               for(int ☃xx = ☃; ☃xx <= ☃; ++☃xx) {
                  IBlockState ☃xxx = this.func_175807_a(☃, ☃x, ☃, ☃xx, ☃);
                  if (!field_212180_g.contains(☃xxx.func_177230_c())) {
                     if (this.func_74862_a(☃) >= ☃.func_181545_F() && ☃xxx != field_175822_f) {
                        this.func_175811_a(☃, Blocks.field_150350_a.func_176223_P(), ☃x, ☃, ☃xx, ☃);
                     } else {
                        this.func_175811_a(☃, field_175822_f, ☃x, ☃, ☃xx, ☃);
                     }
                  }
               }
            }
         }
      }

      protected void func_175821_a(IWorld var1, MutableBoundingBox var2, int var3, int var4, boolean var5) {
         if (☃) {
            this.func_175804_a(☃, ☃, ☃ + 0, 0, ☃ + 0, ☃ + 2, 0, ☃ + 8 - 1, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 5, 0, ☃ + 0, ☃ + 8 - 1, 0, ☃ + 8 - 1, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 3, 0, ☃ + 0, ☃ + 4, 0, ☃ + 2, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 3, 0, ☃ + 5, ☃ + 4, 0, ☃ + 8 - 1, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, ☃ + 3, 0, ☃ + 2, ☃ + 4, 0, ☃ + 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, ☃ + 3, 0, ☃ + 5, ☃ + 4, 0, ☃ + 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, ☃ + 2, 0, ☃ + 3, ☃ + 2, 0, ☃ + 4, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, ☃ + 5, 0, ☃ + 3, ☃ + 5, 0, ☃ + 4, field_175826_b, field_175826_b, false);
         } else {
            this.func_175804_a(☃, ☃, ☃ + 0, 0, ☃ + 0, ☃ + 8 - 1, 0, ☃ + 8 - 1, field_175828_a, field_175828_a, false);
         }
      }

      protected void func_175819_a(IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9) {
         for(int ☃ = ☃; ☃ <= ☃; ++☃) {
            for(int ☃x = ☃; ☃x <= ☃; ++☃x) {
               for(int ☃xx = ☃; ☃xx <= ☃; ++☃xx) {
                  if (this.func_175807_a(☃, ☃x, ☃, ☃xx, ☃) == field_175822_f) {
                     this.func_175811_a(☃, ☃, ☃x, ☃, ☃xx, ☃);
                  }
               }
            }
         }
      }

      protected boolean func_175818_a(MutableBoundingBox var1, int var2, int var3, int var4, int var5) {
         int ☃ = this.func_74865_a(☃, ☃);
         int ☃x = this.func_74873_b(☃, ☃);
         int ☃xx = this.func_74865_a(☃, ☃);
         int ☃xxx = this.func_74873_b(☃, ☃);
         return ☃.func_78885_a(Math.min(☃, ☃xx), Math.min(☃x, ☃xxx), Math.max(☃, ☃xx), Math.max(☃x, ☃xxx));
      }

      protected boolean func_175817_a(IWorld var1, MutableBoundingBox var2, int var3, int var4, int var5) {
         int ☃ = this.func_74865_a(☃, ☃);
         int ☃x = this.func_74862_a(☃);
         int ☃xx = this.func_74873_b(☃, ☃);
         if (☃.func_175898_b(new BlockPos(☃, ☃x, ☃xx))) {
            EntityElderGuardian ☃xxx = new EntityElderGuardian(☃.func_201672_e());
            ☃xxx.func_70691_i(☃xxx.func_110138_aP());
            ☃xxx.func_70012_b((double)☃ + 0.5, (double)☃x, (double)☃xx + 0.5, 0.0F, 0.0F);
            ☃xxx.func_204210_a(☃.func_175649_E(new BlockPos(☃xxx)), null, null);
            ☃.func_72838_d(☃xxx);
            return true;
         } else {
            return false;
         }
      }
   }

   static class RoomDefinition {
      private final int field_175967_a;
      private final OceanMonumentPieces.RoomDefinition[] field_175965_b = new OceanMonumentPieces.RoomDefinition[6];
      private final boolean[] field_175966_c = new boolean[6];
      private boolean field_175963_d;
      private boolean field_175964_e;
      private int field_175962_f;

      public RoomDefinition(int var1) {
         this.field_175967_a = ☃;
      }

      public void func_175957_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2) {
         this.field_175965_b[☃.func_176745_a()] = ☃;
         ☃.field_175965_b[☃.func_176734_d().func_176745_a()] = this;
      }

      public void func_175958_a() {
         for(int ☃ = 0; ☃ < 6; ++☃) {
            this.field_175966_c[☃] = this.field_175965_b[☃] != null;
         }
      }

      public boolean func_175959_a(int var1) {
         if (this.field_175964_e) {
            return true;
         } else {
            this.field_175962_f = ☃;

            for(int ☃ = 0; ☃ < 6; ++☃) {
               if (this.field_175965_b[☃] != null
                  && this.field_175966_c[☃]
                  && this.field_175965_b[☃].field_175962_f != ☃
                  && this.field_175965_b[☃].func_175959_a(☃)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean func_175961_b() {
         return this.field_175967_a >= 75;
      }

      public int func_175960_c() {
         int ☃ = 0;

         for(int ☃x = 0; ☃x < 6; ++☃x) {
            if (this.field_175966_c[☃x]) {
               ++☃;
            }
         }

         return ☃;
      }
   }

   public static class SimpleRoom extends OceanMonumentPieces.Piece {
      private int field_175833_o;

      public SimpleRoom() {
      }

      public SimpleRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 1, 1, 1);
         this.field_175833_o = ☃.nextInt(3);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(☃, ☃, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
         }

         if (this.field_175830_k.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 4, 1, 6, 4, 6, field_175828_a);
         }

         boolean ☃ = this.field_175833_o != 0
            && ☃.nextBoolean()
            && !this.field_175830_k.field_175966_c[EnumFacing.DOWN.func_176745_a()]
            && !this.field_175830_k.field_175966_c[EnumFacing.UP.func_176745_a()]
            && this.field_175830_k.func_175960_c() > 1;
         if (this.field_175833_o == 0) {
            this.func_175804_a(☃, ☃, 0, 1, 0, 2, 1, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 3, 0, 2, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 2, 0, 0, 2, 2, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 1, 2, 0, 2, 2, 0, field_175828_a, field_175828_a, false);
            this.func_175811_a(☃, field_175825_e, 1, 2, 1, ☃);
            this.func_175804_a(☃, ☃, 5, 1, 0, 7, 1, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 5, 3, 0, 7, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 2, 0, 7, 2, 2, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 5, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
            this.func_175811_a(☃, field_175825_e, 6, 2, 1, ☃);
            this.func_175804_a(☃, ☃, 0, 1, 5, 2, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 3, 5, 2, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 2, 5, 0, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 1, 2, 7, 2, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175811_a(☃, field_175825_e, 1, 2, 6, ☃);
            this.func_175804_a(☃, ☃, 5, 1, 5, 7, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 5, 3, 5, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 2, 5, 7, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 5, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
            this.func_175811_a(☃, field_175825_e, 6, 2, 6, ☃);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 3, 3, 0, 4, 3, 0, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, 3, 3, 0, 4, 3, 1, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 3, 2, 0, 4, 2, 0, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 3, 1, 0, 4, 1, 1, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 3, 3, 7, 4, 3, 7, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, 3, 3, 6, 4, 3, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 3, 2, 7, 4, 2, 7, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 3, 1, 6, 4, 1, 7, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 0, 3, 3, 0, 3, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, 0, 3, 3, 1, 3, 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 0, 2, 3, 0, 2, 4, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 0, 1, 3, 1, 1, 4, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 7, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
            } else {
               this.func_175804_a(☃, ☃, 6, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 7, 2, 3, 7, 2, 4, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 6, 1, 3, 7, 1, 4, field_175826_b, field_175826_b, false);
            }
         } else if (this.field_175833_o == 1) {
            this.func_175804_a(☃, ☃, 2, 1, 2, 2, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 2, 1, 5, 2, 3, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 5, 1, 5, 5, 3, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 5, 1, 2, 5, 3, 2, field_175826_b, field_175826_b, false);
            this.func_175811_a(☃, field_175825_e, 2, 2, 2, ☃);
            this.func_175811_a(☃, field_175825_e, 2, 2, 5, ☃);
            this.func_175811_a(☃, field_175825_e, 5, 2, 5, ☃);
            this.func_175811_a(☃, field_175825_e, 5, 2, 2, ☃);
            this.func_175804_a(☃, ☃, 0, 1, 0, 1, 3, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 1, 1, 0, 3, 1, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 1, 7, 1, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 1, 6, 0, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 6, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 1, 6, 7, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 6, 1, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 1, 1, 7, 3, 1, field_175826_b, field_175826_b, false);
            this.func_175811_a(☃, field_175828_a, 1, 2, 0, ☃);
            this.func_175811_a(☃, field_175828_a, 0, 2, 1, ☃);
            this.func_175811_a(☃, field_175828_a, 1, 2, 7, ☃);
            this.func_175811_a(☃, field_175828_a, 0, 2, 6, ☃);
            this.func_175811_a(☃, field_175828_a, 6, 2, 7, ☃);
            this.func_175811_a(☃, field_175828_a, 7, 2, 6, ☃);
            this.func_175811_a(☃, field_175828_a, 6, 2, 0, ☃);
            this.func_175811_a(☃, field_175828_a, 7, 2, 1, ☃);
            if (!this.field_175830_k.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 1, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 1, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 0, 3, 1, 0, 3, 6, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 0, 2, 1, 0, 2, 6, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 0, 1, 1, 0, 1, 6, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
               this.func_175804_a(☃, ☃, 7, 3, 1, 7, 3, 6, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, 7, 2, 1, 7, 2, 6, field_175828_a, field_175828_a, false);
               this.func_175804_a(☃, ☃, 7, 1, 1, 7, 1, 6, field_175826_b, field_175826_b, false);
            }
         } else if (this.field_175833_o == 2) {
            this.func_175804_a(☃, ☃, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
               this.func_209179_a(☃, ☃, 3, 1, 0, 4, 2, 0);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.func_176745_a()]) {
               this.func_209179_a(☃, ☃, 3, 1, 7, 4, 2, 7);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.func_176745_a()]) {
               this.func_209179_a(☃, ☃, 0, 1, 3, 0, 2, 4);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.func_176745_a()]) {
               this.func_209179_a(☃, ☃, 7, 1, 3, 7, 2, 4);
            }
         }

         if (☃) {
            this.func_175804_a(☃, ☃, 3, 1, 3, 4, 1, 4, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 3, 2, 3, 4, 2, 4, field_175828_a, field_175828_a, false);
            this.func_175804_a(☃, ☃, 3, 3, 3, 4, 3, 4, field_175826_b, field_175826_b, false);
         }

         return true;
      }
   }

   public static class SimpleTopRoom extends OceanMonumentPieces.Piece {
      public SimpleTopRoom() {
      }

      public SimpleTopRoom(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         super(1, ☃, ☃, 1, 1, 1);
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(☃, ☃, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.func_176745_a()]);
         }

         if (this.field_175830_k.field_175965_b[EnumFacing.UP.func_176745_a()] == null) {
            this.func_175819_a(☃, ☃, 1, 4, 1, 6, 4, 6, field_175828_a);
         }

         for(int ☃ = 1; ☃ <= 6; ++☃) {
            for(int ☃x = 1; ☃x <= 6; ++☃x) {
               if (☃.nextInt(3) != 0) {
                  int ☃xx = 2 + (☃.nextInt(4) == 0 ? 0 : 1);
                  IBlockState ☃xxx = Blocks.field_196577_ad.func_176223_P();
                  this.func_175804_a(☃, ☃, ☃, ☃xx, ☃x, ☃, 3, ☃x, ☃xxx, ☃xxx, false);
               }
            }
         }

         this.func_175804_a(☃, ☃, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
         this.func_175804_a(☃, ☃, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
         this.func_175804_a(☃, ☃, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
         if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.func_176745_a()]) {
            this.func_209179_a(☃, ☃, 3, 1, 0, 4, 2, 0);
         }

         return true;
      }
   }

   public static class WingRoom extends OceanMonumentPieces.Piece {
      private int field_175834_o;

      public WingRoom() {
      }

      public WingRoom(EnumFacing var1, MutableBoundingBox var2, int var3) {
         super(☃, ☃);
         this.field_175834_o = ☃ & 1;
      }

      @Override
      public boolean func_74875_a(IWorld var1, Random var2, MutableBoundingBox var3, ChunkPos var4) {
         if (this.field_175834_o == 0) {
            for(int ☃ = 0; ☃ < 4; ++☃) {
               this.func_175804_a(☃, ☃, 10 - ☃, 3 - ☃, 20 - ☃, 12 + ☃, 3 - ☃, 20, field_175826_b, field_175826_b, false);
            }

            this.func_175804_a(☃, ☃, 7, 0, 6, 15, 0, 16, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 6, 0, 6, 6, 3, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 16, 0, 6, 16, 3, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 1, 7, 7, 1, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 15, 1, 7, 15, 1, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 7, 1, 6, 9, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 13, 1, 6, 15, 3, 6, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 8, 1, 7, 9, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 13, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 9, 0, 5, 13, 0, 5, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 10, 0, 7, 12, 0, 7, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 8, 0, 10, 8, 0, 12, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 14, 0, 10, 14, 0, 12, field_175827_c, field_175827_c, false);

            for(int ☃ = 18; ☃ >= 7; ☃ -= 3) {
               this.func_175811_a(☃, field_175825_e, 6, 3, ☃, ☃);
               this.func_175811_a(☃, field_175825_e, 16, 3, ☃, ☃);
            }

            this.func_175811_a(☃, field_175825_e, 10, 0, 10, ☃);
            this.func_175811_a(☃, field_175825_e, 12, 0, 10, ☃);
            this.func_175811_a(☃, field_175825_e, 10, 0, 12, ☃);
            this.func_175811_a(☃, field_175825_e, 12, 0, 12, ☃);
            this.func_175811_a(☃, field_175825_e, 8, 3, 6, ☃);
            this.func_175811_a(☃, field_175825_e, 14, 3, 6, ☃);
            this.func_175811_a(☃, field_175826_b, 4, 2, 4, ☃);
            this.func_175811_a(☃, field_175825_e, 4, 1, 4, ☃);
            this.func_175811_a(☃, field_175826_b, 4, 0, 4, ☃);
            this.func_175811_a(☃, field_175826_b, 18, 2, 4, ☃);
            this.func_175811_a(☃, field_175825_e, 18, 1, 4, ☃);
            this.func_175811_a(☃, field_175826_b, 18, 0, 4, ☃);
            this.func_175811_a(☃, field_175826_b, 4, 2, 18, ☃);
            this.func_175811_a(☃, field_175825_e, 4, 1, 18, ☃);
            this.func_175811_a(☃, field_175826_b, 4, 0, 18, ☃);
            this.func_175811_a(☃, field_175826_b, 18, 2, 18, ☃);
            this.func_175811_a(☃, field_175825_e, 18, 1, 18, ☃);
            this.func_175811_a(☃, field_175826_b, 18, 0, 18, ☃);
            this.func_175811_a(☃, field_175826_b, 9, 7, 20, ☃);
            this.func_175811_a(☃, field_175826_b, 13, 7, 20, ☃);
            this.func_175804_a(☃, ☃, 6, 0, 21, 7, 4, 21, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 15, 0, 21, 16, 4, 21, field_175826_b, field_175826_b, false);
            this.func_175817_a(☃, ☃, 11, 2, 16);
         } else if (this.field_175834_o == 1) {
            this.func_175804_a(☃, ☃, 9, 3, 18, 13, 3, 20, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 9, 0, 18, 9, 2, 18, field_175826_b, field_175826_b, false);
            this.func_175804_a(☃, ☃, 13, 0, 18, 13, 2, 18, field_175826_b, field_175826_b, false);
            int ☃ = 9;
            int ☃x = 20;
            int ☃xx = 5;

            for(int ☃xxx = 0; ☃xxx < 2; ++☃xxx) {
               this.func_175811_a(☃, field_175826_b, ☃, 6, 20, ☃);
               this.func_175811_a(☃, field_175825_e, ☃, 5, 20, ☃);
               this.func_175811_a(☃, field_175826_b, ☃, 4, 20, ☃);
               ☃ = 13;
            }

            this.func_175804_a(☃, ☃, 7, 3, 7, 15, 3, 14, field_175826_b, field_175826_b, false);
            int var11 = 10;

            for(int ☃xxx = 0; ☃xxx < 2; ++☃xxx) {
               this.func_175804_a(☃, ☃, var11, 0, 10, var11, 6, 10, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, var11, 0, 12, var11, 6, 12, field_175826_b, field_175826_b, false);
               this.func_175811_a(☃, field_175825_e, var11, 0, 10, ☃);
               this.func_175811_a(☃, field_175825_e, var11, 0, 12, ☃);
               this.func_175811_a(☃, field_175825_e, var11, 4, 10, ☃);
               this.func_175811_a(☃, field_175825_e, var11, 4, 12, ☃);
               var11 = 12;
            }

            var11 = 8;

            for(int ☃xxx = 0; ☃xxx < 2; ++☃xxx) {
               this.func_175804_a(☃, ☃, var11, 0, 7, var11, 2, 7, field_175826_b, field_175826_b, false);
               this.func_175804_a(☃, ☃, var11, 0, 14, var11, 2, 14, field_175826_b, field_175826_b, false);
               var11 = 14;
            }

            this.func_175804_a(☃, ☃, 8, 3, 8, 8, 3, 13, field_175827_c, field_175827_c, false);
            this.func_175804_a(☃, ☃, 14, 3, 8, 14, 3, 13, field_175827_c, field_175827_c, false);
            this.func_175817_a(☃, ☃, 11, 5, 13);
         }

         return true;
      }
   }

   static class XDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private XDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1) {
         return ☃.field_175966_c[EnumFacing.EAST.func_176745_a()] && !☃.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175963_d;
      }

      @Override
      public OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         ☃.field_175963_d = true;
         ☃.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175963_d = true;
         return new OceanMonumentPieces.DoubleXRoom(☃, ☃, ☃);
      }
   }

   static class XYDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private XYDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1) {
         if (☃.field_175966_c[EnumFacing.EAST.func_176745_a()]
            && !☃.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175963_d
            && ☃.field_175966_c[EnumFacing.UP.func_176745_a()]
            && !☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d) {
            OceanMonumentPieces.RoomDefinition ☃ = ☃.field_175965_b[EnumFacing.EAST.func_176745_a()];
            return ☃.field_175966_c[EnumFacing.UP.func_176745_a()] && !☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         ☃.field_175963_d = true;
         ☃.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175963_d = true;
         ☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         ☃.field_175965_b[EnumFacing.EAST.func_176745_a()].field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         return new OceanMonumentPieces.DoubleXYRoom(☃, ☃, ☃);
      }
   }

   static class YDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private YDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1) {
         return ☃.field_175966_c[EnumFacing.UP.func_176745_a()] && !☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d;
      }

      @Override
      public OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         ☃.field_175963_d = true;
         ☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         return new OceanMonumentPieces.DoubleYRoom(☃, ☃, ☃);
      }
   }

   static class YZDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private YZDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1) {
         if (☃.field_175966_c[EnumFacing.NORTH.func_176745_a()]
            && !☃.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175963_d
            && ☃.field_175966_c[EnumFacing.UP.func_176745_a()]
            && !☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d) {
            OceanMonumentPieces.RoomDefinition ☃ = ☃.field_175965_b[EnumFacing.NORTH.func_176745_a()];
            return ☃.field_175966_c[EnumFacing.UP.func_176745_a()] && !☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         ☃.field_175963_d = true;
         ☃.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175963_d = true;
         ☃.field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         ☃.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175965_b[EnumFacing.UP.func_176745_a()].field_175963_d = true;
         return new OceanMonumentPieces.DoubleYZRoom(☃, ☃, ☃);
      }
   }

   static class ZDoubleRoomFitHelper implements OceanMonumentPieces.MonumentRoomFitHelper {
      private ZDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(OceanMonumentPieces.RoomDefinition var1) {
         return ☃.field_175966_c[EnumFacing.NORTH.func_176745_a()] && !☃.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175963_d;
      }

      @Override
      public OceanMonumentPieces.Piece func_175968_a(EnumFacing var1, OceanMonumentPieces.RoomDefinition var2, Random var3) {
         OceanMonumentPieces.RoomDefinition ☃ = ☃;
         if (!☃.field_175966_c[EnumFacing.NORTH.func_176745_a()] || ☃.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175963_d) {
            ☃ = ☃.field_175965_b[EnumFacing.SOUTH.func_176745_a()];
         }

         ☃.field_175963_d = true;
         ☃.field_175965_b[EnumFacing.NORTH.func_176745_a()].field_175963_d = true;
         return new OceanMonumentPieces.DoubleZRoom(☃, ☃, ☃);
      }
   }
}
