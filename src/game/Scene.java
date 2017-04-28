/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author dz3jr
 */
public class Scene {
    private Node landscapeNode;
    private Node npcNode;
    
    private AssetManager assetManager;
    
    public Scene(String filePath, Node rootNode, AssetManager assetManager) {
        landscapeNode = new Node();
        Spatial landscapeShape = assetManager.loadModel("Scenes/villageOutskirts.j3o");
        landscapeNode.attachChild(landscapeShape);
        landscapeNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
        npcNode = new Node();
        
        rootNode.attachChild(landscapeNode);
        landscapeNode.attachChild(npcNode);
        
        this.assetManager = assetManager;
    }
    
    public Node getLandscapeNode() {
        return landscapeNode;
    }
    
    public Node getNpcNode() {
        return npcNode;
    }
    
    public void initNpcs() {
        NonPlayerCharacter arthas = new NonPlayerCharacter(new Vector3f(-27.6f, 0.1f, -11.3f), 
                assetManager.loadModel("Models/Chars/arthas.j3o"), npcNode, "arthas", -1.22f);
        
        NonPlayerCharacter beastmaster = new NonPlayerCharacter(new Vector3f(0.06f, 0f, -15.13f), 
                assetManager.loadModel("Models/Chars/beastmaster.j3o"), npcNode, "beastmaster", -2.268f);
        
        NonPlayerCharacter footman1 = new NonPlayerCharacter(new Vector3f(-55.32f, 0.125f, 3.985f), 
                assetManager.loadModel("Models/Chars/footman.j3o"), npcNode, "footman1", -1.496f);
               
        NonPlayerCharacter footman2 = new NonPlayerCharacter(new Vector3f(-50.696f, 0f, 6.62f), 
                assetManager.loadModel("Models/Chars/footman.j3o"), npcNode, "footman2", -2.77f);
               
        NonPlayerCharacter mage = new NonPlayerCharacter(new Vector3f(-45.534f, 0f, -12.23f), 
                assetManager.loadModel("Models/Chars/mage.j3o"), npcNode, "mage", -1.375f);
               
        NonPlayerCharacter prophet = new NonPlayerCharacter(new Vector3f(-2.605f, 0f, 4.023f), 
                assetManager.loadModel("Models/Chars/prophet.j3o"), npcNode, "prophet", -0.5f);
               
        NonPlayerCharacter sheep1 = new NonPlayerCharacter(new Vector3f(-30.139f, 0.488f, 10.163f), 
                assetManager.loadModel("Models/Chars/sheep.j3o"), npcNode, "sheep1", -1.176f);
               
        NonPlayerCharacter sheep2 = new NonPlayerCharacter(new Vector3f(-26.592f, 0.497f, 2.812f), 
                assetManager.loadModel("Models/Chars/sheep.j3o"), npcNode, "sheep2", -0.783f);
               
        NonPlayerCharacter sheep3 = new NonPlayerCharacter(new Vector3f(-23.916f, -0.044f, 8.737f), 
                assetManager.loadModel("Models/Chars/sheep.j3o"), npcNode, "sheep3", -2.409f);
               
        NonPlayerCharacter villageWoman01 = new NonPlayerCharacter(new Vector3f(8.568f, 0f, 7.317f), 
                assetManager.loadModel("Models/Chars/villageWoman01.j3o"), npcNode, "villageWoman01", -2.793f);
    }
}
