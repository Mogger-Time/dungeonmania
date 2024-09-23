package dungeonmania.entities.items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.entities.staticEntity.logicalEntity.Wire;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Bomb extends Item {
    private int range;
    private boolean deployed = false;
    private String logic;

    public Bomb() {
        super();
        setName("bomb");
    }

    public int getRange() {
        return this.range;
    }

    public void deploy() {
        deployed = true;
    }

    public void checkStatus(Game game) {
        if (deployed == false) {
            return;
        }
        List<Entity> above = game.getEntitiesinPos(getPosition().translateBy(Direction.UP));
        List<Entity> down = game.getEntitiesinPos(getPosition().translateBy(Direction.DOWN));
        List<Entity> right = game.getEntitiesinPos(getPosition().translateBy(Direction.RIGHT));
        List<Entity> left = game.getEntitiesinPos(getPosition().translateBy(Direction.LEFT));
        List<List<Entity>> combo = new ArrayList<List<Entity>>();
        combo.add(above);
        combo.add(down);
        combo.add(right);
        combo.add(left);


        if (logic == null) {
            for (List<Entity> direction : combo) {
                boolean boulderfound = false;
                boolean switchfound = false;
                for (Entity entity : direction) {
                    if (entity instanceof Boulder) {
                        boulderfound = true;
                    }
                    if (entity instanceof FloorSwitch) {
                        switchfound = true;
                    }
                }
                if (boulderfound && switchfound) {
                    detonate(game);
                    break;
                }
            }
        }
        else {
            Position bombpos = this.getPosition();
            List<Position> adjacentBomb = new ArrayList<>();
            int x = bombpos.getX();
            int y = bombpos.getY();
            adjacentBomb.add(new Position(x-1, y));
            adjacentBomb.add(new Position(x+1, y));
            adjacentBomb.add(new Position(x, y-1));
            adjacentBomb.add(new Position(x, y+1));

            Position boulderpos = null;
            Position switchpos = null;
            int numberActivated = 0;
            switch (logic) {
                case "or":
                    for (Position pos : adjacentBomb) {
                        List<Entity> entsInPos = game.getEntitiesinPos(pos);
                        for (Entity entity : entsInPos) {
                            if (entity instanceof Wire) {
                                //If connected to activated wire
                                if (((Wire) entity).isActivated()) {
                                    detonate(game);
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
                                detonate(game);
                            }                  
                        }
                    }
    
                case "and":
                    for (Position pos : adjacentBomb) {
                        List<Entity> entsInPos = game.getEntitiesinPos(pos);
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
                                detonate(game);
                            }
                        }
                    }
                    break;
                    
    
                case "xor":
                    for (Position pos : adjacentBomb) {
                        List<Entity> entsInPos = game.getEntitiesinPos(pos);
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
                        detonate(game);
                        
                    }
                    break;
            
    
                 case "co_and": // make the boulder and switch the same
                    Position activated1 = null;
                    Position activated2 = null;
                    Position activated3 = null;
                    Position activated4 = null;
                    boolean sametick = false;
                     for (Position pos : adjacentBomb) {
                        List<Entity> entsInPos = game.getEntitiesinPos(pos);
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
                        detonate(game);
                    }
                }
        }
    }

    public void detonate(Game game) {
        Position position = this.getPosition();
        position = position.translateBy(-range, -range);
        for (int i = 0; i <= (2 * range); i++) {
            for (int j = 0; j <= (2 * range); j++) {
                Position temppos = position.translateBy(i, j);
                List<Entity> blownupentities = game.getEntities().stream().filter(s->(s.getPosition().equals(temppos))).collect(Collectors.toList());;
                for (Entity entity : blownupentities) {
                    game.removeEntity(entity);
                }
            }
        }
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        this.range = entityConfig.getInt("bomb_radius");
    }

    @Override
    public boolean interact(Player player) {
        if (deployed == true) {
            return false;
        } else {
            return super.interact(player);
        }
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }
}
