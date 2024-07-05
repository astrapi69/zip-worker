/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.zip.sevenz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.*;

import io.github.astrapi69.file.write.StoreFileExtensions;

class SevenZFileExtensionsTest
{

	private File tempDir;
	private File tempZipFile;
	private final char[] testPassword = "password".toCharArray();

	/**
	 * Sets up temporary files and directories before each test.
	 */
	@BeforeEach
	void setup() throws IOException
	{
		tempDir = Files.createTempDirectory("testDir").toFile();
		tempZipFile = Files.createTempFile("test", ".7z").toFile();
	}

	/**
	 * Cleans up temporary files and directories after each test.
	 */
	@AfterEach
	void teardown()
	{
		tempDir.delete();
		if (tempZipFile.exists())
		{
			tempZipFile.delete();
		}
	}

	/**
	 * Tests the {@link SevenZFileExtensions#zipFiles(File, String, File...)} method.
	 * <p>
	 * Creates two text files and compresses them into a 7z archive. Asserts that the archive file
	 * exists and is not empty.
	 * </p>
	 */
	@Test
	void testZipFiles() throws IOException
	{
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
	 * Creates two text files, compresses them into a 7z archive, and then extracts them. Asserts
	 * that the extracted files exist.
	 * </p>
	 */
	@Test
	void testExtractWithoutPassword() throws IOException
	{
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