package org.neverhook.client.ui.components.draggable;

import org.neverhook.client.ui.components.draggable.impl.*;

import java.util.ArrayList;

public class DraggableManager {

    public ArrayList<DraggableModule> mods = new ArrayList<>();

    public DraggableManager() {
        mods.add(new ClientInfoComponent());
        mods.add(new InfoComponent());
        mods.add(new WaterMarkComponent());
    }

    public ArrayList<DraggableModule> getMods() {
        return mods;
    }

    public void setMods(ArrayList<DraggableModule> mods) {
        this.mods = mods;
    }
}
