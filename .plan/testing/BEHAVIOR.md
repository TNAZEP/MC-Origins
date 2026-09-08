# Beta behavior and presentation scenarios

Run baseline and candidate from the same initial fixture and inputs. For each scenario distinguish SP-local, remote client and dedicated authoritative behavior. Capture inputs at tick boundaries where possible; wall-clock automation alone cannot prove exact simulation parity.

| Test family | Minimum cases | Compare |
|---|---|---|
| MOV | Walk/sprint absence, jump, sneak edges, ladders, water/lava, ice, collision, fall damage | Position/velocity per tick, health and input response |
| COM | Melee timing/reach, knockback, projectiles, armor, death/respawn | Damage, timing, inventory and entity state |
| BLK | Place/break, drops, tools, durability, gravity, fluids, fire, light updates | State/event trace and inventories |
| RED | Dust, torches, repeaters, pistons/sticky pistons, doors, buttons, plates, rails | Tick/order-sensitive state transitions |
| ENT | Spawn/despawn, pathfinding, targeting, passive/hostile mobs, minecarts/boats, mounts | Counts, state trajectories and interaction outcomes |
| INV | Crafting, furnace progress/fuel, containers, stack splitting, shift-click, armor | Slot state and timing |
| RNG | Random ticks, drops, spawning and generation under controlled calls | Sequence/draw counts when instrumentable |
| DIM | Overworld/Nether travel, portal placement, respawn, saves in either dimension | Dimension, position, persistent state |
| UI | Hotbar, menus, chat, key repeat, text, mouse capture, fullscreen, pause | Visible behavior and event order |
| VIS | Day/night, underground, Nether, rain, water, leaves, glass, particles, held items, GUI | Fixed-camera screenshots and reviewed differences |
| AUD | Music, ambient, block/entity/step sounds, positional attenuation, volume and pause | Event ordering plus listening checks |
| NET | Join, movement, placement, entities, chat, inventory, dimension change, disconnect/rejoin | Server/client agreement and Beta codec trace |

## Mandatory smoke recipe

Start a local world; move, jump, mine, place, craft, use a chest, save, quit and reload. Check pause behavior. Start the built dedicated server with no display/native client libraries. Join with two candidate clients, exchange items, interact with blocks/entities, chat, disconnect, reconnect, then restart the server and verify persistence. Do not require SP to behave like MP where vanilla Beta differs.

## Numerical comparisons

Use exact discrete-state comparison for deterministic logic. Floating-point tolerances require a justified baseline measurement and must not conceal accumulating drift, different collision outcomes or altered RNG consumption. Record event ordering and relevant neighboring chunks when a circuit or fluid simulation diverges.

## Visual suite

Record resolution, framebuffer scale, graphics options, texture/resource hashes, camera transform, time/weather and animation phase. Review crops and full scenes, including transparency and fog. Calibrate cross-driver thresholds using repeat baseline variance. Avoid blessing a new golden image merely because the new renderer produces it consistently.

## Historical defects

A reproducible vanilla Beta quirk is part of the default behavioral target. An Origins crash, data loss or modernization regression is a defect. If preserving a historical bug conflicts with platform stability, log a specific proposed deviation with alternatives; never apply a broad “bug fixes are allowed” exception.
