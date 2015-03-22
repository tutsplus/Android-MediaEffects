package com.hathy.mediaeffects;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends Activity {

    private GLSurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setRenderer(new EffectsRenderer(this));
        view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(view);
    }
}
