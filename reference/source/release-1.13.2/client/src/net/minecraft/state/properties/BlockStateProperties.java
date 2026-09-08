package net.minecraft.state.properties;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.EnumFacing;

public class BlockStateProperties {
   public static final BooleanProperty field_208174_a = BooleanProperty.func_177716_a("attached");
   public static final BooleanProperty field_208176_c = BooleanProperty.func_177716_a("conditional");
   public static final BooleanProperty field_208178_e = BooleanProperty.func_177716_a("disarmed");
   public static final BooleanProperty field_208179_f = BooleanProperty.func_177716_a("drag");
   public static final BooleanProperty field_208180_g = BooleanProperty.func_177716_a("enabled");
   public static final BooleanProperty field_208181_h = BooleanProperty.func_177716_a("extended");
   public static final BooleanProperty field_208182_i = BooleanProperty.func_177716_a("eye");
   public static final BooleanProperty field_208183_j = BooleanProperty.func_177716_a("falling");
   public static final BooleanProperty field_208184_k = BooleanProperty.func_177716_a("has_bottle_0");
   public static final BooleanProperty field_208185_l = BooleanProperty.func_177716_a("has_bottle_1");
   public static final BooleanProperty field_208186_m = BooleanProperty.func_177716_a("has_bottle_2");
   public static final BooleanProperty field_208187_n = BooleanProperty.func_177716_a("has_record");
   public static final BooleanProperty field_208188_o = BooleanProperty.func_177716_a("inverted");
   public static final BooleanProperty field_208189_p = BooleanProperty.func_177716_a("in_wall");
   public static final BooleanProperty field_208190_q = BooleanProperty.func_177716_a("lit");
   public static final BooleanProperty field_208191_r = BooleanProperty.func_177716_a("locked");
   public static final BooleanProperty field_208192_s = BooleanProperty.func_177716_a("occupied");
   public static final BooleanProperty field_208193_t = BooleanProperty.func_177716_a("open");
   public static final BooleanProperty field_208515_s = BooleanProperty.func_177716_a("persistent");
   public static final BooleanProperty field_208194_u = BooleanProperty.func_177716_a("powered");
   public static final BooleanProperty field_208195_v = BooleanProperty.func_177716_a("short");
   public static final BooleanProperty field_208196_w = BooleanProperty.func_177716_a("snowy");
   public static final BooleanProperty field_208197_x = BooleanProperty.func_177716_a("triggered");
   public static final BooleanProperty field_212646_x = BooleanProperty.func_177716_a("unstable");
   public static final BooleanProperty field_208198_y = BooleanProperty.func_177716_a("waterlogged");
   public static final EnumProperty<EnumFacing.Axis> field_208199_z = EnumProperty.func_177706_a(
      "axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z
   );
   public static final EnumProperty<EnumFacing.Axis> field_208148_A = EnumProperty.func_177709_a("axis", EnumFacing.Axis.class);
   public static final BooleanProperty field_208149_B = BooleanProperty.func_177716_a("up");
   public static final BooleanProperty field_208150_C = BooleanProperty.func_177716_a("down");
   public static final BooleanProperty field_208151_D = BooleanProperty.func_177716_a("north");
   public static final BooleanProperty field_208152_E = BooleanProperty.func_177716_a("east");
   public static final BooleanProperty field_208153_F = BooleanProperty.func_177716_a("south");
   public static final BooleanProperty field_208154_G = BooleanProperty.func_177716_a("west");
   public static final DirectionProperty field_208155_H = DirectionProperty.func_196962_a(
      "facing", EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST, EnumFacing.UP, EnumFacing.DOWN
   );
   public static final DirectionProperty field_208156_I = DirectionProperty.func_177712_a("facing", var0 -> var0 != EnumFacing.UP);
   public static final DirectionProperty field_208157_J = DirectionProperty.func_177712_a("facing", EnumFacing.Plane.HORIZONTAL);
   public static final EnumProperty<AttachFace> field_208158_K = EnumProperty.func_177709_a("face", AttachFace.class);
   public static final EnumProperty<RedstoneSide> field_208159_L = EnumProperty.func_177709_a("east", RedstoneSide.class);
   public static final EnumProperty<RedstoneSide> field_208160_M = EnumProperty.func_177709_a("north", RedstoneSide.class);
   public static final EnumProperty<RedstoneSide> field_208161_N = EnumProperty.func_177709_a("south", RedstoneSide.class);
   public static final EnumProperty<RedstoneSide> field_208162_O = EnumProperty.func_177709_a("west", RedstoneSide.class);
   public static final EnumProperty<DoubleBlockHalf> field_208163_P = EnumProperty.func_177709_a("half", DoubleBlockHalf.class);
   public static final EnumProperty<Half> field_208164_Q = EnumProperty.func_177709_a("half", Half.class);
   public static final EnumProperty<RailShape> field_208165_R = EnumProperty.func_177709_a("shape", RailShape.class);
   public static final EnumProperty<RailShape> field_208166_S = EnumProperty.func_177708_a(
      "shape",
      RailShape.class,
      var0 -> var0 != RailShape.NORTH_EAST && var0 != RailShape.NORTH_WEST && var0 != RailShape.SOUTH_EAST && var0 != RailShape.SOUTH_WEST
   );
   public static final IntegerProperty field_208167_T = IntegerProperty.func_177719_a("age", 0, 2);
   public static final IntegerProperty field_208168_U = IntegerProperty.func_177719_a("age", 0, 3);
   public static final IntegerProperty field_208169_V = IntegerProperty.func_177719_a("age", 0, 5);
   public static final IntegerProperty field_208170_W = IntegerProperty.func_177719_a("age", 0, 7);
   public static final IntegerProperty field_208171_X = IntegerProperty.func_177719_a("age", 0, 15);
   public static final IntegerProperty field_208172_Y = IntegerProperty.func_177719_a("age", 0, 25);
   public static final IntegerProperty field_208173_Z = IntegerProperty.func_177719_a("bites", 0, 6);
   public static final IntegerProperty field_208126_aa = IntegerProperty.func_177719_a("delay", 1, 4);
   public static final IntegerProperty field_208514_aa = IntegerProperty.func_177719_a("distance", 1, 7);
   public static final IntegerProperty field_208127_ab = IntegerProperty.func_177719_a("eggs", 1, 4);
   public static final IntegerProperty field_208128_ac = IntegerProperty.func_177719_a("hatch", 0, 2);
   public static final IntegerProperty field_208129_ad = IntegerProperty.func_177719_a("layers", 1, 8);
   public static final IntegerProperty field_208130_ae = IntegerProperty.func_177719_a("level", 0, 3);
   public static final IntegerProperty field_208131_af = IntegerProperty.func_177719_a("level", 1, 8);
   public static final IntegerProperty field_208132_ag = IntegerProperty.func_177719_a("level", 0, 15);
   public static final IntegerProperty field_208133_ah = IntegerProperty.func_177719_a("moisture", 0, 7);
   public static final IntegerProperty field_208134_ai = IntegerProperty.func_177719_a("note", 0, 24);
   public static final IntegerProperty field_208135_aj = IntegerProperty.func_177719_a("pickles", 1, 4);
   public static final IntegerProperty field_208136_ak = IntegerProperty.func_177719_a("power", 0, 15);
   public static final IntegerProperty field_208137_al = IntegerProperty.func_177719_a("stage", 0, 1);
   public static final IntegerProperty field_208138_am = IntegerProperty.func_177719_a("rotation", 0, 15);
   public static final EnumProperty<BedPart> field_208139_an = EnumProperty.func_177709_a("part", BedPart.class);
   public static final EnumProperty<ChestType> field_208140_ao = EnumProperty.func_177709_a("type", ChestType.class);
   public static final EnumProperty<ComparatorMode> field_208141_ap = EnumProperty.func_177709_a("mode", ComparatorMode.class);
   public static final EnumProperty<DoorHingeSide> field_208142_aq = EnumProperty.func_177709_a("hinge", DoorHingeSide.class);
   public static final EnumProperty<NoteBlockInstrument> field_208143_ar = EnumProperty.func_177709_a("instrument", NoteBlockInstrument.class);
   public static final EnumProperty<PistonType> field_208144_as = EnumProperty.func_177709_a("type", PistonType.class);
   public static final EnumProperty<SlabType> field_208145_at = EnumProperty.func_177709_a("type", SlabType.class);
   public static final EnumProperty<StairsShape> field_208146_au = EnumProperty.func_177709_a("shape", StairsShape.class);
   public static final EnumProperty<StructureMode> field_208147_av = EnumProperty.func_177709_a("mode", StructureMode.class);
}
