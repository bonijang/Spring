package io.github.seccoding.web.mimetype.abst;

import java.io.File;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ExtensionFilter {
	
	private Logger logger = LoggerFactory.getLogger(ExtensionFilter.class);

	public boolean doFilter(String filePath, String ... validExtensions) {
		
		File currentFile = new File(filePath);
		String mimeType = getMimeType(currentFile);
		logger.debug(mimeType);
		for (String extension : validExtensions) {
			if ( isEquals(mimeType, extension) ) {
				return true;
			}
		}
		
		return false;
	}

	public abstract String getMimeType(File currentFile);
	
	protected abstract boolean isEquals(String mimeTypeOfFile, String extension);
	
}
