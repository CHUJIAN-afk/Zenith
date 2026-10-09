package first.zenith.register;

import first.zenith.ZenithMod;
import net.minecraft.resources.ResourceLocation;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.client.PortModelEvent;

import java.util.ArrayList;
import java.util.List;

public class ZenithModelRegister {

    private static final List<ResourceLocation> MODELS = new ArrayList<>();

    public static final ResourceLocation ZENITH_SWORD_COPPER_SHORT_SWORD = zenithSword("copper_short_sword");
    public static final ResourceLocation ZENITH_SWORD_LIGHTS_BANE = zenithSword("lights_bane");
    public static final ResourceLocation ZENITH_SWORD_MURAMASA = zenithSword("muramasa");
    public static final ResourceLocation ZENITH_SWORD_TERRA_BLADE = zenithSword("terra_blade");
    public static final ResourceLocation ZENITH_SWORD_BLOOD_BUTCHERER = zenithSword("blood_butcherer");
    public static final ResourceLocation ZENITH_SWORD_STARFURY = zenithSword("starfury");
    public static final ResourceLocation ZENITH_SWORD_ENCHANTED_SWORD = zenithSword("enchanted_sword");
    public static final ResourceLocation ZENITH_SWORD_BEE_KEEPER = zenithSword("bee_keeper");
    public static final ResourceLocation ZENITH_SWORD_BLADE_OF_GRASS = zenithSword("blade_of_grass");
    public static final ResourceLocation ZENITH_SWORD_FIERY_GREATSWORD = zenithSword("fiery_greatsword");
    public static final ResourceLocation ZENITH_SWORD_NIGHTS_EDGE = zenithSword("nights_edge");
    public static final ResourceLocation ZENITH_SWORD_TRUE_NIGHTS_EDGE = zenithSword("true_nights_edge");
    public static final ResourceLocation ZENITH_SWORD_EXCALIBUR = zenithSword("excalibur");
    public static final ResourceLocation ZENITH_SWORD_TRUE_EXCALIBUR = zenithSword("true_excalibur");
    public static final ResourceLocation ZENITH_SWORD_THE_HORSEMANS_BLADE = zenithSword("the_horsemans_blade");
    public static final ResourceLocation ZENITH_SWORD_SEEDLER = zenithSword("seedler");
    public static final ResourceLocation ZENITH_SWORD_TRUE_TERRA_BLADE = zenithSword("true_terra_blade");
    public static final ResourceLocation ZENITH_SWORD_INFLUX_WAVER = zenithSword("influx_waver");
    public static final ResourceLocation ZENITH_SWORD_STAR_WRATH = zenithSword("star_wrath");
    public static final ResourceLocation ZENITH_SWORD_MEOWMERE = zenithSword("meowmere");
    public static final ResourceLocation ZENITH_SWORD_ZENITH = zenithSword("zenith");

    private static ResourceLocation zenithSword(String path) {
        return standaloneIn("projectile/zenith", path);
    }

    private static ResourceLocation standaloneIn(String folder, String path) {
        ResourceLocation location = ZenithMod.rl("lyra_model/json/" + folder + "/" + path + "/" + path);
        MODELS.add(location);
        return location;
    }

    public static void init() {
        PortEventHandler.addListener(ZenithModelRegister::registerAdditional);
    }

    public static void registerAdditional(PortModelEvent.RegisterAdditional event) {
        MODELS.forEach(event::register);
    }
}
