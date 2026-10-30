package org.neverhook.client.feature;

import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.feature.impl.hud.*;
import org.neverhook.client.feature.impl.movement.AutoSprint;
import org.neverhook.client.feature.impl.visual.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FeatureManager {

    public CopyOnWriteArrayList<Feature> features = new CopyOnWriteArrayList<>();

    public FeatureManager() {
        features.add(new ClickGui());
        features.add(new HUD());
        features.add(new FeatureList());
        features.add(new Notifications());
        features.add(new WaterMark());
        features.add(new AutoSprint());

        features.add(new Animations());
        features.add(new BlockESP());
        features.add(new BlockOverlay());
        features.add(new CameraNoClip());
        features.add(new Chams());
        features.add(new ChestESP());
        features.add(new ChinaHat());
        features.add(new ChunkAnimator());
        features.add(new Crosshair());
        features.add(new EnchantmentColor());
        features.add(new EnderPearlESP());
        features.add(new EntityESP());
        features.add(new FogColor());
        features.add(new FullBright());
        features.add(new HitColor());
        features.add(new ItemESP());
        features.add(new ItemPhysics());
        features.add(new MobESP());
        features.add(new NameTags());
        features.add(new NoRender());
        features.add(new PersonViewer());
        features.add(new Radar());
        features.add(new Scoreboard());
        features.add(new Tracers());
        features.add(new Trails());
        features.add(new Trajectories());
        features.add(new ViewModel());
        features.add(new WorldColor());
        features.add(new XRay());
    }

    public List<Feature> getFeatureList() {
        return this.features;
    }

    public List<Feature> getFeaturesForCategory(Type category) {
        List<Feature> featureList = new ArrayList<>();
        for (Feature feature : getFeatureList()) {
            if (feature.getType() == category) {
                featureList.add(feature);
            }
        }
        return featureList;
    }

    public Feature getFeatureByClass(Class<? extends Feature> classFeature) {
        for (Feature feature : getFeatureList()) {
            if (feature != null) {
                if (feature.getClass() == classFeature) {
                    return feature;
                }
            }
        }
        return null;
    }

    public Feature getFeatureByLabel(String name) {
        for (Feature feature : getFeatureList()) {
            if (feature.getLabel().equalsIgnoreCase(name)) {
                return feature;
            }
        }
        return null;
    }
}
