package app;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.CollisionResult;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import game.Player;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.acl.WorldGroupImpl;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    Node landscape;
    Player player;
    
    public static void main(String[] args) {
        Main app = new Main();
        
        AppSettings appSettings = new AppSettings(true);
        
        appSettings.put("Width", 1280);
        appSettings.put("Height", 720);
        appSettings.put("Title", "Realm Of Curse");
        appSettings.setFrameRate(60);
        
        app.setSettings(appSettings);
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        
        //landscape
        landscape = new Node();
        
        Spatial landscapeShape = assetManager.loadModel("Scenes/newScene.j3o");
        landscape.attachChild(landscapeShape);
        
        rootNode.attachChild(landscape);
        
        ActionListener actionListener = new ActionListener() {
            
            @Override
            public void onAction(String name, boolean keyPressed, float tpf) {
                
                if (name.equals("Click")) {
                    // 1. Reset results list.
                    CollisionResults results = new CollisionResults();
                    
                    // 2. Aim the ray from mouse pointer.
                    Vector2f click2d = inputManager.getCursorPosition();
                    Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                    Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                    Ray ray = new Ray(click3d, dir);
                    
                    // 3. Collect intersections between Ray and Shootables in results list.
                    landscape.collideWith(ray, results);
                    
                    if (results.size() > 0) {
                        CollisionResult c = results.getFarthestCollision();
                        player.moveTowardsTarget(c.getContactPoint());
                    }
                }
            }
        };
        
        inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "Click");
        
        player = new Player(new Vector3f(0, 0, 0), assetManager.loadModel("Models/warrior01.j3o"));
        
        flyCam.setMoveSpeed(0);
        flyCam.setRotationSpeed(0);
        flyCam.setEnabled(false);
        
        cam.setLocation(player.getPostion().add(0, 16, 14));
        cam.lookAt(player.getPostion().add(0, 0, 0), new Vector3f(0, 1, 0));
        
        rootNode.attachChild(player.getNode());
                
        /** A white, directional light source */ 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.8f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.33f, 0.33f, 0.33f,1f));
        rootNode.addLight(ambient); 
    }

    @Override
    public void simpleUpdate(float tpf) {
        player.moveUpdate(tpf);
        
        cam.setLocation(player.getPostion().add(0, 16, 14));
        cam.lookAt(player.getPostion().add(0, 0, 0), new Vector3f(0, 1, 0));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
