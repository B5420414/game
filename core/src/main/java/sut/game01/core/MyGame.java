package sut.game01.core;

import static playn.core.PlayN.*;
import java.util.ArrayList;
import java.util.List;
import playn.core.*;
import playn.core.util.Clock;
import react.UnitSlot;
import sprite.Zealot;
import tripleplay.game.ScreenStack;
import tripleplay.game.Screen;
import tripleplay.ui.*;
import tripleplay.ui.layout.AxisLayout;

public class MyGame extends Game.Default {

    private ScreenStack ss = new ScreenStack();
    private Clock.Source clock = new Clock.Source(UPDATE_RATE);

  public static final int UPDATE_RATE = 25;

  public MyGame() {
    super(UPDATE_RATE);
  }

  private Image bgImage;
  private Image object1;
  private Image object2;
  private ImageLayer bgLayer;
  private ImageLayer sun;
  private ImageLayer cLayer;
  private float x;
  private float y;
  private float x2=50f;
  private float y2=300f;
  private int i;
  private int j;
  private Root back;
/*
  @Override
  public void init() {
    // create and add background image layer
    bgImage = assets().getImage("images/bag.png");
    bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);

    cloudLayer = graphics().createGroupLayer();
    graphics().rootLayer().add(cloudLayer);
    
      assets().getImage(Cloud.IMAGE);

    object1 = assets().getImage("images/4sun.png");
    sun = graphics().createImageLayer(object1);
    graphics().rootLayer().add(sun);

   object2 = assets().getImage("images/cloud.png");
    cloud = graphics().createImageLayer(object2);
    graphics().rootLayer().add(cloud);
    
    
    cloud.setTranslation(x,y);

    pointer().setListener(new Pointer.Adapter() {
      @Override
      public void onPointerEnd(Pointer.Event event) {
        Cloud pea = new Cloud(cloudLayer, event.x(), event.y());
        peas.add(pea);
        sun.setTranslation(x2,y2);
      }
    });
  }*/

  @Override
    public void init(){
          final Screen test = new TestScreen(ss);

          ss.push(new HomeScreen(ss));

      PlayN.keyboard().setListener(new Keyboard.Listener() {
          @Override
          public void onKeyDown(Keyboard.Event event) {
              if(event.key()== Key.ESCAPE){
                  ss.popTo(new HomeScreen(ss));

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

      ss.update(delta);
     /*for (Cloud pea : peas) {
      pea.update(delta);
    }*/
  }

  @Override
  public void paint(float alpha) {

    clock.paint(alpha);
    ss.paint(clock);
    // the background automatically paints itself, so no need to do anything here!
    /*for (Cloud pea : peas) {
      pea.paint(alpha);
    }

    if(x > 640-240){
      i=1;    
    }
    if(x < -13){
      i=0;
    }
    if(x2 > 640 - 240){
      j=1;
    }
    if(x2 < -13){
      j=0;
    }
    if(i==1){
      x -= 3.0f * alpha;
      cloud.setTranslation(x,y);
    }
    if(i==0){
      x += 3.0f * alpha;
      cloud.setTranslation(x,y);
    }
       
    if(j==1){
      x2 -= 1.5f * alpha;
      sun.setTranslation(x2,y2);
    }
    if(j==0){
      x2 += 1.5f * alpha;
      sun.setTranslation(x2,y2);*/
  }
}