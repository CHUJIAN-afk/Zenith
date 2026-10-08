# ⚔️ Zenith — Infinity +1 Sword

**Terraria's ultimate blade, rebuilt for Minecraft. Not a lookalike — a reconstruction.**

> 🏆 **Achievement — Infinity +1 Sword**
> *"Obtain the Zenith, the ultimate sword forged from the peak moments of the journey."*
> *Category: Collector*

In Terraria, the Zenith is generally considered the strongest weapon in the game — rivalled only, in appearance, by its famous variant, the **True Copper Shortsword**. This mod brings that weapon to NeoForge 1.21.1 as a complete mechanical rebuild: the arc, the blades, the particles, the lighting and the collision are all reconstructed so the weapon *behaves* the way the Zenith behaves, not just looks the way it looks.

Every blade you throw is a real object flying a real elliptical arc, re-tracked every frame, lit in real time, and swept against entities as a full oriented bounding box.

---

## 📦 What's inside

| Item | Description |
| --- | --- |
| **Zenith** | Netherite-tier sword with a **+19 attack damage** modifier, Epic rarity, unstackable. |
| **True Copper Shortsword** | Same statline — and it completely changes what the Zenith throws. |

Both convert into each other with a **shapeless recipe** (Zenith ⇄ True Copper Shortsword), because canonically they are the same weapon. The Zenith also has a **1% chance** to appear in **End City treasure**.

Both items are available in the dedicated **Zenith** creative tab.

---

## 🎮 How it handles

**Hold right-click to channel.**

The Zenith is not a spam-click weapon. While you hold use, the blade charges every tick based on your **attack speed** (`attack_speed × 0.5` per tick), and every **3.33 charge** releases one blade. Anything left over is dumped in a single burst — so a high-attack-speed build genuinely fills the air with swords.

- Your gear feeds straight into the volley: attack speed decides how many blades you throw per second, attack damage decides what each one hits for.
- While channelling, the first-person hand is hidden and the use-item movement slowdown is lifted by a Mixin, so you keep full strafe speed.
- A custom swing sound fires on every volley.

---

## 🔬 Why this port is different

### 🌀 A perfect elliptical arc — not a straight line, not a canned animation

Every blade flies a **true ellipse**, recomputed from scratch the moment it spawns:

- Point A is your aim point (or the target it locked onto), point B is four blocks behind your chest, and the two are always forced at least 8 blocks apart.
- The **arc plane normal is randomised but seeded by that blade's UUID**. Each sword gets its own plane orientation, and that orientation is deterministic — so the arc never jitters, wobbles or re-rolls mid-flight.
- Curvature is `0.1 + rand(0–0.4) + min(0.6, 6 / (distance + 1))`: the closer your target, the tighter and more violent the slash.

The blade's **orientation** is derived from the arc itself. The tip direction is the arc point minus a weighted blend of the ellipse's focus midpoint and the arc point half a revolution ahead — so the sword always leads tip-first along its own tangent, exactly like a real slash.

### 🔄 Permanently relative to you and your target

The arc is **not a frozen spline**. Its two anchor points are read from `owner.getPosition(partialTick)` and `target.getPosition(partialTick)` on **every single render frame**.

If you strafe, the whole arc translates with you mid-flight. If the target runs, the arc's far anchor follows it and the blade's remaining path bends to compensate. The ribbon trail re-samples the arc at previous ticks too, so the trail bends with it. Nothing in this weapon is baked into world space.

### 🎯 Real-time tracking, per blade

Targeting happens twice, at two different scales:

1. **At launch** — your aim point is snapped to whatever is actually under your crosshair with a 32-block raycast. Then a **30° cone** search around the landing point scores every candidate as `0.8 × normalised angle + 0.2 × normalised distance`, and the winner becomes the preferred target.
2. **Per blade** — every sword that is not the Zenith itself then looks for its **own** victim within 10 blocks of its landing point and locks onto a random one. If it finds nobody, it falls back to the crosshair target.

The 33% of blades that *are* the Zenith always fly exactly where you aim, with no deviation. That split — some blades hunting, some blades obedient — is what makes a volley read as a swarm rather than a shotgun.

### ⚡ Variable, dynamic launch speed

Two independent things vary the speed of every blade:

- **Travel time is fixed at 9 ticks regardless of distance.** A blade crossing a 30-block arc and a blade striking something two blocks away both arrive on the same tick — so the long one moves *far* faster. Speed is a function of your target's distance, every time.
- The progress curve is a **15-control-point Bézier easing curve**, not a linear ramp. The blade explodes out of your hand, holds its speed through the middle of the arc, and decelerates into the impact.

### 🧊 OBB-grade hitbox

