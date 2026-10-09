package first.zenith.register;

import first.lyra.client.render.model.LyraModelRenderer;
import first.zenith.ZenithMod;
import net.minecraft.client.resources.model.ModelResourceLocation;
import org.mesdag.portlib.event.PortEventHandler;
import org.mesdag.portlib.event.client.PortModelEvent;

import java.util.ArrayList;
import java.util.List;

public class ZenithModelRegister {

    private static final List<ModelResourceLocation> MODELS = new ArrayList<>();

    public static final ModelResourceLocation ZENITH_SWORD_COPPER_SHORT_SWORD = zenithSword("copper_short_sword");
    public static final ModelResourceLocation ZENITH_SWORD_LIGHTS_BANE = zenithSword("lights_bane");
    public static final ModelResourceLocation ZENITH_SWORD_MURAMASA = zenithSword("muramasa");
    public static final ModelResourceLocation ZENITH_SWORD_TERRA_BLADE = zenithSword("terra_blade");
    public static final ModelResourceLocation ZENITH_SWORD_BLOOD_BUTCHERER = zenithSword("blood_butcherer");
    public static final ModelResourceLocation ZENITH_SWORD_STARFURY = zenithSword("starfury");
    public static final ModelResourceLocation ZENITH_SWORD_ENCHANTED_SWORD = zenithSword("enchanted_sword");
    public static final ModelResourceLocation ZENITH_SWORD_BEE_KEEPER = zenithSword("bee_keeper");
    public static final ModelResourceLocation ZENITH_SWORD_BLADE_OF_GRASS = zenithSword("blade_of_grass");
    public static final ModelResourceLocation ZENITH_SWORD_FIERY_GREATSWORD = zenithSword("fiery_greatsword");
    public static final ModelResourceLocation ZENITH_SWORD_NIGHTS_EDGE = zenithSword("nights_edge");
    public static final ModelResourceLocation ZENITH_SWORD_TRUE_NIGHTS_EDGE = zenithSword("true_nights_edge");
    public static final ModelResourceLocation ZENITH_SWORD_EXCALIBUR = zenithSword("excalibur");
    public static final ModelResourceLocation ZENITH_SWORD_TRUE_EXCALIBUR = zenithSword("true_excalibur");
    public static final ModelResourceLocation ZENITH_SWORD_THE_HORSEMANS_BLADE = zenithSword("the_horsemans_blade");
    public static final ModelResourceLocation ZENITH_SWORD_SEEDLER = zenithSword("seedler");
    public static final ModelResourceLocation ZENITH_SWORD_TRUE_TERRA_BLADE = zenithSword("true_terra_blade");
    public static final ModelResourceLocation ZENITH_SWORD_INFLUX_WAVER = zenithSword("influx_waver");
    public static final ModelResourceLocation ZENITH_SWORD_STAR_WRATH = zenithSword("star_wrath");
    public static final ModelResourceLocation ZENITH_SWORD_MEOWMERE = zenithSword("meowmere");
    public static final ModelResourceLocation ZENITH_SWORD_ZENITH = zenithSword("zenith");

    private static ModelResourceLocation zenithSword(String path) {
        return standaloneIn("projectile/zenith", path);
    }

    private static ModelResourceLocation standaloneIn(String folder, String path) {
        ModelResourceLocation location = LyraModelRenderer.jsonLocation(ZenithMod.rl(folder + "/" + path));
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
