package net.minecraft.client.gui.screens;

import net.minecraft.resources.Identifier;

import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.function.ObjIntConsumer;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CommonButtons;
import net.minecraft.client.gui.components.FriendsButton;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.friends.FriendsOverlayScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OnlineOptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.Panorama;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;

public class TitleScreen extends Screen {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Component TITLE = Component.translatable("narrator.screen.title");
    private static final Component COPYRIGHT_TEXT = Component.translatable("title.credits");
    private static final String DEMO_LEVEL_ID = "Demo_World";
    private SplashRenderer splash;
    private RealmsNotificationsScreen realmsNotificationsScreen;
    private FriendsButton friends;
    private boolean fading;
    private long fadeInStart;
    private final LogoRenderer logoRenderer;
    private Screen modUpdateNotification;

    public TitleScreen() {
        this(false);
    }

    public TitleScreen(boolean fading) {
        this(fading, null);
    }

    public TitleScreen(boolean fading, LogoRenderer logoRenderer) {
        super(TITLE);
        this.fading = fading;
        this.logoRenderer = Objects.requireNonNullElseGet(logoRenderer, () -> new LogoRenderer(false));
        this.minecraft.gameRenderer.panorama().startSpin();
    }

    private boolean realmsNotificationsEnabled() {
        return this.realmsNotificationsScreen != null;
    }

    @Override
    public void tick() {
        if (this.realmsNotificationsEnabled()) {
            this.realmsNotificationsScreen.tick();
        }

        if (this.minecraft.getPlayerSocialManager().isFriendListEnabled() && this.friends != null) {
            this.friends.refreshIncomingRequestCount();
        }
    }

