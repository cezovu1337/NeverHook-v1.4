package org.neverhook.client.cmd.impl;

import org.neverhook.client.cmd.CommandAbstract;
import org.neverhook.client.helpers.misc.ChatHelper;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesCommand extends CommandAbstract {

    public AesCommand() {
        super("aes", "aes", ".aes encrypt | decrypt (base64) key text", "aes");
    }

    @Override
    public void execute(String... strings) {
        if (strings.length > 3) {
            if (strings[0].equalsIgnoreCase("aes")) {
                if (strings[1].equalsIgnoreCase("encrypt")) {
                    StringBuilder command = new StringBuilder();
                    for (int i = 4; i < strings.length; ++i) {
                        command.append(strings[i]).append(" ");
                    }
                    String encrypt = encryptAes(command.toString().trim(), strings[3], true);
                    ChatHelper.addChatMessage("Your encrypted text: " + encrypt);
                } else if (strings[1].equalsIgnoreCase("decrypt")) {
                    StringBuilder command = new StringBuilder();
                    for (int i = 3; i < strings.length; ++i) {
                        command.append(strings[i]).append(" ");
                    }
                    String decrypt = decryptAes(command.toString().trim(), strings[2], true);
                    ChatHelper.addChatMessage("Your decrypted text: " + decrypt);
                }
            }
        } else {
            ChatHelper.addChatMessage(this.getUsage());
        }
    }

    public static String encryptAes(String value, String key, boolean base64) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return base64 ? Base64.getEncoder().encodeToString(encrypted) : new String(encrypted);
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
    }

    public static String decryptAes(String encrypted, String key, boolean base64) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec keySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] original = cipher.doFinal(base64 ? Base64.getDecoder().decode(encrypted) : encrypted.getBytes());
            return new String(original);
        } catch (Exception ex) {
            return "Error: " + ex.getMessage();
        }
    }
}
