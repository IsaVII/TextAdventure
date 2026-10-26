package IsaVII.world;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EnvironmentManager {

    private static final String FOREST_MAP_PATH = "world/forest_map.json";
    private static final String ASCII_ROOT = "world/";

    private final Map<String, EnvironmentNode> nodes;

    public EnvironmentManager() {
        nodes = loadMap(FOREST_MAP_PATH);
    }
    
    private Map<String, EnvironmentNode> loadMap(String path) {
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(path),
                StandardCharsets.UTF_8)) {
            Type mapType = new TypeToken<Map<String, EnvironmentNode>>() {}.getType();
            return new Gson().fromJson(reader, mapType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment map: " + path, e);
        }
    }

    public EnvironmentNode getNode(String id) {
        return nodes.get(id);
    }

    public String loadArt(String artPath) {
        String resourcePath = ASCII_ROOT + artPath;
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ascii art: " + resourcePath, e);
        }
    }
}
