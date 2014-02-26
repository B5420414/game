package sut.game01.core;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.CanvasImage;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.util.Clock;
import sprite.Zealot;


import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import static playn.core.PlayN.*;

public class GameScreen extends Screen{

    public static float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private World world;
    private final ScreenStack ss;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;
    Zealot z ;


    public GameScreen(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();

        Vec2 gravity = new Vec2(0.0f,10.0f);
        Vec2 gravity2 = new Vec2(0.0f,-10.0f);
        world = new World(gravity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        if(showDebugDraw) {
            CanvasImage image = graphics().createImage(
                    (int) (width / GameScreen.M_PER_PIXEL),
                    (int) (height / GameScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit | DebugDraw.e_jointBit | DebugDraw.e_aabbBit);
            debugDraw.setCamera(0,0,1f / GameScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(2f,height-2),
                new Vec2(width-2f,height-2));
        ground.createFixture(groundShape,0.0f);



        createBox1();
        createBox2();
        z = new Zealot(world,500f,370f);

        layer.add(z.layer());
    }





    private  void createBox1() {
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0,0);

        Body body = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0.5f;
        fd.friction = 0.1f;
        fd.restitution = 1f;
        body.createFixture(fd);
        body.setLinearDamping(0.5f);
        body.setTransform(new Vec2(15f,10f),0);
    }
    private  void createBox2() {
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0,0);

        final Body body = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1f;
        fd.friction = 0.1f;
        fd.restitution = 1f;
        body.createFixture(fd);
        body.setLinearDamping(0.5f);
        body.setTransform(new Vec2(10f,10f),0);
        PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if(event.key()== Key.SPACE){
                    body.applyLinearImpulse(new Vec2(0f,-10f), body.getPosition());

                }
            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent typedEvent) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {

            }
        });
    }



    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f,10,50);
        z.update(delta);
    }
    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        z.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }
}
