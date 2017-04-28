/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.light.AmbientLight;
import com.jme3.light.Light;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author dz3jr
 */
public class CharacterMovementControl extends BasicControl implements AnimEventListener {
    private Vector3f moveTarget;
    private AnimControl control;
    private AnimChannel channel;
    private Spatial collisionShape;
    private Node npcTarget;
    private boolean movingTowardsNpc;
    
    private final Ray ray;
    private final Vector3f up;
    private final CollisionResults cr;
    private final Node terrain;
    private final Light npcHighlight;
    
    public CharacterMovementControl(Node terrain) {
        this.terrain = terrain;
        moveTarget = new Vector3f();//spatial.getWorldTranslation();
        ray = new Ray(Vector3f.ZERO.clone(), new Vector3f(0, -3, 0));
        up = new Vector3f(0, 50, 0);
        cr = new CollisionResults();
        Sphere collisionSphere = new Sphere(10, 10, 0.75f);
        collisionShape = new Geometry("collisionSphere", collisionSphere);
        movingTowardsNpc = false;
        npcHighlight = new AmbientLight(ColorRGBA.White);
    }
    
    public void moveTowardsTarget(Vector3f moveTarget) {
        this.moveTarget = moveTarget;
        Vector3f lookVector = new Vector3f(moveTarget.getX(), spatial.getWorldTranslation().getY(), moveTarget.getZ());
        spatial.lookAt(lookVector, Vector3f.UNIT_Y);
        setAnim("Walk");
    }
    
    public void moveTowardsNpc(Node npc) {
        if (npcTarget != null) {
            clearNpcTarget();
        }
        npcTarget = npc;
        npcTarget.addLight(npcHighlight);
        movingTowardsNpc = true;
        moveTowardsTarget(npcTarget.getWorldTranslation());
    }
    
    public void clearNpcTarget() {
        moveTarget = spatial.getLocalTranslation();
        npcTarget.removeLight(npcHighlight);
        npcTarget = null;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        float move = tpf*5.f;
        float distance = spatial.getWorldTranslation().distance(moveTarget);
        Vector3f v1, initialPosition;
        initialPosition = spatial.getLocalTranslation();
        
        if (movingTowardsNpc && distance < 3 && npcTarget != null) {
            clearNpcTarget();
            return;
        }
        
        if (distance > 0.05f && !spatial.getWorldTranslation().equals(moveTarget)){
            v1 = spatial.getWorldTranslation().interpolateLocal(moveTarget, move/distance);

            if (terrain != null) {
                ray.setOrigin(spatial.getWorldTranslation().add(up));
                ray.setLimit(100);
                cr.clear();

                terrain.collideWith(ray, cr);

                if (cr.size() > 0) {
                    if (cr.size() > 1) {
                        moveTarget = initialPosition;
                        return; 
                    }
                    
                    CollisionResult result = cr.getClosestCollision();
                    Vector3f vec = result.getContactPoint();
                    
                    if (vec.getY() < -2) {
                        moveTarget = initialPosition;
                        return;
                    }
                    
                    v1.setY(vec.getY());
                }
            }
            
            spatial.setLocalTranslation(v1);
        }
        
        if (!isMoving()) {
            setAnim("Stand");
        }
    }
    
    public boolean isMoving() {
        return spatial.getWorldTranslation().distance(moveTarget) > 0.05f;
    }
    
    public void setAnim(String name) {
        if ("Walk".equals(name) || "Stand".equals(name)) {
            if (channel.getAnimationName() != null && channel.getAnimationName().equals(name))
                return;
            channel.setAnim(name);
            channel.setLoopMode(LoopMode.Loop);
        }
    }
    
    public void initAnimations() {
        Node n = (Node)spatial;
        control = n.getChild("Bandit1").getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        setAnim("Stand");
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        
    }
}
