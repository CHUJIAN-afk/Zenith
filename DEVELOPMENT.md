# Zenith — 开发说明

面向开发者的工程内部说明。模组内容与许可协议见 [README.md](README.md)。

- 平台：Forge 1.20.1（`forge_version=47.4.20`，Parchment `2023.09.03`），JDK 17
- modid：`zenith`，包名：`first.zenith`，版本 `1.20.1.2`
- 依赖：`first:Lyra-1201:1.20.1.14`（mavenLocal）、`org.mesdag:PortLib:1.2.4`、`geckolib-forge-1.20.1:4.8.4`（均由 mavenLocal / maven 解析）

## 迁移说明

原 `Summoner` 工程**未做任何改动**。本工程中的天顶剑相关代码是从原工程逐字复制过来的，
只做了「脱离原工程所必需」的机械改名：

| 原工程 | 本工程 |
| --- | --- |
| `first.summoner.*` | `first.zenith.*` |
| `Summoner`（主类） | `ZenithMod` |
| `Summoner.rl(...)` | `ZenithMod.rl(...)` |
| `SummonerStreamCodecs` | `ZenithStreamCodecs` |
| `SummonerItemRegister` / `SummonerSoundRegister` / `SummonerParticleRegister` | `ZenithItemRegister` / `ZenithSoundRegister` / `ZenithParticleRegister` |
| `SummonerAttachmentRegister` / `SummonerAttachmentEntityRegister` | `ZenithAttachmentRegister` / `ZenithAttachmentEntityRegister` |
| 资源命名空间 `summoner:` | `zenith:` |

**逻辑与排版零改动**：以下文件与原工程对应文件在完成上述改名后逐字节相同。

- `common/projectile/Zenith.java`
- `common/attachment/ZenithData.java`
- `common/particle/zenithParticle/ZenithParticle.java`
- `common/particle/zenithParticle/ZenithParticleOptions.java`
- `common/particle/zenithParticle/ZenithParticleProvider.java`
- `client/attachmentEntityRenderer/projectile/ZenithRenderer.java`
- `client/attachmentEntityRenderer/projectile/ZenithTrailContext.java`
- `network/ZenithPacket.java`
- `utils/ParticleHelper.java`
- `utils/RenderUtil.java`
- `utils/ZenithStreamCodecs.java`（原 `SummonerStreamCodecs.java`）

图片、音效等二进制资源逐字节相同；21 个飞剑模型 JSON 仅把 `summoner:` 命名空间换成 `zenith:`。

## 已移植的子系统

| 子系统 | 涉及文件 |
| --- | --- |
| 粒子 | `ZenithParticle`、`ZenithParticleOptions`、`ZenithParticleProvider`、`ZenithParticleRegister`、`ClientEvent.onRegisterParticleProviders`、`assets/zenith/particles/zenith.json`、`textures/particle/zenith.png` |
| 渲染 | `ZenithRenderer`、`ZenithTrailContext`、`ZenithModelRegister`（`ModelEvent.RegisterAdditional` 注册 21 把飞剑独立模型）、`ZenithAttachmentEntityRenderRegister`（`AttachmentEntityRenderDispatcher.register`） |
| 调度器 | `Zenith.registerSyncFields(SyncFieldDispatcher)` 同步字段、`LyraHelper.get(owner).add(zenith)` 附件实体调度、`ZenithPacket` + `ZenithNetworkPacketRegister`、`Event.tick(PlayerTickEvent.Post)` |
| 物品 | `ZenithItemRegister`（`LyraItemRegistries` 构建）、`ZenithCreativeTabRegister`、`ZenithLanguageRegister` |

## 目录结构

```
src/main/java/first/zenith/
├── ZenithMod.java                                    主类（@Mod("zenith")）
├── common/
│   ├── Event.java                                    玩家 tick：驱动 ZenithData / 左键发包
│   ├── attachment/ZenithData.java                    蓄力与飞剑生成
│   ├── particle/zenithParticle/                      天顶剑粒子
│   └── projectile/Zenith.java                        飞剑附件实体
├── client/
│   ├── ClientEvent.java                              粒子 provider 注册
│   └── attachmentEntityRenderer/projectile/          飞剑本体 + 绸带拖尾
├── register/                                         各类注册
└── utils/                                            ParticleHelper / RenderUtil / ZenithStreamCodecs

src/main/resources/assets/zenith/
├── lyra_model/json/projectile/zenith/<剑名>/          21 把飞剑的独立模型 + 贴图
├── particles/zenith.json
├── sounds.json + sounds/zenith.ogg
└── textures/                                         zenith.png（爆闪）、item/、particle/
```

## 构建

```bash
# 需要 JDK 17 工具链（Gradle 8.8 用 JDK 17 启动）
./gradlew build          # 产物：build/libs/zenith-1.20.1.2.jar
./gradlew runClient      # 开发客户端
./gradlew runData        # 数据生成 -> src/generated/resources
```

源码含中文注释与中文字面量，`build.gradle` 中已强制 `options.encoding = 'UTF-8'`。

> 构建依赖 `Lyra-1201`（`mavenLocal()`）。本地需先发布 Lyra-1201 `1.20.1.14` 到 mavenLocal，
> 否则 Gradle 解析 `first:Lyra-1201` 会失败。

