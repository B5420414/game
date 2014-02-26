package sut.game01.core;

import org.jbox2d.dynamics.World;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import sprite.Zealot;
import sprite.Sprite;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import playn.core.*;

import static playn.core.PlayN.*;

import javax.security.auth.callback.Callback;
import java.awt.*;

public class TestScreen extends Screen{

    private final ScreenStack ss;
    private ImageLayer cLayer;
    private World world;
    private Zealot z = new Zealot(world,800f,800f);

    public TestScreen(ScreenStack ss){
        this.ss = ss;
    }

   @Override
    public void wasAdded() {
        super.wasAdded();
//        Image bgImage = assets().getImage("images/bag.png");
//        bgImage.addCallback(new playn.core.util.Callback<Image>() {
//            @Override
//            public void onSuccess(Image image) {
//            }
//            @Override
//            public void onFailure(Throwable throwable) {
//            }
//        });
//        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
//        layer.add(bgLayer);
        layer.add(z.layer());

    }
    @Override
    public void update(int delta) {
       z.update(delta);
    }
    @Override
    public void paint(Clock clock) {

    }
}
