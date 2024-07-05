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
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

/**
 * Utility class for handling 7z (7-Zip) file compression and decompression.
 */
public class SevenZFileExtensions
{

	/**
	 * Compresses the given files into a 7z archive.
	 *
	 * @param sevenZipFile
	 *            The destination 7z file.
	 * @param dirToZip
	 *            The directory to be zipped.
	 * @param files
	 *            The files to be included in the 7z archive.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void zipFiles(File sevenZipFile, String dirToZip, File... files)
		throws IOException
	{
		try (SevenZOutputFile sevenZOutputFile = new SevenZOutputFile(sevenZipFile))
		{
			for (File file : files)
			{
				addFile(sevenZOutputFile, dirToZip, file);
			}
		}
	}

	/**
	 * Compresses the given files into a 7z archive.
	 *
	 * @param sevenZipFile
	 *            The destination 7z file.
	 * @param files
	 *            The files to be included in the 7z archive.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void zipFiles(File sevenZipFile, File... files) throws IOException
	{
		try (SevenZOutputFile sevenZOutputFile = new SevenZOutputFile(sevenZipFile))
		{
			for (File file : files)
			{
				addFile(sevenZOutputFile, file);
			}
		}
	}

	/**
	 * Extracts the contents of a 7z archive to the specified destination directory.
	 *
	 * @param sevenZipFile
	 *            The source 7z file.
	 * @param destination
	 *            The destination directory.
	 * @param password
	 *            The password for encrypted 7z files.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void extract(File sevenZipFile, File destination, char[] password)
		throws IOException
	{
		try (SevenZFile sevenZFile = SevenZFile.builder().setFile(sevenZipFile)
			.setPassword(password).get())
		{
			extract(sevenZFile, destination);
		}
	}

	/**
	 * Extracts the contents of a 7z archive to the specified destination directory.
	 *
	 * @param sevenZipFile
	 *            The source 7z file.
	 * @param destination
	 *            The destination directory.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void extract(File sevenZipFile, File destination) throws IOException
	{
		try (SevenZFile sevenZFile = SevenZFile.builder().setFile(sevenZipFile).get())
		{
			extract(sevenZFile, destination);
		}
	}

	/**
	 * Extracts files from a 7z archive.
	 *
	 * @param sevenZFile
	 *            The source 7z file.
	 * @param destination
	 *            The destination directory.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void extract(SevenZFile sevenZFile, File destination) throws IOException
	{
		while (true)
		{
			SevenZArchiveEntry entry = sevenZFile.getNextEntry();
			if (entry == null)
				break;
			if (entry.isDirectory())
				continue;

			File currentFile = new File(destination, entry.getName());
			File parent = currentFile.getParentFile();
			if (!parent.exists())
			{
				parent.mkdirs();
			}
			try (FileOutputStream out = new FileOutputStream(currentFile))
			{
				byte[] content = new byte[(int)entry.getSize()];
				sevenZFile.read(content);
				out.write(content);
			}
		}
	}

	/**
	 * Adds a file to a 7z archive.
	 *
	 * @param sevenZOutputFile
	 *            The 7z output file.
	 * @param dirToZip
	 *            The directory to be zipped.
	 * @param file
	 *            The file to be added.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void addFile(SevenZOutputFile sevenZOutputFile, String dirToZip, File file)
		throws IOException
	{
		SevenZOutputFileExtensions.add(sevenZOutputFile, dirToZip, file);
	}

	/**
	 * Adds a file to a 7z archive.
	 *
	 * @param sevenZOutputFile
	 *            The 7z output file.
	 * @param file
	 *            The file to be added.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void addFile(SevenZOutputFile sevenZOutputFile, File file) throws IOException
	{
		SevenZOutputFileExtensions.add(sevenZOutputFile, file);
	}

}