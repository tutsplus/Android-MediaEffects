package com.hathy.mediaeffects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class EffectsRenderer implements GLSurfaceView.Renderer {

    private Bitmap photo;
    private int photoWidth, photoHeight;

    private EffectContext effectContext;
    private Effect effect;

    private Square square;
    private int textures[] = new int[2];

    public EffectsRenderer(Context context){
        super();
        photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.forest);
        photoWidth = photo.getWidth();
        photoHeight = photo.getHeight();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    private void generateSquare(){
        GLES20.glGenTextures(2, textures, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, photo, 0);
        square = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width, height);
        GLES20.glClearColor(0,0,0,1);
        generateSquare();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if(effectContext==null) {
            effectContext = EffectContext.createWithCurrentGlContext();
        }
        if(effect!=null){
            effect.release();
        }
        brightnessEffect();
        square.draw(textures[1]);
    }

    private void documentaryEffect(){
        EffectFactory factory = effectContext.getFactory();
        effect = factory.createEffect(EffectFactory.EFFECT_DOCUMENTARY);
        effect.apply(textures[0], photoWidth, photoHeight, textures[1]);
    }

    private void grayScaleEffect(){
        EffectFactory factory = effectContext.getFactory();
        effect = factory.createEffect(EffectFactory.EFFECT_GRAYSCALE);
        effect.apply(textures[0], photoWidth, photoHeight, textures[1]);
    }

    private void brightnessEffect(){
        EffectFactory factory = effectContext.getFactory();
        effect = factory.createEffect(EffectFactory.EFFECT_BRIGHTNESS);
        effect.setParameter("brightness", 2f);
        effect.apply(textures[0], photoWidth, photoHeight, textures[1]);
    }
}
