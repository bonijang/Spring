package io.github.seccoding.web.mimetype.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.seccoding.web.mimetype.abst.ExtensionFilter;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

public class JMimeMagicMimeTypeFilter extends ExtensionFilter {

	@Override
	public String getMimeType(File currentFile) {
		String mimeType = null;
		
		try {
			Path path = Paths.get(currentFile.getAbsolutePath());
			byte[] data = Files.readAllBytes(path);
			MagicMatch match = Magic.getMagicMatch(data);
			mimeType = match.getMimeType();
		} catch (MagicParseException | MagicMatchNotFoundException | MagicException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return mimeType;
	}

	@Override
	protected boolean isEquals(String mimeTypeOfFile, String extension) {
		if ( mimeTypeOfFile.equalsIgnoreCase( extension.toUpperCase() ) ) {
			return true;
		}
		return false;
	}
	
}
