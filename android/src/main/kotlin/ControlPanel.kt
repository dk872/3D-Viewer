package com.mygdx.my3dviewer.android

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class ControlPanel(
    private val stage: Stage,
    private val onShapeSelected: (Model) -> Unit,
    private val onIncreaseSize: () -> Unit,
    private val onDecreaseSize: () -> Unit,
    private val onStartRotation: () -> Unit,
    private val onStopRotation: () -> Unit,
    private val onChangeColor: (Color) -> Unit,
    private val onDeleteTexture: () -> Unit,
    private val onUploadTexture: () -> Unit
) {
    private val buttonSize = 150f
    private val smallButtonSize = buttonSize / 2f
    private var isColorButtonVisible: Boolean = false
    private var isTextureButtonVisible: Boolean = false

    private val textures = mapOf(
        "cube" to Texture("images/cube.png"),
        "cubeSelected" to Texture("images/cube_selected.png"),
        "sphere" to Texture("images/sphere.png"),
        "sphereSelected" to Texture("images/sphere_selected.png"),
        "cylinder" to Texture("images/cylinder.png"),
        "cylinderSelected" to Texture("images/cylinder_selected.png"),
        "cone" to Texture("images/cone.png"),
        "coneSelected" to Texture("images/cone_selected.png"),
        "increase" to Texture("images/increase.png"),
        "decrease" to Texture("images/decrease.png"),
        "rotate" to Texture("images/rotate.png"),
        "rotateSelected" to Texture("images/rotate_selected.png"),
        "stop" to Texture("images/stop.png"),
        "color" to Texture("images/color.png"),
        "white" to Texture("images/white.png"),
        "yellow" to Texture("images/yellow.png"),
        "blue" to Texture("images/blue.png"),
        "red" to Texture("images/red.png"),
        "green" to Texture("images/green.png"),
        "texture" to Texture("images/texture.png"),
        "delete" to Texture("images/clear.png"),
        "upload" to Texture("images/upload.png")
    )

    private lateinit var cubeButton: ImageButton
    private lateinit var sphereButton: ImageButton
    private lateinit var cylinderButton: ImageButton
    private lateinit var coneButton: ImageButton
    private lateinit var increaseButton: ImageButton
    private lateinit var decreaseButton: ImageButton
    private lateinit var rotateButton: ImageButton
    private lateinit var stopButton: ImageButton
    private lateinit var colorButton: ImageButton
    private lateinit var whiteButton: ImageButton
    private lateinit var yellowButton: ImageButton
    private lateinit var blueButton: ImageButton
    private lateinit var redButton: ImageButton
    private lateinit var greenButton: ImageButton
    private lateinit var texturePickerButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var uploadButton: ImageButton

    fun createButtons() {
        createShapeButtons()
        createControlButtons()
        createColorButtons()
        createTextureButtons()

        positionButtons()
        addButtonsToStage()
        setColorButtonsVisibility(isColorButtonVisible)
        setTextureButtonsVisibility(isTextureButtonVisible)
    }

    private fun createShapeButtons() {
        cubeButton = createImageButton("cube", buttonSize) {
            onShapeSelected(CubeModel())
            updateButtonIcons(cubeButton)
        }

        sphereButton = createImageButton("sphere", buttonSize) {
            onShapeSelected(SphereModel())
            updateButtonIcons(sphereButton)
        }

        cylinderButton = createImageButton("cylinder", buttonSize) {
            onShapeSelected(CylinderModel())
            updateButtonIcons(cylinderButton)
        }

        coneButton = createImageButton("cone", buttonSize) {
            onShapeSelected(ConeModel())
            updateButtonIcons(coneButton)
        }
    }

    private fun createControlButtons() {
        increaseButton = createImageButton("increase", buttonSize) {
            onIncreaseSize()
        }

        decreaseButton = createImageButton("decrease", buttonSize) {
            onDecreaseSize()
        }

        rotateButton = createImageButton("rotate", buttonSize) {
            onStartRotation()
            updateRotateButtonIcon(true)
        }

        stopButton = createImageButton("stop", buttonSize) {
            onStopRotation()
            updateRotateButtonIcon(false)
        }

        colorButton = createImageButton("color", buttonSize) {
            setColorButtonsVisibility(!isColorButtonVisible)
        }

        texturePickerButton = createImageButton("texture", buttonSize) {
            setTextureButtonsVisibility(!isTextureButtonVisible)
        }
    }

    private fun createColorButtons() {
        whiteButton = createColorButton("white", Color.WHITE)
        yellowButton = createColorButton("yellow", Color.YELLOW)
        blueButton = createColorButton("blue", Color.BLUE)
        redButton = createColorButton("red", Color.RED)
        greenButton = createColorButton("green", Color.GREEN)
    }

    private fun createTextureButtons() {
        deleteButton = createImageButton("delete", smallButtonSize) {
            onDeleteTexture()
            setTextureButtonsVisibility(!isTextureButtonVisible)
        }

        uploadButton = createImageButton("upload", smallButtonSize) {
            onUploadTexture()
            setTextureButtonsVisibility(!isTextureButtonVisible)
        }
    }

    private fun createImageButton(textureKey: String, size: Float, onClick: () -> Unit): ImageButton {
        val drawable = TextureRegionDrawable(textures[textureKey])
        val button = ImageButton(drawable).apply {
            drawable.minWidth = size
            drawable.minHeight = size
            setSize(size, size)
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    onClick()
                }
            })
        }
        return button
    }

    private fun createColorButton(textureKey: String, color: Color): ImageButton {
        return createImageButton(textureKey, smallButtonSize) {
            onChangeColor(color)
            setColorButtonsVisibility(!isColorButtonVisible)
        }
    }

    private fun updateButtonIcons(selectedButton: ImageButton) {
        val buttonTextures = mapOf(
            cubeButton to "cubeSelected",
            sphereButton to "sphereSelected",
            cylinderButton to "cylinderSelected",
            coneButton to "coneSelected"
        )

        buttonTextures[selectedButton]?.let {
            selectedButton.style.imageUp = TextureRegionDrawable(textures[it])
        }
    }

    private fun updateRotateButtonIcon(isRotating: Boolean) {
        rotateButton.style.imageUp = TextureRegionDrawable(
            if (isRotating) textures["rotateSelected"] else textures["rotate"]
        )
    }

    private fun positionButtons() {
        val bottomPadding = 20f
        val buttonPadding = 20f
        val topPadding = Gdx.graphics.height - buttonSize - buttonPadding
        val centerX = stage.width / 2
        val buttonSpacing = buttonSize + buttonPadding
        val startBottomX = centerX - buttonSpacing * 2
        val startTopX = startBottomX - 160f
        val colorButtonOffsetY = 80f
        val colorButtonY = topPadding - buttonSize - buttonPadding + colorButtonOffsetY

        val textureButtonOffsetX = 8.75f
        val textureButtonExtraOffsetX = 1.0f

        val buttonPositions = listOf(
            Pair(cubeButton, startBottomX),
            Pair(sphereButton, startBottomX + buttonSize + buttonPadding),
            Pair(cylinderButton, startBottomX + (buttonSize + buttonPadding) * 2),
            Pair(coneButton, startBottomX + (buttonSize + buttonPadding) * 3),
            Pair(colorButton, startTopX),
            Pair(increaseButton, startTopX + buttonSize + buttonPadding),
            Pair(decreaseButton, startTopX + (buttonSize + buttonPadding) * 2),
            Pair(rotateButton, startTopX + (buttonSize + buttonPadding) * 3),
            Pair(stopButton, startTopX + (buttonSize + buttonPadding) * 4),
            Pair(texturePickerButton, startTopX + (buttonSize + buttonPadding) * 5)
        )

        val colorButtonPositions = listOf(
            Pair(whiteButton, startTopX),
            Pair(yellowButton, startTopX + smallButtonSize + buttonPadding),
            Pair(blueButton, startTopX + (smallButtonSize + buttonPadding) * 2),
            Pair(redButton, startTopX + (smallButtonSize + buttonPadding) * 3),
            Pair(greenButton, startTopX + (smallButtonSize + buttonPadding) * 4)
        )

        val textureButtonPositions = listOf(
            Pair(deleteButton, startTopX + (smallButtonSize + buttonPadding) * textureButtonOffsetX),
            Pair(uploadButton, startTopX + (smallButtonSize + buttonPadding) *
                (textureButtonOffsetX + textureButtonExtraOffsetX))
        )

        buttonPositions.forEachIndexed { index, (button, xPos) ->
            button.setPosition(
                xPos,
                if (index < 4) bottomPadding else topPadding
            )
        }

        colorButtonPositions.forEachIndexed { _, (button, xPos) ->
            button.setPosition(xPos, colorButtonY)
        }

        textureButtonPositions.forEach { (button, xPos) ->
            button.setPosition(xPos, colorButtonY)
        }
    }

    private fun addButtonsToStage() {
        val buttons = listOf(
            cubeButton, sphereButton, cylinderButton, coneButton, increaseButton, decreaseButton,
            rotateButton, stopButton, colorButton, whiteButton, yellowButton, blueButton,
            redButton, greenButton, texturePickerButton, deleteButton, uploadButton
        )
        buttons.forEach { stage.addActor(it) }
    }

    private fun setColorButtonsVisibility(visible: Boolean) {
        listOf(whiteButton, yellowButton, blueButton, redButton, greenButton).forEach {
            it.isVisible = visible
        }
        isColorButtonVisible = visible
    }

    private fun setTextureButtonsVisibility(visible: Boolean) {
        deleteButton.isVisible = visible
        uploadButton.isVisible = visible
        isTextureButtonVisible = visible
    }
}
