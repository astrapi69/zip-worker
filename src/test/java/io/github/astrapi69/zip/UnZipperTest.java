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
package io.github.astrapi69.zip;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.astrapi69.file.csv.CsvBean;
import io.github.astrapi69.file.search.FileSearchExtensions;
import io.github.astrapi69.file.write.StoreFileExtensions;

/**
 * The unit test class for the class {@link UnZipper}
 *
 * @version 1.0
 * @author Asterios Raptis
 */
public class UnZipperTest extends ZipTestCase
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	@BeforeEach
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@AfterEach
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/**
	 * Test method for {@link CsvBean} constructors and builders
	 */
	@Test
	public final void testConstructors()
	{
		UnZipper model;
		model = UnZipper.builder().build();
		assertNotNull(model);
		model = new UnZipper();
		assertNotNull(model);
	}

	/**
	 * Test method for {@link UnZipper#extractZipEntry(ZipFile, ZipEntry, File)}.
	 *
	 * @throws Exception
	 *             catch all exception
	 */
	@Test
	public void testExtractZipEntry() throws Exception
	{

		final File zipFile = new File(this.zipDir.getAbsoluteFile(), "testZip.zip");
		if (!zipFile.exists())
		{
			zipFile.createNewFile();
		}

		final long length = zipFile.length();
		this.actual = length == 0;
		assertTrue(this.actual);
		final File testFile1 = new File(this.testDir.getAbsoluteFile(), "testZip1.txt");
		final File testFile2 = new File(this.testDir.getAbsoluteFile(), "testZip2.tft");
		final File testFile3 = new File(this.testDir.getAbsoluteFile(), "testZip3.txt");

		final File testFile4 = new File(this.deepDir.getAbsoluteFile(), "testZip4.tft");
		final File testFile5 = new File(this.deepDir.getAbsoluteFile(), "testZip5.cvs");

		final File testFile6 = new File(this.deepDir2.getAbsoluteFile(), "testZip6.txt");
		final File testFile7 = new File(this.deepDir2.getAbsoluteFile(), "testZip7.cvs");

		final String file8 = "testZip8.txt";
		final File testFile8 = new File(this.deeperDir.getAbsoluteFile(), file8);
		final File unzippedFile8 = new File(this.unzipDirDeeperDir, file8);
		final String file9 = "testZip9.cvs";
		final File testFile9 = new File(this.deeperDir.getAbsoluteFile(), file9);
		final File unzippedFile9 = new File(this.unzipDirDeeperDir, file9);

		StoreFileExtensions.toFile(testFile1, "Its a beautifull day!!!");
		StoreFileExtensions.toFile(testFile2, "Its a beautifull evening!!!");
		StoreFileExtensions.toFile(testFile3, "Its a beautifull night!!!");
		StoreFileExtensions.toFile(testFile4, "Its a beautifull morning!!!");
		StoreFileExtensions.toFile(testFile5, "She's a beautifull woman!!!");
		StoreFileExtensions.toFile(testFile6, "Its a beautifull street!!!");
		StoreFileExtensions.toFile(testFile7, "He's a beautifull man!!!");
		StoreFileExtensions.toFile(testFile8, "Its a beautifull city!!!");
		StoreFileExtensions.toFile(testFile9, "He's a beautifull boy!!!");

		ZipExtensions.zip(this.testDir, zipFile);

		final long currentLength = zipFile.length();
		this.actual = 0 < currentLength;
		assertTrue(this.actual);

		final ZipEntry zipEntry = new ZipEntry("testDir" + File.separator + "deepDir"
			+ File.separator + "deeperDir" + File.separator + "testZip8.txt");
		if (!this.unzipDirTestDir.exists())
		{
			this.unzipDirTestDir.mkdir();
		}

		ZipFile zf = new ZipFile(zipFile);
		final UnZipper unzipper = new UnZipper(zf, this.unzipDir);
		unzipper.extractZipEntry(zf, zipEntry, this.unzipDir);
		zf.close();
		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile8);
		assertTrue(this.actual);

		final ZipEntry zipEntry2 = new ZipEntry("testDir" + File.separator + "deepDir"
			+ File.separator + "deeperDir" + File.separator + "testZip9.cvs");
		zf = new ZipFile(zipFile);
		unzipper.extractZipEntry(zf, zipEntry2, this.unzipDir);
		zf.close();
		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile9);
		assertTrue(this.actual);
	}

	/**
	 * Test method for {@link UnZipper#unzip()}.
	 *
	 * @throws ZipException
	 *             the zip exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUnzip() throws ZipException, IOException
	{

		final File zipFile = new File(this.zipDir.getAbsoluteFile(), "testZip.zip");
		if (!zipFile.exists())
		{
			zipFile.createNewFile();
		}

		final long length = zipFile.length();
		this.actual = length == 0;
		assertTrue(this.actual);
		final String file1 = "testZip1.txt";
		final File testFile1 = new File(this.testDir.getAbsoluteFile(), file1);
		final File unzippedFile1 = new File(this.unzipDirTestDir, file1);
		final String file2 = "testZip2.tft";
		final File testFile2 = new File(this.testDir.getAbsoluteFile(), file2);
		final File unzippedFile2 = new File(this.unzipDirTestDir, file2);
		final String file3 = "testZip3.txt";
		final File testFile3 = new File(this.testDir.getAbsoluteFile(), file3);
		final File unzippedFile3 = new File(this.unzipDirTestDir, file3);
		final String file4 = "testZip4.tft";
		final File testFile4 = new File(this.deepDir.getAbsoluteFile(), file4);
		final File unzippedFile4 = new File(this.unzipDirDeepDir, file4);
		final String file5 = "testZip5.cvs";
		final File testFile5 = new File(this.deepDir.getAbsoluteFile(), file5);
		final File unzippedFile5 = new File(this.unzipDirDeepDir, file5);
		final String file6 = "testZip6.txt";
		final File testFile6 = new File(this.deepDir2.getAbsoluteFile(), file6);
		final File unzippedFile6 = new File(this.unzipDirDeepDir2, file6);
		final String file7 = "testZip7.cvs";
		final File testFile7 = new File(this.deepDir2.getAbsoluteFile(), file7);
		final File unzippedFile7 = new File(this.unzipDirDeepDir2, file7);
		final String file8 = "testZip8.txt";
		final File testFile8 = new File(this.deeperDir.getAbsoluteFile(), file8);
		final File unzippedFile8 = new File(this.unzipDirDeeperDir, file8);
		final String file9 = "testZip9.cvs";
		final File testFile9 = new File(this.deeperDir.getAbsoluteFile(), file9);
		final File unzippedFile9 = new File(this.unzipDirDeeperDir, file9);

		StoreFileExtensions.toFile(testFile1, "Its a beautifull day!!!");
		StoreFileExtensions.toFile(testFile2, "Its a beautifull evening!!!");
		StoreFileExtensions.toFile(testFile3, "Its a beautifull night!!!");
		StoreFileExtensions.toFile(testFile4, "Its a beautifull morning!!!");
		StoreFileExtensions.toFile(testFile5, "She's a beautifull woman!!!");
		StoreFileExtensions.toFile(testFile6, "Its a beautifull street!!!");
		StoreFileExtensions.toFile(testFile7, "He's a beautifull man!!!");
		StoreFileExtensions.toFile(testFile8, "Its a beautifull city!!!");
		StoreFileExtensions.toFile(testFile9, "He's a beautifull boy!!!");

		final Zipper zipper = new Zipper(this.testDir, zipFile);
		zipper.zip();

		final long currentLength = zipFile.length();
		this.actual = 0 < currentLength;
		assertTrue(this.actual);

		this.actual = zipper.getFileCounter() == 9;
		assertTrue(this.actual);

		this.actual = zipper.getZipFile().equals(zipFile);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.zipDir, zipFile);
		assertTrue(this.actual);
		final ZipFile zf = new ZipFile(zipFile);
		final UnZipper unzipper = new UnZipper(zf, this.unzipDir);
		unzipper.unzip();

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile1);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile2);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile3);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile4);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile5);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile6);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile7);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile8);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile9);
		assertTrue(this.actual);

	}

	/**
	 * Test method for {@link UnZipper#unzip(ZipFile, File)}
	 *
	 * @throws ZipException
	 *             the zip exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Test
	public void testUnzipZipFileFile() throws ZipException, IOException
	{
		final File zipFile = new File(this.zipDir.getAbsoluteFile(), "testZip.zip");
		if (!zipFile.exists())
		{
			zipFile.createNewFile();
		}

		final long length = zipFile.length();
		this.actual = length == 0;
		assertTrue(this.actual);
		final String file1 = "testZip1.txt";
		final File testFile1 = new File(this.testDir.getAbsoluteFile(), file1);
		final File unzippedFile1 = new File(this.unzipDirTestDir, file1);
		final String file2 = "testZip2.tft";
		final File testFile2 = new File(this.testDir.getAbsoluteFile(), file2);
		final File unzippedFile2 = new File(this.unzipDirTestDir, file2);
		final String file3 = "testZip3.txt";
		final File testFile3 = new File(this.testDir.getAbsoluteFile(), file3);
		final File unzippedFile3 = new File(this.unzipDirTestDir, file3);
		final String file4 = "testZip4.tft";
		final File testFile4 = new File(this.deepDir.getAbsoluteFile(), file4);
		final File unzippedFile4 = new File(this.unzipDirDeepDir, file4);
		final String file5 = "testZip5.cvs";
		final File testFile5 = new File(this.deepDir.getAbsoluteFile(), file5);
		final File unzippedFile5 = new File(this.unzipDirDeepDir, file5);
		final String file6 = "testZip6.txt";
		final File testFile6 = new File(this.deepDir2.getAbsoluteFile(), file6);
		final File unzippedFile6 = new File(this.unzipDirDeepDir2, file6);
		final String file7 = "testZip7.cvs";
		final File testFile7 = new File(this.deepDir2.getAbsoluteFile(), file7);
		final File unzippedFile7 = new File(this.unzipDirDeepDir2, file7);
		final String file8 = "testZip8.txt";
		final File testFile8 = new File(this.deeperDir.getAbsoluteFile(), file8);
		final File unzippedFile8 = new File(this.unzipDirDeeperDir, file8);
		final String file9 = "testZip9.cvs";
		final File testFile9 = new File(this.deeperDir.getAbsoluteFile(), file9);
		final File unzippedFile9 = new File(this.unzipDirDeeperDir, file9);

		StoreFileExtensions.toFile(testFile1, "Its a beautifull day!!!");
		StoreFileExtensions.toFile(testFile2, "Its a beautifull evening!!!");
		StoreFileExtensions.toFile(testFile3, "Its a beautifull night!!!");
		StoreFileExtensions.toFile(testFile4, "Its a beautifull morning!!!");
		StoreFileExtensions.toFile(testFile5, "She's a beautifull woman!!!");
		StoreFileExtensions.toFile(testFile6, "Its a beautifull street!!!");
		StoreFileExtensions.toFile(testFile7, "He's a beautifull man!!!");
		StoreFileExtensions.toFile(testFile8, "Its a beautifull city!!!");
		StoreFileExtensions.toFile(testFile9, "He's a beautifull boy!!!");

		final Zipper zipper = new Zipper(this.testDir, zipFile);
		zipper.zip();

		final long currentLength = zipFile.length();
		this.actual = 0 < currentLength;
		assertTrue(this.actual);

		this.actual = zipper.getFileCounter() == 9;
		assertTrue(this.actual);

		this.actual = zipper.getZipFile().equals(zipFile);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.zipDir, zipFile);
		assertTrue(this.actual);

		final UnZipper unzipper = new UnZipper();
		final ZipFile zf = new ZipFile(zipFile);
		unzipper.unzip(zf, this.unzipDir);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile1);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile2);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile3);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile4);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile5);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile6);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile7);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile8);
		assertTrue(this.actual);

		this.actual = FileSearchExtensions.containsFileRecursive(this.unzipDir, unzippedFile9);
		assertTrue(this.actual);

	}

}
