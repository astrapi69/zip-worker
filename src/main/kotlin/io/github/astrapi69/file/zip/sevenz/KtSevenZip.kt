/**
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
package io.github.astrapi69.file.zip.sevenz

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.commons.compress.archivers.sevenz.SevenZFile
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile

/** Utility object for handling 7z (7-Zip) file compression and decompression. */
object KtSevenZip {

  /**
   * Compresses the given files into a 7z archive.
   *
   * @param sevenZipFile The destination 7z file.
   * @param dirToZip The directory to be zipped.
   * @param files The files to be included in the 7z archive.
   */
  @JvmStatic
  fun zipFiles(sevenZipFile: File, dirToZip: String, vararg files: File) {
    SevenZOutputFile(sevenZipFile).use { sevenZOutputFile ->
      for (file in files) {
        addFile(sevenZOutputFile, dirToZip, file)
      }
    }
  }

  /**
   * Compresses the given files into a 7z archive.
   *
   * @param sevenZipFile The destination 7z file.
   * @param files The files to be included in the 7z archive.
   */
  @JvmStatic
  fun zipFiles(sevenZipFile: File, vararg files: File) {
    SevenZOutputFile(sevenZipFile).use { sevenZOutputFile ->
      for (file in files) {
        addFile(sevenZOutputFile, file)
      }
    }
  }

  /**
   * Extracts the contents of a 7z archive to the specified destination directory.
   *
   * @param sevenZipFile The source 7z file.
   * @param destination The destination directory.
   * @param password The password for encrypted 7z files.
   */
  @JvmStatic
  fun extract(seveZipFile: File, destination: File, password: CharArray) {
    val sevenZFile = SevenZFile(seveZipFile, password)
    extract(sevenZFile, destination)
  }

  /**
   * Extracts the contents of a 7z archive to the specified destination directory.
   *
   * @param sevenZipFile The source 7z file.
   * @param destination The destination directory.
   */
  @JvmStatic
  fun extract(seveZipFile: File, destination: File) {
    val sevenZFile = SevenZFile.builder().setFile(seveZipFile).get()
    extract(sevenZFile, destination)
  }

  /**
   * Extracts files from a 7z archive.
   *
   * @param sevenZFile The source 7z file.
   * @param destination The destination directory.
   */
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

  /**
   * Adds a file to a 7z archive.
   *
   * @param sevenZOutputFile The 7z output file.
   * @param dirToZip The directory to be zipped.
   * @param file The file to be added.
   */
  private fun addFile(sevenZOutputFile: SevenZOutputFile, dirToZip: String, file: File) {
    sevenZOutputFile.add(dirToZip, file)
  }

  /**
   * Adds a file to a 7z archive.
   *
   * @param sevenZOutputFile The 7z output file.
   * @param file The file to be added.
   */
  private fun addFile(sevenZOutputFile: SevenZOutputFile, file: File) {
    sevenZOutputFile.add(file)
  }
}

/** Extension functions for SevenZOutputFile. */

/**
 * Adds a file to the 7z archive under the specified directory.
 *
 * @param dirToZip The directory to be zipped.
 * @param file The file to be added.
 */
fun SevenZOutputFile.add(dirToZip: String, file: File) {
  val name = dirToZip + File.separator + file.name
  addFileWithName(file, name)
}

/**
 * Adds a file to the 7z archive.
 *
 * @param file The file to be added.
 */
fun SevenZOutputFile.add(file: File) {
  val name = file.name
  addFileWithName(file, name)
}

/**
 * Adds a file to the 7z archive with the specified name.
 *
 * @param file The file to be added.
 * @param name The name of the file in the archive.
 */
private fun SevenZOutputFile.addFileWithName(file: File, name: String) {
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
