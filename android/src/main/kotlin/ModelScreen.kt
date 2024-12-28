package com.mygdx.my3dviewer.android

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.environment.PointLight
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.graphics.Texture

class ModelScreen(stage: Stage, private val filePicker: FilePicker) {
    private val modelBatch = ModelBatch()
    private val camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(),
        Gdx.graphics.height.toFloat())
    private var currentModel: Model? = null
    private val environment = com.badlogic.gdx.graphics.g3d.Environment()
    private val buttons = ControlPanel(
        stage,
        onShapeSelected = { shape -> showShape(shape) },
        onIncreaseSize = { onIncreaseSize() },
        onDecreaseSize = { onDecreaseSize() },
        onStartRotation = { onStartRotation() },
        onStopRotation = { onStopRotation() },
        onChangeColor = { color -> changeShapeColor(color) },
        onDeleteTexture = { deleteTexture() },
        onUploadTexture = { uploadTexture() }
    )

    private var lastX = 0f
    private var lastY = 0f
    private var isDragging = false
    private var isRotating = false
    private var isTextureUpdatePending = false

    init {
        setCamera()
        addLights()
        buttons.createButtons()
    }

    private fun setCamera() {
        val initialCameraPosition = Vector3(15f, 15f, 15f)
        val initialCameraLookAt = Vector3(0f, 0f, 0f)
        val nearDistance = 1f
        val farDistance = 100f

        camera.position.set(initialCameraPosition)
        camera.lookAt(initialCameraLookAt)
        camera.near = nearDistance
        camera.far = farDistance
        camera.update()
    }

    private fun addLights() {
        val directionalLight1 = DirectionalLight().apply {
            set(Color.WHITE, 0.8f, -1f, -2f)
        }
        environment.add(directionalLight1)

        val directionalLight2 = DirectionalLight().apply {
            set(Color.WHITE, -0.8f, 1f, 2f)
        }
        environment.add(directionalLight2)

        val pointLight = PointLight().apply {
            set(Color(1f, 1f, 1f, 1f), 10f, 10f, 10f, 5f)
        }
        environment.add(pointLight)
    }

    private fun showShape(model: Model) {
        currentModel?.dispose()
        currentModel = model
        currentModel?.create()
    }

    private fun changeShapeColor(color: Color) {
        currentModel?.changeColor(color)
    }

    private fun uploadTexture() {
        filePicker.pickFile {
            isTextureUpdatePending = true
        }
    }

    private fun setTextureFromFile() {
        isTextureUpdatePending = false
        val file = Gdx.files.local("upload_texture.jpg")
        val texture = Texture(file)
        currentModel?.changeTexture(texture)
    }

    private fun deleteTexture() {
        currentModel?.deleteTexture()
    }

    private fun onIncreaseSize() {
        val increaseFactor = 1.1f
        currentModel?.changeSize(increaseFactor)
    }

    private fun onDecreaseSize() {
        val decreaseFactor = 0.9f
        currentModel?.changeSize(decreaseFactor)
    }

    private fun onStartRotation() {
        isRotating = true
    }

    private fun onStopRotation() {
        isRotating = false
    }

    private fun handleInput() {
        val deltaX = Gdx.input.x - lastX
        val deltaY = Gdx.input.y - lastY
        val rotationSpeed = 0.2f

        if (!Gdx.input.isTouched) {
            isDragging = false
            return
        }

        if (!isDragging) {
            updateLastInputPosition()
            isDragging = true
            return
        }

        camera.rotateAround(Vector3.Zero, Vector3.Y, -deltaX * rotationSpeed)
        camera.rotateAround(Vector3.Zero, camera.direction.cpy().crs(camera.up),
            deltaY * rotationSpeed)

        updateLastInputPosition()
        camera.update()
    }

    private fun updateLastInputPosition() {
        lastX = Gdx.input.x.toFloat()
        lastY = Gdx.input.y.toFloat()
    }

    fun render() {
        handleInput()

        if (isTextureUpdatePending) {
            setTextureFromFile()
            isTextureUpdatePending = false
        }

        if (isRotating) {
            currentModel?.rotate()
        }

        modelBatch.begin(camera)
        currentModel?.render(modelBatch, environment, isRotating)
        modelBatch.end()
    }

    fun dispose() {
        modelBatch.dispose()
        currentModel?.dispose()
    }
}
