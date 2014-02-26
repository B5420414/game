package sprite;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.GameScreen;

public class Zealot {

    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    private int HP;
    private Body body;


    public Layer layer() {
        return sprite.layer();
    }

    public enum State {
        RUN,DIE,IDLE
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 8;

    public Zealot(final World world, final float x_px,final float y_px) {

        PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {
                if(event.key()== Key.LEFT){
                    body.applyLinearImpulse(new Vec2(-5f,-10f), body.getPosition());

                }
                if(event.key()== Key.RIGHT){
                    body.applyLinearImpulse(new Vec2(5f,-10f), body.getPosition());

                }
                if(event.key()== Key.SPACE){
                    body.applyLinearImpulse(new Vec2(0f,-25f), body.getPosition());

                }
            }


            @Override
            public void onKeyTyped(Keyboard.TypedEvent typedEvent) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {

            }
        });


        sprite = SpriteLoader.getSprite("images/zealot.json");
        sprite.addCallback(new Callback<Sprite>() {

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x_px,y_px+13f);



                body = initPhysicsBody(world,
                        GameScreen.M_PER_PIXEL * x_px,
                        GameScreen.M_PER_PIXEL * y_px);
                hasLoaded = true;



                      }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error Loading Image !!!",cause);
            }
        });
        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                state = State.RUN;
            }
        });
    }

    public void update(int delta) {
        if(!hasLoaded) return;
        e += delta;

        if(e > 150) {
            switch(state) {
                case RUN: offset = 0;
                          if(state == state.RUN){
                              state = state.IDLE;
                          }

                case DIE: offset = 4;

                          break;

                case IDLE: offset = 8;

                          break;
            }
            spriteIndex = offset + ((spriteIndex + 1) % 4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }


    public void paint(Clock clock) {
        if (!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / GameScreen.M_PER_PIXEL) - 10,
                body.getPosition().y / GameScreen.M_PER_PIXEL);



    }
    private Body initPhysicsBody(World world, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        Body body = world.createBody(bodyDef);

        //EdgeShape = new EdgeShape();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56 * GameScreen.M_PER_PIXEL / 2,
                sprite.layer().height()*GameScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        //fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y),0f);
        return body;

    }



}

