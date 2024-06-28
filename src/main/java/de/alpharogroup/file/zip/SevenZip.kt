/**
 * ====
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ====
 *
 * The MIT License
 *
 * Copyright (C) 2020 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.file.zip

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.commons.compress.archivers.sevenz.SevenZFile
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile

object SevenZip {

  @JvmStatic
  fun zipFiles(sevenZipFile: File, dirToZip: String, vararg files: File) {
    SevenZOutputFile(sevenZipFile).use { sevenZOutputFile ->
      for (file in files) {
        addFile(sevenZOutputFile, dirToZip, file)
      }
    }
  }

  @JvmStatic
  fun extract(seveZipFile: File, destination: File, password: CharArray) {
    val sevenZFile = SevenZFile(seveZipFile, password)
    extract(sevenZFile, destination)
  }

  @JvmStatic
  fun extract(seveZipFile: File, destination: File) {
    val sevenZFile = SevenZFile(seveZipFile)
    extract(sevenZFile, destination)
  }

  private fun extract(sevenZFile: SevenZFile, destination: File) {
    while (true) {
      val entry = sevenZFile.nextEntry ?: break
      if (entry.isDirectory) {
        continue
      }
      val currentFile = File(destination, entry.name)
      val parent = currentFile.parentFile
      if (!parent.exists()) {
        parent.mkdirs()
      }
      FileOutputStream(currentFile).use { out ->
        val content = ByteArray(entry.size.toInt())
        sevenZFile.read(content, 0, content.size)
        out.write(content)
      }
    }
  }

  private fun addFile(sevenZOutputFile: SevenZOutputFile, dirToZip: String, file: File) {
    sevenZOutputFile.add(dirToZip, file)
  }
}

fun SevenZOutputFile.add(dirToZip: String, file: File) {
  val name = dirToZip + File.separator + file.name
  if (file.isDirectory) {
    val children = file.listFiles()
    if (children != null) {
      for (child in children) {
        this.add(name, child)
      }
    }
  } else {
    val entry = this.createArchiveEntry(file, name)
    this.putArchiveEntry(entry)
    val inputStream = FileInputStream(file)
    val b = ByteArray(1024)
    var count: Int
    while (inputStream.read(b).also { count = it } > 0) {
      this.write(b, 0, count)
    }
    this.closeArchiveEntry()
  }
}
