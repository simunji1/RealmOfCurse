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
public interface IngameCharacter {
    
    Vector3f getPosition();
    
    void setPosition(float x, float y, float z);
    
    Spatial getShape();
    
    Node getNode();
    
    void setNode(Node node);
}
