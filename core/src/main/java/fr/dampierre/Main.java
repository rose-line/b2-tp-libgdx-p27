package fr.dampierre;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
  private SpriteBatch batch;
  private Texture playerTxt;
  private int playerX = 0;
  private int playerY = 0;
  private int playerSize = 30;
  private Texture enemyTxt;
  private int enemyX = 100;
  private int enemyY = 100;
  private int enemySpeed = 1;
  private Vector2 enemyDirection = new Vector2(1, 1);
  private int enemySize = 30;
  private Texture explosionTxt;
  private boolean collisionDetected = false;

  /**
   * La méthode `create()` est appelée UNE SEULE FOIS lorsque l'application
   * démarre. Vous pouvez la voir comme le "constructeur" de la classe de jeu.
   * C'est l'endroit où vous devez initialiser les ressources de votre jeu, telles
   * que les textures, les sons, les polices, etc. Dans cet exemple, nous créons
   * un `SpriteBatch` pour dessiner des sprites et nous chargeons une texture à
   * partir d'un fichier image.
   */
  @Override
  public void create() {
    batch = new SpriteBatch();
    playerTxt = new Texture("eddie.png");
    enemyTxt = new Texture("bomb.png");
    explosionTxt = new Texture("explosion.png");
  }

  /**
   * La méthode `render()` est appelée en continu pour mettre à jour et dessiner
   * le jeu. C'est là que vous devez implémenter la logique de votre jeu, y
   * compris la gestion des entrées, la mise à jour de l'état du jeu et le rendu
   * des graphiques. Dans cet exemple, nous effaçons l'écran avec une couleur de
   * fond, puis nous dessinons l'image à une position spécifique.
   */
  @Override
  public void render() {
    processInput();
    updateGameState();
    renderGame();
  }

  // Traitement des entrées (input utilisateur : clavier, souris...)
  private void processInput() {
    if (Gdx.input.isKeyPressed(Keys.LEFT)) {
      // La touche flèche gauche est enfoncée
      System.out.println("Flèche gauche enfoncée");
      playerX -= 5;
    }
    if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
      // La touche flèche droite est enfoncée
      System.out.println("Flèche droite enfoncée");
      playerX += 5;
    }
    if (Gdx.input.isKeyPressed(Keys.UP)) {
      // La touche flèche haut est enfoncée
      System.out.println("Flèche haut enfoncée");
      playerY += 5;
    }
    if (Gdx.input.isKeyPressed(Keys.DOWN)) {
      // La touche flèche bas est enfoncée
      System.out.println("Flèche bas enfoncée");
      playerY -= 5;
    }
  }

  // Mise à jour de l'état du jeu (déplacer les objets du jeu, vérifier les
  // collisions, vérifier les conditions de victoire/défaite...)
  private void updateGameState() {
    // Empêcher le joueur de sortir de l'écran
    if (playerX < 0) {
      playerX = 0;
    }
    if (playerX > Gdx.graphics.getWidth() - playerSize) {
      playerX = Gdx.graphics.getWidth() - playerSize;
    }
    if (playerY < 0) {
      playerY = 0;
    }
    if (playerY > Gdx.graphics.getHeight() - playerSize) {
      playerY = Gdx.graphics.getHeight() - playerSize;
    }

    // Déplacer l'ennemi en fonction de la direction enemyDirection
    enemyX += enemyDirection.x * enemySpeed;
    enemyY += enemyDirection.y * enemySpeed;

    // Faire rebondir l'ennemi sur les bords de l'écran
    if (enemyX < 0 || enemyX > Gdx.graphics.getWidth() - enemySize) {
      enemyDirection.x = -enemyDirection.x; // inverser la direction horizontale
      enemySpeed += 1.0f; // augmenter la vitesse à chaque rebond
    }
    if (enemyY < 0 || enemyY > Gdx.graphics.getHeight() - enemySize) {
      enemyDirection.y = -enemyDirection.y; // inverser la direction vertical
      enemySpeed += 1.0f; // augmenter la vitesse à chaque rebond
    }

    // Gestion de collision simple entre le joueur et l'ennemi
    Rectangle playerBox = getPlayerBox(); // obtenir la "box" du joueur
    Rectangle enemyBox = getEnemyBox(); // obtenir la "box" de l'obstacle
    if (playerBox.overlaps(enemyBox)) { // elles se chevauchent ?
      // Collision détectée
      System.out.println("Collision détectée !");
      collisionDetected = true;
    }
  }

  private Rectangle getPlayerBox() {
    return new Rectangle(playerX, playerY, playerSize, playerSize);
  }

  private Rectangle getEnemyBox() {
    return new Rectangle(enemyX, enemyY, playerSize, playerSize);
  }

  // Rendu (dessiner finalement l'état actuel du jeu, qui prend en compte les
  // entrées et le changement d'état)
  private void renderGame() {
    ScreenUtils.clear(Color.PINK);
    batch.begin();
    if (collisionDetected) {
      batch.draw(explosionTxt, playerX, playerY, playerSize, playerSize);
    } else {
      batch.draw(playerTxt, playerX, playerY, playerSize, playerSize);
      batch.draw(enemyTxt, enemyX, enemyY, enemySize, enemySize);
    }
    batch.end();
  }

  /**
   * La méthode `dispose()` est appelée lorsque l'application est fermée ou
   * lorsque les ressources ne sont plus nécessaires. C'est l'endroit où vous
   * devez libérer les ressources que vous avez allouées dans la méthode
   * `create()`, telles que les textures, les sons, les polices, etc.
   */
  @Override
  public void dispose() {
    batch.dispose();
    playerTxt.dispose();
  }
}
