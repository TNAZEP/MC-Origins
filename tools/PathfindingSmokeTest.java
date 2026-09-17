import net.minecraft.src.*;

/** A blocked direct route must still reach its target, including on a reused search. */
public final class PathfindingSmokeTest {
    public static void main(String[] args) {
        Pathfinder search = new Pathfinder() {
            protected int getVerticalOffset(int x, int y, int z, PathPoint size) {
                for (int dx = x; dx < x + size.xCoord; dx++)
                    for (int dy = y; dy < y + size.yCoord; dy++)
                        for (int dz = z; dz < z + size.zCoord; dz++)
                            if (dy < 1 || (dx == 2 && dz == 0 && dy < 4)) return 0;
                return 1;
            }
        };
        for (int repeat = 0; repeat < 2; repeat++) {
            PathEntity path = search.createPath(0, 1, 0, 0.6F, 1.8F, 4.5D, 1, 0.5D, 16);
            if (path == null || path.func_22328_c().xCoord != 4) throw new AssertionError("unreachable target");
            boolean detour = false;
            while (!path.isFinished()) {
                Vec3D point = path.getPosition(0.6F);
                if (point.yCoord != 1 || (point.xCoord == 2.5D && point.zCoord == 0.5D))
                    throw new AssertionError("crossed wall or floor");
                detour |= point.zCoord != 0.5D;
                path.incrementPathIndex();
            }
            if (!detour) throw new AssertionError("missing detour");
        }
        System.out.println("PASS: shared path search routes around an obstacle and supports reuse");
    }
}
