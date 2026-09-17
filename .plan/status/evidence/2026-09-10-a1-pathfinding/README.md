# Shared pathfinding checkpoint

2026-09-10, main e1eaaac7d06a41452762eb92fe4bf3fec5670968 plus preserved changes. Same Linux/Java 8/Gradle environment as foundation checkpoint.

Path/PathPoint/PathEntity and the path search now compile once in main. BetaPathfinder retains entity bounds/target conversion and the original block/door/material traversal query in each host. PathEntity takes width directly. A* ordering, fall limit, coordinate hashing and heap arithmetic unchanged. Client/server differences were only named aliases, recorded by the source diff.

PASS: `./gradlew --gradle-user-home /tmp/origins-gradle-cache --no-daemon --offline build` (build.log); `javac -cp build/classes/java/main -d build/smoke-tests tools/PathfindingSmokeTest.java` and `java -cp build/smoke-tests:build/classes/java/main PathfindingSmokeTest` with `/usr/lib/jvm/java-8-openjdk/bin/` tools: routes around a wall and supports reused searches. Initial build exposed two argument-removal errors, corrected before final PASS. Sandbox lock-socket failure required elevated Gradle execution.

Mob movement in a graphical world NOT RUN; full a1 remains in progress. Continuous merge work proceeds into packet definitions.
