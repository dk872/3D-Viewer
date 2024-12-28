package com.mygdx.my3dviewer.android

interface FilePicker {
    fun pickFile(onFilePicked: () -> Unit)
}
