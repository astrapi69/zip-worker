package io.github.astrapi69.file.zip.sevenz;

import java.io.File;
import java.io.FileOutputStream;

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
	 */
	public static void zipFiles(File sevenZipFile, String dirToZip, File... files)
	{
		try (SevenZOutputFile sevenZOutputFile = new SevenZOutputFile(sevenZipFile))
		{
			for (File file : files)
			{
				addFile(sevenZOutputFile, dirToZip, file);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Compresses the given files into a 7z archive.
	 *
	 * @param sevenZipFile
	 *            The destination 7z file.
	 * @param files
	 *            The files to be included in the 7z archive.
	 */
	public static void zipFiles(File sevenZipFile, File... files)
	{
		try (SevenZOutputFile sevenZOutputFile = new SevenZOutputFile(sevenZipFile))
		{
			for (File file : files)
			{
				addFile(sevenZOutputFile, file);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
	 */
	public static void extract(File sevenZipFile, File destination, char[] password)
	{
		try (SevenZFile sevenZFile = SevenZFile.builder().setFile(sevenZipFile).setPassword(password).get())
		{
			extract(sevenZFile, destination);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Extracts the contents of a 7z archive to the specified destination directory.
	 *
	 * @param sevenZipFile
	 *            The source 7z file.
	 * @param destination
	 *            The destination directory.
	 */
	public static void extract(File sevenZipFile, File destination)
	{
		try (SevenZFile sevenZFile = SevenZFile.builder().setFile(sevenZipFile).get())
		{
			extract(sevenZFile, destination);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Extracts files from a 7z archive.
	 *
	 * @param sevenZFile
	 *            The source 7z file.
	 * @param destination
	 *            The destination directory.
	 */
	private static void extract(SevenZFile sevenZFile, File destination)
	{
		try
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
		catch (Exception e)
		{
			e.printStackTrace();
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
	 */
	private static void addFile(SevenZOutputFile sevenZOutputFile, String dirToZip, File file)
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
	 */
	private static void addFile(SevenZOutputFile sevenZOutputFile, File file)
	{
		SevenZOutputFileExtensions.add(sevenZOutputFile, file);
	}

}