package dungeonmania.entities.staticEntity.logicalEntity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.items.Key;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {
    private String logic;
    private int keyID;

    public SwitchDoor() {
        super();
        setName("switch_door");
        super.setInteractable(false);
        setCollision(true);
    }

    public int getKeyID() {
        return keyID;
    }

    public boolean unlockDoor(int keyID) {
        if (keyID == this.keyID || keyID == -1) {
            setCollision(false);
            return true;
        }
        return false;
    }

    public void checkActivated() {
        Position swDoorPos = this.getPosition();
        List<Position> adjacentSwDoor = new ArrayList<>();
        int x = swDoorPos.getX();
        int y = swDoorPos.getY();
        adjacentSwDoor.add(new Position(x-1, y));
        adjacentSwDoor.add(new Position(x+1, y));
        adjacentSwDoor.add(new Position(x, y-1));
        adjacentSwDoor.add(new Position(x, y+1));

        Position boulderpos = null;
        Position switchpos = null;
        int numberActivated = 0;
        
        Game activeGame = GameLauncher.getActiveGame();
        setCollision(true);
        switch (logic) {
            case "or":
                for (Position pos : adjacentSwDoor) {
                    List<Entity> entsInPos = activeGame.getEntitiesinPos(pos);
                    for (Entity entity : entsInPos) {
                        if (entity instanceof Wire) {
                            //If connected to activated wire
                            if (((Wire) entity).isActivated()) {
                                setCollision(false);
                            }
                        }
                        if (entity instanceof Boulder) {
                            boulderpos =  pos;
                        }
                        if (entity instanceof FloorSwitch) {
                            switchpos = pos;
                        }
                        //If wire is connected to an activated floor switch
                        if (boulderpos == switchpos && boulderpos != null) {
                            setCollision(false);
                        }
                    }
                }

            case "and":
                for (Position pos : adjacentSwDoor) {
                    List<Entity> entsInPos = activeGame.getEntitiesinPos(pos);
                    for (Entity entity : entsInPos) {
                        if (entity instanceof Wire) {
                            //If connected to activated wire
                            if (((Wire) entity).isActivated()){
                                numberActivated++;
                            }
                        }
                        if (entity instanceof Boulder) {
                            boulderpos =  pos;
                        }
                        if (entity instanceof FloorSwitch) {
                            switchpos = pos;
                        }
                        //If wire is connected to an activated floor switch
                        if (boulderpos == switchpos && boulderpos != null) {
                            numberActivated++;
                        }
                        if (numberActivated > 1) {
                            setCollision(false);
                        }
                    }
                }
            break;

            case "xor":
                for (Position pos : adjacentSwDoor) {
                List<Entity> entsInPos = activeGame.getEntitiesinPos(pos);
                    for (Entity entity : entsInPos) {
                        if (entity instanceof Wire) {
                            //If connected to activated wire
                            if (((Wire) entity).isActivated()){
                                numberActivated++;
                            }
                            
                        }
                        if (entity instanceof Boulder) {
                            boulderpos =  pos;
                        }
                        if (entity instanceof FloorSwitch) {
                            switchpos = pos;
                        }
                        //If wire is connected to an activated floor switch
                        if (boulderpos == switchpos && boulderpos != null) {
                            numberActivated++;
                        }
                        
                    }
                    
                }
                if (numberActivated == 1) {
                    setCollision(false);
                }
                break;
                

            case "co_and": 
                Position activated1 = null;
                Position activated2 = null;
                Position activated3 = null;
                Position activated4 = null;
                boolean sametick = false;
                for (Position pos : adjacentSwDoor) {
                    List<Entity> entsInPos = activeGame.getEntitiesinPos(pos);
                    for (Entity entity : entsInPos) {
                        if (entity instanceof Wire) {
                            //If connected to activated wire
                            if (((Wire) entity).isActivated()){
                                numberActivated++;
                            }
                            
                        }
                        if (entity instanceof Boulder) {
                            boulderpos =  pos;
                        }
                        if (entity instanceof FloorSwitch) {
                            switchpos = pos;
                        }
                        //If wire is connected to an activated floor switch
                         //If wire is connected to same activated floor switch
                         if (boulderpos == switchpos && boulderpos != null) {
                            if (activated1 == null) {
                                numberActivated++;
                                activated1 = pos;
                            }
                            if ( activated2 == null) {
                               numberActivated++;
                               activated2 = pos;
                            }
                            if (activated3 == null) {
                               numberActivated++;
                               activated3 = pos;
                            }
                            if (activated4 == null) {
                               numberActivated++;
                               activated4 = pos;
                            }
                        }
                        // If same boulder then activated on same tick
                        if ((activated1  == activated2) || (activated1 == activated3) || (activated1 == activated4) || (activated2 == activated3) ||
                            (activated2 == activated4) || (activated3 == activated4)) {
                            sametick = true;
                        }
                    }
                }
                if (numberActivated > 1 && sametick == true) {
                    setCollision(false);
                }
  
        }
    }
    @Override
    public void premove(Player player, Direction direction) {
        List<Item> inventory = player.getInventory();
        for (Item item : inventory) {
            if (item instanceof Key) {
                Key temp = (Key) item;
                if (unlockDoor(temp.getKeyid())) {
                    inventory.remove(item);
                    break;
                }
            }
        }
    }
    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        this.logic = entityConfig.getString("logic");
        this.keyID = entityConfig.getInt("key");
        setPosition(position);
    }
    
}
