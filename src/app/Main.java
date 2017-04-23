package app;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.CollisionResult;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.EdgeFilteringMode;
import com.jme3.system.AppSettings;
import game.Player;

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
        Spatial landscapeShape = assetManager.loadModel("Scenes/villageOutskirts.j3o");
        landscape.attachChild(landscapeShape);
        landscape.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
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
                    Spatial land = landscape.getChild("LAND");
                    land.collideWith(ray, results);
                    
                    if (results.size() > 0) {
                        CollisionResult c = results.getFarthestCollision();
                        player.moveTowardsTarget(c.getContactPoint());
                    }
                }
            }
        };
        
        inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "Click");
        
        Spatial playerSpatial = assetManager.loadModel("Models/Chars/warrior01.j3o");
        playerSpatial.setMaterial(assetManager.loadMaterial("Materials/Bandit1.j3m"));
        
        player = new Player(new Vector3f(0, 0, 0), playerSpatial, landscape);
        
        flyCam.setMoveSpeed(0);
        flyCam.setRotationSpeed(0);
        flyCam.setEnabled(false);
        
        cam.setLocation(player.getPosition().add(0, 16, 14));
        cam.lookAt(player.getPosition().add(0, 0, 0), new Vector3f(0, 1, 0));
        
        rootNode.attachChild(player.getNode());
                
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.2f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White.mult(.6f));
        rootNode.addLight(sun);
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(.7f, .7f, .7f, 1f));
        rootNode.addLight(ambient);
        
        FilterPostProcessor fpp = assetManager.loadFilter("Scenes/water.j3f");//new FilterPostProcessor(assetManager);
        
        DirectionalLightShadowFilter dlsf = new DirectionalLightShadowFilter(assetManager, 4096, 2);
        dlsf.setLight(sun);
        dlsf.setShadowIntensity(0.5f);
        dlsf.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
        fpp.addFilter(dlsf);
        
        SSAOFilter ssaoFilter = new SSAOFilter(1.294f, 10f, 0.33f, 0.61f);
        //fpp.addFilter(ssaoFilter);
        
        viewPort.addProcessor(fpp); 

        //FilterPostProcessor water = assetManager.loadFilter("Scenes/water.j3f");
        //viewPort.addProcessor(water);
        
        /*DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 1024, 3);
        dlsr.setLight(sun);
        dlsr.setLambda(0.55f);
        dlsr.setShadowIntensity(0.4f);
        dlsr.setEdgeFilteringMode(EdgeFilteringMode.Bilinear);
        viewPort.addProcessor(dlsr);*/
    }

    @Override
    public void simpleUpdate(float tpf) {
        //player.moveUpdate(tpf);
        
        cam.setLocation(player.getPosition().add(0, 16, 14));
        cam.lookAt(player.getPosition().add(0, 0, 0), new Vector3f(0, 1, 0));
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
