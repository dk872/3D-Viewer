package com.mygdx.my3dviewer.android

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.badlogic.gdx.Gdx

class AndroidLauncher : AndroidApplication(), FilePicker {
    private val pickFileRequestCode = 1001
    private var onFilePickedCallback: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = AndroidApplicationConfiguration()
        initialize(Main(this), config)
    }

    override fun pickFile(onFilePicked: () -> Unit) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        onFilePickedCallback = onFilePicked
        intent.type = "image/jpeg"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, pickFileRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == pickFileRequestCode && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                handleFileUri(uri)
                onFilePickedCallback?.invoke()
                onFilePickedCallback = null
            }
        }
    }

    private fun handleFileUri(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)

        inputStream?.let {
            val fileHandle = Gdx.files.local("upload_texture.jpg")
            fileHandle.writeBytes(inputStream.readBytes(), false)
            inputStream.close()
        }
    }
}
