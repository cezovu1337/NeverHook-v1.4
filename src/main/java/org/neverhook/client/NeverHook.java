package org.neverhook.client;

import org.neverhook.client.cmd.CommandManager;
import org.neverhook.client.event.EventManager;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.game.EventShutdownClient;
import org.neverhook.client.event.events.impl.input.EventInputKey;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.FeatureManager;
import org.neverhook.client.files.FileManager;
import org.neverhook.client.files.impl.FriendConfig;
import org.neverhook.client.files.impl.HudConfig;
import org.neverhook.client.files.impl.MacroConfig;
import org.neverhook.client.friend.FriendManager;
import org.neverhook.client.helpers.Helper;
import org.neverhook.client.helpers.math.RotationHelper;
import org.neverhook.client.helpers.render.BlurUtil;
import org.neverhook.client.macro.Macro;
import org.neverhook.client.macro.MacroManager;
import org.neverhook.client.settings.config.ConfigManager;
import org.neverhook.client.ui.GuiCapeSelector;
import org.neverhook.client.ui.components.changelog.ChangeManager;
import org.neverhook.client.ui.components.draggable.DraggableManager;
import org.neverhook.client.ui.newclickgui.ClickGuiScreen;

import java.awt.*;
import java.net.URI;

/**
 * <p>
 * На улице людной, средь белого дня
 * Поймал патруль с пакетом ганджа меня
 * Привели в отдел, усадили за стол
 * Строгий майор стал составлять протокол, бом-бом
 * <p>
 * Тили-бом, тили-тили-бом-бом
 * Руки за голову, в стену лбом-бом-бом
 * Тили-бом, тили-тили-бом-бом
 * Руки за голову, в стену лбом-бом-бом-бом
 * <p>
 * «Зачем курил? Зачем другим давал?
 * Пойми дурак, тебе конец настал
 * Садись и пиши у кого её брал»
 * Но я никому ничего не сказал, бом-бом
 * <p>
 * Тили-бом, тили-тили-бом-бом
 * Руки за голову, в стену лбом-бом-бом
 * Тили-бом, тили-тили-бом-бом
 * Руки за голову, в стену лбом-бом-бом-бом
 */
public class NeverHook implements Helper {

    public static NeverHook instance = new NeverHook();

    public static NeverHook getInstance() {
        return instance;
    }

    public String name = "NeverHook", type = "Premium", version = "1.4", status = "Release", build = "161021";
    public FeatureManager featureManager = new FeatureManager();
    public org.neverhook.client.ui.clickgui.ClickGuiScreen clickGui;
    public ClickGuiScreen newClickGui;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public MacroManager macroManager;
    public FileManager fileManager;
    public DraggableManager draggableManager;
    public FriendManager friendManager;
    public RotationHelper.Rotation rotation;
    public BlurUtil blurUtil;
    public ChangeManager changeManager;

    public void load() {
        openurl("https://t.me/oldhouse1337");
        GuiCapeSelector.Selector.setCapeName("neverhookcape3");
        (fileManager = new FileManager()).loadFiles();
        featureManager = new FeatureManager();
        clickGui = new org.neverhook.client.ui.clickgui.ClickGuiScreen();
        newClickGui = new ClickGuiScreen();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        macroManager = new MacroManager();
        draggableManager = new DraggableManager();
        friendManager = new FriendManager();
        rotation = new RotationHelper.Rotation();
        blurUtil = new BlurUtil();
        changeManager = new ChangeManager();

        try {
            fileManager.getFile(FriendConfig.class).loadFile();
        } catch (Exception ignored) {
        }

        try {
            fileManager.getFile(MacroConfig.class).loadFile();
        } catch (Exception ignored) {
        }

        try {
            fileManager.getFile(HudConfig.class).loadFile();
        } catch (Exception ignored) {
        }

        EventManager.register(rotation);
        EventManager.register(this);
    }

    @EventTarget
    public void shutDown(EventShutdownClient event) {
        EventManager.unregister(this);
        (fileManager = new FileManager()).saveFiles();
    }

    static void openurl(String url) {
        try {
            if (Desktop.isDesktopSupported()
                    && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", url});
                } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                    Runtime.getRuntime().exec(new String[]{"xdg-open", url});
                } else {
                    // пошел нахуй
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @EventTarget
    public void onInputKey(EventInputKey event) {
        for (Feature feature : featureManager.getFeatureList()) {
            if (feature.getBind() == event.getKey()) {
                feature.state();
            }
        }
        for (Macro macro : macroManager.getMacros()) {
            if (macro.getKey() == event.getKey()) {
                if (mc.player != null && mc.player.getHealth() > 0) {
                    mc.player.connection.sendChat(macro.getValue());
                }
            }
        }
    }
}