    public static void registerTextures(TextureManager textureManager) {
        textureManager.registerForNextReload(LogoRenderer.MINECRAFT_LOGO);
        textureManager.registerForNextReload(LogoRenderer.MINECRAFT_EDITION);
        textureManager.registerForNextReload(Panorama.PANORAMA_OVERLAY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    protected void init() {
        int btnW = 110;
        int btnH = 15;
        int btnX = this.width / 2 - 55;
        int startY = this.height / 2 - 10;
        int spacing = 20;

        this.addRenderableWidget(new org.neverhook.client.ui.button.NeverHookMenuButton(btnX, startY, btnW, btnH, "Singleplayer", () -> this.minecraft.gui.setScreen(new SelectWorldScreen(this))));
        this.addRenderableWidget(new org.neverhook.client.ui.button.NeverHookMenuButton(btnX, startY + spacing, btnW, btnH, "Multiplayer", () -> this.minecraft.gui.setScreen(new JoinMultiplayerScreen(this))));
        this.addRenderableWidget(new org.neverhook.client.ui.button.NeverHookMenuButton(btnX, startY + spacing * 2, btnW, btnH, "Options", () -> this.minecraft.gui.setScreen(new OptionsScreen(this, this.minecraft.options, false))));
        this.addRenderableWidget(new org.neverhook.client.ui.button.NeverHookMenuButton(btnX, startY + spacing * 3, btnW, btnH, "Alt Manager", () -> this.minecraft.gui.setScreen(new org.neverhook.client.ui.components.altmanager.GuiAltManager())));
        this.addRenderableWidget(new org.neverhook.client.ui.button.NeverHookMenuButton(btnX, startY + spacing * 4, btnW, btnH, "Quit", () -> this.minecraft.stop()));
    }

    private int getHorizontalPosition(int currentButton, int numberOfButtons, int buttonWidth) {
        int totalWidth = numberOfButtons * buttonWidth + (numberOfButtons - 1) * 4;
        return this.width / 2 - totalWidth / 2 + (currentButton - 1) * (buttonWidth + 4);
    }

    private int createNormalMenuOptions(int topPos, int spacing) {
        Button singleplayerButton = this.addRenderableWidget(
            Button.builder(Component.translatable("menu.singleplayer"), var1x -> this.minecraft.gui.setScreen(new SelectWorldScreen(this)))
                .bounds(this.width / 2 - 100, topPos, 200, 20)
                .build()
        );
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            this.addRenderableWidget(
                Button.builder(Component.literal("TW"), var1x -> CreateWorldScreen.testWorld(this.minecraft, () -> this.minecraft.gui.setScreen(this)))
                    .bounds(singleplayerButton.getX() + singleplayerButton.getWidth() + 2, topPos, 20, 20)
                    .build()
            );
        }

        Component multiplayerDisabledReason = this.getMultiplayerDisabledReason();
        boolean multiplayerAllowed = multiplayerDisabledReason == null;
        Tooltip tooltip = multiplayerDisabledReason != null ? Tooltip.create(multiplayerDisabledReason) : null;
        int var7;
        this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), button -> {
            Screen screen = this.minecraft.options.skipMultiplayerWarning ? new JoinMultiplayerScreen(this) : new SafetyScreen(this);
            this.minecraft.gui.setScreen(screen);
        }).bounds(this.width / 2 - 100, var7 = topPos + spacing, 200, 20).tooltip(tooltip).build()).active = multiplayerAllowed;
        boolean forge = Reflector.ModListScreen_Constructor.exists();
        int realmsX = forge ? this.width / 2 + 2 : this.width / 2 - 100;
        int realmsWidth = forge ? 98 : 200;
        this.addRenderableWidget(
                Button.builder(Component.literal("AltManager"), var1x -> this.minecraft.gui.setScreen(new org.neverhook.client.ui.components.altmanager.GuiAltManager()))
                    .bounds(realmsX, topPos = var7 + spacing, realmsWidth, 20)
                    .tooltip(tooltip)
                    .build()
            )
            .active = true;
        return topPos;
    }

    private Component getMultiplayerDisabledReason() {
        if (this.minecraft.allowsMultiplayer()) {
            return null;
        } else if (this.minecraft.isNameBanned()) {
            return Component.translatable("title.multiplayer.disabled.banned.name");
        } else {
            BanDetails multiplayerBan = this.minecraft.multiplayerBan();
            if (multiplayerBan != null) {
                return multiplayerBan.expires() != null
                    ? Component.translatable("title.multiplayer.disabled.banned.temporary")
                    : Component.translatable("title.multiplayer.disabled.banned.permanent");
            } else {
                return Component.translatable("title.multiplayer.disabled");
            }
        }
    }

    private int createDemoMenuOptions(int topPos, int spacing) {
        boolean demoWorldPresent = this.checkDemoWorldPresence();
        this.addRenderableWidget(
            Button.builder(
                    Component.translatable("menu.playdemo"),
                    button -> {
                        if (demoWorldPresent) {
                            this.minecraft.createWorldOpenFlows().openWorld("Demo_World", () -> this.minecraft.gui.setScreen(this));
                        } else {
                            this.minecraft
                                .createWorldOpenFlows()
                                .createFreshLevel(
                                    "Demo_World", MinecraftServer.DEMO_SETTINGS, WorldOptions.DEMO_OPTIONS, WorldPresets::createNormalWorldDimensions, this
                                );
                        }
                    }
                )
                .bounds(this.width / 2 - 100, topPos, 200, 20)
                .build()
        );
        int var5;
        Button resetDemoButton = this.addRenderableWidget(
            Button.builder(
                    Component.translatable("menu.resetdemo"),
                    button -> {
                        LevelStorageSource levelSource = this.minecraft.getLevelSource();

                        try (LevelStorageSource.LevelStorageAccess levelAccess = levelSource.createAccess("Demo_World")) {
                            if (levelAccess.hasWorldData()) {
                                this.minecraft
                                    .gui
                                    .setScreen(
                                        new ConfirmScreen(
                                            this::confirmDemo,
                                            Component.translatable("selectWorld.deleteQuestion"),
                                            Component.translatable("selectWorld.deleteWarning", MinecraftServer.DEMO_SETTINGS.levelName()),
                                            Component.translatable("selectWorld.deleteButton"),
                                            CommonComponents.GUI_CANCEL
                                        )
                                    );
                            }
                        } catch (IOException e) {
                            SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
                            LOGGER.warn("Failed to access demo world", e);
                        }
                    }
                )
                .bounds(this.width / 2 - 100, var5 = topPos + spacing, 200, 20)
                .build()
        );
        resetDemoButton.active = demoWorldPresent;
        return var5;
    }

    private boolean checkDemoWorldPresence() {
        try (LevelStorageSource.LevelStorageAccess levelSource = this.minecraft.getLevelSource().createAccess("Demo_World")) {
            return levelSource.hasWorldData();
        } catch (IOException e) {
            SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
            LOGGER.warn("Failed to read demo world data", e);
            return false;
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }

        float widgetFade = 1.0F;
        GlStateManager._disableDepthTest();
        if (this.fading) {
            float fade = (float)(Util.getMillis() - this.fadeInStart) / 2000.0F;
            if (fade > 1.0F) {
                this.fading = false;
            } else {
                fade = Mth.clamp(fade, 0.0F, 1.0F);
                widgetFade = Mth.clampedMap(fade, 0.5F, 1.0F, 0.0F, 1.0F);
            }

            this.fadeWidgets(widgetFade);
        }

        graphics.fill(0, 0, this.width, this.height, 0xFF161616);

        for (int i = 0; i < this.width; i++) {
            graphics.fill(i, 0, i + 1, 2, org.neverhook.client.helpers.palette.PaletteHelper.rainbow(i * 7, 0.5F, 1).getRGB());
        }

        Identifier logo = Identifier.fromNamespaceAndPath("minecraft", "neverhook/logo.png");
        int logoW = 60;
        int logoH = 70;
        int logoX = (this.width - logoW) / 2;
        int logoY = this.height / 2 - 140;
        graphics.blit(logo, logoX, logoY, logoX + logoW, logoY + logoH, 0.0F, 1.0F, 0.0F, 1.0F, -1);

        super.extractRenderState(graphics, mouseX, mouseY, a);

        if (this.minecraft.robotoRegularFontRender != null) {
            this.minecraft.robotoRegularFontRender.drawStringWithShadow("NeverHook Software (v" + org.neverhook.client.NeverHook.instance.version + ")", 4, this.height - 12, -1);
            String userText = "Hello, https://t.me/oldhouse1337";
            this.minecraft.robotoRegularFontRender.drawStringWithShadow(userText, this.width - this.minecraft.robotoRegularFontRender.getStringWidth(userText) - 4, this.height - 12, -1);
        }
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return super.mouseClicked(event, doubleClick)
            ? true
            : this.realmsNotificationsEnabled() && this.realmsNotificationsScreen.mouseClicked(event, doubleClick);
    }

    @Override
    public void removed() {
        if (this.realmsNotificationsScreen != null) {
            this.realmsNotificationsScreen.removed();
        }
    }

    @Override
    public void added() {
        super.added();
        if (this.realmsNotificationsScreen != null) {
            this.realmsNotificationsScreen.added();
        }
    }

    private void confirmDemo(boolean result) {
        if (result) {
            try (LevelStorageSource.LevelStorageAccess levelSource = this.minecraft.getLevelSource().createAccess("Demo_World")) {
                levelSource.deleteLevel();
            } catch (IOException e) {
                SystemToast.onWorldDeleteFailure(this.minecraft, "Demo_World");
                LOGGER.warn("Failed to delete demo world", e);
            }
        }

        this.minecraft.gui.setScreen(this);
    }

    @Override
    public boolean canInterruptWithAnotherScreen() {
        return true;
    }
}
