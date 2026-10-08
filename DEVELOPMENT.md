# Zenith — 开发说明

面向开发者的工程内部说明。模组内容与许可协议见 [README.md](README.md)。

- 平台：NeoForge 1.21.1（`neo_version=21.1.238`，Parchment `2024.11.17`）
- modid：`zenith`，包名：`first.zenith`
- 依赖：`first:Lyra:1.21.1.13`（mavenLocal），GeckoLib 由 Lyra 传递引入

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
├── network/ZenithPacket.java                         左键挥砍包
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
# 需要 JDK 21 工具链（Gradle 可用 JDK 17/21 启动）
./gradlew build          # 产物：build/libs/zenith-1.21.1.1.jar
./gradlew runClient      # 开发客户端
./gradlew runData        # 数据生成 -> src/generated/resources
```

源码含中文注释与中文字面量，`build.gradle` 中已强制 `options.encoding = 'UTF-8'`。

> 构建依赖 `lyra`（`mavenLocal()`）。本地需先发布 Lyra `1.21.1.13` 到 mavenLocal，
> 否则 Gradle 解析 `first:Lyra` 会失败。
