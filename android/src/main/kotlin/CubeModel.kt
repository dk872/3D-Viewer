package com.mygdx.my3dviewer.android

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute

class CubeModel : Model() {
    private val width = 7f
    private val height = 7f
    private val depth = 7f

    override fun create() {
        val modelBuilder = ModelBuilder()

        defaultTexture?.let {
            material.set(TextureAttribute.createDiffuse(it))
        }

        model = modelBuilder.createBox(width, height, depth, material, usage)
        instance = ModelInstance(model)
    }
}
