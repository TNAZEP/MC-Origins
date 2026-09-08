package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;

public class GameTestRegistry {
   private static final Collection<TestFunction> TEST_FUNCTIONS = Lists.<TestFunction>newArrayList();
   private static final Set<String> TEST_CLASS_NAMES = Sets.newHashSet();
   private static final Map<String, Consumer<ServerLevel>> BEFORE_BATCH_FUNCTIONS = Maps.newHashMap();
   private static final Map<String, Consumer<ServerLevel>> AFTER_BATCH_FUNCTIONS = Maps.newHashMap();
   private static final Collection<TestFunction> LAST_FAILED_TESTS = Sets.<TestFunction>newHashSet();

   public static void register(Class<?> var0) {
      Arrays.stream(â˜ƒ.getDeclaredMethods()).forEach(GameTestRegistry::register);
   }

   public static void register(Method var0) {
      String â˜ƒ = â˜ƒ.getDeclaringClass().getSimpleName();
      GameTest â˜ƒx = (GameTest)â˜ƒ.getAnnotation(GameTest.class);
      if (â˜ƒx != null) {
         TEST_FUNCTIONS.add(turnMethodIntoTestFunction(â˜ƒ));
         TEST_CLASS_NAMES.add(â˜ƒ);
      }

      GameTestGenerator â˜ƒ = (GameTestGenerator)â˜ƒ.getAnnotation(GameTestGenerator.class);
      if (â˜ƒ != null) {
         TEST_FUNCTIONS.addAll(useTestGeneratorMethod(â˜ƒ));
         TEST_CLASS_NAMES.add(â˜ƒ);
      }

      registerBatchFunction(â˜ƒ, BeforeBatch.class, BeforeBatch::batch, BEFORE_BATCH_FUNCTIONS);
      registerBatchFunction(â˜ƒ, AfterBatch.class, AfterBatch::batch, AFTER_BATCH_FUNCTIONS);
   }

   private static <T extends Annotation> void registerBatchFunction(
      Method var0, Class<T> var1, Function<T, String> var2, Map<String, Consumer<ServerLevel>> var3
   ) {
      T â˜ƒ = â˜ƒ.getAnnotation(â˜ƒ);
      if (â˜ƒ != null) {
         String â˜ƒx = (String)â˜ƒ.apply(â˜ƒ);
         Consumer<ServerLevel> â˜ƒxx = (Consumer)â˜ƒ.putIfAbsent(â˜ƒx, turnMethodIntoConsumer(â˜ƒ));
         if (â˜ƒxx != null) {
            throw new RuntimeException("Hey, there should only be one " + â˜ƒ + " method per batch. Batch '" + â˜ƒx + "' has more than one!");
         }
      }
   }

   public static Collection<TestFunction> getTestFunctionsForClassName(String var0) {
      return (Collection<TestFunction>)TEST_FUNCTIONS.stream().filter(var1 -> isTestFunctionPartOfClass(var1, â˜ƒ)).collect(Collectors.toList());
   }

   public static Collection<TestFunction> getAllTestFunctions() {
      return TEST_FUNCTIONS;
   }

   public static Collection<String> getAllTestClassNames() {
      return TEST_CLASS_NAMES;
   }

   public static boolean isTestClass(String var0) {
      return TEST_CLASS_NAMES.contains(â˜ƒ);
   }

   @Nullable
   public static Consumer<ServerLevel> getBeforeBatchFunction(String var0) {
      return (Consumer<ServerLevel>)BEFORE_BATCH_FUNCTIONS.get(â˜ƒ);
   }

   @Nullable
   public static Consumer<ServerLevel> getAfterBatchFunction(String var0) {
      return (Consumer<ServerLevel>)AFTER_BATCH_FUNCTIONS.get(â˜ƒ);
   }

   public static Optional<TestFunction> findTestFunction(String var0) {
      return getAllTestFunctions().stream().filter(var1 -> var1.getTestName().equalsIgnoreCase(â˜ƒ)).findFirst();
   }

   public static TestFunction getTestFunction(String var0) {
      Optional<TestFunction> â˜ƒ = findTestFunction(â˜ƒ);
      if (!â˜ƒ.isPresent()) {
         throw new IllegalArgumentException("Can't find the test function for " + â˜ƒ);
      } else {
         return (TestFunction)â˜ƒ.get();
      }
   }

   private static Collection<TestFunction> useTestGeneratorMethod(Method var0) {
      try {
         Object â˜ƒ = â˜ƒ.getDeclaringClass().newInstance();
         return (Collection<TestFunction>)â˜ƒ.invoke(â˜ƒ);
      } catch (ReflectiveOperationException var2) {
         throw new RuntimeException(var2);
      }
   }

   private static TestFunction turnMethodIntoTestFunction(Method var0) {
      GameTest â˜ƒ = (GameTest)â˜ƒ.getAnnotation(GameTest.class);
      String â˜ƒx = â˜ƒ.getDeclaringClass().getSimpleName();
      String â˜ƒxx = â˜ƒx.toLowerCase();
      String â˜ƒxxx = â˜ƒxx + "." + â˜ƒ.getName().toLowerCase();
      String â˜ƒxxxx = â˜ƒ.template().isEmpty() ? â˜ƒxxx : â˜ƒxx + "." + â˜ƒ.template();
      String â˜ƒxxxxx = â˜ƒ.batch();
      Rotation â˜ƒxxxxxx = StructureUtils.getRotationForRotationSteps(â˜ƒ.rotationSteps());
      return new TestFunction(
         â˜ƒxxxxx,
         â˜ƒxxx,
         â˜ƒxxxx,
         â˜ƒxxxxxx,
         â˜ƒ.timeoutTicks(),
         â˜ƒ.setupTicks(),
         â˜ƒ.required(),
         â˜ƒ.requiredSuccesses(),
         â˜ƒ.attempts(),
         turnMethodIntoConsumer(â˜ƒ)
      );
   }

   private static Consumer<?> turnMethodIntoConsumer(Method var0) {
      return var1 -> {
         try {
            Object â˜ƒ = â˜ƒ.getDeclaringClass().newInstance();
            â˜ƒ.invoke(â˜ƒ, var1);
         } catch (InvocationTargetException var3) {
            if (var3.getCause() instanceof RuntimeException) {
               throw (RuntimeException)var3.getCause();
            } else {
               throw new RuntimeException(var3.getCause());
            }
         } catch (ReflectiveOperationException var4) {
            throw new RuntimeException(var4);
         }
      };
   }

   private static boolean isTestFunctionPartOfClass(TestFunction var0, String var1) {
      return â˜ƒ.getTestName().toLowerCase().startsWith(â˜ƒ.toLowerCase() + ".");
   }

   public static Collection<TestFunction> getLastFailedTests() {
      return LAST_FAILED_TESTS;
   }

   public static void rememberFailedTest(TestFunction var0) {
      LAST_FAILED_TESTS.add(â˜ƒ);
   }

   public static void forgetFailedTests() {
      LAST_FAILED_TESTS.clear();
   }
}
