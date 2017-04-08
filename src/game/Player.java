/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import controls.CharacterMovementControl;

/**
 *
 * @author dz3jr
 */
public class Player implements IngameCharacter {
    private final Spatial shape;
    
    //private Vector3f position;
    private Node node;
    private CharacterMovementControl mc;

    public Player(Vector3f position, Spatial shape, Node landscape) {
        //this.position = position;
        //moveTarget = position;
        this.shape = shape;
        
        node = new Node("player");
        node.setLocalTranslation(position);
        node.attachChild(shape);
        
        //node.addControl(new TerrainHeightControl(landscape));
        mc = new CharacterMovementControl(landscape);
        node.addControl(mc);
        mc.initAnimations();
    }
    
    @Override
    public Vector3f getPosition() {
        return node.getWorldTranslation();
    }

    @Override
    public void setPosition(float x, float y, float z) {
        node.setLocalTranslation(new Vector3f(x, y, z).subtract(node.getWorldTranslation()));
    }

    @Override
    public Spatial getShape() {
        return shape;
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }
    
    public void moveTowardsTarget(Vector3f moveTarget) {
        mc.moveTowardsTarget(moveTarget);
    }
}
