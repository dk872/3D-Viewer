package com.mygdx.my3dviewer.android

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder

class ConeModel : Model() {
    private val width = 7f
    private val height = 14f
    private val depth = 7f
    private val divisions = 20

    override fun create() {
        val modelBuilder = ModelBuilder()

        defaultTexture?.let {
            material.set(TextureAttribute.createDiffuse(it))
        }

        model = modelBuilder.createCone(width, height, depth, divisions, material, usage)
        instance = ModelInstance(model)
    }
}
