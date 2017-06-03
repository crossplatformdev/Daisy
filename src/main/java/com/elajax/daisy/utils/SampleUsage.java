package com.elajax.daisy.utils;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.Imaging;

public class SampleUsage {

	@SuppressWarnings("unused")
	public SampleUsage() {

		try {
			// <b>Code won't work unless these variables are properly
			// initialized.
			// Imaging works equally well with File, byte array or InputStream
			// inputs.</b>
			final BufferedImage someImage = null;
			String colorString = "";
			for (int i = 0; i < 480; i++) {
				for (int j = 0; j < 360; j++) {
					colorString += Math.round(Math.random() * 3);
				}
				colorString += "\n";
			}
			final byte someBytes[] = colorString.getBytes();
			final File someFile = new File("./test.png");
			final InputStream someInputStream = new ByteArrayInputStream(someBytes);
			final OutputStream someOutputStream = new FileOutputStream(someFile);

			// <b>The Imaging class provides a simple interface to the library.
			// </b>

			// <b>how to read an image: </b>
			final byte imageBytes[] = someBytes;
			final BufferedImage image_1 = Imaging.getBufferedImage(imageBytes);

			// <b>methods of Imaging usually accept files, byte arrays, or
			// inputstreams as arguments. </b>
			final BufferedImage image_2 = Imaging.getBufferedImage(imageBytes);

			// <b>Write an image. </b>
			final BufferedImage image = image_1;
			final File dst = someFile;
			final ImageFormat format = ImageFormats.BMP;
			final Map<String, Object> optionalParams = new HashMap<>();
			Imaging.writeImage(image, dst, format, optionalParams);

			final OutputStream os = someOutputStream;
			Imaging.writeImage(image, os, format, optionalParams);
			//
			// // <b>get the image's embedded ICC Profile, if it has one. </b>
			// final byte iccProfileBytes[] =
			// Imaging.getICCProfileBytes(imageBytes);
			//
			// final ICC_Profile iccProfile = Imaging.getICCProfile(imageBytes);
			//
			// // <b>get the image's width and height. </b>
			// final Dimension d = Imaging.getImageSize(imageBytes);
			//
			// // <b>get all of the image's info (ie. bits per pixel, size,
			// // transparency, etc.) </b>
			// final ImageInfo imageInfo = Imaging.getImageInfo(imageBytes);
			//
			// if (imageInfo.getColorType() == ImageInfo.ColorType.GRAYSCALE) {
			// System.out.println("Grayscale image.");
			// }
			// if (imageInfo.getHeight() > 1000) {
			// System.out.println("Large image.");
			// }
			//
			// // <b>try to guess the image's format. </b>
			// final ImageFormat imageFormat = Imaging.guessFormat(imageBytes);
			// imageFormat.equals(ImageFormats.PNG);
			//
			// // <b>get all metadata stored in EXIF format (ie. from JPEG or
			// // TIFF). </b>
			// final ImageMetadata metadata = Imaging.getMetadata(imageBytes);
			//
			// // <b>print a dump of information about an image to stdout. </b>
			Imaging.dumpImageFile(imageBytes);
			//
			// // <b>get a summary of format errors. </b>
			// final FormatCompliance formatCompliance =
			// Imaging.getFormatCompliance(imageBytes);

		} catch (final Exception e) {

		}
	}

	public static void main(String[] args) {
		SampleUsage s = new SampleUsage();
	}
}