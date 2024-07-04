package io.github.astrapi69.file.zip.sevenz;

import io.github.astrapi69.file.write.StoreFileExtensions;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.*;

class SevenZFileExtensionsTest
{

    private File tempDir;
    private File tempZipFile;
    private final char[] testPassword = "password".toCharArray();

    /**
     * Sets up temporary files and directories before each test.
     */
    @BeforeEach
    void setup() throws IOException {
        tempDir = Files.createTempDirectory("testDir").toFile();
        tempZipFile = Files.createTempFile("test", ".7z").toFile();
    }

    /**
     * Cleans up temporary files and directories after each test.
     */
    @AfterEach
    void teardown() {
        tempDir.delete();
        if (tempZipFile.exists()) {
            tempZipFile.delete();
        }
    }

    /**
     * Tests the {@link SevenZFileExtensions#zipFiles(File, String, File...)} method.
     * <p>
     * Creates two text files and compresses them into a 7z archive. Asserts that the archive file exists and is not empty.
     * </p>
     */
    @Test
    void testZipFiles() throws IOException {
        File file1 = new File(tempDir, "file1.txt");
        File file2 = new File(tempDir, "file2.txt");


        StoreFileExtensions.toFile(file1, "Its a beautifull day!!!");
        StoreFileExtensions.toFile(file2, "Its a beautifull evening!!!");

        SevenZFileExtensions.zipFiles(tempZipFile, tempDir.getAbsolutePath(), file1, file2);

        Assertions.assertTrue(tempZipFile.exists());
        Assertions.assertTrue(tempZipFile.length() > 0);
    }

    /**
     * Tests the {@link SevenZFileExtensions#extract(File, File)} method without a password.
     * <p>
     * Creates two text files, compresses them into a 7z archive, and then extracts them. Asserts that the extracted files exist.
     * </p>
     */
    @Test
    void testExtractWithoutPassword() throws IOException {
        File file1 = new File(tempDir, "file1.txt");
        File file2 = new File(tempDir, "file2.txt");


        StoreFileExtensions.toFile(file1, "Its a beautifull day!!!");
        StoreFileExtensions.toFile(file2, "Its a beautifull evening!!!");

        SevenZFileExtensions.zipFiles(tempZipFile, file1, file2);

        File extractDir = Files.createTempDirectory("extractDir").toFile();
        SevenZFileExtensions.extract(tempZipFile, extractDir);

        Assertions.assertTrue(new File(extractDir, "file1.txt").exists());
        Assertions.assertTrue(new File(extractDir, "file2.txt").exists());

        extractDir.delete();
    }
}