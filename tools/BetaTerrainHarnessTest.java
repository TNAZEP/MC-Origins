import java.util.Arrays;
import java.util.Random;

/** Check that observing RNG calls does not change Java Random results. */
public final class BetaTerrainHarnessTest {
    public static void main(String[] args) throws Exception {
        long[] seeds = {0, 1, -1, 8675309, Long.MIN_VALUE, Long.MAX_VALUE};
        for (long seed : seeds) {
            Random expected = new Random(seed);
            BetaTerrainHarness.TracedRandom actual = new BetaTerrainHarness.TracedRandom(seed);
            for (int i = 0; i < 10000; i++) {
                if (expected.nextInt(1073741825) != actual.nextInt(1073741825)
                    || expected.nextInt(4) != actual.nextInt(4)
                    || expected.nextLong() != actual.nextLong()
                    || expected.nextDouble() != actual.nextDouble()
                    || expected.nextGaussian() != actual.nextGaussian()) {
                    throw new AssertionError("RNG changed for seed " + seed + " iteration " + i);
                }
                byte[] a = new byte[7], b = new byte[7];
                expected.nextBytes(a);
                actual.nextBytes(b);
                if (!Arrays.equals(a, b)) throw new AssertionError("nextBytes changed");
            }
            if (actual.calls == 0) throw new AssertionError("No trace recorded");
        }
        System.out.println("PASS: traced Random matches java.util.Random for six seeds, 60000 mixed iterations");
    }
}
