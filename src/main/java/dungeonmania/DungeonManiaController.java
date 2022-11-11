package dungeonmania;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dungeonmania.battles.BattleFacade;
import dungeonmania.entities.CollectableEntity;
import dungeonmania.entities.Entity;
import dungeonmania.entities.MovingEntity;
import dungeonmania.entities.Player;
import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.Goal;
import dungeonmania.map.GraphNode;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ResponseBuilder;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

public class DungeonManiaController {
    private ArrayList<String> savedGameNames = new ArrayList<String>();
    private ArrayList<String> savedGames = new ArrayList<String>();
    private Game game = null;
    // private DungeonResponse lastTickResponse;
    // private int gameCount = 0;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException(dungeonName + " is not a dungeon that exists");
        }

        if (!configs().contains(configName)) {
            throw new IllegalArgumentException(configName + " is not a configuration that exists");
        }

        try {
            GameBuilder builder = new GameBuilder();
            game = builder.setConfigName(configName).setDungeonName(dungeonName).buildGame();
            return ResponseBuilder.getDungeonResponse(game);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.tick(itemUsedId));
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return ResponseBuilder.getDungeonResponse(game.tick(movementDirection));
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        List<String> validBuildables = List.of("bow", "shield", "midnight_armour", "sceptre");
        if (!validBuildables.contains(buildable)) {
            throw new IllegalArgumentException("Only bow, shield, midnight_armour and sceptre can be built");
        }

        return ResponseBuilder.getDungeonResponse(game.build(buildable));
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.interact(entityId));
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        String filename = "src/main/java/dungeonmania/SaveFiles/" + name + ".ser";
        try {
            
            // JSONArray entities = new JSONArray();
            // // Save entities
            // List <Entity> gameEntities = game.getGameEntites();
            // for (Entity e : gameEntities) {
            //     entities.put(e.toJSON());
            // }
            
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(game.getId());
            out.writeObject(game.getName());
            out.writeObject(game.getGoals());
            out.writeObject(new ArrayList<Position> (game.getMap().getNodes().keySet()));
            out.writeObject(new ArrayList<GraphNode> (game.getMap().getNodes().values()));
            out.writeObject(game.getPlayer());
            out.writeObject(game.getBattleFacade());
            out.writeObject(game.getInitialTreasureCount());
            out.writeObject(game.getKilledEnemies());
            out.writeObject(game.getEntityFactory().getRandom());
            out.writeObject(game.isInTick());
            out.writeObject(game.getTickCount());
            // System.out.println("HERE");
            // out.writeObject(new ArrayList<ComparableCallback>(game.getSub()));
            // System.out.println("HEREc");
            // out.writeObject(new ArrayList<ComparableCallback>(game.getAddingSub()));
            // System.out.println("HEREcc");
            out.flush();
            out.close();
            this.savedGames.add(filename);
            System.out.println("Successfully Saved game to: " + filename);
        } catch (Exception x) {
            System.out.println(x);
            System.out.println("Failed to Save Game");
        }
        return ResponseBuilder.getDungeonResponse(game);


        // String fileName = "src/main/java/dungeonmania/datastore.json";
        // Gson gson = new Gson();
        // String jsonStr = gson.toJson(lastTickResponse);
        // JsonObject jsonObj = gson.fromJson(jsonStr, JsonElement.class).getAsJsonObject();
        
        // // jsonObj.addProperty("save_name", name);
        // // jsonObj.addProperty("numEntities", game.getMap().getEntities().size());
        // // jsonObj.addProperty("whichTick", game.getTick());
        // // jsonObj.addProperty("inventoryInvisibilityPotion", game.getPlayer().getInventory().getEntities(InvisibilityPotion.class).size());
        // // jsonObj.addProperty("mapInvisibilityPotion", game.getMap().getEntities(InvisibilityPotion.class).size());
        // // jsonObj.addProperty("inventoryInvincibilityPotion", game.getPlayer().getInventory().getEntities(InvincibilityPotion.class).size());
        // // jsonObj.addProperty("mapInvincibilityPotion", game.getMap().getEntities(InvincibilityPotion.class).size());
        
        // JsonArray jsonEntities = jsonObj.get("entities").getAsJsonArray();
        // JsonArray jsonInventory = jsonObj.get("inventory").getAsJsonArray();

        // List<MovingEntity> movingEntitiesList = new ArrayList<MovingEntity>();
        // List<StaticEntity> staticEntitiesList = new ArrayList<StaticEntity>();
        // List<CollectableEntity> collectableEntitiesList = new ArrayList<CollectableEntity>();
        
        // for (Entity ent : game.getMap().getEntities()) {
        //     if (ent instanceof MovingEntity) {
        //         movingEntitiesList.add((MovingEntity) ent);
        //     } else if (ent instanceof StaticEntity) {
        //         staticEntitiesList.add((StaticEntity) ent);
        //     } else if (ent instanceof CollectableEntity) {
        //         collectableEntitiesList.add((CollectableEntity) ent);
        //     }
        // }

        // for (int i = 0; i < jsonEntities.size(); i++) {
        //     JsonObject jsonEnt = jsonEntities.get(i).getAsJsonObject();
        //     if (jsonEnt.get("type").equals("player")) {
        //         for (MovingEntity ent : movingEntitiesList) {
        //             if (ent instanceof Player) {
        //                 jsonEnt.addProperty("battleStatistics", (((Player) ent).getBattleStatistics());
        //             }
        //         }
        //     }
        // }
        

        // for (InventoryItem ent : game.getPlayer().getInventory().getItems()) {

        // }
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            // System.out.println("DEBUG HERE");
            FileInputStream file = new FileInputStream(name);
            // System.out.println("DEBUG HERE");
            ObjectInputStream in = new ObjectInputStream(file);
            // System.out.prinssdstln("DEBUG HERE");
            game.setId((String) in.readObject());
            // System.out.println("DEBUG HERE");
            game.setName((String) in.readObject());
            game.setGoals((Goal) in.readObject());

            List <Position> mapKeys =  (List <Position>) in.readObject();
            List <GraphNode> graphNodes = (List <GraphNode>) in.readObject();

            System.out.println("LOADING: " + graphNodes);

            Map<Position, GraphNode> newNodes = new HashMap<>();
            for (int i = 0; i < mapKeys.size(); i++) newNodes.put(mapKeys.get(i), graphNodes.get(i));

            game.getMap().setNodes(newNodes);

            game.setPlayer((Player) in.readObject());
            game.setBattleFacade((BattleFacade) in.readObject());
            game.setInitialTreasureCount((int) in.readObject());
            game.setKilledEnemies((int) in.readObject());
            game.getEntityFactory().setRandom((Random) in.readObject());

            game.setIsInTick(((boolean) in.readObject()));
            game.setTickCount((int) in.readObject());

            in.close();
            file.close();
            System.out.println("Loaded game from :" + name);
        } catch (Exception x) {
            System.out.println("Failed to load game");
        }
        game.getMap().init();
        return ResponseBuilder.getDungeonResponse(game);
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return savedGames;
    }

    /**
     * /game/new/generate
     */
    public DungeonResponse generateDungeon(
            int xStart, int yStart, int xEnd, int yEnd, String configName) throws IllegalArgumentException {
        return null;
    }

    /**
     * /game/rewind
     */
    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        return null;
    }

}
