package me.werfrag.pluginStrikeService.Utils;

import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch)
                builder.append("&").append(c);
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String TColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static final Map<Character, String> smallCapsMap = new HashMap<>();

    static {
        // Mappatura per small caps
        smallCapsMap.put('a', "ᴀ");
        smallCapsMap.put('b', "ʙ");
        smallCapsMap.put('c', "ᴄ");
        smallCapsMap.put('d', "ᴅ");
        smallCapsMap.put('e', "ᴇ");
        smallCapsMap.put('f', "ꜰ");
        smallCapsMap.put('g', "ɢ");
        smallCapsMap.put('h', "ʜ");
        smallCapsMap.put('i', "ɪ");
        smallCapsMap.put('j', "ᴊ");
        smallCapsMap.put('k', "ᴋ");
        smallCapsMap.put('l', "ʟ");
        smallCapsMap.put('m', "ᴍ");
        smallCapsMap.put('n', "ɴ");
        smallCapsMap.put('o', "ᴏ");
        smallCapsMap.put('p', "ᴘ");
        smallCapsMap.put('q', "ǫ");
        smallCapsMap.put('r', "ʀ");
        smallCapsMap.put('s', "ꜱ");
        smallCapsMap.put('t', "ᴛ");
        smallCapsMap.put('u', "ᴜ");
        smallCapsMap.put('v', "ᴠ");
        smallCapsMap.put('w', "ᴡ");
        smallCapsMap.put('x', "x");
        smallCapsMap.put('y', "ʏ");
        smallCapsMap.put('z', "ᴢ");

        // Per i caratteri maiuscoli, utilizzare quelli normali
        for (char c = 'A'; c <= 'Z'; c++) {
            smallCapsMap.put(c, Character.toString(c));
        }
    }

    public static String toSmallCaps(String text) {
        StringBuilder smallText = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            smallText.append(smallCapsMap.getOrDefault(c, Character.toString(c)));
        }
        return smallText.toString();
    }
}
