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
import controls.CharacterMovementControl;

/**
 *
 * @author dz3jr
 */
public class Player extends AbstractCharacter {
    private CharacterMovementControl mc;

    public Player(Vector3f position, Spatial shape, Node landscape) {
        this.shape = shape;
        shape.setShadowMode(RenderQueue.ShadowMode.Cast);
        
        node = new Node("player");
        node.setLocalTranslation(position);
        node.attachChild(shape);
        
        mc = new CharacterMovementControl(landscape);
        node.addControl(mc);
        mc.initAnimations();
    }
    
    public void moveTowardsTarget(Vector3f moveTarget) {
        mc.moveTowardsTarget(moveTarget);
    }
    
    public void interactWithNpc(Node npc) {
        mc.moveTowardsNpc(npc);
    }
}
