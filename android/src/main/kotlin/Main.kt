package com.mygdx.my3dviewer.android

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport

class Main(private val filePicker: FilePicker) : ApplicationAdapter() {
    private lateinit var stage: Stage
    private lateinit var modelScreen: ModelScreen

    override fun create() {
        stage = Stage(ScreenViewport())
        modelScreen = ModelScreen(stage, filePicker)
        Gdx.input.inputProcessor = stage
    }

    override fun render() {
        val clearColorRed = 0f
        val clearColorGreen = 0f
        val clearColorBlue = 0f
        val clearColorAlpha = 1f
        val clearFlags = GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT

        Gdx.gl.glClearColor(clearColorRed, clearColorGreen, clearColorBlue, clearColorAlpha)
        Gdx.gl.glClear(clearFlags)
        stage.act()
        stage.draw()
        modelScreen.render()
    }


    override fun dispose() {
        stage.dispose()
        modelScreen.dispose()
    }
}
