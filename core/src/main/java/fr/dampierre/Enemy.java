package fr.dampierre;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
  private Texture texture;
  private int x = 100;
  private int y = 100;
  private int speed = 1;
  private Vector2 direction = new Vector2(1, 1);
  private int size = 30;

  public Enemy(String texturePath) {
    texture = new Texture(texturePath);
  }

  public void move() {
    x += direction.x * speed;
    y += direction.y * speed;
  }

  public void bound() {
    if (x < 0 || x > Gdx.graphics.getWidth() - size) {
      direction.x = -direction.x; // inverser la direction horizontale
      speed += 1.0f; // augmenter la vitesse à chaque rebond
    }
    if (y < 0 || y > Gdx.graphics.getHeight() - size) {
      direction.y = -direction.y; // inverser la direction vertical
      speed += 1.0f; // augmenter la vitesse à chaque rebond
    }
  }

  public Rectangle getBox() {
    return new Rectangle(x, y, size, size);
  }

  public void draw(SpriteBatch batch) {
    batch.draw(texture, x, y, size, size);
  }
}
