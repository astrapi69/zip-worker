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

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipFile;

/**
 * The Interface ZipModel.
 *
 * @version 1.0
 * @author Asterios Raptis
 */
public interface ZipModel
{

	/**
	 * Returns the field <code>compressionMethod</code>.
	 *
	 * @return The field <code>compressionMethod</code>.
	 */
	int getCompressionMethod();

	/**
	 * Sets the field <code>compressionMethod</code>.
	 *
	 * @param compressionMethod
	 *            The <code>compressionMethod</code> to set
	 */
	void setCompressionMethod(final int compressionMethod);

	/**
	 * Returns the field <code>directoryToZip</code>.
	 *
	 * @return The field <code>directoryToZip</code>.
	 */
	File getDirectoryToZip();

	/**
	 * Sets the field <code>directoryToZip</code>.
	 *
	 * @param directoryToZip
	 *            The <code>directoryToZip</code> to set
	 */
	void setDirectoryToZip(final File directoryToZip);

	/**
	 * Returns the field <code>dirToStart</code>.
	 *
	 * @return The field <code>dirToStart</code>.
	 */
	String getDirToStart();

	/**
	 * Sets the field <code>dirToStart</code>.
	 *
	 * @param dirToStart
	 *            The <code>dirToStart</code> to set
	 */
	void setDirToStart(final String dirToStart);

	/**
	 * Returns the field <code>fileCounter</code>.
	 *
	 * @return The field <code>fileCounter</code>.
	 */
	int getFileCounter();

	/**
	 * Sets the field <code>fileCounter</code>.
	 *
	 * @param fileCounter
	 *            The <code>fileCounter</code> to set
	 */
	void setFileCounter(final int fileCounter);

	/**
	 * Returns the field <code>fileFilter</code>.
	 *
	 * @return The field <code>fileFilter</code>.
	 */
	FilenameFilter getFileFilter();

	/**
	 * Sets the field <code>fileFilter</code>.
	 *
	 * @param fileFilter
	 *            The <code>fileFilter</code> to set
	 */
	void setFileFilter(final FilenameFilter fileFilter);

	/**
	 * Returns the field <code>fileLength</code>.
	 *
	 * @return The field <code>fileLength</code>.
	 */
	long getFileLength();

	/**
	 * Sets the field <code>fileLength</code>.
	 *
	 * @param fileLength
	 *            The <code>fileLength</code> to set
	 */
	void setFileLength(final long fileLength);

	/**
	 * Returns the field <code>zipFile</code>.
	 *
	 * @return The field <code>zipFile</code>.
	 */
	File getZipFile();

	/**
	 * Sets the field <code>zipFile</code>.
	 *
	 * @param zipFile
	 *            The <code>zipFile</code> to set
	 */
	void setZipFile(final File zipFile);

	/**
	 * Returns the field <code>zipFileComment</code>.
	 *
	 * @return The field <code>zipFileComment</code>.
	 */
	String getZipFileComment();

	/**
	 * Sets the field <code>zipFileComment</code>.
	 *
	 * @param zipFileComment
	 *            The <code>zipFileComment</code> to set
	 */
	void setZipFileComment(final String zipFileComment);

	/**
	 * Returns the field <code>zipFileName</code>.
	 *
	 * @return The field <code>zipFileName</code>.
	 */
	String getZipFileName();

	/**
	 * Sets the field <code>zipFileName</code>.
	 *
	 * @param zipFileName
	 *            The <code>zipFileName</code> to set
	 */
	void setZipFileName(final String zipFileName);

	/**
	 * Returns the field <code>zipFileObj</code>.
	 *
	 * @return The field <code>zipFileObj</code>.
	 */
	ZipFile getZipFileObj();

	/**
	 * Sets the field <code>zipFileObj</code>.
	 *
	 * @param zipFileObj
	 *            The <code>zipFileObj</code> to set
	 */
	void setZipFileObj(final ZipFile zipFileObj);

	/**
	 * Returns the field <code>zipLevel</code>.
	 *
	 * @return The field <code>zipLevel</code>.
	 */
	int getZipLevel();

	/**
	 * Sets the field <code>zipLevel</code>.
	 *
	 * @param zipLevel
	 *            The <code>zipLevel</code> to set
	 */
	void setZipLevel(final int zipLevel);

}
