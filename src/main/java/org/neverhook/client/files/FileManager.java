package org.neverhook.client.files;

import org.neverhook.client.NeverHook;
import org.neverhook.client.files.impl.*;

import java.io.File;
import java.util.ArrayList;

public class FileManager {

    public static File directory = new File(NeverHook.instance.name);
    public static ArrayList<CustomFile> files = new ArrayList<>();

    public FileManager() {
        files.add(new AltConfig("AltConfig", true));
        files.add(new FriendConfig("FriendConfig", true));
        files.add(new MacroConfig("MacroConfig", true));
        files.add(new HudConfig("HudConfig", true));
        files.add(new CapeConfig("CapeConfig", true));
    }

    public void loadFiles() {
        for (CustomFile file : files) {
            try {
                if (file.loadOnStart()) {
                    file.loadFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFiles() {
        for (CustomFile f : files) {
            try {
                f.saveFile();
            } catch (Exception e) {

            }
        }
    }

    public CustomFile getFile(Class<?> clazz) {
        for (CustomFile file : files) {
            if (file.getClass() == clazz) {
                return file;
            }
        }
        return null;
    }

    public abstract static class CustomFile {

        private final File file;
        private final String name;
        private final boolean load;

        public CustomFile(String name, boolean loadOnStart) {
            this.name = name;
            this.load = loadOnStart;
            this.file = new File(FileManager.directory, name + ".json");
            if (!this.file.exists()) {
                try {
                    this.saveFile();
                } catch (Exception e) {

                }
            }
        }

        public final File getFile() {
            return this.file;
        }

        private boolean loadOnStart() {
            return this.load;
        }

        public final String getName() {
            return this.name;
        }

        public abstract void loadFile() throws Exception;

        public abstract void saveFile() throws Exception;
    }
}
