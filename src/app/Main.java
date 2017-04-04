package app;

import com.jme3.app.SimpleApplication;
import com.jme3.export.binary.BinaryImporter;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
        //this.flyCam.setEnabled(true);
                
        //Spatial landscape = assetManager.loadModel("Models/landscape001.blend");
        
        Player player = new Player(new Vector3f(0, 0, 0), assetManager.loadModel("Models/char01.j3o"));
        
        getCamera().setLocation(player.getPostion().add(15, 16, 15));
        getCamera().lookAt(player.getPostion().add(0, 1, 0), new Vector3f(0, 1, 0));
        
        rootNode.attachChild(player.getShape());
        
        Spatial landscape = assetManager.loadModel("Scenes/newScene.j3o");
        
        //landscape.setLocalTranslation(0, -10, 0);
        
        /** A white, directional light source */ 
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.8f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        
        /** A white ambient light source. */ 
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.33f, 0.33f, 0.33f,1f));
        rootNode.addLight(ambient); 

        rootNode.attachChild(landscape);
        
        /** Load a Node from a .j3o file */
        /*String userHome = System.getProperty("user.home");
        BinaryImporter importer = BinaryImporter.getInstance();
        importer.setAssetManager(assetManager);
        File file = new File(userHome+"Scenes/newScene.j3o");
        try {
            Node loadedNode = (Node)importer.load(file);
          loadedNode.setName("loaded node");
          rootNode.attachChild(loadedNode);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "No saved node loaded.", ex);
        } */

    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
