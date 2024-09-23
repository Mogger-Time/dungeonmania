package dungeonmania.entities.staticEntity.logicalEntity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;
import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private String logic;

    public LightBulb() {
        super();
        setName("light_bulb_off");
        super.setInteractable(false);
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        this.logic = entityConfig.getString("logic");
        setPosition(position);
    }

    public void checkActivated() {
        Position bulbpos = this.getPosition();
        List<Position> adjacentBulb = new ArrayList<>();
        int x = bulbpos.getX();
        int y = bulbpos.getY();
        adjacentBulb.add(new Position(x-1, y));
        adjacentBulb.add(new Position(x+1, y));
        adjacentBulb.add(new Position(x, y-1));
        adjacentBulb.add(new Position(x, y+1));

        Position boulderpos = null;
        Position switchpos = null;
        int numberActivated = 0;
        

        Game activeGame = GameLauncher.getActiveGame();
        setName("light_bulb_off");
        switch (logic) {
            case "or":
                for (Position pos : adjacentBulb) {
                    List<Entity> entsInPos = activeGame.getEntitiesinPos(pos);
                    for (Entity entity : entsInPos) {
                        if (entity instanceof Wire) {
                            //If connected to activated wire
                            if (((Wire) entity).isActivated()) {
                                setName("light_bulb_on");
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
                            setName("light_bulb_on");
                        }                  
                    }
                }

            case "and":
                for (Position pos : adjacentBulb) {
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
                            setName("light_bulb_on");
                        }
                    }
                }
                break;
                

            case "xor":
                for (Position pos : adjacentBulb) {
                    List<Entity> entsInPos = activeGame.getEntitiesinPos(pos);
                    for (Entity entity : entsInPos) {
                        numberActivated = 0;
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
                    setName("light_bulb_on");
                    
                }
                break;
        

             case "co_and": // make the boulder and switch the same
                Position activated1 = null;
                Position activated2 = null;
                Position activated3 = null;
                Position activated4 = null;
                boolean sametick = false;
                 for (Position pos : adjacentBulb) {
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
                    setName("light_bulb_on");
                }
            }
        }

}
