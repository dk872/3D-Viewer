package com.mygdx.my3dviewer.android

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.VertexAttributes.Usage
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute

abstract class Model {
    protected var model: Model? = null
    var instance: ModelInstance? = null
    protected val material = Material(ColorAttribute.createDiffuse(defaultColor))
    protected val usage = (Usage.Position or Usage.Normal or Usage.TextureCoordinates).toLong()

    companion object {
        var defaultColor: Color = Color.WHITE
        var defaultTexture: Texture? = null
    }

    private var sizeMultiplier = 1f
    private var rotationAngle = 1f

    abstract fun create()

    open fun render(batch: ModelBatch, environment: com.badlogic.gdx.graphics.g3d.Environment,
                    isRotating: Boolean) {
        instance?.let {
            it.transform.setToScaling(sizeMultiplier, sizeMultiplier, sizeMultiplier)
            if (isRotating) {
                rotate()
            }
            batch.render(it, environment)
        }
    }

    open fun rotate() {
        val rotatingSpeed = 30f

        rotationAngle += Gdx.graphics.deltaTime * rotatingSpeed
        instance?.transform?.rotate(0f, 1f, 0f, rotationAngle)
    }

    open fun changeSize(multiplier: Float) {
        val minSizeMultiplier = 0.2f
        val maxSizeMultiplier = 1.3f
        val newSize = sizeMultiplier * multiplier

        if (newSize in minSizeMultiplier..maxSizeMultiplier) {
            sizeMultiplier = newSize
        }
    }

    open fun changeColor(color: Color) {
        defaultColor = color
        material.set(ColorAttribute.createDiffuse(color))
        instance?.materials?.forEach { it.set(ColorAttribute.createDiffuse(color)) }
    }

    open fun changeTexture(texture: Texture) {
        defaultTexture = texture
        instance?.materials?.forEach { it.set(TextureAttribute.createDiffuse(defaultTexture)) }
    }

    open fun deleteTexture() {
        defaultTexture = null
        instance?.materials?.forEach { material ->
            material.remove(TextureAttribute.Diffuse)
            material.set(ColorAttribute.createDiffuse(defaultColor))
        }
    }

    open fun dispose() {
        model?.dispose()
    }
}
