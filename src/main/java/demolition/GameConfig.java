package demolition;

import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * class for deserializing config json
 * 
 */
public class GameConfig {

    public static GameConfig fromJSON(JSONObject json, App app){
        JSONArray jsonArray = json.getJSONArray("levels");
        Level[] levels = new Level[jsonArray.size()];
        for (int i = 0; i < levels.length; i++) {
            JSONObject l = (JSONObject) jsonArray.get(i);
            levels[i]= new Level(l.getString("path"),l.getInt("time"), app);
        }

        return new GameConfig(
            json.getInt("lives"),
            levels
        );

    }


    int lives;
    Level [] levels;

    public GameConfig(int lives, Level[] levels) {
        this.lives = lives;
        this.levels = levels;
    }

    public int getLives() {
        return lives;
    }

    public Level[] getLevels() {
        return levels;
    }
}
