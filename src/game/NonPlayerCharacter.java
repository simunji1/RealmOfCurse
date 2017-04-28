/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author dz3jr
 */
public class NonPlayerCharacter extends AbstractCharacter {
    //private CharacterMovementControl mc;

    public NonPlayerCharacter(Vector3f position, Spatial shape, Node npcNode, String npcName, float rotation) {
        this.shape = shape;
        shape.setShadowMode(RenderQueue.ShadowMode.Cast);
        
        node = new Node(npcName);
        node.attachChild(shape);
        node.setLocalTranslation(position);
        node.rotate(0, rotation, 0);
        
        npcNode.attachChild(node);
    }
    
}
