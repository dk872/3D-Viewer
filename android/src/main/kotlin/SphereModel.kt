package com.mygdx.my3dviewer.android

import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder

class SphereModel : Model() {
    private val width = 10f
    private val height = 10f
    private val depth = 10f
    private val divisionsU = 20
    private val divisionsV = 20

    override fun create() {
        val modelBuilder = ModelBuilder()

        defaultTexture?.let {
            material.set(TextureAttribute.createDiffuse(it))
        }

        model = modelBuilder.createSphere(width, height, depth, divisionsU, divisionsV,
            material, usage)
        instance = ModelInstance(model)
    }
}
