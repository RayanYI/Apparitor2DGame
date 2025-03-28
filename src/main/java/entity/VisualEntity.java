package entity;

import main.GamePanel;
import sprite.Sprite;
import temp.Drawable;
import math.Vec2;
import temp.Hitbox;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class VisualEntity extends Entity implements Drawable {

    public Map<String, Sprite> sprites;
    public String currentSpriteKey = null;
    public Vec2 screenPosition;
    public boolean drawHitbox;
    //public Map<String,List<temp.Hitbox>> hitboxes;
    public GamePanel gamePanel;

    VisualEntity(Vec2 worldPosition,Vec2 screenPosition, GamePanel gamePanel) {
        super(worldPosition);
        this.sprites = new HashMap<String, Sprite>();
        this.gamePanel = gamePanel;
        this.screenPosition = screenPosition;
    }

    public VisualEntity(Vec2 worldPosition, Vec2 screenPosition, Hitbox hitbox, GamePanel gamePanel, String currentSpriteKey,boolean drawHitbox) {
        super(worldPosition);
        this.sprites = new HashMap<String, Sprite>();
        this.gamePanel = gamePanel;
        this.currentSpriteKey = currentSpriteKey;
        this.screenPosition = screenPosition;
        this.hitbox = hitbox;
        this.drawHitbox = drawHitbox;
    }
    VisualEntity(Vec2 worldPosition,Vec2 screenPosition, Map<String, Sprite> sprites, GamePanel gamePanel) {
        super(worldPosition);
        this.sprites = sprites;
        this.gamePanel = gamePanel;
        this.screenPosition = screenPosition;
    }

    VisualEntity(Vec2 worldPosition,Vec2 screenPosition, Map<String, Sprite> sprites, String currentSpriteKey, GamePanel gamePanel) {
        super(worldPosition);
        this.sprites = sprites;
        setCurrentSpriteKey(currentSpriteKey);
        this.gamePanel = gamePanel;
        this.screenPosition = screenPosition;
    }

    public void addSprite(String key, Sprite sprite) {
        sprites.put(key,sprite);
    }

    public Sprite getSprite(String key) {
        return sprites.getOrDefault(key,null);
    }

    public void setCurrentSpriteKey(String key) {
        if(sprites.containsKey(key)) {
            this.currentSpriteKey = key;
        }else{
            System.out.println("No such key("+key+"), cannot set the current sprite key");
        }
    }

    public Sprite getCurrentSprite() {
        return sprites.get(currentSpriteKey);
    }

    @Override
    public void update(){
        if(currentSpriteKey != null) {
            sprites.get(currentSpriteKey).update();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(currentSpriteKey != null) {
            g2.drawImage(
                    sprites.get(currentSpriteKey).getSprite(),
                    screenPosition.getXInt(),
                    screenPosition.getYInt(),
                    gamePanel.tileSize,
                    gamePanel.tileSize,
                    null
            );

            if(drawHitbox == true) {
                Rectangle hit = new Rectangle(screenPosition.getXInt() + hitbox.getBounds().x, screenPosition.getYInt() + hitbox.getBounds().y, hitbox.getBounds().width, hitbox.getBounds().height);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% opacity
                g2.setColor(Color.BLUE);
                g2.fill(hit);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // 50% opacity
                g2.setColor(Color.RED);
                g2.draw(hit);
            }
        }
    }
}
