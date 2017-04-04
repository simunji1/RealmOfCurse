/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author dz3jr
 */
public class Player implements IngameCharacter {
    private Vector3f position;
    private Spatial shape;

    public Player(Vector3f position, Spatial shape) {
        this.position = position;
        
        this.shape = shape;
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
    
}
