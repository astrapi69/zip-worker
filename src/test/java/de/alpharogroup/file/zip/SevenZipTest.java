/**
 * The MIT License
 *
 * Copyright (C) 2020 Asterios Raptis
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
package de.alpharogroup.file.zip;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.file.create.DirectoryFactory;
import io.github.astrapi69.file.create.FileFactory;
import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapi69.file.write.StoreFileExtensions;

class SevenZipTest
{

	@Test
	void zipFiles() throws IOException
	{
		File testFile1;
		File testFile2;
		File testFile3;
		File destination;
		File sevenZipFile;

		destination = new File(PathFinder.getSrcTestResourcesDir(), "/tmp");
		DirectoryFactory.newDirectory(destination);
		sevenZipFile = new File(destination, "testzip.7z");
		FileFactory.newFile(sevenZipFile);

		testFile1 = new File(PathFinder.getSrcTestResourcesDir(), "testZip1.txt");
		testFile2 = new File(PathFinder.getSrcTestResourcesDir(), "testZip2.txt");
		testFile3 = new File(PathFinder.getSrcTestResourcesDir(), "testZip3.txt");

		StoreFileExtensions.toFile(testFile1, "Its a beautifull day!!!");
		StoreFileExtensions.toFile(testFile2, "Its a beautifull evening!!!");
		StoreFileExtensions.toFile(testFile3, "Its a beautifull night!!!");

		SevenZip.zipFiles(sevenZipFile, "", testFile1, testFile2, testFile3);

		assertTrue(sevenZipFile.exists());
		// clean up
		DeleteFileExtensions.delete(sevenZipFile);
		DeleteFileExtensions.delete(testFile1);
		DeleteFileExtensions.delete(testFile2);
		DeleteFileExtensions.delete(testFile3);
		DeleteFileExtensions.delete(destination);
	}

	@Test
	void testExtract() throws IOException
	{
		File testFile1;
		File testFile2;
		File testFile3;
		File destination;
		File sevenZipFile;

		destination = new File(PathFinder.getSrcTestResourcesDir(), "/tmp");
		DirectoryFactory.newDirectory(destination);
		sevenZipFile = new File(destination, "testzip.7z");
		FileFactory.newFile(sevenZipFile);

		testFile1 = new File(PathFinder.getSrcTestResourcesDir(), "testZip1.txt");
		testFile2 = new File(PathFinder.getSrcTestResourcesDir(), "testZip2.txt");
		testFile3 = new File(PathFinder.getSrcTestResourcesDir(), "testZip3.txt");

		StoreFileExtensions.toFile(testFile1, "Its a beautifull day!!!");
		StoreFileExtensions.toFile(testFile2, "Its a beautifull evening!!!");
		StoreFileExtensions.toFile(testFile3, "Its a beautifull night!!!");

		assertTrue(testFile1.exists());
		assertTrue(testFile2.exists());
		assertTrue(testFile3.exists());

		SevenZip.zipFiles(sevenZipFile, "", testFile1, testFile2, testFile3);

		DeleteFileExtensions.delete(testFile1);
		DeleteFileExtensions.delete(testFile2);
		DeleteFileExtensions.delete(testFile3);

		SevenZip.extract(sevenZipFile, destination);

		testFile1 = new File(destination, "testZip1.txt");
		testFile2 = new File(destination, "testZip2.txt");
		testFile3 = new File(destination, "testZip3.txt");
		assertTrue(testFile1.exists());
		assertTrue(testFile2.exists());
		assertTrue(testFile3.exists());
		// clean up
		DeleteFileExtensions.delete(sevenZipFile);
		DeleteFileExtensions.delete(testFile1);
		DeleteFileExtensions.delete(testFile2);
		DeleteFileExtensions.delete(testFile3);
		DeleteFileExtensions.delete(destination);
	}

}