The blade's hitbox is a **0.7 × 0.2 × 3.4 oriented bounding box** — a box the actual shape and length of the blade, rotated to match the blade's orientation in space at that instant. It is not a point, not a sphere, and not an axis-aligned approximation of a sword.

### 🧵 Swept collision across the entire arc

Testing an OBB at one point per tick would let fast blades tunnel straight through entities. So each tick the weapon **rewinds one tick, re-samples the whole arc at 16 points, and runs the oriented-box sweep at every one of them**, rebuilding the path node between samples.

The result: the blade collides with everything its *entire flight path* passed through during that tick, at any speed, at any angle — no tunnelling, no misses, no "I clearly hit that".

Hits apply your full attack damage with knockback, and the blade keeps flying — it cuts through, it does not stop.

### ✨ Volumetric particles, scattered along the tangent

Blades shed particles for the first 8 ticks of their life, sampled along the arc at up to 16 points:

- **Tangent scattering** — a particle's direction is computed as the vector between two adjacent samples of the arc, then normalised with a ±0.1 jitter. Particles are always thrown **along the arc's tangent**, not radially, so the spray traces the shape of the slash instead of puffing around it.
- Each sample is offset perpendicular to the blade using the path node's own pitch and yaw, so the stream hugs the blade rather than sitting on its centreline.
- Lifetime scales with distance along the arc (`5–15 ticks + sample index × 2`), speed varies 0.3–0.6, friction 0.5–0.75, size 0.01–0.03.

And the particles themselves are not billboards. Each one is a **true 3D droplet**: a cubic head whose mid-cross-section is perpendicular to the direction of travel, four corners rolled around the motion axis using a Rodrigues rotation, plus a four-sided pyramidal tail converging to a point behind it. Both ends are sealed, so the droplet stays solid from every camera angle and never collapses into a flat sprite when it slows down.

### 💡 Dynamic lighting on every particle — with a real fade-out

Every single droplet is a **moving light source**. Each calls into the dynamic light dispatcher with an intensity of `(1 − progress)² × 0.5`, where progress is normalised across that particle's own lifetime.

That means the **light fades out on the same quadratic curve the droplet shrinks on**: a volley lights up the terrain around it, and the glow dies away smoothly instead of popping off. Hundreds of independent, individually fading point lights tracing an arc through the world.

### 🗡️ Dynamic lighting on the blade itself

Each of the 21 blades is also a moving light source in its own right, emitting at `blade alpha × 0.5`, with an extra ramp-in over the first two ticks so the sword fades into existence rather than snapping on. Blades are rendered at full brightness, so they read as genuinely self-luminous objects cutting through the dark.

### 🎗️ Trail and impact flash

- A **ribbon trail** built from up to 60 samples spaced 0.05 ticks apart along the arc, ramping in over the first 4 ticks and out after tick 7, so the trail draws the shape of the flight rather than a straight smear.
- Blades with sufficient alpha additionally project a **slash flash** along the trail while the arc progress passes through its middle — shaped by an ease-in-out curve, so the streak swells and vanishes exactly at the peak of the swing.

### 🥄 The True Copper Shortsword transformation

The Zenith throws **21 different blades**: Copper Shortsword, Lights Bane, Muramasa, Terra Blade, Blood Butcherer, Starfury, Enchanted Sword, Bee Keeper, Blade of Grass, Fiery Greatsword, Night's Edge, True Night's Edge, Excalibur, True Excalibur, The Horseman's Blade, Seedler, True Terra Blade, Influx Waver, Star Wrath, Meowmere — and the Zenith itself, forced on 33% of launches.

Blade opacity is randomised too: most blades arrive fully solid, the rest come through semi-transparent as ghost swords.

And then there is the **True Copper Shortsword**. Hold it instead, and **every single blade you throw is the Copper Shortsword.** No randomness, no rotation, no exceptions — a full screen of copper shortswords, exactly as the meme demands. It is the one weapon the Zenith has always been measured against, and now you can actually wield it.

---

## ⚙️ Requirements

| | |
| --- | --- |
| **Minecraft** | 1.21.1 |
| **Loader** | NeoForge 21.1+ |
| **Dependency** | **Lyra** 1.21.1.8+ (required) — link this to the Lyra project page once it is published |
| **Side** | Client and server |

Built on the **Lyra** attachment-entity engine, which supplies the entity scheduler, the render dispatcher, the network sync layer and the dynamic light dispatcher this weapon runs on.

---

## 🙏 Credits

- **Terraria** and the Zenith are the work of **Re-Logic**. This is an unofficial fan-made mod; it is not affiliated with or endorsed by Re-Logic.
- All art and sound in this mod — every texture, the flying-sword models and the swing sound effect — are taken from **Terraria**. They are **not the author's original work; copyright belongs to Re-Logic**.
- Built by **FirstSight**.

> *"Obtain the Zenith, the ultimate sword forged from the peak moments of the journey."*