## 1.20.1 / Forge 移植说明

本分支把 1.21.1 / NeoForge 的实现移植到 **1.20.1 / Forge 47.4.20**，逻辑与数值保持一致。
移植依赖 Lyra 内置的 **PortLib**：它在 Forge 上提供 NeoForge 风格的接口，因此大部分改动是
「换前缀」，而不是重写。

### 导入映射

| 1.21.1 / NeoForge | 1.20.1 / Forge + PortLib |
| --- | --- |
| `net.neoforged.fml.common.Mod` | `net.minecraftforge.fml.common.Mod` |
| `net.neoforged.bus.api.IEventBus` | `net.minecraftforge.eventbus.api.IEventBus` |
| `@EventBusSubscriber` + `@SubscribeEvent` | `PortEventHandler.addListener(...)`（在各 `init()` 中显式挂载） |
| `net.neoforged.api.distmarker.Dist` | `PortEnvironment.isPhysicalClient()` |
| `DeferredRegister` / `DeferredHolder` | `PortRegisterHandler.*` / `PortRegistryEntry` |
| `DeferredItem` | `PortDeferredItem` |
| `AttachmentType` / `IAttachmentHolder` | `PortAttachmentType` / `IPortAttachmentHolder` |
| `RegisterParticleProvidersEvent` | `PortRegisterParticleProvidersEvent` |
| `RenderHandEvent` / `RenderLevelStageEvent` | `PortRenderHandEvent` / `PortRenderLevelStageEvent` |
| `ModelEvent.RegisterAdditional` | `PortModelEvent.RegisterAdditional` |
| `PlayerTickEvent.Post` | `PortPlayerTickEvent.Post` |
| `FMLClientSetupEvent` | `PortFMLClientSetupEventPort`（PortLib 1.2.4 的类名；1.2.7 起改名） |
| `RegistryFriendlyByteBuf` / `StreamCodec` | `PortRegistryFriendlyByteBuf` / `PortStreamCodec` |
| `ItemTags.*_ENCHANTABLE` | `PortTags.Items.*_ENCHANTABLE` |
| `net.neoforged…ItemModelProvider` | `net.minecraftforge.client.model.generators.ItemModelProvider` |

`ResourceLocation.fromNamespaceAndPath` / `withDefaultNamespace` 由 PortLib 在 1.20.1 上补齐，可原样保留。

### 非机械改动的几处

| 位置 | 说明 |
| --- | --- |
| `ZenithItem` 属性 | 1.21 的 `SwordItem` 不施加默认属性，`+19` 就是纯修饰符值；1.20.1 的 `SwordItem(Tier,int,float,Properties)` 会把**等级加成**并入攻击伤害并额外施加攻速修饰符。故传 `19 - 下界合金加成(4) = 15` 与攻速 `0`，最终仍是「攻击伤害 +19、无攻速修饰符」。攻速必须保持无修饰符——蓄力速率取决于 `ATTACK_SPEED`。 |
| 客户端事件分流 | 原 `@EventBusSubscriber(modid)` 双端注册，其中动态光照监听用到客户端类。现拆出 `Event.initClient()`，仅在 `PortEnvironment.isPhysicalClient()` 时挂载，避免专用服务端加载客户端类。 |
| `ZenithAttachmentEntityRegister.holder(...)` | Lyra 的 `AttachmentEntity` 构造函数要求 `Holder<AttachmentEntityType<?>>`，而 Lyra 自定义注册表的泛型是 `AttachmentEntityType<? extends AttachmentEntity>`；泛型不变性导致需一次受检窄化转换，运行时无影响。 |
| `ParticleOptions` | 1.20.1 仍要求 `writeToNetwork(FriendlyByteBuf)` 与 `writeToString()`，1.21 已移除。前者复用同一 `STREAM_CODEC`（经 `IPortFriendlyByteBufExtension.wrap()`），保证收发格式一致。 |
| 顶点写入 | 1.21 的 `addVertex(11 参数)` 在 1.20.1 需改为 `vertex().color().uv().overlayCoords().uv2().normal().endVertex()` 链式。 |
| `FastColor.ARGB32` | 1.20.1 无 `(alpha, packedRGB)` 重载，按 Lyra 的打包约定手动拆位。 |
| `handheldItem` | 1.20.1 的 `ItemModelProvider` 只有 `basicItem`（父模型 `item/generated`），已按 1.21 的实现等价补上 `item/handheld` 版本，否则剑会渲染成扁平图标。 |

### 数据生成

`./gradlew runData` 已跑通，产出与 1.21.1 分支一一对应，仅目录命名随版本变化：

| 1.21.1 | 1.20.1 |
| --- | --- |
| `data/minecraft/tags/item/...` | `data/minecraft/tags/items/...` |
| `data/zenith/recipe/...` | `data/zenith/recipes/...` |
| `data/zenith/advancement/...` | `data/zenith/advancements/...` |

语言文件、物品模型与标签内容与 1.21.1 分支**逐字节相同**；配方仅因原版 JSON 序列化格式差异
（1.21 的 `result.id`/`count` → 1.20.1 的 `result.item`）而不同，语义等价。
