package IsaVII.localization;

import IsaVII.config.GameConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;

public class LocalizationManager {

    private static final String LANG_ROOT = "lang/";

    private static Map<Language, Map<String, String>> textsByLanguage;

    public LocalizationManager() {
        textsByLanguage = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            textsByLanguage.put(language, loadLanguageFile(language));
        }
    }

    private Map<String, String> loadLanguageFile(Language language) {
        String path = LANG_ROOT + language.name().toLowerCase() + ".json";
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(path),
                StandardCharsets.UTF_8)) {
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            return new Gson().fromJson(reader, mapType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load language file: " + path, e);
        }
    }

    // args are applied with String.format, so templates for parameterized keys use %s / %d placeholders.
    public static String getText(String key, Object... args) {
        String template = resolve(key);
        return args.length == 0 ? template : String.format(template, args);
    }

    private static String resolve(String key) {
        Map<String, String> currentTexts = textsByLanguage.get(GameConfig.CURRENT_LANGUAGE);
        String text = currentTexts != null ? currentTexts.get(key) : null;

        if (text == null) {
            Map<String, String> fallbackTexts = textsByLanguage.get(Language.EN);
            text = fallbackTexts != null ? fallbackTexts.get(key) : null;
        }

        return text != null ? text : "[[" + key + "]]";
    }
}
