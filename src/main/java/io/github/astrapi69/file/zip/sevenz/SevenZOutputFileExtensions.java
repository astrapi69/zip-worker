package io.github.astrapi69.file.zip.sevenz;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

/**
 * Extension functions for SevenZOutputFile.
 */
public class SevenZOutputFileExtensions
{

	/**
	 * Adds a file to the 7z archive under the specified directory.
	 *
	 * @param dirToZip
	 *            The directory to be zipped.
	 * @param file
	 *            The file to be added.
	 */
	public static void add(SevenZOutputFile sevenZOutputFile, String dirToZip, File file)
	{
		String name = dirToZip + File.separator + file.getName();
		addFileWithName(sevenZOutputFile, file, name);
	}

	/**
	 * Adds a file to the 7z archive.
	 *
	 * @param file
	 *            The file to be added.
	 */
	public static void add(SevenZOutputFile sevenZOutputFile, File file)
	{
		String name = file.getName();
		addFileWithName(sevenZOutputFile, file, name);
	}

	/**
	 * Adds a file to the 7z archive with the specified name.
	 *
	 * @param file
	 *            The file to be added.
	 * @param name
	 *            The name of the file in the archive.
	 */
	private static void addFileWithName(SevenZOutputFile sevenZOutputFile, File file, String name)
	{
		try
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
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}