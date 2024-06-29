package io.github.astrapi69.file.sevenzip

import de.alpharogroup.file.zip.SevenZip
import java.io.*
import java.nio.file.Files
import org.junit.jupiter.api.*

class SevenZipTest {

  private lateinit var tempDir: File
  private lateinit var tempZipFile: File
  private val testPassword = "password".toCharArray()

  @BeforeEach
  fun setup() {
    tempDir = Files.createTempDirectory("testDir").toFile()
    tempZipFile = Files.createTempFile("test", ".7z").toFile()
  }

  @AfterEach
  fun teardown() {
    tempDir.deleteRecursively()
    if (tempZipFile.exists()) {
      tempZipFile.delete()
    }
  }

  @Test
  fun `test zipFiles`() {
    val file1 = File(tempDir, "file1.txt").apply { writeText("Hello World 1") }
    val file2 = File(tempDir, "file2.txt").apply { writeText("Hello World 2") }

    SevenZip.zipFiles(tempZipFile, tempDir.absolutePath, file1, file2)

    Assertions.assertTrue(tempZipFile.exists())
    Assertions.assertTrue(tempZipFile.length() > 0)
  }

  @Test
  fun `test extract without password`() {
    val file1 = File(tempDir, "file1.txt").apply { writeText("Hello World 1") }
    val file2 = File(tempDir, "file2.txt").apply { writeText("Hello World 2") }

    SevenZip.zipFiles(tempZipFile, file1, file2)

    val extractDir = Files.createTempDirectory("extractDir").toFile()
    SevenZip.extract(tempZipFile, extractDir)

    Assertions.assertTrue(File(extractDir, "file1.txt").exists())
    Assertions.assertTrue(File(extractDir, "file2.txt").exists())

    extractDir.deleteRecursively()
  }
}
