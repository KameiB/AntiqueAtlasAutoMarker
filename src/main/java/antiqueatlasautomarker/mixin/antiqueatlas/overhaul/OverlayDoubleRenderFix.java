package antiqueatlasautomarker.mixin.antiqueatlas.overhaul;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import hunternif.mc.atlas.core.DimensionData;
import hunternif.mc.atlas.marker.DimensionMarkersData;
import hunternif.mc.atlas.util.Rect;
import kenkron.antiqueatlasoverlay.AAORenderEventReceiver;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AAORenderEventReceiver.class)
public abstract class OverlayDoubleRenderFix {
    //@Shadow(remap = false) private static void drawMarkersData(DimensionMarkersData markersData, Rect shape, DimensionData biomeData, Vec3d position) {}

    @WrapWithCondition(
            method = "drawMarkers",
            at = @At(value = "INVOKE", target = "Lkenkron/antiqueatlasoverlay/AAORenderEventReceiver;drawMarkersData(Lhunternif/mc/atlas/marker/DimensionMarkersData;Lhunternif/mc/atlas/util/Rect;Lhunternif/mc/atlas/core/DimensionData;Lnet/minecraft/util/math/Vec3d;)V", ordinal = 0),
            remap = false
    )
    private static boolean stopRenderingGlobalMarkersTwice(DimensionMarkersData globalMarkersData, Rect shape, DimensionData biomeData, Vec3d position){
        //no op, global markers are already in local markers as copies, no need to draw them
        //proof: this would draw a (moved) second set of global markers: drawMarkersData(globalMarkersData, shape, biomeData, position.add(10.,0.,10.));
        return false;
    }
}
