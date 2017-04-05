/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author dz3jr
 */
public class Player implements IngameCharacter {
    private final Spatial shape;
    
    private Vector3f position;
    private Vector3f moveTarget;
    private Node node;

    public Player(Vector3f position, Spatial shape) {
        this.position = position;
        moveTarget = position;
        this.shape = shape;
       
        node = new Node("player");
        node.attachChild(shape);
    }
    
    @Override
    public Vector3f getPostion() {
        return position;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        position = new Vector3f(x, y, z);
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
        this.moveTarget = moveTarget;
        node.lookAt(moveTarget, Vector3f.UNIT_Y);
    }
    
    public void moveUpdate(float tpf) {
        float move = tpf*5.f;
        float distance = position.distance(moveTarget);
        
        Vector3f vector;
        if (distance > 0.05f && !node.getWorldTranslation().equals(moveTarget)){
            vector = position.interpolateLocal(moveTarget, move/distance);
            node.setLocalTranslation(vector);
        }
      
        position = node.getWorldTranslation();
    }
}
