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
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

/**
 * Extension functions for SevenZOutputFile.
 */
public class SevenZOutputFileExtensions
{

	/**
	 * Adds a file to the 7z archive under the specified directory
	 *
	 * @param sevenZOutputFile
	 *            The 7z archive zip file to add the file
	 * @param dirToZip
	 *            The directory to be zipped.
	 * @param file
	 *            The file to be added.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void add(SevenZOutputFile sevenZOutputFile, String dirToZip, File file)
		throws IOException
	{
		String name = dirToZip + File.separator + file.getName();
		addFileWithName(sevenZOutputFile, file, name);
	}

	/**
	 * Adds a file to the 7z archive.
	 *
	 * @param sevenZOutputFile
	 *            The 7z archive zip file to add the file
	 * @param file
	 *            The file to be added.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void add(SevenZOutputFile sevenZOutputFile, File file) throws IOException
	{
		String name = file.getName();
		addFileWithName(sevenZOutputFile, file, name);
	}

	/**
	 * Adds a file to the 7z archive with the specified name.
	 *
	 * @param sevenZOutputFile
	 *            The 7z archive zip file to add the file
	 * @param file
	 *            The file to be added.
	 * @param name
	 *            The name of the file in the archive.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void addFileWithName(SevenZOutputFile sevenZOutputFile, File file, String name)
		throws IOException
	{
		if (file.isDirectory())
		{
			File[] children = file.listFiles();
			if (children != null)
			{
				for (File child : children)
				{
					add(sevenZOutputFile, name, child);
				}
			}
		}
		else
		{
			SevenZArchiveEntry entry = sevenZOutputFile.createArchiveEntry(file, name);
			sevenZOutputFile.putArchiveEntry(entry);
			try (FileInputStream inputStream = new FileInputStream(file))
			{
				byte[] buffer = new byte[1024];
				int count;
				while ((count = inputStream.read(buffer)) > 0)
				{
					sevenZOutputFile.write(buffer, 0, count);
				}
				sevenZOutputFile.closeArchiveEntry();
			}
		}
	}
}