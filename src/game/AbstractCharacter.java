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
public abstract class AbstractCharacter implements IngameCharacter {
    protected Spatial shape;
    protected Node node;
    
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
}
